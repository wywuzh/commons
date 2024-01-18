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
package com.github.wywuzh.commons.mybatis.generator.constant;

/**
 * 类PropertyConstants的实现描述：Property属性
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-11-17 14:54:42
 * @version v2.7.0
 * @since JDK 1.8
 */
public class MbgPropertyConstants {

    // =======================>>> <property>属性名
    /**
     * 属性名：表是否开启逻辑删除
     *
     * @since 2.3.6
     */
    public static final String PROPERTY_ENABLE_LOGIC_DELETE = "enableLogicDelete";
    /**
     * 属性名：逻辑删除字段。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件。
     *
     * @since 2.3.6
     */
    public static final String PROPERTY_LOGIC_DELETE_FIELD = "logicDeleteField";
    /**
     * 属性名：排除数据sql，即剔除已删除数据的sql
     *
     * @since 2.3.6
     */
    public static final String PROPERTY_EXCLUDE_DELETED_SQL = "excludeDeletedSql";

    // =======================>>> <property>属性默认值
    /**
     * 默认属性值：表是否开启逻辑删除，默认为true
     *
     * @since 2.3.6
     */
    public static boolean enableLogicDelete = true;
    /**
     * 默认属性值：逻辑删除字段默认值
     *
     * @since 2.3.6
     */
    public static String logicDeleteField = "is_delete";
    /**
     * 默认属性值：排除删除数据sql。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件
     *
     * @since 2.3.6
     */
    public static String excludeDeletedSql = "is_delete = 0";

}
