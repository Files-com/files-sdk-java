package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.ChildSiteManagementPolicy;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * ChildSiteManagementPolicy operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#childSiteManagementPolicies()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.ChildSiteManagementPolicy} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class ChildSiteManagementPolicyOperations extends ResourceOperations {
  public ChildSiteManagementPolicyOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public ListIterator<ChildSiteManagementPolicy> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/child_site_management_policies";
    TypeReference<List<ChildSiteManagementPolicy>> typeReference = new TypeReference<List<ChildSiteManagementPolicy>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<ChildSiteManagementPolicy> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<ChildSiteManagementPolicy> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Child Site Management Policy ID.
   */
  public ChildSiteManagementPolicy find() throws RuntimeException {
    return find(null, null, null);
  }

  public ChildSiteManagementPolicy find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public ChildSiteManagementPolicy find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public ChildSiteManagementPolicy find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/child_site_management_policies/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ChildSiteManagementPolicy> typeReference = new TypeReference<ChildSiteManagementPolicy>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ChildSiteManagementPolicy get() throws RuntimeException {
    return get(null, null, null);
  }

  public ChildSiteManagementPolicy get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
   *   skip_child_site_ids - array(int64) - IDs of child sites excluded from this default policy.
   *   child_site_ids - array(int64) - IDs of child sites explicitly assigned to this non-default policy.
   *   default_policy - boolean - Whether this policy applies to child sites not explicitly assigned to another policy.
   *   policy_type (required) - string - Type of policy.  Valid values: `settings`.
   *   name - string - Name for this policy.
   *   description - string - Description for this policy.
   */
  public ChildSiteManagementPolicy create() throws RuntimeException {
    return create(null, null);
  }

  public ChildSiteManagementPolicy create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public ChildSiteManagementPolicy create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("policy_type") || parameters.get("policy_type") == null) {
      throw new NullPointerException("Parameter missing: policy_type parameters[\"policy_type\"]");
    }

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }

    if (parameters.containsKey("skip_child_site_ids") && !(parameters.get("skip_child_site_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: skip_child_site_ids must be of type Long[] parameters[\"skip_child_site_ids\"]");
    }

    if (parameters.containsKey("child_site_ids") && !(parameters.get("child_site_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: child_site_ids must be of type Long[] parameters[\"child_site_ids\"]");
    }

    if (parameters.containsKey("default_policy") && !(parameters.get("default_policy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: default_policy must be of type Boolean parameters[\"default_policy\"]");
    }

    if (parameters.containsKey("policy_type") && !(parameters.get("policy_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: policy_type must be of type String parameters[\"policy_type\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    String apiPath = "/child_site_management_policies";
    TypeReference<ChildSiteManagementPolicy> typeReference = new TypeReference<ChildSiteManagementPolicy>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
   *   skip_child_site_ids - array(int64) - IDs of child sites excluded from this default policy.
   *   child_site_ids - array(int64) - IDs of child sites explicitly assigned to this non-default policy.
   *   default_policy - boolean - Whether this policy applies to child sites not explicitly assigned to another policy.
   *   policy_type - string - Type of policy.  Valid values: `settings`.
   *   name - string - Name for this policy.
   *   description - string - Description for this policy.
   */
  public ChildSiteManagementPolicy update() throws RuntimeException {
    return update(null, null, null);
  }

  public ChildSiteManagementPolicy update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public ChildSiteManagementPolicy update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public ChildSiteManagementPolicy update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }

    if (parameters.containsKey("skip_child_site_ids") && !(parameters.get("skip_child_site_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: skip_child_site_ids must be of type Long[] parameters[\"skip_child_site_ids\"]");
    }

    if (parameters.containsKey("child_site_ids") && !(parameters.get("child_site_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: child_site_ids must be of type Long[] parameters[\"child_site_ids\"]");
    }

    if (parameters.containsKey("default_policy") && !(parameters.get("default_policy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: default_policy must be of type Boolean parameters[\"default_policy\"]");
    }

    if (parameters.containsKey("policy_type") && !(parameters.get("policy_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: policy_type must be of type String parameters[\"policy_type\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    String apiPath = String.format("/child_site_management_policies/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<ChildSiteManagementPolicy> typeReference = new TypeReference<ChildSiteManagementPolicy>() {};
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

    String apiPath = String.format("/child_site_management_policies/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
