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
package com.github.wywuzh.commons.core.poi.style;

import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 类CustomFreezeRowColHandler的实现描述：冻结行列
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-07-11 12:38:38
 * @version v2.6.0
 * @since JDK 1.8
 */
public class CustomFreezeRowColHandler extends AbstractSheetWriteHandler {

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

    public CustomFreezeRowColHandler() {
        this(0, 1, 0, 1);
    }

    public CustomFreezeRowColHandler(int colSplit, int rowSplit) {
        this(colSplit, rowSplit, colSplit, rowSplit);
    }

    public CustomFreezeRowColHandler(int colSplit, int rowSplit, int leftmostColumn, int topRow) {
        this.colSplit = colSplit;
        this.rowSplit = rowSplit;
        this.leftmostColumn = leftmostColumn;
        this.topRow = topRow;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();

        // 冻结行列
        sheet.createFreezePane(colSplit, rowSplit, leftmostColumn, topRow);
    }
}
