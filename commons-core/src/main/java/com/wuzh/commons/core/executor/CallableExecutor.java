package com.wuzh.commons.core.executor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.concurrent.*;

/**
 * 类CallableExecutor.java的实现描述：事物管理
 * 
 * @author 伍章红 2014-7-18 下午9:29:27
 * @since JDK 1.6.0_20
 */
@Component
public class CallableExecutor<T> {

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public T callable(Callable<T> callable) {
        Assert.notNull(callable, "Callable must be not null");
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        T executeResult = null;
        try {
            ExecutorCompletionService<T> completionService = new ExecutorCompletionService<T>(
                    executorService);

            Future<T> future = completionService.submit(callable);
            executeResult = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        return executeResult;
    }

}
