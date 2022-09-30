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
package com.github.wywuzh.commons.core.http;

/**
 * 类ResponseCallBack.java的实现描述：自定义响应回调接口
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月12日 上午11:43:04
 * @version v1.0.0
 * @since JDK 1.7
 */
public interface ResponseCallBack {

  /**
   * 响应回调方法
   *
   * @param statusCode 返回码
   * @param resultJson 返回结果
   * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月11日 上午10:58:18
   */
  public void response(int statusCode, String resultJson);
}
