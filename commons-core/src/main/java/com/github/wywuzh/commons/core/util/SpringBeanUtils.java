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
package com.github.wywuzh.commons.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 类SpringBeanUtils的实现描述：Spring容器工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-16 08:24:32
 * @version v2.3.6
 * @since JDK 1.8
 */
@Configuration
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static Object getBean(String name) {
        return APPLICATION_CONTEXT.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return APPLICATION_CONTEXT.getBean(name, requiredType);
    }

    public static Object getBean(String name, Object... args) {
        return APPLICATION_CONTEXT.getBean(name, args);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return APPLICATION_CONTEXT.getBean(requiredType);
    }

    public static boolean containsBean(String beanName) {
        return APPLICATION_CONTEXT.containsBean(beanName);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        return APPLICATION_CONTEXT.getBeansOfType(requiredType);
    }

}
