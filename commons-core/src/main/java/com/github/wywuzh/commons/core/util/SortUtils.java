/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.util;

import java.util.*;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang3.StringUtils;

/**
 * 类ComparatorUtils的实现描述：排序工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-22 23:02:11
 * @version v2.3.2
 * @since JDK 1.8
 */
public class SortUtils {

    /**
     * 排序，正序
     *
     * @param data       源数据
     * @param fieldNames 需要排序的字段名
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> data, String[] fieldNames) {
        if (fieldNames == null || fieldNames.length == 0) {
            return data;
        }
        List<Object> sortFields = new LinkedList<>();
        for (String fieldName : fieldNames) {
            Comparator comparator = ComparableComparator.getInstance();
            // 允许null
            comparator = ComparatorUtils.nullHighComparator(comparator);

            sortFields.add(new BeanComparator<>(fieldName, comparator));
        }
        // 创建一个排序链
        ComparatorChain multiSort = new ComparatorChain(sortFields);
        Collections.sort(data, multiSort);
        return data;
    }

    /**
     * 排序，正序
     *
     * @param data       源数据
     * @param fieldNames 需要排序的字段名
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> data, List<String> fieldNames) {
        if (CollectionUtils.isEmpty(fieldNames)) {
            return data;
        }
        return sort(data, fieldNames.toArray(new String[0]));
    }

    /**
     * 排序，正序
     *
     * @param data         源数据
     * @param fieldNameMap 需要排序的字段：key=字段名，value=排序类型（ASC|DESC）
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> data, Map<String, String> fieldNameMap) {
        if (fieldNameMap == null || fieldNameMap.size() == 0) {
            return data;
        }
        List<Object> sortFields = new LinkedList<>();
        for (Map.Entry<String, String> entry : fieldNameMap.entrySet()) {
            // 字段名
            String fieldName = entry.getKey();
            // 排序类型：ASC, DESC
            String sortType = entry.getValue();

            Comparator comparator = ComparableComparator.getInstance();
            // 允许null
            comparator = ComparatorUtils.nullHighComparator(comparator);
            // 默认为正序，可选择设置为逆序
            if (StringUtils.equalsIgnoreCase(sortType, "DESC")) {
                comparator = ComparatorUtils.reversedComparator(comparator);
            }

            sortFields.add(new BeanComparator<>(fieldName, comparator));
        }
        // 创建一个排序链
        ComparatorChain multiSort = new ComparatorChain(sortFields);
        Collections.sort(data, multiSort);
        return data;
    }

}
