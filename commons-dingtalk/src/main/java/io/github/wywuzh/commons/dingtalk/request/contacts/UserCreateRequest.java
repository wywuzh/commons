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
package io.github.wywuzh.commons.dingtalk.request.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 类UserCreateRequest的实现描述：创建用户请求
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 13:47:02
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class UserCreateRequest implements Serializable {
    private static final long serialVersionUID = -7026751142196405998L;

    /**
     * 员工唯一标识ID（不可修改），企业内必须唯一。长度为1~64个字符，如果不传，将自动生成一个userid
     */
    @SerializedName("userid")
    private String userid;
    /**
     * 员工名称，长度最大80个字符
     */
    @SerializedName("name")
    private String name;
    /**
     * 手机号码，企业内必须唯一，不可重复。如果是国际号码，请使用+xx-xxxxxx的格式。eg: 18513027676
     */
    @SerializedName("mobile")
    private String mobile;
    /**
     * 是否号码隐藏：
     *
     * <pre>
     * true: 隐藏隐藏手机号后，手机号在个人资料页隐藏，但仍可对其发DING、发起钉钉免费商务电话。
     * false: 不隐藏
     * </pre>
     */
    @SerializedName("hide_mobile")
    private Boolean hideMobile;
    /**
     * 分机号，长度最大50个字符。企业内必须唯一，不可重复。eg: 010-86123456-2345
     */
    @SerializedName("telephone")
    private String telephone;
    /**
     * 员工工号。对应显示到OA后台和客户端个人资料的工号栏目。长度最大为50个字符
     */
    @SerializedName("job_number")
    private String jobNumber;
    /**
     * 职位，长度最大为200个字符
     */
    @SerializedName("title")
    private String title;
    /**
     * 邮箱。长度为0~64个字符。企业内必须唯一，不可重复. eg: test@xxx.com
     */
    @SerializedName("email")
    private String email;
    /**
     * 员工的企业邮箱，员工的企业邮箱已开通，才能增加此字段， 否则会报错. eg: test@xxx.com
     */
    @SerializedName("org_email")
    private String orgEmail;
    /**
     * 办公地点，长度最大100个字符
     */
    @SerializedName("work_place")
    private String workPlace;
    /**
     * 备注，长度为0~1000个字符
     */
    @SerializedName("remark")
    private String remark;
    /**
     * 所属部门id列表. eg: 2,3,4
     */
    @SerializedName("dept_id_list")
    private String deptIdList;
    /**
     * 员工在对应的部门中的排序
     */
    @SerializedName("dept_order_list")
    private List<DeptOrder> deptOrderList;
    /**
     * 员工在对应的部门中的职位
     */
    @SerializedName("dept_title_list")
    private List<DeptTitle> deptTitleList;
    /**
     * 扩展属性，可以设置多种属性，最大长度2000个字符。在使用该参数前，需要先在OA管理后台增加该属性，然后再调用接口进行赋值。详情请参见关于extension参数的使用。手机上最多只能显示10个扩展属性。该字段的值支持链接类型填写，同时链接支持变量通配符自动替换，目前支持通配符有：userid，corpid。示例：
     * [工位地址](http://www.dingtalk.com?userid=#userid#&corpid=#corpid#)。
     */
    @SerializedName("extension")
    private Map<String, String> extension;
    /**
     * 是否开启高管模式：
     *
     * <pre>
     * true：开启。开启后，手机号码对所有员工隐藏。普通员工无法对其发DING、发起钉钉免费商务电话。高管之间不受影响。
     * false：不开启。
     * </pre>
     */
    @SerializedName("senior_mode")
    private Boolean seniorMode;
    /**
     * 入职时间，Unix时间戳，单位毫秒
     */
    @SerializedName("hired_date")
    private Long hiredDate;
    /**
     * 登录邮箱. eg: test@xxx.com
     */
    @SerializedName("login_email")
    private String login_email;
    /**
     * 是否专属帐号。为true时，不能指定loginEmail或mobile）。
     */
    @SerializedName("exclusive_account")
    private Boolean exclusiveAccount;

    /**
     * 类DeptOrder的实现描述：员工在对应的部门中的排序
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 22:02:47
     * @version v2.3.8
     * @since JDK 1.8
     */
    @Data
    public class DeptOrder implements Serializable {

        /**
         * 部门ID
         */
        @SerializedName(value = "dept_id")
        private Long deptId;
        /**
         * 员工在部门中的排序
         */
        @SerializedName(value = "order")
        private Long order;
    }

    /**
     * 类DeptTitle的实现描述：员工在对应的部门中的职位
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 14:21:18
     * @version v2.3.8
     * @since JDK 1.8
     */
    @Data
    public class DeptTitle implements Serializable {

        /**
         * 部门ID
         */
        @SerializedName(value = "dept_id")
        private Long deptId;
        /**
         * 员工在部门中的职位
         */
        @SerializedName(value = "title")
        private String title;
    }

}
