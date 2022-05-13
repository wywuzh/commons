/*
 * Copyright 2015-2022 the original author or authors.
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
package com.wuzh.commons.pager;

import java.io.Serializable;

/**
 * 类Sort.java的实现描述：排序
 * 
 * @author 伍章红 2015年12月4日 上午9:20:16
 * @version v2.0.2
 * @since JDK 1.8
 */
public class Sort implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 排序字段名
     */
    private String sort;
    /**
     * 排序类型
     */
    private Order order;

    public Sort() {
        super();
    }

    public Sort(String sort, Order order) {
        super();
        this.sort = sort;
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
