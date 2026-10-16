package com.files.net;

import com.files.FilesClient;
import com.files.FilesConfig;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Sends a prepared request, retrying it as {@link FilesClient#defaultClient()} retries requests.
 * Kept for compatibility; use a {@link FilesClient}.
 */
public class FilesHttpExecutor {
  private final CloseableHttpClient client;
  private HttpTransport transport;

  FilesHttpExecutor() {
    this.client = null;
  }

  /**
   * An executor that sends requests with the given HttpClient instead of the default client's
   * connections.
   */
  public FilesHttpExecutor(CloseableHttpClient client) {
    this.client = client;
  }

  /**
   * Sends the request as it is, and returns its successful response for the caller to read and
   * close. A request whose body cannot be sent twice is sent once. Retries follow
   * {@link FilesConfig}: its limit and wait are read before each attempt. An HttpClient given to this
   * executor keeps its own timeouts and connections.
   */
  public HttpResponse executeWithRetry(HttpRequestBase request) throws IOException {
    FilesConfig config = FilesConfig.getInstance();
    ClientRuntime.Execution execution;
    if (client != null) {
      execution = client::execute;
    } else {
      HttpTransport defaultTransport = transport();
      URI apiRoot = URI.create(config.getApiRoot());
      execution = prepared -> {
        CredentialGuard.Exchange guard = CredentialGuard.forApi(apiRoot);
        try {
          return defaultTransport.execute(prepared, HttpClientContext.create());
        } finally {
          guard.close();
        }
      };
    }
    return ClientRuntime.executePrepared(request, execution);
  }

  private synchronized HttpTransport transport() {
    if (transport == null) {
      transport = HttpTransport.builder().connectionManager(FilesClient.connectionManager).build();
    }
    return transport;
  }
}
