package com.aphrodite.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 具备上下文透传能力的线程池包装器。
 */
public class ContextAwareExecutorService extends AbstractExecutorService {

    private final ExecutorService executor;
    protected final List<ContextPropagator<?>> propagators;

    /**
     * 包装线程池，使其具备上下文透传能力。
     *
     * @param executor 原始线程池
     */
    public ContextAwareExecutorService(ExecutorService executor) {
        Objects.requireNonNull(executor, "executor can't be null");
        this.executor = executor;
        this.propagators = new ArrayList<>();
    }

    public <T> ContextAwareExecutorService addContextPropagator(ThreadLocal<T> threadLocal) {
        this.propagators.add(new ContextPropagator<>(threadLocal));
        return this;
    }

    public <T> ContextAwareExecutorService addContextPropagator(Supplier<T> getFunc, Consumer<T> setFunc, Runnable removeFunc) {
        this.propagators.add(new ContextPropagator<>(getFunc, setFunc, removeFunc));
        return this;
    }

    @Override
    public void execute(Runnable command) {
        executor.execute(this.wrapRunnable(command));
    }

    /**
     * 包装 Runnable
     */
    protected Runnable wrapRunnable(Runnable runnable) {
        List<Runnable> setList = propagators.stream().map(ContextPropagator::buildSetAction).toList();
        Thread fatherThread = Thread.currentThread();
        return () -> {
            Thread childThread = Thread.currentThread();
            if (childThread != fatherThread) {
                // 子线程 set 数据
                setList.forEach(Runnable::run);
                try {
                    runnable.run();
                } finally {
                    // 子线程 remove 数据
                    propagators.forEach(ContextPropagator::remove);
                }
            } else {
                runnable.run();
            }
        };
    }

    /**
     * 包装 Callable
     */
    protected <T> Callable<T> wrapCallable(Callable<T> callable) {
        List<Runnable> setList = propagators.stream().map(ContextPropagator::buildSetAction).toList();
        return () -> {
            // 子线程 set 数据
            setList.forEach(Runnable::run);
            try {
                return callable.call();
            } finally {
                // 子线程 remove 数据
                propagators.forEach(ContextPropagator::remove);
            }
        };
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executor.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    /**
     * 获取原始线程池
     */
    public ExecutorService getTargetExecutor() {
        return executor;
    }

    /**
     * 上下文传播器
     */
    public static class ContextPropagator<T> {
        private ThreadLocal<T> threadLocal;
        private Supplier<T> getFunc;
        private Consumer<T> setFunc;
        private Runnable removeFunc;

        private ContextPropagator(ThreadLocal<T> threadLocal) {
            if (threadLocal == null) {
                throw new IllegalArgumentException("threadLocal can't be null");
            }
            this.threadLocal = threadLocal;
        }

        private ContextPropagator(Supplier<T> getFunc, Consumer<T> setFunc, Runnable removeFunc) {
            if (getFunc == null) {
                throw new IllegalArgumentException("getFunc can't be null");
            }
            if (setFunc == null) {
                throw new IllegalArgumentException("setFunc can't be null");
            }
            if (removeFunc == null) {
                throw new IllegalArgumentException("removeFunc can't be null");
            }
            this.getFunc = getFunc;
            this.setFunc = setFunc;
            this.removeFunc = removeFunc;
        }

        private T get() {
            if (threadLocal != null) {
                return threadLocal.get();
            } else {
                return getFunc.get();
            }
        }

        private void set(T value) {
            if (threadLocal != null) {
                threadLocal.set(value);
            } else {
                setFunc.accept(value);
            }
        }

        /**
         * 该方法只能在主线程执行，直接获取 threadLocal 数据然后组装函数式接口返回。
         * @return 存储数据的函数式接口，已经包含要传递的数据，但是必须到子线程中在执行set动作。
         */
        public Runnable buildSetAction() {
            T value = this.get();
            // 不可在此完成set动作，必须返回函数式接口。
            return () -> this.set(value);
        }

        public void remove() {
            if (threadLocal != null) {
                threadLocal.remove();
            } else {
                removeFunc.run();
            }
        }
    }
}

