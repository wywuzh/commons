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
package io.github.wywuzh.commons.components.easyui.model;

import java.io.Serializable;

/**
 * 类IBaseUIModel.java的实现描述：UI组件基类
 *
 * @author 伍章红 2015年11月10日 下午4:29:05
 * @version v1.0.0
 * @since JDK 1.7
 */
public interface IBaseUIModel {

    /**
     * 关联目标对象，作为扩展属性使用，提供前台组件引用
     *
     * @author 伍章红 2015年11月10日 下午5:18:52
     * @return
     */
    public Serializable getTarget();

    /**
     * 关联目标对象，作为扩展属性使用，提供前台组件引用
     *
     * @author 伍章红 2015年11月10日 下午5:18:54
     * @param target
     */
    public void setTarget(Serializable target);
}
