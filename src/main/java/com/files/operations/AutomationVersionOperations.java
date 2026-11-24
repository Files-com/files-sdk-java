package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Automation;
import com.files.models.AutomationVersion;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AutomationVersion operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#automationVersions()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.AutomationVersion} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class AutomationVersionOperations extends ResourceOperations {
  public AutomationVersionOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AutomationVersion> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AutomationVersion> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public ListIterator<AutomationVersion> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("automation_id") || parameters.get("automation_id") == null) {
      throw new NullPointerException("Parameter missing: automation_id parameters[\"automation_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("automation_id") && !(parameters.get("automation_id") instanceof Long || parameters.get("automation_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: automation_id must be of type Long or Integer parameters[\"automation_id\"]");
    }

    String apiPath = "/automation_versions";
    TypeReference<List<AutomationVersion>> typeReference = new TypeReference<List<AutomationVersion>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AutomationVersion> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Automation Versions
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AutomationVersion> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AutomationVersion find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AutomationVersion find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AutomationVersion find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public AutomationVersion find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/automation_versions/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AutomationVersion> typeReference = new TypeReference<AutomationVersion>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AutomationVersion get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Automation Version
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AutomationVersion get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @see #restore(Long, HashMap, HashMap)
   */
  public Automation restore() throws RuntimeException {
    return restore(null, null, null);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @see #restore(Long, HashMap, HashMap)
   */
  public Automation restore(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return restore(id, parameters, null);
  }

  /**
   * Restore an Automation v2 definition as a new current version
   *
   * @see #restore(Long, HashMap, HashMap)
   */
  public Automation restore(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public Automation restore(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/automation_versions/%s/restore", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
