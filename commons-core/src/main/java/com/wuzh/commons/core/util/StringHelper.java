/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.wuzh.commons.core.gson.GsonUtil;

/**
 * 类StringHelper.java的实现描述：字符串
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015-8-6 下午4:08:54
 * @version v1.0.0
 * @since JDK 1.7
 */
public class StringHelper {

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
     * @param length
     *            指定length长度
     * @param number
     *            需要转换的整型数字
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
     * @param length
     *            指定length长度
     * @param number
     *            需要转换的长整型数字
     * @return
     */
    public static String fillZero(final int length, final long number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 将str拼写成length长度的字符
     * 
     * <pre>
     * StringHelper.spellStr(2, "*") = "**"
     * StringHelper.spellStr(2, "?") = "??"
     * </pre>
     * 
     * @param length
     *            指定length长度
     * @param str
     *            目标字符
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
     * @param length
     *            指定length长度
     * @param str
     *            目标字符
     * @param separator
     *            分隔符
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
     * @param length
     *            指定length长度
     * @param str
     *            目标字符
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
     * @param length
     *            指定length长度
     * @param str
     *            目标字符
     * @return
     */
    public static List<String> spellToList(final int length, final String str) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            list.add(str);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(fillZero(3, 2L));

        System.out.println(spellToStr(3, "t"));

        System.out.println(spellToStr(3, "?", ","));

        System.out.println(GsonUtil.format(spellToArray(2, "t")));

        System.out.println(GsonUtil.format(spellToList(2, "?")));
    }

}
