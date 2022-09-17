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
package com.github.wywuzh.commons.dingtalk.request.message;

/**
 * 类MsgType的实现描述：消息类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 17:49:13
 * @version v2.3.8
 * @since JDK 1.8
 */
public enum MsgType {
  text("text"), // 文本
  image("image"), // 图片
  voice("voice"), // 语音
  file("file"), // 文件
  link("link"), // 链接
  oa("oa"), // oa
  markdown("markdown"), // markdown
  action_card("action_card"), // 卡片
  ;

  private String type;

  MsgType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
