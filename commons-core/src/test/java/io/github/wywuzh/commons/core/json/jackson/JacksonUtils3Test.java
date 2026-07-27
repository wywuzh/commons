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

import java.math.BigDecimal;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import io.github.wywuzh.commons.core.poi.entity.User;

/**
 * 类JacksonUtils3Test的实现描述：Jackson 3.x 工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-07-26 11:59:57
 * @version v4.0.0
 * @since JDK 17
 */
@Slf4j
public class JacksonUtils3Test {

    @Test
    public void toJson() {
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("伍章红");
        user.setEmail("wywuzh@163.com");
        user.setMobile("14700000000");
        user.setSex("男");
        user.setBirthdate(new Date());
        user.setBalance(new BigDecimal("100000000000.3698"));

        String json = JacksonUtils3.DEFAULT_JSON_MAPPER.toJson(user);
        log.info("json: {}", json);
    }

    @Test
    public void fromJson() {
        String json = "{\"balance\":100000000000.3698,\"birthdate\":1785040233568,\"email\":\"wywuzh@163.com\",\"mobile\":\"14700000000\",\"nick\":\"伍章红\",\"sex\":\"男\",\"username\":\"wywuzh\"}";
        json = "{\"balance\":100000000000.3698,\"birthdate\":\"2026-07-26T04:01:58.238Z\",\"email\":\"wywuzh@163.com\",\"mobile\":\"14700000000\",\"nick\":\"伍章红\",\"sex\":\"男\",\"username\":\"wywuzh\"}";
        User user = JacksonUtils3.DEFAULT_JSON_MAPPER.fromJson(json, User.class);
        log.info("user: {}", user);
    }

}
