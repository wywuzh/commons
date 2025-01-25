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
package io.github.wywuzh.commons.core.common;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
 * 类CharacterSetTest的实现描述：字符集编码
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-01-25 18:02:23
 * @version v2.3.1
 * @since JDK 1.8
 */
@Slf4j
public class CharacterSetTest {

    @Test
    public void availableCharsets() {
        SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
        for (Iterator<Map.Entry<String, Charset>> iterator = availableCharsets.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Charset> entry = iterator.next();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

}
