/*
 * Copyright 2015-2022 the original author or authors.
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
package com.wuzh.commons.dbutils.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

/**
 * 类BeanAnnotationUtil.java的实现描述：实体类注解工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月21日 下午8:31:39
 * @version v1.0.0
 * @since JDK 1.7
 */
public class BeanAnnotationUtil {
    private static final Log logger = LogFactory.getLog(BeanAnnotationUtil.class);

    /**
     * 判断Method是否存在指定的Annotation
     * 
     * @param method
     *            Method方法
     * @param annotation
     *            元数据
     * @return 返回true表示该Method存在指定的Annotation，否则不存在
     */
    public static boolean isBeanAnnotated(Method method, Annotation annotation) {
        Assert.notNull(method, "method must not be null");
        Assert.notNull(annotation, "annotation must not be null");
        return AnnotationUtils.findAnnotation(method, annotation.getClass()) != null;
    }

    /**
     * 获取table表名
     * 
     * @param clazz
     *            Class类
     * @return 返回指定的Class类的&#64;Table映射的表名
     */
    public static String getTableName(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        Table table = clazz.getAnnotation(Table.class);
        if (null != table && StringUtils.isNotEmpty(table.name())) {
            // 获取大写的表名
            return table.name().toUpperCase();
        }

        return null;
    }

    /**
     * 获取Class类上的<code>column</code> -->> <code>field</code>映射数据
     * 
     * @param clazz
     *            Class类
     * @return 返回的映射数据中，key为column，value为field
     */
    public static Map<String, String> getColumnsMap(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        // key=column,value=field
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] allFields = FieldUtils.getAllFields(clazz);
            for (Field field : allFields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }

                // 获取字段上面的Column
                Column column = field.getAnnotation(Column.class);
                if (null == column) {
                    // 获取get方法上的Column
                    Method method = clazz
                            .getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                    column = AnnotationUtils.findAnnotation(method, Column.class);
                }

                if (null != column) {
                    if (StringUtils.isNotEmpty(column.name())) {
                        // 字段名转为大写
                        map.put(column.name().toUpperCase(), fieldName);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 获取Class类上的<code>field</code> -->> <code>column</code>映射数据
     * 
     * @param clazz
     *            Class类
     * @return 返回的映射数据中，key为field，value为column
     */
    public static Map<String, String> getFieldsMap(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        // key=field,value=column
        Map<String, String> map = new HashMap<String, String>();
        try {
            Field[] allFields = FieldUtils.getAllFields(clazz);
            for (Field field : allFields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }

                // 获取字段上面的Column
                Column column = field.getAnnotation(Column.class);
                if (null == column) {
                    // 获取get方法上的Column
                    Method method = clazz
                            .getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                    column = AnnotationUtils.findAnnotation(method, Column.class);
                }
                // 字段名转为大写
                map.put(fieldName, column == null ? null
                        : (StringUtils.isEmpty(column.name()) ? null : column.name().toUpperCase()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

}
