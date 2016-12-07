/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.util.Assert;

/**
 * 类FileUtils.java的实现描述：文件工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:43:49
 * @version v1.0.0
 * @since JDK 1.7
 */
public class FileUtils {

    /**
     * 获取文件属性信息
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static FileAttributes getAttributes(File file) throws IOException {
        Assert.notNull(file, "file must not be null");

        return getAttributes(file.toPath());
    }

    /**
     * 获取文件属性信息
     * 
     * @param path
     * @return
     * @throws IOException
     */
    public static FileAttributes getAttributes(Path path) throws IOException {
        Assert.notNull(path, "path must not be null");

        File file = path.toFile();
        Assert.notNull(file, "找不到此文件信息");

        FileAttributes fileAttributes = new FileAttributes();

        // 文件名
        fileAttributes.setName(file.getName());
        // 文件路径
        fileAttributes.setPath(file.getAbsolutePath());
        // 文件最后修改时间
        fileAttributes.setLastModifiedTime(file.lastModified());
        // 文件大小
        fileAttributes.setSize(file.length());
        // 是否可读
        fileAttributes.setCanRead(file.canRead());
        // 是否可写
        fileAttributes.setCanWrite(file.canWrite());
        // 是否可操作
        fileAttributes.setCanExecute(file.canExecute());
        // 是否隐藏
        fileAttributes.setHidden(file.isHidden());

        BasicFileAttributes attributes = Files
                .getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).readAttributes();
        // 文件创建时间
        fileAttributes.setCreationTime(attributes.creationTime().toMillis());
        // 文件最后访问时间
        fileAttributes.setLastAccessTime(attributes.lastAccessTime().toMillis());

        return fileAttributes;
    }

    /**
     * 获取文件属性信息
     * 
     * @param uri
     * @return
     * @throws IOException
     */
    public static FileAttributes getAttributes(URI uri) throws IOException {
        Assert.notNull(uri, "uri must not be null");

        return getAttributes(new File(uri));
    }

    /**
     * 读取文件信息
     * 
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        Assert.notNull(fileName, "fileName must not be null");

        return read(new File(fileName));
    }

    /**
     * 读取文件信息
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static String read(File file) throws IOException {
        Assert.notNull(file, "file must not be null");

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        StringBuilder content = new StringBuilder();
        byte[] bs = new byte[2048];
        int length = 0;
        while ((length = inputStream.read(bs)) != -1) {
            content.append(new String(bs, 0, length));
            content.append("\n");
        }
        return content.toString();
    }

    /**
     * 读取文件信息
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String read(Path path) throws IOException {
        Assert.notNull(path, "path must not be null");

        return read(path.toFile());
    }

    /**
     * 删除文件
     * 
     * @author 伍章红 2016年4月7日 上午12:39:33
     * @param file
     * @return
     */
    public static boolean delete(File file) {
        Assert.notNull(file, "file must not be null");

        return file.delete();
    }

    /**
     * 删除文件
     * 
     * @author 伍章红 2016年4月7日 上午12:39:50
     * @param fileName
     * @return
     */
    public static boolean delete(String fileName) {
        Assert.notNull(fileName, "fileName must not be null");

        return delete(new File(fileName));
    }

    /**
     * 删除文件
     * 
     * @author 伍章红 2016年4月7日 上午12:39:52
     * @param filePath
     * @return
     */
    public static boolean delete(Path filePath) {
        Assert.notNull(filePath, "filePath must not be null");

        return delete(filePath.toFile());
    }

    /**
     * 将content内容写入到的destFile文件中
     * 
     * @author 伍章红 2016年4月7日 下午8:50:38
     * @param destFile
     * @param content
     */
    public static void writer(File destFile, String content) {
        Assert.notNull(destFile, "destFile must not be null");
        Assert.notNull(content, "content must not be null");

        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(destFile));
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * TODO 将content内容追加到的destFile文件中
     * 
     * @author 伍章红 2016年4月7日 下午9:38:13
     * @param destFile
     * @param content
     */
    public static void append(File destFile, String content) {
        Assert.notNull(destFile, "destFile must not be null");
        Assert.notNull(content, "content must not be null");
    }

    public static void main(String[] args) {
        // try {
        // String read = read("F:\\work\\frame\\document\\html\\scripts\\util\\easyui-util.js");
        // System.out.println(read);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // delete("F://bank.txt");
        writer(new File("F://bank.txt"), "eeeeeeeeee===========ppp=============eee");
    }
}
