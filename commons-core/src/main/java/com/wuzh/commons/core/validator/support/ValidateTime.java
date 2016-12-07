/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.validator.support;

import com.wuzh.commons.core.validator.PatternType;
import com.wuzh.commons.core.validator.Validate;

/**
 * 类ValidateTime.java的实现描述：时间验证（格式：HH:mm:ss）
 * 
 * @author 伍章红 2015-8-6 下午1:11:50
 * @see com.wuzh.frame.core.validator.Validate
 * @see com.wuzh.frame.core.validator.PatternType
 * @since JDK 1.7.0_71
 */
public class ValidateTime extends Validate {

    public static final String PATTERN_TIME = "(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$";

    @Override
    protected PatternType getPatternType() {
        return PatternType.PATTERN_TIME;
    }

    public static void main(String[] args) {
        ValidateTime validateTime = new ValidateTime();
        System.out.println(validateTime.matches("12:59:59"));
    }
}
