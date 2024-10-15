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
import java.util.ArrayList;
import java.util.List;

/**
 * 类ReturnBase.java的实现描述：返回结果基类
 *
 * @author 伍章红 2015-9-12 下午12:23:53
 * @version v1.0.0
 * @since JDK 1.7.0_71
 */
public abstract class ReturnBase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果编码
     */
    private ReturnCode returnCode;
    /**
     * 返回结果消息
     */
    private String message;
    /**
     * 错误信息
     */
    private List<Error> errors;

    public ReturnBase() {
        super();
    }

    public ReturnBase(ReturnCode returnCode) {
        super();
        this.returnCode = returnCode;
    }

    public ReturnBase(ReturnCode returnCode, String message) {
        super();
        this.returnCode = returnCode;
        this.message = message;
    }

    public ReturnCode getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void addError(Error error) {
        if (errors == null) {
            errors = new ArrayList<Error>();
        }
        errors.add(error);
    }

    public void addError(String field, String message) {
        if (errors == null) {
            errors = new ArrayList<Error>();
        }
        errors.add(new Error(field, message));
    }

    public void addError(String code, String field, String message) {
        if (errors == null) {
            errors = new ArrayList<Error>();
        }
        errors.add(new Error(code, field, message));
    }

    @Override
    public abstract String toString();

}
