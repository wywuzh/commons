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

import static io.github.wywuzh.commons.core.util.AmountUtil.toCapitalAmount;

import org.junit.Test;

/**
 * 类AmountUtilTest的实现描述：金额转换工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-06-25 12:40:48
 * @version v3.5.0
 * @since JDK 17
 */
public class AmountUtilTest {

    public static void main(String[] args) {
        // System.out.println(parseAmount("12,000,00.006"));
        //
        // double amountDouble = 0.006;
        // long amountStr = 12012012012000L;
        // // NumberFormat formater = new
        // DecimalFormat("##,###,###,###,###.000");
        // System.out.println(FORMAT.format(amountDouble));
        // System.out.println(FORMAT.format(amountStr));
        //
        // try {
        // Number number = FORMAT.parse("12,012,012,012,034.01");
        // System.out.println(number.longValue());
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }

        String amount = "￥012,012,034.01";
        System.out.println(AmountUtil.parseDouble(amount));
        System.out.println(AmountUtil.parse(amount));

    }

    @Test
    public void toCapitalAmountTest() {

        // 整数
        System.out.println(toCapitalAmount(0));              // 零元整
        System.out.println(toCapitalAmount(123));            // 壹佰贰拾叁元整
        System.out.println(toCapitalAmount(1000000));        // 壹佰万元整
        System.out.println(toCapitalAmount(100000001));      // 壹亿零壹元整
        System.out.println(toCapitalAmount(1000000000));     // 壹拾亿元整
        System.out.println(toCapitalAmount(1234567890));     // 壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元整
        System.out.println(toCapitalAmount(1001100101));     // 壹拾亿零壹佰壹拾万零壹佰零壹元整
        System.out.println(toCapitalAmount(110101010));      // 壹亿壹仟零壹拾万壹仟零壹拾元整

        // 小数
        System.out.println(toCapitalAmount(0.12));          // 壹角贰分
        System.out.println(toCapitalAmount(123.34));        // 壹佰贰拾叁元叁角肆分
        System.out.println(toCapitalAmount(1000000.56));    // 壹佰万元伍角陆分
        System.out.println(toCapitalAmount(100000001.78));  // 壹亿零壹元柒角捌分
        System.out.println(toCapitalAmount(1000000000.90)); // 壹拾亿元玖角
        System.out.println(toCapitalAmount(1234567890.03)); // 壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元叁分
        System.out.println(toCapitalAmount(1001100101.00)); // 壹拾亿零壹佰壹拾万零壹佰零壹元整
        System.out.println(toCapitalAmount(110101010.10));  // 壹亿壹仟零壹拾万壹仟零壹拾元壹角

        // 负数
        System.out.println(toCapitalAmount(-0.12));          // 负壹角贰分
        System.out.println(toCapitalAmount(-123.34));        // 负壹佰贰拾叁元叁角肆分
        System.out.println(toCapitalAmount(-1000000.56));    // 负壹佰万元伍角陆分
        System.out.println(toCapitalAmount(-100000001.78));  // 负壹亿零壹元柒角捌分
        System.out.println(toCapitalAmount(-1000000000.90)); // 负壹拾亿元玖角
        System.out.println(toCapitalAmount(-1234567890.03)); // 负壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元叁分
        System.out.println(toCapitalAmount(-1001100101.00)); // 负壹拾亿零壹佰壹拾万零壹佰零壹元整
        System.out.println(toCapitalAmount(-110101010.10));  // 负壹亿壹仟零壹拾万壹仟零壹拾元壹角

    }

}
