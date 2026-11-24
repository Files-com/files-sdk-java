package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.IntegrationCentricProfile;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * IntegrationCentricProfile operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#integrationCentricProfiles()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.IntegrationCentricProfile} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class IntegrationCentricProfileOperations extends ResourceOperations {
  public IntegrationCentricProfileOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IntegrationCentricProfile> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IntegrationCentricProfile> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public ListIterator<IntegrationCentricProfile> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/integration_centric_profiles";
    TypeReference<List<IntegrationCentricProfile>> typeReference = new TypeReference<List<IntegrationCentricProfile>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IntegrationCentricProfile> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Integration Centric Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IntegrationCentricProfile> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public IntegrationCentricProfile find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/integration_centric_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<IntegrationCentricProfile> typeReference = new TypeReference<IntegrationCentricProfile>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Integration Centric Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Integration Centric Profile
   *
   * @see #create(HashMap, HashMap)
   */
  public IntegrationCentricProfile create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Integration Centric Profile
   *
   * @see #create(HashMap, HashMap)
   */
  public IntegrationCentricProfile create(HashMap<String, Object> parameters) throws RuntimeException {
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
  public IntegrationCentricProfile create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("expected_remote_servers") || parameters.get("expected_remote_servers") == null) {
      throw new NullPointerException("Parameter missing: expected_remote_servers parameters[\"expected_remote_servers\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("expected_remote_servers") && !(parameters.get("expected_remote_servers") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: expected_remote_servers must be of type Object[] parameters[\"expected_remote_servers\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("use_for_all_users") && !(parameters.get("use_for_all_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_for_all_users must be of type Boolean parameters[\"use_for_all_users\"]");
    }

    String apiPath = "/integration_centric_profiles";
    TypeReference<IntegrationCentricProfile> typeReference = new TypeReference<IntegrationCentricProfile>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Integration Centric Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Integration Centric Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Integration Centric Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public IntegrationCentricProfile update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public IntegrationCentricProfile update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("expected_remote_servers") && !(parameters.get("expected_remote_servers") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: expected_remote_servers must be of type Object[] parameters[\"expected_remote_servers\"]");
    }

    if (parameters.containsKey("use_for_all_users") && !(parameters.get("use_for_all_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_for_all_users must be of type Boolean parameters[\"use_for_all_users\"]");
    }

    String apiPath = String.format("/integration_centric_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<IntegrationCentricProfile> typeReference = new TypeReference<IntegrationCentricProfile>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @param id Integration Centric Profile ID.
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

    String apiPath = String.format("/integration_centric_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Integration Centric Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
