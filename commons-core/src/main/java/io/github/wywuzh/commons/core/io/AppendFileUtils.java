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
package io.github.wywuzh.commons.core.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

import io.github.wywuzh.commons.core.util.Assert;

/**
 * 类AppendFile.java的实现描述：java IO追加文件的三种方式
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:44:17
 * @version v1.0.0
 * @since JDK 1.7
 */
@Slf4j
public class AppendFileUtils {

    /**
     * 追加文件：使用BufferedWriter方式
     *
     * @param fileName 文件完整路径名
     * @param content  内容
     */
    public static void appendContentForBw(String fileName, String content) throws IOException {
        Assert.notBlank(fileName, "参数fileName不能为空");
        Assert.notNull(content, "参数content不能为空");

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), StandardCharsets.UTF_8))) {
            out.write(content);
            // 确保内容写入磁盘（通常close()会自动flush）
            out.flush();
        } catch (IOException e) {
            log.error("fileName={} 使用BufferedWriter方式追加文件失败", fileName, e);
            throw e;
        }
    }

    /**
     * 追加文件：使用FileWriter方式。如果需要明确控制字符编码，请使用 {@link #appendContentForBw(String, String)} 方法
     *
     * @param fileName 文件完整路径名
     * @param content  内容
     * @see #appendContentForBw(String, String) 使用BufferedWriter方式追加文件内容
     */
    public static void appendContentForFw(String fileName, String content) throws IOException {
        Assert.notBlank(fileName, "参数fileName不能为空");
        Assert.notNull(content, "参数content不能为空");

        // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content);
        } catch (IOException e) {
            log.error("fileName={} 使用FileWriter方式追加文件失败", fileName, e);
            throw e;
        }
    }

    /**
     * 追加文件：使用RandomAccessFile方式
     *
     * <pre>
     * RandomAccessFile的优点/缺点/使用场景
     * 优点：
     *   1. 可以随机访问文件任意位置
     *   2. 适合大文件操作（可以定位到特定位置）
     *   3. 同时支持读写操作
     * 缺点：
     *   1. 代码相对复杂
     *   2. 需要手动处理字节转换
     *   3. 性能可能不如BufferedWriter（缺少缓冲）
     * 使用场景建议：
     *   推荐使用：需要随机访问文件不同位置时
     *   不推荐用于纯追加场景：如果只是简单追加内容，{@link #appendContentForBw(String, String) BufferedWriter方式} 或 {@link #appendContentForFw(String, String) FileWriter方式} 更简洁高效
     * </pre>
     *
     * @param fileName 文件完整路径名
     * @param content  追加的内容
     */
    public static void appendContentForRaf(String fileName, String content) throws IOException {
        Assert.notBlank(fileName, "参数fileName不能为空");
        Assert.notNull(content, "参数content不能为空");

        // 打开一个随机访问文件流，按读写方式
        try (RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw")) {
            // 将写文件指针移到文件尾。文件长度，字节数
            randomFile.seek(randomFile.length());
            // 使用明确编码写入内容，避免乱码问题
            randomFile.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("fileName={} 使用RandomAccessFile方式追加文件失败：", fileName, e);
            throw e;
        }
    }

}
