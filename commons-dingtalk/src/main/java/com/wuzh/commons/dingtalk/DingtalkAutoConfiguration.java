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
package com.wuzh.commons.dingtalk;

import com.wuzh.commons.dingtalk.config.ApiConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类DingtalkAutoConfiguration的实现描述：Dingtalk自动配置类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 13:16:40
 * @version v2.3.8
 * @since JDK 1.8
 */
@Configuration
@EnableConfigurationProperties(DingtalkProperties.class)
public class DingtalkAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiConfig apiConfig(DingtalkProperties properties) {
        return new ApiConfig(properties.getAgentId(), properties.getAppKey(), properties.getAppSecret());
    }

}