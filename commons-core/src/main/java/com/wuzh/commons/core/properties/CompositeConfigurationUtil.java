/*
 * Copyright 2015-2017 the original author or authors.
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类CompositeConfigurationUtil.java的实现描述：读取属性文件信息
 * 
 * <pre>
 * 说明：CompositeConfiguration可以设置读取多个配置文件（可通过配置同时读取properties、xml等文件）
 * </pre>
 * 
 * @author 伍章红 2015-8-13 上午8:53:06
 * @version v1.0.0
 * @see org.apache.commons.configuration.CompositeConfiguration
 * @since JDK 1.7.0_71
 */
public class CompositeConfigurationUtil {
    private static final Log logger = LogFactory.getLog(CompositeConfigurationUtil.class);

    private static final String DEFAULT_PATHNAME = "commons-core.properties";

    private static CompositeConfiguration configuration = null;

    /**
     * 初始化配置文件（属性文件采用默认设置）
     * 
     * @return
     */
    public static synchronized CompositeConfiguration getInstance() {
        return getInstance(DEFAULT_PATHNAME);
    }

    /**
     * 初始化配置文件
     * 
     * @param pathName
     * @return
     */
    public static synchronized CompositeConfiguration getInstance(String pathName) {
        CompositeConfiguration configuration = null;
        try {
            Thread currentThread = Thread.currentThread();
            URL resource = currentThread.getContextClassLoader().getResource(pathName);
            configuration = new CompositeConfiguration();
            configuration.addConfiguration(new PropertiesConfiguration(resource));
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
            Thread currentThread = Thread.currentThread();
            URL resource = currentThread.getContextClassLoader().getResource(DEFAULT_PATHNAME);

            CompositeConfiguration configuration = new CompositeConfiguration();
            configuration.addConfiguration(new SystemConfiguration());
            configuration.addConfiguration(new PropertiesConfiguration(resource));
            configuration.addConfiguration(new XMLConfiguration("com/wuzh/schabm/core/xml/account.xml"));

            System.out.println(configuration.getString("description"));

            List<Object> list = configuration.getList("accountList.account");
            System.out.println(list);
            System.out.println(configuration.getString("content:component-scan"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

}
