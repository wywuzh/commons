/*
 * Copyright 2015-2019 the original author or authors.
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
package com.wuzh.commons.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.wuzh.commons.mybatis.setting.DruidReadConfig;
import com.wuzh.commons.mybatis.setting.DruidWriteConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类MyBatisConfig的实现描述：MyBatis配置
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-20 12:56:44
 * @version v2.2.0
 * @since JDK 1.8
 */
@Configuration
@EnableConfigurationProperties({DruidWriteConfig.class, DruidReadConfig.class})
public class MyBatisConfig {
    private final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    /**
     * mapper文件存放路径
     */
    private final String MAPPER_LOCATION = "classpath:com/wuzh/**/*.xml";
    /**
     * 配置文件信息
     */
    private final String CONFIG_LOCATION = "classpath:mybatis-config.xml";
    /**
     * 实体类所在包
     */
    private final String TYPE_ALIASES_PACKAGE = "com.wuzh.**.entity";

    @Primary
    @Bean(name = "writeDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource writeDataSource(DruidWriteConfig druidWriteConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidWriteConfig.getDriverClassName());
        dataSource.setUrl(druidWriteConfig.getUrl());
        dataSource.setUsername(druidWriteConfig.getUsername());
        dataSource.setPassword(druidWriteConfig.getPassword());

        dataSource.setInitialSize(druidWriteConfig.getInitialSize());
        dataSource.setMinIdle(druidWriteConfig.getMinIdle());
        dataSource.setMaxActive(druidWriteConfig.getMaxActive());
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(druidWriteConfig.getMaxWait());
        dataSource.setValidationQuery(druidWriteConfig.getValidationQuery());
        dataSource.setTestWhileIdle(druidWriteConfig.getTestWhileIdle());
        dataSource.setTestOnBorrow(druidWriteConfig.getTestOnBorrow());
        dataSource.setTestOnReturn(druidWriteConfig.getTestOnReturn());
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(druidWriteConfig.getTimeBetweenEvictionRunsMillis());
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(druidWriteConfig.getMinEvictableIdleTimeMillis());
        dataSource.setPoolPreparedStatements(druidWriteConfig.getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(druidWriteConfig.getMaxOpenPreparedStatements());
        try {
            // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            dataSource.setFilters(druidWriteConfig.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return dataSource;
    }

    @Bean(name = "readDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource readDataSource(DruidReadConfig druidReadConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidReadConfig.getDriverClassName());
        dataSource.setUrl(druidReadConfig.getUrl());
        dataSource.setUsername(druidReadConfig.getUsername());
        dataSource.setPassword(druidReadConfig.getPassword());

        dataSource.setInitialSize(druidReadConfig.getInitialSize());
        dataSource.setMinIdle(druidReadConfig.getMinIdle());
        dataSource.setMaxActive(druidReadConfig.getMaxActive());
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(druidReadConfig.getMaxWait());
        dataSource.setValidationQuery(druidReadConfig.getValidationQuery());
        dataSource.setTestWhileIdle(druidReadConfig.getTestWhileIdle());
        dataSource.setTestOnBorrow(druidReadConfig.getTestOnBorrow());
        dataSource.setTestOnReturn(druidReadConfig.getTestOnReturn());
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(druidReadConfig.getTimeBetweenEvictionRunsMillis());
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(druidReadConfig.getMinEvictableIdleTimeMillis());
        dataSource.setPoolPreparedStatements(druidReadConfig.getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(druidReadConfig.getMaxOpenPreparedStatements());
        try {
            // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            dataSource.setFilters(druidReadConfig.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return dataSource;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                 @Qualifier("readDataSource") DataSource readDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("writeDataSource", writeDataSource);
        dataSourceMap.put("readDataSource", readDataSource);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(writeDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        return routingDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        // 设置mapper文件目录
        sessionFactory.setMapperLocations(resourcePatternResolver.getResources(MAPPER_LOCATION));
        // 加载配置文件信息
        sessionFactory.setConfigLocation(resourcePatternResolver.getResource(CONFIG_LOCATION));
        // 实体类所在包
        sessionFactory.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
