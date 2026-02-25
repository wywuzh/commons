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
package io.github.wywuzh.commons.pager;

/**
 * 类Order.java的实现描述：排序类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:27:25
 * @version v2.0.2
 * @since JDK 1.8
 */
public enum Order {

    /**
     * 升序
     */
    ASC("ASC"),
    /**
     * 降序
     */
    DESC("DESC");

    private final String value;

    // 缓存枚举值数组，避免每次调用values()都创建新数组
    private static final Order[] VALUES = values();

    private Order(String value) {
        this.value = value;
    }

    /**
     * 获取排序值
     *
     * @return 排序值
     */
    public String getValue() {
        return value;
    }

    /**
     * 根据值查找对应的枚举（区分大小写）
     *
     * @param value 排序值
     * @return 对应的枚举，未找到返回null
     */
    public static Order findByValue(String value) {
        return findByValue(value, true);
    }

    /**
     * 根据值查找对应的枚举（不区分大小写）
     *
     * @param value 排序值
     * @return 对应的枚举，未找到返回null
     */
    public static Order fromValue(String value) {
        return findByValue(value, false);
    }

    /**
     * 根据值查找对应的枚举（别名方法，不区分大小写）
     *
     * @param value 排序值
     * @return 对应的枚举，未找到返回null
     */
    public static Order of(String value) {
        return fromValue(value);
    }

    /**
     * 根据值查找对应的枚举（带默认值，不区分大小写）
     *
     * @param value        排序值
     * @param defaultOrder 默认值
     * @return 对应的枚举，未找到返回默认值
     */
    public static Order fromValueOrDefault(String value, Order defaultOrder) {
        Order order = fromValue(value);
        return order != null ? order : defaultOrder;
    }

    /**
     * 根据值查找对应的枚举
     *
     * @param value         排序值
     * @param caseSensitive 是否区分大小写
     * @return 对应的枚举，未找到返回null
     */
    private static Order findByValue(String value, boolean caseSensitive) {
        if (value == null) {
            return null;
        }
        for (Order order : VALUES) {
            if (caseSensitive ? order.value.equals(value) : order.value.equalsIgnoreCase(value)) {
                return order;
            }
        }
        return null;
    }

    /**
     * 验证给定的值是否为有效的排序类型
     *
     * @param value 待验证的值
     * @return true=有效，false=无效
     */
    public static boolean isValidValue(String value) {
        return fromValue(value) != null;
    }

    @Override
    public String toString() {
        return value;
    }
}
