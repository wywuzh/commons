/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.core.poi;

import com.wuzh.commons.core.poi.excel.CellType;
import com.wuzh.commons.core.poi.excel.ExcelCell;
import com.wuzh.commons.core.poi.excel.ExcelRequest;
import com.wuzh.commons.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类ExcelUtils的实现描述：Excel 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-05-29 10:54:36
 * @version v2.2.6
 * @since JDK 1.8
 */
public class ExcelUtils {

    public static final int MAX_ROW = 50000;

    public static void main(String[] args) {
        String[] columns = {"brand", "shopNo", "shopFullName", "afeeLaborAgencyExpenses"};
        String[] columnTitles = {"品牌", "店铺编码", "店铺全称", "A劳务中介费"};
        Integer[] columnLengths = {100, 125, 287, 150};
        List<String> requiredColumnTitles = Arrays.asList("品牌", "店铺编码", "店铺全称");
        String fileName = "统计基础表.xlsx";
        List<Map<String, Object>> dataColl = new LinkedList<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("brand", "BLACK by moussy");
        dataMap.put("shopNo", "BBJ101");
        dataMap.put("shopFullName", "北京三里屯太古里店MOU");
        dataMap.put("afeeLaborAgencyExpenses", BigDecimal.ZERO);
        dataColl.add(dataMap);
        Map<String, Object> dataMap1 = new HashMap<>();
        dataMap1.put("brand", "moussy");
        dataMap1.put("shopNo", "BBJ101");
        dataMap1.put("shopFullName", "北京三里屯太古里店MOU");
        dataMap1.put("afeeLaborAgencyExpenses", BigDecimal.ZERO);
        dataColl.add(dataMap1);

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setColumns(columns);
        excelRequest.setColumnTitles(columnTitles);
        excelRequest.setColumnLengths(columnLengths);
        excelRequest.setRequiredColumnTitles(requiredColumnTitles);
        excelRequest.setDataColl(dataColl);

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File("D:\\data", fileName));
            Workbook workbook = createWorkbook(excelRequest, fileName);
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出数据
     *
     * @param request 请求信息
     */
    public static void exportData(HttpServletRequest request, HttpServletResponse response, ExcelRequest excelRequest, String fileName)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        OutputStream outputStream = response.getOutputStream();
        response.reset();
        // 获取浏览器类型
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        response.setContentType("application/vnd.ms-excel");
        if (agent.contains("firefox")) {
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
        } else {
            String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        }

        Workbook workbook = createWorkbook(excelRequest, fileName);
        workbook.write(outputStream);
        outputStream.close();
    }

    public static Workbook createWorkbook(ExcelRequest excelRequest, String fileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Assert.notNull(excelRequest, "excelRequest must not be null");
        Assert.notEmpty(excelRequest.getColumns(), "columns must not be empty");
        Assert.notEmpty(excelRequest.getColumnTitles(), "columnTitles must not be empty");

        // 检查文件名后缀是否为“.xlsx”
        boolean xssf = StringUtils.endsWithIgnoreCase(fileName, ".xlsx") ? true : false;
        Workbook workbook = WorkbookFactory.create(xssf);
        createSheet(workbook, excelRequest);
        return workbook;
    }

    public static Sheet createSheet(Workbook workbook, ExcelRequest excelRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Sheet sheet = workbook.createSheet(excelRequest.getSheetName() == null ? "sheet" : excelRequest.getSheetName());

        // 生成头部列(单元格)样式
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle requiredHeaderStyle = null;
        // 生成内容列(单元格)样式
        CellStyle cellStyle = createContentStyle(workbook);

        String[] columns = excelRequest.getColumns();
        String[] columnTitles = excelRequest.getColumnTitles();
        Integer[] columnLengths = excelRequest.getColumnLengths();
        List<String> requiredColumnTitles = excelRequest.getRequiredColumnTitles();
        if (CollectionUtils.isNotEmpty(requiredColumnTitles)) {
            requiredHeaderStyle = createRequiredHeaderStyle(workbook);
        }

        // 创建第一行（表头）
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(20);

        // 因为POI自动列宽计算的是String.length长度，在中文环境下会有问题，所以自行处理
        int[] maxLength = new int[columnTitles.length];

        for (int j = 0; j < columnTitles.length; j++) {
            // 生成第j列 - 单元格
            Cell cell = headerRow.createCell(j);
            String columnComment = columnTitles[j];

            if (CollectionUtils.isNotEmpty(requiredColumnTitles) && requiredColumnTitles.contains(columnComment)) {
                //必填项
                cell.setCellValue("*" + columnComment);
                cell.setCellStyle(requiredHeaderStyle);
            } else {
                cell.setCellStyle(headerStyle);
                cell.setCellValue(columnComment);
            }

            if (columnLengths != null && columnLengths.length > 0) {
                maxLength[j] = columnLengths[j] * 30;
            } else {
                // 设置列宽
                maxLength[j] = stringRealLength(columnComment) * 357;
            }
        }

        // 冻结窗口-首行
        sheet.createFreezePane(0, 1);

        // 输入值
        if (excelRequest.getDataColl() != null) {
            Iterator<?> iterator = excelRequest.getDataColl().iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Object data = iterator.next();
                if (data == null) {
                    continue;
                }

                // 创建行，从第二行开始
                Row sheetRow = sheet.createRow(index + 1);
                sheetRow.setHeightInPoints(20);

                for (int k = 0; k < columns.length; k++) {
                    // 生成第k列 - 单元格
                    Cell cell = sheetRow.createCell(k);
                    String columnName = columns[k];

                    Object realValue = getRealValue(data, columnName);
                    setCellValue(cell, realValue);
                    setCellStyle(workbook, cell, cellStyle, data, columnName);

                    if (columnLengths != null && columnLengths.length > 0) {
                        maxLength[k] = columnLengths[k] * 30;
                    } else {
                        // 设置列宽
                        int length = stringRealLength(realValue.toString()) * 357;
                        maxLength[k] = Math.max(length, maxLength[k]);
                    }
                }

                index++;
            }
        }

        for (int j = 0; j < maxLength.length; j++) {
            sheet.setColumnWidth(j, maxLength[j]);// 每个字符大约10像素
        }

        return sheet;
    }

    private static void setCellStyle(Workbook workbook, Cell cell, CellStyle cellStyle, Object data, String columnName) {
        cellStyle.setDataFormat(getCellDateFormat(workbook, data, columnName));
        cell.setCellStyle(cellStyle);
    }

    private static short getCellDateFormat(Workbook workbook, Object data, String columnName) {
        // 取到columnName对应的实际字段/方法
        Object realField = getRealField(data, columnName);
        Short dataFormat = -1;
        if (realField != null && realField.getClass().isAnnotationPresent(ExcelCell.class)) {
            dataFormat = getCellDateFormat(workbook, realField);
        }
        return dataFormat;
    }

    private static short getCellDateFormat(Workbook workbook, Object realField) {
        ExcelCell excelCell = realField.getClass().getAnnotation(ExcelCell.class);
        if (excelCell == null) {
            return -1;
        }
        Short dataFormat = getCellDateFormat(workbook, excelCell);
        if (dataFormat != null) {
            return dataFormat;
        }
        return -1;
    }

    private static short getCellDateFormat(Workbook workbook, ExcelCell excelCell) {
        com.wuzh.commons.core.poi.excel.CellType cellType = excelCell.cellType();
        if (com.wuzh.commons.core.poi.excel.CellType.String.equals(cellType)) { // 字符串文本
            DataFormat dataFormat = workbook.createDataFormat();
            return dataFormat.getFormat("TEXT");
        } else if (com.wuzh.commons.core.poi.excel.CellType.Percent.equals(cellType)) { // 百分比
            DataFormat dataFormat = workbook.createDataFormat();
            return dataFormat.getFormat("0.00%");
        } else if (com.wuzh.commons.core.poi.excel.CellType.Date.equals(cellType)) { // 日期
            DataFormat dataFormat = workbook.createDataFormat();
            return dataFormat.getFormat("yyyy-MM-dd");
        } else if (com.wuzh.commons.core.poi.excel.CellType.Time.equals(cellType)) { // 日期
            DataFormat dataFormat = workbook.createDataFormat();
            return dataFormat.getFormat("hh:mm:ss");
        } else if (CellType.DateTime.equals(cellType)) { // 日期
            DataFormat dataFormat = workbook.createDataFormat();
            return dataFormat.getFormat("yyyy-MM-dd hh:mm:ss");
        }
        return -1;
    }

    private static Object getRealField(Object data, String columnName) {
        // 如果行对象为Map类型，则不需要取字段的类型
        if (data instanceof Map) {
            return null;
        }

        // tips：先根据columnName取Field，如果取不到就去找Method
        Class<?> clazz = data.getClass();
        Field field = FieldUtils.getField(clazz, columnName, true);
        if (field != null) {
            return field;
        }

        // 如果field字段无法取到值，则通过Getter方法取
        // 将第一个字符转换为大写
        String firstChar = columnName.substring(0, 1);
        String lastChar = columnName.substring(1);
        columnName = firstChar.toUpperCase() + lastChar;
        Method fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + columnName);
        if (fieldMethod == null) {
            // 此处为解决实体类字段第二个字符为大写的问题
            // 注意：这里是为了兼容第二个字符为大写的字段，在设计表结构的时候，尽量不要采用这种方式，这种设计方式不合理，会在Getter、Setter方法以及接口返回的时候出现意料之外的结果
            firstChar = columnName.substring(0, 2);
            lastChar = columnName.substring(2);
            columnName = firstChar.toUpperCase() + lastChar;
            fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + columnName);
        }
        if (fieldMethod != null) {
            fieldMethod.setAccessible(true);
        }

        return null;
    }

    private static void setCellValue(Cell cell, Object realValue) {
        if (realValue == null) {
            cell.setCellValue("");
            return;
        }

        if (realValue instanceof Date) {
            //进行转换
            String dateValue = DateUtil.format((Date) realValue, DateUtil.PATTERN_DATE_TIME);
            //将属性值存入单元格
            cell.setCellValue(dateValue);
        } else if (realValue instanceof java.sql.Date) {
            //进行转换
            String dateValue = DateUtil.format((Date) realValue, DateUtil.PATTERN_DATE);
            //将属性值存入单元格
            cell.setCellValue(dateValue);
        } else if (realValue instanceof java.sql.Time) {
            //进行转换
            String dateValue = DateUtil.format((Date) realValue, DateUtil.PATTERN_TIME);
            //将属性值存入单元格
            cell.setCellValue(dateValue);
        } else if (realValue instanceof BigDecimal
                || realValue instanceof Float
                || realValue instanceof Double) {
            if (realValue.toString().matches("^(-?\\d+)(\\.\\d+)?$")) { // 数值型
                // 将属性值存入单元格
                cell.setCellValue(new BigDecimal(realValue.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else if (realValue.toString().matches("^[-\\+]?[\\d]*$")) { // 整数
                // 将属性值存入单元格
                cell.setCellValue(new BigDecimal(realValue.toString()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        } else if (realValue instanceof Byte
                || realValue instanceof Short
                || realValue instanceof Integer
                || realValue instanceof Long) {
            cell.setCellValue(new BigDecimal(realValue.toString()).setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
        } else {
            cell.setCellValue(realValue.toString());
        }
    }

    private static Object getRealValue(Object data, String columnName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object realValue = null;
        if (data instanceof Map) {
            realValue = ((Map) data).get(columnName);
        } else {
            Class<?> clazz = data.getClass();
            Field field = FieldUtils.getField(clazz, columnName, true);

            if (field == null) {
                // 如果field字段无法取到值，则通过Getter方法取
                // 将第一个字符转换为大写
                String firstChar = columnName.substring(0, 1);
                String lastChar = columnName.substring(1);
                columnName = firstChar.toUpperCase() + lastChar;
                Method fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + columnName);
                if (fieldMethod == null) {
                    // 此处为解决实体类字段第二个字符为大写的问题
                    // 注意：这里是为了兼容第二个字符为大写的字段，在设计表结构的时候，尽量不要采用这种方式，这种设计方式不合理，会在Getter、Setter方法以及接口返回的时候出现意料之外的结果
                    firstChar = columnName.substring(0, 2);
                    lastChar = columnName.substring(2);
                    columnName = firstChar.toUpperCase() + lastChar;
                    fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + columnName);
                }
                fieldMethod.setAccessible(true);
                realValue = MethodUtils.invokeMethod(data, fieldMethod.getName());
            } else {
                realValue = field.get(data);
            }
        }
        return realValue;
    }

    /**
     * 设置表头列(单元格)样式
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:26 )
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle cellStyle = createCellStyle(workbook);
        Font font = workbook.createFont();
        // 文本加粗
        font.setBold(true);
//        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置表头列(单元格)样式 - 红色提示
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:26 )
     */
    private static CellStyle createRequiredHeaderStyle(Workbook workbook) {
        CellStyle cellStyle = createCellStyle(workbook);
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        // 文本加粗
        font.setBold(true);
//        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置表头列(单元格)样式 - 红色提示
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:26 )
     */
    private static CellStyle createContentStyle(Workbook workbook) {
        CellStyle cellStyle = createCellStyle(workbook);
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_NORMAL);
//        font.setFontName("宋体");
//        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置数据列(单元格)样式
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:53 )
     */
    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    /**
     * 计算文本长度
     *
     * <pre>
     *  [\u4e00-\u9fa5]说明：
     *  1.这两个Unicode值正好是Unicode表中的汉字的头和尾
     *  2."[]"代表里边的值出现一个就可以
     * </pre>
     *
     * @param value
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:10:32 )
     */
    private static int stringRealLength(String value) {
        if (value == null) {
            return 0;
        }
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (String.valueOf(c).matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 导入数据
     *
     * @param request 请求信息
     */
    public static void importData(ExcelRequest request, InputStream inputStream) {
    }


}