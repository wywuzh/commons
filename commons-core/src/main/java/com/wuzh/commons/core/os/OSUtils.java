/*
 * Copyright 2015-2021 the original author or authors.
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
package com.wuzh.commons.core.os;

/**
 * 类OSUtils的实现描述：操作系统工具
 * <pre>
 * 参考：
 * https://blog.csdn.net/fangchao2011/article/details/88785637
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-13 16:54:16
 * @version v2.3.6
 * @since JDK 1.8
 */
public class OSUtils {

    public static String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return OS_NAME.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS_NAME.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS_NAME.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS_NAME.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS_NAME.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS_NAME.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS_NAME.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS_NAME.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS_NAME.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS_NAME.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS_NAME.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS_NAME.indexOf("digital") >= 0 && OS_NAME.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS_NAME.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS_NAME.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS_NAME.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static OSPlatform getOSPlatform() {
        return OSPlatform.findByName(OS_NAME);
    }

}