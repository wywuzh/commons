/*
 * Copyright 2015-2021 the original author or authors.
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
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.github.wywuzh.commons.core.reflect.ReflectUtils;
import com.github.wywuzh.commons.core.poi.style.CustomCellWriteHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * 类EasyExcelUtils的实现描述：Alibaba EasyExcel工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-07-14 22:10:53
 * @version v2.2.6
 * @since JDK 1.8
 */
public class EasyExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelUtils.class);

    public static ExcelWriter createExcelWriter(String fileName) {
        return EasyExcelFactory.write(fileName).build();
    }

    public static ExcelWriter createExcelWriter(File destFile) {
        return EasyExcelFactory.write(destFile).build();
    }

    public static ExcelWriter createExcelWriter(File destFile, String templateFile) {
        return EasyExcelFactory.write(destFile).withTemplate(templateFile).build();
    }

    public static ExcelWriter createExcelWriter(File destFile, File templateFile) {
        return EasyExcelFactory.write(destFile).withTemplate(templateFile).build();
    }

    public static ExcelWriter createExcelWriter(File destFile, InputStream templateInputStream) {
        return EasyExcelFactory.write(destFile).withTemplate(templateInputStream).build();
    }

    public static <T> WriteSheet createWriteSheet(String sheetName, String[] columnTitles, Integer[] columnLengths) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 不显示背景颜色
        headWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
        // 内容水平居中 、垂直居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //内容超过宽度是否换行
        headWriteCellStyle.setWrapped(false);
        headWriteCellStyle.setLocked(true);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setColor(IndexedColors.BLACK.index);
        headWriteFont.setFontName("宋体");
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 不显示背景颜色
        contentWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
        // 内容水平居中 、垂直居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteFont.setColor(IndexedColors.BLACK.index);
        contentWriteFont.setFontName("宋体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName)
                .registerWriteHandler(new CustomCellWriteHandler(columnLengths))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .build();

        // 设置表头
        List<List<String>> head = new LinkedList<>();
        for (String columnTitle : columnTitles) {
            head.add(Collections.singletonList(columnTitle));
        }
        writeSheet.setHead(head);
        writeSheet.setIncludeColumnFiledNames(Arrays.asList(columnTitles));

        return writeSheet;
    }

    /**
     * 写入数据
     *
     * @param excelWriter
     * @param writeSheet
     * @param list
     * @param columns
     * @param <T>
     */
    public static <T> void writeData(ExcelWriter excelWriter, WriteSheet writeSheet, List<T> list, String[] columns) {
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
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            data.add(row);
        }
        excelWriter.write(data, writeSheet);
    }

    public static <T> void writer(String fileName, List<T> list, String[] columns, String[] columnTitles) {
        EasyExcelUtils.writer(fileName, "sheet", list, columns, columnTitles, null);
    }

    public static <T> void writer(String fileName, String sheetName, List<T> list, String[] columns, String[] columnTitles, Integer[] columnLengths) {
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet";
        }
        if (columnLengths == null) {
            columnLengths = new Integer[columnTitles.length];
            for (int j = 0; j < columnTitles.length; j++) {
                String columnComment = columnTitles[j];
                columnLengths[j] = columnComment.length() * 357;
            }
        }

        ExcelWriter excelWriter = EasyExcelUtils.createExcelWriter(fileName);

        WriteSheet writeSheet = EasyExcelUtils.createWriteSheet(sheetName, columnTitles, columnLengths);

        EasyExcelUtils.writeData(excelWriter, writeSheet, list, columns);
    }

}
