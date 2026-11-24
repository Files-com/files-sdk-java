package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.AiAssistantPersonality;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

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
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AiAssistantPersonality> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AiAssistantPersonality> list(HashMap<String, Object> parameters) throws RuntimeException {
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

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AiAssistantPersonality> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Ai Assistant Personalities
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AiAssistantPersonality> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Ai Assistant Personality
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Ai Assistant Personality
   *
   * @see #create(HashMap, HashMap)
   */
  public AiAssistantPersonality create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Ai Assistant Personality
   *
   * @see #create(HashMap, HashMap)
   */
  public AiAssistantPersonality create(HashMap<String, Object> parameters) throws RuntimeException {
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
   * Update Ai Assistant Personality
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Ai Assistant Personality
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Ai Assistant Personality
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public AiAssistantPersonality update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @param id Ai Assistant Personality ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Ai Assistant Personality
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
