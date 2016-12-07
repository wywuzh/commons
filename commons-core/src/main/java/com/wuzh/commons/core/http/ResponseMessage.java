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
package com.wuzh.commons.core.http;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类ResponseMessage.java的实现描述：处理结果消息返回
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月12日 上午10:42:16
 * @version v1.0.0
 * @since JDK 1.7
 */
public class ResponseMessage extends HttpBasic {
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private int statusCode;
    /**
     * 处理结果
     */
    private String result;

    public ResponseMessage() {
        super();
    }

    public ResponseMessage(int statusCode, String result) {
        super();
        this.statusCode = statusCode;
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result + statusCode;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResponseMessage other = (ResponseMessage) obj;
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        } else if (!result.equals(other.result)) {
            return false;
        }
        if (statusCode != other.statusCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
