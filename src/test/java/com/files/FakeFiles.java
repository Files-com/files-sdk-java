package com.files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * A loopback stand-in for the Files.com API's upload and download endpoints and for the storage
 * URLs it hands out, with the wire formats of the real service: begin_upload answers with an
 * array of parts, each part is a PUT of its bytes to a signed URL, and {@code action=end}
 * completes the upload. Requests are served concurrently and recorded; tests decide what each
 * storage attempt and the final request do.
 */
final class FakeFiles implements AutoCloseable {
  static final String SIGNATURE = "X-Amz-Signature=STORAGE-SECRET";
  private static final ObjectMapper JSON = new ObjectMapper();

  private final HttpServer server;
  private final ExecutorService executor = Executors.newCachedThreadPool(runnable -> {
    Thread thread = new Thread(runnable, "fake-files");
    thread.setDaemon(true);
    return thread;
  });
  private final AtomicInteger refs = new AtomicInteger();
  private final AtomicInteger activeParts = new AtomicInteger();
  private final AtomicInteger maxActiveParts = new AtomicInteger();

  // How begin_upload plans parts: what every part it returns says.
  volatile long partsize = 5;
  volatile Long nextPartsize;
  volatile Boolean parallelParts = true;
  volatile Boolean retryParts = true;
  volatile String sendJson = "{\"partsize\":\"required-header Content-Length\",\"partdata\":\"body\"}";
  volatile String headersJson = "{}";
  volatile String parametersJson = "{}";
  volatile String httpMethod = "PUT";
  volatile String expires = "2099-01-01T00:00:00Z";
  // Changes a part as begin_upload returns it, for one test's needs.
  volatile Function<ObjectNode, ObjectNode> partEditor = part -> part;
  // The storage URL's query after the signature, sent back exactly as written.
  volatile String extraQuery = "";

  // What a storage attempt does, by part number and attempt; null means accept it.
  volatile Storage storage = (part, attempt) -> null;
  // What the final request does; null means complete the upload.
  volatile Finish finish = request -> null;
  // What a begin_upload request does; null means plan its parts.
  volatile Finish beginAnswer = request -> null;
  // What a download's GET does; null means send the contents.
  volatile Download download = attempt -> null;
  volatile byte[] contents = new byte[0];
  volatile Long describedSize;
  // How long the description of a file to download takes.
  volatile long describeDelayMillis;
  // Whether a download's GET with a Range is answered with that range (206), as storage that serves
  // ranges does, and the entity tag of the contents, sent with every download answer, or null for
  // none. A request whose If-Match is not the entity tag is answered 412.
  volatile boolean ranges;
  volatile String etag;
  // Whether If-Match is checked; storage that ignores it answers with whatever version it has.
  volatile boolean ifMatch = true;
  // What a ranged GET does, by its first byte and its attempt for that byte; null means answer it.
  volatile RangeAnswer rangeAnswer = (start, attempt) -> null;
  // When positive, a range's body is sent this many bytes at a time, each sent on its own after a
  // pause, so that the client reads it in short reads, as from a slow link.
  volatile int rangeFragmentBytes;
  final List<Received> rangeRequests = new CopyOnWriteArrayList<>();
  // The requests describing a file to download, each of which gives a download URL: its resource,
  // unless downloadResource replaces it, and its query, the signature then downloadQuery.
  final List<Received> describes = new CopyOnWriteArrayList<>();
  volatile String downloadResource;
  volatile String downloadQuery = "";
  // Changes each download URL as a describe request returns it, for one test's needs.
  volatile java.util.function.UnaryOperator<String> downloadUriEditor = uri -> uri;
  private final AtomicInteger activeRanges = new AtomicInteger();
  private final AtomicInteger maxActiveRanges = new AtomicInteger();
  // The ranges in flight, above, and below, those of them asked for with If-Match: the parts of a
  // download bound to a version, which its concurrency admits, not the first range it asks for,
  // without a condition, to bind one. Each range counts from its arrival until the server begins to
  // answer it, after any hold or delay: a time within the client's own attempt, so that a range the
  // client has finished with is never counted with the next. A test checking how many are in flight
  // at once holds or delays their answers, so that ranges in flight together are counted together.
  private final AtomicInteger activeBoundRanges = new AtomicInteger();
  private final AtomicInteger maxActiveBoundRanges = new AtomicInteger();

  final List<Received> begins = new CopyOnWriteArrayList<>();
  final List<Received> parts = new CopyOnWriteArrayList<>();
  final List<Received> finishes = new CopyOnWriteArrayList<>();
  final List<Received> creates = new CopyOnWriteArrayList<>();
  final List<Received> downloads = new CopyOnWriteArrayList<>();
  private final Map<String, AtomicInteger> attempts = new ConcurrentHashMap<>();

  interface Storage {
    Answer answer(long part, int attempt) throws Exception;
  }

  interface Finish {
    Answer answer(Received request) throws Exception;
  }

  interface Download {
    Answer answer(int attempt) throws Exception;
  }

  interface RangeAnswer {
    Answer answer(long start, int attempt) throws Exception;
  }

  /** What the server does with one request instead of its normal answer. */
  static final class Answer {
    int status = 200;
    long delayMillis;
    boolean drop;
    String body = "";
    CountDownLatch hold;
    final List<String[]> headers = new ArrayList<>();
    Integer truncateTo;
    boolean endsCleanly;
    int partialBytes = -1;
    CountDownLatch bodyHold;

    static Answer status(int status, String body) {
      Answer answer = new Answer();
      answer.status = status;
      answer.body = body;
      return answer;
    }

    /** Answers normally, but closes the connection after the first bytes of the body. */
    static Answer cutShortAfter(int bytes) {
      Answer answer = new Answer();
      answer.truncateTo = bytes;
      return answer;
    }

    /** Answers a range in chunks, without a Content-Length, and ends its body after the first bytes. */
    static Answer endsEarlyAfter(int bytes) {
      Answer answer = cutShortAfter(bytes);
      answer.endsCleanly = true;
      return answer;
    }

    /** Closes the connection without answering. */
    static Answer drop() {
      Answer answer = new Answer();
      answer.drop = true;
      return answer;
    }

    /** Answers with its headers and the first bytes of its body, then waits for the latch to send the rest. */
    static Answer bodyHeldAfter(int status, String body, int bytes, CountDownLatch latch) {
      Answer answer = status(status, body);
      answer.partialBytes = bytes;
      answer.bodyHold = latch;
      return answer;
    }

    /** Waits for millis before answering normally. */
    static Answer delayed(long millis) {
      Answer answer = new Answer();
      answer.status = 0;
      answer.delayMillis = millis;
      return answer;
    }

    /** Waits for the latch, or at most 30 seconds, before answering normally. */
    static Answer holdUntil(CountDownLatch latch) {
      Answer answer = new Answer();
      answer.status = 0;
      answer.hold = latch;
      return answer;
    }

    Answer header(String name, String value) {
      headers.add(new String[]{name, value});
      return this;
    }

    Answer after(long millis) {
      delayMillis = millis;
      return this;
    }
  }

  /** A request as the server received it. */
  static final class Received {
    final String method;
    final String rawPath;
    final String rawQuery;
    final Map<String, List<String>> headers;
    final byte[] body;
    final long part;
    final int attempt;

    Received(HttpExchange exchange, byte[] body, long part, int attempt) {
      this.method = exchange.getRequestMethod();
      this.rawPath = exchange.getRequestURI().getRawPath();
      this.rawQuery = exchange.getRequestURI().getRawQuery();
      TreeMap<String, List<String>> received = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
      received.putAll(exchange.getRequestHeaders());
      this.headers = Collections.unmodifiableMap(received);
      this.body = body;
      this.part = part;
      this.attempt = attempt;
    }

    String header(String name) {
      List<String> values = headers.get(name);
      return values == null || values.isEmpty() ? null : values.get(0);
    }

    JsonNode json() throws IOException {
      return JSON.readTree(body);
    }
  }

  FakeFiles() throws IOException {
    server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 100);
    server.setExecutor(executor);
    server.createContext("/api/rest/v1/file_actions/begin_upload/", this::begin);
    server.createContext("/api/rest/v1/files/", this::files);
    server.createContext("/storage/", this::part);
    server.createContext("/download/", this::downloadContents);
    server.start();
  }

  String root() {
    return "http://127.0.0.1:" + server.getAddress().getPort();
  }

  int port() {
    return server.getAddress().getPort();
  }

  int maxConcurrentParts() {
    return maxActiveParts.get();
  }

  int maxConcurrentRanges() {
    return maxActiveRanges.get();
  }

  int maxConcurrentBoundRanges() {
    return maxActiveBoundRanges.get();
  }

  List<Received> partsNumbered(long number) {
    List<Received> sent = new ArrayList<>();
    for (Received part : parts) {
      if (part.part == number) {
        sent.add(part);
      }
    }
    return sent;
  }

  private void begin(HttpExchange exchange) throws IOException {
    byte[] body = readAll(exchange.getRequestBody());
    Received request = new Received(exchange, body, 0, 0);
    begins.add(request);
    Answer instead;
    try {
      instead = beginAnswer.answer(request);
    } catch (Exception e) {
      throw new IOException(e);
    }
    if (instead != null) {
      answer(exchange, instead);
      return;
    }
    JsonNode parameters = request.json();
    String ref = parameters.hasNonNull("ref") ? parameters.get("ref").asText() : "put-" + refs.incrementAndGet();
    long first = parameters.hasNonNull("part") ? parameters.get("part").asLong() : 1;
    long count = parameters.hasNonNull("parts") ? parameters.get("parts").asLong() : 1;
    ArrayNode answer = JSON.createArrayNode();
    for (long number = first; number < first + count; number++) {
      answer.add(partEditor.apply(part(ref, number)));
    }
    respond(exchange, 200, JSON.writeValueAsBytes(answer));
  }

  ObjectNode part(String ref, long number) throws IOException {
    ObjectNode part = JSON.createObjectNode();
    part.set("send", JSON.readTree(sendJson));
    part.put("action", "put/write");
    part.put("ask_about_overwrites", false);
    part.put("available_parts", 10000);
    part.put("expires", expires);
    part.set("headers", JSON.readTree(headersJson));
    part.put("http_method", httpMethod);
    if (nextPartsize != null) {
      part.put("next_partsize", nextPartsize);
    }
    if (parallelParts != null) {
      part.put("parallel_parts", parallelParts);
    }
    if (retryParts != null) {
      part.put("retry_parts", retryParts);
    }
    part.set("parameters", JSON.readTree(parametersJson));
    part.put("part_number", number);
    part.put("partsize", partsize);
    part.put("ref", ref);
    part.put("upload_uri", root() + "/storage/" + ref + "/" + number + "?" + SIGNATURE + extraQuery);
    return part;
  }

  private void files(HttpExchange exchange) throws IOException {
    byte[] body = readAll(exchange.getRequestBody());
    Received request = new Received(exchange, body, 0, 0);
    String path = java.net.URLDecoder.decode(request.rawPath.substring("/api/rest/v1/files/".length()), "UTF-8");
    if ("GET".equals(request.method)) {
      describes.add(request);
      sleep(describeDelayMillis);
      ObjectNode file = JSON.createObjectNode();
      file.put("path", path);
      file.put("type", "file");
      if (describedSize != null) {
        file.put("size", describedSize);
      }
      String resource = downloadResource != null ? downloadResource
          : root() + "/download/" + request.rawPath.substring("/api/rest/v1/files/".length());
      file.put("download_uri", downloadUriEditor.apply(resource + "?" + SIGNATURE + downloadQuery));
      respond(exchange, 200, JSON.writeValueAsBytes(file));
      return;
    }
    JsonNode parameters = request.json();
    String action = parameters.path("action").asText("");
    if (!"end".equals(action)) {
      // The older way to start an upload: POST /files/{path} with action=put.
      creates.add(request);
      respond(exchange, 200, JSON.writeValueAsBytes(part("put-" + refs.incrementAndGet(), 1)));
      return;
    }
    finishes.add(request);
    Answer answer;
    try {
      answer = finish.answer(request);
    } catch (Exception e) {
      throw new IOException(e);
    }
    if (answer == null) {
      ObjectNode file = JSON.createObjectNode();
      file.put("path", path);
      file.put("type", "file");
      file.put("size", parameters.path("size").asLong());
      respond(exchange, 201, JSON.writeValueAsBytes(file));
    } else {
      answer(exchange, answer);
    }
  }

  private void part(HttpExchange exchange) throws IOException {
    int active = activeParts.incrementAndGet();
    maxActiveParts.accumulateAndGet(active, Math::max);
    try {
      // /storage/<ref>/<part number>, or, like S3's own URLs, /storage/<ref>/<object>?partNumber=<part number>&...
      String[] segments = exchange.getRequestURI().getRawPath().split("/");
      String query = exchange.getRequestURI().getRawQuery();
      java.util.regex.Matcher partNumber = java.util.regex.Pattern.compile("(?:^|&)partNumber=(\\d+)").matcher(query == null ? "" : query);
      long number = Long.parseLong(partNumber.find() ? partNumber.group(1) : segments[segments.length - 1]);
      int attempt = attempts.computeIfAbsent(segments[segments.length - 2] + "/" + number, key -> new AtomicInteger()).incrementAndGet();
      Answer answer;
      try {
        answer = storage.answer(number, attempt);
      } catch (Exception e) {
        throw new IOException(e);
      }
      if (answer != null && answer.hold != null) {
        await(answer.hold);
        answer = null;
      }
      if (answer != null && answer.drop) {
        parts.add(new Received(exchange, readAll(exchange.getRequestBody()), number, attempt));
        exchange.close();
        return;
      }
      byte[] body = readAll(exchange.getRequestBody());
      parts.add(new Received(exchange, body, number, attempt));
      if (answer == null) {
        exchange.getResponseHeaders().add("ETag", "\"etag-" + number + "-" + attempt + "\"");
        respond(exchange, 200, new byte[0]);
      } else {
        answer(exchange, answer);
      }
    } finally {
      activeParts.decrementAndGet();
    }
  }

  private void downloadContents(HttpExchange exchange) throws IOException {
    String range = exchange.getRequestHeaders().getFirst("Range");
    if (ranges && range != null) {
      downloadRange(exchange, range);
      return;
    }
    Received request = new Received(exchange, new byte[0], 0, downloads.size() + 1);
    downloads.add(request);
    if (etag != null) {
      exchange.getResponseHeaders().add("ETag", etag);
    }
    Answer answer;
    try {
      answer = download.answer(request.attempt);
    } catch (Exception e) {
      throw new IOException(e);
    }
    if (answer != null && answer.hold != null && answer.status == 0) {
      await(answer.hold);
      answer = null;
    }
    if (answer == null) {
      respond(exchange, 200, contents);
      return;
    }
    if (answer.truncateTo != null) {
      for (String[] header : answer.headers) {
        exchange.getResponseHeaders().add(header[0], header[1]);
      }
      exchange.sendResponseHeaders(200, contents.length);
      OutputStream out = exchange.getResponseBody();
      out.write(contents, 0, answer.truncateTo);
      out.flush();
      exchange.close();
      return;
    }
    answer(exchange, answer);
  }

  // bytes=a-b of the contents as they are now: 206 with them, their Content-Range and the entity tag;
  // 412 when If-Match is not the entity tag; 416 past the end.
  private void downloadRange(HttpExchange exchange, String range) throws IOException {
    java.util.regex.Matcher bytes = java.util.regex.Pattern.compile("bytes=(\\d+)-(\\d+)").matcher(range);
    if (!bytes.matches()) {
      throw new IOException("Unexpected Range " + range);
    }
    long start = Long.parseLong(bytes.group(1));
    int attempt = attempts.computeIfAbsent("range/" + start, key -> new AtomicInteger()).incrementAndGet();
    Received request = new Received(exchange, new byte[0], start, attempt);
    maxActiveRanges.accumulateAndGet(activeRanges.incrementAndGet(), Math::max);
    boolean bound = request.header("If-Match") != null;
    if (bound) {
      maxActiveBoundRanges.accumulateAndGet(activeBoundRanges.incrementAndGet(), Math::max);
    }
    rangeRequests.add(request);
    Answer answer;
    try {
      try {
        answer = rangeAnswer.answer(start, attempt);
      } catch (Exception e) {
        throw new IOException(e);
      }
      if (answer != null && answer.status == 0) {
        // Held or delayed, then answered normally.
        if (answer.hold != null) {
          await(answer.hold);
        }
        sleep(answer.delayMillis);
        answer = null;
      }
    } finally {
      activeRanges.decrementAndGet();
      if (bound) {
        activeBoundRanges.decrementAndGet();
      }
    }
    byte[] current = contents;
    String ifMatch = request.header("If-Match");
    if (answer == null && this.ifMatch && ifMatch != null && !ifMatch.equals(etag)) {
      answer = Answer.status(412, "");
    }
    if (answer == null && start >= current.length) {
      answer = Answer.status(416, "");
    }
    if (answer != null && answer.truncateTo == null) {
      answer(exchange, answer);
      return;
    }
    int from = (int) start;
    int to = (int) Math.min(Long.parseLong(bytes.group(2)), current.length - 1L);
    if (etag != null) {
      exchange.getResponseHeaders().add("ETag", etag);
    }
    exchange.getResponseHeaders().add("Content-Range", "bytes " + from + "-" + to + "/" + current.length);
    exchange.sendResponseHeaders(206, answer != null && answer.endsCleanly ? 0 : to - from + 1);
    OutputStream out = exchange.getResponseBody();
    if (answer != null) {
      // Cut short after truncateTo bytes: the connection closes, or the chunked body ends.
      writeRangeBody(out, current, from, Math.min(answer.truncateTo, to - from + 1));
      if (answer.endsCleanly) {
        out.close();
        return;
      }
      out.flush();
      exchange.close();
      return;
    }
    writeRangeBody(out, current, from, to - from + 1);
    out.close();
  }

  private void writeRangeBody(OutputStream out, byte[] current, int from, int length) throws IOException {
    int fragment = rangeFragmentBytes > 0 ? rangeFragmentBytes : length;
    for (int sent = 0; sent < length; sent += fragment) {
      if (sent > 0) {
        out.flush();
        sleep(5);
      }
      out.write(current, from + sent, Math.min(fragment, length - sent));
    }
  }

  private void answer(HttpExchange exchange, Answer answer) throws IOException {
    if (answer.delayMillis > 0) {
      sleep(answer.delayMillis);
    }
    if (answer.hold != null) {
      await(answer.hold);
    }
    if (answer.drop) {
      exchange.close();
      return;
    }
    for (String[] header : answer.headers) {
      exchange.getResponseHeaders().add(header[0], header[1]);
    }
    byte[] body = answer.body.getBytes(StandardCharsets.UTF_8);
    if (answer.partialBytes >= 0) {
      exchange.sendResponseHeaders(answer.status, body.length);
      OutputStream out = exchange.getResponseBody();
      out.write(body, 0, answer.partialBytes);
      out.flush();
      await(answer.bodyHold);
      out.write(body, answer.partialBytes, body.length - answer.partialBytes);
      out.close();
      return;
    }
    respond(exchange, answer.status, body);
  }

  private static void respond(HttpExchange exchange, int status, byte[] body) throws IOException {
    exchange.getResponseHeaders().add("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, body.length == 0 ? -1 : body.length);
    try (OutputStream out = exchange.getResponseBody()) {
      // A response sent without a body (-1) takes no write: Java 8 refuses even an empty one.
      if (body.length > 0) {
        out.write(body);
      }
    }
  }

  static byte[] readAll(InputStream in) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[64 * 1024];
    int read;
    while ((read = in.read(buffer)) >= 0) {
      out.write(buffer, 0, read);
    }
    return out.toByteArray();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static void await(CountDownLatch latch) {
    try {
      latch.await(30, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void close() {
    server.stop(0);
    executor.shutdownNow();
  }
}
