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

/**
 * 类DeptListParentByUser的实现描述：获取指定用户的所有父部门列表
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-31 20:52:56
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class DeptListParentByUser implements Serializable {
    private static final long serialVersionUID = -3808409834439675788L;

    /**
     * 父部门列表集合
     */
    @SerializedName(value = "parent_list")
    private List<DeptParent> parentList;

    @Data
    public static class DeptParent implements Serializable {

        /**
         * 父部门列表
         */
        @SerializedName(value = "parent_dept_id_list")
        private List<Long> parentDeptIdList;
    }
}
