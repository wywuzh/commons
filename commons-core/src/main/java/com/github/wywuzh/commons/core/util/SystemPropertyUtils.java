/*
 * Copyright 2015-2022 the original author or authors.
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
package com.github.wywuzh.commons.core.util;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 类SystemPropertyUtils的实现描述：系统属性
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-07-29 17:45:52
 * @version v2.7.0
 * @since JDK 1.8
 */
public class SystemPropertyUtils {

    public static String getProperty(String key) {
        Assert.notBlank(key, "key must not be blank");

        return System.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return System.getProperty(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static String getString(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getProperty(key);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getProperty(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Byte getByte(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getByte(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Byte getByte(String key, byte defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getByte(key, new Byte(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Byte getByte(String key, Byte defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Byte.valueOf(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Short getShort(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getShort(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Short getShort(String key, short defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getShort(key, new Short(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Short getShort(String key, Short defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Short.valueOf(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Integer getInt(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getInteger(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Integer getInt(String key, int defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getInteger(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Integer getInteger(String key, Integer defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Long getLong(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getLong(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Long getLong(String key, long defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getLong(key, new Long(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Long getLong(String key, Long defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Long.valueOf(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Float getFloat(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getFloat(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Float getFloat(String key, float defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getFloat(key, new Float(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Float getFloat(String key, Float defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Float.valueOf(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Double getDouble(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getDouble(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Double getDouble(String key, double defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getDouble(key, new Double(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Double getDouble(String key, Double defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Double.valueOf(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static BigDecimal getBigDecimal(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getBigDecimal(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return new BigDecimal(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static BigInteger getBigInteger(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getBigInteger(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return new BigInteger(value);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key 属性key
     * @return
     */
    public static Boolean getBoolean(String key) {
        Assert.notBlank(key, "key must not be blank");

        return getBoolean(key, null);
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Boolean getBoolean(String key, boolean defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        return getBoolean(key, BooleanUtils.toBoolean(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     *
     * @param key          属性key
     * @param defaultValue 如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * @return
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        Assert.notBlank(key, "key must not be blank");

        String value = getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Boolean.valueOf(value);
    }


    /**
     * @return 字体名称
     */
    public static String getFontName() {
        return System.getProperty("font.name", "宋体");
    }

    /**
     * @return 字体大小
     */
    public static short getFontHeight() {
        return Short.parseShort(System.getProperty("font.height", "11"));
    }

    // =========================================== POI属性 >>> Start ===========================================

    /**
     * 表头列(单元格)样式 - 表头提示信息：填充方案编码
     *
     * @return 填充方案编码
     */
    public static Short getHeaderStyleTipsForFillPatternCode() {
        String property = System.getProperty("cell_style.header.tips.fill_pattern_type.code");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//FillPatternType.SOLID_FOREGROUND.getCode();
    }

    /**
     * 表头列(单元格)样式 - 表头提示信息：前景色
     *
     * @return 前景色
     */
    public static Short getHeaderStyleTipsForFillForegroundColor() {
        String property = System.getProperty("cell_style.header.tips.foreground.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//IndexedColors.YELLOW.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头提示信息：背景色
     *
     * @return 背景色
     */
    public static Short getHeaderStyleTipsForFillBackgroundColor() {
        String property = System.getProperty("cell_style.header.tips.background.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//IndexedColors.YELLOW.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头提示信息：字体颜色
     *
     * @return 字体颜色
     */
    public static Short getHeaderStyleTipsForFontColor() {
        String property = System.getProperty("cell_style.header.tips.font.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//Font.COLOR_RED;
    }


    /**
     * 表头列(单元格)样式 - 表头必填字段：填充方案编码
     *
     * @return 填充方案编码
     */
    public static Short getHeaderStyleRequiredForFillPatternCode() {
        String property = System.getProperty("cell_style.header.required.fill_pattern_type.code");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//FillPatternType.SOLID_FOREGROUND.getCode();
    }

    /**
     * 表头列(单元格)样式 - 表头必填字段：前景色
     *
     * @return 前景色
     */
    public static Short getHeaderStyleRequiredForFillForegroundColor() {
        String property = System.getProperty("cell_style.header.required.foreground.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//IndexedColors.BRIGHT_GREEN.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头必填字段：背景色
     *
     * @return 背景色
     */
    public static Short getHeaderStyleRequiredForFillBackgroundColor() {
        String property = System.getProperty("cell_style.header.required.background.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//IndexedColors.AUTOMATIC.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头必填字段：字体颜色
     *
     * @return 字体颜色
     */
    public static short getHeaderStyleRequiredForFontColor() {
        String property = System.getProperty("cell_style.header.required.font.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return Font.COLOR_RED;
    }


    /**
     * 表头列(单元格)样式 - 表头字段：填充方案编码
     *
     * @return 填充方案编码
     */
    public static Short getHeaderStyleForFillPatternCode() {
        String property = System.getProperty("cell_style.header.fill_pattern_type.code");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//FillPatternType.SOLID_FOREGROUND.getCode();
    }

    /**
     * 表头列(单元格)样式 - 表头字段：前景色
     *
     * @return 前景色
     */
    public static Short getHeaderStyleForFillForegroundColor() {
        String property = System.getProperty("cell_style.header.foreground.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//IndexedColors.AUTOMATIC.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头字段：背景色
     *
     * @return 背景色
     */
    public static Short getHeaderStyleForFillBackgroundColor() {
        String property = System.getProperty("cell_style.header.background.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//IndexedColors.AUTOMATIC.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头字段：字体颜色
     *
     * @return 字体颜色
     */
    public static Short getHeaderStyleForFontColor() {
        String property = System.getProperty("cell_style.header.font.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//Font.COLOR_RED;
    }

    // =========================================== POI属性 <<< End ===========================================

}
