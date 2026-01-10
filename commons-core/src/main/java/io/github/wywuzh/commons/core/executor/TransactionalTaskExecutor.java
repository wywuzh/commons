/*
 * Copyright 2015-2026 the original author or authors.
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
package io.github.wywuzh.commons.core.executor;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * 事务性任务执行器：为Runnable和Callable任务提供事务管理支持
 *
 * @author 伍章红 2014-7-18 下午9:29:30
 * @since JDK 1.6.0_20
 */
@Component
public class TransactionalTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalTaskExecutor.class);

    private final TransactionTemplate transactionTemplate;

    /**
     * 构造函数
     *
     * @param transactionManager 事务管理器，不能为null
     */
    public TransactionalTaskExecutor(PlatformTransactionManager transactionManager) {
        Assert.notNull(transactionManager, "PlatformTransactionManager must not be null");

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        configureDefaultTransactionAttributes();
    }

    /**
     * 配置默认事务属性
     */
    private void configureDefaultTransactionAttributes() {
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        this.transactionTemplate.setTimeout(30); // 30秒超时
        this.transactionTemplate.setReadOnly(false);
    }

    /**
     * 在事务中执行Runnable任务
     *
     * @param task 要执行的任务，不能为null
     * @throws IllegalArgumentException   当task为null时抛出
     * @throws TransactionalTaskException 当事务执行失败时抛出
     */
    public void execute(Runnable task) {
        Assert.notNull(task, "Runnable task must not be null");

        logger.debug("开始在事务中执行Runnable任务");

        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    task.run();
                    logger.debug("Runnable任务在事务中执行完成");
                }
            });
        } catch (Exception e) {
            String errorMsg = "事务中执行Runnable任务失败";
            logger.error(errorMsg, e);
            throw new TransactionalTaskException(errorMsg, e);
        }
    }

    /**
     * 在事务中执行Callable任务并返回结果
     *
     * @param task 要执行的任务，不能为null
     * @param <T>  返回结果类型
     * @return 任务执行结果
     * @throws IllegalArgumentException   当task为null时抛出
     * @throws TransactionalTaskException 当事务执行失败时抛出
     */
    public <T> T execute(Callable<T> task) {
        Assert.notNull(task, "Callable task must not be null");

        logger.debug("开始在事务中执行Callable任务");

        try {
            return transactionTemplate.execute(status -> {
                try {
                    T result = task.call();
                    logger.debug("Callable任务在事务中执行完成，返回结果: {}", result);
                    return result;
                } catch (Exception e) {
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    } else {
                        throw new TransactionalTaskException("Callable任务执行异常", e);
                    }
                }
            });
        } catch (Exception e) {
            String errorMsg = "事务中执行Callable任务失败";
            logger.error(errorMsg, e);
            throw new TransactionalTaskException(errorMsg, e);
        }
    }

    /**
     * 在只读事务中执行Runnable任务
     *
     * @param task 要执行的任务
     */
    public void executeInReadOnlyTransaction(Runnable task) {
        executeWithCustomAttributes(task, TransactionDefinition.PROPAGATION_REQUIRED, TransactionDefinition.ISOLATION_READ_COMMITTED, 30, true);
    }

    /**
     * 在只读事务中执行Callable任务
     *
     * @param task 要执行的任务
     * @param <T>  返回结果类型
     * @return 任务执行结果
     */
    public <T> T executeInReadOnlyTransaction(Callable<T> task) {
        return executeWithCustomAttributes(task, TransactionDefinition.PROPAGATION_REQUIRED, TransactionDefinition.ISOLATION_READ_COMMITTED, 30, true);
    }

    /**
     * 使用自定义事务属性执行Runnable任务
     *
     * @param task                要执行的任务
     * @param propagationBehavior 事务传播行为
     * @param isolationLevel      事务隔离级别
     * @param timeout             事务超时时间（秒）
     * @param readOnly            是否只读事务
     */
    public void executeWithCustomAttributes(Runnable task, int propagationBehavior, int isolationLevel, int timeout, boolean readOnly) {
        Assert.notNull(task, "Runnable task must not be null");

        TransactionTemplate customTemplate = createCustomTransactionTemplate(propagationBehavior, isolationLevel, timeout, readOnly);

        try {
            customTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    task.run();
                }
            });
        } catch (Exception e) {
            String errorMsg = "自定义事务中执行Runnable任务失败";
            logger.error(errorMsg, e);
            throw new TransactionalTaskException(errorMsg, e);
        }
    }

    /**
     * 使用自定义事务属性执行Callable任务
     *
     * @param task                要执行的任务
     * @param propagationBehavior 事务传播行为
     * @param isolationLevel      事务隔离级别
     * @param timeout             事务超时时间（秒）
     * @param readOnly            是否只读事务
     * @param <T>                 返回结果类型
     * @return 任务执行结果
     */
    public <T> T executeWithCustomAttributes(Callable<T> task, int propagationBehavior, int isolationLevel, int timeout, boolean readOnly) {
        Assert.notNull(task, "Callable task must not be null");

        TransactionTemplate customTemplate = createCustomTransactionTemplate(propagationBehavior, isolationLevel, timeout, readOnly);

        try {
            return customTemplate.execute(status -> {
                try {
                    return task.call();
                } catch (Exception e) {
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    } else {
                        throw new TransactionalTaskException("Callable任务执行异常", e);
                    }
                }
            });
        } catch (Exception e) {
            String errorMsg = "自定义事务中执行Callable任务失败";
            logger.error(errorMsg, e);
            throw new TransactionalTaskException(errorMsg, e);
        }
    }

    /**
     * 创建自定义事务模板
     */
    private TransactionTemplate createCustomTransactionTemplate(int propagationBehavior, int isolationLevel, int timeout, boolean readOnly) {
        TransactionTemplate customTemplate = new TransactionTemplate(transactionTemplate.getTransactionManager());
        customTemplate.setPropagationBehavior(propagationBehavior);
        customTemplate.setIsolationLevel(isolationLevel);
        customTemplate.setTimeout(timeout);
        customTemplate.setReadOnly(readOnly);
        return customTemplate;
    }

    /**
     * 获取底层事务模板（用于高级定制）
     *
     * @return TransactionTemplate实例
     */
    public TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate;
    }
}

/**
 * 事务性任务执行异常
 */
class TransactionalTaskException extends RuntimeException {

    public TransactionalTaskException(String message) {
        super(message);
    }

    public TransactionalTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
