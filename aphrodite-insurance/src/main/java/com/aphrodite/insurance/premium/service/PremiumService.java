package com.aphrodite.insurance.premium.service;

import com.aphrodite.common.api.ApiRequest;
import com.aphrodite.insurance.premium.dto.PremiumCalculateReqDTO;
import com.aphrodite.insurance.premium.dto.PremiumCalculateResDTO;

public interface PremiumService {
    PremiumCalculateResDTO calculatePremium(ApiRequest<PremiumCalculateReqDTO> request);
}
