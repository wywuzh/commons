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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

/**
 * 类AESUtilsTest的实现描述：AES加密、解密工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-02 15:28:14
 * @version v3.5.0
 * @since JDK 17
 */
@Slf4j
public class AESUtilsTest {

    public static void main(String[] args) {
        // 推荐使用GCM模式
        AESUtils.EncryptionResult result = AESUtils.encryptWithGCM("敏感数据", "强密码");
        String base64Encrypted = Base64.encodeBase64String(result.getEncryptedData());
        String base64Salt = Base64.encodeBase64String(result.getSalt());
        String base64Iv = Base64.encodeBase64String(result.getIv());

        // 解密
        try {
            AESUtils.EncryptionResult decryptResult = new AESUtils.EncryptionResult(
                    Base64.decodeBase64(base64Encrypted),
                    Base64.decodeBase64(base64Salt),
                    Base64.decodeBase64(base64Iv)
            );
            String decrypted = AESUtils.decryptWithGCM(decryptResult, "强密码");
            log.info("解密结果：{}", decrypted);
        } catch (Exception e) {
            log.error("加密、解密失败", e);
        }

        // 兼容旧版本
        try {
            String encrypted = AESUtils.encryptBase64("数据", "密码");
            String decrypted = AESUtils.decryptBase64(encrypted, "密码");
            log.info("解密结果(兼容旧版本)：{}", decrypted);
        } catch (Exception e) {
            log.error("旧版本加密、解密失败", e);
        }
    }

}
