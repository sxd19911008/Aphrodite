package com.aphrodite.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PLAN_STEP_TBL 险种步骤关联表
 * <p>用于记录险种在不同流程（如：保费计算、保单校验等）中所对应的步骤。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("plan_step_tbl")
@Schema(title = "险种步骤关联表")
public class PlanStepTblEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "险种代码")
    private String planCode;

    @Schema(title = "步骤类型")
    private String stepType;

    @Schema(title = "顺序")
    private Long seq;

    @Schema(title = "步骤标识")
    private String stepCode;

    @Schema(title = "创建人")
    private String createdBy;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新人")
    private String updatedBy;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}

