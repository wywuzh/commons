/*
 * Copyright 2015-2019 the original author or authors.
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
package com.wuzh.commons.core.web.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类ResponseEntity的实现描述：返回结果信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/13 20:48
 * @version v2.0.2
 * @since JDK 1.8
 */
public class ResponseEntity<T> implements Serializable {

    /**
     * 结果编号
     * 200=请求成功
     * 301=表示传入额参数有问题
     * 302=根据参数找不到数据
     * 400=请求失败，例如处理出现异常
     * 401=用户授权Session会话过期
     * 402=请求RPC接口超时
     */
    private Integer statusCode;
    /**
     * 返回结果消息
     */
    private String message;
    /**
     * 返回结果数据
     */
    private T result;
    /**
     * 错误信息
     */
    private List<ErrorMsg> errors;

    public ResponseEntity() {
    }

    public ResponseEntity(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * @since 2.3.2
     */
    public static <T> ResponseEntity<T> success() {
        return new ResponseEntity(StatusCode.OK.getValue(), "请求成功");
    }

    /**
     * @since 2.3.2
     */
    public static <T> ResponseEntity<T> success(String message) {
        return new ResponseEntity(StatusCode.OK.getValue(), message);
    }

    /**
     * @since 2.3.2
     */
    public static <T> ResponseEntity<T> success(T result) {
        ResponseEntity responseEntity = new ResponseEntity(StatusCode.OK.getValue(), "请求成功");
        responseEntity.setResult(result);
        return responseEntity;
    }

    /**
     * @since 2.3.2
     */
    public static <T> ResponseEntity<T> fail() {
        return new ResponseEntity(StatusCode.ERROR.getValue(), "请求失败");
    }

    /**
     * @since 2.3.2
     */
    public static <T> ResponseEntity<T> fail(String message) {
        return new ResponseEntity(StatusCode.ERROR.getValue(), message);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<ErrorMsg> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorMsg> errors) {
        this.errors = errors;
    }

    public void addError(ErrorMsg error) {
        if (errors == null) {
            errors = new ArrayList<ErrorMsg>();
        }
        errors.add(error);
    }

    public void addError(String field, String message) {
        if (errors == null) {
            errors = new ArrayList<ErrorMsg>();
        }
        errors.add(new ErrorMsg(field, message));
    }

    public void addError(String code, String field, String message) {
        if (errors == null) {
            errors = new ArrayList<ErrorMsg>();
        }
        errors.add(new ErrorMsg(code, field, message));
    }

    /**
     * @return 结果编码
     * @since 2.3.2
     */
    public Integer getCode() {
        return statusCode;
    }

    /**
     * @return 返回结果消息
     * @since 2.3.2
     */
    public String getMsg() {
        return message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}