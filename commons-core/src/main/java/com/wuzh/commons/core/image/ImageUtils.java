/*
 * Copyright 2015-2017 the original author or authors.
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
package com.wuzh.commons.core.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 类ImageUtils.java的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年5月2日 上午9:56:27
 * @version 3.2.17
 * @since JDK 1.7
 */
public class ImageUtils {

    private static final String FORMAT_NAME = "jpg";

    /**
     * 图片缩放，采用ImageIO形式对图片流进行输出
     *
     * @param sourceFile
     *            图片源地址
     * @param destinationFile
     *            压缩完图片的地址
     * @param width
     *            缩放后的宽度
     * @param height
     *            缩放后的高度
     * @return 返回图片缩放后的目录地址
     */
    public static String scaleImage(File sourceFile, File destinationFile, int width, int height) {
        // 当源文件为空或者源文件不存在时
        if (sourceFile == null || destinationFile == null || !sourceFile.exists()) {
            return null;
        }
        // 取得目标图片文件夹信息
        File parentFile = new File(destinationFile.getParent());
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            BufferedImage image = ImageIO.read(sourceFile);

            BufferedImage outputImage = new BufferedImage(width, height, Image.SCALE_DEFAULT);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image.getScaledInstance(width, height, Image.SCALE_DEFAULT), 0, 0, null);
            graphics.dispose();
            // 使用ImageIO对图片进行输出
            if (ImageIO.write(outputImage, FORMAT_NAME, destinationFile)) {
                return destinationFile.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片缩放，采用ImageIO形式对图片流进行输出
     *
     * @param sourceFile
     *            图片源地址
     * @param destinationFile
     *            压缩完图片的地址
     * @param rate
     *            图片压缩比例，如果指定比例为空或0，则取原图
     * @return 返回图片缩放后的目录地址
     */
    public static String scaleImage(File sourceFile, File destinationFile, Float rate) {
        // 当源文件为空或者源文件不存在时
        if (sourceFile == null || destinationFile == null || !sourceFile.exists()) {
            return null;
        }
        // 取得目标图片文件夹信息
        File parentFile = new File(destinationFile.getParent());
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            BufferedImage image = ImageIO.read(sourceFile);
            // 图片宽度
            int width = image.getWidth();
            // 图片高度
            int height = image.getHeight();
            // 当缩放比例不为空时，根据原图进行等比例缩放
            if (rate != null) {
                // 指定图片宽度
                width = (int) (width * rate);
                // 指定图片高度
                height = (int) (height * rate);
            }

            BufferedImage outputImage = new BufferedImage(width, height, Image.SCALE_DEFAULT);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image.getScaledInstance(width, height, Image.SCALE_DEFAULT), 0, 0, null);
            // 释放资源
            graphics.dispose();
            // 使用ImageIO对图片进行输出
            if (ImageIO.write(outputImage, FORMAT_NAME, destinationFile)) {
                return destinationFile.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
