/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.core.enums;

/**
 * 类IsFreezeEnum的实现描述：是否冻结
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-19 14:22:09
 * @version v2.3.3
 * @since JDK 1.8
 */
public enum IsFreezeEnum {
    TRUE(1),
    FALSE(0);

    private Integer value;

    IsFreezeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static IsFreezeEnum findByValue(Integer value) {
        if (value == 0) {
            return IsFreezeEnum.FALSE;
        } else if (value == 1) {
            return IsFreezeEnum.TRUE;
        }
        return null;
    }
}
