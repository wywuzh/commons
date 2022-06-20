/*
 * Copyright 2015-2022 the original author or authors.
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
package com.wuzh.commons.core.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 类AESSecurityUtils.java的实现描述：AES加密、解密工具类
 *
 * <pre>
 * AES是美国联邦政府采用的商业及政府数据加密标准，预计将在未来几十年里代替DES在各个领域中得到广泛应用。AES提供128位密钥，因此，128位AES的加密强度是56位DES加密强度的1021倍还多。
 * 假设可以制造一部可以在1秒内破解DES密码的机器，那么使用这台机器破解一个128位AES密码需要大约149亿万年的时间。
 * （更深一步比较而言，宇宙一般被认为存在了还不到200亿年）因此可以预计，美国国家标准局倡导的AES即将作为新标准取代DES。
 * </pre>
 *
 * <pre>
 * 参考网址：
 * 1）https://www.iteye.com/blog/duanfei-1725613
 * 2）http://blog.sina.com.cn/s/blog_51a7b40e0100xn2l.html
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年4月14日 下午6:16:08
 * @version v1.0.0
 * @since JDK 1.7
 */
public class AESSecurityUtils {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER = "AES/ECB/PKCS5Padding";
    private static final String IV_PARAMETER = "****************";

    /**
     * 默认字符集
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //2.初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
        keyGenerator.init(128, new SecureRandom(password.getBytes(CHARSET_NAME)));
        //3.生成对称密钥
        SecretKey secretKey = keyGenerator.generateKey();

        //6.根据AES算法指定密码器
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // 创建密码器
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(content.getBytes(CHARSET_NAME));
    }

    public static String encryptBase64(String content, String password) throws Exception {
        return new String(Base64.encodeBase64(encrypt(content, password)));
    }

    public static String encryptHex(String content, String password) throws Exception {
        return new String(Hex.encodeHex(encrypt(content, password)));
    }

    /**
     * 解密
     *
     * @param encrypt  加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] decrypt(byte[] encrypt, String password) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //2.初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
        keyGenerator.init(128, new SecureRandom(password.getBytes(CHARSET_NAME)));
        //3.生成对称密钥
        SecretKey secretKey = keyGenerator.generateKey();

        //6.根据AES算法指定密码器
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // 解密
        Cipher decryptCipher = Cipher.getInstance(CIPHER);
        decryptCipher.init(Cipher.DECRYPT_MODE, keySpec);
        // 信息解密时，将数据进行解码
        return decryptCipher.doFinal(encrypt);
    }

    public static String decryptBase64(String content, String password) throws Exception {
        return new String(decrypt(Base64.decodeBase64(content), password));
    }

    public static String decryptHex(String content, String password) throws Exception {
        return new String(decrypt(Hex.decodeHex(content), password));
    }

    public static void main(String[] args) {
        String content = "public";
        String password = "admin";

        try {
            // 生成密钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom(password.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey generateKey = keyGenerator.generateKey();
            byte[] encoded = generateKey.getEncoded();
            // 注意：应该将securitySalt值给到用户
            String securitySalt = new String(Base64.encodeBase64(encoded));

            // 用KeyGenerator生成的SecretKey值来SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, ALGORITHM);

            // 加密
            Cipher encryptCipher = Cipher.getInstance(CIPHER);
            IvParameterSpec ivParameter = new IvParameterSpec(IV_PARAMETER.getBytes());
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec/*, ivParameter*/);
            byte[] encrypt = encryptCipher.doFinal(content.getBytes(CHARSET_NAME));
            // 信息加密时，将数据进行编码
            String encodeBase64 = new String(Base64.encodeBase64(encrypt));
            System.out.println("加密后的值：" + encodeBase64);

            // 解密
            Cipher decryptCipher = Cipher.getInstance(CIPHER);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec/*, ivParameter*/);
            // 信息解密时，将数据进行解码
            byte[] decrypt = decryptCipher.doFinal(Base64.decodeBase64(encodeBase64));
            System.out.println("解密后的值" + new String(decrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
