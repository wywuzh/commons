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
