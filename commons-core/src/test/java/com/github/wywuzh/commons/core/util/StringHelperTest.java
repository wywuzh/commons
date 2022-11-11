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
package com.github.wywuzh.commons.core.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类StringHelperTest的实现描述：StringHelper测试类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-08-12 13:52:40
 * @version v2.4.8
 * @since JDK 1.8
 */
public class StringHelperTest {
    private final Logger logger = LoggerFactory.getLogger(StringHelperTest.class);

    @Test
    public void lengthTest() {
        String content = "是否启用JSR303标准注解验证";
        int length = StringHelper.length(content);
        logger.info("{} --> {}", content, length);
    }

}
