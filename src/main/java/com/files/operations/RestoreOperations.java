package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Restore;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * Restore operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#restores()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Restore} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class RestoreOperations extends ResourceOperations {
  public RestoreOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Restore> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Restore> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Restores
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `restoration_type`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Restore} records, in a {@code ListIterator}
   */
  public ListIterator<Restore> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/restores";
    TypeReference<List<Restore>> typeReference = new TypeReference<List<Restore>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Restore> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Restores
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Restore> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Restore
   *
   * @see #create(HashMap, HashMap)
   */
  public Restore create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Restore
   *
   * @see #create(HashMap, HashMap)
   */
  public Restore create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Restore
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code earliest_date} (String): Required. Restore files or users deleted on or after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.</li>
   * <li>{@code prefix} (String): Prefix of the files/folders to restore, or a case-insensitive username prefix for a user restore. A trailing slash selects that folder and its contents only; without it, the prefix matches any path beginning with that text. Do not use a leading slash. To restore all deleted items of the selected restoration type within the selected site or workspace scope, specify an empty string (`''`) or omit the field.</li>
   * <li>{@code restoration_type} (String): Type of restoration to perform. `files` restores deleted filesystem items. `users` restores deleted users and associated authentication records, plus permissions when `restore_deleted_permissions=true`. A 5-minute grace period allows for batch deletion processes: associated records deleted within the 5 minutes before the user are included, even if removed independently. Records deleted earlier, or before `earliest_date`, remain deleted.</li>
   * <li>{@code restore_deleted_permissions} (Boolean): If true, a user restore restores permissions deleted along with the selected users, including permissions removed independently within the 5 minutes before user deletion. Permissions deleted earlier, or before `earliest_date`, remain deleted. An in-place file restore restores user and group permissions on returned folders and their subfolders that were deleted from the time of the folder's deletion through 24 hours afterward, including permissions removed independently within that window. File restores into a new folder do not restore permissions.</li>
   * <li>{@code restore_in_place} (Boolean): If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.</li>
   * <li>{@code update_timestamps} (Boolean): If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.</li>
   * <li>{@code workspace_id} (Long): Workspace ID for a workspace-scoped restore. `0` means the default site-wide scope.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Restore}
   */
  public Restore create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("earliest_date") || parameters.get("earliest_date") == null) {
      throw new NullPointerException("Parameter missing: earliest_date parameters[\"earliest_date\"]");
    }

    if (parameters.containsKey("earliest_date") && !(parameters.get("earliest_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: earliest_date must be of type String parameters[\"earliest_date\"]");
    }

    if (parameters.containsKey("prefix") && !(parameters.get("prefix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: prefix must be of type String parameters[\"prefix\"]");
    }

    if (parameters.containsKey("restoration_type") && !(parameters.get("restoration_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: restoration_type must be of type String parameters[\"restoration_type\"]");
    }

    if (parameters.containsKey("restore_deleted_permissions") && !(parameters.get("restore_deleted_permissions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restore_deleted_permissions must be of type Boolean parameters[\"restore_deleted_permissions\"]");
    }

    if (parameters.containsKey("restore_in_place") && !(parameters.get("restore_in_place") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restore_in_place must be of type Boolean parameters[\"restore_in_place\"]");
    }

    if (parameters.containsKey("update_timestamps") && !(parameters.get("update_timestamps") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: update_timestamps must be of type Boolean parameters[\"update_timestamps\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/restores";
    TypeReference<Restore> typeReference = new TypeReference<Restore>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
