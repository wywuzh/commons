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
package io.github.wywuzh.commons.core.http;

/**
 * 类Protocol.java的实现描述：HTTP、HTTPS协议
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月12日 上午11:57:12
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum Scheme {

    HTTP("http"), HTTPS("https");

    /**
     * 协议名称
     */
    private String name;

    private Scheme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Scheme getByName(String name) {
        for (Scheme protocol : values()) {
            if (protocol.name.equals(name)) {
                return protocol;
            }
        }
        return null;
    }
}
