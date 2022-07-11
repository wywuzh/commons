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
 * 类SsoAccessTokenResponse的实现描述：微应用后台免登的access_token
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-10 15:08:30
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SsoAccessTokenResponse extends BaseResponse {
    private static final long serialVersionUID = 5206585234128988288L;

    /**
     * 获取到的凭证
     */
    @SerializedName(value = "access_token")
    private String accessToken;
    /**
     * access_token的过期时间，单位秒
     */
    @SerializedName(value = "expires_in")
    private Integer expiresIn;
}
