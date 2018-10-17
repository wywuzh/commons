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
package com.wuzh.commons.core.returns;

/**
 * 类ReturnCode.java的实现描述：返回结果编码
 * 
 * @author 伍章红 2015-9-12 下午12:29:34
 * @version v1.0.0
 * @since JDK 1.7.0_71
 */
public enum ReturnCode {

    /**
     * 请求成功
     */
    SUCCESS("SUCCESS"),
    /**
     * 请求失败
     */
    FAIL("FAIL"),
    /**
     * 请求错误
     */
    ERROR("ERROR"),
    /**
     * 请求异常
     */
    EXCEPTION("EXCEPTION");

    private String code;

    private ReturnCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ReturnCode findByCode(String code) {
        if ("SUCCESS".equals(code)) {
            return SUCCESS;
        } else if ("FAIL".equals(code)) {
            return FAIL;
        } else if ("ERROR".equals(code)) {
            return ERROR;
        } else if ("EXCEPTION".equals(code)) {
            return EXCEPTION;
        }
        return null;
    }
}
