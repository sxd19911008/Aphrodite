package com.aphrodite.common.exception;

import com.aphrodite.common.constants.ErrCodeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 自定义通用异常
 *
 * @author Aphrodite
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class AphroditeException extends RuntimeException {

    private final ErrCodeEnum errCode;

    public AphroditeException() {
        this.errCode = ErrCodeEnum.ERROR; // 默认异常编码
    }

    public AphroditeException(ErrCodeEnum errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public AphroditeException(ErrCodeEnum errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public AphroditeException(ErrCodeEnum errCode) {
        super(errCode.getMsg());
        this.errCode = errCode;
    }

    public AphroditeException(ErrCodeEnum errCode, Throwable cause) {
        super(errCode.getMsg(), cause);
        this.errCode = errCode;
    }

    public AphroditeException(String message) {
        super(message);
        this.errCode = ErrCodeEnum.ERROR; // 默认异常编码
    }

    public AphroditeException(String message, Throwable cause) {
        super(message, cause);
        this.errCode = ErrCodeEnum.ERROR; // 默认异常编码
    }
}

