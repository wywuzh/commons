/*
 * Copyright 2015-2025 the original author or authors.
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 类GsonUtilsTest的实现描述：Google JSON转换工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-09-01 08:44:01
 * @version v3.5.0
 * @since JDK 17
 */
@Slf4j
public class GsonUtilsTest {

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
        JsonElement parse = JsonParser.parseString(jsonObject);
        if (parse.isJsonObject()) {
            System.out.println(parse.getAsJsonObject());
        }
    }

}
