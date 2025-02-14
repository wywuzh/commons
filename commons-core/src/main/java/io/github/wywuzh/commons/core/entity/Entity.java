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
package io.github.wywuzh.commons.core.entity;

import java.io.Serializable;

/**
 * 类Entity.java的实现描述：数据库持久层实体基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:21:12
 * @version v1.0.0
 * @see java.io.Serializable
 * @since JDK 1.7
 */
public interface Entity extends Serializable {

    /**
     * @author 伍章红 2015-8-19 上午10:43:36
     * @return
     */
    @Override
    public int hashCode();

    /**
     * @author 伍章红 2015-8-19 上午10:43:37
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj);

    /**
     * @author 伍章红 2015-8-19 上午10:43:38
     * @return
     */
    @Override
    public String toString();
}
