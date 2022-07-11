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
 * 类AccessTokenResponse的实现描述：获取凭证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 12:40:20
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccessTokenResponse extends BaseResponse {
    private static final long serialVersionUID = -8122654296338669728L;

    /**
     * 生成的access_token
     *
     * <pre>
     * 注意 在使用access_token时，请注意：
     * 1. access_token的有效期为7200秒（2小时），有效期内重复获取会返回相同结果并自动续期，过期后获取会返回新的access_token。
     * 2. 开发者需要缓存access_token，用于后续接口的调用。因为每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。
     * 3. 不能频繁调用gettoken接口，否则会受到频率拦截。
     * </pre>
     */
    @SerializedName(value = "access_token")
    private String accessToken;
    /**
     * access_token的过期时间，单位秒
     */
    @SerializedName(value = "expires_in")
    private Integer expiresIn;

}
