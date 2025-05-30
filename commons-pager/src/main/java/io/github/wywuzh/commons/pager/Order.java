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
package io.github.wywuzh.commons.pager;

/**
 * 类Order.java的实现描述：排序类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:27:25
 * @version v2.0.2
 * @since JDK 1.8
 */
public enum Order {

    /**
     * 升序
     */
    ASC("ASC"),
    /**
     * 降序
     */
    DESC("DESC");

    private String value;

    private Order(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Order findByValue(String value) {
        if ("ASC".equals(value)) {
            return ASC;
        } else if ("DESC".equals(value)) {
            return DESC;
        }
        return null;
    }
}
