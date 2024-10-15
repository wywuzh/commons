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
package io.github.wywuzh.commons.dingtalk.request.contacts;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.github.wywuzh.commons.dingtalk.enums.Language;

/**
 * 类UserUpdateRequest的实现描述：更新用户信息请求
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-02 22:02:08
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateRequest extends UserCreateRequest {
    private static final long serialVersionUID = 5738700162274534575L;

    /**
     * 通讯录语言：
     * zh_CN：中文
     * en_US：英文
     */
    @SerializedName(value = "language")
    private String language = Language.zh_CN.getLang();

}
