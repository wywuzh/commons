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
package io.github.wywuzh.commons.core.reflect;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * 类ReflectFieldCache的实现描述：反射字段缓存工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-01-25 21:09:06
 * @version v3.5.0
 * @since JDK 17
 */
public class ReflectFieldCache {

    /**
     * Field缓存，key为clazz+fieldName的组合，value为对应的Field对象
     */
    private static final Map<String, Field> FIELD_CACHE = new ConcurrentHashMap<>(256);

    /**
     * 获取类的字段，优先从缓存中获取
     *
     * @param clazz     类对象
     * @param fieldName 字段名称
     * @return 字段对象
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        // 构建缓存key
        String cacheKey = buildCacheKey(clazz, fieldName);

        // computeIfAbsent 保证线程安全且只执行一次反射
        return FIELD_CACHE.computeIfAbsent(cacheKey, k -> {
            try {
                // 使用 FieldUtils.getField 获取字段（支持父类字段）
                return FieldUtils.getField(clazz, fieldName, true);
            } catch (Exception e) {
                throw new RuntimeException("获取字段失败: class=" + clazz.getName() + ", field=" + fieldName, e);
            }
        });
    }

    /**
     * 构建缓存key
     *
     * @param clazz     类对象
     * @param fieldName 字段名称
     * @return 缓存key
     */
    private static String buildCacheKey(Class<?> clazz, String fieldName) {
        return clazz.getName() + "#" + fieldName;
    }

    /**
     * 清空所有缓存
     */
    public static void clear() {
        FIELD_CACHE.clear();
    }

    /**
     * 获取缓存大小
     *
     * @return 缓存大小
     */
    public static int size() {
        return FIELD_CACHE.size();
    }
}
