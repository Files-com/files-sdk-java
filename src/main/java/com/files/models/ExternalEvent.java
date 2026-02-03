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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class ExternalEvent implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public ExternalEvent() {
    this(null, null);
  }

  public ExternalEvent(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ExternalEvent(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Event ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Type of event being recorded.
  */
  @JsonProperty("event_type")
  public String eventType;

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  /**
  * Status of event.
  */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
  * Event body
  */
  @JsonProperty("body")
  public String body;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  /**
  * External event create date/time
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Link to log file.
  */
  @JsonProperty("body_url")
  public String bodyUrl;

  public String getBodyUrl() {
    return bodyUrl;
  }

  public void setBodyUrl(String bodyUrl) {
    this.bodyUrl = bodyUrl;
  }

  /**
  * Folder Behavior ID
  */
  @JsonProperty("folder_behavior_id")
  public Long folderBehaviorId;

  public Long getFolderBehaviorId() {
    return folderBehaviorId;
  }

  public void setFolderBehaviorId(Long folderBehaviorId) {
    this.folderBehaviorId = folderBehaviorId;
  }

  /**
  * SIEM HTTP Destination ID.
  */
  @JsonProperty("siem_http_destination_id")
  public Long siemHttpDestinationId;

  public Long getSiemHttpDestinationId() {
    return siemHttpDestinationId;
  }

  public void setSiemHttpDestinationId(Long siemHttpDestinationId) {
    this.siemHttpDestinationId = siemHttpDestinationId;
  }

  /**
  * For sync events, the number of files handled successfully.
  */
  @JsonProperty("successful_files")
  public Long successfulFiles;

  public Long getSuccessfulFiles() {
    return successfulFiles;
  }

  public void setSuccessfulFiles(Long successfulFiles) {
    this.successfulFiles = successfulFiles;
  }

  /**
  * For sync events, the number of files that encountered errors.
  */
  @JsonProperty("errored_files")
  public Long erroredFiles;

  public Long getErroredFiles() {
    return erroredFiles;
  }

  public void setErroredFiles(Long erroredFiles) {
    this.erroredFiles = erroredFiles;
  }

  /**
  * For sync events, the total number of bytes synced.
  */
  @JsonProperty("bytes_synced")
  public Long bytesSynced;

  public Long getBytesSynced() {
    return bytesSynced;
  }

  public void setBytesSynced(Long bytesSynced) {
    this.bytesSynced = bytesSynced;
  }

  /**
  * For sync events, the number of files considered for the sync.
  */
  @JsonProperty("compared_files")
  public Long comparedFiles;

  public Long getComparedFiles() {
    return comparedFiles;
  }

  public void setComparedFiles(Long comparedFiles) {
    this.comparedFiles = comparedFiles;
  }

  /**
  * For sync events, the number of folders listed and considered for the sync.
  */
  @JsonProperty("compared_folders")
  public Long comparedFolders;

  public Long getComparedFolders() {
    return comparedFolders;
  }

  public void setComparedFolders(Long comparedFolders) {
    this.comparedFolders = comparedFolders;
  }

  /**
  * Associated Remote Server type, if any
  */
  @JsonProperty("remote_server_type")
  public String remoteServerType;

  public String getRemoteServerType() {
    return remoteServerType;
  }

  public void setRemoteServerType(String remoteServerType) {
    this.remoteServerType = remoteServerType;
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    ExternalEvent.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `siem_http_destination_id`, `created_at`, `event_type`, `status` or `folder_behavior_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `event_type`, `remote_server_type`, `status`, `folder_behavior_id` or `siem_http_destination_id`. Valid field combinations are `[ event_type, created_at ]`, `[ remote_server_type, created_at ]`, `[ status, created_at ]`, `[ folder_behavior_id, created_at ]`, `[ event_type, status ]`, `[ remote_server_type, status ]`, `[ folder_behavior_id, status ]`, `[ event_type, status, created_at ]`, `[ remote_server_type, status, created_at ]` or `[ folder_behavior_id, status, created_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
  */
  public static ListIterator<ExternalEvent> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ExternalEvent> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ExternalEvent> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/external_events", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ExternalEvent>> typeReference = new TypeReference<List<ExternalEvent>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ExternalEvent> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ExternalEvent> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - External Event ID.
  */
  public static ExternalEvent find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ExternalEvent find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ExternalEvent find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ExternalEvent find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/external_events/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ExternalEvent> typeReference = new TypeReference<ExternalEvent>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ExternalEvent get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ExternalEvent get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   status (required) - string - Status of event.
  *   body (required) - string - Event body
  */
  public static ExternalEvent create() throws RuntimeException {
    return create(null, null);
  }

  public static ExternalEvent create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static ExternalEvent create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("status") || parameters.get("status") == null) {
      throw new NullPointerException("Parameter missing: status parameters[\"status\"]");
    }
    if (!parameters.containsKey("body") || parameters.get("body") == null) {
      throw new NullPointerException("Parameter missing: body parameters[\"body\"]");
    }

    if (parameters.containsKey("status") && !(parameters.get("status") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: status must be of type String parameters[\"status\"]");
    }
    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }


    String url = String.format("%s%s/external_events", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<ExternalEvent> typeReference = new TypeReference<ExternalEvent>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
