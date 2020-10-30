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
package com.wuzh.commons.core.json.gson;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * 类TypeSerializer.java的实现描述：类型转换器接口。序列化，反序列化处理
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:39:57
 * @version v1.0.0
 * @since JDK 1.7
 */
public interface TypeSerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    /**
     * 返回泛型T的Type
     *
     * @return
     */
    Type getType();

}
