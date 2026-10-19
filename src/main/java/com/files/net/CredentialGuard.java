package com.files.net;

import com.files.util.UrlUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

// Keeps Files.com credentials to the API root a request was sent to. The check runs as each request
// head is written to a connection, first request and every redirect alike, below everything an
// application can customize in the Apache HttpClient: interceptors, a replaced HttpProcessor,
// request executor or redirect strategy all pass through it. A request that has once gone to
// another origin carries no credentials again, even back at the API root.
//
// The SDK names the API root for the requests it sends on the current thread; Apache HttpClient 4
// sends a request, and its redirects, on the thread that executes it. A request with no API root,
// such as one to a storage URL, never carries credentials.
final class CredentialGuard {
  private static final String[] FILES_AUTH_HEADERS = {
      "X-FilesAPI-Key",
      "X-FilesAPI-Auth",
      "X-Files-Workspace-Id"
  };

  private static final ThreadLocal<Exchange> CURRENT = new ThreadLocal<Exchange>();

  private CredentialGuard() {
  }

  // Requests sent on this thread until the result is closed may carry credentials to apiRoot.
  static Exchange forApi(URI apiRoot) {
    return new Exchange(apiRoot, null, null);
  }

  // Requests sent on this thread until the result is closed carry no credentials.
  static Exchange forStorage() {
    return new Exchange(null, null, null);
  }

  // As forStorage, for a request to a signed storage URL known to expire at expiresAt, or null when
  // that is not known. A request head about to be written for url itself once that time has come is
  // refused with ExpiredUrl: after any wait for a connection, and before any of it is written. A head
  // for another URL, such as one a redirect leads to, is refused the same way once the expiry that URL
  // states itself has come, whether or not url's own has.
  static Exchange forStorage(URI url, Instant expiresAt) {
    return new Exchange(null, url, expiresAt);
  }

  // Removes the credentials from a request head about to be written to a connection to target,
  // unless the current exchange allows them there. Refuses it instead when it is for a signed URL
  // that has expired (forStorage). The refusal only throws: the connection held now is given up as
  // the request fails, and the transfer renews the URL once it holds none.
  static void beforeSend(HttpRequest request, HttpHost target) {
    Exchange exchange = CURRENT.get();
    if (exchange != null && exchange.url != null) {
      Instant expiresAt = expiresAt(exchange, request, target);
      if (expiresAt != null && !Instant.now().isBefore(expiresAt)) {
        throw new ExpiredUrl(exchange.mayHaveSent);
      }
    }
    if (exchange != null && exchange.apiRoot != null && !sameOrigin(exchange.apiRoot, target)) {
      exchange.left = true;
    }
    if (exchange == null || exchange.apiRoot == null || exchange.left) {
      for (String header : FILES_AUTH_HEADERS) {
        request.removeHeaders(header);
      }
    }
  }

  // When the URL a request head about to be written to target is for stops being usable: the
  // exchange's own expiry when the head is for the exchange's URL itself; otherwise, as after a
  // redirect to another URL, the expiry that URL states itself, or null when it states none. The
  // request line is read as written, never rewritten.
  private static Instant expiresAt(Exchange exchange, HttpRequest request, HttpHost target) {
    String requestTarget = request.getRequestLine().getUri();
    if (requestTarget == null) {
      return null;
    }
    if (isExchangeUrl(exchange, requestTarget, target)) {
      return exchange.expiresAt;
    }
    return UrlUtils.expiresAt(requestTarget);
  }

  // Whether a request head with requestTarget, written to target, is for the exchange's URL: the same
  // origin, and the same path and query, compared as written, whether the request line holds them
  // alone (origin form) or after the scheme and authority (absolute form, as to a proxy).
  private static boolean isExchangeUrl(Exchange exchange, String requestTarget, HttpHost target) {
    if (!sameOrigin(exchange.url, target)) {
      return false;
    }
    if (requestTarget.startsWith("/")) {
      return requestTarget.equals(exchange.requestTarget);
    }
    URI absolute;
    try {
      absolute = new URI(requestTarget);
    } catch (URISyntaxException e) {
      return false;
    }
    return absolute.isAbsolute() && sameOrigin(absolute, target) && requestTarget(absolute).equals(exchange.requestTarget);
  }

  // The request target, in origin form, of a request for url: its path and query exactly as written,
  // and / for an empty path.
  private static String requestTarget(URI url) {
    String path = url.getRawPath() == null || url.getRawPath().isEmpty() ? "/" : url.getRawPath();
    return url.getRawQuery() == null ? path : path + "?" + url.getRawQuery();
  }

  // Whether target has apiRoot's origin: its scheme, host and port, a port left out being its
  // scheme's default. The SDK's one origin rule, for credentials here and for where a region mismatch
  // may come from (ClientRuntime). An IPv6 address is the same host however it is written.
  static boolean sameOrigin(URI apiRoot, HttpHost target) {
    return target != null
        && apiRoot.getScheme() != null
        && apiRoot.getHost() != null
        && apiRoot.getScheme().equalsIgnoreCase(target.getSchemeName())
        && sameHost(apiRoot.getHost(), target.getHostName())
        && effectivePort(apiRoot.getScheme(), apiRoot.getPort()) == effectivePort(target.getSchemeName(), target.getPort());
  }

  // Whether two hosts are the same: the same name, ignoring case, or the same IPv6 address, each with
  // or without brackets and written in any of its forms, such as [::1] and [0:0:0:0:0:0:0:1]. Only
  // addresses are compared, never names: InetAddress.getByName reads an address in brackets without
  // looking anything up, and fails for anything else in brackets. An address with a zone, such as
  // fe80::1%eth0, must be written the same way.
  private static boolean sameHost(String first, String second) {
    if (first == null || second == null) {
      return false;
    }
    String a = withoutBrackets(first);
    String b = withoutBrackets(second);
    if (a.equalsIgnoreCase(b)) {
      return true;
    }
    if (!a.contains(":") || !b.contains(":") || a.contains("%") || b.contains("%")) {
      return false;
    }
    try {
      return InetAddress.getByName("[" + a + "]").equals(InetAddress.getByName("[" + b + "]"));
    } catch (UnknownHostException e) {
      return false;
    }
  }

  private static String withoutBrackets(String host) {
    return host.length() > 1 && host.startsWith("[") && host.endsWith("]") ? host.substring(1, host.length() - 1) : host;
  }

  private static int effectivePort(String scheme, int port) {
    if (port != -1) {
      return port;
    }
    return "https".equalsIgnoreCase(scheme) ? 443 : 80;
  }

  // Where the credentials of the requests on this thread may go, from creation until closed.
  static final class Exchange implements AutoCloseable {
    private final URI apiRoot;
    // A signed storage URL and when it is known to expire, or null; requests for that URL itself are
    // refused from then on, and requests for any other, after a redirect, once the expiry it states
    // has come (beforeSend). requestTarget is the URL's path and query as a request line holds them.
    private final URI url;
    private final Instant expiresAt;
    private final String requestTarget;
    private final Exchange previous;
    private boolean left;
    // Whether a request head of the exchange may have been written: set before each is handed to a
    // connection, and never cleared, so a refusal after a redirect is not taken as nothing sent.
    private boolean mayHaveSent;

    private Exchange(URI apiRoot, URI url, Instant expiresAt) {
      this.apiRoot = apiRoot;
      this.url = url;
      this.expiresAt = expiresAt;
      // As a request line carries it: in ASCII, its other characters percent-encoded.
      this.requestTarget = url != null ? CredentialGuard.requestTarget(URI.create(url.toASCIIString())) : null;
      this.previous = CURRENT.get();
      CURRENT.set(this);
    }

    @Override
    public void close() {
      if (previous == null) {
        CURRENT.remove();
      } else {
        CURRENT.set(previous);
      }
    }
  }

  // Records that a request head of the current exchange is about to be written.
  private static void writing() {
    Exchange exchange = CURRENT.get();
    if (exchange != null) {
      exchange.mayHaveSent = true;
    }
  }

  // A request to a signed storage URL refused as its head was about to be written, because the URL
  // had expired by then, such as while the request waited for a connection from the pool. It passes
  // through the HTTP client, which gives up the connection, to the transfer that sent the request,
  // which renews the URL once it holds no connection or slot. mayHaveSent tells whether an earlier
  // head of the same exchange, such as one before a redirect, may have been written; otherwise
  // nothing of the request was.
  static final class ExpiredUrl extends RuntimeException {
    private static final long serialVersionUID = 1L;

    final boolean mayHaveSent;

    ExpiredUrl(boolean mayHaveSent) {
      super("The storage URL expired before its request could be sent", null, false, false);
      this.mayHaveSent = mayHaveSent;
    }
  }

  // A connection manager whose connections apply the guard. Connections are unwrapped before they
  // are handed back to the manager, which identifies its own.
  static final class GuardedConnectionManager implements HttpClientConnectionManager {
    private final HttpClientConnectionManager manager;

    GuardedConnectionManager(HttpClientConnectionManager manager) {
      this.manager = manager;
    }

    @Override
    public ConnectionRequest requestConnection(HttpRoute route, Object state) {
      ConnectionRequest request = manager.requestConnection(route, state);
      return new ConnectionRequest() {
        @Override
        public HttpClientConnection get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException {
          return new GuardedConnection(request.get(timeout, unit), route.getTargetHost());
        }

        @Override
        public boolean cancel() {
          return request.cancel();
        }
      };
    }

    @Override
    public void releaseConnection(HttpClientConnection connection, Object newState, long validDuration,
        TimeUnit unit) {
      manager.releaseConnection(unwrap(connection), newState, validDuration, unit);
    }

    @Override
    public void connect(HttpClientConnection connection, HttpRoute route, int connectTimeout,
        HttpContext context) throws IOException {
      manager.connect(unwrap(connection), route, connectTimeout, context);
    }

    @Override
    public void upgrade(HttpClientConnection connection, HttpRoute route, HttpContext context)
        throws IOException {
      manager.upgrade(unwrap(connection), route, context);
    }

    @Override
    public void routeComplete(HttpClientConnection connection, HttpRoute route, HttpContext context)
        throws IOException {
      manager.routeComplete(unwrap(connection), route, context);
    }

    @Override
    public void closeIdleConnections(long idleTime, TimeUnit unit) {
      manager.closeIdleConnections(idleTime, unit);
    }

    @Override
    public void closeExpiredConnections() {
      manager.closeExpiredConnections();
    }

    @Override
    public void shutdown() {
      manager.shutdown();
    }

    private static HttpClientConnection unwrap(HttpClientConnection connection) {
      return connection instanceof GuardedConnection ? ((GuardedConnection) connection).connection : connection;
    }
  }

  // A pooled connection to one route's target, applying the guard to each request head it writes.
  private static final class GuardedConnection implements ManagedHttpClientConnection {
    private final HttpClientConnection connection;
    private final HttpHost target;

    GuardedConnection(HttpClientConnection connection, HttpHost target) {
      this.connection = connection;
      this.target = target;
    }

    @Override
    public void sendRequestHeader(HttpRequest request) throws HttpException, IOException {
      beforeSend(request, target);
      writing();
      connection.sendRequestHeader(request);
    }

    @Override
    public boolean isResponseAvailable(int timeout) throws IOException {
      return connection.isResponseAvailable(timeout);
    }

    @Override
    public void sendRequestEntity(HttpEntityEnclosingRequest request) throws HttpException, IOException {
      connection.sendRequestEntity(request);
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
      return connection.receiveResponseHeader();
    }

    @Override
    public void receiveResponseEntity(HttpResponse response) throws HttpException, IOException {
      connection.receiveResponseEntity(response);
    }

    @Override
    public void flush() throws IOException {
      connection.flush();
    }

    @Override
    public void close() throws IOException {
      connection.close();
    }

    @Override
    public boolean isOpen() {
      return connection.isOpen();
    }

    @Override
    public boolean isStale() {
      return connection.isStale();
    }

    @Override
    public void setSocketTimeout(int timeout) {
      connection.setSocketTimeout(timeout);
    }

    @Override
    public int getSocketTimeout() {
      return connection.getSocketTimeout();
    }

    @Override
    public void shutdown() throws IOException {
      connection.shutdown();
    }

    @Override
    public HttpConnectionMetrics getMetrics() {
      return connection.getMetrics();
    }

    @Override
    public String getId() {
      return connection instanceof ManagedHttpClientConnection ? ((ManagedHttpClientConnection) connection).getId() : null;
    }

    @Override
    public void bind(Socket socket) throws IOException {
      if (!(connection instanceof ManagedHttpClientConnection)) {
        throw new UnsupportedOperationException("The connection cannot be bound to a socket");
      }
      ((ManagedHttpClientConnection) connection).bind(socket);
    }

    @Override
    public Socket getSocket() {
      return connection instanceof ManagedHttpClientConnection ? ((ManagedHttpClientConnection) connection).getSocket() : null;
    }

    @Override
    public SSLSession getSSLSession() {
      return connection instanceof ManagedHttpClientConnection ? ((ManagedHttpClientConnection) connection).getSSLSession() : null;
    }

    @Override
    public InetAddress getLocalAddress() {
      return connection instanceof HttpInetConnection ? ((HttpInetConnection) connection).getLocalAddress() : null;
    }

    @Override
    public int getLocalPort() {
      return connection instanceof HttpInetConnection ? ((HttpInetConnection) connection).getLocalPort() : -1;
    }

    @Override
    public InetAddress getRemoteAddress() {
      return connection instanceof HttpInetConnection ? ((HttpInetConnection) connection).getRemoteAddress() : null;
    }

    @Override
    public int getRemotePort() {
      return connection instanceof HttpInetConnection ? ((HttpInetConnection) connection).getRemotePort() : -1;
    }
  }
}
