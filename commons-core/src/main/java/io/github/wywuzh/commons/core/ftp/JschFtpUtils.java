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
package io.github.wywuzh.commons.core.ftp;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 类JschFtpUtils的实现描述：jsch FTP 文件传输工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-14 09:45:40
 * @version v2.3.6
 * @since JDK 1.8
 */
@Slf4j
public class JschFtpUtils {

    public static ThreadLocal<Session> SESSION = new ThreadLocal<>();
    public static ThreadLocal<Channel> CHANNEL = new ThreadLocal<>();
    public static ThreadLocal<ChannelSftp> CHANNEL_SFTP = new ThreadLocal<>();

    /**
     * 开启连接，获取ChannelSftp通道
     *
     * @param host     主机地址
     * @param port     端口
     * @param username 用户
     * @param password 密码
     * @return
     * @throws JSchException
     */
    public static ChannelSftp connect(String host, int port, String username, String password) throws JSchException {
        ChannelSftp channelSftp = null;
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setPassword(password);

            Properties config = new Properties();
            // SSH公钥检查机制：no、ask、yes
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            // 设置超时
            session.setTimeout(1500);
            // 开启连接
            session.connect();
            log.info("Session Connected!");

            // 获取sftp通道
            Channel channel = session.openChannel("sftp");
            // 开启
            channel.connect();
            log.info("ChannelSftp Connected!");

            channelSftp = (ChannelSftp) channel;

            SESSION.set(session);
            CHANNEL.set(channel);
            CHANNEL_SFTP.set(channelSftp);
        } catch (JSchException e) {
            log.error(e.getMessage(), e);
            try {
                disconnect();
            } finally {
                throw e;
            }
        }

        return channelSftp;
    }

    /**
     * 关闭连接
     */
    public static void disconnect() {
        ChannelSftp channelSftp = CHANNEL_SFTP.get();
        if (channelSftp != null && channelSftp.isConnected()) {
            channelSftp.disconnect();
        }
        Channel channel = CHANNEL.get();
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        Session session = SESSION.get();
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    /**
     * 上传文件到服务器
     *
     * @param ftpPath        服务器目标地址，文件上传目录
     * @param localFile      需要上传的本地文件
     * @param remoteFilename 新文件名
     * @throws IOException
     */
    public static void uploadFile(String ftpPath, String localFile, String remoteFilename) throws IOException {
        ChannelSftp channelSftp = CHANNEL_SFTP.get();
        if (channelSftp == null || !channelSftp.isConnected()) {
            throw new RuntimeException("连接未开启！");
        }
        if (channelSftp.isClosed()) {
            throw new RuntimeException("连接已关闭，请求失败！");
        }

        FileInputStream inputStream = null;
        try {
            try {
                // 进入目录
                channelSftp.cd(ftpPath);
            } catch (SftpException e) {
                if (channelSftp.SSH_FX_NO_SUCH_FILE == e.id) { // 指定上传路径不存在
                    channelSftp.mkdir(ftpPath);// 创建目录
                    channelSftp.cd(ftpPath); // 进入目录
                }
            }
            inputStream = new FileInputStream(new File(localFile));
            channelSftp.put(inputStream, remoteFilename);
        } catch (IOException | SftpException e) {
            log.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 上传文件
     *
     * @param host           服务器地址
     * @param port           服务器端口
     * @param username       用户名
     * @param password       密码
     * @param ftpPath        服务器目标地址，文件上传目录
     * @param localFile      需要上传的本地文件
     * @param remoteFilename 新文件名
     * @throws JSchException
     * @throws IOException
     */
    public static void uploadFile(String host, int port, String username, String password, String ftpPath, String localFile, String remoteFilename) throws JSchException, IOException {
        try {
            // 开启连接
            connect(host, port, username, password);
            log.info("host={}, port={} 连接已开启！", host, port);

            // 上传文件
            // scp -rp -P 60777 root@10.0.43.32:/www/report-allin
            uploadFile(ftpPath, localFile, remoteFilename);
            log.info("localFile={} 文件已经成功上传到{}@{}:{}目录", localFile, username, host, ftpPath);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            // 关闭连接
            disconnect();
        }
    }

    /**
     * 下载文件
     *
     * @param host     服务器地址
     * @param port     服务器端口
     * @param username 用户名
     * @param password 密码
     * @param ftpPath  服务器目标地址，文件上传目录
     * @param fileName 服务器文件名称
     */
    public static void downloadFile(String host, int port, String username, String password, String ftpPath, String fileName) {
    }

}
