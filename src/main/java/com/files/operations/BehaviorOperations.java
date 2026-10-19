package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Behavior;
import com.files.net.Attachment;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Behavior operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#behaviors()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Behavior} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class BehaviorOperations extends ResourceOperations {
  public BehaviorOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Behaviors
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Behavior> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Behaviors
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Behavior> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Behaviors
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `clickwrap_id`, `form_field_set_id`, `impacts_ui`, `remote_server_id` or `behavior`. Valid field combinations are `[ impacts_ui, behavior ]` and `[ behavior, remote_server_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Behavior} records, in a {@code ListIterator}
   */
  public ListIterator<Behavior> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/behaviors";
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Behaviors
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Behavior> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Behaviors
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Behavior> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Behavior
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Behavior find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Behavior
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Behavior find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Behavior
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Behavior find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Behavior
   *
   * @param id Behavior ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Behavior}
   */
  public Behavior find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/behaviors/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Behavior
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Behavior get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Behavior
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Behavior get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * List Behaviors by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public ListIterator<Behavior> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  /**
   * List Behaviors by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public ListIterator<Behavior> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  /**
   * List Behaviors by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public ListIterator<Behavior> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  /**
   * List Behaviors by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `impacts_ui` and `behavior`. Valid field combinations are `[ impacts_ui, behavior ]`.</li>
   * <li>{@code ancestor_behaviors} (Boolean): If `true`, behaviors above this path are shown.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Behavior} records, in a {@code ListIterator}
   */
  public ListIterator<Behavior> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("ancestor_behaviors") && !(parameters.get("ancestor_behaviors") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ancestor_behaviors must be of type Boolean parameters[\"ancestor_behaviors\"]");
    }

    String apiPath = String.format("/behaviors/folders/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<Behavior>> typeReference = new TypeReference<List<Behavior>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Create Behavior
   *
   * @see #create(HashMap, HashMap)
   */
  public Behavior create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Behavior
   *
   * @see #create(HashMap, HashMap)
   */
  public Behavior create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Behavior
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code value} (Object): This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.</li>
   * <li>{@code attachment_file} (byte[] or Attachment): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.</li>
   * <li>{@code disable_parent_folder_behavior} (Boolean): If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.</li>
   * <li>{@code recursive} (Boolean): Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.</li>
   * <li>{@code name} (String): Name for this behavior.</li>
   * <li>{@code description} (String): Description for this behavior.</li>
   * <li>{@code path} (String): Required. Path where this behavior should apply.</li>
   * <li>{@code behavior} (String): Required. Behavior type.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Behavior}
   */
  public Behavior create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("behavior") || parameters.get("behavior") == null) {
      throw new NullPointerException("Parameter missing: behavior parameters[\"behavior\"]");
    }

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }

    if (parameters.containsKey("attachment_file") && !(parameters.get("attachment_file") instanceof byte[] || parameters.get("attachment_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: attachment_file must be of type byte[] or Attachment parameters[\"attachment_file\"]");
    }

    if (parameters.containsKey("disable_parent_folder_behavior") && !(parameters.get("disable_parent_folder_behavior") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_parent_folder_behavior must be of type Boolean parameters[\"disable_parent_folder_behavior\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("behavior") && !(parameters.get("behavior") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: behavior must be of type String parameters[\"behavior\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("attachment_file") instanceof byte[]) {
      parameters.put("attachment_file", Attachment.fromBytes((byte[]) parameters.get("attachment_file")));
    }

    String apiPath = "/behaviors";
    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Test Webhook
   *
   * @see #webhookTest(HashMap, HashMap)
   */
  public void webhookTest() throws RuntimeException {
    webhookTest(null, null);
  }

  /**
   * Test Webhook
   *
   * @see #webhookTest(HashMap, HashMap)
   */
  public void webhookTest(HashMap<String, Object> parameters) throws RuntimeException {
    webhookTest(parameters, null);
  }

  /**
   * Test Webhook
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code url} (String): Required. URL for testing the webhook.</li>
   * <li>{@code method} (String): HTTP request method (GET or POST).</li>
   * <li>{@code encoding} (String): Encoding type for the webhook payload. Can be JSON, XML, or RAW (form data).</li>
   * <li>{@code headers} (Object): Additional request headers to send via HTTP.</li>
   * <li>{@code body} (Object): Additional body parameters to include in the webhook payload.</li>
   * <li>{@code action} (String): Action for test body.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void webhookTest(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("url") || parameters.get("url") == null) {
      throw new NullPointerException("Parameter missing: url parameters[\"url\"]");
    }

    if (parameters.containsKey("url") && !(parameters.get("url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: url must be of type String parameters[\"url\"]");
    }

    if (parameters.containsKey("method") && !(parameters.get("method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: method must be of type String parameters[\"method\"]");
    }

    if (parameters.containsKey("encoding") && !(parameters.get("encoding") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: encoding must be of type String parameters[\"encoding\"]");
    }

    if (parameters.containsKey("headers") && !(parameters.get("headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: headers must be of type Object parameters[\"headers\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type Object parameters[\"body\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    String apiPath = "/behaviors/webhook/test";
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Update Behavior
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Behavior update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Behavior
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Behavior update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Behavior
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Behavior update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Behavior
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code value} (Object): This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.</li>
   * <li>{@code attachment_file} (byte[] or Attachment): Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.</li>
   * <li>{@code disable_parent_folder_behavior} (Boolean): If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.</li>
   * <li>{@code recursive} (Boolean): Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.</li>
   * <li>{@code name} (String): Name for this behavior.</li>
   * <li>{@code description} (String): Description for this behavior.</li>
   * <li>{@code attachment_delete} (Boolean): If `true`, delete the file stored in `attachment`.</li>
   * </ul>
   *
   * @param id Behavior ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Behavior}
   */
  public Behavior update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }

    if (parameters.containsKey("attachment_file") && !(parameters.get("attachment_file") instanceof byte[] || parameters.get("attachment_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: attachment_file must be of type byte[] or Attachment parameters[\"attachment_file\"]");
    }

    if (parameters.containsKey("disable_parent_folder_behavior") && !(parameters.get("disable_parent_folder_behavior") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_parent_folder_behavior must be of type Boolean parameters[\"disable_parent_folder_behavior\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("attachment_delete") && !(parameters.get("attachment_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: attachment_delete must be of type Boolean parameters[\"attachment_delete\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("attachment_file") instanceof byte[]) {
      parameters.put("attachment_file", Attachment.fromBytes((byte[]) parameters.get("attachment_file")));
    }

    String apiPath = String.format("/behaviors/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Behavior> typeReference = new TypeReference<Behavior>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Behavior
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Behavior
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Behavior
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Behavior
   *
   * @param id Behavior ID.
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

    String apiPath = String.format("/behaviors/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Behavior
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Behavior
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
