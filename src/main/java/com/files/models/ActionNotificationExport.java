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
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionNotificationExport implements ModelInterface {
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

  // One mapper for every ActionNotificationExport, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public ActionNotificationExport() {
    this(null, null);
  }

  public ActionNotificationExport(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionNotificationExport(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * Workspace whose logs are exported. Set to `0` for the default workspace. A null value means a site-wide export.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
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


  /**
   * Creates this ActionNotificationExport.
   * On success, this object's attributes are replaced by the server's response.
   * ActionNotificationExports cannot be updated, so saving one that already has an id throws
   * {@link UnsupportedOperationException}.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      throw new UnsupportedOperationException("The ActionNotificationExport Object doesn't support updates.");
    }
    refreshFrom(filesClient().actionNotificationExports().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(ActionNotificationExport canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.exportVersion = canonical.exportVersion;
    this.startAt = canonical.startAt;
    this.endAt = canonical.endAt;
    this.status = canonical.status;
    this.queryPath = canonical.queryPath;
    this.queryFolder = canonical.queryFolder;
    this.queryMessage = canonical.queryMessage;
    this.queryRequestMethod = canonical.queryRequestMethod;
    this.queryRequestUrl = canonical.queryRequestUrl;
    this.queryStatus = canonical.queryStatus;
    this.querySuccess = canonical.querySuccess;
    this.resultsUrl = canonical.resultsUrl;
    this.userId = canonical.userId;
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
    return FilesClient.defaultClient().actionNotificationExports().find(id, parameters, options);
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
   *   workspace_id - int64 - Workspace whose logs are exported. Set to `0` for the default workspace. A null value means a site-wide export.
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
    return FilesClient.defaultClient().actionNotificationExports().create(parameters, options);
  }


}
