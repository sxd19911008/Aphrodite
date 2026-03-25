package com.aphrodite.insurance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * POLICY_INFO 保单主信息表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("POLICY_INFO")
@Schema(title = "保单主信息表")
public class PolicyInfoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "POLICY_NO", type = IdType.INPUT)
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

    @Schema(title = "创建人")
    private String createdBy;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新人")
    private String updatedBy;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}

