package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
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
public class AiAssistantPersonality implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public AiAssistantPersonality() {
    this(null, null);
  }

  public AiAssistantPersonality(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public AiAssistantPersonality(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * AI Assistant Personality ID.
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Workspace ID. `0` means the default workspace.
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
  * System prompt injected into the in-app AI Assistant.
  */
  @JsonProperty("system_prompt")
  public String systemPrompt;

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public void setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  /**
  * Whether this personality is the default personality for the Workspace.
  */
  @JsonProperty("use_by_default")
  public Boolean useByDefault;

  public Boolean getUseByDefault() {
    return useByDefault;
  }

  public void setUseByDefault(Boolean useByDefault) {
    this.useByDefault = useByDefault;
  }

  /**
  * If true, this default-workspace personality can apply to users in all workspaces.
  */
  @JsonProperty("apply_to_all_workspaces")
  public Boolean applyToAllWorkspaces;

  public Boolean getApplyToAllWorkspaces() {
    return applyToAllWorkspaces;
  }

  public void setApplyToAllWorkspaces(Boolean applyToAllWorkspaces) {
    this.applyToAllWorkspaces = applyToAllWorkspaces;
  }

  /**
  * Creation time.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Last update time.
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
  * Parameters:
  *   apply_to_all_workspaces - boolean - If true, this default-workspace personality can apply to users in all workspaces.
  *   system_prompt - string - System prompt injected into the in-app AI Assistant.
  *   use_by_default - boolean - Whether this personality is the default personality for the Workspace.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public AiAssistantPersonality update(HashMap<String, Object> parameters) throws IOException {
    return AiAssistantPersonality.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    AiAssistantPersonality.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    AiAssistantPersonality.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
  */
  public static ListIterator<AiAssistantPersonality> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }


    String url = String.format("%s%s/ai_assistant_personalities", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<AiAssistantPersonality>> typeReference = new TypeReference<List<AiAssistantPersonality>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<AiAssistantPersonality> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<AiAssistantPersonality> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Ai Assistant Personality ID.
  */
  public static AiAssistantPersonality find() throws RuntimeException {
    return find(null, null, null);
  }

  public static AiAssistantPersonality find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static AiAssistantPersonality find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static AiAssistantPersonality find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/ai_assistant_personalities/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<AiAssistantPersonality> typeReference = new TypeReference<AiAssistantPersonality>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static AiAssistantPersonality get() throws RuntimeException {
    return get(null, null, null);
  }

  public static AiAssistantPersonality get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   apply_to_all_workspaces - boolean - If true, this default-workspace personality can apply to users in all workspaces.
  *   system_prompt (required) - string - System prompt injected into the in-app AI Assistant.
  *   use_by_default - boolean - Whether this personality is the default personality for the Workspace.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public static AiAssistantPersonality create() throws RuntimeException {
    return create(null, null);
  }

  public static AiAssistantPersonality create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static AiAssistantPersonality create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("system_prompt") || parameters.get("system_prompt") == null) {
      throw new NullPointerException("Parameter missing: system_prompt parameters[\"system_prompt\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }
    if (parameters.containsKey("system_prompt") && !(parameters.get("system_prompt") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: system_prompt must be of type String parameters[\"system_prompt\"]");
    }
    if (parameters.containsKey("use_by_default") && !(parameters.get("use_by_default") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_by_default must be of type Boolean parameters[\"use_by_default\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }


    String url = String.format("%s%s/ai_assistant_personalities", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<AiAssistantPersonality> typeReference = new TypeReference<AiAssistantPersonality>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   apply_to_all_workspaces - boolean - If true, this default-workspace personality can apply to users in all workspaces.
  *   system_prompt - string - System prompt injected into the in-app AI Assistant.
  *   use_by_default - boolean - Whether this personality is the default personality for the Workspace.
  *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
  */
  public static AiAssistantPersonality update() throws RuntimeException {
    return update(null, null, null);
  }

  public static AiAssistantPersonality update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static AiAssistantPersonality update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static AiAssistantPersonality update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }
    if (parameters.containsKey("system_prompt") && !(parameters.get("system_prompt") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: system_prompt must be of type String parameters[\"system_prompt\"]");
    }
    if (parameters.containsKey("use_by_default") && !(parameters.get("use_by_default") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_by_default must be of type Boolean parameters[\"use_by_default\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }



    String url = String.format("%s%s/ai_assistant_personalities/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<AiAssistantPersonality> typeReference = new TypeReference<AiAssistantPersonality>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/ai_assistant_personalities/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
