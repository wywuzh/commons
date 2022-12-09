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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 类BaseMapperPlugin的实现描述：BaseMapper基类插件。参考网址：https://blog.csdn.net/u011781521/article/details/78697775
 *
 * <pre class="code">
 * 使用方式：
 * 1. 添加&lt;plugin&gt;
 *     &lt;plugin type="com.github.wywuzh.commons.mybatis.generator.plugins.BaseMapperPlugin"&gt;
 *         &lt;property name="targetProject" value="src/main/java"/&gt;
 *         &lt;property name="targetPackage" value="com.github.wywuzh.platform.frame.core.mapper"/&gt;
 *     &lt;/plugin&gt;
 * 2. 修改javaClientGenerator，添加rootInterface属性
 *    &lt;javaClientGenerator type="XMLMAPPER" targetPackage="com.github.wywuzh.platform.frame.core.mapper" targetProject="src/main/java"&gt;
 *         &lt;property name="enableSubPackages" value="true"/&gt;
 *         &lt;property name="rootInterface" value="com.github.wywuzh.commons.mybatis.generator.mapper.BaseMapper"/&gt;
 *    &lt;/javaClientGenerator&gt;
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/26 9:35
 * @version v2.1.1
 * @since JDK 1.8
 */
public class BaseMapperPlugin extends BasePlugin {
    private static final String DEFAULT_DAO_SUPER_CLASS = "com.github.wywuzh.commons.mybatis.generator.mapper.BaseMapper";
    private static final String DEFAULT_DAO_SUPER_CLASS_NAME = "BaseMapper";
    private static final String DEFAULT_EXPAND_DAO_SUPER_CLASS = "com.github.wywuzh.commons.mybatis.generator.mapper.BaseExpandMapper";
    private String daoTargetDir;
    private String daoTargetPackage;

    private String daoSuperClass;
    private String daoSuperClassName;

    // 扩展
    private String expandDaoTargetPackage;
    private String expandDaoSuperClass;

    private ShellCallback shellCallback = null;

    public BaseMapperPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    /**
     * 验证参数是否有效
     *
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
        daoTargetDir = properties.getProperty("targetProject");
        boolean valid = StringUtility.stringHasValue(daoTargetDir);

        daoTargetPackage = properties.getProperty("targetPackage");
        boolean valid2 = StringUtility.stringHasValue(daoTargetPackage);

        daoSuperClass = properties.getProperty("daoSuperClass");
        if (!StringUtility.stringHasValue(daoSuperClass)) {
            daoSuperClass = DEFAULT_DAO_SUPER_CLASS;
        }
        daoSuperClassName = properties.getProperty("daoSuperClassName");
        if (!StringUtility.stringHasValue(daoSuperClassName)) {
            daoSuperClassName = DEFAULT_DAO_SUPER_CLASS_NAME;
        }

        expandDaoTargetPackage = properties.getProperty("expandTargetPackage");
        expandDaoSuperClass = properties.getProperty("expandDaoSuperClass");
        if (!StringUtility.stringHasValue(expandDaoSuperClass)) {
            expandDaoSuperClass = DEFAULT_EXPAND_DAO_SUPER_CLASS;
        }
        return valid && valid2;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        JavaFormatter javaFormatter = context.getJavaFormatter();
        List<GeneratedJavaFile> generatedJavaFileList = new ArrayList<GeneratedJavaFile>();
        for (GeneratedJavaFile generatedJavaFile : introspectedTable.getGeneratedJavaFiles()) {
            CompilationUnit unit = generatedJavaFile.getCompilationUnit();
            FullyQualifiedJavaType baseModelJavaType = unit.getType();

            String shortName = baseModelJavaType.getShortName();

            GeneratedJavaFile mapperJavafile = null;

            if (shortName.endsWith("Mapper")) { // 扩展Mapper
                if (StringUtility.stringHasValue(expandDaoTargetPackage)) {
                    Interface mapperInterface = new Interface(expandDaoTargetPackage + "." + shortName.replace("Mapper", "ExpandMapper"));
                    mapperInterface.setVisibility(JavaVisibility.PUBLIC);

                    FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(expandDaoSuperClass);
                    mapperInterface.addImportedType(daoSuperType);
                    mapperInterface.addSuperInterface(daoSuperType);

                    mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, javaFormatter);
                    try {
                        File mapperDir = shellCallback.getDirectory(daoTargetDir, daoTargetPackage);
                        File mapperFile = new File(mapperDir, mapperJavafile.getFileName());
                        // 文件不存在
                        if (!mapperFile.exists()) {
                            generatedJavaFileList.add(mapperJavafile);
                        }
                    } catch (ShellException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!shortName.endsWith("Example")) { // CRUD Mapper
                Interface mapperInterface = new Interface(daoTargetPackage + "." + shortName + "Mapper");

                mapperInterface.setVisibility(JavaVisibility.PUBLIC);

                // import引入依赖类
                mapperInterface.addImportedType(baseModelJavaType);
                mapperInterface.addImportedType(new FullyQualifiedJavaType(daoSuperClass));
                // 继承接口设置类型
                FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(daoSuperClassName);
                // 添加泛型支持
                daoSuperType.addTypeArgument(baseModelJavaType);
                // 获取主键字段类型
                List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
                FullyQualifiedJavaType fullyQualifiedJavaType = primaryKeyColumns.get(0).getFullyQualifiedJavaType();
                daoSuperType.addTypeArgument(fullyQualifiedJavaType);
                mapperInterface.addSuperInterface(daoSuperType);

                mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, javaFormatter);
                generatedJavaFileList.add(mapperJavafile);
            }
        }
        return generatedJavaFileList;
    }
}
