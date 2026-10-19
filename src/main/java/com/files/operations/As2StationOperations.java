package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.As2Station;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * As2Station operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#as2Stations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.As2Station} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class As2StationOperations extends ResourceOperations {
  public As2StationOperations(FilesClient client) {
    super(client);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2Station> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2Station> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List AS2 Stations
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
   * @return the {@code As2Station} records, in a {@code ListIterator}
   */
  public ListIterator<As2Station> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/as2_stations";
    TypeReference<List<As2Station>> typeReference = new TypeReference<List<As2Station>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2Station> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List AS2 Stations
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2Station> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2Station find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2Station find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2Station find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show AS2 Station
   *
   * @param id As2 Station ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station}
   */
  public As2Station find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/as2_stations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2Station get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show AS2 Station
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2Station get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create AS2 Station
   *
   * @see #create(HashMap, HashMap)
   */
  public As2Station create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create AS2 Station
   *
   * @see #create(HashMap, HashMap)
   */
  public As2Station create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create AS2 Station
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. The station's formal AS2 name.</li>
   * <li>{@code workspace_id} (Long): ID of the Workspace associated with this AS2 Station.</li>
   * <li>{@code public_certificate} (String): Public certificate used for message security.</li>
   * <li>{@code private_key} (String): PEM-encoded private key matching public_certificate.</li>
   * <li>{@code private_key_password} (String): Password for the PEM-encoded private key.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's certificate and private key. Provide this instead of public_certificate and private_key.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station}
   */
  public As2Station create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }

    if (parameters.containsKey("pkcs12") && !(parameters.get("pkcs12") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12 must be of type String parameters[\"pkcs12\"]");
    }

    if (parameters.containsKey("pkcs12_password") && !(parameters.get("pkcs12_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12_password must be of type String parameters[\"pkcs12_password\"]");
    }

    String apiPath = "/as2_stations";
    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update AS2 Station
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public As2Station update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update AS2 Station
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public As2Station update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update AS2 Station
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public As2Station update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update AS2 Station
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): The station's formal AS2 name.</li>
   * <li>{@code public_certificate} (String): Public certificate used for message security.</li>
   * <li>{@code private_key} (String): PEM-encoded private key matching public_certificate.</li>
   * <li>{@code private_key_password} (String): Password for the PEM-encoded private key.</li>
   * <li>{@code pkcs12} (String): Base64-encoded PKCS#12 (.pfx or .p12) file containing the identity's replacement certificate and private key. Provide this instead of public_certificate and private_key.</li>
   * <li>{@code pkcs12_password} (String): Password for pkcs12. The file and password are used only for import; the extracted certificate and private key are stored as PEM.</li>
   * </ul>
   *
   * @param id As2 Station ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2Station}
   */
  public As2Station update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("private_key_password") && !(parameters.get("private_key_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key_password must be of type String parameters[\"private_key_password\"]");
    }

    if (parameters.containsKey("pkcs12") && !(parameters.get("pkcs12") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12 must be of type String parameters[\"pkcs12\"]");
    }

    if (parameters.containsKey("pkcs12_password") && !(parameters.get("pkcs12_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12_password must be of type String parameters[\"pkcs12_password\"]");
    }

    String apiPath = String.format("/as2_stations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<As2Station> typeReference = new TypeReference<As2Station>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete AS2 Station
   *
   * @param id As2 Station ID.
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

    String apiPath = String.format("/as2_stations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete AS2 Station
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
