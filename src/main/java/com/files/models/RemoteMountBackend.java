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
public class RemoteMountBackend implements ModelInterface {
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


  public RemoteMountBackend() {
    this(null, null);
  }

  public RemoteMountBackend(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteMountBackend(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Path to the canary file used for health checks.
  */
  @JsonProperty("canary_file_path")
  public String canaryFilePath;

  public String getCanaryFilePath() {
    return canaryFilePath;
  }

  public void setCanaryFilePath(String canaryFilePath) {
    this.canaryFilePath = canaryFilePath;
  }

  /**
  * True if this backend is enabled.
  */
  @JsonProperty("enabled")
  public Boolean enabled;

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
  * Number of consecutive failures before considering the backend unhealthy.
  */
  @JsonProperty("fall")
  public Long fall;

  public Long getFall() {
    return fall;
  }

  public void setFall(Long fall) {
    this.fall = fall;
  }

  /**
  * True if health checks are enabled for this backend.
  */
  @JsonProperty("health_check_enabled")
  public Boolean healthCheckEnabled;

  public Boolean getHealthCheckEnabled() {
    return healthCheckEnabled;
  }

  public void setHealthCheckEnabled(Boolean healthCheckEnabled) {
    this.healthCheckEnabled = healthCheckEnabled;
  }

  /**
  * Type of health check to perform.
  */
  @JsonProperty("health_check_type")
  public String healthCheckType;

  public String getHealthCheckType() {
    return healthCheckType;
  }

  public void setHealthCheckType(String healthCheckType) {
    this.healthCheckType = healthCheckType;
  }

  /**
  * Unique identifier for this backend.
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
  * Interval in seconds between health checks.
  */
  @JsonProperty("interval")
  public Long interval;

  public Long getInterval() {
    return interval;
  }

  public void setInterval(Long interval) {
    this.interval = interval;
  }

  /**
  * Minimum free CPU percentage required for this backend to be considered healthy.
  */
  @JsonProperty("min_free_cpu")
  public Double minFreeCpu;

  public Double getMinFreeCpu() {
    return minFreeCpu;
  }

  public void setMinFreeCpu(Double minFreeCpu) {
    this.minFreeCpu = minFreeCpu;
  }

  /**
  * Minimum free memory percentage required for this backend to be considered healthy.
  */
  @JsonProperty("min_free_mem")
  public Double minFreeMem;

  public Double getMinFreeMem() {
    return minFreeMem;
  }

  public void setMinFreeMem(Double minFreeMem) {
    this.minFreeMem = minFreeMem;
  }

  /**
  * Priority of this backend.
  */
  @JsonProperty("priority")
  public Long priority;

  public Long getPriority() {
    return priority;
  }

  public void setPriority(Long priority) {
    this.priority = priority;
  }

  /**
  * Path on the remote server to treat as the root of this mount.
  */
  @JsonProperty("remote_path")
  public String remotePath;

  public String getRemotePath() {
    return remotePath;
  }

  public void setRemotePath(String remotePath) {
    this.remotePath = remotePath;
  }

  /**
  * The remote server that this backend is associated with.
  */
  @JsonProperty("remote_server_id")
  public Long remoteServerId;

  public Long getRemoteServerId() {
    return remoteServerId;
  }

  public void setRemoteServerId(Long remoteServerId) {
    this.remoteServerId = remoteServerId;
  }

  /**
  * The mount ID of the Remote Server Mount that this backend is associated with.
  */
  @JsonProperty("remote_server_mount_id")
  public Long remoteServerMountId;

  public Long getRemoteServerMountId() {
    return remoteServerMountId;
  }

  public void setRemoteServerMountId(Long remoteServerMountId) {
    this.remoteServerMountId = remoteServerMountId;
  }

  /**
  * Number of consecutive successes before considering the backend healthy.
  */
  @JsonProperty("rise")
  public Long rise;

  public Long getRise() {
    return rise;
  }

  public void setRise(Long rise) {
    this.rise = rise;
  }

  /**
  * Status of this backend.
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
  * True if this backend is undergoing maintenance.
  */
  @JsonProperty("undergoing_maintenance")
  public Boolean undergoingMaintenance;

  public Boolean getUndergoingMaintenance() {
    return undergoingMaintenance;
  }

  public void setUndergoingMaintenance(Boolean undergoingMaintenance) {
    this.undergoingMaintenance = undergoingMaintenance;
  }

  /**
  * Reset backend status to healthy
  */
  public void resetStatus(HashMap<String, Object> parameters) throws IOException {
    RemoteMountBackend.resetStatus(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   canary_file_path (required) - string - Path to the canary file used for health checks.
  *   remote_server_mount_id (required) - int64 - The mount ID of the Remote Server Mount that this backend is associated with.
  *   remote_server_id (required) - int64 - The remote server that this backend is associated with.
  *   enabled - boolean - True if this backend is enabled.
  *   fall - int64 - Number of consecutive failures before considering the backend unhealthy.
  *   health_check_enabled - boolean - True if health checks are enabled for this backend.
  *   health_check_type - string - Type of health check to perform.
  *   interval - int64 - Interval in seconds between health checks.
  *   min_free_cpu - double - Minimum free CPU percentage required for this backend to be considered healthy.
  *   min_free_mem - double - Minimum free memory percentage required for this backend to be considered healthy.
  *   priority - int64 - Priority of this backend.
  *   remote_path - string - Path on the remote server to treat as the root of this mount.
  *   rise - int64 - Number of consecutive successes before considering the backend healthy.
  */
  public RemoteMountBackend update(HashMap<String, Object> parameters) throws IOException {
    return RemoteMountBackend.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    RemoteMountBackend.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    RemoteMountBackend.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `remote_server_mount_id`.
  */
  public static ListIterator<RemoteMountBackend> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }


    String url = String.format("%s%s/remote_mount_backends", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<RemoteMountBackend>> typeReference = new TypeReference<List<RemoteMountBackend>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<RemoteMountBackend> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<RemoteMountBackend> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Remote Mount Backend ID.
  */
  public static RemoteMountBackend find() throws RuntimeException {
    return find(null, null, null);
  }

  public static RemoteMountBackend find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static RemoteMountBackend find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static RemoteMountBackend find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_mount_backends/%s", urlParts);

    TypeReference<RemoteMountBackend> typeReference = new TypeReference<RemoteMountBackend>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static RemoteMountBackend get() throws RuntimeException {
    return get(null, null, null);
  }

  public static RemoteMountBackend get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   canary_file_path (required) - string - Path to the canary file used for health checks.
  *   remote_server_mount_id (required) - int64 - The mount ID of the Remote Server Mount that this backend is associated with.
  *   remote_server_id (required) - int64 - The remote server that this backend is associated with.
  *   enabled - boolean - True if this backend is enabled.
  *   fall - int64 - Number of consecutive failures before considering the backend unhealthy.
  *   health_check_enabled - boolean - True if health checks are enabled for this backend.
  *   health_check_type - string - Type of health check to perform.
  *   interval - int64 - Interval in seconds between health checks.
  *   min_free_cpu - double - Minimum free CPU percentage required for this backend to be considered healthy.
  *   min_free_mem - double - Minimum free memory percentage required for this backend to be considered healthy.
  *   priority - int64 - Priority of this backend.
  *   remote_path - string - Path on the remote server to treat as the root of this mount.
  *   rise - int64 - Number of consecutive successes before considering the backend healthy.
  */
  public static RemoteMountBackend create() throws RuntimeException {
    return create(null, null);
  }

  public static RemoteMountBackend create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static RemoteMountBackend create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("canary_file_path") || parameters.get("canary_file_path") == null) {
      throw new NullPointerException("Parameter missing: canary_file_path parameters[\"canary_file_path\"]");
    }
    if (!parameters.containsKey("remote_server_mount_id") || parameters.get("remote_server_mount_id") == null) {
      throw new NullPointerException("Parameter missing: remote_server_mount_id parameters[\"remote_server_mount_id\"]");
    }
    if (!parameters.containsKey("remote_server_id") || parameters.get("remote_server_id") == null) {
      throw new NullPointerException("Parameter missing: remote_server_id parameters[\"remote_server_id\"]");
    }

    if (parameters.containsKey("canary_file_path") && !(parameters.get("canary_file_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: canary_file_path must be of type String parameters[\"canary_file_path\"]");
    }
    if (parameters.containsKey("remote_server_mount_id") && !(parameters.get("remote_server_mount_id") instanceof Long || parameters.get("remote_server_mount_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_mount_id must be of type Long or Integer parameters[\"remote_server_mount_id\"]");
    }
    if (parameters.containsKey("remote_server_id") && !(parameters.get("remote_server_id") instanceof Long || parameters.get("remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_id must be of type Long or Integer parameters[\"remote_server_id\"]");
    }
    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }
    if (parameters.containsKey("fall") && !(parameters.get("fall") instanceof Long || parameters.get("fall") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: fall must be of type Long or Integer parameters[\"fall\"]");
    }
    if (parameters.containsKey("health_check_enabled") && !(parameters.get("health_check_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: health_check_enabled must be of type Boolean parameters[\"health_check_enabled\"]");
    }
    if (parameters.containsKey("health_check_type") && !(parameters.get("health_check_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: health_check_type must be of type String parameters[\"health_check_type\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof Long || parameters.get("interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type Long or Integer parameters[\"interval\"]");
    }
    if (parameters.containsKey("min_free_cpu") && !(parameters.get("min_free_cpu") instanceof Double)) {
      throw new IllegalArgumentException("Bad parameter: min_free_cpu must be of type Double parameters[\"min_free_cpu\"]");
    }
    if (parameters.containsKey("min_free_mem") && !(parameters.get("min_free_mem") instanceof Double)) {
      throw new IllegalArgumentException("Bad parameter: min_free_mem must be of type Double parameters[\"min_free_mem\"]");
    }
    if (parameters.containsKey("priority") && !(parameters.get("priority") instanceof Long || parameters.get("priority") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: priority must be of type Long or Integer parameters[\"priority\"]");
    }
    if (parameters.containsKey("remote_path") && !(parameters.get("remote_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: remote_path must be of type String parameters[\"remote_path\"]");
    }
    if (parameters.containsKey("rise") && !(parameters.get("rise") instanceof Long || parameters.get("rise") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: rise must be of type Long or Integer parameters[\"rise\"]");
    }


    String url = String.format("%s%s/remote_mount_backends", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<RemoteMountBackend> typeReference = new TypeReference<RemoteMountBackend>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Reset backend status to healthy
  */
  public static void resetStatus() throws RuntimeException {
    resetStatus(null, null, null);
  }

  public static void resetStatus(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resetStatus(id, parameters, null);
  }

  public static void resetStatus(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resetStatus(null, parameters, options);
  }

  public static void resetStatus(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_mount_backends/%s/reset_status", urlParts);

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   canary_file_path (required) - string - Path to the canary file used for health checks.
  *   remote_server_mount_id (required) - int64 - The mount ID of the Remote Server Mount that this backend is associated with.
  *   remote_server_id (required) - int64 - The remote server that this backend is associated with.
  *   enabled - boolean - True if this backend is enabled.
  *   fall - int64 - Number of consecutive failures before considering the backend unhealthy.
  *   health_check_enabled - boolean - True if health checks are enabled for this backend.
  *   health_check_type - string - Type of health check to perform.
  *   interval - int64 - Interval in seconds between health checks.
  *   min_free_cpu - double - Minimum free CPU percentage required for this backend to be considered healthy.
  *   min_free_mem - double - Minimum free memory percentage required for this backend to be considered healthy.
  *   priority - int64 - Priority of this backend.
  *   remote_path - string - Path on the remote server to treat as the root of this mount.
  *   rise - int64 - Number of consecutive successes before considering the backend healthy.
  */
  public static RemoteMountBackend update() throws RuntimeException {
    return update(null, null, null);
  }

  public static RemoteMountBackend update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static RemoteMountBackend update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static RemoteMountBackend update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("canary_file_path") || parameters.get("canary_file_path") == null) {
      throw new NullPointerException("Parameter missing: canary_file_path parameters[\"canary_file_path\"]");
    }
    if (!parameters.containsKey("remote_server_mount_id") || parameters.get("remote_server_mount_id") == null) {
      throw new NullPointerException("Parameter missing: remote_server_mount_id parameters[\"remote_server_mount_id\"]");
    }
    if (!parameters.containsKey("remote_server_id") || parameters.get("remote_server_id") == null) {
      throw new NullPointerException("Parameter missing: remote_server_id parameters[\"remote_server_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("canary_file_path") && !(parameters.get("canary_file_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: canary_file_path must be of type String parameters[\"canary_file_path\"]");
    }
    if (parameters.containsKey("remote_server_mount_id") && !(parameters.get("remote_server_mount_id") instanceof Long || parameters.get("remote_server_mount_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_mount_id must be of type Long or Integer parameters[\"remote_server_mount_id\"]");
    }
    if (parameters.containsKey("remote_server_id") && !(parameters.get("remote_server_id") instanceof Long || parameters.get("remote_server_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: remote_server_id must be of type Long or Integer parameters[\"remote_server_id\"]");
    }
    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }
    if (parameters.containsKey("fall") && !(parameters.get("fall") instanceof Long || parameters.get("fall") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: fall must be of type Long or Integer parameters[\"fall\"]");
    }
    if (parameters.containsKey("health_check_enabled") && !(parameters.get("health_check_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: health_check_enabled must be of type Boolean parameters[\"health_check_enabled\"]");
    }
    if (parameters.containsKey("health_check_type") && !(parameters.get("health_check_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: health_check_type must be of type String parameters[\"health_check_type\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof Long || parameters.get("interval") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type Long or Integer parameters[\"interval\"]");
    }
    if (parameters.containsKey("min_free_cpu") && !(parameters.get("min_free_cpu") instanceof Double)) {
      throw new IllegalArgumentException("Bad parameter: min_free_cpu must be of type Double parameters[\"min_free_cpu\"]");
    }
    if (parameters.containsKey("min_free_mem") && !(parameters.get("min_free_mem") instanceof Double)) {
      throw new IllegalArgumentException("Bad parameter: min_free_mem must be of type Double parameters[\"min_free_mem\"]");
    }
    if (parameters.containsKey("priority") && !(parameters.get("priority") instanceof Long || parameters.get("priority") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: priority must be of type Long or Integer parameters[\"priority\"]");
    }
    if (parameters.containsKey("remote_path") && !(parameters.get("remote_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: remote_path must be of type String parameters[\"remote_path\"]");
    }
    if (parameters.containsKey("rise") && !(parameters.get("rise") instanceof Long || parameters.get("rise") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: rise must be of type Long or Integer parameters[\"rise\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_mount_backends/%s", urlParts);

    TypeReference<RemoteMountBackend> typeReference = new TypeReference<RemoteMountBackend>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/remote_mount_backends/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
