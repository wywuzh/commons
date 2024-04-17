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
package com.github.wywuzh.commons.core.poi;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.github.wywuzh.commons.core.common.ContentType;
import com.github.wywuzh.commons.core.math.CalculationUtils;
import com.github.wywuzh.commons.core.poi.annotation.ExcelCell;
import com.github.wywuzh.commons.core.poi.constants.CellStyleConstants;
import com.github.wywuzh.commons.core.poi.enums.CellTypeEnum;
import com.github.wywuzh.commons.core.poi.modle.ExcelCellField;
import com.github.wywuzh.commons.core.poi.modle.ExcelExportRequest;
import com.github.wywuzh.commons.core.poi.modle.FreezePane;
import com.github.wywuzh.commons.core.poi.style.CellStyleTools;
import com.github.wywuzh.commons.core.reflect.ReflectUtils;
import com.github.wywuzh.commons.core.util.DateUtils;
import com.github.wywuzh.commons.core.util.SortUtils;
import com.github.wywuzh.commons.core.util.StringHelper;

/**
 * 类ExcelUtils的实现描述：Excel 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-05-29 10:54:36
 * @version v2.2.6
 * @since JDK 1.8
 */
public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    public static final int MAX_ROW = 50000;

    /**
     * 解析Cell列公式
     */
    private static ThreadLocal<FormulaEvaluator> FORMULA_EVALUATOR_LOCAL = new ThreadLocal<>();

    /**
     * 将异常信息导出
     *
     * @param request
     * @param response
     * @param fileName 文件名
     * @param e        异常信息
     * @since 2.3.6
     */
    public static void exportError(HttpServletRequest request, HttpServletResponse response, String fileName, Exception e) {
        String errorMsg = ExceptionUtils.getStackTrace(e);
        ExcelUtils.exportError(request, response, fileName, errorMsg);
    }

    /**
     * 将异常信息导出
     *
     * @param request
     * @param response
     * @param fileName  文件名
     * @param throwable 异常信息
     * @since 2.3.6
     */
    public static void exportError(HttpServletRequest request, HttpServletResponse response, String fileName, Throwable throwable) {
        String errorMsg = ExceptionUtils.getStackTrace(throwable);
        ExcelUtils.exportError(request, response, fileName, errorMsg);
    }

    /**
     * 将异常信息导出
     *
     * @param request
     * @param response
     * @param fileName 文件名
     * @param errorMsg 异常信息
     */
    public static void exportError(HttpServletRequest request, HttpServletResponse response, String fileName, String errorMsg) {
        BufferedOutputStream outputStream = null;
        try {
            // 获取浏览器类型
            String agent = request.getHeader("USER-AGENT").toLowerCase();
            response.setContentType("text/plain");
            if (agent.contains("firefox")) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
            } else {
                String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
            }
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(errorMsg.getBytes("UTF-8"));
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 导出数据
     *
     * @param request
     * @param response
     * @param fileName            文件名称
     * @param sheetName           文件sheet名
     * @param sheetColumnNames    列名/字段名
     * @param sheetColumnComments 列的标题
     * @param dataColl            请求数据
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> void exportData(HttpServletRequest request, HttpServletResponse response, String fileName, String sheetName, String[] sheetColumnNames, String[] sheetColumnComments,
            Collection<T> dataColl) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ExcelExportRequest excelExportRequest = new ExcelExportRequest();
        excelExportRequest.setSheetName(sheetName);
        excelExportRequest.setColumns(sheetColumnNames);
        excelExportRequest.setColumnTitles(sheetColumnComments);
        excelExportRequest.setDataColl(dataColl);

        exportData(request, response, fileName, excelExportRequest);
    }

    /**
     * 导出数据（支持导出多个sheet）
     *
     * @param request
     * @param response
     * @param fileName            文件名称
     * @param sheetNames          文件sheet名
     * @param sheetColumnNames    列名/字段名
     * @param sheetColumnComments 列的标题
     * @param dataColl            请求数据
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> void exportData(HttpServletRequest request, HttpServletResponse response, String fileName, String[] sheetNames, String[][] sheetColumnNames, String[][] sheetColumnComments,
            Collection<T>[] dataColl) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 创建workbook
        Workbook workbook = createWorkbook(fileName);
        for (int i = 0; i < sheetNames.length; i++) {
            ExcelExportRequest excelExportRequest = new ExcelExportRequest();
            excelExportRequest.setSheetName(sheetNames[i]);
            excelExportRequest.setColumns(sheetColumnNames[i]);
            excelExportRequest.setColumnTitles(sheetColumnComments[i]);
            excelExportRequest.setDataColl(dataColl[i]);

            // 创建sheet
            Sheet sheet = createSheet(workbook, excelExportRequest);
            // 写入内容
            writeData(workbook, sheet, excelExportRequest);
        }

        // 将workbook工作簿内容写入输出流中
        writeWorkbook(request, response, workbook, fileName);
    }

    /**
     * 导出数据
     *
     * @param request            请求信息
     * @param response           响应信息
     * @param fileName           导出文件名，注意需要包含文件后缀
     * @param excelExportRequest 导出数据请求条件
     */
    public static void exportData(HttpServletRequest request, HttpServletResponse response, String fileName, ExcelExportRequest excelExportRequest)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 创建workbook
        Workbook workbook = createWorkbook(fileName);
        // 创建sheet
        Sheet sheet = createSheet(workbook, excelExportRequest);
        // 写入内容
        writeData(workbook, sheet, excelExportRequest);

        // 将workbook工作簿内容写入输出流中
        writeWorkbook(request, response, workbook, fileName);
    }

    /**
     * 将workbook工作簿内容写入输出流中
     *
     * @param request
     * @param response
     * @param workbook 工作簿
     * @param fileName 文件名称
     * @since v2.7.0
     */
    public static <T> void writeWorkbook(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.reset();
        // 获取浏览器类型
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        response.setContentType(ContentType.APPLICATION_POINT_OFFICE2003_XLS);
        if (StringUtils.contains(userAgent, "firefox")) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
        } else {
            String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        }

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 创建workbook
     *
     * @param fileName 文件名
     * @return
     */
    public static Workbook createWorkbook(String fileName) throws IOException {
        // 检查文件名后缀是否为“.xlsx”
        boolean xssf = StringUtils.endsWithIgnoreCase(fileName, ".xlsx") ? true : false;
        Workbook workbook = WorkbookFactory.create(xssf);
        return workbook;
    }

    /**
     * 创建sheet
     *
     * @param workbook           工作簿
     * @param excelExportRequest Excel导出请求
     * @return
     * @since 2.3.6
     */
    public static Sheet createSheet(Workbook workbook, ExcelExportRequest excelExportRequest) {
        String sheetName = excelExportRequest.getSheetName();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = workbook.getNumberOfSheets() > 1 ? "sheet" + (workbook.getNumberOfSheets() + 1) : "sheet";
        }
        return createSheet(workbook, sheetName);
    }

    /**
     * 创建sheet
     *
     * @param workbook  工作簿
     * @param sheetName sheet名
     * @return
     * @since 2.3.6
     */
    public static Sheet createSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName == null ? "sheet" : sheetName);
        return sheet;
    }

    /**
     * 写入数据
     *
     * @param workbook            工作簿
     * @param sheet               写入数据的目标sheet
     * @param sheetColumnNames    列名/字段名
     * @param sheetColumnComments 列的标题
     * @param dataColl            请求数据
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @since 2.3.6
     */
    public static <T> void writeData(Workbook workbook, Sheet sheet, String[] sheetColumnNames, String[] sheetColumnComments, Collection<T> dataColl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ExcelExportRequest excelExportRequest = new ExcelExportRequest();
        excelExportRequest.setColumns(sheetColumnNames);
        excelExportRequest.setColumnTitles(sheetColumnComments);
        excelExportRequest.setDataColl(dataColl);

        // 写入内容
        writeData(workbook, sheet, excelExportRequest);
    }

    /**
     * 写入数据
     *
     * @param workbook           工作簿
     * @param sheet              写入数据的目标sheet
     * @param excelExportRequest Excel导出请求
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Sheet writeData(Workbook workbook, Sheet sheet, ExcelExportRequest excelExportRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Assert.notNull(excelExportRequest, "excelRequest must not be null");
        Assert.notEmpty(excelExportRequest.getColumns(), "columns must not be empty");
        Assert.notEmpty(excelExportRequest.getColumnTitles(), "columnTitles must not be empty");

        // 生成头部列(单元格)样式
        CellStyle headerStyle = CellStyleTools.createHeaderStyle(workbook);
        CellStyle requiredHeaderStyle = null;
        // 生成内容列(单元格)样式
        CellStyle contentStyle = CellStyleTools.createContentStyle(workbook);

        String[] columns = excelExportRequest.getColumns();
        String[] columnTitles = excelExportRequest.getColumnTitles();
        Integer[] columnLengths = excelExportRequest.getColumnLengths();
        List<String> requiredColumnTitles = excelExportRequest.getRequiredColumnTitles();
        if (CollectionUtils.isNotEmpty(requiredColumnTitles)) {
            requiredHeaderStyle = CellStyleTools.createHeaderStyleForRequired(workbook);
        }
        // 行高
        final float height = -1;

        // 数据开始行
        int firstRowNumber = 1;
        if (StringUtils.isNotBlank(excelExportRequest.getTips())) {
            firstRowNumber = 2;
            Row tipsRow = sheet.createRow(0);
            // 第一行添加提示信息
            Cell cell = tipsRow.createCell(0);
            cell.setCellValue(excelExportRequest.getTips());
            cell.setCellStyle(CellStyleTools.createHeaderStyleForTips(workbook));
            // 合并单元格，合并的列与导出列保持一致
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
            // 设置第一行高度
            int length = StringUtils.split(excelExportRequest.getTips(), "\n").length;
            tipsRow.setHeightInPoints(length * 18);
            // 设置自动换行
            cell.getCellStyle().setWrapText(true);
        }

        // 因为POI自动列宽计算的是String.length长度，在中文环境下会有问题，所以自行处理
        int[] maxLength = new int[columnTitles.length];

        Map<String, String[]> columnValidation = excelExportRequest.getColumnValidation();

        // 标题行
        Row headerRow = sheet.createRow(firstRowNumber - 1);
        headerRow.setHeightInPoints(height);

        // 创建表头行
        for (int j = 0; j < columnTitles.length; j++) {
            int columnTitleIndex = j;
            // 生成第j列 - 单元格
            Cell cell = headerRow.createCell(j);
            String columnComment = columnTitles[j];

            if (CollectionUtils.isNotEmpty(requiredColumnTitles) && requiredColumnTitles.contains(columnComment)) {
                // 必填项
                cell.setCellValue("*" + columnComment);
                cell.setCellStyle(requiredHeaderStyle);
            } else {
                cell.setCellStyle(headerStyle);
                cell.setCellValue(columnComment);
            }

            // 列宽
            if (columnLengths != null && columnLengths.length > 0) {
                maxLength[j] = columnLengths[j] * 30;
            } else {
                // 设置列宽
                maxLength[j] = getRealLength(columnComment) * 357;
            }

            // 下拉框
            if (columnValidation != null && columnValidation.containsKey(columnComment)) {
                // 注意：org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint.XSSFDataValidationConstraint(java.lang.String[])要求传入的数组不能为空，这里在入口进行控制
                String[] columnValidationData = columnValidation.get(columnComment);
                if (columnValidationData == null || columnValidationData.length == 0) {
                    continue;
                }
                // 设置为下拉框
                setValidation(workbook, sheet, columnComment, columnValidationData, 1, MAX_ROW, columnTitleIndex, columnTitleIndex);

                // 默认该列为为文本格式
                CellStyle cellStyleForText = Optional.ofNullable(sheet.getColumnStyle(columnTitleIndex)).orElse(CellStyleTools.createCellStyle(workbook));
                cellStyleForText.setDataFormat(getCellDateFormat(workbook, CellTypeEnum.String));
                sheet.setDefaultColumnStyle(columnTitleIndex, cellStyleForText);
            }
        }

        // 冻结窗口-首行
        // [v2.7.8]优先使用传入的冻结请求
        FreezePane freezePane = excelExportRequest.getFreezePane();
        if (freezePane == null) {
            freezePane = new FreezePane(0, firstRowNumber);
        }
        sheet.createFreezePane(freezePane.getColSplit(), freezePane.getRowSplit(), freezePane.getLeftmostColumn(), freezePane.getTopRow());

        // 数据行
        if (excelExportRequest.getDataColl() != null) {
            Iterator<?> iterator = excelExportRequest.getDataColl().iterator();
            int index = firstRowNumber;
            while (iterator.hasNext()) {
                Object data = iterator.next();
                if (data == null) {
                    continue;
                }

                // 数据行，从第二行开始
                Row sheetRow = sheet.createRow(index);
                sheetRow.setHeightInPoints(height);

                for (int k = 0; k < columns.length; k++) {
                    // 生成第k列 - 单元格
                    Cell cell = sheetRow.createCell(k);
                    String columnName = columns[k];
                    CellStyle columnStyle = Optional.ofNullable(sheet.getColumnStyle(k)).orElse(contentStyle);

                    Object realValue = getRealValue(data, columnName);
                    // 设置cell列字段值
                    setCellValue(workbook, cell, data, columnName, realValue);
                    // 设置cell列style样式
                    setCellStyle(workbook, cell, columnStyle, data, columnName);

                    // 列宽有设置时以设置为准，否则通过字段值长度计算列宽
                    if (columnLengths != null && columnLengths.length > 0) {
                        maxLength[k] = columnLengths[k] * 30;
                    } else {
                        // 设置列宽
                        int length = getRealLength(realValue) * 357;
                        maxLength[k] = Math.max(length, maxLength[k]);
                    }
                }

                index++;
            }
        }

        // 设置单元格宽度
        for (int j = 0; j < maxLength.length; j++) {
            sheet.setColumnWidth(j, maxLength[j]); // 每个字符大约10像素
        }

        return sheet;
    }

    private static void setCellStyle(Workbook workbook, Cell cell, CellStyle defaultCellStyle, Object data, String columnName) {
        short cellDateFormat = getCellDateFormat(workbook, data, columnName);
        if (cellDateFormat != -1) {
            CellStyle cellStyle = CellStyleTools.createContentStyle(workbook);
            cellStyle.cloneStyleFrom(defaultCellStyle);
            cellStyle.setDataFormat(cellDateFormat);
            cell.setCellStyle(cellStyle);
        } else {
            cell.setCellStyle(defaultCellStyle);
        }
    }

    public static short getCellDateFormat(Workbook workbook, Object data, String columnName) {
        // 取到columnName对应的实际字段/方法
        Object realField = getRealField(data, columnName);
        Short dataFormat = -1;
        if (realField != null) {
            dataFormat = getCellDateFormat(workbook, realField);
        }
        return dataFormat;
    }

    public static short getCellDateFormat(Workbook workbook, Object realField) {
        ExcelCell excelCell = null;
        if (realField instanceof Field) {
            excelCell = ((Field) realField).getAnnotation(ExcelCell.class);
        } else if (realField instanceof Method) {
            excelCell = ((Method) realField).getAnnotation(ExcelCell.class);
        }
        if (excelCell == null) {
            return -1;
        }
        Short dataFormat = -1;
        // [v2.7.0]优先使用用户自定义单元格格式
        // 更多单元格格式可参考：org.apache.poi.ss.usermodel.BuiltinFormats._formats
        if (StringUtils.isNotBlank(excelCell.format())) {
            dataFormat = workbook.createDataFormat().getFormat(excelCell.format());
        }
        if (dataFormat == -1) {
            // 系统预设单元格格式
            dataFormat = getCellDateFormat(workbook, excelCell.cellType());
        }
        return dataFormat;
    }

    public static short getCellDateFormat(Workbook workbook, CellTypeEnum cellTypeEnum) {
        String format = getFormat(cellTypeEnum);
        if (StringUtils.isBlank(format)) {
            return -1;
        }
        DataFormat dataFormat = workbook.createDataFormat();
        return dataFormat.getFormat(format);
//        if (CellTypeEnum.String.equals(cellTypeEnum)) { // 字符串文本
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_String);
//        } else if (CellTypeEnum.Percent.equals(cellTypeEnum)) { // 百分比
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Percent);
//        } else if (CellTypeEnum.Integer.equals(cellTypeEnum)) { // 整型数值
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Integer);
//        } else if (CellTypeEnum.BigDecimal.equals(cellTypeEnum)) { // 数值，保留2位小数
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_BigDecimal);
//        } else if (CellTypeEnum.Money.equals(cellTypeEnum)) { // 金额，保留2位小数
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Money);
//        } else if (CellTypeEnum.Rate.equals(cellTypeEnum)) { // 率，保留4位小数
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Rate);
//        } else if (CellTypeEnum.Accounting.equals(cellTypeEnum)) { // 会计专用
//            DataFormat dataFormat = workbook.createDataFormat();
//            // 金额格式：会计专用格式
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Accounting);
//        } else if (CellTypeEnum.Date.equals(cellTypeEnum)) { // 日期
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Date);
//        } else if (CellTypeEnum.Time.equals(cellTypeEnum)) { // 时间
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_Time);
//        } else if (CellTypeEnum.DateTime.equals(cellTypeEnum)) { // 日期时间
//            DataFormat dataFormat = workbook.createDataFormat();
//            return dataFormat.getFormat(CellStyleConstants.STYLE_FORMAT_DateTime);
//        }
//        return -1;
    }

    public static String getFormat(CellTypeEnum cellTypeEnum) {
        switch (cellTypeEnum) {
        case String: // 字符串文本
            return CellStyleConstants.STYLE_FORMAT_String;
        case Percent: // 百分比
            return CellStyleConstants.STYLE_FORMAT_Percent;
        case Integer: // 整型数值
            return CellStyleConstants.STYLE_FORMAT_Integer;
        case BigDecimal: // 数值，保留2位小数
            return CellStyleConstants.STYLE_FORMAT_BigDecimal;
        case Money: // 金额，保留2位小数
            return CellStyleConstants.STYLE_FORMAT_Money;
        case Rate: // 率，保留4位小数
            return CellStyleConstants.STYLE_FORMAT_Rate;
        case Accounting: // 会计专用，保留2位小数
            return CellStyleConstants.STYLE_FORMAT_Accounting;
        case Date: // 日期：yyyy-MM-dd格式
            return CellStyleConstants.STYLE_FORMAT_Date;
        case Time: // 时间：HH:mm:ss格式
            return CellStyleConstants.STYLE_FORMAT_Time;
        case DateTime: // 日期时间：yyyy-MM-dd HH:mm:ss格式
            return CellStyleConstants.STYLE_FORMAT_DateTime;
        }
        return null;
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

        return fieldMethod;
    }

    private static ExcelCell getExcelCell(Object data, String columnName) {
        // 取到columnName对应的实际字段/方法
        Object realField = getRealField(data, columnName);
        ExcelCell excelCell = null;
        if (realField instanceof Field) {
            excelCell = ((Field) realField).getAnnotation(ExcelCell.class);
        } else if (realField instanceof Method) {
            excelCell = ((Method) realField).getAnnotation(ExcelCell.class);
        }
        return excelCell;
    }

    private static CellTypeEnum getCellTypeEnum(Object data, String columnName) {
        ExcelCell excelCell = getExcelCell(data, columnName);
        CellTypeEnum cellTypeEnum = null;
        if (excelCell != null) {
            cellTypeEnum = excelCell.cellType();
        }
        return cellTypeEnum;
    }

    /**
     * 设置单元格值
     *
     * @param workbook   工作簿对象
     * @param cell       单元格
     * @param data       数据行
     * @param columnName 字段名
     * @param realValue  字段值
     * @since v2.7.0
     */
    private static void setCellValue(Workbook workbook, Cell cell, Object data, String columnName, Object realValue) {
        if (realValue == null) {
            cell.setCellValue("");
            return;
        }
        if (realValue instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) realValue).doubleValue());
        } else {
            cell.setCellValue(String.valueOf(realValue));
        }
    }

    /**
     * 设置单元格值
     *
     * @param workbook   工作簿对象
     * @param cell       单元格
     * @param data       数据行
     * @param columnName 字段名
     * @since v2.7.0
     */
    private static void setCellValue(Workbook workbook, Cell cell, Object data, String columnName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object realValue = getRealValue(data, columnName);
        if (realValue == null) {
            cell.setCellValue("");
            return;
        }
        if (realValue instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) realValue).doubleValue());
        } else {
            cell.setCellValue(String.valueOf(realValue));
        }
    }

    /**
     * 获取字段值
     *
     * @param data       数据对象
     * @param columnName 字段名
     * @return
     */
    private static Object getRealValue(Object data, String columnName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object realValue = ReflectUtils.getValue(data, columnName);
        if (realValue == null) {
            return realValue;
        }

        if (realValue instanceof java.util.Date) {
            return getRealValueForDate(data, columnName, realValue);
        } else if (realValue instanceof java.sql.Date) {
            // 进行转换
            String dateValue = DateUtils.format((Date) realValue, DateUtils.PATTERN_DATE);
            return dateValue;
        } else if (realValue instanceof java.sql.Time) {
            // 进行转换
            String dateValue = DateUtils.format((Date) realValue, DateUtils.PATTERN_TIME);
            // 将属性值存入单元格
            return dateValue;
        } else if (realValue instanceof BigDecimal || realValue instanceof Float || realValue instanceof Double) {
//            return getRealValueForNumber(data, columnName, realValue);
            return new BigDecimal(realValue.toString());
        } else if (realValue instanceof Byte || realValue instanceof Short || realValue instanceof Integer || realValue instanceof Long) {
            return new BigDecimal(realValue.toString()).setScale(0, BigDecimal.ROUND_HALF_UP);
        }
        return realValue.toString();
    }

    /**
     * 获取实际字段值(日期格式)
     *
     * @param data       数据对象
     * @param columnName 字段名
     * @param realValue  实际字段值
     * @return
     */
    private static Object getRealValueForDate(Object data, String columnName, Object realValue) {
        ExcelCell excelCell = getExcelCell(data, columnName);
        CellTypeEnum cellTypeEnum = null;
        if (excelCell != null) {
            cellTypeEnum = excelCell.cellType();
        }

        String pattern = null;
        if (CellTypeEnum.DateTime.equals(cellTypeEnum)) {
            pattern = DateUtils.PATTERN_DATE_TIME;
        } else if (CellTypeEnum.Date.equals(cellTypeEnum)) {
            pattern = DateUtils.PATTERN_DATE;
        } else if (CellTypeEnum.Time.equals(cellTypeEnum)) {
            pattern = DateUtils.PATTERN_TIME;
        }
        if (excelCell != null && StringUtils.isNotBlank(excelCell.format())) {
            pattern = excelCell.format();
        }
        // 注：优先使用@ExcelCell注解中format指定的格式，然后使用cellType指定的字段类型，如果都未指定则取默认格式
        // 优先级别：format > cellType > 默认
        if (StringUtils.isBlank(pattern)) {
            pattern = DateUtils.PATTERN_DATE_TIME;
        }
        // 进行转换
        String dateValue = DateUtils.format((Date) realValue, pattern);
        return dateValue;
    }

    /**
     * 获取实际字段值(数值格式)
     *
     * @param data       数据对象
     * @param columnName 字段名
     * @param realValue  实际字段值
     * @return
     */
    private static Object getRealValueForNumber(Object data, String columnName, Object realValue) {
        ExcelCell excelCell = getExcelCell(data, columnName);
        CellTypeEnum cellTypeEnum = null;
        if (excelCell != null) {
            cellTypeEnum = excelCell.cellType();
        }

        String pattern = null;
        if (CellTypeEnum.BigDecimal.equals(cellTypeEnum)) { // 2位小数
            pattern = CellStyleConstants.STYLE_FORMAT_BigDecimal;
        } else if (CellTypeEnum.Integer.equals(cellTypeEnum)) { // 整型数值
            pattern = CellStyleConstants.STYLE_FORMAT_Integer;
        } else if (CellTypeEnum.Money.equals(cellTypeEnum)) { // 金额，保留2位小数
            pattern = CellStyleConstants.STYLE_FORMAT_Money;
        } else if (CellTypeEnum.Rate.equals(cellTypeEnum)) { // 率，保留4位小数
            pattern = CellStyleConstants.STYLE_FORMAT_Rate;
        }
        if (excelCell != null && StringUtils.isNotBlank(excelCell.format())) {
            pattern = excelCell.format();
        }
        // 注：优先使用@ExcelCell注解中format指定的格式，然后使用cellType指定的字段类型，如果都未指定则取默认格式
        // 优先级别：format > cellType > 默认
        if (StringUtils.isNotBlank(pattern)) {
            return new DecimalFormat(pattern).format(new BigDecimal(realValue.toString()));
        }

        return new BigDecimal(realValue.toString());
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet                要设置的sheet
     * @param columnValidationData 字段下拉列表：Excel验证数据
     * @param firstRow             开始行，从1开始
     * @param lastRow              结束行，不能小于开始行
     * @param firstCol             开始列，从0开始
     * @param lastCol              结束列，不能小于开始列
     * @return 设置好的sheet.
     */
    private static Sheet setSmallValidation(Sheet sheet, String[] columnValidationData, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (firstRow < 1) {
            throw new IllegalArgumentException("firstRow must not less 1");
        }
        if (lastRow < firstRow) {
            throw new IllegalArgumentException("lastRow must not less firstRow");
        }
        if (firstCol < 0) {
            throw new IllegalArgumentException("firstCol must not less 0");
        }
        if (lastCol < firstCol) {
            throw new IllegalArgumentException("lastCol must not less firstCol");
        }
        DataValidationHelper helper = sheet.getDataValidationHelper();

        DataValidationConstraint constraint = helper.createExplicitListConstraint(columnValidationData);

        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

        DataValidation dataValidation = helper.createValidation(constraint, addressList);

        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        // 作用在目标sheet上
        sheet.addValidationData(dataValidation);
        return sheet;
    }

    /**
     * 设置列的下拉值，Excel验证数据
     *
     * @param workbook             工作簿
     * @param sheet                目标sheet
     * @param columnTitle          字段标题
     * @param columnValidationData 字段下拉列表：Excel验证数据
     * @param firstRow             开始行，从1开始
     * @param lastRow              结束行，不能小于开始行
     * @param firstCol             开始列，从0开始
     * @param lastCol              结束列，不能小于开始列
     * @return
     */
    public static Sheet setValidation(Workbook workbook, Sheet sheet, String columnTitle, String[] columnValidationData, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (firstRow < 1) {
            throw new IllegalArgumentException("firstRow must not less 1");
        }
        if (lastRow < firstRow) {
            throw new IllegalArgumentException("lastRow must not less firstRow");
        }
        if (firstCol < 0) {
            throw new IllegalArgumentException("firstCol must not less 0");
        }
        if (lastCol < firstCol) {
            throw new IllegalArgumentException("lastCol must not less firstCol");
        }
        // 当下拉列表数据少于10个时，不需要创建隐藏sheet
        // 说明：下拉框数据量超过一定数量时，文件打不开。为了防止这一情况，这里默认设置超过10条就采用隐藏sheet的方式来设置下拉框
        if (columnValidationData.length <= 10) {
            return setSmallValidation(sheet, columnValidationData, firstRow, lastRow, firstCol, lastCol);
        }
        // 参考地址：https://www.cnblogs.com/zouhao/p/11346243.html
        // 创建sheet，写入枚举项
        /*
         * int sheets = workbook.getNumberOfSheets();
         * String prefixName = StringUtils.join(columnTitle, StaConstants.SEPARATE_UNDERLINE, sheets);
         * Sheet hideSheet = workbook.createSheet(prefixName + "_hiddenSheet");
         */
        String prefixName = columnTitle;
        // 已经创建了隐藏sheet，直接使用原来的就行
        Sheet hideSheet = workbook.getSheet(prefixName + "_hiddenSheet");
        if (hideSheet != null) {
            return sheet;
        }
        hideSheet = workbook.createSheet(prefixName + "_hiddenSheet");
        for (int i = 0; i < columnValidationData.length; i++) {
            hideSheet.createRow(i).createCell(0).setCellValue(columnValidationData[i]);
        }
        // 创建名称，可被其他单元格引用
        Name categoryName = workbook.createName();
        categoryName.setNameName(prefixName + "_hidden");
        // 设置名称引用的公式
        // 使用像'A1：B1'这样的相对值会导致在Microsoft Excel中使用工作簿时名称所指向的单元格的意外移动，
        // 通常使用绝对引用，例如'$A$1:$B$1'可以避免这种情况。
        // 参考： http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Name.html
        categoryName.setRefersToFormula(prefixName + "_hiddenSheet!" + "$A$1:$A$" + columnValidationData.length);
        // 获取上文名称内数据
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createFormulaListConstraint(prefixName + "_hidden");
        // 设置下拉框位置
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            // 数据校验
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        // 作用在目标sheet上
        sheet.addValidationData(dataValidation);
        // 设置hiddenSheet隐藏
        workbook.setSheetHidden(workbook.getSheetIndex(hideSheet), true);
        return sheet;
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
     * @deprecated 已废弃，请使用 {@link com.github.wywuzh.commons.core.util.StringHelper#length(String)} 方法
     */
    @Deprecated
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
     * 计算文本长度
     *
     * @param value 目标对象
     * @return
     * @since v2.3.2
     */
    private static int getRealLength(Object value) {
        if (value == null) {
            return 0;
        }
        return StringHelper.length(value.toString());
    }

    /**
     * 导入数据
     *
     * @param inputStream 输入流
     * @param clazz       返回结果类
     */
    public static <T> List<T> importData(InputStream inputStream, Class<T> clazz) throws Exception {
        return importData(inputStream, clazz, null);
    }

    /**
     * 导入数据
     *
     * @param inputStream 输入流
     * @param clazz       返回结果类
     * @param columns     读取字段
     */
    public static <T> List<T> importData(InputStream inputStream, Class<T> clazz, String[] columns) throws Exception {
        return importData(inputStream, clazz, columns, 1);
    }

    /**
     * 导入数据
     *
     * @param inputStream 输入流
     * @param clazz       返回结果类
     * @param columns     读取字段
     * @param startRow    开始读取数据行，从0开始
     */
    public static <T> List<T> importData(InputStream inputStream, Class<T> clazz, String[] columns, int startRow) throws Exception {
        Assert.notNull(inputStream, "inputStream must not be null");
        Assert.notNull(clazz, "clazz must not be null");

        List<T> resultList = new LinkedList<>();
        try {
            // 解析需要读取的字段，如果 columns 为空，就以 clazz 类中 @ExcelCell 注解标记的字段为准
            columns = transformRealColumns(clazz, columns);
            Assert.notEmpty(columns, "columns must not be empty");

            // 读取内容
            Workbook workbook = create(inputStream);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                // 检查sheet是否隐藏，如果是则不读取数据
                if (sheet == null || StringUtils.contains(sheet.getSheetName(), "hiddenSheet") || workbook.isSheetHidden(i) || workbook.isSheetVeryHidden(i)) {
                    LOGGER.warn("sheetName={} 该sheet页为隐藏sheet，不读取该sheet页数据！", sheet.getSheetName());
                    continue;
                }
                resultList.addAll(getSheetData(workbook, sheet, clazz, columns, startRow));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return resultList;
    }

    /**
     * 读取类字段上面的ExcelCell注解，并转换为ExcelCellField对象
     *
     * @param clazz 目标类
     * @return ExcelCellField对象
     * @since v2.7.8
     */
    public static <T> List<ExcelCellField> resolvedExcelCellField(Class<T> clazz) {
        List<ExcelCellField> excelCellFieldList = new ArrayList<>();
        List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(clazz, ExcelCell.class);
        if (CollectionUtils.isEmpty(fieldList)) {
            return excelCellFieldList;
        }
        for (Field field : fieldList) {
            ExcelCell excelCell = field.getAnnotation(ExcelCell.class);

            ExcelCellField excelCellField = new ExcelCellField();
            excelCellField.setFieldName(field.getName());
            excelCellField.setFieldTitle(excelCell.value());
            excelCellField.setCellType(excelCell.cellType());
            excelCellField.setIndex(excelCell.index());
            excelCellField.setSortNo(excelCell.sort());
            excelCellField.setFormat(excelCell.format());
            excelCellFieldList.add(excelCellField);
        }
        return excelCellFieldList;
    }

    /**
     * 解析需要读取的字段，如果 columns 为空，就以 clazz 类中 @ExcelCell 注解标记的字段为准
     *
     * @param clazz   目标类
     * @param columns 需要读取的字段
     * @return
     */
    private static <T> String[] transformRealColumns(Class<T> clazz, String[] columns) {
        if (columns != null && columns.length != 0) {
            return columns;
        }

        List<ExcelCellField> excelCellFieldList = resolvedExcelCellField(clazz);
        if (CollectionUtils.isEmpty(excelCellFieldList)) {
            return columns;
        }
        // 排序：索引、排序、字段标题
        SortUtils.sort(excelCellFieldList, new String[] {
                "index"/* , "sortNo", "fieldTitle" */
        });

        List<String> columnList = new LinkedList<>();
        for (ExcelCellField excelCellField : excelCellFieldList) {
            String fieldName = excelCellField.getFieldName();
            columnList.add(fieldName);
        }
        return columnList.toArray(new String[0]);
    }

    /**
     * 创建工作簿
     *
     * @param file 目标文件
     * @return
     * @throws IOException
     * @since v2.4.8
     */
    public static Workbook create(File file) throws IOException {
        Assert.notNull(file, "file must not be null");

        return WorkbookFactory.create(file);
    }

    /**
     * 创建工作簿
     *
     * @param inputStream 输入流
     * @return
     * @throws IOException
     */
    public static Workbook create(InputStream inputStream) throws IOException {
        Assert.notNull(inputStream, "inputStream must not be null");

        return WorkbookFactory.create(inputStream);
    }

    /**
     * 读取模板sheet的数据
     *
     * @param workbook 工作簿
     * @param sheet    目标sheet
     * @param clazz    解析目标结果类
     * @param columns  字段
     * @param startRow 开始读取数据行，从0开始
     * @return 传入sheet页读取到的数据
     */
    public static <T> List<T> getSheetData(Workbook workbook, Sheet sheet, Class<T> clazz, String[] columns, int startRow) throws Exception {
        List<T> resultList = new LinkedList<>();

        try {
            // 解析Cell列公式
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            FORMULA_EVALUATOR_LOCAL.set(formulaEvaluator);

            // 读取内容
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                // 获取不为空的列个数
                if (row.getPhysicalNumberOfCells() == 0) {
                    continue; // 该行的列为空
                }

                T data = clazz.newInstance();
                // 获取每一行的列数据
                for (int c = 0; c < columns.length; c++) {
                    Cell cell = row.getCell(c);
                    String fieldName = columns[c];
                    try {
                        Field field = FieldUtils.getField(clazz, fieldName, true);

                        ReflectUtils.setValue(data, fieldName, getRealValue(row, cell, clazz, fieldName, field));
                    } catch (Exception e) {
                        String errorMsg = MessageFormat.format("Excel文件中[{0}]Sheet的第{1}行第{2}列数据[{3}]导入错误，请检查！", sheet.getSheetName(), (r + 1), getExcelColumnLabel(cell.getColumnIndex()), cell);
                        LOGGER.error("fieldName={} {}:", fieldName, errorMsg, e);
                        throw new IOException(errorMsg, e);
                    }
                }

                resultList.add(data);
            }
        } finally {
            FORMULA_EVALUATOR_LOCAL.remove();
        }
        return resultList;
    }

    /**
     * 通过列索引获取Excel其对应列的字母
     *
     * @param cellIndex 列索引，从0开始
     * @return
     */
    private static String getExcelColumnLabel(int cellIndex) {
        String cellIndexName = "";
        int baseCol = 65 + cellIndex;
        if (baseCol > 90) {
            // 十位位置
            int i2 = 0;
            if ((baseCol - 90) / 26 > 0) {
                i2 = 65 + ((baseCol - 90 - 1) / 26);
            } else {
                i2 = 65;
            }
            // 个位位置
            int i1 = ((baseCol - 90 - 1) % 26);
            i1 = 65 + i1;

            cellIndexName = String.valueOf((char) i2) + String.valueOf((char) i1);
        } else {
            cellIndexName = String.valueOf((char) baseCol);
        }
        return cellIndexName;
    }

    private static <T> Object getRealValue(Row row, Cell cell, Class<T> clazz, String fieldName, Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType == Byte.class) {
            if (isBlank(cell)) {
                return null;
            }
            return new BigDecimal(getCellData(cell).toString()).byteValue();
        } else if (fieldType == Short.class) {
            if (isBlank(cell)) {
                return null;
            }
            return new BigDecimal(getCellData(cell).toString()).shortValue();
        } else if (fieldType == Integer.class) {
            if (isBlank(cell)) {
                return null;
            }
            return new BigDecimal(getCellData(cell).toString()).intValue();
        } else if (fieldType == Double.class) {
            if (isBlank(cell)) {
                return null;
            }
            // 判断是否存在 %
            String cellValue = getCellData(cell).toString();
            if (StringUtils.contains(cellValue, "%")) {
                cellValue = StringUtils.replace(cellValue, "%", "");

                // 需要将数值%100
                return CalculationUtils.div(new BigDecimal(cellValue), CalculationUtils.DEFAULT_ONE_HUNDRED).doubleValue();
            } else {
                return new BigDecimal(cellValue).doubleValue();
            }
        } else if (fieldType == Float.class) {
            if (isBlank(cell)) {
                return null;
            }
            return new BigDecimal(getCellData(cell).toString()).floatValue();
        } else if (fieldType == Long.class) {
            if (isBlank(cell)) {
                return null;
            }
            return new BigDecimal(getCellData(cell).toString()).longValue();
        } else if (fieldType == Boolean.class) {
            if (isBlank(cell)) {
                return null;
            }
        } else if (fieldType == String.class) {
            if (isBlank(cell)) {
                return null;
            }
            // 去掉前后空格
            return StringUtils.strip(getCellData(cell).toString());
        } else if (fieldType == BigDecimal.class) {
            if (isBlank(cell)) {
                return null;
            }
            String cellValue = getCellData(cell).toString();
            if (StringUtils.contains(cellValue, "%")) {
                cellValue = cellValue.replace("%", "").replace(",", "");
                return CalculationUtils.div(new BigDecimal(cellValue), new BigDecimal(100), 4);
            } else {
                return new BigDecimal(cellValue.replace(",", ""));
            }
        } else if (fieldType == java.util.Date.class) {
            if (cell == null) {
                return null;
            }
            Date cellValue = null;
            try {
                cellValue = cell.getDateCellValue();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            // 日期转换优先级别：yyyy-MM-dd HH:mm:ss -> yyyy-MM-dd -> yyyy/MM/dd -> yyyy-MM
            if (cellValue == null) {
                cellValue = DateUtils.parse(cell.getStringCellValue(), DateUtils.PATTERN_DATE_TIME);
            }
            if (cellValue == null) {
                cellValue = DateUtils.parse(cell.getStringCellValue(), DateUtils.PATTERN_DATE);
            }
            if (cellValue == null) {
                cellValue = DateUtils.parse(cell.getStringCellValue(), "yyyy/MM/dd");
            }
            if (cellValue == null) {
                cellValue = DateUtils.parse(cell.getStringCellValue(), DateUtils.PATTERN_YYYY_MM);
            }
            return cellValue;
        }
        return null;
    }

    /**
     * 获取指定行的列数据
     *
     * @param row     工作表数据行
     * @param columns 指定列名称集合
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年10月23日 下午5:30:56
     */
    public static Map<String, Object> getRowData(Row row, String[] columns) {
        Assert.notNull(row, "row must not be null");
        Assert.notEmpty(columns, "columns must not be empty");

        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < columns.length; i++) {
            map.put(columns[i], getCellData(row.getCell(i)));
        }
        return map;
    }

    /**
     * 获取指定的列数据
     *
     * @param cell 指定列（单元格）
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年10月23日 下午5:29:03
     */
    public static Object getCellData(Cell cell) {
        if (cell == null) {
            return null;
        }
        Object value = null;
        if (cell.getCellType() == CellType.NUMERIC) { // 数值类型：整数、小数、日期
            // 解决自动加".0"的数字
            String format = NumberFormat.getInstance().format(cell.getNumericCellValue());
            // 逗号去掉
            format = StringUtils.replace(format, ",", "");
            value = new BigDecimal(format);
        } else if (cell.getCellType() == CellType.STRING) { // 字符串
            value = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.FORMULA) { // 公式
            CellValue cellValue = FORMULA_EVALUATOR_LOCAL.get().evaluate(cell);
            // 解决自动加".0"的数字
            String format = NumberFormat.getInstance().format(cellValue.getNumberValue());
            // 逗号去掉
            format = StringUtils.replace(format, ",", "");
            value = new BigDecimal(format);
        } else if (cell.getCellType() == CellType.BLANK) { // 空单元格：没值，但有单元格样式
            value = "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            value = cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.ERROR) { // 错误单元格
            value = cell.getErrorCellValue();
        } else {
            value = cell.getRichStringCellValue();
        }
        return value;
    }

    /**
     * 校验单元格数据是否为空
     *
     * @param cell 单元格对象
     * @return
     * @since 2.3.6
     */
    private static boolean isBlank(Cell cell) {
        Object cellValue = getCellData(cell);
        return cellValue == null;
//        if (!CellType.STRING.equals(cell.getCellType())) {
//            //所有的格式都设置为 String 类型
//            cell.setCellType(CellType.STRING);
//        }
//        return StringUtils.isBlank(cell.getStringCellValue());
    }

}
