package com.aphrodite.insurance.premium.dto;

import com.ethan.step.utils.OraDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "险种保费对象")
public class PlanPremiumDTO {

    @Schema(title = "主附约号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderNo;
    @Schema(title = "保费金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private OraDecimal premiumAmount;
}
