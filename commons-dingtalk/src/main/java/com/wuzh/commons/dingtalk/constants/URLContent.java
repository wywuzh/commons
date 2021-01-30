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
package com.wuzh.commons.dingtalk.constants;

/**
 * 类URLContent的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 11:56:21
 * @version v2.3.8
 * @since JDK 1.8
 */
public class URLContent {

    /**
     * 钉钉服务域名
     */
    public static final String BASE_API_URL = "https://oapi.dingtalk.com";

    /**
     * 获取凭证：获取企业内部应用的access_token
     */
    public static final String URL_GET_TOKKEN = BASE_API_URL + "/gettoken";


    /**
     * 用户管理2.0：根据userid获取用户详情
     */
    public static final String URL_USER_V2_GET = BASE_API_URL + "/topapi/v2/user/get?access_token=#";
    /**
     * 用户管理2.0：根据手机号获取用户信息
     */
    public static final String URL_USER_V2_GET_BY_MOBILE = BASE_API_URL + "/topapi/v2/user/getbymobile?access_token=#";
    /**
     * 用户管理2.0：根据unionid获取用户userid
     */
    public static final String URL_USER_V2_GET_BY_UNIONID = BASE_API_URL + "/topapi/user/getbyunionid?access_token=#";

}