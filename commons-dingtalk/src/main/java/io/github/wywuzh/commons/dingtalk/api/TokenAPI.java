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
package io.github.wywuzh.commons.dingtalk.api;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.springframework.util.Assert;

import io.github.wywuzh.commons.core.http.HttpClientUtils;
import io.github.wywuzh.commons.core.http.ResponseMessage;
import io.github.wywuzh.commons.core.json.gson.GsonUtil;
import io.github.wywuzh.commons.dingtalk.constants.URLContent;
import io.github.wywuzh.commons.dingtalk.exception.DingtalkException;
import io.github.wywuzh.commons.dingtalk.response.AccessTokenResponse;
import io.github.wywuzh.commons.dingtalk.response.JsapiTicketResponse;
import io.github.wywuzh.commons.dingtalk.response.SsoAccessTokenResponse;
import io.github.wywuzh.commons.dingtalk.response.SuiteAccessTokenResponse;

/**
 * 类TokenAPI的实现描述：获取凭证(旧版)
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
     * @see <a href="https://open.dingtalk.com/document/orgapp/obtain-orgapp-token">获取企业内部应用的access_token</a>
     * @return 企业内部应用的access_token凭证
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
     * 获取定制应用的access_token(HTTP请求)
     *
     * @param accessKey   如果是定制应用，输入定制应用的CustomKey；如果是第三方企业应用，输入第三方企业应用的SuiteKey
     * @param timestamp   当前时间戳，单位是毫秒
     * @param suiteTicket 钉钉推送的suiteTicket：定制应用可随意填写；第三方企业应用使用钉钉开放平台向应用推送的suite_ticket
     * @param signature   签名
     * @param authCorpid  授权企业的CorpId
     * @see <a href="https://open.dingtalk.com/document/orgapp/obtains-the-enterprise-authorized-credential">获取定制应用的access_token</a>
     * @see <a href=
     *      "https://open.dingtalk.com/document/direction-test/signature-calculation-method-for-third-party-access-interfaces?spm=ding_open_doc.document.0.0.66d84a97IjCGKC">企业内部应用，访问接口的signature签名计算方法</a>
     * @see <a href="https://open.dingtalk.com/document/isvapp/signature-calculation-method-for-third-party-access-interfaces-1">第三方企业应用，访问接口的signature签名计算方法</a>
     * @return 第三方企业应用的access_token凭证
     */
    public static AccessTokenResponse getCorpToken(String accessKey, Long timestamp, String suiteTicket, String signature, String authCorpid) throws DingtalkException {
        Assert.notNull(accessKey, "accessKey参数为空");
        Assert.notNull(timestamp, "timestamp参数为空");
        Assert.notNull(suiteTicket, "suiteTicket参数为空");
        Assert.notNull(signature, "signature参数为空");
        Assert.notNull(authCorpid, "authCorpid参数为空");
        log.info("accessKey={}, accessSecret={} 获取第三方企业应用的access_token", accessKey, timestamp);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("accessKey", accessKey);
        queryParams.put("timestamp", String.valueOf(timestamp));
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
     * @return 第三方企业应用的suite_ticket凭证
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
     * @see <a href="https://open.dingtalk.com/document/orgapp/obtain-jsapi_ticket">获取jsapi_ticket</a>
     * @return jsapi_ticket凭证
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
     * @see <a href="https://open.dingtalk.com/document/orgapp/obtain-the-ssotoken-for-micro-application-background-logon-free">获取微应用后台免登的access_token</a>
     * @return 微应用后台免登的access_token凭证
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
