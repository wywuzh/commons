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

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

/**
 * code编码工具类
 * 
 * @author <a href="mailto:wywuzh@163.com">伍章红</a>, 2014-4-30 下午12:45:20
 * @version 4.0.0
 * @since JDK 1.6.0_20
 */
public class CodeUtil {
    /**
     * 计算下一个编码
     * 
     * @param code
     * @return
     */
    public static String nextCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        StringBuffer codeBuff = new StringBuffer();
        // 获取字母部分
        String codeOne = code.substring(0, 1);
        // 获取数字部分
        String codeTwo = code.substring(1);
        // 数字加1
        int codeTwoLength = codeTwo.length();
        Integer codeTwoInt = Integer.valueOf(codeTwo) + 1;
        String str = codeTwoInt.toString();
        if (str.length() > codeTwoLength) {
            str = "1";

            codeOne = String.valueOf((char) (codeOne.toCharArray()[0] + 1));
        }
        codeBuff.append(codeOne);
        if (str.length() < codeTwoLength) {
            for (int i = str.length(); i < codeTwoLength; i++) {
                codeBuff.append("0");
            }
        }
        codeBuff.append(str);
        return codeBuff.toString();
    }

    /**
     * 获取第一个编码
     * 
     * <pre>
     * 编码位数默认为13位
     * </pre>
     * 
     * @return
     */
    public static String firstCode() {
        StringBuffer code = new StringBuffer("A");
        for (int i = 1; i < 12; i++) {
            code.append("0");
        }
        code.append("1");
        return code.toString();
    }

    /**
     * 获取指定位数的第一个编码
     * 
     * @param number
     * @return
     */
    public static String firstCode(int number) {
        StringBuffer code = new StringBuffer("A");
        for (int i = 1; i < number - 1; i++) {
            code.append("0");
        }
        code.append("1");
        return code.toString();
    }

    public static void main(String[] args) {
        String code = "A000000000001";
        System.out.println(code.length());

        // String substring = code.substring(1);
        // System.out.println(substring);
        // String c = code.substring(0, 1);
        // char[] charArray = c.toCharArray();
        // char cc = charArray[0];
        // System.out.println((char) (cc + 1));

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            code = nextCode(code);
            System.out.println(code);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(new BigDecimal(endTime - startTime).divide(new BigDecimal(1000)).setScale(3, 0).toString());

        System.out.println(firstCode());
        System.out.println(firstCode(3));
    }
}
