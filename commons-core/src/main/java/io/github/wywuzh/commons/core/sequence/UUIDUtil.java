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
package io.github.wywuzh.commons.core.sequence;

import java.io.Serializable;
import java.util.UUID;

/**
 * UUID工具类
 *
 * @author <a href="mailto:wywuzh@163.com">wywuzh</a>
 * @version 1.0, 07/29/2013
 * @since JDK 1.6
 */
public class UUIDUtil implements Serializable {
    private static final long serialVersionUID = -741325524978030957L;

    /**
     * 获取随机UUID值
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年11月28日 上午11:32:34
     * @return
     */
    public static UUID getUUID() {
        return UUID.randomUUID();
    }

    /**
     * 根据指定name生成UUID
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年11月28日 上午11:32:37
     * @param name
     * @return
     */
    public static UUID getUUID(String name) {
        return UUID.fromString(name);
    }

    /**
     * 获取32个字符长度的UUID
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年11月28日 上午11:34:40
     * @return
     */
    public static String getUUID32() {
        return getUUID36().replaceAll("-", "");
    }

    /**
     * 获取36个字符长度的UUID
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年11月28日 上午11:34:43
     * @return
     */
    public static String getUUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取指定name的32个字符长度的UUID
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年11月28日 上午11:33:30
     * @param name
     * @return
     */
    public static String getUUID32(String name) {
        return getUUID36(name).replaceAll("-", "");
    }

    /**
     * 获取指定name的36个字符长度的UUID
     *
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年11月28日 上午11:33:33
     * @param name
     * @return
     */
    public static String getUUID36(String name) {
        return getUUID(name).toString();
    }

    public static void main(String[] args) {
        System.out.println(getUUID36());
        System.out.println(getUUID36().length());

        System.out.println(getUUID32());
        System.out.println(getUUID32().length());
    }

}
