package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.PartnerChannel;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * PartnerChannel operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#partnerChannels()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.PartnerChannel} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class PartnerChannelOperations extends ResourceOperations {
  public PartnerChannelOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path` or `partner_id`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `partner_id` and `workspace_id`. Valid field combinations are `[ workspace_id, partner_id ]`.
   */
  public ListIterator<PartnerChannel> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<PartnerChannel> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<PartnerChannel> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/partner_channels";
    TypeReference<List<PartnerChannel>> typeReference = new TypeReference<List<PartnerChannel>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<PartnerChannel> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<PartnerChannel> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Partner Channel ID.
   */
  public PartnerChannel find() throws RuntimeException {
    return find(null, null, null);
  }

  public PartnerChannel find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public PartnerChannel find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public PartnerChannel find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/partner_channels/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<PartnerChannel> typeReference = new TypeReference<PartnerChannel>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public PartnerChannel get() throws RuntimeException {
    return get(null, null, null);
  }

  public PartnerChannel get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   direction - string - Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   *   use_channel_root - boolean - Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.
   *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
   *   from_partner_managed_folder_paths - array(string) - Managed folder paths inside the from-Partner folder.
   *   from_partner_route_path - string - Optional route path for files uploaded by the Partner.
   *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
   *   to_partner_managed_folder_paths - array(string) - Managed folder paths inside the to-Partner folder.
   *   to_partner_route_path - string - Optional route path for files delivered to the Partner.
   *   partner_id (required) - int64 - ID of the Partner this Channel belongs to.
   *   path (required) - string - Channel path relative to the Partner root folder.
   *   workspace_id - int64 - ID of the Workspace associated with this Partner Channel.
   */
  public PartnerChannel create() throws RuntimeException {
    return create(null, null);
  }

  public PartnerChannel create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public PartnerChannel create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("partner_id") || parameters.get("partner_id") == null) {
      throw new NullPointerException("Parameter missing: partner_id parameters[\"partner_id\"]");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("direction") && !(parameters.get("direction") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: direction must be of type String parameters[\"direction\"]");
    }

    if (parameters.containsKey("use_channel_root") && !(parameters.get("use_channel_root") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_channel_root must be of type Boolean parameters[\"use_channel_root\"]");
    }

    if (parameters.containsKey("from_partner_folder_name") && !(parameters.get("from_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_folder_name must be of type String parameters[\"from_partner_folder_name\"]");
    }

    if (parameters.containsKey("from_partner_managed_folder_paths") && !(parameters.get("from_partner_managed_folder_paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: from_partner_managed_folder_paths must be of type String[] parameters[\"from_partner_managed_folder_paths\"]");
    }

    if (parameters.containsKey("from_partner_route_path") && !(parameters.get("from_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_route_path must be of type String parameters[\"from_partner_route_path\"]");
    }

    if (parameters.containsKey("to_partner_folder_name") && !(parameters.get("to_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_folder_name must be of type String parameters[\"to_partner_folder_name\"]");
    }

    if (parameters.containsKey("to_partner_managed_folder_paths") && !(parameters.get("to_partner_managed_folder_paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: to_partner_managed_folder_paths must be of type String[] parameters[\"to_partner_managed_folder_paths\"]");
    }

    if (parameters.containsKey("to_partner_route_path") && !(parameters.get("to_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_route_path must be of type String parameters[\"to_partner_route_path\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    String apiPath = "/partner_channels";
    TypeReference<PartnerChannel> typeReference = new TypeReference<PartnerChannel>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   direction - string - Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   *   use_channel_root - boolean - Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.
   *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
   *   from_partner_managed_folder_paths - array(string) - Managed folder paths inside the from-Partner folder.
   *   from_partner_route_path - string - Optional route path for files uploaded by the Partner.
   *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
   *   to_partner_managed_folder_paths - array(string) - Managed folder paths inside the to-Partner folder.
   *   to_partner_route_path - string - Optional route path for files delivered to the Partner.
   *   path - string - Channel path relative to the Partner root folder.
   */
  public PartnerChannel update() throws RuntimeException {
    return update(null, null, null);
  }

  public PartnerChannel update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public PartnerChannel update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public PartnerChannel update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("direction") && !(parameters.get("direction") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: direction must be of type String parameters[\"direction\"]");
    }

    if (parameters.containsKey("use_channel_root") && !(parameters.get("use_channel_root") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_channel_root must be of type Boolean parameters[\"use_channel_root\"]");
    }

    if (parameters.containsKey("from_partner_folder_name") && !(parameters.get("from_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_folder_name must be of type String parameters[\"from_partner_folder_name\"]");
    }

    if (parameters.containsKey("from_partner_managed_folder_paths") && !(parameters.get("from_partner_managed_folder_paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: from_partner_managed_folder_paths must be of type String[] parameters[\"from_partner_managed_folder_paths\"]");
    }

    if (parameters.containsKey("from_partner_route_path") && !(parameters.get("from_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_route_path must be of type String parameters[\"from_partner_route_path\"]");
    }

    if (parameters.containsKey("to_partner_folder_name") && !(parameters.get("to_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_folder_name must be of type String parameters[\"to_partner_folder_name\"]");
    }

    if (parameters.containsKey("to_partner_managed_folder_paths") && !(parameters.get("to_partner_managed_folder_paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: to_partner_managed_folder_paths must be of type String[] parameters[\"to_partner_managed_folder_paths\"]");
    }

    if (parameters.containsKey("to_partner_route_path") && !(parameters.get("to_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_route_path must be of type String parameters[\"to_partner_route_path\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/partner_channels/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<PartnerChannel> typeReference = new TypeReference<PartnerChannel>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/partner_channels/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
