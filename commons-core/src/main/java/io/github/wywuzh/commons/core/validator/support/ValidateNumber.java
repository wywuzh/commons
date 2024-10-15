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
package io.github.wywuzh.commons.core.validator.support;

import io.github.wywuzh.commons.core.validator.PatternType;
import io.github.wywuzh.commons.core.validator.Validate;

/**
 * 类ValidateNumber.java的实现描述：验证验证（零和非零开头的数字，支持负数）
 *
 * @author 伍章红 2015-8-6 下午2:43:19
 * @see Validate
 * @see PatternType
 * @since JDK 1.7.0_71
 */
public class ValidateNumber extends Validate {

    @Override
    protected PatternType getPatternType() {
        return PatternType.PATTERN_NUMBER;
    }

}
