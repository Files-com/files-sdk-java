package com.files.net;

import com.files.FilesClient;
import com.files.FilesConfig;
import devcsrj.okhttp3.logging.HttpLoggingInterceptor;
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

public class FilesHttpClient {
  private static FilesHttpClient instance;
  protected static final Logger log = LogManager.getLogger(FilesHttpClient.class);
  protected OkHttpClient okHttpClient;

  protected FilesHttpClient() {
    FilesConfig filesConfig = FilesConfig.getInstance();
    ConnectionPool pool = FilesClient.httpPool;
    try {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.cache(null);
      builder.connectionPool(pool);
      builder.hostnameVerifier((hostname, session) -> true);
      builder.retryOnConnectionFailure(false);
      builder.addInterceptor(new FilesHttpInterceptor());

      if (FilesConfig.getInstance().getHttpLoggingEnabled() && log.isDebugEnabled()) {
        builder.addInterceptor(new HttpLoggingInterceptor());
      }

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
      synchronized (FilesHttpClient.class) {
        if (instance == null) {
          instance = new FilesHttpClient();
        }
      }
    }
    return instance.okHttpClient;
  }
}
