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
public class IntegrationCentricProfile implements ModelInterface {
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

  // One mapper for every IntegrationCentricProfile, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code IntegrationCentricProfile} with no attributes set.
   */
  public IntegrationCentricProfile() {
    this(null, null);
  }

  /**
   * Creates a {@code IntegrationCentricProfile} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public IntegrationCentricProfile(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code IntegrationCentricProfile} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public IntegrationCentricProfile(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Integration Centric Profile ID
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
   * Profile name
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
   * Workspace ID
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
   * Whether this profile applies to all users in the Workspace by default
   */
  @JsonProperty("use_for_all_users")
  public Boolean useForAllUsers;

  /**
   * Gets {@link #useForAllUsers}.
   *
   * @return the value of {@link #useForAllUsers}
   */
  public Boolean getUseForAllUsers() {
    return useForAllUsers;
  }

  /**
   * Sets {@link #useForAllUsers}.
   *
   * @param useForAllUsers the new value
   */
  public void setUseForAllUsers(Boolean useForAllUsers) {
    this.useForAllUsers = useForAllUsers;
  }

  /**
   * Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.
   */
  @JsonProperty("expected_remote_servers")
  public Object[] expectedRemoteServers;

  /**
   * Gets {@link #expectedRemoteServers}.
   *
   * @return the value of {@link #expectedRemoteServers}
   */
  public Object[] getExpectedRemoteServers() {
    return expectedRemoteServers;
  }

  /**
   * Sets {@link #expectedRemoteServers}.
   *
   * @param expectedRemoteServers the new value
   */
  public void setExpectedRemoteServers(Object[] expectedRemoteServers) {
    this.expectedRemoteServers = expectedRemoteServers;
  }

  /**
   * Updates this IntegrationCentricProfile. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Integration Centric Profile
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Profile name</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code expected_remote_servers} (Object[]): Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.</li>
   * <li>{@code use_for_all_users} (Boolean): Whether this profile applies to all users in the Workspace by default</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code IntegrationCentricProfile}
   */
  public IntegrationCentricProfile update(HashMap<String, Object> parameters) throws IOException {
    IntegrationCentricProfile updated = filesClient().integrationCentricProfiles().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Integration Centric Profile
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().integrationCentricProfiles().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this IntegrationCentricProfile: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().integrationCentricProfiles().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.expectedRemoteServers != null) {
      parameters.put("expected_remote_servers", this.expectedRemoteServers);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(IntegrationCentricProfile canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.workspaceId = canonical.workspaceId;
    this.useForAllUsers = canonical.useForAllUsers;
    this.expectedRemoteServers = canonical.expectedRemoteServers;
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IntegrationCentricProfile> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IntegrationCentricProfile> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Integration Centric Profiles
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code IntegrationCentricProfile} records, in a {@code ListIterator}
   */
  public static ListIterator<IntegrationCentricProfile> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().integrationCentricProfiles().list(parameters, options);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IntegrationCentricProfile> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<IntegrationCentricProfile> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Integration Centric Profile
   *
   * @param id Integration Centric Profile ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code IntegrationCentricProfile}
   */
  public static IntegrationCentricProfile find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().integrationCentricProfiles().find(id, parameters, options);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Integration Centric Profile
   *
   * @see #create(HashMap, HashMap)
   */
  public static IntegrationCentricProfile create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Integration Centric Profile
   *
   * @see #create(HashMap, HashMap)
   */
  public static IntegrationCentricProfile create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Integration Centric Profile
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Profile name</li>
   * <li>{@code expected_remote_servers} (Object[]): Required. Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code use_for_all_users} (Boolean): Whether this profile applies to all users in the Workspace by default</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code IntegrationCentricProfile}
   */
  public static IntegrationCentricProfile create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().integrationCentricProfiles().create(parameters, options);
  }


  /**
   * Update Integration Centric Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Integration Centric Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Integration Centric Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static IntegrationCentricProfile update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Integration Centric Profile
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Profile name</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code expected_remote_servers} (Object[]): Remote Server integrations the user is expected to add and connect. Each entry requires `server_type` and may include a display `name`.</li>
   * <li>{@code use_for_all_users} (Boolean): Whether this profile applies to all users in the Workspace by default</li>
   * </ul>
   *
   * @param id Integration Centric Profile ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code IntegrationCentricProfile}
   */
  public static IntegrationCentricProfile update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().integrationCentricProfiles().update(id, parameters, options);
  }


  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @param id Integration Centric Profile ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().integrationCentricProfiles().delete(id, parameters, options);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
