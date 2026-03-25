package com.aphrodite.insurance.config;

import com.aphrodite.insurance.common.datasource.DataSourceContextHolder;
import com.aphrodite.insurance.common.datasource.DataSourceEnum;
import com.aphrodite.insurance.common.datasource.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("aphrodite.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class) // 指定实现类
                .build();
    }

    @Bean
    @ConfigurationProperties("aphrodite.datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class) // 指定实现类
                .build();
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSource mysqlDataSource, DataSource oracleDataSource) {
        Map<String, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceEnum.MySQL.getDb(), mysqlDataSource);
        dataSourceMap.put(DataSourceEnum.Oracle.getDb(), oracleDataSource);

        // 初始化数据源切换工具
        DataSourceContextHolder.initDataSourceSet(dataSourceMap.keySet());
        // 初始化动态数据源
        DynamicDataSource routingDataSource = new DynamicDataSource();
        routingDataSource.setDefaultTargetDataSource(mysqlDataSource); // 默认数据源
        routingDataSource.setTargetDataSources(new HashMap<>(dataSourceMap));

        // 返回动态数据源
        return routingDataSource;
    }
}