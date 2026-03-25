package com.aphrodite.insurance.premium.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "保费计算响应对象")
public class PremiumCalculateResDTO {

    @Schema(title = "保费计算响应对象", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<PlanPremiumDTO> premiumList;
}
