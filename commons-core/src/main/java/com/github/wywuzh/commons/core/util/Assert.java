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

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * 类Asset的实现描述：Spring Assert扩展
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-08-02 10:42:24
 * @version v2.7.0
 * @since JDK 1.8
 */
public class Assert extends org.springframework.util.Assert {

    public static void notBlank(@Nullable String text) {
        notBlank(text, "[Assertion failed] - this argument is required; it must not be blank");
    }

    public static void notBlank(@Nullable String text, String message) {
        if (StringUtils.isNotBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

}
