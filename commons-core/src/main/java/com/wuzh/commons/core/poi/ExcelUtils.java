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

import com.wuzh.commons.core.common.ContentType;
import com.wuzh.commons.core.poi.excel.CellType;
import com.wuzh.commons.core.poi.excel.ExcelCell;
import com.wuzh.commons.core.poi.excel.ExcelRequest;
import com.wuzh.commons.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

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
        dataMap.put("afeeLaborAgencyExpenses", BigDecimal.valueOf(129.99));
        dataColl.add(dataMap);
        Map<String, Object> dataMap1 = new HashMap<>();
        dataMap1.put("brand", "moussy");
        dataMap1.put("shopNo", "BBJ101");
        dataMap1.put("shopFullName", "北京三里屯太古里店MOU");
        dataMap1.put("afeeLaborAgencyExpenses", BigDecimal.ZERO);
        dataColl.add(dataMap1);

        Map<String, String[]> columnValidation = new HashMap<>();
        String[] brands = {"BLACK by moussy", "moussy", "Belle"};
        columnValidation.put("品牌", brands);

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setColumns(columns);
        excelRequest.setColumnTitles(columnTitles);
        excelRequest.setColumnLengths(columnLengths);
        excelRequest.setRequiredColumnTitles(requiredColumnTitles);
        excelRequest.setDataColl(dataColl);
        excelRequest.setColumnValidation(columnValidation);

        OutputStream outputStream = null;
        try {
            File destFile = new File("D:\\data");
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            outputStream = new FileOutputStream(new File(destFile, fileName));
            Workbook workbook = createWorkbook(fileName);
            createSheet(workbook, excelRequest);
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

    public static void exportData(HttpServletRequest request, HttpServletResponse response,
                                  String fileName, String sheetNames, String[] sheetColumnNames, String[] sheetColumnComments,
                                  Collection<Map<String, Object>> dataColl) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        exportData(request, response, fileName, new String[]{sheetNames}, new String[][]{sheetColumnNames}, new String[][]{sheetColumnComments}, new Collection[]{dataColl});
    }

    public static void exportData(HttpServletRequest request, HttpServletResponse response,
                                  String fileName, String[] sheetNames, String[][] sheetColumnNames, String[][] sheetColumnComments,
                                  Collection<Map<String, Object>>[] dataColl) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        OutputStream outputStream = response.getOutputStream();
        response.reset();
        // 获取浏览器类型
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        response.setContentType(ContentType.APPLICATION_POINT_OFFICE2003_XLS);
        if (StringUtils.contains(userAgent, "firefox")) {
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
        } else {
            String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        }

        Workbook workbook = createWorkbook(fileName);
        for (int i = 0; i < sheetNames.length; i++) {
            ExcelRequest excelRequest = new ExcelRequest();
            excelRequest.setSheetName(sheetNames[i]);
            excelRequest.setColumns(sheetColumnNames[i]);
            excelRequest.setColumnTitles(sheetColumnComments[i]);
            excelRequest.setDataColl(dataColl[i]);
            createSheet(workbook, excelRequest);
        }

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 导出数据
     *
     * @param request      请求信息
     * @param response     响应信息
     * @param excelRequest 导出数据请求条件
     * @param fileName     导出文件名，注意需要包含文件后缀
     */
    public static void exportData(HttpServletRequest request, HttpServletResponse response, ExcelRequest excelRequest, String fileName)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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

        Workbook workbook = createWorkbook(fileName);
        createSheet(workbook, excelRequest);

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public static Workbook createWorkbook(String fileName) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 检查文件名后缀是否为“.xlsx”
        boolean xssf = StringUtils.endsWithIgnoreCase(fileName, ".xlsx") ? true : false;
        Workbook workbook = WorkbookFactory.create(xssf);
        return workbook;
    }

    public static Sheet createSheet(Workbook workbook, ExcelRequest excelRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Assert.notNull(excelRequest, "excelRequest must not be null");
        Assert.notEmpty(excelRequest.getColumns(), "columns must not be empty");
        Assert.notEmpty(excelRequest.getColumnTitles(), "columnTitles must not be empty");

        String sheetName = excelRequest.getSheetName();
        if (StringUtils.isEmpty(sheetName)) {
            sheetName = workbook.getNumberOfSheets() > 1 ? "sheet" + (workbook.getNumberOfSheets() + 1) : "sheet";
        }
        Sheet sheet = workbook.createSheet(sheetName);

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

        Map<String, String[]> columnValidation = excelRequest.getColumnValidation();

        for (int j = 0; j < columnTitles.length; j++) {
            int titleIndex = j;
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

            // 列宽
            if (columnLengths != null && columnLengths.length > 0) {
                maxLength[j] = columnLengths[j] * 30;
            } else {
                // 设置列宽
                maxLength[j] = stringRealLength(columnComment) * 357;
            }

            // 下拉框
            if (columnValidation != null && columnValidation.containsKey(columnComment)) {
                String[] columnValidationData = columnValidation.get(columnComment);
                //设置为下拉框
                setValidation(workbook, sheet, columnComment, columnValidationData, 1, MAX_ROW, titleIndex, titleIndex);
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
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet：要设置的sheet
     * @param columnValidationData 字段下拉列表：Excel验证数据
     * @param firstRow             开始行
     * @param lastRow              结束行
     * @param firstCol             开始列
     * @param lastCol              结束列
     * @return 设置好的sheet.
     */
    private static Sheet setValidation(Sheet sheet,
                                       String[] columnValidationData,
                                       int firstRow, int lastRow, int firstCol, int lastCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();

        DataValidationConstraint constraint = helper.createExplicitListConstraint(columnValidationData);

        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

        DataValidation dataValidation = helper.createValidation(constraint, regions);

        //处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        //添加到sheet中
        sheet.addValidationData(dataValidation);
        //返回sheet
        return sheet;
    }

    /**
     * 设置列的下拉值，Excel验证数据
     *
     * @param workbook
     * @param sheet
     * @param columnTitle          字段标题
     * @param columnValidationData 字段下拉列表：Excel验证数据
     * @param firstRow             开始行
     * @param lastRow              结束行
     * @param firstCol             开始列
     * @param lastCol              结束列
     * @return
     */
    private static Sheet setValidation(Workbook workbook, Sheet sheet,
                                       String columnTitle, String[] columnValidationData,
                                       int firstRow, int lastRow, int firstCol, int lastCol) {
        // 当下拉列表数据少于10个时，不需要创建隐藏sheet
        // 说明：下拉框数据量超过一定数量时，文件打不开。为了防止这一情况，这里默认设置超过10条就采用隐藏sheet的方式来设置下拉框
        if (columnValidationData.length <= 10) {
            return setValidation(sheet, columnValidationData, firstRow, lastRow, firstCol, lastCol);
        }
        // 参考地址：https://www.cnblogs.com/zouhao/p/11346243.html
        // 创建sheet，写入枚举项
        /*int sheets = workbook.getNumberOfSheets();
        String prefixName = StringUtils.join(columnTitle, StaConstants.SEPARATE_UNDERLINE, sheets);
        Sheet hideSheet = workbook.createSheet(prefixName + "_hiddenSheet");*/
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
     * @param inputStream 输入流
     * @param clazz       返回结果类
     */
    public static <T> List<T> importData(InputStream inputStream, Class<T> clazz) {
        return importData(inputStream, clazz, null);
    }

    /**
     * 导入数据
     *
     * @param inputStream 输入流
     * @param clazz       返回结果类
     * @param columns     读取字段
     */
    public static <T> List<T> importData(InputStream inputStream, Class<T> clazz, String[] columns) {
        Assert.notNull(inputStream, "inputStream must not be null");
        Assert.notNull(clazz, "clazz must not be null");

        List<T> resultList = new LinkedList<>();
        try {
            // 解析需要读取的字段，如果 columns 为空，就以 clazz 类中ExcelCell标记的字段为准
            columns = transformRealColumns(clazz, columns);

            // 读取内容
            Workbook workbook = reader(inputStream);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                // 检查sheet是否隐藏，如果是则不读取数据
                if (sheet == null || StringUtils.contains(sheet.getSheetName(), "hiddenSheet")
                        || workbook.isSheetHidden(i) || workbook.isSheetVeryHidden(i)) {
                    continue;
                }
                resultList.addAll(getSheetData(sheet, columns, 1));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return resultList;
    }

    private static <T> String[] transformRealColumns(Class<T> clazz, String[] columns) {
        if (columns != null && columns.length != 0) {
            return columns;
        }
        List<String> columnList = new LinkedList<>();
        for (Field field : FieldUtils.getAllFieldsList(clazz)) {
            String fieldName = field.getName();
            columnList.add(fieldName);
        }
        return columnList.toArray(new String[0]);
    }

    public static Workbook reader(InputStream inputStream) throws FileNotFoundException, IOException {
        Assert.notNull(inputStream, "inputStream must not be null");

        return WorkbookFactory.create(inputStream);
    }

    public static <T> List<T> getSheetData(Sheet sheet, String[] columns, int startRow) {
        List<T> resultList = new LinkedList<>();
        // todo 读取内容
        return resultList;
    }
}