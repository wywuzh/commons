/*
 * Copyright 2015-2017 the original author or authors.
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
import java.sql.Timestamp;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

/**
 * 类TimestampSerializer.java的实现描述：java.sql.Timestamp格式转换
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年5月7日 下午9:34:31
 * @version v3.1.10
 * @since JDK 1.7
 */
public class TimestampSerializer implements TypeSerializer<Timestamp> {

    @Override
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());
    }

    @Override
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        long time = json.getAsLong();
        if (time == 0) {
            return null;
        }
        return new Timestamp(time);
    }

    @Override
    public Type getType() {
        return java.sql.Timestamp.class;
    }

}
