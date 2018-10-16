/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.properties;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 类ResourceBundleUtil.java的实现描述：读取资源文件信息
 * 
 * <pre>
 *  利用 java.util.ResourceBundle 读取属性文件
 * </pre>
 * 
 * @author 伍章红 2015-8-12 上午10:08:51
 * @version v1.0.0
 * @see java.util.ResourceBundle
 * @since JDK 1.7.0_71
 */
public class ResourceBundleUtil {
    private static final Log logger = LogFactory.getLog(ResourceBundleUtil.class);

    public static final String DEFAULT_BASE_NAME = "commons-core";

    private static ResourceBundle resourceBundle = null;

    /**
     * 初始化配置文件（属性文件采用默认设置）
     * 
     * @return
     */
    public static ResourceBundle getInstance() {
        logger.info("初始化配置文件（属性文件采用默认设置）");

        return getInstance(DEFAULT_BASE_NAME);
    }

    /**
     * 初始化配置文件
     * 
     * @param baseName
     * @return
     */
    public static ResourceBundle getInstance(String baseName) {
        logger.info("初始化配置文件");
        Assert.notNull(baseName, "baseName must not be null");

        return ResourceBundle.getBundle(baseName, Locale.getDefault());
    }

    /**
     * 初始化配置文件
     * 
     * @author 伍章红 2015年12月7日 上午10:23:57
     * @param baseName
     * @param locale
     * @return
     */
    public static ResourceBundle getInstance(String baseName, Locale locale) {
        logger.info("初始化配置文件");
        Assert.notNull(baseName, "baseName must not be null");
        Assert.notNull(locale, "locale must not be null");

        return ResourceBundle.getBundle(baseName, locale);
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULT_BASE_NAME, Locale.getDefault());
        System.out.println(resourceBundle.getString("version"));

        System.out.println(getString("124"));
        System.out.println(getString("124", "v1.0.1"));

        Locale[] availableLocales = Locale.getAvailableLocales();
        for (int i = 0; i < availableLocales.length; i++) {
            // 属性文件命名采用 filename_语言_国家.properties 的形式
            System.out.println(availableLocales[i].getDisplayLanguage() + "_" + availableLocales[i].getDisplayCountry()
                    + "--" + availableLocales[i].getLanguage() + "_" + availableLocales[i].getCountry());
        }
    }
}
