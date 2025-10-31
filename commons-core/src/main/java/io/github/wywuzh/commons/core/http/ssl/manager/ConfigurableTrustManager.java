package io.github.wywuzh.commons.core.http.ssl.manager;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 可配置的信任管理器
 * 通过系统属性控制是否启用严格验证
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-31 09:28:09
 * @version v3.5.0
 * @since JDK 17
 */
public class ConfigurableTrustManager implements X509TrustManager {

    private static final boolean STRICT_MODE =
            Boolean.parseBoolean(System.getProperty("ssl.strict.mode", "true"));

    private final X509TrustManager strictTrustManager;
    private final X509TrustManager lenientTrustManager;

    public ConfigurableTrustManager() throws Exception {
        this.strictTrustManager = createDefaultTrustManager();
        this.lenientTrustManager = new LenientTrustManager();
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        if (STRICT_MODE) {
            strictTrustManager.checkClientTrusted(chain, authType);
        } else {
            lenientTrustManager.checkClientTrusted(chain, authType);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        if (STRICT_MODE) {
            strictTrustManager.checkServerTrusted(chain, authType);
        } else {
            lenientTrustManager.checkServerTrusted(chain, authType);
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return STRICT_MODE ?
                strictTrustManager.getAcceptedIssuers() :
                lenientTrustManager.getAcceptedIssuers();
    }

    private X509TrustManager createDefaultTrustManager() throws Exception {
        javax.net.ssl.TrustManagerFactory tmf =
                javax.net.ssl.TrustManagerFactory.getInstance(
                        javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());
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
