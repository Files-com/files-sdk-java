package com.files.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.AbstractExecutionAwareRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
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
 * <p>To send requests with an Apache HttpClient of your own, build a {@link GuardedApacheClient}
 * with {@link Builder#buildClient()}. You can send your own requests with it, and
 * {@link #borrowed(GuardedApacheClient)} and {@link #owned(GuardedApacheClient)} make transports,
 * as many as you like, that send the clients' requests with that very client. A transport that
 * borrows it leaves it open; one that owns it closes it, once, when the transport is closed, and from
 * then on the client, and every transport using it, refuses requests.
 *
 * <p>Closing a transport refuses later requests through it and aborts the requests still using it,
 * a response whose body is still being read, and a transfer's request still waiting its turn for the
 * pool, included. A transport that owns a client that made its own pool, as one
 * {@link Builder#build() built} without {@link Builder#connectionManager a pool of yours} does,
 * closes that pool, which ends them, and stops the pool's idle connection evictor
 * thread. Any other transport aborts only its own requests. One that borrows its client leaves the
 * client open: you, and the other transports using it, go on using it. One that owns its client,
 * whose pool is yours, then closes the client, once, so that later requests through it, yours and
 * those of other transports using it, are refused; your pool, and clients built on it apart from
 * that one, stay usable.
 *
 * <p>Transfers sharing a transport, or sharing a {@link GuardedApacheClient} through several
 * transports, send no more part and range requests at once than its pool can lease connections
 * for: to each storage host, at most the pool's limit for that host's route, and to all storage
 * hosts together, at most the pool's total limit. For the pool a transport makes, those
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
  private final GuardedApacheClient client;
  // Whether closing the transport closes its client.
  private final boolean ownsClient;
  private final RequestConfig defaults;
  private final AtomicBoolean closed = new AtomicBoolean();
  // Admits storage requests within the pool's limits, or null when the pool's limits cannot be
  // known. It is the client's, shared by every transport using the client.
  private final StorageGate storageGate;
  // The requests still using the transport, each with its response until that is closed, which
  // closing the transport aborts; guarded by itself. Null for a transport that owns a client that
  // made its own pool: closing the client closes the pool, which ends them all.
  private final Set<Lease> leases;

  private HttpTransport(GuardedApacheClient client, boolean ownsClient) {
    this.client = client;
    this.ownsClient = ownsClient;
    this.defaults = client.getConfig();
    this.storageGate = client.storageGate();
    this.leases = ownsClient && client.ownsPool() ? null : new HashSet<Lease>();
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Makes a transport that sends requests with {@code client}, which stays yours: closing the
   * transport leaves it open, for you and the other transports using it.
   *
   * @param client a client built with {@link Builder#buildClient()}
   * @return the transport
   * @throws IllegalArgumentException if {@code client} is null
   * @throws IllegalStateException if {@code client} has been closed
   */
  public static HttpTransport borrowed(GuardedApacheClient client) {
    return using(client, false);
  }

  /**
   * Makes a transport that sends requests with {@code client} and closes it, once, when the transport
   * is closed; from then on the client refuses requests, those of other transports using it
   * included.
   *
   * @param client a client built with {@link Builder#buildClient()}
   * @return the transport
   * @throws IllegalArgumentException if {@code client} is null
   * @throws IllegalStateException if {@code client} has been closed
   */
  public static HttpTransport owned(GuardedApacheClient client) {
    return using(client, true);
  }

  private static HttpTransport using(GuardedApacheClient client, boolean owned) {
    if (client == null) {
      throw new IllegalArgumentException("The HTTP client cannot be null");
    }
    if (client.isClosed()) {
      throw new IllegalStateException("The HTTP client has been closed");
    }
    return new HttpTransport(client, owned);
  }

  public boolean isClosed() {
    return closed.get();
  }

  /**
   * Closes the transport, as the class description describes. Requests still using it fail, and
   * later requests through it fail with {@link IllegalStateException}. Closing it again has no
   * effect.
   */
  @Override
  public void close() throws IOException {
    List<Lease> open;
    if (leases == null) {
      if (!closed.compareAndSet(false, true)) {
        return;
      }
      open = new ArrayList<Lease>();
    } else {
      synchronized (leases) {
        if (!closed.compareAndSet(false, true)) {
          return;
        }
        open = new ArrayList<Lease>(leases);
        leases.clear();
      }
    }
    if (storageGate != null) {
      // Requests of this transport waiting for a storage slot stop waiting; others go on waiting.
      storageGate.wakeAll();
    }
    for (Lease lease : open) {
      lease.abort();
    }
    if (ownsClient) {
      client.close();
    }
  }

  // Sends request, with its context, over the client; once the transport is closed, refuses it. A
  // transport that aborts its own requests keeps track of each until its response is closed.
  CloseableHttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
    if (leases == null) {
      if (closed.get()) {
        throw closedError();
      }
      return client.executeForSdk(request, context);
    }
    Lease lease = new Lease(request);
    synchronized (leases) {
      if (closed.get()) {
        throw closedError();
      }
      leases.add(lease);
    }
    CloseableHttpResponse response;
    try {
      response = client.executeForSdk(request, context);
    } catch (IOException | RuntimeException | Error e) {
      release(lease);
      throw e;
    }
    synchronized (leases) {
      if (!closed.get()) {
        lease.response = response;
        return new LeasedResponse(response, lease);
      }
    }
    // The transport was closed while the request was out, and its response came after: it is closed,
    // not handed on.
    closeQuietly(response);
    throw closedError();
  }

  private void release(Lease lease) {
    synchronized (leases) {
      leases.remove(lease);
    }
  }

  private static IllegalStateException closedError() {
    return new IllegalStateException("The HTTP transport has been closed");
  }

  private static void closeQuietly(CloseableHttpResponse response) {
    try {
      response.close();
    } catch (IOException e) {
      // The connection is discarded.
    }
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
  // the pool's limits cannot be known, or the URL names no host, so there is nothing to wait for. Once
  // the transport is closed, the request is refused, and a wait for a slot ends at once, taking
  // nothing; a slot given as the transport closes is given back.
  StorageGate.Slot storageRequestSlot(URI uri, OperationScope scope) {
    HttpRoute route = routeOf(uri);
    if (storageGate == null || route == null) {
      return null;
    }
    StorageGate.Slot slot = storageGate.acquire(route, scope, closed::get);
    if (slot == null || closed.get()) {
      if (slot != null) {
        slot.close();
      }
      throw closedError();
    }
    return slot;
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

  // A request using the transport, and its response once it has one.
  private static final class Lease {
    private final HttpUriRequest request;
    // Set, holding the transport's leases, once the request has its response.
    private CloseableHttpResponse response;

    Lease(HttpUriRequest request) {
      this.request = request;
    }

    // Ends the request: aborts it, which also ends a body still being read, and closes its response.
    void abort() {
      if (request instanceof AbstractExecutionAwareRequest) {
        ((AbstractExecutionAwareRequest) request).abort();
      }
      if (response != null) {
        closeQuietly(response);
      }
    }
  }

  // A response as the client gave it, whose closing also ends its request's lease. Everything else is
  // the response's own, its entity and the body it reads included.
  private final class LeasedResponse implements CloseableHttpResponse {
    private final CloseableHttpResponse response;
    private final Lease lease;

    LeasedResponse(CloseableHttpResponse response, Lease lease) {
      this.response = response;
      this.lease = lease;
    }

    @Override
    public void close() throws IOException {
      try {
        response.close();
      } finally {
        release(lease);
      }
    }

    @Override
    public StatusLine getStatusLine() {
      return response.getStatusLine();
    }

    @Override
    public void setStatusLine(StatusLine statusline) {
      response.setStatusLine(statusline);
    }

    @Override
    public void setStatusLine(ProtocolVersion ver, int code) {
      response.setStatusLine(ver, code);
    }

    @Override
    public void setStatusLine(ProtocolVersion ver, int code, String reason) {
      response.setStatusLine(ver, code, reason);
    }

    @Override
    public void setStatusCode(int code) {
      response.setStatusCode(code);
    }

    @Override
    public void setReasonPhrase(String reason) {
      response.setReasonPhrase(reason);
    }

    @Override
    public HttpEntity getEntity() {
      return response.getEntity();
    }

    @Override
    public void setEntity(HttpEntity entity) {
      response.setEntity(entity);
    }

    @Override
    public Locale getLocale() {
      return response.getLocale();
    }

    @Override
    public void setLocale(Locale loc) {
      response.setLocale(loc);
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
      return response.getProtocolVersion();
    }

    @Override
    public boolean containsHeader(String name) {
      return response.containsHeader(name);
    }

    @Override
    public Header[] getHeaders(String name) {
      return response.getHeaders(name);
    }

    @Override
    public Header getFirstHeader(String name) {
      return response.getFirstHeader(name);
    }

    @Override
    public Header getLastHeader(String name) {
      return response.getLastHeader(name);
    }

    @Override
    public Header[] getAllHeaders() {
      return response.getAllHeaders();
    }

    @Override
    public void addHeader(Header header) {
      response.addHeader(header);
    }

    @Override
    public void addHeader(String name, String value) {
      response.addHeader(name, value);
    }

    @Override
    public void setHeader(Header header) {
      response.setHeader(header);
    }

    @Override
    public void setHeader(String name, String value) {
      response.setHeader(name, value);
    }

    @Override
    public void setHeaders(Header[] headers) {
      response.setHeaders(headers);
    }

    @Override
    public void removeHeader(Header header) {
      response.removeHeader(header);
    }

    @Override
    public void removeHeaders(String name) {
      response.removeHeaders(name);
    }

    @Override
    public HeaderIterator headerIterator() {
      return response.headerIterator();
    }

    @Override
    public HeaderIterator headerIterator(String name) {
      return response.headerIterator(name);
    }

    @Override
    @Deprecated
    public org.apache.http.params.HttpParams getParams() {
      return response.getParams();
    }

    @Override
    @Deprecated
    public void setParams(org.apache.http.params.HttpParams params) {
      response.setParams(params);
    }
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
     * transport, and its Apache client, never shut it down, and do not evict its idle connections;
     * you do both. It cannot be combined with the builder's other connection settings.
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
     * on the Apache builder have no effect, because the pool is the builder's or yours; use
     * {@link #sslContext(SSLContext)}, or your pool's own.
     */
    public Builder customize(Consumer<HttpClientBuilder> customizer) {
      this.customizer = customizer;
      return this;
    }

    /**
     * Builds a transport with an Apache client of its own, which it closes when it is closed.
     */
    public HttpTransport build() {
      return new HttpTransport(buildClient(), true);
    }

    /**
     * Builds an Apache client with these settings, for you to send your own requests with and to give
     * to transports with {@link HttpTransport#borrowed(GuardedApacheClient)} or
     * {@link HttpTransport#owned(GuardedApacheClient)}. Each call builds another client, with a pool of
     * its own unless you gave the builder yours.
     */
    public GuardedApacheClient buildClient() {
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
      return new GuardedApacheClient(builder.build(), ownsPool, storageGate(manager));
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
