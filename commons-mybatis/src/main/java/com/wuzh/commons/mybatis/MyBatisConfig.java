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
package com.wuzh.commons.mybatis;

import com.wuzh.commons.mybatis.constants.DataSourceConstants;
import com.wuzh.commons.mybatis.datasource.DruidDataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
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
@Import({DruidDataSourceConfig.class})
public class MyBatisConfig {
    private final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    /**
     * MAPPER映射器文件存放路径
     */
    private final String MAPPER_LOCATION = "classpath:com/wuzh/**/mapper/**/*.xml";
    /**
     * MAPPER接口文件存放路径
     *
     * @since v2.3.6
     */
    private final String MAPPER_PACKAGE = "com.wuzh.**.mapper";
    /**
     * 配置文件信息
     */
    private final String CONFIG_LOCATION = "/META-INF/mybatis-config.xml";
    /**
     * 实体类所在包
     */
    private final String TYPE_ALIASES_PACKAGE = "com.wuzh.**.entity";

    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                 @Qualifier("readDataSource") DataSource readDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceConstants.BEAN_NAME_WRITE, writeDataSource);
        targetDataSources.put(DataSourceConstants.BEAN_NAME_READ, readDataSource);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(writeDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 设置数据源
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

    @Bean
    @ConditionalOnMissingBean({MapperScannerRegistrar.class, MapperScannerConfigurer.class})
    public MapperScannerConfigurer mapperScannerConfigurer() {
        // v2.3.6：扫描Mapper接口文件，在未配置 @MapperScan 和MapperScannerConfigurer的情况下，启用该配置
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(MAPPER_PACKAGE);
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }

}
