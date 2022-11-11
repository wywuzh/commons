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
package com.github.wywuzh.commons.dingtalk;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 类DingtalkProperties的实现描述：钉钉应用属性信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 13:18:13
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@ConfigurationProperties(prefix = "dingtalk")
public class DingtalkProperties {

    /**
     * 应用的agentId
     */
    private String agentId;
    /**
     * 应用的唯一标识key
     */
    private String appKey;
    /**
     * 应用的密钥
     */
    private String appSecret;

}
