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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 类SystemPropertyUtils的实现描述：系统属性
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-07-29 17:45:52
 * @version v2.7.0
 * @since JDK 1.8
 */
public class SystemPropertyUtils {

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
        return Short.parseShort(System.getProperty("font.height", "12"));
    }

    // =========================================== POI属性 >>> Start ===========================================

    /**
     * 表头列(单元格)样式 - 表头提示信息：填充方案编码
     *
     * @return 填充方案编码
     */
    public static int getHeaderStyleTipsForFillPatternCode() {
        String property = System.getProperty("cell_style.header.tips.fill_pattern_type.code");
        if (StringUtils.isNotBlank(property)) {
            return Integer.parseInt(property);
        }
        return FillPatternType.SOLID_FOREGROUND.getCode();
    }

    /**
     * 表头列(单元格)样式 - 表头提示信息：前景色
     *
     * @return 前景色
     */
    public static short getHeaderStyleTipsForFillForegroundColor() {
        String property = System.getProperty("cell_style.header.tips.foreground.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return IndexedColors.YELLOW.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头提示信息：背景色
     *
     * @return 背景色
     */
    public static short getHeaderStyleTipsForFillBackgroundColor() {
        String property = System.getProperty("cell_style.header.tips.background.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return IndexedColors.YELLOW.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头提示信息：字体颜色
     *
     * @return 字体颜色
     */
    public static short getHeaderStyleTipsForFontColor() {
        String property = System.getProperty("cell_style.header.tips.font.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return Font.COLOR_RED;
    }


    /**
     * 表头列(单元格)样式 - 表头必填字段：填充方案编码
     *
     * @return 填充方案编码
     */
    public static int getHeaderStyleRequiredForFillPatternCode() {
        String property = System.getProperty("cell_style.header.required.fill_pattern_type.code");
        if (StringUtils.isNotBlank(property)) {
            return Integer.parseInt(property);
        }
        return FillPatternType.SOLID_FOREGROUND.getCode();
    }

    /**
     * 表头列(单元格)样式 - 表头必填字段：前景色
     *
     * @return 前景色
     */
    public static short getHeaderStyleRequiredForFillForegroundColor() {
        String property = System.getProperty("cell_style.header.required.foreground.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return IndexedColors.WHITE.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头必填字段：背景色
     *
     * @return 背景色
     */
    public static short getHeaderStyleRequiredForFillBackgroundColor() {
        String property = System.getProperty("cell_style.header.required.background.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return IndexedColors.AUTOMATIC.getIndex();
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
    public static int getHeaderStyleForFillPatternCode() {
        String property = System.getProperty("cell_style.header.fill_pattern_type.code");
        if (StringUtils.isNotBlank(property)) {
            return Integer.parseInt(property);
        }
        return FillPatternType.SOLID_FOREGROUND.getCode();
    }

    /**
     * 表头列(单元格)样式 - 表头字段：前景色
     *
     * @return 前景色
     */
    public static short getHeaderStyleForFillForegroundColor() {
        String property = System.getProperty("cell_style.header.foreground.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return IndexedColors.AUTOMATIC.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头字段：背景色
     *
     * @return 背景色
     */
    public static short getHeaderStyleForFillBackgroundColor() {
        String property = System.getProperty("cell_style.header.background.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return IndexedColors.AUTOMATIC.getIndex();
    }

    /**
     * 表头列(单元格)样式 - 表头字段：字体颜色
     *
     * @return 字体颜色
     */
    public static short getHeaderStyleForFontColor() {
        String property = System.getProperty("cell_style.header.font.color");
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return Font.COLOR_RED;
    }

    // =========================================== POI属性 <<< End ===========================================

}
