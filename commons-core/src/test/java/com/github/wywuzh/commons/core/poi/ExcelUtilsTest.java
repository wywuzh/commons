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

import com.github.wywuzh.commons.core.json.jackson.JsonMapper;
import com.github.wywuzh.commons.core.poi.entity.User;
import com.github.wywuzh.commons.core.poi.modle.ExcelRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * 类ExcelUtilsTest的实现描述：Excel 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-25 18:10:04
 * @version v2.3.7
 * @since JDK 1.8
 */
@Slf4j
public class ExcelUtilsTest {

    @Test
    public void exportTest() {
        String[] columns = {"username", "nick", "email", "mobile", "sex"};
        String[] columnTitles = {"用户名", "昵称", "邮箱", "手机号", "性别"};
        Integer[] columnLengths = {100, 125, 287, 150, 100};
        List<String> requiredColumnTitles = Arrays.asList("用户名", "昵称", "邮箱", "手机号", "性别");
        String fileName = "用户信息.xlsx";
        List<User> dataColl = new LinkedList<>();
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("伍章红");
        user.setEmail("wywuzh@163.com");
        user.setMobile("14700000000");
        user.setSex("男");
        dataColl.add(user);

        Map<String, String[]> columnValidation = new HashMap<>();
        String[] genders = {"男", "女", "未知"};
        columnValidation.put("性别", genders);

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setColumns(columns);
        excelRequest.setColumnTitles(columnTitles);
        excelRequest.setColumnLengths(columnLengths);
        excelRequest.setRequiredColumnTitles(requiredColumnTitles);
        excelRequest.setDataColl(dataColl);
        excelRequest.setColumnValidation(columnValidation);
        excelRequest.setTips("注：用户信息不能删除！");

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
            Sheet sheet = ExcelUtils.createSheet(workbook, excelRequest);
            // 写入内容
            ExcelUtils.writeData(workbook, sheet, excelRequest);

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
            inputStream = new FileInputStream(new File("D:\\data\\用户信息.xlsx"));
            String[] columns = {
                    "username", "nick", "email", "mobile", "sex"
            };
            List<User> dataColl = ExcelUtils.importData(inputStream, User.class, columns, 2);
            log.info("导入结果:{}", JsonMapper.buildNonEmptyMapper().toJson(dataColl));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
