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

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import io.github.wywuzh.commons.core.util.Assert;

/**
 * 类StringSortUtils的实现描述：排序工具类 - Map
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-02-28 09:42:02
 * @version v3.5.0
 * @since JDK 17
 */
public class MapSortUtils {

    /**
     * 排序
     *
     * @param sourceMap 源Map
     * @return 排序后的Map
     */
    public static Map<String, Object> sort(Map<String, Object> sourceMap) {
        return sort(sourceMap, "ASC");
    }

    /**
     * 排序
     *
     * @param sourceMap 源Map
     * @param sortType  排序类型：ASC/DESC
     * @return 排序后的Map
     */
    public static Map<String, Object> sort(Map<String, Object> sourceMap, String sortType) {
        Assert.notEmpty(sourceMap, "sourceMap must not be empty");

        // 排序
        if (StringUtils.equalsIgnoreCase(sortType, "ASC")) {
            Map<String, Object> resultMap = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2); // 正序：从小到大排序
                }
            });
            resultMap.putAll(sourceMap);
            return resultMap;
        } else if (StringUtils.equalsIgnoreCase(sortType, "DESC")) {
            Map<String, Object> resultMap = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1); // 倒序：从大到小排序
                }
            });
            resultMap.putAll(sourceMap);
            return resultMap;
        }
        return sourceMap;
    }

}
