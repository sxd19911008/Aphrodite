package com.aphrodite.insurance.config;

import com.aphrodite.common.exception.AphroditeException;
import com.aphrodite.common.utils.ContextAwareExecutorService;
import com.eredar.janus.core.threadpool.JanusBranchThreadPoolMetricsProvider;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class JanusBranchThreadPoolProvider implements JanusBranchThreadPoolMetricsProvider {

    @Override
    public int getQueueSize(ExecutorService executor) {
        BlockingQueue<Runnable> queue = this.getExecutorQueue(executor);
        return queue.size();
    }

    @Override
    public int getQueueCapacity(ExecutorService executor) {
        BlockingQueue<Runnable> queue = this.getExecutorQueue(executor);
        // 队列当前 size
        int currentSize = queue.size();
        // 队列剩余 size
        int remainingCapacity = queue.remainingCapacity();
        // 队列总 size
        return currentSize + remainingCapacity;
    }

    public BlockingQueue<Runnable> getExecutorQueue(ExecutorService executor) {
        ThreadPoolExecutor threadPoolExecutor = null;
        if (executor instanceof ThreadPoolExecutor) {
            threadPoolExecutor = (ThreadPoolExecutor) executor;
        } else if (executor instanceof ContextAwareExecutorService contextAwareExecutorService) {
            ExecutorService target = contextAwareExecutorService.getTargetExecutor();
            if (target instanceof ThreadPoolExecutor) {
                threadPoolExecutor = (ThreadPoolExecutor) target;
            }
        }

        if (threadPoolExecutor == null) {
            throw new AphroditeException("未知的JanusBranchThreadPool线程池类型：" + executor.getClass().getName());
        } else {
            return threadPoolExecutor.getQueue();
        }
    }
}
