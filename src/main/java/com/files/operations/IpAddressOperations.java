package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.IpAddress;
import com.files.models.PublicIpAddress;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * IpAddress operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#ipAddresses()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.IpAddress} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class IpAddressOperations extends ResourceOperations {
  public IpAddressOperations(FilesClient client) {
    super(client);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IpAddress> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IpAddress> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List IP Addresses associated with the current site
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code IpAddress} records, in a {@code ListIterator}
   */
  public ListIterator<IpAddress> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/ip_addresses";
    TypeReference<List<IpAddress>> typeReference = new TypeReference<List<IpAddress>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IpAddress> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List IP Addresses associated with the current site
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<IpAddress> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * List all possible public SmartFile IP addresses
   *
   * @see #getSmartfileReserved(HashMap, HashMap)
   */
  public PublicIpAddress getSmartfileReserved() throws RuntimeException {
    return getSmartfileReserved(null, null);
  }

  /**
   * List all possible public SmartFile IP addresses
   *
   * @see #getSmartfileReserved(HashMap, HashMap)
   */
  public PublicIpAddress getSmartfileReserved(HashMap<String, Object> parameters) throws RuntimeException {
    return getSmartfileReserved(parameters, null);
  }

  /**
   * List all possible public SmartFile IP addresses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicIpAddress}
   */
  public PublicIpAddress getSmartfileReserved(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/ip_addresses/smartfile-reserved";
    TypeReference<PublicIpAddress> typeReference = new TypeReference<PublicIpAddress>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List all possible public ExaVault IP addresses
   *
   * @see #getExavaultReserved(HashMap, HashMap)
   */
  public PublicIpAddress getExavaultReserved() throws RuntimeException {
    return getExavaultReserved(null, null);
  }

  /**
   * List all possible public ExaVault IP addresses
   *
   * @see #getExavaultReserved(HashMap, HashMap)
   */
  public PublicIpAddress getExavaultReserved(HashMap<String, Object> parameters) throws RuntimeException {
    return getExavaultReserved(parameters, null);
  }

  /**
   * List all possible public ExaVault IP addresses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicIpAddress}
   */
  public PublicIpAddress getExavaultReserved(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/ip_addresses/exavault-reserved";
    TypeReference<PublicIpAddress> typeReference = new TypeReference<PublicIpAddress>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List all possible public IP addresses
   *
   * @see #getReserved(HashMap, HashMap)
   */
  public PublicIpAddress getReserved() throws RuntimeException {
    return getReserved(null, null);
  }

  /**
   * List all possible public IP addresses
   *
   * @see #getReserved(HashMap, HashMap)
   */
  public PublicIpAddress getReserved(HashMap<String, Object> parameters) throws RuntimeException {
    return getReserved(parameters, null);
  }

  /**
   * List all possible public IP addresses
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PublicIpAddress}
   */
  public PublicIpAddress getReserved(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/ip_addresses/reserved";
    TypeReference<PublicIpAddress> typeReference = new TypeReference<PublicIpAddress>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }
}
