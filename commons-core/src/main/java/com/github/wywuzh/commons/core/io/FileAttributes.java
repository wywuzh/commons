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
package com.github.wywuzh.commons.core.io;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 类FileAttributes.java的实现描述：文件属性信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:44:03
 * @version v1.0.0
 * @since JDK 1.7
 */
public class FileAttributes implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 文件名
   */
  private String name;
  /**
   * 文件路径（相对路径）
   */
  private String path;
  /**
   * 绝对路径
   */
  private String absolutePath;
  /**
   * 创建时间
   */
  private long creationTime;
  /**
   * 最后修改时间
   */
  private long lastModifiedTime;
  /**
   * 最后访问时间
   */
  private long lastAccessTime;
  /**
   * 文件大小（字节）
   */
  private long size;
  /**
   * 是否可读
   */
  private boolean canRead;
  /**
   * 是否可写
   */
  private boolean canWrite;
  /**
   * 是否可操作文件
   */
  private boolean canExecute;
  /**
   * 是否隐藏文件
   */
  private boolean isHidden;

  public FileAttributes() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getAbsolutePath() {
    return absolutePath;
  }

  public void setAbsolutePath(String absolutePath) {
    this.absolutePath = absolutePath;
  }

  public long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }

  public long getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(long lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public long getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(long lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public boolean isCanRead() {
    return canRead;
  }

  public void setCanRead(boolean canRead) {
    this.canRead = canRead;
  }

  public boolean isCanWrite() {
    return canWrite;
  }

  public void setCanWrite(boolean canWrite) {
    this.canWrite = canWrite;
  }

  public boolean isCanExecute() {
    return canExecute;
  }

  public void setCanExecute(boolean canExecute) {
    this.canExecute = canExecute;
  }

  public boolean isHidden() {
    return isHidden;
  }

  public void setHidden(boolean isHidden) {
    this.isHidden = isHidden;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
