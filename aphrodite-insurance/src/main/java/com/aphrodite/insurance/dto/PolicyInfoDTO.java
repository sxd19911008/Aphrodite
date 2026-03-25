package com.aphrodite.insurance.dto;

import com.aphrodite.insurance.entity.PolicyInfoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/*
 * POLICY_INFO 保单主信息 DTO 对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "保单主信息 DTO")
public class PolicyInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "保单号")
    private String policyNo;

    @Schema(title = "投保单号")
    private String applicationNo;

    @Schema(title = "投保日期")
    private LocalDate applyDate;

    @Schema(title = "保单生效时间")
    private LocalDate effectiveDate;

    @Schema(title = "保单状态")
    private String policyStatus;

    public PolicyInfoDTO(PolicyInfoEntity entity) {
        if (entity != null) {
            this.policyNo = entity.getPolicyNo();
            this.applicationNo = entity.getApplicationNo();
            this.applyDate = entity.getApplyDate();
            this.effectiveDate = entity.getEffectiveDate();
            this.policyStatus = entity.getPolicyStatus();
        }
    }
}

