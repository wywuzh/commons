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
 * 类DeptBase的实现描述：部门列表
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 15:01:49
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class DeptBase implements Serializable {
    private static final long serialVersionUID = -4480965933938486948L;

    /**
     * 部门ID
     */
    @SerializedName(value = "dept_id")
    private Long deptId;
    /**
     * 部门名称
     */
    @SerializedName(value = "name")
    private String name;
    /**
     * 父部门ID
     */
    @SerializedName(value = "parent_id")
    private Long parentId;
    /**
     * 是否同步创建一个关联此部门的企业群：
     * true：创建
     * false：不创建
     */
    @SerializedName(value = "create_dept_group")
    private Boolean createDeptGroup;
    /**
     * 部门群已经创建后，有新人加入部门是否会自动加入该群：
     * true：会自动入群
     * false：不会
     */
    @SerializedName(value = "auto_add_user")
    private Boolean autoAddUser;
}
