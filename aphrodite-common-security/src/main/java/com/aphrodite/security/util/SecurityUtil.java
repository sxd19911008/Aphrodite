package com.aphrodite.security.util;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 安全工具类
 * 封装 Sa-Token 常用操作
 * 
 * @author Aphrodite
 */
public class SecurityUtil {

    /**
     * 获取当前登录用户ID
     * 
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 获取当前登录用户ID（字符串类型）
     * 
     * @return 用户ID字符串
     */
    public static String getCurrentUserIdAsString() {
        return StpUtil.getLoginIdAsString();
    }

    /**
     * 获取当前登录用户的Token值
     * 
     * @return Token值
     */
    public static String getCurrentToken() {
        return StpUtil.getTokenValue();
    }

    /**
     * 检查当前用户是否已登录
     * 
     * @return true-已登录，false-未登录
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 检查当前用户是否具有指定角色
     * 
     * @param role 角色标识
     * @return true-具有该角色，false-不具有
     */
    public static boolean hasRole(String role) {
        return StpUtil.hasRole(role);
    }

    /**
     * 检查当前用户是否具有指定权限
     * 
     * @param permission 权限标识
     * @return true-具有该权限，false-不具有
     */
    public static boolean hasPermission(String permission) {
        return StpUtil.hasPermission(permission);
    }

    /**
     * 登出当前用户
     */
    public static void logout() {
        StpUtil.logout();
    }

    /**
     * 登出指定用户
     * 
     * @param userId 用户ID
     */
    public static void logout(Long userId) {
        StpUtil.logout(userId);
    }
}


