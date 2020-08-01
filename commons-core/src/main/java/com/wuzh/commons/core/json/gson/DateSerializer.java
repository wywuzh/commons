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
package com.wuzh.commons.core.json.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * 类DateSerializer.java的实现描述：java.sql.Date 日期格式转换
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:39:07
 * @version v1.0.0
 * @since JDK 1.7
 */
public class DateSerializer implements TypeSerializer<java.sql.Date> {

    @Override
    public Type getType() {
        return java.sql.Date.class;
    }

    @Override
    public JsonElement serialize(java.sql.Date value, Type typeOfSrc, JsonSerializationContext context) {
        // 对象转换为json时调用，实现JsonDeserializer接口
        return new JsonPrimitive(value.getTime());
    }

    @Override
    public java.sql.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // json转换为对象时调用，实现JsonSerializer接口
        long value = json.getAsLong();
        if (value == 0) {
            return null;
        }
        return new java.sql.Date(value);
    }

}
