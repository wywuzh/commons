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
package io.github.wywuzh.commons.core.codec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import io.github.wywuzh.commons.core.common.CharacterSet;
import io.github.wywuzh.commons.core.util.Assert;

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
    public static String DEFAULT_CHARSET_NAME = CharacterSet.UTF_8;

    /**
     * 默认字符集对象
     *
     * @since v3.5.0
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * MessageDigest 实例缓存，提高性能
     *
     * @since v3.5.0
     */
    private static final ConcurrentMap<String, MessageDigest> DIGEST_CACHE = new ConcurrentHashMap<>();

    /**
     * 私有构造方法，防止实例化
     *
     * @since v3.5.0
     */
    private DigestUtils() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }

    /**
     * 内容信息编码
     *
     * @param content   需要编码的内容信息，不能为空
     * @param algorithm 算法类型，不能为空
     * @return 根据指定的algorithm算法计算摘要，并返回十六进制字符串
     * @throws IllegalArgumentException 如果参数为空或算法不支持
     */
    public static String encode(String content, MessageDigestAlgorithm algorithm) {
        return encode(content, DEFAULT_CHARSET_NAME, algorithm);
    }

    /**
     * 内容信息编码
     *
     * @param content     需要编码的内容信息，不能为空
     * @param charsetName 字符集，如果为空则使用默认UTF-8
     * @param algorithm   算法类型，不能为空
     * @return 根据指定的algorithm算法计算摘要，并返回十六进制字符串
     * @throws IllegalArgumentException 如果参数为空或算法不支持
     */
    public static String encode(String content, String charsetName, MessageDigestAlgorithm algorithm) {
        Assert.notBlank(content, "内容不能为空");
        Assert.notNull(algorithm, "算法类型不能为空");

        Charset charset = StringUtils.isEmpty(charsetName) ? DEFAULT_CHARSET : Charset.forName(charsetName);
        return encode(content, charset, algorithm);
    }

    /**
     * 内容信息编码
     *
     * @param content   需要编码的内容信息，不能为空
     * @param charset   字符集，如果为空则使用默认UTF-8
     * @param algorithm 算法类型，不能为空
     * @return 根据指定的algorithm算法计算摘要，并返回十六进制字符串
     * @throws IllegalArgumentException 如果参数为空或算法不支持
     */
    public static String encode(String content, Charset charset, MessageDigestAlgorithm algorithm) {
        Assert.notBlank(content, "内容不能为空");
        Assert.notNull(algorithm, "算法类型不能为空");

        Charset actualCharset = charset != null ? charset : DEFAULT_CHARSET;
        byte[] data = content.getBytes(actualCharset);

        return encode(data, algorithm);
    }

    /**
     * 字节数组编码
     *
     * @param data      需要编码的字节数组，不能为空
     * @param algorithm 算法类型，不能为空
     * @return 根据指定的algorithm算法计算摘要，并返回十六进制字符串
     * @throws IllegalArgumentException 如果参数为空或算法不支持
     * @since v3.5.0
     */
    public static String encode(byte[] data, MessageDigestAlgorithm algorithm) {
        Assert.notEmpty(data, "数据字节数组不能为空");
        Assert.notNull(algorithm, "算法类型不能为空");

        MessageDigest messageDigest = getDigest(algorithm);
        return encodeWithLock(messageDigest, data);
    }

    /**
     * 获取信息摘要实例
     *
     * @param algorithm 信息摘要算法
     * @return 实现指定算法的 MessageDigest 对象
     * @throws IllegalArgumentException 如果算法不支持
     */
    public static MessageDigest getDigest(MessageDigestAlgorithm algorithm) {
        Assert.notNull(algorithm, "算法类型不能为null");

        return DIGEST_CACHE.computeIfAbsent(algorithm.getValue(), algo -> {
            try {
                MessageDigest digest = MessageDigest.getInstance(algo);
                // 初始化一次，避免首次使用的性能问题
                digest.reset();
                return digest;
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException("不支持的摘要算法: " + algo, e);
            }
        });
    }

    /**
     * 带锁的编码方法，确保线程安全
     *
     * @since v3.5.0
     */
    private static String encodeWithLock(MessageDigest messageDigest, byte[] data) {
        synchronized (messageDigest) {
            messageDigest.reset(); // 重置状态，确保每次计算都是独立的
            byte[] digest = messageDigest.digest(data);
            return Hex.encodeHexString(digest);
        }
    }

    /**
     * 快速MD5编码（便捷方法）
     *
     * @param content 需要编码的内容
     * @return MD5摘要的十六进制字符串
     * @since v3.5.0
     */
    public static String md5(String content) {
        return encode(content, MessageDigestAlgorithm.MD5);
    }

    /**
     * 快速SHA-1编码（便捷方法）
     *
     * @param content 需要编码的内容
     * @return SHA-1摘要的十六进制字符串
     * @since v3.5.0
     */
    public static String sha1(String content) {
        return encode(content, MessageDigestAlgorithm.SHA_1);
    }

    /**
     * 快速SHA-256编码（便捷方法）
     *
     * @param content 需要编码的内容
     * @return SHA-256摘要的十六进制字符串
     * @since v3.5.0
     */
    public static String sha256(String content) {
        return encode(content, MessageDigestAlgorithm.SHA_256);
    }

    /**
     * 快速SHA-512编码（便捷方法）
     *
     * @param content 需要编码的内容
     * @return SHA-512摘要的十六进制字符串
     * @since v3.5.0
     */
    public static String sha512(String content) {
        return encode(content, MessageDigestAlgorithm.SHA_512);
    }

    /**
     * 清理缓存（主要用于测试或内存敏感场景）
     *
     * @since v3.5.0
     */
    public static void clearCache() {
        DIGEST_CACHE.clear();
    }

    /**
     * 获取当前缓存的算法数量
     *
     * @since v3.5.0
     */
    public static int getCacheSize() {
        return DIGEST_CACHE.size();
    }

    /**
     * 检查算法是否支持
     *
     * @param algorithm 算法名称
     * @return 是否支持该算法
     * @since v3.5.0
     */
    public static boolean isAlgorithmSupported(String algorithm) {
        if (StringUtils.isBlank(algorithm)) {
            return false;
        }
        try {
            MessageDigest.getInstance(algorithm);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

}
