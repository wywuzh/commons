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
package com.github.wywuzh.commons.pager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类PaginationObject.java的实现描述：分页结果对象
 *
 * @author 伍章红 2015年11月6日 上午10:21:35
 * @version v2.0.2
 * @since JDK 1.8
 */
public class PaginationObject<R extends Serializable, P extends Serializable> extends PaginationParameter<P> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 行记录总数
     */
    public long rowCount;
    /**
     * 分页结果集
     */
    public List<R> resultList = new ArrayList<R>();

    public PaginationObject() {
        super();
    }

    public PaginationObject(long rowCount, List<R> resultList) {
        super();
        this.rowCount = rowCount;
        this.resultList = resultList;
    }

    @Override
    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public List<R> getResultList() {
        return resultList;
    }

    public void setResultList(List<R> resultList) {
        this.resultList = resultList;
    }

}
