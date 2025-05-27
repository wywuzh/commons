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
package io.github.wywuzh.commons.core.sequence;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
 * 类CodeUtilTest的实现描述：code编码工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-03-04 11:38:30
 * @version v3.3.0
 * @since JDK 17
 */
@Slf4j
public class CodeUtilTest {

    @Test
    public void nextCode() {
        log.info(CodeUtil.nextCode(null, 2, null));
        log.info(CodeUtil.nextCode(null, 2, "HC"));

        String firstCode = CodeUtil.nextCode(null, 2, "HC");
        log.info("firstCode={}", firstCode);
        String secondCode = CodeUtil.nextCode(firstCode, 2, "HC");
        log.info("secondCode={}", secondCode);
    }

    public static void main(String[] args) {
        String code = "A000000000001";
        System.out.println(code.length());

        // String substring = code.substring(1);
        // System.out.println(substring);
        // String c = code.substring(0, 1);
        // char[] charArray = c.toCharArray();
        // char cc = charArray[0];
        // System.out.println((char) (cc + 1));

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            code = CodeUtil.nextCode(code);
            System.out.println(code);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(new BigDecimal(endTime - startTime).divide(new BigDecimal(1000)).setScale(3, 0).toString());

        System.out.println(CodeUtil.firstCode());
        System.out.println(CodeUtil.firstCode(3));
    }

}
