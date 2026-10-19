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
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerChannel> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerChannel> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Partner Channels
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path` or `partner_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `partner_id` and `workspace_id`. Valid field combinations are `[ workspace_id, partner_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel} records, in a {@code ListIterator}
   */
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

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerChannel> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerChannel> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PartnerChannel find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PartnerChannel find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PartnerChannel find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Partner Channel
   *
   * @param id Partner Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel}
   */
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

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PartnerChannel get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public PartnerChannel get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Partner Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public PartnerChannel create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Partner Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public PartnerChannel create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Partner Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code direction} (String): Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.</li>
   * <li>{@code use_channel_root} (Boolean): Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.</li>
   * <li>{@code from_partner_folder_name} (String): Optional Channel-level from-Partner folder name override.</li>
   * <li>{@code from_partner_managed_folder_paths} (String[]): Managed folder paths inside the from-Partner folder.</li>
   * <li>{@code from_partner_route_path} (String): Optional route path for files uploaded by the Partner.</li>
   * <li>{@code to_partner_folder_name} (String): Optional Channel-level to-Partner folder name override.</li>
   * <li>{@code to_partner_managed_folder_paths} (String[]): Managed folder paths inside the to-Partner folder.</li>
   * <li>{@code to_partner_route_path} (String): Optional route path for files delivered to the Partner.</li>
   * <li>{@code partner_id} (Long): Required. ID of the Partner this Channel belongs to.</li>
   * <li>{@code path} (String): Required. Channel path relative to the Partner root folder.</li>
   * <li>{@code workspace_id} (Long): ID of the Workspace associated with this Partner Channel.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel}
   */
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
   * Update Partner Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public PartnerChannel update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Partner Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public PartnerChannel update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Partner Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public PartnerChannel update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Partner Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code direction} (String): Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.</li>
   * <li>{@code use_channel_root} (Boolean): Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.</li>
   * <li>{@code from_partner_folder_name} (String): Optional Channel-level from-Partner folder name override.</li>
   * <li>{@code from_partner_managed_folder_paths} (String[]): Managed folder paths inside the from-Partner folder.</li>
   * <li>{@code from_partner_route_path} (String): Optional route path for files uploaded by the Partner.</li>
   * <li>{@code to_partner_folder_name} (String): Optional Channel-level to-Partner folder name override.</li>
   * <li>{@code to_partner_managed_folder_paths} (String[]): Managed folder paths inside the to-Partner folder.</li>
   * <li>{@code to_partner_route_path} (String): Optional route path for files delivered to the Partner.</li>
   * <li>{@code path} (String): Channel path relative to the Partner root folder.</li>
   * </ul>
   *
   * @param id Partner Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel}
   */
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
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Partner Channel
   *
   * @param id Partner Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
