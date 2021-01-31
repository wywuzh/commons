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
import com.wuzh.commons.dingtalk.response.contacts.ContactsResponse;
import com.wuzh.commons.dingtalk.response.contacts.DeptBase;
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
    public void listSub() {
        DepartmentAPI departmentAPI = new DepartmentAPI(apiConfig);
        // 获取部门列表
        String deptId = "1"; // deptId=457359137, name=商品
        ContactsResponse<List<DeptBase>> response = departmentAPI.listSub(deptId);
        log.info("获取部门列表：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }


}