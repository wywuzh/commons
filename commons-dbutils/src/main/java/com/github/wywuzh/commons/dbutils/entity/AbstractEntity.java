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
package com.github.wywuzh.commons.dbutils.entity;

import java.io.Serializable;

import com.github.wywuzh.commons.dbutils.annotation.Column;

/**
 * 类IdEntity.java的实现描述：实体基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月11日 下午1:59:42
 * @version v1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Column(name = "ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public abstract String toString();

}
