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
package io.github.wywuzh.commons.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.util.Assert;

/**
 * 类ZipUtil.java的实现描述：压缩文件工具类
 *
 * <pre>
 *  1.压缩文件：{@link #zipFile(String, String, Format)}
 *  2.解压缩文件：{@link #unZipFile(String, String)}
 * </pre>
 *
 * @author 伍章红 2015-9-24 上午9:32:38
 * @version v1.0.0
 * @since JDK 1.7.0_71
 */
public class ZipFileUtils {

    /**
     * 压缩文件格式
     *
     * @author 伍章红 2015-9-24 上午9:51:43
     * @version v1.0.0
     * @since JDK 1.7.0_71
     */
    public enum Format {
        /**
         * 最常见的压缩文件格式
         */
        ZIP(".zip"),
        /**
         * 相当多的下载网站都选择了用RAR格式来压缩他们的文件，最根本的原因就在于RAR格式的文件压缩率比ZIP更高
         */
        RAR(".rar"),
        /**
         * 作为压缩格式的后起新秀，7Z有着比RAR更高的压缩率，能够将文件压缩的更加小巧
         */
        Z7(".7z"),
        /**
         * Linux下较为常用的压缩文件的格式，并不是什么数据库文件
         */
        TAR(".tar");

        private final String name;

        private Format(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    /**
     * TODO 压缩文件。
     * <压缩文件名不需传入，默认采用filePath传入的名称。
     *
     * <pre>
     *  <code>filePath</code>支持文件和文件目录两种方式：
     *  1.文件：对单个文件进行压缩。压缩文件名为文件名,如<code>filePath</code>传入为 <strong>E:\\document\\java压缩文件.txt</strong>，则压缩文件名为 <strong>java压缩文件</strong>；
     *  2.文件目录：对指定的文件目录下的所有文件进行压缩（支持文件目录下包含多重子级文件目录）。压缩文件名为文件目录最低级文件夹名称
     * </pre>
     *
     * @author 伍章红 2015-9-24 上午9:35:04
     * @param filePath
     *                        需要压缩的文件或文件路径。如 E:\\document 或 E:\\document\\java 压缩文件.txt
     * @param zipFilePath
     *                        压缩后的文件的保存路径。如 E:\\document
     * @param format
     *                        压缩文件格式
     * @throws IOException
     */
    public static void zipFile(String filePath, String zipFilePath, Format format) throws IOException {
        Assert.notNull(filePath, "filePath must not be null");
        Assert.notNull(zipFilePath, "zipFilePath must not be null");
        Assert.notNull(format, "format must not be null");

        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        String zipFileName = null;
        String fileSuffix = filePath.substring(filePath.lastIndexOf(File.separator));

        if (file.isDirectory()) {
            long startTime = System.currentTimeMillis();
            // 组装压缩文件名称
            zipFileName = fileSuffix + format.getName();
            // 创建文件输入流对象
            BufferedInputStream inputStream = null;
            // 创建ZIP数据输出流对象
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName), Charset.defaultCharset());

            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                File destFile = listFiles[i];
                inputStream = new BufferedInputStream(new FileInputStream(destFile));
                outputStream.putNextEntry(new ZipEntry(destFile.getName()));

                int length = 0;
                while ((length = inputStream.read()) != -1) {
                    outputStream.write(length);
                }

                inputStream.close();
            }
            outputStream.close();
            long endTime = System.currentTimeMillis();
            String minute = new BigDecimal(endTime - startTime).divide(new BigDecimal(1000)).setScale(3, 5).toString();
        } else {
            // 组装压缩文件名称
            zipFileName = fileSuffix.substring(0, fileSuffix.lastIndexOf(".")) + format.getName();
            // 创建文件输入流对象
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            // 创建ZIP数据输出流对象
            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)), Charset.defaultCharset());
            // 设置压缩文件备注信息
            outputStream.setComment(fileSuffix);
            // 创建指向压缩原始文件的入口
            ZipEntry zipEntry = new ZipEntry(file.getName());
            outputStream.putNextEntry(zipEntry);
            int length = 0;
            while ((length = inputStream.read()) != -1) {
                outputStream.write(length);
            }

            outputStream.close();
            inputStream.close();
        }
    }

    public static void compress(String filePath, String zipFilePath, Format format) {
        Assert.notNull(filePath, "filePath must not be null");

        File destFile = new File(filePath);
        if (!destFile.exists()) {
            return;
        }

        try {
            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)), Charset.defaultCharset());

            compress(destFile, outputStream, "");
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compress(File destFile, ZipOutputStream outputStream, String baseDir) throws IOException {
        Assert.notNull(destFile, "destFile must not be null");
        Assert.notNull(outputStream, "outputStream must not be null");
        Assert.notNull(baseDir, "baseDir must not be null");

        if (destFile.isDirectory()) {
            compressDirectory(destFile, outputStream, baseDir);
        } else {
            compressFile(destFile, outputStream, baseDir);
        }
    }

    /**
     * 压缩文件夹
     *
     * @author wywuzh 2016年4月25日 下午5:53:22
     * @param directory
     * @param outputStream
     * @param baseDir
     * @throws IOException
     */
    public static void compressDirectory(File directory, ZipOutputStream outputStream, String baseDir) throws IOException {
        Assert.notNull(directory, "directory must not be null");
        Assert.notNull(outputStream, "outputStream must not be null");
        Assert.notNull(baseDir, "baseDir must not be null");

        if (!directory.exists()) {
            return;
        }

        File[] listFiles = directory.listFiles();
        for (File file : listFiles) {
            // 递归调用
            compress(file, outputStream, baseDir + file.getName() + "/");
        }
    }

    public static void compressFile(File file, ZipOutputStream outputStream, String baseDir) throws IOException {
        Assert.notNull(file, "file must not be null");
        Assert.notNull(outputStream, "outputStream must not be null");
        Assert.notNull(baseDir, "baseDir must not be null");

        if (!file.exists()) {
            return;
        }

        outputStream.putNextEntry(new ZipEntry(file.getName()));
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        byte[] bytes = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, length);
        }
        inputStream.close();
    }

    /**
     * TODO 解压缩文件
     *
     * @author 伍章红 2015-9-24 上午9:36:07
     * @param zipFilePath
     *                        压缩文件路径。如E:\\windows.zip
     * @param outputPath
     *                        输出文件路径。如E:\\windows
     */
    public static void unZipFile(String zipFilePath, String outputPath) {

    }

    public static void main(String[] args) {
        // String filePath
        // ="E:\\work\\schabm\\document\\技术文档\\Spring帮助文档\\Spring MVC";

        try {
            String filePath = "D:\\www\\logs\\retail-electricity-mbg";
            String zipFilePath = "D:\\";
            // 注意：zipFile方法需要指定具体文件的文件夹，不支持递归查找文件操作
            zipFile(filePath, zipFilePath, Format.ZIP);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            String filePath = "D:\\www\\logs";
//            String zipFilePath = "D:\\learning.zip";
//            // 注意：compress需要指定压缩文件名称，支持递归查找文件操作
//            compress(filePath, zipFilePath, Format.RAR);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
