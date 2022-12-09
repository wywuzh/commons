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
import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 类BatchInsertPlugin的实现描述：itfsw批量新增SQL插件精简版，去掉batchInsertSelective接口。去掉ModelColumnPlugin插件依赖
 *
 * <pre class="code">
 * <strong>enableMergeInto使用方式</strong>：
 * 1. 添加&lt;plugin&gt;，在plugin中配置的property属性做为全局属性存在
 *     &lt;plugin type="com.github.wywuzh.commons.mybatis.generator.plugins.SelectByParamsPlugin"&gt;
 *         &lt;!-- 是否启用merge into格式进行插入，默认为false --&gt;
 *         &lt;property name="enableMergeInto" value="true"/&gt;
 *     &lt;/plugin&gt;
 * 2. 若需要对某张表单独定制，可在该table下配置进行覆盖
 *     &lt;table tableName="goods_brand" domainObjectName="GoodsBrand"
 *                enableUpdateByExample="false" enableDeleteByExample="false"
 *                enableCountByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"&gt;
 *         &lt;!-- 是否启用merge into格式进行插入，默认为false --&gt;
 *         &lt;property name="enableMergeInto" value="true"/&gt;
 *     &lt;/table&gt;
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/26 18:49
 * @version v2.1.1
 * @since JDK 1.8
 */
public class BatchInsertPlugin extends BasePlugin {
    public static final String METHOD_BATCH_INSERT = "batchInsert";  // 方法名
    public static final String PRO_ALLOW_MULTI_QUERIES = "allowMultiQueries";   // property allowMultiQueries
    public static final String ENABLE_MERGE_INTO = "enableMergeInto"; // 是否启用merge into格式进行插入

    private boolean allowMultiQueries = false;  // 是否允许多sql提交
    /**
     * 是否启用merge into格式进行插入，默认为false
     *
     * @since 2.3.2
     */
    private boolean enableMergeInto = false;

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

        // 是否启用merge into格式进行插入，默认为false
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
     * @since v2.4.5
     */
    private boolean enableMergeInto(TableConfiguration tableConfiguration) {
        // 如果在<table>中有配置，以该配置为准，否则读取全局配置
        String enableMergeInto = tableConfiguration.getProperty(ENABLE_MERGE_INTO);
        if (StringUtils.isNotBlank(enableMergeInto)) {
            return Boolean.valueOf(enableMergeInto);
        }
        return this.enableMergeInto;
    }

    /**
     * {@inheritDoc}
     */
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
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 1. batchInsert
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        listType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        Method mBatchInsert = JavaElementGeneratorTools.generateMethod(METHOD_BATCH_INSERT, JavaVisibility.DEFAULT, FullyQualifiedJavaType.getIntInstance(),
                new Parameter(listType, "list", "@Param(\"list\")"));
        commentGenerator.addGeneralMethodComment(mBatchInsert, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mBatchInsert);
        logger.debug("itfsw(批量插入插件):" + interfaze.getType().getShortName() + "增加batchInsert方法。");
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
        // 1. batchInsert
        XmlElement batchInsertEle = new XmlElement("insert");
        batchInsertEle.addAttribute(new Attribute("id", METHOD_BATCH_INSERT));
        // 参数类型
        batchInsertEle.addAttribute(new Attribute("parameterType", "map"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(batchInsertEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(batchInsertEle, introspectedTable);

        Properties properties = getProperties();

        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (DRIVER_ORACLE.equalsIgnoreCase(driverClass) || DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass)) {
            boolean hasClobOrBlob = hasClobOrBlob(introspectedTable);
            logger.info("table={} 检查该表是否存在clob、blob类型字段 =>{}", introspectedTable.getFullyQualifiedTableNameAtRuntime(), hasClobOrBlob);
            if (hasClobOrBlob) {
                // 说明：当表的字段中存在clob、blog大文本类型的字段时，需要采用begin ;end;语句
                generateOracleForBeginEnd(document, introspectedTable, batchInsertEle);
            } else {
                // 生成Oracle批量新增SQL
                // 检查是否启用merge into格式进行插入，默认为false
                if (enableMergeInto(introspectedTable.getTableConfiguration())) {
                    // 启用merge into格式进行插入
                    generateOracleForMergeInto(document, introspectedTable, batchInsertEle);
                } else {
                    generateOracle(document, introspectedTable, batchInsertEle);
                }
            }
        } else {
            // 生成MySQL批量新增SQL
            generateMySQL(document, introspectedTable, batchInsertEle);
        }

        document.getRootElement().addElement(batchInsertEle);
        logger.debug("itfsw(批量插入插件):" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加batchInsert实现方法。");
        return true;
    }

    @Deprecated
    private void generateOracleForInsertAll(Document document, IntrospectedTable introspectedTable, XmlElement batchInsertEle) {
        batchInsertEle.addElement(new TextElement("insert all"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));

        foreachElement.addElement(new TextElement("into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        // column构建
        for (Element element : XmlElementGeneratorTools.generateKeys(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), true)) {
            foreachElement.addElement(element);
        }
        // values 构建
        foreachElement.addElement(new TextElement("values"));
        for (Element element : XmlElementGeneratorTools.generateValues(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), "item.")) {
            foreachElement.addElement(element);
        }

        batchInsertEle.addElement(foreachElement);

        batchInsertEle.addElement(new TextElement("select 1 from dual"));
    }

    private void generateOracle(Document document, IntrospectedTable introspectedTable, XmlElement batchInsertEle) {
        batchInsertEle.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        // column构建
        for (Element element : XmlElementGeneratorTools.generateKeys(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), true)) {
            batchInsertEle.addElement(element);
        }

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", "union all"));

        // 构建虚拟表
        foreachElement.addElement(new TextElement("select"));
        for (Element element : XmlElementGeneratorTools.generateValues(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), "item.", false)) {
            foreachElement.addElement(element);
        }
        foreachElement.addElement(new TextElement("from dual"));

        batchInsertEle.addElement(foreachElement);
    }

    private boolean hasClobOrBlob(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : columnList) {
            if (StringUtils.equalsIgnoreCase(introspectedColumn.getJdbcTypeName(), "clob") || StringUtils.equalsIgnoreCase(introspectedColumn.getJdbcTypeName(), "blob")) {
                return true;
            }
        }
        return false;
    }

    private void generateOracleForBeginEnd(Document document, IntrospectedTable introspectedTable, XmlElement batchInsertEle) {
        batchInsertEle.addElement(new TextElement("begin"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ";"));

        foreachElement.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        // column构建
        for (Element element : XmlElementGeneratorTools.generateKeys(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), true)) {
            foreachElement.addElement(element);
        }

        // 构建虚拟表
        foreachElement.addElement(new TextElement("values("));
        for (Element element : XmlElementGeneratorTools.generateValues(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), "item.", false)) {
            foreachElement.addElement(element);
        }
        foreachElement.addElement(new TextElement(")"));

        batchInsertEle.addElement(foreachElement);
        batchInsertEle.addElement(new TextElement(";end;"));
    }

    private void generateOracleForMergeInto(Document document, IntrospectedTable introspectedTable, XmlElement batchInsertEle) {
        batchInsertEle.addElement(new TextElement("MERGE INTO " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " basic"));
        batchInsertEle.addElement(new TextElement("USING ("));

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

        batchInsertEle.addElement(foreachElement);

        batchInsertEle.addElement(new TextElement(") temp"));
        batchInsertEle.addElement(new TextElement("ON (1=0)"));
        batchInsertEle.addElement(new TextElement("WHEN NOT MATCHED THEN"));
        batchInsertEle.addElement(new TextElement("INSERT ("));

        for (IntrospectedColumn introspectedColumn : columnList) {
            sb.append("basic.").append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));

            if (index < columnTotal) {
                sb.append(", ");
            }
            if (sb.length() > 80) {
                batchInsertEle.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
            index++;
        }
        if (sb.length() > 0) {
            batchInsertEle.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;

        batchInsertEle.addElement(new TextElement(")"));
        batchInsertEle.addElement(new TextElement("VALUES ("));

        for (IntrospectedColumn introspectedColumn : columnList) {
            sb.append("temp.").append(introspectedColumn.getJavaProperty());

            if (index < columnTotal) {
                sb.append(", ");
            }
            if (sb.length() > 80) {
                batchInsertEle.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
            index++;
        }
        if (sb.length() > 0) {
            batchInsertEle.addElement(new TextElement(sb.toString()));
        }
        // 重置
        sb.setLength(0);
        index = 0;

        batchInsertEle.addElement(new TextElement(")"));

    }

    private void generateMySQL(Document document, IntrospectedTable introspectedTable, XmlElement batchInsertEle) {
        batchInsertEle.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        for (Element element : XmlElementGeneratorTools.generateKeys(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), true)) {
            batchInsertEle.addElement(element);
        }

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));

        for (Element element : XmlElementGeneratorTools.generateValues(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), "item.")) {
            foreachElement.addElement(element);
        }

        // values 构建
        batchInsertEle.addElement(new TextElement("values"));
        batchInsertEle.addElement(foreachElement);
    }
}
