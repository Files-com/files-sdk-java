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
public class EventTarget implements ModelInterface {
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


  public EventTarget() {
    this(null, null);
  }

  public EventTarget(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public EventTarget(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Event Target ID
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
  * Event Target name.
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * Event Target type.
  */
  @JsonProperty("target_type")
  public String targetType;

  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  /**
  * Workspace ID. 0 means the default workspace or site-wide.
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
  * If true, this default-workspace target can receive events from all workspaces.
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
  * Whether this Event Target can receive events.
  */
  @JsonProperty("enabled")
  public Boolean enabled;

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
  * Event Target configuration.
  */
  @JsonProperty("config")
  public Object config;

  public Object getConfig() {
    return config;
  }

  public void setConfig(Object config) {
    this.config = config;
  }

  /**
  * Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.
  */
  @JsonProperty("delivery_policy")
  public Object deliveryPolicy;

  public Object getDeliveryPolicy() {
    return deliveryPolicy;
  }

  public void setDeliveryPolicy(Object deliveryPolicy) {
    this.deliveryPolicy = deliveryPolicy;
  }

  /**
  * Event Target create date/time.
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Event Target update date/time.
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
  * Parameters:
  *   name - string - Event Target name.
  *   workspace_id - int64 - Workspace ID. 0 means the default workspace or site-wide.
  *   apply_to_all_workspaces - boolean - If true, this default-workspace target can receive events from all workspaces.
  *   target_type - string - Event Target type.
  *   enabled - boolean - Whether this Event Target can receive events.
  *   config - object - Event Target configuration.
  *   delivery_policy - object - Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.
  */
  public EventTarget update(HashMap<String, Object> parameters) throws IOException {
    return EventTarget.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    EventTarget.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    EventTarget.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `enabled` and `workspace_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `enabled`, `target_type` or `workspace_id`. Valid field combinations are `[ enabled, target_type ]`, `[ workspace_id, enabled ]` or `[ workspace_id, enabled, target_type ]`.
  */
  public static ListIterator<EventTarget> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<EventTarget> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<EventTarget> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/event_targets", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<EventTarget>> typeReference = new TypeReference<List<EventTarget>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<EventTarget> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<EventTarget> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Event Target ID.
  */
  public static EventTarget find() throws RuntimeException {
    return find(null, null, null);
  }

  public static EventTarget find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static EventTarget find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static EventTarget find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/event_targets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<EventTarget> typeReference = new TypeReference<EventTarget>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static EventTarget get() throws RuntimeException {
    return get(null, null, null);
  }

  public static EventTarget get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name (required) - string - Event Target name.
  *   workspace_id - int64 - Workspace ID. 0 means the default workspace or site-wide.
  *   apply_to_all_workspaces - boolean - If true, this default-workspace target can receive events from all workspaces.
  *   target_type (required) - string - Event Target type.
  *   enabled - boolean - Whether this Event Target can receive events.
  *   config (required) - object - Event Target configuration.
  *   delivery_policy - object - Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.
  */
  public static EventTarget create() throws RuntimeException {
    return create(null, null);
  }

  public static EventTarget create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static EventTarget create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("target_type") || parameters.get("target_type") == null) {
      throw new NullPointerException("Parameter missing: target_type parameters[\"target_type\"]");
    }
    if (!parameters.containsKey("config") || parameters.get("config") == null) {
      throw new NullPointerException("Parameter missing: config parameters[\"config\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }
    if (parameters.containsKey("target_type") && !(parameters.get("target_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: target_type must be of type String parameters[\"target_type\"]");
    }
    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }
    if (parameters.containsKey("config") && !(parameters.get("config") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: config must be of type Object parameters[\"config\"]");
    }
    if (parameters.containsKey("delivery_policy") && !(parameters.get("delivery_policy") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: delivery_policy must be of type Object parameters[\"delivery_policy\"]");
    }


    String url = String.format("%s%s/event_targets", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<EventTarget> typeReference = new TypeReference<EventTarget>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Event Target name.
  *   workspace_id - int64 - Workspace ID. 0 means the default workspace or site-wide.
  *   apply_to_all_workspaces - boolean - If true, this default-workspace target can receive events from all workspaces.
  *   target_type - string - Event Target type.
  *   enabled - boolean - Whether this Event Target can receive events.
  *   config - object - Event Target configuration.
  *   delivery_policy - object - Event Target delivery policy. Email targets support batch_interval in seconds, between 600 and 86400.
  */
  public static EventTarget update() throws RuntimeException {
    return update(null, null, null);
  }

  public static EventTarget update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static EventTarget update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static EventTarget update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }
    if (parameters.containsKey("target_type") && !(parameters.get("target_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: target_type must be of type String parameters[\"target_type\"]");
    }
    if (parameters.containsKey("enabled") && !(parameters.get("enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enabled must be of type Boolean parameters[\"enabled\"]");
    }
    if (parameters.containsKey("config") && !(parameters.get("config") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: config must be of type Object parameters[\"config\"]");
    }
    if (parameters.containsKey("delivery_policy") && !(parameters.get("delivery_policy") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: delivery_policy must be of type Object parameters[\"delivery_policy\"]");
    }



    String url = String.format("%s%s/event_targets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<EventTarget> typeReference = new TypeReference<EventTarget>() {};
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



    String url = String.format("%s%s/event_targets/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
