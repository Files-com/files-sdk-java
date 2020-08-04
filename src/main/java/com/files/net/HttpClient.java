package com.files.net;

import com.files.FilesConfig;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class HttpClient {
  private static HttpClient instance;
  protected static final Logger log = LogManager.getLogger(HttpClient.class);
  protected OkHttpClient okHttpClient;

  protected HttpClient() {
    FilesConfig filesConfig = FilesConfig.getInstance();
    ConnectionPool pool = new ConnectionPool(filesConfig.getUpstreamMaxConnections(), filesConfig.getUpstreamTimeout(), TimeUnit.MILLISECONDS);
    try {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.cache(null);
      builder.connectionPool(pool);
      builder.hostnameVerifier((hostname, session) -> true);
      builder.retryOnConnectionFailure(false);
      builder.addNetworkInterceptor(new FilesHttpInterceptor());

      if (filesConfig.getUpstreamInsecureAllowed()) {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new java.security.cert.X509Certificate[]{};
            }
          }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      }

      if (FilesConfig.getInstance().getUpstreamHttp2Enabled()) {
        builder.protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2));
      } else {
        builder.protocols(Arrays.asList(Protocol.HTTP_1_1));
      }

      okHttpClient = builder.build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static OkHttpClient getHttpClient() {
    if (instance == null) {
      synchronized (HttpClient.class) {
        if (instance == null) {
          instance = new HttpClient();
        }
      }
    }
    return instance.okHttpClient;
  }
}
