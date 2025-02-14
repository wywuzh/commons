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
package io.github.wywuzh.commons.core.validator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.wywuzh.commons.core.validator.support.ValidateMobile;

/**
 * 类ValidateMobileTest的实现描述：手机号验证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-08-09 15:59:43
 * @version v2.4.8
 * @since JDK 1.8
 */
public class ValidateMobileTest {
    private final Logger logger = LoggerFactory.getLogger(ValidateMobileTest.class);

    @Test
    public void validateMobileType() {
        ValidateMobile validateMobile = ValidateMobile.getInstance();
        String mobileType = validateMobile.validateMobileType("18500000000");
        logger.info("手机类型：{}", mobileType);
    }

    @Test
    public void getMobileEmail() {
        ValidateMobile validateMobile = ValidateMobile.getInstance();
        String mobileType = validateMobile.getMobileEmail("18500000000");
        logger.info("获取手机号的邮箱地址：{}", mobileType);
    }

    @Test
    public void matches() {
        ValidateMobile validateMobile = ValidateMobile.getInstance();
        boolean mobileType = validateMobile.matches("18500000000");
        logger.info("验证：{}", mobileType);
    }
}
