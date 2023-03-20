/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.os;

import org.apache.commons.lang3.StringUtils;

/**
 * 类OSPlatform的实现描述：操作系统平台枚举类
 *
 * <pre>
 * 参考：
 * https://blog.csdn.net/fangchao2011/article/details/88785637
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-13 16:54:59
 * @version v2.3.6
 * @since JDK 1.8
 */
public enum OSPlatform {

    Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows("Windows"), OS2("OS/2"), Solaris("Solaris"), SunOS("SunOS"), MPEiX("MPE/iX"), HP_UX("HP-UX"), AIX("AIX"), OS390(
            "OS/390"), FreeBSD("FreeBSD"), Irix("Irix"), Digital_Unix("Digital Unix"), NetWare_411("NetWare"), OSF1("OSF1"), OpenVMS("OpenVMS"), Others("Others");

    /**
     * 操作系统名称
     */
    private String name;

    OSPlatform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OSPlatform findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (OSPlatform item : values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        return null;
    }

}
