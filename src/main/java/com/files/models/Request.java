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
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request implements ModelInterface {
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

  // One mapper for every Request, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Request} with no attributes set.
   */
  public Request() {
    this(null, null);
  }

  /**
   * Creates a {@code Request} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Request(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Request} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Request(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Request ID
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
   * Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Source filename, if applicable
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
   * Sets {@link #source}.
   *
   * @param source the new value
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Destination filename
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
   * Sets {@link #destination}.
   *
   * @param destination the new value
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * ID of automation that created request
   */
  @JsonProperty("automation_id")
  public Long automationId;

  /**
   * Gets {@link #automationId}.
   *
   * @return the value of {@link #automationId}
   */
  public Long getAutomationId() {
    return automationId;
  }

  /**
   * Sets {@link #automationId}.
   *
   * @param automationId the new value
   */
  public void setAutomationId(Long automationId) {
    this.automationId = automationId;
  }

  /**
   * User making the request (if applicable)
   */
  @JsonProperty("user_display_name")
  public String userDisplayName;

  /**
   * Gets {@link #userDisplayName}.
   *
   * @return the value of {@link #userDisplayName}
   */
  public String getUserDisplayName() {
    return userDisplayName;
  }

  /**
   * Sets {@link #userDisplayName}.
   *
   * @param userDisplayName the new value
   */
  public void setUserDisplayName(String userDisplayName) {
    this.userDisplayName = userDisplayName;
  }

  /**
   * A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.
   */
  @JsonProperty("user_ids")
  public String userIds;

  /**
   * Gets {@link #userIds}.
   *
   * @return the value of {@link #userIds}
   */
  public String getUserIds() {
    return userIds;
  }

  /**
   * Sets {@link #userIds}.
   *
   * @param userIds the new value
   */
  public void setUserIds(String userIds) {
    this.userIds = userIds;
  }

  /**
   * A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.
   */
  @JsonProperty("group_ids")
  public String groupIds;

  /**
   * Gets {@link #groupIds}.
   *
   * @return the value of {@link #groupIds}
   */
  public String getGroupIds() {
    return groupIds;
  }

  /**
   * Sets {@link #groupIds}.
   *
   * @param groupIds the new value
   */
  public void setGroupIds(String groupIds) {
    this.groupIds = groupIds;
  }

  /**
   * Delete Request
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().requests().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Request
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Creates this Request.
   * On success, this object's attributes are replaced by the server's response.
   * Requests cannot be updated, so saving one that already has an id throws
   * {@link UnsupportedOperationException}.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      throw new UnsupportedOperationException("The Request Object doesn't support updates.");
    }
    refreshFrom(filesClient().requests().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Request canonical) {
    this.id = canonical.id;
    this.path = canonical.path;
    this.source = canonical.source;
    this.destination = canonical.destination;
    this.automationId = canonical.automationId;
    this.userDisplayName = canonical.userDisplayName;
    this.userIds = canonical.userIds;
    this.groupIds = canonical.groupIds;
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Request> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Request> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * <li>{@code mine} (Boolean): Only show requests of the current user?  (Defaults to true if current user is not a site admin.)</li>
   * <li>{@code path} (String): Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Request} records, in a {@code ListIterator}
   */
  public static ListIterator<Request> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().requests().list(parameters, options);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Request> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Request> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * List Requests
   *
   * @see #getFolder(String, HashMap, HashMap)
   */
  public static Request getFolder() throws RuntimeException {
    return getFolder(null, null, null);
  }

  /**
   * List Requests
   *
   * @see #getFolder(String, HashMap, HashMap)
   */
  public static Request getFolder(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return getFolder(path, parameters, null);
  }

  /**
   * List Requests
   *
   * @see #getFolder(String, HashMap, HashMap)
   */
  public static Request getFolder(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return getFolder(null, parameters, options);
  }

  /**
   * List Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * <li>{@code mine} (Boolean): Only show requests of the current user?  (Defaults to true if current user is not a site admin.)</li>
   * </ul>
   *
   * @param path Path to show requests for.  If omitted, shows all paths. Send `/` to represent the root directory.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Request}
   */
  public static Request getFolder(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().requests().getFolder(path, parameters, options);
  }


  /**
   * Create Request
   *
   * @see #create(HashMap, HashMap)
   */
  public static Request create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Request
   *
   * @see #create(HashMap, HashMap)
   */
  public static Request create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code path} (String): Required. Folder path on which to request the file.</li>
   * <li>{@code destination} (String): Required. Destination filename (without extension) to request.</li>
   * <li>{@code user_ids} (String): A list of user IDs to request the file from. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code group_ids} (String): A list of group IDs to request the file from. If sent as a string, it should be comma-delimited.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Request}
   */
  public static Request create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().requests().create(parameters, options);
  }


  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Request
   *
   * @param id Request ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().requests().delete(id, parameters, options);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
