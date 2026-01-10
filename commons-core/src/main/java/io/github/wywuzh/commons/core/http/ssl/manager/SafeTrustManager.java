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

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.X509TrustManager;

/**
 * 类SafeTrustManager的实现描述：安全的信任管理器实现
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-31 09:24:46
 * @version v3.5.0
 * @since JDK 17
 */
public class SafeTrustManager implements X509TrustManager {

    private final X509TrustManager defaultTrustManager;
    private final List<X509Certificate> trustedCertificates;

    public SafeTrustManager() throws KeyStoreException, NoSuchAlgorithmException {
        this.trustedCertificates = new ArrayList<>();
        this.defaultTrustManager = getDefaultTrustManager();
    }

    /**
     * 添加自定义信任证书
     */
    public void addTrustedCertificate(X509Certificate certificate) {
        if (certificate != null) {
            trustedCertificates.add(certificate);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // 对于客户端证书验证，可以根据需要实现
        if (chain == null || chain.length == 0) {
            throw new CertificateException("客户端证书链为空");
        }

        try {
            // 首先尝试默认验证
            defaultTrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException e) {
            // 如果默认验证失败，检查自定义信任证书
            if (!isTrusted(chain[0])) {
                throw new CertificateException("客户端证书不受信任", e);
            }
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (chain == null || chain.length == 0) {
            throw new CertificateException("服务器证书链为空");
        }

        try {
            // 首先尝试默认验证
            defaultTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException e) {
            // 如果默认验证失败，检查自定义信任证书
            if (!isTrusted(chain[0])) {
                throw new CertificateException("服务器证书不受信任: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        List<X509Certificate> issuers = new ArrayList<>();

        // 添加默认的受信任颁发者
        issuers.addAll(Arrays.asList(defaultTrustManager.getAcceptedIssuers()));

        // 添加自定义证书的颁发者
        for (X509Certificate cert : trustedCertificates) {
            issuers.add(cert);
        }

        return issuers.toArray(new X509Certificate[0]);
    }

    private boolean isTrusted(X509Certificate certificate) {
        for (X509Certificate trustedCert : trustedCertificates) {
            if (certificate.equals(trustedCert)) {
                return true;
            }
        }
        return false;
    }

    private X509TrustManager getDefaultTrustManager() throws KeyStoreException, NoSuchAlgorithmException {
        try {
            javax.net.ssl.TrustManagerFactory tmf = javax.net.ssl.TrustManagerFactory.getInstance(javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);

            for (javax.net.ssl.TrustManager tm : tmf.getTrustManagers()) {
                if (tm instanceof X509TrustManager) {
                    return (X509TrustManager) tm;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("无法获取默认信任管理器", e);
        }
        throw new RuntimeException("未找到X509TrustManager");
    }
}
