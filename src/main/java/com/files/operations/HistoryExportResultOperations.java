package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.HistoryExportResult;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * HistoryExportResult operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#historyExportResults()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.HistoryExportResult} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class HistoryExportResultOperations extends ResourceOperations {
  public HistoryExportResultOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   history_export_id (required) - int64 - ID of the associated history export.
   */
  public ListIterator<HistoryExportResult> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<HistoryExportResult> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<HistoryExportResult> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("history_export_id") || parameters.get("history_export_id") == null) {
      throw new NullPointerException("Parameter missing: history_export_id parameters[\"history_export_id\"]");
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

    if (parameters.containsKey("history_export_id") && !(parameters.get("history_export_id") instanceof Long || parameters.get("history_export_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: history_export_id must be of type Long or Integer parameters[\"history_export_id\"]");
    }

    String apiPath = "/history_export_results";
    TypeReference<List<HistoryExportResult>> typeReference = new TypeReference<List<HistoryExportResult>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<HistoryExportResult> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<HistoryExportResult> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }
}
