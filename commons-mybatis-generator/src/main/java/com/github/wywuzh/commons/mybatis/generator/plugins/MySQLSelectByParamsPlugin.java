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

import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 类MySQLSelectByParamsPlugin的实现描述：自定义select查询插件(MySQL)
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-11-17 15:42:43
 * @version v2.7.0
 * @since JDK 1.8
 */
public class MySQLSelectByParamsPlugin extends SelectByParamsPlugin {

    /**
     * xml映射器文件sql：分页
     *
     * @param document
     * @param introspectedTable    table表信息
     * @param selectPagerEle       分页查询节点
     * @param includeConditionsEle 引入where条件
     * @since v2.7.0
     */
    @Override
    protected void generateSqlMapForPager(Document document, IntrospectedTable introspectedTable, XmlElement selectPagerEle, XmlElement includeConditionsEle) {
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

    /**
     * 生成模糊查询SQL
     *
     * @param javaProperty java属性
     * @return
     * @since v2.7.0
     */
    @Override
    protected String resolveDbLikeSql(String javaProperty) {
        // 生成MySQL模糊查询SQL
        String likeSql = " like concat('%'," + javaProperty + ",'%')";
        return likeSql;
    }
}
