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
public class SyncRun implements ModelInterface {
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

  // One mapper for every SyncRun, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SyncRun} with no attributes set.
   */
  public SyncRun() {
    this(null, null);
  }

  /**
   * Creates a {@code SyncRun} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SyncRun(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SyncRun} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SyncRun(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * SyncRun ID
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
   * Log or summary body for this run
   */
  @JsonProperty("body")
  public String body;

  /**
   * Gets {@link #body}.
   *
   * @return the value of {@link #body}
   */
  public String getBody() {
    return body;
  }

  /**
   * Total bytes synced in this run
   */
  @JsonProperty("bytes_synced")
  public Long bytesSynced;

  /**
   * Gets {@link #bytesSynced}.
   *
   * @return the value of {@link #bytesSynced}
   */
  public Long getBytesSynced() {
    return bytesSynced;
  }

  /**
   * Number of files compared
   */
  @JsonProperty("compared_files")
  public Long comparedFiles;

  /**
   * Gets {@link #comparedFiles}.
   *
   * @return the value of {@link #comparedFiles}
   */
  public Long getComparedFiles() {
    return comparedFiles;
  }

  /**
   * Number of folders compared
   */
  @JsonProperty("compared_folders")
  public Long comparedFolders;

  /**
   * Gets {@link #comparedFolders}.
   *
   * @return the value of {@link #comparedFolders}
   */
  public Long getComparedFolders() {
    return comparedFolders;
  }

  /**
   * When this run was completed
   */
  @JsonProperty("completed_at")
  public Date completedAt;

  /**
   * Gets {@link #completedAt}.
   *
   * @return the value of {@link #completedAt}
   */
  public Date getCompletedAt() {
    return completedAt;
  }

  /**
   * When this run was created
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
   * Destination remote server type, if any
   */
  @JsonProperty("dest_remote_server_type")
  public String destRemoteServerType;

  /**
   * Gets {@link #destRemoteServerType}.
   *
   * @return the value of {@link #destRemoteServerType}
   */
  public String getDestRemoteServerType() {
    return destRemoteServerType;
  }

  /**
   * Whether this run was a dry run (no actual changes made)
   */
  @JsonProperty("dry_run")
  public Boolean dryRun;

  /**
   * Gets {@link #dryRun}.
   *
   * @return the value of {@link #dryRun}
   */
  public Boolean getDryRun() {
    return dryRun;
  }

  /**
   * Number of files that errored
   */
  @JsonProperty("errored_files")
  public Long erroredFiles;

  /**
   * Gets {@link #erroredFiles}.
   *
   * @return the value of {@link #erroredFiles}
   */
  public Long getErroredFiles() {
    return erroredFiles;
  }

  /**
   * Estimated bytes count for this run
   */
  @JsonProperty("estimated_bytes_count")
  public Long estimatedBytesCount;

  /**
   * Gets {@link #estimatedBytesCount}.
   *
   * @return the value of {@link #estimatedBytesCount}
   */
  public Long getEstimatedBytesCount() {
    return estimatedBytesCount;
  }

  /**
   * Array of errors encountered during the run
   */
  @JsonProperty("event_errors")
  public String[] eventErrors;

  /**
   * Gets {@link #eventErrors}.
   *
   * @return the value of {@link #eventErrors}
   */
  public String[] getEventErrors() {
    return eventErrors;
  }

  /**
   * Link to external log file.
   */
  @JsonProperty("log_url")
  public String logUrl;

  /**
   * Gets {@link #logUrl}.
   *
   * @return the value of {@link #logUrl}
   */
  public String getLogUrl() {
    return logUrl;
  }

  /**
   * Total runtime in seconds
   */
  @JsonProperty("runtime")
  public Double runtime;

  /**
   * Gets {@link #runtime}.
   *
   * @return the value of {@link #runtime}
   */
  public Double getRuntime() {
    return runtime;
  }

  /**
   * Site ID
   */
  @JsonProperty("site_id")
  public Long siteId;

  /**
   * Gets {@link #siteId}.
   *
   * @return the value of {@link #siteId}
   */
  public Long getSiteId() {
    return siteId;
  }

  /**
   * Workspace ID
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Source remote server type, if any
   */
  @JsonProperty("src_remote_server_type")
  public String srcRemoteServerType;

  /**
   * Gets {@link #srcRemoteServerType}.
   *
   * @return the value of {@link #srcRemoteServerType}
   */
  public String getSrcRemoteServerType() {
    return srcRemoteServerType;
  }

  /**
   * Status of the sync run (success, failure, partial_failure, in_progress, skipped)
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
   * Number of files successfully synced
   */
  @JsonProperty("successful_files")
  public Long successfulFiles;

  /**
   * Gets {@link #successfulFiles}.
   *
   * @return the value of {@link #successfulFiles}
   */
  public Long getSuccessfulFiles() {
    return successfulFiles;
  }

  /**
   * ID of the Sync this run belongs to
   */
  @JsonProperty("sync_id")
  public Long syncId;

  /**
   * Gets {@link #syncId}.
   *
   * @return the value of {@link #syncId}
   */
  public Long getSyncId() {
    return syncId;
  }

  /**
   * Name of the Sync this run belongs to
   */
  @JsonProperty("sync_name")
  public String syncName;

  /**
   * Gets {@link #syncName}.
   *
   * @return the value of {@link #syncName}
   */
  public String getSyncName() {
    return syncName;
  }

  /**
   * When this run was last updated
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Array of in-progress file transfers with progress data. Only present when the sync run status is in_progress.
   */
  @JsonProperty("live_transfers")
  public SyncRunLiveTransfer[] liveTransfers;

  /**
   * Gets {@link #liveTransfers}.
   *
   * @return the value of {@link #liveTransfers}
   */
  public SyncRunLiveTransfer[] getLiveTransfers() {
    return liveTransfers;
  }


  /**
   * List Sync Runs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SyncRun> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Sync Runs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SyncRun> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Sync Runs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id`, `sync_id`, `created_at` or `status`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `status`, `dry_run`, `workspace_id`, `src_remote_server_type`, `dest_remote_server_type` or `sync_id`. Valid field combinations are `[ status, created_at ]`, `[ workspace_id, created_at ]`, `[ src_remote_server_type, created_at ]`, `[ dest_remote_server_type, created_at ]`, `[ sync_id, created_at ]`, `[ workspace_id, status ]`, `[ src_remote_server_type, status ]`, `[ dest_remote_server_type, status ]`, `[ sync_id, status ]`, `[ workspace_id, src_remote_server_type ]`, `[ workspace_id, dest_remote_server_type ]`, `[ workspace_id, sync_id ]`, `[ workspace_id, status, created_at ]`, `[ src_remote_server_type, status, created_at ]`, `[ dest_remote_server_type, status, created_at ]`, `[ sync_id, status, created_at ]`, `[ workspace_id, src_remote_server_type, created_at ]`, `[ workspace_id, dest_remote_server_type, created_at ]`, `[ workspace_id, sync_id, created_at ]`, `[ workspace_id, src_remote_server_type, status ]`, `[ workspace_id, dest_remote_server_type, status ]`, `[ workspace_id, sync_id, status ]`, `[ workspace_id, src_remote_server_type, status, created_at ]`, `[ workspace_id, dest_remote_server_type, status, created_at ]` or `[ workspace_id, sync_id, status, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SyncRun} records, in a {@code ListIterator}
   */
  public static ListIterator<SyncRun> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncRuns().list(parameters, options);
  }

  /**
   * List Sync Runs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SyncRun> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Sync Runs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SyncRun> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Sync Run
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SyncRun find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Sync Run
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SyncRun find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Sync Run
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SyncRun find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Sync Run
   *
   * @param id Sync Run ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SyncRun}
   */
  public static SyncRun find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().syncRuns().find(id, parameters, options);
  }

  /**
   * Show Sync Run
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SyncRun get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Sync Run
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SyncRun get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
