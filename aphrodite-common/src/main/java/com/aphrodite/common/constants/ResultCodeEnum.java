package com.aphrodite.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常用API返回对象枚举
 *
 * @author Aphrodite
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    
    SUCCESS("0", "操作成功"),
    FAILED("-1", "操作失败");

    private final String code;
    private final String msg;
}

