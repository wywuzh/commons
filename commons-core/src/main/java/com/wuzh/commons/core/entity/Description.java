/*
 * Copyright 2015-2016 the original author or authors.
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
 * 类Description.java的实现描述：备注信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:24:16
 * @version v1.0.0
 * @see com.wuzh.frame.core.entity.Entity
 * @since JDK 1.7
 */
public interface Description extends Entity {

    /**
     * 备注信息
     * 
     * @author 伍章红 2015-8-19 上午11:38:17
     * @return
     */
    public String getDescription();

    /**
     * @author 伍章红 2015-8-19 上午11:38:23
     * @param description
     */
    public void setDescription(String description);

}
