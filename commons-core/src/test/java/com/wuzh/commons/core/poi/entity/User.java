/*
 * Copyright 2015-2021 the original author or authors.
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
package com.wuzh.commons.core.poi.entity;

import lombok.Data;

/**
 * 类User的实现描述：用户
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-25 18:38:12
 * @version v2.3.7
 * @since JDK 1.8
 */
@Data
public class User {

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 性别
     */
    private String sex;

}