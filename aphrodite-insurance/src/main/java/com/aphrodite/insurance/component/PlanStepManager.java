package com.aphrodite.insurance.component;

import com.aphrodite.common.constants.ErrCodeEnum;
import com.aphrodite.common.exception.AphroditeException;
import com.aphrodite.common.utils.AphUtils;
import com.aphrodite.common.utils.JsonUtils;
import com.aphrodite.common.web.intf.InitInterface;
import com.aphrodite.insurance.dao.PlanStepTblMapper;
import com.aphrodite.insurance.entity.PlanStepTblEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.step.StepExecutor;
import com.ethan.step.dto.StepContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlanStepManager implements InitInterface {

    private final Map<String, Map<String, List<String>>> planStepMap = new HashMap<>();

    @Autowired
    private PlanStepTblMapper planStepTblMapper;
    @Autowired
    private StepExecutor stepExecutor;


    @Override
    public void init() {
        List<PlanStepTblEntity> planStepTblList = planStepTblMapper.selectList(new LambdaQueryWrapper<PlanStepTblEntity>()
                .select(PlanStepTblEntity::getPlanCode,
                        PlanStepTblEntity::getStepType,
                        PlanStepTblEntity::getSeq,
                        PlanStepTblEntity::getStepCode)
        );

        Map<String, Map<String, List<PlanStepTblEntity>>> tempMap = new HashMap<>();
        for (PlanStepTblEntity entity : planStepTblList) {
            Map<String, List<PlanStepTblEntity>> typeMap = tempMap.computeIfAbsent(entity.getPlanCode(), k -> new HashMap<>());
            List<PlanStepTblEntity> tblList = typeMap.computeIfAbsent(entity.getStepType(), k -> new ArrayList<>());
            tblList.add(entity);
        }

        for (Map.Entry<String, Map<String, List<PlanStepTblEntity>>> planEntry : tempMap.entrySet()) {
            String planCode = planEntry.getKey();
            Map<String, List<String>> resTypeMap = planStepMap.computeIfAbsent(planCode, k -> new HashMap<>());
            Map<String, List<PlanStepTblEntity>> typeMap = planEntry.getValue();
            for (Map.Entry<String, List<PlanStepTblEntity>> entry : typeMap.entrySet()) {
                String type = entry.getKey();
                List<PlanStepTblEntity> tblList = entry.getValue().stream()
                        .sorted(Comparator.comparing(PlanStepTblEntity::getSeq))
                        .toList();
                for (int i = 0; i < tblList.size(); i++) {
                    PlanStepTblEntity entity = tblList.get(i);
                    if (i != entity.getSeq() - 1) {
                        throw new AphroditeException(ErrCodeEnum.INIT_ERR, "PLAN_STEP_TBL表order不合法：" + JsonUtils.writeValueAsString(entity));
                    }
                }
                resTypeMap.put(type, tblList.stream().map(PlanStepTblEntity::getStepCode).toList());
            }
        }
    }

    /**
     * 查询险种的某个类型的步骤
     *
     * @param planCode 险种
     * @param stepType 步骤类型
     * @return 步骤 Code 列表
     */
    public List<String> getStepCodeList(String planCode, String stepType) {
        Map<String, List<String>> typeMap = planStepMap.get(planCode);
        if (typeMap == null) {
            throw new AphroditeException(ErrCodeEnum.STEP_ERR, String.format("【%s】险种未配置任何步骤", planCode));
        }
        List<String> planStepTblList = typeMap.get(stepType);
        if (AphUtils.isEmpty(planStepTblList)) {
            throw new AphroditeException(ErrCodeEnum.STEP_ERR, String.format("【%s】险种未配置【%s】步骤", planCode, stepType));
        }
        return planStepTblList;
    }

    /**
     * 执行险种指定的步骤类型对应的所有步骤
     *
     * @param planCode 险种
     * @param stepType 步骤类型
     * @param paramsMap 参数集合
     */
    public Map<String, Object> executeByStepType(String planCode, String stepType, Map<String, Object> paramsMap) {
        List<String> stepCodeList = this.getStepCodeList(planCode, stepType);
        StepContext stepContext = StepContext.builder().paramsMap(paramsMap).build();
        Map<String, Object> resMap = new HashMap<>();
        for (String stepCode : stepCodeList) {
            Map<String, Object> tempMap = stepExecutor.executeByStepCode(stepCode, stepContext, null);
            stepContext.putAll(tempMap);
            resMap.putAll(tempMap);
        }
        return resMap;
    }
}
