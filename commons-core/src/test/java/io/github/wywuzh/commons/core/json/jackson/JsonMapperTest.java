/*
 * Copyright 2015-2026 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
///*
// * Copyright 2015-2026 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package io.github.wywuzh.commons.core.json.jackson;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 类JsonMapperTest的实现描述：JsonMapper工具测试
// *
// * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-22 21:46:45
// * @version v2.3.2
// * @since JDK 1.8
// */
//public class JsonMapperTest {
//    public static final Logger LOGGER = LoggerFactory.getLogger(JsonMapperTest.class);
//
//    @Test
//    public void constructor_defaultConfig_flagsAligned() throws JsonProcessingException {
//        JsonMapper jsonMapper = new JsonMapper();
//        ObjectMapper objectMapper = jsonMapper.getObjectMapper();
//        Assert.assertFalse(objectMapper.getSerializationConfig().isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
//        Assert.assertFalse(objectMapper.getDeserializationConfig().isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
//        JsonMapper.LocalDateTimeBean bean = new JsonMapper.LocalDateTimeBean();
//        String json = jsonMapper.toJson(bean);
//        Assert.assertNotNull(json);
//    }
//
//    @Test
//    public void constructor_nonNullConfig() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.NON_NULL);
//        JsonMapper.Person person = new JsonMapper.Person();
//        person.setName("test");
//        person.setAge(null);
//        String jsonString = jsonMapper.toJson(person);
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertFalse(jsonString.contains("age"));
//    }
//
//    @Test
//    public void toJson() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        String jsonString = jsonMapper.toJson("123");
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertNotNull(jsonString);
//    }
//
//    @Test
//    public void toJson_null_returnsLiteralNullString() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        String jsonString = jsonMapper.toJson(null);
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertEquals("null", jsonString);
//    }
//
//    @Test
//    public void toJson_emptyList_returnsEmptyArray() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        String jsonString = jsonMapper.toJson(Collections.emptyList());
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertEquals("[]", jsonString);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void fromJson_invalidJson_throwRuntimeException() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        jsonMapper.fromJson("{invalid", JsonMapper.Person.class);
//    }
//
//    @Test
//    public void fromJson_nullString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.fromJson(null, String.class));
//    }
//
//    @Test
//    public void fromJson_emptyString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.fromJson("", String.class));
//    }
//
//    @Test
//    public void fromJson_literalNullString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.fromJson("null", String.class));
//    }
//
//    @Test
//    public void fromJson_validString() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertEquals("abc", jsonMapper.fromJson("\"abc\"", String.class));
//    }
//
//    @Test
//    public void fromJsonJavaType_listOfString() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        JavaType javaType = jsonMapper.constructParametricType(List.class, String.class);
//        List<String> list = jsonMapper.fromJson("[\"a\",\"b\"]", javaType);
//        Assert.assertEquals(2, list.size());
//        Assert.assertEquals(Arrays.asList("a", "b"), list);
//    }
//
//    @Test
//    public void fromJsonToList_emptyArrayString_returnEmptyList() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        List<String> list = jsonMapper.fromJsonToList("[]", String.class);
//        Assert.assertNotNull(list);
//        Assert.assertTrue(list.isEmpty());
//    }
//
//    @Test
//    public void fromJsonToList_nullString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.fromJsonToList(null, String.class));
//    }
//
//    @Test
//    public void fromJsonToList_emptyString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.fromJsonToList("", String.class));
//    }
//
//    @Test
//    public void fromJsonToList_literalNullString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.fromJsonToList("null", String.class));
//    }
//
//    @Test
//    public void parseNode_validJson_returnsJsonNode() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        JsonNode node = jsonMapper.parseNode("{\"a\":1}");
//        Assert.assertNotNull(node);
//        Assert.assertEquals(1, node.get("a").asInt());
//    }
//
//    @Test
//    public void parseNode_nullString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.parseNode(null));
//    }
//
//    @Test
//    public void parseNode_emptyString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.parseNode(""));
//    }
//
//    @Test
//    public void parseNode_literalNullString_returnNull() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        Assert.assertNull(jsonMapper.parseNode("null"));
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void parseNode_invalidJson_throwRuntimeException() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        jsonMapper.parseNode("{invalid");
//    }
//
//    @Test
//    public void toJsonFormat() {
//        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
//        String jsonString = jsonMapper.toJsonFormat("123");
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertNotNull(jsonString);
//        Assert.assertTrue(jsonString.contains("\n"));
//    }
//
//    @Test
//    public void toNormalJson_basic() {
//        String jsonString = JsonMapper.toNormalJson("123");
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertNotNull(jsonString);
//        Assert.assertTrue(jsonString.contains("123"));
//    }
//
//    @Test
//    public void toNonNullJson_ignoreNullField() {
//        JsonMapper.Person person = new JsonMapper.Person();
//        person.setName("test");
//        person.setAge(null);
//        String jsonString = JsonMapper.toNonNullJson(person);
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertFalse(jsonString.contains("age"));
//    }
//
//    @Test
//    public void toNonEmptyJson_ignoreEmptyCollection() {
//        JsonMapper.PersonWithList person = new JsonMapper.PersonWithList();
//        person.setName("test");
//        person.setTags(Collections.<String> emptyList());
//        String jsonString = JsonMapper.toNonEmptyJson(person);
//        LOGGER.info("转换结果：{}", jsonString);
//        Assert.assertFalse(jsonString.contains("tags"));
//    }
//
//}
