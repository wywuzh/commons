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
import com.google.gson.stream.JsonReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类GsonUtil.java的实现描述：Google JSON转换工具类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午9:29:46
 * @version v1.0.0
 * @since JDK 1.7
 */
public class GsonUtil {
    private static final Log logger = LogFactory.getLog(GsonUtil.class);

    private static GsonBuilder gsonBuilder;
    public static Gson gson = null;

    static {
        gsonBuilder = new GsonBuilder();
        // 注册java.util.Date 日期时间格式转换
        gsonBuilder.registerTypeAdapter(Date.class, new DateTimeSerializer());
        // 注册java.sql.Date 日期格式转换
        gsonBuilder.registerTypeAdapter(java.sql.Date.class, new DateSerializer());
        // 解决value为null时key不存在的问题
        gsonBuilder.serializeNulls();

        create();
    }

    /**
     * 注册类型转换器
     *
     * @param clazz          数据类型
     * @param typeSerializer 类型转换器
     */
    public static <T> void register(Class<TypeSerializer<T>> clazz, TypeSerializer<T> typeSerializer) {
        gsonBuilder.registerTypeAdapter(clazz, typeSerializer);
    }

    /**
     * 注册类型转换器
     *
     * @param type           数据类型
     * @param typeSerializer 类型转换器
     */
    public static <T> void register(Type type, TypeSerializer<T> typeSerializer) {
        gsonBuilder.registerTypeAdapter(type, typeSerializer);
    }

    /**
     * 注册类型转换器
     *
     * @param list 类型转换器集合
     */
    public static <T> void register(List<? extends TypeSerializer<T>> list) {
        for (TypeSerializer<T> serializer : list) {
            gsonBuilder.registerTypeAdapter(serializer.getType(), serializer);
        }
    }

    public static GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public static Gson create() {
        gson = gsonBuilder.create();
        return gson;
    }

    /**
     * 将Bean对象转换为JSON
     *
     * @param bean 实现Serializable接口的Bean对象
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:41
     */
    public static String format(Object bean) {
        if (gson == null) {
            gson = create();
        }
        return gson.toJson(bean);
    }

    /**
     * 将Bean对象转换为json
     *
     * @param bean 实现Serializable接口的Bean对象
     * @param type Bean对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月2日 下午6:17:39
     */
    public static String format(Object bean, Type type) {
        if (gson == null) {
            gson = create();
        }
        return gson.toJson(bean, type);
    }

    /**
     * 将Bean对象集合转换为json
     *
     * @param beanList Bean对象集合
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:45
     */
    public static String format(List<?> beanList) {
        if (gson == null) {
            gson = create();
        }
        return gson.toJson(beanList);
    }

    /**
     * 将Bean对象集合转换为json
     *
     * @param beanList Bean对象集合
     * @param type     Bean对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月2日 下午6:21:55
     */
    public static String format(List<?> beanList, Type type) {
        if (gson == null) {
            gson = create();
        }
        return gson.toJson(beanList, type);
    }

    /**
     * 将json转换为对象
     *
     * @param json 需要转换的json数据
     * @param type 需要转换的数据类型
     * @return 转换成功的Bean对象
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:50
     */
    public static <T> T parse(String json, Type type) {
        if (gson == null) {
            gson = create();
        }

        T t = null;
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(json));
            jsonReader.setLenient(true);
            t = gson.fromJson(jsonReader, type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return t;
    }

    /**
     * 将json转换为对象
     *
     * @param json 需要转换的json数据
     * @param type 需要转换的数据类型
     * @return 转换成功的Bean对象
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:50
     */
    public static <T> T parse(JsonElement json, Type type) {
        if (gson == null) {
            gson = create();
        }

        T t = null;
        try {
            t = gson.fromJson(json, type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return t;
    }

    /**
     * 将json字符串转为JsonObject对象
     *
     * @param json json字符串
     * @return JsonObject对象
     * @throws JsonSyntaxException
     */
    public static JsonObject fromObject(String json) throws JsonSyntaxException {
        JsonElement jsonElement = JsonParser.parseString(json);
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        } else {
            throw new IllegalArgumentException("传入字符串数据不是JsonObject格式");
        }
    }

    /**
     * 将json字符串转为JsonArray对象
     *
     * @param json json字符串
     * @return JsonArray对象
     * @throws JsonSyntaxException
     */
    public static JsonArray fromArray(String json) throws JsonSyntaxException {
        JsonElement jsonElement = JsonParser.parseString(json);
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        } else {
            throw new IllegalArgumentException("传入字符串数据不是JsonArray格式");
        }
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        String json = gson.toJson(new Date());
        System.out.println(json);
        System.out.println(format(new Date()));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", "12");
        map.put("rows", null);
        gson = new GsonBuilder().serializeNulls().create();
        String json2 = gson.toJson(map, Map.class);
        System.out.println(json2);

        String jsonObject = "1234";
        JsonElement parse = JsonParser.parseString(jsonObject);
        if (parse.isJsonObject()) {
            System.out.println(parse.getAsJsonObject());
        }
    }
}
