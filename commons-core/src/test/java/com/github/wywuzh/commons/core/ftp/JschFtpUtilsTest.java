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
package com.github.wywuzh.commons.core.ftp;

import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * 类JschFtpUtilsTest的实现描述：jsch FTP 文件传输工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-07-13 12:40:24
 * @version v2.6.0
 * @since JDK 1.8
 */
@Slf4j
public class JschFtpUtilsTest {

    @Test
    public void uploadFile() {
        String host = "10.0.43.32";
        int port = 60777;
        String username = "root";
        String password = "12345678";
        String path = "/www/tools/";
        String localFile = "D:\\test.txt";
        String remoteFile = "test.txt";
        try {
            JschFtpUtils.uploadFile(host, port, username, password, path, localFile, remoteFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (JSchException e) {
            log.error(e.getMessage(), e);
        }
    }

}
