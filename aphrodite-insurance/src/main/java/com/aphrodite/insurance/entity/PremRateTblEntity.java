package com.aphrodite.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ethan.step.utils.OraDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * PREM_RATE_TBL 费率表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("PREM_RATE_TBL")
@Schema(title = "费率表")
public class PremRateTblEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "险种代码")
    private String planCode;

    @Schema(title = "性别", description = "M-男; F-女")
    private String gender;

    @Schema(title = "被保人年龄")
    private Long age;

    @Schema(title = "缴费年期")
    private Long paymentPeriod;

    @Schema(title = "保障年期")
    private Long coveragePeriod;

    @Schema(title = "保单状态")
    private String planStatus;

    @Schema(title = "年交保费")
    private OraDecimal annualPremium;
}

