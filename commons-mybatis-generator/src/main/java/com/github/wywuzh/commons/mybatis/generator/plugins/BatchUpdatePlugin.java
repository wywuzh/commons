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
package com.github.wywuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;
import com.itfsw.mybatis.generator.plugins.utils.FormatTools;
import com.itfsw.mybatis.generator.plugins.utils.JavaElementGeneratorTools;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
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
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 类BatchUpdatePlugin的实现描述：批量更新SQL插件
 * 
 * <pre class="code">
 * <strong>enableMergeInto使用方式</strong>：
 * 1. 添加&lt;plugin&gt;，在plugin中配置的property属性做为全局属性存在
 *     &lt;plugin type="com.github.wywuzh.commons.mybatis.generator.plugins.SelectByParamsPlugin"&gt;
 *         &lt;!-- 是否启用merge into格式进行更新，默认为false --&gt;
 *         &lt;property name="enableMergeInto" value="true"/&gt;
 *     &lt;/plugin&gt;
 * 2. 若需要对某张表单独定制，可在该table下配置进行覆盖
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- 是否启用merge into格式进行更新，默认为false --&gt;
 *         &lt;property name="enableMergeInto" value="true"/&gt;
 *     &lt;/table&gt;
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-06-30 13:45:06
 * @version v2.4.5
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator
 * @since JDK 1.8
 */
public class BatchUpdatePlugin extends BasePlugin {
    public static final String METHOD_BATCH_UPDATE = "batchUpdate";  // 方法名
    public static final String PRO_ALLOW_MULTI_QUERIES = "allowMultiQueries";   // property allowMultiQueries
    public static final String ENABLE_MERGE_INTO = "enableMergeInto"; // 是否启用merge into格式进行更新

    private boolean allowMultiQueries = false;  // 是否允许多sql提交
    /**
     * 是否启用merge into格式进行更新，默认为false
     */
    private boolean enableMergeInto = false;

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

        // 是否启用merge into格式进行更新，默认为false
        String enableMergeInto = super.getProperties().getProperty(ENABLE_MERGE_INTO);
        if (StringUtils.isNotBlank(enableMergeInto)) {
            this.enableMergeInto = Boolean.valueOf(enableMergeInto);
        }
    }

    /**
     * 检查是否启用merge into
     *
     * @param tableConfiguration table配置
     * @return
     */
    private boolean enableMergeInto(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String enableMergeInto = tableConfiguration.getProperty(ENABLE_MERGE_INTO);
        if (StringUtils.isNotBlank(enableMergeInto)) {
            return Boolean.valueOf(enableMergeInto);
        }
        return this.enableMergeInto;
    }

    @Override
    public boolean validate(List<String> warnings) {
        // 插件使用前提是数据库为MySQL、MariaDB、Oracle或者SQLserver，因为返回主键使用了JDBC的getGenereatedKeys方法获取主键
        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (DRIVER_MySQL.equalsIgnoreCase(driverClass) == false && DRIVER_MySQL6.equalsIgnoreCase(driverClass) == false && DRIVER_MariaDB.equalsIgnoreCase(driverClass) == false
                && DRIVER_ORACLE.equalsIgnoreCase(driverClass) == false && DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass) == false && DRIVER_MICROSOFT_JDBC.equalsIgnoreCase(driverClass) == false
                && DRIVER_MICROSOFT_SQLSERVER.equalsIgnoreCase(driverClass) == false) {
            warnings.add("itfsw:插件" + this.getClass().getTypeName() + "插件使用前提是数据库为MySQL、MariaDB、Oracle或者SQLserver，因为返回主键使用了JDBC的getGenereatedKeys方法获取主键！");
            return false;
        }

        // 插件是否开启了多sql提交
        Properties properties = this.getProperties();
        String allowMultiQueries = properties.getProperty(PRO_ALLOW_MULTI_QUERIES);
        this.allowMultiQueries = allowMultiQueries == null ? false : StringUtility.isTrue(allowMultiQueries);
        if (this.allowMultiQueries) {
            // 提示用户注意信息
            warnings.add("itfsw:插件" + this.getClass().getTypeName()
                    + "插件您开启了allowMultiQueries支持，注意在jdbc url 配置中增加“allowMultiQueries=true”支持（不怎么建议使用该功能，开启多sql提交会增加sql注入的风险，请确保你所有sql都使用MyBatis书写，请不要使用statement进行sql提交）！");
        }

        return super.validate(warnings);
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
        // 1. batchUpdate
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        listType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        Method mBatchUpdate = JavaElementGeneratorTools.generateMethod(METHOD_BATCH_UPDATE, JavaVisibility.DEFAULT, FullyQualifiedJavaType.getIntInstance(),
                new Parameter(listType, "list", "@Param(\"list\")"));
        commentGenerator.addGeneralMethodComment(mBatchUpdate, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mBatchUpdate);
        logger.debug("itfsw(批量插入插件):" + interfaze.getType().getShortName() + "增加batchUpdate方法。");
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
        // 1. batchUpdate
        XmlElement batchUpdateEle = new XmlElement("update");
        batchUpdateEle.addAttribute(new Attribute("id", METHOD_BATCH_UPDATE));
        // 参数类型
        batchUpdateEle.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(batchUpdateEle);

        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (DRIVER_ORACLE.equalsIgnoreCase(driverClass) || DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass)) {
//            // 生成Oracle批量更新SQL
//            generateOracle(document, introspectedTable, batchUpdateEle);
            // 生成Oracle批量更新SQL
            // 检查是否启用merge into格式进行更新，默认为false
            if (enableMergeInto(introspectedTable.getTableConfiguration())) {
                // 启用merge into格式进行插入
                generateOracleForMergeInto(document, introspectedTable, batchUpdateEle);
            } else {
                generateOracle(document, introspectedTable, batchUpdateEle);
            }
        } else {
            // 生成MySQL批量更新SQL
            generateMySQL(document, introspectedTable, batchUpdateEle);
        }

        document.getRootElement().addElement(batchUpdateEle);
        logger.debug("itfsw(批量插入插件):" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加batchUpdate实现方法。");
        return true;
    }

    private void generateOracle(Document document, IntrospectedTable introspectedTable, XmlElement batchUpdateEle) {

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("open", "begin"));
        foreachElement.addAttribute(new Attribute("close", ";end;"));
        foreachElement.addAttribute(new Attribute("separator", ";"));

        foreachElement.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        foreachElement.addElement(dynamicElement);

        List<IntrospectedColumn> introspectedColumnList = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        for (int i = 0; i < introspectedColumnList.size(); i++) {
            IntrospectedColumn introspectedColumn = introspectedColumnList.get(i);
            // 第二步：添加map中key的空判断
            XmlElement mapKeyIfElement = new XmlElement("if");
            // 获取Java字段名
            String javaProperty = introspectedColumn.getJavaProperty("item.");
            // 获取字段类型
            int columnJdbcType = introspectedColumn.getJdbcType();
            mapKeyIfElement.addAttribute(this.getIfAttribute(javaProperty, columnJdbcType));

            // 添加column字段查询条件SQL（这里默认给表的所有字段添加and条件）
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            if (i >= 1) {
                mapKeyIfElement.addElement(new TextElement(columnName + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.") + ","));
            } else {
                mapKeyIfElement.addElement(new TextElement(columnName + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.") + ","));
            }
            dynamicElement.addElement(mapKeyIfElement);
        }

        // update where
        boolean and = false;
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
            foreachElement.addElement(new TextElement(sb.toString()));
        }

        batchUpdateEle.addElement(foreachElement);

    }

    private void generateOracleForMergeInto(Document document, IntrospectedTable introspectedTable, XmlElement batchUpdateEle) {
        batchUpdateEle.addElement(new TextElement("MERGE INTO " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " basic"));
        batchUpdateEle.addElement(new TextElement("USING ("));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", "union all"));

        // 构建虚拟表
        foreachElement.addElement(new TextElement("select"));
        StringBuilder sb = new StringBuilder();
        int index = 0;
        List<IntrospectedColumn> columnList = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        final int columnTotal = columnList.size() - 1;
        for (IntrospectedColumn introspectedColumn : columnList) {
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
            sb.append(" AS ").append(introspectedColumn.getJavaProperty());

            if (index < columnTotal) {
                sb.append(", ");
            }
            if (sb.length() > 80) {
                foreachElement.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
            index++;
        }
        if (sb.length() > 0) {
            foreachElement.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;

        foreachElement.addElement(new TextElement("from dual"));

        batchUpdateEle.addElement(foreachElement);

        batchUpdateEle.addElement(new TextElement(") temp"));
        batchUpdateEle.addElement(new TextElement("ON ( basic.id=temp.id )"));

        // 1、条件匹配时按照更新处理
        batchUpdateEle.addElement(new TextElement("WHEN MATCHED THEN"));
        batchUpdateEle.addElement(new TextElement("update set"));

        List<IntrospectedColumn> introspectedColumnList = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        for (IntrospectedColumn introspectedColumn : introspectedColumnList) {
            sb.append("basic.").append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)).append(" = ").append(introspectedColumn.getJavaProperty("temp."));
            if (index < introspectedColumnList.size() - 1) {
                sb.append(", ");
            }
            batchUpdateEle.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
            index++;
        }
        if (sb.length() > 0) {
            foreachElement.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;
        // update where
        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append("basic.").append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)).append(" = ").append(introspectedColumn.getJavaProperty("temp."));
            batchUpdateEle.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;

        // 2、条件不匹配时按照新增处理
        batchUpdateEle.addElement(new TextElement("WHEN NOT MATCHED THEN"));
        batchUpdateEle.addElement(new TextElement("INSERT ("));

        for (IntrospectedColumn introspectedColumn : columnList) {
            sb.append("basic.").append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));

            if (index < columnTotal) {
                sb.append(", ");
            }
            if (sb.length() > 80) {
                batchUpdateEle.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
            index++;
        }
        if (sb.length() > 0) {
            batchUpdateEle.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;

        batchUpdateEle.addElement(new TextElement(")"));
        batchUpdateEle.addElement(new TextElement("VALUES ("));

        for (IntrospectedColumn introspectedColumn : columnList) {
            sb.append("temp.").append(introspectedColumn.getJavaProperty());

            if (index < columnTotal) {
                sb.append(", ");
            }
            if (sb.length() > 80) {
                batchUpdateEle.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
            index++;
        }
        if (sb.length() > 0) {
            batchUpdateEle.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;

        batchUpdateEle.addElement(new TextElement(")"));

    }

    private void generateMySQL(Document document, IntrospectedTable introspectedTable, XmlElement batchUpdateEle) {

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ";"));

        foreachElement.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        foreachElement.addElement(dynamicElement);

        List<IntrospectedColumn> introspectedColumnList = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        for (int i = 0; i < introspectedColumnList.size(); i++) {
            IntrospectedColumn introspectedColumn = introspectedColumnList.get(i);
            // 第二步：添加map中key的空判断
            XmlElement mapKeyIfElement = new XmlElement("if");
            // 获取Java字段名
            String javaProperty = introspectedColumn.getJavaProperty("item.");
            // 获取字段类型
            int columnJdbcType = introspectedColumn.getJdbcType();
            mapKeyIfElement.addAttribute(this.getIfAttribute(javaProperty, columnJdbcType));

            // 添加column字段查询条件SQL（这里默认给表的所有字段添加and条件）
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            if (i >= 1) {
                mapKeyIfElement.addElement(new TextElement(columnName + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.") + ","));
            } else {
                mapKeyIfElement.addElement(new TextElement(columnName + " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.") + ","));
            }
            dynamicElement.addElement(mapKeyIfElement);
        }

        // update where
        boolean and = false;
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
            foreachElement.addElement(new TextElement(sb.toString()));
        }

        batchUpdateEle.addElement(foreachElement);

    }

    /**
     * 获取 if 标签上的test属性
     *
     * @param javaProperty   Java属性名
     * @param columnJdbcType 数据库字段类型
     * @return
     */
    private Attribute getIfAttribute(String javaProperty, int columnJdbcType) {
        Attribute ifAttribute = null;

        JdbcType jdbcType = JdbcType.forCode(columnJdbcType);
        if (JdbcType.CHAR.equals(jdbcType) || JdbcType.VARCHAR.equals(jdbcType) || JdbcType.LONGVARCHAR.equals(jdbcType) ||

                JdbcType.BINARY.equals(jdbcType) || JdbcType.VARBINARY.equals(jdbcType) || JdbcType.LONGVARBINARY.equals(jdbcType) ||

                JdbcType.BLOB.equals(jdbcType) || JdbcType.CLOB.equals(jdbcType) ||

                JdbcType.NVARCHAR.equals(jdbcType) || JdbcType.NCHAR.equals(jdbcType) || JdbcType.NCLOB.equals(jdbcType) || JdbcType.LONGNVARCHAR.equals(jdbcType)) {
            ifAttribute = new Attribute("test", "@org.apache.commons.lang3.StringUtils@isNotEmpty(" + javaProperty + ")");
        } else if (JdbcType.BIT.equals(jdbcType) || JdbcType.TINYINT.equals(jdbcType) || JdbcType.SMALLINT.equals(jdbcType) || JdbcType.INTEGER.equals(jdbcType) || JdbcType.BIGINT.equals(jdbcType)
                || JdbcType.FLOAT.equals(jdbcType) || JdbcType.REAL.equals(jdbcType) || JdbcType.DOUBLE.equals(jdbcType) || JdbcType.NUMERIC.equals(jdbcType) || JdbcType.DECIMAL.equals(jdbcType)) {
            ifAttribute = new Attribute("test", javaProperty + " != null");
        } else {
            ifAttribute = new Attribute("test", javaProperty + " != null");
        }
        return ifAttribute;
    }

}
