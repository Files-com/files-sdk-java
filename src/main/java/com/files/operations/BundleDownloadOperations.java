package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.BundleDownload;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BundleDownload operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#bundleDownloads()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.BundleDownload} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class BundleDownloadOperations extends ResourceOperations {
  public BundleDownloadOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `created_at`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
   *   bundle_id - int64 - Bundle ID
   *   bundle_registration_id - int64 - BundleRegistration ID
   */
  public ListIterator<BundleDownload> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<BundleDownload> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<BundleDownload> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("bundle_id") && !(parameters.get("bundle_id") instanceof Long || parameters.get("bundle_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_id must be of type Long or Integer parameters[\"bundle_id\"]");
    }

    if (parameters.containsKey("bundle_registration_id") && !(parameters.get("bundle_registration_id") instanceof Long || parameters.get("bundle_registration_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_registration_id must be of type Long or Integer parameters[\"bundle_registration_id\"]");
    }

    String apiPath = "/bundle_downloads";
    TypeReference<List<BundleDownload>> typeReference = new TypeReference<List<BundleDownload>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<BundleDownload> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<BundleDownload> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }
}
