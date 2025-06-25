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
package io.github.wywuzh.commons.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import io.github.wywuzh.commons.core.exception.AmountException;

/**
 * 金额转换工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 12/24/2013
 * @version 1.0.0
 * @since JDK 1.6.0_20
 */
@Slf4j
public class AmountUtil {
    private static final DecimalFormat FORMAT = new DecimalFormat("###,##0.00");
    private static final String PREFIX = "￥";

    /**
     * 将byte类型金额数值转换成金额类型
     *
     * @param amount
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wywuzh</a>, 08/06/2015
     */
    public static String format(byte amount) {
        return PREFIX + FORMAT.format(amount);
    }

    /**
     * 将short类型金额数值转换成金额类型
     *
     * @param amount
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wywuzh</a>, 08/06/2015
     */
    public static String format(short amount) {
        return PREFIX + FORMAT.format(amount);
    }

    /**
     * 将int类型金额数值转换成金额类型
     *
     * @param amount
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wywuzh</a>, 08/06/2015
     */
    public static String format(int amount) {
        return PREFIX + FORMAT.format(amount);
    }

    /**
     * 将float类型金额数值转换成金额类型
     *
     * @param amount
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wywuzh</a>, 08/06/2015
     */
    public static String format(float amount) {
        return PREFIX + FORMAT.format(amount);
    }

    /**
     * 将double类型金额数值转换成金额类型
     *
     * <pre>
     * toAmount方法可以把传入double类型的数值转换成金额字符串输出。数据在转换成金额时，采用###,###.##的格式。
     * 如传入值为20，输出值则为￥20.00；传入值为30000，输出值则为￥30,000.00。
     * 调用此方法时，请输入double类型的数值，或者能直接转换为double类型的数据。基本类型如int、float、byte、short，对象类型如long。
     * 不建议传入char类型的数据，虽然char型数据可以直接转成double类型，但我们应该明确char数值代表的是字符位。
     * </pre>
     *
     * @param number 数值
     * @return 格式化之后的金额字符串
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wuzh</a>, 12/24/2013
     * @author <a href="mailto:wywuzh@163.com">wuzh</a>, 02/20/2014
     */
    public static String format(double number) {
        return PREFIX + FORMAT.format(number);
    }

    /**
     * 将long类型金额数值转换成金额类型
     *
     * @param number
     * @return 格式化之后的金额字符串
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wuzh</a>, 02/20/2014
     */
    public static String format(long number) {
        return PREFIX + FORMAT.format(number);
    }

    /**
     * 将BigDecimal类型金额数值转换成金额类型
     *
     * @param amount
     * @return 返回转换后的金额字符串，格式为￥###,##0.00
     * @author <a href="mailto:wywuzh@163.com">wywuzh</a>, 08/06/2015
     */
    public static String format(BigDecimal amount) {
        if (null == amount) {
            return null;
        }
        return PREFIX + FORMAT.format(amount);
    }

    /**
     * 将字符串格式的金额解析为int类型的金额
     *
     * @param amount
     * @return
     * @author wywuzh 2016年5月6日 下午12:38:51
     */
    public static int parseInt(String amount) {
        return parse(amount).intValue();
    }

    /**
     * 将字符串格式的金额解析为float类型的金额
     *
     * @param amount
     * @return
     * @author wywuzh 2016年5月6日 下午12:05:15
     */
    public static float parseFloat(String amount) {
        return parse(amount).floatValue();
    }

    /**
     * 将字符串格式的金额解析为double类型的金额
     *
     * @param amount 字符串格式的金额。金额格式为“###,##0.00”，前缀可以为空，或者也可以为“$”、“￥”
     * @return double类型的金额
     * @author <a href="mailto:wywuzh@163.com">wuzh</a>, 02/20/2014
     */
    public static double parseDouble(String amount) {
        return parse(amount).doubleValue();
    }

    /**
     * 将字符串格式的金额解析为long类型的金额
     *
     * @param amount
     * @return
     * @author wywuzh 2016年5月6日 下午12:05:17
     */
    public static long parseLong(String amount) {
        return parse(amount).longValue();
    }

    /**
     * 将字符串格式的金额解析为BigDecimal类型的金额
     *
     * @param amount 字符串格式的金额。金额格式为“###,##0.00”，前缀可以为空，或者也可以为“$”、“￥”
     * @return
     * @author 伍章红 2015年5月8日 ( 下午4:00:11 )
     */
    public static BigDecimal parse(String amount) {
        if (null == amount) {
            throw new AmountException("传入金额为空");
        }
        if (StringUtils.contains(amount, "$")) {
            amount = amount.replace("$", "");
        }
        if (StringUtils.contains(amount, "￥")) {
            amount = amount.replace("￥", "");
        }

        try {
            amount = amount.substring(amount.indexOf(PREFIX) + 1);
            return new BigDecimal(FORMAT.parse(amount).toString());
        } catch (ParseException e) {
            log.error("amount={} 金额格式解析失败：", e);
            throw new NumberFormatException("金额格式传入错误");
        }
    }

    /**
     * 把金额数值转成大写
     *
     * <pre>
     * toCapitalAmount方法可以把传入double类型的数值转换成大写的金额字符串输出。
     * 调用此方法时，请输入double类型的数值，或者能直接转换为double类型的数据。基本类型如int、float、byte、short，对象类型如long。
     * 不建议传入char类型的数据，虽然char型数据可以直接转成double类型，但我们应该明确char数值代表的是字符位。
     * </pre>
     *
     * @param amount 金额数值
     * @return 返回大写金额
     * @author <a href="mailto:wywuzh@163.com">wuzh</a>, 12/24/2013
     */
    public static String toCapitalAmount(double amount) {
        String[] fraction = {
                "角", "分"
        };
        String[] digit = {
                "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"
        };
        String[][] unit = {
                {
                        "元", "万", "亿"
                }, {
                        "", "拾", "佰", "仟"
                }
        };
        String head = amount < 0 ? "负" : "";
        // 获取绝对值
        amount = Math.abs(amount);
        String result = "";
        for (int i = 0; i < fraction.length; i++) {
            // amount = 100
            // Math.pow表示第一个参数的第二次幂的值。Math.pow(a,b)表示返回a为底数，b为指数。如Math.pow(2,3)，返回值2的3次幂为8。
            result += (digit[(int) (Math.floor(amount * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (result.length() < 1) {
            result = "整";
        }
        int integerPart = (int) Math.floor(amount);
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && amount > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            result = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + result;
        }

        return head + result.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

}
