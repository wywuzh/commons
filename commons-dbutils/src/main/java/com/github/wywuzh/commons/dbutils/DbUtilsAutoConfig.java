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
package com.github.wywuzh.commons.dbutils;

import com.github.wywuzh.commons.dbutils.repository.BasicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类DbUtilsAutoConfig的实现描述：DbUtils Auto Configuration
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-16 10:26:21
 * @version v2.3.6
 * @since JDK 1.8
 */
@Configuration
public class DbUtilsAutoConfig {
    private final Logger logger = LoggerFactory.getLogger(DbUtilsAutoConfig.class);

    @Bean
    @ConditionalOnMissingBean
    public BasicRepository basicRepository(DataSource dataSource) throws SQLException {
        BasicRepository basicRepository = new BasicRepository();
        basicRepository.setDataSource(dataSource);

        init(dataSource);
        return basicRepository;
    }

    public void init(DataSource dataSource) throws SQLException {
        Connection connection = null;
        try {
            JdbcUtils.setDataSource(dataSource);
            connection = dataSource.getConnection();
            JdbcUtils.setAutoCommit(connection.getAutoCommit());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}
