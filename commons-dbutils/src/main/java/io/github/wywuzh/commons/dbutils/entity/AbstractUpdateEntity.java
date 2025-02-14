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
package io.github.wywuzh.commons.dbutils.entity;

import java.util.Date;

import io.github.wywuzh.commons.core.entity.Updated;
import io.github.wywuzh.commons.dbutils.annotation.Column;

/**
 * 类AbstractUpdateEntity.java的实现描述：实体基类（含修改信息）
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月11日 下午2:10:47
 * @version v1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractUpdateEntity extends AbstractCreateEntity implements Updated {
    private static final long serialVersionUID = 1L;

    /**
     * 修改人
     */
    @Column(name = "UPDATE_USER")
    private String updateUser;
    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
