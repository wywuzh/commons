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
package com.github.wywuzh.commons.dingtalk.request.contacts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类DepartmentUpdateRequest的实现描述：更新部门请求
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 20:23:01
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentUpdateRequest extends DepartmentCreateRequest {
    private static final long serialVersionUID = -1151758676410204159L;

    /**
     * 通讯录语言：
     * zh_CN：中文
     * en_US：英文
     */
    @SerializedName(value = "language")
    private String language;
    /**
     * 当部门群已经创建后，有新人加入部门时是否会自动加入该群：
     * true：自动加入群
     * false：不会自动加入群
     * 不传值，则保持不变。
     */
    @SerializedName(value = "auto_add_user")
    private Boolean autoAddUser;
    /**
     * 部门的主管userid列表。多个值用英文逗号分隔
     */
    @SerializedName(value = "dept_manager_userid_list")
    private String deptManagerUseridList;
    /**
     * 部门群是否包含子部门：
     * true：包含
     * false：不包含
     * 不传值，则保持不变。
     */
    @SerializedName(value = "group_contain_sub_dept")
    private Boolean groupContainSubDept;
    /**
     * 部门群是否包含外包部门：
     * true：包含
     * false：不包含
     * 不传值，则保持不变。
     */
    @SerializedName(value = "group_contain_outer_dept")
    private Boolean groupContainOuterDept;
    /**
     * 部门群是否包含隐藏部门：
     * true：包含
     * false：不包含
     * 不传值，则保持不变
     */
    @SerializedName(value = "group_contain_hidden_dept")
    private Boolean groupContainHiddenDept;
    /**
     * 企业群群主的userid
     */
    @SerializedName(value = "org_dept_owner")
    private Boolean orgDeptOwner;

}
