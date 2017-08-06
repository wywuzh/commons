/*
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类PaginationObject.java的实现描述：分页结果对象
 * 
 * @author 伍章红 2015年11月6日 上午10:21:35
 * @version v1.0.0
 * @since JDK 1.7
 */
public class PaginationObject<E extends Serializable, V extends Serializable> extends PaginationParamter<V>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 行记录总数
     */
    public long rowCount;
    /**
     * 分页结果集
     */
    public List<E> resultList = new ArrayList<E>();

    public PaginationObject() {
        super();
    }

    public PaginationObject(long rowCount, List<E> resultList) {
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

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
