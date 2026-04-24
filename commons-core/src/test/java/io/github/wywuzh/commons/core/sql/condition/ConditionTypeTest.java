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

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 类ConditionTypeTest.java的实现描述：Where条件字段匹配类型测试
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-03-05
 * @version v3.5.0
 * @since JDK 17
 */
@Slf4j
public class ConditionTypeTest {

    /**
     * 测试枚举值的 SQL 操作符
     */
    @Test
    public void testGetOperator() {
        log.info("测试枚举值的 SQL 操作符");

        assert "=".equals(ConditionType.column.getOperator());
        assert "=".equals(ConditionType.equals.getOperator());
        assert "LIKE".equals(ConditionType.like.getOperator());
        assert "IN".equals(ConditionType.in.getOperator());
        assert "UNION".equals(ConditionType.unions.getOperator());
        assert "BETWEEN".equals(ConditionType.between.getOperator());
        assert "<>".equals(ConditionType.notEquals.getOperator());
        assert "NOT LIKE".equals(ConditionType.notLike.getOperator());
        assert "NOT IN".equals(ConditionType.notIn.getOperator());
        assert "NOT BETWEEN".equals(ConditionType.notBetween.getOperator());
        assert ">".equals(ConditionType.gt.getOperator());
        assert ">=".equals(ConditionType.gte.getOperator());
        assert "<".equals(ConditionType.lt.getOperator());
        assert "<=".equals(ConditionType.lte.getOperator());
        assert "IS NULL".equals(ConditionType.isNull.getOperator());
        assert "IS NOT NULL".equals(ConditionType.isNotNull.getOperator());

        log.info("所有枚举值的操作符测试通过");
    }

    /**
     * 测试批量操作判断
     */
    @Test
    public void testIsBatch() {
        log.info("测试批量操作判断");

        assert ConditionType.in.isBatch();
        assert ConditionType.notIn.isBatch();
        assert ConditionType.unions.isBatch();
        assert !ConditionType.equals.isBatch();
        assert !ConditionType.like.isBatch();
        assert !ConditionType.gt.isBatch();

        log.info("批量操作判断测试通过");
    }

    /**
     * 测试区间操作判断
     */
    @Test
    public void testIsBetween() {
        log.info("测试区间操作判断");

        assert ConditionType.between.isBetween();
        assert ConditionType.notBetween.isBetween();
        assert !ConditionType.equals.isBetween();
        assert !ConditionType.like.isBetween();
        assert !ConditionType.in.isBetween();

        log.info("区间操作判断测试通过");
    }

    /**
     * 测试模糊匹配操作判断
     */
    @Test
    public void testIsLike() {
        log.info("测试模糊匹配操作判断");

        assert ConditionType.like.isLike();
        assert ConditionType.notLike.isLike();
        assert !ConditionType.equals.isLike();
        assert !ConditionType.in.isLike();
        assert !ConditionType.between.isLike();

        log.info("模糊匹配操作判断测试通过");
    }

    /**
     * 测试空值判断操作判断
     */
    @Test
    public void testIsNullCheck() {
        log.info("测试空值判断操作判断");

        assert ConditionType.isNull.isNullCheck();
        assert ConditionType.isNotNull.isNullCheck();
        assert !ConditionType.equals.isNullCheck();
        assert !ConditionType.like.isNullCheck();
        assert !ConditionType.in.isNullCheck();

        log.info("空值判断操作判断测试通过");
    }

    /**
     * 测试根据操作符获取枚举
     */
    @Test
    public void testFromOperator() {
        log.info("测试根据操作符获取枚举");

        assert ConditionType.fromOperator("=") == ConditionType.equals;
        assert ConditionType.fromOperator("LIKE") == ConditionType.like;
        assert ConditionType.fromOperator("IN") == ConditionType.in;
        assert ConditionType.fromOperator("BETWEEN") == ConditionType.between;
        assert ConditionType.fromOperator("<>") == ConditionType.notEquals;
        assert ConditionType.fromOperator("NOT LIKE") == ConditionType.notLike;
        assert ConditionType.fromOperator("NOT IN") == ConditionType.notIn;
        assert ConditionType.fromOperator("NOT BETWEEN") == ConditionType.notBetween;
        assert ConditionType.fromOperator(">") == ConditionType.gt;
        assert ConditionType.fromOperator(">=") == ConditionType.gte;
        assert ConditionType.fromOperator("<") == ConditionType.lt;
        assert ConditionType.fromOperator("<=") == ConditionType.lte;
        assert ConditionType.fromOperator("IS NULL") == ConditionType.isNull;
        assert ConditionType.fromOperator("IS NOT NULL") == ConditionType.isNotNull;

        // 测试不区分大小写
        assert ConditionType.fromOperator("=") == ConditionType.equals;
        assert ConditionType.fromOperator("like") == ConditionType.like;
        assert ConditionType.fromOperator("In") == ConditionType.in;
        assert ConditionType.fromOperator("between") == ConditionType.between;

        // 测试无效操作符
        assert ConditionType.fromOperator("INVALID") == null;
        assert ConditionType.fromOperator(null) == null;

        log.info("根据操作符获取枚举测试通过");
    }

    /**
     * 测试操作符有效性判断
     */
    @Test
    public void testIsValidOperator() {
        log.info("测试操作符有效性判断");

        assert ConditionType.isValidOperator("=");
        assert ConditionType.isValidOperator("LIKE");
        assert ConditionType.isValidOperator("IN");
        assert ConditionType.isValidOperator("BETWEEN");
        assert ConditionType.isValidOperator("<>");
        assert ConditionType.isValidOperator("NOT LIKE");
        assert ConditionType.isValidOperator("NOT IN");
        assert ConditionType.isValidOperator(">");
        assert ConditionType.isValidOperator(">=");
        assert ConditionType.isValidOperator("<");
        assert ConditionType.isValidOperator("<=");
        assert ConditionType.isValidOperator("IS NULL");
        assert ConditionType.isValidOperator("IS NOT NULL");

        assert !ConditionType.isValidOperator("INVALID");
        assert !ConditionType.isValidOperator(null);

        log.info("操作符有效性判断测试通过");
    }

    /**
     * 测试所有枚举值
     */
    @Test
    public void testAllEnumValues() {
        log.info("测试所有枚举值");

        ConditionType[] values = ConditionType.values();
        log.info("ConditionType 共有 {} 个枚举值", values.length);

        // 打印所有枚举值
        for (ConditionType type : values) {
            log.info("枚举值: {}, 操作符: {}, 批量: {}, 区间: {}, 模糊: {}, 空值: {}",
                    type.name(), type.getOperator(), type.isBatch(),
                    type.isBetween(), type.isLike(), type.isNullCheck());
        }

        // 验证枚举值的数量
        assert values.length == 16;

        log.info("所有枚举值测试通过");
    }
}
