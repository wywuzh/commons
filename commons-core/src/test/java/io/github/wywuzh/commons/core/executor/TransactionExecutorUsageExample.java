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

import org.springframework.transaction.TransactionDefinition;

import io.github.wywuzh.commons.core.executor.exception.TransactionalTaskException;

/**
 * 事务执行器使用示例
 *
 * @author 伍章红
 * @since JDK 1.8
 */
public class TransactionExecutorUsageExample {

    // ==================== TransExecutor 使用示例 ====================

    /**
     * 使用 TransExecutor 执行事务（推荐用于简单场景）
     */
    public void example1_TransExecutor() {
        TransExecutor transExecutor = new TransExecutor(null);

        // 方式1: 使用默认事务配置
        transExecutor.execute(new TransUnit() {
            @Override
            public void execute() {
                // 业务逻辑
                System.out.println("执行业务操作");
            }
        });

        // 方式2: 使用 Lambda 表达式
        transExecutor.execute(() -> {
            // 业务逻辑
            System.out.println("执行业务操作");
        });

        // 方式3: 只读事务
        transExecutor.executeInReadOnlyTransaction(() -> {
            // 查询操作
            System.out.println("执行查询操作");
        });

        // 方式4: 自定义事务属性
        transExecutor.execute(() -> {
            // 业务逻辑
            System.out.println("执行自定义事务业务操作");
        }, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_SERIALIZABLE, 120, true);
    }

    // ==================== TransactionalTaskExecutor 使用示例 ====================

    /**
     * 使用 TransactionalTaskExecutor 执行 Runnable 任务
     */
    public void example2_TransactionalTaskExecutor_Runnable() {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        // 方式1: 执行 Runnable（无返回值）
        executor.execute(() -> {
            System.out.println("执行 Runnable 任务");
        });

        // 方式2: 只读事务
        executor.executeInReadOnlyTransaction(() -> {
            System.out.println("执行只读事务");
        });

        // 方式3: 自定义事务属性
        executor.executeWithCustomAttributes(() -> {
            System.out.println("执行自定义属性事务");
        }, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_READ_COMMITTED, 60, false);
    }

    /**
     * 使用 TransactionalTaskExecutor 执行 Callable 任务（有返回值）
     */
    public void example3_TransactionalTaskExecutor_Callable() throws Exception {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        // 方式1: 执行 Callable（有返回值）
        Callable<String> task = () -> {
            return "执行结果";
        };
        String result = executor.execute(task);
        System.out.println("结果: " + result);

        // 方式2: 只读事务
        String readonlyResult = executor.executeInReadOnlyTransaction(() -> {
            return "查询结果";
        });
        System.out.println("只读结果: " + readonlyResult);

        // 方式3: 自定义事务属性
        String customResult = executor.executeWithCustomAttributes(() -> {
            return "自定义事务结果";
        }, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_SERIALIZABLE, 120, true);
        System.out.println("自定义结果: " + customResult);
    }

    // ==================== 实际业务场景示例 ====================

    /**
     * 场景1: 保存用户信息
     */
    public void example4_SaveUser() {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        executor.execute(() -> {
            // 保存用户
            System.out.println("保存用户到数据库");
            // 保存用户角色
            System.out.println("保存用户角色");
            // 发送通知（事务提交后）
            System.out.println("事务提交成功");
        });
    }

    /**
     * 场景2: 查询用户信息（只读事务）
     */
    public String example5_QueryUser() {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        return executor.executeInReadOnlyTransaction(() -> {
            // 查询用户
            System.out.println("查询用户信息");
            return "用户信息";
        });
    }

    /**
     * 场景3: 转账操作（需要 REQUIRES_NEW 传播行为）
     */
    public void example6_Transfer() {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        // 扣款
        executor.executeWithCustomAttributes(() -> {
            System.out.println("扣款操作");
        }, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_READ_COMMITTED, 30, false);

        // 收款
        executor.executeWithCustomAttributes(() -> {
            System.out.println("收款操作");
        }, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_READ_COMMITTED, 30, false);
    }

    /**
     * 场景4: 异常处理示例
     */
    public void example7_ExceptionHandling() {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        try {
            executor.execute(() -> {
                // 业务逻辑
                System.out.println("执行业务操作");
                // 模拟异常
                throw new RuntimeException("业务异常");
            });
        } catch (TransactionalTaskException e) {
            System.out.println("事务执行失败: " + e.getMessage());
            // 处理异常
        }
    }

    /**
     * 场景5: 使用 TransUnit 封装复杂业务
     */
    public void example8_ComplexBusiness() {
        TransExecutor transExecutor = new TransExecutor(null);

        // 封装为 TransUnit
        TransUnit orderUnit = () -> {
            System.out.println("创建订单");
            System.out.println("扣减库存");
            System.out.println("生成支付记录");
        };

        transExecutor.execute(orderUnit);
    }

    /**
     * 场景6: 嵌套事务
     */
    public void example9_NestedTransaction() {
        TransactionalTaskExecutor executor = new TransactionalTaskExecutor(null);

        // 外部事务
        executor.execute(() -> {
            System.out.println("外部事务开始");
            System.out.println("执行操作1");

            // 内部事务（REQUIRES_NEW）
            executor.executeWithCustomAttributes(() -> {
                System.out.println("内部事务开始");
                System.out.println("执行操作2");
                System.out.println("内部事务提交");
            }, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_READ_COMMITTED, 30, false);

            System.out.println("执行操作3");
            System.out.println("外部事务提交");
        });
    }
}
