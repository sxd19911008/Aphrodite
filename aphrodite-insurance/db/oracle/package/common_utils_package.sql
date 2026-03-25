/*
 * 公共工具包，用于存放通用的计算逻辑
 */
CREATE OR REPLACE PACKAGE common_utils_package AS
    /**
     * 根据出生日期和计算日期计算年龄（周岁）
     * @param p_birth_date 出生日期
     * @param p_calc_date 计算日期
     * @return 年龄（整数）
     */
    FUNCTION calc_age(
        p_birth_date IN DATE,
        p_calc_date IN DATE)
        RETURN NUMBER;
END common_utils_package;
/

CREATE OR REPLACE PACKAGE BODY common_utils_package AS
    /**
     * 根据出生日期和计算日期计算年龄（周岁）
     * @param p_birth_date 出生日期
     * @param p_calc_date 计算日期
     * @return 年龄（整数）
     */
    FUNCTION calc_age(
        p_birth_date IN DATE,
        p_calc_date IN DATE)
        RETURN NUMBER IS
    BEGIN
        -- 使用 MONTHS_BETWEEN 计算月数差，除以 12 后取整得到周岁
        RETURN TRUNC(MONTHS_BETWEEN(p_calc_date, p_birth_date) / 12);
    END calc_age;
END common_utils_package;
/

