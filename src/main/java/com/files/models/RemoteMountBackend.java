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

  // One mapper for every RemoteMountBackend, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code RemoteMountBackend} with no attributes set.
   */
  public RemoteMountBackend() {
    this(null, null);
  }

  /**
   * Creates a {@code RemoteMountBackend} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public RemoteMountBackend(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code RemoteMountBackend} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #canaryFilePath}.
   *
   * @return the value of {@link #canaryFilePath}
   */
  public String getCanaryFilePath() {
    return canaryFilePath;
  }

  /**
   * Sets {@link #canaryFilePath}.
   *
   * @param canaryFilePath the new value
   */
  public void setCanaryFilePath(String canaryFilePath) {
    this.canaryFilePath = canaryFilePath;
  }

  /**
   * True if this backend is enabled.
   */
  @JsonProperty("enabled")
  public Boolean enabled;

  /**
   * Gets {@link #enabled}.
   *
   * @return the value of {@link #enabled}
   */
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * Sets {@link #enabled}.
   *
   * @param enabled the new value
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Number of consecutive failures before considering the backend unhealthy.
   */
  @JsonProperty("fall")
  public Long fall;

  /**
   * Gets {@link #fall}.
   *
   * @return the value of {@link #fall}
   */
  public Long getFall() {
    return fall;
  }

  /**
   * Sets {@link #fall}.
   *
   * @param fall the new value
   */
  public void setFall(Long fall) {
    this.fall = fall;
  }

  /**
   * True if health checks are enabled for this backend.
   */
  @JsonProperty("health_check_enabled")
  public Boolean healthCheckEnabled;

  /**
   * Gets {@link #healthCheckEnabled}.
   *
   * @return the value of {@link #healthCheckEnabled}
   */
  public Boolean getHealthCheckEnabled() {
    return healthCheckEnabled;
  }

  /**
   * Sets {@link #healthCheckEnabled}.
   *
   * @param healthCheckEnabled the new value
   */
  public void setHealthCheckEnabled(Boolean healthCheckEnabled) {
    this.healthCheckEnabled = healthCheckEnabled;
  }

  /**
   * Array of recent health check results.
   */
  @JsonProperty("health_check_results")
  public Object[] healthCheckResults;

  /**
   * Gets {@link #healthCheckResults}.
   *
   * @return the value of {@link #healthCheckResults}
   */
  public Object[] getHealthCheckResults() {
    return healthCheckResults;
  }

  /**
   * Sets {@link #healthCheckResults}.
   *
   * @param healthCheckResults the new value
   */
  public void setHealthCheckResults(Object[] healthCheckResults) {
    this.healthCheckResults = healthCheckResults;
  }

  /**
   * Type of health check to perform.
   */
  @JsonProperty("health_check_type")
  public String healthCheckType;

  /**
   * Gets {@link #healthCheckType}.
   *
   * @return the value of {@link #healthCheckType}
   */
  public String getHealthCheckType() {
    return healthCheckType;
  }

  /**
   * Sets {@link #healthCheckType}.
   *
   * @param healthCheckType the new value
   */
  public void setHealthCheckType(String healthCheckType) {
    this.healthCheckType = healthCheckType;
  }

  /**
   * Unique identifier for this backend.
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
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Interval in seconds between health checks.
   */
  @JsonProperty("interval")
  public Long interval;

  /**
   * Gets {@link #interval}.
   *
   * @return the value of {@link #interval}
   */
  public Long getInterval() {
    return interval;
  }

  /**
   * Sets {@link #interval}.
   *
   * @param interval the new value
   */
  public void setInterval(Long interval) {
    this.interval = interval;
  }

  /**
   * Minimum free CPU percentage required for this backend to be considered healthy.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("min_free_cpu")
  public BigDecimal minFreeCpu;

  /**
   * Gets {@link #minFreeCpu}.
   *
   * @return the value of {@link #minFreeCpu}
   */
  public BigDecimal getMinFreeCpu() {
    return minFreeCpu;
  }

  /**
   * Sets {@link #minFreeCpu}.
   *
   * @param minFreeCpu the new value
   */
  public void setMinFreeCpu(BigDecimal minFreeCpu) {
    this.minFreeCpu = minFreeCpu;
  }

  /**
   * Minimum free memory percentage required for this backend to be considered healthy.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("min_free_mem")
  public BigDecimal minFreeMem;

  /**
   * Gets {@link #minFreeMem}.
   *
   * @return the value of {@link #minFreeMem}
   */
  public BigDecimal getMinFreeMem() {
    return minFreeMem;
  }

  /**
   * Sets {@link #minFreeMem}.
   *
   * @param minFreeMem the new value
   */
  public void setMinFreeMem(BigDecimal minFreeMem) {
    this.minFreeMem = minFreeMem;
  }

  /**
   * Priority of this backend.
   */
  @JsonProperty("priority")
  public Long priority;

  /**
   * Gets {@link #priority}.
   *
   * @return the value of {@link #priority}
   */
  public Long getPriority() {
    return priority;
  }

  /**
   * Sets {@link #priority}.
   *
   * @param priority the new value
   */
  public void setPriority(Long priority) {
    this.priority = priority;
  }

  /**
   * Path on the remote server to treat as the root of this mount.
   */
  @JsonProperty("remote_path")
  public String remotePath;

  /**
   * Gets {@link #remotePath}.
   *
   * @return the value of {@link #remotePath}
   */
  public String getRemotePath() {
    return remotePath;
  }

  /**
   * Sets {@link #remotePath}.
   *
   * @param remotePath the new value
   */
  public void setRemotePath(String remotePath) {
    this.remotePath = remotePath;
  }

  /**
   * The remote server that this backend is associated with.
   */
  @JsonProperty("remote_server_id")
  public Long remoteServerId;

  /**
   * Gets {@link #remoteServerId}.
   *
   * @return the value of {@link #remoteServerId}
   */
  public Long getRemoteServerId() {
    return remoteServerId;
  }

  /**
   * Sets {@link #remoteServerId}.
   *
   * @param remoteServerId the new value
   */
  public void setRemoteServerId(Long remoteServerId) {
    this.remoteServerId = remoteServerId;
  }

  /**
   * The mount ID of the Remote Server Mount that this backend is associated with.
   */
  @JsonProperty("remote_server_mount_id")
  public Long remoteServerMountId;

  /**
   * Gets {@link #remoteServerMountId}.
   *
   * @return the value of {@link #remoteServerMountId}
   */
  public Long getRemoteServerMountId() {
    return remoteServerMountId;
  }

  /**
   * Sets {@link #remoteServerMountId}.
   *
   * @param remoteServerMountId the new value
   */
  public void setRemoteServerMountId(Long remoteServerMountId) {
    this.remoteServerMountId = remoteServerMountId;
  }

  /**
   * Number of consecutive successes before considering the backend healthy.
   */
  @JsonProperty("rise")
  public Long rise;

  /**
   * Gets {@link #rise}.
   *
   * @return the value of {@link #rise}
   */
  public Long getRise() {
    return rise;
  }

  /**
   * Sets {@link #rise}.
   *
   * @param rise the new value
   */
  public void setRise(Long rise) {
    this.rise = rise;
  }

  /**
   * Status of this backend.
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
   * Sets {@link #status}.
   *
   * @param status the new value
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * True if this backend is undergoing maintenance.
   */
  @JsonProperty("undergoing_maintenance")
  public Boolean undergoingMaintenance;

  /**
   * Gets {@link #undergoingMaintenance}.
   *
   * @return the value of {@link #undergoingMaintenance}
   */
  public Boolean getUndergoingMaintenance() {
    return undergoingMaintenance;
  }

  /**
   * Sets {@link #undergoingMaintenance}.
   *
   * @param undergoingMaintenance the new value
   */
  public void setUndergoingMaintenance(Boolean undergoingMaintenance) {
    this.undergoingMaintenance = undergoingMaintenance;
  }

  /**
   * Reset backend status to healthy
   *
   * @param parameters the request's parameters, by their API names
   */
  public void resetStatus(HashMap<String, Object> parameters) throws IOException {
    filesClient().remoteMountBackends().resetStatus(this.id, parameters, this.options);
  }

  /**
   * Updates this RemoteMountBackend. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Remote Mount Backend
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enabled} (Boolean): True if this backend is enabled.</li>
   * <li>{@code fall} (Long): Number of consecutive failures before considering the backend unhealthy.</li>
   * <li>{@code health_check_enabled} (Boolean): True if health checks are enabled for this backend.</li>
   * <li>{@code health_check_type} (String): Type of health check to perform.</li>
   * <li>{@code interval} (Long): Interval in seconds between health checks.</li>
   * <li>{@code min_free_cpu} (Double, BigDecimal or String): Minimum free CPU percentage required for this backend to be considered healthy.</li>
   * <li>{@code min_free_mem} (Double, BigDecimal or String): Minimum free memory percentage required for this backend to be considered healthy.</li>
   * <li>{@code priority} (Long): Priority of this backend.</li>
   * <li>{@code remote_path} (String): Path on the remote server to treat as the root of this mount.</li>
   * <li>{@code rise} (Long): Number of consecutive successes before considering the backend healthy.</li>
   * <li>{@code canary_file_path} (String): Path to the canary file used for health checks.</li>
   * <li>{@code remote_server_id} (Long): The remote server that this backend is associated with.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code RemoteMountBackend}
   */
  public RemoteMountBackend update(HashMap<String, Object> parameters) throws IOException {
    RemoteMountBackend updated = filesClient().remoteMountBackends().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Remote Mount Backend
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().remoteMountBackends().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(HashMap)
   */
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
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteMountBackend> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Remote Mount Backends
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `remote_server_mount_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend} records, in a {@code ListIterator}
   */
  public static ListIterator<RemoteMountBackend> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteMountBackends().list(parameters, options);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteMountBackend> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Remote Mount Backends
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<RemoteMountBackend> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Remote Mount Backend
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend}
   */
  public static RemoteMountBackend find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteMountBackends().find(id, parameters, options);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Remote Mount Backend
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Remote Mount Backend
   *
   * @see #create(HashMap, HashMap)
   */
  public static RemoteMountBackend create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Remote Mount Backend
   *
   * @see #create(HashMap, HashMap)
   */
  public static RemoteMountBackend create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Remote Mount Backend
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enabled} (Boolean): True if this backend is enabled.</li>
   * <li>{@code fall} (Long): Number of consecutive failures before considering the backend unhealthy.</li>
   * <li>{@code health_check_enabled} (Boolean): True if health checks are enabled for this backend.</li>
   * <li>{@code health_check_type} (String): Type of health check to perform.</li>
   * <li>{@code interval} (Long): Interval in seconds between health checks.</li>
   * <li>{@code min_free_cpu} (Double, BigDecimal or String): Minimum free CPU percentage required for this backend to be considered healthy.</li>
   * <li>{@code min_free_mem} (Double, BigDecimal or String): Minimum free memory percentage required for this backend to be considered healthy.</li>
   * <li>{@code priority} (Long): Priority of this backend.</li>
   * <li>{@code remote_path} (String): Path on the remote server to treat as the root of this mount.</li>
   * <li>{@code rise} (Long): Number of consecutive successes before considering the backend healthy.</li>
   * <li>{@code canary_file_path} (String): Required. Path to the canary file used for health checks.</li>
   * <li>{@code remote_server_mount_id} (Long): Required. The mount ID of the Remote Server Mount that this backend is associated with.</li>
   * <li>{@code remote_server_id} (Long): Required. The remote server that this backend is associated with.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend}
   */
  public static RemoteMountBackend create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteMountBackends().create(parameters, options);
  }


  /**
   * Reset backend status to healthy
   *
   * @see #resetStatus(Long, HashMap, HashMap)
   */
  public static void resetStatus() throws RuntimeException {
    resetStatus(null, null, null);
  }

  /**
   * Reset backend status to healthy
   *
   * @see #resetStatus(Long, HashMap, HashMap)
   */
  public static void resetStatus(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resetStatus(id, parameters, null);
  }

  /**
   * Reset backend status to healthy
   *
   * @see #resetStatus(Long, HashMap, HashMap)
   */
  public static void resetStatus(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resetStatus(null, parameters, options);
  }

  /**
   * Reset backend status to healthy
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void resetStatus(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().remoteMountBackends().resetStatus(id, parameters, options);
  }


  /**
   * Update Remote Mount Backend
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Remote Mount Backend
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Remote Mount Backend
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static RemoteMountBackend update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Remote Mount Backend
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code enabled} (Boolean): True if this backend is enabled.</li>
   * <li>{@code fall} (Long): Number of consecutive failures before considering the backend unhealthy.</li>
   * <li>{@code health_check_enabled} (Boolean): True if health checks are enabled for this backend.</li>
   * <li>{@code health_check_type} (String): Type of health check to perform.</li>
   * <li>{@code interval} (Long): Interval in seconds between health checks.</li>
   * <li>{@code min_free_cpu} (Double, BigDecimal or String): Minimum free CPU percentage required for this backend to be considered healthy.</li>
   * <li>{@code min_free_mem} (Double, BigDecimal or String): Minimum free memory percentage required for this backend to be considered healthy.</li>
   * <li>{@code priority} (Long): Priority of this backend.</li>
   * <li>{@code remote_path} (String): Path on the remote server to treat as the root of this mount.</li>
   * <li>{@code rise} (Long): Number of consecutive successes before considering the backend healthy.</li>
   * <li>{@code canary_file_path} (String): Path to the canary file used for health checks.</li>
   * <li>{@code remote_server_id} (Long): The remote server that this backend is associated with.</li>
   * </ul>
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code RemoteMountBackend}
   */
  public static RemoteMountBackend update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().remoteMountBackends().update(id, parameters, options);
  }


  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @param id Remote Mount Backend ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().remoteMountBackends().delete(id, parameters, options);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Remote Mount Backend
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
