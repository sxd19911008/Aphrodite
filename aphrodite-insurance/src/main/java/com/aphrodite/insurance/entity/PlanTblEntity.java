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
 * PLAN_TBL 险种表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("PLAN_TBL")
@Schema(title = "险种表")
public class PlanTblEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "PLAN_CODE", type = IdType.INPUT)
    @Schema(title = "险种代码")
    private String planCode;

    @Schema(title = "险种名称")
    private String planName;

    @Schema(title = "计费类型", description = "U-份数型, S-保额型")
    private String unitSum;

    @Schema(title = "1份价格")
    private OraDecimal unitPrice;

    @Schema(title = "起售日期")
    private LocalDate saleStartDate;

    @Schema(title = "停售日期")
    private LocalDate saleEndDate;

    @Schema(title = "状态", description = "1-有效, 0-无效")
    private Long status;

    @Schema(title = "创建人")
    private String createdBy;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新人")
    private String updatedBy;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}

