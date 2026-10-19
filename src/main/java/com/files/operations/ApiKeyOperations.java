package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ApiKey;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * ApiKey operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#apiKeys()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ApiKey} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ApiKeyOperations extends ResourceOperations {
  public ApiKeyOperations(FilesClient client) {
    super(client);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApiKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApiKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List API Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id` and `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `aws_style_credentials` and `expires_at`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `expires_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey} records, in a {@code ListIterator}
   */
  public ListIterator<ApiKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
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

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    String apiPath = "/api_keys";
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApiKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List API Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<ApiKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show information about current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #findCurrent(HashMap, HashMap)
   */
  public ApiKey findCurrent() throws RuntimeException {
    return findCurrent(null, null);
  }

  /**
   * Show information about current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #findCurrent(HashMap, HashMap)
   */
  public ApiKey findCurrent(HashMap<String, Object> parameters) throws RuntimeException {
    return findCurrent(parameters, null);
  }

  /**
   * Show information about current API key.  (Requires current API connection to be using an API key.)
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public ApiKey findCurrent(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/api_key";
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApiKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApiKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApiKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show API Key
   *
   * @param id Api Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public ApiKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/api_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApiKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show API Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public ApiKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create API Key
   *
   * @see #create(HashMap, HashMap)
   */
  public ApiKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create API Key
   *
   * @see #create(HashMap, HashMap)
   */
  public ApiKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create API Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code description} (String): User-supplied description of API key.</li>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Required. Internal name for the API Key.  For your use.</li>
   * <li>{@code aws_style_credentials} (Boolean): If `true`, this API key will be usable with AWS-compatible endpoints, such as our Inbound S3-compatible endpoint.</li>
   * <li>{@code path} (String): Restricts the file and folder operations made with this key, meaning the files, folders, and file_actions endpoints, to the specified folder and its descendants, including copy and move destinations. For `GET /file_migrations/{id}`, a key with a path can read only migrations it started. Other endpoints do not apply the path restriction; use the `files_only` permission set to confine a key to file operations and their supporting lookups. Does not grant access beyond the owning user's permissions. Optional except for `office_integration` keys, which require a path the owning user can read.</li>
   * <li>{@code permission_set} (String): Permissions for this API Key. Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `mobile_app` permission set can only perform the functions provided by our Mobile App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Keys with the `files_only` permission set can use only the files, folders, and file_actions endpoints, excluding the GPG Encrypt and GPG Decrypt file actions, where they perform file operations as a full-access file user in the key's workspace scope, along with `GET /file_migrations/{id}` and `GET /api_key`. On the migration lookup, any key with a `path` can read only migrations it started. Without a `path`, user-owned keys retain user-scoped access; site-wide Workspace-bound keys can read only migrations whose source and destination are both in their Workspace. Every `files_only` key is Workspace-bound, including Workspace `0`, as is a full-access key in a named Workspace. A full-access site-wide key in the default Workspace retains Site-wide migration access. Migrations outside the caller's visibility return `not-found`. Keys with `files_only` cannot use site admin, workspace admin, folder admin, group admin, partner admin, or billing privileges from the owning user. Both GPG file actions and every other endpoint deny them with `not-authorized/api-key-only-for-file-operations`. Folder-configured GPG encryption and decryption still apply to files uploaded with a `files_only` key.</li>
   * <li>{@code workspace_id} (Long): Workspace ID for this API Key. `0` means the default workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public ApiKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("aws_style_credentials") && !(parameters.get("aws_style_credentials") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: aws_style_credentials must be of type Boolean parameters[\"aws_style_credentials\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/api_keys";
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #updateCurrent(HashMap, HashMap)
   */
  public ApiKey updateCurrent() throws RuntimeException {
    return updateCurrent(null, null);
  }

  /**
   * Update current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #updateCurrent(HashMap, HashMap)
   */
  public ApiKey updateCurrent(HashMap<String, Object> parameters) throws RuntimeException {
    return updateCurrent(parameters, null);
  }

  /**
   * Update current API key.  (Requires current API connection to be using an API key.)
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Internal name for the API Key.  For your use.</li>
   * <li>{@code permission_set} (String): Permissions for this API Key. Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations). Keys with the `mobile_app` permission set can only perform the functions provided by our Mobile App (File and Share Link operations). Keys with the `office_integration` permission set are auto generated, and automatically expire, to allow users to interact with office integration platforms. Keys with the `files_only` permission set can use only the files, folders, and file_actions endpoints, excluding the GPG Encrypt and GPG Decrypt file actions, where they perform file operations as a full-access file user in the key's workspace scope, along with `GET /file_migrations/{id}` and `GET /api_key`. On the migration lookup, any key with a `path` can read only migrations it started. Without a `path`, user-owned keys retain user-scoped access; site-wide Workspace-bound keys can read only migrations whose source and destination are both in their Workspace. Every `files_only` key is Workspace-bound, including Workspace `0`, as is a full-access key in a named Workspace. A full-access site-wide key in the default Workspace retains Site-wide migration access. Migrations outside the caller's visibility return `not-found`. Keys with `files_only` cannot use site admin, workspace admin, folder admin, group admin, partner admin, or billing privileges from the owning user. Both GPG file actions and every other endpoint deny them with `not-authorized/api-key-only-for-file-operations`. Folder-configured GPG encryption and decryption still apply to files uploaded with a `files_only` key.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public ApiKey updateCurrent(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }

    String apiPath = "/api_key";
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Update API Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ApiKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update API Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ApiKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update API Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public ApiKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update API Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code description} (String): User-supplied description of API key.</li>
   * <li>{@code expires_at} (String): API Key expiration date</li>
   * <li>{@code name} (String): Internal name for the API Key.  For your use.</li>
   * </ul>
   *
   * @param id Api Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ApiKey}
   */
  public ApiKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    String apiPath = String.format("/api_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #deleteCurrent(HashMap, HashMap)
   */
  public void deleteCurrent() throws RuntimeException {
    deleteCurrent(null, null);
  }

  /**
   * Delete current API key.  (Requires current API connection to be using an API key.)
   *
   * @see #deleteCurrent(HashMap, HashMap)
   */
  public void deleteCurrent(HashMap<String, Object> parameters) throws RuntimeException {
    deleteCurrent(parameters, null);
  }

  /**
   * Delete current API key.  (Requires current API connection to be using an API key.)
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void deleteCurrent(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/api_key";
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete API Key
   *
   * @param id Api Key ID.
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

    String apiPath = String.format("/api_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete API Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
