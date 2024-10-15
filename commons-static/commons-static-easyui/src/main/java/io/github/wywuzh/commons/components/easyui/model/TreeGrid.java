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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类TreeGrid.java的实现描述：jQuery-easyui组件treegrid对应实体类
 *
 * @author 伍章红 2015年11月10日 下午3:58:10
 * @version v1.0.0
 * @since JDK 1.7
 */
public class TreeGrid<T extends Serializable> extends Tree<T> {
    private static final long serialVersionUID = 1L;

    public TreeGrid() {
        super();
    }

    public TreeGrid(String id, String name, String text, String pid, String iconCls) {
        super(id, name, text, pid, iconCls);
    }

    public TreeGrid(String id, String name, String text, String pid) {
        super(id, name, text, pid);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
