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


  public History() {
    this(null, null);
  }

  public History(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  /**
   * Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
   * Action occurrence date/time
   */
  @JsonProperty("when")
  public Date when;

  public Date getWhen() {
    return when;
  }

  /**
   * The destination path for this action, if applicable
   */
  @JsonProperty("destination")
  public String destination;

  public String getDestination() {
    return destination;
  }

  /**
   * Friendly displayed output
   */
  @JsonProperty("display")
  public String display;

  public String getDisplay() {
    return display;
  }

  /**
   * IP Address that performed this action
   */
  @JsonProperty("ip")
  public String ip;

  public String getIp() {
    return ip;
  }

  /**
   * The source path for this action, if applicable
   */
  @JsonProperty("source")
  public String source;

  public String getSource() {
    return source;
  }

  /**
   * Targets
   */
  @JsonProperty("targets")
  public Object targets;

  public Object getTargets() {
    return targets;
  }

  /**
   * User ID
   */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  /**
   * Username
   */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  /**
   * true if this change was performed by a user on a parent site.
   */
  @JsonProperty("user_is_from_parent_site")
  public Boolean userIsFromParentSite;

  public Boolean getUserIsFromParentSite() {
    return userIsFromParentSite;
  }

  /**
   * Type of action
   */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  /**
   * Failure type.  If action was a user login or session failure, why did it fail?
   */
  @JsonProperty("failure_type")
  public String failureType;

  public String getFailureType() {
    return failureType;
  }

  /**
   * Interface on which this action occurred.
   */
  @JsonProperty("interface")
  public String interfaceName;

  public String getInterfaceName() {
    return interfaceName;
  }


  /**
   * Parameters:
   *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
   *   end_at - string - Leave blank or set to a date/time to filter later entries.
   *   display - string - Display format. Leave blank or set to `full` or `parent`.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
   *   path (required) - string - Path to operate on.
   */
  public static ListIterator<Action> listForFile() throws RuntimeException {
    return listForFile(null, null, null);
  }

  public static ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFile(path, parameters, null);
  }

  public static ListIterator<Action> listForFile(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFile(null, parameters, options);
  }

  public static ListIterator<Action> listForFile(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listForFile(path, parameters, options);
  }


  /**
   * Parameters:
   *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
   *   end_at - string - Leave blank or set to a date/time to filter later entries.
   *   display - string - Display format. Leave blank or set to `full` or `parent`.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
   *   path (required) - string - Path to operate on.
   */
  public static ListIterator<Action> listForFolder() throws RuntimeException {
    return listForFolder(null, null, null);
  }

  public static ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listForFolder(path, parameters, null);
  }

  public static ListIterator<Action> listForFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForFolder(null, parameters, options);
  }

  public static ListIterator<Action> listForFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listForFolder(path, parameters, options);
  }


  /**
   * Parameters:
   *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
   *   end_at - string - Leave blank or set to a date/time to filter later entries.
   *   display - string - Display format. Leave blank or set to `full` or `parent`.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
   *   user_id (required) - int64 - User ID.
   */
  public static ListIterator<Action> listForUser() throws RuntimeException {
    return listForUser(null, null, null);
  }

  public static ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters) throws RuntimeException {
    return listForUser(user_id, parameters, null);
  }

  public static ListIterator<Action> listForUser(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listForUser(null, parameters, options);
  }

  public static ListIterator<Action> listForUser(Long user_id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listForUser(user_id, parameters, options);
  }


  /**
   * Parameters:
   *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
   *   end_at - string - Leave blank or set to a date/time to filter later entries.
   *   display - string - Display format. Leave blank or set to `full` or `parent`.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
   */
  public static ListIterator<Action> listLogins() throws RuntimeException {
    return listLogins(null, null);
  }

  public static ListIterator<Action> listLogins(HashMap<String, Object> parameters) throws RuntimeException {
    return listLogins(parameters, null);
  }


  public static ListIterator<Action> listLogins(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().listLogins(parameters, options);
  }


  /**
   * Parameters:
   *   start_at - string - Leave blank or set to a date/time to filter earlier entries.
   *   end_at - string - Leave blank or set to a date/time to filter later entries.
   *   display - string - Display format. Leave blank or set to `full` or `parent`.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `user_id`, `folder` or `path`. Valid field combinations are `[  ]`, `[ path ]`, `[ path ]` or `[ path ]`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
   */
  public static ListIterator<Action> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Action> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Action> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().histories().list(parameters, options);
  }

  public static ListIterator<Action> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Action> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
