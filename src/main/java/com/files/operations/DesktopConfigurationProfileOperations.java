package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.DesktopConfigurationProfile;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * DesktopConfigurationProfile operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#desktopConfigurationProfiles()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.DesktopConfigurationProfile} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class DesktopConfigurationProfileOperations extends ResourceOperations {
  public DesktopConfigurationProfileOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Desktop Configuration Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DesktopConfigurationProfile> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Desktop Configuration Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DesktopConfigurationProfile> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Desktop Configuration Profiles
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
   * @return the {@code DesktopConfigurationProfile} records, in a {@code ListIterator}
   */
  public ListIterator<DesktopConfigurationProfile> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/desktop_configuration_profiles";
    TypeReference<List<DesktopConfigurationProfile>> typeReference = new TypeReference<List<DesktopConfigurationProfile>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Desktop Configuration Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DesktopConfigurationProfile> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Desktop Configuration Profiles
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DesktopConfigurationProfile> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Desktop Configuration Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Desktop Configuration Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Desktop Configuration Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Desktop Configuration Profile
   *
   * @param id Desktop Configuration Profile ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DesktopConfigurationProfile}
   */
  public DesktopConfigurationProfile find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/desktop_configuration_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<DesktopConfigurationProfile> typeReference = new TypeReference<DesktopConfigurationProfile>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Desktop Configuration Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Desktop Configuration Profile
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Desktop Configuration Profile
   *
   * @see #create(HashMap, HashMap)
   */
  public DesktopConfigurationProfile create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Desktop Configuration Profile
   *
   * @see #create(HashMap, HashMap)
   */
  public DesktopConfigurationProfile create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Desktop Configuration Profile
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Profile name</li>
   * <li>{@code mount_mappings} (Object): Required. Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code use_for_all_users} (Boolean): Whether this profile applies to all users in the Workspace by default</li>
   * <li>{@code disable_drive_mounting} (Boolean): Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DesktopConfigurationProfile}
   */
  public DesktopConfigurationProfile create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("mount_mappings") || parameters.get("mount_mappings") == null) {
      throw new NullPointerException("Parameter missing: mount_mappings parameters[\"mount_mappings\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("mount_mappings") && !(parameters.get("mount_mappings") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: mount_mappings must be of type Object parameters[\"mount_mappings\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("use_for_all_users") && !(parameters.get("use_for_all_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_for_all_users must be of type Boolean parameters[\"use_for_all_users\"]");
    }

    if (parameters.containsKey("disable_drive_mounting") && !(parameters.get("disable_drive_mounting") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_drive_mounting must be of type Boolean parameters[\"disable_drive_mounting\"]");
    }

    String apiPath = "/desktop_configuration_profiles";
    TypeReference<DesktopConfigurationProfile> typeReference = new TypeReference<DesktopConfigurationProfile>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Desktop Configuration Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Desktop Configuration Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Desktop Configuration Profile
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public DesktopConfigurationProfile update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Desktop Configuration Profile
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Profile name</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code mount_mappings} (Object): Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.</li>
   * <li>{@code use_for_all_users} (Boolean): Whether this profile applies to all users in the Workspace by default</li>
   * <li>{@code disable_drive_mounting} (Boolean): Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile</li>
   * </ul>
   *
   * @param id Desktop Configuration Profile ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DesktopConfigurationProfile}
   */
  public DesktopConfigurationProfile update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("mount_mappings") && !(parameters.get("mount_mappings") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: mount_mappings must be of type Object parameters[\"mount_mappings\"]");
    }

    if (parameters.containsKey("use_for_all_users") && !(parameters.get("use_for_all_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_for_all_users must be of type Boolean parameters[\"use_for_all_users\"]");
    }

    if (parameters.containsKey("disable_drive_mounting") && !(parameters.get("disable_drive_mounting") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_drive_mounting must be of type Boolean parameters[\"disable_drive_mounting\"]");
    }

    String apiPath = String.format("/desktop_configuration_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<DesktopConfigurationProfile> typeReference = new TypeReference<DesktopConfigurationProfile>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Desktop Configuration Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Desktop Configuration Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Desktop Configuration Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Desktop Configuration Profile
   *
   * @param id Desktop Configuration Profile ID.
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

    String apiPath = String.format("/desktop_configuration_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Desktop Configuration Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Desktop Configuration Profile
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
