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
import java.util.List;

/**
 * 类PageInfo.java的实现描述：分页信息
 *
 * <pre>
 * MySQL分页语法：limit m,n。m代表分页开始行（从0开始），n代表每页要查询的数据量
 * </pre>
 *
 * @author 伍章红 2015年11月6日 上午10:53:48
 * @version v2.0.2
 * @since JDK 1.87
 */
public abstract class PageImpl implements Page, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码（当前所处页号）。页码从1开始
     */
    private int pageNo;
    /**
     * 每页显示数据量
     */
    private int pageSize;

    /**
     * 行记录总数
     */
    // private long rowCount;
    public PageImpl() {
        super();
    }

    public PageImpl(int pageNo, int pageSize) {
        super();
        if (pageNo < 1) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }

        if (pageSize < 1) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    @Override
    public int getPageNo() {
        if (this.pageNo < 1) {
            this.pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public int getPageSize() {
        if (this.pageSize < 1) {
            this.pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageTotal() {
        // 计算分页页码总数共分两种算法：
        // 1.pageTotal=rowCount%pageSize==0?rowCount%pageSize:rowCount%pageSize+1
        // 2.pageTotal = (rowCount-1) / pageSize + 1

        return (int) ((getRowCount() - 1) / getPageSize() + 1);
    }

    @Override
    public abstract long getRowCount();

    @Override
    public int getOffSet() {
        // 当前页开始行
        return (getPageNo() - 1) * getPageSize();
    }

    @Override
    public int getEndSet() {
        // 当前页结束行
        return getOffSet() + getPageSize();
    }

    @Override
    public boolean isFirstPage() {
        return getPageNo() <= 1;
    }

    @Override
    public boolean isLastPage() {
        return getPageNo() >= getPageTotal();
    }

    @Override
    public boolean hasPreviousPage() {
        return !isFirstPage();
    }

    @Override
    public boolean hasNextPage() {
        return !isLastPage();
    }

    @Override
    public abstract List<Sort> getSorts();

}
