package com.files.net;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * An Apache HttpClient that {@link HttpTransport.Builder#buildClient()} builds with the SDK's
 * protections, for you to send your own requests with and to give to Files.com clients:
 * {@link HttpTransport#borrowed(GuardedApacheClient)} and {@link HttpTransport#owned(GuardedApacheClient)}
 * make transports that send their requests with this very client. It is safe for use by several
 * threads, and several transports, and so several {@link com.files.FilesClient} instances, can use
 * it at once, sharing its connections but not their credentials or settings.
 *
 * <p>It is built as {@link HttpTransport.Builder} describes: with your proxy, TLS, pool,
 * interceptors and other customizations, and with the settings that stay the SDK's. It does not
 * retry automatically: a Files.com client retries its requests itself, within one limit. Files.com
 * credentials are removed from every request as it is written to its connection unless a Files.com
 * client sends it to its API root, so a request you send yourself never carries them, even one you
 * send from an interceptor while a Files.com client's request is being sent. Its
 * {@link #getConfig() default request settings}, such as a proxy, are the ones every request starts
 * from; a Files.com client sets its own time limits on its requests. Transports using it share its
 * pool's limits for transfers, as {@link HttpTransport} describes.
 *
 * <p>Only a client built this way can be given to a transport: an Apache client built otherwise
 * could bypass those protections.
 *
 * <p>Closing it closes the pool it made, if it made one, but never a pool you manage. From then on it
 * refuses requests with {@link IllegalStateException}, those of the transports using it included.
 * Closing it again has no effect.
 */
public final class GuardedApacheClient extends CloseableHttpClient implements Configurable {
  private final CloseableHttpClient delegate;
  private final RequestConfig defaults;
  // Whether the client made its pool, which closing it then closes.
  private final boolean ownsPool;
  // Admits transfers' storage requests within the pool's limits, for every transport using the
  // client, or null when the pool's limits cannot be known.
  private final StorageGate storageGate;
  private final AtomicBoolean closed = new AtomicBoolean();

  GuardedApacheClient(CloseableHttpClient delegate, boolean ownsPool, StorageGate storageGate) {
    this.delegate = delegate;
    RequestConfig configured = delegate instanceof Configurable ? ((Configurable) delegate).getConfig() : null;
    this.defaults = configured != null ? configured : RequestConfig.DEFAULT;
    this.ownsPool = ownsPool;
    this.storageGate = storageGate;
  }

  /** Returns the settings every request starts from, such as a proxy, as the client was built with them. */
  @Override
  public RequestConfig getConfig() {
    return defaults;
  }

  /** Returns whether the client has been closed. */
  public boolean isClosed() {
    return closed.get();
  }

  /**
   * Closes the client, as the class description describes. Closing it again has no effect.
   */
  @Override
  public void close() throws IOException {
    if (closed.compareAndSet(false, true)) {
      delegate.close();
    }
  }

  // Sends a request of your own, itself, with its context, so that its configuration, its abort and
  // what the context records stay yours. It is sent in an exchange of its own that allows no Files.com
  // credentials, even when a Files.com client is sending a request on this thread meanwhile, as from
  // an interceptor of that request; that request's exchange is as it was once this one is sent.
  @Override
  protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
    checkOpen();
    CredentialGuard.Exchange own = CredentialGuard.forStorage();
    try {
      return delegate.execute(target, request, context);
    } finally {
      own.close();
    }
  }

  // Sends a request a Files.com client prepared, in the exchange the client set for it on this thread,
  // which says where its credentials may go and when its storage URL expires.
  CloseableHttpResponse executeForSdk(HttpUriRequest request, HttpContext context) throws IOException {
    checkOpen();
    return delegate.execute(request, context);
  }

  private void checkOpen() {
    if (closed.get()) {
      throw new IllegalStateException("The HTTP client has been closed");
    }
  }

  @Override
  @Deprecated
  public org.apache.http.params.HttpParams getParams() {
    return delegate.getParams();
  }

  @Override
  @Deprecated
  public org.apache.http.conn.ClientConnectionManager getConnectionManager() {
    return delegate.getConnectionManager();
  }

  boolean ownsPool() {
    return ownsPool;
  }

  StorageGate storageGate() {
    return storageGate;
  }
}
