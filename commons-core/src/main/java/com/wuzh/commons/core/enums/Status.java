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
package com.wuzh.commons.core.enums;

/**
 * 类Status.java的实现描述：数据状态
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:26:48
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum Status {

    /**
     * 初始（不可用）
     */
    INITIALIZE("0"),
    /**
     * 正常
     */
    EFFECTIVE("1"),
    /**
     * 删除
     */
    DELETE("2");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status findByValue(String value) {
        if ("0".equals(value)) {
            return INITIALIZE;
        } else if ("1".equals(value)) {
            return EFFECTIVE;
        } else if ("2".equals(value)) {
            return DELETE;
        }
        return null;
    }
}
