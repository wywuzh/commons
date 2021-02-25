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
import com.wuzh.commons.dingtalk.enums.Language;
import com.wuzh.commons.dingtalk.exception.DingtalkException;
import com.wuzh.commons.dingtalk.request.contacts.DepartmentCreateRequest;
import com.wuzh.commons.dingtalk.request.contacts.DepartmentUpdateRequest;
import com.wuzh.commons.dingtalk.response.contacts.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 类DeptV2API的实现描述：部门管理2.0
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 14:58:28
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class DeptV2API extends BaseAPI {

    public DeptV2API(ApiConfig apiConfig) {
        super(apiConfig);
    }

    /**
     * 创建部门
     *
     * @param request 请求
     * @return
     */
    public ContactsResponse<DeptCreate> create(DepartmentCreateRequest request) {
        Assert.notNull(request, "request must not be null");
        Assert.notNull(request.getName(), "request name must not be null");
        Assert.notNull(request.getParentId(), "request parentId must not be null");

        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_CREATE, request);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<DeptCreate> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<DeptCreate>>() {
        }.getType());
        return response;
    }

    /**
     * 更新部门
     *
     * @param request 请求
     * @return
     */
    public ContactsResponse<DeptCreate> update(DepartmentUpdateRequest request) {
        Assert.notNull(request, "request must not be null");
        Assert.notNull(request.getDeptId(), "request deptId must not be null");

        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_UPDATE, request);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<DeptCreate> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<DeptCreate>>() {
        }.getType());
        return response;
    }

    /**
     * 删除部门
     *
     * @param deptId 部门id
     * @return
     */
    public ContactsResponse delete(Long deptId) {
        Assert.notNull(deptId, "deptId must not be null");

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("dept_id", deptId);
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_DELETE, requestParams);
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
     * 获取部门详情
     *
     * @param deptId   部门id
     * @param language 通讯录语言，传入为空时默认为“zh_CN”
     * @return
     */
    public ContactsResponse<DeptGet> get(Long deptId, Language language) {
        Assert.notNull(deptId, "deptId must not be null");
        // 通讯录语言
        language = Optional.ofNullable(language).orElse(Language.zh_CN);

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("dept_id", deptId);
        requestParams.put("language", language.getLang());
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_GET, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<DeptGet> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<DeptGet>>() {
        }.getType());
        return response;
    }

    /**
     * 获取子部门ID列表
     *
     * @param deptId 父部门ID，根部门传1
     * @return
     */
    public ContactsResponse<DeptListSubId> listSubId(Long deptId) {
        Assert.notNull(deptId, "deptId must not be null");

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("dept_id", deptId);
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_LISTSUBID, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<DeptListSubId> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<DeptListSubId>>() {
        }.getType());
        return response;
    }

    /**
     * 获取指定用户的所有父部门列表
     *
     * @param userid 用户id
     * @return
     */
    public ContactsResponse<DeptListParentByUser> listParentByUser(String userid) {
        Assert.notNull(userid, "userid must not be null");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("userid", userid);
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_LISTPARENTBYUSER, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<DeptListParentByUser> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<DeptListParentByUser>>() {
        }.getType());
        return response;
    }

    /**
     * 获取指定部门的所有父部门列表
     *
     * @param deptId 要查询的部门的ID
     * @return
     */
    public ContactsResponse<DeptListParentByDeptId> listParentByDept(Long deptId) {
        Assert.notNull(deptId, "deptId must not be null");

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("dept_id", deptId);
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_LISTPARENTBYDEPT, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<DeptListParentByDeptId> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<DeptListParentByDeptId>>() {
        }.getType());
        return response;
    }

    /**
     * 获取部门列表
     *
     * @param deptId   父部门ID。如果不传，默认部门为根部门，根部门ID为1。只支持查询下一级子部门，不支持查询多级子部门
     * @param language 通讯录语言，传入为空时默认为“zh_CN”
     * @return
     */
    public ContactsResponse<List<DeptBase>> listSub(Long deptId, Language language) {
        Assert.notNull(deptId, "deptId must not be null");
        // 通讯录语言
        language = Optional.ofNullable(language).orElse(Language.zh_CN);

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("dept_id", deptId);
        requestParams.put("language", language.getLang());
        ResponseMessage responseMessage = doPost(URLContent.URL_V2_DEPARTMENT_LISTSUB, requestParams);
        log.info("返回结果：{}", responseMessage);
        if (HttpStatus.SC_OK != responseMessage.getStatusCode()) {
            throw new DingtalkException("请求调用失败，请检查URL是否正确！");
        }
        // 注：当一个类的子类嵌套级别超过2层时，需要改用gson工具类进行解析，否则可能会解析失败
        ContactsResponse<List<DeptBase>> response = GsonUtil.parse(responseMessage.getResult(), new TypeToken<ContactsResponse<List<DeptBase>>>() {
        }.getType());
        return response;
    }

}