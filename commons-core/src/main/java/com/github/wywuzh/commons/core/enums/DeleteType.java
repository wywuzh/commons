/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.enums;

/**
 * 类DeleteType.java的实现描述：数据删除类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:25:45
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum DeleteType {

    /**
     * 逻辑删除
     */
    LOGIC("LOGIC", "逻辑删除"),
    /**
     * 物理删除
     */
    PHYSICS("PHYSICS", "物理删除");

    private String type;
    private String desc;

    DeleteType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static DeleteType findByType(String type) {
        if (LOGIC.getType().equals(type)) {
            return LOGIC;
        } else if (PHYSICS.getType().equals(type)) {
            return PHYSICS;
        }
        return null;
    }

    public static DeleteType findByDesc(String desc) {
        if (LOGIC.getDesc().equals(desc)) {
            return LOGIC;
        } else if (PHYSICS.getDesc().equals(desc)) {
            return PHYSICS;
        }
        return null;
    }
}
