package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.AiAssistantPersonality;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AiAssistantPersonality operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#aiAssistantPersonalities()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.AiAssistantPersonality} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class AiAssistantPersonalityOperations extends ResourceOperations {
  public AiAssistantPersonalityOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public ListIterator<AiAssistantPersonality> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    String apiPath = "/ai_assistant_personalities";
    TypeReference<List<AiAssistantPersonality>> typeReference = new TypeReference<List<AiAssistantPersonality>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<AiAssistantPersonality> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<AiAssistantPersonality> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Ai Assistant Personality ID.
   */
  public AiAssistantPersonality find() throws RuntimeException {
    return find(null, null, null);
  }

  public AiAssistantPersonality find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public AiAssistantPersonality find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public AiAssistantPersonality find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/ai_assistant_personalities/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AiAssistantPersonality> typeReference = new TypeReference<AiAssistantPersonality>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public AiAssistantPersonality get() throws RuntimeException {
    return get(null, null, null);
  }

  public AiAssistantPersonality get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   apply_to_all_workspaces - boolean - If true, this default-workspace personality can apply to users in all workspaces.
   *   name (required) - string - AI Assistant Personality name.
   *   system_prompt (required) - string - System prompt injected into the in-app AI Assistant.
   *   use_by_default - boolean - Whether this personality is the default personality for the Workspace.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public AiAssistantPersonality create() throws RuntimeException {
    return create(null, null);
  }

  public AiAssistantPersonality create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public AiAssistantPersonality create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("system_prompt") || parameters.get("system_prompt") == null) {
      throw new NullPointerException("Parameter missing: system_prompt parameters[\"system_prompt\"]");
    }

    if (parameters.containsKey("apply_to_all_workspaces") && !(parameters.get("apply_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: apply_to_all_workspaces must be of type Boolean parameters[\"apply_to_all_workspaces\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
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

    String apiPath = "/ai_assistant_personalities";
    TypeReference<AiAssistantPersonality> typeReference = new TypeReference<AiAssistantPersonality>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   apply_to_all_workspaces - boolean - If true, this default-workspace personality can apply to users in all workspaces.
   *   name - string - AI Assistant Personality name.
   *   system_prompt - string - System prompt injected into the in-app AI Assistant.
   *   use_by_default - boolean - Whether this personality is the default personality for the Workspace.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public AiAssistantPersonality update() throws RuntimeException {
    return update(null, null, null);
  }

  public AiAssistantPersonality update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public AiAssistantPersonality update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public AiAssistantPersonality update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
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

    String apiPath = String.format("/ai_assistant_personalities/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AiAssistantPersonality> typeReference = new TypeReference<AiAssistantPersonality>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/ai_assistant_personalities/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
