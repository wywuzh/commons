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
package com.github.wywuzh.commons.dingtalk.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类SuiteAccessTokenResponse的实现描述：第三方企业应用的凭证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-10 13:22:40
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SuiteAccessTokenResponse extends BaseResponse {
    private static final long serialVersionUID = -4364817866794670941L;

    /**
     * 应用套件的凭证
     */
    @SerializedName(value = "suite_access_token")
    private String suiteAccessToken;
    /**
     * 应用套件凭证的过期时间，单位秒
     * <p>
     * 说明：suite_access_token有效期为7200秒，过期之前建议服务端做定时器主动更新，而不是依赖钉钉的定时推送。
     */
    @SerializedName(value = "expires_in")
    private Integer expiresIn;
}
