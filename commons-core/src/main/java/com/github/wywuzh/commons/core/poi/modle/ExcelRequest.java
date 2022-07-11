/*
 * Copyright 2015-2021 the original author or authors.
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

import javafx.print.PrintSides;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 类ExcelRequest的实现描述：请求数据
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-05-29 10:55:10
 * @version v2.2.6
 * @since JDK 1.8
 */
public class ExcelRequest implements Serializable {
    private static final long serialVersionUID = -4416704498235565697L;

    private String sheetName;
    /**
     * 列名/字段名
     */
    private String[] columns;
    /**
     * 列的标题
     */
    private String[] columnTitles;
    /**
     * 列的长度
     */
    private Integer[] columnLengths;

    /**
     * 必填字段
     */
    private List<String> requiredColumnTitles;
    /**
     * 字段有效性验证：key=column字段，value=column字段下拉框列表数据（验证数据）
     */
    private Map<String, String[]> columnValidation;

    /**
     * 请求数据
     */
    private Collection<?> dataColl;
    /**
     * 提示信息。注意：该信息不为空时，会占据第一行，标题行会变为第二行
     *
     * @since v2.4.8
     */
    private String tips;

    public ExcelRequest() {
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getColumnTitles() {
        return columnTitles;
    }

    public void setColumnTitles(String[] columnTitles) {
        this.columnTitles = columnTitles;
    }

    public Integer[] getColumnLengths() {
        return columnLengths;
    }

    public void setColumnLengths(Integer[] columnLengths) {
        this.columnLengths = columnLengths;
    }

    public List<String> getRequiredColumnTitles() {
        return requiredColumnTitles;
    }

    public void setRequiredColumnTitles(List<String> requiredColumnTitles) {
        this.requiredColumnTitles = requiredColumnTitles;
    }

    public Map<String, String[]> getColumnValidation() {
        return columnValidation;
    }

    public void setColumnValidation(Map<String, String[]> columnValidation) {
        this.columnValidation = columnValidation;
    }

    public Collection<?> getDataColl() {
        return dataColl;
    }

    public void setDataColl(Collection<?> dataColl) {
        this.dataColl = dataColl;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
