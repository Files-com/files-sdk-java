package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationConnection implements ModelInterface {
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

  // One mapper for every ApplicationConnection, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ApplicationConnection} with no attributes set.
   */
  public ApplicationConnection() {
    this(null, null);
  }

  /**
   * Creates a {@code ApplicationConnection} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ApplicationConnection(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ApplicationConnection} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public ApplicationConnection(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Connection ID.
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
   * Workspace that owns this connection.
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
   * Connection display name.
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
   * Application key from the Automation applications list.
   */
  @JsonProperty("application_key")
  public String applicationKey;

  /**
   * Gets {@link #applicationKey}.
   *
   * @return the value of {@link #applicationKey}
   */
  public String getApplicationKey() {
    return applicationKey;
  }

  /**
   * Sets {@link #applicationKey}.
   *
   * @param applicationKey the new value
   */
  public void setApplicationKey(String applicationKey) {
    this.applicationKey = applicationKey;
  }

  /**
   * HTTPS origin for this connection.
   */
  @JsonProperty("origin")
  public String origin;

  /**
   * Gets {@link #origin}.
   *
   * @return the value of {@link #origin}
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Sets {@link #origin}.
   *
   * @param origin the new value
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * ID of the credential in the Secret Store, if the connection uses a credential.
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
   * Current credential type and placement, without the secret value.
   */
  @JsonProperty("authentication")
  public Object authentication;

  /**
   * Gets {@link #authentication}.
   *
   * @return the value of {@link #authentication}
   */
  public Object getAuthentication() {
    return authentication;
  }

  /**
   * Sets {@link #authentication}.
   *
   * @param authentication the new value
   */
  public void setAuthentication(Object authentication) {
    this.authentication = authentication;
  }

  /**
   * Time the connection was created.
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
   * Time the connection was updated.
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
   * Updates this ApplicationConnection. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Application Connection
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Connection display name.</li>
   * <li>{@code secret_id} (Long): ID of the credential in the Secret Store, if the connection uses a credential.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ApplicationConnection}
   */
  public ApplicationConnection update(HashMap<String, Object> parameters) throws IOException {
    ApplicationConnection updated = filesClient().applicationConnections().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Application Connection
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().applicationConnections().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this ApplicationConnection: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().applicationConnections().create(parameters, this.options));
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
  private void refreshFrom(ApplicationConnection canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.applicationKey = canonical.applicationKey;
    this.origin = canonical.origin;
    this.secretId = canonical.secretId;
    this.authentication = canonical.authentication;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApplicationConnection> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApplicationConnection> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Application Connections
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `application_key`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id` and `application_key`. Valid field combinations are `[ workspace_id, application_key ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `application_key`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection} records, in a {@code ListIterator}
   */
  public static ListIterator<ApplicationConnection> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().applicationConnections().list(parameters, options);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApplicationConnection> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Application Connections
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ApplicationConnection> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApplicationConnection find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApplicationConnection find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApplicationConnection find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Application Connection
   *
   * @param id Application Connection ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection}
   */
  public static ApplicationConnection find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().applicationConnections().find(id, parameters, options);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApplicationConnection get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Application Connection
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ApplicationConnection get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Application Connection
   *
   * @see #create(HashMap, HashMap)
   */
  public static ApplicationConnection create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Application Connection
   *
   * @see #create(HashMap, HashMap)
   */
  public static ApplicationConnection create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Application Connection
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Connection display name.</li>
   * <li>{@code secret_id} (Long): ID of the credential in the Secret Store, if the connection uses a credential.</li>
   * <li>{@code application_key} (String): Required. Application key from the Automation applications list.</li>
   * <li>{@code origin} (String): Required. HTTPS origin for this connection.</li>
   * <li>{@code workspace_id} (Long): Workspace that owns this connection.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection}
   */
  public static ApplicationConnection create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().applicationConnections().create(parameters, options);
  }


  /**
   * Update Application Connection
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ApplicationConnection update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Application Connection
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ApplicationConnection update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Application Connection
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ApplicationConnection update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Application Connection
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Connection display name.</li>
   * <li>{@code secret_id} (Long): ID of the credential in the Secret Store, if the connection uses a credential.</li>
   * </ul>
   *
   * @param id Application Connection ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApplicationConnection}
   */
  public static ApplicationConnection update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().applicationConnections().update(id, parameters, options);
  }


  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Application Connection
   *
   * @param id Application Connection ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().applicationConnections().delete(id, parameters, options);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Application Connection
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
