/*
 * Copyright 2015-2026 the original author or authors.
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

    private static final ThreadLocal<Session> SESSION = new ThreadLocal<>();
    private static final ThreadLocal<Channel> CHANNEL = new ThreadLocal<>();
    private static final ThreadLocal<ChannelSftp> CHANNEL_SFTP = new ThreadLocal<>();

    private static final int DEFAULT_TIMEOUT = 1500;
    private static final String STRICT_HOST_KEY_CHECKING = "no";
    private static final String CHANNEL_TYPE_SFTP = "sftp";

    /**
     * 私有构造方法，防止实例化
     */
    private JschFtpUtils() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }

    /**
     * 开启连接，获取ChannelSftp通道
     *
     * @param host     主机地址
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return ChannelSftp 实例
     * @throws JSchException 连接失败时抛出
     */
    public static ChannelSftp connect(String host, int port, String username, String password) throws JSchException {
        return connect(host, port, username, password, DEFAULT_TIMEOUT);
    }

    /**
     * 开启连接，获取ChannelSftp通道（可自定义超时时间）
     *
     * @param host     主机地址
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @param timeout  超时时间（毫秒）
     * @return ChannelSftp 实例
     * @throws JSchException 连接失败时抛出
     */
    public static ChannelSftp connect(String host, int port, String username, String password, int timeout) throws JSchException {
        // 清理之前的连接
        cleanup();

        Session session = null;
        Channel channel = null;

        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, host, port);
            session.setPassword(password);

            Properties config = new Properties();
            // SSH公钥检查机制：no、ask、yes
            config.put("StrictHostKeyChecking", STRICT_HOST_KEY_CHECKING);
            session.setConfig(config);
            session.setTimeout(timeout);

            // 开启连接
            session.connect();
            log.info("Session connected successfully to {}:{}", host, port);

            // 获取sftp通道
            channel = session.openChannel(CHANNEL_TYPE_SFTP);
            channel.connect();

            ChannelSftp channelSftp = (ChannelSftp) channel;
            log.info("ChannelSftp connected successfully");

            // 存储到ThreadLocal
            SESSION.set(session);
            CHANNEL.set(channel);
            CHANNEL_SFTP.set(channelSftp);

            return channelSftp;

        } catch (JSchException e) {
            // 连接失败时清理资源
            closeQuietly(channel);
            closeQuietly(session);
            log.error("Failed to connect to {}:{} with user {}", host, port, username, e);
            throw new JSchException("SFTP connection failed: " + e.getMessage(), e);
        }
    }

    /**
     * 关闭连接并清理ThreadLocal
     */
    public static void disconnect() {
        cleanup();
    }

    /**
     * 清理资源
     */
    private static void cleanup() {
        ChannelSftp channelSftp = CHANNEL_SFTP.get();
        if (channelSftp != null) {
            closeQuietly(channelSftp);
            CHANNEL_SFTP.remove();
        }

        Channel channel = CHANNEL.get();
        if (channel != null) {
            closeQuietly(channel);
            CHANNEL.remove();
        }

        Session session = SESSION.get();
        if (session != null) {
            closeQuietly(session);
            SESSION.remove();
        }
    }

    /**
     * 静默关闭ChannelSftp
     */
    private static void closeQuietly(ChannelSftp channelSftp) {
        if (channelSftp != null && channelSftp.isConnected()) {
            try {
                channelSftp.disconnect();
                log.debug("ChannelSftp disconnected successfully");
            } catch (Exception e) {
                log.warn("Error while disconnecting ChannelSftp", e);
            }
        }
    }

    /**
     * 静默关闭Channel
     */
    private static void closeQuietly(Channel channel) {
        if (channel != null && channel.isConnected()) {
            try {
                channel.disconnect();
                log.debug("Channel disconnected successfully");
            } catch (Exception e) {
                log.warn("Error while disconnecting Channel", e);
            }
        }
    }

    /**
     * 静默关闭Session
     */
    private static void closeQuietly(Session session) {
        if (session != null && session.isConnected()) {
            try {
                session.disconnect();
                log.debug("Session disconnected successfully");
            } catch (Exception e) {
                log.warn("Error while disconnecting Session", e);
            }
        }
    }

    /**
     * 检查连接状态
     *
     * @return 连接是否有效
     */
    public static boolean isConnected() {
        ChannelSftp channelSftp = CHANNEL_SFTP.get();
        return channelSftp != null && channelSftp.isConnected() && !channelSftp.isClosed();
    }

    /**
     * 确保目录存在，如果不存在则创建
     *
     * @param ftpPath 目录路径
     * @throws SftpException 目录操作失败时抛出
     */
    private static void ensureDirectoryExists(String ftpPath) throws SftpException {
        ChannelSftp channelSftp = CHANNEL_SFTP.get();
        if (channelSftp == null) {
            throw new IllegalStateException("ChannelSftp is not initialized");
        }

        try {
            channelSftp.cd(ftpPath);
            log.debug("Directory {} already exists", ftpPath);
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                // 目录不存在，递归创建
                createDirectories(ftpPath, channelSftp);
                channelSftp.cd(ftpPath);
                log.debug("Directory {} created successfully", ftpPath);
            } else {
                throw e;
            }
        }
    }

    /**
     * 递归创建目录
     *
     * @param path        目录路径
     * @param channelSftp ChannelSftp实例
     * @throws SftpException 创建失败时抛出
     */
    private static void createDirectories(String path, ChannelSftp channelSftp) throws SftpException {
        String[] directories = path.split("/");
        StringBuilder currentPath = new StringBuilder();

        for (String dir : directories) {
            if (dir.isEmpty()) {
                continue;
            }
            currentPath.append("/").append(dir);

            try {
                channelSftp.mkdir(currentPath.toString());
            } catch (SftpException e) {
                if (e.id != ChannelSftp.SSH_FX_FAILURE) {
                    // 如果不是目录已存在的错误，重新抛出
                    throw e;
                }
            }
        }
    }

    /**
     * 上传文件到服务器
     *
     * @param ftpPath        服务器目标地址，文件上传目录
     * @param localFile      需要上传的本地文件路径
     * @param remoteFilename 远程文件名
     * @throws IOException   文件操作异常
     * @throws SftpException SFTP操作异常
     */
    public static void uploadFile(String ftpPath, String localFile, String remoteFilename) throws IOException, SftpException {

        if (!isConnected()) {
            throw new IllegalStateException("SFTP连接未开启或已关闭！");
        }

        File file = new File(localFile);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("本地文件不存在或不是文件: " + localFile);
        }

        try (FileInputStream inputStream = new FileInputStream(file)) {
            // 确保目录存在
            ensureDirectoryExists(ftpPath);

            // 上传文件
            ChannelSftp channelSftp = CHANNEL_SFTP.get();
            channelSftp.put(inputStream, remoteFilename);
            log.info("File {} uploaded successfully to {}/{}", localFile, ftpPath, remoteFilename);

        } catch (SftpException e) {
            log.error("SFTP error while uploading file {} to {}/{}", localFile, ftpPath, remoteFilename, e);
            throw new SftpException(e.id, "Failed to upload file: " + e.getMessage(), e);
        }
    }

    /**
     * 上传文件（自动管理连接）
     *
     * @param host           服务器地址
     * @param port           服务器端口
     * @param username       用户名
     * @param password       密码
     * @param ftpPath        服务器目标地址，文件上传目录
     * @param localFile      需要上传的本地文件
     * @param remoteFilename 远程文件名
     * @throws JSchException 连接异常
     * @throws IOException   文件操作异常
     * @throws SftpException SFTP操作异常
     */
    public static void uploadFile(String host, int port, String username, String password, String ftpPath, String localFile, String remoteFilename) throws JSchException, IOException, SftpException {

        try {
            // 开启连接
            connect(host, port, username, password);
            log.info("Connected to {}:{}", host, port);

            // 上传文件
            uploadFile(ftpPath, localFile, remoteFilename);
            log.info("File {} successfully uploaded to {}@{}:{}", localFile, username, host, ftpPath);

        } finally {
            // 确保连接关闭
            disconnect();
        }
    }

    /**
     * 下载文件
     *
     * @param remotePath     远程文件路径
     * @param remoteFilename 远程文件名
     * @param localFile      本地文件路径
     * @throws IOException   文件操作异常
     * @throws SftpException SFTP操作异常
     */
    public static void downloadFile(String remotePath, String remoteFilename, String localFile) throws IOException, SftpException {

        if (!isConnected()) {
            throw new IllegalStateException("SFTP连接未开启或已关闭！");
        }

        ChannelSftp channelSftp = CHANNEL_SFTP.get();
        try {
            channelSftp.cd(remotePath);
            channelSftp.get(remoteFilename, localFile);
            log.info("File {}/{} downloaded successfully to {}", remotePath, remoteFilename, localFile);

        } catch (SftpException e) {
            log.error("SFTP error while downloading file {}/{} to {}", remotePath, remoteFilename, localFile, e);
            throw new SftpException(e.id, "Failed to download file: " + e.getMessage(), e);
        }
    }

    /**
     * 下载文件（自动管理连接）
     *
     * @param host           服务器地址
     * @param port           服务器端口
     * @param username       用户名
     * @param password       密码
     * @param remotePath     远程文件路径
     * @param remoteFilename 远程文件名
     * @param localFile      本地文件路径
     * @throws JSchException 连接异常
     * @throws IOException   文件操作异常
     * @throws SftpException SFTP操作异常
     */
    public static void downloadFile(String host, int port, String username, String password, String remotePath, String remoteFilename, String localFile)
            throws JSchException, IOException, SftpException {

        try {
            connect(host, port, username, password);
            log.info("Connected to {}:{}", host, port);

            downloadFile(remotePath, remoteFilename, localFile);
            log.info("File {}/{} successfully downloaded from {}@{}", remotePath, remoteFilename, username, host);

        } finally {
            disconnect();
        }
    }
}
