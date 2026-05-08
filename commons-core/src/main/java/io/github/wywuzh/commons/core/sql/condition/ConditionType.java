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
package io.github.wywuzh.commons.core.sql.condition;

/**
 * 类ConditionType的实现描述：Where条件字段匹配类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-03-05 15:39:28
 * @version v3.5.0
 * @since JDK 17
 */
public enum ConditionType {
    /**
     * 精确匹配表字段名（直接使用字段名，不进行条件判断）
     */
    column("="),

    /**
     * 精确匹配（等于）
     */
    equals("="),

    /**
     * 模糊匹配（LIKE）
     */
    like("LIKE"),

    /**
     * IN 匹配
     */
    in("IN"),

    /**
     * IN 匹配超过1000时改用union查询 or 模糊查询改用in查询
     */
    unions("UNION"),

    /**
     * 区间匹配（BETWEEN）
     */
    between("BETWEEN"),

    /**
     * 不等于
     */
    notEquals("<>"),

    /**
     * 不模糊匹配（NOT LIKE）
     */
    notLike("NOT LIKE"),

    /**
     * NOT IN 匹配
     */
    notIn("NOT IN"),

    /**
     * 不在区间内匹配（NOT BETWEEN）
     */
    notBetween("NOT BETWEEN"),

    /**
     * 大于
     */
    gt(">"),

    /**
     * 大于等于
     */
    gte(">="),

    /**
     * 小于
     */
    lt("<"),

    /**
     * 小于等于
     */
    lte("<="),

    /**
     * 为空（IS NULL）
     */
    isNull("IS NULL"),

    /**
     * 不为空（IS NOT NULL）
     */
    isNotNull("IS NOT NULL");

    /**
     * SQL 操作符
     */
    private final String operator;

    ConditionType(String operator) {
        this.operator = operator;
    }

    /**
     * 获取 SQL 操作符
     *
     * @return SQL 操作符
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 判断是否为批量操作（IN、NOT IN、UNIONS 等）
     *
     * @return true 表示批量操作，false 表示非批量操作
     */
    public boolean isBatch() {
        return this == in || this == notIn || this == unions;
    }

    /**
     * 判断是否为区间操作（BETWEEN、NOT BETWEEN）
     *
     * @return true 表示区间操作，false 表示非区间操作
     */
    public boolean isBetween() {
        return this == between || this == notBetween;
    }

    /**
     * 判断是否为模糊匹配操作（LIKE、NOT LIKE）
     *
     * @return true 表示模糊匹配，false 表示非模糊匹配
     */
    public boolean isLike() {
        return this == like || this == notLike;
    }

    /**
     * 判断是否为空值判断操作（IS NULL、IS NOT NULL）
     *
     * @return true 表示空值判断，false 表示非空值判断
     */
    public boolean isNullCheck() {
        return this == isNull || this == isNotNull;
    }

    /**
     * 根据操作符获取 ConditionType
     *
     * @param operator SQL 操作符
     * @return ConditionType，如果找不到则返回 null
     */
    public static ConditionType fromOperator(String operator) {
        if (operator == null) {
            return null;
        }
        for (ConditionType type : values()) {
            if (type.operator.equalsIgnoreCase(operator)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断指定的操作符是否有效
     *
     * @param operator SQL 操作符
     * @return true 表示有效，false 表示无效
     */
    public static boolean isValidOperator(String operator) {
        return fromOperator(operator) != null;
    }
}
