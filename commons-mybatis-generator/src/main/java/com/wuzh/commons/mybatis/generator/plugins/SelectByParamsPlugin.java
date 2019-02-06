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
import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

/**
 * 类SelectByParamsPlugin的实现描述：自定义select查询插件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/25 21:48
 * @version v2.1.1
 * @since JDK 1.8
 */
public class SelectByParamsPlugin extends BasePlugin {
    /**
     * 数据总数查询
     */
    public static final String METHOD_SELECT_TOTAL = "selectTotalByParams";
    /**
     * 数据列表查询
     */
    public static final String METHOD_SELECT_LIST = "selectListByParams";
    /**
     * 数据列表查询（分页）
     */
    public static final String METHOD_SELECT_PAGER = "selectPagerByParams";
    /**
     * where条件SQL
     */
    public static final String WHERE_CONDITION = "appendConditions";

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

    /**
     * SQL Map Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // 1. selectTotalByParams
        XmlElement selectTotalEle = new XmlElement("select");
        // xml节点设置唯一ID
        selectTotalEle.addAttribute(new Attribute("id", METHOD_SELECT_TOTAL));
        selectTotalEle.addAttribute(new Attribute("resultType", "java.lang.Long"));
        // 参数类型
        selectTotalEle.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectTotalEle);
        selectTotalEle.addElement(new TextElement("select count(1)"));
        selectTotalEle.addElement(new TextElement("from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
//        selectTotalEle.addElement(new TextElement("where 1=1"));
        // 引入where条件
        XmlElement includeConditionsEle = new XmlElement("include");
        includeConditionsEle.addAttribute(new Attribute("refid", WHERE_CONDITION));
        selectTotalEle.addElement(includeConditionsEle);
        document.getRootElement().addElement(selectTotalEle);

        // 2. selectListByParams
        XmlElement selectListEle = new XmlElement("select");
        // xml节点设置唯一ID
        selectListEle.addAttribute(new Attribute("id", METHOD_SELECT_LIST));
        selectListEle.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
        // 参数类型
        selectListEle.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectListEle);
        selectListEle.addElement(new TextElement("select "));
        selectListEle.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));
        selectListEle.addElement(new TextElement("from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
//        selectListEle.addElement(new TextElement("where 1=1"));
        selectListEle.addElement(includeConditionsEle);
        // 增加排序功能
        selectListEle.addElement(generateSortElement(introspectedTable));
        document.getRootElement().addElement(selectListEle);

        // 3. selectListByParams
        XmlElement selectPagerEle = new XmlElement("select");
        // xml节点设置唯一ID
        selectPagerEle.addAttribute(new Attribute("id", METHOD_SELECT_PAGER));
        selectPagerEle.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
        // 参数类型
        selectPagerEle.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectPagerEle);
        selectPagerEle.addElement(new TextElement("select "));
        selectPagerEle.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));
        selectPagerEle.addElement(new TextElement("from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
//        selectPagerEle.addElement(new TextElement("where 1=1"));
        // 增加where条件SQL
        selectPagerEle.addElement(includeConditionsEle);
        // 增加排序功能
        selectPagerEle.addElement(generateSortElement(introspectedTable));
        // 添加分页SQL
        selectPagerEle.addElement(new TextElement("limit #{offset}, #{pageSize}"));
        document.getRootElement().addElement(selectPagerEle);

        // 生成where条件
        document.getRootElement().addElement(generateWhereConditionsElement(introspectedTable));
        return true;
    }

    /**
     * 生成Where查询条件
     *
     * @param introspectedTable
     * @return
     */
    private XmlElement generateWhereConditionsElement(IntrospectedTable introspectedTable) {
        XmlElement conditionsElement = new XmlElement("sql");
        conditionsElement.addAttribute(new Attribute("id", WHERE_CONDITION));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(conditionsElement);

        // 第一步：先判断map是否为空
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "map != null"));
        // 创建where
        XmlElement whereElement = new XmlElement("where");

        // 获取到table中的所有column
        List<IntrospectedColumn> introspectedColumnList = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < introspectedColumnList.size(); i++) {
            IntrospectedColumn introspectedColumn = introspectedColumnList.get(i);
            // 第二步：添加map中key的空判断
            XmlElement mapKeyIfElement = new XmlElement("if");
            // 获取Java字段名
            String javaProperty = introspectedColumn.getJavaProperty("map.");
            // 获取字段类型
            int columnJdbcType = introspectedColumn.getJdbcType();
            JdbcType jdbcType = JdbcType.forCode(columnJdbcType);
            if (JdbcType.CHAR.equals(jdbcType) ||
                    JdbcType.VARCHAR.equals(jdbcType) ||
                    JdbcType.LONGVARCHAR.equals(jdbcType) ||

                    JdbcType.BINARY.equals(jdbcType) ||
                    JdbcType.VARBINARY.equals(jdbcType) ||
                    JdbcType.LONGVARBINARY.equals(jdbcType) ||

                    JdbcType.BLOB.equals(jdbcType) ||

                    JdbcType.NVARCHAR.equals(jdbcType) ||
                    JdbcType.NCHAR.equals(jdbcType) ||
                    JdbcType.NCLOB.equals(jdbcType) ||
                    JdbcType.LONGNVARCHAR.equals(jdbcType)) {
                mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null and " + javaProperty + " != ''"));
            } else {
                mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null"));
            }
            // 添加column字段查询条件SQL（这里默认给表的所有字段添加and条件）
            if (i >= 1) {
                mapKeyIfElement.addElement(new TextElement("and " + MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)
                        + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "map.")));
            } else {
                mapKeyIfElement.addElement(new TextElement(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)
                        + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "map.")));
            }
            whereElement.addElement(mapKeyIfElement);
        }
        ifElement.addElement(whereElement);

        conditionsElement.addElement(ifElement);
        return conditionsElement;
    }

    private XmlElement generateSortElement(IntrospectedTable introspectedTable) {
        XmlElement sortRootElement = new XmlElement("if");
        sortRootElement.addAttribute(new Attribute("test", "map != null and map.sorts != null and map.sorts.size &gt; 0"));
        sortRootElement.addElement(new TextElement("order by"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "map.sorts"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement("${item.sort} ${item.order}"));

        sortRootElement.addElement(foreachElement);
        return sortRootElement;
    }
}