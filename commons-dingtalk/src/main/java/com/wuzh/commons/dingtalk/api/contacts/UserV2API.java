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
package com.wuzh.commons.dingtalk.api.contacts;

import com.google.gson.reflect.TypeToken;
import com.wuzh.commons.core.http.ResponseMessage;
import com.wuzh.commons.core.json.gson.GsonUtil;
import com.wuzh.commons.dingtalk.api.BaseAPI;
import com.wuzh.commons.dingtalk.config.ApiConfig;
import com.wuzh.commons.dingtalk.constants.URLContent;
import com.wuzh.commons.dingtalk.exception.DingtalkException;
import com.wuzh.commons.dingtalk.request.contacts.UserCreateRequest;
import com.wuzh.commons.dingtalk.request.contacts.UserUpdateRequest;
import com.wuzh.commons.dingtalk.response.BaseResponse;
import com.wuzh.commons.dingtalk.response.contacts.ContactsResponse;
import com.wuzh.commons.dingtalk.response.contacts.UserGet;
import com.wuzh.commons.dingtalk.response.contacts.UserGetByMobile;
import com.wuzh.commons.dingtalk.response.contacts.UserGetByUnionId;
import com.wuzh.commons.dingtalk.response.contacts.userget.UserCreate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 类UserV2API的实现描述：用户管理2.0
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 20:56:04
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class UserV2API extends BaseAPI {

    public UserV2API(ApiConfig apiConfig) {
        super(apiConfig);
    }

    /**
     * 创建用户
     *
     * @param request 请求
     * @return
     */
    public ContactsResponse<UserCreate> create(UserCreateRequest request) {
        Assert.notNull(request, "request must not be null");
        Assert.notNull(request.getName(), "request name must not be null");

        ResponseMessage responseMessage = doPost(URLContent.URL_V2_USER_CREATE, request);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<UserCreate> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<UserCreate>>() {
        }.getType());
        return response;
    }

    /**
     * 更新用户信息
     *
     * @param request 请求
     * @return
     */
    public ContactsResponse update(UserUpdateRequest request) {
        Assert.notNull(request, "request must not be null");
        Assert.notNull(request.getUserid(), "request userid must not be null");

        ResponseMessage responseMessage = doPost(URLContent.URL_V2_USER_UPDATE, request);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse>() {
        }.getType());
        return response;
    }

    /**
     * 根据userid获取用户详情
     *
     * @param userId 用户的userid
     */
    public ContactsResponse<UserGet> get(String userId) {
        Assert.notNull(userId, "userId must not be null");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("userid", userId);
        requestParams.put("language", "zh_CN");
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_USER_GET, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<UserGet> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<UserGet>>() {
        }.getType());
        return response;
    }

    /**
     * 根据手机号获取userid
     *
     * @param mobile 用户的手机号
     */
    public ContactsResponse<UserGetByMobile> getByMobile(String mobile) {
        Assert.notNull(mobile, "mobile must not be null");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("mobile", mobile);
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_USER_GETBYMOBILE, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        ContactsResponse<UserGetByMobile> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<UserGetByMobile>>() {
        }.getType());
        return response;
    }

    /**
     * 根据unionid获取用户userid
     * <pre>
     * 注意：unionid是员工在当前开发者企业账号范围内的唯一标识，由系统生成：
     * 1. 同一个企业员工，在不同的开发者企业账号下，unionid是不相同的。
     * 2. 在同一个开发者企业账号下，unionid是唯一且不变的，例如同一个服务商开发的多个应用，或者是扫码登录等场景的多个App账号。
     * </pre>
     *
     * @param unionID 员工在当前开发者企业账号范围内的唯一标识，系统生成，不会改变
     */
    public ContactsResponse<UserGetByUnionId> getByUnionID(String unionID) {
        Assert.notNull(unionID, "unionID must not be null");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("unionid", unionID);
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_USER_GETBYUNIONID, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        ContactsResponse<UserGetByUnionId> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<UserGetByUnionId>>() {
        }.getType());
        return response;
    }

}