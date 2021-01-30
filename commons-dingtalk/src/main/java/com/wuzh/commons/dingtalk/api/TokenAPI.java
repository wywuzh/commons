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
package com.wuzh.commons.dingtalk.api;

import com.google.gson.reflect.TypeToken;
import com.wuzh.commons.core.http.HttpClientUtils;
import com.wuzh.commons.core.http.ResponseMessage;
import com.wuzh.commons.core.json.gson.GsonUtil;
import com.wuzh.commons.dingtalk.constants.URLContent;
import com.wuzh.commons.dingtalk.exception.DingtalkException;
import com.wuzh.commons.dingtalk.response.AccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 类TokenAPI的实现描述：获取凭证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 20:24:14
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class TokenAPI {

    /**
     * 获取企业内部应用的access_token
     *
     * @param appKey    应用的唯一标识key
     * @param appSecret 应用的密钥
     */
    public static AccessTokenResponse getToken(String appKey, String appSecret) throws DingtalkException {
        Assert.notNull(appKey, "appKey参数为空");
        Assert.notNull(appSecret, "appSecret参数为空");
        log.info("appKey={}, appSecret={} 获取凭证", appKey, appSecret);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("appkey", appKey);
        queryParams.put("appsecret", appSecret);
        ResponseMessage responseMessage = HttpClientUtils.doGet(URLContent.URL_GET_TOKKEN, queryParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // AccessTokenResponse中的嵌套子类未超过两级，可以通过jackson、fastjson、JSON等工具类进行解析；当操作时就需要改用gson工具类进行解析，否则可能解析json失败
        AccessTokenResponse tokenResponse = GsonUtil.parse(responseMessage.getResult(), new TypeToken<AccessTokenResponse>() {
        }.getType());
        return tokenResponse;
    }

}