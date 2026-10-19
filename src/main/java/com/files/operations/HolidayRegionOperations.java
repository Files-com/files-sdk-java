package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.HolidayRegion;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;

/**
 * HolidayRegion operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#holidayRegions()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.HolidayRegion} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class HolidayRegionOperations extends ResourceOperations {
  public HolidayRegionOperations(FilesClient client) {
    super(client);
  }

  /**
   * List all possible holiday regions
   *
   * @see #getSupported(HashMap, HashMap)
   */
  public HolidayRegion getSupported() throws RuntimeException {
    return getSupported(null, null);
  }

  /**
   * List all possible holiday regions
   *
   * @see #getSupported(HashMap, HashMap)
   */
  public HolidayRegion getSupported(HashMap<String, Object> parameters) throws RuntimeException {
    return getSupported(parameters, null);
  }

  /**
   * List all possible holiday regions
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code HolidayRegion}
   */
  public HolidayRegion getSupported(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/holiday_regions/supported";
    TypeReference<HolidayRegion> typeReference = new TypeReference<HolidayRegion>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }
}
