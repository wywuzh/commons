/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;

import java.util.Arrays;
import java.util.List;

/**
 * 类AbstractPlugin的实现描述：基础plugin
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2023-06-08 16:21:02
 * @version v2.7.0
 * @since JDK 1.8
 */
public class AbstractPlugin extends BasePlugin {

    /**
     * 数据库驱动
     */
    public static final String DRIVER_MySQL = "com.mysql.jdbc.Driver"; // mysql-connector-java 5及5之前的
    public static final String DRIVER_MySQL6 = "com.mysql.cj.jdbc.Driver"; // mysql-connector-java 6及6之后的
    public static final String DRIVER_MariaDB = "org.mariadb.jdbc.Driver";
    public static final String DRIVER_ORACLE_OLD = "oracle.jdbc.driver.OracleDriver"; // oracle 9之前的
    public static final String DRIVER_ORACLE = "oracle.jdbc.OracleDriver"; // oracle 9之后的
    public static final String DRIVER_MICROSOFT_JDBC = "com.microsoft.jdbc.sqlserver.SQLServerDriver"; // SQL Server 2000
    public static final String DRIVER_MICROSOFT_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // SQL Server 2005及以后
    public static final String DRIVER_DB2 = "com.ibm.db2.jdbc.app.DB2Driver"; // DB2
    public static final String DRIVER_INFORMIX = "com.informix.jdbc.IfxDriver"; // informix
    public static final String DRIVER_Sybase = "com.sybase.jdbc.SybDriver"; // Sybase
    public static final String DRIVER_PostgreSQL = "org.postgresql.Driver"; // PostgreSQL
    public static final String DRIVER_Visual_Foxpro = "sun.jdbc.odbc.JdbcOdbcDriver"; // Visual Foxpro

    public static final List<String> AVAILABLE_DRIVER = Arrays.asList(DRIVER_MySQL, DRIVER_MySQL6, DRIVER_MariaDB, DRIVER_ORACLE_OLD, DRIVER_ORACLE, DRIVER_MICROSOFT_JDBC, DRIVER_MICROSOFT_SQLSERVER,
            DRIVER_DB2, DRIVER_INFORMIX, DRIVER_Sybase, DRIVER_PostgreSQL, DRIVER_Visual_Foxpro);
}
