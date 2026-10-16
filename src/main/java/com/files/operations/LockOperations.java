package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Lock;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lock operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#locks()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Lock} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class LockOperations extends ResourceOperations {
  public LockOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   path (required) - string - Path to operate on.
   *   include_children - boolean - Include locks from children objects?
   */
  public ListIterator<Lock> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  public ListIterator<Lock> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  public ListIterator<Lock> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  public ListIterator<Lock> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("include_children") && !(parameters.get("include_children") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_children must be of type Boolean parameters[\"include_children\"]");
    }

    String apiPath = String.format("/locks/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<Lock>> typeReference = new TypeReference<List<Lock>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   path (required) - string - Path
   *   token - string - Lock token. With expected_token, use the same value to refresh or a different value to replace the existing token.
   *   expected_token - string - Require this existing, unexpired token before refreshing or replacing a lock. Set token to the same value to refresh, or a different value to replace.
   *   allow_access_by_any_user - boolean - Can lock be modified by users other than its creator?
   *   exclusive - boolean - Is lock exclusive?
   *   recursive - boolean - Does lock apply to subfolders?
   *   owner - string - Arbitrary descriptive label for the lock. Does not change the lock creator or permissions.
   *   timeout - int64 - Lock timeout in seconds
   */
  public Lock create() throws RuntimeException {
    return create(null, null, null);
  }

  public Lock create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  public Lock create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  public Lock create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("token") && !(parameters.get("token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: token must be of type String parameters[\"token\"]");
    }

    if (parameters.containsKey("expected_token") && !(parameters.get("expected_token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expected_token must be of type String parameters[\"expected_token\"]");
    }

    if (parameters.containsKey("allow_access_by_any_user") && !(parameters.get("allow_access_by_any_user") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_access_by_any_user must be of type Boolean parameters[\"allow_access_by_any_user\"]");
    }

    if (parameters.containsKey("exclusive") && !(parameters.get("exclusive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: exclusive must be of type Boolean parameters[\"exclusive\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("owner") && !(parameters.get("owner") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: owner must be of type String parameters[\"owner\"]");
    }

    if (parameters.containsKey("timeout") && !(parameters.get("timeout") instanceof Long || parameters.get("timeout") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: timeout must be of type Long or Integer parameters[\"timeout\"]");
    }

    String apiPath = String.format("/locks/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<Lock> typeReference = new TypeReference<Lock>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   token (required) - string - Lock token
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("token") || parameters.get("token") == null) {
      throw new NullPointerException("Parameter missing: token parameters[\"token\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("token") && !(parameters.get("token") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: token must be of type String parameters[\"token\"]");
    }

    String apiPath = String.format("/locks/%s", UrlUtils.encodeUrlPath(path));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }
}
