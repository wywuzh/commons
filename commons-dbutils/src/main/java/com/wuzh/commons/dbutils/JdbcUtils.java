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
package com.wuzh.commons.dbutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类JdbcUtils.java的实现描述：JDBC工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月28日 下午2:01:35
 * @version v1.0.0
 * @since JDK 1.7
 */
public final class JdbcUtils {
    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    private static DataSource dataSource;
    /**
     * 事物自动提交：默认关闭
     */
    private static boolean autoCommit = false;

    public static DataSource getDataSource() {
        return JdbcUtils.dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        JdbcUtils.dataSource = dataSource;
    }

    public static boolean isAutoCommit() {
        return JdbcUtils.autoCommit;
    }

    public static void setAutoCommit(boolean autoCommit) {
        JdbcUtils.autoCommit = autoCommit;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (null == connection) {
            logger.info("从数据源中获取数据库连接");
            // 从数据源中获取数据库连接
            connection = getDataSource().getConnection();
            // 设置事物是否自动提交
            connection.setAutoCommit(isAutoCommit());
            // 将数据库连接绑定到当前线程中
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 开启事物
     *
     * @throws SQLException
     */
    @Deprecated
    public static void openTransaction() throws SQLException {
        Connection connection = getConnection();
        // 设置事物是否自动提交
        connection.setAutoCommit(isAutoCommit());
    }

    /**
     * 事物回滚
     *
     * @throws SQLException
     */
    public static void rollback() throws SQLException {
        Connection connection = getConnection();
        if (null != connection) {
            logger.info("事物回滚");
            // 事物回滚
            connection.rollback();
        }
    }

    /**
     * 事物回滚，隐藏事物操作时发生的SQLException
     */
    public static void rollbackQuietly() {
        try {
            rollback();
        } catch (SQLException e) {
            logger.error("事物回滚异常：", e);
        }
    }

    /**
     * 事物回滚并关闭<code>Connection</code>
     *
     * @throws SQLException
     */
    public static void rollbackAndClose() throws SQLException {
        try {
            // 事物回滚
            rollback();
        } finally {
            // 关闭连接
            close();
        }
    }

    /**
     * 事物回滚并关闭<code>Connection</code>，隐藏事物操作时发生的SQLException
     */
    public static void rollbackAndCloseQuietly() {
        try {
            rollbackAndClose();
        } catch (SQLException e) {
            logger.error("事物回滚并关闭Connection异常：", e);
        }
    }

    /**
     * 事物提交
     *
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        Connection connection = getConnection();
        if (null != connection) {
            logger.info("事物提交");
            // 事物提交
            connection.commit();
        }
    }

    /**
     * 事物提交，隐藏事物操作时发生的SQLException
     */
    public static void commitQuietly() {
        try {
            commit();
        } catch (SQLException e) {
            logger.error("事物提交异常：", e);
        }
    }

    /**
     * 事物提交并关闭<code>Connection</code>
     *
     * @throws SQLException
     */
    public static void commitAndClose() throws SQLException {
        try {
            // 事物提交
            commit();
        } finally {
            // 关闭连接
            close();
        }
    }

    /**
     * 事物提交并关闭<code>Connection</code>，隐藏事物操作时发生的SQLException
     */
    public static void commitAndCloseQuietly() {
        try {
            commitAndClose();
        } catch (SQLException e) {
            logger.error("事物提交并关闭Connection异常：", e);
        }
    }

    /**
     * 关闭<code>Connection</code>
     *
     * @throws SQLException
     */
    public static void close() throws SQLException {
        Connection connection = getConnection();
        if (null != connection) {
            logger.info("关闭连接, 移除当前线程上绑定的连接");
            // 关闭连接
            connection.close();
            // 移除当前线程上绑定的连接
            threadLocal.remove();
        }
    }

    /**
     * 关闭<code>Connection</code>，隐藏事物操作时发生的SQLException
     */
    public static void closeQuietly() {
        try {
            close();
        } catch (SQLException e) {
            logger.error("关闭Connection异常：", e);
        }
    }

}
