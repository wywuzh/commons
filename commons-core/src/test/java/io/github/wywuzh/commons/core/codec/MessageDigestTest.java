package io.github.wywuzh.commons.core.codec;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * 类MessageDigestTest的实现描述：加密算法工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-01-18 14:43:34
 * @version v3.3.0
 * @since JDK 1.8
 */
public class MessageDigestTest {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            System.out.println(MessageDigest.getMD5("000000"));
            System.out.println(org.apache.commons.codec.digest.DigestUtils.md5Hex("000000"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom secure = new SecureRandom();
            secure.setSeed("123456".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(DigestUtils.sha1Hex("admin123456"));
    }
}
