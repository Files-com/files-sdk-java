package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.PartnerSiteRequest;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * PartnerSiteRequest operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#partnerSiteRequests()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.PartnerSiteRequest} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class PartnerSiteRequestOperations extends ResourceOperations {
  public PartnerSiteRequestOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerSiteRequest> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Partner Site Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `host_partner_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `host_partner_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerSiteRequest} records, in a {@code ListIterator}
   */
  public ListIterator<PartnerSiteRequest> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/partner_site_requests";
    TypeReference<List<PartnerSiteRequest>> typeReference = new TypeReference<List<PartnerSiteRequest>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerSiteRequest> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Partner Site Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<PartnerSiteRequest> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Find partner site request by pairing key
   *
   * @see #findByPairingKey(HashMap, HashMap)
   */
  public void findByPairingKey() throws RuntimeException {
    findByPairingKey(null, null);
  }

  /**
   * Find partner site request by pairing key
   *
   * @see #findByPairingKey(HashMap, HashMap)
   */
  public void findByPairingKey(HashMap<String, Object> parameters) throws RuntimeException {
    findByPairingKey(parameters, null);
  }

  /**
   * Find partner site request by pairing key
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code pairing_key} (String): Required. Pairing key for the partner site request</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void findByPairingKey(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("pairing_key") || parameters.get("pairing_key") == null) {
      throw new NullPointerException("Parameter missing: pairing_key parameters[\"pairing_key\"]");
    }

    if (parameters.containsKey("pairing_key") && !(parameters.get("pairing_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pairing_key must be of type String parameters[\"pairing_key\"]");
    }

    String apiPath = "/partner_site_requests/find_by_pairing_key";
    request(RequestMethods.GET, apiPath, parameters, options);
  }

  /**
   * Create Partner Site Request
   *
   * @see #create(HashMap, HashMap)
   */
  public PartnerSiteRequest create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Partner Site Request
   *
   * @see #create(HashMap, HashMap)
   */
  public PartnerSiteRequest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create Partner Site Request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code host_partner_id} (Long): Required. Host Partner ID to link with</li>
   * <li>{@code guest_site_url} (String): Required. Guest Site URL to link to</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerSiteRequest}
   */
  public PartnerSiteRequest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("host_partner_id") || parameters.get("host_partner_id") == null) {
      throw new NullPointerException("Parameter missing: host_partner_id parameters[\"host_partner_id\"]");
    }

    if (!parameters.containsKey("guest_site_url") || parameters.get("guest_site_url") == null) {
      throw new NullPointerException("Parameter missing: guest_site_url parameters[\"guest_site_url\"]");
    }

    if (parameters.containsKey("host_partner_id") && !(parameters.get("host_partner_id") instanceof Long || parameters.get("host_partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: host_partner_id must be of type Long or Integer parameters[\"host_partner_id\"]");
    }

    if (parameters.containsKey("guest_site_url") && !(parameters.get("guest_site_url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: guest_site_url must be of type String parameters[\"guest_site_url\"]");
    }

    String apiPath = "/partner_site_requests";
    TypeReference<PartnerSiteRequest> typeReference = new TypeReference<PartnerSiteRequest>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Reject partner site request
   *
   * @see #reject(HashMap, HashMap)
   */
  public void reject() throws RuntimeException {
    reject(null, null);
  }

  /**
   * Reject partner site request
   *
   * @see #reject(HashMap, HashMap)
   */
  public void reject(HashMap<String, Object> parameters) throws RuntimeException {
    reject(parameters, null);
  }

  /**
   * Reject partner site request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code pairing_key} (String): Required. Pairing key for the partner site request</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void reject(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("pairing_key") || parameters.get("pairing_key") == null) {
      throw new NullPointerException("Parameter missing: pairing_key parameters[\"pairing_key\"]");
    }

    if (parameters.containsKey("pairing_key") && !(parameters.get("pairing_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pairing_key must be of type String parameters[\"pairing_key\"]");
    }

    String apiPath = "/partner_site_requests/reject";
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Approve partner site request
   *
   * @see #approve(HashMap, HashMap)
   */
  public void approve() throws RuntimeException {
    approve(null, null);
  }

  /**
   * Approve partner site request
   *
   * @see #approve(HashMap, HashMap)
   */
  public void approve(HashMap<String, Object> parameters) throws RuntimeException {
    approve(parameters, null);
  }

  /**
   * Approve partner site request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code pairing_key} (String): Required. Pairing key for the partner site request</li>
   * <li>{@code partner_id} (Long): ID of an existing Partner on this site, with the host role, that represents the requesting organization. The connection binds to that Partner and makes it host_and_guest. When omitted, a guest Partner named after the host site is created.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void approve(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("pairing_key") || parameters.get("pairing_key") == null) {
      throw new NullPointerException("Parameter missing: pairing_key parameters[\"pairing_key\"]");
    }

    if (parameters.containsKey("pairing_key") && !(parameters.get("pairing_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pairing_key must be of type String parameters[\"pairing_key\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    String apiPath = "/partner_site_requests/approve";
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Partner Site Request
   *
   * @param id Partner Site Request ID.
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

    String apiPath = String.format("/partner_site_requests/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Partner Site Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
