package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Clickwrap;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Clickwrap operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#clickwraps()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Clickwrap} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ClickwrapOperations extends ResourceOperations {
  public ClickwrapOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Clickwrap> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Clickwrap> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Clickwraps
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap} records, in a {@code ListIterator}
   */
  public ListIterator<Clickwrap> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/clickwraps";
    TypeReference<List<Clickwrap>> typeReference = new TypeReference<List<Clickwrap>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Clickwrap> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Clickwrap> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Clickwrap find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Clickwrap find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Clickwrap find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Clickwrap
   *
   * @param id Clickwrap ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap}
   */
  public Clickwrap find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/clickwraps/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Clickwrap> typeReference = new TypeReference<Clickwrap>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Clickwrap get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Clickwrap get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Clickwrap
   *
   * @see #create(HashMap, HashMap)
   */
  public Clickwrap create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Clickwrap
   *
   * @see #create(HashMap, HashMap)
   */
  public Clickwrap create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Clickwrap
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)</li>
   * <li>{@code body} (String): Body text of Clickwrap (supports Markdown formatting).</li>
   * <li>{@code use_with_bundles} (String): Use this Clickwrap for Bundles?</li>
   * <li>{@code use_with_inboxes} (String): Use this Clickwrap for Inboxes?</li>
   * <li>{@code use_with_users} (String): Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap}
   */
  public Clickwrap create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    if (parameters.containsKey("use_with_bundles") && !(parameters.get("use_with_bundles") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: use_with_bundles must be of type String parameters[\"use_with_bundles\"]");
    }

    if (parameters.containsKey("use_with_inboxes") && !(parameters.get("use_with_inboxes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: use_with_inboxes must be of type String parameters[\"use_with_inboxes\"]");
    }

    if (parameters.containsKey("use_with_users") && !(parameters.get("use_with_users") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: use_with_users must be of type String parameters[\"use_with_users\"]");
    }

    String apiPath = "/clickwraps";
    TypeReference<Clickwrap> typeReference = new TypeReference<Clickwrap>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Clickwrap
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Clickwrap update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Clickwrap
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Clickwrap update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Clickwrap
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Clickwrap update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Clickwrap
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)</li>
   * <li>{@code body} (String): Body text of Clickwrap (supports Markdown formatting).</li>
   * <li>{@code use_with_bundles} (String): Use this Clickwrap for Bundles?</li>
   * <li>{@code use_with_inboxes} (String): Use this Clickwrap for Inboxes?</li>
   * <li>{@code use_with_users} (String): Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).</li>
   * </ul>
   *
   * @param id Clickwrap ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap}
   */
  public Clickwrap update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type String parameters[\"body\"]");
    }

    if (parameters.containsKey("use_with_bundles") && !(parameters.get("use_with_bundles") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: use_with_bundles must be of type String parameters[\"use_with_bundles\"]");
    }

    if (parameters.containsKey("use_with_inboxes") && !(parameters.get("use_with_inboxes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: use_with_inboxes must be of type String parameters[\"use_with_inboxes\"]");
    }

    if (parameters.containsKey("use_with_users") && !(parameters.get("use_with_users") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: use_with_users must be of type String parameters[\"use_with_users\"]");
    }

    String apiPath = String.format("/clickwraps/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Clickwrap> typeReference = new TypeReference<Clickwrap>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Clickwrap
   *
   * @param id Clickwrap ID.
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

    String apiPath = String.format("/clickwraps/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
