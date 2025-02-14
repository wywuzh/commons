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

/**
 * 类Code.java的实现描述：编码
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:22:39
 * @version v1.0.0
 * @since JDK 1.7
 */
public interface Code extends Entity {

    /**
     * 编码（采用32位的UUID值）
     *
     * @author 伍章红 2015-8-19 上午11:30:01
     * @return
     */
    public String getCode();

    /**
     * @author 伍章红 2015-8-19 上午11:29:57
     * @param code
     */
    public void setCode(String code);

}
