package com.wuzh.commons.core.executor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 类RunnableExecutor.java的实现描述：事物管理
 * 
 * @author 伍章红 2014-7-18 下午9:29:30
 * @since JDK 1.6.0_20
 */
@Component
public class RunnableExecutor {

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void runnable(Runnable runnable) {
        Assert.notNull(runnable, "Runnable must be not null");
        runnable.run();
    }

}
