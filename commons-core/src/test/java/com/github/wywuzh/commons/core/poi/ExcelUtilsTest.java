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

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import com.github.wywuzh.commons.core.json.jackson.JsonMapper;
import com.github.wywuzh.commons.core.poi.annotation.ExcelCell;
import com.github.wywuzh.commons.core.poi.constants.CellStyleConstants;
import com.github.wywuzh.commons.core.poi.entity.User;
import com.github.wywuzh.commons.core.poi.modle.ExcelExportRequest;
import com.github.wywuzh.commons.core.poi.style.CellStyleTools;

import lombok.extern.slf4j.Slf4j;

/**
 * 类ExcelUtilsTest的实现描述：Excel 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-25 18:10:04
 * @version v2.3.7
 * @since JDK 1.8
 */
@Slf4j
public class ExcelUtilsTest {

//    @Before
    public void init() {
        System.setProperty("font.name", "宋体");
        System.setProperty("font.height", "9");

        // 表头列(单元格)样式 - 表头提示信息
        // 填充方案编码
        System.setProperty("cell_style.header.tips.fill_pattern_type.code", String.valueOf(FillPatternType.SOLID_FOREGROUND.getCode()));
        // 设置前景色
        System.setProperty("cell_style.header.tips.foreground.color", String.valueOf(IndexedColors.YELLOW.getIndex()));
        // 设置背景色
        System.setProperty("cell_style.header.tips.background.color", String.valueOf(IndexedColors.YELLOW.getIndex()));
        // 字体颜色
        System.setProperty("cell_style.header.tips.font.color", String.valueOf(Font.COLOR_RED));

        // 表头列(单元格)样式 - 表头必填字段
        // 填充方案编码
        System.setProperty("cell_style.header.required.fill_pattern_type.code", String.valueOf(FillPatternType.SOLID_FOREGROUND.getCode()));
        // 设置前景色：深青色/蓝色, 个形色1, 深色50%
        System.setProperty("cell_style.header.required.foreground.color", String.valueOf(IndexedColors.DARK_TEAL.getIndex()));
        // 设置背景色：深青色/蓝色, 个形色1, 深色50%
        System.setProperty("cell_style.header.required.background.color", String.valueOf(IndexedColors.DARK_TEAL.getIndex()));
        // 字体颜色：白色
        System.setProperty("cell_style.header.required.font.color", String.valueOf(IndexedColors.RED.index));

        // 表头列(单元格)样式 - 表头
        // 填充方案编码
        System.setProperty("cell_style.header.fill_pattern_type.code", String.valueOf(FillPatternType.SOLID_FOREGROUND.getCode()));
        // 设置前景色：深青色/蓝色, 个形色1, 深色50%
        System.setProperty("cell_style.header.foreground.color", String.valueOf(IndexedColors.DARK_TEAL.getIndex()));
        // 设置背景色：深青色/蓝色, 个形色1, 深色50%
        System.setProperty("cell_style.header.background.color", String.valueOf(IndexedColors.DARK_TEAL.getIndex()));
        // 字体颜色：白色
        System.setProperty("cell_style.header.font.color", String.valueOf(IndexedColors.WHITE.index));
    }

    @Test
    public void decimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat(CellStyleConstants.STYLE_FORMAT_Accounting);
        System.out.println(decimalFormat.format(new BigDecimal(1000000)));
    }

    @Test
    public void exportData() {
        String[] columns = {
                "username", "nick", "email", "mobile", "sex", "birthdate", "balance"
        };
        String[] columnTitles = {
                "用户名", "昵称", "邮箱", "手机号", "性别", "出生日期", "资产余额"
        };
        Integer[] columnLengths = {
                100, 125, 287, 150, 100, 120, 160
        };
        List<String> requiredColumnTitles = Arrays.asList("用户名", "邮箱", "手机号", "性别");
        String fileName = "用户信息" + System.currentTimeMillis() + ".xlsx";
        List<User> dataColl = new LinkedList<>();
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("伍章红");
        user.setEmail("wywuzh@163.com");
        user.setMobile("14700000000");
        user.setSex("男");
        user.setBirthdate(new Date());
        user.setBalance(new BigDecimal("100000000000.3698"));
        dataColl.add(user);

        Map<String, String[]> columnValidation = new HashMap<>();
        String[] genders = {
                "男", "女", "未知"
        };
        columnValidation.put("性别", genders);

        ExcelExportRequest excelExportRequest = new ExcelExportRequest();
        excelExportRequest.setColumns(columns);
        excelExportRequest.setColumnTitles(columnTitles);
        excelExportRequest.setColumnLengths(columnLengths);
        excelExportRequest.setRequiredColumnTitles(requiredColumnTitles);
        excelExportRequest.setDataColl(dataColl);
        excelExportRequest.setColumnValidation(columnValidation);
        excelExportRequest.setTips("注：用户信息不能删除！");

        OutputStream outputStream = null;
        try {
            File destFile = new File("D:\\data");
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            outputStream = new FileOutputStream(new File(destFile, fileName));

            // 创建workbook
            Workbook workbook = ExcelUtils.createWorkbook(fileName);
            // 创建sheet
            Sheet sheet = ExcelUtils.createSheet(workbook, excelExportRequest);
            // 写入内容
            ExcelUtils.writeData(workbook, sheet, excelExportRequest);

            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Test
    public void exportDataForCellStyle() {
        String[] columns = {
                "username", "nick", "email", "mobile", "sex", "birthdate", "balance"
        };
        String[] columnTitles = {
                "用户名", "昵称", "邮箱", "手机号", "性别", "出生日期", "资产余额"
        };
        Integer[] columnLengths = {
                100, 125, 287, 150, 100, 120, 160
        };
        List<String> requiredColumnTitles = Arrays.asList("用户名", "邮箱", "手机号", "性别");
        String tips = "注意：\n" + "1. 带*号红色字段为必填项\n" + "2. 可修改字段：昵称\n" + "3. 标灰字段请勿修改";
        String fileName = "用户信息" + System.currentTimeMillis() + ".xlsx";
        List<User> dataColl = new LinkedList<>();
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("伍章红");
        user.setEmail("wywuzh@163.com");
        user.setMobile("14700000000");
        user.setSex("男");
        user.setBirthdate(new Date());
        user.setBalance(new BigDecimal("100000000000.3698"));
        dataColl.add(user);
        User user1 = new User();
        user1.setUsername("user1");
        user1.setNick("user1");
        user1.setEmail("user1@163.com");
        user1.setMobile("14700000000");
        user1.setSex("男");
        user1.setBirthdate(new Date());
        user1.setBalance(new BigDecimal(0));
        dataColl.add(user1);

        Map<String, String[]> columnValidation = new HashMap<>();
        String[] genders = {
                "男", "女", "未知"
        };
        columnValidation.put("性别", genders);

        ExcelExportRequest excelExportRequest = new ExcelExportRequest();
        excelExportRequest.setColumns(columns);
        excelExportRequest.setColumnTitles(columnTitles);
        excelExportRequest.setColumnLengths(columnLengths);
        excelExportRequest.setRequiredColumnTitles(requiredColumnTitles);
        excelExportRequest.setDataColl(dataColl);
        excelExportRequest.setColumnValidation(columnValidation);
        excelExportRequest.setTips(tips);

        OutputStream outputStream = null;
        try {
            File destFile = new File("D:\\data");
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            outputStream = new FileOutputStream(new File(destFile, fileName));

            // 创建workbook
            Workbook workbook = ExcelUtils.createWorkbook(fileName);
            // 创建sheet
            Sheet sheet = ExcelUtils.createSheet(workbook, excelExportRequest);
            // 内容列(单元格)样式(只读)
            CellStyle disableCellStyle = CellStyleTools.createContentStyle(workbook);
            disableCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            disableCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            disableCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            /*// 自定义颜色：Color(217, 217, 217)，HEX=D9D9D9
            XSSFColor xssfColor = new XSSFColor();
            xssfColor.setRGB(new byte[] {
                    (byte) 192, (byte) 192, (byte) 192
            });
            disableCellStyle.setFillForegroundColor(xssfColor.getIndex());
            disableCellStyle.setFillBackgroundColor(xssfColor.getIndex());*/
            // 内容列(单元格)样式(可编辑)
            CellStyle enableCellStyle = CellStyleTools.createContentStyle(workbook);
            List<String> enableColumns = Arrays.asList("昵称");
            for (int j = 0; j < columnTitles.length; j++) {
                if (enableColumns.contains(columnTitles[j])) {
                    sheet.setDefaultColumnStyle(j, enableCellStyle);
                } else {
                    sheet.setDefaultColumnStyle(j, disableCellStyle);
                }
            }

            // 写入内容
            ExcelUtils.writeData(workbook, sheet, excelExportRequest);

            Row sheetRow = sheet.getRow(1);
            for (int j = 0; j < columnTitles.length; j++) {
                Cell cell = sheetRow.getCell(j);
                CellStyle cellStyle = cell.getCellStyle();
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                /*cellStyle.setFillForegroundColor(xssfColor.getIndex());
                cellStyle.setFillBackgroundColor(xssfColor.getIndex());*/
                cell.setCellStyle(cellStyle);
            }

            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Test
    public void importTest() throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("D:\\data\\用户信息1668666946221.xlsx"));
            String[] columns = {
                    "username", "nick", "email", "mobile", "sex"
            };
            List<User> dataColl = ExcelUtils.importData(inputStream, User.class, null, 2);
            log.info("导入结果:{}", JsonMapper.buildNonEmptyMapper().toJson(dataColl));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Test
    public void getFieldsListWithAnnotation() {
        List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(User.class, ExcelCell.class);
        log.info("字段：{}", fieldList);
    }

}
