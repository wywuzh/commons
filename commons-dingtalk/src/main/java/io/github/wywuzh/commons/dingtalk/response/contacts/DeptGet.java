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
package io.github.wywuzh.commons.dingtalk.response.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 类DeptGet的实现描述：获取部门详情
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 21:03:52
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class DeptGet implements Serializable {
    private static final long serialVersionUID = 6988603116659725985L;

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
     * 当隐藏本部门时（即hide_dept为true时），配置的允许在通讯录中查看本部门的部门列表。
     */
    @SerializedName(value = "dept_permits")
    private List<Long> deptPermits;
    /**
     * 当隐藏本部门时（即hide_dept为true时），配置的允许在通讯录中查看本部门的员工列表。
     */
    @SerializedName(value = "user_permits")
    private List<String> userPermits;
    /**
     * 是否限制本部门成员查看通讯录：
     * true：开启限制。开启后本部门成员只能看到限定范围内的通讯录
     * false（默认值）：不限制
     */
    @SerializedName(value = "outer_dept")
    private Boolean outerDept;
    /**
     * 当限制部门成员的通讯录查看范围时（即outer_dept为true时），配置的部门员工可见员工列表。
     */
    @SerializedName(value = "outer_permit_users")
    private List<String> outerPermitUsers;
    /**
     * 当限制部门成员的通讯录查看范围时（即outer_dept为true时），配置的部门员工可见部门列表。
     */
    @SerializedName(value = "outer_permit_depts")
    private List<Long> outerPermitDepts;
    /**
     * 是否同步创建一个关联此部门的企业群：
     * true：创建
     * false：不创建
     */
    @SerializedName(value = "create_dept_group")
    private Boolean createDeptGroup;
    /**
     * 在父部门中的排序值，order值小的排序靠前
     */
    @SerializedName(value = "order")
    private Long order;
    /**
     * 部门标识字段，开发者可用该字段来唯一标识一个部门，并与钉钉外部通讯录里的部门做映射. 说明：第三方企业应用不返回该参数。
     */
    @SerializedName(value = "source_identifier")
    private String sourceIdentifier;
    /**
     * 当部门群已经创建后，有新人加入部门时是否会自动加入该群：
     * true：自动加入群
     * false：不会自动加入群
     * 不传值，则保持不变。
     */
    @SerializedName(value = "auto_add_user")
    private Boolean autoAddUser;
    /**
     * 部门是否来自关联组织：
     * true：是
     * false：不是
     * 说明：第三方企业应用不返回该参数。
     */
    @SerializedName(value = "from_union_org")
    private Boolean fromUnionOrg;
    /**
     * 教育部门标签：
     * campus：校区
     * period：学段
     * grade：年级
     * class：班级
     * 说明 第三方企业应用不返回该参数。
     */
    @SerializedName(value = "tags")
    private String tags;
    /**
     * 部门群ID
     */
    @SerializedName(value = "dept_group_chat_id")
    private String deptGroupChatId;
    /**
     * 部门群是否包含子部门：
     * true：包含
     * false：不包含
     * 不传值，则保持不变。
     */
    @SerializedName(value = "group_contain_sub_dept")
    private Boolean groupContainSubDept;
    /**
     * 企业群群主的userid
     */
    @SerializedName(value = "org_dept_owner")
    private Boolean orgDeptOwner;
    /**
     * 部门的主管userid列表
     */
    @SerializedName(value = "dept_manager_userid_list")
    private List<String> deptManagerUseridList;
}
