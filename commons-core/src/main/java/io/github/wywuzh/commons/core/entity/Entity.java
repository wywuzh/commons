/*
 * Copyright 2015-2026 the original author or authors.
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
 * <p>
 * 所有实体接口的顶层基类，约定实体必须支持序列化。
 * 同时显式声明 {@link #hashCode()}、{@link #equals(Object)}、{@link #toString()} 三个方法，
 * 强制实现类必须重写（Java 规范禁止接口以 default 方式覆盖 Object 的 public 方法，故此处采用抽象声明约束实现类）。
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:21:12
 * @version v1.0.0
 * @see java.io.Serializable
 * @since JDK 1.7
 */
public interface Entity extends Serializable {

    /**
     * 实体哈希码，实现类必须重写，建议基于业务主键生成
     *
     * @author 伍章红 2015-8-19 上午10:43:36
     * @return 哈希码
     */
    @Override
    public int hashCode();

    /**
     * 实体相等性比较，实现类必须重写，建议基于业务主键比较
     *
     * @author 伍章红 2015-8-19 上午10:43:37
     * @param obj
     *                待比较对象
     * @return true 表示相等
     */
    @Override
    public boolean equals(Object obj);

    /**
     * 实体字符串表示，实现类必须重写
     *
     * @author 伍章红 2015-8-19 上午10:43:38
     * @return 字符串表示
     */
    @Override
    public String toString();
}
