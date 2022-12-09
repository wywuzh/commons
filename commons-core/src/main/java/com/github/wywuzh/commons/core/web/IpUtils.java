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
package com.github.wywuzh.commons.core.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 类IpUtils的实现描述：IP地址信息
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-04-09 09:48:37
 * @version v2.2.1
 * @since JDK 1.8
 */
public class IpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    public static final String LOOPBACK_ADDRESS = "127.0.0.1";

    /**
     * @return 获取IP地址
     * @since v2.2.1
     */
    public static String getIpAddr() {
        return getIpAddr(ServletUtils.getRequest());
    }

    /**
     * @return 获取IP地址
     * @since v2.2.1
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddr = request.getHeader("x-forwarded-for");
        if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
            ipAddr = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
            ipAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
            ipAddr = request.getRemoteAddr();
        }
        // 本地
        if (StringUtils.equals(ipAddr, "0:0:0:0:0:0:0:1")) {
            ipAddr = LOOPBACK_ADDRESS;
        }
        return ipAddr.split(",")[0];
    }

    /**
     * 获取Mac地址
     *
     * @return
     * @since v2.2.1
     */
    public static String getMacAddr(HttpServletRequest request) throws SocketException, UnknownHostException {
        return getMacAddr(getIpAddr(request));
    }

    /**
     * 获取Mac地址
     *
     * @param ipAddr IP地址
     * @return
     * @since v2.2.1
     */
    public static String getMacAddr(String ipAddr) throws UnknownHostException, SocketException {
        String macAddr = "";
        // 如果为127.0.0.1,则获取本地MAC地址。
        if (LOOPBACK_ADDRESS.equals(ipAddr)) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            // 貌似此方法需要JDK1.6。
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            macAddr = sb.toString().trim().toUpperCase();
            return macAddr;
        } else {
            // 获取非本地IP的MAC地址
            BufferedReader reader = null;
            try {
                Process p = Runtime.getRuntime().exec("nbtstat -A " + ipAddr);

                reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String str = "";
                while ((str = reader.readLine()) != null) {
                    if (str.indexOf("MAC") > 1) {
                        macAddr = str.substring(str.indexOf("MAC") + 9, str.length());
                        macAddr = macAddr.trim();
                        break;
                    }
                }
                p.destroy();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            return macAddr;
        }
    }

}
