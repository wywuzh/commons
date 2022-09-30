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
package com.github.wywuzh.commons.jdbc.vo;

import com.github.wywuzh.commons.core.entity.Created;

/**
 * 类AbstractCreateVo.java的实现描述：实体基类（含创建信息）
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月6日 上午10:44:04
 * @version v1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractCreateVo extends AbstractVo implements Created {
  private static final long serialVersionUID = 6471105541415695396L;

  /**
   * 创建人
   */
  private String createUser;
  /**
   * 创建时间
   */
  private java.util.Date createTime;

  @Override
  public String getCreateUser() {
    return createUser;
  }

  @Override
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  @Override
  public java.util.Date getCreateTime() {
    return createTime;
  }

  @Override
  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }
}
