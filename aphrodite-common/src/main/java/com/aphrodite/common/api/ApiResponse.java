package com.aphrodite.common.api;

import com.aphrodite.common.constants.ResultCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回对象
 * 
 * @param <T> 数据类型
 * @author Aphrodite
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "公共响应对象")
public class ApiResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "响应标识", description = "0：成功；-1：异常", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
    @Schema(title = "异常标识", description = "报错时根据需要添加，可以为空")
    private String errCode;
    @Schema(title = "响应信息")
    private String message;
    @Schema(title = "响应数据")
    private T data;

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(ResultCodeEnum.SUCCESS.getCode())
                .message(ResultCodeEnum.SUCCESS.getMsg())
                .data(data)
                .build();
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(ResultCodeEnum.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }
}

