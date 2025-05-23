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
package io.github.wywuzh.commons.dingtalk.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类BaseResponse的实现描述：响应基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 12:40:20
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseResponse extends io.github.wywuzh.commons.core.web.response.BaseResponse {
    private static final long serialVersionUID = -7123803560551710336L;

    /**
     * 返回码
     */
    @SerializedName(value = "errcode")
    private Integer errCode;
    /**
     * 返回码描述
     */
    @SerializedName(value = "errmsg")
    private String errMsg;

    @SerializedName(value = "sub_code")
    private String subCode;
    @SerializedName(value = "sub_msg")
    private String subMsg;
}
