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

/**
 * 类MessageDigestAlgorithm.java的实现描述：信息摘要算法算法类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年4月14日 下午5:39:08
 * @version v1.0.0
 * @since JDK 1.7
 */
public enum MessageDigestAlgorithm {

    MD2("MD2"), MD5("MD5"), SHA_1("SHA-1"), SHA_256("SHA-256"), SHA_384("SHA-384"), SHA_512("SHA-512");

    /**
     * 算法类型
     */
    private String value;

    private MessageDigestAlgorithm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MessageDigestAlgorithm findByValue(String value) {
        for (MessageDigestAlgorithm algorithm : values()) {
            if (algorithm.value.equals(value)) {
                return algorithm;
            }
        }
        return null;
    }

}
