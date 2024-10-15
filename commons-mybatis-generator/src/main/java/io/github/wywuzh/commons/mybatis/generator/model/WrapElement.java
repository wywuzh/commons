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
package io.github.wywuzh.commons.mybatis.generator.model;

import org.mybatis.generator.api.dom.xml.Element;

/**
 * 类WrapElement的实现描述：换行
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2024-01-19 11:15:12
 * @version v2.7.8
 * @since JDK 1.8
 */
public class WrapElement extends Element {

    @Override
    public String getFormattedContent(int indentLevel) {
        return "\n";
    }

}
