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
public class ActionNotificationExport implements ModelInterface {
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


  public ActionNotificationExport() {
    this(null, null);
  }

  public ActionNotificationExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionNotificationExport(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * History Export ID
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
  * Version of the underlying records for the export.
  */
  @JsonProperty("export_version")
  public String exportVersion;

  public String getExportVersion() {
    return exportVersion;
  }

  public void setExportVersion(String exportVersion) {
    this.exportVersion = exportVersion;
  }

  /**
  * Start date/time of export range.
  */
  @JsonProperty("start_at")
  public Date startAt;

  public Date getStartAt() {
    return startAt;
  }

  public void setStartAt(Date startAt) {
    this.startAt = startAt;
  }

  /**
  * End date/time of export range.
  */
  @JsonProperty("end_at")
  public Date endAt;

  public Date getEndAt() {
    return endAt;
  }

  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }

  /**
  * Status of export.  Valid values: `building`, `ready`, or `failed`
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
  * Return notifications that were triggered by actions on this specific path.
  */
  @JsonProperty("query_path")
  public String queryPath;

  public String getQueryPath() {
    return queryPath;
  }

  public void setQueryPath(String queryPath) {
    this.queryPath = queryPath;
  }

  /**
  * Return notifications that were triggered by actions in this folder.
  */
  @JsonProperty("query_folder")
  public String queryFolder;

  public String getQueryFolder() {
    return queryFolder;
  }

  public void setQueryFolder(String queryFolder) {
    this.queryFolder = queryFolder;
  }

  /**
  * Error message associated with the request, if any.
  */
  @JsonProperty("query_message")
  public String queryMessage;

  public String getQueryMessage() {
    return queryMessage;
  }

  public void setQueryMessage(String queryMessage) {
    this.queryMessage = queryMessage;
  }

  /**
  * The HTTP request method used by the webhook.
  */
  @JsonProperty("query_request_method")
  public String queryRequestMethod;

  public String getQueryRequestMethod() {
    return queryRequestMethod;
  }

  public void setQueryRequestMethod(String queryRequestMethod) {
    this.queryRequestMethod = queryRequestMethod;
  }

  /**
  * The target webhook URL.
  */
  @JsonProperty("query_request_url")
  public String queryRequestUrl;

  public String getQueryRequestUrl() {
    return queryRequestUrl;
  }

  public void setQueryRequestUrl(String queryRequestUrl) {
    this.queryRequestUrl = queryRequestUrl;
  }

  /**
  * The HTTP status returned from the server in response to the webhook request.
  */
  @JsonProperty("query_status")
  public String queryStatus;

  public String getQueryStatus() {
    return queryStatus;
  }

  public void setQueryStatus(String queryStatus) {
    this.queryStatus = queryStatus;
  }

  /**
  * true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
  */
  @JsonProperty("query_success")
  public Boolean querySuccess;

  public Boolean getQuerySuccess() {
    return querySuccess;
  }

  public void setQuerySuccess(Boolean querySuccess) {
    this.querySuccess = querySuccess;
  }

  /**
  * If `status` is `ready`, this will be a URL where all the results can be downloaded at once as a CSV.
  */
  @JsonProperty("results_url")
  public String resultsUrl;

  public String getResultsUrl() {
    return resultsUrl;
  }

  public void setResultsUrl(String resultsUrl) {
    this.resultsUrl = resultsUrl;
  }

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    ActionNotificationExport.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Action Notification Export ID.
  */
  public static ActionNotificationExport find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ActionNotificationExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ActionNotificationExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ActionNotificationExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/action_notification_exports/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ActionNotificationExport> typeReference = new TypeReference<ActionNotificationExport>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ActionNotificationExport get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ActionNotificationExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   start_at - string - Start date/time of export range.
  *   end_at - string - End date/time of export range.
  *   query_message - string - Error message associated with the request, if any.
  *   query_request_method - string - The HTTP request method used by the webhook.
  *   query_request_url - string - The target webhook URL.
  *   query_status - string - The HTTP status returned from the server in response to the webhook request.
  *   query_success - boolean - true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
  *   query_path - string - Return notifications that were triggered by actions on this specific path.
  *   query_folder - string - Return notifications that were triggered by actions in this folder.
  */
  public static ActionNotificationExport create() throws RuntimeException {
    return create(null, null);
  }

  public static ActionNotificationExport create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static ActionNotificationExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }
    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }
    if (parameters.containsKey("query_message") && !(parameters.get("query_message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_message must be of type String parameters[\"query_message\"]");
    }
    if (parameters.containsKey("query_request_method") && !(parameters.get("query_request_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_request_method must be of type String parameters[\"query_request_method\"]");
    }
    if (parameters.containsKey("query_request_url") && !(parameters.get("query_request_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_request_url must be of type String parameters[\"query_request_url\"]");
    }
    if (parameters.containsKey("query_status") && !(parameters.get("query_status") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_status must be of type String parameters[\"query_status\"]");
    }
    if (parameters.containsKey("query_success") && !(parameters.get("query_success") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: query_success must be of type Boolean parameters[\"query_success\"]");
    }
    if (parameters.containsKey("query_path") && !(parameters.get("query_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_path must be of type String parameters[\"query_path\"]");
    }
    if (parameters.containsKey("query_folder") && !(parameters.get("query_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_folder must be of type String parameters[\"query_folder\"]");
    }


    String url = String.format("%s%s/action_notification_exports", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<ActionNotificationExport> typeReference = new TypeReference<ActionNotificationExport>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
