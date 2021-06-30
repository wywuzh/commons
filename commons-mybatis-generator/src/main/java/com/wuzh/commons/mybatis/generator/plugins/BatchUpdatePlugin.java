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
package com.wuzh.commons.mybatis.generator.plugins;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;
import com.itfsw.mybatis.generator.plugins.utils.FormatTools;
import com.itfsw.mybatis.generator.plugins.utils.JavaElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;
import java.util.Properties;

/**
 * 类BatchUpdatePlugin的实现描述：批量更新SQL插件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-06-30 13:45:06
 * @version v2.4.5
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator
 * @since JDK 1.8
 */
public class BatchUpdatePlugin extends BasePlugin {
    public static final String METHOD_BATCH_UPDATE = "batchUpdate";  // 方法名
    public static final String PRO_ALLOW_MULTI_QUERIES = "allowMultiQueries";   // property allowMultiQueries

    private boolean allowMultiQueries = false;  // 是否允许多sql提交

    @Override
    public boolean validate(List<String> warnings) {
        // 插件使用前提是数据库为MySQL、MariaDB、Oracle或者SQLserver，因为返回主键使用了JDBC的getGenereatedKeys方法获取主键
        String driverClass = this.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (DRIVER_MySQL.equalsIgnoreCase(driverClass) == false
                && DRIVER_MySQL6.equalsIgnoreCase(driverClass) == false
                && DRIVER_MariaDB.equalsIgnoreCase(driverClass) == false
                && DRIVER_ORACLE.equalsIgnoreCase(driverClass) == false
                && DRIVER_ORACLE_OLD.equalsIgnoreCase(driverClass) == false
                && DRIVER_MICROSOFT_JDBC.equalsIgnoreCase(driverClass) == false
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
            warnings.add("itfsw:插件" + this.getClass().getTypeName() + "插件您开启了allowMultiQueries支持，注意在jdbc url 配置中增加“allowMultiQueries=true”支持（不怎么建议使用该功能，开启多sql提交会增加sql注入的风险，请确保你所有sql都使用MyBatis书写，请不要使用statement进行sql提交）！");
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
        Method mBatchUpdate = JavaElementGeneratorTools.generateMethod(
                METHOD_BATCH_UPDATE,
                JavaVisibility.DEFAULT,
                FullyQualifiedJavaType.getIntInstance(),
                new Parameter(listType, "list", "@Param(\"list\")")
        );
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
            // 生成Oracle批量新增SQL
            generateOracle(document, introspectedTable, batchUpdateEle);
        } else {
            // 生成MySQL批量新增SQL
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

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns())) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

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

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            foreachElement.addElement(new TextElement(sb.toString()));
        }

        batchUpdateEle.addElement(foreachElement);

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

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns())) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

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

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            foreachElement.addElement(new TextElement(sb.toString()));
        }

        batchUpdateEle.addElement(foreachElement);

    }

}