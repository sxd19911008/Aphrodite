package com.aphrodite.security.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 安全异常处理器
 * 统一处理 Sa-Token 相关异常
 * 
 * @author Aphrodite
 */
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    /**
     * 处理未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public SaResult handleNotLoginException(NotLoginException e) {
        log.warn("未登录异常: {}", e.getMessage());
        return SaResult.error("未登录或登录已过期，请先登录").setCode(401);
    }

    /**
     * 处理无权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public SaResult handleNotPermissionException(NotPermissionException e) {
        log.warn("无权限异常: {}", e.getMessage());
        return SaResult.error("无权限访问").setCode(403);
    }

    /**
     * 处理无角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public SaResult handleNotRoleException(NotRoleException e) {
        log.warn("无角色异常: {}", e.getMessage());
        return SaResult.error("无角色权限").setCode(403);
    }
}


