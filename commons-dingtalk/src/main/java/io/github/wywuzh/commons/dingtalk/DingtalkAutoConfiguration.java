/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.dingtalk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.wywuzh.commons.dingtalk.api.contacts.DeptV2API;
import io.github.wywuzh.commons.dingtalk.api.contacts.UserV2API;
import io.github.wywuzh.commons.dingtalk.api.message.CorpconversationAPI;
import io.github.wywuzh.commons.dingtalk.config.ApiConfig;

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

    // contacts beans
    @Bean
    public UserV2API userV2API(ApiConfig apiConfig) {
        return new UserV2API(apiConfig);
    }

    @Bean
    public DeptV2API deptV2API(ApiConfig apiConfig) {
        return new DeptV2API(apiConfig);
    }

    // message beans
    @Bean
    public CorpconversationAPI corpconversationAPI(ApiConfig apiConfig) {
        return new CorpconversationAPI(apiConfig);
    }

}
