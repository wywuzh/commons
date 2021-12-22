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
            jep.addVariableAsObject("x", 1);
            jep.addVariableAsObject("y", 1);
            jep.addVariableAsObject("z", 1);
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
            jep.addVariableAsObject("利息支出", 1000);
            jep.addVariableAsObject("工资", -1);
            jep.addVariableAsObject("劳务费", 6000);
            jep.parseExpression(formula);
            System.out.println("复杂逻辑：" + jep.getValueAsObject());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void getCalcValueTest() {
        String calcExpression = "利息支出+劳务费";//"-(鞋*(10/1.13))+4000.00";
        Map<String, Object> fieldValueMap = new HashMap<>();
        fieldValueMap.put("利息支出", BigDecimal.valueOf(400));
        fieldValueMap.put("劳务费", BigDecimal.valueOf(300));
        System.out.println("复杂逻辑：" + CalcExpressionUtils.getCalcValue(calcExpression, fieldValueMap));

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

}
