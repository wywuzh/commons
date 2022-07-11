/*
 * Copyright 2015-2022 the original author or authors.
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
package com.github.wywuzh.commons.dingtalk.api.message;

import com.github.wywuzh.commons.core.json.gson.GsonUtil;
import com.github.wywuzh.commons.dingtalk.api.AbstractTest;
import com.github.wywuzh.commons.dingtalk.request.message.AsyncsendV2Request;
import com.github.wywuzh.commons.dingtalk.request.message.Msg;
import com.github.wywuzh.commons.dingtalk.request.message.MsgType;
import com.github.wywuzh.commons.dingtalk.response.message.AsyncsendV2Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 类CorpconversationAPITest的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-18 20:41:20
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class CorpconversationAPITest extends AbstractTest {

    @Test
    public void asyncsendV2ForVoice() {
        CorpconversationAPI corpconversationAPI = new CorpconversationAPI(apiConfig);

        // 创建部门 deptId=457162465
        AsyncsendV2Request request = new AsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setDeptIdList(null);
        request.setToAllUser(false);
        request.setUseridList("manager8283");

        Msg msg = new Msg();
        msg.setMsgType(MsgType.text);
        Msg.Voice voice = new Msg.Voice();
        voice.setDuration(null);
        voice.setMediaId(null);
        request.setMsg(msg);

        log.info("请求条件：{}", GsonUtil.format(request));

        AsyncsendV2Response response = corpconversationAPI.asyncsendV2(request);
        log.info("发送文本消息结果：{}", GsonUtil.format(response));
    }

    @Test
    public void asyncsendV2ForText() {
        CorpconversationAPI corpconversationAPI = new CorpconversationAPI(apiConfig);

        // 创建部门 deptId=457162465
        AsyncsendV2Request request = new AsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setDeptIdList(null);
        request.setToAllUser(false);
        request.setUseridList("manager8283");

        Msg msg = new Msg();
        msg.setMsgType(MsgType.text);
        Msg.Text text = new Msg.Text();
        text.setContent("12345");
        msg.setText(text);
        request.setMsg(msg);

        log.info("请求条件：{}", GsonUtil.format(request));

        AsyncsendV2Response response = corpconversationAPI.asyncsendV2(request);
        log.info("发送文本消息结果：{}", GsonUtil.format(response));
    }

}
