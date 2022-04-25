/*
 * Copyright 2015-2021 the original author or authors.
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
package com.wuzh.commons.core.math;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.nfunk.jep.JEP;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 类CalcExpressionUtilsTest的实现描述：计算公式解析测试
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-12-22 18:06:21
 * @version v2.5.2
 * @since JDK 1.8
 */
@Slf4j
public class CalcExpressionUtilsTest {

    @Test
    public void jepTest() {
        try {
            JEP jep = new JEP();

            // 常规公式
            String formula01 = "3*x+4*y+-5*z";
            jep.addVariable("x", 1);
            jep.addVariable("y", 1);
            jep.addVariable("z", 1);
            jep.parseExpression(formula01);
            System.out.println("常规公式：" + jep.getValueAsObject());

            // 公式中分母为0
            String formula02 = "3/0";
            jep.parseExpression(formula02);
            System.out.println("公式中分母为0：" + jep.getValueAsObject());

            // 布尔表达式
            String formula03 = "3>=0";
            jep.parseExpression(formula03);
            System.out.println("布尔表达式：" + jep.getValueAsObject());

            // 逻辑运算：
            // 注：并且、或者、不等于分别用&&、||、!=这三个符号表示。and、or、<>这三个符号不生效
//            String formula04 = "1&&0";
//            String formula04 = "1||0";
            String formula04 = "1!=1";
            jep.parseExpression(formula04);
            System.out.println("逻辑运算：" + jep.getValueAsObject());

            // 复杂逻辑
            String formula = "利息支出>0 || 工资<=0";
            jep.addVariable("利息支出", 1000);
            jep.addVariable("工资", -1);
            jep.addVariable("劳务费", 6000);
            jep.parseExpression(formula);
            System.out.println("复杂逻辑：" + jep.getValueAsObject());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void getCalcValueTest() {
        // 计算公式解析：规则一
        final String calcFieldName = "interestExpense,laborCost";
        final String calcFieldTitle = "利息支出,劳务费";
        final String calcExpression = "利息支出+劳务费";
//        Map target = new HashMap<>();
//        target.put("interestExpense", BigDecimal.valueOf(400));
//        target.put("laborCost", BigDecimal.valueOf(300));
        CalcTarget target = new CalcTarget();
        target.setInterestExpense(new BigDecimal("0.000000000000000004"));
        target.setLaborCost(new BigDecimal("0.000000003"));
        System.out.println("复杂逻辑(" + calcExpression + ")：" + CalcExpressionUtils.getCalcValue(calcFieldName, calcFieldTitle, calcExpression, target));
        System.out.println("复杂逻辑(" + calcExpression + ")：" + CalcExpressionUtils.getCalcValue(calcFieldName, calcFieldTitle, calcExpression, target).toPlainString());
        System.out.println("复杂逻辑(" + calcExpression + ")：" + CalcExpressionUtils.getCalcValue(calcFieldName, calcFieldTitle, calcExpression, target).stripTrailingZeros().toPlainString());

        // 计算公式解析：规则二
        System.out.println("计算公式解析：规则二 ------------------------ >>>");
        String formula = "利息支出+劳务费";
        Map<String, BigDecimal> fieldValueMap = new HashMap<>();
        fieldValueMap.put("利息支出", new BigDecimal("0.000000000000000004").setScale(23, BigDecimal.ROUND_HALF_UP));
        fieldValueMap.put("劳务费", new BigDecimal("0.00000003").setScale(23, BigDecimal.ROUND_HALF_UP));
        System.out.println("复杂逻辑(" + formula + ")：" + CalcExpressionUtils.getCalcValue(formula, fieldValueMap));
        System.out.println("复杂逻辑(" + formula + ")：" + CalcExpressionUtils.getCalcValue(formula, fieldValueMap).stripTrailingZeros().toPlainString());

        fieldValueMap.clear();
        String formula01 = "(本月利息支出-上月利息支出)/上月利息支出*100";
        fieldValueMap.put("本月利息支出", BigDecimal.valueOf(400));
        fieldValueMap.put("上月利息支出", BigDecimal.valueOf(200));
        System.out.println("复杂逻辑(" + formula01 + ")：" + CalcExpressionUtils.getCalcValue(formula01, fieldValueMap));

        fieldValueMap.clear();
        String formula02 = "利息支出>0||工资<=0";
        fieldValueMap.put("利息支出", BigDecimal.valueOf(400));
        fieldValueMap.put("工资", BigDecimal.valueOf(200));
        System.out.println("复杂逻辑(" + formula02 + ")：" + CalcExpressionUtils.getCalcValue(formula02, fieldValueMap));

        fieldValueMap.clear();
        String formula03 = "利息支出<>0 and 劳务费<>0";
        fieldValueMap.put("利息支出", BigDecimal.valueOf(1));
        fieldValueMap.put("劳务费", BigDecimal.valueOf(1));
        System.out.println("复杂逻辑(" + formula03 + ")：" + CalcExpressionUtils.getCalcValue(formula03, fieldValueMap));

    }

    public static class CalcTarget {
        private BigDecimal interestExpense;
        private BigDecimal laborCost;

        public BigDecimal getInterestExpense() {
            return interestExpense;
        }

        public void setInterestExpense(BigDecimal interestExpense) {
            this.interestExpense = interestExpense;
        }

        public BigDecimal getLaborCost() {
            return laborCost;
        }

        public void setLaborCost(BigDecimal laborCost) {
            this.laborCost = laborCost;
        }
    }

}
