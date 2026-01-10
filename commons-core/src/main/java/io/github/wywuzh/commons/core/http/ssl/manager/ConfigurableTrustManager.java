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
package io.github.wywuzh.commons.core.http.ssl.manager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 可配置的信任管理器
 * 通过系统属性控制是否启用严格验证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-31 09:28:09
 * @version v3.5.0
 * @since JDK 17
 */
public class ConfigurableTrustManager implements X509TrustManager {

    private static final boolean STRICT_MODE = Boolean.parseBoolean(System.getProperty("ssl.strict.mode", "true"));

    private final X509TrustManager strictTrustManager;
    private final X509TrustManager lenientTrustManager;

    public ConfigurableTrustManager() throws Exception {
        this.strictTrustManager = createDefaultTrustManager();
        this.lenientTrustManager = new LenientTrustManager();
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (STRICT_MODE) {
            strictTrustManager.checkClientTrusted(chain, authType);
        } else {
            lenientTrustManager.checkClientTrusted(chain, authType);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (STRICT_MODE) {
            strictTrustManager.checkServerTrusted(chain, authType);
        } else {
            lenientTrustManager.checkServerTrusted(chain, authType);
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return STRICT_MODE ? strictTrustManager.getAcceptedIssuers() : lenientTrustManager.getAcceptedIssuers();
    }

    private X509TrustManager createDefaultTrustManager() throws Exception {
        javax.net.ssl.TrustManagerFactory tmf = javax.net.ssl.TrustManagerFactory.getInstance(javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((java.security.KeyStore) null);

        for (javax.net.ssl.TrustManager tm : tmf.getTrustManagers()) {
            if (tm instanceof X509TrustManager) {
                return (X509TrustManager) tm;
            }
        }
        throw new RuntimeException("未找到默认信任管理器");
    }

    /**
     * 内部使用的宽松信任管理器
     */
    private static class LenientTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            // 宽松模式下的实现
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // 宽松模式下的实现
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
