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
package io.github.wywuzh.commons.mybatis.generator.types;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.springframework.context.annotation.Configuration;

/**
 * 类JavaTypeResolverSupport的实现描述：Java类型映射扩展插件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/16 17:16
 * @version v2.1.1
 * @since JDK 1.8
 */
@Configuration
public class JavaTypeResolverSupport extends JavaTypeResolverDefaultImpl {

    public JavaTypeResolverSupport() {
        super();
        // 把数据库的 TINYINT 映射成 Integer
        super.typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        // 把数据库的 TINYINT 映射成 Integer
        super.typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
