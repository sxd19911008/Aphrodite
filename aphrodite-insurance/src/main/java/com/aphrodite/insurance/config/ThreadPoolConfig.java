package com.aphrodite.insurance.config;

import com.aphrodite.common.utils.ContextAwareExecutorService;
import com.aphrodite.insurance.common.datasource.DataSourceContextHolder;
import com.eredar.janus.core.threadpool.JanusThreadPoolComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration
public class ThreadPoolConfig {

    @Autowired
    private JanusThreadPoolComponent janusThreadPoolComponent;

    @Bean
    public ExecutorService janusBranchThreadPool() {
        // 创建原始线程池
        ExecutorService janusBranchThreadPool = janusThreadPoolComponent.getJanusBranchThreadPool();
        // 使用 ContextAwareExecutorService 进行增强，确保父线程上下文（如数据源标识）在子线程中可用
        return new ContextAwareExecutorService(janusBranchThreadPool)
                .addContextPropagator(
                        DataSourceContextHolder::getDataSource,
                        DataSourceContextHolder::setDataSource,
                        DataSourceContextHolder::removeDataSource
                );
    }
}
