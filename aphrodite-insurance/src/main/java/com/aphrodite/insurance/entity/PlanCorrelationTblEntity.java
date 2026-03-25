package com.aphrodite.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * PLAN_CORRELATION_TBL 险种基数相关性表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("plan_correlation_tbl")
@Schema(title = "险种基数相关性表")
public class PlanCorrelationTblEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "险种代码")
    private String planCode;

    @Schema(title = "表名")
    private String tableName;

    @Schema(title = "相关性")
    private String correlation;
}

