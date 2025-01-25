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
package io.github.wywuzh.commons.core.validator.support;

import io.github.wywuzh.commons.core.validator.PatternType;
import io.github.wywuzh.commons.core.validator.Validate;

/**
 * 类ValidatePhone.java的实现描述：联系电话验证
 *
 * @author 伍章红 2015年11月26日 下午10:55:50
 * @version v1.0.0
 * @since JDK 1.7
 */
public class ValidatePhone extends Validate {

    @Override
    protected PatternType getPatternType() {
        return PatternType.PATTERN_PHONE;
    }

    public static void main(String[] args) {
        Validate validate = new ValidatePhone();
        System.out.println(validate.matches("020-8888888"));
    }
}
