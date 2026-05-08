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
package io.github.wywuzh.commons.core.util;

import java.util.Date;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import io.github.wywuzh.commons.core.json.jackson.JsonMapper;

/**
 * 类ConvertUtilTest.java的实现描述：类型转换工具类测试
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-02-28 10:25:08
 * @version v3.5.0
 * @since JDK 17
 */
@Slf4j
public class ConvertUtilTest {

    /**
     * 测试将JavaBean转换为Map
     */
    @Test
    public void testConvertBean() {
        TestBean bean = new TestBean();
        bean.setId(100L);
        bean.setAge(25);
        bean.setScore((short) 90);
        bean.setLevel((byte) 5);
        bean.setGrade('A');
        bean.setPrice(99.99);
        bean.setDiscount(0.85f);
        bean.setName("测试用户");
        bean.setActive(true);
        bean.setCreateDate(new Date());
        bean.setSqlDate(new java.sql.Date(System.currentTimeMillis()));
        bean.setTime(new java.sql.Time(System.currentTimeMillis()));

        Map<String, Object> map = ConvertUtil.convertBean(bean);
        log.info("Bean转Map结果：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(map));

        // 验证转换结果
        assert map.containsKey("id");
        assert map.get("id").equals(100L);
        assert map.containsKey("age");
        assert map.get("age").equals(25);
        assert map.containsKey("name");
        assert map.get("name").equals("测试用户");
        assert !map.containsKey("class"); // 不应包含class属性
    }

    /**
     * 测试将Map转换为JavaBean
     */
    @Test
    public void testConvertMap() {
        Map<String, Object> map = ConvertUtil.convertBean(createTestBean());
        log.info("\n原始Map：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(map));

        TestBean bean = (TestBean) ConvertUtil.convertMap(map, TestBean.class);
        log.info("\nMap转Bean结果：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(bean));

        // 验证转换结果
        assert bean.getId().equals(100L);
        assert bean.getAge().equals(25);
        assert bean.getName().equals("测试用户");
        assert bean.getActive().equals(true);
        assert bean.getCreateDate() != null;
    }

    /**
     * 测试属性为null的情况
     */
    @Test
    public void testConvertBeanWithNullValues() {
        TestBean bean = new TestBean();
        bean.setId(1L);
        bean.setName("测试");
        // 其他属性为null

        Map<String, Object> map = ConvertUtil.convertBean(bean);
        log.info("\n含null值的Bean转Map结果：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(map));

        // null值应转换为空字符串
        assert map.get("age").equals("");
        assert map.get("price").equals("");
        assert map.get("name").equals("测试");
    }

    /**
     * 测试数值类型转换
     */
    @Test
    public void testNumericTypeConversion() {
        TestBean bean = createTestBean();
        Map<String, Object> map = ConvertUtil.convertBean(bean);

        // 测试各种数值类型的转换
        TestBean convertedBean = (TestBean) ConvertUtil.convertMap(map, TestBean.class);

        assert convertedBean.getId().equals(bean.getId());
        assert convertedBean.getAge().equals(bean.getAge());
        assert convertedBean.getScore().equals(bean.getScore());
        assert convertedBean.getLevel().equals(bean.getLevel());
        assert convertedBean.getGrade().equals(bean.getGrade());
        assert convertedBean.getPrice().equals(bean.getPrice());
        assert convertedBean.getDiscount().equals(bean.getDiscount());

        log.info("\n数值类型转换测试通过");
        log.info("id: " + convertedBean.getId() + " (" + convertedBean.getId().getClass().getSimpleName() + ")");
        log.info("age: " + convertedBean.getAge() + " (" + convertedBean.getAge().getClass().getSimpleName() + ")");
        log.info("score: " + convertedBean.getScore() + " (" + convertedBean.getScore().getClass().getSimpleName() + ")");
        log.info("level: " + convertedBean.getLevel() + " (" + convertedBean.getLevel().getClass().getSimpleName() + ")");
        log.info("grade: " + convertedBean.getGrade() + " (" + convertedBean.getGrade().getClass().getSimpleName() + ")");
        log.info("price: " + convertedBean.getPrice() + " (" + convertedBean.getPrice().getClass().getSimpleName() + ")");
        log.info("discount: " + convertedBean.getDiscount() + " (" + convertedBean.getDiscount().getClass().getSimpleName() + ")");
    }

    /**
     * 测试日期时间类型转换
     */
    @Test
    public void testDateTimeConversion() {
        TestBean bean = createTestBean();
        Map<String, Object> map = ConvertUtil.convertBean(bean);

        log.info("\n日期类型转换测试：");
        log.info("createDate: " + map.get("createDate"));
        log.info("sqlDate: " + map.get("sqlDate"));
        log.info("time: " + map.get("time"));

        // 验证日期格式化
        assert map.get("createDate") instanceof String;
        assert map.get("sqlDate") instanceof String;
        assert map.get("time") instanceof String;

        // 将Map转换回Bean
        TestBean convertedBean = (TestBean) ConvertUtil.convertMap(map, TestBean.class);
        assert convertedBean.getCreateDate() != null;
        assert convertedBean.getSqlDate() != null;
        assert convertedBean.getTime() != null;

        log.info("日期时间转换测试通过");
    }

    /**
     * 测试字符类型转换
     */
    @Test
    public void testCharacterConversion() {
        TestBean bean = new TestBean();
        bean.setGrade('Z');

        Map<String, Object> map = ConvertUtil.convertBean(bean);
        assert map.get("grade").equals('Z');

        TestBean convertedBean = (TestBean) ConvertUtil.convertMap(map, TestBean.class);
        assert convertedBean.getGrade().equals('Z');

        log.info("\n字符类型转换测试通过：grade = " + convertedBean.getGrade());
    }

    /**
     * 测试双向转换
     */
    @Test
    public void testBidirectionalConversion() {
        TestBean originalBean = createTestBean();
        log.info("\n原始Bean：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(originalBean));

        // Bean -> Map
        Map<String, Object> map = ConvertUtil.convertBean(originalBean);
        log.info("\n转Map：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(map));

        // Map -> Bean
        TestBean convertedBean = (TestBean) ConvertUtil.convertMap(map, TestBean.class);
        log.info("\n转回Bean：");
        log.info(JsonMapper.DEFAULT_JSON_MAPPER.toJson(convertedBean));

        // 验证一致性
        assert originalBean.getId().equals(convertedBean.getId());
        assert originalBean.getName().equals(convertedBean.getName());
        assert originalBean.getAge().equals(convertedBean.getAge());
        assert originalBean.getActive().equals(convertedBean.getActive());

        log.info("\n双向转换测试通过");
    }

    /**
     * 创建测试用的TestBean对象
     */
    private TestBean createTestBean() {
        TestBean bean = new TestBean();
        bean.setId(100L);
        bean.setAge(25);
        bean.setScore((short) 90);
        bean.setLevel((byte) 5);
        bean.setGrade('A');
        bean.setPrice(99.99);
        bean.setDiscount(0.85f);
        bean.setName("测试用户");
        bean.setActive(true);
        bean.setCreateDate(new Date());
        bean.setSqlDate(new java.sql.Date(System.currentTimeMillis()));
        bean.setTime(new java.sql.Time(System.currentTimeMillis()));
        return bean;
    }
}
