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
package io.github.wywuzh.commons.core.properties;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 类AbstractConfiguration.java的实现描述：读取配置文件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月7日 下午11:56:51
 * @version v1.0.0
 * @see org.apache.commons.configuration.Configuration
 * @since JDK 1.7
 */
public abstract class AbstractConfiguration {

    private Configuration configuration = null;

    public abstract Configuration getInstance();

    public abstract Configuration getInstance(String fileName);

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
    public String getString(String key) {
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
    public String getString(String key, String defaultValue) {
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
    public String[] getStringArray(String key) {
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
    public Byte getByte(String key) {
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
    public Byte getByte(String key, byte defaultValue) {
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
    public Byte getByte(String key, Byte defaultValue) {
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
    public Byte getShort(String key) {
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
    public Short getShort(String key, short defaultValue) {
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
    public Short getShort(String key, Short defaultValue) {
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
    public Integer getInt(String key) {
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
    public Integer getInt(String key, int defaultValue) {
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
    public Integer getInteger(String key, Integer defaultValue) {
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
    public Long getLong(String key) {
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
    public Long getLong(String key, long defaultValue) {
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
    public Long getLong(String key, Long defaultValue) {
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
    public Float getFloat(String key) {
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
    public Float getFloat(String key, float defaultValue) {
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
    public Float getFloat(String key, Float defaultValue) {
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
    public Double getDouble(String key) {
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
    public Double getDouble(String key, double defaultValue) {
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
    public Double getDouble(String key, Double defaultValue) {
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
    public BigDecimal getBigDecimal(String key) {
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
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
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
    public BigInteger getBigInteger(String key) {
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
    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
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
    public Boolean getBoolean(String key) {
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
    public Boolean getBoolean(String key, boolean defaultValue) {
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
    public Boolean getBoolean(String key, Boolean defaultValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (null == configuration) {
            configuration = getInstance();
        }

        return configuration.getBoolean(key, defaultValue);
    }
}
