<#--

    Copyright 2015-2020 the original author or authors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<?xml version="1.0" encoding="UTF-8"?>
<template>
    <!-- #############################################################################################################
    /**
     * Java文件注释
     *
     * @param compilationUnit
     *            the compilation unit
     */
    -->
    <comment ID="addJavaFileComment">
<![CDATA[
/*
 * Copyright 2015-${.now?string("yyyy")} the original author or authors.
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
]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * Xml节点注释
     *
     * @param xmlElement
     *            the xml element
     */
    -->
    <comment ID="addComment"><![CDATA[
        <!--
  WARNING - ${mgb}
  This element is automatically generated by MyBatis Generator, do not modify.
  @project https://github.com/itfsw/mybatis-generator-plugin
-->
        ]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * Xml根节点注释
     *
     * @param rootElement
     *            the root element
     */
    -->
    <comment ID="addRootComment"></comment>

    <!-- #############################################################################################################
    /**
     * Java 字段注释(非生成Model对应表字段时，introspectedColumn可能不存在)
     *
     * @param field
     *            the field
     * @param introspectedTable
     *            the introspected table
     * @param introspectedColumn
     *            the introspected column
     */
    -->
    <comment ID="addFieldComment"><![CDATA[
<#if introspectedColumn??>
/**
    <#if introspectedColumn.remarks?? && introspectedColumn.remarks != ''>
        <#list introspectedColumn.remarks?split("\n") as remark>
 * ${remark}
        </#list>
    </#if>
 */
<#else>
/**
 *
 */
</#if>
        ]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * 表Model类注释
     *
     * @param topLevelClass
     *            the top level class
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addModelClassComment">
<![CDATA[
/**
 * 类${(introspectedTable.fullyQualifiedTable.domainObjectName)!}的实现描述：TODO 类实现描述
 *
 * <pre>
 *  <strong>table</strong>: ${introspectedTable.fullyQualifiedTable}
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> ${.now?string("yyyy-MM-dd HH:mm:ss")}
 * @version v2.3.2
 * @since JDK 1.8
 */
]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * 类注释
     *
     * @param innerClass
     *            the inner class
     * @param introspectedTable
     *            the introspected table
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    -->
    <comment ID="addClassComment">
<![CDATA[
/**
 * 类${innerClass}的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> ${.now?string("yyyy-MM-dd hh:mm:SS")}
 * @version v2.3.2
 * @since JDK 1.8
 */
]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * 枚举注释
     *
     * @param innerEnum
     *            the inner enum
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addEnumComment"><![CDATA[
        /**
        * This enum was generated by MyBatis Generator.
        * This enum corresponds to the database table ${introspectedTable.fullyQualifiedTable}
        *
        * ${mgb}
        * @project https://github.com/itfsw/mybatis-generator-plugin
        */
        ]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * 接口注释(itfsw插件新增类型)
     *
     * @param innerInterface
     *            the inner interface
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addInterfaceComment"><![CDATA[
        /**
        * This interface was generated by MyBatis Generator.
        * This interface corresponds to the database table ${introspectedTable.fullyQualifiedTable}
        *
        * ${mgb}
        * @project https://github.com/itfsw/mybatis-generator-plugin
        */
        ]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * getter方法注释(introspectedColumn可能不存在)
     *
     * @param method
     *            the method
     * @param introspectedTable
     *            the introspected table
     * @param introspectedColumn
     *            the introspected column
     */
    -->
    <comment ID="addGetterComment"><![CDATA[
<#if introspectedColumn??>
/**
 * @return the value of ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 */
<#else>
/**
 * This method was generated by MyBatis Generator.
 * This method returns the value of the field ${method.name?replace("get","")?replace("is", "")?uncap_first}
 *
 * @return the value of field ${method.name?replace("get","")?replace("is", "")?uncap_first}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
</#if>
        ]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * setter方法注释(introspectedColumn可能不存在)
     *
     * @param method
     *            the method
     * @param introspectedTable
     *            the introspected table
     * @param introspectedColumn
     *            the introspected column
     */
    -->
    <comment ID="addSetterComment"><![CDATA[
<#if introspectedColumn??>
/**
 * @param ${method.parameters[0].name} the value for ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 */
<#else>
/**
 * This method was generated by MyBatis Generator.
 * This method sets the value of the field ${method.name?replace("set","")?uncap_first}
 *
 * @param ${method.parameters[0].name} the value for field ${method.name?replace("set","")?uncap_first}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
</#if>
        ]]>
    </comment>

    <!-- #############################################################################################################
    /**
     * 方法注释
     *
     * @param method
     *            the method
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addGeneralMethodComment"></comment>
</template>
