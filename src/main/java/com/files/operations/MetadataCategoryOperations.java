package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.MetadataCategory;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * MetadataCategory operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#metadataCategories()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.MetadataCategory} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class MetadataCategoryOperations extends ResourceOperations {
  public MetadataCategoryOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Metadata Categories
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory} records, in a {@code ListIterator}
   */
  public ListIterator<MetadataCategory> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/metadata_categories";
    TypeReference<List<MetadataCategory>> typeReference = new TypeReference<List<MetadataCategory>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public MetadataCategory find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public MetadataCategory find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public MetadataCategory find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Metadata Category
   *
   * @param id Metadata Category ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory}
   */
  public MetadataCategory find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/metadata_categories/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<MetadataCategory> typeReference = new TypeReference<MetadataCategory>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public MetadataCategory get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public MetadataCategory get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * List Metadata Categories by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  /**
   * List Metadata Categories by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  /**
   * List Metadata Categories by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public ListIterator<MetadataCategory> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  /**
   * List Metadata Categories by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory} records, in a {@code ListIterator}
   */
  public ListIterator<MetadataCategory> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/metadata_categories/list_by_path/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<MetadataCategory>> typeReference = new TypeReference<List<MetadataCategory>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Create Metadata Category
   *
   * @see #create(HashMap, HashMap)
   */
  public MetadataCategory create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Metadata Category
   *
   * @see #create(HashMap, HashMap)
   */
  public MetadataCategory create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Metadata Category
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name of the metadata category.</li>
   * <li>{@code default_columns} (String[]): Metadata keys that should appear as columns in the UI by default.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory}
   */
  public MetadataCategory create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("default_columns") && !(parameters.get("default_columns") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: default_columns must be of type String[] parameters[\"default_columns\"]");
    }

    String apiPath = "/metadata_categories";
    TypeReference<MetadataCategory> typeReference = new TypeReference<MetadataCategory>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update Metadata Category
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public MetadataCategory update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Metadata Category
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public MetadataCategory update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Metadata Category
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public MetadataCategory update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Metadata Category
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the metadata category.</li>
   * <li>{@code default_columns} (String[]): Metadata keys that should appear as columns in the UI by default.</li>
   * </ul>
   *
   * @param id Metadata Category ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory}
   */
  public MetadataCategory update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("default_columns") && !(parameters.get("default_columns") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: default_columns must be of type String[] parameters[\"default_columns\"]");
    }

    String apiPath = String.format("/metadata_categories/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<MetadataCategory> typeReference = new TypeReference<MetadataCategory>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Metadata Category
   *
   * @param id Metadata Category ID.
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

    String apiPath = String.format("/metadata_categories/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
