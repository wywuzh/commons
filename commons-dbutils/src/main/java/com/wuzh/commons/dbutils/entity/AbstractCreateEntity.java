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
package com.wuzh.commons.dbutils.entity;

import java.util.Date;

import com.wuzh.commons.core.entity.Created;
import com.wuzh.commons.dbutils.annotation.Column;

/**
 * 类DeleteEntity.java的实现描述：实体基类（含创建信息）
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月11日 下午2:06:21
 * @version v1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractCreateEntity extends AbstractEntity implements Created {
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER")
    private String createUser;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
