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
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExavaultApiRequestLog implements ModelInterface {
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

  // One mapper for every ExavaultApiRequestLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ExavaultApiRequestLog} with no attributes set.
   */
  public ExavaultApiRequestLog() {
    this(null, null);
  }

  /**
   * Creates a {@code ExavaultApiRequestLog} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ExavaultApiRequestLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ExavaultApiRequestLog} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ExavaultApiRequestLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Start Time of Action. Deprecrated: Use created_at.
   */
  @JsonProperty("timestamp")
  public Date timestamp;

  /**
   * Gets {@link #timestamp}.
   *
   * @return the value of {@link #timestamp}
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * Name of API Endpoint
   */
  @JsonProperty("endpoint")
  public String endpoint;

  /**
   * Gets {@link #endpoint}.
   *
   * @return the value of {@link #endpoint}
   */
  public String getEndpoint() {
    return endpoint;
  }

  /**
   * Exavault API Version
   */
  @JsonProperty("version")
  public Double version;

  /**
   * Gets {@link #version}.
   *
   * @return the value of {@link #version}
   */
  public Double getVersion() {
    return version;
  }

  /**
   * IP of requesting client
   */
  @JsonProperty("request_ip")
  public String requestIp;

  /**
   * Gets {@link #requestIp}.
   *
   * @return the value of {@link #requestIp}
   */
  public String getRequestIp() {
    return requestIp;
  }

  /**
   * HTTP Method
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
   * Error type, if applicable
   */
  @JsonProperty("error_type")
  public String errorType;

  /**
   * Gets {@link #errorType}.
   *
   * @return the value of {@link #errorType}
   */
  public String getErrorType() {
    return errorType;
  }

  /**
   * Error message, if applicable
   */
  @JsonProperty("error_message")
  public String errorMessage;

  /**
   * Gets {@link #errorMessage}.
   *
   * @return the value of {@link #errorMessage}
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * User-Agent
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
   * HTTP Response Code
   */
  @JsonProperty("response_code")
  public Long responseCode;

  /**
   * Gets {@link #responseCode}.
   *
   * @return the value of {@link #responseCode}
   */
  public Long getResponseCode() {
    return responseCode;
  }

  /**
   * `false` if HTTP Response Code is 4xx or 5xx
   */
  @JsonProperty("success")
  public Boolean success;

  /**
   * Gets {@link #success}.
   *
   * @return the value of {@link #success}
   */
  public Boolean getSuccess() {
    return success;
  }

  /**
   * Duration (in milliseconds)
   */
  @JsonProperty("duration_ms")
  public Long durationMs;

  /**
   * Gets {@link #durationMs}.
   *
   * @return the value of {@link #durationMs}
   */
  public Long getDurationMs() {
    return durationMs;
  }

  /**
   * Start Time of Action
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }


  /**
   * List Exavault API Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExavaultApiRequestLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Exavault API Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExavaultApiRequestLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Exavault API Request Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `request_ip`, `request_method`, `success` or `created_at`. Valid field combinations are `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ created_at ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_ip, created_at ]`, `[ request_method, success ]`, `[ request_method, created_at ]`, `[ success, created_at ]`, `[ request_ip, request_method, success ]`, `[ request_ip, request_method, created_at ]`, `[ request_ip, success, created_at ]`, `[ request_method, success, created_at ]` or `[ request_ip, request_method, success, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ created_at ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_ip, created_at ]`, `[ request_method, success ]`, `[ request_method, created_at ]`, `[ success, created_at ]`, `[ request_ip, request_method, success ]`, `[ request_ip, request_method, created_at ]`, `[ request_ip, success, created_at ]`, `[ request_method, success, created_at ]` or `[ request_ip, request_method, success, created_at ]`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ created_at ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_ip, created_at ]`, `[ request_method, success ]`, `[ request_method, created_at ]`, `[ success, created_at ]`, `[ request_ip, request_method, success ]`, `[ request_ip, request_method, created_at ]`, `[ request_ip, success, created_at ]`, `[ request_method, success, created_at ]` or `[ request_ip, request_method, success, created_at ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `request_ip` and `request_method`. Valid field combinations are `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ created_at ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_ip, created_at ]`, `[ request_method, success ]`, `[ request_method, created_at ]`, `[ success, created_at ]`, `[ request_ip, request_method, success ]`, `[ request_ip, request_method, created_at ]`, `[ request_ip, success, created_at ]`, `[ request_method, success, created_at ]` or `[ request_ip, request_method, success, created_at ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ created_at ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_ip, created_at ]`, `[ request_method, success ]`, `[ request_method, created_at ]`, `[ success, created_at ]`, `[ request_ip, request_method, success ]`, `[ request_ip, request_method, created_at ]`, `[ request_ip, success, created_at ]`, `[ request_method, success, created_at ]` or `[ request_ip, request_method, success, created_at ]`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ request_ip ]`, `[ request_method ]`, `[ success ]`, `[ created_at ]`, `[ request_ip, request_method ]`, `[ request_ip, success ]`, `[ request_ip, created_at ]`, `[ request_method, success ]`, `[ request_method, created_at ]`, `[ success, created_at ]`, `[ request_ip, request_method, success ]`, `[ request_ip, request_method, created_at ]`, `[ request_ip, success, created_at ]`, `[ request_method, success, created_at ]` or `[ request_ip, request_method, success, created_at ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ExavaultApiRequestLog} records, in a {@code ListIterator}
   */
  public static ListIterator<ExavaultApiRequestLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().exavaultApiRequestLogs().list(parameters, options);
  }

  /**
   * List Exavault API Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExavaultApiRequestLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Exavault API Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ExavaultApiRequestLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
