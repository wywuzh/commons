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
package io.github.wywuzh.commons.core.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.*;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.cfg.EnumFeature;

/**
 * 类JsonMapper3的实现描述：Jackson 3.x 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-07-26 11:52:47
 * @version v4.0.0
 * @since JDK 17
 */
public class JacksonUtils3 {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils3.class);

    private static boolean isNullOrEmptyJson(String jsonString) {
        return StringUtils.isBlank(jsonString) || "null".equalsIgnoreCase(jsonString.trim());
    }

    private static boolean isEmptyArrayJson(String jsonString) {
        return jsonString != null && "[]".equals(jsonString.trim());
    }

    public static final JacksonUtils3 DEFAULT_JSON_MAPPER = JacksonUtils3.buildNormalMapper();
    public static final JacksonUtils3 JSON_MAPPER_NON_NULL = JacksonUtils3.buildNonNullMapper();
    public static final JacksonUtils3 JSON_MAPPER_NON_DEFAULT = JacksonUtils3.buildNonDefaultMapper();
    public static final JacksonUtils3 JSON_MAPPER_NON_EMPTY = JacksonUtils3.buildNonEmptyMapper();

    private tools.jackson.databind.json.JsonMapper objectMapper;

    public JacksonUtils3() {
        this(JsonInclude.Include.ALWAYS);
    }

    public JacksonUtils3(JsonInclude.Include include) {
        // Jackson 3.x: 使用不可变的 Builder 模式构建 ObjectMapper
        // 注：FAIL_ON_EMPTY_BEANS 默认值在 3.x 中变为 false（2.x 为 true），此处显式启用保持原有行为
        // 注：FAIL_ON_UNKNOWN_PROPERTIES 默认值在 3.x 中变为 false（2.x 为 true），此处显式启用保持原有行为
        // 注：JavaTimeModule 在 3.x 中已内置到 jackson-databind，无需手动注册
        // 注：WRITE_DATES_AS_TIMESTAMPS 在 3.x 中默认关闭（输出 ISO-8601 字符串），此处显式启用以兼容遗留系统
        objectMapper = tools.jackson.databind.json.JsonMapper.builder()
                // 控制哪些字段会被序列化成 JSON
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(include))
                // 日期输出为时间戳（兼容旧版行为），而非 ISO-8601 字符串
                .enable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 日期不输出时区 ID，避免出现：2025-01-01T12:00:00[Asia/Shanghai]
                .disable(DateTimeFeature.WRITE_DATES_WITH_ZONE_ID)
                // 空对象（无任何字段）转 JSON 直接抛异常
                .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // 反序列化时遇到未知字段抛出异常
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();
    }

    /**
     * 创建输出全部属性到Json字符串的Mapper.
     */
    public static JacksonUtils3 buildNormalMapper() {
        return new JacksonUtils3(JsonInclude.Include.ALWAYS);
    }

    /**
     * 创建只输出非空属性到Json字符串的Mapper.
     */
    public static JacksonUtils3 buildNonNullMapper() {
        return new JacksonUtils3(JsonInclude.Include.NON_NULL);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper.
     */
    public static JacksonUtils3 buildNonDefaultMapper() {
        return new JacksonUtils3(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
     */
    public static JacksonUtils3 buildNonEmptyMapper() {
        return new JacksonUtils3(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JacksonException e) {
            logger.error("Failed to serialize object to JSON. objectClass={}", (object != null ? object.getClass().getName() : "null"), e);
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    /**
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJsonFormat(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JacksonException e) {
            logger.error("Failed to serialize object to formatted JSON. objectClass={}", (object != null ? object.getClass().getName() : "null"), e);
            throw new RuntimeException("JSON serialization failed", e);
        }
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
        if (isNullOrEmptyJson(jsonString)) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JacksonException e) {
            logger.error("Failed to deserialize JSON string to class. targetClass={}, json={}", (clazz != null ? clazz.getName() : "null"), jsonString, e);
            throw new RuntimeException("JSON deserialization failed", e);
        }
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
        if (isNullOrEmptyJson(jsonString)) {
            return null;
        }

        try {
            return (T) objectMapper.readValue(jsonString, javaType);
        } catch (JacksonException e) {
            logger.error("Failed to deserialize JSON string to JavaType. targetType={}, json={}", javaType, jsonString, e);
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> valueTypeRef) {
        if (isNullOrEmptyJson(jsonString)) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, valueTypeRef);
        } catch (JacksonException e) {
            logger.error("Failed to deserialize JSON string to TypeReference. targetTypeRef={}, json={}", valueTypeRef, jsonString, e);
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, Class<?> parametrized, Class<?>... parameterClasses) {
        return (T) this.fromJson(jsonString, constructParametricType(parametrized, parameterClasses));
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta) {
        if (isNullOrEmptyJson(jsonString)) {
            return null;
        }
        if (isEmptyArrayJson(jsonString)) {
            return new java.util.ArrayList<>();
        }
        return (List<T>) this.fromJson(jsonString, constructParametricType(List.class, classMeta));
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(JsonParser jsonParser, Class<?> parametrized, Class<?>... parameterClasses) {
        if (jsonParser == null) {
            logger.warn("JsonParser is null when calling fromJson(JsonParser, ...) ; return null.");
            return null;
        }
        JavaType javaType = constructParametricType(parametrized, parameterClasses);
        try {
            return (T) objectMapper.readValue(jsonParser, javaType);
        } catch (JacksonException e) {
            logger.error("Failed to deserialize from JsonParser to type. parametrized={}, parameterClasses={}", parametrized, java.util.Arrays.toString(parameterClasses), e);
            throw new RuntimeException("JSON deserialization from parser failed", e);
        }
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
        if (object == null) {
            logger.warn("Target object is null in update(), jsonString={}", jsonString);
            return null;
        }
        if (StringUtils.isBlank(jsonString) || "null".equalsIgnoreCase(jsonString.trim())) {
            logger.debug("Empty or null jsonString in update(). Return original object without changes.");
            return object;
        }
        try {
            return (T) objectMapper.readerForUpdating(object).readValue(jsonString);
        } catch (JacksonException e) {
            // Jackson 3.x: JacksonException 继承自 RuntimeException，为非受检异常
            logger.error("Failed to update object from json. targetClass={}, json={}", object.getClass().getName(), jsonString, e);
            throw new RuntimeException("JSON update failed", e);
        }
    }

    /**
     * 輸出JSONP格式數據.
     * <p>
     * 注：Jackson 3.x 已移除 JSONPObject 类，此处直接手动拼接 JSONP 回调格式
     */
    public String toJsonP(String functionName, Object object) {
        return functionName + "(" + toJson(object) + ");";
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum,
     * 為False時使用Enum的name()函數來讀寫Enum, 默認為False.
     * <p>
     * 注：Jackson 3.x 中 JsonMapper3 为不可变对象，
     * 需通过 {@code rebuild()} 重新构建来修改配置。
     */
    public void setEnumUseToString(boolean value) {
        // Jackson 3.x: configure() 已移除，改用 enable/disable；
        // WRITE_ENUMS_USING_TO_STRING / READ_ENUMS_USING_TO_STRING 已迁移至 EnumFeature
        tools.jackson.databind.json.JsonMapper.Builder builder = objectMapper.rebuild();
        if (value) {
            builder.enable(EnumFeature.WRITE_ENUMS_USING_TO_STRING);
            builder.enable(EnumFeature.READ_ENUMS_USING_TO_STRING);
        } else {
            builder.disable(EnumFeature.WRITE_ENUMS_USING_TO_STRING);
            builder.disable(EnumFeature.READ_ENUMS_USING_TO_STRING);
        }
        objectMapper = builder.build();
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public JsonNode parseNode(String json) {
        if (isNullOrEmptyJson(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, JsonNode.class);
        } catch (JacksonException e) {
            logger.error("Failed to parse JSON to JsonNode. json={}", json, e);
            throw new RuntimeException("JSON parse to JsonNode failed", e);
        }
    }

    /**
     * 输出全部属性
     *
     * @param object
     * @return
     */
    public static String toNormalJson(Object object) {
        return DEFAULT_JSON_MAPPER.toJson(object);
    }

    /**
     * 输出非空属性
     *
     * @param object
     * @return
     */
    public static String toNonNullJson(Object object) {
        return JSON_MAPPER_NON_NULL.toJson(object);
    }

    /**
     * 输出初始值被改变部分的属性
     *
     * @param object
     * @return
     */
    public static String toNonDefaultJson(Object object) {
        return JSON_MAPPER_NON_DEFAULT.toJson(object);
    }

    /**
     * 输出非Null且非Empty(如List.isEmpty)的属性
     *
     * @param object
     * @return
     */
    public static String toNonEmptyJson(Object object) {
        return JSON_MAPPER_NON_EMPTY.toJson(object);
    }

}
