/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.poi;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.github.wywuzh.commons.core.poi.modle.ExcelCellField;
import com.github.wywuzh.commons.core.poi.modle.ExcelExportRequest;
import com.github.wywuzh.commons.core.poi.modle.FreezePane;
import com.github.wywuzh.commons.core.poi.style.SheetFreezePaneWriteHandler;
import com.github.wywuzh.commons.core.poi.style.SheetValidationWriteHandler;
import com.github.wywuzh.commons.core.poi.style.VerticalCellStyleStrategy;
import com.github.wywuzh.commons.core.poi.style.column.ColumnWidthAdaptiveStyleStrategy;
import com.github.wywuzh.commons.core.reflect.ReflectUtils;
import com.github.wywuzh.commons.core.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类EasyExcelUtils的实现描述：Alibaba EasyExcel工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-07-14 22:10:53
 * @version v2.2.6
 * @since JDK 1.8
 */
public class EasyExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelUtils.class);

    /**
     * 根据目标文件路径名创建ExcelWriter对象
     *
     * @param filePath 目标文件路径名
     * @return
     */
    public static ExcelWriter createExcelWriter(String filePath) {
        return EasyExcelFactory.write(filePath).build();
    }

    /**
     * 根据目标文件创建ExcelWriter对象
     *
     * @param destFile 目标文件
     * @return
     */
    public static ExcelWriter createExcelWriter(File destFile) {
        return EasyExcelFactory.write(destFile).build();
    }

    /**
     * 根据目标文件输出流创建ExcelWriter对象
     *
     * @param outputStream 目标文件输出流
     * @return
     */
    public static ExcelWriter createExcelWriter(OutputStream outputStream) {
        return EasyExcelFactory.write(outputStream).build();
    }

    /**
     * 根据目标文件、模板文件创建ExcelWriter对象
     *
     * @param destFile     目标文件
     * @param templateFile 模板文件
     * @return
     */
    public static ExcelWriter createExcelWriter(File destFile, String templateFile) {
        return EasyExcelFactory.write(destFile).withTemplate(templateFile).build();
    }

    /**
     * 根据目标文件、模板文件创建ExcelWriter对象
     *
     * @param destFile     目标文件
     * @param templateFile 模板文件
     * @return
     */
    public static ExcelWriter createExcelWriter(File destFile, File templateFile) {
        return EasyExcelFactory.write(destFile).withTemplate(templateFile).build();
    }

    /**
     * 根据目标文件、模板文件创建ExcelWriter对象
     *
     * @param destFile            目标文件
     * @param templateInputStream 模板文件输入流
     * @return
     */
    public static ExcelWriter createExcelWriter(File destFile, InputStream templateInputStream) {
        return EasyExcelFactory.write(destFile).withTemplate(templateInputStream).build();
    }

    /**
     * 创建WriteSheet对象
     *
     * @param sheetName     Sheet页名
     * @param columnTitles  列的标题
     * @param columnLengths 列的长度
     * @return
     * @deprecated 已废弃，请使用 {@link #createWriteSheet(ExcelExportRequest)} 方法
     */
    @Deprecated
    public static <T> WriteSheet createWriteSheet(String sheetName, String[] columnTitles, Integer[] columnLengths) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = createHeadWriteCellStyle();

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = createContentWriteCellStyle();

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName).registerWriteHandler(new ColumnWidthAdaptiveStyleStrategy(columnLengths)).registerWriteHandler(horizontalCellStyleStrategy)
                .build();

        // 设置表头
        List<List<String>> head = new LinkedList<>();
        for (String columnTitle : columnTitles) {
            head.add(Collections.singletonList(columnTitle));
        }
        writeSheet.setHead(head);
        writeSheet.setIncludeColumnFieldNames(Arrays.asList(columnTitles));

        return writeSheet;
    }

    /**
     * 创建WriteSheet对象
     *
     * @param excelExportRequest 导出请求
     * @return
     * @since v2.7.8
     */
    public static WriteSheet createWriteSheet(ExcelExportRequest excelExportRequest) {
        Assert.notNull(excelExportRequest, "excelExportRequest参数不能为空");
        Assert.notEmpty(excelExportRequest.getColumns(), "excelExportRequest.columns参数不能为空");
        Assert.notEmpty(excelExportRequest.getColumnTitles(), "excelExportRequest.columnTitles参数不能为空");

        // sheetName传入为空时，默认为“sheet”
        String sheetName = Optional.ofNullable(excelExportRequest.getSheetName()).orElse("sheet");
        // 列名/字段名
        String[] columns = excelExportRequest.getColumns();
        // 列的标题
        String[] columnTitles = excelExportRequest.getColumnTitles();
        // 列的长度
        Integer[] columnLengths = excelExportRequest.getColumnLengths();
        // 必填字段
        List<String> requiredColumnTitles = excelExportRequest.getRequiredColumnTitles();
        // 冻结窗格
        FreezePane freezePane = Optional.ofNullable(excelExportRequest.getFreezePane()).orElseGet(FreezePane::new);
        // 字段有效性验证：key=column字段，value=column字段下拉框列表数据（验证数据）
        Map<String, String[]> columnValidation = excelExportRequest.getColumnValidation();
        // 列字段格式：key=字段标题, value=ExcelCellField
        Map<String, ExcelCellField> fieldTitleExcelCellMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(excelExportRequest.getDataColl())) {
            Object data = excelExportRequest.getDataColl().iterator().next();
            List<ExcelCellField> excelCellFieldList = ExcelUtils.resolvedExcelCellField(data.getClass());
            if (CollectionUtils.isNotEmpty(excelCellFieldList)) {
                for (ExcelCellField excelCellField : excelCellFieldList) {
                    fieldTitleExcelCellMap.put(excelCellField.getFieldTitle(), excelCellField);
                }
            }
        }

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcelFactory.writerSheet(sheetName)
                // 冻结行列
                .registerWriteHandler(new SheetFreezePaneWriteHandler(freezePane.getColSplit(), freezePane.getRowSplit(), freezePane.getLeftmostColumn(), freezePane.getTopRow()))
                // 字段有效性验证(下拉列表)
                .registerWriteHandler(new SheetValidationWriteHandler(columns, columnTitles, columnLengths, columnValidation))
                // 自适应列宽
                .registerWriteHandler(new ColumnWidthAdaptiveStyleStrategy(columnLengths))
                // 自定义列样式
                .registerWriteHandler(new VerticalCellStyleStrategy(columns, columnTitles, columnLengths, requiredColumnTitles, fieldTitleExcelCellMap));
        WriteSheet writeSheet = excelWriterSheetBuilder.build();

        // 设置表头
        List<List<String>> head = new LinkedList<>();
        for (String columnTitle : columnTitles) {
            head.add(Collections.singletonList(columnTitle));
        }
        writeSheet.setHead(head);
        writeSheet.setIncludeColumnFieldNames(Arrays.asList(columnTitles));

        return writeSheet;
    }

    /**
     * @return 标题栏的列样式
     * @since v2.7.8
     */
    public static WriteCellStyle createHeadWriteCellStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置边框样式：无框线
        headWriteCellStyle.setBorderTop(BorderStyle.NONE);
        headWriteCellStyle.setBorderRight(BorderStyle.NONE);
        headWriteCellStyle.setBorderBottom(BorderStyle.NONE);
        headWriteCellStyle.setBorderLeft(BorderStyle.NONE);
        // 不显示背景颜色
        headWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
        // 内容水平居中 、垂直居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 内容超过宽度是否换行
        headWriteCellStyle.setWrapped(false);
        headWriteCellStyle.setLocked(true);
        // 表头行字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setColor(IndexedColors.BLACK.index);
        headWriteFont.setFontName("宋体");
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);

        return headWriteCellStyle;
    }

    /**
     * @return 表格内容的列样式
     * @since v2.7.8
     */
    public static WriteCellStyle createContentWriteCellStyle() {
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置边框样式：无框线
        contentWriteCellStyle.setBorderTop(BorderStyle.NONE);
        contentWriteCellStyle.setBorderRight(BorderStyle.NONE);
        contentWriteCellStyle.setBorderBottom(BorderStyle.NONE);
        contentWriteCellStyle.setBorderLeft(BorderStyle.NONE);
        // 不显示背景颜色
        contentWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
        // 内容水平居中 、垂直居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 内容行字体
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteFont.setColor(IndexedColors.BLACK.index);
        contentWriteFont.setFontName("宋体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        return contentWriteCellStyle;
    }

    /**
     * 写入数据
     *
     * @param excelWriter ExcelWriter对象
     * @param writeSheet  WriteSheet对象
     * @param list        数据
     * @param columns     列名/字段名
     */
    public static <T> void writeData(ExcelWriter excelWriter, WriteSheet writeSheet, List<T> list, String[] columns) {
        excelWriter.writeContext().writeWorkbookHolder();

        List<List<Object>> data = new LinkedList<>();
        for (T item : list) {
            List<Object> row = new LinkedList<>();
            if (item instanceof Map) {
                for (String column : columns) {
                    row.add(((Map) item).get(column));
                }
            } else {
                for (String column : columns) {
                    try {
                        Object value = ReflectUtils.getValue(item, column);
                        row.add(value);
                    } catch (IllegalAccessException e) {
                        LOGGER.error("class={}, column={} 字段取值有误：", item.getClass(), column, e);
                    }
                }
            }
            data.add(row);
        }
        excelWriter.write(data, writeSheet);
    }

    /**
     * 将数据写出到目标文件
     *
     * @param destFile     目标文件
     * @param list         数据
     * @param columns      列名/字段名
     * @param columnTitles 列的标题
     */
    public static <T> void writer(File destFile, List<T> list, String[] columns, String[] columnTitles) {
        EasyExcelUtils.writer(destFile, "sheet", list, columns, columnTitles, null);
    }

    /**
     * 将数据写出到目标文件
     *
     * @param destFile      目标文件
     * @param sheetName     sheet名
     * @param list          数据
     * @param columns       列名/字段名
     * @param columnTitles  列的标题
     * @param columnLengths 列的长度
     */
    public static <T> void writer(File destFile, String sheetName, List<T> list, String[] columns, String[] columnTitles, Integer[] columnLengths) {
        // sheetName传入为空时，默认为“sheet”
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet";
        }
        // 列的长度
        if (columnLengths == null) {
            columnLengths = new Integer[columnTitles.length];
            for (int j = 0; j < columnTitles.length; j++) {
                String columnComment = columnTitles[j];
                columnLengths[j] = columnComment.length() * 357;
            }
        }

        // 1. 创建ExcelWriter对象
        ExcelWriter excelWriter = EasyExcelUtils.createExcelWriter(destFile);

        // 2. 创建WriteSheet对象
        ExcelExportRequest excelExportRequest = new ExcelExportRequest();
        excelExportRequest.setSheetName(sheetName);
        excelExportRequest.setColumns(columns);
        excelExportRequest.setColumnTitles(columnTitles);
        excelExportRequest.setColumnLengths(columnLengths);
        excelExportRequest.setDataColl(list);
        WriteSheet writeSheet = EasyExcelUtils.createWriteSheet(excelExportRequest);

        // 3. 写入数据
        EasyExcelUtils.writeData(excelWriter, writeSheet, list, columns);

        // 4. 关闭IO
        excelWriter.finish();
    }

}
