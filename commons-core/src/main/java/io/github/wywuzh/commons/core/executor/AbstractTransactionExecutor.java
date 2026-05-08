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

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * 抽象事务执行器：提供事务执行的公共逻辑
 *
 * @author 伍章红
 * @since JDK 1.8
 */
public abstract class AbstractTransactionExecutor {

    /**
     * 默认事务超时时间（秒）
     */
    protected static final int DEFAULT_TIMEOUT = 30;

    /**
     * 默认事务传播行为
     */
    protected static final int DEFAULT_PROPAGATION = TransactionDefinition.PROPAGATION_REQUIRED;

    /**
     * 默认事务隔离级别
     */
    protected static final int DEFAULT_ISOLATION = TransactionDefinition.ISOLATION_READ_COMMITTED;

    /**
     * 事务模板
     */
    protected final TransactionTemplate transactionTemplate;

    /**
     * 构造函数
     *
     * @param transactionManager 事务管理器，不能为null
     * @throws IllegalArgumentException 当transactionManager为null时抛出
     */
    protected AbstractTransactionExecutor(PlatformTransactionManager transactionManager) {
        Assert.notNull(transactionManager, "PlatformTransactionManager must not be null");
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        configureDefaultAttributes();
    }

    /**
     * 配置默认事务属性
     */
    protected void configureDefaultAttributes() {
        this.transactionTemplate.setPropagationBehavior(DEFAULT_PROPAGATION);
        this.transactionTemplate.setIsolationLevel(DEFAULT_ISOLATION);
        this.transactionTemplate.setTimeout(DEFAULT_TIMEOUT);
        this.transactionTemplate.setReadOnly(false);
    }

    /**
     * 创建自定义事务模板
     *
     * @param propagationBehavior 事务传播行为
     * @param isolationLevel      事务隔离级别
     * @param timeout             事务超时时间（秒）
     * @param readOnly            是否只读事务
     * @return 自定义事务模板
     */
    protected TransactionTemplate createCustomTransactionTemplate(int propagationBehavior, int isolationLevel, int timeout, boolean readOnly) {
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
