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

import com.github.wywuzh.commons.core.util.DateUtils;
import com.github.wywuzh.commons.core.validator.PatternType;
import com.github.wywuzh.commons.core.validator.Validate;

import java.util.Date;

import org.springframework.util.Assert;

/**
 * 类ValidateRangeDate.java的实现描述：日期范围验证
 *
 * @author 伍章红 2015-8-6 下午2:59:47
 * @see Validate
 * @see PatternType
 * @since JDK 1.7.0_71
 */
public class ValidateRangeDate extends ValidateDate {

    private final java.util.Date startDate;
    private final java.util.Date endDate;

    public ValidateRangeDate(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean validate() {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");

        return startDate.before(endDate);
    }

    public static void main(String[] args) {
        Date startDate = DateUtils.parse("2014-12-12 00:00:00");
        Date endDate = DateUtils.parse("2014-12-13 00:00:00");
        ValidateRangeDate rangeDate = new ValidateRangeDate(startDate, endDate);
        System.out.println(rangeDate.validate());
    }
}
