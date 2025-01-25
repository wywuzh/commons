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

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;

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
