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
package com.wuzh.commons.components.easyui.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类ComboGrid的实现描述：下拉表格
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2018/12/27 22:18
 * @version v2.1.1
 * @since JDK 1.8
 */
public class ComboGrid<T> implements Serializable {
    private static final long serialVersionUID = 1400606840645509781L;

    /**
     * 数据总行数
     */
    private Long total;
    /**
     * 查询记录数
     */
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
