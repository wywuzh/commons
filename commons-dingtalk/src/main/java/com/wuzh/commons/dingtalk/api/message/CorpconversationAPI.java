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
package com.wuzh.commons.dingtalk.api.message;

import com.google.gson.reflect.TypeToken;
import com.wuzh.commons.core.http.ResponseMessage;
import com.wuzh.commons.core.json.gson.GsonUtil;
import com.wuzh.commons.dingtalk.api.BaseAPI;
import com.wuzh.commons.dingtalk.config.ApiConfig;
import com.wuzh.commons.dingtalk.constants.URLContent;
import com.wuzh.commons.dingtalk.exception.DingtalkException;
import com.wuzh.commons.dingtalk.request.message.AsyncsendV2Request;
import com.wuzh.commons.dingtalk.response.contacts.ContactsResponse;
import com.wuzh.commons.dingtalk.response.contacts.ListUserByDept;
import com.wuzh.commons.dingtalk.response.contacts.UserGetByMobile;
import com.wuzh.commons.dingtalk.response.message.AsyncsendV2Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 类CorpconversationAPI的实现描述：工作通知
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 21:38:34
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class CorpconversationAPI extends BaseAPI {

    public CorpconversationAPI(ApiConfig apiConfig) {
        super(apiConfig);
    }

    /**
     * 发送工作通知
     *
     * @param request 请求
     */
    public AsyncsendV2Response asyncsendV2(AsyncsendV2Request request) {
        ResponseMessage responseMessage = doPost(URLContent.URL_MESSAGE_CORPCONVERSATION_ASYNCSEND_V2, request);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            log.warn("请求调用失败，请检查URL是否正确！{}", responseMessage);
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        AsyncsendV2Response response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<AsyncsendV2Response>() {
        }.getType());
        return response;
    }
}