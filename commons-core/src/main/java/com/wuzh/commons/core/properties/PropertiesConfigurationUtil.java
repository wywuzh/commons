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
package com.wuzh.commons.core.properties;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类PropertiesConfigurationUtil.java的实现描述：读取属性文件
 * 
 * @author 伍章红 2015-8-12 上午11:16:48
 * @version v1.0.0
 * @see org.apache.commons.configuration.PropertiesConfiguration
 * @since JDK 1.7.0_71
 */
public class PropertiesConfigurationUtil {
    private static final Log logger = LogFactory.getLog(PropertiesConfigurationUtil.class);

    public static final String DEFAULT_FILE_NAME = "commons-core.properties";

    private static PropertiesConfiguration configuration = null;

    /**
     * 初始化配置文件（属性文件采用默认设置）
     * 
     * @return
     */
    public static synchronized PropertiesConfiguration getInstance() {
        return getInstance(DEFAULT_FILE_NAME);
    }

    /**
     * 初始化配置文件
     * 
     * @param fileName
     * @return
     */
    public static synchronized PropertiesConfiguration getInstance(String fileName) {
        PropertiesConfiguration configuration = null;
        try {
            configuration = new PropertiesConfiguration(new File(fileName));
            // Automatic Reloading 自动重新加载
            // 通常需要开启一个线程来监视配置文件的时间，并在文件被修改后重新加载进来。
            // Commons Configuration集成了这个加载机制,如果需要使用自动加载，只需要在年id配置信息里声明一个自动重载策略：
            // 现在我们随时手动修改了schabm-core.properties，配置信息都能够自动刷新，修改后的值立即在程序里生效。
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return configuration;
    }

    /**
     * 根据指定的key获取value值
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

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getString(key);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回defaultValue
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

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getString(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
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
    public static String[] getStringArray(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getStringArray(key);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回-1
     * </pre>
     * 
     * @param key
     * @return
     */
    public static Byte getByte(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Byte value = getByte(key, null);
        if (null == value) {
            return -1;
        }
        return null;
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Byte getByte(String key, byte defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getByte(key, new Byte(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Byte getByte(String key, Byte defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getByte(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
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
    public static Byte getShort(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Short value = getShort(key, null);
        if (null == value) {
            return -1;
        }
        return null;
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Short getShort(String key, short defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return getShort(key, new Short(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Short getShort(String key, Short defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getShort(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回-1
     * </pre>
     * 
     * @param key
     * @return
     */
    public static Integer getInt(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Integer integer = getInteger(key, null);
        if (null == integer) {
            return -1;
        }

        return integer;
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInt(String key, int defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getInteger(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInteger(String key, Integer defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getInteger(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回-1
     * </pre>
     * 
     * @param key
     * @return
     */
    public static Long getLong(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Long value = getLong(key, null);
        if (null == value) {
            return -1L;
        }

        return value;
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Long getLong(String key, long defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getLong(key, new Long(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Long getLong(String key, Long defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getLong(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回-1
     * </pre>
     * 
     * @param key
     * @return
     */
    public static Float getFloat(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Float value = getFloat(key, null);
        if (null == value) {
            return new Float(-1);
        }

        return value;
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Float getFloat(String key, float defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getFloat(key, new Float(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Float getFloat(String key, Float defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getFloat(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回-1
     * </pre>
     * 
     * @param key
     * @return
     */
    public static Double getDouble(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Double value = getDouble(key, null);
        if (null == value) {
            return new Double(-1);
        }

        return value;
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Double getDouble(String key, double defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getDouble(key, new Double(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Double getDouble(String key, Double defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getDouble(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
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
    public static BigDecimal getBigDecimal(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getBigDecimal(key, null);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getBigDecimal(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
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
    public static BigInteger getBigInteger(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getBigInteger(key, null);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getBigInteger(key, defaultValue);
    }

    /**
     * 根据指定的key获取value值
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
    public static Boolean getBoolean(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getBoolean(key, null);
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String key, boolean defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return getBoolean(key, BooleanUtils.toBoolean(defaultValue));
    }

    /**
     * 根据指定的key获取value值
     * 
     * <pre>
     * 说明：
     *  1）如果传入的key为null，则返回null
     *  2）如果指定的key在属性文件中不存在，则返回指定的defaultValue值
     * </pre>
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getBoolean(key, defaultValue);
    }

    public static void main(String[] args) {
        try {
            PropertiesConfiguration configuration = new PropertiesConfiguration(DEFAULT_FILE_NAME);

            // 1.Automatic Reloading 自动重新加载
            // 通常需要开启一个线程来监视配置文件的时间，并在文件被修改后重新加载进来。
            // Commons Configuration集成了这个加载机制,如果需要使用自动加载，只需要在年id配置信息里声明一个自动重载策略：
            // 现在我们随时手动修改了schabm-core.properties，配置信息都能够自动刷新，修改后的值立即在程序里生效。
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());

            System.out.println(configuration.getString("version"));
            String[] stringArray = configuration.getStringArray("version");
            System.out.println(stringArray.length);
            // 2.Saving 保存
            // 调用save()方法就可以保存你的配置:
            // configuration.setProperty("version", "V4.0.1");
            // configuration.save();
            // configuration.save(new File("schabm.properties"));
            System.out.println(configuration.getString("version"));

            configuration.addProperty("username", "wuzh");
            configuration.save();
            System.out.println(configuration.getProperty("username"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
