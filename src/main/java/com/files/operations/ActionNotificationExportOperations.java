package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ActionNotificationExport;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ActionNotificationExport operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#actionNotificationExports()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ActionNotificationExport} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ActionNotificationExportOperations extends ResourceOperations {
  public ActionNotificationExportOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Action Notification Export ID.
   */
  public ActionNotificationExport find() throws RuntimeException {
    return find(null, null, null);
  }

  public ActionNotificationExport find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public ActionNotificationExport find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public ActionNotificationExport find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/action_notification_exports/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ActionNotificationExport> typeReference = new TypeReference<ActionNotificationExport>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ActionNotificationExport get() throws RuntimeException {
    return get(null, null, null);
  }

  public ActionNotificationExport get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   workspace_id - int64 - Workspace whose logs are exported. Set to `0` for the default workspace. A null value means a site-wide export.
   *   start_at - string - Start date/time of export range.
   *   end_at - string - End date/time of export range.
   *   query_message - string - Error message associated with the request, if any.
   *   query_request_method - string - The HTTP request method used by the webhook.
   *   query_request_url - string - The target webhook URL.
   *   query_status - string - The HTTP status returned from the server in response to the webhook request.
   *   query_success - boolean - true if the webhook request succeeded (i.e. returned a 200 or 204 response status). false otherwise.
   *   query_path - string - Return notifications that were triggered by actions on this specific path.
   *   query_folder - string - Return notifications that were triggered by actions in this folder.
   */
  public ActionNotificationExport create() throws RuntimeException {
    return create(null, null);
  }

  public ActionNotificationExport create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public ActionNotificationExport create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("start_at") && !(parameters.get("start_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_at must be of type String parameters[\"start_at\"]");
    }

    if (parameters.containsKey("end_at") && !(parameters.get("end_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: end_at must be of type String parameters[\"end_at\"]");
    }

    if (parameters.containsKey("query_message") && !(parameters.get("query_message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_message must be of type String parameters[\"query_message\"]");
    }

    if (parameters.containsKey("query_request_method") && !(parameters.get("query_request_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_request_method must be of type String parameters[\"query_request_method\"]");
    }

    if (parameters.containsKey("query_request_url") && !(parameters.get("query_request_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_request_url must be of type String parameters[\"query_request_url\"]");
    }

    if (parameters.containsKey("query_status") && !(parameters.get("query_status") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_status must be of type String parameters[\"query_status\"]");
    }

    if (parameters.containsKey("query_success") && !(parameters.get("query_success") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: query_success must be of type Boolean parameters[\"query_success\"]");
    }

    if (parameters.containsKey("query_path") && !(parameters.get("query_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_path must be of type String parameters[\"query_path\"]");
    }

    if (parameters.containsKey("query_folder") && !(parameters.get("query_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: query_folder must be of type String parameters[\"query_folder\"]");
    }

    String apiPath = "/action_notification_exports";
    TypeReference<ActionNotificationExport> typeReference = new TypeReference<ActionNotificationExport>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
