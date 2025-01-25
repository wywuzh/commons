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
package io.github.wywuzh.commons.core.web.response;

/**
 * 类StatusCode的实现描述：返回结果状态
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/13 20:53
 * @version v2.0.2
 * @since JDK 1.8
 */
public enum StatusCode {

    /**
     * 请求成功
     */
    OK(0, "请求成功"),
    /**
     * 请求失败，例如：请求参数有误
     */
    BAD_REQUEST(301, "请求参数有误"),
    /**
     * 找不到记录
     */
    NOT_FOUND(302, "找不到记录"),
    /**
     * 请求失败
     */
    ERROR(400, "请求失败"),
    /**
     * 用户授权Session超时
     */
    SESSION_TIMEOUT(401, "会话过期");

    private int value;
    private String reasonPhrase;

    private StatusCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public static StatusCode findBy(int statusCode) {
        for (StatusCode item : values()) {
            if (item.value == statusCode) {
                return item;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }
}
