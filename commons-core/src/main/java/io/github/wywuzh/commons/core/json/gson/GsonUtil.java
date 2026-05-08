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
package io.github.wywuzh.commons.core.json.gson;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.wywuzh.commons.core.json.gson.serializer.DateSerializer;
import io.github.wywuzh.commons.core.json.gson.serializer.DateTimeSerializer;
import io.github.wywuzh.commons.core.json.gson.serializer.TypeSerializer;

/**
 * 类GsonUtil.java的实现描述：Google JSON转换工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午9:29:46
 * @version v1.0.0
 * @since JDK 1.7
 */
public class GsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(GsonUtil.class);

    private static volatile GsonBuilder gsonBuilder = createDefaultBuilder();
    private static volatile Gson gson = null;

    private GsonUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static GsonBuilder createDefaultBuilder() {
        GsonBuilder builder = new GsonBuilder();
        // 注册 java.util.Date 日期时间格式转换
        builder.registerTypeAdapter(Date.class, new DateTimeSerializer());
        // 注册 java.sql.Date 日期格式转换
        builder.registerTypeAdapter(java.sql.Date.class, new DateSerializer());
        // 解决value为null时key不存在的问题
        builder.serializeNulls();
        return builder;
    }

    /**
     * 注册类型转换器
     *
     * @param clazz          数据类型
     * @param typeSerializer 类型转换器
     */
    public static <T> void register(Class<TypeSerializer<T>> clazz, TypeSerializer<T> typeSerializer) {
        synchronized (GsonUtil.class) {
            gsonBuilder.registerTypeAdapter(clazz, typeSerializer);
            gson = null; // 使现有实例失效
        }
    }

    /**
     * 注册类型转换器
     *
     * @param type           数据类型
     * @param typeSerializer 类型转换器
     */
    public static <T> void register(Type type, TypeSerializer<T> typeSerializer) {
        synchronized (GsonUtil.class) {
            gsonBuilder.registerTypeAdapter(type, typeSerializer);
            gson = null; // 使现有实例失效
        }
    }

    /**
     * 注册类型转换器
     *
     * @param list 类型转换器集合
     */
    public static <T> void registerAll(List<? extends TypeSerializer<T>> list) {
        synchronized (GsonUtil.class) {
            for (TypeSerializer<T> serializer : list) {
                gsonBuilder.registerTypeAdapter(serializer.getType(), serializer);
            }
            gson = null; // 使现有实例失效
        }
    }

    /**
     * 获取GsonBuilder实例（线程安全副本）
     */
    public static GsonBuilder getGsonBuilder() {
        synchronized (GsonUtil.class) {
            return gsonBuilder;
        }
    }

    /**
     * 创建或获取Gson实例
     */
    public static Gson create() {
        if (gson == null) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    gson = gsonBuilder.create();
                }
            }
        }
        return gson;
    }

    /**
     * 重置为默认配置
     */
    public static void reset() {
        synchronized (GsonUtil.class) {
            gsonBuilder = createDefaultBuilder();
            gson = null;
        }
    }

    /**
     * 将Bean对象转换为JSON字符串
     *
     * @param bean 实现Serializable接口的Bean对象
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:41
     */
    public static String format(Object bean) {
        return create().toJson(bean);
    }

    /**
     * 将Bean对象转换为JSON字符串
     *
     * @param bean 实现Serializable接口的Bean对象
     * @param type Bean对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月2日 下午6:17:39
     */
    public static String format(Object bean, Type type) {
        return create().toJson(bean, type);
    }

    /**
     * 将Bean对象集合转换为json
     *
     * @param collection Bean对象集合
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:45
     */
    public static String format(Collection<?> collection) {
        return create().toJson(collection);
    }

    /**
     * 将Bean对象集合转换为json
     *
     * @param collection Bean对象集合
     * @param type       Bean对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return JSON格式字符串
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月2日 下午6:21:55
     */
    public static String format(Collection<?> collection, Type type) {
        return create().toJson(collection, type);
    }

    /**
     * 将Map对象转换为json
     *
     * @param map Map对象
     * @return JSON格式字符串
     */
    public static String format(Map<?, ?> map) {
        return create().toJson(map);
    }

    /**
     * 将Map对象转换为json
     *
     * @param map  Map对象
     * @param type Map对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return JSON格式字符串
     */
    public static String format(Map<?, ?> map, Type type) {
        return create().toJson(map, type);
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
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
        try (JsonReader jsonReader = new JsonReader(new StringReader(json))) {
            jsonReader.setLenient(true);
            return create().fromJson(jsonReader, type);
        } catch (JsonSyntaxException e) {
            logger.error("解析JSON到类型失败: {}, json: {}", type, json, e);
            throw e;
        } catch (Exception e) {
            logger.error("解析JSON时发生意外错误: {}", json, e);
            throw new RuntimeException("解析JSON失败", e);
        }
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
        if (json == null || json.isJsonNull()) {
            throw new IllegalArgumentException("JSON元素不能为空");
        }
        try {
            return create().fromJson(json, type);
        } catch (JsonSyntaxException e) {
            logger.error("解析JSON到类型失败: {}", type, e);
            throw e;
        } catch (Exception e) {
            logger.error("解析JSON时发生意外错误", e);
            throw new RuntimeException("解析JSON失败", e);
        }
    }

    /**
     * 安静地将json转换为对象，失败时返回null
     *
     * @param json 需要转换的json数据
     * @param type 需要转换的数据类型
     * @return 转换成功的Bean对象，失败返回null
     */
    public static <T> T parseQuietly(String json, Type type) {
        try {
            return parse(json, type);
        } catch (Exception e) {
            logger.warn("安静解析JSON失败，返回null: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 安静地将json转换为对象，失败时返回null
     *
     * @param json 需要转换的json数据
     * @param type 需要转换的数据类型
     * @return 转换成功的Bean对象，失败返回null
     */
    public static <T> T parseQuietly(JsonElement json, Type type) {
        try {
            return parse(json, type);
        } catch (Exception e) {
            logger.warn("安静解析JSON失败，返回null: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 将json字符串转为JsonObject对象
     *
     * @param json json字符串
     * @return JsonObject对象
     * @throws JsonSyntaxException
     */
    public static JsonObject fromObject(String json) throws JsonSyntaxException {
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
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
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON字符串不能为空");
        }
        JsonElement jsonElement = JsonParser.parseString(json);
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        } else {
            throw new IllegalArgumentException("传入字符串数据不是JsonArray格式");
        }
    }

    /**
     * 验证是否为有效的JSON字符串
     *
     * @param json json字符串
     * @return 是否为有效JSON
     */
    public static boolean isValidJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }
        try {
            JsonParser.parseString(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证是否为有效的JsonObject格式
     *
     * @param json json字符串
     * @return 是否为有效JsonObject
     */
    public static boolean isValidJsonObject(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.isJsonObject();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证是否为有效的JsonArray格式
     *
     * @param json json字符串
     * @return 是否为有效JsonArray
     */
    public static boolean isValidJsonArray(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.isJsonArray();
        } catch (Exception e) {
            return false;
        }
    }

}
