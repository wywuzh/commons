/*
 * Copyright 2015-2022 the original author or authors.
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
package com.github.wywuzh.commons.pager;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * 类PaginationParamter.java的实现描述：分页请求参数类
 *
 * @author 伍章红 2015年11月6日 下午1:08:24
 * @version v2.0.2
 * @since JDK 1.8
 */
public class PaginationParameter<P extends Serializable> extends PageImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询条件
     */
    private P vo;

    /**
     * 排序字段
     */
    private List<Sort> sorts;

    public PaginationParameter() {
        super();
    }

    public PaginationParameter(int pageNo, int pageSize) {
        super(pageNo, pageSize);
    }

    public P getVo() {
        return vo;
    }

    public void setVo(P vo) {
        this.vo = vo;
    }

    @Override
    public long getRowCount() {
        return 0;
    }

    @Override
    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    /**
     * 生成排序sql
     *
     * @return 排序sql
     * @since v2.3.7
     */
    public String generateOrderSql() {
        List<Sort> sorts = this.getSorts();
        if (CollectionUtils.isEmpty(sorts)) {
            return "";
        }
        StringBuilder orderSql = new StringBuilder(" order by ");
        for (Sort sort : sorts) {
            orderSql.append(sort.getOrder()).append(" ").append(sort.getOrder().getValue());
        }
        return orderSql.toString();
    }
}
