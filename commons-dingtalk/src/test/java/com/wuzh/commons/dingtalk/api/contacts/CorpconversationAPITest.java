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
import com.wuzh.commons.dingtalk.api.message.CorpconversationAPI;
import com.wuzh.commons.dingtalk.request.contacts.DepartmentCreateRequest;
import com.wuzh.commons.dingtalk.request.message.AsyncsendV2Request;
import com.wuzh.commons.dingtalk.response.contacts.ContactsResponse;
import com.wuzh.commons.dingtalk.response.contacts.DeptCreate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 类CorpconversationAPITest的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-05 17:39:02
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class CorpconversationAPITest extends AbstractTest {

    @Test
    public void create() {
        CorpconversationAPI deptV2API = new CorpconversationAPI(apiConfig);

        AsyncsendV2Request request = new AsyncsendV2Request();
        request.setAgent_id(AGENT_ID);

        // 创建部门 deptId=457162465
//        DepartmentCreateRequest request = new DepartmentCreateRequest();
//        request.setName("mall");
//        request.setParentId(1L);
        deptV2API.asyncsendV2(request);
//        log.info("创建部门：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

}