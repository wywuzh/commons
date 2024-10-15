/*
 * Copyright 2015-2024 the original author or authors.
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

/**
 * 类Status.java的实现描述：数据使用状态
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:23:14
 * @version v1.0.0
 * @see Entity
 * @since JDK 1.7
 */
public interface Status extends Entity {

    /**
     * 数据使用状态
     * 0）初始（不可用）
     * 1）正常
     * 2）删除
     *
     * @author 伍章红 2015-8-19 上午11:40:07
     * @return
     */
    public String getStatus();

    /**
     * @author 伍章红 2015-8-19 上午11:40:11
     * @param status
     */
    public void setStatus(String status);

}
