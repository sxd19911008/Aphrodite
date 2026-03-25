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
import java.time.LocalDateTime;

/**
 * STEP_TBL 步骤表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("step_tbl")
public class StepTblEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "step_code", type = IdType.INPUT)
    @Schema(title = "步骤标识")
    private String stepCode;

    @Schema(title = "步骤名称")
    private String stepName;

    @Schema(title = "步骤类型")
    private String stepType;

    @Schema(title = "简介")
    private String summary;

    @Schema(title = "内容类型", description = "详情见 StepContentTypeEnum")
    private String contentType;

    @Schema(title = "步骤内容", description = "JSON 格式存储")
    private String content;

    @Schema(title = "参数", description = "表达式引擎 或 javaMethod 类型可以有参数")
    private String params;

    @Schema(title = "返回字段", description = "多个返回字段配置在这里，否则为空")
    private String returnFields;

    @Schema(title = "步骤介绍")
    private String description;

    @Schema(title = "创建人")
    private String createdBy;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新人")
    private String updatedBy;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}