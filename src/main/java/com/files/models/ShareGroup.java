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
public class ShareGroup implements ModelInterface {
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

  // One mapper for every ShareGroup, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ShareGroup} with no attributes set.
   */
  public ShareGroup() {
    this(null, null);
  }

  /**
   * Creates a {@code ShareGroup} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ShareGroup(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ShareGroup} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ShareGroup(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Share Group ID
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
   * Name of the share group
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Additional notes of the share group
   */
  @JsonProperty("notes")
  public String notes;

  /**
   * Gets {@link #notes}.
   *
   * @return the value of {@link #notes}
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Sets {@link #notes}.
   *
   * @param notes the new value
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * Owner User ID
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
   * A list of share group members
   */
  @JsonProperty("members")
  public ShareGroupMember[] members;

  /**
   * Gets {@link #members}.
   *
   * @return the value of {@link #members}
   */
  public ShareGroupMember[] getMembers() {
    return members;
  }

  /**
   * Sets {@link #members}.
   *
   * @param members the new value
   */
  public void setMembers(ShareGroupMember[] members) {
    this.members = members;
  }

  /**
   * Updates this ShareGroup. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Share Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Additional notes of the share group</li>
   * <li>{@code name} (String): Name of the share group</li>
   * <li>{@code members} (Object[]): A list of share group members.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ShareGroup}
   */
  public ShareGroup update(HashMap<String, Object> parameters) throws IOException {
    ShareGroup updated = filesClient().shareGroups().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Share Group
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().shareGroups().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this ShareGroup: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().shareGroups().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.members != null) {
      parameters.put("members", this.members);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(ShareGroup canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.notes = canonical.notes;
    this.userId = canonical.userId;
    this.members = canonical.members;
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ShareGroup> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ShareGroup> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Share Groups
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup} records, in a {@code ListIterator}
   */
  public static ListIterator<ShareGroup> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().shareGroups().list(parameters, options);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ShareGroup> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Share Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ShareGroup> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ShareGroup find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ShareGroup find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ShareGroup find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Share Group
   *
   * @param id Share Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup}
   */
  public static ShareGroup find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().shareGroups().find(id, parameters, options);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ShareGroup get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Share Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ShareGroup get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Share Group
   *
   * @see #create(HashMap, HashMap)
   */
  public static ShareGroup create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Share Group
   *
   * @see #create(HashMap, HashMap)
   */
  public static ShareGroup create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Share Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code notes} (String): Additional notes of the share group</li>
   * <li>{@code name} (String): Required. Name of the share group</li>
   * <li>{@code members} (Object[]): Required. A list of share group members.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup}
   */
  public static ShareGroup create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().shareGroups().create(parameters, options);
  }


  /**
   * Update Share Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ShareGroup update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Share Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ShareGroup update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Share Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ShareGroup update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Share Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Additional notes of the share group</li>
   * <li>{@code name} (String): Name of the share group</li>
   * <li>{@code members} (Object[]): A list of share group members.</li>
   * </ul>
   *
   * @param id Share Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ShareGroup}
   */
  public static ShareGroup update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().shareGroups().update(id, parameters, options);
  }


  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Share Group
   *
   * @param id Share Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().shareGroups().delete(id, parameters, options);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Share Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
