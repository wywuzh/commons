/*
 * Copyright 2015-2019 the original author or authors.
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
package com.wuzh.commons.core.jep;

import com.wuzh.commons.core.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.nfunk.jep.JEP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 类CalcExpressionUtil的实现描述：计算公式解析
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-12 12:59:09
 * @version v2.1.1
 * @since JDK 1.8
 */
public class CalcExpressionUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(CalcExpressionUtil.class);

    public static final String SEPARATE_COMMA = ",";

    /**
     * 计算公式解析
     *
     * @param calcFieldName  字段名。eg: shoes,hqNotaxCost
     * @param calcFieldTitle 字段标题。eg: 鞋,总部无税成本
     * @param calcExpression 计算公式。eg: -((鞋+总部无税成本)/1.08*0.08+0.13)
     * @param target         计算目标类
     * @return
     */
    public static BigDecimal getCalcValue(String calcFieldName, String calcFieldTitle, String calcExpression, Object target) {
        BigDecimal calcValue = BigDecimal.ZERO;
        if (StringUtils.isBlank(calcExpression)) {
            LOGGER.warn("calcFieldName={}, calcFieldTitle={}, calcExpression={}, target.getClass()={} 传入的计算公式为空，直接返回0", calcFieldName, calcFieldTitle, calcExpression, target.getClass());
            return calcValue;
        }
        // 解析公式：将公式中的中文字段名替换为实体类中的英文字段名
        String[] calcFieldNameArr = StringUtils.split(calcFieldName, SEPARATE_COMMA);
        String[] calcFieldTitleArr = StringUtils.split(calcFieldTitle, SEPARATE_COMMA);
        // key=中文字段名,value=英文字段名
        Map<String, String> fieldTitleMap = new HashMap<>(calcFieldTitleArr.length);
        for (int i = 0; i < calcFieldTitleArr.length; i++) {
            fieldTitleMap.put(calcFieldTitleArr[i], calcFieldNameArr[i]);
        }
        if (StringUtils.indexOf(calcExpression, "=") == 0) {
            // 如果计算公式的第一个字符为“=”符号，需要先去掉
            calcExpression = StringUtils.substring(calcExpression, 1);
        }
        List<String> characters = CommonUtil.splitEnglishCharacter(calcExpression);
        StringBuffer sb = new StringBuffer();
        List<String> calcFieldNameList = new LinkedList<>();
        for (String str : characters) {
            if (CommonUtil.isContainChinese(str)) {
                String fieldName = fieldTitleMap.get(str);
                // 如果根据title找不到name，则还是保持原来的title
                if (StringUtils.isBlank(fieldName)) {
                    sb.append(str);
                } else {
                    sb.append(fieldName);
                    calcFieldNameList.add(fieldName);
                }
            } else {
                sb.append(str);
            }
        }
        // 在计算公式后面加个“*1”，避免公式解析失败
        calcExpression = sb.append("*1").toString();

        JEP jep = new JEP();
        for (String fieldName : calcFieldNameList) {
            Object calcFieldValue = null;
            try {
                // 取出字段
                Field field = FieldUtils.getDeclaredField(target.getClass(), fieldName, true);
                calcFieldValue = field.get(target);
                if (calcFieldValue != null) {
                    // 取出的字段值要保留2位小数，避免字符赋值给另外一个对象时出现进度差异
                    calcFieldValue = new BigDecimal(calcFieldValue.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("target.getClass()={}, fieldName={} 字段取值失败：", target.getClass(), fieldName, e);
            } catch (Exception e) {
                LOGGER.error("target.getClass()={}, fieldName={} 字段取值失败：", target.getClass(), fieldName, e);
            } finally {
                // 如果字段没有取到值或者值为空，默认赋值为0
                if (calcFieldValue == null) {
                    calcFieldValue = BigDecimal.ZERO;
                }
            }
            jep.addVariableAsObject(fieldName, calcFieldValue);
        }
        // 运算公式
        jep.parseExpression(calcExpression);
        // 取到结果
        Object value = jep.getValueAsObject();
        if (value == null) {
            LOGGER.error("calcFieldName={}, calcFieldTitle={}, calcExpression={}, target.getClass()={} 公式解析失败！", calcFieldName, calcFieldTitle, calcExpression, target.getClass());
            throw new IllegalArgumentException("【" + calcExpression + "】 公式解析失败！");
        }
        return new BigDecimal(value.toString());
    }

    public static void main(String[] args) {
        JEP jep = new JEP();
        jep.addVariable("鞋", 100);
        jep.addVariable("总部无税成本", 100);
        jep.parseExpression("-((鞋+总部无税成本)/1.08*0.08+0.13)");
        Object value = jep.getValueAsObject();
        System.out.println(value);
        System.out.println(new BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

}
