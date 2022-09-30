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
package com.github.wywuzh.commons.mybatis.annotation;

import com.github.wywuzh.commons.mybatis.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * 类DataSourceChoice的实现描述：动态数据源选择
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-20 14:26:18
 * @version v2.2.0
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceChoice {

  DataSourceType value() default DataSourceType.WRITE;

}
