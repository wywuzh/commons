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
package com.wuzh.commons.core.returns.support;

import com.wuzh.commons.core.returns.ReturnBase;
import com.wuzh.commons.core.returns.ReturnCode;

/**
 * 类SimpleInfoResult.java的实现描述：查询明细数据返回
 * 
 * @author 伍章红 2015-9-12 下午12:34:22
 * @version v1.0.0
 * @since JDK 1.7.0_71
 */
public class SimpleInfoResult<T> extends ReturnBase {
    private static final long serialVersionUID = 1L;

    /**
     * 单个数据信息列表
     */
    public T entity;

    public SimpleInfoResult() {
        super();
    }

    public SimpleInfoResult(ReturnCode returnCode, String message) {
        super(returnCode, message);
    }

    public SimpleInfoResult(ReturnCode returnCode) {
        super(returnCode);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("SimpleQueryResult [");
        result.append("returnCode=").append(getReturnCode()).append(",");
        result.append("message=").append(getMessage()).append(",");
        result.append("entity=").append(entity);
        result.append("]");
        return result.toString();
    }
}
