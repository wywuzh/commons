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
package com.github.wywuzh.commons.core.poi.modle;

import java.io.Serializable;

import com.github.wywuzh.commons.core.poi.enums.CellTypeEnum;

/**
 * 类ExcelCellField的实现描述：Excel 列字段
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-11-23 10:43:54
 * @version v2.7.0
 * @since JDK 1.8
 */
public class ExcelCellField implements Serializable {
    private static final long serialVersionUID = -4928477127591031659L;

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段标题
     */
    private String fieldTitle;
    /**
     * 字段长度
     */
    private Integer fieldLength;

    /**
     * 单元格列类型
     *
     * @since v2.7.8
     */
    private CellTypeEnum cellType;
    /**
     * 单元格列数据格式
     */
    private String format;
    /**
     * 字段索引，从0开始
     */
    private Integer index = 0;
    /**
     * 排序
     */
    private Integer sortNo = 0;

    public ExcelCellField() {
    }

    public ExcelCellField(String fieldName, String fieldTitle) {
        this.fieldName = fieldName;
        this.fieldTitle = fieldTitle;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }

    public CellTypeEnum getCellType() {
        return cellType;
    }

    public void setCellType(CellTypeEnum cellType) {
        this.cellType = cellType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}
