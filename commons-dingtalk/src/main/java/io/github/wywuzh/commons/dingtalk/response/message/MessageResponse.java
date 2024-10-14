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
package io.github.wywuzh.commons.dingtalk.response.message;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.github.wywuzh.commons.dingtalk.response.BaseResponse;

/**
 * 类MessageResponse的实现描述：消息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-19 09:48:22
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageResponse<T> extends BaseResponse {
    private static final long serialVersionUID = -1100277101788085318L;

    /**
     * 请求ID
     */
    @SerializedName(value = "request_id")
    private String requestId;
}
