package com.aphrodite.insurance.premium.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "保费计算请求对象")
public class PremiumCalculateReqDTO {

    @NotBlank(message = "保单号(policyNo)不能为空")
    @Schema(title = "保单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String policyNo;

    @Schema(title = "计算时间", description = "传null时，按照当前时间计算")
    private LocalDateTime calcDate;
}
