package com.aphrodite.insurance.component;

import com.aphrodite.insurance.dao.PolicyInfoMapper;
import com.aphrodite.insurance.dao.PolicyPlanMapper;
import com.aphrodite.insurance.entity.PolicyInfoEntity;
import com.aphrodite.insurance.entity.PolicyPlanEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolicyComponent {

    @Autowired
    private PolicyInfoMapper policyInfoMapper;
    @Autowired
    private PolicyPlanMapper policyPlanMapper;

    /**
     * 根据保单号查询 POLICY_INFO 表，如果返回多条则报错。
     *
     * @param policyNo 保单号
     * @return POLICY_INFO 表信息
     */
    public PolicyInfoEntity queryPolicyInfo(String policyNo) {
        return policyInfoMapper.selectOne(new LambdaQueryWrapper<PolicyInfoEntity>()
                .select(PolicyInfoEntity::getPolicyNo,
                        PolicyInfoEntity::getApplicationNo,
                        PolicyInfoEntity::getApplyDate,
                        PolicyInfoEntity::getEffectiveDate,
                        PolicyInfoEntity::getPolicyStatus)
                .eq(PolicyInfoEntity::getPolicyNo, policyNo)
        );
    }

    /**
     * 根据保单号查询 POLICY_PLAN 表。
     *
     * @param policyNo 保单号
     * @return POLICY_INFO 表信息列表
     */
    public List<PolicyPlanEntity> queryPolicyPlanList(String policyNo) {
        return policyPlanMapper.selectList(new LambdaQueryWrapper<PolicyPlanEntity>()
                .select(PolicyPlanEntity::getPolicyNo,
                        PolicyPlanEntity::getOrderNo,
                        PolicyPlanEntity::getPlanCode,
                        PolicyPlanEntity::getInsuredId,
                        PolicyPlanEntity::getHolderId,
                        PolicyPlanEntity::getEffectiveDate,
                        PolicyPlanEntity::getStatus,
                        PolicyPlanEntity::getPremium,
                        PolicyPlanEntity::getSumAssured,
                        PolicyPlanEntity::getUnit,
                        PolicyPlanEntity::getPaymentPeriod,
                        PolicyPlanEntity::getCoveragePeriod)
                .eq(PolicyPlanEntity::getPolicyNo, policyNo)
        );
    }

    /**
     * 根据保单号和主附约号查询 POLICY_INFO 表，如果返回多条则报错。
     *
     * @param policyNo 保单号
     * @param orderNo 主附约号
     * @return POLICY_INFO 表信息
     */
    public PolicyPlanEntity queryPolicyPlan(String policyNo, Integer orderNo) {
        return policyPlanMapper.selectOne(new LambdaQueryWrapper<PolicyPlanEntity>()
                .select(PolicyPlanEntity::getPolicyNo,
                        PolicyPlanEntity::getOrderNo,
                        PolicyPlanEntity::getPlanCode,
                        PolicyPlanEntity::getInsuredId,
                        PolicyPlanEntity::getHolderId,
                        PolicyPlanEntity::getEffectiveDate,
                        PolicyPlanEntity::getStatus,
                        PolicyPlanEntity::getPremium,
                        PolicyPlanEntity::getSumAssured,
                        PolicyPlanEntity::getUnit,
                        PolicyPlanEntity::getPaymentPeriod,
                        PolicyPlanEntity::getCoveragePeriod)
                .eq(PolicyPlanEntity::getPolicyNo, policyNo)
                .eq(PolicyPlanEntity::getOrderNo, orderNo)
        );
    }
}
