/*
 * Copyright 2015-2017 the original author or authors.
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
 * 类Flag.java的实现描述：TRUE or FALSE
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:28:11
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum Flag {

    TRUE("1", true), FALSE("0", false);

    private String value;
    private boolean flag;

    private Flag(String value, boolean flag) {
        this.value = value;
        this.flag = flag;
    }

    public String getValue() {
        return value;
    }

    public boolean isFlag() {
        return flag;
    }

    public static Flag findByValue(String value) {
        if ("0".equals(value)) {
            return FALSE;
        } else if ("1".equals(value)) {
            return TRUE;
        }
        return null;
    }

    public static Flag getFlag(boolean flag) {
        if (flag) {
            return TRUE;
        } else {
            return FALSE;
        }
    }
}
