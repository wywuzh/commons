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
import java.util.List;

import lombok.Data;

import io.github.wywuzh.commons.dingtalk.response.contacts.userget.DeptLeader;
import io.github.wywuzh.commons.dingtalk.response.contacts.userget.DeptOrder;
import io.github.wywuzh.commons.dingtalk.response.contacts.userget.UnionEmpExt;

/**
 * 类UserGet的实现描述：根据userid获取用户详情
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:53:47
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserGet implements Serializable {
    private static final long serialVersionUID = -6114043198179249811L;

    /**
     * 员工的userid
     */
    @SerializedName(value = "userid")
    private String userid;
    /**
     * 员工在当前开发者企业账号范围内的唯一标识
     */
    @SerializedName(value = "unionid")
    private String unionid;
    /**
     * 员工名称
     */
    @SerializedName(value = "name")
    private String name;
    /**
     * 头像
     */
    @SerializedName(value = "avatar")
    private String avatar;
    /**
     * 国际电话区号. eg: 86
     */
    @SerializedName(value = "state_code")
    private String stateCode;
    /**
     * 手机号码. eg: 185xxxx7676
     */
    @SerializedName(value = "mobile")
    private String mobile;
    /**
     * 是否号码隐藏：true=隐藏, false=不隐藏
     */
    @SerializedName(value = "hide_mobile")
    private Boolean hideMobile;
    /**
     * 分机号. eg: 010-8xxxx6-2345
     */
    @SerializedName(value = "telephone")
    private String telephone;
    /**
     * 员工工号
     */
    @SerializedName(value = "job_number")
    private String jobNumber;
    /**
     * 职位
     */
    @SerializedName(value = "title")
    private String title;
    /**
     * 员工邮箱. eg: test@example.com
     */
    @SerializedName(value = "email")
    private String email;
    /**
     * 办公地点
     */
    @SerializedName(value = "work_place")
    private String workPlace;
    /**
     * 备注
     */
    @SerializedName(value = "remark")
    private String remark;
    /**
     * 所属部门ID列表. eg: [2,3,4]
     */
    @SerializedName(value = "dept_id_list")
    private List<Integer> deptIdList;
    /**
     * 员工在对应的部门中的排序
     */
    @SerializedName(value = "dept_order_list")
    private List<DeptOrder> deptOrderList;
    /**
     * 扩展属性，最大长度2000个字符. eg: {"爱好":"旅游","年龄":"24"}
     */
    @SerializedName(value = "extension")
    private String extension;
    /**
     * 入职时间，Unix时间戳，单位毫秒
     */
    @SerializedName(value = "hired_date")
    private Long hiredDate;
    /**
     * 是否激活了钉钉：true=已激活, false=未激活
     */
    @SerializedName(value = "active")
    private Boolean active;
    /**
     * 是否完成了实名认证：true=已认证, false=未认证
     */
    @SerializedName(value = "real_authed")
    private String realAuthed;
    /**
     * 是否为企业的高管：true=是, false=不是
     */
    @SerializedName(value = "senior")
    private String senior;
    /**
     * 是否为企业的管理员：true=是, false=不是
     */
    @SerializedName(value = "admin")
    private String admin;
    /**
     * 是否为企业的老板：true=是, false=不是
     */
    @SerializedName(value = "boss")
    private String boss;

    /**
     * 员工在对应的部门中是否领导
     */
    @SerializedName(value = "leader_in_dept")
    private List<DeptLeader> leaderInDept;
    /**
     * 角色列表
     */
    @SerializedName(value = "role_list")
    private List<UserRole> roleList;
    /**
     * 当用户来自于关联组织时的关联信息
     */
    @SerializedName(value = "union_emp_ext")
    private List<UnionEmpExt> unionEmpExt;

}
