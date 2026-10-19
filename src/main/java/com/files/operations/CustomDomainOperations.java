package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.CustomDomain;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * CustomDomain operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#customDomains()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.CustomDomain} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class CustomDomainOperations extends ResourceOperations {
  public CustomDomainOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<CustomDomain> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<CustomDomain> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Custom Domains
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `id` or `available_to_all_workspaces`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain} records, in a {@code ListIterator}
   */
  public ListIterator<CustomDomain> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/custom_domains";
    TypeReference<List<CustomDomain>> typeReference = new TypeReference<List<CustomDomain>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<CustomDomain> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Custom Domains
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<CustomDomain> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public CustomDomain find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public CustomDomain find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public CustomDomain find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Custom Domain
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public CustomDomain find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/custom_domains/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public CustomDomain get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Custom Domain
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public CustomDomain get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   *
   * @see #createAllocateIp(Long, HashMap, HashMap)
   */
  public CustomDomain createAllocateIp() throws RuntimeException {
    return createAllocateIp(null, null, null);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   *
   * @see #createAllocateIp(Long, HashMap, HashMap)
   */
  public CustomDomain createAllocateIp(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return createAllocateIp(id, parameters, null);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   *
   * @see #createAllocateIp(Long, HashMap, HashMap)
   */
  public CustomDomain createAllocateIp(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return createAllocateIp(null, parameters, options);
  }

  /**
   * Allocate dedicated IP addresses to this Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code count} (Long): Required. Number of dedicated IP addresses to allocate.</li>
   * </ul>
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public CustomDomain createAllocateIp(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!parameters.containsKey("count") || parameters.get("count") == null) {
      throw new NullPointerException("Parameter missing: count parameters[\"count\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("count") && !(parameters.get("count") instanceof Long || parameters.get("count") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: count must be of type Long or Integer parameters[\"count\"]");
    }

    String apiPath = String.format("/custom_domains/%s/allocate_ips", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Create Custom Domain
   *
   * @see #create(HashMap, HashMap)
   */
  public CustomDomain create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Custom Domain
   *
   * @see #create(HashMap, HashMap)
   */
  public CustomDomain create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code available_to_all_workspaces} (Boolean): Allow all workspaces to use this default-workspace Custom Domain.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for the default workspace).</li>
   * <li>{@code destination} (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.</li>
   * <li>{@code folder_behavior_id} (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.</li>
   * <li>{@code ssl_certificate_id} (Long): Current SSL certificate ID.</li>
   * <li>{@code domain} (String): Required. Customer-owned domain name.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public CustomDomain create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("domain") || parameters.get("domain") == null) {
      throw new NullPointerException("Parameter missing: domain parameters[\"domain\"]");
    }

    if (parameters.containsKey("available_to_all_workspaces") && !(parameters.get("available_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: available_to_all_workspaces must be of type Boolean parameters[\"available_to_all_workspaces\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("folder_behavior_id") && !(parameters.get("folder_behavior_id") instanceof Long || parameters.get("folder_behavior_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: folder_behavior_id must be of type Long or Integer parameters[\"folder_behavior_id\"]");
    }

    if (parameters.containsKey("ssl_certificate_id") && !(parameters.get("ssl_certificate_id") instanceof Long || parameters.get("ssl_certificate_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate_id must be of type Long or Integer parameters[\"ssl_certificate_id\"]");
    }

    if (parameters.containsKey("domain") && !(parameters.get("domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: domain must be of type String parameters[\"domain\"]");
    }

    String apiPath = "/custom_domains";
    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Custom Domain
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public CustomDomain update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Custom Domain
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public CustomDomain update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Custom Domain
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public CustomDomain update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Custom Domain
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code available_to_all_workspaces} (Boolean): Allow all workspaces to use this default-workspace Custom Domain.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for the default workspace).</li>
   * <li>{@code destination} (String): Where this custom domain routes. Can be `site_alias`, `public_hosting`, `s3_endpoint`, or `unassigned` (not routing traffic). Set to `unassigned` automatically when a bound `public_hosting` folder behavior is deleted, and can be set manually via the API for any reason.</li>
   * <li>{@code folder_behavior_id} (Long): Public Hosting behavior ID when this domain routes to a specific Public Hosting behavior.  Preserved as historical context when `destination` becomes `unassigned`.</li>
   * <li>{@code ssl_certificate_id} (Long): Current SSL certificate ID.</li>
   * <li>{@code domain} (String): Customer-owned domain name.</li>
   * </ul>
   *
   * @param id Custom Domain ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code CustomDomain}
   */
  public CustomDomain update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("available_to_all_workspaces") && !(parameters.get("available_to_all_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: available_to_all_workspaces must be of type Boolean parameters[\"available_to_all_workspaces\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("folder_behavior_id") && !(parameters.get("folder_behavior_id") instanceof Long || parameters.get("folder_behavior_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: folder_behavior_id must be of type Long or Integer parameters[\"folder_behavior_id\"]");
    }

    if (parameters.containsKey("ssl_certificate_id") && !(parameters.get("ssl_certificate_id") instanceof Long || parameters.get("ssl_certificate_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ssl_certificate_id must be of type Long or Integer parameters[\"ssl_certificate_id\"]");
    }

    if (parameters.containsKey("domain") && !(parameters.get("domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: domain must be of type String parameters[\"domain\"]");
    }

    String apiPath = String.format("/custom_domains/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<CustomDomain> typeReference = new TypeReference<CustomDomain>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Custom Domain
   *
   * @param id Custom Domain ID.
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

    String apiPath = String.format("/custom_domains/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Custom Domain
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
