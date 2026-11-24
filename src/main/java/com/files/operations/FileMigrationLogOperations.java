package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.FileMigrationLog;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * FileMigrationLog operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#fileMigrationLogs()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.FileMigrationLog} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class FileMigrationLogOperations extends ResourceOperations {
  public FileMigrationLogOperations(FilesClient client) {
    super(client);
  }

  /**
   * List File Migration Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<FileMigrationLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List File Migration Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<FileMigrationLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List File Migration Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `file_migration_id`, `operation`, `status`, `type` or `created_at`. Valid field combinations are `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ created_at ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ file_migration_id, created_at ]`, `[ operation, status ]`, `[ operation, type ]`, `[ operation, created_at ]`, `[ status, type ]`, `[ status, created_at ]`, `[ type, created_at ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, operation, created_at ]`, `[ file_migration_id, status, type ]`, `[ file_migration_id, status, created_at ]`, `[ file_migration_id, type, created_at ]`, `[ operation, status, type ]`, `[ operation, status, created_at ]`, `[ operation, type, created_at ]`, `[ status, type, created_at ]`, `[ file_migration_id, operation, status, type ]`, `[ file_migration_id, operation, status, created_at ]`, `[ file_migration_id, operation, type, created_at ]`, `[ file_migration_id, status, type, created_at ]`, `[ operation, status, type, created_at ]` or `[ file_migration_id, operation, status, type, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ created_at ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ file_migration_id, created_at ]`, `[ operation, status ]`, `[ operation, type ]`, `[ operation, created_at ]`, `[ status, type ]`, `[ status, created_at ]`, `[ type, created_at ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, operation, created_at ]`, `[ file_migration_id, status, type ]`, `[ file_migration_id, status, created_at ]`, `[ file_migration_id, type, created_at ]`, `[ operation, status, type ]`, `[ operation, status, created_at ]`, `[ operation, type, created_at ]`, `[ status, type, created_at ]`, `[ file_migration_id, operation, status, type ]`, `[ file_migration_id, operation, status, created_at ]`, `[ file_migration_id, operation, type, created_at ]`, `[ file_migration_id, status, type, created_at ]`, `[ operation, status, type, created_at ]` or `[ file_migration_id, operation, status, type, created_at ]`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ created_at ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ file_migration_id, created_at ]`, `[ operation, status ]`, `[ operation, type ]`, `[ operation, created_at ]`, `[ status, type ]`, `[ status, created_at ]`, `[ type, created_at ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, operation, created_at ]`, `[ file_migration_id, status, type ]`, `[ file_migration_id, status, created_at ]`, `[ file_migration_id, type, created_at ]`, `[ operation, status, type ]`, `[ operation, status, created_at ]`, `[ operation, type, created_at ]`, `[ status, type, created_at ]`, `[ file_migration_id, operation, status, type ]`, `[ file_migration_id, operation, status, created_at ]`, `[ file_migration_id, operation, type, created_at ]`, `[ file_migration_id, status, type, created_at ]`, `[ operation, status, type, created_at ]` or `[ file_migration_id, operation, status, type, created_at ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ created_at ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ file_migration_id, created_at ]`, `[ operation, status ]`, `[ operation, type ]`, `[ operation, created_at ]`, `[ status, type ]`, `[ status, created_at ]`, `[ type, created_at ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, operation, created_at ]`, `[ file_migration_id, status, type ]`, `[ file_migration_id, status, created_at ]`, `[ file_migration_id, type, created_at ]`, `[ operation, status, type ]`, `[ operation, status, created_at ]`, `[ operation, type, created_at ]`, `[ status, type, created_at ]`, `[ file_migration_id, operation, status, type ]`, `[ file_migration_id, operation, status, created_at ]`, `[ file_migration_id, operation, type, created_at ]`, `[ file_migration_id, status, type, created_at ]`, `[ operation, status, type, created_at ]` or `[ file_migration_id, operation, status, type, created_at ]`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ file_migration_id ]`, `[ operation ]`, `[ status ]`, `[ type ]`, `[ created_at ]`, `[ file_migration_id, operation ]`, `[ file_migration_id, status ]`, `[ file_migration_id, type ]`, `[ file_migration_id, created_at ]`, `[ operation, status ]`, `[ operation, type ]`, `[ operation, created_at ]`, `[ status, type ]`, `[ status, created_at ]`, `[ type, created_at ]`, `[ file_migration_id, operation, status ]`, `[ file_migration_id, operation, type ]`, `[ file_migration_id, operation, created_at ]`, `[ file_migration_id, status, type ]`, `[ file_migration_id, status, created_at ]`, `[ file_migration_id, type, created_at ]`, `[ operation, status, type ]`, `[ operation, status, created_at ]`, `[ operation, type, created_at ]`, `[ status, type, created_at ]`, `[ file_migration_id, operation, status, type ]`, `[ file_migration_id, operation, status, created_at ]`, `[ file_migration_id, operation, type, created_at ]`, `[ file_migration_id, status, type, created_at ]`, `[ operation, status, type, created_at ]` or `[ file_migration_id, operation, status, type, created_at ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileMigrationLog} records, in a {@code ListIterator}
   */
  public ListIterator<FileMigrationLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
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

    String apiPath = "/file_migration_logs";
    TypeReference<List<FileMigrationLog>> typeReference = new TypeReference<List<FileMigrationLog>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List File Migration Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<FileMigrationLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List File Migration Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<FileMigrationLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }
}
