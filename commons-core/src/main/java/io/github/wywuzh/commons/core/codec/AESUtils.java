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

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类AESUtils的实现描述：AES加密、解密工具类
 *
 * <pre>
 * AES是美国联邦政府采用的商业及政府数据加密标准，预计将在未来几十年里代替DES在各个领域中得到广泛应用。
 * 本工具类支持AES-GCM和AES-CBC模式，推荐使用更安全的AES-GCM模式。
 * </pre>
 *
 * <pre>
 * 安全说明：
 * 1. 使用PBKDF2密钥派生函数从密码生成密钥，避免弱密钥问题
 * 2. 支持GCM模式提供认证加密，防止密文被篡改
 * 3. 每次加密生成随机盐和IV，提高安全性
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-02 15:22:38
 * @version v3.5.0
 * @since JDK 17
 */
public class AESUtils {
    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    // 算法常量
    public static final String ALGORITHM = "AES";
    public static final String GCM_CIPHER = "AES/GCM/NoPadding";
    public static final String CBC_CIPHER = "AES/CBC/PKCS5Padding";
    public static final String ECB_CIPHER = "AES/ECB/PKCS5Padding"; // 不推荐使用

    // 密钥相关常量
    public static final int AES_128_KEY_SIZE = 128;
    public static final int AES_192_KEY_SIZE = 192;
    public static final int AES_256_KEY_SIZE = 256;
    public static final int DEFAULT_KEY_SIZE = AES_128_KEY_SIZE;

    // GCM模式参数
    public static final int GCM_TAG_LENGTH = 128; // bits
    public static final int GCM_IV_LENGTH = 12;   // bytes (推荐值)

    // PBKDF2参数
    public static final int PBKDF2_ITERATIONS = 10000;
    public static final int SALT_LENGTH = 16;     // bytes

    // 字符集
    public static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    /**
     * 加密结果容器
     */
    public static class EncryptionResult {
        private final byte[] encryptedData;
        private final byte[] salt;
        private final byte[] iv;

        public EncryptionResult(byte[] encryptedData, byte[] salt, byte[] iv) {
            this.encryptedData = encryptedData;
            this.salt = salt;
            this.iv = iv;
        }

        // getter方法
        public byte[] getEncryptedData() {
            return encryptedData;
        }

        public byte[] getSalt() {
            return salt;
        }

        public byte[] getIv() {
            return iv;
        }
    }

    /**
     * 使用GCM模式加密（推荐）
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return 加密结果容器
     */
    public static EncryptionResult encryptWithGCM(String content, String password) throws SecurityException {
        validateInput(content, password);

        try {
            // 生成随机盐和IV
            byte[] salt = generateRandomBytes(SALT_LENGTH);
            byte[] iv = generateRandomBytes(GCM_IV_LENGTH);

            // 从密码派生密钥
            SecretKey secretKey = deriveKey(password, salt, DEFAULT_KEY_SIZE);

            // 初始化加密器
            Cipher cipher = Cipher.getInstance(GCM_CIPHER);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            // 执行加密
            byte[] encryptedData = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));

            return new EncryptionResult(encryptedData, salt, iv);

        } catch (Exception e) {
            logger.error("content={}, password={} AES-GCM加密失败", content, password, e);
            throw new SecurityException("加密操作失败", e);
        }
    }

    /**
     * 使用GCM模式解密
     *
     * @param result   加密结果
     * @param password 加密密码
     * @return 解密后的原文
     */
    public static String decryptWithGCM(EncryptionResult result, String password) throws SecurityException {
        validateDecryptionInput(result, password);

        try {
            SecretKey secretKey = deriveKey(password, result.getSalt(), DEFAULT_KEY_SIZE);

            Cipher cipher = Cipher.getInstance(GCM_CIPHER);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, result.getIv());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            byte[] decryptedData = cipher.doFinal(result.getEncryptedData());
            return new String(decryptedData, DEFAULT_CHARSET);

        } catch (Exception e) {
            logger.error("result={}, password={} AES-GCM解密失败", result, password, e);
            throw new SecurityException("解密操作失败", e);
        }
    }

    /**
     * 兼容旧版本的加密方法（不推荐使用）
     */
    public static byte[] encrypt(String content, String password) throws SecurityException {
        validateInput(content, password);

        try {
            // 生成固定盐（为了兼容性）
            byte[] salt = "FixedSaltForCompat".getBytes(DEFAULT_CHARSET);
            SecretKey secretKey = deriveKey(password, salt, AES_128_KEY_SIZE);

            Cipher cipher = Cipher.getInstance(ECB_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(content.getBytes(DEFAULT_CHARSET));

        } catch (Exception e) {
            logger.error("content={}, password={} AES加密失败", content, password, e);
            throw new SecurityException("加密操作失败", e);
        }
    }

    /**
     * 兼容旧版本的解密方法（不推荐使用）
     */
    public static String decrypt(byte[] encryptedData, String password) throws SecurityException {
        if (encryptedData == null || encryptedData.length == 0) {
            throw new IllegalArgumentException("加密数据不能为空");
        }
        validatePassword(password);

        try {
            // 使用相同的固定盐
            byte[] salt = "FixedSaltForCompat".getBytes(DEFAULT_CHARSET);
            SecretKey secretKey = deriveKey(password, salt, AES_128_KEY_SIZE);

            Cipher cipher = Cipher.getInstance(ECB_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData, DEFAULT_CHARSET);

        } catch (Exception e) {
            logger.error("encryptedData={}, password={} AES解密失败", encryptedData, password, e);
            throw new SecurityException("解密操作失败", e);
        }
    }

    // Base64编码的便捷方法
    public static String encryptBase64(String content, String password) throws SecurityException {
        byte[] encrypted = encrypt(content, password);
        return Base64.encodeBase64String(encrypted);
    }

    public static String decryptBase64(String content, String password) throws SecurityException {
        byte[] decoded = Base64.decodeBase64(content);
        return decrypt(decoded, password);
    }

    // Hex编码的便捷方法
    public static String encryptHex(String content, String password) throws SecurityException {
        byte[] encrypted = encrypt(content, password);
        return Hex.encodeHexString(encrypted);
    }

    public static String decryptHex(String content, String password) throws SecurityException {
        try {
            byte[] decoded = Hex.decodeHex(content);
            return decrypt(decoded, password);
        } catch (Exception e) {
            throw new SecurityException("Hex解码失败", e);
        }
    }

    /**
     * 使用PBKDF2从密码派生密钥
     */
    private static SecretKey deriveKey(String password, byte[] salt, int keySize) throws Exception {
        javax.crypto.spec.PBEKeySpec spec = new javax.crypto.spec.PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, keySize);
        javax.crypto.SecretKeyFactory factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * 生成随机字节数组
     */
    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    // 输入验证方法
    private static void validateInput(String content, String password) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("加密内容不能为空");
        }
        validatePassword(password);
    }

    private static void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("加密密码不能为空");
        }
        if (password.length() < 8) {
            logger.warn("加密密码长度过短，建议使用至少8位密码");
        }
    }

    private static void validateDecryptionInput(EncryptionResult result, String password) {
        if (result == null || result.getEncryptedData() == null || result.getSalt() == null || result.getIv() == null) {
            throw new IllegalArgumentException("解密参数不完整");
        }
        validatePassword(password);
    }

    /**
     * 安全清空字节数组内容
     */
    public static void clearSensitiveData(byte[] sensitiveData) {
        if (sensitiveData != null) {
            Arrays.fill(sensitiveData, (byte) 0);
        }
    }

    /**
     * 安全清空字符数组内容
     */
    public static void clearSensitiveData(char[] sensitiveData) {
        if (sensitiveData != null) {
            Arrays.fill(sensitiveData, '\0');
        }
    }
}
