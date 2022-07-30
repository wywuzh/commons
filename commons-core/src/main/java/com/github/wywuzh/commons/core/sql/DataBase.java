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
package com.github.wywuzh.commons.core.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * 类DataBase的实现描述：数据库信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-06 15:53:52
 * @version v2.3.6
 * @since JDK 1.8
 */
public class DataBase {
    private final Logger logger = LoggerFactory.getLogger(DataBase.class);

    private Connection connection;
    private DatabaseMetaData metaData;
    private Product product;

    public DataBase() {
    }

    public DataBase(Connection connection) {
        this.connection = connection;
        try {
            this.metaData = connection.getMetaData();
            this.product = new Product(metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseMetaData getMetaData() {
        return metaData;
    }

    public String getSchema() throws SQLException {
        return connection.getCatalog();
    }

    public Product getProduct() {
        return product;
    }

    public String getDriverClass() throws SQLException {
        return metaData.getDriverName();
    }

    public String getJdbcUrl() throws SQLException {
        return metaData.getURL();
    }

    public String getUsername() throws SQLException {
        return metaData.getUserName();
    }

    public void close() throws SQLException {
        if (this.connection != null) {
            connection.close();
        }
    }

}
