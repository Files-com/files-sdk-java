package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.FormFieldSet;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * FormFieldSet operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#formFieldSets()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.FormFieldSet} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class FormFieldSetOperations extends ResourceOperations {
  public FormFieldSetOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public ListIterator<FormFieldSet> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<FormFieldSet> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<FormFieldSet> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = "/form_field_sets";
    TypeReference<List<FormFieldSet>> typeReference = new TypeReference<List<FormFieldSet>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<FormFieldSet> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<FormFieldSet> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Form Field Set ID.
   */
  public FormFieldSet find() throws RuntimeException {
    return find(null, null, null);
  }

  public FormFieldSet find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public FormFieldSet find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public FormFieldSet find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/form_field_sets/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public FormFieldSet get() throws RuntimeException {
    return get(null, null, null);
  }

  public FormFieldSet get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   title - string - Title to be displayed
   *   require_complete_metadata_capture - boolean - Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   *   workspace_id - int64 - Workspace ID
   *   skip_email - boolean - Skip validating form email
   *   skip_name - boolean - Skip validating form name
   *   skip_company - boolean - Skip validating company
   *   form_fields - array(object)
   */
  public FormFieldSet create() throws RuntimeException {
    return create(null, null);
  }

  public FormFieldSet create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public FormFieldSet create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (parameters.containsKey("require_complete_metadata_capture") && !(parameters.get("require_complete_metadata_capture") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_complete_metadata_capture must be of type Boolean parameters[\"require_complete_metadata_capture\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }

    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }

    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }

    if (parameters.containsKey("form_fields") && !(parameters.get("form_fields") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: form_fields must be of type Object[] parameters[\"form_fields\"]");
    }

    String apiPath = "/form_field_sets";
    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   title - string - Title to be displayed
   *   require_complete_metadata_capture - boolean - Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   *   workspace_id - int64 - Workspace ID
   *   skip_email - boolean - Skip validating form email
   *   skip_name - boolean - Skip validating form name
   *   skip_company - boolean - Skip validating company
   *   form_fields - array(object)
   */
  public FormFieldSet update() throws RuntimeException {
    return update(null, null, null);
  }

  public FormFieldSet update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public FormFieldSet update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public FormFieldSet update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (parameters.containsKey("require_complete_metadata_capture") && !(parameters.get("require_complete_metadata_capture") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_complete_metadata_capture must be of type Boolean parameters[\"require_complete_metadata_capture\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }

    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }

    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }

    if (parameters.containsKey("form_fields") && !(parameters.get("form_fields") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: form_fields must be of type Object[] parameters[\"form_fields\"]");
    }

    String apiPath = String.format("/form_field_sets/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<FormFieldSet> typeReference = new TypeReference<FormFieldSet>() {};
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

    String apiPath = String.format("/form_field_sets/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
