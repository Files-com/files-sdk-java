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
public class Snapshot implements ModelInterface {
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

  // One mapper for every Snapshot, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Snapshot() {
    this(null, null);
  }

  public Snapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Snapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The snapshot's unique ID.
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
   * When the snapshot expires.
   */
  @JsonProperty("expires_at")
  public Date expiresAt;

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
   * When the snapshot was finalized.
   */
  @JsonProperty("finalized_at")
  public Date finalizedAt;

  public Date getFinalizedAt() {
    return finalizedAt;
  }

  public void setFinalizedAt(Date finalizedAt) {
    this.finalizedAt = finalizedAt;
  }

  /**
   * A name for the snapshot.
   */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * The user that created this snapshot, if applicable.
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
   * The bundle using this snapshot, if applicable.
   */
  @JsonProperty("bundle_id")
  public Long bundleId;

  public Long getBundleId() {
    return bundleId;
  }

  public void setBundleId(Long bundleId) {
    this.bundleId = bundleId;
  }

  /**
   * Workspace ID. `0` means the default workspace.
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
   * An array of paths to add to the snapshot.
   */
  @JsonProperty("paths")
  public String[] paths;

  public String[] getPaths() {
    return paths;
  }

  public void setPaths(String[] paths) {
    this.paths = paths;
  }

  /**
   * Finalize Snapshot
   */
  public void performFinalize(HashMap<String, Object> parameters) throws IOException {
    filesClient().snapshots().performFinalize(this.id, parameters, this.options);
  }

  /**
   * Updates this Snapshot. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   expires_at - string - When the snapshot expires.
   *   name - string - A name for the snapshot.
   *   paths - array(string) - An array of paths to add to the snapshot.
   */
  public Snapshot update(HashMap<String, Object> parameters) throws IOException {
    Snapshot updated = filesClient().snapshots().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().snapshots().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Snapshot: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().snapshots().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.paths != null) {
      parameters.put("paths", this.paths);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Snapshot canonical) {
    this.id = canonical.id;
    this.expiresAt = canonical.expiresAt;
    this.finalizedAt = canonical.finalizedAt;
    this.name = canonical.name;
    this.userId = canonical.userId;
    this.bundleId = canonical.bundleId;
    this.workspaceId = canonical.workspaceId;
    this.paths = canonical.paths;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public static ListIterator<Snapshot> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Snapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Snapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().snapshots().list(parameters, options);
  }

  public static ListIterator<Snapshot> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Snapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Snapshot ID.
   */
  public static Snapshot find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Snapshot find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Snapshot find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Snapshot find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().snapshots().find(id, parameters, options);
  }

  public static Snapshot get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Snapshot get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   expires_at - string - When the snapshot expires.
   *   name - string - A name for the snapshot.
   *   paths - array(string) - An array of paths to add to the snapshot.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public static Snapshot create() throws RuntimeException {
    return create(null, null);
  }

  public static Snapshot create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Snapshot create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().snapshots().create(parameters, options);
  }


  /**
   * Finalize Snapshot
   */
  public static void performFinalize() throws RuntimeException {
    performFinalize(null, null, null);
  }

  public static void performFinalize(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    performFinalize(id, parameters, null);
  }

  public static void performFinalize(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    performFinalize(null, parameters, options);
  }

  public static void performFinalize(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().snapshots().performFinalize(id, parameters, options);
  }


  /**
   * Parameters:
   *   expires_at - string - When the snapshot expires.
   *   name - string - A name for the snapshot.
   *   paths - array(string) - An array of paths to add to the snapshot.
   */
  public static Snapshot update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Snapshot update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Snapshot update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Snapshot update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().snapshots().update(id, parameters, options);
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
    FilesClient.defaultClient().snapshots().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
