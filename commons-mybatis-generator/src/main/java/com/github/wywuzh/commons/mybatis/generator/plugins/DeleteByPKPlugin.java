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
package com.github.wywuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;
import com.itfsw.mybatis.generator.plugins.utils.FormatTools;
import com.itfsw.mybatis.generator.plugins.utils.JavaElementGeneratorTools;
import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.TableConfiguration;

/**
 * 类DeleteByPKPlugin的实现描述：根据主键删除数据
 * 
 * <pre class="code">
 * 使用方式：
 * 1. 添加&lt;plugin&gt;，在plugin中配置的property属性做为全局属性存在
 *     &lt;plugin type="com.github.wywuzh.commons.mybatis.generator.plugins.DeleteByPKPlugin"&gt;
 *         &lt;!-- 方法名，默认：deleteByIds。 --&gt;
 *         &lt;property name="methodName" value="deleteByIds"/&gt;
 *         &lt;!-- 表是否开启逻辑删除，默认为true。如果传入值为false，表示该表使用的是物理删除，生成的sql为delete删除语句，logicDeleteField、logicDeletedFlag两个参数值将没有意义。 --&gt;
 *         &lt;property name="enableLogicDelete" value="true"/&gt;
 *         &lt;!-- 逻辑删除字段，默认值：is_delete --&gt;
 *         &lt;property name="logicDeleteField" value="is_delete"/&gt;
 *         &lt;!-- 逻辑删除标识，默认为1 --&gt;
 *         &lt;property name="logicDeletedFlag" value="1"/&gt;
 *     &lt;/plugin&gt;
 * 2. 若需要对某张表单独定制，可在该table下配置进行覆盖
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- 方法名，默认：deleteByIds。 --&gt;
 *         &lt;property name="methodName" value="deleteByIds"/&gt;
 *         &lt;!-- 表是否开启逻辑删除，默认为true。如果传入值为false，表示该表使用的是物理删除，生成的sql为delete删除语句，logicDeleteField、logicDeletedFlag两个参数值将没有意义。 --&gt;
 *         &lt;property name="enableLogicDelete" value="true"/&gt;
 *         &lt;!-- 逻辑删除字段，默认值：is_delete --&gt;
 *         &lt;property name="logicDeleteField" value="is_delete"/&gt;
 *         &lt;!-- 逻辑删除标识，默认为1 --&gt;
 *         &lt;property name="logicDeletedFlag" value="1"/&gt;
 *     &lt;/table&gt;
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-12-25 21:38:17
 * @version v2.3.6
 * @since JDK 1.8
 */
public class DeleteByPKPlugin extends BasePlugin {

    /**
     * 方法名
     */
    public static final String METHOD_NAME = "methodName";
    /**
     * 主键字段
     */
    public static final String PK_NAME = "pk";
    /**
     * 是否开启删除
     *
     * @since v2.5.2
     */
    public static final String ENABLE_DELETE = "enableDelete";
    /**
     * 表是否开启逻辑删除
     */
    public static final String ENABLE_LOGIC_DELETE = "enableLogicDelete";
    /**
     * 逻辑删除字段
     */
    public static final String LOGIC_DELETE_FIELD = "logicDeleteField";
    /**
     * 逻辑删除标识
     */
    public static final String LOGIC_DELETED_FLAG = "logicDeletedFlag";

    /**
     * 默认方法名
     */
    private String DEFAULT_METHOD_NAME = "deleteByIds";
    /**
     * 默认主键字段名
     */
    private String DEFAULT_PK_COLUMN = "id";
    /**
     * 表是否开启逻辑删除，默认为true。如果传入值为false，表示该表使用的是物理删除，生成的sql为delete删除语句，logicDeleteField、logicDeletedFlag两个参数值将没有意义。
     */
    private boolean enableLogicDelete = true;
    /**
     * 逻辑删除字段，默认值：is_delete
     */
    private String logicDeleteField = "is_delete";
    /**
     * 逻辑删除标识，默认值：1
     */
    private String logicDeletedFlag = "1";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        if (enableDelete(introspectedTable.getTableConfiguration()) == true && getPrimaryKeyColumn(introspectedTable) == null) {
            logger.error("DeleteByPKPlugin(根据主键删除数据插件):{} 检查到该表未定义主键字段，initialized方法初始化失败！", introspectedTable.getFullyQualifiedTable());
            throw new IllegalArgumentException("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTable() + " 检查到该表未定义主键字段，initialized方法初始化失败！");
        }

        // 表是否开启逻辑删除，默认为true
        String enableLogicDelete = super.getProperties().getProperty(ENABLE_LOGIC_DELETE);
        if (StringUtils.isNotBlank(enableLogicDelete)) {
            this.enableLogicDelete = Boolean.valueOf(enableLogicDelete);
        }
        // 逻辑删除字段
        String logicDeleteField = super.getProperties().getProperty(LOGIC_DELETE_FIELD);
        if (StringUtils.isNotBlank(logicDeleteField)) {
            this.logicDeleteField = logicDeleteField;
        }
        // 逻辑删除标识
        String logicDeletedFlag = super.getProperties().getProperty(LOGIC_DELETED_FLAG);
        if (StringUtils.isNotBlank(logicDeletedFlag)) {
            this.logicDeletedFlag = logicDeletedFlag;
        }
    }

    /**
     * 是否开启删除
     *
     * @param tableConfiguration table配置
     * @return
     * @since v2.5.2
     */
    private boolean enableDelete(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String enableDelete = tableConfiguration.getProperty(ENABLE_DELETE);
        if (StringUtils.isNotBlank(enableDelete)) {
            return Boolean.valueOf(enableDelete);
        }
        return true;
    }

    /**
     * 表是否开启逻辑删除，默认为true
     *
     * @param tableConfiguration table配置
     * @return
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
     * 逻辑删除字段
     *
     * @param tableConfiguration table配置
     * @return
     */
    private String logicDeletedFlag(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String logicDeletedFlag = tableConfiguration.getProperty(LOGIC_DELETED_FLAG);
        if (StringUtils.isNotBlank(logicDeletedFlag)) {
            return logicDeletedFlag;
        }
        return this.logicDeletedFlag;
    }

    /**
     * 逻辑删除字段
     *
     * @param tableConfiguration table配置
     * @return
     */
    private String logicDeleteField(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String logicDeleteField = tableConfiguration.getProperty(LOGIC_DELETE_FIELD);
        if (StringUtils.isNotBlank(logicDeleteField)) {
            return logicDeleteField;
        }
        return this.logicDeleteField;
    }

    /**
     * 获取主键字段
     *
     * @param introspectedTable table表信息
     * @return
     * @since v2.5.2
     */
    private IntrospectedColumn getPrimaryKeyColumn(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (CollectionUtils.isEmpty(primaryKeyColumns)) {
            return null;
        }
        IntrospectedColumn introspectedColumn = primaryKeyColumns.get(0);
        return introspectedColumn;
    }

    /**
     * 获取主键字段的Java类型
     *
     * @param introspectedTable table表信息
     * @return
     * @since v2.5.2
     */
    private FullyQualifiedJavaType getPrimaryKeyJavaType(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (CollectionUtils.isEmpty(primaryKeyColumns)) {
            return null;
        }
        IntrospectedColumn introspectedColumn = primaryKeyColumns.get(0);
        return introspectedColumn.getFullyQualifiedJavaType();
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
        FullyQualifiedJavaType deleteByType = FullyQualifiedJavaType.getNewListInstance();
        deleteByType.addTypeArgument(Optional.ofNullable(getPrimaryKeyJavaType(introspectedTable)).orElse(FullyQualifiedJavaType.getStringInstance()));
        Method mBatchInsert = JavaElementGeneratorTools.generateMethod(DEFAULT_METHOD_NAME, JavaVisibility.DEFAULT, FullyQualifiedJavaType.getIntInstance(),
                new Parameter(deleteByType, "ids", "@Param(\"ids\")"), new Parameter(new FullyQualifiedJavaType("java.lang.String"), "updateUser", "@Param(\"updateUser\")"),
                new Parameter(new FullyQualifiedJavaType("java.util.Date"), "updateTime", "@Param(\"updateTime\")"));
        commentGenerator.addGeneralMethodComment(mBatchInsert, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mBatchInsert);
        logger.debug("DeleteByPKPlugin(根据主键删除数据插件):" + interfaze.getType().getShortName() + "增加deleteByIds方法。");
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement deleteByEle = new XmlElement("update");
        deleteByEle.addAttribute(new Attribute("id", DEFAULT_METHOD_NAME));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(deleteByEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(deleteByEle, introspectedTable);

        if (enableLogicDelete(introspectedTable.getTableConfiguration())) {
            // 逻辑删除
            generateLogicDelete(document, introspectedTable, deleteByEle);
        } else {
            // 物理删除
            generatePhysicsDelete(document, introspectedTable, deleteByEle);
        }

        document.getRootElement().addElement(deleteByEle);
        logger.debug("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加deleteByIds方法。");
        return true;
    }

    /**
     * 逻辑删除
     *
     * @param document
     * @param introspectedTable table表信息
     * @param deleteByEle
     */
    private void generateLogicDelete(Document document, IntrospectedTable introspectedTable, XmlElement deleteByEle) {
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        // 主键字段
        IntrospectedColumn primaryKeyColumn = getPrimaryKeyColumn(introspectedTable);
        IntrospectedColumn updateUser = introspectedTable.getColumn("UPDATE_USER");
        IntrospectedColumn updateTime = introspectedTable.getColumn("UPDATE_TIME");
        if (updateUser == null) {
            logger.error("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " 检查到该表未定义UPDATE_USER字段，生成逻辑删除SQL语句失败！");
            throw new IllegalArgumentException("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " UPDATE_USER字段检验不通过！");
        }
        if (updateTime == null) {
            logger.error("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " 检查到该表未定义UPDATE_TIME字段，生成逻辑删除SQL语句失败！");
            throw new IllegalArgumentException("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " UPDATE_TIME字段检验不通过！");
        }
        String logicDeleteField = logicDeleteField(tableConfiguration);
        if (StringUtils.isBlank(logicDeleteField)) {
            logger.error("DeleteByPKPlugin(根据主键删除数据插件):{} logicDeleteField属性值定义为空，生成逻辑删除SQL语句失败！", introspectedTable.getFullyQualifiedTableNameAtRuntime());
            throw new IllegalArgumentException("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " logicDeleteField属性值检验不通过！");
        }
        IntrospectedColumn deleteField = introspectedTable.getColumn(logicDeleteField);
        if (deleteField == null) {
            logger.error("DeleteByPKPlugin(根据主键删除数据插件):{} 检查到该表未定义{}字段，生成逻辑删除SQL语句失败！", introspectedTable.getFullyQualifiedTableNameAtRuntime(), logicDeleteField);
            throw new IllegalArgumentException("DeleteByPKPlugin(根据主键删除数据插件):" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " logicDeleteField属性值检验不通过！");
        }

        deleteByEle.addElement(new TextElement("UPDATE " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        deleteByEle.addElement(new TextElement("set " + MyBatis3FormattingUtilities.getAliasedEscapedColumnName(deleteField) + " = " + logicDeletedFlag(tableConfiguration) + ","));
        deleteByEle.addElement(new TextElement(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(updateUser) + " = " + MyBatis3FormattingUtilities.getParameterClause(updateUser) + ","));
        deleteByEle.addElement(new TextElement(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(updateTime) + " = " + MyBatis3FormattingUtilities.getParameterClause(updateTime)));
        deleteByEle.addElement(new TextElement("WHERE ID in"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "ids"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("open", "("));
        foreachElement.addAttribute(new Attribute("close", ")"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement("#{item,jdbcType=" + primaryKeyColumn.getJdbcTypeName() + "}"));

        deleteByEle.addElement(foreachElement);
    }

    /**
     * 物理删除
     *
     * @param document
     * @param introspectedTable table表信息
     * @param deleteByEle
     */
    private void generatePhysicsDelete(Document document, IntrospectedTable introspectedTable, XmlElement deleteByEle) {
        // 主键字段
        IntrospectedColumn primaryKeyColumn = getPrimaryKeyColumn(introspectedTable);

        deleteByEle.addElement(new TextElement("delete from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        deleteByEle.addElement(new TextElement("WHERE ID in"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "ids"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("open", "("));
        foreachElement.addAttribute(new Attribute("close", ")"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement("#{item,jdbcType=" + primaryKeyColumn.getJdbcTypeName() + "}"));

        deleteByEle.addElement(foreachElement);
    }

}
