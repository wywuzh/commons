/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.components.okadmin;

import java.io.Serializable;
import java.util.List;

/**
 * 类Tree的实现描述：树形组件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-05-05 15:51:23
 * @version v2.2.6
 * @since JDK 1.8
 */
public class Tree<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -3854844412243011587L;

    /**
     * 节点唯一索引值，用于对指定节点进行各类操作
     */
    private String id;
    /**
     * 节点字段名
     */
    private String field;
    /**
     * 节点标题
     */
    private String title;
    /**
     * 父类节点id
     */
    private String parentId;
    /**
     * 点击节点弹出新窗口对应的 url。需开启 isJump 参数
     */
    private String href;
    /**
     * 节点是否初始展开，默认 false
     */
    private boolean spread = false;
    /**
     * 节点是否初始为选中状态（如果开启复选框的话），默认 false
     */
    private boolean checked = false;
    /**
     * 节点是否为禁用状态。默认 false
     */
    private boolean disabled = false;
    /**
     * 子节点。支持设定选项同父节点
     */
    private List<Tree<T>> children;
    /**
     * 自定义属性
     */
    private T attributes;

    public Tree() {
    }

    public Tree(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }
}