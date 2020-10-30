/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.core.reflect;

import com.wuzh.commons.core.util.CalculationUtils;
import com.wuzh.commons.core.json.jackson.JsonMapper;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 类ReflectUtils的实现描述：反射工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-04-23 09:32:03
 * @version v2.2.6
 * @since JDK 1.8
 */
public class ReflectUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 获取字段值
     *
     * @param instance  实例对象
     * @param fieldName 字段名称
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> T getValue(Object instance, String fieldName) throws IllegalAccessException {
        Field declaredField = FieldUtils.getField(instance.getClass(), fieldName, true);
        return (T) declaredField.get(instance);
    }

    /**
     * 设置字段值
     *
     * @param instance  实例对象
     * @param fieldName 字段名称
     * @param value     字段值
     * @param <T>
     * @throws Exception
     */
    public static <T> void setValue(Object instance, String fieldName, T value) throws Exception {
        try {
            Field declaredField = FieldUtils.getField(instance.getClass(), fieldName, true);
            declaredField.set(instance, value);
        } catch (Exception e) {
            LOGGER.error("instance={}, fieldName={} 字段设置值失败：", instance.getClass(), fieldName, e);
            throw new IllegalAccessException(e.getMessage());
        }
    }

    /**
     * 复制属性值
     *
     * @param sourceInstance 源实例对象
     * @param targetInstance 目标实例对象
     * @param fields         字段
     * @throws Exception
     */
    public static void copyProperties(Object sourceInstance, Object targetInstance, String[] fields) throws Exception {
        // 源字段
        String[] sourceFields = fields;
        // 目标字段
        String[] targetFields = fields;

        copyProperties(sourceInstance, targetInstance, sourceFields, targetFields);
    }

    /**
     * 复制属性值
     *
     * @param sourceInstance 源实例对象
     * @param targetInstance 目标实例对象
     * @param sourceFields   源字段
     * @param targetFields   目标字段
     * @throws Exception
     */
    public static void copyProperties(Object sourceInstance, Object targetInstance, String[] sourceFields, String[] targetFields) throws Exception {
        Assert.notNull(sourceInstance, "sourceInstance is not null");
        Assert.notNull(targetInstance, "targetInstance is not null");
        Assert.notEmpty(sourceFields, "sourceFields is not empty");
        Assert.notEmpty(targetFields, "targetFields is not empty");

        if (sourceFields.length != targetFields.length) {
            LOGGER.error("sourceFields={},targetFields={} sourceFields和targetFields的数组长度不一致！", JsonMapper.DEFAULT_JSON_MAPPER.toJson(sourceFields), JsonMapper.DEFAULT_JSON_MAPPER.toJson(targetFields));
            throw new IllegalArgumentException("sourceFields和targetFields的数组长度不一致！");
        }

        for (int i = 0; i < sourceFields.length; i++) {
            // 源字段
            String sourceFieldName = sourceFields[i];
            // 目标字段
            String targetFieldName = targetFields[i];

            try {
                Field targetField = FieldUtils.getField(targetInstance.getClass(), targetFieldName, true);
                targetField.set(targetInstance, ReflectUtils.getValue(sourceInstance, sourceFieldName));
            } catch (Exception e) {
                LOGGER.error("sourceInstance.class={}, targetInstance.class={}, sourceFieldName={}, targetFieldName={} 获取源字段值、设置目标字段值失败：",
                        sourceInstance.getClass(), targetInstance.getClass(), sourceFieldName, targetFieldName, e);
                throw new Exception("获取源字段值、设置模板字段值失败");
            }
        }
    }


    /**
     * 合并属性值
     *
     * @param sourceInstance 源实例对象
     * @param targetInstance 目标实例对象
     * @param fields         字段
     * @throws Exception
     */
    public static void mergeProperties(Object sourceInstance, Object targetInstance, String[] fields) throws Exception {
        // 源字段
        String[] sourceFields = fields;
        // 目标字段
        String[] targetFields = fields;

        mergeProperties(sourceInstance, targetInstance, sourceFields, targetFields);
    }

    /**
     * 合并属性值
     *
     * @param sourceInstance 源实例对象
     * @param targetInstance 目标实例对象
     * @param sourceFields   源字段
     * @param targetFields   目标字段
     * @throws Exception
     */
    public static void mergeProperties(Object sourceInstance, Object targetInstance, String[] sourceFields, String[] targetFields) throws Exception {
        Assert.notNull(sourceInstance, "sourceInstance is not null");
        Assert.notNull(targetInstance, "targetInstance is not null");
        Assert.notEmpty(sourceFields, "sourceFields is not empty");
        Assert.notEmpty(targetFields, "targetFields is not empty");

        if (sourceFields.length != targetFields.length) {
            LOGGER.error("sourceFields={},targetFields={} sourceFields和targetFields的数组长度不一致！", JsonMapper.DEFAULT_JSON_MAPPER.toJson(sourceFields), JsonMapper.DEFAULT_JSON_MAPPER.toJson(targetFields));
            throw new IllegalArgumentException("sourceFields和targetFields的数组长度不一致！");
        }

        for (int i = 0; i < sourceFields.length; i++) {
            // 源字段
            String sourceFieldName = sourceFields[i];
            // 目标字段
            String targetFieldName = targetFields[i];

            try {
                // source
                Field sourceField = FieldUtils.getField(sourceInstance.getClass(), sourceFieldName, true);
                Object sourceFieldValue = sourceField.get(sourceInstance);
                // target
                Field targetField = FieldUtils.getField(targetInstance.getClass(), targetFieldName, true);
                Object targetFieldValue = targetField.get(targetField);
                Object mergeValue = null;
                if (sourceField.getType().equals(targetField.getType())) {
                    if (targetField.getType().getName().equals(Byte.class)
                            || targetField.getType().getName().equals(Short.class)
                            || targetField.getType().getName().equals(Integer.class)
                            || targetField.getType().getName().equals(Long.class)
                            || targetField.getType().getName().equals(Double.class)
                            || targetField.getType().getName().equals(Float.class)
                            || targetField.getType().getName().equals(BigDecimal.class)) {
                        mergeValue = CalculationUtils.add(new BigDecimal(sourceFieldValue.toString()), new BigDecimal(targetFieldValue.toString()));
                    }
                }

                targetField.set(targetInstance, mergeValue);
            } catch (Exception e) {
                LOGGER.error("sourceInstance.class={}, targetInstance.class={}, sourceFieldName={}, targetFieldName={} 获取源字段值、设置目标字段值失败：",
                        sourceInstance.getClass(), targetInstance.getClass(), sourceFieldName, targetFieldName, e);
                throw new Exception("获取源字段值、设置模板字段值失败");
            }
        }
    }

}