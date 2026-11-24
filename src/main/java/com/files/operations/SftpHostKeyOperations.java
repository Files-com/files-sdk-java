package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.SftpHostKey;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * SftpHostKey operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#sftpHostKeys()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.SftpHostKey} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SftpHostKeyOperations extends ResourceOperations {
  public SftpHostKeyOperations(FilesClient client) {
    super(client);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SftpHostKey> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SftpHostKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List SFTP Host Keys
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey} records, in a {@code ListIterator}
   */
  public ListIterator<SftpHostKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/sftp_host_keys";
    TypeReference<List<SftpHostKey>> typeReference = new TypeReference<List<SftpHostKey>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SftpHostKey> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List SFTP Host Keys
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<SftpHostKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SftpHostKey find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SftpHostKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SftpHostKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show SFTP Host Key
   *
   * @param id Sftp Host Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey}
   */
  public SftpHostKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/sftp_host_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<SftpHostKey> typeReference = new TypeReference<SftpHostKey>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SftpHostKey get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show SFTP Host Key
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public SftpHostKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create SFTP Host Key
   *
   * @see #create(HashMap, HashMap)
   */
  public SftpHostKey create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create SFTP Host Key
   *
   * @see #create(HashMap, HashMap)
   */
  public SftpHostKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create SFTP Host Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code active} (Boolean): If true, use this SFTP Host Key.</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.</li>
   * <li>{@code name} (String): The friendly name of this SFTP Host Key.</li>
   * <li>{@code private_key} (String): The private key data.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey}
   */
  public SftpHostKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("active") && !(parameters.get("active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: active must be of type Boolean parameters[\"active\"]");
    }

    if (parameters.containsKey("custom_domain_id") && !(parameters.get("custom_domain_id") instanceof Long || parameters.get("custom_domain_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: custom_domain_id must be of type Long or Integer parameters[\"custom_domain_id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    String apiPath = "/sftp_host_keys";
    TypeReference<SftpHostKey> typeReference = new TypeReference<SftpHostKey>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update SFTP Host Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public SftpHostKey update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update SFTP Host Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public SftpHostKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update SFTP Host Key
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public SftpHostKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update SFTP Host Key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code active} (Boolean): If true, use this SFTP Host Key.</li>
   * <li>{@code custom_domain_id} (Long): Custom Domain ID. If set, this key is used only for that Custom Domain.</li>
   * <li>{@code name} (String): The friendly name of this SFTP Host Key.</li>
   * <li>{@code private_key} (String): The private key data.</li>
   * </ul>
   *
   * @param id Sftp Host Key ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SftpHostKey}
   */
  public SftpHostKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("active") && !(parameters.get("active") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: active must be of type Boolean parameters[\"active\"]");
    }

    if (parameters.containsKey("custom_domain_id") && !(parameters.get("custom_domain_id") instanceof Long || parameters.get("custom_domain_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: custom_domain_id must be of type Long or Integer parameters[\"custom_domain_id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    String apiPath = String.format("/sftp_host_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<SftpHostKey> typeReference = new TypeReference<SftpHostKey>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete SFTP Host Key
   *
   * @param id Sftp Host Key ID.
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

    String apiPath = String.format("/sftp_host_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete SFTP Host Key
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
