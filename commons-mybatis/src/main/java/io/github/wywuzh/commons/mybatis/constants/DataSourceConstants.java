/*
 * Copyright 2015-2024 the original author or authors.
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
package io.github.wywuzh.commons.mybatis.constants;

/**
 * 类DataSourceConstants的实现描述：数据源相关常量
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-12-29 17:26:41
 * @version v2.3.6
 * @since JDK 1.8
 */
public class DataSourceConstants {

    /**
     * Bean名称
     */
    public static final String BEAN_NAME_WRITE = "writeDataSource"; // 写/主
    public static final String BEAN_NAME_READ = "readDataSource"; // 读/从

    /**
     * 数据源属性前缀
     */
    public static final String PROPERTIES_PREFIX_WRITE = "spring.datasource"; // 写/主
    public static final String PROPERTIES_PREFIX_READ = "spring.datasource.read"; // 读/从

}
