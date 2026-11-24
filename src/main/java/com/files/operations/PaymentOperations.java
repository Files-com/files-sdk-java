package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.AccountLineItem;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Payment operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#payments()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Payment} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class PaymentOperations extends ResourceOperations {
  public PaymentOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Payments
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AccountLineItem> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Payments
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AccountLineItem> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Payments
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AccountLineItem} records, in a {@code ListIterator}
   */
  public ListIterator<AccountLineItem> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/payments";
    TypeReference<List<AccountLineItem>> typeReference = new TypeReference<List<AccountLineItem>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Payments
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AccountLineItem> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Payments
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<AccountLineItem> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Payment
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AccountLineItem find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Payment
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AccountLineItem find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Payment
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AccountLineItem find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Payment
   *
   * @param id Payment ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code AccountLineItem}
   */
  public AccountLineItem find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/payments/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<AccountLineItem> typeReference = new TypeReference<AccountLineItem>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Payment
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AccountLineItem get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Payment
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public AccountLineItem get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }
}
