/*
 * Copyright 2015-2025 the original author or authors.
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
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import io.github.wywuzh.commons.core.poi.annotation.ExcelCell;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import io.github.wywuzh.commons.core.json.jackson.JsonMapper;
import io.github.wywuzh.commons.core.math.CalculationUtils;

/**
 * 类ReflectUtils的实现描述：反射工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-04-23 09:32:03
 * @version v2.2.6
 * @since JDK 1.8
 */
public class ReflectUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtils.class);

    public static Object invokeMethod(Object object, String methodName) throws Exception {
        Object[] args = ArrayUtils.EMPTY_OBJECT_ARRAY;
        return invokeMethod(object, methodName, args);
    }

    public static Object invokeMethod(Object object, String methodName, Object[] args) throws Exception {
        args = ArrayUtils.nullToEmpty(args);
        Class<?>[] parameterTypes = ClassUtils.toClass(args);
        return invokeMethod(object, methodName, parameterTypes, args);
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] args) {
        parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
        args = ArrayUtils.nullToEmpty(args);
        try {
            Method method = MethodUtils.getAccessibleMethod(object.getClass(), methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            LOGGER.error("Class={}, method={}, parameterTypes={}, args={} invoke方法请求失败：", object.getClass(), methodName, Arrays.toString(parameterTypes), Arrays.toString(args), e);
        }
        return null;
    }

    /**
     * 取到fieldName对应的实际字段/方法
     *
     * @param instance  实例对象
     * @param fieldName 字段名称
     * @return fieldName对应的实际字段/方法
     * @since v3.3.0
     */
    public static Object getRealField(Object instance, String fieldName) {
        // 如果对象为Map类型，则不需要取字段的类型
        if (instance instanceof Map) {
            return null;
        }

        // tips：先根据fieldName取Field，如果取不到就去找Method
        Class<?> clazz = instance.getClass();
        Field field = FieldUtils.getField(clazz, fieldName, true);
        if (field != null) {
            return field;
        }

        // 如果field字段无法取到值，则通过Getter方法取
        // 将第一个字符转换为大写
        String firstChar = fieldName.substring(0, 1);
        String lastChar = fieldName.substring(1);
        fieldName = firstChar.toUpperCase() + lastChar;
        Method fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + fieldName);
        if (fieldMethod == null) {
            // 此处为解决实体类字段第二个字符为大写的问题
            // 注意：这里是为了兼容第二个字符为大写的字段，在设计表结构的时候，尽量不要采用这种方式，这种设计方式不合理，会在Getter、Setter方法以及接口返回的时候出现意料之外的结果
            firstChar = fieldName.substring(0, 2);
            lastChar = fieldName.substring(2);
            fieldName = firstChar.toUpperCase() + lastChar;
            fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + fieldName);
        }
        if (fieldMethod != null) {
            fieldMethod.setAccessible(true);
        }

        return fieldMethod;
    }

    /**
     * 获取字段值
     *
     * @param instance  实例对象
     * @param fieldName 字段名称
     * @return 字段值
     */
    public static <T> T getValue(Object instance, String fieldName) throws IllegalAccessException {
        try {
            Object realValue = null;
            if (instance instanceof Map) {
                realValue = ((Map) instance).get(fieldName);
            } else {
                // 取到fieldName对应的实际字段/方法
                Object realField = getRealField(instance, fieldName);
                if (realField instanceof Field) {
                    realValue = ((Field) realField).get(instance);
                } else if (realField instanceof Method) {
                    realValue = MethodUtils.invokeMethod(instance, ((Method) realField).getName());
                }
            }

            return (T) realValue;

//            Object realValue = null;
//            if (instance instanceof Map) {
//                realValue = ((Map) instance).get(fieldName);
//            } else {
//                Class<?> clazz = instance.getClass();
//                Field field = FieldUtils.getField(clazz, fieldName, true);
//
//                if (field == null) {
//                    // 如果field字段无法取到值，则通过Getter方法取
//                    // 将第一个字符转换为大写
//                    String firstChar = fieldName.substring(0, 1);
//                    String lastChar = fieldName.substring(1);
//                    fieldName = firstChar.toUpperCase() + lastChar;
//                    Method fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + fieldName);
//                    if (fieldMethod == null) {
//                        // 此处为解决实体类字段第二个字符为大写的问题
//                        // 注意：这里是为了兼容第二个字符为大写的字段，在设计表结构的时候，尽量不要采用这种方式，这种设计方式不合理，会在Getter、Setter方法以及接口返回的时候出现意料之外的结果
//                        firstChar = fieldName.substring(0, 2);
//                        lastChar = fieldName.substring(2);
//                        fieldName = firstChar.toUpperCase() + lastChar;
//                        fieldMethod = MethodUtils.getAccessibleMethod(clazz, "get" + fieldName);
//                    }
//                    fieldMethod.setAccessible(true);
//                    realValue = MethodUtils.invokeMethod(instance, fieldMethod.getName());
//                } else {
//                    realValue = field.get(instance);
//                }
//            }
//            return (T) realValue;
        } catch (IllegalAccessException e) {
            LOGGER.error("instance={}, fieldName={} 字段获取值失败：", instance.getClass(), fieldName, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("instance={}, fieldName={} 字段获取值失败：", instance.getClass(), fieldName, e);
            throw new IllegalAccessException(e.getMessage());
        }
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
            if (instance instanceof Map) {
                ((Map) instance).put(fieldName, value);
            } else {
                Field declaredField = FieldUtils.getField(instance.getClass(), fieldName, true);
                declaredField.set(instance, value);
            }
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
            LOGGER.error("sourceFields={},targetFields={} sourceFields和targetFields的数组长度不一致！", JsonMapper.DEFAULT_JSON_MAPPER.toJson(sourceFields),
                    JsonMapper.DEFAULT_JSON_MAPPER.toJson(targetFields));
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
                LOGGER.error("sourceInstance.class={}, targetInstance.class={}, sourceFieldName={}, targetFieldName={} 获取源字段值、设置目标字段值失败：", sourceInstance.getClass(), targetInstance.getClass(),
                        sourceFieldName, targetFieldName, e);
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
            LOGGER.error("sourceFields={},targetFields={} sourceFields和targetFields的数组长度不一致！", JsonMapper.DEFAULT_JSON_MAPPER.toJson(sourceFields),
                    JsonMapper.DEFAULT_JSON_MAPPER.toJson(targetFields));
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
                // target
                Field targetField = FieldUtils.getField(targetInstance.getClass(), targetFieldName, true);
                if (!sourceField.getType().equals(targetField.getType())) {
                    throw new IllegalArgumentException("sourceFields和targetFields的数组字段类型不匹配！");
                }

                BigDecimal sourceFieldValue = getValue(sourceInstance, sourceFieldName, sourceField);
                BigDecimal targetFieldValue = getValue(targetInstance, targetFieldName, targetField);
                Object mergeValue = null;
                if (sourceField.getType() == Byte.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue).byteValue();
                } else if (sourceField.getType() == Short.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue).shortValue();
                } else if (sourceField.getType() == Integer.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue).intValue();
                } else if (sourceField.getType() == Long.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue).longValue();
                } else if (sourceField.getType() == Double.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue).doubleValue();
                } else if (sourceField.getType() == Float.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue).floatValue();
                } else if (sourceField.getType() == BigDecimal.class) {
                    mergeValue = CalculationUtils.add(sourceFieldValue, targetFieldValue);
                } else {
                    throw new IllegalArgumentException(sourceField.getName() + "字段类型不支持合并操作！");
                }

                targetField.set(targetInstance, mergeValue);
            } catch (Exception e) {
                LOGGER.error("sourceInstance.class={}, targetInstance.class={}, sourceFieldName={}, targetFieldName={} 获取源字段值、设置目标字段值失败：", sourceInstance.getClass(), targetInstance.getClass(),
                        sourceFieldName, targetFieldName, e);
                throw new Exception("获取源字段值、设置模板字段值失败");
            }
        }
    }

    private static BigDecimal getValue(Object instance, String fieldName, Field field) throws IllegalAccessException {
        Object fieldValue = ReflectUtils.getValue(instance, fieldName);
        if (fieldValue == null) {
            return null;
        }
        if (field.getType() == Byte.class) {
            return new BigDecimal((Byte) fieldValue);
        } else if (field.getType() == Short.class) {
            return new BigDecimal((Short) fieldValue);
        } else if (field.getType() == Integer.class) {
            return new BigDecimal((Integer) fieldValue);
        } else if (field.getType() == Long.class) {
            return new BigDecimal((Long) fieldValue);
        } else if (field.getType() == Double.class) {
            return new BigDecimal((Double) fieldValue);
        } else if (field.getType() == Float.class) {
            return new BigDecimal((Float) fieldValue);
        } else if (field.getType() == BigDecimal.class) {
            return (BigDecimal) fieldValue;
        }
        return null;
    }

}
