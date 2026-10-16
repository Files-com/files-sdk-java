package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteMountBackend implements ModelInterface {
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

  // One mapper for every RemoteMountBackend, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public RemoteMountBackend() {
    this(null, null);
  }

  public RemoteMountBackend(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public RemoteMountBackend(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * Array of recent health check results.
   */
  @JsonProperty("health_check_results")
  public Object[] healthCheckResults;

  public Object[] getHealthCheckResults() {
    return healthCheckResults;
  }

  public void setHealthCheckResults(Object[] healthCheckResults) {
    this.healthCheckResults = healthCheckResults;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("min_free_cpu")
  public BigDecimal minFreeCpu;

  public BigDecimal getMinFreeCpu() {
    return minFreeCpu;
  }

  public void setMinFreeCpu(BigDecimal minFreeCpu) {
    this.minFreeCpu = minFreeCpu;
  }

  /**
   * Minimum free memory percentage required for this backend to be considered healthy.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("min_free_mem")
  public BigDecimal minFreeMem;

  public BigDecimal getMinFreeMem() {
    return minFreeMem;
  }

  public void setMinFreeMem(BigDecimal minFreeMem) {
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
    filesClient().remoteMountBackends().resetStatus(this.id, parameters, this.options);
  }

  /**
   * Updates this RemoteMountBackend. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
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
   *   canary_file_path - string - Path to the canary file used for health checks.
   *   remote_server_id - int64 - The remote server that this backend is associated with.
   */
  public RemoteMountBackend update(HashMap<String, Object> parameters) throws IOException {
    RemoteMountBackend updated = filesClient().remoteMountBackends().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().remoteMountBackends().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this RemoteMountBackend: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().remoteMountBackends().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.minFreeCpu != null) {
      parameters.put("min_free_cpu", this.minFreeCpu);
    }
    if (this.minFreeMem != null) {
      parameters.put("min_free_mem", this.minFreeMem);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(RemoteMountBackend canonical) {
    this.canaryFilePath = canonical.canaryFilePath;
    this.enabled = canonical.enabled;
    this.fall = canonical.fall;
    this.healthCheckEnabled = canonical.healthCheckEnabled;
    this.healthCheckResults = canonical.healthCheckResults;
    this.healthCheckType = canonical.healthCheckType;
    this.id = canonical.id;
    this.interval = canonical.interval;
    this.minFreeCpu = canonical.minFreeCpu;
    this.minFreeMem = canonical.minFreeMem;
    this.priority = canonical.priority;
    this.remotePath = canonical.remotePath;
    this.remoteServerId = canonical.remoteServerId;
    this.remoteServerMountId = canonical.remoteServerMountId;
    this.rise = canonical.rise;
    this.status = canonical.status;
    this.undergoingMaintenance = canonical.undergoingMaintenance;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `remote_server_mount_id`.
   */
  public static ListIterator<RemoteMountBackend> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteMountBackends().list(parameters, options);
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
    return FilesClient.defaultClient().remoteMountBackends().find(id, parameters, options);
  }

  public static RemoteMountBackend get() throws RuntimeException {
    return get(null, null, null);
  }

  public static RemoteMountBackend get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
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
   *   canary_file_path (required) - string - Path to the canary file used for health checks.
   *   remote_server_mount_id (required) - int64 - The mount ID of the Remote Server Mount that this backend is associated with.
   *   remote_server_id (required) - int64 - The remote server that this backend is associated with.
   */
  public static RemoteMountBackend create() throws RuntimeException {
    return create(null, null);
  }

  public static RemoteMountBackend create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static RemoteMountBackend create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteMountBackends().create(parameters, options);
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
    FilesClient.defaultClient().remoteMountBackends().resetStatus(id, parameters, options);
  }


  /**
   * Parameters:
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
   *   canary_file_path - string - Path to the canary file used for health checks.
   *   remote_server_id - int64 - The remote server that this backend is associated with.
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
    return FilesClient.defaultClient().remoteMountBackends().update(id, parameters, options);
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
    FilesClient.defaultClient().remoteMountBackends().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
