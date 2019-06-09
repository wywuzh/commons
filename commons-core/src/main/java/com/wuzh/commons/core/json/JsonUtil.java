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
/*
package com.wuzh.commons.core.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.*;
*/

/**
 * 类JsonUtil.java的实现描述：JSON工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:45:50
 * @version v1.0.0
 * @since JDK 1.7
 */
/*
public class JsonUtil {

    */
/**
     * 把javaBean转换为JSONObject
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:46:22
     * @param bean
     * @return
     *//*

    public static JSONObject convertJsonObject(Object bean) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(bean, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    */
/**
     * 把对象转换为JSONArray
     * 
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:46:37
     * @param obj
     * @return
     *//*

    public static JSONArray convertJsonArray(Object obj) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jsonArray = null;
        try {
            jsonArray = JSONArray.fromObject(obj, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> s1 = new HashMap<String, Object>();
        s1.put("name", "jim");
        s1.put("age", "15");
        s1.put("time", new Date());
        list.add(s1);
        Map<String, Object> s2 = new HashMap<String, Object>();
        s2.put("name", "lucy");
        s2.put("age", "12");
        s2.put("time", new Date());
        list.add(s2);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray jo = JSONArray.fromObject(list, jsonConfig);
        System.out.println("json:" + jo.toString());
    }
}
*/
