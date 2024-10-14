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
package io.github.wywuzh.commons.dingtalk.constants;

/**
 * 类URLContent的实现描述：钉钉URL地址
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
     * 获取凭证：获取第三方企业应用的access_token
     */
    public static final String URL_SERVICE_GET_CORP_TOKEN = BASE_API_URL + "/service/get_corp_token";
    /**
     * 获取凭证：获取第三方企业应用的suite_ticket
     */
    public static final String URL_SERVICE_GET_SUITE_TOKEN = BASE_API_URL + "/service/get_suite_token";
    /**
     * 获取凭证：获取jsapi_ticket
     */
    public static final String URL_GET_JSAPI_TICKET = BASE_API_URL + "/get_jsapi_ticket";
    /**
     * 获取凭证：获取微应用后台免登的access_token
     */
    public static final String URL_SSO_GETTOKKEN = BASE_API_URL + "/sso/gettoken";

    /**
     * 用户管理2.0：创建用户
     */
    public static final String URL_V2_USER_CREATE = BASE_API_URL + "/topapi/v2/user/create?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：更新用户信息
     */
    public static final String URL_V2_USER_UPDATE = BASE_API_URL + "/topapi/v2/user/update?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：删除用户
     */
    public static final String URL_V2_USER_DELETE = BASE_API_URL + "/topapi/v2/user/delete?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：根据userid获取用户详情
     */
    public static final String URL_V2_USER_GET = BASE_API_URL + "/topapi/v2/user/get?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：获取部门用户基础信息
     */
    public static final String URL_USER_LISTSIMPLE = BASE_API_URL + "/topapi/user/listsimple?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：获取部门用户userid列表
     */
    public static final String URL_USER_LISTID = BASE_API_URL + "/topapi/user/listid?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：获取部门用户详情
     */
    public static final String URL_V2_USER_LIST = BASE_API_URL + "/topapi/v2/user/list?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：根据手机号获取用户信息
     */
    public static final String URL_V2_USER_GETBYMOBILE = BASE_API_URL + "/topapi/v2/user/getbymobile?access_token=#ACCESS_TOKEN";
    /**
     * 用户管理2.0：根据unionid获取用户userid
     */
    public static final String URL_V2_USER_GETBYUNIONID = BASE_API_URL + "/topapi/user/getbyunionid?access_token=#ACCESS_TOKEN";

    /**
     * 部门管理2.0：创建部门
     */
    public static final String URL_V2_DEPARTMENT_CREATE = BASE_API_URL + "/topapi/v2/department/create?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：更新部门
     */
    public static final String URL_V2_DEPARTMENT_UPDATE = BASE_API_URL + "/topapi/v2/department/update?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：删除部门
     */
    public static final String URL_V2_DEPARTMENT_DELETE = BASE_API_URL + "/topapi/v2/department/delete?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：获取部门详情
     */
    public static final String URL_V2_DEPARTMENT_GET = BASE_API_URL + "/topapi/v2/department/get?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：获取子部门ID列表
     */
    public static final String URL_V2_DEPARTMENT_LISTSUBID = BASE_API_URL + "/topapi/v2/department/listsubid?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：获取指定用户的所有父部门列表
     */
    public static final String URL_V2_DEPARTMENT_LISTPARENTBYUSER = BASE_API_URL + "/topapi/v2/department/listparentbyuser?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：获取指定部门的所有父部门列表
     */
    public static final String URL_V2_DEPARTMENT_LISTPARENTBYDEPT = BASE_API_URL + "/topapi/v2/department/listparentbydept?access_token=#ACCESS_TOKEN";
    /**
     * 部门管理2.0：获取部门列表
     */
    public static final String URL_V2_DEPARTMENT_LISTSUB = BASE_API_URL + "/topapi/v2/department/listsub?access_token=#ACCESS_TOKEN";

    /**
     * 消息通知：发送工作通知
     */
    public static final String URL_MESSAGE_CORPCONVERSATION_ASYNCSEND_V2 = BASE_API_URL + "/topapi/message/corpconversation/asyncsend_v2?access_token=#ACCESS_TOKEN";

}
