package com.aphrodite.insurance.premium;

import com.aphrodite.common.api.ApiRequest;
import com.aphrodite.common.api.ApiResponse;
import com.aphrodite.insurance.common.datasource.DataSourceContextHolder;
import com.aphrodite.insurance.common.datasource.DataSourceEnum;
import com.aphrodite.insurance.premium.dto.PremiumCalculateReqDTO;
import com.aphrodite.insurance.premium.dto.PremiumCalculateResDTO;
import com.aphrodite.insurance.premium.service.PremiumService;
import com.aphrodite.insurance.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/premium")
public class PremiumController {

    @Autowired
    private PremiumService premiumService;

    @PostMapping("/calculatePremium")
    public ApiResponse<PremiumCalculateResDTO> calculatePremium(@Validated @RequestBody ApiRequest<PremiumCalculateReqDTO> request) {
        DataSourceContextHolder.setDataSource(DataSourceEnum.Oracle.getDb());
        return ResponseUtils.success("calculatePremium", premiumService.calculatePremium(request));
    }
}
