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
package com.github.wywuzh.commons.components.easyui.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类Tree.java的实现描述：jQuery-easyui组件tree对应实体类
 *
 * @author 伍章红 2015年11月10日 下午3:17:21
 * @version v1.0.0
 * @since JDK 1.7
 */
public class Tree<T extends Serializable> implements IBaseUIModel, Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 节点id
   */
  private String id;
  /**
   * 节点名称
   */
  private String name;
  /**
   * 节点文本值
   */
  private String text;
  /**
   * 父类节点id
   */
  private String pid;
  /**
   * 节点状态（值为'open'或者'closed'，默认为'open'。open时表示展开子节点，closed表示关闭子节点）
   */
  private String state;

  /**
   * 节点图标
   */
  private String iconCls;
  /**
   * 子节点
   */
  private List<Tree<T>> children;
  /**
   * 自定义属性
   */
  private T attributes;

  /**
   * 关联目标对象，作为扩展属性使用，提供前台组件引用
   */
  private Serializable target;

  public Tree() {
    super();
    this.state = State.OPEN.getValue();
  }

  public Tree(String id, String name, String text, String pid) {
    super();
    this.id = id;
    this.name = name;
    this.text = text;
    this.pid = pid;
    this.state = State.OPEN.getValue();
  }

  public Tree(String id, String name, String text, String pid, String iconCls) {
    super();
    this.id = id;
    this.name = name;
    this.text = text;
    this.pid = pid;
    this.iconCls = iconCls;
    this.state = State.OPEN.getValue();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
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

  @Override
  public Serializable getTarget() {
    return target;
  }

  @Override
  public void setTarget(Serializable target) {
    this.target = target;
  }

}
