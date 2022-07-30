/*
 * Copyright 2015-2022 the original author or authors.
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
package com.github.wywuzh.commons.core.math;

import com.github.wywuzh.commons.core.common.Constants;
import com.github.wywuzh.commons.core.json.jackson.JsonMapper;
import com.github.wywuzh.commons.core.reflect.ReflectUtils;
import com.github.wywuzh.commons.core.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.JEP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 类CalcExpressionUtils的实现描述：计算公式解析
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-12 12:59:09
 * @version v2.1.1
 * @since JDK 1.8
 */
public class CalcExpressionUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(CalcExpressionUtils.class);

    /**
     * 默认计算符号：加、减、乘、除、大于、小于、大于等于、小于等于、等于、并且、或者、不等于
     *
     * @since v2.5.2
     */
    public static final String DEFAULT_SYMBOL = "+-*/()><=!&|";

    /**
     * 计算公式解析
     *
     * @param calcFieldName  字段名。eg: shoes,hqNotaxCost
     * @param calcFieldTitle 字段标题。eg: 鞋,总部无税成本
     * @param calcExpression 计算公式。eg: -((鞋+总部无税成本)/1.08*0.08+0.13)
     * @param target         计算目标类
     * @return
     */
    public static BigDecimal getCalcValue(final String calcFieldName, final String calcFieldTitle, final String calcExpression, Object target) {
        BigDecimal calcValue = BigDecimal.ZERO;
        if (StringUtils.isBlank(calcExpression)) {
            LOGGER.warn("calcFieldName={}, calcFieldTitle={}, calcExpression={}, target.getClass()={} 传入的计算公式为空，直接返回0", calcFieldName, calcFieldTitle, calcExpression, target.getClass());
            return calcValue;
        }
        // 解析公式：将公式中的中文字段名替换为实体类中的英文字段名
        String[] calcFieldNameArr = StringUtils.split(calcFieldName, Constants.SEPARATE_COMMA);
        String[] calcFieldTitleArr = StringUtils.split(calcFieldTitle, Constants.SEPARATE_COMMA);
        // key=中文字段名,value=英文字段名
        Map<String, String> fieldTitleMap = new HashMap<>(calcFieldTitleArr.length);
        for (int i = 0; i < calcFieldTitleArr.length; i++) {
            fieldTitleMap.put(calcFieldTitleArr[i], calcFieldNameArr[i]);
        }
        String resolvedCalcExpression = calcExpression;
        if (StringUtils.indexOf(resolvedCalcExpression, "=") == 0) {
            // 如果计算公式的第一个字符为“=”符号，需要先去掉
            resolvedCalcExpression = StringUtils.substring(resolvedCalcExpression, 1);
        }
        // 并且、或者、不等于分别用&&、||、!=这三个符号表示。and、or、<>这三个符号不生效
        resolvedCalcExpression = resolvedCalcExpression
                .replaceAll("and+", "&&")
                .replaceAll("or+", "||")
                .replaceAll("<>+", "!=")
                .replaceAll("\\s", "");
        // 中英文冒号去掉
        resolvedCalcExpression = resolvedCalcExpression
                .replaceAll(":+", "")
                .replaceAll("：+", "");
        // 中文括号去掉
        resolvedCalcExpression = resolvedCalcExpression
                .replaceAll("（+", "")
                .replaceAll("）+", "");
        List<String> characters = CommonUtil.splitContent(resolvedCalcExpression, DEFAULT_SYMBOL);//CommonUtil.splitEnglishCharacter(resolvedCalcExpression);
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
        resolvedCalcExpression = sb.append("*1").toString();

        JEP jep = new JEP();
        for (String fieldName : calcFieldNameList) {
            Object calcFieldValue = null;
            try {
                // 取出字段
                if (target instanceof Map) {
                    calcFieldValue = ((Map) target).get(fieldName);
                } else {
                    calcFieldValue = ReflectUtils.getValue(target, fieldName);
                }
                if (calcFieldValue != null) {
                    if (calcFieldValue instanceof String) {
                        // 取出的字段值要保留6位小数，避免字符赋值给另外一个对象时出现精度差异
                        calcFieldValue = new BigDecimal(calcFieldValue.toString()).setScale(23, BigDecimal.ROUND_HALF_UP);
                    }
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
        jep.parseExpression(resolvedCalcExpression);
        // 取到结果
        Object value = jep.getValueAsObject();
        if (value == null) {
            LOGGER.error("calcFieldName={}, calcFieldTitle={}, calcExpression={}, resolvedCalcExpression={}, target={} 公式解析失败！", calcFieldName, calcFieldTitle, calcExpression, resolvedCalcExpression, JsonMapper.DEFAULT_JSON_MAPPER.toJson(target));
            throw new ExpressionException("【" + calcExpression + "】 公式解析失败！");
        }
        if (StringUtils.equalsIgnoreCase("Infinity", value.toString())) {
            LOGGER.error("calcFieldName={}, calcFieldTitle={}, calcExpression={}, resolvedCalcExpression={}, target={} 公式分母中出现为0的数值，解析失败！", calcFieldName, calcFieldTitle, calcExpression, resolvedCalcExpression, JsonMapper.DEFAULT_JSON_MAPPER.toJson(target));
            throw new ExpressionException("【" + calcExpression + "】 公式分母中出现为0的数值，解析失败！");
        }
        if (StringUtils.equalsIgnoreCase("NaN", value.toString())) {
            LOGGER.error("calcFieldName={}, calcFieldTitle={}, calcExpression={}, resolvedCalcExpression={}, target={} 公式分子和分母中出现为0的数值，解析失败！", calcFieldName, calcFieldTitle, calcExpression, resolvedCalcExpression, JsonMapper.DEFAULT_JSON_MAPPER.toJson(target));
            throw new ExpressionException("【" + calcExpression + "】 公式分子和分母中出现为0的数值，解析失败！");
        }
        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) {
            LOGGER.error("calcFieldName={}, calcFieldTitle={}, calcExpression={}, resolvedCalcExpression={}, target={} 公式解析失败，结果值={}", calcFieldName, calcFieldTitle, calcExpression, resolvedCalcExpression, JsonMapper.DEFAULT_JSON_MAPPER.toJson(target), value);
            throw new ExpressionException("【" + calcExpression + "】 公式解析失败！");
        }
    }

    /**
     * 计算公式解析
     *
     * @param calcExpression 计算公式。eg: -((鞋+总部无税成本)/1.08*0.08+0.13)
     * @param fieldValueMap  字段值。eg：{"本月A租赁费":400,"上月A租赁费":300}
     * @return
     * @since v2.5.2
     */
    public static BigDecimal getCalcValue(final String calcExpression, Map<String, BigDecimal> fieldValueMap) {
        BigDecimal calcValue = BigDecimal.ZERO;
        if (StringUtils.isBlank(calcExpression)) {
            LOGGER.warn("calcExpression={}, fieldValueMap={} 传入的计算公式为空，直接返回0", calcExpression, fieldValueMap);
            return calcValue;
        }
        String resolvedCalcExpression = calcExpression;
        // 解析公式：将公式中的中文字段名替换为实体类中的英文字段名
        if (StringUtils.indexOf(resolvedCalcExpression, "=") == 0) {
            // 如果计算公式的第一个字符为“=”符号，需要先去掉
            resolvedCalcExpression = StringUtils.substring(resolvedCalcExpression, 1);
        }
        // 并且、或者、不等于分别用&&、||、!=这三个符号表示。and、or、<>这三个符号不生效
        resolvedCalcExpression = resolvedCalcExpression
                .replaceAll("and+", "&&")
                .replaceAll("or+", "||")
                .replaceAll("<>+", "!=")
                .replaceAll("\\s", "");
        // 中英文冒号去掉
        resolvedCalcExpression = resolvedCalcExpression
                .replaceAll(":+", "")
                .replaceAll("：+", "");
        // 中文括号去掉
        resolvedCalcExpression = resolvedCalcExpression
                .replaceAll("（+", "")
                .replaceAll("）+", "");
        // 在计算公式后面加个“*1”，避免公式解析失败
        resolvedCalcExpression = "(" + resolvedCalcExpression + ")*1";

        JEP jep = new JEP();
        for (Map.Entry<String, BigDecimal> entry : fieldValueMap.entrySet()) {
            String fieldName = entry.getKey();
            BigDecimal calcFieldValue = entry.getValue();
            // 如果字段没有取到值或者值为空，默认赋值为0
            if (calcFieldValue == null) {
                LOGGER.warn("fieldValueMap={}, fieldName={} 该字段传值为空，设置默认值：0", fieldValueMap, fieldName);
                calcFieldValue = BigDecimal.ZERO;
            }
            // 中英文冒号去掉
            fieldName = fieldName
                    .replaceAll(":+", "")
                    .replaceAll("：+", "");
            // 中文括号去掉
            fieldName = fieldName
                    .replaceAll("（+", "")
                    .replaceAll("）+", "");
            jep.addVariableAsObject(fieldName, calcFieldValue);
        }
        // 运算公式
        jep.parseExpression(resolvedCalcExpression);
        // 取到结果
        Object value = jep.getValueAsObject();
        if (value == null) {
            LOGGER.error("calcExpression={}, resolvedCalcExpression={} fieldValueMap={} 公式解析失败！", calcExpression, resolvedCalcExpression, fieldValueMap);
            throw new ExpressionException("【" + calcExpression + "】 公式解析失败！");
        }
        if (StringUtils.equalsIgnoreCase("Infinity", value.toString())) {
            LOGGER.error("calcExpression={}, resolvedCalcExpression={}, fieldValueMap={} 公式分母中出现为0的数值，解析失败！", calcExpression, resolvedCalcExpression, fieldValueMap);
            throw new ExpressionException("【" + calcExpression + "】 公式分母中出现为0的数值，解析失败！");
        }
        if (StringUtils.equalsIgnoreCase("NaN", value.toString())) {
            LOGGER.error("calcExpression={}, resolvedCalcExpression={}, fieldValueMap={} 公式分子和分母中出现为0的数值，解析失败！", calcExpression, resolvedCalcExpression, fieldValueMap);
            throw new ExpressionException("【" + calcExpression + "】 公式分子和分母中出现为0的数值，解析失败！");
        }
        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) {
            LOGGER.error("calcExpression={}, resolvedCalcExpression={}, fieldValueMap={} 公式解析失败，结果值={}", calcExpression, resolvedCalcExpression, fieldValueMap, value);
            throw new ExpressionException("【" + calcExpression + "】 公式解析失败！");
        }
    }

}
