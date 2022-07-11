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
package com.github.wywuzh.commons.core.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类PropertyResourceBundleUtil.java的实现描述：读取属性文件信息
 *
 * <pre>
 *  利用 java.util.PropertyResourceBundle 读取属性文件
 * </pre>
 *
 * @author 伍章红 2015-8-12 上午10:56:17
 * @version v1.0.0
 * @see java.util.ResourceBundle
 * @see java.util.PropertyResourceBundle
 * @since JDK 1.7.0_71
 */
public class PropertyResourceBundleUtil {
    private static final Log logger = LogFactory.getLog(PropertyResourceBundleUtil.class);

    public static final String DEFAULT_FILE_NAME = "commons-core.properties";

    private static ResourceBundle resourceBundle = null;

    /**
     * 初始化配置文件（属性文件采用默认设置）
     *
     * @return
     */
    public static ResourceBundle getInstance() {
        logger.info("初始化配置文件（属性文件采用默认设置）");
        return getInstance(DEFAULT_FILE_NAME);
    }

    /**
     * 初始化配置文件
     *
     * @return
     */
    public static ResourceBundle getInstance(String fileName) {
        logger.info("初始化配置文件（属性文件采用默认设置）");
        ResourceBundle resourceBundle = null;
        try {
            Thread currentThread = Thread.currentThread();
            InputStream resourceAsStream = currentThread.getContextClassLoader().getResourceAsStream(fileName);
            resourceBundle = new PropertyResourceBundle(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return resourceBundle;
    }

    /**
     * 根据指定的key获取value值。
     *
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回null
     * </pre>
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == resourceBundle) {
            resourceBundle = getInstance();
        }

        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        }
        return null;
    }

    /**
     * 根据指定的key获取value值。
     *
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key不存在，则返回指定的defaultValue值
     * </pre>
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == resourceBundle) {
            resourceBundle = getInstance();
        }

        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        }
        return defaultValue;
    }

    public static void main(String[] args) {
        ResourceBundle resourceBundle = getInstance();
        System.out.println(resourceBundle.getString("version"));
    }
}
