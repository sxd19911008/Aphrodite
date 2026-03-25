package com.aphrodite.common.utils;

import java.util.concurrent.*;

/**
 * 具备上下文透传能力的定时线程池包装器。
 */
public final class ContextAwareScheduledExecutorService extends ContextAwareExecutorService implements ScheduledExecutorService {

    private final ScheduledExecutorService executor;

    public ContextAwareScheduledExecutorService(ScheduledExecutorService executor) {
        super(executor);
        this.executor = executor;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return executor.schedule(this.wrapRunnable(command), delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return executor.schedule(this.wrapCallable(callable), delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return executor.scheduleAtFixedRate(this.wrapRunnable(command), initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return executor.scheduleWithFixedDelay(this.wrapRunnable(command), initialDelay, delay, unit);
    }

    /**
     * 获取原始线程池
     */
    public ExecutorService getTargetExecutor() {
        return executor;
    }
}
