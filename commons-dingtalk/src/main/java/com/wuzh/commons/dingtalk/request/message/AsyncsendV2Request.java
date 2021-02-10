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
package com.wuzh.commons.dingtalk.request.message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 类AsyncsendV2Body的实现描述：发送工作通知请求
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 12:40:20
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class AsyncsendV2Request implements Serializable {
    private static final long serialVersionUID = -5900398987563654895L;

    /**
     * 发送消息时使用的微应用的AgentID
     */
    @SerializedName(value = "agent_id")
    private String agent_id;
    /**
     * 接收者的userid列表，最大用户列表长度100
     */
    @SerializedName(value = "userid_list")
    private String userid_list;
    /**
     * 接收者的部门id列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户
     */
    @SerializedName(value = "dept_id_list")
    private String dept_id_list;
    /**
     * 是否发送给企业全部用户。
     * 说明：当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。
     */
    @SerializedName(value = "to_all_user")
    private Boolean to_all_user;
    /**
     * 消息类型
     */
    private MsgType msgType = MsgType.text;
    /**
     * 发送消息时使用的微应用的AgentID
     */
    @SerializedName(value = "msg")
    private Map<String, Object> msg;

    public Map<String, Object> getMsg() {
        Map<String, Object> msg = new HashMap<>();
        msg.put("msgtype", msgType.getType());

        Map<String, Object> content = new HashMap<>();
        switch (msgType) {
            case text: // 文本
                content.put("content", msg);
                break;
            case image: // 图片
                // 媒体文件mediaid。
                //可以通过上传媒体文件接口获取。建议宽600像素 x 400像素，宽高比3 : 2
                content.put("media_id", msg);
                break;
            case voice: // 语音
                //媒体文件ID。
                //可以通过上传媒体文件接口获取。建议宽600像素 x 400像素，宽高比3 : 2
                content.put("media_id", msg);
                content.put("duration", msg);
                break;
            case file: // 文件
                content.put("media_id", msg);
                break;
            case link: // 链接
                content.put("media_id", msg);
                break;
            case oa: // oa
                content.put("media_id", msg);
                break;
            case markdown: // markdown
                content.put("media_id", msg);
                break;
            case action_card: // 卡片
                content.put("media_id", msg);
                break;
            default:
                break;
        }

        msg.put(msgType.getType(), content);
        return msg;
    }

}