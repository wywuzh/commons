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
package com.wuzh.commons.core.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * 类Validate.java的实现描述：验证基类
 * 
 * @author 伍章红 2015-8-3 下午3:02:44
 * @see java.util.regex.Pattern
 * @since JDK 1.6.0_20
 */
public abstract class Validate {

    /**
     * 返回校验规则对象
     * 
     * @param pattern
     *            校验规则
     * @return
     */
    public Pattern pattern(String pattern) {
        Assert.notNull(pattern, "pattern must not be null");

        return Pattern.compile(pattern);
    }

    /**
     * 验证
     * 
     * @param pattern
     *            校验规则
     * @param object
     *            需要进行验证的对象
     * @return true or false
     */
    public boolean matches(String pattern, Object object) {
        Assert.notNull(pattern, "pattern must not be null");
        Assert.notNull(object, "object must not be null");

        Pattern compile = this.pattern(pattern);
        Matcher matcher = compile.matcher(object.toString());
        return matcher.matches();
    }

    /**
     * 验证
     * 
     * @param object
     *            需要进行验证的对象
     * @return true or false
     */
    public boolean matches(Object object) {
        return this.matches(getPatternType().getPattern(), object);
    }

    /**
     * 获取验证类型
     * 
     * @return 验证类型
     */
    protected abstract PatternType getPatternType();
}
