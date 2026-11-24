package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.UserSftpClientUse;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * UserSftpClientUse operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#userSftpClientUses()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.UserSftpClientUse} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class UserSftpClientUseOperations extends ResourceOperations {
  public UserSftpClientUseOperations(FilesClient client) {
    super(client);
  }

  /**
   * List User SFTP Client Uses
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserSftpClientUse> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User SFTP Client Uses
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserSftpClientUse> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List User SFTP Client Uses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID. If provided, will return uses for this user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `created_at`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserSftpClientUse} records, in a {@code ListIterator}
   */
  public ListIterator<UserSftpClientUse> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/user_sftp_client_uses";
    TypeReference<List<UserSftpClientUse>> typeReference = new TypeReference<List<UserSftpClientUse>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List User SFTP Client Uses
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserSftpClientUse> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User SFTP Client Uses
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<UserSftpClientUse> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }
}
