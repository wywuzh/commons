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
package io.github.wywuzh.commons.core.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import io.github.wywuzh.commons.core.util.SpringBeanUtils;

/**
 * 类DataBaseUtils的实现描述：数据库工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-17 21:38:58
 * @version v2.3.6
 * @since JDK 1.8
 */
@Slf4j
public class DataBaseUtils {

    public static DataBase getDataBase() throws SQLException {
        DataSource dataSource = SpringBeanUtils.getBean(DataSource.class);
        if (dataSource == null) {
            return null;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return new DataBase(connection);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            close(connection);
        }
    }

    /**
     * 关闭<code>Connection</code>
     *
     * @throws SQLException
     */
    public static void close(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 关闭<code>Connection</code>，隐藏事物操作时发生的SQLException
     */
    public static void closeQuietly(Connection connection) {
        try {
            close(connection);
        } catch (SQLException e) {
            log.error("关闭Connection异常：", e);
        }
    }

}
