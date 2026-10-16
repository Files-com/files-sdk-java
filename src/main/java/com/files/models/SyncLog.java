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
public class SyncLog implements ModelInterface {
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

  // One mapper for every SyncLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public SyncLog() {
    this(null, null);
  }

  public SyncLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SyncLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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
   * Sync ID
   */
  @JsonProperty("sync_id")
  public Long syncId;

  public Long getSyncId() {
    return syncId;
  }

  /**
   * External Event ID used by legacy syncs
   */
  @JsonProperty("external_event_id")
  public Long externalEventId;

  public Long getExternalEventId() {
    return externalEventId;
  }

  /**
   * Sync Run ID
   */
  @JsonProperty("sync_run_id")
  public Long syncRunId;

  public Long getSyncRunId() {
    return syncRunId;
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
   * Message
   */
  @JsonProperty("message")
  public String message;

  public String getMessage() {
    return message;
  }

  /**
   * Operation type
   */
  @JsonProperty("operation")
  public String operation;

  public String getOperation() {
    return operation;
  }

  /**
   * File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
   * File size
   */
  @JsonProperty("size")
  public Long size;

  public Long getSize() {
    return size;
  }

  /**
   * File type
   */
  @JsonProperty("file_type")
  public String fileType;

  public String getFileType() {
    return fileType;
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
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `external_event_id`, `operation`, `status`, `sync_id`, `created_at` or `sync_run_id`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ sync_run_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ external_event_id, sync_run_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ operation, sync_run_id ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ status, sync_run_id ]`, `[ sync_id, created_at ]`, `[ sync_id, sync_run_id ]`, `[ created_at, sync_run_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, operation, sync_run_id ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, status, sync_run_id ]`, `[ external_event_id, sync_id, created_at ]`, `[ external_event_id, sync_id, sync_run_id ]`, `[ external_event_id, created_at, sync_run_id ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, status, sync_run_id ]`, `[ operation, sync_id, created_at ]`, `[ operation, sync_id, sync_run_id ]`, `[ operation, created_at, sync_run_id ]`, `[ status, sync_id, created_at ]`, `[ status, sync_id, sync_run_id ]`, `[ status, created_at, sync_run_id ]`, `[ sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, status, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, operation, sync_id, sync_run_id ]`, `[ external_event_id, operation, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ external_event_id, status, sync_id, sync_run_id ]`, `[ external_event_id, status, created_at, sync_run_id ]`, `[ external_event_id, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at ]`, `[ operation, status, sync_id, sync_run_id ]`, `[ operation, status, created_at, sync_run_id ]`, `[ operation, sync_id, created_at, sync_run_id ]`, `[ status, sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id, sync_run_id ]`, `[ external_event_id, operation, status, created_at, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at, sync_run_id ]` or `[ external_event_id, operation, status, sync_id, created_at, sync_run_id ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ sync_run_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ external_event_id, sync_run_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ operation, sync_run_id ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ status, sync_run_id ]`, `[ sync_id, created_at ]`, `[ sync_id, sync_run_id ]`, `[ created_at, sync_run_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, operation, sync_run_id ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, status, sync_run_id ]`, `[ external_event_id, sync_id, created_at ]`, `[ external_event_id, sync_id, sync_run_id ]`, `[ external_event_id, created_at, sync_run_id ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, status, sync_run_id ]`, `[ operation, sync_id, created_at ]`, `[ operation, sync_id, sync_run_id ]`, `[ operation, created_at, sync_run_id ]`, `[ status, sync_id, created_at ]`, `[ status, sync_id, sync_run_id ]`, `[ status, created_at, sync_run_id ]`, `[ sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, status, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, operation, sync_id, sync_run_id ]`, `[ external_event_id, operation, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ external_event_id, status, sync_id, sync_run_id ]`, `[ external_event_id, status, created_at, sync_run_id ]`, `[ external_event_id, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at ]`, `[ operation, status, sync_id, sync_run_id ]`, `[ operation, status, created_at, sync_run_id ]`, `[ operation, sync_id, created_at, sync_run_id ]`, `[ status, sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id, sync_run_id ]`, `[ external_event_id, operation, status, created_at, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at, sync_run_id ]` or `[ external_event_id, operation, status, sync_id, created_at, sync_run_id ]`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ sync_run_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ external_event_id, sync_run_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ operation, sync_run_id ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ status, sync_run_id ]`, `[ sync_id, created_at ]`, `[ sync_id, sync_run_id ]`, `[ created_at, sync_run_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, operation, sync_run_id ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, status, sync_run_id ]`, `[ external_event_id, sync_id, created_at ]`, `[ external_event_id, sync_id, sync_run_id ]`, `[ external_event_id, created_at, sync_run_id ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, status, sync_run_id ]`, `[ operation, sync_id, created_at ]`, `[ operation, sync_id, sync_run_id ]`, `[ operation, created_at, sync_run_id ]`, `[ status, sync_id, created_at ]`, `[ status, sync_id, sync_run_id ]`, `[ status, created_at, sync_run_id ]`, `[ sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, status, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, operation, sync_id, sync_run_id ]`, `[ external_event_id, operation, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ external_event_id, status, sync_id, sync_run_id ]`, `[ external_event_id, status, created_at, sync_run_id ]`, `[ external_event_id, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at ]`, `[ operation, status, sync_id, sync_run_id ]`, `[ operation, status, created_at, sync_run_id ]`, `[ operation, sync_id, created_at, sync_run_id ]`, `[ status, sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id, sync_run_id ]`, `[ external_event_id, operation, status, created_at, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at, sync_run_id ]` or `[ external_event_id, operation, status, sync_id, created_at, sync_run_id ]`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ sync_run_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ external_event_id, sync_run_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ operation, sync_run_id ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ status, sync_run_id ]`, `[ sync_id, created_at ]`, `[ sync_id, sync_run_id ]`, `[ created_at, sync_run_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, operation, sync_run_id ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, status, sync_run_id ]`, `[ external_event_id, sync_id, created_at ]`, `[ external_event_id, sync_id, sync_run_id ]`, `[ external_event_id, created_at, sync_run_id ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, status, sync_run_id ]`, `[ operation, sync_id, created_at ]`, `[ operation, sync_id, sync_run_id ]`, `[ operation, created_at, sync_run_id ]`, `[ status, sync_id, created_at ]`, `[ status, sync_id, sync_run_id ]`, `[ status, created_at, sync_run_id ]`, `[ sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, status, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, operation, sync_id, sync_run_id ]`, `[ external_event_id, operation, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ external_event_id, status, sync_id, sync_run_id ]`, `[ external_event_id, status, created_at, sync_run_id ]`, `[ external_event_id, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at ]`, `[ operation, status, sync_id, sync_run_id ]`, `[ operation, status, created_at, sync_run_id ]`, `[ operation, sync_id, created_at, sync_run_id ]`, `[ status, sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id, sync_run_id ]`, `[ external_event_id, operation, status, created_at, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at, sync_run_id ]` or `[ external_event_id, operation, status, sync_id, created_at, sync_run_id ]`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ external_event_id ]`, `[ operation ]`, `[ status ]`, `[ sync_id ]`, `[ created_at ]`, `[ sync_run_id ]`, `[ external_event_id, operation ]`, `[ external_event_id, status ]`, `[ external_event_id, sync_id ]`, `[ external_event_id, created_at ]`, `[ external_event_id, sync_run_id ]`, `[ operation, status ]`, `[ operation, sync_id ]`, `[ operation, created_at ]`, `[ operation, sync_run_id ]`, `[ status, sync_id ]`, `[ status, created_at ]`, `[ status, sync_run_id ]`, `[ sync_id, created_at ]`, `[ sync_id, sync_run_id ]`, `[ created_at, sync_run_id ]`, `[ external_event_id, operation, status ]`, `[ external_event_id, operation, sync_id ]`, `[ external_event_id, operation, created_at ]`, `[ external_event_id, operation, sync_run_id ]`, `[ external_event_id, status, sync_id ]`, `[ external_event_id, status, created_at ]`, `[ external_event_id, status, sync_run_id ]`, `[ external_event_id, sync_id, created_at ]`, `[ external_event_id, sync_id, sync_run_id ]`, `[ external_event_id, created_at, sync_run_id ]`, `[ operation, status, sync_id ]`, `[ operation, status, created_at ]`, `[ operation, status, sync_run_id ]`, `[ operation, sync_id, created_at ]`, `[ operation, sync_id, sync_run_id ]`, `[ operation, created_at, sync_run_id ]`, `[ status, sync_id, created_at ]`, `[ status, sync_id, sync_run_id ]`, `[ status, created_at, sync_run_id ]`, `[ sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id ]`, `[ external_event_id, operation, status, created_at ]`, `[ external_event_id, operation, status, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at ]`, `[ external_event_id, operation, sync_id, sync_run_id ]`, `[ external_event_id, operation, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at ]`, `[ external_event_id, status, sync_id, sync_run_id ]`, `[ external_event_id, status, created_at, sync_run_id ]`, `[ external_event_id, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at ]`, `[ operation, status, sync_id, sync_run_id ]`, `[ operation, status, created_at, sync_run_id ]`, `[ operation, sync_id, created_at, sync_run_id ]`, `[ status, sync_id, created_at, sync_run_id ]`, `[ external_event_id, operation, status, sync_id, created_at ]`, `[ external_event_id, operation, status, sync_id, sync_run_id ]`, `[ external_event_id, operation, status, created_at, sync_run_id ]`, `[ external_event_id, operation, sync_id, created_at, sync_run_id ]`, `[ external_event_id, status, sync_id, created_at, sync_run_id ]`, `[ operation, status, sync_id, created_at, sync_run_id ]` or `[ external_event_id, operation, status, sync_id, created_at, sync_run_id ]`.
   */
  public static ListIterator<SyncLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SyncLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SyncLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncLogs().list(parameters, options);
  }

  public static ListIterator<SyncLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SyncLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
