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
package com.wuzh.commons.dingtalk.request.contacts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 类DepartmentCreateRequest的实现描述：创建部门
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 18:51:13
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class DepartmentCreateRequest implements Serializable {

    /**
     * 部门ID
     */
    @SerializedName(value = "dept_id")
    private Long deptId;
    /**
     * 部门名称。长度限制为1~64个字符，不允许包含字符"-"","以及","
     */
    @SerializedName(value = "name")
    private String name;
    /**
     * 父部门ID，根部门ID为1
     */
    @SerializedName(value = "parent_id")
    private Long parentId;
    /**
     * 是否隐藏本部门：
     * true：隐藏部门，隐藏后本部门将不会显示在公司通讯录中
     * false（默认值）：显示部门
     */
    @SerializedName(value = "hide_dept")
    private Boolean hideDept;
    /**
     * 指定可以查看本部门的其他部门列表，总数不能超过200。当hide_dept为true时，则此值生效。多个值用英文逗号分隔
     */
    @SerializedName(value = "dept_permits")
    private String deptPermits;
    /**
     * 指定可以查看本部门的人员userid列表，总数不能超过200。当hide_dept为true时，则此值生效。多个值用英文逗号分隔
     */
    @SerializedName(value = "user_permits")
    private String userPermits;
    /**
     * 是否限制本部门成员查看通讯录：
     * true：开启限制。开启后本部门成员只能看到限定范围内的通讯录
     * false（默认值）：不限制
     */
    @SerializedName(value = "outer_dept")
    private Boolean outerDept;
    /**
     * 本部门成员是否只能看到所在部门及下级部门通讯录：
     * true：只能看到所在部门及下级部门通讯录
     * false：不能查看所有通讯录，在通讯录中仅能看到自己
     * 当outer_dept为true时，此参数生效。
     */
    @SerializedName(value = "outer_dept_only_self")
    private Boolean outerDeptOnlySelf;
    /**
     * 指定本部门成员可查看的通讯录用户userid列表，总数不能超过200。当outer_dept为true时，此参数生效。多个值用英文逗号分隔
     */
    @SerializedName(value = "outer_permit_users")
    private String outerPermitUsers;
    /**
     * 指定本部门成员可查看的通讯录部门ID列表，总数不能超过200。当outer_dept为true时，此参数生效。多个值用英文逗号分隔
     */
    @SerializedName(value = "outer_permit_depts")
    private String outerPermitDepts;
    /**
     * 是否创建一个关联此部门的企业群，默认为false即不创建
     */
    @SerializedName(value = "create_dept_group")
    private Boolean createDeptGroup;
    /**
     * 在父部门中的排序值，order值小的排序靠前
     */
    @SerializedName(value = "order")
    private Long order;
    /**
     * 部门标识字段，开发者可用该字段来唯一标识一个部门，并与钉钉外部通讯录里的部门做映射
     */
    @SerializedName(value = "source_identifier")
    private String sourceIdentifier;
}