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

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类MessageDigestAlgorithm.java的实现描述：信息摘要算法算法类型。支持常见的消息摘要算法，包括MD系列和SHA系列
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年4月14日 下午5:39:08
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum MessageDigestAlgorithm {

    /**
     * MD2 算法（不推荐使用，安全性较低）
     */
    MD2("MD2"),

    /**
     * MD5 算法（不推荐用于安全场景，存在碰撞风险）
     */
    MD5("MD5"),

    /**
     * SHA-1 算法（不推荐用于安全场景，存在碰撞风险）
     */
    SHA_1("SHA-1"),

    /**
     * SHA-256 算法（推荐用于一般安全场景）
     */
    SHA_256("SHA-256"),

    /**
     * SHA-384 算法（推荐用于高安全场景）
     */
    SHA_384("SHA-384"),

    /**
     * SHA-512 算法（推荐用于高安全场景）
     */
    SHA_512("SHA-512"),

    /**
     * SHA3-256 算法（SHA-3系列，推荐用于新项目）
     */
    SHA3_256("SHA3-256"),

    /**
     * SHA3-512 算法（SHA-3系列，推荐用于新项目）
     */
    SHA3_512("SHA3-512");

    /**
     * 算法类型值
     */
    private final String value;

    /**
     * 值到枚举的缓存映射，提高查找性能
     */
    private static final Map<String, MessageDigestAlgorithm> VALUE_CACHE = new ConcurrentHashMap<>();

    // 初始化缓存
    static {
        Arrays.stream(values()).forEach(algorithm ->
                VALUE_CACHE.put(algorithm.value, algorithm)
        );
    }

    /**
     * 构造函数
     *
     * @param value 算法字符串值
     */
    MessageDigestAlgorithm(String value) {
        this.value = value;
    }

    /**
     * 获取算法字符串值
     *
     * @return 算法字符串值
     */
    public String getValue() {
        return value;
    }

    /**
     * 根据算法字符串值查找对应的枚举实例
     *
     * @param value 算法字符串值
     * @return 对应的枚举实例，如果未找到返回null
     */
    public static MessageDigestAlgorithm findByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return VALUE_CACHE.get(value.trim());
    }

    /**
     * 根据算法字符串值查找对应的枚举实例（安全版本）
     *
     * @param value 算法字符串值
     * @return 对应的枚举实例
     * @throws IllegalArgumentException 如果未找到对应的算法
     */
    public static MessageDigestAlgorithm findByValueSafe(String value) {
        MessageDigestAlgorithm algorithm = findByValue(value);
        if (algorithm == null) {
            throw new IllegalArgumentException("不支持的摘要算法: " + value);
        }
        return algorithm;
    }

    /**
     * 检查算法值是否被支持
     *
     * @param value 算法字符串值
     * @return 如果支持返回true，否则返回false
     */
    public static boolean isSupported(String value) {
        return findByValue(value) != null;
    }

    /**
     * 获取所有支持的算法值数组
     *
     * @return 算法值数组
     */
    public static String[] getSupportedAlgorithms() {
        return Arrays.stream(values())
                .map(MessageDigestAlgorithm::getValue)
                .toArray(String[]::new);
    }

    /**
     * 判断算法是否属于MD系列
     *
     * @return 如果是MD系列算法返回true
     */
    public boolean isMDSeries() {
        return this.name().startsWith("MD");
    }

    /**
     * 判断算法是否属于SHA系列
     *
     * @return 如果是SHA系列算法返回true
     */
    public boolean isSHASeries() {
        return this.name().startsWith("SHA");
    }

    /**
     * 判断算法是否属于SHA3系列
     *
     * @return 如果是SHA3系列算法返回true
     */
    public boolean isSHA3Series() {
        return this.name().startsWith("SHA3");
    }

    /**
     * 判断算法是否安全（MD2、MD5、SHA-1被认为不安全）
     *
     * @return 如果算法安全返回true
     */
    public boolean isSecure() {
        return this != MD2 && this != MD5 && this != SHA_1;
    }

    /**
     * 获取算法推荐的用途描述
     *
     * @return 用途描述
     */
    public String getRecommendedUsage() {
        switch (this) {
            case MD2:
            case MD5:
                return "不推荐使用，仅用于兼容旧系统";
            case SHA_1:
                return "不推荐用于安全场景，可用于校验和数据完整性检查";
            case SHA_256:
                return "推荐用于一般安全场景，如密码哈希、数据完整性验证";
            case SHA_384:
            case SHA_512:
                return "推荐用于高安全场景，如数字签名、证书";
            case SHA3_256:
            case SHA3_512:
                return "推荐用于新项目，抗碰撞性更强";
            default:
                return "通用摘要算法";
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
