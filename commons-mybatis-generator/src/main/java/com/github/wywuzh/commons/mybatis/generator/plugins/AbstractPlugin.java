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
package com.github.wywuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.TableConfiguration;

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
    public static final String DRIVER_MariaDB = "org.mariadb.jdbc.Driver"; // MariaDB
    public static final String DRIVER_ORACLE_OLD = "oracle.jdbc.driver.OracleDriver"; // oracle 9之前的
    public static final String DRIVER_ORACLE = "oracle.jdbc.OracleDriver"; // oracle 9之后的
    public static final String DRIVER_MICROSOFT_JDBC = "com.microsoft.jdbc.sqlserver.SQLServerDriver"; // SQL Server 2000
    public static final String DRIVER_MICROSOFT_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // SQL Server 2005及以后
    public static final String DRIVER_DB2 = "com.ibm.db2.jdbc.app.DB2Driver"; // DB2
    public static final String DRIVER_INFORMIX = "com.informix.jdbc.IfxDriver"; // informix
    public static final String DRIVER_Sybase = "com.sybase.jdbc.SybDriver"; // Sybase
    public static final String DRIVER_PostgreSQL = "org.postgresql.Driver"; // PostgreSQL
    public static final String DRIVER_Visual_Foxpro = "sun.jdbc.odbc.JdbcOdbcDriver"; // Visual Foxpro
    public static final String DRIVER_OceanBase = "com.alipay.oceanbase.jdbc.Driver"; // OceanBase 由蚂蚁集团完全自主研发的国产原生分布式数据库
    public static final String DRIVER_ClickHouse = "com.clickhouse.jdbc.ClickHouseDriver"; // ClickHouse 0.3.2之后的
    public static final String DRIVER_ClickHouse_OLD = "ru.yandex.clickhouse.ClickHouseDriver"; // ClickHouse 0.3.2之前的
    public static final String DRIVER_DM = "dm.jdbc.driver.DmDriver"; // 达梦数据库
    public static final String DRIVER_Kingbase8 = "com.kingbase8.Driver"; // 人大金仓数据库

    public static final List<String> AVAILABLE_DRIVER = Arrays.asList(DRIVER_MySQL, DRIVER_MySQL6, DRIVER_MariaDB, DRIVER_ORACLE_OLD, DRIVER_ORACLE, DRIVER_MICROSOFT_JDBC, DRIVER_MICROSOFT_SQLSERVER,
            DRIVER_DB2, DRIVER_INFORMIX, DRIVER_Sybase, DRIVER_PostgreSQL, DRIVER_Visual_Foxpro, DRIVER_OceanBase, DRIVER_ClickHouse, DRIVER_ClickHouse_OLD, DRIVER_DM, DRIVER_Kingbase8);

    /**
     * 获取属性值
     *
     * @param introspectedTable table表信息
     * @param key               属性名
     * @param defaultValue      默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected String getProperty(IntrospectedTable introspectedTable, String key, String defaultValue) {
        return this.getProperty(introspectedTable.getTableConfiguration(), super.getProperties(), key, defaultValue);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected String getProperty(TableConfiguration tableConfiguration, String key, String defaultValue) {
        return this.getProperty(tableConfiguration, super.getProperties(), key, defaultValue);
    }

    /**
     * 获取属性值
     *
     * @param introspectedTable table表信息
     * @param properties        属性配置(全局配置)
     * @param key               属性名
     * @param defaultValue      默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected String getProperty(IntrospectedTable introspectedTable, Properties properties, String key, String defaultValue) {
        return this.getProperty(introspectedTable.getTableConfiguration(), properties, key, defaultValue);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected String getProperty(TableConfiguration tableConfiguration, Properties properties, String key, String defaultValue) {
        // 如果在<table>中有配置，以该配置为准，否则读取<plugin>全局配置
        String val = this.getProperty(tableConfiguration, properties, key);
        if (StringUtils.isNotBlank(val)) {
            return val;
        }
        // 返回默认值
        return defaultValue;
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @return 属性值
     * @since v2.7.8
     */
    protected String getProperty(TableConfiguration tableConfiguration, Properties properties, String key) {
        // 如果在<table>中有配置，以该配置为准，否则读取<plugin>全局配置
        // 1. 优先从<table>配置中读取
        String val = tableConfiguration.getProperty(key);
        if (StringUtils.isNotBlank(val)) {
            return val;
        }
        // 2. 从<plugin>全局配置中读取
        val = properties.getProperty(key);
        if (StringUtils.isNotBlank(val)) {
            return val;
        }
        return null;
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Boolean getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Boolean defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Boolean.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Byte getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Byte defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Byte.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Short getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Short defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Short.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Integer getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Integer defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Integer.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Long getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Long defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Long.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Double getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Double defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Double.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected Float getProperty(TableConfiguration tableConfiguration, Properties properties, String key, Float defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : Float.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param tableConfiguration table配置
     * @param properties         属性配置(全局配置)
     * @param key                属性名
     * @param defaultValue       默认属性值
     * @return 属性值
     * @since v2.7.8
     */
    protected BigDecimal getProperty(TableConfiguration tableConfiguration, Properties properties, String key, BigDecimal defaultValue) {
        String val = this.getProperty(tableConfiguration, properties, key);
        return StringUtils.isBlank(val) ? defaultValue : new BigDecimal(val);
    }

}
