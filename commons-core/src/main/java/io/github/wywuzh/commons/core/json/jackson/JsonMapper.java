/*
 * Copyright 2015-2024 the original author or authors.
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
package io.github.wywuzh.commons.core.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java对象和json相互转化 https://suipian1029.iteye.com/blog/2002536
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2018-12-28 18:07
 * @version v1.0.0
 * @since JDK 1.8
 */
public class JsonMapper {
    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    public static JsonMapper DEFAULT_JSON_MAPPER = JsonMapper.buildNormalMapper();

    private ObjectMapper objectMapper;

    public JsonMapper() {
        this(JsonInclude.Include.ALWAYS);
    }

    public JsonMapper(JsonInclude.Include include) {
        objectMapper = new ObjectMapper();
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(include);
        // 取消默认转换timestamp形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        // 设置自定义的 SimpleDateFormat，该对象支持"yyyy-MM-dd HH:mm:ss"格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略在json字符串中存在，但是在Java对象中不存在对应属性的情况
        // 有时JSON字符串中含有我们并不需要的字段，那么当对应的实体类中不含有该字段时，会抛出一个异常，告诉你有些字段（java 原始类型）没有在实体类中找到
        // 设置为false即不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    /**
     * 创建输出全部属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNormalMapper() {
        return new JsonMapper(JsonInclude.Include.ALWAYS);
    }

    /**
     * 创建只输出非空属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonNullMapper() {
        return new JsonMapper(JsonInclude.Include.NON_NULL);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonDefaultMapper() {
        return new JsonMapper(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonEmptyMapper() {
        return new JsonMapper(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJsonFormat(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
     *
     * @see #constructParametricType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
     *
     * @see #constructParametricType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public <T> T fromJson(String jsonString, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) objectMapper.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, Class<?> parametrized, Class<?>... parameterClasses) {
        return (T) this.fromJson(jsonString, constructParametricType(parametrized, parameterClasses));
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta) {
        return (List<T>) this.fromJson(jsonString, constructParametricType(List.class, classMeta));
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(JsonParser jsonParser, Class<?> parametrized, Class<?>... parameterClasses) {
        JavaType javaType = constructParametricType(parametrized, parameterClasses);
        try {
            return (T) objectMapper.readValue(jsonParser, javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 構造泛型的Type如List<MyBean>, 则调用constructParametricType(ArrayList.class,MyBean.class)
     * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
     */
    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    @SuppressWarnings("unchecked")
    public <T> T update(T object, String jsonString) {
        try {
            return (T) objectMapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum,
     * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
     * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
     */
    public void setEnumUseToString(boolean value) {
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, value);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, value);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public JsonNode parseNode(String json) {
        try {
            return objectMapper.readValue(json, JsonNode.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 输出全部属性
     *
     * @param object
     * @return
     */
    public static String toNormalJson(Object object) {
        return new JsonMapper(JsonInclude.Include.ALWAYS).toJson(object);
    }

    /**
     * 输出非空属性
     *
     * @param object
     * @return
     */
    public static String toNonNullJson(Object object) {
        return new JsonMapper(JsonInclude.Include.NON_NULL).toJson(object);
    }

    /**
     * 输出初始值被改变部分的属性
     *
     * @param object
     * @return
     */
    public static String toNonDefaultJson(Object object) {
        return new JsonMapper(JsonInclude.Include.NON_DEFAULT).toJson(object);
    }

    /**
     * 输出非Null且非Empty(如List.isEmpty)的属性
     *
     * @param object
     * @return
     */
    public static String toNonEmptyJson(Object object) {
        return new JsonMapper(JsonInclude.Include.NON_EMPTY).toJson(object);
    }

    public void setDateFormat(String dateFormat) {
        objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
    }

    public static String toLogJson(Object object) {
        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.NON_EMPTY);
        jsonMapper.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return jsonMapper.toJson(object);
    }

}
