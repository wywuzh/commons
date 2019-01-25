/*
 * Copyright 2015-2019 the original author or authors.
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
package com.wuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;
import com.itfsw.mybatis.generator.plugins.utils.FormatTools;
import com.itfsw.mybatis.generator.plugins.utils.JavaElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

/**
 * 类QueryPlugin的实现描述：自定义select查询插件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/25 21:48
 * @version v3.0.0
 * @since JDK 1.8
 */
public class SelectByParamsPlugin extends BasePlugin {
    public static final String METHOD_SELECT_TOTAL = "selectTotalByParams";
    public static final String METHOD_SELECT_LIST = "selectListByParams";
    public static final String METHOD_SELECT_PAGER = "selectPagerByParams";

    /**
     * Java Client Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 1. selectTotalByParam
        // 查询接口请求参数
        FullyQualifiedJavaType searchMapType = FullyQualifiedJavaType.getNewMapInstance();
        searchMapType.addTypeArgument(new FullyQualifiedJavaType("java.lang.String"));
        searchMapType.addTypeArgument(new FullyQualifiedJavaType("java.lang.Object"));
        Method mSelectTotal = JavaElementGeneratorTools.generateMethod(
                METHOD_SELECT_TOTAL,
                JavaVisibility.DEFAULT,
                new FullyQualifiedJavaType("java.lang.Long"),
                new Parameter(searchMapType, "searchMap", "@Param(\"map\")")
        );
        commentGenerator.addGeneralMethodComment(mSelectTotal, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mSelectTotal);
        logger.debug("selectTotalByParams插件:" + interfaze.getType().getShortName() + "增加selectTotalByParams方法。");

        // 2. selectListByParam
        // 查询接口返回数据
        FullyQualifiedJavaType resultType = FullyQualifiedJavaType.getNewListInstance();
        resultType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        Method mSelectList = JavaElementGeneratorTools.generateMethod(
                METHOD_SELECT_LIST,
                JavaVisibility.DEFAULT,
                resultType,
                new Parameter(searchMapType, "searchMap", "@Param(\"map\")")
        );
        commentGenerator.addGeneralMethodComment(mSelectList, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mSelectList);
        logger.debug("selectListByParams插件:" + interfaze.getType().getShortName() + "增加selectListByParams方法。");

        // 3. selectPagerByParams
        // 查询接口返回数据
        Method mSelectPager = JavaElementGeneratorTools.generateMethod(
                METHOD_SELECT_PAGER,
                JavaVisibility.DEFAULT,
                resultType,
                new Parameter(searchMapType, "searchMap", "@Param(\"map\")"),
                new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "offset", "@Param(\"offset\")"),
                new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "pageSize", "@Param(\"pageSize\")")
        );
        commentGenerator.addGeneralMethodComment(mSelectPager, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mSelectPager);
        logger.debug("selectPagerByParams插件:" + interfaze.getType().getShortName() + "增加selectPagerByParams方法。");
        return true;
    }
}