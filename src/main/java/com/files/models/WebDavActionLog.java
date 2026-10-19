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
public class WebDavActionLog implements ModelInterface {
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

  // One mapper for every WebDavActionLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code WebDavActionLog} with no attributes set.
   */
  public WebDavActionLog() {
    this(null, null);
  }

  /**
   * Creates a {@code WebDavActionLog} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public WebDavActionLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code WebDavActionLog} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public WebDavActionLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
   * IP Address of WebDAV Client
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
   * IP Address of WebDAV Server
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
   * Username
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Authentication Ciphers
   */
  @JsonProperty("auth_ciphers")
  public String authCiphers;

  /**
   * Gets {@link #authCiphers}.
   *
   * @return the value of {@link #authCiphers}
   */
  public String getAuthCiphers() {
    return authCiphers;
  }

  /**
   * Action Type
   */
  @JsonProperty("action_type")
  public String actionType;

  /**
   * Gets {@link #actionType}.
   *
   * @return the value of {@link #actionType}
   */
  public String getActionType() {
    return actionType;
  }

  /**
   * Path as sent by the Client (may not match Files.com path due to user root folders for WebDAV). This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Path on Files.com
   */
  @JsonProperty("true_path")
  public String truePath;

  /**
   * Gets {@link #truePath}.
   *
   * @return the value of {@link #truePath}
   */
  public String getTruePath() {
    return truePath;
  }

  /**
   * Name of File
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
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
   * Path of the HTTP call.
   */
  @JsonProperty("http_path")
  public String httpPath;

  /**
   * Gets {@link #httpPath}.
   *
   * @return the value of {@link #httpPath}
   */
  public String getHttpPath() {
    return httpPath;
  }

  /**
   * HTTP Response Code returned to the WebDAV Client.
   */
  @JsonProperty("http_response_code")
  public Long httpResponseCode;

  /**
   * Gets {@link #httpResponseCode}.
   *
   * @return the value of {@link #httpResponseCode}
   */
  public Long getHttpResponseCode() {
    return httpResponseCode;
  }

  /**
   * Size of File That was Uploaded or Downloaded.
   */
  @JsonProperty("size")
  public Long size;

  /**
   * Gets {@link #size}.
   *
   * @return the value of {@link #size}
   */
  public Long getSize() {
    return size;
  }

  /**
   * Number of entries returned when listing files and folders
   */
  @JsonProperty("entries_returned")
  public Long entriesReturned;

  /**
   * Gets {@link #entriesReturned}.
   *
   * @return the value of {@link #entriesReturned}
   */
  public Long getEntriesReturned() {
    return entriesReturned;
  }

  /**
   * Whether WebDAV Action was successful.
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
   * WebDAV Action status.
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
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
   * List WebDAV Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<WebDavActionLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List WebDAV Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<WebDavActionLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List WebDAV Action Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `true_path`, `remote_ip`, `success`, `status`, `action_type`, `username` or `created_at`. Valid field combinations are `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ status ]`, `[ action_type ]`, `[ username ]`, `[ created_at ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, status ]`, `[ path, action_type ]`, `[ path, username ]`, `[ path, created_at ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, status ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ true_path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, status ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ remote_ip, created_at ]`, `[ success, status ]`, `[ success, action_type ]`, `[ success, username ]`, `[ success, created_at ]`, `[ status, action_type ]`, `[ status, username ]`, `[ status, created_at ]`, `[ action_type, username ]`, `[ action_type, created_at ]`, `[ username, created_at ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, status ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, true_path, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, status ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, remote_ip, created_at ]`, `[ path, success, status ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, success, created_at ]`, `[ path, status, action_type ]`, `[ path, status, username ]`, `[ path, status, created_at ]`, `[ path, action_type, username ]`, `[ path, action_type, created_at ]`, `[ path, username, created_at ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, status ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, remote_ip, created_at ]`, `[ true_path, success, status ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, success, created_at ]`, `[ true_path, status, action_type ]`, `[ true_path, status, username ]`, `[ true_path, status, created_at ]`, `[ true_path, action_type, username ]`, `[ true_path, action_type, created_at ]`, `[ true_path, username, created_at ]`, `[ remote_ip, success, status ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, success, created_at ]`, `[ remote_ip, status, action_type ]`, `[ remote_ip, status, username ]`, `[ remote_ip, status, created_at ]`, `[ remote_ip, action_type, username ]`, `[ remote_ip, action_type, created_at ]`, `[ remote_ip, username, created_at ]`, `[ success, status, action_type ]`, `[ success, status, username ]`, `[ success, status, created_at ]`, `[ success, action_type, username ]`, `[ success, action_type, created_at ]`, `[ success, username, created_at ]`, `[ status, action_type, username ]`, `[ status, action_type, created_at ]`, `[ status, username, created_at ]`, `[ action_type, username, created_at ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, status ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, remote_ip, created_at ]`, `[ path, true_path, success, status ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, success, created_at ]`, `[ path, true_path, status, action_type ]`, `[ path, true_path, status, username ]`, `[ path, true_path, status, created_at ]`, `[ path, true_path, action_type, username ]`, `[ path, true_path, action_type, created_at ]`, `[ path, true_path, username, created_at ]`, `[ path, remote_ip, success, status ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, success, created_at ]`, `[ path, remote_ip, status, action_type ]`, `[ path, remote_ip, status, username ]`, `[ path, remote_ip, status, created_at ]`, `[ path, remote_ip, action_type, username ]`, `[ path, remote_ip, action_type, created_at ]`, `[ path, remote_ip, username, created_at ]`, `[ path, success, status, action_type ]`, `[ path, success, status, username ]`, `[ path, success, status, created_at ]`, `[ path, success, action_type, username ]`, `[ path, success, action_type, created_at ]`, `[ path, success, username, created_at ]`, `[ path, status, action_type, username ]`, `[ path, status, action_type, created_at ]`, `[ path, status, username, created_at ]`, `[ path, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, success, created_at ]`, `[ true_path, remote_ip, status, action_type ]`, `[ true_path, remote_ip, status, username ]`, `[ true_path, remote_ip, status, created_at ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, remote_ip, action_type, created_at ]`, `[ true_path, remote_ip, username, created_at ]`, `[ true_path, success, status, action_type ]`, `[ true_path, success, status, username ]`, `[ true_path, success, status, created_at ]`, `[ true_path, success, action_type, username ]`, `[ true_path, success, action_type, created_at ]`, `[ true_path, success, username, created_at ]`, `[ true_path, status, action_type, username ]`, `[ true_path, status, action_type, created_at ]`, `[ true_path, status, username, created_at ]`, `[ true_path, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type ]`, `[ remote_ip, success, status, username ]`, `[ remote_ip, success, status, created_at ]`, `[ remote_ip, success, action_type, username ]`, `[ remote_ip, success, action_type, created_at ]`, `[ remote_ip, success, username, created_at ]`, `[ remote_ip, status, action_type, username ]`, `[ remote_ip, status, action_type, created_at ]`, `[ remote_ip, status, username, created_at ]`, `[ remote_ip, action_type, username, created_at ]`, `[ success, status, action_type, username ]`, `[ success, status, action_type, created_at ]`, `[ success, status, username, created_at ]`, `[ success, action_type, username, created_at ]`, `[ status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, success, created_at ]`, `[ path, true_path, remote_ip, status, action_type ]`, `[ path, true_path, remote_ip, status, username ]`, `[ path, true_path, remote_ip, status, created_at ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, remote_ip, action_type, created_at ]`, `[ path, true_path, remote_ip, username, created_at ]`, `[ path, true_path, success, status, action_type ]`, `[ path, true_path, success, status, username ]`, `[ path, true_path, success, status, created_at ]`, `[ path, true_path, success, action_type, username ]`, `[ path, true_path, success, action_type, created_at ]`, `[ path, true_path, success, username, created_at ]`, `[ path, true_path, status, action_type, username ]`, `[ path, true_path, status, action_type, created_at ]`, `[ path, true_path, status, username, created_at ]`, `[ path, true_path, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type ]`, `[ path, remote_ip, success, status, username ]`, `[ path, remote_ip, success, status, created_at ]`, `[ path, remote_ip, success, action_type, username ]`, `[ path, remote_ip, success, action_type, created_at ]`, `[ path, remote_ip, success, username, created_at ]`, `[ path, remote_ip, status, action_type, username ]`, `[ path, remote_ip, status, action_type, created_at ]`, `[ path, remote_ip, status, username, created_at ]`, `[ path, remote_ip, action_type, username, created_at ]`, `[ path, success, status, action_type, username ]`, `[ path, success, status, action_type, created_at ]`, `[ path, success, status, username, created_at ]`, `[ path, success, action_type, username, created_at ]`, `[ path, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type ]`, `[ true_path, remote_ip, success, status, username ]`, `[ true_path, remote_ip, success, status, created_at ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, created_at ]`, `[ true_path, remote_ip, success, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username ]`, `[ true_path, remote_ip, status, action_type, created_at ]`, `[ true_path, remote_ip, status, username, created_at ]`, `[ true_path, remote_ip, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username ]`, `[ true_path, success, status, action_type, created_at ]`, `[ true_path, success, status, username, created_at ]`, `[ true_path, success, action_type, username, created_at ]`, `[ true_path, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username ]`, `[ remote_ip, success, status, action_type, created_at ]`, `[ remote_ip, success, status, username, created_at ]`, `[ remote_ip, success, action_type, username, created_at ]`, `[ remote_ip, status, action_type, username, created_at ]`, `[ success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type ]`, `[ path, true_path, remote_ip, success, status, username ]`, `[ path, true_path, remote_ip, success, status, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, created_at ]`, `[ path, true_path, remote_ip, success, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username ]`, `[ path, true_path, remote_ip, status, action_type, created_at ]`, `[ path, true_path, remote_ip, status, username, created_at ]`, `[ path, true_path, remote_ip, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username ]`, `[ path, true_path, success, status, action_type, created_at ]`, `[ path, true_path, success, status, username, created_at ]`, `[ path, true_path, success, action_type, username, created_at ]`, `[ path, true_path, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username ]`, `[ path, remote_ip, success, status, action_type, created_at ]`, `[ path, remote_ip, success, status, username, created_at ]`, `[ path, remote_ip, success, action_type, username, created_at ]`, `[ path, remote_ip, status, action_type, username, created_at ]`, `[ path, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username ]`, `[ true_path, remote_ip, success, status, action_type, created_at ]`, `[ true_path, remote_ip, success, status, username, created_at ]`, `[ true_path, remote_ip, success, action_type, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type, username ]`, `[ path, true_path, remote_ip, success, status, action_type, created_at ]`, `[ path, true_path, remote_ip, success, status, username, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username, created_at ]` or `[ path, true_path, remote_ip, success, status, action_type, username, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ status ]`, `[ action_type ]`, `[ username ]`, `[ created_at ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, status ]`, `[ path, action_type ]`, `[ path, username ]`, `[ path, created_at ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, status ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ true_path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, status ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ remote_ip, created_at ]`, `[ success, status ]`, `[ success, action_type ]`, `[ success, username ]`, `[ success, created_at ]`, `[ status, action_type ]`, `[ status, username ]`, `[ status, created_at ]`, `[ action_type, username ]`, `[ action_type, created_at ]`, `[ username, created_at ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, status ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, true_path, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, status ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, remote_ip, created_at ]`, `[ path, success, status ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, success, created_at ]`, `[ path, status, action_type ]`, `[ path, status, username ]`, `[ path, status, created_at ]`, `[ path, action_type, username ]`, `[ path, action_type, created_at ]`, `[ path, username, created_at ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, status ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, remote_ip, created_at ]`, `[ true_path, success, status ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, success, created_at ]`, `[ true_path, status, action_type ]`, `[ true_path, status, username ]`, `[ true_path, status, created_at ]`, `[ true_path, action_type, username ]`, `[ true_path, action_type, created_at ]`, `[ true_path, username, created_at ]`, `[ remote_ip, success, status ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, success, created_at ]`, `[ remote_ip, status, action_type ]`, `[ remote_ip, status, username ]`, `[ remote_ip, status, created_at ]`, `[ remote_ip, action_type, username ]`, `[ remote_ip, action_type, created_at ]`, `[ remote_ip, username, created_at ]`, `[ success, status, action_type ]`, `[ success, status, username ]`, `[ success, status, created_at ]`, `[ success, action_type, username ]`, `[ success, action_type, created_at ]`, `[ success, username, created_at ]`, `[ status, action_type, username ]`, `[ status, action_type, created_at ]`, `[ status, username, created_at ]`, `[ action_type, username, created_at ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, status ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, remote_ip, created_at ]`, `[ path, true_path, success, status ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, success, created_at ]`, `[ path, true_path, status, action_type ]`, `[ path, true_path, status, username ]`, `[ path, true_path, status, created_at ]`, `[ path, true_path, action_type, username ]`, `[ path, true_path, action_type, created_at ]`, `[ path, true_path, username, created_at ]`, `[ path, remote_ip, success, status ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, success, created_at ]`, `[ path, remote_ip, status, action_type ]`, `[ path, remote_ip, status, username ]`, `[ path, remote_ip, status, created_at ]`, `[ path, remote_ip, action_type, username ]`, `[ path, remote_ip, action_type, created_at ]`, `[ path, remote_ip, username, created_at ]`, `[ path, success, status, action_type ]`, `[ path, success, status, username ]`, `[ path, success, status, created_at ]`, `[ path, success, action_type, username ]`, `[ path, success, action_type, created_at ]`, `[ path, success, username, created_at ]`, `[ path, status, action_type, username ]`, `[ path, status, action_type, created_at ]`, `[ path, status, username, created_at ]`, `[ path, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, success, created_at ]`, `[ true_path, remote_ip, status, action_type ]`, `[ true_path, remote_ip, status, username ]`, `[ true_path, remote_ip, status, created_at ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, remote_ip, action_type, created_at ]`, `[ true_path, remote_ip, username, created_at ]`, `[ true_path, success, status, action_type ]`, `[ true_path, success, status, username ]`, `[ true_path, success, status, created_at ]`, `[ true_path, success, action_type, username ]`, `[ true_path, success, action_type, created_at ]`, `[ true_path, success, username, created_at ]`, `[ true_path, status, action_type, username ]`, `[ true_path, status, action_type, created_at ]`, `[ true_path, status, username, created_at ]`, `[ true_path, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type ]`, `[ remote_ip, success, status, username ]`, `[ remote_ip, success, status, created_at ]`, `[ remote_ip, success, action_type, username ]`, `[ remote_ip, success, action_type, created_at ]`, `[ remote_ip, success, username, created_at ]`, `[ remote_ip, status, action_type, username ]`, `[ remote_ip, status, action_type, created_at ]`, `[ remote_ip, status, username, created_at ]`, `[ remote_ip, action_type, username, created_at ]`, `[ success, status, action_type, username ]`, `[ success, status, action_type, created_at ]`, `[ success, status, username, created_at ]`, `[ success, action_type, username, created_at ]`, `[ status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, success, created_at ]`, `[ path, true_path, remote_ip, status, action_type ]`, `[ path, true_path, remote_ip, status, username ]`, `[ path, true_path, remote_ip, status, created_at ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, remote_ip, action_type, created_at ]`, `[ path, true_path, remote_ip, username, created_at ]`, `[ path, true_path, success, status, action_type ]`, `[ path, true_path, success, status, username ]`, `[ path, true_path, success, status, created_at ]`, `[ path, true_path, success, action_type, username ]`, `[ path, true_path, success, action_type, created_at ]`, `[ path, true_path, success, username, created_at ]`, `[ path, true_path, status, action_type, username ]`, `[ path, true_path, status, action_type, created_at ]`, `[ path, true_path, status, username, created_at ]`, `[ path, true_path, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type ]`, `[ path, remote_ip, success, status, username ]`, `[ path, remote_ip, success, status, created_at ]`, `[ path, remote_ip, success, action_type, username ]`, `[ path, remote_ip, success, action_type, created_at ]`, `[ path, remote_ip, success, username, created_at ]`, `[ path, remote_ip, status, action_type, username ]`, `[ path, remote_ip, status, action_type, created_at ]`, `[ path, remote_ip, status, username, created_at ]`, `[ path, remote_ip, action_type, username, created_at ]`, `[ path, success, status, action_type, username ]`, `[ path, success, status, action_type, created_at ]`, `[ path, success, status, username, created_at ]`, `[ path, success, action_type, username, created_at ]`, `[ path, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type ]`, `[ true_path, remote_ip, success, status, username ]`, `[ true_path, remote_ip, success, status, created_at ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, created_at ]`, `[ true_path, remote_ip, success, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username ]`, `[ true_path, remote_ip, status, action_type, created_at ]`, `[ true_path, remote_ip, status, username, created_at ]`, `[ true_path, remote_ip, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username ]`, `[ true_path, success, status, action_type, created_at ]`, `[ true_path, success, status, username, created_at ]`, `[ true_path, success, action_type, username, created_at ]`, `[ true_path, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username ]`, `[ remote_ip, success, status, action_type, created_at ]`, `[ remote_ip, success, status, username, created_at ]`, `[ remote_ip, success, action_type, username, created_at ]`, `[ remote_ip, status, action_type, username, created_at ]`, `[ success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type ]`, `[ path, true_path, remote_ip, success, status, username ]`, `[ path, true_path, remote_ip, success, status, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, created_at ]`, `[ path, true_path, remote_ip, success, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username ]`, `[ path, true_path, remote_ip, status, action_type, created_at ]`, `[ path, true_path, remote_ip, status, username, created_at ]`, `[ path, true_path, remote_ip, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username ]`, `[ path, true_path, success, status, action_type, created_at ]`, `[ path, true_path, success, status, username, created_at ]`, `[ path, true_path, success, action_type, username, created_at ]`, `[ path, true_path, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username ]`, `[ path, remote_ip, success, status, action_type, created_at ]`, `[ path, remote_ip, success, status, username, created_at ]`, `[ path, remote_ip, success, action_type, username, created_at ]`, `[ path, remote_ip, status, action_type, username, created_at ]`, `[ path, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username ]`, `[ true_path, remote_ip, success, status, action_type, created_at ]`, `[ true_path, remote_ip, success, status, username, created_at ]`, `[ true_path, remote_ip, success, action_type, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type, username ]`, `[ path, true_path, remote_ip, success, status, action_type, created_at ]`, `[ path, true_path, remote_ip, success, status, username, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username, created_at ]` or `[ path, true_path, remote_ip, success, status, action_type, username, created_at ]`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ status ]`, `[ action_type ]`, `[ username ]`, `[ created_at ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, status ]`, `[ path, action_type ]`, `[ path, username ]`, `[ path, created_at ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, status ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ true_path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, status ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ remote_ip, created_at ]`, `[ success, status ]`, `[ success, action_type ]`, `[ success, username ]`, `[ success, created_at ]`, `[ status, action_type ]`, `[ status, username ]`, `[ status, created_at ]`, `[ action_type, username ]`, `[ action_type, created_at ]`, `[ username, created_at ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, status ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, true_path, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, status ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, remote_ip, created_at ]`, `[ path, success, status ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, success, created_at ]`, `[ path, status, action_type ]`, `[ path, status, username ]`, `[ path, status, created_at ]`, `[ path, action_type, username ]`, `[ path, action_type, created_at ]`, `[ path, username, created_at ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, status ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, remote_ip, created_at ]`, `[ true_path, success, status ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, success, created_at ]`, `[ true_path, status, action_type ]`, `[ true_path, status, username ]`, `[ true_path, status, created_at ]`, `[ true_path, action_type, username ]`, `[ true_path, action_type, created_at ]`, `[ true_path, username, created_at ]`, `[ remote_ip, success, status ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, success, created_at ]`, `[ remote_ip, status, action_type ]`, `[ remote_ip, status, username ]`, `[ remote_ip, status, created_at ]`, `[ remote_ip, action_type, username ]`, `[ remote_ip, action_type, created_at ]`, `[ remote_ip, username, created_at ]`, `[ success, status, action_type ]`, `[ success, status, username ]`, `[ success, status, created_at ]`, `[ success, action_type, username ]`, `[ success, action_type, created_at ]`, `[ success, username, created_at ]`, `[ status, action_type, username ]`, `[ status, action_type, created_at ]`, `[ status, username, created_at ]`, `[ action_type, username, created_at ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, status ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, remote_ip, created_at ]`, `[ path, true_path, success, status ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, success, created_at ]`, `[ path, true_path, status, action_type ]`, `[ path, true_path, status, username ]`, `[ path, true_path, status, created_at ]`, `[ path, true_path, action_type, username ]`, `[ path, true_path, action_type, created_at ]`, `[ path, true_path, username, created_at ]`, `[ path, remote_ip, success, status ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, success, created_at ]`, `[ path, remote_ip, status, action_type ]`, `[ path, remote_ip, status, username ]`, `[ path, remote_ip, status, created_at ]`, `[ path, remote_ip, action_type, username ]`, `[ path, remote_ip, action_type, created_at ]`, `[ path, remote_ip, username, created_at ]`, `[ path, success, status, action_type ]`, `[ path, success, status, username ]`, `[ path, success, status, created_at ]`, `[ path, success, action_type, username ]`, `[ path, success, action_type, created_at ]`, `[ path, success, username, created_at ]`, `[ path, status, action_type, username ]`, `[ path, status, action_type, created_at ]`, `[ path, status, username, created_at ]`, `[ path, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, success, created_at ]`, `[ true_path, remote_ip, status, action_type ]`, `[ true_path, remote_ip, status, username ]`, `[ true_path, remote_ip, status, created_at ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, remote_ip, action_type, created_at ]`, `[ true_path, remote_ip, username, created_at ]`, `[ true_path, success, status, action_type ]`, `[ true_path, success, status, username ]`, `[ true_path, success, status, created_at ]`, `[ true_path, success, action_type, username ]`, `[ true_path, success, action_type, created_at ]`, `[ true_path, success, username, created_at ]`, `[ true_path, status, action_type, username ]`, `[ true_path, status, action_type, created_at ]`, `[ true_path, status, username, created_at ]`, `[ true_path, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type ]`, `[ remote_ip, success, status, username ]`, `[ remote_ip, success, status, created_at ]`, `[ remote_ip, success, action_type, username ]`, `[ remote_ip, success, action_type, created_at ]`, `[ remote_ip, success, username, created_at ]`, `[ remote_ip, status, action_type, username ]`, `[ remote_ip, status, action_type, created_at ]`, `[ remote_ip, status, username, created_at ]`, `[ remote_ip, action_type, username, created_at ]`, `[ success, status, action_type, username ]`, `[ success, status, action_type, created_at ]`, `[ success, status, username, created_at ]`, `[ success, action_type, username, created_at ]`, `[ status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, success, created_at ]`, `[ path, true_path, remote_ip, status, action_type ]`, `[ path, true_path, remote_ip, status, username ]`, `[ path, true_path, remote_ip, status, created_at ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, remote_ip, action_type, created_at ]`, `[ path, true_path, remote_ip, username, created_at ]`, `[ path, true_path, success, status, action_type ]`, `[ path, true_path, success, status, username ]`, `[ path, true_path, success, status, created_at ]`, `[ path, true_path, success, action_type, username ]`, `[ path, true_path, success, action_type, created_at ]`, `[ path, true_path, success, username, created_at ]`, `[ path, true_path, status, action_type, username ]`, `[ path, true_path, status, action_type, created_at ]`, `[ path, true_path, status, username, created_at ]`, `[ path, true_path, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type ]`, `[ path, remote_ip, success, status, username ]`, `[ path, remote_ip, success, status, created_at ]`, `[ path, remote_ip, success, action_type, username ]`, `[ path, remote_ip, success, action_type, created_at ]`, `[ path, remote_ip, success, username, created_at ]`, `[ path, remote_ip, status, action_type, username ]`, `[ path, remote_ip, status, action_type, created_at ]`, `[ path, remote_ip, status, username, created_at ]`, `[ path, remote_ip, action_type, username, created_at ]`, `[ path, success, status, action_type, username ]`, `[ path, success, status, action_type, created_at ]`, `[ path, success, status, username, created_at ]`, `[ path, success, action_type, username, created_at ]`, `[ path, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type ]`, `[ true_path, remote_ip, success, status, username ]`, `[ true_path, remote_ip, success, status, created_at ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, created_at ]`, `[ true_path, remote_ip, success, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username ]`, `[ true_path, remote_ip, status, action_type, created_at ]`, `[ true_path, remote_ip, status, username, created_at ]`, `[ true_path, remote_ip, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username ]`, `[ true_path, success, status, action_type, created_at ]`, `[ true_path, success, status, username, created_at ]`, `[ true_path, success, action_type, username, created_at ]`, `[ true_path, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username ]`, `[ remote_ip, success, status, action_type, created_at ]`, `[ remote_ip, success, status, username, created_at ]`, `[ remote_ip, success, action_type, username, created_at ]`, `[ remote_ip, status, action_type, username, created_at ]`, `[ success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type ]`, `[ path, true_path, remote_ip, success, status, username ]`, `[ path, true_path, remote_ip, success, status, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, created_at ]`, `[ path, true_path, remote_ip, success, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username ]`, `[ path, true_path, remote_ip, status, action_type, created_at ]`, `[ path, true_path, remote_ip, status, username, created_at ]`, `[ path, true_path, remote_ip, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username ]`, `[ path, true_path, success, status, action_type, created_at ]`, `[ path, true_path, success, status, username, created_at ]`, `[ path, true_path, success, action_type, username, created_at ]`, `[ path, true_path, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username ]`, `[ path, remote_ip, success, status, action_type, created_at ]`, `[ path, remote_ip, success, status, username, created_at ]`, `[ path, remote_ip, success, action_type, username, created_at ]`, `[ path, remote_ip, status, action_type, username, created_at ]`, `[ path, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username ]`, `[ true_path, remote_ip, success, status, action_type, created_at ]`, `[ true_path, remote_ip, success, status, username, created_at ]`, `[ true_path, remote_ip, success, action_type, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type, username ]`, `[ path, true_path, remote_ip, success, status, action_type, created_at ]`, `[ path, true_path, remote_ip, success, status, username, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username, created_at ]` or `[ path, true_path, remote_ip, success, status, action_type, username, created_at ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`, `true_path`, `action_type` or `username`. Valid field combinations are `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ status ]`, `[ action_type ]`, `[ username ]`, `[ created_at ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, status ]`, `[ path, action_type ]`, `[ path, username ]`, `[ path, created_at ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, status ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ true_path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, status ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ remote_ip, created_at ]`, `[ success, status ]`, `[ success, action_type ]`, `[ success, username ]`, `[ success, created_at ]`, `[ status, action_type ]`, `[ status, username ]`, `[ status, created_at ]`, `[ action_type, username ]`, `[ action_type, created_at ]`, `[ username, created_at ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, status ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, true_path, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, status ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, remote_ip, created_at ]`, `[ path, success, status ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, success, created_at ]`, `[ path, status, action_type ]`, `[ path, status, username ]`, `[ path, status, created_at ]`, `[ path, action_type, username ]`, `[ path, action_type, created_at ]`, `[ path, username, created_at ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, status ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, remote_ip, created_at ]`, `[ true_path, success, status ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, success, created_at ]`, `[ true_path, status, action_type ]`, `[ true_path, status, username ]`, `[ true_path, status, created_at ]`, `[ true_path, action_type, username ]`, `[ true_path, action_type, created_at ]`, `[ true_path, username, created_at ]`, `[ remote_ip, success, status ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, success, created_at ]`, `[ remote_ip, status, action_type ]`, `[ remote_ip, status, username ]`, `[ remote_ip, status, created_at ]`, `[ remote_ip, action_type, username ]`, `[ remote_ip, action_type, created_at ]`, `[ remote_ip, username, created_at ]`, `[ success, status, action_type ]`, `[ success, status, username ]`, `[ success, status, created_at ]`, `[ success, action_type, username ]`, `[ success, action_type, created_at ]`, `[ success, username, created_at ]`, `[ status, action_type, username ]`, `[ status, action_type, created_at ]`, `[ status, username, created_at ]`, `[ action_type, username, created_at ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, status ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, remote_ip, created_at ]`, `[ path, true_path, success, status ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, success, created_at ]`, `[ path, true_path, status, action_type ]`, `[ path, true_path, status, username ]`, `[ path, true_path, status, created_at ]`, `[ path, true_path, action_type, username ]`, `[ path, true_path, action_type, created_at ]`, `[ path, true_path, username, created_at ]`, `[ path, remote_ip, success, status ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, success, created_at ]`, `[ path, remote_ip, status, action_type ]`, `[ path, remote_ip, status, username ]`, `[ path, remote_ip, status, created_at ]`, `[ path, remote_ip, action_type, username ]`, `[ path, remote_ip, action_type, created_at ]`, `[ path, remote_ip, username, created_at ]`, `[ path, success, status, action_type ]`, `[ path, success, status, username ]`, `[ path, success, status, created_at ]`, `[ path, success, action_type, username ]`, `[ path, success, action_type, created_at ]`, `[ path, success, username, created_at ]`, `[ path, status, action_type, username ]`, `[ path, status, action_type, created_at ]`, `[ path, status, username, created_at ]`, `[ path, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, success, created_at ]`, `[ true_path, remote_ip, status, action_type ]`, `[ true_path, remote_ip, status, username ]`, `[ true_path, remote_ip, status, created_at ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, remote_ip, action_type, created_at ]`, `[ true_path, remote_ip, username, created_at ]`, `[ true_path, success, status, action_type ]`, `[ true_path, success, status, username ]`, `[ true_path, success, status, created_at ]`, `[ true_path, success, action_type, username ]`, `[ true_path, success, action_type, created_at ]`, `[ true_path, success, username, created_at ]`, `[ true_path, status, action_type, username ]`, `[ true_path, status, action_type, created_at ]`, `[ true_path, status, username, created_at ]`, `[ true_path, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type ]`, `[ remote_ip, success, status, username ]`, `[ remote_ip, success, status, created_at ]`, `[ remote_ip, success, action_type, username ]`, `[ remote_ip, success, action_type, created_at ]`, `[ remote_ip, success, username, created_at ]`, `[ remote_ip, status, action_type, username ]`, `[ remote_ip, status, action_type, created_at ]`, `[ remote_ip, status, username, created_at ]`, `[ remote_ip, action_type, username, created_at ]`, `[ success, status, action_type, username ]`, `[ success, status, action_type, created_at ]`, `[ success, status, username, created_at ]`, `[ success, action_type, username, created_at ]`, `[ status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, success, created_at ]`, `[ path, true_path, remote_ip, status, action_type ]`, `[ path, true_path, remote_ip, status, username ]`, `[ path, true_path, remote_ip, status, created_at ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, remote_ip, action_type, created_at ]`, `[ path, true_path, remote_ip, username, created_at ]`, `[ path, true_path, success, status, action_type ]`, `[ path, true_path, success, status, username ]`, `[ path, true_path, success, status, created_at ]`, `[ path, true_path, success, action_type, username ]`, `[ path, true_path, success, action_type, created_at ]`, `[ path, true_path, success, username, created_at ]`, `[ path, true_path, status, action_type, username ]`, `[ path, true_path, status, action_type, created_at ]`, `[ path, true_path, status, username, created_at ]`, `[ path, true_path, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type ]`, `[ path, remote_ip, success, status, username ]`, `[ path, remote_ip, success, status, created_at ]`, `[ path, remote_ip, success, action_type, username ]`, `[ path, remote_ip, success, action_type, created_at ]`, `[ path, remote_ip, success, username, created_at ]`, `[ path, remote_ip, status, action_type, username ]`, `[ path, remote_ip, status, action_type, created_at ]`, `[ path, remote_ip, status, username, created_at ]`, `[ path, remote_ip, action_type, username, created_at ]`, `[ path, success, status, action_type, username ]`, `[ path, success, status, action_type, created_at ]`, `[ path, success, status, username, created_at ]`, `[ path, success, action_type, username, created_at ]`, `[ path, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type ]`, `[ true_path, remote_ip, success, status, username ]`, `[ true_path, remote_ip, success, status, created_at ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, created_at ]`, `[ true_path, remote_ip, success, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username ]`, `[ true_path, remote_ip, status, action_type, created_at ]`, `[ true_path, remote_ip, status, username, created_at ]`, `[ true_path, remote_ip, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username ]`, `[ true_path, success, status, action_type, created_at ]`, `[ true_path, success, status, username, created_at ]`, `[ true_path, success, action_type, username, created_at ]`, `[ true_path, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username ]`, `[ remote_ip, success, status, action_type, created_at ]`, `[ remote_ip, success, status, username, created_at ]`, `[ remote_ip, success, action_type, username, created_at ]`, `[ remote_ip, status, action_type, username, created_at ]`, `[ success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type ]`, `[ path, true_path, remote_ip, success, status, username ]`, `[ path, true_path, remote_ip, success, status, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, created_at ]`, `[ path, true_path, remote_ip, success, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username ]`, `[ path, true_path, remote_ip, status, action_type, created_at ]`, `[ path, true_path, remote_ip, status, username, created_at ]`, `[ path, true_path, remote_ip, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username ]`, `[ path, true_path, success, status, action_type, created_at ]`, `[ path, true_path, success, status, username, created_at ]`, `[ path, true_path, success, action_type, username, created_at ]`, `[ path, true_path, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username ]`, `[ path, remote_ip, success, status, action_type, created_at ]`, `[ path, remote_ip, success, status, username, created_at ]`, `[ path, remote_ip, success, action_type, username, created_at ]`, `[ path, remote_ip, status, action_type, username, created_at ]`, `[ path, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username ]`, `[ true_path, remote_ip, success, status, action_type, created_at ]`, `[ true_path, remote_ip, success, status, username, created_at ]`, `[ true_path, remote_ip, success, action_type, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type, username ]`, `[ path, true_path, remote_ip, success, status, action_type, created_at ]`, `[ path, true_path, remote_ip, success, status, username, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username, created_at ]` or `[ path, true_path, remote_ip, success, status, action_type, username, created_at ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ status ]`, `[ action_type ]`, `[ username ]`, `[ created_at ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, status ]`, `[ path, action_type ]`, `[ path, username ]`, `[ path, created_at ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, status ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ true_path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, status ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ remote_ip, created_at ]`, `[ success, status ]`, `[ success, action_type ]`, `[ success, username ]`, `[ success, created_at ]`, `[ status, action_type ]`, `[ status, username ]`, `[ status, created_at ]`, `[ action_type, username ]`, `[ action_type, created_at ]`, `[ username, created_at ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, status ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, true_path, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, status ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, remote_ip, created_at ]`, `[ path, success, status ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, success, created_at ]`, `[ path, status, action_type ]`, `[ path, status, username ]`, `[ path, status, created_at ]`, `[ path, action_type, username ]`, `[ path, action_type, created_at ]`, `[ path, username, created_at ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, status ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, remote_ip, created_at ]`, `[ true_path, success, status ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, success, created_at ]`, `[ true_path, status, action_type ]`, `[ true_path, status, username ]`, `[ true_path, status, created_at ]`, `[ true_path, action_type, username ]`, `[ true_path, action_type, created_at ]`, `[ true_path, username, created_at ]`, `[ remote_ip, success, status ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, success, created_at ]`, `[ remote_ip, status, action_type ]`, `[ remote_ip, status, username ]`, `[ remote_ip, status, created_at ]`, `[ remote_ip, action_type, username ]`, `[ remote_ip, action_type, created_at ]`, `[ remote_ip, username, created_at ]`, `[ success, status, action_type ]`, `[ success, status, username ]`, `[ success, status, created_at ]`, `[ success, action_type, username ]`, `[ success, action_type, created_at ]`, `[ success, username, created_at ]`, `[ status, action_type, username ]`, `[ status, action_type, created_at ]`, `[ status, username, created_at ]`, `[ action_type, username, created_at ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, status ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, remote_ip, created_at ]`, `[ path, true_path, success, status ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, success, created_at ]`, `[ path, true_path, status, action_type ]`, `[ path, true_path, status, username ]`, `[ path, true_path, status, created_at ]`, `[ path, true_path, action_type, username ]`, `[ path, true_path, action_type, created_at ]`, `[ path, true_path, username, created_at ]`, `[ path, remote_ip, success, status ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, success, created_at ]`, `[ path, remote_ip, status, action_type ]`, `[ path, remote_ip, status, username ]`, `[ path, remote_ip, status, created_at ]`, `[ path, remote_ip, action_type, username ]`, `[ path, remote_ip, action_type, created_at ]`, `[ path, remote_ip, username, created_at ]`, `[ path, success, status, action_type ]`, `[ path, success, status, username ]`, `[ path, success, status, created_at ]`, `[ path, success, action_type, username ]`, `[ path, success, action_type, created_at ]`, `[ path, success, username, created_at ]`, `[ path, status, action_type, username ]`, `[ path, status, action_type, created_at ]`, `[ path, status, username, created_at ]`, `[ path, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, success, created_at ]`, `[ true_path, remote_ip, status, action_type ]`, `[ true_path, remote_ip, status, username ]`, `[ true_path, remote_ip, status, created_at ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, remote_ip, action_type, created_at ]`, `[ true_path, remote_ip, username, created_at ]`, `[ true_path, success, status, action_type ]`, `[ true_path, success, status, username ]`, `[ true_path, success, status, created_at ]`, `[ true_path, success, action_type, username ]`, `[ true_path, success, action_type, created_at ]`, `[ true_path, success, username, created_at ]`, `[ true_path, status, action_type, username ]`, `[ true_path, status, action_type, created_at ]`, `[ true_path, status, username, created_at ]`, `[ true_path, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type ]`, `[ remote_ip, success, status, username ]`, `[ remote_ip, success, status, created_at ]`, `[ remote_ip, success, action_type, username ]`, `[ remote_ip, success, action_type, created_at ]`, `[ remote_ip, success, username, created_at ]`, `[ remote_ip, status, action_type, username ]`, `[ remote_ip, status, action_type, created_at ]`, `[ remote_ip, status, username, created_at ]`, `[ remote_ip, action_type, username, created_at ]`, `[ success, status, action_type, username ]`, `[ success, status, action_type, created_at ]`, `[ success, status, username, created_at ]`, `[ success, action_type, username, created_at ]`, `[ status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, success, created_at ]`, `[ path, true_path, remote_ip, status, action_type ]`, `[ path, true_path, remote_ip, status, username ]`, `[ path, true_path, remote_ip, status, created_at ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, remote_ip, action_type, created_at ]`, `[ path, true_path, remote_ip, username, created_at ]`, `[ path, true_path, success, status, action_type ]`, `[ path, true_path, success, status, username ]`, `[ path, true_path, success, status, created_at ]`, `[ path, true_path, success, action_type, username ]`, `[ path, true_path, success, action_type, created_at ]`, `[ path, true_path, success, username, created_at ]`, `[ path, true_path, status, action_type, username ]`, `[ path, true_path, status, action_type, created_at ]`, `[ path, true_path, status, username, created_at ]`, `[ path, true_path, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type ]`, `[ path, remote_ip, success, status, username ]`, `[ path, remote_ip, success, status, created_at ]`, `[ path, remote_ip, success, action_type, username ]`, `[ path, remote_ip, success, action_type, created_at ]`, `[ path, remote_ip, success, username, created_at ]`, `[ path, remote_ip, status, action_type, username ]`, `[ path, remote_ip, status, action_type, created_at ]`, `[ path, remote_ip, status, username, created_at ]`, `[ path, remote_ip, action_type, username, created_at ]`, `[ path, success, status, action_type, username ]`, `[ path, success, status, action_type, created_at ]`, `[ path, success, status, username, created_at ]`, `[ path, success, action_type, username, created_at ]`, `[ path, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type ]`, `[ true_path, remote_ip, success, status, username ]`, `[ true_path, remote_ip, success, status, created_at ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, created_at ]`, `[ true_path, remote_ip, success, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username ]`, `[ true_path, remote_ip, status, action_type, created_at ]`, `[ true_path, remote_ip, status, username, created_at ]`, `[ true_path, remote_ip, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username ]`, `[ true_path, success, status, action_type, created_at ]`, `[ true_path, success, status, username, created_at ]`, `[ true_path, success, action_type, username, created_at ]`, `[ true_path, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username ]`, `[ remote_ip, success, status, action_type, created_at ]`, `[ remote_ip, success, status, username, created_at ]`, `[ remote_ip, success, action_type, username, created_at ]`, `[ remote_ip, status, action_type, username, created_at ]`, `[ success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type ]`, `[ path, true_path, remote_ip, success, status, username ]`, `[ path, true_path, remote_ip, success, status, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, created_at ]`, `[ path, true_path, remote_ip, success, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username ]`, `[ path, true_path, remote_ip, status, action_type, created_at ]`, `[ path, true_path, remote_ip, status, username, created_at ]`, `[ path, true_path, remote_ip, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username ]`, `[ path, true_path, success, status, action_type, created_at ]`, `[ path, true_path, success, status, username, created_at ]`, `[ path, true_path, success, action_type, username, created_at ]`, `[ path, true_path, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username ]`, `[ path, remote_ip, success, status, action_type, created_at ]`, `[ path, remote_ip, success, status, username, created_at ]`, `[ path, remote_ip, success, action_type, username, created_at ]`, `[ path, remote_ip, status, action_type, username, created_at ]`, `[ path, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username ]`, `[ true_path, remote_ip, success, status, action_type, created_at ]`, `[ true_path, remote_ip, success, status, username, created_at ]`, `[ true_path, remote_ip, success, action_type, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type, username ]`, `[ path, true_path, remote_ip, success, status, action_type, created_at ]`, `[ path, true_path, remote_ip, success, status, username, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username, created_at ]` or `[ path, true_path, remote_ip, success, status, action_type, username, created_at ]`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ path ]`, `[ true_path ]`, `[ remote_ip ]`, `[ success ]`, `[ status ]`, `[ action_type ]`, `[ username ]`, `[ created_at ]`, `[ path, true_path ]`, `[ path, remote_ip ]`, `[ path, success ]`, `[ path, status ]`, `[ path, action_type ]`, `[ path, username ]`, `[ path, created_at ]`, `[ true_path, remote_ip ]`, `[ true_path, success ]`, `[ true_path, status ]`, `[ true_path, action_type ]`, `[ true_path, username ]`, `[ true_path, created_at ]`, `[ remote_ip, success ]`, `[ remote_ip, status ]`, `[ remote_ip, action_type ]`, `[ remote_ip, username ]`, `[ remote_ip, created_at ]`, `[ success, status ]`, `[ success, action_type ]`, `[ success, username ]`, `[ success, created_at ]`, `[ status, action_type ]`, `[ status, username ]`, `[ status, created_at ]`, `[ action_type, username ]`, `[ action_type, created_at ]`, `[ username, created_at ]`, `[ path, true_path, remote_ip ]`, `[ path, true_path, success ]`, `[ path, true_path, status ]`, `[ path, true_path, action_type ]`, `[ path, true_path, username ]`, `[ path, true_path, created_at ]`, `[ path, remote_ip, success ]`, `[ path, remote_ip, status ]`, `[ path, remote_ip, action_type ]`, `[ path, remote_ip, username ]`, `[ path, remote_ip, created_at ]`, `[ path, success, status ]`, `[ path, success, action_type ]`, `[ path, success, username ]`, `[ path, success, created_at ]`, `[ path, status, action_type ]`, `[ path, status, username ]`, `[ path, status, created_at ]`, `[ path, action_type, username ]`, `[ path, action_type, created_at ]`, `[ path, username, created_at ]`, `[ true_path, remote_ip, success ]`, `[ true_path, remote_ip, status ]`, `[ true_path, remote_ip, action_type ]`, `[ true_path, remote_ip, username ]`, `[ true_path, remote_ip, created_at ]`, `[ true_path, success, status ]`, `[ true_path, success, action_type ]`, `[ true_path, success, username ]`, `[ true_path, success, created_at ]`, `[ true_path, status, action_type ]`, `[ true_path, status, username ]`, `[ true_path, status, created_at ]`, `[ true_path, action_type, username ]`, `[ true_path, action_type, created_at ]`, `[ true_path, username, created_at ]`, `[ remote_ip, success, status ]`, `[ remote_ip, success, action_type ]`, `[ remote_ip, success, username ]`, `[ remote_ip, success, created_at ]`, `[ remote_ip, status, action_type ]`, `[ remote_ip, status, username ]`, `[ remote_ip, status, created_at ]`, `[ remote_ip, action_type, username ]`, `[ remote_ip, action_type, created_at ]`, `[ remote_ip, username, created_at ]`, `[ success, status, action_type ]`, `[ success, status, username ]`, `[ success, status, created_at ]`, `[ success, action_type, username ]`, `[ success, action_type, created_at ]`, `[ success, username, created_at ]`, `[ status, action_type, username ]`, `[ status, action_type, created_at ]`, `[ status, username, created_at ]`, `[ action_type, username, created_at ]`, `[ path, true_path, remote_ip, success ]`, `[ path, true_path, remote_ip, status ]`, `[ path, true_path, remote_ip, action_type ]`, `[ path, true_path, remote_ip, username ]`, `[ path, true_path, remote_ip, created_at ]`, `[ path, true_path, success, status ]`, `[ path, true_path, success, action_type ]`, `[ path, true_path, success, username ]`, `[ path, true_path, success, created_at ]`, `[ path, true_path, status, action_type ]`, `[ path, true_path, status, username ]`, `[ path, true_path, status, created_at ]`, `[ path, true_path, action_type, username ]`, `[ path, true_path, action_type, created_at ]`, `[ path, true_path, username, created_at ]`, `[ path, remote_ip, success, status ]`, `[ path, remote_ip, success, action_type ]`, `[ path, remote_ip, success, username ]`, `[ path, remote_ip, success, created_at ]`, `[ path, remote_ip, status, action_type ]`, `[ path, remote_ip, status, username ]`, `[ path, remote_ip, status, created_at ]`, `[ path, remote_ip, action_type, username ]`, `[ path, remote_ip, action_type, created_at ]`, `[ path, remote_ip, username, created_at ]`, `[ path, success, status, action_type ]`, `[ path, success, status, username ]`, `[ path, success, status, created_at ]`, `[ path, success, action_type, username ]`, `[ path, success, action_type, created_at ]`, `[ path, success, username, created_at ]`, `[ path, status, action_type, username ]`, `[ path, status, action_type, created_at ]`, `[ path, status, username, created_at ]`, `[ path, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status ]`, `[ true_path, remote_ip, success, action_type ]`, `[ true_path, remote_ip, success, username ]`, `[ true_path, remote_ip, success, created_at ]`, `[ true_path, remote_ip, status, action_type ]`, `[ true_path, remote_ip, status, username ]`, `[ true_path, remote_ip, status, created_at ]`, `[ true_path, remote_ip, action_type, username ]`, `[ true_path, remote_ip, action_type, created_at ]`, `[ true_path, remote_ip, username, created_at ]`, `[ true_path, success, status, action_type ]`, `[ true_path, success, status, username ]`, `[ true_path, success, status, created_at ]`, `[ true_path, success, action_type, username ]`, `[ true_path, success, action_type, created_at ]`, `[ true_path, success, username, created_at ]`, `[ true_path, status, action_type, username ]`, `[ true_path, status, action_type, created_at ]`, `[ true_path, status, username, created_at ]`, `[ true_path, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type ]`, `[ remote_ip, success, status, username ]`, `[ remote_ip, success, status, created_at ]`, `[ remote_ip, success, action_type, username ]`, `[ remote_ip, success, action_type, created_at ]`, `[ remote_ip, success, username, created_at ]`, `[ remote_ip, status, action_type, username ]`, `[ remote_ip, status, action_type, created_at ]`, `[ remote_ip, status, username, created_at ]`, `[ remote_ip, action_type, username, created_at ]`, `[ success, status, action_type, username ]`, `[ success, status, action_type, created_at ]`, `[ success, status, username, created_at ]`, `[ success, action_type, username, created_at ]`, `[ status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status ]`, `[ path, true_path, remote_ip, success, action_type ]`, `[ path, true_path, remote_ip, success, username ]`, `[ path, true_path, remote_ip, success, created_at ]`, `[ path, true_path, remote_ip, status, action_type ]`, `[ path, true_path, remote_ip, status, username ]`, `[ path, true_path, remote_ip, status, created_at ]`, `[ path, true_path, remote_ip, action_type, username ]`, `[ path, true_path, remote_ip, action_type, created_at ]`, `[ path, true_path, remote_ip, username, created_at ]`, `[ path, true_path, success, status, action_type ]`, `[ path, true_path, success, status, username ]`, `[ path, true_path, success, status, created_at ]`, `[ path, true_path, success, action_type, username ]`, `[ path, true_path, success, action_type, created_at ]`, `[ path, true_path, success, username, created_at ]`, `[ path, true_path, status, action_type, username ]`, `[ path, true_path, status, action_type, created_at ]`, `[ path, true_path, status, username, created_at ]`, `[ path, true_path, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type ]`, `[ path, remote_ip, success, status, username ]`, `[ path, remote_ip, success, status, created_at ]`, `[ path, remote_ip, success, action_type, username ]`, `[ path, remote_ip, success, action_type, created_at ]`, `[ path, remote_ip, success, username, created_at ]`, `[ path, remote_ip, status, action_type, username ]`, `[ path, remote_ip, status, action_type, created_at ]`, `[ path, remote_ip, status, username, created_at ]`, `[ path, remote_ip, action_type, username, created_at ]`, `[ path, success, status, action_type, username ]`, `[ path, success, status, action_type, created_at ]`, `[ path, success, status, username, created_at ]`, `[ path, success, action_type, username, created_at ]`, `[ path, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type ]`, `[ true_path, remote_ip, success, status, username ]`, `[ true_path, remote_ip, success, status, created_at ]`, `[ true_path, remote_ip, success, action_type, username ]`, `[ true_path, remote_ip, success, action_type, created_at ]`, `[ true_path, remote_ip, success, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username ]`, `[ true_path, remote_ip, status, action_type, created_at ]`, `[ true_path, remote_ip, status, username, created_at ]`, `[ true_path, remote_ip, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username ]`, `[ true_path, success, status, action_type, created_at ]`, `[ true_path, success, status, username, created_at ]`, `[ true_path, success, action_type, username, created_at ]`, `[ true_path, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username ]`, `[ remote_ip, success, status, action_type, created_at ]`, `[ remote_ip, success, status, username, created_at ]`, `[ remote_ip, success, action_type, username, created_at ]`, `[ remote_ip, status, action_type, username, created_at ]`, `[ success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type ]`, `[ path, true_path, remote_ip, success, status, username ]`, `[ path, true_path, remote_ip, success, status, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username ]`, `[ path, true_path, remote_ip, success, action_type, created_at ]`, `[ path, true_path, remote_ip, success, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username ]`, `[ path, true_path, remote_ip, status, action_type, created_at ]`, `[ path, true_path, remote_ip, status, username, created_at ]`, `[ path, true_path, remote_ip, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username ]`, `[ path, true_path, success, status, action_type, created_at ]`, `[ path, true_path, success, status, username, created_at ]`, `[ path, true_path, success, action_type, username, created_at ]`, `[ path, true_path, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username ]`, `[ path, remote_ip, success, status, action_type, created_at ]`, `[ path, remote_ip, success, status, username, created_at ]`, `[ path, remote_ip, success, action_type, username, created_at ]`, `[ path, remote_ip, status, action_type, username, created_at ]`, `[ path, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username ]`, `[ true_path, remote_ip, success, status, action_type, created_at ]`, `[ true_path, remote_ip, success, status, username, created_at ]`, `[ true_path, remote_ip, success, action_type, username, created_at ]`, `[ true_path, remote_ip, status, action_type, username, created_at ]`, `[ true_path, success, status, action_type, username, created_at ]`, `[ remote_ip, success, status, action_type, username, created_at ]`, `[ path, true_path, remote_ip, success, status, action_type, username ]`, `[ path, true_path, remote_ip, success, status, action_type, created_at ]`, `[ path, true_path, remote_ip, success, status, username, created_at ]`, `[ path, true_path, remote_ip, success, action_type, username, created_at ]`, `[ path, true_path, remote_ip, status, action_type, username, created_at ]`, `[ path, true_path, success, status, action_type, username, created_at ]`, `[ path, remote_ip, success, status, action_type, username, created_at ]`, `[ true_path, remote_ip, success, status, action_type, username, created_at ]` or `[ path, true_path, remote_ip, success, status, action_type, username, created_at ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code WebDavActionLog} records, in a {@code ListIterator}
   */
  public static ListIterator<WebDavActionLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().webDavActionLogs().list(parameters, options);
  }

  /**
   * List WebDAV Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<WebDavActionLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List WebDAV Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<WebDavActionLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
