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
package io.github.wywuzh.commons.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 类IsEnableEnum的实现描述：是否启用
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-19 14:22:09
 * @version v2.3.3
 * @since JDK 1.8
 */
public enum IsEnableEnum {
    TRUE(1, "是"), FALSE(0, "否");

    private Integer value;
    private String desc;

    IsEnableEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static IsEnableEnum findByValue(Integer value) {
        if (FALSE.getValue().equals(value)) {
            return FALSE;
        } else if (TRUE.getValue().equals(value)) {
            return TRUE;
        }
        return null;
    }

    public static IsEnableEnum findByDesc(String desc) {
        if (FALSE.getDesc().equals(desc)) {
            return FALSE;
        } else if (TRUE.getDesc().equals(desc)) {
            return TRUE;
        }
        return null;
    }

    /**
     * @return 是否启用代号映射：key=是否启用代号，value=是否启用描述
     */
    public static Map<Integer, String> getFlagValueMap() {
        Map<Integer, String> flagValueMap = new HashMap<>();
        for (IsEnableEnum item : values()) {
            flagValueMap.put(item.getValue(), item.getDesc());
        }
        return flagValueMap;
    }

    /**
     * @return 是否启用描述映射：key=是否启用描述，value=是否启用代号
     */
    public static Map<String, Integer> getFlagDescMap() {
        Map<String, Integer> flagDescMap = new HashMap<>();
        for (IsEnableEnum item : values()) {
            flagDescMap.put(item.getDesc(), item.getValue());
        }
        return flagDescMap;
    }

}
