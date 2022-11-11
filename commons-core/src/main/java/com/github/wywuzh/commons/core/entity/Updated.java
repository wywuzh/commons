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
package com.github.wywuzh.commons.core.entity;

/**
 * 类Updated.java的实现描述：信息修改人
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:23:43
 * @version v1.0.0
 * @see Entity
 * @since JDK 1.7
 */
public interface Updated extends Entity {

    /**
     * 修改人编码（统一指向账户编码）
     *
     * @author 伍章红 2015-8-19 上午11:53:34
     * @return
     */
    public String getUpdateUser();

    /**
     * @author 伍章红 2015-8-19 上午11:53:45
     * @param updateUser
     */
    public void setUpdateUser(String updateUser);

    /**
     * 修改时间
     *
     * @author 伍章红 2015-8-19 上午11:53:40
     * @return
     */
    public java.util.Date getUpdateTime();

    /**
     * @author 伍章红 2015-8-19 上午11:53:48
     * @param updateTime
     */
    public void setUpdateTime(java.util.Date updateTime);
}
