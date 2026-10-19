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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileMigration implements ModelInterface {
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

  // One mapper for every FileMigration, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code FileMigration} with no attributes set.
   */
  public FileMigration() {
    this(null, null);
  }

  /**
   * Creates a {@code FileMigration} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public FileMigration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code FileMigration} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public FileMigration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * File migration ID
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
   * Source path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Destination path
   */
  @JsonProperty("dest_path")
  public String destPath;

  /**
   * Gets {@link #destPath}.
   *
   * @return the value of {@link #destPath}
   */
  public String getDestPath() {
    return destPath;
  }

  /**
   * Reason for the failure, if applicable.
   */
  @JsonProperty("failure_message")
  public String failureMessage;

  /**
   * Gets {@link #failureMessage}.
   *
   * @return the value of {@link #failureMessage}
   */
  public String getFailureMessage() {
    return failureMessage;
  }

  /**
   * Number of files processed
   */
  @JsonProperty("files_moved")
  public Long filesMoved;

  /**
   * Gets {@link #filesMoved}.
   *
   * @return the value of {@link #filesMoved}
   */
  public Long getFilesMoved() {
    return filesMoved;
  }

  /**
   * Deprecated total file count for a regional migration; other migration types return zero.
   */
  @JsonProperty("files_total")
  public Long filesTotal;

  /**
   * Gets {@link #filesTotal}.
   *
   * @return the value of {@link #filesTotal}
   */
  public Long getFilesTotal() {
    return filesTotal;
  }

  /**
   * The type of operation
   */
  @JsonProperty("operation")
  public String operation;

  /**
   * Gets {@link #operation}.
   *
   * @return the value of {@link #operation}
   */
  public String getOperation() {
    return operation;
  }

  /**
   * Region
   */
  @JsonProperty("region")
  public String region;

  /**
   * Gets {@link #region}.
   *
   * @return the value of {@link #region}
   */
  public String getRegion() {
    return region;
  }

  /**
   * Status
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
   * Link to download the log file for this migration.
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
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FileMigration find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FileMigration find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FileMigration find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show File Migration
   *
   * @param id File Migration ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileMigration}
   */
  public static FileMigration find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().fileMigrations().find(id, parameters, options);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FileMigration get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FileMigration get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
