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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.TableConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 类SelectByParamsPlugin的实现描述：自定义select查询插件
 * <pre class="code">
 * <strong>enableLogicDelete使用方式</strong>：
 * 1. 添加&lt;plugin&gt;，在plugin中配置的property属性做为全局属性存在
 *     &lt;plugin type="com.wuzh.commons.mybatis.generator.plugins.SelectByParamsPlugin"&gt;
 *         &lt;!-- 表是否开启物理删除 --&gt;
 *         &lt;property name="enableLogicDelete" value="true"/&gt;
 *         &lt;!-- 排除删除数据sql。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件 --&gt;
 *         &lt;property name="excludeDeletedSql" value="is_delete = 0"/&gt;
 *     &lt;/plugin&gt;
 * 2. 若需要对某张表单独定制，可在该table下配置进行覆盖
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- 表是否开启物理删除 --&gt;
 *         &lt;property name="enableLogicDelete" value="true"/&gt;
 *         &lt;!-- 排除删除数据sql。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件 --&gt;
 *         &lt;property name="excludeDeletedSql" value="is_delete = 0"/&gt;
 *     &lt;/table&gt;
 *
 * <strong>conditionsLikeColumns使用方式</strong>：
 *
 * <strong>conditionsForeachInColumns使用方式</strong>：
 *
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/25 21:48
 * @version v2.1.1
 * @since JDK 1.8
 */
public class SelectByParamsPlugin extends BasePlugin {

    /**
     * 表是否开启逻辑删除
     *
     * @since 2.3.6
     */
    public static final String ENABLE_LOGIC_DELETE = "enableLogicDelete";
    /**
     * 逻辑删除字段。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件。
     *
     * @since 2.3.6
     */
    public static final String LOGIC_DELETE_FIELD = "logicDeleteField";
    /**
     * 排除数据sql，即剔除已删除数据的sql
     *
     * @since 2.3.6
     */
    public static final String EXCLUDE_DELETED_SQL = "excludeDeletedSql";
    /**
     * 表是否开启逻辑删除，默认为true
     *
     * @since 2.3.6
     */
    private boolean enableLogicDelete = true;
    /**
     * 逻辑删除字段默认值
     *
     * @since 2.3.6
     */
    private String logicDeleteField = "is_delete";
    /**
     * 排除删除数据sql。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件
     *
     * @since 2.3.6
     */
    private String excludeDeletedSql = "is_delete = 0";


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
     * Like模糊查询
     */
    public static final String CONDITIONS_LIKE_COLUMNS = "conditionsLikeColumns";
    /**
     * Foreach in查询
     */
    public static final String CONDITIONS_FOREACH_IN_COLUMNS = "conditionsForeachInColumns";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

        // v2.3.6
        // 表是否开启逻辑删除，默认为true
        String enableLogicDelete = super.getProperties().getProperty(ENABLE_LOGIC_DELETE);
        if (StringUtils.isNotBlank(enableLogicDelete)) {
            this.enableLogicDelete = Boolean.valueOf(enableLogicDelete);
        }
        // 逻辑删除字段
        String logicDeleteField = super.getProperties().getProperty(LOGIC_DELETE_FIELD);
        if (StringUtils.isNotBlank(enableLogicDelete)) {
            this.logicDeleteField = logicDeleteField;
        }
        // 排除数据sql，即剔除已删除数据的sql
        String excludeDeletedSql = super.getProperties().getProperty(EXCLUDE_DELETED_SQL);
        if (StringUtils.isNotBlank(excludeDeletedSql)) {
            this.excludeDeletedSql = excludeDeletedSql;
        }
    }

    /**
     * 表是否开启逻辑删除，默认为true
     *
     * @param tableConfiguration table配置
     * @return
     * @since 2.3.6
     */
    private boolean enableLogicDelete(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String enableLogicDelete = tableConfiguration.getProperty(ENABLE_LOGIC_DELETE);
        if (StringUtils.isNotBlank(enableLogicDelete)) {
            return Boolean.valueOf(enableLogicDelete);
        }
        return this.enableLogicDelete;
    }

    /**
     * 排除删除数据sql。在生成selectTotalByParams、selectListByParams、selectPagerByParams查询语句时，会在where条件后面添加该条件
     *
     * @param tableConfiguration table配置
     * @return
     * @since 2.3.6
     */
    private String excludeDeletedSql(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String excludeDeletedSql = tableConfiguration.getProperty(EXCLUDE_DELETED_SQL);
        if (StringUtils.isNotBlank(excludeDeletedSql)) {
            return excludeDeletedSql;
        }
        return this.excludeDeletedSql;
    }

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
        logger.debug("itfsw(查询插件):" + interfaze.getType().getShortName() + "增加selectTotalByParams方法。");

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
        logger.debug("itfsw(查询插件):" + interfaze.getType().getShortName() + "增加selectListByParams方法。");

        // 3. selectPagerByParams
        // 查询接口返回数据
        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        Parameter secondParameter = new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "pageSize", "@Param(\"pageSize\")");
        if (DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass)
                || DRIVER_ORACLE.equalsIgnoreCase(driverClass)) {
            secondParameter = new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "endset", "@Param(\"endset\")");
        }
        Method mSelectPager = JavaElementGeneratorTools.generateMethod(
                METHOD_SELECT_PAGER,
                JavaVisibility.DEFAULT,
                resultType,
                new Parameter(searchMapType, "searchMap", "@Param(\"map\")"),
                new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "offset", "@Param(\"offset\")"),
                secondParameter
        );
        commentGenerator.addGeneralMethodComment(mSelectPager, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mSelectPager);
        logger.debug("itfsw(查询插件):" + interfaze.getType().getShortName() + "增加selectPagerByParams方法。");
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
        // select条件
        Element selectWhereElement = getSelectWhereElement(introspectedTable);

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
        if (selectWhereElement != null) {
            selectTotalEle.addElement(selectWhereElement);
        }
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
        if (selectWhereElement != null) {
            selectListEle.addElement(selectWhereElement);
        }
        selectListEle.addElement(includeConditionsEle);
        // 增加排序功能
        selectListEle.addElement(generateSortElement(introspectedTable));
        document.getRootElement().addElement(selectListEle);

        // 3. selectPagerByParams
        XmlElement selectPagerEle = new XmlElement("select");
        // xml节点设置唯一ID
        selectPagerEle.addAttribute(new Attribute("id", METHOD_SELECT_PAGER));
        selectPagerEle.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
        // 参数类型
        selectPagerEle.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectPagerEle);

        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass)
                || DRIVER_ORACLE.equalsIgnoreCase(driverClass)) {
            // 生成Oracle分页查询SQL
            generateOraclePager(document, introspectedTable, selectPagerEle, includeConditionsEle);
        } else {
            // 生成MySQL分页查询SQL
            generateMySQLPager(document, introspectedTable, selectPagerEle, includeConditionsEle);
        }
        document.getRootElement().addElement(selectPagerEle);

        // 4. 生成where条件
        document.getRootElement().addElement(generateConditionsElement(introspectedTable));
        return true;
    }

    private void generateMySQLPager(Document document, IntrospectedTable introspectedTable, XmlElement selectPagerEle, XmlElement includeConditionsEle) {
        // select条件
        Element selectWhereElement = getSelectWhereElement(introspectedTable);

        selectPagerEle.addElement(new TextElement("select "));
        selectPagerEle.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));
        selectPagerEle.addElement(new TextElement("from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        if (selectWhereElement != null) {
            selectPagerEle.addElement(selectWhereElement);
        }
        // 增加where条件SQL
        selectPagerEle.addElement(includeConditionsEle);
        // 增加排序功能
        selectPagerEle.addElement(generateSortElement(introspectedTable));
        // 添加分页SQL
        selectPagerEle.addElement(new TextElement("limit #{offset}, #{pageSize}"));
    }

    private void generateOraclePager(Document document, IntrospectedTable introspectedTable, XmlElement selectPagerEle, XmlElement includeConditionsEle) {
        // select条件
        Element selectWhereElement = getSelectWhereElement(introspectedTable);

        selectPagerEle.addElement(new TextElement("select * from ("));
        selectPagerEle.addElement(new TextElement("select a.*, ROWNUM rn from ("));
        selectPagerEle.addElement(new TextElement("select "));
        selectPagerEle.addElement(XmlElementGeneratorTools.getBaseColumnListElement(introspectedTable));
        selectPagerEle.addElement(new TextElement("from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        if (selectWhereElement != null) {
            selectPagerEle.addElement(selectWhereElement);
        }
        // 增加where条件SQL
        selectPagerEle.addElement(includeConditionsEle);
        // 增加排序功能
        selectPagerEle.addElement(generateSortElement(introspectedTable));
        // 添加分页SQL
        selectPagerEle.addElement(new TextElement(")a"));
        selectPagerEle.addElement(new TextElement("where ROWNUM &lt;= #{endset}"));
        selectPagerEle.addElement(new TextElement(")"));
        selectPagerEle.addElement(new TextElement("where rn &gt; #{offset}"));
    }

    /**
     * 获取查询条件
     *
     * @param introspectedTable
     * @return
     * @since 2.3.6
     */
    private Element getSelectWhereElement(IntrospectedTable introspectedTable) {
        // 表是否开启逻辑删除，默认为true
        boolean enableLogicDelete = enableLogicDelete(introspectedTable.getTableConfiguration());
        // 排除数据sql，即剔除已删除数据的sql
        String excludeDeletedSql = excludeDeletedSql(introspectedTable.getTableConfiguration());
        if (!enableLogicDelete || StringUtils.isBlank(excludeDeletedSql)) {
            return null; // 没有开启逻辑删除，或者排除数据sql为空
        }

        // 去掉前后空格
        excludeDeletedSql = StringUtils.strip(excludeDeletedSql);
        // 检查传入的SQL是否“and ”开头，如果是则需要剔除掉
        if (StringUtils.startsWithIgnoreCase(excludeDeletedSql, "and ")) {
            excludeDeletedSql = StringUtils.substringAfter(excludeDeletedSql, "and ");
        }
        return new TextElement("where " + excludeDeletedSql);
    }

    /**
     * 生成Where查询条件
     *
     * @param introspectedTable
     * @return
     * @since 2.3.6
     */
    private XmlElement generateConditionsElement(IntrospectedTable introspectedTable) {
        XmlElement conditionsElement = new XmlElement("sql");
        conditionsElement.addAttribute(new Attribute("id", WHERE_CONDITION));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(conditionsElement);

        // 第一步：先判断map是否为空
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "map != null"));

        if (getSelectWhereElement(introspectedTable) != null) {
            // 获取到table中的所有column
            addElementForAppendConditions(introspectedTable, ifElement);
        } else {
            // 创建where
            XmlElement whereElement = new XmlElement("where");
            // 获取到table中的所有column
            addElementForAppendConditions(introspectedTable, whereElement);
            ifElement.addElement(whereElement);
        }

        conditionsElement.addElement(ifElement);
        return conditionsElement;
    }

    /**
     * 生成Where查询条件
     *
     * @param introspectedTable
     * @return
     */
    @Deprecated
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
        addElementForAppendConditions(introspectedTable, whereElement);

        ifElement.addElement(whereElement);

        conditionsElement.addElement(ifElement);
        return conditionsElement;
    }

    /**
     * appendConditions查询条件中添加if节点
     *
     * @param introspectedTable
     * @param rootElement
     * @since 2.3.6
     */
    private void addElementForAppendConditions(IntrospectedTable introspectedTable, XmlElement rootElement) {
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();

        // 开启Like模糊查询
        List<String> conditionsLikeColumns = getConditionsLikeColumns(tableConfiguration);
        // 开启Foreach in查询
        List<String> conditionsForeachInColumns = getConditionsForeachInColumns(tableConfiguration);

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
                    JdbcType.CLOB.equals(jdbcType) ||

                    JdbcType.NVARCHAR.equals(jdbcType) ||
                    JdbcType.NCHAR.equals(jdbcType) ||
                    JdbcType.NCLOB.equals(jdbcType) ||
                    JdbcType.LONGNVARCHAR.equals(jdbcType)) {
                mapKeyIfElement.addAttribute(new Attribute("test", "@org.apache.commons.lang3.StringUtils@isNotEmpty(" + javaProperty + ")"));
            } else if (JdbcType.BIT.equals(jdbcType) ||
                    JdbcType.TINYINT.equals(jdbcType) ||
                    JdbcType.SMALLINT.equals(jdbcType) ||
                    JdbcType.INTEGER.equals(jdbcType) ||
                    JdbcType.BIGINT.equals(jdbcType) ||
                    JdbcType.FLOAT.equals(jdbcType) ||
                    JdbcType.REAL.equals(jdbcType) ||
                    JdbcType.DOUBLE.equals(jdbcType) ||
                    JdbcType.NUMERIC.equals(jdbcType) ||
                    JdbcType.DECIMAL.equals(jdbcType)) {
                mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null"));
            } else {
                mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null"));
            }
            // 添加column字段查询条件SQL（这里默认给表的所有字段添加and条件）
            String columnName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            if (i >= 1) {
                mapKeyIfElement.addElement(new TextElement("and " + columnName
                        + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "map.")));
            } else {
                mapKeyIfElement.addElement(new TextElement("and " + columnName
                        + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "map.")));
            }
            rootElement.addElement(mapKeyIfElement);

            // 添加 like 模糊查询支持
            if (conditionsLikeColumns != null && conditionsLikeColumns.size() > 0 &&
                    conditionsLikeColumns.contains(columnName)) {
                addElementForLike(rootElement, introspectedColumn, columnName, javaProperty + "Like");
            }

            // 添加 foreach in 查询支持
            if (conditionsForeachInColumns != null && conditionsForeachInColumns.size() > 0 &&
                    conditionsForeachInColumns.contains(columnName)) {
                addElementForIn(rootElement, introspectedColumn, columnName, javaProperty + "s");
            }
        }
    }

    /**
     * @param whereElement
     * @param columnName   表字段名
     * @param javaProperty Java字段名
     */
    private void addElementForLike(XmlElement whereElement, IntrospectedColumn introspectedColumn, String columnName, String javaProperty) {
        // 获取字段类型
        int columnJdbcType = introspectedColumn.getJdbcType();
        JdbcType jdbcType = JdbcType.forCode(columnJdbcType);
        if (!(JdbcType.CHAR.equals(jdbcType) ||
                JdbcType.VARCHAR.equals(jdbcType) ||
                JdbcType.LONGVARCHAR.equals(jdbcType) ||

                JdbcType.BINARY.equals(jdbcType) ||
                JdbcType.VARBINARY.equals(jdbcType) ||
                JdbcType.LONGVARBINARY.equals(jdbcType) ||

                JdbcType.BLOB.equals(jdbcType) ||
                JdbcType.CLOB.equals(jdbcType) ||

                JdbcType.NVARCHAR.equals(jdbcType) ||
                JdbcType.NCHAR.equals(jdbcType) ||
                JdbcType.NCLOB.equals(jdbcType) ||
                JdbcType.LONGNVARCHAR.equals(jdbcType))) {
            // 过滤掉不是字符类型的字段
            return;
        }

        // 第二步：添加map中key的空判断
        XmlElement mapKeyIfElement = new XmlElement("if");
        mapKeyIfElement.addAttribute(new Attribute("test", "@org.apache.commons.lang3.StringUtils@isNotEmpty(" + javaProperty + ")"));

        javaProperty = "#{" + javaProperty + "}";
        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass)
                || DRIVER_ORACLE.equalsIgnoreCase(driverClass)) {
            // 生成Oracle模糊查询SQL
            String likeSql = " like '%'||" + javaProperty + "||'%'";
            mapKeyIfElement.addElement(new TextElement("and " + columnName + likeSql));
        } else {
            // 生成MySQL模糊查询SQL
            String likeSql = " like concat('%'," + javaProperty + ",'%')";
            mapKeyIfElement.addElement(new TextElement("and " + columnName + likeSql));
        }
        whereElement.addElement(mapKeyIfElement);
    }

    /**
     * @param whereElement
     * @param columnName   表字段名
     * @param javaProperty Java字段名
     */
    private void addElementForIn(XmlElement whereElement, IntrospectedColumn introspectedColumn, String columnName, String javaProperty) {
        // 第二步：添加map中key的空判断
        XmlElement mapKeyIfElement = new XmlElement("if");
        mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null and " + javaProperty + ".size &gt; 0"));
        mapKeyIfElement.addElement(new TextElement("and " + columnName + " in"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", javaProperty));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("open", "("));
        foreachElement.addAttribute(new Attribute("close", ")"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement("#{item}"));

        mapKeyIfElement.addElement(foreachElement);

        whereElement.addElement(mapKeyIfElement);
    }

    /**
     * 开启Like模糊查询
     *
     * @param tableConfiguration
     * @return
     */
    private List<String> getConditionsLikeColumns(TableConfiguration tableConfiguration) {
        List<String> resultList = new ArrayList<>(0);

        String conditionsLikeColumns = tableConfiguration.getProperty(CONDITIONS_LIKE_COLUMNS);
        if (StringUtils.isNotBlank(conditionsLikeColumns)) {
            // conditionsLikeColumns 兼容全角和半角的逗号分隔符
            conditionsLikeColumns = StringUtils.replace(conditionsLikeColumns, "，", ",");
            String[] dataArr = StringUtils.split(conditionsLikeColumns, ",");
            for (String str : dataArr) {
                str = str.trim();
                if (str == null || "".equals(str)) {
                    continue;
                }
                resultList.add(str);
            }
        }
        return resultList;
    }

    /**
     * 开启Foreach in查询
     *
     * @param tableConfiguration
     * @return
     */
    private List<String> getConditionsForeachInColumns(TableConfiguration tableConfiguration) {
        List<String> resultList = new ArrayList<>(0);

        String conditionsForeachInColumns = tableConfiguration.getProperty(CONDITIONS_FOREACH_IN_COLUMNS);
        if (StringUtils.isNotBlank(conditionsForeachInColumns)) {
            // conditionsForeachInColumns 兼容全角和半角的逗号分隔符
            conditionsForeachInColumns = StringUtils.replace(conditionsForeachInColumns, "，", ",");
            String[] dataArr = StringUtils.split(conditionsForeachInColumns, ",");
            for (String str : dataArr) {
                str = str.trim();
                if (str == null || "".equals(str)) {
                    continue;
                }
                resultList.add(str);
            }
        }
        return resultList;
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