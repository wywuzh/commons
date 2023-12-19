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

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.wywuzh.commons.core.poi.entity.User;
import com.github.wywuzh.commons.core.poi.modle.ExcelExportRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 类EasyExcelUtilsTest的实现描述：EasyExcelUtils测试类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2023-12-18 14:43:41
 * @version v2.7.8
 * @since JDK 1.8
 */
@Slf4j
public class EasyExcelUtilsTest {

    /**
     * 模板文件
     */
    public static final String TEMPLATE_FILE = "/templates/files/导出模板.xlsx";

    @Test
    public void exportDataTest() {
        String[] columns = {
                "username", "nick", "email", "mobile", "sex", "birthdate", "balance"
        };
        String[] columnTitles = {
                "用户名", "昵称", "邮箱", "手机号", "性别", "出生日期", "资产余额"
        };
        Integer[] columnLengths = {
                100, 125, 287, 150, 100, 120, 120
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

        try {
            File destFile = new File("D:\\data");
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            File xlsxFile = new File(destFile, fileName);
            if (xlsxFile.exists()) {
                FileUtils.forceDelete(xlsxFile);
            }

            // 模板文件
            InputStream templateInputStream = this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_FILE);
            // 1. 根据目标文件、模板文件创建ExcelWriter对象
            ExcelWriter excelWriter = EasyExcelUtils.createExcelWriter(xlsxFile, templateInputStream);
            // 2. 创建WriteSheet对象
            WriteSheet writeSheet = EasyExcelUtils.createWriteSheet(excelExportRequest);
            // 3. 写入数据
            EasyExcelUtils.writeData(excelWriter, writeSheet, dataColl, columns);
            // 4. 关闭IO
            excelWriter.finish();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void exportDataTest2() {
        String[] columns = {
                "username", "nick", "email", "mobile", "sex", "birthdate", "balance"
        };
        String[] columnTitles = {
                "用户名", "昵称", "邮箱", "手机号", "性别", "出生日期", "资产余额"
        };
        Integer[] columnLengths = {
                100, 125, 287, 150, 100, 120, 120
        };
        String fileName = "用户信息Test2" + System.currentTimeMillis() + ".xlsx";
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

        try {
            File destFile = new File("D:\\data");
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            File xlsxFile = new File(destFile, fileName);
            if (xlsxFile.exists()) {
                FileUtils.forceDelete(xlsxFile);
            }

            EasyExcelUtils.writer(xlsxFile, "sheet", dataColl, columns, columnTitles, columnLengths);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
