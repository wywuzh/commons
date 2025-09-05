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
package io.github.wywuzh.commons.core.sql;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类Type的实现描述：数据库类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-06 15:54:49
 * @version v2.3.6
 * @since JDK 1.8
 */
public enum Type {
    MySQL("MySQL"), Oracle("Oracle"), UNKNOWN(""),
    ;

    private final String name;
    private static final Map<String, Type> NAME_MAP = new HashMap<>();

    static {
        for (Type type : values()) {
            if (type != UNKNOWN) {
                NAME_MAP.put(type.name.toLowerCase(), type);
            }
        }
    }

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Type findBy(String name) {
        if (StringUtils.isBlank(name)) {
            return UNKNOWN;
        }

        String normalizedName = name.trim().toLowerCase();
        return NAME_MAP.getOrDefault(normalizedName, UNKNOWN);
    }

    public boolean isKnown() {
        return this != UNKNOWN;
    }

}
