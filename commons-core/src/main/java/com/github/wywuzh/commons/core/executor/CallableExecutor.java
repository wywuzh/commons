/*
 * Copyright 2015-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wywuzh.commons.core.executor;

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
