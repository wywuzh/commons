package io.github.wywuzh.commons.core.http.ssl.manager;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 仅用于开发和测试环境的信任管理器
 * <strong>警告：禁止在生产环境中使用</strong>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2025-10-31 09:27:00
 * @version v3.5.0
 * @since JDK 17
 */
public class DevTrustManager implements X509TrustManager {

    private final boolean logWarnings;

    public DevTrustManager() {
        this(true);
    }

    public DevTrustManager(boolean logWarnings) {
        this.logWarnings = logWarnings;
        if (logWarnings) {
            warnAboutUsage();
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        if (chain == null || chain.length == 0) {
            throw new CertificateException("客户端证书链为空");
        }
        logWarning("接受所有客户端证书: " + chain[0].getSubjectDN());
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        if (chain == null || chain.length == 0) {
            throw new CertificateException("服务器证书链为空");
        }
        logWarning("接受所有服务器证书: " + chain[0].getSubjectDN());
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private void warnAboutUsage() {
        System.err.println("⚠️  警告: 使用不安全的信任管理器");
        System.err.println("⚠️  此实现接受所有SSL证书，存在严重安全风险");
        System.err.println("⚠️  仅限开发和测试环境使用");
        System.err.println("⚠️  禁止在生产环境中使用此实现");
    }

    private void logWarning(String message) {
        if (logWarnings) {
            System.err.println("信任管理器警告: " + message);
        }
    }
}
