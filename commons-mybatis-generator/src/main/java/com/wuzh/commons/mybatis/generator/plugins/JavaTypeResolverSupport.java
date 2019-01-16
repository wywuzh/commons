package com.wuzh.commons.mybatis.generator.plugins;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.springframework.context.annotation.Configuration;

import java.sql.Types;

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
    }
}
