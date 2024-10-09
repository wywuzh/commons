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
package com.github.wywuzh.commons.dingtalk.api;

import com.github.wywuzh.commons.core.http.HttpClientUtils;
import com.github.wywuzh.commons.core.http.ResponseMessage;
import com.github.wywuzh.commons.dingtalk.config.ApiConfig;

import java.nio.charset.Charset;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 类BaseAPI的实现描述：API基类，提供一些通用方法
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-27 23:02:07
 * @version v2.3.8
 * @since JDK 1.8
 */
public abstract class BaseAPI {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final ApiConfig apiConfig;

    public BaseAPI(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    protected ResponseMessage doGet(String url, Map<String, String> queryParams) {
        Assert.notNull(url, "url must not be null");

        String getUrl = url.replace("#ACCESS_TOKEN", apiConfig.getAccessToken());
        return HttpClientUtils.doGet(getUrl, queryParams);
    }

    protected ResponseMessage doPost(String url, Map<String, String> requestParams) {
        Assert.notNull(url, "url must not be null");

        String getUrl = url.replace("#ACCESS_TOKEN", apiConfig.getAccessToken());
        return HttpClientUtils.doPost(getUrl, requestParams);
    }

    protected ResponseMessage doPost(String url, Object requestParams) {
        Assert.notNull(url, "url must not be null");

        String getUrl = url.replace("#ACCESS_TOKEN", apiConfig.getAccessToken());
        return HttpClientUtils.doPostJson(getUrl, requestParams, null, Charset.forName("UTF-8"));
    }

}
