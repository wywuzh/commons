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
package io.github.wywuzh.commons.core.poi.modle;

import java.io.Serializable;

/**
 * 类FreezePane的实现描述：冻结窗格
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2023-12-18 15:36:51
 * @version v2.7.8
 * @since JDK 1.8
 */
public class FreezePane implements Serializable {
    private static final long serialVersionUID = 2481719881902669335L;

    /**
     * 要冻结的列，从0开始
     */
    private Integer colSplit;
    /**
     * 要冻结的行，从0开始
     */
    private Integer rowSplit;
    /**
     * 右边区域[可见]的首列序号，从0开始
     */
    private Integer leftmostColumn;
    /**
     * 下边区域[可见]的首行序号，从0开始
     */
    private Integer topRow;

    public FreezePane() {
        this(0, 1, 0, 1);
    }

    public FreezePane(int colSplit, int rowSplit) {
        this(colSplit, rowSplit, colSplit, rowSplit);
    }

    public FreezePane(Integer colSplit, Integer rowSplit, Integer leftmostColumn, Integer topRow) {
        this.colSplit = colSplit;
        this.rowSplit = rowSplit;
        this.leftmostColumn = leftmostColumn;
        this.topRow = topRow;
    }

    public Integer getColSplit() {
        return colSplit;
    }

    public void setColSplit(Integer colSplit) {
        this.colSplit = colSplit;
    }

    public Integer getRowSplit() {
        return rowSplit;
    }

    public void setRowSplit(Integer rowSplit) {
        this.rowSplit = rowSplit;
    }

    public Integer getLeftmostColumn() {
        return leftmostColumn;
    }

    public void setLeftmostColumn(Integer leftmostColumn) {
        this.leftmostColumn = leftmostColumn;
    }

    public Integer getTopRow() {
        return topRow;
    }

    public void setTopRow(Integer topRow) {
        this.topRow = topRow;
    }
}
