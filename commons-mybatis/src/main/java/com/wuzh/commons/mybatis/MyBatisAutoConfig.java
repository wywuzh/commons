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

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 类DataSourceAutoConfig的实现描述：多数据源配置
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-20 14:31:48
 * @version v2.2.0
 * @since JDK 1.8
 */
@Configuration
@Import({MyBatisConfig.class, TransactionConfiguration.class})
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class MyBatisAutoConfig {

}
