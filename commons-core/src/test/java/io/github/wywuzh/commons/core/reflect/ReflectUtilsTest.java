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
package io.github.wywuzh.commons.core.reflect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import io.github.wywuzh.commons.core.poi.entity.User;

/**
 * 类ReflectUtilsTest的实现描述：反射工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-03-03 11:14:52
 * @version v3.3.0
 * @since JDK 1.8
 */
@Slf4j
public class ReflectUtilsTest {

    @SneakyThrows
    @Test
    public void getValue() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", "wywuzh");
        log.info("ReflectUtils.getValue获取字段值：map格式={}", Optional.ofNullable(ReflectUtils.getValue(userMap, "username")));

        User user = new User();
        user.setUsername("wywuzh");
        log.info("ReflectUtils.getValue获取字段值：字段格式={}", Optional.ofNullable(ReflectUtils.getValue(user, "username")));

    }

}
