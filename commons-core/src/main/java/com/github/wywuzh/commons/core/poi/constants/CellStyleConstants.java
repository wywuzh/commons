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
package com.github.wywuzh.commons.core.poi.constants;

/**
 * 类CellStyleConstants的实现描述：CellStyle常量
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-08-04 10:22:29
 * @version v2.7.0
 * @since JDK 1.8
 */
public class CellStyleConstants {

    /**
     * 表头列(单元格)样式 - 表头字段：填充方案编码
     */
    public static final String HEADER_STYLE_FILL_PATTERN_CODE = "cell_style.header.fill_pattern_type.code";
    /**
     * 表头列(单元格)样式 - 表头字段：前景色
     */
    public static final String HEADER_STYLE_FOREGROUND_COLOR = "cell_style.header.foreground.color";
    /**
     * 表头列(单元格)样式 - 表头字段：背景色
     */
    public static final String HEADER_STYLE_BACKGROUND_COLOR = "cell_style.header.background.color";
    /**
     * 表头列(单元格)样式 - 表头字段：字体颜色
     */
    public static final String HEADER_STYLE_FONT_COLOR = "cell_style.header.font.color";

    /**
     * 表头列(单元格)样式 - 表头提示信息：填充方案编码
     */
    public static final String HEADER_STYLE_TIPS_FILL_PATTERN_CODE = "cell_style.header.tips.fill_pattern_type.code";
    /**
     * 表头列(单元格)样式 - 表头提示信息：前景色
     */
    public static final String HEADER_STYLE_TIPS_FOREGROUND_COLOR = "cell_style.header.tips.foreground.color";
    /**
     * 表头列(单元格)样式 - 表头提示信息：背景色
     */
    public static final String HEADER_STYLE_TIPS_BACKGROUND_COLOR = "cell_style.header.tips.background.color";
    /**
     * 表头列(单元格)样式 - 表头提示信息：字体颜色
     */
    public static final String HEADER_STYLE_TIPS_FONT_COLOR = "cell_style.header.tips.font.color";

    /**
     * 表头列(单元格)样式 - 表头必填字段：填充方案编码
     */
    public static final String HEADER_STYLE_REQUIRED_FILL_PATTERN_CODE = "cell_style.header.required.fill_pattern_type.code";
    /**
     * 表头列(单元格)样式 - 表头必填字段：前景色
     */
    public static final String HEADER_STYLE_REQUIRED_FOREGROUND_COLOR = "cell_style.header.required.foreground.color";
    /**
     * 表头列(单元格)样式 - 表头必填字段：背景色
     */
    public static final String HEADER_STYLE_REQUIRED_BACKGROUND_COLOR = "cell_style.header.required.background.color";
    /**
     * 表头列(单元格)样式 - 表头必填字段：字体颜色
     */
    public static final String HEADER_STYLE_REQUIRED_FONT_COLOR = "cell_style.header.required.font.color";

    // ============ 单元格格式
    public static final String STYLE_FORMAT_String = "TEXT"; // 字符
    public static final String STYLE_FORMAT_Percent = "0.00%"; // 百分比
    public static final String STYLE_FORMAT_Integer = "0"; // 整型数值
    public static final String STYLE_FORMAT_BigDecimal = "0.00"; // 2位小数
    public static final String STYLE_FORMAT_Money = "###,##0.00"; // 金额，保留2位小数
    public static final String STYLE_FORMAT_Accounting = "_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"-\"??_ ;_ @_ "; // 会计专用，保留2位小数
    public static final String STYLE_FORMAT_Date = "yyyy-MM-dd"; // 日期：yyyy-MM-dd格式
    public static final String STYLE_FORMAT_Time = "hh:mm:ss"; // 时间：HH:mm:ss格式
    public static final String STYLE_FORMAT_DateTime = "yyyy-MM-dd hh:mm:ss"; // 日期时间：yyyy-MM-dd HH:mm:ss格式

}
