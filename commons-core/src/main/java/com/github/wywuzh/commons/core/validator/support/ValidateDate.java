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
package com.github.wywuzh.commons.core.validator.support;

import com.github.wywuzh.commons.core.validator.PatternType;
import com.github.wywuzh.commons.core.validator.Validate;

/**
 * 类ValidateTime.java的实现描述：日期验证（格式：yyyy-MM-dd）
 *
 * @author 伍章红 2015-8-6 下午1:11:50
 * @see Validate
 * @see PatternType
 * @since JDK 1.7.0_71
 */
public class ValidateDate extends Validate {

  @Override
  protected PatternType getPatternType() {
    return PatternType.PATTERN_DATE;
  }

}
