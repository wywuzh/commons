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

/**
 * 类StringHelper.java的实现描述：字符串
 * 
 * @author 伍章红 2015-8-6 下午4:08:54
 */
public class StringHelper {

    /**
     * 返回指定length位数的0字符串
     * 
     * @param length
     * @return
     */
    public static String getZeroString(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append("0");
        }
        return buffer.toString();
    }

    /**
     * 将number长整型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
     * 
     * @param length
     *            指定length长度
     * @param number
     *            需要转换的长整型数字
     * @return
     */
    public static String formatToString(int length, long number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 将number浮点型数字转为指定length长度的字符，如果值得长度不足，将自动补零处理
     * 
     * @param length
     *            指定length长度
     * @param number
     *            需要转换的浮点型数字
     * @return
     */
    public static String formatToString(int length, double number) {
        return new DecimalFormat(getZeroString(length)).format(number);
    }

    /**
     * 将object型值转为指定length长度的字符，如果值得长度不足，将自动补零处理
     * 
     * @param length
     *            指定length长度
     * @param object
     *            需要转换的object型值
     * @return
     */
    public static String formatToString(int length, Object object) {
        return new DecimalFormat(getZeroString(length)).format(object);
    }
}
