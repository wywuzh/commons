/*
 * Copyright 2015-2021 the original author or authors.
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
package com.github.wywuzh.commons.dingtalk.config;

import com.github.wywuzh.commons.dingtalk.api.TokenAPI;
import com.github.wywuzh.commons.dingtalk.response.AccessTokenResponse;
import com.github.wywuzh.commons.dingtalk.exception.DingtalkException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 类ApiConfig的实现描述：API配置类，项目中请保证其为单例
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-28 12:46:49
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class ApiConfig {

    /**
     * 应用的agentId
     */
    private String agentId;
    /**
     * 应用的唯一标识key
     */
    private String appKey;
    /**
     * 应用的密钥
     */
    private String appSecret;
    /**
     * 企业内部应用的access_token
     */
    private transient String accessToken;
    /**
     * access_token的过期时间，单位秒
     */
    private transient Integer expiresIn;
    /**
     * access_token获取到的时间
     */
    private transient long tokenStartTime;

    /**
     * 这里定义token正在刷新的标识，想要达到的目标是当有一个请求来获取token，发现token已经过期（我这里的过期逻辑是比官方提供的早100秒），然后开始刷新token
     * 在刷新的过程里，如果又继续来获取token，会先把旧的token返回，直到刷新结束，之后再来的请求，将获取到新的token
     * 利用AtomicBoolean实现原理：
     * 当请求来的时候，检查token是否已经过期（7100秒）以及标识是否已经是true（表示已经在刷新了，还没刷新完），过期则将此标识设为true，并开始刷新token
     * 在刷新结束前再次进来的请求，由于标识一直是true，而会直接拿到旧的token，由于我们的过期逻辑比官方的早100秒，所以旧的还可以继续用
     * 无论刷新token正在结束还是出现异常，都在最后将标识改回false，表示刷新工作已经结束
     */
    private final AtomicBoolean tokenRefreshing = new AtomicBoolean(false);

    public ApiConfig(String agentId, String appKey, String appSecret) {
        this.agentId = agentId;
        this.appKey = appKey;
        this.appSecret = appSecret;
        // 初始化配置，即第一次获取access_token
        initToken(System.currentTimeMillis());
    }

    public String getAgentId() {
        return agentId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getAccessToken() {
        long now = System.currentTimeMillis();
        long diff = now - this.tokenStartTime;
        try {
            /*
             * 判断优先顺序：
             * 1.官方给出的超时时间是7200秒，这里用7100秒来做，防止出现已经过期的情况
             * 2.刷新标识判断，如果已经在刷新了，则也直接跳过，避免多次重复刷新，如果没有在刷新，则开始刷新
             */

            // 满足条件：获取到token的时间已经超过7100秒，并且通过CAS机制（并发竞争）将刷新标识设置为了true
            if (diff > 7100000 && this.tokenRefreshing.compareAndSet(false, true)) {
                log.debug("准备刷新token.............");
                initToken(now);
            }
        } catch (Exception e) {
            log.warn("刷新Token出错.", e);
            //刷新工作出现有异常，将标识设置回false
            this.tokenRefreshing.set(false);
        }
        return accessToken;
    }

    /**
     * 初始化token
     *
     * @param refreshTime 刷新时间，单位毫秒
     */
    private void initToken(long refreshTime) {
        // 记住原本的时间，用于出错回滚
        final long oldTime = this.tokenStartTime;
        this.tokenStartTime = refreshTime;

        try {
            AccessTokenResponse tokenResponse = TokenAPI.getToken(appKey, appSecret);
            if (tokenResponse.getErrCode() != 0) {
                this.tokenStartTime = oldTime;
                throw new DingtalkException("请求调用失败！");
            }

            this.accessToken = tokenResponse.getAccessToken();
            this.expiresIn = tokenResponse.getExpiresIn();
        } finally {
            // 刷新工作做完，将标识设置回false
            this.tokenRefreshing.set(false);
        }
    }

}
