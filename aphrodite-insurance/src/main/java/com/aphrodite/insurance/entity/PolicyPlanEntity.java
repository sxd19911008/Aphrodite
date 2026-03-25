package com.aphrodite.insurance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ethan.step.utils.OraDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * POLICY_PLAN 保单险种明细表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("POLICY_PLAN")
@Schema(title = "保单险种明细表")
public class PolicyPlanEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
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

    @Schema(title = "创建人")
    private String createdBy;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新人")
    private String updatedBy;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}

