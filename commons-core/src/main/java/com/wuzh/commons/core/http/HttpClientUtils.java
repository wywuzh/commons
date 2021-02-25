/*
 * Copyright 2015-2016 the original author or authors.
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
package com.wuzh.commons.core.http;

import com.wuzh.commons.core.json.gson.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 类HttpUtil.java的实现描述：HTTP、HTTPS调用工具类
 *
 * <pre>
 * 参考网址：
 * 1. https://jingyan.baidu.com/article/154b46317353d228ca8f4112.html
 * </pre>
 *
 * <pre>
 * 1）GET：GET操作是安全的。所谓安全是指不管进行多少次操作，资源的状态都不会改变。
 * 2）POST：POST操作既不是安全的，也不是幂等的，比如常见的POST重复加载问题：当我们多次发出同样的POST请求后，其结果是创建出了若干的资源。
 * 3）PUT：PUT操作是幂等的。所谓幂等是指不管进行多少次操作，结果都一样。
 * 4）DELETE：DELETE操作是幂等的。所谓幂等是指不管进行多少次操作，结果都一样。
 * </pre>
 *
 * <pre>
 * <strong>使用场合：</strong>
 *  POST    /uri      创建
 *  DELETE  /uri/xxx  删除
 *  PUT     /uri/xxx  更新或创建
 *  GET     /uri/xxx  查看
 * </pre>
 *
 * @author wywuzh 2016年4月25日 下午2:07:31
 * @version v1.0.0
 * @since JDK 1.7
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * GET请求数据
     *
     * @param uri   请求URI地址
     * @param param 请求参数。格式param1=value1&amp;param2=value2&amp;param3=value3
     * @return
     * @author wywuzh 2016年4月25日 下午2:17:14
     */
    public static ResponseMessage doGet(String uri, String param) {
        Assert.notNull(uri, "uri must not be null");

        return doGet(uri, param, Charset.defaultCharset());
    }

    /**
     * GET请求数据
     *
     * @param uri   请求URI地址
     * @param param 请求参数
     * @return
     * @author wywuzh 2016年4月25日 下午2:37:27
     */
    public static ResponseMessage doGet(String uri, Map<String, String> param) {
        Assert.notNull(uri, "uri must not be null");

        StringBuilder paramStr = new StringBuilder();
        if (null != param && param.size() > 0) {
            for (String key : param.keySet()) {
                if (paramStr.length() > 0) {
                    paramStr.append("&");
                }
                paramStr.append(key).append("=").append(param.get(key));
            }
        }
        return doGet(uri, paramStr.toString());
    }

    /**
     * GET请求数据
     *
     * @param uri    请求URI地址
     * @param param  请求参数（urlParam,格式param1=value1&amp;param2=value2&amp;param3=value3）
     * @param header 请求参数（header）
     * @return
     * @author wywuzh 2016年5月23日 下午5:47:07
     */
    public static ResponseMessage doGet(String uri, String param, Map<String, String> header) {
        Assert.notNull(uri, "uri must not be null");

        return doGet(uri, param, header, Charset.defaultCharset());
    }

    /**
     * GET请求数据
     *
     * @param uri    请求URI地址
     * @param param  请求参数（urlParam）
     * @param header 请求参数（header）
     * @return
     * @author wywuzh 2016年5月23日 下午5:47:15
     */
    public static ResponseMessage doGet(String uri, Map<String, String> param, Map<String, String> header) {
        Assert.notNull(uri, "uri must not be null");

        return doGet(uri, param, header, Charset.defaultCharset());
    }

    /**
     * GET请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数,格式param1=value1&amp;param2=value2&amp;param3=value3
     * @param charset 字符集
     * @return
     * @author wywuzh 2016年4月25日 下午2:23:07
     */
    public static ResponseMessage doGet(String uri, String param, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        return doGet(uri, param, null, charset);
    }

    /**
     * GET请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数
     * @param charset 字符集
     * @return
     * @author wywuzh 2016年4月25日 下午2:39:19
     */
    public static ResponseMessage doGet(String uri, Map<String, String> param, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        StringBuilder paramStr = new StringBuilder();
        if (null != param && param.size() > 0) {
            for (String key : param.keySet()) {
                if (paramStr.length() > 0) {
                    paramStr.append("&");
                }
                paramStr.append(key).append("=").append(param.get(key));
            }
        }
        return doGet(uri, paramStr.toString(), charset);
    }

    /**
     * GET请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数（urlParam,格式param1=value1&amp;param2=value2&amp;param3=value3）
     * @param header  请求参数（header）
     * @param charset 字符集
     * @return
     * @author wywuzh 2016年5月23日 下午5:46:29
     */
    public static ResponseMessage doGet(String uri, String param, Map<String, String> header, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        HttpGet httpGet = new HttpGet();

        // 请求参数（urlParam）
        if (StringUtils.isNotEmpty(param)) {
            uri = uri + "?" + param;
        }

        try {
            httpGet.setURI(new URI(uri));
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException", e);
        }

        // 请求参数（header）
        if (null != header && header.size() > 0) {
            Header[] headers = new Header[header.size()];
            int index = 0;
            for (String key : header.keySet()) {
                headers[index] = new BasicHeader(key, header.get(key));
                index++;
            }
            httpGet.setHeaders(headers);
        }

        return doRequest(httpGet);
    }

    /**
     * GET请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数（urlParam）
     * @param header  请求参数（header）
     * @param charset 字符集
     * @return
     * @author wywuzh 2016年5月23日 下午5:47:21
     */
    public static ResponseMessage doGet(String uri, Map<String, String> param, Map<String, String> header,
                                        Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        StringBuilder paramStr = new StringBuilder();
        if (null != param && param.size() > 0) {
            for (String key : param.keySet()) {
                if (paramStr.length() > 0) {
                    paramStr.append("&");
                }
                paramStr.append(key).append("=").append(param.get(key));
            }
        }
        return doGet(uri, paramStr.toString(), header, charset);
    }

    /**
     * POST请求数据
     *
     * @param uri    请求URI地址
     * @param params 请求参数
     * @return
     * @author wywuzh 2016年4月26日 下午5:15:22
     */
    public static ResponseMessage doPost(String uri, Map<String, String> params) {
        Assert.notNull(uri, "uri must not be null");

        return doPost(uri, params, Charset.defaultCharset());
    }

    /**
     * POST请求数据
     *
     * @param uri    请求URI地址
     * @param param  请求参数
     * @param header
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午9:58:00
     */
    public static ResponseMessage doPost(String uri, Map<String, String> param, Map<String, String> header) {
        Assert.notNull(uri, "uri must not be null");

        return doPost(uri, param, header, Charset.defaultCharset());
    }

    /**
     * POST请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数
     * @param charset
     * @return
     * @author wywuzh 2016年4月25日 下午2:41:10
     */
    public static ResponseMessage doPost(String uri, Map<String, String> param, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        return doPost(uri, param, null, charset);
    }

    /**
     * POST请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数
     * @param header  header参数
     * @param charset 字符集
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午9:57:56
     */
    public static ResponseMessage doPost(String uri, Map<String, String> param, Map<String, String> header,
                                         Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        HttpPost httpPost = new HttpPost(uri);

        // 请求参数
        if (null != param && param.size() > 0) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (String key : param.keySet()) {
                list.add(new BasicNameValuePair(key, param.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
            httpPost.setEntity(entity);
        }

        // header参数
        if (null != header && header.size() > 0) {
            Header[] headers = new Header[header.size()];
            int index = 0;
            for (String key : header.keySet()) {
                headers[index] = new BasicHeader(key, header.get(key));
                index++;
            }
            httpPost.setHeaders(headers);
        }
        return doRequest(httpPost);
    }

    /**
     * POST请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数
     * @param header  header参数
     * @param charset 字符集
     * @return
     * @since v2.3.8
     */
    public static ResponseMessage doPost(String uri, Object param, Map<String, String> header,
                                         Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        HttpPost httpPost = new HttpPost(uri);

        // 请求参数
        if (null != param) {
            String json = GsonUtil.format(param);
            httpPost.setEntity(new StringEntity(json, charset));
        }

        // header参数
        if (null != header && header.size() > 0) {
            Header[] headers = new Header[header.size()];
            int index = 0;
            for (String key : header.keySet()) {
                headers[index] = new BasicHeader(key, header.get(key));
                index++;
            }
            httpPost.setHeaders(headers);
        }
        return doRequest(httpPost);
    }

    /**
     * PUT请求数据
     *
     * @param uri    请求URI地址
     * @param params 请求参数
     * @return
     * @author wywuzh 2016年4月26日 下午6:04:49
     */
    public static ResponseMessage doPut(String uri, Map<String, String> params) {
        Assert.notNull(uri, "uri must not be null");

        return doPut(uri, params, Charset.defaultCharset());
    }

    /**
     * PUT请求数据
     *
     * @param uri    请求URI地址
     * @param param  请求参数
     * @param header header参数
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午11:32:48
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, Map<String, String> header) {
        Assert.notNull(uri, "uri must not be null");

        return doPut(uri, param, header, Charset.defaultCharset());
    }

    /**
     * PUT请求数据
     *
     * @param uri      请求URI地址
     * @param param    请求参数
     * @param fileList 附件列表
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午11:33:50
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, List<File> fileList) {
        Assert.notNull(uri, "uri must not be null");

        return doPut(uri, param, fileList, Charset.defaultCharset());
    }

    /**
     * PUT请求数据
     *
     * @param uri      请求URI地址
     * @param param    请求参数
     * @param header   header参数
     * @param fileList 附件列表
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午11:33:34
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, Map<String, String> header,
                                        List<File> fileList) {
        Assert.notNull(uri, "uri must not be null");

        return doPut(uri, param, header, fileList, Charset.defaultCharset());
    }

    /**
     * PUT请求数据
     *
     * @param uri   请求URI地址
     * @param param 请求参数
     * @return
     * @author wywuzh 2016年4月26日 下午5:38:20
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        return doPut(uri, param, null, null, charset);
    }

    /**
     * PUT请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数
     * @param header  header参数
     * @param charset 字符集
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午11:37:15
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, Map<String, String> header,
                                        Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        return doPut(uri, param, header, null, charset);
    }

    /**
     * PUT请求数据
     *
     * @param uri      请求URI地址
     * @param param    请求参数
     * @param fileList 附件列表
     * @param charset  字符集
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午11:37:18
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, List<File> fileList, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        return doPut(uri, param, null, fileList, charset);
    }

    /**
     * PUT请求数据
     *
     * @param uri      请求URI地址
     * @param param    请求参数
     * @param header   header参数
     * @param fileList 附件列表
     * @param charset  字符集
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月17日 下午11:14:04
     */
    public static ResponseMessage doPut(String uri, Map<String, String> param, Map<String, String> header,
                                        List<File> fileList, Charset charset) {
        Assert.notNull(uri, "uri must note be null");
        Assert.notNull(charset, "charset must not be null");

        HttpPut httpPut = new HttpPut(uri);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        // 请求参数
        if (null != param && param.size() > 0) {
            for (String key : param.keySet()) {
                multipartEntityBuilder.addPart(key,
                        new StringBody(param.get(key), ContentType.APPLICATION_FORM_URLENCODED));
            }
        }

        // 需要上传的文件
        if (null != fileList && fileList.size() > 0) {
            for (File file : fileList) {
                multipartEntityBuilder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName());
            }
        }
        httpPut.setEntity(multipartEntityBuilder.build());

        // header参数
        if (null != header && header.size() > 0) {
            Header[] headers = new Header[header.size()];
            int index = 0;
            for (String key : header.keySet()) {
                headers[index] = new BasicHeader(key, header.get(key));
                index++;
            }
            httpPut.setHeaders(headers);
        }
        return doRequest(httpPut);
    }

    /**
     * DELETE请求数据
     *
     * @param uri   请求URI地址
     * @param param 请求参数
     * @return
     * @author wywuzh 2016年4月26日 下午6:06:47
     */
    public static String doDelete(String uri, String param) {
        Assert.notNull(uri, "uri must not be null");

        return doDelete(uri, param, Charset.defaultCharset());
    }

    /**
     * DELETE请求数据
     *
     * @param uri     请求URI地址
     * @param param   请求参数
     * @param charset
     * @return
     * @author wywuzh 2016年4月26日 下午6:07:06
     */
    public static String doDelete(String uri, String param, Charset charset) {
        Assert.notNull(uri, "uri must not be null");
        Assert.notNull(charset, "charset must not be null");

        String result = null;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete();

            if (StringUtils.isNotEmpty(param)) {
                uri = uri + "?" + param;
            }
            httpDelete.setURI(new URI(uri));

            // 调用DELETE请求
            HttpResponse httpResponse = httpClient.execute(httpDelete);

            // 返回处理结果状态
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), charset);
            }
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException：", e);
        } catch (IOException e) {
            logger.error("IOException：", e);
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException：", e);
        }
        return result;
    }


    /**
     * 私密连接工厂（单例）
     *
     * @since v2.3.8
     */
    private volatile static ConnectionSocketFactory connectionSocketFactory;

    private static ConnectionSocketFactory getConnectionSocketFactory() {
        if (connectionSocketFactory == null) {
            synchronized (HttpClientUtils.class) {
                if (connectionSocketFactory == null) {
                    try {
                        SSLContext sslContext = SSLContext.getInstance("TLS");
                        sslContext.init(null, new TrustManager[]{new TrustManager()}, null);

                        connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                    } catch (NoSuchAlgorithmException e) {
                        logger.error("NoSuchAlgorithmException", e);
                    } catch (KeyManagementException e) {
                        logger.error("KeyManagementException", e);
                    }
                }
            }
        }
        return connectionSocketFactory;
    }

    /**
     * 处理用户请求
     *
     * @param request
     * @return
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月12日 下午3:29:04
     */
    public static ResponseMessage doRequest(HttpUriRequest request) {
        if (null == request) {
            throw new IllegalArgumentException("HttpUriRequest must not be null");
        }
        if (null == request.getURI()) {
            throw new IllegalArgumentException("HttpUriRequest URI must not be null");
        }

        // 设置全局的标准cookie策略
        RequestConfig config = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .setConnectTimeout(30 * 1000)
                .setSocketTimeout(30 * 1000)
                .setConnectionRequestTimeout(30 * 1000)
                .build();
        // 创建可用Scheme
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(Scheme.HTTP.name(), PlainConnectionSocketFactory.INSTANCE)
                .register(Scheme.HTTPS.name(), getConnectionSocketFactory())
                .build();
        // 创建ConnectionManager
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        // 设置可关闭的httpclient
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();

        ResponseMessage responseMessage = null;
        try {
            // 发起用户请求
            CloseableHttpResponse httpResponse = httpClient.execute(request);
            // 处理结果返回码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            // 返回结果
            String entity = EntityUtils.toString(httpResponse.getEntity());
            responseMessage = new ResponseMessage(statusCode, entity);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException：", e);
            responseMessage = new ResponseMessage(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
        } catch (IOException e) {
            logger.error("IOException：", e);
            responseMessage = new ResponseMessage(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("IOException：", e);
            }
        }
        return responseMessage;
    }

    /**
     * 处理用户请求
     *
     * @param request
     * @param callBack
     * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年8月11日 上午11:17:59
     */
    public static void doRequest(HttpUriRequest request, ResponseCallBack callBack) {
        if (null == request) {
            throw new IllegalArgumentException("HttpUriRequest must not be null");
        }
        if (null == request.getURI()) {
            throw new IllegalArgumentException("HttpUriRequest URI must not be null");
        }

        // 设置全局的标准cookie策略
        RequestConfig config = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .setConnectTimeout(30 * 1000)
                .setSocketTimeout(30 * 1000)
                .setConnectionRequestTimeout(30 * 1000)
                .build();
        // 创建可用Scheme
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(Scheme.HTTP.name(), PlainConnectionSocketFactory.INSTANCE)
                .register(Scheme.HTTPS.name(), getConnectionSocketFactory())
                .build();
        // 创建ConnectionManager
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        // 设置可关闭的httpclient
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();

        try {
            // 发起用户请求
            CloseableHttpResponse httpResponse = httpClient.execute(request);

            // 是否将处理结果返回
            if (null != callBack) {
                // 返回码
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                // 返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("UTF-8"));
                // 响应回调
                callBack.response(statusCode, result);
            }
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException：", e);
            if (null != callBack) {
                callBack.response(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        } catch (IOException e) {
            logger.error("IOException：", e);
            if (null != callBack) {
                callBack.response(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("IOException：", e);
            }
        }
    }

}
