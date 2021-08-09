/*
 * Copyright 2015-2021 the original author or authors.
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
package com.wuzh.commons.jdbc;

/**
 * 类Sql.java的实现描述：通用SQL
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月28日 下午2:01:51
 * @version v1.0.0
 * @since JDK 1.7
 */
public final class Sql {

    /**
     * 新增数据
     */
    public static final String INSERT = "INSERT INTO {0}({1}) VALUES({2})";
    /**
     * 修改数据
     */
    public static final String UPDATE = "UPDATE {0} SET {1} WHERE 1=1 {2}";
    /**
     * 删除数据
     */
    public static final String DELETE = "DELETE FROM {0} WHERE 1=1 {1}";
    /**
     * 查询数据
     */
    public static final String QUERY = "SELECT {0} FROM {1} WHERE 1=1 {2}";
    /**
     * 查询字段别名
     */
    public static final String QUERY_AS = " AS ";
    /**
     * 调用存储过程
     */
    public static final String CALL = "call {0}({1})";

}
