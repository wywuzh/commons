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
package com.github.wywuzh.commons.mybatis.generator.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * 类Properties的实现描述：Properties属性工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-07-14 18:17:08
 * @version v2.4.5
 * @since JDK 1.8
 */
public class PropertiesUtils {

    /**
     * 获取属性值
     *
     * @param properties
     * @param key          the hashtable key.
     * @param defaultValue a default value.
     * @return the value in this property list with the specified key value.
     */
    public static String getProperty(Properties properties, String key, String defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : val;
    }

}
