/*
 * Copyright 2015-2022 the original author or authors.
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
package com.github.wywuzh.commons.core.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类JsonMapperTest的实现描述：JsonMapper工具测试
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-22 21:46:45
 * @version v2.3.2
 * @since JDK 1.8
 */
public class JsonMapperTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonMapperTest.class);

    @Test
    public void toJson() {
        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
        String jsonString = jsonMapper.toJson("123");
        LOGGER.info("转换结果：{}", jsonString);
        Assert.assertNotNull(jsonString);
    }

    @Test
    public void toJsonFormat() {
        JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.ALWAYS);
        String jsonString = jsonMapper.toJsonFormat("123");
        LOGGER.info("转换结果：{}", jsonString);
        Assert.assertNotNull(jsonString);
    }

}
