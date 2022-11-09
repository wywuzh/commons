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
package com.github.wywuzh.commons.core.poi.enums;

/**
 * 类CellType的实现描述：Excel单元格列类型
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-06-04 15:34:25
 * @version v2.2.6
 * @since JDK 1.8
 */
public enum CellTypeEnum {

    Undefined, // 未知
    String, // 字符
    Integer, // 数值
    BigDecimal, // 2位小数
    Money, // 金额
    Percent, // 百分比
    Date, // 日期：yyyy-MM-dd格式
    Time, // 时间：HH:mm:ss格式
    DateTime, // 日期时间：yyyy-MM-dd HH:mm:ss格式
    ;

}
