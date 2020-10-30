/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.core.poi.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类ExcelCell的实现描述：Excel列
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-06-04 15:32:55
 * @version v2.2.6
 * @since JDK 1.8
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCell {

    String value();

    /**
     * 单元格列类型
     *
     * @return
     */
    CellType cellType() default CellType.Undefined;

    /**
     * 列所以，从0开始
     *
     * @return
     * @since v2.3.2
     */
    int index() default 0;

}
