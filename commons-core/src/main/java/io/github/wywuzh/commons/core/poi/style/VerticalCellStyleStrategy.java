/*
 * Copyright 2015-2026 the original author or authors.
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
package io.github.wywuzh.commons.core.poi.style;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import io.github.wywuzh.commons.core.poi.EasyExcelUtils;
import io.github.wywuzh.commons.core.poi.ExcelUtils;
import io.github.wywuzh.commons.core.poi.modle.ExcelCellField;

/**
 * 类VerticalCellStyleStrategy的实现描述：自定义列样式
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2023-12-18 16:50:39
 * @version v2.7.10
 * @since JDK 1.8
 */
public class VerticalCellStyleStrategy extends AbstractVerticalCellStyleStrategy {

    /**
     * 标题栏的列样式
     */
    final WriteCellStyle defaultHeadWriteCellStyle = EasyExcelUtils.createHeadWriteCellStyle();
    /**
     * 表格内容的列样式
     */
    final WriteCellStyle defaultContentWriteCellStyle = EasyExcelUtils.createContentWriteCellStyle();

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
     * 必填字段
     */
    private List<String> requiredColumnTitles;
    /**
     * 列字段格式：key=字段标题, value=ExcelCellField
     */
    private Map<String, ExcelCellField> fieldTitleExcelCellMap;
    /**
     * 列样式：key=列索引(从0开始), value=列样式
     *
     * @since v4.0.0
     */
    private Map<Integer, CellStyle> columnStyleMap;

    public VerticalCellStyleStrategy() {
    }

    public VerticalCellStyleStrategy(String[] columns, String[] columnTitles, Integer[] columnLengths, List<String> requiredColumnTitles, Map<String, ExcelCellField> fieldTitleExcelCellMap) {
        this.columns = columns;
        this.columnTitles = columnTitles;
        this.columnLengths = columnLengths;
        this.requiredColumnTitles = requiredColumnTitles;
        this.fieldTitleExcelCellMap = fieldTitleExcelCellMap;
    }

    public VerticalCellStyleStrategy(String[] columns, String[] columnTitles, Integer[] columnLengths, List<String> requiredColumnTitles, Map<String, ExcelCellField> fieldTitleExcelCellMap,
            Map<Integer, CellStyle> columnStyleMap) {
        this.columns = columns;
        this.columnTitles = columnTitles;
        this.columnLengths = columnLengths;
        this.requiredColumnTitles = requiredColumnTitles;
        this.fieldTitleExcelCellMap = fieldTitleExcelCellMap;
        this.columnStyleMap = columnStyleMap;
    }

    // 设置列默认样式
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = sheet.getWorkbook();
        Integer columnIndex = head.getColumnIndex();
        CellStyle columnStyle = sheet.getColumnStyle(columnIndex);
        if (columnStyle != null) {
            // 已经设置过样式，不需要重复设置
            return;
        }

        // 列样式优先级：columnStyleMap > fieldTitleExcelCellMap > CellStyleTools#createContentStyle(workbook)

        // 1、columnStyleMap列样式
        // tips：该方式交给调用方完全自定义，不做任何加工
        if (MapUtils.isNotEmpty(columnStyleMap) && columnStyleMap.containsKey(columnIndex)) {
            CellStyle cellStyle = columnStyleMap.get(columnIndex);
            // 设置列默认样式
            sheet.setDefaultColumnStyle(columnIndex, cellStyle);
            return;
        }

        // 2、fieldTitleExcelCellMap列样式
        // tips：仅支持设置单元格的Format格式
        ExcelCellField excelCellField = null;
        if (fieldTitleExcelCellMap != null) {
            String headerName = StringUtils.replace(head.getHeadNameList().get(0), "*", "");
            excelCellField = fieldTitleExcelCellMap.get(headerName);
        }
        if (excelCellField != null) {
            // 列格式：优先format，没有再根据cellType找到对应的format
            String format = Optional.ofNullable(excelCellField.getFormat()).orElse(ExcelUtils.getFormat(excelCellField.getCellType()));

            CellStyle cellStyle = CellStyleTools.createContentStyle(workbook);
            cellStyle.setDataFormat(ExcelUtils.getDateFormat(workbook, format));
            // 设置列默认样式
            sheet.setDefaultColumnStyle(columnIndex, cellStyle);
            return;
        }

        // 3、CellStyleTools#createContentStyle(workbook)列样式
        CellStyle cellStyle = CellStyleTools.createContentStyle(workbook);
        // 设置列默认样式
        sheet.setDefaultColumnStyle(columnIndex, cellStyle);
    }

    // 设置标题栏的列样式
    @Override
    protected WriteCellStyle headCellStyle(Head head) {
        String headerName = StringUtils.replace(head.getHeadNameList().get(0), "*", "");
        if (CollectionUtils.isNotEmpty(requiredColumnTitles) && requiredColumnTitles.contains(headerName)) {
            WriteCellStyle headWriteCellStyle = EasyExcelUtils.createHeadWriteCellStyle();
            // 必填项
            headWriteCellStyle.getWriteFont().setColor(IndexedColors.RED.index);
            return headWriteCellStyle;
        }
        return defaultHeadWriteCellStyle;
    }

    // 设置表格内容的列样式
    @Override
    protected WriteCellStyle contentCellStyle(Head head) {
        return defaultContentWriteCellStyle;
    }

    // 设置表格内容的列样式
    @Override
    protected WriteCellStyle contentCellStyle(CellWriteHandlerContext context) {
        // 内容列样式优先级：columnStyleMap > fieldTitleExcelCellMap > defaultContentWriteCellStyle

        // 1、columnStyleMap列样式
        Integer columnIndex = context.getHeadData().getColumnIndex();
        if (MapUtils.isNotEmpty(columnStyleMap) && columnStyleMap.containsKey(columnIndex)) {
            Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
            Sheet sheet = context.getWriteSheetHolder().getSheet();

            CellStyle cellStyle = columnStyleMap.get(columnIndex);
            // 设置列默认样式
            sheet.setDefaultColumnStyle(columnIndex, cellStyle);

            // 将POI CellStyle的全部样式属性复制到EasyExcel WriteCellStyle
            return EasyExcelUtils.convertCellStyle(workbook, cellStyle);
        }

        // 2、fieldTitleExcelCellMap列样式
        ExcelCellField excelCellField = null;
        if (fieldTitleExcelCellMap != null) {
            String headerName = StringUtils.replace(context.getHeadData().getHeadNameList().get(0), "*", "");
            excelCellField = fieldTitleExcelCellMap.get(headerName);
        }
        if (excelCellField != null) {
            WriteCellStyle contentWriteCellStyle = EasyExcelUtils.createContentWriteCellStyle();
            DataFormatData dataFormatData = new DataFormatData();
            // 列格式：优先format，没有再根据cellType找到对应的format
            String format = Optional.ofNullable(excelCellField.getFormat()).orElse(ExcelUtils.getFormat(excelCellField.getCellType()));
            dataFormatData.setFormat(format);
            contentWriteCellStyle.setDataFormatData(dataFormatData);
            return contentWriteCellStyle;
        }

        // 3、defaultContentWriteCellStyle列样式
        return defaultContentWriteCellStyle;
    }
}
