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

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类PropertiesConfiguration.java的实现描述：读取属性文件信息
 *
 * @author 伍章红 2015-8-13 上午9:54:54
 * @version v1.0.0
 * @see AbstractConfiguration
 * @see org.apache.commons.configuration.PropertiesConfiguration
 * @since JDK 1.7.0_71
 */
public class PropertiesConfiguration extends AbstractConfiguration {
    private final Log logger = LogFactory.getLog(PropertiesConfiguration.class);

    public final String DEFAULT_FILE_NAME = "commons-core.properties";

    @Override
    public Configuration getInstance() {
        return getInstance(DEFAULT_FILE_NAME);
    }

    @Override
    public Configuration getInstance(String fileName) {
        org.apache.commons.configuration.PropertiesConfiguration configuration = null;
        try {
            configuration = new org.apache.commons.configuration.PropertiesConfiguration(new File(fileName));
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

}
