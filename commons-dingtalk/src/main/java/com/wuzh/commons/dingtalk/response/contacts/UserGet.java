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
import java.util.List;

/**
 * 类UserGet的实现描述：根据userid获取用户详情
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:53:47
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserGet implements Serializable {

    /**
     * 员工的userid
     */
    @SerializedName("userid")
    private String userId;
    /**
     * 员工在当前开发者企业账号范围内的唯一标识
     */
    @SerializedName("unionid")
    private String unionId;
    /**
     * 员工名称
     */
    @SerializedName("name")
    private String name;
    /**
     * 头像
     */
    @SerializedName("avatar")
    private String avatar;
    /**
     * 国际电话区号. eg: 86
     */
    @SerializedName("state_code")
    private String state_code;
    /**
     * 手机号码. eg: 185xxxx7676
     */
    @SerializedName("mobile")
    private String mobile;
    /**
     * 是否号码隐藏：true=隐藏, false=不隐藏
     */
    @SerializedName("hide_mobile")
    private Boolean hide_mobile;
    /**
     * 分机号. eg: 010-8xxxx6-2345
     */
    @SerializedName("telephone")
    private String telephone;
    /**
     * 员工工号
     */
    @SerializedName("job_number")
    private String job_number;
    /**
     * 职位
     */
    @SerializedName("title")
    private String title;
    /**
     * 员工邮箱. eg: test@example.com
     */
    @SerializedName("email")
    private String email;
    /**
     * 办公地点
     */
    @SerializedName("work_place")
    private String work_place;
    /**
     * 备注
     */
    @SerializedName("remark")
    private String remark;
    /**
     * 所属部门ID列表. eg: [2,3,4]
     */
    @SerializedName("dept_id_list")
    private List<Integer> dept_id_list;
    /**
     * 员工在对应的部门中的排序
     */
    @SerializedName("dept_order_list")
    private List<DeptOrder> dept_order_list;
    /**
     * 扩展属性，最大长度2000个字符. eg: {"爱好":"旅游","年龄":"24"}
     */
    @SerializedName("extension")
    private String extension;
    /**
     * 入职时间，Unix时间戳，单位毫秒
     */
    @SerializedName("hired_date")
    private String hired_date;
    /**
     * 是否激活了钉钉：true=已激活, false=未激活
     */
    @SerializedName("active")
    private Boolean active;
    /**
     * 是否完成了实名认证：true=已认证, false=未认证
     */
    @SerializedName("real_authed")
    private String real_authed;

}