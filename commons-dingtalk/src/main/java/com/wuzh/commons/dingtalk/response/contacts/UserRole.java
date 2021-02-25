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
package com.wuzh.commons.dingtalk.response.contacts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 类UserRole的实现描述：角色列表
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 23:34:09
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = -1813026990096327864L;

    /**
     * 角色ID
     */
    @SerializedName(value = "id")
    private Long id;
    /**
     * 角色名称
     */
    @SerializedName(value = "name")
    private String name;
    /**
     * 角色组名称
     */
    @SerializedName(value = "group_name")
    private String groupName;
}