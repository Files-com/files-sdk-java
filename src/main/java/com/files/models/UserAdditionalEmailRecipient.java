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
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAdditionalEmailRecipient implements ModelInterface {
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

  // One mapper for every UserAdditionalEmailRecipient, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code UserAdditionalEmailRecipient} with no attributes set.
   */
  public UserAdditionalEmailRecipient() {
    this(null, null);
  }

  /**
   * Creates a {@code UserAdditionalEmailRecipient} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public UserAdditionalEmailRecipient(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code UserAdditionalEmailRecipient} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public UserAdditionalEmailRecipient(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * User additional email recipient ID
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
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Workspace ID (0 for default workspace).
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * Additional email recipient address
   */
  @JsonProperty("email")
  public String email;

  /**
   * Gets {@link #email}.
   *
   * @return the value of {@link #email}
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets {@link #email}.
   *
   * @param email the new value
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Created at date/time
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
   * Updates this UserAdditionalEmailRecipient. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update User Additional Email Recipient
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code email} (String): Additional email recipient address</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public UserAdditionalEmailRecipient update(HashMap<String, Object> parameters) throws IOException {
    UserAdditionalEmailRecipient updated = filesClient().userAdditionalEmailRecipients().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().userAdditionalEmailRecipients().delete(this.id, parameters, this.options);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this UserAdditionalEmailRecipient: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().userAdditionalEmailRecipients().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(UserAdditionalEmailRecipient canonical) {
    this.id = canonical.id;
    this.userId = canonical.userId;
    this.workspaceId = canonical.workspaceId;
    this.email = canonical.email;
    this.createdAt = canonical.createdAt;
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserAdditionalEmailRecipient> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserAdditionalEmailRecipient> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List User Additional Email Recipients
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `email`, `user_id` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `email`, `workspace_id` or `user_id`. Valid field combinations are `[ workspace_id, email ]`, `[ user_id, email ]`, `[ workspace_id, user_id ]` or `[ workspace_id, user_id, email ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `email`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient} records, in a {@code ListIterator}
   */
  public static ListIterator<UserAdditionalEmailRecipient> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userAdditionalEmailRecipients().list(parameters, options);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserAdditionalEmailRecipient> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserAdditionalEmailRecipient> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @param id User Additional Email Recipient ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public static UserAdditionalEmailRecipient find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userAdditionalEmailRecipients().find(id, parameters, options);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User Additional Email Recipient
   *
   * @see #create(HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User Additional Email Recipient
   *
   * @see #create(HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create User Additional Email Recipient
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code email} (String): Required. Additional email recipient address</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public static UserAdditionalEmailRecipient create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userAdditionalEmailRecipients().create(parameters, options);
  }


  /**
   * Update User Additional Email Recipient
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update User Additional Email Recipient
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update User Additional Email Recipient
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static UserAdditionalEmailRecipient update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update User Additional Email Recipient
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code email} (String): Additional email recipient address</li>
   * </ul>
   *
   * @param id User Additional Email Recipient ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public static UserAdditionalEmailRecipient update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userAdditionalEmailRecipients().update(id, parameters, options);
  }


  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @param id User Additional Email Recipient ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().userAdditionalEmailRecipients().delete(id, parameters, options);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
