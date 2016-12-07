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
package com.wuzh.commons.core.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 类DateTimeSerializer.java的实现描述：java.util.Date 日期时间格式
 * 
 * <pre>
 * 参考网址：
 *  1）http://blog.csdn.net/lk_blog/article/details/7685347
 * </pre>
 * 
 * @author 伍章红 2015年11月12日 上午10:33:54
 * @version v1.0.0
 * @since JDK 1.7
 */
public class DateTimeSerializer implements JsonSerializer<java.util.Date>, JsonDeserializer<java.util.Date> {

    @Override
    public JsonElement serialize(java.util.Date value, Type typeOfSrc, JsonSerializationContext context) {
        // 对象转换为json时调用，实现JsonDeserializer接口
        return new JsonPrimitive(value.getTime());
    }

    @Override
    public java.util.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // json转换为对象时调用，实现JsonSerializer接口
        long value = json.getAsLong();
        if (value == 0) {
            return null;
        }
        return new java.util.Date(value);
    }

}
