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
package com.github.wywuzh.commons.dingtalk.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 类Language的实现描述：语言
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-02-11 15:16:15
 * @version v2.3.8
 * @since JDK 1.8
 */
public enum Language {

    zh_CN("zh_CN", "中文"),
    en_US("en_US", "英文"),
    ;

    /**
     * 语言
     */
    private String lang;
    /**
     * 语言描述
     */
    private String desc;

    Language(String lang, String desc) {
        this.lang = lang;
        this.desc = desc;
    }

    public String getLang() {
        return lang;
    }

    public String getDesc() {
        return desc;
    }

    public static Language findByLang(String lang) {
        if (StringUtils.isBlank(lang)) {
            return null;
        }
        for (Language item : values()) {
            if (StringUtils.equalsIgnoreCase(item.lang, lang)) {
                return item;
            }
        }
        return null;
    }
}
