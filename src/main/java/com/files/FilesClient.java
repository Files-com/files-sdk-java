package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.ListIterator;
import com.files.models.ModelInterface;
import com.files.models.Session;
import com.files.net.ApacheHttpEvictor;
import com.files.net.FilesApacheHttpApi;
import com.files.net.FilesApiInterface;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Process-wide request defaults, and the low-level request methods the generated model classes use
 * to call the Files.com API. Most applications set {@link #apiKey} or {@link #session} and then
 * call model methods rather than the request methods here.
 *
 * <p>The request header defaults {@link #apiKey}, {@link #session}, {@link #workspaceId} and
 * {@link #language} apply to the SDK API requests this class sends through {@link #apiRequest},
 * {@link #requestItem} and the pages of {@link #requestList}, which model methods use.
 * {@link #getFileInputStream}, {@link #putBuffer} and {@link #putBufferedInputStream} do not use
 * these four fields, and other HTTP requests in the JVM are not affected. For one request, the
 * options map passed to a model method or request method can replace the credentials with the
 * {@code "session_id"} or {@code "api_key"} option (each a {@code String}), and the workspace
 * with the {@code "workspace_id"} option; {@link #language} has no per-request option. Every SDK
 * API request except session creation (a POST to a URL containing {@code sessions})
 * authenticates with the first of these that is set: the {@code "session_id"} option, the
 * {@code "api_key"} option, {@link #session}, then {@link #apiKey}. When none is set, the request
 * fails with an authentication error before it is sent.
 *
 * <p>{@link #apiKey}, {@link #session}, {@link #workspaceId} and {@link #language} are plain static
 * fields that this class does not synchronize or validate. The code that sets one changes it for
 * every later SDK API request in the JVM that does not override it.
 */
public abstract class FilesClient {
  private static final Logger log = LoggerFactory.getLogger(FilesClient.class);

  private static final FilesApiInterface filesApi = new FilesApacheHttpApi();
  /**
   * The default API key, sent as the {@code X-FilesAPI-Key} header by API requests that have no
   * {@code "session_id"} or {@code "api_key"} option when {@link #session} is not set. Not used
   * when {@code null} or empty.
   */
  public static String apiKey;
  /**
   * The default language, sent as the {@code Accept-Language} header of API requests when not
   * {@code null}.
   */
  public static String language;
  /**
   * The default workspace ID, sent as the {@code X-Files-Workspace-Id} header by authenticated API
   * requests whose options have no {@code "workspace_id"} entry. Not sent when {@code null}.
   */
  public static Long workspaceId;
  /**
   * The default session. Its ID is sent as the {@code X-FilesAPI-Auth} header by API requests that
   * have no {@code "session_id"} or {@code "api_key"} option, in preference to {@link #apiKey}. Not
   * used when {@code null} or when its ID is empty.
   */
  public static Session session;
  /**
   * The HTTP connection pool the SDK's requests share. It is created when this class is
   * initialized, allowing {@link FilesConfig#getUpstreamMaxConnections()} connections in total and
   * 20 per route, as configured at that moment. The SDK's HTTP client takes this pool once, when
   * the first request creates the client; assigning this field after that does not change the pool
   * the client uses.
   */
  public static PoolingHttpClientConnectionManager connectionManager;
  private static final ApacheHttpEvictor evictor;

  static {
    connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(FilesConfig.getInstance().getUpstreamMaxConnections());
    connectionManager.setDefaultMaxPerRoute(20);
    evictor = new ApacheHttpEvictor(connectionManager, FilesConfig.getInstance().getUpstreamTimeout());
  }

  /**
   * Sends one API request and returns its response, with the body read as text. GET, DELETE and
   * HEAD requests send {@code parameters} as the query string; POST, PUT and PATCH requests send
   * them as a JSON body. The request carries the {@link FilesConfig#getUserAgent()} user agent and
   * the language, workspace and authentication headers described for this class.
   *
   * @param url the full request URL
   * @param requestType the HTTP method
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, such as {@code "api_key"}, {@code "session_id"} or
   *     {@code "workspace_id"}; must not be {@code null}
   * @return the response's status code, headers and body
   * @throws RuntimeException if the request cannot be authenticated, an option has the wrong type,
   *     or the request fails; an {@code IOException} from sending it or reading the response is
   *     wrapped in a {@code RuntimeException}
   */
  public static FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return filesApi.apiRequest(url, requestType, parameters, options);
  }

  /**
   * Returns a {@link ListIterator} for a listing request, without sending the request. Each page
   * is requested, as {@link #apiRequest} sends it, only when the iterator loads that page; see
   * {@link ListIterator} for the ways to load pages.
   *
   * @param <T> the type of each listed item
   * @param url the full request URL
   * @param requestType the HTTP method
   * @param className the type each page's JSON array is read as
   * @param parameters the request parameters; the iterator keeps this map, sends it with each page
   *     request and adds a {@code "cursor"} entry to it when paging
   * @param options request options, sent with each page request and set on each loaded model
   * @return an iterator that has not loaded any page
   * @throws RuntimeException not thrown by the current implementation, which only creates the
   *     iterator
   */
  public static <T> ListIterator<T> requestList(String url, RequestMethods requestType, TypeReference<List<T>> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return filesApi.apiRequestList(url, requestType, className, parameters, options);
  }

  /**
   * Sends one API request, as {@link #apiRequest} does, and reads the response body as a
   * {@code T}. When the result is a model ({@link ModelInterface}), {@code options} is set on it,
   * so the model's own later requests use the same options.
   *
   * @param <T> the type the response body is read as
   * @param url the full request URL
   * @param requestType the HTTP method
   * @param className the type the response body is read as
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, as for {@link #apiRequest}; must not be {@code null}
   * @return the response body read as a {@code T}
   * @throws RuntimeException if the request fails, as for {@link #apiRequest}, or the response body
   *     cannot be read as a {@code T}
   */
  public static <T> T requestItem(String url, RequestMethods requestType, TypeReference<T> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    final T item = filesApi.apiRequestItem(url, requestType, className, parameters, options);
    if (item instanceof ModelInterface) {
      ((ModelInterface) item).setOptions(options);
    }
    return item;
  }

  /**
   * Sends a GET request to {@code url} and returns a stream of the response body. Unlike
   * {@link #apiRequest}, it adds no authentication, workspace or language header, so {@code url}
   * must be one that needs none, such as a download URL the API returned.
   *
   * <p>The current implementation does not use {@code start} or {@code end}: the request asks for
   * the whole resource.
   *
   * @param url the full URL to download from
   * @param start not used
   * @param end not used
   * @return a stream of the response body, which the caller should close when done
   * @throws IOException if the request fails
   */
  public static FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    return filesApi.getFileInputStream(url, start, end);
  }

  /**
   * Uploads the first {@code length} bytes of {@code buffer} to {@code url} in one HTTP PUT, with
   * content type {@code application/octet-stream}. It always sends a PUT, and adds no
   * authentication, workspace or language header, so {@code url} must be one that needs none, such
   * as an upload URL the API returned.
   *
   * @param url the full URL to upload to
   * @param requestType not used; the request is always a PUT
   * @param name not used
   * @param buffer the bytes to upload
   * @param length the number of bytes to send from the start of {@code buffer}
   * @return {@code length}
   * @throws IOException if the request fails
   */
  public static long putBuffer(String url, RequestMethods requestType, String name, byte[] buffer, long length) throws IOException {
    return filesApi.putBuffer(url, requestType, name, buffer, length);
  }

  /**
   * Uploads {@code length} bytes read from {@code inputStream} to {@code url} in one HTTP PUT, with
   * content type {@code application/octet-stream}. It always sends a PUT, and adds no
   * authentication, workspace or language header, so {@code url} must be one that needs none, such
   * as an upload URL the API returned. {@code inputStream} becomes the request body; this method
   * does not close it itself.
   *
   * @param url the full URL to upload to
   * @param requestType not used; the request is always a PUT
   * @param name not used
   * @param inputStream the stream the request body is read from
   * @param length the number of bytes to send from {@code inputStream}
   * @return 0 in the current implementation, not the number of bytes sent
   * @throws IOException if reading {@code inputStream} or the request fails
   */
  public static long putBufferedInputStream(String url, RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException {
    return filesApi.putBufferedInputStream(url, requestType, name, inputStream, length);
  }

  /**
   * Sets a property of the process-wide configuration, as {@link FilesConfig#setProperty} does.
   *
   * <p>Calling this method first initializes this class if it is not yet initialized, and
   * initialization reads {@code upstreamMaxConnections} and {@code upstreamTimeout}. So setting
   * those two here has no effect on the connection pool; set them with
   * {@link FilesConfig#setProperty} before this class is first used.
   *
   * @param property the property name, such as {@code "apiRoot"}; must not be {@code null}
   * @param value the property value; must not be {@code null}
   */
  public static void setProperty(String property, String value) {
    FilesConfig.getInstance().setProperty(property, value);
  }
}
