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

import java.util.Enumeration;
import java.util.Properties;

/**
 * 类SystemPropertyUtilsTest的实现描述：系统属性工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-08-11 13:46:22
 * @version v2.7.0
 * @since JDK 1.8
 */
public class SystemPropertyUtilsTest {

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            System.out.println(String.format("属性名=%s, 属性值=%s", propertyName, properties.getProperty(propertyName)));
        }
    }

}
