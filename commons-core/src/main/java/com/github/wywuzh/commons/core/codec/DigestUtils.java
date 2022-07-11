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
package com.github.wywuzh.commons.core.codec;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * 类DigestUtils.java的实现描述：摘要信息算法工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年4月14日 下午5:38:10
 * @version v1.0.0
 * @since JDK 1.7
 */
public class DigestUtils {

    /**
     * 默认编码字符集
     */
    public static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 内容信息编码
     *
     * @param content
     *            需要编码的内容信息
     * @param algorithm
     *            算法类型
     * @return 根据指定的algorithm算法计算摘要，并返回值为32个字符的十六进制字符串
     */
    public static String encode(String content, MessageDigestAlgorithm algorithm) {
        return encode(content, DEFAULT_CHARSET, algorithm);
    }

    /**
     * 内容信息编码
     *
     * @param content
     *            需要编码的内容信息
     * @param charsetName
     *            字符集，默认为UTF-8
     * @param algorithm
     *            算法类型
     * @return 根据指定的algorithm算法计算摘要，并返回值为32个字符的十六进制字符串
     */
    public static String encode(String content, String charsetName, MessageDigestAlgorithm algorithm) {
        return encode(content,
                StringUtils.isEmpty(charsetName) ? Charset.forName(DEFAULT_CHARSET) : Charset.forName(charsetName),
                algorithm);
    }

    /**
     * 内容信息编码
     *
     * @param content
     *            需要编码的内容信息
     * @param charset
     *            字符集
     * @param algorithm
     *            算法类型
     * @return 根据指定的algorithm算法计算摘要，并返回值为32个字符的十六进制字符串
     */
    public static String encode(String content, Charset charset, MessageDigestAlgorithm algorithm) {
        if (StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException("content不能为空");
        }
        if (algorithm == null) {
            throw new IllegalArgumentException("algorithm不能为空");
        }

        // 根据指定的algorithm算法计算摘要
        MessageDigest messageDigest = getDigest(algorithm);
        // 获取内容信息的字节数组
        byte[] bytes = content.getBytes(charset == null ? Charset.forName(DEFAULT_CHARSET) : charset);
        // 使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算。
        byte[] digest = messageDigest.digest(bytes);
        // 把计算完的摘要信息转化为十六进制字符串
        return new String(Hex.encodeHex(digest, false));
    }

    /**
     * 获取信息摘要
     *
     * @param algorithm
     *            信息摘要算法
     * @return 实现指定算法的 MessageDigest 对象
     */
    public static MessageDigest getDigest(MessageDigestAlgorithm algorithm) {
        try {
            return MessageDigest.getInstance(algorithm.getValue());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
