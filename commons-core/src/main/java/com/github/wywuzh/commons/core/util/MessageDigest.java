/*
 * Copyright 2015-2021 the original author or authors.
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
package com.github.wywuzh.commons.core.util;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密算法工具类
 *
 * @author wuzh
 * @version 1.0, 05/16/2013
 * @since JDK 1.6
 */
public class MessageDigest {
    protected static final String KEY_MD5 = "MD5";
    protected static final String KEY_SHA = "SHA";
    protected static final String KEY_SHA256 = "SHA-256";
    protected static final String KEY_SHA512 = "SHA-512";
    protected static final String CHARSET_GBK = "GBK";

    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * MD5密码加密算法
     *
     * @param mess
     * @return String
     * @throws Exception
     */
    public static String getMD5(String mess) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance(KEY_MD5);
        md.update(mess.getBytes(CHARSET_UTF8));
        byte[] digest = md.digest();
        return byte2hex(digest);
    }

    /**
     * SHA密码加密算法
     *
     * @param mess
     * @return String
     * @throws Exception
     */
    public static String getSHA(String mess) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance(KEY_SHA);
        md.update(mess.getBytes(CHARSET_UTF8));
        byte[] digest = md.digest();
        return byte2hex(digest);
    }

    /**
     * 将二进制转为字符串
     *
     * @param buff
     * @return String
     */
    private static String byte2hex(byte[] buff) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buff.length; i++) {
            String hex = Integer.toHexString(buff[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex.toLowerCase());
        }
        return sb.toString();
    }

    public static String digest() {
        return null;
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            System.out.println(getMD5("000000"));
            System.out.println(DigestUtils.md5Hex("000000"));
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
