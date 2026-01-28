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

import io.github.wywuzh.commons.core.executor.exception.TransactionExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * 事务执行器：提供基于TransUnit接口的事务管理
 *
 * @author 伍章红
 * @since JDK 1.8
 */
@Component
public class TransExecutor extends AbstractTransactionExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TransExecutor.class);

    /**
     * 构造函数
     *
     * @param transactionManager 事务管理器
     * @throws IllegalArgumentException 当transactionManager为null时抛出
     */
    public TransExecutor(PlatformTransactionManager transactionManager) {
        super(transactionManager);
    }

    /**
     * 在事务中执行业务单元（使用默认事务配置）
     *
     * @param unit 业务执行单元，不能为null
     * @throws IllegalArgumentException      当unit为null时抛出
     * @throws TransactionExecutionException 当事务执行失败时抛出
     */
    public void execute(TransUnit unit) {
        Assert.notNull(unit, "TransUnit must not be null");

        logger.debug("开始执行事务性操作");

        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    unit.execute();
                    logger.debug("事务性操作执行完成");
                }
            });
        } catch (Exception e) {
            logger.error("事务执行失败", e);
            throw new TransactionExecutionException("事务执行失败: " + e.getMessage(), e);
        }
    }

    /**
     * 在事务中执行业务单元（支持自定义事务属性）
     *
     * @param unit                业务执行单元
     * @param propagationBehavior 事务传播行为
     * @param isolationLevel      事务隔离级别
     * @param timeout             事务超时时间（秒）
     * @param readOnly            是否只读事务
     * @throws IllegalArgumentException      当unit为null时抛出
     * @throws TransactionExecutionException 当事务执行失败时抛出
     */
    public void execute(TransUnit unit, int propagationBehavior, int isolationLevel, int timeout, boolean readOnly) {
        Assert.notNull(unit, "TransUnit must not be null");

        TransactionTemplate customTemplate = createCustomTransactionTemplate(propagationBehavior, isolationLevel, timeout, readOnly);

        try {
            customTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    unit.execute();
                }
            });
        } catch (Exception e) {
            logger.error("自定义事务执行失败", e);
            throw new TransactionExecutionException("自定义事务执行失败: " + e.getMessage(), e);
        }
    }

    /**
     * 在只读事务中执行业务单元
     *
     * @param unit 业务执行单元
     * @throws IllegalArgumentException      当unit为null时抛出
     * @throws TransactionExecutionException 当事务执行失败时抛出
     */
    public void executeInReadOnlyTransaction(TransUnit unit) {
        execute(unit, DEFAULT_PROPAGATION, DEFAULT_ISOLATION, DEFAULT_TIMEOUT, true);
    }
}
