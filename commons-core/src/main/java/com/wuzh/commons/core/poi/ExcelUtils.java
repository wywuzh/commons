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

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 类ExcelUtils的实现描述：Excel 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-05-29 10:54:36
 * @version v2.2.6
 * @since JDK 1.8
 */
public class ExcelUtils {

    /**
     * 导出数据
     *
     * @param request 请求信息
     */
    public static void exportData(HttpServletRequest request, HttpServletResponse response, ExcelRequest excelRequest, String fileName) throws IOException {
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

        // TODO
        Workbook workbook = WorkbookFactory.create(new File(fileName));
        workbook.write(outputStream);
        outputStream.close();
    }

    /**
     * 导入数据
     *
     * @param request 请求信息
     */
    public static void importData(ExcelRequest request, InputStream inputStream) {
    }


}