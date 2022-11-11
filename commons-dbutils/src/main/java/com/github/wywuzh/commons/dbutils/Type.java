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
package com.github.wywuzh.commons.dbutils;

/**
 * 类Type.java的实现描述：数据库类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年1月8日 下午11:06:06
 * @version v1.0.0
 * @since JDK 1.7
 * @deprecated 废弃，请使用 {@link com.github.wywuzh.commons.core.sql.Type} 类
 */
@Deprecated
public enum Type {
    MySQL("MySQL"), Oracle("Oracle"), UNKNOW("");

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
