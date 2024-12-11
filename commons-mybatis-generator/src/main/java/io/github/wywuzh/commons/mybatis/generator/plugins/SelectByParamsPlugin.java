/*
 * Copyright 2015-2024 the original author or authors.
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
package io.github.wywuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.FormatTools;
import com.itfsw.mybatis.generator.plugins.utils.JavaElementGeneratorTools;
import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.TableConfiguration;

import io.github.wywuzh.commons.core.util.StringHelper;
import io.github.wywuzh.commons.mybatis.generator.constant.MbgPropertyConstants;
import io.github.wywuzh.commons.mybatis.generator.model.BlankElement;
import io.github.wywuzh.commons.mybatis.generator.utils.MbgPluginUtils;
import io.github.wywuzh.commons.mybatis.generator.utils.MbgPropertiesUtils;

/**
 * 类SelectByParamsPlugin的实现描述：自定义select查询插件
 *
 * <pre class="code">
 * <strong>enableLogicDelete使用方式</strong>：
 * 1. 添加&lt;plugin&gt;，在plugin中配置的property属性做为全局属性存在
 *     &lt;plugin type="io.github.wywuzh.commons.mybatis.generator.plugins.SelectByParamsPlugin"&gt;
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
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- Like模糊查询 --&gt;
 *         &lt;property name="conditionsLikeColumns" value=""/&gt;
 *     &lt;/table&gt;
 *
 * <strong>conditionsForeachInColumns使用方式</strong>：
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- Foreach in查询 --&gt;
 *         &lt;property name="conditionsForeachInColumns" value=""/&gt;
 *     &lt;/table&gt;
 *
 * <strong>conditionsNotInColumns使用方式</strong>：
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- not in查询 --&gt;
 *         &lt;property name="conditionsNotInColumns" value=""/&gt;
 *     &lt;/table&gt;
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/25 21:48
 * @version v2.1.1
 * @since JDK 1.8
 */
public abstract class SelectByParamsPlugin extends AbstractPlugin {

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

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
    }

    /**
     * Java Client Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable table表信息
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 1. selectTotalByParam
        // 查询接口请求参数
        FullyQualifiedJavaType searchMapType = FullyQualifiedJavaType.getNewMapInstance();
        searchMapType.addTypeArgument(new FullyQualifiedJavaType("java.lang.String"));
        searchMapType.addTypeArgument(new FullyQualifiedJavaType("java.lang.Object"));
        Method mSelectTotal = JavaElementGeneratorTools.generateMethod(METHOD_SELECT_TOTAL, JavaVisibility.DEFAULT, new FullyQualifiedJavaType("java.lang.Long"),
                new Parameter(searchMapType, "searchMap", "@Param(\"map\")"));
        commentGenerator.addGeneralMethodComment(mSelectTotal, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mSelectTotal);
        logger.debug("itfsw(查询插件):" + interfaze.getType().getShortName() + "增加selectTotalByParams方法。");

        // 2. selectListByParam
        // 查询接口返回数据
        FullyQualifiedJavaType resultType = FullyQualifiedJavaType.getNewListInstance();
        resultType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        Method mSelectList = JavaElementGeneratorTools.generateMethod(METHOD_SELECT_LIST, JavaVisibility.DEFAULT, resultType, new Parameter(searchMapType, "searchMap", "@Param(\"map\")"));
        commentGenerator.addGeneralMethodComment(mSelectList, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mSelectList);
        logger.debug("itfsw(查询插件):" + interfaze.getType().getShortName() + "增加selectListByParams方法。");

        // 3. selectPagerByParams
        // 查询接口返回数据
        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        Parameter secondParameter = new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "pageSize", "@Param(\"pageSize\")");
        if (DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass) || DRIVER_ORACLE.equalsIgnoreCase(driverClass)) {
            secondParameter = new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "endset", "@Param(\"endset\")");
        }
        Method mSelectPager = JavaElementGeneratorTools.generateMethod(METHOD_SELECT_PAGER, JavaVisibility.DEFAULT, resultType, new Parameter(searchMapType, "searchMap", "@Param(\"map\")"),
                new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "offset", "@Param(\"offset\")"), secondParameter);
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
     * @param introspectedTable table表信息
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

        // xml映射器文件sql：分页
        generateSqlMapForPager(document, introspectedTable, selectPagerEle, includeConditionsEle);
        document.getRootElement().addElement(selectPagerEle);

        // 4. 生成where条件
        document.getRootElement().addElement(generateConditionsElement(introspectedTable));

        // [v2.7.8]通过<include>标签引入的子项
        String PROPERTY_CONDITIONS_INCLUDES = super.getProperty(introspectedTable, MbgPropertyConstants.PROPERTY_CONDITIONS_INCLUDES, null);
        List<String> conditionsIncludes = MbgPropertiesUtils.split(PROPERTY_CONDITIONS_INCLUDES);
        for (String include : conditionsIncludes) {
            XmlElement conditionsElement = new XmlElement("sql");
            conditionsElement.addAttribute(new Attribute("id", include));
            // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
            commentGenerator.addComment(conditionsElement);
            document.getRootElement().addElement(conditionsElement);
        }

        return true;
    }

    /**
     * xml映射器文件sql：分页
     *
     * @param document
     * @param introspectedTable    table表信息
     * @param selectPagerEle       分页查询节点
     * @param includeConditionsEle 引入where条件
     * @since v2.7.0
     */
    protected abstract void generateSqlMapForPager(Document document, IntrospectedTable introspectedTable, XmlElement selectPagerEle, XmlElement includeConditionsEle);

    /**
     * 获取查询条件
     *
     * @param introspectedTable table表信息
     * @return
     * @since 2.3.6
     */
    protected Element getSelectWhereElement(IntrospectedTable introspectedTable) {
        // table配置：<table>标签配置信息
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        // <plugin>标签属性信息
        Properties properties = super.getProperties();

        // 表是否开启逻辑删除，默认为true
        boolean enableLogicDelete = super.getProperty(tableConfiguration, properties, MbgPropertyConstants.PROPERTY_ENABLE_LOGIC_DELETE, MbgPropertyConstants.enableLogicDelete);
        // 排除数据sql，即剔除已删除数据的sql
        String excludeDeletedSql = super.getProperty(tableConfiguration, properties, MbgPropertyConstants.PROPERTY_EXCLUDE_DELETED_SQL, MbgPropertyConstants.excludeDeletedSql);
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
     * @param introspectedTable table表信息
     * @return
     * @since 2.3.6
     */
    protected XmlElement generateConditionsElement(IntrospectedTable introspectedTable) {
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
     * 字段类型：字符
     *
     * @since v2.7.0
     */
    protected List<JdbcType> JDBC_TYPE_CHARACTER = Arrays.asList(JdbcType.CHAR, JdbcType.VARCHAR, JdbcType.LONGVARCHAR, JdbcType.BINARY, JdbcType.VARBINARY, JdbcType.LONGVARBINARY, JdbcType.BLOB,
            JdbcType.CLOB, JdbcType.NVARCHAR, JdbcType.NCHAR, JdbcType.NCLOB, JdbcType.LONGNVARCHAR);
    /**
     * 字段类型：数值
     *
     * @since v2.7.0
     */
    protected List<JdbcType> JDBC_TYPE_NUMBER = Arrays.asList(JdbcType.BIT, JdbcType.TINYINT, JdbcType.SMALLINT, JdbcType.INTEGER, JdbcType.BIGINT, JdbcType.FLOAT, JdbcType.REAL, JdbcType.DOUBLE,
            JdbcType.NUMERIC, JdbcType.DECIMAL);

    /**
     * appendConditions查询条件中添加if节点
     *
     * @param introspectedTable table表信息
     * @param rootElement       父节点
     * @since 2.3.6
     */
    protected void addElementForAppendConditions(IntrospectedTable introspectedTable, XmlElement rootElement) {
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();

        // [v2.7.8]通过<include>标签引入的子项
        String PROPERTY_CONDITIONS_INCLUDES = super.getProperty(introspectedTable, MbgPropertyConstants.PROPERTY_CONDITIONS_INCLUDES, null);
        List<String> conditionsIncludes = MbgPropertiesUtils.split(PROPERTY_CONDITIONS_INCLUDES);
        if (CollectionUtils.isNotEmpty(conditionsIncludes)) {
            for (String include : conditionsIncludes) {
                XmlElement includeEle = new XmlElement("include");
                includeEle.addAttribute(new Attribute("refid", include));
                rootElement.addElement(includeEle);
            }
            // 创建一个空行
            rootElement.addElement(new BlankElement());
        }

        // 开启Like模糊查询
//        List<String> conditionsLikeColumns = getConditionsLikeColumns(tableConfiguration);
        List<String> conditionsLikeColumns = MbgPropertiesUtils.split(tableConfiguration.getProperty(MbgPropertyConstants.PROPERTY_CONDITIONS_LIKE_COLUMNS));
        // 开启Foreach in查询
//        List<String> conditionsForeachInColumns = getConditionsForeachInColumns(tableConfiguration);
        List<String> conditionsForeachInColumns = MbgPropertiesUtils.split(tableConfiguration.getProperty(MbgPropertyConstants.PROPERTY_CONDITIONS_FOREACH_IN_COLUMNS));
        // not in查询字段
//        List<String> conditionsNotInColumns = getConditionsNotInColumns(tableConfiguration);
        List<String> conditionsNotInColumns = MbgPropertiesUtils.split(tableConfiguration.getProperty(MbgPropertyConstants.PROPERTY_CONDITIONS_NOT_IN_COLUMNS));

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
            if (JDBC_TYPE_CHARACTER.contains(jdbcType)) { // 字符类型
                mapKeyIfElement.addAttribute(new Attribute("test", "@org.apache.commons.lang3.StringUtils@isNotEmpty(" + javaProperty + ")"));
            } else if (JDBC_TYPE_NUMBER.contains(jdbcType)) { // 数值类型
                mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null"));
            } else {
                mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null"));
            }
            // 添加column字段查询条件SQL（这里默认给表的所有字段添加and条件）
            String columnName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);
            if (i >= 1) {
                mapKeyIfElement.addElement(new TextElement("and " + columnName + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "map.")));
            } else {
                mapKeyIfElement.addElement(new TextElement("and " + columnName + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "map.")));
            }
            rootElement.addElement(mapKeyIfElement);

            // 添加 like 模糊查询支持
            if (conditionsLikeColumns != null && conditionsLikeColumns.size() > 0 && conditionsLikeColumns.contains(columnName)) {
                addElementForLike(rootElement, introspectedColumn, columnName, javaProperty + "Like");
            }

            // 添加 foreach in 查询支持
            if (conditionsForeachInColumns != null && conditionsForeachInColumns.size() > 0 && conditionsForeachInColumns.contains(columnName)) {
                MbgPluginUtils.addElementForIn(rootElement, introspectedColumn, columnName, javaProperty + "s");
            }

            // 添加 not in 查询支持
            if (conditionsNotInColumns != null && conditionsNotInColumns.size() > 0 && conditionsNotInColumns.contains(columnName)) {
                // 封装 not in 字段，eg：notInIds
                String fieldName = StringUtils.join("map.", "notIn", StringHelper.firstCharToUpperCase(introspectedColumn.getJavaProperty()), "s");
                MbgPluginUtils.addElementForNotIn(rootElement, introspectedColumn, columnName, fieldName);
            }
        }
    }

    /**
     * @param whereElement 父节点
     * @param columnName   表字段名
     * @param javaProperty Java字段名
     */
    protected void addElementForLike(XmlElement whereElement, IntrospectedColumn introspectedColumn, String columnName, String javaProperty) {
        // 获取字段类型
        int columnJdbcType = introspectedColumn.getJdbcType();
        JdbcType jdbcType = JdbcType.forCode(columnJdbcType);
        if (!(JdbcType.CHAR.equals(jdbcType) || JdbcType.VARCHAR.equals(jdbcType) || JdbcType.LONGVARCHAR.equals(jdbcType) ||

                JdbcType.BINARY.equals(jdbcType) || JdbcType.VARBINARY.equals(jdbcType) || JdbcType.LONGVARBINARY.equals(jdbcType) ||

                JdbcType.BLOB.equals(jdbcType) || JdbcType.CLOB.equals(jdbcType) ||

                JdbcType.NVARCHAR.equals(jdbcType) || JdbcType.NCHAR.equals(jdbcType) || JdbcType.NCLOB.equals(jdbcType) || JdbcType.LONGNVARCHAR.equals(jdbcType))) {
            // 过滤掉不是字符类型的字段
            return;
        }

        // 第二步：添加map中key的空判断
        XmlElement mapKeyIfElement = new XmlElement("if");
        mapKeyIfElement.addAttribute(new Attribute("test", "@org.apache.commons.lang3.StringUtils@isNotEmpty(" + javaProperty + ")"));

        javaProperty = "#{" + javaProperty + "}";
//        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
//        if (DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass) || DRIVER_ORACLE.equalsIgnoreCase(driverClass)) {
//            // 生成Oracle模糊查询SQL
//            String likeSql = " like '%'||" + javaProperty + "||'%'";
//            mapKeyIfElement.addElement(new TextElement("and " + columnName + likeSql));
//        } else {
//            // 生成MySQL模糊查询SQL
//            String likeSql = " like concat('%'," + javaProperty + ",'%')";
//            mapKeyIfElement.addElement(new TextElement("and " + columnName + likeSql));
//        }
        mapKeyIfElement.addElement(new TextElement("and " + columnName + resolveDbLikeSql(javaProperty)));
        whereElement.addElement(mapKeyIfElement);
    }

    /**
     * 生成模糊查询SQL
     *
     * @param javaProperty java属性
     * @return
     * @since v2.7.0
     */
    protected abstract String resolveDbLikeSql(String javaProperty);

    /**
     * 开启Like模糊查询
     *
     * @param tableConfiguration
     * @return
     */
    @Deprecated
    protected List<String> getConditionsLikeColumns(TableConfiguration tableConfiguration) {
        String PROPERTY_CONDITIONS_LIKE_COLUMNS = tableConfiguration.getProperty(MbgPropertyConstants.PROPERTY_CONDITIONS_LIKE_COLUMNS);
        List<String> resultList = MbgPropertiesUtils.split(PROPERTY_CONDITIONS_LIKE_COLUMNS);
        return resultList;
    }

    /**
     * 开启Foreach in查询
     *
     * @param tableConfiguration
     * @return
     */
    @Deprecated
    protected List<String> getConditionsForeachInColumns(TableConfiguration tableConfiguration) {
        String PROPERTY_CONDITIONS_FOREACH_IN_COLUMNS = tableConfiguration.getProperty(MbgPropertyConstants.PROPERTY_CONDITIONS_FOREACH_IN_COLUMNS);
        List<String> resultList = MbgPropertiesUtils.split(PROPERTY_CONDITIONS_FOREACH_IN_COLUMNS);
        return resultList;
    }

    /**
     * not in查询字段
     *
     * @param tableConfiguration
     * @return
     * @since v2.4.5
     */
    @Deprecated
    protected List<String> getConditionsNotInColumns(TableConfiguration tableConfiguration) {
        String PROPERTY_CONDITIONS_NOT_IN_COLUMNS = tableConfiguration.getProperty(MbgPropertyConstants.PROPERTY_CONDITIONS_NOT_IN_COLUMNS);
        List<String> resultList = MbgPropertiesUtils.split(PROPERTY_CONDITIONS_NOT_IN_COLUMNS);
        return resultList;
    }

    protected XmlElement generateSortElement(IntrospectedTable introspectedTable) {
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
