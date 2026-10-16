package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.InboxRecipient;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * InboxRecipient operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#inboxRecipients()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.InboxRecipient} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class InboxRecipientOperations extends ResourceOperations {
  public InboxRecipientOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `has_registrations`.
   *   inbox_id (required) - int64 - List recipients for the inbox with this ID.
   */
  public ListIterator<InboxRecipient> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<InboxRecipient> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<InboxRecipient> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("inbox_id") || parameters.get("inbox_id") == null) {
      throw new NullPointerException("Parameter missing: inbox_id parameters[\"inbox_id\"]");
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

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }

    String apiPath = "/inbox_recipients";
    TypeReference<List<InboxRecipient>> typeReference = new TypeReference<List<InboxRecipient>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<InboxRecipient> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<InboxRecipient> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   inbox_id (required) - int64 - Inbox to share.
   *   recipient (required) - string - Email address to share this inbox with.
   *   name - string - Name of recipient.
   *   company - string - Company of recipient.
   *   note - string - Note to include in email.
   *   share_after_create - boolean - Set to true to share the link with the recipient upon creation.
   */
  public InboxRecipient create() throws RuntimeException {
    return create(null, null);
  }

  public InboxRecipient create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public InboxRecipient create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("inbox_id") || parameters.get("inbox_id") == null) {
      throw new NullPointerException("Parameter missing: inbox_id parameters[\"inbox_id\"]");
    }

    if (!parameters.containsKey("recipient") || parameters.get("recipient") == null) {
      throw new NullPointerException("Parameter missing: recipient parameters[\"recipient\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
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

    String apiPath = "/inbox_recipients";
    TypeReference<InboxRecipient> typeReference = new TypeReference<InboxRecipient>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
