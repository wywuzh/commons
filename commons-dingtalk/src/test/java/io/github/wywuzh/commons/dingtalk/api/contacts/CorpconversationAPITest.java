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
package io.github.wywuzh.commons.dingtalk.api.contacts;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import io.github.wywuzh.commons.dingtalk.api.AbstractTest;
import io.github.wywuzh.commons.dingtalk.api.message.CorpconversationAPI;
import io.github.wywuzh.commons.dingtalk.request.message.AsyncsendV2Request;

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
        request.setAgentId(AGENT_ID);

        // 创建部门 deptId=457162465
//        DepartmentCreateRequest request = new DepartmentCreateRequest();
//        request.setName("mall");
//        request.setParentId(1L);
        deptV2API.asyncsendV2(request);
//        log.info("创建部门：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
    }

}
