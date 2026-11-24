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
public class DatabaseDestination implements ModelInterface {
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

  // One mapper for every DatabaseDestination, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code DatabaseDestination} with no attributes set.
   */
  public DatabaseDestination() {
    this(null, null);
  }

  /**
   * Creates a {@code DatabaseDestination} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public DatabaseDestination(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code DatabaseDestination} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public DatabaseDestination(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Database Destination ID.
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
   * Workspace ID. 0 means the default workspace.
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
   * Name of the destination.
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
   * Database provider.
   */
  @JsonProperty("provider")
  public String provider;

  /**
   * Gets {@link #provider}.
   *
   * @return the value of {@link #provider}
   */
  public String getProvider() {
    return provider;
  }

  /**
   * Sets {@link #provider}.
   *
   * @param provider the new value
   */
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /**
   * Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.
   */
  @JsonProperty("config")
  public Object config;

  /**
   * Gets {@link #config}.
   *
   * @return the value of {@link #config}
   */
  public Object getConfig() {
    return config;
  }

  /**
   * Sets {@link #config}.
   *
   * @param config the new value
   */
  public void setConfig(Object config) {
    this.config = config;
  }

  /**
   * ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.
   */
  @JsonProperty("secret_id")
  public Long secretId;

  /**
   * Gets {@link #secretId}.
   *
   * @return the value of {@link #secretId}
   */
  public Long getSecretId() {
    return secretId;
  }

  /**
   * Sets {@link #secretId}.
   *
   * @param secretId the new value
   */
  public void setSecretId(Long secretId) {
    this.secretId = secretId;
  }

  /**
   * When the destination was created.
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
   * When the destination was last updated.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this DatabaseDestination. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Database Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the destination.</li>
   * <li>{@code secret_id} (Long): ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.</li>
   * <li>{@code config} (Object): Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code DatabaseDestination}
   */
  public DatabaseDestination update(HashMap<String, Object> parameters) throws IOException {
    DatabaseDestination updated = filesClient().databaseDestinations().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Database Destination
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().databaseDestinations().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this DatabaseDestination: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().databaseDestinations().create(parameters, this.options));
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
  private void refreshFrom(DatabaseDestination canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.provider = canonical.provider;
    this.config = canonical.config;
    this.secretId = canonical.secretId;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<DatabaseDestination> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<DatabaseDestination> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Database Destinations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `name`. Valid field combinations are `[ workspace_id, name ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination} records, in a {@code ListIterator}
   */
  public static ListIterator<DatabaseDestination> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().databaseDestinations().list(parameters, options);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<DatabaseDestination> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Database Destinations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<DatabaseDestination> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static DatabaseDestination find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static DatabaseDestination find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static DatabaseDestination find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Database Destination
   *
   * @param id Database Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination}
   */
  public static DatabaseDestination find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().databaseDestinations().find(id, parameters, options);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static DatabaseDestination get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Database Destination
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static DatabaseDestination get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Database Destination
   *
   * @see #create(HashMap, HashMap)
   */
  public static DatabaseDestination create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Database Destination
   *
   * @see #create(HashMap, HashMap)
   */
  public static DatabaseDestination create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Database Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name of the destination.</li>
   * <li>{@code provider} (String): Required. Database provider.</li>
   * <li>{@code config} (Object): Required. Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.</li>
   * <li>{@code secret_id} (Long): Required. ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. 0 means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination}
   */
  public static DatabaseDestination create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().databaseDestinations().create(parameters, options);
  }


  /**
   * Update Database Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static DatabaseDestination update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Database Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static DatabaseDestination update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Database Destination
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static DatabaseDestination update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Database Destination
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the destination.</li>
   * <li>{@code secret_id} (Long): ID of a Secret in the same Workspace. Use a Token Secret for Snowflake, a service account Key/Value Secret for BigQuery, or a Username/Password Secret for a database login.</li>
   * <li>{@code config} (Object): Provider settings for the destination. Use the schema matching provider. The connection address and table are fixed after creation.</li>
   * </ul>
   *
   * @param id Database Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DatabaseDestination}
   */
  public static DatabaseDestination update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().databaseDestinations().update(id, parameters, options);
  }


  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Database Destination
   *
   * @param id Database Destination ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().databaseDestinations().delete(id, parameters, options);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Database Destination
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
