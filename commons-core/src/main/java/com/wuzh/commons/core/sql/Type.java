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
package com.wuzh.commons.core.sql;

/**
 * 类Type的实现描述：数据库类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-06 15:54:49
 * @version v2.3.6
 * @since JDK 1.8
 */
public enum Type {
    MySQL("MySQL"),
    Oracle("Oracle"),
    UNKNOW(""),
    ;

    private String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Type findBy(String name) {
        for (Type type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return UNKNOW;
    }
}
