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
 * 类DeptLeader的实现描述：是否是领导
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 23:32:25
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class DeptLeader implements Serializable {
    private static final long serialVersionUID = 2434639785386751509L;

    /**
     * 部门ID
     */
    @SerializedName(value = "dept_id")
    private Long deptId;
    /**
     * 是否是领导：true=是, false=不是
     */
    @SerializedName(value = "leader")
    private Boolean leader;

}