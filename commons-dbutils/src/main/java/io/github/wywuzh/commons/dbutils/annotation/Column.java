/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.dbutils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类Column.java的实现描述：表字段信息
 * <p>
 * 注：本<code>Column</code>的作用只为服务于DbUtils工具类库的SQL解析，如果想要使用Column的完整功能请使用<code>javax.persistence.Column</code>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月20日 下午9:51:38
 * @version v1.0.0
 * @since JDK 1.7
 */
@Target({
        ElementType.METHOD, ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Column {

    /**
     * 表字段名
     *
     * @return 表字段名
     */
    String name();
}
