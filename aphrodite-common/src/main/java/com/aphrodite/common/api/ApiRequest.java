package com.aphrodite.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 通用请求对象
 * 
 * @param <T> 业务数据类型
 * @author Aphrodite
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "公共请求对象")
public class ApiRequest<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "全链路唯一 ID(traceId)不能为空")
    @Schema(title = "全链路唯一 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String traceId;

    @NotBlank(message = "调用方微服务名称(sourceService)不能为空")
    @Schema(title = "调用方微服务名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sourceService;

    @Valid
    @NotNull(message = "业务入参(data)不能为空")
    @Schema(title = "业务入参", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;
}

