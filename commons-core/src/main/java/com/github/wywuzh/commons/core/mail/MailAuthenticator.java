/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 类MailAuthenticator.java的实现描述：服务器邮箱登录验证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:50:57
 * @version v1.0.0
 * @see javax.mail.Authenticator
 * @since JDK 1.7
 */
public class MailAuthenticator extends Authenticator {

    /**
     * 用户名（登录邮箱）
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public MailAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
