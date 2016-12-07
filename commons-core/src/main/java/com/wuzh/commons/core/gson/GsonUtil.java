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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 类GsonUtil.java的实现描述：Google JSON转换工具类型
 * 
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午9:29:46
 * @version v1.0.0
 * @since JDK 1.7
 */
public class GsonUtil {
    private static final Log logger = LogFactory.getLog(GsonUtil.class);
    public static Gson gson = null;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // 注册java.util.Date 日期时间格式转换
        gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateTimeSerializer());
        // 注册java.sql.Date 日期格式转换
        gsonBuilder.registerTypeAdapter(java.sql.Date.class, new DateSerializer());
        // 解决value为null时key不存在的问题
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();
    }

    /**
     * 将Bean对象转换为json
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:41
     * @param bean
     *            实现Serializable接口的Bean对象
     * @return
     */
    public static String format(Object bean) {
        return gson.toJson(bean);
    }

    /**
     * 将Bean对象转换为json
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月2日 下午6:17:39
     * @param bean
     *            实现Serializable接口的Bean对象
     * @param type
     *            Bean对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return
     */
    public static String format(Object bean, Type type) {
        return gson.toJson(bean, type);
    }

    /**
     * 将Bean对象集合转换为json
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:45
     * @param beanList
     *            Bean对象集合
     * @return
     */
    public static String format(List<?> beanList) {
        return gson.toJson(beanList);
    }

    /**
     * 将Bean对象集合转换为json
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月2日 下午6:21:55
     * @param beanList
     *            Bean对象集合
     * @param type
     *            Bean对象对应的type。例子：new TypeToken&lt;T&gt;(){}.getType()
     * @return
     */
    public static String format(List<?> beanList, Type type) {
        return gson.toJson(beanList, type);
    }

    /**
     * 将json转换为对象
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月12日 上午10:05:50
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parse(String json, Class<T> clazz) {
        T t = null;
        try {
            t = gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return t;
    }

    public static JsonObject fromObject(String json) throws JsonSyntaxException {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json).getAsJsonObject();
    }

    public static JsonArray fromArray(String json) throws JsonSyntaxException {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json).getAsJsonArray();
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        String json = gson.toJson(new Date());
        System.out.println(json);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", "12");
        map.put("rows", null);
        gson = new GsonBuilder().serializeNulls().create();
        String json2 = gson.toJson(map, Map.class);
        System.out.println(json2);

        String jsonObject = "1234";
        JsonElement parse = new JsonParser().parse(jsonObject);
        if (parse.isJsonObject()) {
            System.out.println(parse.getAsJsonObject());
        }
    }
}
