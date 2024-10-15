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
package io.github.wywuzh.commons.core.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.wywuzh.commons.core.json.gson.GsonUtil;

/**
 * 类StringHelperTest的实现描述：StringHelper测试类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-08-12 13:52:40
 * @version v2.4.8
 * @since JDK 1.8
 */
public class StringHelperTest {
    private final Logger logger = LoggerFactory.getLogger(StringHelperTest.class);

    // 将number整型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
    @Test
    public void fillZero() {
        System.out.println(StringHelper.fillZero(3, 2L));
    }

    @Test
    public void spellToStr() {
        System.out.println(StringHelper.spellToStr(3, "t"));

        System.out.println(StringHelper.spellToStr(3, "?", ","));
    }

    @Test
    public void spellToArray() {
        System.out.println(GsonUtil.format(StringHelper.spellToArray(2, "t")));
    }

    @Test
    public void spellToList() {
        System.out.println(GsonUtil.format(StringHelper.spellToList(2, "?")));
    }

    // 将数据库字段按照驼峰命名规则转换为实体类字段
    @Test
    public void convertToField() {
        String columnName = "USER_ID";
        String field = StringHelper.convertToField(columnName);
        System.out.println("convertToField方法：" + columnName + " ===> " + field);
    }

    // 将驼峰命名规则的实体类字段转换为数据库字段
    @Test
    public void convertToColumn() {
        String field = "userId";
        String column = StringHelper.convertToColumn(field);
        System.out.println("convertToField方法：" + field + " ===> " + column);
    }

    // 字符串长度
    @Test
    public void lengthTest() {
        String content = "是否启用JSR303标准注解验证";
        int length = StringHelper.length(content);
        logger.info("StringHelper.length(\"{}\")={}", content, length);
    }

    // 字符串长度
    @Test
    public void stringRealLength() {
        String content = "是否启用JSR303标准注解验证";
        int length = StringHelper.stringRealLength(content);
        logger.info("StringHelper.length(\"{}\")={}", content, length);
    }

}
