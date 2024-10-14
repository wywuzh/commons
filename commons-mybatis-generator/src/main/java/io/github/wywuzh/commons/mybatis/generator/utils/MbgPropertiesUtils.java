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
package io.github.wywuzh.commons.mybatis.generator.utils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 类Properties的实现描述：Properties属性工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-07-14 18:17:08
 * @version v2.4.5
 * @since JDK 1.8
 */
public class MbgPropertiesUtils {

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static String getProperty(Properties properties, String key, String defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : val;
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Boolean getProperty(Properties properties, String key, Boolean defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Boolean.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Byte getProperty(Properties properties, String key, Byte defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Byte.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Short getProperty(Properties properties, String key, Short defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Short.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Integer getProperty(Properties properties, String key, Integer defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Integer.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Long getProperty(Properties properties, String key, Long defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Long.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Double getProperty(Properties properties, String key, Double defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Double.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static Float getProperty(Properties properties, String key, Float defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : Float.valueOf(val);
    }

    /**
     * 获取属性值
     *
     * @param properties   属性配置
     * @param key          属性名
     * @param defaultValue 默认属性值
     * @return the value in this property list with the specified key value.
     */
    public static BigDecimal getProperty(Properties properties, String key, BigDecimal defaultValue) {
        String val = properties.getProperty(key);
        return StringUtils.isBlank(val) ? defaultValue : new BigDecimal(val);
    }

    /**
     * 拆分属性值
     *
     * @param val 属性值
     * @return
     * @since v2.7.8
     */
    public static List<String> split(String val) {
        List<String> resultList = new LinkedList<>();

        if (StringUtils.isNotBlank(val)) {
            // 特殊符号处理：空格、换行、tab制表符、全角
            val = StringUtils.replaceEach(val, new String[] {
                    " ", "\n", "\t"
            }, new String[] {
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY
            });
            // 兼容全角和半角的逗号分隔符
            val = StringUtils.replace(val, "，", ",");
            String[] dataArr = StringUtils.split(val, ",");
            for (String str : dataArr) {
                str = str.trim();
                if (str == null || "".equals(str)) {
                    continue;
                }
                resultList.add(str);
            }
        }
        return resultList;
    }

}
