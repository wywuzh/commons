/*
 * Copyright 2015-2017 the original author or authors.
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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * 类DataBase.java的实现描述：数据库信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年1月8日 下午4:07:58
 * @version v1.0.0
 * @since JDK 1.7
 * @deprecated 废弃，请使用 {@link com.wuzh.commons.core.sql.DataBase} 类
 */
@Deprecated
public class DataBase {

    private Connection connection;
    private DatabaseMetaData metaData;
    private Product product;

    public DataBase(Connection connection) {
        this.connection = connection;
        try {
            this.metaData = connection.getMetaData();
            this.product = new Product(metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion());
        } catch (SQLException e) {
            e.printStackTrace();
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

}
