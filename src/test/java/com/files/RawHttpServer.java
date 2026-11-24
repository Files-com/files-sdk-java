package com.files;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A loopback HTTP/1.1 server for what WireMock cannot do: cut a connection off while the client is
 * still sending its body, or act between receiving a body and answering it. Each connection
 * carries one request and takes the next {@link Reply} in order; every request is recorded as
 * received, including one the client abandoned partway.
 */
final class RawHttpServer implements AutoCloseable {
  /** What the server does with one request. */
  interface Reply {
    void to(Received request, Socket connection) throws IOException;
  }

  /** A request as the server read it. The body is shorter than its Content-Length if the client stopped sending it. */
  static final class Received {
    final String method;
    final String path;
    final List<String> headers;
    final long contentLength;
    final ByteArrayOutputStream body = new ByteArrayOutputStream();

    Received(String method, String path, List<String> headers, long contentLength) {
      this.method = method;
      this.path = path;
      this.headers = headers;
      this.contentLength = contentLength;
    }

    String header(String name) {
      for (String header : headers) {
        int colon = header.indexOf(':');
        if (header.substring(0, colon).equalsIgnoreCase(name)) {
          return header.substring(colon + 1).trim();
        }
      }
      return null;
    }

    byte[] body() {
      return body.toByteArray();
    }

    boolean complete() {
      return body.size() == contentLength;
    }
  }

  private final ServerSocket server;
  private final List<Reply> replies;
  private final ConcurrentLinkedQueue<Received> received = new ConcurrentLinkedQueue<>();
  private final Thread acceptor;

  RawHttpServer(Reply... replies) throws IOException {
    this.server = new ServerSocket(0, 50, InetAddress.getLoopbackAddress());
    this.replies = new ArrayList<>(List.of(replies));
    this.acceptor = new Thread(this::serve, "raw-http-server");
    acceptor.setDaemon(true);
    acceptor.start();
  }

  String root() {
    return "http://127.0.0.1:" + server.getLocalPort();
  }

  List<Received> received() {
    return Collections.unmodifiableList(new ArrayList<>(received));
  }

  /** Reads the whole body, runs {@code between}, then answers with a JSON body and closes the connection. */
  static Reply respond(int status, String json, Runnable between) {
    return (request, connection) -> {
      readBody(request, connection.getInputStream(), request.contentLength);
      between.run();
      byte[] body = json.getBytes(StandardCharsets.UTF_8);
      OutputStream out = connection.getOutputStream();
      out.write(("HTTP/1.1 " + status + " Status\r\nContent-Type: application/json\r\nContent-Length: " + body.length
          + "\r\nConnection: close\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
      out.write(body);
      out.flush();
    };
  }

  static Reply respond(int status, String json) {
    return respond(status, json, () -> { });
  }

  /** Reads this many body bytes, then resets the connection while the client is still sending. */
  static Reply resetAfter(long bodyBytes) {
    return (request, connection) -> {
      readBody(request, connection.getInputStream(), bodyBytes);
      connection.setSoLinger(true, 0);
    };
  }

  private void serve() {
    while (!server.isClosed()) {
      try (Socket connection = server.accept()) {
        Received request = readHead(connection.getInputStream());
        received.add(request);
        Reply reply;
        synchronized (replies) {
          reply = replies.isEmpty() ? respond(500, "{}") : replies.remove(0);
        }
        reply.to(request, connection);
      } catch (IOException e) {
        // The client gave up on this connection, or the server closed; the request stays recorded.
      }
    }
  }

  private static Received readHead(InputStream in) throws IOException {
    ByteArrayOutputStream head = new ByteArrayOutputStream();
    int matched = 0;
    while (matched < 4) {
      int b = in.read();
      if (b == -1) {
        throw new IOException("connection closed in the request head");
      }
      head.write(b);
      matched = (b == "\r\n\r\n".charAt(matched)) ? matched + 1 : (b == '\r' ? 1 : 0);
    }
    String[] lines = head.toString(StandardCharsets.ISO_8859_1).split("\r\n");
    String[] requestLine = lines[0].split(" ");
    List<String> headers = new ArrayList<>(List.of(lines).subList(1, lines.length));
    long contentLength = 0;
    for (String header : headers) {
      if (header.toLowerCase(Locale.ROOT).startsWith("content-length:")) {
        contentLength = Long.parseLong(header.substring("content-length:".length()).trim());
      }
    }
    return new Received(requestLine[0], requestLine[1], headers, contentLength);
  }

  private static void readBody(Received request, InputStream in, long limit) throws IOException {
    byte[] buffer = new byte[64 * 1024];
    long remaining = Math.min(limit, request.contentLength) - request.body.size();
    while (remaining > 0) {
      int read = in.read(buffer, 0, (int) Math.min(buffer.length, remaining));
      if (read == -1) {
        return;
      }
      request.body.write(buffer, 0, read);
      remaining -= read;
    }
  }

  /** Stops accepting connections and waits for the current one to finish. */
  @Override
  public void close() throws IOException {
    server.close();
    try {
      acceptor.join(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
