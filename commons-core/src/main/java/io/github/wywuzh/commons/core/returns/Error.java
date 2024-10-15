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
package io.github.wywuzh.commons.core.returns;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类Error.java的实现描述：错误信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年8月6日 下午1:52:00
 * @version v1.0.0
 * @since JDK 1.7
 */
public class Error implements Serializable {
    private static final long serialVersionUID = 7475908929063980528L;

    /**
     * 错误编码
     */
    private String code;
    /**
     * 错误字段
     */
    private String field;
    /**
     * 错误信息
     */
    private String message;

    public Error() {
    }

    public Error(String field, String message) {
        this.code = "01";
        this.field = field;
        this.message = message;
    }

    public Error(String code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
