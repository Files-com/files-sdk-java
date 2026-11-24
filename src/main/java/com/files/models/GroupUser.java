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
public class GroupUser implements ModelInterface {
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

  // One mapper for every GroupUser, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code GroupUser} with no attributes set.
   */
  public GroupUser() {
    this(null, null);
  }

  /**
   * Creates a {@code GroupUser} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public GroupUser(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code GroupUser} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public GroupUser(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Group name
   */
  @JsonProperty("group_name")
  public String groupName;

  /**
   * Gets {@link #groupName}.
   *
   * @return the value of {@link #groupName}
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Sets {@link #groupName}.
   *
   * @param groupName the new value
   */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * Group ID
   */
  @JsonProperty("group_id")
  public Long groupId;

  /**
   * Gets {@link #groupId}.
   *
   * @return the value of {@link #groupId}
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * Sets {@link #groupId}.
   *
   * @param groupId the new value
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
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
   * Is this user an administrator of this group?
   */
  @JsonProperty("admin")
  public Boolean admin;

  /**
   * Gets {@link #admin}.
   *
   * @return the value of {@link #admin}
   */
  public Boolean getAdmin() {
    return admin;
  }

  /**
   * Sets {@link #admin}.
   *
   * @param admin the new value
   */
  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  /**
   * Username of the user
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
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Group User ID.
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
   * Updates this GroupUser. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID to add user to.</li>
   * <li>{@code user_id} (Long): Required. User ID to add to group.</li>
   * <li>{@code admin} (Boolean): Is the user a group administrator?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code GroupUser}
   */
  public GroupUser update(HashMap<String, Object> parameters) throws IOException {
    GroupUser updated = filesClient().groupUsers().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID from which to remove user.</li>
   * <li>{@code user_id} (Long): Required. User ID to remove from group.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().groupUsers().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Group User
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this GroupUser: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().groupUsers().create(parameters, this.options));
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
  private void refreshFrom(GroupUser canonical) {
    this.groupName = canonical.groupName;
    this.groupId = canonical.groupId;
    this.userId = canonical.userId;
    this.admin = canonical.admin;
    this.username = canonical.username;
    this.id = canonical.id;
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GroupUser> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GroupUser> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Group Users
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code group_id} (Long): Group ID. If provided, returns memberships of this group. Requires a Site Administrator, a Read-only Administrator, a Workspace Administrator for the group's workspace, or a Group Administrator of this group.</li>
   * <li>{@code user_id} (Long): User ID.  If provided, will return group_users of this user.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GroupUser} records, in a {@code ListIterator}
   */
  public static ListIterator<GroupUser> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groupUsers().list(parameters, options);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GroupUser> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Group Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<GroupUser> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Group User
   *
   * @see #create(HashMap, HashMap)
   */
  public static GroupUser create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Group User
   *
   * @see #create(HashMap, HashMap)
   */
  public static GroupUser create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID to add user to.</li>
   * <li>{@code user_id} (Long): Required. User ID to add to group.</li>
   * <li>{@code admin} (Boolean): Is the user a group administrator?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GroupUser}
   */
  public static GroupUser create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groupUsers().create(parameters, options);
  }


  /**
   * Update Group User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static GroupUser update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Group User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static GroupUser update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Group User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static GroupUser update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID to add user to.</li>
   * <li>{@code user_id} (Long): Required. User ID to add to group.</li>
   * <li>{@code admin} (Boolean): Is the user a group administrator?</li>
   * </ul>
   *
   * @param id Group User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GroupUser}
   */
  public static GroupUser update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groupUsers().update(id, parameters, options);
  }


  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Group User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code group_id} (Long): Required. Group ID from which to remove user.</li>
   * <li>{@code user_id} (Long): Required. User ID to remove from group.</li>
   * </ul>
   *
   * @param id Group User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().groupUsers().delete(id, parameters, options);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Group User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
