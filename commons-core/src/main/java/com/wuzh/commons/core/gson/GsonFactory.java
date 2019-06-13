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

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * 类GsonFactory.java的实现描述：Google Gson工厂
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:48:18
 * @version v1.0.0
 * @since JDK 1.7
 */
public class GsonFactory {

    /**
     * 得到GsonBuilder对象示例
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @return
     */
    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder();
    }

    /**
     * 得到GsonBuilder对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param clazz
     *            需要转换的类的Class
     * @param typeSerializer
     *            类型转换器接口
     * @return
     */
    public static <T> GsonBuilder getGsonBuilder(Class<TypeSerializer<T>> clazz, TypeSerializer<T> typeSerializer) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.registerTypeAdapter(clazz, typeSerializer);
        return gsonBuilder;
    }

    /**
     * 得到GsonBuilder对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param type
     *            需要转换的类的Type
     * @param typeSerializer
     *            类型转换器接口
     * @return
     */
    public static <T> GsonBuilder getGsonBuilder(Type type, TypeSerializer<T> typeSerializer) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeSerializer);
        return gsonBuilder;
    }

    /**
     * 得到GsonBuilder对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param list
     *            类型转换器接口集合
     * @return
     */
    public static <T> GsonBuilder getGsonBuilder(List<? extends TypeSerializer<T>> list) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        for (TypeSerializer<T> serializer : list) {
            gsonBuilder.registerTypeAdapter(serializer.getType(), serializer);
        }
        return gsonBuilder;
    }

    /**
     * 得到GsonBuilder对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param serializers
     *            类型转换器接口数组
     * @return
     */
    public static <T> GsonBuilder getGsonBuilder(TypeSerializer<T>[] serializers) {
        if (null == serializers || serializers.length == 0) {
            return getGsonBuilder();
        }
        return getGsonBuilder(Arrays.asList(serializers));
    }

    /**
     * 得到Gson对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @return
     */
    public static Gson getGson() {
        return getGsonBuilder().create();
    }

    /**
     * 得到Gson对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param omitNull
     *            是否忽略value值为null的属性
     * @return
     */
    public static Gson getGson(boolean omitNull) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        if (!omitNull) {
            gsonBuilder.serializeNulls();
        }
        return gsonBuilder.create();
    }

    /**
     * 得到Gson对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param serializers
     *            类型转换器接口数组
     * @return
     */
    public static <T> Gson getGson(TypeSerializer<T>[] serializers) {
        return getGsonBuilder(serializers).create();
    }

    /**
     * 得到Gson对象示例
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月3日 下午5:49:04
     * @param serializers
     *            类型转换器接口数组
     * @param omitNull
     *            是否忽略value值为null的属性
     * @return
     */
    public static <T> Gson getGson(TypeSerializer<T>[] serializers, boolean omitNull) {
        GsonBuilder gsonBuilder = getGsonBuilder(serializers);
        if (!omitNull) {
            gsonBuilder.serializeNulls();
        }
        return gsonBuilder.create();
    }

    public static JsonElement getJsonElement(String json) throws JsonSyntaxException {
        return new JsonParser().parse(json);
    }

    public static JsonElement getJsonElement(Reader json) throws JsonIOException, JsonSyntaxException {
        return new JsonParser().parse(json);
    }

    public static JsonElement getJsonElement(JsonReader json) throws JsonIOException, JsonSyntaxException {
        return new JsonParser().parse(json);
    }
}
