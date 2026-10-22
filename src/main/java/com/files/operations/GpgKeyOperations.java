package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.GpgKey;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * GpgKey operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#gpgKeys()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.GpgKey} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class GpgKeyOperations extends ResourceOperations {
  public GpgKeyOperations(FilesClient client) {
    super(client);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GpgKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GpgKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List GPG Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `name` or `expires_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`, `partner_id` or `expires_at`. Valid field combinations are `[ workspace_id, partner_id ]`, `[ workspace_id, expires_at ]`, `[ partner_id, expires_at ]` or `[ workspace_id, partner_id, expires_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `expires_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `expires_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey} records, in a {@code ListIterator}
   */
  public ListIterator<GpgKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/gpg_keys";
    TypeReference<List<GpgKey>> typeReference = new TypeReference<List<GpgKey>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GpgKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List GPG Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<GpgKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public GpgKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public GpgKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public GpgKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show GPG Key
   *
   * @param id Gpg Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey}
   */
  public GpgKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/gpg_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<GpgKey> typeReference = new TypeReference<GpgKey>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public GpgKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show GPG Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public GpgKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create GPG Key
   *
   * @see #create(HashMap, HashMap)
   */
  public GpgKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create GPG Key
   *
   * @see #create(HashMap, HashMap)
   */
  public GpgKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create GPG Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code partner_id} (Long): Partner ID who owns this GPG Key, if applicable.</li>
   * <li>{@code public_key} (String): The GPG public key</li>
   * <li>{@code private_key} (String): The GPG private key</li>
   * <li>{@code private_key_password} (String): The GPG private key password</li>
   * <li>{@code name} (String): Required. GPG key name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID (0 for default workspace).</li>
   * <li>{@code generate_expires_at} (String): Expiration date of the key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_keypair} (Boolean): If true, generate a new GPG key pair. Can not be used with `public_key`/`private_key`</li>
   * <li>{@code generate_full_name} (String): Full name of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_email} (String): Email address of the key owner. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey}
   */
  public GpgKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("generate_expires_at") && !(parameters.get("generate_expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generate_expires_at must be of type String parameters[\"generate_expires_at\"]");
    }

    if (parameters.containsKey("generate_keypair") && !(parameters.get("generate_keypair") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: generate_keypair must be of type Boolean parameters[\"generate_keypair\"]");
    }

    if (parameters.containsKey("generate_full_name") && !(parameters.get("generate_full_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generate_full_name must be of type String parameters[\"generate_full_name\"]");
    }

    if (parameters.containsKey("generate_email") && !(parameters.get("generate_email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generate_email must be of type String parameters[\"generate_email\"]");
    }

    String apiPath = "/gpg_keys";
    TypeReference<GpgKey> typeReference = new TypeReference<GpgKey>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update GPG Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public GpgKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update GPG Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public GpgKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update GPG Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public GpgKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update GPG Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code partner_id} (Long): Partner ID who owns this GPG Key, if applicable.</li>
   * <li>{@code public_key} (String): The GPG public key</li>
   * <li>{@code private_key} (String): The GPG private key</li>
   * <li>{@code private_key_password} (String): The GPG private key password</li>
   * <li>{@code name} (String): GPG key name.</li>
   * </ul>
   *
   * @param id Gpg Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code GpgKey}
   */
  public GpgKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    String apiPath = String.format("/gpg_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<GpgKey> typeReference = new TypeReference<GpgKey>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete GPG Key
   *
   * @param id Gpg Key ID.
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

    String apiPath = String.format("/gpg_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete GPG Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
