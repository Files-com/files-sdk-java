package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.DnsRecord;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;
import java.util.List;

/**
 * DnsRecord operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#dnsRecords()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.DnsRecord} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class DnsRecordOperations extends ResourceOperations {
  public DnsRecordOperations(FilesClient client) {
    super(client);
  }

  /**
   * Show Site DNS Configuration
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DnsRecord> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * Show Site DNS Configuration
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DnsRecord> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * Show Site DNS Configuration
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code DnsRecord} records, in a {@code ListIterator}
   */
  public ListIterator<DnsRecord> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/dns_records";
    TypeReference<List<DnsRecord>> typeReference = new TypeReference<List<DnsRecord>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Site DNS Configuration
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DnsRecord> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * Show Site DNS Configuration
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<DnsRecord> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }
}
