package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.PublicKey;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * PublicKey operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#publicKeys()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.PublicKey} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class PublicKeyOperations extends ResourceOperations {
  public PublicKeyOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PublicKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PublicKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Public Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `user_id`, `title` or `created_at`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at` and `workspace_id`. Valid field combinations are `[ workspace_id, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey} records, in a {@code ListIterator}
   */
  public ListIterator<PublicKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/public_keys";
    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PublicKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Public Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PublicKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PublicKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PublicKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PublicKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Public Key
   *
   * @param id Public Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey}
   */
  public PublicKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/public_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<PublicKey> typeReference = new TypeReference<PublicKey>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PublicKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Public Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PublicKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Public Key
   *
   * @see #create(HashMap, HashMap)
   */
  public PublicKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Public Key
   *
   * @see #create(HashMap, HashMap)
   */
  public PublicKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Public Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code title} (String): Required. Internal reference for key.</li>
   * <li>{@code public_key} (String): Actual contents of SSH key.</li>
   * <li>{@code generate_keypair} (Boolean): If true, generate a new SSH key pair. Can not be used with `public_key`</li>
   * <li>{@code generate_private_key_password} (String): Password for the private key. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_algorithm} (String): Type of key to generate.  One of rsa, dsa, ecdsa, ed25519. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * <li>{@code generate_length} (Long): Length of key to generate. If algorithm is ecdsa, this is the signature size. Used for the generation of the key. Will be ignored if `generate_keypair` is false.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey}
   */
  public PublicKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("title") || parameters.get("title") == null) {
      throw new NullPointerException("Parameter missing: title parameters[\"title\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }

    if (parameters.containsKey("generate_keypair") && !(parameters.get("generate_keypair") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: generate_keypair must be of type Boolean parameters[\"generate_keypair\"]");
    }

    if (parameters.containsKey("generate_private_key_password") && !(parameters.get("generate_private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generate_private_key_password must be of type String parameters[\"generate_private_key_password\"]");
    }

    if (parameters.containsKey("generate_algorithm") && !(parameters.get("generate_algorithm") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: generate_algorithm must be of type String parameters[\"generate_algorithm\"]");
    }

    if (parameters.containsKey("generate_length") && !(parameters.get("generate_length") instanceof Long || parameters.get("generate_length") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: generate_length must be of type Long or Integer parameters[\"generate_length\"]");
    }

    String apiPath = "/public_keys";
    TypeReference<PublicKey> typeReference = new TypeReference<PublicKey>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Public Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public PublicKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Public Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public PublicKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Public Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public PublicKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Public Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code title} (String): Required. Internal reference for key.</li>
   * </ul>
   *
   * @param id Public Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicKey}
   */
  public PublicKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!parameters.containsKey("title") || parameters.get("title") == null) {
      throw new NullPointerException("Parameter missing: title parameters[\"title\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    String apiPath = String.format("/public_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<PublicKey> typeReference = new TypeReference<PublicKey>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Public Key
   *
   * @param id Public Key ID.
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

    String apiPath = String.format("/public_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Public Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
