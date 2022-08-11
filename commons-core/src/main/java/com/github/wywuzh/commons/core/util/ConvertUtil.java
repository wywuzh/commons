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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类型转换工具类
 *
 * @author wuzh
 * @version 1.0, 10/30/2013
 * @since JDK 1.6
 */
public class ConvertUtil {
    private static final Log log = LogFactory.getLog(ConvertUtil.class);

    /**
     * 将JavaBean对象转换为Map对象。
     * <p>
     * 将传入的JavaBean对象转换为Map对象
     * </p>
     *
     * @param bean
     *            传入的JavaBean对象
     * @return Map 把JavaBean转换为Map的Map对象
     * @author wuzh, 10/30/2013
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> convertBean(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        Class type = bean.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertDescriptors[i];
                String propertyName = descriptor.getName();
                String typeName = descriptor.getPropertyType().getName();
                if (!propertyName.equals("class")) {
                    Object result = descriptor.getReadMethod().invoke(bean, new Object[0]);
                    if (null != result) {
                        if (typeName == "java.util.Date") {
                            map.put(propertyName, DateUtils.format((java.util.Date) result, DateUtils.PATTERN_DATE_TIME));
                        } else if (typeName == "java.sql.Date") {
                            map.put(propertyName, DateUtils.format((java.sql.Date) result, DateUtils.PATTERN_DATE));
                        } else if (typeName == "java.sql.Time") {
                            map.put(propertyName, DateUtils.format((Time) result, DateUtils.PATTERN_TIME));
                        } else if (typeName == "java.lang.Long" || typeName == "long") {
                            map.put(propertyName, result);
                        } else if (typeName == "java.lang.Short" || typeName == "short") {
                            map.put(propertyName, result);
                        } else if (typeName == "java.lang.Byte" || typeName == "byte") {
                            map.put(propertyName, result);
                        } else if (typeName == "java.lang.Character" || typeName == "char") {
                            map.put(propertyName, result);
                        } else if (typeName == "java.lang.Integer" || typeName == "int") {
                            map.put(propertyName, result);
                        } else if (typeName == "java.lang.Double" || typeName == "double") {
                            map.put(propertyName, result);
                        } else if (typeName == "java.lang.Float" || typeName == "float") {
                            map.put(propertyName, result);
                        } else {
                            map.put(propertyName, result);
                        }
                    } else {
                        map.put(propertyName, "");
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return map;
    }

    /**
     * 将Map对象转换为JavaBean对象
     *
     * @param map
     *            Map对象
     * @param clazz
     *            JavaBean对象
     * @return JavaBean对象
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Map map, Class clazz) {
        try {
            // 得到JavaBean对象
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            //
            Object object = clazz.newInstance();
            // 获取JavaBean对象中的属性
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                String typeName = descriptor.getPropertyType().getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    if (typeName == "java.util.Date" || typeName == "java.sql.Date") {
                        args[0] = DateUtils.parse(value.toString(), DateUtils.PATTERN_DATE_TIME);
                        ;
                    } else if (typeName == "java.lang.Long" || typeName == "long") {
                        args[0] = Long.valueOf(value.toString());
                    } else if (typeName == "java.lang.Short" || typeName == "short") {
                        args[0] = Short.valueOf(value.toString());
                    } else if (typeName == "java.lang.Byte" || typeName == "byte") {
                        args[0] = Short.valueOf(value.toString());
                    } else if (typeName == "java.lang.Character" || typeName == "char") {
                        args[0] = Short.valueOf(value.toString());
                    } else if (typeName == "java.lang.Integer" || typeName == "int") {
                        args[0] = Short.valueOf(value.toString());
                    } else if (typeName == "java.lang.Double" || typeName == "double") {
                        args[0] = Short.valueOf(value.toString());
                    } else if (typeName == "java.lang.Float" || typeName == "float") {
                        args[0] = Short.valueOf(value.toString());
                    } else {
                        args[0] = value;
                    }
                    descriptor.getWriteMethod().invoke(object, args);
                }
            }
            return object;
        } catch (IntrospectionException e) {
            e.printStackTrace();
            log.error("分析类属性失败");
            log.error(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            log.error("实例化JavaBean失败");
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("实例化JavaBean失败");
            log.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        // UserInfo userInfo = new UserInfo();
        // userInfo.setId(Long.valueOf("12"));
        // userInfo.setCode("A101");
        // userInfo.setLoginAccount("wuzh");
        // userInfo.setLoginPassword("123456");
        // userInfo.setShowName("wuzh");
        // userInfo.setMobile("12345678901");
        // userInfo.setEmail("wywuzh@163.com");
        // userInfo.setStatus(UserStatus.OK);
        // userInfo.setRemark("wuzh");
        // userInfo.setCreateBy("A101");
        // userInfo.setCreateDate(new Date());
        // userInfo.setUpdateBy("A101");
        // userInfo.setUpdateDate(new Date());
        // Map<String, Object> map = convertBean(userInfo);
        // System.out.println("code:" + map.get("code"));
        //
        // UserInfo user = (UserInfo) convertMap(map, UserInfo.class);
        // System.out.println("createDate:"
        // + DateUtil.format(user.getCreateDate(),
        // DateUtil.PATTERN_DATE_TIME));
        // log.debug("用户信息：" + user.getEmail());
        // JSONObject jsonObject = JSONObject.fromObject(map);
        // System.out.println(jsonObject.get("code"));
        //
        // StringBuffer sb = new StringBuffer();
        // sb.append("Checking sorted filter chain: [Root bean: class
        // [org.springframework.security.web.access.channel.ChannelProcessingFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 100, Root
        // bean: class [org.springframework.security.web.session.ConcurrentSessionFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 200, Root
        // bean: class [org.springframework.security.web.context.SecurityContextPersistenceFilter]; scope=;
        // abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 300, Root
        // bean: class [org.springframework.security.web.authentication.logout.LogoutFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 400, Root
        // bean: class [org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter]; scope=;
        // abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 800, Root
        // bean: class [org.springframework.security.web.authentication.www.BasicAuthenticationFilter]; scope=;
        // abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 1200, Root
        // bean: class [org.springframework.security.web.savedrequest.RequestCacheAwareFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 1300, Root
        // bean: class [org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter]; scope=;
        // abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 1400, Root
        // bean: class [org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter];
        // scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true;
        // primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null,
        // order = 1500, Root bean: class
        // [org.springframework.security.web.authentication.AnonymousAuthenticationFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 1600, Root
        // bean: class [org.springframework.security.web.session.SessionManagementFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 1700, Root
        // bean: class [org.springframework.security.web.access.ExceptionTranslationFilter]; scope=; abstract=false;
        // lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null, order = 1800,
        // <org.springframework.security.web.access.intercept.FilterSecurityInterceptor#0>, order = 1900]");
        // System.out.println("length:" + sb.length());
    }
}
