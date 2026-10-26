package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.As2IncomingMessage;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * As2IncomingMessage operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#as2IncomingMessages()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.As2IncomingMessage} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class As2IncomingMessageOperations extends ResourceOperations {
  public As2IncomingMessageOperations(FilesClient client) {
    super(client);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2IncomingMessage> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2IncomingMessage> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List AS2 Incoming Messages
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `as2_partner_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `as2_station_id`, `workspace_id`, `replayed_from_message_id` or `as2_partner_id`. Valid field combinations are `[ as2_station_id, created_at ]`, `[ workspace_id, created_at ]`, `[ replayed_from_message_id, created_at ]`, `[ as2_partner_id, created_at ]`, `[ workspace_id, as2_station_id ]`, `[ workspace_id, replayed_from_message_id ]`, `[ workspace_id, as2_partner_id ]`, `[ workspace_id, as2_station_id, created_at ]`, `[ workspace_id, replayed_from_message_id, created_at ]` or `[ workspace_id, as2_partner_id, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2IncomingMessage} records, in a {@code ListIterator}
   */
  public ListIterator<As2IncomingMessage> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/as2_incoming_messages";
    TypeReference<List<As2IncomingMessage>> typeReference = new TypeReference<List<As2IncomingMessage>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2IncomingMessage> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List AS2 Incoming Messages
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<As2IncomingMessage> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show AS2 Incoming Message
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2IncomingMessage find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show AS2 Incoming Message
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2IncomingMessage find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show AS2 Incoming Message
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2IncomingMessage find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show AS2 Incoming Message
   *
   * @param id As2 Incoming Message ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2IncomingMessage}
   */
  public As2IncomingMessage find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/as2_incoming_messages/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<As2IncomingMessage> typeReference = new TypeReference<As2IncomingMessage>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show AS2 Incoming Message
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2IncomingMessage get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show AS2 Incoming Message
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public As2IncomingMessage get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Replay AS2 Incoming Message
   *
   * @see #replay(Long, HashMap, HashMap)
   */
  public As2IncomingMessage replay() throws RuntimeException {
    return replay(null, null, null);
  }

  /**
   * Replay AS2 Incoming Message
   *
   * @see #replay(Long, HashMap, HashMap)
   */
  public As2IncomingMessage replay(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return replay(id, parameters, null);
  }

  /**
   * Replay AS2 Incoming Message
   *
   * @see #replay(Long, HashMap, HashMap)
   */
  public As2IncomingMessage replay(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return replay(null, parameters, options);
  }

  /**
   * Replay AS2 Incoming Message
   *
   * @param id As2 Incoming Message ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code As2IncomingMessage}
   */
  public As2IncomingMessage replay(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/as2_incoming_messages/%s/replay", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<As2IncomingMessage> typeReference = new TypeReference<As2IncomingMessage>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
