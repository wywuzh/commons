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
import com.github.wywuzh.commons.core.web.request.BaseRequest;
import com.github.wywuzh.commons.dingtalk.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类UserListsimpleRequest的实现描述：获取部门用户基础信息请求条件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-10 16:59:53
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserListsimpleRequest extends BaseRequest {
    private static final long serialVersionUID = 2958886522615924202L;

    /**
     * 部门ID，根部门ID为1
     */
    @SerializedName(value = "dept_id")
    private Long deptId;
    /**
     * 分页查询的游标，最开始传0，后续传返回参数中的next_cursor值
     */
    @SerializedName(value = "cursor")
    private Long cursor = 0L;
    /**
     * 分页长度
     */
    @SerializedName(value = "size")
    private Long size = 10L;
    /**
     * 部门成员的排序规则：
     * <pre>
     * <strong>entry_asc</strong>：代表按照进入部门的时间升序。
     * <strong>entry_desc</strong>：代表按照进入部门的时间降序。
     * <strong>modify_asc</strong>：代表按照部门信息修改时间升序。
     * <strong>modify_desc</strong>：代表按照部门信息修改时间降序。
     * <strong>custom</strong>：代表用户定义(未定义时按照拼音)排序。默认值
     * </pre>
     */
    @SerializedName(value = "order_field")
    private String orderField = OrderField.custom.getOrder();
    /**
     * 是否返回访问受限的员工
     */
    @SerializedName(value = "contain_access_limit")
    private Boolean containAccessLimit;
    /**
     * 通讯录语言
     * <pre>
     * zh_CN：中文（默认值）。
     * en_US：英文。
     * </pre>
     */
    @SerializedName(value = "language")
    private String language = Language.zh_CN.getLang();


    /**
     * 排序规则
     */
    public static enum OrderField {
        entry_asc("entry_asc", "按照进入部门的时间升序"),
        entry_desc("entry_desc", "按照进入部门的时间降序"),
        modify_asc("modify_asc", "按照部门信息修改时间升序"),
        modify_desc("modify_desc", "按照部门信息修改时间降序"),
        custom("custom", "用户定义(未定义时按照拼音)排序"),
        ;
        private String order;
        private String desc;

        OrderField(String order, String desc) {
            this.order = order;
            this.desc = desc;
        }

        public String getOrder() {
            return order;
        }

        public String getDesc() {
            return desc;
        }
    }
}
