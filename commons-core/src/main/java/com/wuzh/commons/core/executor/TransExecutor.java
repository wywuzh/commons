package com.wuzh.commons.core.executor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 类TransExecutor.java的实现描述：事物控制管理
 * 
 * @author 伍章红 2014-7-29 上午11:01:15
 * @since JDK 1.6.0_20
 */
@Component
public class TransExecutor {

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void doExecutor(TransUnit unit) {
        Assert.notNull(unit, "TransUnit must be not null");
        unit.excute();
    }
}
