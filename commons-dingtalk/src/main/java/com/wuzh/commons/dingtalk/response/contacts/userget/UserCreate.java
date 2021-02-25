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
package com.wuzh.commons.dingtalk.response.contacts.userget;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 类UserCreate的实现描述：创建用户
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:22:15
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserCreate implements Serializable {
    private static final long serialVersionUID = 3802477338441046693L;

    /**
     * 员工的userid
     */
    @SerializedName(value = "userid")
    private String userid;

}