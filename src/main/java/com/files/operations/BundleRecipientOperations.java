package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.BundleRecipient;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * BundleRecipient operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#bundleRecipients()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.BundleRecipient} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class BundleRecipientOperations extends ResourceOperations {
  public BundleRecipientOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Share Link Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleRecipient> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Share Link Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleRecipient> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Share Link Recipients
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `has_registrations`.</li>
   * <li>{@code bundle_id} (Long): Required. List recipients for the bundle with this ID.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleRecipient} records, in a {@code ListIterator}
   */
  public ListIterator<BundleRecipient> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }

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

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long || parameters.get("bundle_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long or Integer parameters[\"bundle_id\"]");
    }

    String apiPath = "/bundle_recipients";
    TypeReference<List<BundleRecipient>> typeReference = new TypeReference<List<BundleRecipient>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Share Link Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleRecipient> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Share Link Recipients
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<BundleRecipient> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Share Link Recipient
   *
   * @see #create(HashMap, HashMap)
   */
  public BundleRecipient create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Share Link Recipient
   *
   * @see #create(HashMap, HashMap)
   */
  public BundleRecipient create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Share Link Recipient
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code bundle_id} (Long): Required. Bundle to share.</li>
   * <li>{@code recipient} (String): Required. Email addresses to share this bundle with.</li>
   * <li>{@code name} (String): Name of recipient.</li>
   * <li>{@code company} (String): Company of recipient.</li>
   * <li>{@code note} (String): Note to include in email.</li>
   * <li>{@code share_after_create} (Boolean): Set to true to share the link with the recipient upon creation.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleRecipient}
   */
  public BundleRecipient create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("bundle_id") || parameters.get("bundle_id") == null) {
      throw new NullPointerException("Parameter missing: bundle_id parameters[\"bundle_id\"]");
    }

    if (!parameters.containsKey("recipient") || parameters.get("recipient") == null) {
      throw new NullPointerException("Parameter missing: recipient parameters[\"recipient\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long || parameters.get("bundle_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long or Integer parameters[\"bundle_id\"]");
    }

    if (parameters.containsKey("recipient") && !(parameters.get("recipient") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: recipient must be of type String parameters[\"recipient\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("company") && !(parameters.get("company") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: company must be of type String parameters[\"company\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("share_after_create") && !(parameters.get("share_after_create") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: share_after_create must be of type Boolean parameters[\"share_after_create\"]");
    }

    String apiPath = "/bundle_recipients";
    TypeReference<BundleRecipient> typeReference = new TypeReference<BundleRecipient>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
