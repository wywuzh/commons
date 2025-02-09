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
package io.github.wywuzh.commons.dingtalk.response.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * 类UserGetByUnionId的实现描述：根据手机号获取用户信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:42:03
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserGetByUnionid implements Serializable {
    private static final long serialVersionUID = -7942331814511039517L;

    /**
     * 联系类型：0=企业内部员工, 1=企业外部联系人
     */
    @SerializedName(value = "contact_type")
    private String contactType;

    /**
     * 员工的userid
     */
    @SerializedName(value = "userid")
    private String userid;

}
