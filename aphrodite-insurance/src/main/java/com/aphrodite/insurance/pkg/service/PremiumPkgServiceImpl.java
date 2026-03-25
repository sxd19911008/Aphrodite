package com.aphrodite.insurance.pkg.service;

import com.aphrodite.common.api.ApiRequest;
import com.aphrodite.common.utils.AphUtils;
import com.aphrodite.common.utils.JsonUtils;
import com.aphrodite.insurance.pkg.dao.PremiumPkgMapper;
import com.aphrodite.insurance.pkg.entity.CalcPremiumByPolicyNoJsonPkgEntity;
import com.aphrodite.insurance.premium.dto.PlanPremiumDTO;
import com.aphrodite.insurance.premium.dto.PremiumCalculateReqDTO;
import com.aphrodite.insurance.premium.dto.PremiumCalculateResDTO;
import com.aphrodite.insurance.premium.service.PremiumService;
import com.eredar.janus.core.annotation.Secondary;
import com.ethan.step.utils.OraDecimal;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Secondary
@Service
public class PremiumPkgServiceImpl implements PremiumService {

    @Autowired
    private PremiumPkgMapper premiumPkgMapper;

    @Override
    public PremiumCalculateResDTO calculatePremium(ApiRequest<PremiumCalculateReqDTO> request) {
        PremiumCalculateReqDTO reqDTO = request.getData();
        CalcPremiumByPolicyNoJsonPkgEntity pkgEntity = CalcPremiumByPolicyNoJsonPkgEntity.builder()
                .policyNo(reqDTO.getPolicyNo())
                .calcDate(reqDTO.getCalcDate())
                .build();
        premiumPkgMapper.calcPremiumByPolicyNoJson(pkgEntity);
        String jsonStr = pkgEntity.getJsonPremiumMap();
        Map<Long, OraDecimal> resMap = JsonUtils.readValue(jsonStr, new TypeReference<>() {});
        List<PlanPremiumDTO> premiumList = new ArrayList<>();
        if (AphUtils.isNotEmpty(resMap)) {
            List<Long> keyList = new ArrayList<>(resMap.keySet()).stream().sorted().toList();
            for (Long orderNo : keyList) {
                OraDecimal premiumAmount = resMap.get(orderNo);
                premiumList.add(PlanPremiumDTO.builder()
                        .orderNo(orderNo)
                        .premiumAmount(premiumAmount)
                        .build());
            }
        }
        return PremiumCalculateResDTO.builder()
                .premiumList(premiumList)
                .build();
    }
}
