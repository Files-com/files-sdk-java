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
public class History implements ModelInterface {
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

  // One mapper for every History, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code History} with no attributes set.
   */
  public History() {
    this(null, null);
  }

  /**
   * Creates a {@code History} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public History(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code History} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public History(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Action ID
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
   * Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Action occurrence date/time
   */
  @JsonProperty("when")
  public Date when;

  /**
   * Gets {@link #when}.
   *
   * @return the value of {@link #when}
   */
  public Date getWhen() {
    return when;
  }

  /**
   * The destination path for this action, if applicable
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
   * Friendly displayed output
   */
  @JsonProperty("display")
  public String display;

  /**
   * Gets {@link #display}.
   *
   * @return the value of {@link #display}
   */
  public String getDisplay() {
    return display;
  }

  /**
   * IP Address that performed this action
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
   * The source path for this action, if applicable
   */
  @JsonProperty("source")
  public String source;

  /**
   * Gets {@link #source}.
   *
   * @return the value of {@link #source}
   */
  public String getSource() {
    return source;
  }

  /**
   * Targets
   */
  @JsonProperty("targets")
  public Object targets;

  /**
   * Gets {@link #targets}.
   *
   * @return the value of {@link #targets}
   */
  public Object getTargets() {
    return targets;
  }

  /**
   * User ID
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
   * Username
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
   * true if this change was performed by a user on a parent site.
   */
  @JsonProperty("user_is_from_parent_site")
  public Boolean userIsFromParentSite;

  /**
   * Gets {@link #userIsFromParentSite}.
   *
   * @return the value of {@link #userIsFromParentSite}
   */
  public Boolean getUserIsFromParentSite() {
    return userIsFromParentSite;
  }

  /**
   * Type of action
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
   * Failure type.  If action was a user login or session failure, why did it fail?
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
   * Interface on which this action occurred.
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
   * List history for specific file
   *
   * @see #listForFile(String, HashMap, HashMap)
   */
  public static ListIterator<Action> listForFile() throws RuntimeException {
    return listForFile(null, null, null);
  }

  /**
   * List history for specific file
   *
   * @see #listForFile(String, HashMap, HashMap)
   */
  public static ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFile(path, parameters, null);
  }

  /**
   * List history for specific file
   *
   * @see #listForFile(String, HashMap, HashMap)
   */
  public static ListIterator<Action> listForFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFile(null, parameters, options);
  }

  /**
   * List history for specific file
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public static ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listForFile(path, parameters, options);
  }


  /**
   * List history for specific folder
   *
   * @see #listForFolder(String, HashMap, HashMap)
   */
  public static ListIterator<Action> listForFolder() throws RuntimeException {
    return listForFolder(null, null, null);
  }

  /**
   * List history for specific folder
   *
   * @see #listForFolder(String, HashMap, HashMap)
   */
  public static ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFolder(path, parameters, null);
  }

  /**
   * List history for specific folder
   *
   * @see #listForFolder(String, HashMap, HashMap)
   */
  public static ListIterator<Action> listForFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFolder(null, parameters, options);
  }

  /**
   * List history for specific folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public static ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listForFolder(path, parameters, options);
  }


  /**
   * List history for specific user
   *
   * @see #listForUser(Long, HashMap, HashMap)
   */
  public static ListIterator<Action> listForUser() throws RuntimeException {
    return listForUser(null, null, null);
  }

  /**
   * List history for specific user
   *
   * @see #listForUser(Long, HashMap, HashMap)
   */
  public static ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters) throws RuntimeException {
    return listForUser(user_id, parameters, null);
  }

  /**
   * List history for specific user
   *
   * @see #listForUser(Long, HashMap, HashMap)
   */
  public static ListIterator<Action> listForUser(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForUser(null, parameters, options);
  }

  /**
   * List history for specific user
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param user_id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public static ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listForUser(user_id, parameters, options);
  }


  /**
   * List site login history
   *
   * @see #listLogins(HashMap, HashMap)
   */
  public static ListIterator<Action> listLogins() throws RuntimeException {
    return listLogins(null, null);
  }

  /**
   * List site login history
   *
   * @see #listLogins(HashMap, HashMap)
   */
  public static ListIterator<Action> listLogins(HashMap<String, Object> parameters) throws RuntimeException {
    return listLogins(parameters, null);
  }


  /**
   * List site login history
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public static ListIterator<Action> listLogins(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listLogins(parameters, options);
  }


  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Action> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Action> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List site full action history
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code start_at} (String): Leave blank or set to a date/time to filter earlier entries.</li>
   * <li>{@code end_at} (String): Leave blank or set to a date/time to filter later entries.</li>
   * <li>{@code display} (String): Display format. Leave blank or set to `full` or `parent`.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`. Valid field combinations are `[  ]`, `[ path ]`, `[ path ]` or `[ path ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Action} records, in a {@code ListIterator}
   */
  public static ListIterator<Action> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().list(parameters, options);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Action> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List site full action history
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Action> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
