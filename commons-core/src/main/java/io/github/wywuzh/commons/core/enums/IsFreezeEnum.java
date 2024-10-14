/*
 * Copyright 2015-2024 the original author or authors.
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

/**
 * 类IsFreezeEnum的实现描述：是否冻结
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-19 14:22:09
 * @version v2.3.3
 * @since JDK 1.8
 */
public enum IsFreezeEnum {
    TRUE(1, "是"), FALSE(0, "否");

    private Integer value;
    private String desc;

    IsFreezeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static IsFreezeEnum findByValue(Integer value) {
        if (FALSE.getValue().equals(value)) {
            return FALSE;
        } else if (TRUE.getValue().equals(value)) {
            return TRUE;
        }
        return null;
    }

    public static IsFreezeEnum findByDesc(String desc) {
        if (FALSE.getDesc().equals(desc)) {
            return FALSE;
        } else if (TRUE.getDesc().equals(desc)) {
            return TRUE;
        }
        return null;
    }
}
