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
package io.github.wywuzh.commons.core.mail;

import java.util.List;
import java.util.Properties;

import org.springframework.util.Assert;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * 类MailSender.java的实现描述：发送邮件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:50:18
 * @version v1.0.0
 * @see java.util.Properties
 * @see MailAuthenticator
 * @since JDK 1.7
 */
public class MailSender {

    /**
     * 发送邮件的props文件
     */
    private final transient Properties properties = System.getProperties();

    private transient MailAuthenticator authenticator;

    private Session session;

    public MailSender(final String username, final String password) {
        init(username, password, "smtp.163.com");
    }

    public MailSender(final String smtpHostName, final String username, final String password) {
        init(username, password, smtpHostName);
    }

    private void init(String username, String password, String smtpHostName) {
        // 初始化properties
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", smtpHostName);

        // 验证
        authenticator = new MailAuthenticator(username, password);
        // 创建session
        session = Session.getInstance(properties, authenticator);
    }

    /**
     * 发送邮件
     *
     * @param recipient
     *                      收件人邮箱地址
     * @param subject
     *                      邮件主题
     * @param content
     *                      邮件内容
     * @throws MessagingException
     * @throws AddressException
     */
    public void send(String recipient, String subject, Object content) throws AddressException, MessagingException {
        Assert.notNull(recipient, "收件人邮箱地址不能为空");

        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        // 发送邮件
        Transport.send(message);
    }

    /**
     * 发送邮件（含抄送人）
     *
     * @param recipient
     *                      收件人邮箱
     * @param cc
     *                      抄送人邮箱
     * @param subject
     *                      邮件主题
     * @param content
     *                      邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, String cc, String subject, Object content) throws AddressException, MessagingException {
        Assert.notNull(recipient, "收件人邮箱地址不能为空");
        Assert.notNull(cc, "抄送人邮箱地址不能为空");

        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));

        message.setRecipient(RecipientType.CC, new InternetAddress(cc));
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        // 发送邮件
        Transport.send(message);
    }

    /**
     * 发送邮件（包含抄送人、密送人）
     *
     * @param recipient
     *                      收件人邮箱地址
     * @param cc
     *                      抄送人邮箱地址
     * @param bcc
     *                      密送人邮箱地址
     * @param subject
     *                      邮件主题
     * @param content
     *                      邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, String cc, String bcc, String subject, Object content) throws AddressException, MessagingException {
        Assert.notNull(recipient, "收件人邮箱地址不能为空");
        Assert.notNull(cc, "抄送人邮箱地址不能为空");
        Assert.notNull(bcc, "密送人邮箱地址不能为空");

        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
        // 抄送人
        message.setRecipient(RecipientType.CC, new InternetAddress(cc));
        // 密送人
        message.setRecipient(RecipientType.BCC, new InternetAddress(bcc));
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        // 发送邮件
        Transport.send(message);
    }

    /**
     * 群发邮件
     *
     * @param recipients
     *                       收件人列表
     * @param subject
     *                       邮件主题
     * @param content
     *                       邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Object content) throws AddressException, MessagingException {
        Assert.notEmpty(recipients, "收件人邮箱地址不能为空");

        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        InternetAddress[] addresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.TO, addresses);
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        // 发送邮件
        Transport.send(message);
    }

    /**
     * 群发邮件（包含抄送人）
     *
     * @param recipients
     *                       收件人邮箱地址
     * @param ccs
     *                       抄送人邮箱地址
     * @param subject
     *                       主题
     * @param content
     *                       邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, List<String> ccs, String subject, Object content) throws AddressException, MessagingException {
        Assert.notEmpty(recipients, "收件人邮箱地址不能为空");
        Assert.notEmpty(ccs, "抄送人邮箱地址不能为空");

        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        InternetAddress[] toAddresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            toAddresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.TO, toAddresses);
        // 设置抄送人
        InternetAddress[] ccaddresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            ccaddresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.CC, ccaddresses);
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        // 发送邮件
        Transport.send(message);
    }

    /**
     * 群发邮件（包含抄送人、密送人）
     *
     * @param recipients
     *                       收件人邮箱地址
     * @param ccs
     *                       抄送人邮箱地址
     * @param bccs
     *                       密送人邮箱地址
     * @param subject
     *                       主题
     * @param content
     *                       邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, List<String> ccs, List<String> bccs, String subject, Object content) throws AddressException, MessagingException {
        Assert.notEmpty(recipients, "收件人邮箱地址不能为空");
        Assert.notEmpty(ccs, "抄送人邮箱地址不能为空");
        Assert.notEmpty(bccs, "密送人邮箱地址不能为空");

        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        InternetAddress[] addresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.TO, addresses);
        // 设置抄送人
        InternetAddress[] ccaddresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            ccaddresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.CC, ccaddresses);
        // 设置密送人
        InternetAddress[] bccaddresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            bccaddresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.BCC, bccaddresses);
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");
        // 发送邮件
        Transport.send(message);
    }
}
