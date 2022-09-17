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
package com.github.wywuzh.commons.mybatis.aspect;

import com.github.wywuzh.commons.mybatis.DataSourceContextHolder;
import com.github.wywuzh.commons.mybatis.annotation.DataSourceChoice;
import com.github.wywuzh.commons.mybatis.enums.DataSourceType;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 类RoutingDataSourceAspect的实现描述：方法拦截，动态切换数据源
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-12-20 14:22:04
 * @version v2.2.0
 * @since JDK 1.8
 */
@Aspect
@Component
public class RoutingDataSourceAspect {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Before("@annotation(com.github.wywuzh.commons.mybatis.annotation.DataSourceChoice)")
  public void before(JoinPoint point) {
    // 获得当前访问的class
    Class<?> className = point.getTarget().getClass();
    // 获得访问的方法名
    String methodName = point.getSignature().getName();
    // 得到方法的参数的类型
    Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
    DataSourceType dataSource = DataSourceType.WRITE;
    try {
      // 得到访问的方法对象
      Method method = className.getMethod(methodName, argClass);
      // 判断是否存在@DataSourceChoice注解
      if (method.isAnnotationPresent(DataSourceChoice.class)) {
        // 取出注解中的数据源名
        DataSourceChoice dataSourceChoice = method.getAnnotation(DataSourceChoice.class);
        dataSource = dataSourceChoice.value();
      }
    } catch (Exception e) {
      logger.error("{}#{}({}) 方法 解析异常：", className.getName(), methodName, argClass, e);
    }
    // 切换数据源
    logger.info("执行：{}#{}({}) 方法，使用数据源：{}。", className.getName(), methodName, argClass, dataSource.getName());
    DataSourceContextHolder.setDataSource(dataSource.getName());
  }

  @After("@annotation(com.github.wywuzh.commons.mybatis.annotation.DataSourceChoice)")
  public void after() {
    try {
      DataSourceContextHolder.clearDataSource();
    } catch (Exception e) {
    }
  }
}
