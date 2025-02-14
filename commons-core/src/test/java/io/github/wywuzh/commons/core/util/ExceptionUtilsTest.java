/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.core.util;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 类ExceptionUtilsTest的实现描述：异常信息工具类测试
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-04-19 18:21:42
 * @version v2.5.2
 * @since JDK 1.8
 */
@Slf4j
public class ExceptionUtilsTest {

    public static void throwException() {
        throw new IllegalArgumentException("方法不可用！");
    }

    public static void main(String[] args) {
        try {
            throwException();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info(ExceptionUtils.getMessage(e));
            log.info(ExceptionUtils.getStackTrace(e));
        }
    }

}
