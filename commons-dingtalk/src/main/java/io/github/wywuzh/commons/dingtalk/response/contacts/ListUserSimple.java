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
 * 类ListUserSimple的实现描述：用户信息列表
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-11 15:34:44
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class ListUserSimple implements Serializable {
    private static final long serialVersionUID = 8979729297062092927L;

    /**
     * 用户的userid
     */
    @SerializedName(value = "userid")
    private String userid;
    /**
     * 用户姓名
     */
    @SerializedName(value = "name")
    private String name;
}
