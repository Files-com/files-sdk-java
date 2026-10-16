package com.files.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
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
    return new Exchange(apiRoot);
  }

  // Requests sent on this thread until the result is closed carry no credentials.
  static Exchange forStorage() {
    return new Exchange(null);
  }

  // Removes the credentials from a request head about to be written to a connection to target,
  // unless the current exchange allows them there.
  static void beforeSend(HttpRequest request, HttpHost target) {
    Exchange exchange = CURRENT.get();
    if (exchange != null && exchange.apiRoot != null && !sameOrigin(exchange.apiRoot, target)) {
      exchange.left = true;
    }
    if (exchange == null || exchange.apiRoot == null || exchange.left) {
      for (String header : FILES_AUTH_HEADERS) {
        request.removeHeaders(header);
      }
    }
  }

  static boolean sameOrigin(URI apiRoot, HttpHost target) {
    return target != null
        && apiRoot.getScheme() != null
        && apiRoot.getHost() != null
        && apiRoot.getScheme().equalsIgnoreCase(target.getSchemeName())
        && apiRoot.getHost().equalsIgnoreCase(target.getHostName())
        && effectivePort(apiRoot.getScheme(), apiRoot.getPort()) == effectivePort(target.getSchemeName(), target.getPort());
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
    private final Exchange previous;
    private boolean left;

    private Exchange(URI apiRoot) {
      this.apiRoot = apiRoot;
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
