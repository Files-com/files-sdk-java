package com.files.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.CancellationToken;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.RegionRelocationPolicy;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.OperationCancelledException.Reason;
import com.files.exceptions.SdkException;
import com.files.models.FileUploadPart;
import com.files.models.ModelInterface;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.TruncatedChunkException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The requests of one {@link FilesClient}: its settings, its transport and the API root its
 * requests go to. Used by the SDK's generated operations, listings and transfers; applications use
 * {@link FilesClient} instead.
 */
public final class ClientRuntime implements Closeable {
  private static final Logger log = LoggerFactory.getLogger(ClientRuntime.class);

  /** The request option holding a {@link CancellationToken}. */
  public static final String CANCELLATION_OPTION = "cancellation";
  /** The request option holding a {@link Duration}: the time limit of the call. */
  public static final String TIMEOUT_OPTION = "timeout";
  /** The request option holding the most retries of the call, an Integer from 0. */
  public static final String MAX_RETRIES_OPTION = "max_retries";
  /**
   * The request option holding the API root of the call, a String, instead of the client's. Objects
   * and listings the call returns keep it.
   */
  public static final String API_ROOT_OPTION = "api_root";

  // Request options that control one call rather than describing who makes it; objects that a
  // call returns do not keep them.
  private static final List<String> CALL_OPTIONS = Arrays.asList(CANCELLATION_OPTION, TIMEOUT_OPTION, MAX_RETRIES_OPTION);

  static final ObjectMapper MAPPER = JsonMapper.builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();

  private final FilesClient client;
  private final Settings settings;
  private final HttpTransport transport;
  private final boolean ownsTransport;
  // Whether this is FilesClient.defaultClient(), which follows FilesConfig: its API requests, and
  // its storage requests other than transfers, read their retry limit and wait again before each
  // attempt, as the static settings did in SDK 1.x. Every other client, and every transfer, keeps
  // the settings it began with.
  private final boolean followsConfiguration;
  private final AtomicReference<URI> apiRoot;
  private final Set<OperationScope> active = Collections.newSetFromMap(new ConcurrentHashMap<OperationScope, Boolean>());
  private final AtomicBoolean closed = new AtomicBoolean();
  // Taken to admit an operation, to end one and to close, so that no operation starts unseen by
  // close and the end of the last one after close is seen once.
  private final Object admission = new Object();
  // Counted down once the client is closed, its last operation has ended and its transport is
  // closed.
  private final CountDownLatch settled = new CountDownLatch(1);
  private final AtomicBoolean settling = new AtomicBoolean();
  // For each upload part this client's requests returned, where the request that returned it was
  // answered and the request options it was sent with; an upload continued from the part with the
  // same options starts there. Kept by the SDK, never serialized, and dropped with the part.
  private final Map<FileUploadPart, UploadOrigin> uploadOrigins =
      Collections.synchronizedMap(new WeakHashMap<FileUploadPart, UploadOrigin>());

  public ClientRuntime(FilesClient client, Settings settings, HttpTransport transport, boolean ownsTransport,
      boolean followsConfiguration) {
    this.client = client;
    this.settings = settings;
    this.transport = transport;
    this.ownsTransport = ownsTransport;
    this.followsConfiguration = followsConfiguration;
    this.apiRoot = new AtomicReference<URI>(settings.apiRoot);
  }

  public Settings settings() {
    return settings;
  }

  /**
   * The API root each operation's requests start at: the configured one, or where Files.com
   * relocated the site for a request with the client's own credentials.
   */
  public URI apiRoot() {
    return apiRoot.get();
  }

  public boolean isClosed() {
    return closed.get();
  }

  FilesClient client() {
    return client;
  }

  HttpTransport transport() {
    return transport;
  }

  /**
   * Sends an API request and returns its successful response. target is a path under the API
   * root, such as {@code /users}, or an absolute URL.
   */
  public FilesResponse request(RequestMethods method, String target, HashMap<String, Object> parameters,
      HashMap<String, Object> options) {
    Map<String, Object> callOptions = options != null ? options : Collections.<String, Object>emptyMap();
    try (OperationScope scope = open("The request", callOptions)) {
      return send(method, target, parameters, callOptions, scope, requestRetries(callOptions));
    }
  }

  /**
   * Sends an API request whose response is one object, and returns that object. A model keeps
   * this client and the request options, without the options that controlled only this call. For
   * an upload part, the client also remembers where the request was answered, for an upload
   * continued from the part.
   */
  public <T> T requestItem(RequestMethods method, String target, TypeReference<T> type, HashMap<String, Object> parameters,
      HashMap<String, Object> options) {
    Map<String, Object> callOptions = options != null ? options : Collections.<String, Object>emptyMap();
    FilesResponse response;
    URI answeredAt;
    try (OperationScope scope = open("The request", callOptions)) {
      response = send(method, target, parameters, callOptions, scope, requestRetries(callOptions));
      answeredAt = scope.apiRoot();
    }
    T item = readItem(response.getBody(), type);
    // An upload started with action=put keeps the path as the caller gave it, which the response
    // may spell differently.
    if (item instanceof FileUploadPart && parameters != null && "put".equals(parameters.get("action"))
        && parameters.get("path") instanceof String) {
      ((FileUploadPart) item).path = (String) parameters.get("path");
    }
    if (item instanceof FileUploadPart && !isAbsolute(target)) {
      uploadOrigins.put((FileUploadPart) item, new UploadOrigin(answeredAt, retainedOptions(options)));
    }
    bind(item, options);
    return item;
  }

  <T> T readItem(String body, TypeReference<T> type) {
    T item;
    try {
      item = MAPPER.readValue(body, type);
    } catch (JsonProcessingException e) {
      throw undecodableResponse();
    }
    if (item == null) {
      throw undecodableResponse();
    }
    return item;
  }

  static ApiErrorException.ApiConnectionException undecodableResponse() {
    return new ApiErrorException.ApiConnectionException("The Files.com API response body is not the expected JSON object");
  }

  /**
   * Makes a model returned by one of this client's requests keep using this client, with a copy
   * of the request options.
   */
  public void bind(Object item, HashMap<String, Object> options) {
    if (item instanceof ModelInterface) {
      ((ModelInterface) item).setClient(client);
      ((ModelInterface) item).setOptions(retainedOptions(options));
    }
  }

  /**
   * The options an object returned by a call keeps: a copy, without those that controlled only
   * that call, such as its cancellation token and time limit.
   */
  public static HashMap<String, Object> retainedOptions(Map<String, Object> options) {
    HashMap<String, Object> retained = options == null ? new HashMap<String, Object>() : new HashMap<String, Object>(options);
    retained.keySet().removeAll(CALL_OPTIONS);
    return retained;
  }

  // Opens the scope of one call, whose requests start at its api_root option or, without one, at
  // the client's API root.
  OperationScope open(String subject, Map<String, Object> options) {
    return open(subject, options, callRoot(options));
  }

  // Opens the scope of an upload. One that continues from a part this client returned, with the
  // request options that part was requested with, starts where the request that returned it was
  // answered: perhaps a server Files.com named for the site, which the upload's final request,
  // never relocated itself, must reach too. Any other upload starts where any other call would.
  OperationScope openUpload(String subject, Map<String, Object> options, FileUploadPart firstPart) {
    UploadOrigin origin = firstPart != null ? uploadOrigins.get(firstPart) : null;
    boolean sameOptions = origin != null && origin.options.equals(retainedOptions(options));
    return open(subject, options, sameOptions ? origin.apiRoot : callRoot(options));
  }

  // The API root a call's requests are set to start at, before Files.com relocates any: its
  // api_root option or the client's configured root. A checkpoint is bound to it (UploadCheckpoint).
  URI configuredRoot(Map<String, Object> options) {
    return options != null && options.get(API_ROOT_OPTION) != null ? callRoot(options) : settings.apiRoot;
  }

  // The workspace a call's requests are made in: its workspace_id option or the client's; "" for none.
  String workspace(Map<String, Object> options) {
    Object workspaceId = options.containsKey("workspace_id") ? options.get("workspace_id") : settings.workspaceId;
    return workspaceId == null ? "" : workspaceId.toString().trim();
  }

  // The API root a call's requests start at: its api_root option, which moves neither the client
  // nor its other calls, or else the client's. Credentials go only to the root the call starts at.
  private URI callRoot(Map<String, Object> options) {
    Object root = options == null ? null : options.get(API_ROOT_OPTION);
    if (root == null) {
      return apiRoot.get();
    }
    if (!(root instanceof String)) {
      throw new ApiErrorException.InvalidParameterException("Bad option: api_root must be of type String");
    }
    try {
      return Settings.parseApiRoot((String) root);
    } catch (IllegalArgumentException e) {
      throw new ApiErrorException.InvalidParameterException("Bad option: api_root must be an http or https URL");
    }
  }

  // Opens the scope of one call: its cancellation token, its time limit or the client's, the
  // calling thread, and the API root where its requests start. A closed client refuses the call
  // before anything is set up; the scope is registered with the client under the same lock close
  // takes, so close either sees it or refuses it. Once the client is closed, the end of its last
  // call settles it.
  private OperationScope open(String subject, Map<String, Object> options, URI start) {
    ensureOpen();
    Object token = options.get(CANCELLATION_OPTION);
    if (token != null && !(token instanceof CancellationToken)) {
      throw new ApiErrorException.InvalidParameterException("Bad option: cancellation must be of type CancellationToken");
    }
    Object timeout = options.containsKey(TIMEOUT_OPTION) ? options.get(TIMEOUT_OPTION) : settings.callTimeout;
    if (timeout != null && (!(timeout instanceof Duration) || ((Duration) timeout).isNegative() || ((Duration) timeout).isZero())) {
      throw new ApiErrorException.InvalidParameterException("Bad option: timeout must be a positive java.time.Duration");
    }
    OperationScope scope = OperationScope.open(subject, (CancellationToken) token, (Duration) timeout, start);
    synchronized (admission) {
      if (!closed.get()) {
        active.add(scope);
        scope.whenClosed(() -> ended(scope));
        return scope;
      }
    }
    scope.close();
    throw closedError();
  }

  private void ended(OperationScope scope) {
    boolean last;
    synchronized (admission) {
      active.remove(scope);
      last = closed.get() && active.isEmpty();
    }
    if (last) {
      settle();
    }
  }

  // Closes the transport the client owns, once, and lets every close waiting for it return.
  private void settle() {
    if (!settling.compareAndSet(false, true)) {
      return;
    }
    try {
      if (ownsTransport) {
        transport.close();
      }
    } catch (IOException e) {
      log.debug("Could not close the client's HTTP transport", e);
    } finally {
      settled.countDown();
    }
  }

  // The retry limit of a request that follows FilesConfig: the limit and the wait are read from it
  // before each attempt rather than when the request begins.
  private static final int FOLLOW_CONFIGURATION = -1;

  // The retry limit of an API request: the default client follows FilesConfig, unless the call
  // gives its own max_retries.
  private int requestRetries(Map<String, Object> options) {
    return followsConfiguration && options.get(MAX_RETRIES_OPTION) == null ? FOLLOW_CONFIGURATION : maxRetries(options);
  }

  // The retry limit of a storage request that can be sent again.
  private int storageRetries() {
    return followsConfiguration ? FOLLOW_CONFIGURATION : settings.maxRetries;
  }

  // The retry limit for the next decision of a request that began with maxRetries.
  private static int retryLimit(int maxRetries) {
    return maxRetries == FOLLOW_CONFIGURATION ? FilesConfig.getInstance().getMaxNetworkRetries() : maxRetries;
  }

  int maxRetries(Map<String, Object> options) {
    Object value = options.get(MAX_RETRIES_OPTION);
    if (value == null) {
      return settings.maxRetries;
    }
    if (!(value instanceof Integer) || (Integer) value < 0) {
      throw new ApiErrorException.InvalidParameterException("Bad option: max_retries must be an Integer of at least 0");
    }
    return (Integer) value;
  }

  /**
   * Throws {@link IllegalStateException} if the client has been closed.
   */
  public void ensureOpen() {
    if (closed.get()) {
      throw closedError();
    }
  }

  private static IllegalStateException closedError() {
    return new IllegalStateException("This FilesClient has been closed");
  }

  // Sends the request, retrying it within one budget of maxRetries: a retry after a failure that
  // may be temporary, and a relocation after Files.com named another server for the site, each
  // use one. The body is prepared once and resent unchanged. A relocation moves the rest of the
  // operation; it moves the client's later operations too only when the request carried the
  // client's own credentials, since a call's own credentials, or none, may be for another site.
  FilesResponse send(RequestMethods method, String target, HashMap<String, Object> parameters, Map<String, Object> options,
      OperationScope scope, int maxRetries) {
    return send(method, target, parameters, options, scope, maxRetries, false, new AtomicBoolean());
  }

  // As above. With onlyUnsent, a request that may have reached the server is never sent again: only
  // an attempt shown to have failed before any of it was sent, such as one that could not connect,
  // is retried. reached is set once an attempt may have reached the server.
  FilesResponse send(RequestMethods method, String target, HashMap<String, Object> parameters, Map<String, Object> options,
      OperationScope scope, int maxRetries, boolean onlyUnsent, AtomicBoolean reached) {
    if (log.isDebugEnabled()) {
      log.debug(String.format("Sending a %s request to %s with parameters: %s and options %s", method, target,
          parameters, options));
    }
    boolean relocatable = !isAbsolute(target);
    AttachmentBody attachments = null;
    try {
      HttpEntity body = null;
      if (hasBody(method) && parameters != null) {
        // Attachments are read, or copied, before anything is sent; the copies are deleted when the
        // request ends.
        attachments = AttachmentBody.prepare(parameters, MAPPER, scope::check);
        body = attachments != null ? attachments.entity() : new StringEntity(serialize(parameters), ContentType.APPLICATION_JSON);
      }
      for (int retry = 0; ; retry++) {
        scope.check();
        URI root = scope.apiRoot();
        String url = relocatable ? root.toString().replaceAll("/+$", "") + settings.apiBase + target : target;
        HttpRequestBase request = newRequest(method, hasBody(method) ? url : withQuery(url, parameters));
        boolean clientCredentials = addHeaders(request, options);
        WrittenEntity written = null;
        if (body != null) {
          written = new WrittenEntity(body);
          ((HttpEntityEnclosingRequestBase) request).setEntity(written);
        }

        Attempt attempt = attempt(request, root, scope, reached, written);
        if (attempt.response != null) {
          return attempt.response;
        }
        int limit = retryLimit(maxRetries);
        boolean canRetry = retry < limit && !(onlyUnsent && attempt.sent);
        if (attempt.error instanceof ApiErrorException.LockoutRegionMismatchException) {
          URI relocated = relocatable ? relocation(root, (ApiErrorException.LockoutRegionMismatchException) attempt.error) : null;
          if (relocated == null || !canRetry) {
            throw attempt.error;
          }
          scope.relocate(root, relocated);
          if (clientCredentials) {
            apiRoot.compareAndSet(root, relocated);
          }
          log.warn("Files.com directed this site's requests from {} to {}; sending them there (retry {} of {})",
              root, relocated, retry + 1, limit);
          continue;
        }
        if (!attempt.retryable || !canRetry) {
          throw attempt.error;
        }
        long wait = retryDelay(retry + 1, attempt.retryAfterMillis, maxRetries);
        log.warn("Retrying the {} request (retry {} of {}) in {} ms after {}", method, retry + 1, limit, wait, attempt.reason);
        scope.sleep(wait);
      }
    } catch (AttachmentSourceException e) {
      throw e.unchecked();
    } finally {
      if (attachments != null) {
        attachments.close();
      }
    }
  }

  // One attempt: its successful response, or its failure and whether it may be retried. reached is
  // set when the request may have reached the server: a response came back, or the failure came
  // after sending began. Only a failure shown to precede sending leaves it unset: HttpClient had not
  // started to write the request (it marks HTTP_REQ_SENT, false, as it begins) and no byte of the
  // body was asked for. HTTP_REQ_SENT stays false until the whole request has been flushed, so a
  // false value does not show that nothing was sent.
  private Attempt attempt(HttpRequestBase request, URI root, OperationScope scope, AtomicBoolean reached, WrittenEntity body) {
    CloseableHttpResponse response;
    CancellationToken.Registration abort = scope.onStop(request::abort);
    try {
      HttpClientContext context = HttpClientContext.create();
      CredentialGuard.Exchange guard = CredentialGuard.forApi(root);
      try {
        request.setConfig(requestConfig(false));
        response = transport.execute(request, context);
      } catch (AttachmentSourceException e) {
        // An attachment's own file or stream failed; sending the request again cannot help.
        if (scope.isStopped()) {
          throw scope.stopped(null);
        }
        throw e.unchecked();
      } catch (IOException e) {
        boolean mayHaveReached = context.getAttribute(HttpCoreContext.HTTP_REQ_SENT) != null || (body != null && body.written);
        if (mayHaveReached) {
          reached.set(true);
        }
        if (scope.isStopped()) {
          throw scope.stopped(null);
        }
        log.debug("The {} request failed", request.getMethod(), e);
        return Attempt.failed(connectionError(e, request), isRetryable(e), describe(e), 0, mayHaveReached);
      } finally {
        guard.close();
      }
      reached.set(true);
      try {
        int status = response.getStatusLine().getStatusCode();
        if (status < 400) {
          HttpEntity entity = response.getEntity();
          String responseBody;
          try {
            responseBody = entity != null ? EntityUtils.toString(entity, StandardCharsets.UTF_8) : "";
          } catch (IOException e) {
            if (scope.isStopped()) {
              throw scope.stopped(null);
            }
            throw unreadableResponse(e, request);
          }
          return Attempt.succeeded(new FilesResponse(status, Arrays.asList(response.getAllHeaders()), responseBody));
        }
        SdkException error = ApiErrors.forResponse(response, status, operation(request));
        scope.check();
        long retryAfter = status == 429 || status == 503 ? retryAfterMillis(response.getFirstHeader("Retry-After")) : 0;
        return Attempt.failed(error, isRetryableStatus(request, status), "HTTP status " + status, retryAfter, true);
      } finally {
        closeQuietly(response);
      }
    } finally {
      abort.close();
    }
  }

  // Where to send the site's requests after a region mismatch, or null to fail with it.
  private URI relocation(URI root, ApiErrorException.LockoutRegionMismatchException error) {
    Map<String, Object> data = error.getData();
    Object host = data == null ? null : data.get("host");
    URI relocated = settings.regionRelocation.relocate(root, host instanceof String ? (String) host : null);
    if (relocated == null
        || relocated.getHost() == null
        || relocated.getRawUserInfo() != null
        || relocated.getRawQuery() != null
        || relocated.getRawFragment() != null
        || !(relocated.getRawPath() == null || relocated.getRawPath().isEmpty() || "/".equals(relocated.getRawPath()))
        || !("https".equalsIgnoreCase(relocated.getScheme()) || "http".equalsIgnoreCase(relocated.getScheme()))
        || ("https".equalsIgnoreCase(root.getScheme()) && !"https".equalsIgnoreCase(relocated.getScheme()))) {
      return null;
    }
    URI normalized = URI.create(relocated.getScheme().toLowerCase(Locale.ROOT) + "://" + relocated.getRawAuthority());
    return normalized.equals(root) ? null : normalized;
  }

  // The wait before a retry: the retry delay, doubling with each retry up to the maximum, or the
  // server's Retry-After within that maximum when it is longer.
  long retryDelay(int retryNumber, long retryAfterMillis, int maxRetries) {
    if (maxRetries == FOLLOW_CONFIGURATION) {
      FilesConfig config = FilesConfig.getInstance();
      return retryDelay(retryNumber, retryAfterMillis, config.getInitialRetryDelayMillis(),
          TimeUnit.SECONDS.toMillis(config.getMaximumRetrySeconds()));
    }
    return retryDelay(retryNumber, retryAfterMillis, settings.retryDelay.toMillis(), settings.maxRetryDelay.toMillis());
  }

  static long retryDelay(int retryNumber, long retryAfterMillis, long initial, long max) {
    long delay = retryNumber > 32 || initial > (max >> Math.min(31, retryNumber - 1)) ? max : initial << (retryNumber - 1);
    return Math.max(Math.min(delay, max), Math.min(retryAfterMillis, max));
  }

  interface Execution {
    CloseableHttpResponse execute(HttpRequestBase request) throws IOException;
  }

  // Sends a request that the caller prepared, as it is, retrying like an API request; used by the
  // SDK's older executor API. A body that cannot be sent twice is sent once. Like the default client,
  // it reads its retry limit and wait from FilesConfig before each attempt.
  static CloseableHttpResponse executePrepared(HttpRequestBase request, Execution execution) throws IOException {
    boolean replayable = !(request instanceof HttpEntityEnclosingRequestBase)
        || ((HttpEntityEnclosingRequestBase) request).getEntity() == null
        || ((HttpEntityEnclosingRequestBase) request).getEntity().isRepeatable();
    for (int retry = 0; ; retry++) {
      SdkException error;
      boolean retryable;
      String reason;
      long retryAfter = 0;
      try {
        CloseableHttpResponse response = execution.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status < 400) {
          return response;
        }
        try {
          error = ApiErrors.forResponse(response, status, operation(request));
        } finally {
          closeQuietly(response);
        }
        retryable = isRetryableStatus(request, status);
        reason = "HTTP status " + status;
        if (status == 429 || status == 503) {
          retryAfter = retryAfterMillis(response.getFirstHeader("Retry-After"));
        }
      } catch (IOException e) {
        log.debug("The {} request failed", request.getMethod(), e);
        error = connectionError(e, request);
        retryable = isRetryable(e);
        reason = describe(e);
      }
      FilesConfig config = FilesConfig.getInstance();
      int retries = replayable ? config.getMaxNetworkRetries() : 0;
      if (!retryable || retry >= retries) {
        throw error;
      }
      long wait = retryDelay(retry + 1, retryAfter, config.getInitialRetryDelayMillis(),
          TimeUnit.SECONDS.toMillis(config.getMaximumRetrySeconds()));
      log.warn("Retrying the {} request (retry {} of {}) in {} ms after {}", request.getMethod(), retry + 1, retries, wait, reason);
      try {
        Thread.sleep(wait);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new com.files.exceptions.OperationCancelledException(Reason.INTERRUPTED, "The request was interrupted.");
      }
      request.reset();
    }
  }

  static long retryAfterMillis(Header header) {
    return retryAfterMillis(header, TimeUnit.DAYS.toSeconds(1));
  }

  // The wait a Retry-After header asks for, in milliseconds, with at most maxSeconds of delay seconds;
  // 0 for none or one that cannot be read.
  static long retryAfterMillis(Header header, long maxSeconds) {
    if (header == null || header.getValue() == null) {
      return 0;
    }
    String value = header.getValue().trim();
    try {
      long seconds = Long.parseLong(value);
      return seconds <= 0 ? 0 : TimeUnit.SECONDS.toMillis(Math.min(seconds, maxSeconds));
    } catch (NumberFormatException e) {
      try {
        long millis = Duration.between(Instant.now(), ZonedDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME)).toMillis();
        return Math.max(0, millis);
      } catch (DateTimeParseException | ArithmeticException e2) {
        return 0;
      }
    }
  }

  // The client's time limits for one request. Storage URLs are sent exactly as given.
  RequestConfig requestConfig(boolean storage) {
    return transport.requestConfig()
        .setConnectTimeout(millis(settings.connectTimeout))
        .setSocketTimeout(millis(settings.readTimeout))
        .setConnectionRequestTimeout(millis(settings.connectionAcquisitionTimeout))
        .setNormalizeUri(!storage)
        .build();
  }

  private static int millis(Duration duration) {
    return (int) Math.min(Integer.MAX_VALUE, duration.toMillis());
  }

  // Failures that sending the request again cannot fix are not retried: a URL the client cannot
  // send, because it has no host or a scheme the client cannot connect with, even when the client
  // finds that only while planning the route; a server certificate or host name that does not
  // verify; redirects that loop or go past the client's limit; the request aborted; or an
  // attachment's own file or stream failing. Any other failure, including other TLS and protocol
  // failures, may be temporary, and is retried.
  static boolean isRetryable(IOException e) {
    return !(e instanceof AttachmentSourceException
        || e instanceof RequestAbortedException
        || e instanceof UnsupportedSchemeException
        || isUnsupportedSchemeInRoute(e)
        || isMissingHost(e)
        || isCertificateVerificationFailure(e)
        || isRedirectFailure(e));
  }

  // Whether the client found, while planning the route, that it does not support the URL's scheme:
  // Apache's route planner turns the UnsupportedSchemeException into an HttpException with its
  // message, which the client wraps in a ClientProtocolException.
  private static boolean isUnsupportedSchemeInRoute(IOException e) {
    Throwable cause = e.getCause();
    return e instanceof ClientProtocolException && cause instanceof HttpException
        && cause.getMessage() != null && cause.getMessage().endsWith(" protocol is not supported");
  }

  // Whether the client refused the request because its URL has no host, which it checks before it
  // plans a route, as in http:///path.
  private static boolean isMissingHost(IOException e) {
    return e instanceof ClientProtocolException && e.getMessage() != null
        && e.getMessage().startsWith("URI does not specify a valid host name: ");
  }

  // Whether TLS failed because the server's certificate could not be verified. The JDK rejects a
  // certificate it does not trust, or one that is expired or otherwise invalid, with an
  // SSLHandshakeException caused by a CertificateException; Apache rejects a certificate that does
  // not match the host name with an SSLPeerUnverifiedException. Another layer, such as a client
  // passed to FilesHttpExecutor, may wrap either, so the whole cause chain is searched, stopping if it
  // loops. Other TLS failures, such as a handshake alert from the server, are not certificate
  // failures.
  static boolean isCertificateVerificationFailure(Throwable failure) {
    java.util.Set<Throwable> seen = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
    boolean inHandshakeFailure = false;
    for (Throwable cause = failure; cause != null && seen.add(cause); cause = cause.getCause()) {
      if (cause instanceof SSLPeerUnverifiedException) {
        return true;
      }
      if (cause instanceof SSLHandshakeException) {
        inHandshakeFailure = true;
      } else if (inHandshakeFailure && cause instanceof CertificateException) {
        return true;
      }
    }
    return false;
  }

  // Whether the client stopped following redirects: Apache throws a RedirectException when a response
  // would take it past its redirect limit, and a CircularRedirectException, a kind of
  // RedirectException, when a redirect leads back to a location it already followed. Either may be
  // wrapped, so the whole cause chain is searched, stopping if it loops.
  static boolean isRedirectFailure(Throwable failure) {
    java.util.Set<Throwable> seen = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
    for (Throwable cause = failure; cause != null && seen.add(cause); cause = cause.getCause()) {
      if (cause instanceof RedirectException) {
        return true;
      }
    }
    return false;
  }

  // Whether an API request that got this status may be sent again: 429, and 502 or above, which
  // are temporary; 500 only for a GET, since repeating a GET cannot change data while repeating
  // another method might; never 501, which means the server does not support the request at all.
  static boolean isRetryableStatus(HttpRequestBase request, int status) {
    return status == 429 || status >= 502 || (status == 500 && "GET".equals(request.getMethod()));
  }

  // The request a failure belongs to: its method and its path as sent, without the host or the
  // query, so a caller can tell which operation failed without the message repeating credentials or
  // signed URL parameters.
  static String operation(HttpRequestBase request) {
    String path = request.getURI() == null ? null : request.getURI().getRawPath();
    return "(response to " + request.getMethod() + " " + (path == null ? "" : path) + ")";
  }

  // What went wrong, safe to log and to show: the kind of failure, never its message, which can
  // repeat URLs, headers or other text from the layer that failed, including an application's own
  // interceptors. The failure itself is logged at DEBUG where it happens.
  static String describe(IOException e) {
    String kind;
    if (e instanceof ConnectionPoolTimeoutException) {
      kind = "no connection became free in time";
    } else if (e instanceof InterruptedIOException) {
      kind = e instanceof RequestAbortedException ? "the request was aborted" : "a time limit ran out";
    } else if (e instanceof UnknownHostException) {
      kind = "the host name could not be resolved";
    } else if (e instanceof ConnectException || e instanceof NoRouteToHostException) {
      kind = "the connection could not be made";
    } else if (e instanceof NoHttpResponseException) {
      kind = "the server closed the connection without answering";
    } else if (isCertificateVerificationFailure(e)) {
      kind = "the server's certificate or host name did not verify";
    } else if (e instanceof SSLException) {
      kind = "the TLS connection failed";
    } else if (e instanceof UnsupportedSchemeException || isUnsupportedSchemeInRoute(e)) {
      kind = "the URL's scheme is not supported";
    } else if (isRedirectFailure(e)) {
      kind = "the redirects looped or went past the limit";
    } else if (isMissingHost(e)) {
      kind = "the request could not be sent as written";
    } else if (e instanceof ClientProtocolException) {
      kind = "the HTTP exchange failed";
    } else if (e instanceof ConnectionClosedException || e instanceof TruncatedChunkException
        || e instanceof MalformedChunkCodingException) {
      kind = "the connection ended before the response was complete";
    } else if (e instanceof SocketException) {
      kind = "the connection failed";
    } else {
      kind = "an I/O failure";
    }
    return kind + " (" + e.getClass().getSimpleName() + ")";
  }

  private static SdkException connectionError(IOException e) {
    return new ApiErrorException.ApiConnectionException("The request failed: " + describe(e));
  }

  // As above, naming the API request (see operation).
  private static SdkException connectionError(IOException e, HttpRequestBase request) {
    return new ApiErrorException.ApiConnectionException("The request failed: " + describe(e) + " " + operation(request));
  }

  // Keeps the read failure as the cause, except a malformed chunk: its message quotes the response
  // bytes it could not parse, so that failure is only named.
  private static ApiErrorException.ApiConnectionException unreadableResponse(IOException e, HttpRequestBase request) {
    if (e instanceof MalformedChunkCodingException) {
      return new ApiErrorException.ApiConnectionException(
          "The Files.com API response could not be read: its chunked encoding is malformed " + operation(request));
    }
    ApiErrorException.ApiConnectionException connectionError =
        new ApiErrorException.ApiConnectionException("The Files.com API response could not be read " + operation(request));
    connectionError.initCause(e);
    return connectionError;
  }

  private static boolean isAbsolute(String target) {
    return !target.startsWith("/");
  }

  private static boolean hasBody(RequestMethods method) {
    return method == RequestMethods.POST || method == RequestMethods.PUT || method == RequestMethods.PATCH;
  }

  private static HttpRequestBase newRequest(RequestMethods method, String url) {
    switch (method) {
      case GET:
        return new HttpGet(url);
      case DELETE:
        return new HttpDelete(url);
      case HEAD:
        return new HttpHead(url);
      case POST:
        return new HttpPost(url);
      case PUT:
        return new HttpPut(url);
      case PATCH:
        return new HttpPatch(url);
      default:
        throw new UnsupportedOperationException("Unsupported request type: " + method);
    }
  }

  // Adds the request's headers, and returns whether it carries the client's own credentials rather
  // than the call's own or none.
  private boolean addHeaders(HttpRequestBase request, Map<String, Object> options) {
    request.addHeader("User-Agent", settings.userAgent);
    if (settings.language != null) {
      request.addHeader("Accept-Language", settings.language);
    }

    String requestType = request.getMethod();
    String url = request.getURI().toString();
    boolean requiresAuth = !(url.contains("sessions") && "POST".equalsIgnoreCase(requestType));
    if (!requiresAuth) {
      return false;
    }

    Object workspaceId = options.containsKey("workspace_id") ? options.get("workspace_id") : settings.workspaceId;
    if (workspaceId != null && !workspaceId.toString().trim().isEmpty()) {
      request.addHeader("X-Files-Workspace-Id", workspaceId.toString());
    }

    if (options.containsKey("session_id")) {
      if (!(options.get("session_id") instanceof String)) {
        throw new ApiErrorException.InvalidParameterException("Bad option: session_id must be of type String");
      }
      request.addHeader("X-FilesAPI-Auth", (String) options.get("session_id"));
      return false;
    } else if (options.containsKey("api_key")) {
      if (!(options.get("api_key") instanceof String)) {
        throw new ApiErrorException.InvalidParameterException("Bad option: api_key must be of type String");
      }
      request.addHeader("X-FilesAPI-Key", (String) options.get("api_key"));
      return false;
    } else if (settings.sessionId != null && !settings.sessionId.isEmpty()) {
      request.addHeader("X-FilesAPI-Auth", settings.sessionId);
      return true;
    } else if (settings.apiKey != null && !settings.apiKey.isEmpty()) {
      request.addHeader("X-FilesAPI-Key", settings.apiKey);
      return true;
    } else {
      throw new ApiErrorException.AuthenticationException(
          String.format("Authentication required for API request: %s %s", UrlUtils.redactUrls(url), requestType), null);
    }
  }

  private static String withQuery(String baseUrl, Map<String, Object> parameters) {
    if (parameters == null || parameters.isEmpty()) {
      return baseUrl;
    }

    StringBuilder queryString = new StringBuilder();
    parameters.forEach((key, value) -> {
      if (value instanceof Map) {
        ((Map<?, ?>) value).forEach((k2, v2) -> {
          if (queryString.length() > 0) {
            queryString.append("&");
          }
          queryString.append(key).append("[").append(k2).append("]=").append(encode(v2.toString()));
        });
      } else {
        if (queryString.length() > 0) {
          queryString.append("&");
        }
        queryString.append(key).append("=").append(encode(value.toString()));
      }
    });

    return baseUrl + (baseUrl.contains("?") ? "&" : "?") + queryString;
  }

  private static String encode(String value) {
    try {
      return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (Exception e) {
      throw new RuntimeException("Failed to encode query parameter", e);
    }
  }

  private static String serialize(Map<String, Object> parameters) {
    try {
      return MAPPER.writeValueAsString(parameters);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize parameters", e);
    }
  }

  /**
   * Uploads and downloads that run with this client.
   */
  public Transfers transfers() {
    return new Transfers(this);
  }

  // Sends a request to a storage URL Files.com gave for a file's contents; it carries no Files.com
  // credentials. Stopping the scope aborts the request until the returned exchange is closed, so
  // it also ends a read of the response body, successful or not, that is waiting for the server.
  StorageExchange executeStorage(HttpRequestBase request, OperationScope scope) throws IOException {
    return executeStorage(request, scope, true);
  }

  // As executeStorage, following no redirect unless followRedirects: a request whose answer must come
  // from the very resource it names, such as a range of one version of a file.
  StorageExchange executeStorage(HttpRequestBase request, OperationScope scope, boolean followRedirects) throws IOException {
    request.setHeader("User-Agent", settings.userAgent);
    RequestConfig config = requestConfig(true);
    request.setConfig(followRedirects ? config : RequestConfig.copy(config).setRedirectsEnabled(false).build());
    CancellationToken.Registration abort = scope.onStop(request::abort);
    CredentialGuard.Exchange guard = CredentialGuard.forStorage();
    try {
      return new StorageExchange(transport.execute(request, HttpClientContext.create()), abort);
    } catch (IOException | RuntimeException | Error e) {
      abort.close();
      if (e instanceof IOException) {
        log.debug("The {} request to a storage URL failed", request.getMethod(), e);
      }
      throw e;
    } finally {
      guard.close();
    }
  }

  // A storage response and the registration that aborts its request while it is read. Closing it
  // releases the response's connection, then the registration.
  static final class StorageExchange implements AutoCloseable {
    private final CloseableHttpResponse response;
    private final CancellationToken.Registration abort;

    private StorageExchange(CloseableHttpResponse response, CancellationToken.Registration abort) {
      this.response = response;
      this.abort = abort;
    }

    CloseableHttpResponse response() {
      return response;
    }

    // Hands the response to a caller that reads it outside the operation, which no longer aborts it.
    CloseableHttpResponse detach() {
      abort.close();
      return response;
    }

    @Override
    public void close() {
      try {
        closeQuietly(response);
      } finally {
        abort.close();
      }
    }
  }

  /**
   * Opens a download from a storage URL, retrying as API requests are; the caller closes it.
   */
  public FilesApacheHttpInputStream openDownload(String url) {
    return new FilesApacheHttpInputStream(sendStorage(() -> new HttpGet(url), storageRetries()));
  }

  /**
   * Sends bytes to a storage URL with the given method, retrying as API requests are.
   */
  public long putBuffer(String url, RequestMethods method, byte[] buffer, long length) {
    HttpEntity entity = new org.apache.http.entity.ByteArrayEntity(buffer, 0, (int) length);
    closeQuietly(sendStorage(() -> storageRequest(method, url, entity), storageRetries()));
    return length;
  }

  /**
   * Sends the stream's next length bytes to a storage URL once: a stream cannot be read again, so
   * a failed request is not retried.
   */
  public long putStream(String url, RequestMethods method, java.io.InputStream stream, long length) {
    HttpEntity entity = new org.apache.http.entity.InputStreamEntity(stream, length);
    closeQuietly(sendStorage(() -> storageRequest(method, url, entity), 0));
    return length;
  }

  private static HttpEntityEnclosingRequestBase storageRequest(RequestMethods method, String url, HttpEntity entity) {
    HttpEntityEnclosingRequestBase request = method == RequestMethods.POST ? new HttpPost(url)
        : method == RequestMethods.PATCH ? new HttpPatch(url) : new HttpPut(url);
    request.setEntity(entity);
    return request;
  }

  // A storage request with API retries, returning the successful response for the caller to read
  // and close.
  private CloseableHttpResponse sendStorage(java.util.function.Supplier<HttpRequestBase> requests, int maxRetries) {
    try (OperationScope scope = open("The request", Collections.<String, Object>emptyMap())) {
      for (int retry = 0; ; retry++) {
        scope.check();
        HttpRequestBase request = requests.get();
        SdkException error;
        boolean retryable;
        String reason;
        long retryAfter = 0;
        try {
          StorageExchange exchange = executeStorage(request, scope);
          CloseableHttpResponse response = exchange.response();
          int status = response.getStatusLine().getStatusCode();
          if (status < 400) {
            return exchange.detach();
          }
          try {
            error = ApiErrors.forResponse(response, status);
          } finally {
            exchange.close();
          }
          scope.check();
          retryable = status == 429 || (status >= 500 && status != 501);
          reason = "HTTP status " + status;
          if (status == 429 || status == 503) {
            retryAfter = retryAfterMillis(response.getFirstHeader("Retry-After"));
          }
        } catch (IOException e) {
          if (scope.isStopped()) {
            throw scope.stopped(null);
          }
          error = connectionError(e);
          retryable = isRetryable(e);
          reason = describe(e);
        }
        int limit = retryLimit(maxRetries);
        if (!retryable || retry >= limit) {
          throw error;
        }
        long wait = retryDelay(retry + 1, retryAfter, maxRetries);
        log.warn("Retrying the {} request (retry {} of {}) in {} ms after {}", request.getMethod(), retry + 1, limit, wait, reason);
        scope.sleep(wait);
      }
    }
  }

  static void closeQuietly(CloseableHttpResponse response) {
    try {
      response.close();
    } catch (IOException e) {
      // The connection is discarded; the response was already handled.
    }
  }

  /**
   * Closes the client: the work it is doing stops and is waited for, and the transport it owns is
   * closed. See {@link FilesClient#close()}.
   */
  @Override
  public void close() {
    boolean first;
    boolean idle;
    List<OperationScope> running;
    synchronized (admission) {
      first = !closed.getAndSet(true);
      running = new ArrayList<OperationScope>(active);
      idle = active.isEmpty();
    }
    if (first) {
      for (OperationScope scope : running) {
        scope.cancelForClose();
      }
    }
    if (idle) {
      settle();
    }
    // A thread doing an operation's work, such as a progress listener closing the client, cannot
    // wait: its own operation cannot end meanwhile, and another may be waiting for it. The client
    // settles once its last operation ends.
    if (OperationScope.isWorkingOnCurrentThread()) {
      return;
    }
    // Wait even if this thread is interrupted meanwhile; its interrupt status is restored after.
    boolean interrupted = false;
    while (true) {
      try {
        if (settled.await(1, TimeUnit.SECONDS)) {
          break;
        }
        log.debug("Waiting for the client's operations to stop before closing it");
      } catch (InterruptedException e) {
        interrupted = true;
      }
    }
    if (interrupted) {
      Thread.currentThread().interrupt();
    }
  }

  // The body of one attempt, which records whether HttpClient asked for any of its bytes.
  private static final class WrittenEntity extends HttpEntityWrapper {
    volatile boolean written;

    WrittenEntity(HttpEntity body) {
      super(body);
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
      written = true;
      super.writeTo(out);
    }

    @Override
    public InputStream getContent() throws IOException {
      written = true;
      return super.getContent();
    }
  }

  // Where the request that returned an upload part was answered, and the request options it was
  // sent with, without those that controlled only that call.
  private static final class UploadOrigin {
    final URI apiRoot;
    final HashMap<String, Object> options;

    UploadOrigin(URI apiRoot, HashMap<String, Object> options) {
      this.apiRoot = apiRoot;
      this.options = options;
    }
  }

  private static final class Attempt {
    final FilesResponse response;
    final SdkException error;
    final boolean retryable;
    final String reason;
    final long retryAfterMillis;
    // Whether the request was sent in full, so the server may have acted on it.
    final boolean sent;

    private Attempt(FilesResponse response, SdkException error, boolean retryable, String reason, long retryAfterMillis,
        boolean sent) {
      this.response = response;
      this.error = error;
      this.retryable = retryable;
      this.reason = reason;
      this.retryAfterMillis = retryAfterMillis;
      this.sent = sent;
    }

    static Attempt succeeded(FilesResponse response) {
      return new Attempt(response, null, false, null, 0, true);
    }

    static Attempt failed(SdkException error, boolean retryable, String reason, long retryAfterMillis, boolean sent) {
      return new Attempt(null, error, retryable, reason, retryAfterMillis, sent);
    }
  }

  /**
   * The settings of one client. Immutable.
   */
  public static final class Settings {
    final String apiKey;
    final String sessionId;
    final Long workspaceId;
    final String language;
    final URI apiRoot;
    final String apiBase;
    final String userAgent;
    final int maxRetries;
    final Duration retryDelay;
    final Duration maxRetryDelay;
    final Duration connectTimeout;
    final Duration readTimeout;
    final Duration connectionAcquisitionTimeout;
    final Duration callTimeout;
    final RegionRelocationPolicy regionRelocation;

    private Settings(Builder builder) {
      this.apiKey = builder.apiKey;
      this.sessionId = builder.sessionId;
      this.workspaceId = builder.workspaceId;
      this.language = builder.language;
      this.apiRoot = builder.apiRoot;
      this.apiBase = builder.apiBase;
      this.userAgent = builder.userAgent;
      this.maxRetries = builder.maxRetries;
      this.retryDelay = builder.retryDelay;
      this.maxRetryDelay = builder.maxRetryDelay;
      this.connectTimeout = builder.connectTimeout;
      this.readTimeout = builder.readTimeout;
      this.connectionAcquisitionTimeout = builder.connectionAcquisitionTimeout;
      this.callTimeout = builder.callTimeout;
      this.regionRelocation = builder.regionRelocation;
    }

    public URI apiRoot() {
      return apiRoot;
    }

    public int maxRetries() {
      return maxRetries;
    }

    public Duration retryDelay() {
      return retryDelay;
    }

    public Duration maxRetryDelay() {
      return maxRetryDelay;
    }

    public Duration connectTimeout() {
      return connectTimeout;
    }

    public Duration readTimeout() {
      return readTimeout;
    }

    public Duration connectionAcquisitionTimeout() {
      return connectionAcquisitionTimeout;
    }

    public Duration callTimeout() {
      return callTimeout;
    }

    public RegionRelocationPolicy regionRelocation() {
      return regionRelocation;
    }

    public Long workspaceId() {
      return workspaceId;
    }

    public String language() {
      return language;
    }

    public String userAgent() {
      return userAgent;
    }

    /**
     * Whether other settings would make a client that behaves the same way; credentials are
     * compared, never printed.
     */
    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Settings)) {
        return false;
      }
      Settings that = (Settings) other;
      return java.util.Objects.equals(apiKey, that.apiKey)
          && java.util.Objects.equals(sessionId, that.sessionId)
          && java.util.Objects.equals(workspaceId, that.workspaceId)
          && java.util.Objects.equals(language, that.language)
          && apiRoot.equals(that.apiRoot)
          && apiBase.equals(that.apiBase)
          && userAgent.equals(that.userAgent)
          && maxRetries == that.maxRetries
          && retryDelay.equals(that.retryDelay)
          && maxRetryDelay.equals(that.maxRetryDelay)
          && connectTimeout.equals(that.connectTimeout)
          && readTimeout.equals(that.readTimeout)
          && connectionAcquisitionTimeout.equals(that.connectionAcquisitionTimeout)
          && java.util.Objects.equals(callTimeout, that.callTimeout)
          && regionRelocation.equals(that.regionRelocation);
    }

    @Override
    public int hashCode() {
      return java.util.Objects.hash(apiRoot, workspaceId, maxRetries);
    }

    // An API root as given, which must be an http or https URL with a host.
    static URI parseApiRoot(String apiRoot) {
      URI root;
      try {
        root = URI.create(apiRoot);
      } catch (IllegalArgumentException | NullPointerException e) {
        throw new IllegalArgumentException("The API root must be an http or https URL, such as https://MY-SUBDOMAIN.files.com");
      }
      if (root.getHost() == null || !("https".equalsIgnoreCase(root.getScheme()) || "http".equalsIgnoreCase(root.getScheme()))) {
        throw new IllegalArgumentException("The API root must be an http or https URL, such as https://MY-SUBDOMAIN.files.com");
      }
      return root;
    }

    /**
     * Builds {@link Settings}, starting from the SDK's defaults.
     */
    public static final class Builder {
      private String apiKey;
      private String sessionId;
      private Long workspaceId;
      private String language;
      private URI apiRoot = URI.create("https://app.files.com");
      private String apiBase = "/api/rest/v1";
      private String userAgent;
      private int maxRetries = 3;
      private Duration retryDelay = Duration.ofMillis(100);
      private Duration maxRetryDelay = Duration.ofSeconds(10);
      private Duration connectTimeout = Duration.ofSeconds(30);
      private Duration readTimeout = Duration.ofSeconds(80);
      private Duration connectionAcquisitionTimeout = Duration.ofSeconds(10);
      private Duration callTimeout;
      private RegionRelocationPolicy regionRelocation = RegionRelocationPolicy.FILES_COM_HOSTS;

      public Builder(String userAgent) {
        this.userAgent = userAgent;
      }

      public Builder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
      }

      public Builder sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
      }

      public Builder workspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
        return this;
      }

      public Builder language(String language) {
        this.language = language;
        return this;
      }

      public Builder apiRoot(String apiRoot) {
        this.apiRoot = parseApiRoot(apiRoot);
        return this;
      }

      public Builder apiBase(String apiBase) {
        this.apiBase = apiBase;
        return this;
      }

      public Builder userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
      }

      public Builder maxRetries(int maxRetries) {
        if (maxRetries < 0) {
          throw new IllegalArgumentException("The most retries cannot be negative");
        }
        this.maxRetries = maxRetries;
        return this;
      }

      public Builder retryDelay(Duration retryDelay) {
        this.retryDelay = nonNegative(retryDelay, "retry delay");
        return this;
      }

      public Builder maxRetryDelay(Duration maxRetryDelay) {
        this.maxRetryDelay = nonNegative(maxRetryDelay, "maximum retry delay");
        return this;
      }

      public Builder connectTimeout(Duration connectTimeout) {
        this.connectTimeout = positive(connectTimeout, "connect timeout");
        return this;
      }

      public Builder readTimeout(Duration readTimeout) {
        this.readTimeout = positive(readTimeout, "read timeout");
        return this;
      }

      // The connect and read time limits as FilesConfig gives them to the default client, in
      // milliseconds: 0 means no limit, and a negative value leaves the limit to the system default,
      // as the HTTP client reads them.
      public Builder connectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeout = Duration.ofMillis(connectTimeoutMillis);
        return this;
      }

      public Builder readTimeoutMillis(int readTimeoutMillis) {
        this.readTimeout = Duration.ofMillis(readTimeoutMillis);
        return this;
      }

      public Builder connectionAcquisitionTimeout(Duration connectionAcquisitionTimeout) {
        this.connectionAcquisitionTimeout = positive(connectionAcquisitionTimeout, "connection acquisition timeout");
        return this;
      }

      public Builder callTimeout(Duration callTimeout) {
        this.callTimeout = callTimeout == null ? null : positive(callTimeout, "call timeout");
        return this;
      }

      public Builder regionRelocation(RegionRelocationPolicy regionRelocation) {
        if (regionRelocation == null) {
          throw new IllegalArgumentException("The region relocation policy cannot be null; use RegionRelocationPolicy.DISABLED");
        }
        this.regionRelocation = regionRelocation;
        return this;
      }

      public Settings build() {
        return new Settings(this);
      }

      private static Duration nonNegative(Duration duration, String name) {
        if (duration == null || duration.isNegative()) {
          throw new IllegalArgumentException("The " + name + " cannot be null or negative");
        }
        return duration;
      }

      private static Duration positive(Duration duration, String name) {
        if (duration == null || duration.isNegative() || duration.isZero()) {
          throw new IllegalArgumentException("The " + name + " must be positive");
        }
        return duration;
      }
    }
  }
}
