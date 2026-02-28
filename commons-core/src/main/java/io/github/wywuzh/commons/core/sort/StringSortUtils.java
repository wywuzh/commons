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
package io.github.wywuzh.commons.core.sort;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import io.github.wywuzh.commons.core.util.Assert;

/**
 * 类StringSortUtils的实现描述：排序工具类 - 字符
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-02-28 09:42:02
 * @version v3.5.0
 * @since JDK 17
 */
public class StringSortUtils {

    /**
     * 排序
     *
     * @param list 待排序的字符串列表
     * @return 排序后的字符串列表
     */
    public static List<String> sort(List<String> list) {
        return sort(list, "ASC");
    }

    /**
     * 排序
     *
     * @param list     待排序的字符串列表
     * @param sortType 排序类型：ASC/DESC
     * @return 排序后的字符串列表
     */
    public static List<String> sort(List<String> list, String sortType) {
        Assert.notEmpty(list, "list must not be empty");

        // 排序
        if (StringUtils.equalsIgnoreCase(sortType, "ASC")) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        } else if (StringUtils.equalsIgnoreCase(sortType, "DESC")) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
        }
        return list;
    }

    /**
     * 排序
     *
     * @param set 待排序的字符串集合
     * @return 排序后的字符串集合
     */
    public static Set<String> sort(Set<String> set) {
        return sort(set, "ASC");
    }

    /**
     * 排序
     *
     * @param set      待排序的字符串集合
     * @param sortType 排序类型：ASC/DESC
     * @return 排序后的字符串集合
     */
    public static Set<String> sort(Set<String> set, String sortType) {
        Assert.notEmpty(set, "set must not be empty");

        // 排序
        Set<String> sortSet = null;
        if (StringUtils.equalsIgnoreCase(sortType, "ASC")) {
            sortSet = new TreeSet<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        } else if (StringUtils.equalsIgnoreCase(sortType, "DESC")) {
            sortSet = new TreeSet<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
        }
        sortSet.addAll(set);
        return sortSet;
    }

}
