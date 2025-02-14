/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.core.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 类ServletUtils的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019-02-05 21:07:22
 * @version v3.0.0
 * @since JDK 1.8
 */
public class ServletUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    /**
     * 获取当前请求对象
     * web.xml: <listener><listener-class>
     * org.springframework.web.context.request.RequestContextListener
     * </listener-class></listener>
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            if (RequestContextHolder.currentRequestAttributes() != null) {
                request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            }
        } catch (Exception e) {
            logger.error("通过RequestContextHolder.currentRequestAttributes()获取request失败：", e);
            return null;
        }
        return request;
    }

    /**
     * 获取当前响应对象
     * web.xml: <filter><filter-name>requestContextFilter</filter-name><filter-class>
     * org.springframework.web.filter.RequestContextFilter</filter-class></filter><filter-mapping>
     * <filter-name>requestContextFilter</filter-name><url-pattern>/*</url-pattern></filter-mapping>
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = null;
        try {
            if (RequestContextHolder.currentRequestAttributes() != null) {
                response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            }
        } catch (Exception e) {
            logger.error("通过RequestContextHolder.currentRequestAttributes()获取response失败：", e);
            return null;
        }
        return response;
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.containsAny(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.containsAny(ajax, "json", "xml")) {
            return true;
        }
        return false;
    }

    /**
     * 支持AJAX的页面跳转
     */
    public static void redirectUrl(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            if (ServletUtils.isAjaxRequest(request)) {
                request.getRequestDispatcher(url).forward(request, response); // AJAX不支持Redirect改用Forward
            } else {
                response.sendRedirect(request.getContextPath() + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得请求参数值
     */
    public static String getParameter(String name) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getParameter(name);
    }

    /**
     * 获得请求参数Map
     */
    public static Map<String, Object> getParameters() {
        return getParameters(getRequest());
    }

    /**
     * 获得请求参数Map
     */
    public static Map<String, Object> getParameters(ServletRequest request) {
        if (request == null) {
            return new HashMap<String, Object>();
        }
        return getParametersStartingWith(request, "");
    }

    /**
     * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
     * 返回的结果的Parameter名已去除前缀.
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        String pre = prefix;
        if (pre == null) {
            pre = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(pre) || paramName.startsWith(pre)) {
                String unprefixed = paramName.substring(pre.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    values = new String[] {};
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

}
