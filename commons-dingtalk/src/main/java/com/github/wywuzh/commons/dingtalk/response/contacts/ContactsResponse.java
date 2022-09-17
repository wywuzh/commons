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
package com.github.wywuzh.commons.dingtalk.response.contacts;

import com.github.wywuzh.commons.dingtalk.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类ContactsResponse的实现描述：通讯录管理-返回基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:18:52
 * @version v2.3.8
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactsResponse<T> extends BaseResponse {
  private static final long serialVersionUID = 1353956884950909981L;

  /**
   * 请求ID
   */
  @SerializedName(value = "request_id")
  private String requestId;
  /**
   * 结果
   */
  @SerializedName(value = "result")
  private T result;

}
