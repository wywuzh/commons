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
package com.github.wywuzh.commons.dbutils.vo;

import com.github.wywuzh.commons.core.entity.Updated;

import java.util.Date;

/**
 * 类AbstractUpdateEntity.java的实现描述：实体基类（含修改信息）
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月11日 下午2:10:47
 * @version v1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractUpdateVo extends AbstractCreateVo implements Updated {
    private static final long serialVersionUID = 1L;

    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
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
