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
 * 类CheckboxTree.java的实现描述：复选框tree
 *
 * @author 伍章红 2015年11月10日 下午4:36:23
 * @version v1.0.0
 * @since JDK 1.7
 */
public class CheckboxTree<T extends Serializable> extends Tree<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点是否被选中
     */
    private boolean checked;

    public CheckboxTree() {
        super();
    }

    public CheckboxTree(String id, String name, String text, String pid) {
        super(id, name, text, pid);
    }

    public CheckboxTree(String id, String name, String text, String pid, String iconCls) {
        super(id, name, text, pid, iconCls);
    }

    public CheckboxTree(String id, String name, String text, String pid, boolean checked) {
        super(id, name, text, pid);
        this.checked = checked;
    }

    public CheckboxTree(String id, String name, String text, String pid, String iconCls, boolean checked) {
        super(id, name, text, pid, iconCls);
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
