/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.poi.entity;

import com.github.wywuzh.commons.core.poi.annotation.ExcelCell;
import com.github.wywuzh.commons.core.poi.enums.CellTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 类User的实现描述：用户
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-25 18:38:12
 * @version v2.3.7
 * @since JDK 1.8
 */
@Data
public class User {

    /**
     * 用户名
     */
    @ExcelCell(value = "用户名", index = 0)
    private String username;
    /**
     * 昵称
     */
    @ExcelCell(value = "昵称", index = 1)
    private String nick;
    /**
     * 邮箱
     */
    @ExcelCell(value = "邮箱", index = 2)
    private String email;
    /**
     * 手机号
     */
    @ExcelCell(value = "手机号", index = 3)
    private String mobile;
    /**
     * 性别
     */
    @ExcelCell(value = "性别", index = 4)
    private String sex;
    /**
     * 出生日期
     */
    @ExcelCell(value = "出生日期", cellType = CellTypeEnum.Date, index = 5)
    private Date birthdate;
    /**
     * 资产
     */
    @ExcelCell(value = "资产余额", cellType = CellTypeEnum.Money, format = "￥###,##0.00", index = 6)
    private BigDecimal balance;

}
