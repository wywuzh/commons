/*
 * Copyright 2015-2026 the original author or authors.
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
package io.github.wywuzh.commons.core.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import io.github.wywuzh.commons.core.common.Constants;
import io.github.wywuzh.commons.core.reflect.ReflectUtils;

/**
 * 计算公式解析工具类
 * 使用Spring EL表达式替代JEP，提供更好的性能和安全性
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-31 09:36:49
 * @version v3.5.0
 * @since JDK 17
 */
public class SpelExpressionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpelExpressionUtils.class);

    /**
     * 支持的运算符
     */
    private static final String SUPPORTED_SYMBOLS = "+-*/()><=!&|";

    /**
     * 默认计算精度
     */
    private static final int DEFAULT_SCALE = 6;

    /**
     * 最大计算精度（用于中间计算）
     */
    private static final int MAX_CALCULATION_SCALE = 23;

    /**
     * 运算符替换映射
     */
    private static final Map<String, String> OPERATOR_REPLACEMENTS = createOperatorReplacements();

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private static Map<String, String> createOperatorReplacements() {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("and", "&&");
        replacements.put("or", "||");
        replacements.put("<>", "!=");
        replacements.put("（", "(");
        replacements.put("）", ")");
        replacements.put("：", ":");
        replacements.put("\\s", ""); // 空格
        return replacements;
    }

    /**
     * 计算公式解析
     *
     * @param calcFieldName  字段名。eg: shoes,hqNotaxCost
     * @param calcFieldTitle 字段标题。eg: 鞋,总部无税成本
     * @param calcExpression 计算公式。eg: -((鞋+总部无税成本)/1.08*0.08+0.13)
     * @param target         计算目标类
     * @return 计算结果
     */
    public static BigDecimal getCalcValue(final String calcFieldName, final String calcFieldTitle, final String calcExpression, Object target) {
        // 参数验证
        validateParameters(calcFieldName, calcFieldTitle, calcExpression, target);

        try {
            // 构建字段映射
            Map<String, String> fieldTitleMap = buildFieldTitleMap(calcFieldName, calcFieldTitle);

            // 预处理表达式
            String processedExpression = preprocessExpression(calcExpression);

            // 提取和替换字段名
            ExpressionParseResult parseResult = replaceFieldNames(processedExpression, fieldTitleMap);

            // 构建计算上下文
            StandardEvaluationContext context = buildCalculationContext(parseResult.getFieldNames(), target);

            // 执行计算
            return evaluateExpression(parseResult.getExpression(), context, calcExpression);

        } catch (CalculationException e) {
            // 已知的计算异常，直接抛出
            throw e;
        } catch (Exception e) {
            // 未知异常，包装后抛出
            LOGGER.error("计算公式解析发生未知错误: calcExpression={}, target={}", calcExpression, getTargetInfo(target), e);
            throw new CalculationException("计算公式解析失败: " + calcExpression, e);
        }
    }

    /**
     * 计算公式解析（Map版本）
     *
     * @param calcExpression 计算公式
     * @param fieldValueMap  字段值映射
     * @return 计算结果
     */
    public static BigDecimal getCalcValue(final String calcExpression, Map<String, BigDecimal> fieldValueMap) {
        // 参数验证
        validateParameters(calcExpression, fieldValueMap);

        try {
            // 预处理表达式
            String processedExpression = preprocessExpression(calcExpression);

            // 构建计算上下文
            StandardEvaluationContext context = buildCalculationContext(fieldValueMap);

            // 执行计算
            return evaluateExpression(processedExpression, context, calcExpression);

        } catch (CalculationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("计算公式解析发生未知错误: calcExpression={}, fieldValueMap={}", calcExpression, fieldValueMap, e);
            throw new CalculationException("计算公式解析失败: " + calcExpression, e);
        }
    }

    // ========== 私有方法 ==========

    /**
     * 参数验证
     */
    private static void validateParameters(String calcFieldName, String calcFieldTitle, String calcExpression, Object target) {
        if (StringUtils.isBlank(calcExpression)) {
            LOGGER.warn("计算公式为空，直接返回0。target: {}", getTargetInfo(target));
            throw new CalculationException("计算公式不能为空");
        }

        if (target == null) {
            throw new CalculationException("计算目标对象不能为空");
        }

        if (StringUtils.isBlank(calcFieldName) || StringUtils.isBlank(calcFieldTitle)) {
            throw new CalculationException("字段名和字段标题不能为空");
        }

        String[] nameArr = StringUtils.split(calcFieldName, Constants.SEPARATE_COMMA);
        String[] titleArr = StringUtils.split(calcFieldTitle, Constants.SEPARATE_COMMA);

        if (nameArr.length != titleArr.length) {
            throw new CalculationException("字段名和字段标题数量不匹配");
        }
    }

    private static void validateParameters(String calcExpression, Map<String, BigDecimal> fieldValueMap) {
        if (StringUtils.isBlank(calcExpression)) {
            LOGGER.warn("计算公式为空，直接返回0");
            throw new CalculationException("计算公式不能为空");
        }

        if (fieldValueMap == null || fieldValueMap.isEmpty()) {
            throw new CalculationException("字段值映射不能为空");
        }
    }

    /**
     * 构建字段标题映射
     */
    private static Map<String, String> buildFieldTitleMap(String calcFieldName, String calcFieldTitle) {
        String[] nameArr = StringUtils.split(calcFieldName, Constants.SEPARATE_COMMA);
        String[] titleArr = StringUtils.split(calcFieldTitle, Constants.SEPARATE_COMMA);

        Map<String, String> fieldTitleMap = new HashMap<>(titleArr.length);
        for (int i = 0; i < titleArr.length; i++) {
            fieldTitleMap.put(cleanFieldName(titleArr[i]), nameArr[i]);
        }
        return fieldTitleMap;
    }

    /**
     * 预处理表达式
     */
    private static String preprocessExpression(String expression) {
        String processed = expression.trim();

        // 移除开头的等号
        if (processed.startsWith("=")) {
            processed = processed.substring(1);
        }

        // 替换运算符
        for (Map.Entry<String, String> entry : OPERATOR_REPLACEMENTS.entrySet()) {
            processed = processed.replaceAll(entry.getKey(), entry.getValue());
        }

        // 清理字段名中的特殊字符
        processed = cleanFieldNamesInExpression(processed);

        return processed;
    }

    /**
     * 清理字段名
     */
    private static String cleanFieldName(String fieldName) {
        return fieldName.replaceAll("[:：\\s（）()]", "");
    }

    /**
     * 清理表达式中的字段名
     */
    private static String cleanFieldNamesInExpression(String expression) {
        // 提取非运算符部分的token进行清理
        Pattern pattern = Pattern.compile("[a-zA-Z\\u4e00-\\u9fa5][a-zA-Z0-9\\u4e00-\\u9fa5]*");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String token = matcher.group();
            // 如果是纯中文或包含中文的token，进行清理
            if (containsChinese(token)) {
                matcher.appendReplacement(result, cleanFieldName(token));
            } else {
                matcher.appendReplacement(result, token);
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    /**
     * 替换字段名
     */
    private static ExpressionParseResult replaceFieldNames(String expression, Map<String, String> fieldTitleMap) {
        Set<String> usedFieldNames = new LinkedHashSet<>();
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+[\\u4e00-\\u9fa5a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String chineseField = matcher.group();
            String englishField = fieldTitleMap.get(chineseField);

            if (StringUtils.isNotBlank(englishField)) {
                matcher.appendReplacement(result, englishField);
                usedFieldNames.add(englishField);
            } else {
                // 保持原字段名，但记录警告
                LOGGER.warn("未找到字段映射: {}", chineseField);
                matcher.appendReplacement(result, chineseField);
            }
        }
        matcher.appendTail(result);

        return new ExpressionParseResult(result.toString(), usedFieldNames);
    }

    /**
     * 构建计算上下文
     */
    private static StandardEvaluationContext buildCalculationContext(Set<String> fieldNames, Object target) {
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (String fieldName : fieldNames) {
            try {
                Object value = extractFieldValue(fieldName, target);
                context.setVariable(fieldName, value);
            } catch (Exception e) {
                LOGGER.warn("字段取值失败: fieldName={}, target={}, 使用默认值0", fieldName, getTargetInfo(target));
                context.setVariable(fieldName, BigDecimal.ZERO);
            }
        }

        return context;
    }

    private static StandardEvaluationContext buildCalculationContext(Map<String, BigDecimal> fieldValueMap) {
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (Map.Entry<String, BigDecimal> entry : fieldValueMap.entrySet()) {
            String fieldName = cleanFieldName(entry.getKey());
            BigDecimal value = entry.getValue();

            if (value == null) {
                LOGGER.warn("字段值为空: fieldName={}, 使用默认值0", fieldName);
                value = BigDecimal.ZERO;
            }

            context.setVariable(fieldName, value);
        }

        return context;
    }

    /**
     * 提取字段值
     */
    private static Object extractFieldValue(String fieldName, Object target) throws Exception {
        Object value;

        if (target instanceof Map) {
            value = ((Map<?, ?>) target).get(fieldName);
        } else {
            value = ReflectUtils.getValue(target, fieldName);
        }

        if (value instanceof String) {
            // 字符串转换为BigDecimal，设置合适的精度
            return new BigDecimal(value.toString()).setScale(MAX_CALCULATION_SCALE, RoundingMode.HALF_UP);
        }

        return value != null ? value : BigDecimal.ZERO;
    }

    /**
     * 执行表达式计算
     */
    private static BigDecimal evaluateExpression(String expression, StandardEvaluationContext context, String originalExpression) {
        try {
            Expression parsedExpression = EXPRESSION_PARSER.parseExpression(expression);
            Object result = parsedExpression.getValue(context);

            if (result == null) {
                throw new CalculationException("表达式计算结果为空: " + originalExpression);
            }

            return convertToBigDecimal(result, originalExpression);

        } catch (Exception e) {
            handleEvaluationError(e, originalExpression);
            throw new CalculationException("表达式计算失败: " + originalExpression, e);
        }
    }

    /**
     * 转换为BigDecimal
     */
    private static BigDecimal convertToBigDecimal(Object value, String originalExpression) {
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue()).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
        }

        String stringValue = value.toString();

        // 检查特殊值
        if ("Infinity".equalsIgnoreCase(stringValue) || "NaN".equalsIgnoreCase(stringValue)) {
            throw new CalculationException("计算公式存在除零错误: " + originalExpression);
        }

        try {
            return new BigDecimal(stringValue).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new CalculationException("无法将计算结果转换为数字: " + stringValue, e);
        }
    }

    /**
     * 处理计算错误
     */
    private static void handleEvaluationError(Exception e, String originalExpression) {
        String errorMessage = e.getMessage();

        if (errorMessage != null) {
            if (errorMessage.contains("divide by zero") || errorMessage.contains("/ by zero")) {
                throw new CalculationException("计算公式存在除零错误: " + originalExpression);
            }
        }
    }

    /**
     * 检查是否包含中文
     */
    private static boolean containsChinese(String str) {
        return str.codePoints().anyMatch(codepoint -> Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }

    /**
     * 获取目标对象信息
     */
    private static String getTargetInfo(Object target) {
        if (target == null) {
            return "null";
        }
        try {
            return target.getClass().getSimpleName();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /**
     * 表达式解析结果
     */
    private static class ExpressionParseResult {
        private final String expression;
        private final Set<String> fieldNames;

        public ExpressionParseResult(String expression, Set<String> fieldNames) {
            this.expression = expression;
            this.fieldNames = fieldNames;
        }

        public String getExpression() {
            return expression;
        }

        public Set<String> getFieldNames() {
            return fieldNames;
        }
    }

    /**
     * 自定义计算异常
     */
    public static class CalculationException extends RuntimeException {
        public CalculationException(String message) {
            super(message);
        }

        public CalculationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
