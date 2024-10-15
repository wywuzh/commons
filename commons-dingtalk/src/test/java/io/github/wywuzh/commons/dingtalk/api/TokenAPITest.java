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
package io.github.wywuzh.commons.dingtalk.api;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import io.github.wywuzh.commons.core.json.jackson.JsonMapper;
import io.github.wywuzh.commons.dingtalk.response.AccessTokenResponse;
import io.github.wywuzh.commons.dingtalk.response.JsapiTicketResponse;
import io.github.wywuzh.commons.dingtalk.response.SsoAccessTokenResponse;
import io.github.wywuzh.commons.dingtalk.response.SuiteAccessTokenResponse;

/**
 * 类TokenAPITest的实现描述：获取凭证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-10 15:18:20
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class TokenAPITest {

    @Test
    public void getCorpToken() {
        String accessKey = "XXX";
        String accessSecret = "XXX";
        String suiteTicket = "XXX";
        String signature = "XXX";
        String authCorpid = "XXX";
        AccessTokenResponse accessTokenResponse = TokenAPI.getCorpToken(accessKey, accessSecret, suiteTicket, signature, authCorpid);
        log.info("获取第三方企业应用的access_token：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(accessTokenResponse));
    }

    @Test
    public void getSuiteToken() {
        String suiteKey = "XXX";
        String suiteSecret = "XXX";
        String suiteTicket = "XXX";
        SuiteAccessTokenResponse suiteAccessTokenResponse = TokenAPI.getSuiteToken(suiteKey, suiteSecret, suiteTicket);
        log.info("获取第三方企业应用的suite_ticket：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(suiteAccessTokenResponse));
    }

    @Test
    public void getJsapiTicket() {
        String accessToken = "XXX";
        JsapiTicketResponse jsapiTicketResponse = TokenAPI.getJsapiTicket(accessToken);
        log.info("获取jsapi_ticket：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(jsapiTicketResponse));
    }

    @Test
    public void ssoGettoken() {
        String corpId = "XXX";
        String corpSecret = "XXX";
        SsoAccessTokenResponse ssoAccessTokenResponse = TokenAPI.ssoGettoken(corpId, corpSecret);
        log.info("获取微应用后台免登的access_token：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(ssoAccessTokenResponse));
    }

}
