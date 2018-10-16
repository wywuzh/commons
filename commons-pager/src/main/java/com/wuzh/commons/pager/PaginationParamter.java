/*
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
package com.wuzh.commons.pager;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类PaginationParamter.java的实现描述：分页请求参数类
 * 
 * @author 伍章红 2015年11月6日 下午1:08:24
 * @version v1.0.0
 * @since JDK 1.7
 */
public class PaginationParamter<P extends Serializable> extends PageImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 查询条件
     */
    private P vo;

    /**
     * 排序字段
     */
    private List<Sort> sorts;

    public PaginationParamter() {
        super();
    }

    public PaginationParamter(int pageNo, int pageSize) {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
