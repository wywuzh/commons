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
package io.github.wywuzh.commons.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 类型转换工具类
 *
 * @author wuzh
 * @version 1.0, 10/30/2013
 * @since JDK 1.6
 */
@Slf4j
public class ConvertUtil {

    /**
     * 将JavaBean对象转换为Map对象。
     * <p>
     * 将传入的JavaBean对象转换为Map对象
     * </p>
     *
     * @param bean 传入的JavaBean对象
     * @return Map 把JavaBean转换为Map的Map对象
     * @author wuzh, 10/30/2013
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> convertBean(Object bean) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Class type = bean.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertDescriptors) {
                String propertyName = descriptor.getName();
                String typeName = descriptor.getPropertyType().getName();
                if (!propertyName.equals("class")) {
                    Object result = descriptor.getReadMethod().invoke(bean, new Object[0]);
                    if (null != result) {
                        if ("java.util.Date".equals(typeName)) {
                            resultMap.put(propertyName, DateUtils.format((java.util.Date) result, DateUtils.PATTERN_DATE_TIME));
                        } else if ("java.sql.Date".equals(typeName)) {
                            resultMap.put(propertyName, DateUtils.format((java.sql.Date) result, DateUtils.PATTERN_DATE));
                        } else if ("java.sql.Time".equals(typeName)) {
                            resultMap.put(propertyName, DateUtils.format((Time) result, DateUtils.PATTERN_TIME));
                        } else {
                            resultMap.put(propertyName, result);
                        }
                    } else {
                        resultMap.put(propertyName, "");
                    }
                }
            }
        } catch (IntrospectionException e) {
            log.error("转换JavaBean为Map时发生IntrospectionException异常", e);
        } catch (IllegalArgumentException e) {
            log.error("转换JavaBean为Map时发生IllegalArgumentException异常", e);
        } catch (IllegalAccessException e) {
            log.error("转换JavaBean为Map时发生IllegalAccessException异常", e);
        } catch (InvocationTargetException e) {
            log.error("转换JavaBean为Map时发生InvocationTargetException异常", e);
        }
        return resultMap;
    }

    /**
     * 将Map对象转换为JavaBean对象
     *
     * @param sourceMap Map对象
     * @param clazz     JavaBean对象
     * @return JavaBean对象
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Map sourceMap, Class clazz) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            Object object = clazz.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                String typeName = descriptor.getPropertyType().getName();
                if (sourceMap.containsKey(propertyName)) {
                    try {
                        Object value = sourceMap.get(propertyName);
                        Object[] args = new Object[1];
                        if ("java.sql.Date".equals(typeName)) {
                            java.util.Date date = DateUtils.parse(value.toString(), DateUtils.PATTERN_DATE);
                            args[0] = new java.sql.Date(date.getTime());
                        } else if ("java.sql.Time".equals(typeName)) {
                            java.util.Date date = DateUtils.parse(value.toString(), DateUtils.PATTERN_TIME);
                            args[0] = new java.sql.Time(date.getTime());
                        } else if ("java.util.Date".equals(typeName)) {
                            args[0] = DateUtils.parse(value.toString(), DateUtils.PATTERN_DATE_TIME);
                        } else if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
                            args[0] = Long.valueOf(value.toString());
                        } else if ("java.lang.Short".equals(typeName) || "short".equals(typeName)) {
                            args[0] = Short.valueOf(value.toString());
                        } else if ("java.lang.Byte".equals(typeName) || "byte".equals(typeName)) {
                            args[0] = Byte.valueOf(value.toString());
                        } else if ("java.lang.Character".equals(typeName) || "char".equals(typeName)) {
                            String strValue = value.toString();
                            args[0] = strValue.isEmpty() ? '\0' : strValue.charAt(0);
                        } else if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
                            args[0] = Integer.valueOf(value.toString());
                        } else if ("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
                            args[0] = Double.valueOf(value.toString());
                        } else if ("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
                            args[0] = Float.valueOf(value.toString());
                        } else {
                            args[0] = value;
                        }
                        descriptor.getWriteMethod().invoke(object, args);
                    } catch (Exception e) {
                        log.error("propertyName={}, typeName={} 转换Map为JavaBean时发生异常", propertyName, typeName, e);
                        throw e;
                    }
                }
            }
            return object;
        } catch (Exception e) {
            log.error("将Map对象转换为JavaBean对象失败", e);
        }
        return null;
    }
}
