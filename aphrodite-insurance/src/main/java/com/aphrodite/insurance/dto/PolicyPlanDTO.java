package com.aphrodite.insurance.dto;

import com.aphrodite.insurance.entity.PolicyPlanEntity;
import com.ethan.step.utils.OraDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/*
 * POLICY_PLAN 保单险种明细 DTO 对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "保单险种明细 DTO")
public class PolicyPlanDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键 ID")
    private Long id;

    @Schema(title = "保单号")
    private String policyNo;

    @Schema(title = "主附约号")
    private Long orderNo;

    @Schema(title = "险种代码")
    private String planCode;

    @Schema(title = "被保人 ID")
    private Long insuredId;

    @Schema(title = "投保人 ID")
    private Long holderId;

    @Schema(title = "险种生效时间")
    private LocalDate effectiveDate;

    @Schema(title = "险种状态")
    private String status;

    @Schema(title = "保费")
    private OraDecimal premium;

    @Schema(title = "保额")
    private OraDecimal sumAssured;

    @Schema(title = "份数")
    private Long unit;

    @Schema(title = "缴费年期")
    private Long paymentPeriod;

    @Schema(title = "保障年期")
    private Long coveragePeriod;

    public PolicyPlanDTO(PolicyPlanEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.policyNo = entity.getPolicyNo();
            this.orderNo = entity.getOrderNo();
            this.planCode = entity.getPlanCode();
            this.insuredId = entity.getInsuredId();
            this.holderId = entity.getHolderId();
            this.effectiveDate = entity.getEffectiveDate();
            this.status = entity.getStatus();
            this.premium = entity.getPremium();
            this.sumAssured = entity.getSumAssured();
            this.unit = entity.getUnit();
            this.paymentPeriod = entity.getPaymentPeriod();
            this.coveragePeriod = entity.getCoveragePeriod();
        }
    }
}

