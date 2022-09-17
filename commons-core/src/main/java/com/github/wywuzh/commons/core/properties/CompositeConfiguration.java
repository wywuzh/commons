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

import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类CompositeConfiguration.java的实现描述：读取属性文件信息
 *
 * @author 伍章红 2015-8-13 上午10:00:19
 * @version v1.0.0
 * @see AbstractConfiguration
 * @see org.apache.commons.configuration.CompositeConfiguration
 * @since JDK 1.7.0_71
 */
public class CompositeConfiguration extends AbstractConfiguration {
  private static final Log logger = LogFactory.getLog(CompositeConfiguration.class);

  private static final String DEFAULT_PATHNAME = "application.properties";

  @Override
  public Configuration getInstance() {
    return getInstance(DEFAULT_PATHNAME);
  }

  @Override
  public Configuration getInstance(String fileName) {
    org.apache.commons.configuration.CompositeConfiguration configuration = null;
    try {
      Thread currentThread = Thread.currentThread();
      URL resource = currentThread.getContextClassLoader().getResource(fileName);
      configuration = new org.apache.commons.configuration.CompositeConfiguration();
      configuration.addConfiguration(new PropertiesConfiguration(resource));
    } catch (ConfigurationException e) {
      logger.error(e.getMessage(), e);
    }
    return configuration;
  }

}
