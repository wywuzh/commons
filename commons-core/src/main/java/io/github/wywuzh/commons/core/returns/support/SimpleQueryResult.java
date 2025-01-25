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
package io.github.wywuzh.commons.core.returns.support;

import java.util.List;

import io.github.wywuzh.commons.core.returns.ReturnBase;
import io.github.wywuzh.commons.core.returns.ReturnCode;

/**
 * 类SimpleQueryResult.java的实现描述：查询列表数据返回
 *
 * @author 伍章红 2015-9-12 下午12:42:13
 * @version v1.0.0
 * @since JDK 1.7.0_71
 */
public class SimpleQueryResult<T> extends ReturnBase {
    private static final long serialVersionUID = 1L;

    /**
     * 查询数据总数
     */
    private long total;
    /**
     * 查询数据结果
     */
    private List<T> entityList;

    public SimpleQueryResult() {
        super();
    }

    public SimpleQueryResult(ReturnCode returnCode) {
        super(returnCode);
    }

    public SimpleQueryResult(ReturnCode returnCode, String message) {
        super(returnCode, message);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("SimpleQueryResult [");
        result.append("returnCode=").append(getReturnCode()).append(",");
        result.append("message=").append(getMessage()).append(",");
        result.append("total=").append(total).append(",");
        result.append("entityList=").append(entityList);
        result.append("]");
        return result.toString();
    }
}
