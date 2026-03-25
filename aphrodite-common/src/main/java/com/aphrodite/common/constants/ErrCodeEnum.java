package com.aphrodite.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrCodeEnum {

    INIT_ERR("err_init", "系统启动时加载信息异常"),

    ERROR("err_0000", "异常"),
    VALIDATE_FAILED("err_0001", "参数检验失败"),
    UNAUTHORIZED("err_0002", "暂无登录或 token 已经过期"),
    FORBIDDEN("err_0003", "没有相关权限"),

    ILLEGAL_POLICY_STATUS("err_1001", "保单状态非法"),
    STEP_ERR("err_1002", "步骤异常"),
    PLAN_CORRELATION_ERR("err_1003", "险种相关性异常"),

    // 保险模块异常代码
    ;

    private final String code;
    private final String msg;
}
