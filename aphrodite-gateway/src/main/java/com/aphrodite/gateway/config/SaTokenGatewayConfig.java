package com.aphrodite.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Sa-Token Gateway 配置类
 * 用于 Spring Cloud Gateway 的鉴权过滤
 * 
 * @author Aphrodite
 */
@Configuration
public class SaTokenGatewayConfig {

    /**
     * 配置 Gateway 鉴权过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .setIncludeList(Arrays.asList("/**"))
                // 排除地址（登录、注册等公开接口）
                .setExcludeList(Arrays.asList(
                        "/auth/login",
                        "/auth/register",
                        "/actuator/**"
                ))
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 排除以下路径，其它所有请求都要登录
                    SaRouter.match("/**")
                            .notMatch("/auth/**")
                            .notMatch("/actuator/**")
                            .check(r -> StpUtil.checkLogin());
                })
                // 异常处理函数：每次 setAuth 函数出现异常时进入
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                });
    }
}


