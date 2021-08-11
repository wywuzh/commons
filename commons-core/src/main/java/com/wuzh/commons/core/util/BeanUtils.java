/*
 * Copyright 2015-2021 the original author or authors.
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
package com.wuzh.commons.core.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类BeanUtils的实现描述：Bean工具转换类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/27 11:09
 * @version v3.0.0
 * @since JDK 1.8
 */
public class BeanUtils {

    /**
     * 获取对象属性，返回一个字符串数组
     *
     * @param o 对象
     * @return String[] 字符串数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 将一个Map对象转化为一个Bean.
     * 说明：只针对有一级属性的bean
     *
     * @param map   待转换的Map
     * @param clazz 转换后的Bean
     * @return 转换出来的Bean
     */
    public static <T> T convertMapToBean(Map<String, T> map, Class<T> clazz) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        T obj = clazz.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 将一个Bean对象转化为一个Map.
     * 说明：只针对有一级属性的bean
     *
     * @param bean 要转化的Bean对象
     * @return 转化出来的Map对象
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> convertBeanToMap(Object bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (StringUtils.equalsIgnoreCase(propertyName, "class")) {
                continue;
            }
            Method readMethod = descriptor.getReadMethod();
            Object result = readMethod.invoke(bean, new Object[0]);
            if (result != null) {
                returnMap.put(propertyName, result);
            } else {
                returnMap.put(propertyName, null);
            }
        }
        return returnMap;
    }

}