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
package com.github.wywuzh.commons.dingtalk.api;

import com.github.wywuzh.commons.core.http.HttpClientUtils;
import com.github.wywuzh.commons.core.http.ResponseMessage;
import com.github.wywuzh.commons.core.json.gson.GsonUtil;
import com.github.wywuzh.commons.dingtalk.constants.URLContent;
import com.github.wywuzh.commons.dingtalk.exception.DingtalkException;
import com.github.wywuzh.commons.dingtalk.response.AccessTokenResponse;
import com.github.wywuzh.commons.dingtalk.response.JsapiTicketResponse;
import com.github.wywuzh.commons.dingtalk.response.SsoAccessTokenResponse;
import com.github.wywuzh.commons.dingtalk.response.SuiteAccessTokenResponse;
import com.google.gson.reflect.TypeToken;
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

    /**
     * 获取第三方企业应用的access_token
     *
     * @param accessKey    如果是定制应用，输入定制应用的CustomKey；如果是第三方企业应用，输入第三方企业应用的SuiteKey
     * @param accessSecret 如果是定制应用，输入定制应用的CustomSecret；如果是第三方企业应用，输入第三方企业应用的SuiteSecret
     * @param suiteTicket  钉钉推送的suiteTicket：定制应用可随意填写；第三方企业应用使用钉钉开放平台向应用推送的suite_ticket
     * @param signature    签名
     * @param authCorpid   授权企业的CorpId
     * @return
     * @throws DingtalkException
     */
    public static AccessTokenResponse getCorpToken(String accessKey, String accessSecret, String suiteTicket, String signature, String authCorpid) throws DingtalkException {
        Assert.notNull(accessKey, "accessKey参数为空");
        Assert.notNull(accessSecret, "accessSecret参数为空");
        log.info("accessKey={}, accessSecret={} 获取第三方企业应用的access_token", accessKey, accessSecret);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("accessKey", accessKey);
        queryParams.put("accessSecret", accessSecret);
        queryParams.put("suiteTicket", suiteTicket);
        queryParams.put("signature", signature);
        queryParams.put("auth_corpid", authCorpid);
        ResponseMessage responseMessage = HttpClientUtils.doPost(URLContent.URL_SERVICE_GET_CORP_TOKEN, queryParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // AccessTokenResponse中的嵌套子类未超过两级，可以通过jackson、fastjson、JSON等工具类进行解析；当操作时就需要改用gson工具类进行解析，否则可能解析json失败
        AccessTokenResponse tokenResponse = GsonUtil.parse(responseMessage.getResult(), new TypeToken<AccessTokenResponse>() {
        }.getType());
        return tokenResponse;
    }

    /**
     * 获取第三方企业应用的suite_ticket
     *
     * @param suiteKey    第三方应用的suiteKey
     * @param suiteSecret 第三方应用的suiteSecret
     * @param suiteTicket 钉钉开放平台会向应用的回调URL推送的suite_ticket
     * @return
     * @throws DingtalkException
     */
    public static SuiteAccessTokenResponse getSuiteToken(String suiteKey, String suiteSecret, String suiteTicket) throws DingtalkException {
        Assert.notNull(suiteKey, "suiteKey参数为空");
        Assert.notNull(suiteSecret, "suiteSecret参数为空");
        Assert.notNull(suiteTicket, "suiteTicket参数为空");
        log.info("suiteKey={}, suiteSecret={}, suiteTicket={} 获取第三方企业应用的suite_ticket", suiteKey, suiteSecret, suiteTicket);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("suite_key", suiteKey);
        queryParams.put("suite_secret", suiteSecret);
        queryParams.put("suite_ticket", suiteTicket);
        ResponseMessage responseMessage = HttpClientUtils.doPost(URLContent.URL_SERVICE_GET_SUITE_TOKEN, queryParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // SuiteAccessTokenResponse中的嵌套子类未超过两级，可以通过jackson、fastjson、JSON等工具类进行解析；当操作时就需要改用gson工具类进行解析，否则可能解析json失败
        SuiteAccessTokenResponse tokenResponse = GsonUtil.parse(responseMessage.getResult(), new TypeToken<SuiteAccessTokenResponse>() {
        }.getType());
        return tokenResponse;
    }

    /**
     * 获取jsapi_ticket
     *
     * @param accessToken 调用服务端API的应用凭证
     * @return
     * @throws DingtalkException
     */
    public static JsapiTicketResponse getJsapiTicket(String accessToken) throws DingtalkException {
        Assert.notNull(accessToken, "accessToken参数为空");
        log.info("accessToken={} 获取jsapi_ticket", accessToken);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("access_token", accessToken);
        ResponseMessage responseMessage = HttpClientUtils.doGet(URLContent.URL_GET_JSAPI_TICKET, queryParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // JsapiTicketResponse中的嵌套子类未超过两级，可以通过jackson、fastjson、JSON等工具类进行解析；当操作时就需要改用gson工具类进行解析，否则可能解析json失败
        JsapiTicketResponse ticketResponse = GsonUtil.parse(responseMessage.getResult(), new TypeToken<JsapiTicketResponse>() {
        }.getType());
        return ticketResponse;
    }

    /**
     * 获取微应用后台免登的access_token
     *
     * @param corpId     企业的corpid
     * @param corpSecret 微应用管理后台SSOSecret
     */
    public static SsoAccessTokenResponse ssoGettoken(String corpId, String corpSecret) throws DingtalkException {
        Assert.notNull(corpId, "corpid参数为空");
        Assert.notNull(corpSecret, "corpsecret参数为空");
        log.info("corpid={}, corpsecret={} 获取微应用后台免登的access_token", corpId, corpSecret);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("corpid", corpId);
        queryParams.put("corpsecret", corpSecret);
        ResponseMessage responseMessage = HttpClientUtils.doGet(URLContent.URL_SSO_GETTOKKEN, queryParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // SsoAccessTokenResponse中的嵌套子类未超过两级，可以通过jackson、fastjson、JSON等工具类进行解析；当操作时就需要改用gson工具类进行解析，否则可能解析json失败
        SsoAccessTokenResponse tokenResponse = GsonUtil.parse(responseMessage.getResult(), new TypeToken<SsoAccessTokenResponse>() {
        }.getType());
        return tokenResponse;
    }

}
