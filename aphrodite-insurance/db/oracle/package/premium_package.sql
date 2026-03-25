/**
 * 保费相关包
 */
CREATE OR REPLACE PACKAGE premium_package AS
    -- 常量定义
    SUCCESS CONSTANT VARCHAR2(1) := '0';
    FAILURE CONSTANT VARCHAR2(2) := '-1';

    -- 定义输入列表类型
    TYPE t_policy_plan_list IS TABLE OF POLICY_PLAN%ROWTYPE INDEX BY PLS_INTEGER;
    -- 定义保费结果Map类型，Key为ORDER_NO，Value为保费
    TYPE t_premium_map IS TABLE OF NUMBER INDEX BY PLS_INTEGER;

    -- 计算所有险种保费的方法
    PROCEDURE calc_premium(
        p_policy_info IN POLICY_INFO%ROWTYPE,
        p_policy_plan_list IN t_policy_plan_list,
        p_calc_date IN DATE,
        p_premium_map OUT t_premium_map,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    );

    -- JSON接口：方便Java项目调用
    PROCEDURE calc_premium_json(
        p_json_policy_info IN VARCHAR2,
        p_json_policy_plan_list IN VARCHAR2,
        p_calc_date IN DATE,
        p_json_premium_map OUT VARCHAR2,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    );

    -- 根据保单号计算保费
    PROCEDURE calc_premium_by_policy_no(
        p_policy_no IN VARCHAR2,
        p_calc_date IN DATE,
        p_premium_map OUT t_premium_map,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    );

    -- 根据保单号计算保费（JSON接口）
    PROCEDURE calc_premium_by_policy_no_json(
        p_policy_no IN VARCHAR2,
        p_calc_date IN DATE,
        p_json_premium_map OUT VARCHAR2,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    );
END premium_package;
/

CREATE OR REPLACE PACKAGE BODY premium_package AS

    /**
     * 计算单个100险种计划的保费 (私有方法)
     */
    FUNCTION get_single_premium_100(
        p_policy_info IN POLICY_INFO%ROWTYPE,
        p_policy_plan IN POLICY_PLAN%ROWTYPE,
        p_calc_date IN DATE
    ) RETURN NUMBER IS
        v_calc_date   DATE;
        v_unit_sum    PLAN_TBL.UNIT_SUM%type;
        v_unit_price  PLAN_TBL.UNIT_PRICE%type;
        v_birth_date  CUSTOMER_INFO.BIRTH_DATE%type;
        v_age         NUMBER;
        v_gender      VARCHAR2(1);
        v_annual_prem NUMBER;
        v_unit        NUMBER;
        v_premium     NUMBER;
    BEGIN
        -- 查询险种单价 (1份价格)
        SELECT UNIT_SUM, UNIT_PRICE
        INTO v_unit_sum, v_unit_price
        FROM PLAN_TBL
        WHERE PLAN_CODE = '100';

        -- 查询被保人信息 (出生日期)
        SELECT BIRTH_DATE
        INTO v_birth_date
        FROM CUSTOMER_INFO
        WHERE ID = p_policy_plan.INSURED_ID;

        v_gender := 'M'; 

        IF v_birth_date IS NULL THEN
            RAISE_APPLICATION_ERROR(-20003, '被保人 [ID:' || p_policy_plan.INSURED_ID || '] 出生日期为空，无法计算年龄');
        END IF;

        v_calc_date := p_calc_date;
        IF v_calc_date IS NULL THEN
            -- 计算时间为空时，设置为当前时间。
            v_calc_date := sysdate;
        END IF;

        -- 计算年龄
        v_age := common_utils_package.calc_age(v_birth_date, v_calc_date);

        -- 计算份数
        IF v_unit_sum = 'S' THEN
            -- 保额型险种：保额除以基本保额
            v_unit := p_policy_plan.SUM_ASSURED / v_unit_price;
        ELSE
            -- 份数型险种：直接取份数
            v_unit := p_policy_plan.UNIT;
        END IF;

        -- 查询费率 (年交保费)
        SELECT ANNUAL_PREMIUM 
        INTO v_annual_prem
        FROM PREM_RATE_TBL 
        WHERE PLAN_CODE = '100'
            AND GENDER = v_gender
            AND AGE = v_age
            AND PAYMENT_PERIOD = p_policy_plan.PAYMENT_PERIOD
            AND COVERAGE_PERIOD = p_policy_plan.COVERAGE_PERIOD;
        
        -- 计算保费
        IF v_annual_prem IS NOT NULL AND v_unit IS NOT NULL THEN
            v_premium := v_annual_prem * v_unit;
        ELSE
            v_premium := 0;
        END IF;

        RETURN v_premium;
    END get_single_premium_100;

    PROCEDURE calc_premium(
        p_policy_info IN POLICY_INFO%ROWTYPE,
        p_policy_plan_list IN t_policy_plan_list,
        p_calc_date IN DATE,
        p_premium_map OUT t_premium_map,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    ) IS
    BEGIN
        -- 循环处理每一个险种计划
        FOR i IN p_policy_plan_list.FIRST .. p_policy_plan_list.LAST LOOP
            IF p_policy_plan_list.EXISTS(i) THEN
                
                -- 策略模式：根据险种代码调用不同的计算方法
                IF p_policy_plan_list(i).PLAN_CODE = '100' THEN
                    -- 调用100险种计算方法
                    p_premium_map(p_policy_plan_list(i).ORDER_NO) := get_single_premium_100(p_policy_info, p_policy_plan_list(i), p_calc_date);
                
                -- ELSIF p_policy_plan_list(i).PLAN_CODE = '200' THEN
                --     ...
                
                ELSE
                    -- 如果没有匹配的计算逻辑，抛出异常
                    RAISE_APPLICATION_ERROR(-20001, '未实现险种 [' || p_policy_plan_list(i).PLAN_CODE || '] 的保费计算方法');
                END IF;

            END IF;
        END LOOP;

        p_flag := SUCCESS;
    EXCEPTION
        WHEN OTHERS THEN
            p_flag := FAILURE;
            p_msg := SQLERRM;
    END calc_premium;

    PROCEDURE calc_premium_json(
        p_json_policy_info IN VARCHAR2,
        p_json_policy_plan_list IN VARCHAR2,
        p_calc_date IN DATE,
        p_json_premium_map OUT VARCHAR2,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    ) IS
        v_policy_info      POLICY_INFO%ROWTYPE;
        v_policy_plan_list t_policy_plan_list;
        v_premium_map      t_premium_map;
        
        v_idx              PLS_INTEGER := 1;
        v_map_json_obj     JSON_OBJECT_T := JSON_OBJECT_T();
        v_key              PLS_INTEGER;

        v_flag             VARCHAR2(10);
        v_msg              VARCHAR2(4000);
    BEGIN
        -- 1. 解析 policy_info
        SELECT * INTO 
            v_policy_info.POLICY_NO, 
            v_policy_info.APPLICATION_NO, 
            v_policy_info.APPLY_DATE, 
            v_policy_info.EFFECTIVE_DATE, 
            v_policy_info.POLICY_STATUS
        FROM JSON_TABLE(p_json_policy_info, '$'
            COLUMNS (
                POLICY_NO      VARCHAR2(50)  PATH '$.POLICY_NO',
                APPLICATION_NO VARCHAR2(50)  PATH '$.APPLICATION_NO',
                APPLY_DATE     DATE          PATH '$.APPLY_DATE',
                EFFECTIVE_DATE DATE          PATH '$.EFFECTIVE_DATE',
                POLICY_STATUS  VARCHAR2(10)  PATH '$.POLICY_STATUS'
            )
        );

        -- 2. 解析 policy_plan_list
        FOR rec IN (
            SELECT * FROM JSON_TABLE(p_json_policy_plan_list, '$[*]'
                COLUMNS (
                    POLICY_NO        VARCHAR2(50)  PATH '$.POLICY_NO',
                    ORDER_NO         NUMBER        PATH '$.ORDER_NO',
                    PLAN_CODE        VARCHAR2(20)  PATH '$.PLAN_CODE',
                    INSURED_ID       NUMBER        PATH '$.INSURED_ID',
                    HOLDER_ID        NUMBER        PATH '$.HOLDER_ID',
                    EFFECTIVE_DATE   DATE          PATH '$.EFFECTIVE_DATE',
                    STATUS           VARCHAR2(10)  PATH '$.STATUS',
                    PREMIUM          NUMBER        PATH '$.PREMIUM',
                    SUM_ASSURED      NUMBER        PATH '$.SUM_ASSURED',
                    UNIT             NUMBER        PATH '$.UNIT',
                    PAYMENT_PERIOD    NUMBER       PATH '$.PAYMENT_PERIOD',
                    COVERAGE_PERIOD  NUMBER        PATH '$.COVERAGE_PERIOD'
                )
            )
        ) LOOP
            v_policy_plan_list(v_idx).POLICY_NO       := rec.POLICY_NO;
            v_policy_plan_list(v_idx).ORDER_NO        := rec.ORDER_NO;
            v_policy_plan_list(v_idx).PLAN_CODE       := rec.PLAN_CODE;
            v_policy_plan_list(v_idx).INSURED_ID      := rec.INSURED_ID;
            v_policy_plan_list(v_idx).HOLDER_ID       := rec.HOLDER_ID;
            v_policy_plan_list(v_idx).EFFECTIVE_DATE  := rec.EFFECTIVE_DATE;
            v_policy_plan_list(v_idx).STATUS          := rec.STATUS;
            v_policy_plan_list(v_idx).PREMIUM         := rec.PREMIUM;
            v_policy_plan_list(v_idx).SUM_ASSURED     := rec.SUM_ASSURED;
            v_policy_plan_list(v_idx).UNIT            := rec.UNIT;
            v_policy_plan_list(v_idx).PAYMENT_PERIOD  := rec.PAYMENT_PERIOD;
            v_policy_plan_list(v_idx).COVERAGE_PERIOD := rec.COVERAGE_PERIOD;
            v_idx := v_idx + 1;
        END LOOP;

        -- 3. 调用核心计算方法
        calc_premium(v_policy_info, v_policy_plan_list, p_calc_date, v_premium_map, v_flag, v_msg);

        IF v_flag = FAILURE THEN
            RAISE_APPLICATION_ERROR(-20002, v_msg);
        END IF;

        -- 4. 将结果序列化为JSON
        v_key := v_premium_map.FIRST;
        WHILE v_key IS NOT NULL LOOP
            v_map_json_obj.put(TO_CHAR(v_key), v_premium_map(v_key));
            v_key := v_premium_map.NEXT(v_key);
        END LOOP;

        p_json_premium_map := v_map_json_obj.to_string();
        p_flag := SUCCESS;
    EXCEPTION
        WHEN OTHERS THEN
            p_flag := FAILURE;
            p_msg := SQLERRM;
    END calc_premium_json;

    /**
     * 根据保单号计算保费
     * 内部查询数据库获取 POLICY_INFO 和 POLICY_PLAN 列表后调用核心计算逻辑
     */
    PROCEDURE calc_premium_by_policy_no(
        p_policy_no IN VARCHAR2,
        p_calc_date IN DATE,
        p_premium_map OUT t_premium_map,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    ) IS
        v_policy_info POLICY_INFO%ROWTYPE;
        v_policy_plan_list t_policy_plan_list;
        v_idx PLS_INTEGER := 1;
    BEGIN
        -- 1. 查询保单主信息
        BEGIN
            SELECT * INTO v_policy_info FROM POLICY_INFO WHERE POLICY_NO = p_policy_no;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                p_flag := FAILURE;
                p_msg := '未找到保单号为 [' || p_policy_no || '] 的保单主信息';
                RETURN;
        END;

        -- 2. 查询保单险种列表
        FOR rec IN (SELECT * FROM POLICY_PLAN WHERE POLICY_NO = p_policy_no ORDER BY ORDER_NO) LOOP
            v_policy_plan_list(v_idx) := rec;
            v_idx := v_idx + 1;
        END LOOP;

        IF v_policy_plan_list.COUNT = 0 THEN
            p_flag := FAILURE;
            p_msg := '保单号为 [' || p_policy_no || '] 的保单没有任何险种计划';
            RETURN;
        END IF;

        -- 3. 调用核心计算方法
        calc_premium(v_policy_info, v_policy_plan_list, p_calc_date, p_premium_map, p_flag, p_msg);

    EXCEPTION
        WHEN OTHERS THEN
            p_flag := FAILURE;
            p_msg := '根据保单号计算保费异常：' || SQLERRM;
    END calc_premium_by_policy_no;

    /**
     * 根据保单号计算保费（JSON接口）
     * 调用 calc_premium_by_policy_no 并将结果 map 转换为 JSON 格式
     */
    PROCEDURE calc_premium_by_policy_no_json(
        p_policy_no IN VARCHAR2,
        p_calc_date IN DATE,
        p_json_premium_map OUT VARCHAR2,
        p_flag OUT VARCHAR2,
        p_msg OUT VARCHAR2
    ) IS
        v_premium_map  t_premium_map;
        v_map_json_obj JSON_OBJECT_T := JSON_OBJECT_T();
        v_key          PLS_INTEGER;
    BEGIN
        -- 1. 调用核心逻辑
        calc_premium_by_policy_no(p_policy_no, p_calc_date, v_premium_map, p_flag, p_msg);

        IF p_flag = FAILURE THEN
            RETURN;
        END IF;

        -- 2. 将结果序列化为JSON
        v_key := v_premium_map.FIRST;
        WHILE v_key IS NOT NULL LOOP
            v_map_json_obj.put(TO_CHAR(v_key), v_premium_map(v_key));
            v_key := v_premium_map.NEXT(v_key);
        END LOOP;

        p_json_premium_map := v_map_json_obj.to_string();
        p_flag := SUCCESS;
    EXCEPTION
        WHEN OTHERS THEN
            p_flag := FAILURE;
            p_msg := '根据保单号计算保费(JSON)异常：' || SQLERRM;
    END calc_premium_by_policy_no_json;

END premium_package;
/
