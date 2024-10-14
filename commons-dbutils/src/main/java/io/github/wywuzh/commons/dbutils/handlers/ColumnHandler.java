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
package io.github.wywuzh.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 类ColumnHandler.java的实现描述：取得特定字段的值
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年1月1日 下午8:28:11
 * @version v1.0.0
 * @since JDK 1.7
 */
public class ColumnHandler<T> implements ResultSetHandler<T> {

    /**
     * 字段索引，索引从1开始
     */
    private final int columnIndex;

    /**
     * 字段名称
     */
    private final String columnName;

    public ColumnHandler(int columnIndex) {
        this(columnIndex, null);
    }

    public ColumnHandler(String columnName) {
        this(1, columnName);
    }

    public ColumnHandler(int columnIndex, String columnName) {
        super();
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T handle(ResultSet rs) throws SQLException {
        // ResultSet对象代表sql语句执行的结果集，维护指向其当前数据行的光标。
        // 每调用一次next()方法，光标向下移动一行。最初它位于第一行之前，因此我们第一次应当调用next()方法使第一行成为当前行。
        // 随着每次调用next()将导致光标向下移动一行。在ResultSet对象及其父辈statement对象关闭之前，光标一直保持有效。
        if (!rs.next()) {
            return null;
        }
        if (this.columnName == null) {
            return (T) rs.getObject(this.columnIndex);
        }
        return (T) rs.getObject(this.columnName);
    }

}
