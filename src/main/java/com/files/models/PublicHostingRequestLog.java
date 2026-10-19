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
public class PublicHostingRequestLog implements ModelInterface {
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

  // One mapper for every PublicHostingRequestLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code PublicHostingRequestLog} with no attributes set.
   */
  public PublicHostingRequestLog() {
    this(null, null);
  }

  /**
   * Creates a {@code PublicHostingRequestLog} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public PublicHostingRequestLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code PublicHostingRequestLog} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public PublicHostingRequestLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
   * IP Address of Public Hosting Client.
   */
  @JsonProperty("remote_ip")
  public String remoteIp;

  /**
   * Gets {@link #remoteIp}.
   *
   * @return the value of {@link #remoteIp}
   */
  public String getRemoteIp() {
    return remoteIp;
  }

  /**
   * IP Address of Public Hosting Server.
   */
  @JsonProperty("server_ip")
  public String serverIp;

  /**
   * Gets {@link #serverIp}.
   *
   * @return the value of {@link #serverIp}
   */
  public String getServerIp() {
    return serverIp;
  }

  /**
   * HTTP Request Hostname.
   */
  @JsonProperty("hostname")
  public String hostname;

  /**
   * Gets {@link #hostname}.
   *
   * @return the value of {@link #hostname}
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * HTTP Request Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * HTTP Response Code.
   */
  @JsonProperty("responseCode")
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
   * Whether SFTP Action was successful.
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
   * Duration (in milliseconds).
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
   * Start Time of Action.
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
   * The number of bytes transferred for file downloads.
   */
  @JsonProperty("bytes_transferred")
  public Long bytesTransferred;

  /**
   * Gets {@link #bytesTransferred}.
   *
   * @return the value of {@link #bytesTransferred}
   */
  public Long getBytesTransferred() {
    return bytesTransferred;
  }

  /**
   * Method of the HTTP call.
   */
  @JsonProperty("http_method")
  public String httpMethod;

  /**
   * Gets {@link #httpMethod}.
   *
   * @return the value of {@link #httpMethod}
   */
  public String getHttpMethod() {
    return httpMethod;
  }


  /**
   * List Public Hosting Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicHostingRequestLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Public Hosting Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicHostingRequestLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Public Hosting Request Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `remote_ip`, `success` or `created_at`. Valid field combinations are `[ path ]`, `[ remote_ip ]`, `[ success ]`, `[ created_at ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, created_at ]`, `[ success, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, created_at ]`, `[ path, success, created_at ]`, `[ remote_ip, success, created_at ]` or `[ path, remote_ip, success, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ remote_ip ]`, `[ success ]`, `[ created_at ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, created_at ]`, `[ success, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, created_at ]`, `[ path, success, created_at ]`, `[ remote_ip, success, created_at ]` or `[ path, remote_ip, success, created_at ]`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ remote_ip ]`, `[ success ]`, `[ created_at ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, created_at ]`, `[ success, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, created_at ]`, `[ path, success, created_at ]`, `[ remote_ip, success, created_at ]` or `[ path, remote_ip, success, created_at ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`. Valid field combinations are `[ path ]`, `[ remote_ip ]`, `[ success ]`, `[ created_at ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, created_at ]`, `[ success, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, created_at ]`, `[ path, success, created_at ]`, `[ remote_ip, success, created_at ]` or `[ path, remote_ip, success, created_at ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ remote_ip ]`, `[ success ]`, `[ created_at ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, created_at ]`, `[ success, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, created_at ]`, `[ path, success, created_at ]`, `[ remote_ip, success, created_at ]` or `[ path, remote_ip, success, created_at ]`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ remote_ip ]`, `[ success ]`, `[ created_at ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, created_at ]`, `[ success, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, created_at ]`, `[ path, success, created_at ]`, `[ remote_ip, success, created_at ]` or `[ path, remote_ip, success, created_at ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicHostingRequestLog} records, in a {@code ListIterator}
   */
  public static ListIterator<PublicHostingRequestLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().publicHostingRequestLogs().list(parameters, options);
  }

  /**
   * List Public Hosting Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicHostingRequestLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Public Hosting Request Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PublicHostingRequestLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
