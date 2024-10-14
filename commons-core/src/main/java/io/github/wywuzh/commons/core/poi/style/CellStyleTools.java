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
package io.github.wywuzh.commons.core.poi.style;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import io.github.wywuzh.commons.core.poi.constants.CellStyleConstants;
import io.github.wywuzh.commons.core.util.SystemPropertyUtils;

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
        return null;// FillPatternType.SOLID_FOREGROUND.getCode();
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
        return null;// IndexedColors.YELLOW.getIndex();
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
        return null;// IndexedColors.YELLOW.getIndex();
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
        return null;// Font.COLOR_RED;
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
        return null;// FillPatternType.SOLID_FOREGROUND.getCode();
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
        return null;// IndexedColors.BRIGHT_GREEN.getIndex();
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
        return null;// IndexedColors.AUTOMATIC.getIndex();
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
        return null;// FillPatternType.SOLID_FOREGROUND.getCode();
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
        return null;// IndexedColors.AUTOMATIC.getIndex();
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
        return null;// IndexedColors.AUTOMATIC.getIndex();
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
        return null;// Font.COLOR_RED;
    }
    // =========================================== POI属性 <<< End ===========================================

    /**
     * 设置表头列(单元格)样式
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:26 )
     */
    public static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle cellStyle = createCellStyle(workbook);
        // [v2.7.0]增加底色
        // 填充方案：全部前景色
        Short fillPatternCode = CellStyleTools.getHeaderStyleForFillPatternCode();
        if (fillPatternCode != null) {
            cellStyle.setFillPattern(FillPatternType.forInt(fillPatternCode));
        }
        // 设置前景色
        Short fillForegroundColor = CellStyleTools.getHeaderStyleForFillForegroundColor();
        if (fillForegroundColor != null) {
            cellStyle.setFillForegroundColor(fillForegroundColor);
        }
        // 设置背景色
        Short fillBackgroundColor = CellStyleTools.getHeaderStyleForFillBackgroundColor();
        if (fillBackgroundColor != null) {
            cellStyle.setFillBackgroundColor(fillBackgroundColor);
        }
        // 注：前景色/背景色不为空时，填充方案不可为空
        if (fillPatternCode == null && (fillForegroundColor != null || fillBackgroundColor != null)) {
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        // 设置字体
        Font font = workbook.createFont();
        Short fontColor = CellStyleTools.getHeaderStyleForFontColor();
        if (fontColor != null) {
            font.setColor(fontColor); // Font.COLOR_RED
        }
        // 文本加粗
        font.setBold(true);
        font.setFontName(SystemPropertyUtils.getFontName());
        font.setFontHeightInPoints(SystemPropertyUtils.getFontHeight());
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置表头列(单元格)样式：表头提示信息
     *
     * @param workbook 工作簿对象
     * @return
     */
    public static CellStyle createHeaderStyleForTips(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        // 内容左对齐 、垂直居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // [v2.7.0]增加底色
        // 填充方案：全部前景色
        Short fillPatternCode = Optional.ofNullable(CellStyleTools.getHeaderStyleTipsForFillPatternCode()).orElse(FillPatternType.SOLID_FOREGROUND.getCode());
        cellStyle.setFillPattern(FillPatternType.forInt(fillPatternCode)); // FillPatternType.SOLID_FOREGROUND
        // 设置前景色：默认黄色
        Short fillForegroundColor = Optional.ofNullable(CellStyleTools.getHeaderStyleTipsForFillForegroundColor()).orElse(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillForegroundColor(fillForegroundColor); // IndexedColors.YELLOW.getIndex()
        // 设置背景色：默认黄色
        Short fillBackgroundColor = Optional.ofNullable(CellStyleTools.getHeaderStyleTipsForFillBackgroundColor()).orElse(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillBackgroundColor(fillBackgroundColor); // IndexedColors.YELLOW.getIndex()

        // 设置字体
        Font font = workbook.createFont();
        Short fontColor = Optional.ofNullable(CellStyleTools.getHeaderStyleTipsForFontColor()).orElse(Font.COLOR_RED);
        font.setColor(fontColor); // Font.COLOR_RED
        font.setBold(true);
        font.setFontName(SystemPropertyUtils.getFontName());
        font.setFontHeightInPoints(SystemPropertyUtils.getFontHeight());
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置表头列(单元格)样式：表头必填字段 - 红色提示
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:26 )
     */
    public static CellStyle createHeaderStyleForRequired(Workbook workbook) {
        CellStyle cellStyle = createCellStyle(workbook);
        // [v2.7.0]增加底色
        // 填充方案：全部前景色
        Short fillPatternCode = CellStyleTools.getHeaderStyleRequiredForFillPatternCode();
        if (fillPatternCode != null) {
            cellStyle.setFillPattern(FillPatternType.forInt(fillPatternCode)); // FillPatternType.SOLID_FOREGROUND.getCode()
        }
        // 设置前景色
        Short fillForegroundColor = CellStyleTools.getHeaderStyleRequiredForFillForegroundColor();
        if (fillForegroundColor != null) {
            cellStyle.setFillForegroundColor(fillForegroundColor); // IndexedColors.YELLOW.getIndex()
        }
        // 设置背景色
        Short fillBackgroundColor = CellStyleTools.getHeaderStyleRequiredForFillBackgroundColor();
        if (fillBackgroundColor != null) {
            cellStyle.setFillBackgroundColor(fillBackgroundColor); // IndexedColors.YELLOW.getIndex()
        }
        // 注：前景色/背景色不为空时，填充方案不可为空
        if (fillPatternCode == null && (fillForegroundColor != null || fillBackgroundColor != null)) {
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        // 设置字体
        Font font = workbook.createFont();
        font.setColor(CellStyleTools.getHeaderStyleRequiredForFontColor()); // Font.COLOR_RED
        // 文本加粗
        font.setBold(true);
        font.setFontName(SystemPropertyUtils.getFontName());
        font.setFontHeightInPoints(SystemPropertyUtils.getFontHeight());
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置内容列(单元格)样式
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:26 )
     */
    public static CellStyle createContentStyle(Workbook workbook) {
        CellStyle cellStyle = createCellStyle(workbook);

        // 设置字体
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_NORMAL);
        font.setFontName(SystemPropertyUtils.getFontName());
        font.setFontHeightInPoints(SystemPropertyUtils.getFontHeight());
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置数据列(单元格)样式
     *
     * @param workbook 工作簿对象
     * @return
     * @author 伍章红 2015年4月28日 ( 下午3:07:53 )
     */
    public static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        // 内容居中对齐 、垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

}
