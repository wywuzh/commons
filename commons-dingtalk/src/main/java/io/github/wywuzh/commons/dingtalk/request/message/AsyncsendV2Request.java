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
package io.github.wywuzh.commons.dingtalk.request.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

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
    private String agentId;
    /**
     * 接收者的userid列表，最大用户列表长度100
     */
    @SerializedName(value = "userid_list")
    private String useridList;
    /**
     * 接收者的部门id列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户
     */
    @SerializedName(value = "dept_id_list")
    private String deptIdList;
    /**
     * 是否发送给企业全部用户。
     * 说明：当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。
     */
    @SerializedName(value = "to_all_user")
    private Boolean toAllUser;
    /**
     * 消息内容，最长不超过2048个字节
     */
    @SerializedName(value = "msg")
    private Msg msg;

}
