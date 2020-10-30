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
package com.wuzh.commons.core.common;

/**
 * 类Constants.java的实现描述：常量
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:19:12
 * @version v1.0.0
 * @since JDK 1.7
 */
public class Constants {

    /**
     * 默认登录密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    public static final String SHORTCUT_ICON = "";

    /**
     * 文件上传默认保存路径
     */
    public static final String UPLOAD_TO_PATH = "/files/uploads/";

    /**
     * 下载时默认读取文件地址
     */
    public static final String DOWNLOAD_FROM_PATH = "/files/downs/";

    /**
     * HTTP调用请求
     */
    public static final String METHOD_GET = "GET"; // 查询
    public static final String METHOD_POST = "POST"; // 新增/修改
    public static final String METHOD_PUT = "PUT"; // 修改
    public static final String METHOD_DELETE = "DELETE"; // 删除

    /**
     * 分隔符：逗号
     */
    public static final String SEPARATE_COMMA = ",";
    /**
     * 分隔符：下划线
     */
    public static final String SEPARATE_UNDERLINE = "_";
    /**
     * 分隔符：分号
     */
    public static final String SEPARATE_SEMICOLON = ";";
    /**
     * 分隔符：斜杠
     */
    public static final String SEPARATE_SLASH = "/";
    /**
     * 分隔符：横杆
     */
    public static final String SEPARATE_CROSS_BAR = "-";
    /**
     * 分隔符：竖
     */
    public static final String SEPARATE_VERTICAL = "|";
    /**
     * 分隔符：双竖
     */
    public static final String SEPARATE_DOUBLE_VERTICAL = "||";

    /**
     * 等于-中文
     */
    public static final String EQUALS_ZH = "等于";
    /**
     * 等于-代码符号
     */
    public static final String EQUALS_SYMBOL = "==";
    /**
     * 等于-代码符号（绝对值）
     */
    public static final String EQUALS_SYMBOL_ABSOLUTE = "===";
    /**
     * 不等于-中文
     */
    public static final String UN_EQUALS_ZH = "不等于";
    /**
     * 不等于-代码符号
     */
    public static final String UN_EQUALS_SYMBOL = "!=";
    /**
     * 不等于：SQL、Excel公式中常用该符号
     */
    public static final String UN_EQUALS_FORMULA = "<>";

    /**
     * 参数名：页码
     *
     * @since v2.3.3
     */
    public static final String PARAMETER_PAGE_NO = "pageNo";
    /**
     * 参数名：页码
     *
     * @since v2.3.3
     */
    public static final String PARAMETER_PAGE_SIZE = "pageSize";
    /**
     * 参数名：排序字段
     *
     * @since v2.3.3
     */
    public static final String PARAMETER_SORT = "sort";
    /**
     * 参数名：排序类型
     *
     * @since v2.3.3
     */
    public static final String PARAMETER_ORDER = "order";
    /**
     * 页面排序条件
     *
     * @since v2.3.3
     */
    public static final String SORT_CONDITIONS = "sortConditions";

    /**
     * 默认页码，页码从1开始
     *
     * @since v2.3.3
     */
    public static final Integer DEFAULT_PAGE_NO = 1;
    /**
     * 默认分页数，每页查询20条数据
     *
     * @since v2.3.3
     */
    public static final Integer DEFAULT_PAGE_SIZE = 20;

    public static final String X_REQUESTED_WITH = "x-requested-with";
    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

}
