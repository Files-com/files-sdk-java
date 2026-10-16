package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Bundle;
import com.files.net.Attachment;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Bundle operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#bundles()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Bundle} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class BundleOperations extends ResourceOperations {
  public BundleOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `expires_at`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `expires_at`, `code`, `group_id`, `user_id` or `bypasses_site_expiration_rules`. Valid field combinations are `[ group_id, expires_at ]` and `[ user_id, expires_at ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at` and `expires_at`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at` and `expires_at`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `code`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at` and `expires_at`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at` and `expires_at`.
   *   deleted - boolean - If true, only list deleted Share Links.
   */
  public ListIterator<Bundle> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<Bundle> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<Bundle> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("deleted") && !(parameters.get("deleted") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: deleted must be of type Boolean parameters[\"deleted\"]");
    }

    String apiPath = "/bundles";
    TypeReference<List<Bundle>> typeReference = new TypeReference<List<Bundle>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<Bundle> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<Bundle> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Bundle ID.
   *   deleted - boolean - If true, show a deleted Share Link.
   */
  public Bundle find() throws RuntimeException {
    return find(null, null, null);
  }

  public Bundle find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public Bundle find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public Bundle find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("deleted") && !(parameters.get("deleted") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: deleted must be of type Boolean parameters[\"deleted\"]");
    }

    String apiPath = String.format("/bundles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public Bundle get() throws RuntimeException {
    return get(null, null, null);
  }

  public Bundle get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   paths (required) - array(string) - A list of paths to include in this bundle.
   *   password - string - Password for this bundle.
   *   bypasses_site_expiration_rules - boolean - If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
   *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
   *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   *   expires_at - string - Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   *   max_uses - int64 - Maximum number of times bundle can be accessed
   *   group_id - int64 - Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   *   internal_name - string - Internal name for identifying this Share Link.
   *   description - string - Public description
   *   note - string - Bundle internal note
   *   code - string - Bundle code.  This code forms the end part of the Public URL.
   *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
   *   permissions - string - Permissions that apply to Folders in this Share Link.
   *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
   *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
   *   inbox_id - int64 - ID of the associated inbox, if available.
   *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   *   send_one_time_password_to_recipient_at_registration - boolean - If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
   *   skip_email - boolean - BundleRegistrations can be saved without providing email?
   *   skip_name - boolean - BundleRegistrations can be saved without providing name?
   *   skip_company - boolean - BundleRegistrations can be saved without providing company?
   *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
   *   snapshot_id - int64 - ID of the snapshot containing this bundle's contents.
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
   *   watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   */
  public Bundle create() throws RuntimeException {
    return create(null, null);
  }

  public Bundle create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public Bundle create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("paths") || parameters.get("paths") == null) {
      throw new NullPointerException("Parameter missing: paths parameters[\"paths\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("bypasses_site_expiration_rules") && !(parameters.get("bypasses_site_expiration_rules") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypasses_site_expiration_rules must be of type Boolean parameters[\"bypasses_site_expiration_rules\"]");
    }

    if (parameters.containsKey("form_field_set_id") && !(parameters.get("form_field_set_id") instanceof Long || parameters.get("form_field_set_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: form_field_set_id must be of type Long or Integer parameters[\"form_field_set_id\"]");
    }

    if (parameters.containsKey("create_snapshot") && !(parameters.get("create_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: create_snapshot must be of type Boolean parameters[\"create_snapshot\"]");
    }

    if (parameters.containsKey("dont_separate_submissions_by_folder") && !(parameters.get("dont_separate_submissions_by_folder") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dont_separate_submissions_by_folder must be of type Boolean parameters[\"dont_separate_submissions_by_folder\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("finalize_snapshot") && !(parameters.get("finalize_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: finalize_snapshot must be of type Boolean parameters[\"finalize_snapshot\"]");
    }

    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long || parameters.get("max_uses") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long or Integer parameters[\"max_uses\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("internal_name") && !(parameters.get("internal_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: internal_name must be of type String parameters[\"internal_name\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }

    if (parameters.containsKey("path_template") && !(parameters.get("path_template") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template must be of type String parameters[\"path_template\"]");
    }

    if (parameters.containsKey("path_template_time_zone") && !(parameters.get("path_template_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template_time_zone must be of type String parameters[\"path_template_time_zone\"]");
    }

    if (parameters.containsKey("permissions") && !(parameters.get("permissions") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permissions must be of type String parameters[\"permissions\"]");
    }

    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }

    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long || parameters.get("clickwrap_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long or Integer parameters[\"clickwrap_id\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }

    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }

    if (parameters.containsKey("send_one_time_password_to_recipient_at_registration") && !(parameters.get("send_one_time_password_to_recipient_at_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: send_one_time_password_to_recipient_at_registration must be of type Boolean parameters[\"send_one_time_password_to_recipient_at_registration\"]");
    }

    if (parameters.containsKey("send_email_receipt_to_uploader") && !(parameters.get("send_email_receipt_to_uploader") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: send_email_receipt_to_uploader must be of type Boolean parameters[\"send_email_receipt_to_uploader\"]");
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

    if (parameters.containsKey("start_access_on_date") && !(parameters.get("start_access_on_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_access_on_date must be of type String parameters[\"start_access_on_date\"]");
    }

    if (parameters.containsKey("snapshot_id") && !(parameters.get("snapshot_id") instanceof Long || parameters.get("snapshot_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: snapshot_id must be of type Long or Integer parameters[\"snapshot_id\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("watermark_attachment_file") && !(parameters.get("watermark_attachment_file") instanceof byte[] || parameters.get("watermark_attachment_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_file must be of type byte[] or Attachment parameters[\"watermark_attachment_file\"]");
    }

    if (parameters.containsKey("watermark_value") && !(parameters.get("watermark_value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: watermark_value must be of type Object parameters[\"watermark_value\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("watermark_attachment_file") instanceof byte[]) {
      parameters.put("watermark_attachment_file", Attachment.fromBytes((byte[]) parameters.get("watermark_attachment_file")));
    }

    String apiPath = "/bundles";
    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Send email(s) with a link to bundle
   *
   * Parameters:
   *   to - array(string) - A list of email addresses to share this bundle with. Required unless `recipients` is used.
   *   note - string - Note to include in email.
   *   recipients - array(object) - A list of recipients to share this bundle with. Required unless `to` is used.
   */
  public void share() throws RuntimeException {
    share(null, null, null);
  }

  public void share(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    share(id, parameters, null);
  }

  public void share(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    share(null, parameters, options);
  }

  public void share(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("to") && !(parameters.get("to") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: to must be of type String[] parameters[\"to\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("recipients") && !(parameters.get("recipients") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: recipients must be of type Object[] parameters[\"recipients\"]");
    }

    String apiPath = String.format("/bundles/%s/share", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Parameters:
   *   paths - array(string) - A list of paths to include in this bundle.
   *   password - string - Password for this bundle.
   *   bypasses_site_expiration_rules - boolean - If true, this Share Link bypasses site-wide expiration rules. Only site admins may set this.
   *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
   *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
   *   code - string - Bundle code.  This code forms the end part of the Public URL.
   *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
   *   description - string - Public description
   *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
   *   expires_at - string - Explicit Bundle expiration date/time. If not set, the site-wide expiration setting may apply.
   *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
   *   inbox_id - int64 - ID of the associated inbox, if available.
   *   max_uses - int64 - Maximum number of times bundle can be accessed
   *   group_id - int64 - Owning group ID. If set, members of this group can view, edit, and share this Share Link.
   *   internal_name - string - Internal name for identifying this Share Link.
   *   note - string - Bundle internal note
   *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
   *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
   *   permissions - string - Permissions that apply to Folders in this Share Link.
   *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
   *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
   *   send_one_time_password_to_recipient_at_registration - boolean - If true, require_share_recipient bundles will send a one-time password to the recipient when they register. Cannot be enabled if the bundle has a password set.
   *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
   *   skip_company - boolean - BundleRegistrations can be saved without providing company?
   *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
   *   skip_email - boolean - BundleRegistrations can be saved without providing email?
   *   skip_name - boolean - BundleRegistrations can be saved without providing name?
   *   user_id - int64 - The owning user id. Only site admins can set this.
   *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
   *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
   *   watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   *   workspace_id - int64 - Workspace ID. `0` means the default workspace.
   */
  public Bundle update() throws RuntimeException {
    return update(null, null, null);
  }

  public Bundle update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public Bundle update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public Bundle update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("bypasses_site_expiration_rules") && !(parameters.get("bypasses_site_expiration_rules") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypasses_site_expiration_rules must be of type Boolean parameters[\"bypasses_site_expiration_rules\"]");
    }

    if (parameters.containsKey("form_field_set_id") && !(parameters.get("form_field_set_id") instanceof Long || parameters.get("form_field_set_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: form_field_set_id must be of type Long or Integer parameters[\"form_field_set_id\"]");
    }

    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long || parameters.get("clickwrap_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long or Integer parameters[\"clickwrap_id\"]");
    }

    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }

    if (parameters.containsKey("create_snapshot") && !(parameters.get("create_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: create_snapshot must be of type Boolean parameters[\"create_snapshot\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("dont_separate_submissions_by_folder") && !(parameters.get("dont_separate_submissions_by_folder") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dont_separate_submissions_by_folder must be of type Boolean parameters[\"dont_separate_submissions_by_folder\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("finalize_snapshot") && !(parameters.get("finalize_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: finalize_snapshot must be of type Boolean parameters[\"finalize_snapshot\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }

    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long || parameters.get("max_uses") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long or Integer parameters[\"max_uses\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("internal_name") && !(parameters.get("internal_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: internal_name must be of type String parameters[\"internal_name\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("path_template") && !(parameters.get("path_template") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template must be of type String parameters[\"path_template\"]");
    }

    if (parameters.containsKey("path_template_time_zone") && !(parameters.get("path_template_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template_time_zone must be of type String parameters[\"path_template_time_zone\"]");
    }

    if (parameters.containsKey("permissions") && !(parameters.get("permissions") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permissions must be of type String parameters[\"permissions\"]");
    }

    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }

    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }

    if (parameters.containsKey("send_one_time_password_to_recipient_at_registration") && !(parameters.get("send_one_time_password_to_recipient_at_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: send_one_time_password_to_recipient_at_registration must be of type Boolean parameters[\"send_one_time_password_to_recipient_at_registration\"]");
    }

    if (parameters.containsKey("send_email_receipt_to_uploader") && !(parameters.get("send_email_receipt_to_uploader") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: send_email_receipt_to_uploader must be of type Boolean parameters[\"send_email_receipt_to_uploader\"]");
    }

    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }

    if (parameters.containsKey("start_access_on_date") && !(parameters.get("start_access_on_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_access_on_date must be of type String parameters[\"start_access_on_date\"]");
    }

    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }

    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("watermark_attachment_delete") && !(parameters.get("watermark_attachment_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_delete must be of type Boolean parameters[\"watermark_attachment_delete\"]");
    }

    if (parameters.containsKey("watermark_attachment_file") && !(parameters.get("watermark_attachment_file") instanceof byte[] || parameters.get("watermark_attachment_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_file must be of type byte[] or Attachment parameters[\"watermark_attachment_file\"]");
    }

    if (parameters.containsKey("watermark_value") && !(parameters.get("watermark_value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: watermark_value must be of type Object parameters[\"watermark_value\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("watermark_attachment_file") instanceof byte[]) {
      parameters.put("watermark_attachment_file", Attachment.fromBytes((byte[]) parameters.get("watermark_attachment_file")));
    }

    String apiPath = String.format("/bundles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
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

    String apiPath = String.format("/bundles/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
