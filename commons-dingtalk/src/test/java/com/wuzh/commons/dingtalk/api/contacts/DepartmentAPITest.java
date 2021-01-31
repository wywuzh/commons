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

import com.wuzh.commons.core.json.jackson.JsonMapper;
import com.wuzh.commons.dingtalk.api.AbstractTest;
import com.wuzh.commons.dingtalk.request.contacts.DepartmentCreateRequest;
import com.wuzh.commons.dingtalk.request.contacts.DepartmentUpdateRequest;
import com.wuzh.commons.dingtalk.response.contacts.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * 类DepartmentAPITest的实现描述：部门管理2.0
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 15:06:19
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class DepartmentAPITest extends AbstractTest {

    @Test
    public void create() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 创建部门 deptId=457162465
        DepartmentCreateRequest request = new DepartmentCreateRequest();
        request.setName("mall");
        request.setParentId(1L);
        ContactsResponse<DeptCreate> response = departmentAPI.create(request);
        log.info("创建部门：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void update() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 更新部门 deptId=457162465
        DepartmentUpdateRequest request = new DepartmentUpdateRequest();
        request.setDeptId(457162465L);
        request.setName("电商中心");
        ContactsResponse<DeptCreate> response = departmentAPI.update(request);
        log.info("更新部门：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void delete() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 删除部门 deptId=457162465
        Long deptId = 457162465L;
        ContactsResponse response = departmentAPI.delete(deptId);
        log.info("删除部门：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void get() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 获取部门详情 deptId=457162465
        Long deptId = 457162465L;
        ContactsResponse<DeptGet> response = departmentAPI.get(deptId);
        log.info("获取部门详情：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void listSubId() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 获取子部门ID列表 deptId=457162465
        Long deptId = 457162465L;
        ContactsResponse<DeptListSubId> response = departmentAPI.listSubId(deptId);
        log.info("获取子部门ID列表：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void listParentByUser() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 获取指定用户的所有父部门列表 deptId=457162465
        String userid = "103559512720455311";
        ContactsResponse<DeptListParentByUser> response = departmentAPI.listParentByUser(userid);
        log.info("获取指定用户的所有父部门列表：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void listParentByDept() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);

        // 获取部门详情 deptId=457162465
        Long deptId = 457162465L;
        ContactsResponse<DeptListParentByDeptId> response = departmentAPI.listParentByDept(deptId);
        log.info("获取指定部门的所有父部门列表：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

    @Test
    public void listSub() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);
        // 获取部门列表
        Long deptId = 1L; // deptId=457359137, name=商品
        ContactsResponse<List<DeptBase>> response = departmentAPI.listSub(deptId);
        log.info("获取部门列表：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }


}