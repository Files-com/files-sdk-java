package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.SftpHostKey;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public ListIterator<SftpHostKey> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<SftpHostKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

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

  public ListIterator<SftpHostKey> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<SftpHostKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Sftp Host Key ID.
   */
  public SftpHostKey find() throws RuntimeException {
    return find(null, null, null);
  }

  public SftpHostKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public SftpHostKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

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

  public SftpHostKey get() throws RuntimeException {
    return get(null, null, null);
  }

  public SftpHostKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   active - boolean - If true, use this SFTP Host Key.
   *   custom_domain_id - int64 - Custom Domain ID. If set, this key is used only for that Custom Domain.
   *   name - string - The friendly name of this SFTP Host Key.
   *   private_key - string - The private key data.
   */
  public SftpHostKey create() throws RuntimeException {
    return create(null, null);
  }

  public SftpHostKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

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
   * Parameters:
   *   active - boolean - If true, use this SFTP Host Key.
   *   custom_domain_id - int64 - Custom Domain ID. If set, this key is used only for that Custom Domain.
   *   name - string - The friendly name of this SFTP Host Key.
   *   private_key - string - The private key data.
   */
  public SftpHostKey update() throws RuntimeException {
    return update(null, null, null);
  }

  public SftpHostKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public SftpHostKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

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

    String apiPath = String.format("/sftp_host_keys/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
