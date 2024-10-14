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
package io.github.wywuzh.commons.core.codec;

import java.security.SecureRandom;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;

import cn.hutool.crypto.SecureUtil;

/**
 * 类AESSecurityUtilsTest的实现描述：AES加密、解密工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-05-13 16:03:22
 * @version v2.5.2
 * @since JDK 1.8
 */
@Slf4j
public class AESSecurityUtilsTest {

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) throws Exception {
        // 1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // 2.初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
        keyGenerator.init(128, new SecureRandom(password.getBytes("UTF-8")));
        // 3.生成对称密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 6.根据AES算法指定密码器
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // AES/ECB/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    public static void main(String[] args) throws Exception {
        String content = "public";
        String password = "admin";
        byte[] encrypt = AESSecurityUtils.encrypt(content, password);
        String secretKey = new String(Base64.encodeBase64(encrypt));
        log.info("Base64加密结果：{}", secretKey);

        String encryptBase64 = AESSecurityUtils.encryptBase64(content, password);
        log.info("Base64加密结果：{}", encryptBase64);
        log.info("Base64解密结果：{}", AESSecurityUtils.decryptBase64(encryptBase64, password));

        String encryptHex = AESSecurityUtils.encryptHex(content, password);
        log.info("Hex加密结果：{}", encryptHex);
        log.info("Base64解密结果：{}", AESSecurityUtils.decryptHex(encryptHex, password));

        String base64 = SecureUtil.aes(password.getBytes("UTF-8")).encryptBase64(content);
        log.info("SecureUtil.aes加密结果：{}", base64);
    }

}
