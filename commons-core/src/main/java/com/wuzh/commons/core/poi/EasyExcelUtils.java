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

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wuzh.commons.core.reflect.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static <T> void writer(ExcelWriter excelWriter, String sheetName, List<T> list, String[] columns, String[] columnTitles) {
        WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName).build();

        // 设置表头
        List<List<String>> head = new LinkedList<>();
        for (String columnTitle : columnTitles) {
            head.add(Collections.singletonList(columnTitle));
        }
        writeSheet.setHead(head);

        // 设置字段值
        List<List<Object>> data = new LinkedList<>();
        for (T item : list) {
            List<Object> objects = new LinkedList<>();
            if (item instanceof Map) {
                for (String column : columns) {
                    objects.add(((Map) item).get(column));
                }
            } else {
                for (String column : columns) {
                    try {
                        objects.add(ReflectUtils.getValue(item, column));
                    } catch (IllegalAccessException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            data.add(objects);
        }
        excelWriter.write(data, writeSheet);
    }

}