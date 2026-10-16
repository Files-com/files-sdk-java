package com.files.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

/**
 * The HTTP connections that clients send their requests over: an Apache HttpClient and its
 * connection pool. A transport is safe to use from many threads, and one transport can be shared
 * by several {@link com.files.FilesClient} instances, which then share its connections but not
 * their credentials or settings.
 *
 * <p>Whoever builds a transport owns it. Give a client one you built with
 * {@link com.files.FilesClient.Builder#transport(HttpTransport)} to share it: closing the client
 * leaves it open, and you close it once no client uses it. Give a client a
 * {@link com.files.FilesClient.Builder#transport(HttpTransport.Builder) builder} instead, and the
 * client builds a transport of its own and closes it when the client is closed.
 *
 * <p>Closing a transport aborts the requests still using it and closes its connections. When the
 * transport made its pool, closing it also stops the pool's idle connection evictor thread.
 *
 * <p>Transfers sharing a transport send no more part and range requests at once than its pool can
 * lease connections for: to each storage host, at most the pool's limit for that host's route, and to
 * all storage hosts together, at most the pool's total limit. For the pool a transport makes, those
 * are its {@link Builder#maxConnectionsPerRoute(int) per-route} and {@link Builder#maxConnections(int)
 * total} limits; for a {@link PoolingHttpClientConnectionManager} you manage, its limit for the route
 * (including one you set for that route) and its total, read whenever a request waits and never
 * changed, so a limit you change later is followed, without forgetting the requests in flight; for a
 * {@link BasicHttpClientConnectionManager}, one request at a time. Further requests wait for one of
 * those to end, in the order they asked, instead of waiting for a pooled connection, which could time
 * out and use up a retry, and a transfer's concurrency never goes above the limit for its storage
 * host. The route of a request is the one Apache's default route planner gives its URL, through the
 * transport's default request proxy if one is set; a route planner of your own may use routes, and
 * limits, the transport does not see. Requests to the Files.com API, and anything else using the
 * pool, are not counted: they can still wait for a connection, and while they hold the pool's
 * connections, so can storage requests. A pool of another kind is not read, and its requests are
 * not held back. The defaults let one storage host have as many connections as an automatic
 * transfer may send parts at once, 1024, within 2048 in all; connections are opened only as they are
 * needed.
 */
public final class HttpTransport implements Closeable {
  private final CloseableHttpClient httpClient;
  private final RequestConfig defaults;
  private final AtomicBoolean closed = new AtomicBoolean();
  // Admits storage requests within the pool's limits, or null when the pool's limits cannot be
  // known.
  private final StorageGate storageGate;

  private HttpTransport(CloseableHttpClient httpClient, StorageGate storageGate) {
    this.httpClient = httpClient;
    RequestConfig configured = httpClient instanceof Configurable ? ((Configurable) httpClient).getConfig() : null;
    this.defaults = configured != null ? configured : RequestConfig.DEFAULT;
    this.storageGate = storageGate;
  }

  public static Builder builder() {
    return new Builder();
  }

  public boolean isClosed() {
    return closed.get();
  }

  /**
   * Closes the transport. Requests still using it fail, and later requests through it fail with
   * {@link IllegalStateException}. Closing it again has no effect.
   */
  @Override
  public void close() throws IOException {
    if (closed.compareAndSet(false, true)) {
      httpClient.close();
    }
  }

  CloseableHttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
    if (closed.get()) {
      throw new IllegalStateException("The HTTP transport has been closed");
    }
    return httpClient.execute(request, context);
  }

  // The request settings a request starts from: the transport's own, such as a proxy, with the
  // client's time limits.
  RequestConfig.Builder requestConfig() {
    return RequestConfig.copy(defaults);
  }

  // The most storage requests to uri's storage host the pool could serve at once now, or 0 when that
  // cannot be known.
  int storageCapacity(URI uri) {
    HttpRoute route = routeOf(uri);
    return storageGate == null || route == null ? 0 : storageGate.capacity(route);
  }

  // Waits for a slot for one storage request to uri, to close once the request has ended; null when
  // the pool's limits cannot be known, or the URL names no host, so there is nothing to wait for.
  StorageGate.Slot storageRequestSlot(URI uri, OperationScope scope) {
    HttpRoute route = routeOf(uri);
    return storageGate == null || route == null ? null : storageGate.acquire(route, scope);
  }

  // The route Apache's default route planner gives a request for uri, through the transport's default
  // request proxy if it has one; null for a URL without a host.
  private HttpRoute routeOf(URI uri) {
    if (uri.getHost() == null) {
      return null;
    }
    String scheme = uri.getScheme() == null ? "http" : uri.getScheme().toLowerCase(Locale.ROOT);
    boolean secure = "https".equals(scheme);
    HttpHost target = new HttpHost(uri.getHost(), uri.getPort() >= 0 ? uri.getPort() : secure ? 443 : 80, scheme);
    HttpHost proxy = defaults.getProxy();
    return proxy == null ? new HttpRoute(target, null, secure) : new HttpRoute(target, null, proxy, secure);
  }

  /**
   * Builds an {@link HttpTransport}. A builder is not safe for use by several threads at once.
   */
  public static final class Builder {
    private int maxConnections = 2048;
    private int maxConnectionsPerRoute = ConcurrencyTuning.MAX_PARTS;
    private Duration idleConnectionTimeout = Duration.ofSeconds(5);
    private SSLContext sslContext;
    private DnsResolver dnsResolver;
    private HttpClientConnectionManager connectionManager;
    private Consumer<HttpClientBuilder> customizer;

    private Builder() {
    }

    /**
     * The most connections the transport keeps open at once, to all hosts. Default 2048.
     */
    public Builder maxConnections(int maxConnections) {
      if (maxConnections < 1) {
        throw new IllegalArgumentException("A transport must allow at least one connection");
      }
      this.maxConnections = maxConnections;
      return this;
    }

    /**
     * The most connections the transport keeps open at once to one host. Default 1024, the most
     * parts an automatic transfer sends at once. Parts of an upload sent at the same time each need
     * one, so this, within {@link #maxConnections(int)}, also limits how many go to one storage host
     * at once.
     */
    public Builder maxConnectionsPerRoute(int maxConnectionsPerRoute) {
      if (maxConnectionsPerRoute < 1) {
        throw new IllegalArgumentException("A transport must allow at least one connection per route");
      }
      this.maxConnectionsPerRoute = maxConnectionsPerRoute;
      return this;
    }

    /**
     * How long a connection may stay unused in the pool before it is closed. Default 5 seconds.
     */
    public Builder idleConnectionTimeout(Duration idleConnectionTimeout) {
      if (idleConnectionTimeout == null || idleConnectionTimeout.isNegative() || idleConnectionTimeout.isZero()) {
        throw new IllegalArgumentException("The idle connection timeout must be positive");
      }
      this.idleConnectionTimeout = idleConnectionTimeout;
      return this;
    }

    /**
     * The TLS settings for HTTPS connections, such as trusted certificates or a client
     * certificate. The server's certificate is checked by the context's trust managers, and its
     * host name is always verified. Default: the Java runtime's own.
     */
    public Builder sslContext(SSLContext sslContext) {
      this.sslContext = sslContext;
      return this;
    }

    /**
     * Resolves host names to addresses. Default: the system resolver.
     */
    public Builder dnsResolver(DnsResolver dnsResolver) {
      this.dnsResolver = dnsResolver;
      return this;
    }

    /**
     * Sends requests over a connection pool you manage, instead of one the transport makes. The
     * transport never shuts it down, and does not evict its idle connections; you do both. It
     * cannot be combined with the builder's other connection settings.
     */
    public Builder connectionManager(HttpClientConnectionManager connectionManager) {
      this.connectionManager = connectionManager;
      return this;
    }

    /**
     * Adjusts the Apache {@link HttpClientBuilder} before the transport is built, for settings
     * such as a proxy ({@code setProxy}, {@code setRoutePlanner}, {@code
     * setDefaultCredentialsProvider}), request and response interceptors for instrumentation,
     * default headers or the redirect strategy.
     *
     * <p>A few settings stay the SDK's, whatever the customizer sets: the connection pool, which
     * comes from this builder; automatic retries, which are off because every client retries
     * requests itself, within one limit; and the removal of Files.com credentials from every
     * request, including each redirect, that is not sent to the client's API root. That removal
     * happens as each request is written to its connection, so it holds whatever interceptors,
     * HTTP processor, request executor or redirect strategy the customizer sets. TLS settings made
     * on the Apache builder have no effect, because the pool is the transport's; use
     * {@link #sslContext(SSLContext)}.
     */
    public Builder customize(Consumer<HttpClientBuilder> customizer) {
      this.customizer = customizer;
      return this;
    }

    public HttpTransport build() {
      boolean ownsPool = connectionManager == null;
      if (!ownsPool && (sslContext != null || dnsResolver != null)) {
        throw new IllegalStateException("A transport with your own connection manager takes its TLS and DNS settings from it");
      }
      HttpClientConnectionManager manager = ownsPool ? pool() : connectionManager;
      HttpClientBuilder builder = HttpClients.custom();
      if (customizer != null) {
        customizer.accept(builder);
      }
      builder
          .setConnectionManager(new CredentialGuard.GuardedConnectionManager(manager))
          .setConnectionManagerShared(!ownsPool)
          .disableAutomaticRetries()
          .setServiceUnavailableRetryStrategy(null);
      if (ownsPool) {
        builder.evictExpiredConnections().evictIdleConnections(idleConnectionTimeout.toMillis(), TimeUnit.MILLISECONDS);
      }
      return new HttpTransport(builder.build(), storageGate(manager));
    }

    // The gate for the pool's limits, which it reads when they are needed and never changes: those of
    // a pooling manager, whether the transport made it or you did, including limits set for routes;
    // one connection for a basic manager; none for another kind, whose limits cannot be read.
    private static StorageGate storageGate(HttpClientConnectionManager manager) {
      if (manager instanceof PoolingHttpClientConnectionManager) {
        PoolingHttpClientConnectionManager pool = (PoolingHttpClientConnectionManager) manager;
        return new StorageGate(new StorageGate.Limits() {
          @Override
          public int perRoute(HttpRoute route) {
            return pool.getMaxPerRoute(route);
          }

          @Override
          public int total() {
            return pool.getMaxTotal();
          }
        });
      }
      if (manager instanceof BasicHttpClientConnectionManager) {
        return new StorageGate(new StorageGate.Limits() {
          @Override
          public int perRoute(HttpRoute route) {
            return 1;
          }

          @Override
          public int total() {
            return 1;
          }
        });
      }
      return null;
    }

    private PoolingHttpClientConnectionManager pool() {
      SSLConnectionSocketFactory tls = new SSLConnectionSocketFactory(
          sslContext != null ? sslContext : SSLContexts.createDefault(),
          SSLConnectionSocketFactory.getDefaultHostnameVerifier());
      PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(
          RegistryBuilder.<ConnectionSocketFactory>create()
              .register("http", PlainConnectionSocketFactory.getSocketFactory())
              .register("https", tls)
              .build(),
          null, null, dnsResolver, -1, TimeUnit.MILLISECONDS);
      pool.setMaxTotal(maxConnections);
      pool.setDefaultMaxPerRoute(maxConnectionsPerRoute);
      return pool;
    }
  }
}
