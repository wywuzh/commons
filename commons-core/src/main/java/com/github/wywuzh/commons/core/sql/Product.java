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
package com.github.wywuzh.commons.core.sql;

/**
 * 类Product的实现描述：数据库产品信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-06 15:54:30
 * @version v2.3.6
 * @since JDK 1.8
 */
public class Product {
    /**
     * 数据库类型
     */
    private Type type;
    /**
     * 数据库版本
     */
    private String version;

    public Product(String name, String version) {
        super();
        this.type = Type.findBy(name);
        this.version = version;
    }

    public Type getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

}
