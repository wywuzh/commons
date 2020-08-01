/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.json.jsonlib;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 类JsonDateValueProcessor.java的实现描述：java.util.Date对象转换为json工具类
 * <p>
 * 在把Java对象转为json数据的过程中，当Java对象中有Date对象时，Java对象转换成json，<br>
 * 把date对象分开转化，即：年、月、日、时、分、秒，每个都当成一个对象给转化了，为了保持date对象的原有值
 * 
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:48:07
 * @version v1.0.0
 * @since JDK 1.7
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

    @Deprecated
    String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    @Deprecated
    String PATTERN_DATE = "yyyy-MM-dd";

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value) {
        try {
            if (null == value) {
                return null;
            }

            if (value instanceof java.util.Date) {

                return ((java.util.Date) value).getTime();
            } else if (value instanceof java.sql.Date) {
                return ((java.sql.Date) value).getTime();
            } else if (value instanceof java.sql.Timestamp) {
                return ((java.sql.Timestamp) value).getTime();
            }

            // if (value instanceof java.util.Date) {
            //
            // return DateUtil
            // .format((java.util.Date) value, PATTERN_DATETIME);
            // } else if (value instanceof java.sql.Date) {
            // return DateUtil.format((java.sql.Date) value, PATTERN_DATE);
            // } else if (value instanceof java.sql.Timestamp) {
            // return DateUtil.format(new java.util.Date(
            // ((java.sql.Timestamp) value).getTime()),
            // PATTERN_DATETIME);
            // }
            return value.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
