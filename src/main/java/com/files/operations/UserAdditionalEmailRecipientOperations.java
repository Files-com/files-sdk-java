package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.UserAdditionalEmailRecipient;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * UserAdditionalEmailRecipient operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#userAdditionalEmailRecipients()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.UserAdditionalEmailRecipient} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class UserAdditionalEmailRecipientOperations extends ResourceOperations {
  public UserAdditionalEmailRecipientOperations(FilesClient client) {
    super(client);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserAdditionalEmailRecipient> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserAdditionalEmailRecipient> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List User Additional Email Recipients
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `email`, `user_id` or `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `email`, `workspace_id` or `user_id`. Valid field combinations are `[ workspace_id, email ]`, `[ user_id, email ]`, `[ workspace_id, user_id ]` or `[ workspace_id, user_id, email ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `email`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient} records, in a {@code ListIterator}
   */
  public ListIterator<UserAdditionalEmailRecipient> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }

    String apiPath = "/user_additional_email_recipients";
    TypeReference<List<UserAdditionalEmailRecipient>> typeReference = new TypeReference<List<UserAdditionalEmailRecipient>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserAdditionalEmailRecipient> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User Additional Email Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserAdditionalEmailRecipient> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @param id User Additional Email Recipient ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public UserAdditionalEmailRecipient find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/user_additional_email_recipients/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<UserAdditionalEmailRecipient> typeReference = new TypeReference<UserAdditionalEmailRecipient>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User Additional Email Recipient
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User Additional Email Recipient
   *
   * @see #create(HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User Additional Email Recipient
   *
   * @see #create(HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create User Additional Email Recipient
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code email} (String): Required. Additional email recipient address</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public UserAdditionalEmailRecipient create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("email") || parameters.get("email") == null) {
      throw new NullPointerException("Parameter missing: email parameters[\"email\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    String apiPath = "/user_additional_email_recipients";
    TypeReference<UserAdditionalEmailRecipient> typeReference = new TypeReference<UserAdditionalEmailRecipient>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update User Additional Email Recipient
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update User Additional Email Recipient
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update User Additional Email Recipient
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public UserAdditionalEmailRecipient update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update User Additional Email Recipient
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code email} (String): Additional email recipient address</li>
   * </ul>
   *
   * @param id User Additional Email Recipient ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserAdditionalEmailRecipient}
   */
  public UserAdditionalEmailRecipient update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    String apiPath = String.format("/user_additional_email_recipients/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<UserAdditionalEmailRecipient> typeReference = new TypeReference<UserAdditionalEmailRecipient>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @param id User Additional Email Recipient ID.
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

    String apiPath = String.format("/user_additional_email_recipients/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User Additional Email Recipient
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
