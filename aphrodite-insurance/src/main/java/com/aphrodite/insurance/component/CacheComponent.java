package com.aphrodite.insurance.component;

import com.aphrodite.common.constants.ErrCodeEnum;
import com.aphrodite.common.exception.AphroditeException;
import com.aphrodite.common.utils.AphUtils;
import com.aphrodite.insurance.constants.BaseDataTblEnum;
import com.aphrodite.insurance.dao.CustomerInfoMapper;
import com.aphrodite.insurance.dao.PlanTblMapper;
import com.aphrodite.insurance.dao.PremRateTblMapper;
import com.aphrodite.insurance.dto.PolicyPlanDTO;
import com.aphrodite.insurance.entity.CustomerInfoEntity;
import com.aphrodite.insurance.entity.PlanTblEntity;
import com.aphrodite.insurance.entity.PremRateTblEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.step.utils.StepUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// TODO 做缓存
@Component
public class CacheComponent {

    @Autowired
    private PlanTblMapper planTblMapper;
    @Autowired
    private PremRateTblMapper premRateTblMapper;
    @Autowired
    private PlanCorrelationManager planCorrelationManager;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    /**
     * 根据险种code查询 PLAN_TBL 表，如果返回多条则报错。
     *
     * @param planCode 保单号
     * @return PLAN_TBL 表信息
     */
    public PlanTblEntity queryPlanTbl(String planCode) {
        return planTblMapper.selectOne(new LambdaQueryWrapper<PlanTblEntity>()
                .select(PlanTblEntity::getPlanCode,
                        PlanTblEntity::getPlanName,
                        PlanTblEntity::getUnitSum,
                        PlanTblEntity::getUnitPrice,
                        PlanTblEntity::getSaleStartDate,
                        PlanTblEntity::getSaleEndDate,
                        PlanTblEntity::getStatus)
                .eq(PlanTblEntity::getPlanCode, planCode)
        );
    }

    /**
     * 根据相关性查询费率表 PREM_RATE_TBL
     *
     * @param paramsMap 参数集合
     * @return 费率表信息
     */
    public PremRateTblEntity queryPremRateTbl(Map<String, Object> paramsMap) {
        PolicyPlanDTO policyPlan = (PolicyPlanDTO) paramsMap.get("policyPlan");
        List<String> correlation = planCorrelationManager.getCorrelation(policyPlan.getPlanCode(), BaseDataTblEnum.PREM_RATE_TBL.getTableName());

        LambdaQueryWrapper<PremRateTblEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PremRateTblEntity::getAnnualPremium)
                .eq(PremRateTblEntity::getPlanCode, policyPlan.getPlanCode());

        if (AphUtils.isNotEmpty(correlation)) {
            // 被保人信息
            CustomerInfoEntity insuredPerson = StepUtils.getValByMap("insuredPerson", paramsMap, CustomerInfoEntity.class);

            for (String key : correlation) {
                switch (key) {
                    case "gender":
                        if (insuredPerson == null) {
                            insuredPerson = customerInfoMapper.selectById(policyPlan.getInsuredId());
                        }
                        queryWrapper.eq(PremRateTblEntity::getGender, insuredPerson.getGender());
                        break;
                    case "age":
                        Long insAge = StepUtils.getValByMap("CM001_insAge", paramsMap, Long.class, true);
                        queryWrapper.eq(PremRateTblEntity::getAge, insAge);
                        break;
                    case "payment_period":
                        queryWrapper.eq(PremRateTblEntity::getPaymentPeriod, policyPlan.getPaymentPeriod());
                        break;
                    case "coverage_period":
                        queryWrapper.eq(PremRateTblEntity::getCoveragePeriod, policyPlan.getCoveragePeriod());
                        break;
                    case "plan_status":
                        queryWrapper.eq(PremRateTblEntity::getPlanStatus, policyPlan.getStatus());
                        break;
                    default:
                        throw new AphroditeException(
                                ErrCodeEnum.PLAN_CORRELATION_ERR,
                                String.format("【%s】险种【%s】表配置【%s】字段不合法",
                                        policyPlan.getPlanCode(),
                                        BaseDataTblEnum.PREM_RATE_TBL.getTableName(),
                                        key
                                )
                        );
                }
            }
        }
        return premRateTblMapper.selectOne(queryWrapper);
    }
}
