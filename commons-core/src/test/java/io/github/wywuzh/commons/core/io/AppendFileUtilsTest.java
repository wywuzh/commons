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
package io.github.wywuzh.commons.core.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
 * 类AppendFileUtilsTest的实现描述：AppendFileUtils工具测试类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-08-31 09:45:16
 * @version v3.5.0
 * @since JDK 17
 */
@Slf4j
public class AppendFileUtilsTest {

    /**
     * 追加文件：使用BufferedWriter
     */
    @SneakyThrows
    @Test
    public void appendContentForBw() {
        String fileName = "d://text.txt";
        String content = String.format("追加文件(使用BufferedWriter)：%s\n", System.currentTimeMillis());
        AppendFileUtils.appendContentForBw(fileName, content);
    }

    /**
     * 追加文件：使用FileWriter
     */
    @SneakyThrows
    @Test
    public void appendContentForFw() {
        String fileName = "d://text.txt";
        String content = String.format("追加文件(appendContentForFw)：%s\n", System.currentTimeMillis());
        AppendFileUtils.appendContentForFw(fileName, content);
    }

    /**
     * 追加文件：使用RandomAccessFile
     */
    @SneakyThrows
    @Test
    public void appendContentForRaf() {
        String fileName = "d://text.txt";
        String content = String.format("追加文件(appendContentForRaf)：%s\n", System.currentTimeMillis());
        AppendFileUtils.appendContentForRaf(fileName, content);
    }

}
