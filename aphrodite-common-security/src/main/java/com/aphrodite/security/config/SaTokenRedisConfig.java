package com.aphrodite.security.config;

import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token Redis 配置类
 * 
 * 注意：当添加了 sa-token-redis-jackson 依赖后，
 * Sa-Token 会自动检测 Redis 连接并配置 Redis 存储。
 * 如果 Spring 容器中存在 RedisConnectionFactory，
 * Sa-Token 会自动使用 Redis 作为存储。
 * 
 * 如需自定义配置，可以在此类中添加 @Bean 方法。
 * 
 * @author Aphrodite
 */
@Configuration
public class SaTokenRedisConfig {
    // Sa-Token 会自动配置 Redis 存储，无需手动配置
    // 只需确保 application.yml 中配置了 Redis 连接信息即可
}


