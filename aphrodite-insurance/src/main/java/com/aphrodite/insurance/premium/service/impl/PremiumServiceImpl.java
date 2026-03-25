package com.aphrodite.insurance.premium.service.impl;


import com.aphrodite.common.api.ApiRequest;
import com.aphrodite.insurance.component.CacheComponent;
import com.aphrodite.insurance.component.PlanStepManager;
import com.aphrodite.insurance.component.PolicyComponent;
import com.aphrodite.insurance.constants.PRKeyConstants;
import com.aphrodite.insurance.constants.StepTypeEnum;
import com.aphrodite.insurance.dao.CustomerInfoMapper;
import com.aphrodite.insurance.dto.PolicyInfoDTO;
import com.aphrodite.insurance.dto.PolicyPlanDTO;
import com.aphrodite.insurance.entity.CustomerInfoEntity;
import com.aphrodite.insurance.entity.PlanTblEntity;
import com.aphrodite.insurance.entity.PolicyInfoEntity;
import com.aphrodite.insurance.entity.PolicyPlanEntity;
import com.aphrodite.insurance.premium.dto.PlanPremiumDTO;
import com.aphrodite.insurance.premium.dto.PremiumCalculateReqDTO;
import com.aphrodite.insurance.premium.dto.PremiumCalculateResDTO;
import com.aphrodite.insurance.premium.service.PremiumService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eredar.janus.core.annotation.Janus;
import com.eredar.janus.core.constants.CompareType;
import com.ethan.step.utils.OraDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Service
public class PremiumServiceImpl implements PremiumService {

    @Autowired
    private PlanStepManager planStepManager;
    @Autowired
    private PolicyComponent policyComponent;
    @Autowired
    private CacheComponent cacheComponent;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Janus(
            methodId = "calculatePremium",
            businessKey = "#request.data.policyNo",
            compareType = CompareType.ASYNC_COMPARE
    )
    @Override
    public PremiumCalculateResDTO calculatePremium(ApiRequest<PremiumCalculateReqDTO> request) {
        PremiumCalculateReqDTO reqDTO = request.getData();

        PolicyInfoEntity policyInfoEntity = policyComponent.queryPolicyInfo(reqDTO.getPolicyNo());
        PolicyInfoDTO policyInfo = new PolicyInfoDTO(policyInfoEntity);

        List<PolicyPlanEntity> policyPlanEntityList = policyComponent.queryPolicyPlanList(reqDTO.getPolicyNo());
        List<PolicyPlanDTO> policyPlanList = policyPlanEntityList.stream().map(PolicyPlanDTO::new).toList();

        List<PlanPremiumDTO> premiumList = new ArrayList<>();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("policyInfo", policyInfo);
        LocalDateTime calcDate = reqDTO.getCalcDate();
        if (calcDate == null) {
            calcDate = LocalDateTime.now();
        }
        paramsMap.put("calcDate", calcDate);
        for (PolicyPlanDTO policyPlan : policyPlanList) {
            paramsMap.put("policyPlan", policyPlan);
            PlanTblEntity plan = cacheComponent.queryPlanTbl(policyPlan.getPlanCode());
            paramsMap.put("plan", plan);
            CustomerInfoEntity insuredPerson = customerInfoMapper.selectOne(new LambdaQueryWrapper<CustomerInfoEntity>()
                    .select(CustomerInfoEntity::getId,
                            CustomerInfoEntity::getCustomerName,
                            CustomerInfoEntity::getBirthDate,
                            CustomerInfoEntity::getGender,
                            CustomerInfoEntity::getPhoneNumber,
                            CustomerInfoEntity::getAddress,
                            CustomerInfoEntity::getLifeStatus
                    )
                    .eq(CustomerInfoEntity::getId, policyPlan.getInsuredId())
            );
            paramsMap.put("insuredPerson", insuredPerson);
            Map<String, Object> map = planStepManager.executeByStepType(policyPlan.getPlanCode(), StepTypeEnum.PR001.getCode(), paramsMap);
            OraDecimal premiumAmount = (OraDecimal) map.get(PRKeyConstants.PR002_premiumAmount);
            premiumList.add(PlanPremiumDTO.builder()
                    .orderNo(policyPlan.getOrderNo())
                    .premiumAmount(premiumAmount)
                    .build());
        }

        return PremiumCalculateResDTO.builder()
                .premiumList(premiumList)
                .build();
    }
}
