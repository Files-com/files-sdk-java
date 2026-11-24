package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScimLog implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every ScimLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ScimLog} with no attributes set.
   */
  public ScimLog() {
    this(null, null);
  }

  /**
   * Creates a {@code ScimLog} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ScimLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ScimLog} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ScimLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The unique ID of this SCIM request.
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * The date and time when this SCIM request occurred.
   */
  @JsonProperty("created_at")
  public String createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   * The path portion of the URL requested.
   */
  @JsonProperty("request_path")
  public String requestPath;

  /**
   * Gets {@link #requestPath}.
   *
   * @return the value of {@link #requestPath}
   */
  public String getRequestPath() {
    return requestPath;
  }

  /**
   * The HTTP method used for this request.
   */
  @JsonProperty("request_method")
  public String requestMethod;

  /**
   * Gets {@link #requestMethod}.
   *
   * @return the value of {@link #requestMethod}
   */
  public String getRequestMethod() {
    return requestMethod;
  }

  /**
   * The HTTP response code returned for this request.
   */
  @JsonProperty("http_response_code")
  public String httpResponseCode;

  /**
   * Gets {@link #httpResponseCode}.
   *
   * @return the value of {@link #httpResponseCode}
   */
  public String getHttpResponseCode() {
    return httpResponseCode;
  }

  /**
   * The User-Agent header sent with the request.
   */
  @JsonProperty("user_agent")
  public String userAgent;

  /**
   * Gets {@link #userAgent}.
   *
   * @return the value of {@link #userAgent}
   */
  public String getUserAgent() {
    return userAgent;
  }

  /**
   * The JSON payload sent with the request.
   */
  @JsonProperty("request_json")
  public String requestJson;

  /**
   * Gets {@link #requestJson}.
   *
   * @return the value of {@link #requestJson}
   */
  public String getRequestJson() {
    return requestJson;
  }

  /**
   * The JSON payload returned in the response.
   */
  @JsonProperty("response_json")
  public String responseJson;

  /**
   * Gets {@link #responseJson}.
   *
   * @return the value of {@link #responseJson}
   */
  public String getResponseJson() {
    return responseJson;
  }


  /**
   * List Scim Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScimLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Scim Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScimLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Scim Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ScimLog} records, in a {@code ListIterator}
   */
  public static ListIterator<ScimLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scimLogs().list(parameters, options);
  }

  /**
   * List Scim Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScimLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Scim Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ScimLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Scim Log
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScimLog find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Scim Log
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScimLog find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Scim Log
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScimLog find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Scim Log
   *
   * @param id Scim Log ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ScimLog}
   */
  public static ScimLog find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().scimLogs().find(id, parameters, options);
  }

  /**
   * Show Scim Log
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScimLog get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Scim Log
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ScimLog get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
