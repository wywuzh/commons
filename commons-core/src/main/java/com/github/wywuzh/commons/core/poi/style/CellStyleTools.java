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
package com.github.wywuzh.commons.core.poi.style;

import com.github.wywuzh.commons.core.poi.constants.CellStyleConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Font;

/**
 * 类CellStyleTools的实现描述：CellStyle工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-08-11 12:39:54
 * @version v2.7.0
 * @since JDK 1.8
 */
public class CellStyleTools {

    // =========================================== POI属性 >>> Start ===========================================

    /**
     * 表头列(单元格)样式 - 表头提示信息：填充方案编码
     *
     * @return 填充方案编码
     */
    public static Short getHeaderStyleTipsForFillPatternCode() {
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_TIPS_FILL_PATTERN_CODE);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_TIPS_FOREGROUND_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_TIPS_BACKGROUND_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_TIPS_FONT_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_REQUIRED_FILL_PATTERN_CODE);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_REQUIRED_FOREGROUND_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_REQUIRED_BACKGROUND_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_REQUIRED_FONT_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_FILL_PATTERN_CODE);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_FOREGROUND_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_BACKGROUND_COLOR);
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
        String property = System.getProperty(CellStyleConstants.HEADER_STYLE_FONT_COLOR);
        if (StringUtils.isNotBlank(property)) {
            return Short.parseShort(property);
        }
        return null;//Font.COLOR_RED;
    }

    // =========================================== POI属性 <<< End ===========================================

}
