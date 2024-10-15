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
package io.github.wywuzh.commons.mybatis.generator.utils;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 类MbgPluginUtils的实现描述：MyBatis Generator Plugin(MBG) 工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2024-01-18 17:01:39
 * @version v2.7.8
 * @since JDK 1.8
 */
public class MbgPluginUtils {

    /**
     * 添加foreach节点(in)
     *
     * @param rootElement        父节点
     * @param introspectedColumn 列信息
     * @param columnName         表字段名
     * @param javaProperty       Java字段名
     */
    public static void addElementForIn(XmlElement rootElement, IntrospectedColumn introspectedColumn, String columnName, String javaProperty) {
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

        rootElement.addElement(mapKeyIfElement);
    }

    /**
     * 添加foreach节点(not in)
     *
     * @param rootElement        父节点
     * @param introspectedColumn 列信息
     * @param columnName         表字段名
     * @param javaProperty       Java字段名
     */
    public static void addElementForNotIn(XmlElement rootElement, IntrospectedColumn introspectedColumn, String columnName, String javaProperty) {
        // 第二步：添加map中key的空判断
        XmlElement mapKeyIfElement = new XmlElement("if");
        mapKeyIfElement.addAttribute(new Attribute("test", javaProperty + " != null and " + javaProperty + ".size &gt; 0"));
        mapKeyIfElement.addElement(new TextElement("and " + columnName + " not in"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", javaProperty));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("open", "("));
        foreachElement.addAttribute(new Attribute("close", ")"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement("#{item}"));

        mapKeyIfElement.addElement(foreachElement);

        rootElement.addElement(mapKeyIfElement);
    }

}
