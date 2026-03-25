package com.aphrodite.common.web.component;

import com.aphrodite.common.web.intf.InitInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Spring 容器初始化完成后，异步执行需要初始化的Bean的init方法。
 */
@Component
public class InitBeanRunner implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, InitInterface> map = applicationContext.getBeansOfType(InitInterface.class);
        if (!map.isEmpty()) {
            // 线程数无限制，有空闲线程则复用，无空闲则新建，空闲60秒后回收
            ExecutorService executorService = Executors.newCachedThreadPool();
            // 异步执行任务
            for (InitInterface bean : map.values()) {
                executorService.execute(bean::init);
            }
            // 关闭线程池
            executorService.shutdown();
        }
    }
}
