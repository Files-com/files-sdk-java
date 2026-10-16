package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutboundConnectionLog implements ModelInterface {
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

  // One mapper for every OutboundConnectionLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public OutboundConnectionLog() {
    this(null, null);
  }

  public OutboundConnectionLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public OutboundConnectionLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * Remote Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
   * End User IP
   */
  @JsonProperty("client_ip")
  public String clientIp;

  public String getClientIp() {
    return clientIp;
  }

  /**
   * Source Remote Server ID
   */
  @JsonProperty("src_remote_server_id")
  public Long srcRemoteServerId;

  public Long getSrcRemoteServerId() {
    return srcRemoteServerId;
  }

  /**
   * Source Remote Server name
   */
  @JsonProperty("src_remote_server_name")
  public String srcRemoteServerName;

  public String getSrcRemoteServerName() {
    return srcRemoteServerName;
  }

  /**
   * Destination Remote Server ID
   */
  @JsonProperty("dest_remote_server_id")
  public Long destRemoteServerId;

  public Long getDestRemoteServerId() {
    return destRemoteServerId;
  }

  /**
   * Destination Remote Server name
   */
  @JsonProperty("dest_remote_server_name")
  public String destRemoteServerName;

  public String getDestRemoteServerName() {
    return destRemoteServerName;
  }

  /**
   * Operation Type
   */
  @JsonProperty("operation")
  public String operation;

  public String getOperation() {
    return operation;
  }

  /**
   * Error message, if applicable
   */
  @JsonProperty("error_message")
  public String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Error operation, if applicable
   */
  @JsonProperty("error_operation")
  public String errorOperation;

  public String getErrorOperation() {
    return errorOperation;
  }

  /**
   * Error type, if applicable
   */
  @JsonProperty("error_type")
  public String errorType;

  public String getErrorType() {
    return errorType;
  }

  /**
   * Status
   */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
   * Duration (in milliseconds)
   */
  @JsonProperty("duration_ms")
  public Long durationMs;

  public Long getDurationMs() {
    return durationMs;
  }

  /**
   * Data Length in Bytes. Present for upload actions that transfer data.
   */
  @JsonProperty("bytes_uploaded")
  public Long bytesUploaded;

  public Long getBytesUploaded() {
    return bytesUploaded;
  }

  /**
   * Data Length in Bytes. Present for download actions that transfer data.
   */
  @JsonProperty("bytes_downloaded")
  public Long bytesDownloaded;

  public Long getBytesDownloaded() {
    return bytesDownloaded;
  }

  /**
   * Number of entries returned for a folder list action.
   */
  @JsonProperty("list_count")
  public Long listCount;

  public Long getListCount() {
    return listCount;
  }

  /**
   * Start Time of Action
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }


  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `operation`, `status`, `src_remote_server_id`, `dest_remote_server_id`, `path`, `client_ip` or `created_at`. Valid field combinations are `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ created_at ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ operation, created_at ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ status, created_at ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ src_remote_server_id, created_at ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ dest_remote_server_id, created_at ]`, `[ path, client_ip ]`, `[ path, created_at ]`, `[ client_ip, created_at ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, status, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, created_at ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, created_at ]`, `[ operation, path, client_ip ]`, `[ operation, path, created_at ]`, `[ operation, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, created_at ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, created_at ]`, `[ status, path, client_ip ]`, `[ status, path, created_at ]`, `[ status, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, created_at ]`, `[ src_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, path, created_at ]`, `[ src_remote_server_id, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, created_at ]`, `[ dest_remote_server_id, client_ip, created_at ]`, `[ path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, created_at ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, created_at ]`, `[ operation, status, path, client_ip ]`, `[ operation, status, path, created_at ]`, `[ operation, status, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, created_at ]`, `[ operation, dest_remote_server_id, client_ip, created_at ]`, `[ operation, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, created_at ]`, `[ status, dest_remote_server_id, client_ip, created_at ]`, `[ status, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ src_remote_server_id, path, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, created_at ]`, `[ operation, status, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ status, src_remote_server_id, path, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]` or `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ created_at ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ operation, created_at ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ status, created_at ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ src_remote_server_id, created_at ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ dest_remote_server_id, created_at ]`, `[ path, client_ip ]`, `[ path, created_at ]`, `[ client_ip, created_at ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, status, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, created_at ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, created_at ]`, `[ operation, path, client_ip ]`, `[ operation, path, created_at ]`, `[ operation, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, created_at ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, created_at ]`, `[ status, path, client_ip ]`, `[ status, path, created_at ]`, `[ status, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, created_at ]`, `[ src_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, path, created_at ]`, `[ src_remote_server_id, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, created_at ]`, `[ dest_remote_server_id, client_ip, created_at ]`, `[ path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, created_at ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, created_at ]`, `[ operation, status, path, client_ip ]`, `[ operation, status, path, created_at ]`, `[ operation, status, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, created_at ]`, `[ operation, dest_remote_server_id, client_ip, created_at ]`, `[ operation, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, created_at ]`, `[ status, dest_remote_server_id, client_ip, created_at ]`, `[ status, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ src_remote_server_id, path, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, created_at ]`, `[ operation, status, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ status, src_remote_server_id, path, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]` or `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ created_at ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ operation, created_at ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ status, created_at ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ src_remote_server_id, created_at ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ dest_remote_server_id, created_at ]`, `[ path, client_ip ]`, `[ path, created_at ]`, `[ client_ip, created_at ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, status, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, created_at ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, created_at ]`, `[ operation, path, client_ip ]`, `[ operation, path, created_at ]`, `[ operation, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, created_at ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, created_at ]`, `[ status, path, client_ip ]`, `[ status, path, created_at ]`, `[ status, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, created_at ]`, `[ src_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, path, created_at ]`, `[ src_remote_server_id, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, created_at ]`, `[ dest_remote_server_id, client_ip, created_at ]`, `[ path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, created_at ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, created_at ]`, `[ operation, status, path, client_ip ]`, `[ operation, status, path, created_at ]`, `[ operation, status, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, created_at ]`, `[ operation, dest_remote_server_id, client_ip, created_at ]`, `[ operation, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, created_at ]`, `[ status, dest_remote_server_id, client_ip, created_at ]`, `[ status, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ src_remote_server_id, path, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, created_at ]`, `[ operation, status, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ status, src_remote_server_id, path, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]` or `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`. Valid field combinations are `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ created_at ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ operation, created_at ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ status, created_at ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ src_remote_server_id, created_at ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ dest_remote_server_id, created_at ]`, `[ path, client_ip ]`, `[ path, created_at ]`, `[ client_ip, created_at ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, status, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, created_at ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, created_at ]`, `[ operation, path, client_ip ]`, `[ operation, path, created_at ]`, `[ operation, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, created_at ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, created_at ]`, `[ status, path, client_ip ]`, `[ status, path, created_at ]`, `[ status, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, created_at ]`, `[ src_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, path, created_at ]`, `[ src_remote_server_id, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, created_at ]`, `[ dest_remote_server_id, client_ip, created_at ]`, `[ path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, created_at ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, created_at ]`, `[ operation, status, path, client_ip ]`, `[ operation, status, path, created_at ]`, `[ operation, status, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, created_at ]`, `[ operation, dest_remote_server_id, client_ip, created_at ]`, `[ operation, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, created_at ]`, `[ status, dest_remote_server_id, client_ip, created_at ]`, `[ status, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ src_remote_server_id, path, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, created_at ]`, `[ operation, status, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ status, src_remote_server_id, path, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]` or `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ created_at ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ operation, created_at ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ status, created_at ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ src_remote_server_id, created_at ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ dest_remote_server_id, created_at ]`, `[ path, client_ip ]`, `[ path, created_at ]`, `[ client_ip, created_at ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, status, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, created_at ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, created_at ]`, `[ operation, path, client_ip ]`, `[ operation, path, created_at ]`, `[ operation, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, created_at ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, created_at ]`, `[ status, path, client_ip ]`, `[ status, path, created_at ]`, `[ status, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, created_at ]`, `[ src_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, path, created_at ]`, `[ src_remote_server_id, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, created_at ]`, `[ dest_remote_server_id, client_ip, created_at ]`, `[ path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, created_at ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, created_at ]`, `[ operation, status, path, client_ip ]`, `[ operation, status, path, created_at ]`, `[ operation, status, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, created_at ]`, `[ operation, dest_remote_server_id, client_ip, created_at ]`, `[ operation, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, created_at ]`, `[ status, dest_remote_server_id, client_ip, created_at ]`, `[ status, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ src_remote_server_id, path, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, created_at ]`, `[ operation, status, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ status, src_remote_server_id, path, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]` or `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ operation ]`, `[ status ]`, `[ src_remote_server_id ]`, `[ dest_remote_server_id ]`, `[ path ]`, `[ client_ip ]`, `[ created_at ]`, `[ operation, status ]`, `[ operation, src_remote_server_id ]`, `[ operation, dest_remote_server_id ]`, `[ operation, path ]`, `[ operation, client_ip ]`, `[ operation, created_at ]`, `[ status, src_remote_server_id ]`, `[ status, dest_remote_server_id ]`, `[ status, path ]`, `[ status, client_ip ]`, `[ status, created_at ]`, `[ src_remote_server_id, dest_remote_server_id ]`, `[ src_remote_server_id, path ]`, `[ src_remote_server_id, client_ip ]`, `[ src_remote_server_id, created_at ]`, `[ dest_remote_server_id, path ]`, `[ dest_remote_server_id, client_ip ]`, `[ dest_remote_server_id, created_at ]`, `[ path, client_ip ]`, `[ path, created_at ]`, `[ client_ip, created_at ]`, `[ operation, status, src_remote_server_id ]`, `[ operation, status, dest_remote_server_id ]`, `[ operation, status, path ]`, `[ operation, status, client_ip ]`, `[ operation, status, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id ]`, `[ operation, src_remote_server_id, path ]`, `[ operation, src_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, created_at ]`, `[ operation, dest_remote_server_id, path ]`, `[ operation, dest_remote_server_id, client_ip ]`, `[ operation, dest_remote_server_id, created_at ]`, `[ operation, path, client_ip ]`, `[ operation, path, created_at ]`, `[ operation, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id ]`, `[ status, src_remote_server_id, path ]`, `[ status, src_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, created_at ]`, `[ status, dest_remote_server_id, path ]`, `[ status, dest_remote_server_id, client_ip ]`, `[ status, dest_remote_server_id, created_at ]`, `[ status, path, client_ip ]`, `[ status, path, created_at ]`, `[ status, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, created_at ]`, `[ src_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, path, created_at ]`, `[ src_remote_server_id, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip ]`, `[ dest_remote_server_id, path, created_at ]`, `[ dest_remote_server_id, client_ip, created_at ]`, `[ path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id ]`, `[ operation, status, src_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, created_at ]`, `[ operation, status, dest_remote_server_id, path ]`, `[ operation, status, dest_remote_server_id, client_ip ]`, `[ operation, status, dest_remote_server_id, created_at ]`, `[ operation, status, path, client_ip ]`, `[ operation, status, path, created_at ]`, `[ operation, status, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, src_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip ]`, `[ operation, dest_remote_server_id, path, created_at ]`, `[ operation, dest_remote_server_id, client_ip, created_at ]`, `[ operation, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ status, src_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip ]`, `[ status, dest_remote_server_id, path, created_at ]`, `[ status, dest_remote_server_id, client_ip, created_at ]`, `[ status, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ src_remote_server_id, path, client_ip, created_at ]`, `[ dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip ]`, `[ operation, status, dest_remote_server_id, path, created_at ]`, `[ operation, status, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ status, src_remote_server_id, path, client_ip, created_at ]`, `[ status, dest_remote_server_id, path, client_ip, created_at ]`, `[ src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, path, created_at ]`, `[ operation, status, src_remote_server_id, dest_remote_server_id, client_ip, created_at ]`, `[ operation, status, src_remote_server_id, path, client_ip, created_at ]`, `[ operation, status, dest_remote_server_id, path, client_ip, created_at ]`, `[ operation, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`, `[ status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]` or `[ operation, status, src_remote_server_id, dest_remote_server_id, path, client_ip, created_at ]`.
   */
  public static ListIterator<OutboundConnectionLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<OutboundConnectionLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<OutboundConnectionLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().outboundConnectionLogs().list(parameters, options);
  }

  public static ListIterator<OutboundConnectionLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<OutboundConnectionLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
