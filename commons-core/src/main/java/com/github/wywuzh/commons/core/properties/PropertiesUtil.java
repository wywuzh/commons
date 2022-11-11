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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类PropertiesUtil.java的实现描述：读取properties属性文件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:58:42
 * @version v1.0.0
 * @see java.util.Properties
 * @since JDK 1.7
 */
public class PropertiesUtil {
    private static final Log logger = LogFactory.getLog(PropertiesUtil.class);
    public static final String DEFAULT_FILE_NAME = "application.properties";

    private static Properties properties = null;

    /**
     * 初始化配置文件（属性文件采用默认设置）
     *
     * @return
     */
    public static Properties getInstance() {
        return getInstance(DEFAULT_FILE_NAME);
    }

    /**
     * 初始化配置文件
     *
     * @param fileName
     *                     资源文件名称
     * @return
     */
    public static Properties getInstance(String fileName) {
        Properties properties = null;
        try {
            Thread currentThread = Thread.currentThread();
            InputStream inputStream = currentThread.getContextClassLoader().getResourceAsStream(fileName);

            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return properties;
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
    public static String getProperty(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        if (null == properties) {
            properties = getInstance();
        }
        return properties.getProperty(key);
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
    public static String getProperty(String key, String defaultValue) {
        if (null == properties) {
            properties = getInstance();
        }
        return properties.getProperty(key, defaultValue);
    }

    public static void main(String[] args) {
        try {
            Thread currentThread = Thread.currentThread();
            InputStream resourceAsStream = currentThread.getContextClassLoader().getResourceAsStream(DEFAULT_FILE_NAME);

            Properties properties = new Properties();
            properties.load(resourceAsStream);

            String property = properties.getProperty("name");
            System.out.println(property);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        System.out.println(getProperty("version2"));
        System.out.println(getProperty("version", "1234"));

        Properties instance = getInstance();
        System.out.println(instance.getProperty("version"));

    }
}
