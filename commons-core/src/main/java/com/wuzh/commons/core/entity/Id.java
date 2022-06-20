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
package com.wuzh.commons.core.entity;

/**
 * 类Id.java的实现描述：id类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:22:07
 * @version v1.0.0
 * @see com.wuzh.commons.core.entity.Entity
 * @since JDK 1.7
 */
public interface Id extends Entity {

    /**
     * 主键id，自增
     * 
     * @author 伍章红 2015-8-19 上午11:26:24
     * @return
     */
    public Long getId();

    /**
     * @author 伍章红 2015-8-19 上午11:26:29
     * @param id
     */
    public void setId(Long id);

}
