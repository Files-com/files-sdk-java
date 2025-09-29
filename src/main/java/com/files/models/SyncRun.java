package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncRun implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public SyncRun() {
    this(null, null);
  }

  public SyncRun(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SyncRun(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * SyncRun ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * ID of the Sync this run belongs to
  */
  @JsonProperty("sync_id")
  public Long syncId;

  public Long getSyncId() {
    return syncId;
  }

  /**
  * Site ID
  */
  @JsonProperty("site_id")
  public Long siteId;

  public Long getSiteId() {
    return siteId;
  }

  /**
  * Status of the sync run (success, failure, partial_failure, in_progress, skipped)
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
  * Source remote server type, if any
  */
  @JsonProperty("src_remote_server_type")
  public String srcRemoteServerType;

  public String getSrcRemoteServerType() {
    return srcRemoteServerType;
  }

  /**
  * Destination remote server type, if any
  */
  @JsonProperty("dest_remote_server_type")
  public String destRemoteServerType;

  public String getDestRemoteServerType() {
    return destRemoteServerType;
  }

  /**
  * Log or summary body for this run
  */
  @JsonProperty("body")
  public String body;

  public String getBody() {
    return body;
  }

  /**
  * Array of errors encountered during the run
  */
  @JsonProperty("event_errors")
  public String[] eventErrors;

  public String[] getEventErrors() {
    return eventErrors;
  }

  /**
  * Number of files compared
  */
  @JsonProperty("compared_files")
  public Long comparedFiles;

  public Long getComparedFiles() {
    return comparedFiles;
  }

  /**
  * Number of folders compared
  */
  @JsonProperty("compared_folders")
  public Long comparedFolders;

  public Long getComparedFolders() {
    return comparedFolders;
  }

  /**
  * Number of files that errored
  */
  @JsonProperty("errored_files")
  public Long erroredFiles;

  public Long getErroredFiles() {
    return erroredFiles;
  }

  /**
  * Number of files successfully synced
  */
  @JsonProperty("successful_files")
  public Long successfulFiles;

  public Long getSuccessfulFiles() {
    return successfulFiles;
  }

  /**
  * Total runtime in seconds
  */
  @JsonProperty("runtime")
  public Double runtime;

  public Double getRuntime() {
    return runtime;
  }

  /**
  * Link to external log file.
  */
  @JsonProperty("log_url")
  public String logUrl;

  public String getLogUrl() {
    return logUrl;
  }

  /**
  * When this run was completed
  */
  @JsonProperty("completed_at")
  public Date completedAt;

  public Date getCompletedAt() {
    return completedAt;
  }

  /**
  * Whether this run was a dry run (no actual changes made)
  */
  @JsonProperty("dry_run")
  public Boolean dryRun;

  public Boolean getDryRun() {
    return dryRun;
  }

  /**
  * Total bytes synced in this run
  */
  @JsonProperty("bytes_synced")
  public Long bytesSynced;

  public Long getBytesSynced() {
    return bytesSynced;
  }

  /**
  * Estimated bytes count for this run
  */
  @JsonProperty("estimated_bytes_count")
  public Long estimatedBytesCount;

  public Long getEstimatedBytesCount() {
    return estimatedBytesCount;
  }

  /**
  * When this run was created
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * When this run was last updated
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }


  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `sync_id`, `created_at` or `status`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `status`, `dry_run`, `src_remote_server_type`, `dest_remote_server_type` or `sync_id`. Valid field combinations are `[ status, created_at ]`, `[ src_remote_server_type, created_at ]`, `[ dest_remote_server_type, created_at ]`, `[ sync_id, created_at ]`, `[ src_remote_server_type, status ]`, `[ dest_remote_server_type, status ]`, `[ sync_id, status ]`, `[ src_remote_server_type, status, created_at ]`, `[ dest_remote_server_type, status, created_at ]` or `[ sync_id, status, created_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
  */
  public static ListIterator<SyncRun> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SyncRun> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SyncRun> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/sync_runs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<SyncRun>> typeReference = new TypeReference<List<SyncRun>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<SyncRun> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SyncRun> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Sync Run ID.
  */
  public static SyncRun find() throws RuntimeException {
    return find(null, null, null);
  }

  public static SyncRun find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static SyncRun find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static SyncRun find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/sync_runs/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<SyncRun> typeReference = new TypeReference<SyncRun>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static SyncRun get() throws RuntimeException {
    return get(null, null, null);
  }

  public static SyncRun get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
