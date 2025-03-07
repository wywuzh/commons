/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.core.sequence;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import io.github.wywuzh.commons.core.math.CalculationUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * code编码工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a>, 2014-4-30 下午12:45:20
 * @version 4.0.0
 * @since JDK 1.6.0_20
 */
@Slf4j
public class CodeUtil {

    /**
     * 计算下一个编码
     *
     * @param code   当前编码
     * @param length 编码长度
     * @param prefix 编码前缀
     * @return 下一个编码
     * @since v3.3.0
     */
    public static synchronized String nextCode(String code, int length, String prefix) {
        Long suffix = 0L;
        if (StringUtils.isNotBlank(code)) {
            try {
                suffix = Long.parseLong(StringUtils.replace(code, prefix, ""));
            } catch (Exception e) {
                log.warn("code={} 获取编码后缀失败：", code, e);
                suffix = 0L;
            }
        }
        // 下一个编码：+1
        BigDecimal nextCode = CalculationUtils.add(new BigDecimal(suffix), CalculationUtils.DEFAULT_ONE, 0);
        return StringUtils.join(prefix, StringUtils.leftPad(nextCode.toString(), length, "0"));
    }

    /**
     * 计算下一个编码
     *
     * @param code
     * @return
     * @deprecated 已废弃，请使用 {@link #nextCode(String, int, String)} 方法
     */
    @Deprecated
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
     * @deprecated 已废弃，请使用 {@link #nextCode(String, int, String)} 方法
     */
    @Deprecated
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
     * @deprecated 已废弃，请使用 {@link #nextCode(String, int, String)} 方法
     */
    @Deprecated
    public static String firstCode(int number) {
        StringBuffer code = new StringBuffer("A");
        for (int i = 1; i < number - 1; i++) {
            code.append("0");
        }
        code.append("1");
        return code.toString();
    }

}
