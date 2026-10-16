package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.DesktopConfigurationProfile;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public ListIterator<DesktopConfigurationProfile> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<DesktopConfigurationProfile> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

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

  public ListIterator<DesktopConfigurationProfile> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<DesktopConfigurationProfile> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Desktop Configuration Profile ID.
   */
  public DesktopConfigurationProfile find() throws RuntimeException {
    return find(null, null, null);
  }

  public DesktopConfigurationProfile find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public DesktopConfigurationProfile find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

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

  public DesktopConfigurationProfile get() throws RuntimeException {
    return get(null, null, null);
  }

  public DesktopConfigurationProfile get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   name (required) - string - Profile name
   *   mount_mappings (required) - object - Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.
   *   workspace_id - int64 - Workspace ID
   *   use_for_all_users - boolean - Whether this profile applies to all users in the Workspace by default
   *   disable_drive_mounting - boolean - Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile
   */
  public DesktopConfigurationProfile create() throws RuntimeException {
    return create(null, null);
  }

  public DesktopConfigurationProfile create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

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
   * Parameters:
   *   name - string - Profile name
   *   workspace_id - int64 - Workspace ID
   *   mount_mappings - object - Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.
   *   use_for_all_users - boolean - Whether this profile applies to all users in the Workspace by default
   *   disable_drive_mounting - boolean - Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile
   */
  public DesktopConfigurationProfile update() throws RuntimeException {
    return update(null, null, null);
  }

  public DesktopConfigurationProfile update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public DesktopConfigurationProfile update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

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

    String apiPath = String.format("/desktop_configuration_profiles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
