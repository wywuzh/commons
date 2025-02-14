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
package io.github.wywuzh.commons.components.easyui.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 类Combobox的实现描述：easyui-combobox
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-05-24 21:09:43
 * @version v2.4.5
 * @since JDK 1.8
 */
@Data
public class Combobox implements Serializable {
    private static final long serialVersionUID = 2200870702520865560L;

    private String id;
    private String text;

    public Combobox() {
    }

    public Combobox(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
