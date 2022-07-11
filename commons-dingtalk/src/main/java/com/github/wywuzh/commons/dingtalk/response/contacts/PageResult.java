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
package com.github.wywuzh.commons.dingtalk.response.contacts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类PageResult的实现描述：分页结果
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-11 15:31:06
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -6787596888901376911L;

    /**
     * 是否还有更多的数据
     */
    @SerializedName(value = "has_more")
    private Boolean hasMore;
    /**
     * 下一次分页的游标，如果has_more为false，表示没有更多的分页数据
     */
    @SerializedName(value = "next_cursor")
    private Long nextCursor;
    /**
     * 信息列表
     */
    @SerializedName(value = "list")
    private List<T> list;
}
