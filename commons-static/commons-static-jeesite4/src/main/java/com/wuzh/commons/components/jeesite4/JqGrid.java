/*
 * Copyright 2015-2019 the original author or authors.
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
package com.wuzh.commons.components.jeesite4;

import java.io.Serializable;
import java.util.List;

/**
 * 类JqGrid的实现描述：jqGrid
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-22 22:41:37
 * @version v2.2.0
 * @since JDK 1.8
 */
public class JqGrid<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 5713908442337752936L;

    /**
     * 页码（当前所处页号）。页码从1开始
     */
    private int pageNo = 1;
    /**
     * 每页显示数据量
     */
    private int pageSize = 20;
    /**
     * 行记录总数
     */
    private long count = 0;
    private int bothNum = 1;
    private int centerNum = 5;
    private String funcName = "page";
    private String funcParam;
    private String pageInfo;
    /**
     * 分页结果集
     */
    private List<T> list;

    public JqGrid() {
    }

    public JqGrid(int count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getBothNum() {
        return bothNum;
    }

    public void setBothNum(int bothNum) {
        this.bothNum = bothNum;
    }

    public int getCenterNum() {
        return centerNum;
    }

    public void setCenterNum(int centerNum) {
        this.centerNum = centerNum;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(String funcParam) {
        this.funcParam = funcParam;
    }

    public String getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
