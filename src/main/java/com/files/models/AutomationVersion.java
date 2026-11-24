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
public class AutomationVersion implements ModelInterface {
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

  // One mapper for every AutomationVersion, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code AutomationVersion} with no attributes set.
   */
  public AutomationVersion() {
    this(null, null);
  }

  /**
   * Creates a {@code AutomationVersion} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public AutomationVersion(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code AutomationVersion} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public AutomationVersion(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Automation revision ID.
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
   * ID of the Automation that owns this revision.
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
   * Workspace that owns the Automation. Zero is the Default Workspace.
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
   * Revision number within the Automation.
   */
  @JsonProperty("version")
  public Long version;

  /**
   * Gets {@link #version}.
   *
   * @return the value of {@link #version}
   */
  public Long getVersion() {
    return version;
  }

  /**
   * Time the revision was saved.
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
   * ID of the user who saved the revision, when available.
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
   * Username recorded when the revision was saved, when available.
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
   * ID of the API key used to save the revision, when available.
   */
  @JsonProperty("api_key_id")
  public Long apiKeyId;

  /**
   * Gets {@link #apiKeyId}.
   *
   * @return the value of {@link #apiKeyId}
   */
  public Long getApiKeyId() {
    return apiKeyId;
  }

  /**
   * Saved Automation v2 graph definition. Included when retrieving a single revision.
   */
  @JsonProperty("definition")
  public Object definition;

  /**
   * Gets {@link #definition}.
   *
   * @return the value of {@link #definition}
   */
  public Object getDefinition() {
    return definition;
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Automation}
   */
  public Automation restore(HashMap<String, Object> parameters) throws IOException {
    return filesClient().automationVersions().restore(this.id, parameters, this.options);
  }


  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AutomationVersion> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AutomationVersion> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Automation Versions
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * <li>{@code automation_id} (Long): Required. ID of the Automation that owns this revision.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AutomationVersion} records, in a {@code ListIterator}
   */
  public static ListIterator<AutomationVersion> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automationVersions().list(parameters, options);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AutomationVersion> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AutomationVersion> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AutomationVersion find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AutomationVersion find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AutomationVersion find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Automation Version
   *
   * @param id Automation Version ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AutomationVersion}
   */
  public static AutomationVersion find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automationVersions().find(id, parameters, options);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AutomationVersion get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AutomationVersion get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @see #restore(Long, HashMap, HashMap)
   */
  public static Automation restore() throws RuntimeException {
    return restore(null, null, null);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @see #restore(Long, HashMap, HashMap)
   */
  public static Automation restore(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return restore(id, parameters, null);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @see #restore(Long, HashMap, HashMap)
   */
  public static Automation restore(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return restore(null, parameters, options);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @param id Automation Version ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Automation}
   */
  public static Automation restore(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().automationVersions().restore(id, parameters, options);
  }


}
