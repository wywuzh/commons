/*
 * Copyright 2015-2023 the original author or authors.
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

import com.github.wywuzh.commons.core.json.gson.GsonUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类StringHelper.java的实现描述：字符串
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015-8-6 下午4:08:54
 * @version v1.0.0
 * @since JDK 1.7
 */
public class StringHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringHelper.class);

    /**
     * 返回指定length位数的0字符串
     *
     * <pre>
     * StringHelper.getZeroString(2) = "00"
     * StringHelper.getZeroString(3) = "000"
     * StringHelper.getZeroString(5) = "00000"
     * </pre>
     *
     * @param length
     * @return
     */
    public static String getZeroString(final int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append("0");
        }
        return buffer.toString();
    }

    /**
     * 将number整型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
     *
     * <pre>
     * StringHelper.fillZero(2, 9)  = "09"
     * StringHelper.fillZero(2, 13) = "13"
     * StringHelper.fillZero(3, 9)  = "009"
     * StringHelper.fillZero(3, 13) = "013"
     * </pre>
     *
     * @param length 指定length长度
     * @param number 需要转换的整型数字
     * @return
     */
    public static String fillZero(final int length, final int number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 将number长整型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
     *
     * <pre>
     * StringHelper.fillZero(2, 9)  = "09"
     * StringHelper.fillZero(2, 13) = "13"
     * StringHelper.fillZero(3, 9)  = "009"
     * StringHelper.fillZero(3, 13) = "013"
     * </pre>
     *
     * @param length 指定length长度
     * @param number 需要转换的长整型数字
     * @return
     */
    public static String fillZero(final int length, final long number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 当content内容不足length长度时，在内容后面添加剩余长度的str字符
     *
     * @param content 内容
     * @param length  期望内容长度
     * @param str     补充字符
     * @return
     */
    public static String fill(String content, int length, char str) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        result.append(content);
        for (int i = content.length(); i <= length; i++) {
            result.append(str);
        }
        return result.toString();
    }

    /**
     * 将str拼写成length长度的字符
     *
     * <pre>
     * StringHelper.spellStr(2, "*") = "**"
     * StringHelper.spellStr(2, "?") = "??"
     * </pre>
     *
     * @param length 指定length长度
     * @param str    目标字符
     * @return
     */
    public static String spellToStr(final int length, final String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 将str拼写成length长度的字符，拼写时采用separator作分隔符
     *
     * <pre>
     * StringHelper.spellStr(2, "*", ",") = "*,*"
     * StringHelper.spellStr(2, "?", ",") = "?,?"
     * </pre>
     *
     * @param length    指定length长度
     * @param str       目标字符
     * @param separator 分隔符
     * @return
     */
    public static String spellToStr(final int length, final String str, final String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 将str拼写成length长度的字符数组
     *
     * <pre>
     * StringHelper.spellToArray(2, "*") = ["*", "*"]
     * StringHelper.spellToArray(2, "?") = ["?", "?"]
     * </pre>
     *
     * @param length 指定length长度
     * @param str    目标字符
     * @return
     */
    public static String[] spellToArray(final int length, final String str) {
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            array[i] = str;
        }
        return array;
    }

    /**
     * 将str拼写成length长度的字符集合
     *
     * <pre>
     * StringHelper.spellToList(2, "*") = ["*", "*"]
     * StringHelper.spellToList(2, "?") = ["?", "?"]
     * </pre>
     *
     * @param length 指定length长度
     * @param str    目标字符
     * @return
     */
    public static List<String> spellToList(final int length, final String str) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            list.add(str);
        }
        return list;
    }

    /**
     * 将number长整型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
     *
     * @param length 指定length长度
     * @param number 需要转换的长整型数字
     * @return
     */
    public static String formatToString(int length, long number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 将number浮点型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
     *
     * @param length 指定length长度
     * @param number 需要转换的浮点型数字
     * @return
     */
    public static String formatToString(int length, double number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 将object型值转为指定length长度的字符，如果值得长度不足，将自动补零处理
     *
     * @param length 指定length长度
     * @param object 需要转换的object型值
     * @return
     */
    public static String formatToString(int length, Object object) {
        return new DecimalFormat(getZeroString(length)).format(object);
    }

    /**
     * 使用gzip进行压缩
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * <p>
     * Description:使用gzip进行解压缩
     * </p>
     *
     * @param compressedStr
     * @return
     */
    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        return decompressed;
    }

    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static final String zip(String str) {
        if (str == null)
            return null;
        byte[] compressed;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        String compressedStr = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            compressed = out.toByteArray();
            compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
        } catch (IOException e) {
            compressed = null;
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return compressedStr;
    }

    /**
     * 使用zip进行解压缩
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
    public static final String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed = null;
        try {
            byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            decompressed = null;
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return decompressed;
    }

    public static String htmlEncode(String value) {
        String result = "";
        if (StringUtils.isNotBlank(value)) {
            result = value.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll(" ", "&nbsp;").replaceAll("\r?\n", "<br/>");
        }
        return result;
    }

    public static String htmlDecode(String value) {
        String result = "";
        if (StringUtils.isNotBlank(value)) {
            result = value.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replace("&nbsp;", " ");
        }
        return result;
    }

    /**
     * 字符串编码(默认使用UTF-8)
     */
    public static String stringEncode(String value) {
        return stringEncode(value, "UTF-8");
    }

    public static String stringEncode(String value, String encoding) {
        String result = null;
        if (StringUtils.isNotBlank(value)) {
            try {
                if (StringUtils.isBlank(encoding)) {
                    encoding = "UTF-8";
                }
                result = new String(value.getBytes("ISO-8859-1"), encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * unicode编码转换为汉字
     *
     * @param unicodeStr 待转化的编码
     * @return 返回转化后的汉子
     */
    public static String unicodeToCN(String unicodeStr) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicodeStr);
        char ch;
        while (matcher.find()) {
            // group
            String group = matcher.group(2);
            // ch:'李四'
            ch = (char) Integer.parseInt(group, 16);
            // group1
            String group1 = matcher.group(1);
            unicodeStr = unicodeStr.replace(group1, ch + "");
        }

        return unicodeStr.replace("\\", "").trim();
    }

    /**
     * 首字母改为大写
     *
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        String firstStr = StringUtils.substring(str, 0, 1);
        String lastStr = StringUtils.substring(str, 1);
        return StringUtils.join(firstStr.toUpperCase(), lastStr);
    }

    /**
     * 首字母改为小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        String firstStr = StringUtils.substring(str, 0, 1);
        String lastStr = StringUtils.substring(str, 1);
        return StringUtils.join(firstStr.toLowerCase(), lastStr);
    }

    /**
     * 将数据库字段按照驼峰命名规则转换为实体类字段，首字母小写
     *
     * @param column 数据库字段值
     * @return
     * @since 2.2.6
     */
    public static String convertToField(String column) {
        if (StringUtils.isBlank(column)) {
            return column;
        }
        String[] split = column.split("_");
        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            // 去空格
            str = str.toLowerCase().trim();
            // 将首字母改为小写
            String firstStr = StringUtils.substring(str, 0, 1);
            String lastStr = StringUtils.substring(str, 1);
            sb.append(firstStr.toUpperCase()).append(lastStr);
        }
        return firstCharToLowerCase(sb.toString());
    }

    /**
     * 将驼峰命名规则的实体类字段转换为数据库字段
     *
     * @param field 驼峰命名规则的实体类字段
     * @return
     * @since 2.2.6
     */
    public static String convertToColumn(String field) {
        if (StringUtils.isBlank(field)) {
            return field;
        }

        char[] charArray = field.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char cha : charArray) {
            if (Character.isUpperCase(cha)) {
                sb.append("_");
            }
            sb.append(Character.toUpperCase(cha));
        }
        return sb.toString();
    }

    /**
     * 计算得到字符串长度：一个汉字或日韩文长度为2，英文字符长度为1
     *
     * @param content 字符串内容
     * @return 字符串长度
     * @since v2.4.8
     */
    public static int length(String content) {
        if (content == null) {
            return 0;
        }
        int letter = 0x80; // 十进制128
        char[] chars = content.toCharArray();
        int length = 0;
        for (int i = 0; i < chars.length; i++) {
            length++;
            if (!(chars[i] / letter == 0)) {
                length++;
            }
        }
        return length;
    }

    public static void main(String[] args) {
        System.out.println(fillZero(3, 2L));

        System.out.println(spellToStr(3, "t"));

        System.out.println(spellToStr(3, "?", ","));

        System.out.println(GsonUtil.format(spellToArray(2, "t")));

        System.out.println(GsonUtil.format(spellToList(2, "?")));

        // 将数据库字段按照驼峰命名规则转换为实体类字段
        String columnName = "USER_ID";
        String field = convertToField(columnName);
        System.out.println("convertToField方法：" + columnName + " ===> " + field);

        // 将驼峰命名规则的实体类字段转换为数据库字段
        String column = convertToColumn(field);
        System.out.println("convertToField方法：" + field + " ===> " + column);
    }

}
