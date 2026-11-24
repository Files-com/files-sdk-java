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
public class ActionLog implements ModelInterface {
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

  // One mapper for every ActionLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ActionLog} with no attributes set.
   */
  public ActionLog() {
    this(null, null);
  }

  /**
   * Creates a {@code ActionLog} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ActionLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ActionLog} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ActionLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The type of action performed
   */
  @JsonProperty("action")
  public String action;

  /**
   * Gets {@link #action}.
   *
   * @return the value of {@link #action}
   */
  public String getAction() {
    return action;
  }

  /**
   * Time the action was performed
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
   * Destination path, for moves and copies
   */
  @JsonProperty("destination")
  public String destination;

  /**
   * Gets {@link #destination}.
   *
   * @return the value of {@link #destination}
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Failure type, if applicable
   */
  @JsonProperty("failure_type")
  public String failureType;

  /**
   * Gets {@link #failureType}.
   *
   * @return the value of {@link #failureType}
   */
  public String getFailureType() {
    return failureType;
  }

  /**
   * Folder involved in the action
   */
  @JsonProperty("folder")
  public String folder;

  /**
   * Gets {@link #folder}.
   *
   * @return the value of {@link #folder}
   */
  public String getFolder() {
    return folder;
  }

  /**
   * Interface used to perform the action
   */
  @JsonProperty("interface")
  public String interfaceName;

  /**
   * Gets {@link #interfaceName}.
   *
   * @return the value of {@link #interfaceName}
   */
  public String getInterfaceName() {
    return interfaceName;
  }

  /**
   * IP address from which the action was performed
   */
  @JsonProperty("ip")
  public String ip;

  /**
   * Gets {@link #ip}.
   *
   * @return the value of {@link #ip}
   */
  public String getIp() {
    return ip;
  }

  /**
   * File or Folder ID associated with the action
   */
  @JsonProperty("metadata_dm_id")
  public Long metadataDmId;

  /**
   * Gets {@link #metadataDmId}.
   *
   * @return the value of {@link #metadataDmId}
   */
  public Long getMetadataDmId() {
    return metadataDmId;
  }

  /**
   * Parent File or Folder ID associated with the action
   */
  @JsonProperty("parent_metadata_dm_id")
  public Long parentMetadataDmId;

  /**
   * Gets {@link #parentMetadataDmId}.
   *
   * @return the value of {@link #parentMetadataDmId}
   */
  public Long getParentMetadataDmId() {
    return parentMetadataDmId;
  }

  /**
   * File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Site ID where the action took place
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
   * Source path, for moves and copies
   */
  @JsonProperty("src")
  public String src;

  /**
   * Gets {@link #src}.
   *
   * @return the value of {@link #src}
   */
  public String getSrc() {
    return src;
  }

  /**
   * User ID who performed the action
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Username who performed the action
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }


  /**
   * List Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ActionLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ActionLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Action Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `folder`, `path`, `src`, `destination`, `created_at`, `user_id` or `username`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `folder`, `path`, `src`, `destination` or `username`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ActionLog} records, in a {@code ListIterator}
   */
  public static ListIterator<ActionLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().actionLogs().list(parameters, options);
  }

  /**
   * List Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ActionLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Action Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ActionLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
