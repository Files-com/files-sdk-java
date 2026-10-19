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
public class AiAssistantPersonality implements ModelInterface {
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

  // One mapper for every AiAssistantPersonality, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code AiAssistantPersonality} with no attributes set.
   */
  public AiAssistantPersonality() {
    this(null, null);
  }

  /**
   * Creates a {@code AiAssistantPersonality} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public AiAssistantPersonality(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code AiAssistantPersonality} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public AiAssistantPersonality(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * AI Assistant Personality ID.
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
   * Workspace ID. `0` means the default workspace.
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
   * AI Assistant Personality name.
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
   * System prompt injected into the in-app AI Assistant.
   */
  @JsonProperty("system_prompt")
  public String systemPrompt;

  /**
   * Gets {@link #systemPrompt}.
   *
   * @return the value of {@link #systemPrompt}
   */
  public String getSystemPrompt() {
    return systemPrompt;
  }

  /**
   * Sets {@link #systemPrompt}.
   *
   * @param systemPrompt the new value
   */
  public void setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  /**
   * Whether this personality is the default personality for the Workspace.
   */
  @JsonProperty("use_by_default")
  public Boolean useByDefault;

  /**
   * Gets {@link #useByDefault}.
   *
   * @return the value of {@link #useByDefault}
   */
  public Boolean getUseByDefault() {
    return useByDefault;
  }

  /**
   * Sets {@link #useByDefault}.
   *
   * @param useByDefault the new value
   */
  public void setUseByDefault(Boolean useByDefault) {
    this.useByDefault = useByDefault;
  }

  /**
   * If true, this default-workspace personality can apply to users in all workspaces.
   */
  @JsonProperty("apply_to_all_workspaces")
  public Boolean applyToAllWorkspaces;

  /**
   * Gets {@link #applyToAllWorkspaces}.
   *
   * @return the value of {@link #applyToAllWorkspaces}
   */
  public Boolean getApplyToAllWorkspaces() {
    return applyToAllWorkspaces;
  }

  /**
   * Sets {@link #applyToAllWorkspaces}.
   *
   * @param applyToAllWorkspaces the new value
   */
  public void setApplyToAllWorkspaces(Boolean applyToAllWorkspaces) {
    this.applyToAllWorkspaces = applyToAllWorkspaces;
  }

  /**
   * Creation time.
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
   * Last update time.
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
   * Updates this AiAssistantPersonality. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Ai Assistant Personality
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace personality can apply to users in all workspaces.</li>
   * <li>{@code name} (String): AI Assistant Personality name.</li>
   * <li>{@code system_prompt} (String): System prompt injected into the in-app AI Assistant.</li>
   * <li>{@code use_by_default} (Boolean): Whether this personality is the default personality for the Workspace.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code AiAssistantPersonality}
   */
  public AiAssistantPersonality update(HashMap<String, Object> parameters) throws IOException {
    AiAssistantPersonality updated = filesClient().aiAssistantPersonalities().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().aiAssistantPersonalities().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this AiAssistantPersonality: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().aiAssistantPersonalities().create(parameters, this.options));
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
  private void refreshFrom(AiAssistantPersonality canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.name = canonical.name;
    this.systemPrompt = canonical.systemPrompt;
    this.useByDefault = canonical.useByDefault;
    this.applyToAllWorkspaces = canonical.applyToAllWorkspaces;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiAssistantPersonality> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Ai Assistant Personalities
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiAssistantPersonality} records, in a {@code ListIterator}
   */
  public static ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiAssistantPersonalities().list(parameters, options);
  }

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiAssistantPersonality> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<AiAssistantPersonality> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @param id Ai Assistant Personality ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiAssistantPersonality}
   */
  public static AiAssistantPersonality find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiAssistantPersonalities().find(id, parameters, options);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Ai Assistant Personality
   *
   * @see #create(HashMap, HashMap)
   */
  public static AiAssistantPersonality create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Ai Assistant Personality
   *
   * @see #create(HashMap, HashMap)
   */
  public static AiAssistantPersonality create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Ai Assistant Personality
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace personality can apply to users in all workspaces.</li>
   * <li>{@code name} (String): Required. AI Assistant Personality name.</li>
   * <li>{@code system_prompt} (String): Required. System prompt injected into the in-app AI Assistant.</li>
   * <li>{@code use_by_default} (Boolean): Whether this personality is the default personality for the Workspace.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiAssistantPersonality}
   */
  public static AiAssistantPersonality create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiAssistantPersonalities().create(parameters, options);
  }


  /**
   * Update Ai Assistant Personality
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Ai Assistant Personality
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Ai Assistant Personality
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static AiAssistantPersonality update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Ai Assistant Personality
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, this default-workspace personality can apply to users in all workspaces.</li>
   * <li>{@code name} (String): AI Assistant Personality name.</li>
   * <li>{@code system_prompt} (String): System prompt injected into the in-app AI Assistant.</li>
   * <li>{@code use_by_default} (Boolean): Whether this personality is the default personality for the Workspace.</li>
   * <li>{@code workspace_id} (Long): Workspace ID. `0` means the default workspace.</li>
   * </ul>
   *
   * @param id Ai Assistant Personality ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AiAssistantPersonality}
   */
  public static AiAssistantPersonality update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().aiAssistantPersonalities().update(id, parameters, options);
  }


  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @param id Ai Assistant Personality ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().aiAssistantPersonalities().delete(id, parameters, options);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
