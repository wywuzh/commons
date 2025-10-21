/*
 * Copyright 2015-2025 the original author or authors.
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

import com.jcraft.jsch.JSchException;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
 * 类JschFtpUtilsTest的实现描述：jsch FTP 文件传输工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-07-13 12:40:24
 * @version v2.6.0
 * @since JDK 1.8
 */
@Slf4j
public class JschFtpUtilsTest {

    // 上传文件
    @Test
    public void uploadFile() {
        String host = "172.20.10.8";
        int port = 50237;
        String username = "root";
        String password = "admin123456";
        String ftpPath = "/www/tools/";
        String localFile = "D:\\data\\test.txt";
        String remoteFile = "test.txt";
        try {
            JschFtpUtils.uploadFile(host, port, username, password, ftpPath, localFile, remoteFile);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // 下载文件
    @Test
    public void downloadFile() {
        // 服务器地址
        String host = "172.20.10.8";
        // 服务器端口
        int port = 50237;
        // 用户名
        String username = "root";
        // 密码
        String password = "admin123456";
        // 远程文件路径
        String remotePath = "/www/tools/";
        // 远程文件名
        String remoteFile = "test.txt";
        // 本地文件路径
        String localFile = "D:\\data\\test.txt";
        try {
            JschFtpUtils.downloadFile(host, port, username, password, remotePath, remoteFile, localFile);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
