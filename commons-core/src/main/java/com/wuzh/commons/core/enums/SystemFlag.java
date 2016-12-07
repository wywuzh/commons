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
package com.wuzh.commons.core.enums;

/**
 * 类SystemFlag.java的实现描述：系统内置
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:26:12
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum SystemFlag {

    /**
     * 
     */
    TRUE("1"),
    /**
     * 
     */
    FALSE("0");

    private String value;

    private SystemFlag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SystemFlag findByValue(String value) {
        if ("0".equals(value)) {
            return FALSE;
        } else if ("1".equals(value)) {
            return TRUE;
        }
        return null;
    }
}
