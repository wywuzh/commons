/*
 * Copyright 2015-2024 the original author or authors.
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

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.github.wywuzh.commons.core.poi.ExcelUtils;
import com.github.wywuzh.commons.core.poi.enums.CellTypeEnum;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 类SheetValidationWriteHandler的实现描述：字段有效性验证(下拉列表)
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2023-12-18 16:18:24
 * @version v2.7.8
 * @since JDK 1.8
 */
public class SheetValidationWriteHandler implements SheetWriteHandler {
    /**
     * 列名/字段名
     */
    private String[] columns;
    /**
     * 列的标题
     */
    private String[] columnTitles;
    /**
     * 列的长度
     */
    private Integer[] columnLengths;

    /**
     * 字段有效性验证：key=column字段，value=column字段下拉框列表数据（验证数据）
     */
    private Map<String, String[]> columnValidation;

    public SheetValidationWriteHandler() {
    }

    public SheetValidationWriteHandler(String[] columns, String[] columnTitles, Integer[] columnLengths, Map<String, String[]> columnValidation) {
        this.columns = columns;
        this.columnTitles = columnTitles;
        this.columnLengths = columnLengths;
        this.columnValidation = columnValidation;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 获取工作簿对象，用于创建存放下拉数据的字典sheet数据页
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        // 获取第一个sheet页
        Sheet sheet = writeSheetHolder.getCachedSheet();

        for (int j = 0; j < columnTitles.length; j++) {
            int titleIndex = j;
            // 生成第j列 - 单元格
            String columnComment = columnTitles[j];

            // 下拉框
            if (columnValidation != null && columnValidation.containsKey(columnComment)) {
                // 注意：org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint.XSSFDataValidationConstraint(java.lang.String[])要求传入的数组不能为空，这里在入口进行控制
                String[] columnValidationData = columnValidation.get(columnComment);
                if (columnValidationData == null || columnValidationData.length == 0) {
                    continue;
                }
                // 设置为下拉框
                ExcelUtils.setValidation(workbook, sheet, columnComment, columnValidationData, 1, ExcelUtils.MAX_ROW, titleIndex, titleIndex);

                // 默认该列为为文本格式
                CellStyle cellStyleForText = CellStyleTools.createCellStyle(workbook);
                cellStyleForText.setDataFormat(ExcelUtils.getCellDateFormat(workbook, CellTypeEnum.String));
                sheet.setDefaultColumnStyle(titleIndex, cellStyleForText);
            }
        }

    }

}
