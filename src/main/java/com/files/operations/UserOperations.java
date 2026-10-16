package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.User;
import com.files.net.Attachment;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#users()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.User} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class UserOperations extends ResourceOperations {
  public UserOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id`, `company`, `name`, `disabled`, `authenticate_until`, `username`, `email`, `site_admin`, `last_desktop_login_at`, `last_login_at`, `password_validity_days` or `ssl_required`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `username`, `name`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until`, `not_site_admin`, `disabled`, `partner_id`, `primary_group_id` or `workspace_id`. Valid field combinations are `[ site_admin, username ]`, `[ not_site_admin, username ]`, `[ workspace_id, username ]`, `[ company, name ]`, `[ workspace_id, name ]`, `[ workspace_id, email ]`, `[ workspace_id, company ]`, `[ workspace_id, site_admin ]`, `[ workspace_id, not_site_admin ]`, `[ workspace_id, disabled ]`, `[ workspace_id, partner_id ]`, `[ workspace_id, site_admin, username ]`, `[ workspace_id, not_site_admin, username ]`, `[ workspace_id, disabled, username ]`, `[ workspace_id, partner_id, username ]` or `[ workspace_id, company, name ]`.
   *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
   *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
   *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `username`, `name`, `email` or `company`. Valid field combinations are `[ company, name ]`.
   *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
   *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
   *   ids - string - comma-separated list of User IDs
   *   include_parent_site_users - boolean - Include users from the parent site.
   *   include_default_workspace_users - boolean - When scoped to a Custom Workspace, include Default Workspace users who have permissions in that Workspace. Available to Site Administrators, and to Workspace Administrators and Folder Admins in that Workspace when the site shares its Default Workspace directory with Workspaces.
   *   search - string - Searches for partial matches of name, username, or email.
   */
  public ListIterator<User> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<User> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<User> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    if (parameters.containsKey("ids") && !(parameters.get("ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ids must be of type String parameters[\"ids\"]");
    }

    if (parameters.containsKey("include_parent_site_users") && !(parameters.get("include_parent_site_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_parent_site_users must be of type Boolean parameters[\"include_parent_site_users\"]");
    }

    if (parameters.containsKey("include_default_workspace_users") && !(parameters.get("include_default_workspace_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_default_workspace_users must be of type Boolean parameters[\"include_default_workspace_users\"]");
    }

    if (parameters.containsKey("search") && !(parameters.get("search") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: search must be of type String parameters[\"search\"]");
    }

    String apiPath = "/users";
    TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<User> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<User> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - User ID.
   */
  public User find() throws RuntimeException {
    return find(null, null, null);
  }

  public User find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public User find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public User find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/users/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<User> typeReference = new TypeReference<User>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public User get() throws RuntimeException {
    return get(null, null, null);
  }

  public User get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   avatar_file - file - An image file for your user avatar.
   *   avatar_delete - boolean - If true, the avatar will be deleted.
   *   change_password - string - Used for changing a password on an existing user.
   *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
   *   email - string - User's email.
   *   grant_permission - string - Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.
   *   group_id - int64 - Group ID to associate this user with.
   *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
   *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
   *   password - string - User password.
   *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
   *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
   *   ai_assistant_personality_id - int64 - AI Assistant Personality ID assigned directly to this user, if any.
   *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
   *   attachments_permission - boolean - DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
   *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
   *   authentication_method - string - How is this user authenticated?
   *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
   *   bypass_user_lifecycle_rules - boolean - Exempt this user from user lifecycle rules?
   *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
   *   dav_permission - boolean - Can the user connect with WebDAV?
   *   desktop_configuration_profile_id - int64 - Desktop Configuration Profile ID assigned directly to this user, if any.
   *   default_workspace_id - int64 - Workspace ID the user should land in by default when more than one Workspace is available.
   *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
   *   filesystem_layout - string - File system layout
   *   ftp_permission - boolean - Can the user access with FTP/FTPS?
   *   header_text - string - Text to display to the user in the header of the UI
   *   integration_centric_profile_id - int64 - Integration Centric Profile ID assigned directly to this user, if any.
   *   language - string - Preferred language
   *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
   *   name - string - User's full name
   *   company - string - User's company
   *   notes - string - Any internal notes on the user
   *   office_integration_enabled - boolean - Enable integration with Office for the web?
   *   partner_admin - boolean - Is this user a Partner administrator?
   *   partner_id - int64 - Partner ID if this user belongs to a Partner
   *   password_validity_days - int64 - Number of days to allow user to use the same password
   *   primary_group_id - int64 - Primary group ID for Group Admin scoping
   *   readonly_site_admin - boolean - Is the user an allowed to view all (non-billing) site configuration for this site?
   *   receive_admin_alerts - boolean - Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.
   *   notify_on_all_site_warnings - boolean - Should the user receive site warnings via email?
   *   notify_on_all_sso_failures - boolean - Should the user receive sso/scim/ldap configuration/sync failures via email?
   *   notify_on_all_user_security_events - boolean - Should the user receive user security events via email?
   *   notify_on_all_pending_work_failures - boolean - Should the user receive pending work failures via email?
   *   notify_on_all_siem_http_destination_failures - boolean - Should the user receive siem failures via email?
   *   notify_on_all_sync_failures - boolean - Should the user receive sync failures via email?
   *   notify_on_all_automation_failures - boolean - Should the user receive automation failures via email?
   *   notify_on_all_expectation_failures - boolean - Should the user receive expectation failures and misses via email?
   *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
   *   require_password_change - boolean - Is a password change required upon next user login?
   *   responsible_group_id - int64 - ID of the internal Group responsible for this Partner User, overriding the Partner default.
   *   responsible_user_id - int64 - ID of the internal User responsible for this Partner User, overriding the Partner default.
   *   restapi_permission - boolean - Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
   *   s3_compatible_endpoint_permission - boolean - Can the user access the S3-compatible endpoint? Defaults to true.
   *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
   *   sftp_permission - boolean - Can the user access with SFTP?
   *   site_admin - boolean - Is the user an administrator for this site?
   *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
   *   ssl_required - string - SSL required setting
   *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
   *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
   *   require_2fa - string - 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.
   *   tags - string - Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
   *   time_zone - string - User time zone
   *   user_root - string - If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.
   *   user_home - string - Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.
   *   workspace_admin - boolean - Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.
   *   username (required) - string - User's username
   *   workspace_id - int64 - ID of the Workspace the user belongs to. 0 is the Default Workspace.
   */
  public User create() throws RuntimeException {
    return create(null, null);
  }

  public User create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public User create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("username") || parameters.get("username") == null) {
      throw new NullPointerException("Parameter missing: username parameters[\"username\"]");
    }

    if (parameters.containsKey("avatar_file") && !(parameters.get("avatar_file") instanceof byte[] || parameters.get("avatar_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: avatar_file must be of type byte[] or Attachment parameters[\"avatar_file\"]");
    }

    if (parameters.containsKey("avatar_delete") && !(parameters.get("avatar_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: avatar_delete must be of type Boolean parameters[\"avatar_delete\"]");
    }

    if (parameters.containsKey("change_password") && !(parameters.get("change_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: change_password must be of type String parameters[\"change_password\"]");
    }

    if (parameters.containsKey("change_password_confirmation") && !(parameters.get("change_password_confirmation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: change_password_confirmation must be of type String parameters[\"change_password_confirmation\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    if (parameters.containsKey("grant_permission") && !(parameters.get("grant_permission") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: grant_permission must be of type String parameters[\"grant_permission\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (parameters.containsKey("imported_password_hash") && !(parameters.get("imported_password_hash") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: imported_password_hash must be of type String parameters[\"imported_password_hash\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("password_confirmation") && !(parameters.get("password_confirmation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password_confirmation must be of type String parameters[\"password_confirmation\"]");
    }

    if (parameters.containsKey("announcements_read") && !(parameters.get("announcements_read") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: announcements_read must be of type Boolean parameters[\"announcements_read\"]");
    }

    if (parameters.containsKey("ai_assistant_personality_id") && !(parameters.get("ai_assistant_personality_id") instanceof Long || parameters.get("ai_assistant_personality_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ai_assistant_personality_id must be of type Long or Integer parameters[\"ai_assistant_personality_id\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("attachments_permission") && !(parameters.get("attachments_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: attachments_permission must be of type Boolean parameters[\"attachments_permission\"]");
    }

    if (parameters.containsKey("authenticate_until") && !(parameters.get("authenticate_until") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authenticate_until must be of type String parameters[\"authenticate_until\"]");
    }

    if (parameters.containsKey("authentication_method") && !(parameters.get("authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authentication_method must be of type String parameters[\"authentication_method\"]");
    }

    if (parameters.containsKey("billing_permission") && !(parameters.get("billing_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: billing_permission must be of type Boolean parameters[\"billing_permission\"]");
    }

    if (parameters.containsKey("bypass_user_lifecycle_rules") && !(parameters.get("bypass_user_lifecycle_rules") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_user_lifecycle_rules must be of type Boolean parameters[\"bypass_user_lifecycle_rules\"]");
    }

    if (parameters.containsKey("bypass_site_allowed_ips") && !(parameters.get("bypass_site_allowed_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_site_allowed_ips must be of type Boolean parameters[\"bypass_site_allowed_ips\"]");
    }

    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }

    if (parameters.containsKey("desktop_configuration_profile_id") && !(parameters.get("desktop_configuration_profile_id") instanceof Long || parameters.get("desktop_configuration_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: desktop_configuration_profile_id must be of type Long or Integer parameters[\"desktop_configuration_profile_id\"]");
    }

    if (parameters.containsKey("default_workspace_id") && !(parameters.get("default_workspace_id") instanceof Long || parameters.get("default_workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: default_workspace_id must be of type Long or Integer parameters[\"default_workspace_id\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("filesystem_layout") && !(parameters.get("filesystem_layout") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filesystem_layout must be of type String parameters[\"filesystem_layout\"]");
    }

    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }

    if (parameters.containsKey("header_text") && !(parameters.get("header_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: header_text must be of type String parameters[\"header_text\"]");
    }

    if (parameters.containsKey("integration_centric_profile_id") && !(parameters.get("integration_centric_profile_id") instanceof Long || parameters.get("integration_centric_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: integration_centric_profile_id must be of type Long or Integer parameters[\"integration_centric_profile_id\"]");
    }

    if (parameters.containsKey("language") && !(parameters.get("language") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: language must be of type String parameters[\"language\"]");
    }

    if (parameters.containsKey("notification_daily_send_time") && !(parameters.get("notification_daily_send_time") instanceof Long || parameters.get("notification_daily_send_time") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: notification_daily_send_time must be of type Long or Integer parameters[\"notification_daily_send_time\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("company") && !(parameters.get("company") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: company must be of type String parameters[\"company\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("office_integration_enabled") && !(parameters.get("office_integration_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: office_integration_enabled must be of type Boolean parameters[\"office_integration_enabled\"]");
    }

    if (parameters.containsKey("partner_admin") && !(parameters.get("partner_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: partner_admin must be of type Boolean parameters[\"partner_admin\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("password_validity_days") && !(parameters.get("password_validity_days") instanceof Long || parameters.get("password_validity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: password_validity_days must be of type Long or Integer parameters[\"password_validity_days\"]");
    }

    if (parameters.containsKey("primary_group_id") && !(parameters.get("primary_group_id") instanceof Long || parameters.get("primary_group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: primary_group_id must be of type Long or Integer parameters[\"primary_group_id\"]");
    }

    if (parameters.containsKey("readonly_site_admin") && !(parameters.get("readonly_site_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: readonly_site_admin must be of type Boolean parameters[\"readonly_site_admin\"]");
    }

    if (parameters.containsKey("receive_admin_alerts") && !(parameters.get("receive_admin_alerts") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: receive_admin_alerts must be of type Boolean parameters[\"receive_admin_alerts\"]");
    }

    if (parameters.containsKey("notify_on_all_site_warnings") && !(parameters.get("notify_on_all_site_warnings") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_site_warnings must be of type Boolean parameters[\"notify_on_all_site_warnings\"]");
    }

    if (parameters.containsKey("notify_on_all_sso_failures") && !(parameters.get("notify_on_all_sso_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_sso_failures must be of type Boolean parameters[\"notify_on_all_sso_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_user_security_events") && !(parameters.get("notify_on_all_user_security_events") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_user_security_events must be of type Boolean parameters[\"notify_on_all_user_security_events\"]");
    }

    if (parameters.containsKey("notify_on_all_pending_work_failures") && !(parameters.get("notify_on_all_pending_work_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_pending_work_failures must be of type Boolean parameters[\"notify_on_all_pending_work_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_siem_http_destination_failures") && !(parameters.get("notify_on_all_siem_http_destination_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_siem_http_destination_failures must be of type Boolean parameters[\"notify_on_all_siem_http_destination_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_sync_failures") && !(parameters.get("notify_on_all_sync_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_sync_failures must be of type Boolean parameters[\"notify_on_all_sync_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_automation_failures") && !(parameters.get("notify_on_all_automation_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_automation_failures must be of type Boolean parameters[\"notify_on_all_automation_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_expectation_failures") && !(parameters.get("notify_on_all_expectation_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_expectation_failures must be of type Boolean parameters[\"notify_on_all_expectation_failures\"]");
    }

    if (parameters.containsKey("require_login_by") && !(parameters.get("require_login_by") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_login_by must be of type String parameters[\"require_login_by\"]");
    }

    if (parameters.containsKey("require_password_change") && !(parameters.get("require_password_change") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_password_change must be of type Boolean parameters[\"require_password_change\"]");
    }

    if (parameters.containsKey("responsible_group_id") && !(parameters.get("responsible_group_id") instanceof Long || parameters.get("responsible_group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_group_id must be of type Long or Integer parameters[\"responsible_group_id\"]");
    }

    if (parameters.containsKey("responsible_user_id") && !(parameters.get("responsible_user_id") instanceof Long || parameters.get("responsible_user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_user_id must be of type Long or Integer parameters[\"responsible_user_id\"]");
    }

    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint_permission") && !(parameters.get("s3_compatible_endpoint_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint_permission must be of type Boolean parameters[\"s3_compatible_endpoint_permission\"]");
    }

    if (parameters.containsKey("self_managed") && !(parameters.get("self_managed") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: self_managed must be of type Boolean parameters[\"self_managed\"]");
    }

    if (parameters.containsKey("sftp_permission") && !(parameters.get("sftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_permission must be of type Boolean parameters[\"sftp_permission\"]");
    }

    if (parameters.containsKey("site_admin") && !(parameters.get("site_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: site_admin must be of type Boolean parameters[\"site_admin\"]");
    }

    if (parameters.containsKey("skip_welcome_screen") && !(parameters.get("skip_welcome_screen") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_welcome_screen must be of type Boolean parameters[\"skip_welcome_screen\"]");
    }

    if (parameters.containsKey("ssl_required") && !(parameters.get("ssl_required") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ssl_required must be of type String parameters[\"ssl_required\"]");
    }

    if (parameters.containsKey("sso_strategy_id") && !(parameters.get("sso_strategy_id") instanceof Long || parameters.get("sso_strategy_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: sso_strategy_id must be of type Long or Integer parameters[\"sso_strategy_id\"]");
    }

    if (parameters.containsKey("subscribe_to_newsletter") && !(parameters.get("subscribe_to_newsletter") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: subscribe_to_newsletter must be of type Boolean parameters[\"subscribe_to_newsletter\"]");
    }

    if (parameters.containsKey("require_2fa") && !(parameters.get("require_2fa") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa must be of type String parameters[\"require_2fa\"]");
    }

    if (parameters.containsKey("tags") && !(parameters.get("tags") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: tags must be of type String parameters[\"tags\"]");
    }

    if (parameters.containsKey("time_zone") && !(parameters.get("time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: time_zone must be of type String parameters[\"time_zone\"]");
    }

    if (parameters.containsKey("user_root") && !(parameters.get("user_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_root must be of type String parameters[\"user_root\"]");
    }

    if (parameters.containsKey("user_home") && !(parameters.get("user_home") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_home must be of type String parameters[\"user_home\"]");
    }

    if (parameters.containsKey("workspace_admin") && !(parameters.get("workspace_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: workspace_admin must be of type Boolean parameters[\"workspace_admin\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("avatar_file") instanceof byte[]) {
      parameters.put("avatar_file", Attachment.fromBytes((byte[]) parameters.get("avatar_file")));
    }

    String apiPath = "/users";
    TypeReference<User> typeReference = new TypeReference<User>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   */
  public void unlock() throws RuntimeException {
    unlock(null, null, null);
  }

  public void unlock(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    unlock(id, parameters, null);
  }

  public void unlock(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    unlock(null, parameters, options);
  }

  public void unlock(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/users/%s/unlock", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Resend user welcome email
   */
  public void resendWelcomeEmail() throws RuntimeException {
    resendWelcomeEmail(null, null, null);
  }

  public void resendWelcomeEmail(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resendWelcomeEmail(id, parameters, null);
  }

  public void resendWelcomeEmail(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resendWelcomeEmail(null, parameters, options);
  }

  public void resendWelcomeEmail(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/users/%s/resend_welcome_email", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   */
  public void user2faReset() throws RuntimeException {
    user2faReset(null, null, null);
  }

  public void user2faReset(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    user2faReset(id, parameters, null);
  }

  public void user2faReset(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    user2faReset(null, parameters, options);
  }

  public void user2faReset(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/users/%s/2fa/reset", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Parameters:
   *   avatar_file - file - An image file for your user avatar.
   *   avatar_delete - boolean - If true, the avatar will be deleted.
   *   change_password - string - Used for changing a password on an existing user.
   *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
   *   email - string - User's email.
   *   grant_permission - string - Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.
   *   group_id - int64 - Group ID to associate this user with.
   *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
   *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
   *   password - string - User password.
   *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
   *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
   *   ai_assistant_personality_id - int64 - AI Assistant Personality ID assigned directly to this user, if any.
   *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
   *   attachments_permission - boolean - DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
   *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
   *   authentication_method - string - How is this user authenticated?
   *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
   *   bypass_user_lifecycle_rules - boolean - Exempt this user from user lifecycle rules?
   *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
   *   dav_permission - boolean - Can the user connect with WebDAV?
   *   desktop_configuration_profile_id - int64 - Desktop Configuration Profile ID assigned directly to this user, if any.
   *   default_workspace_id - int64 - Workspace ID the user should land in by default when more than one Workspace is available.
   *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
   *   filesystem_layout - string - File system layout
   *   ftp_permission - boolean - Can the user access with FTP/FTPS?
   *   header_text - string - Text to display to the user in the header of the UI
   *   integration_centric_profile_id - int64 - Integration Centric Profile ID assigned directly to this user, if any.
   *   language - string - Preferred language
   *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
   *   name - string - User's full name
   *   company - string - User's company
   *   notes - string - Any internal notes on the user
   *   office_integration_enabled - boolean - Enable integration with Office for the web?
   *   partner_admin - boolean - Is this user a Partner administrator?
   *   partner_id - int64 - Partner ID if this user belongs to a Partner
   *   password_validity_days - int64 - Number of days to allow user to use the same password
   *   primary_group_id - int64 - Primary group ID for Group Admin scoping
   *   readonly_site_admin - boolean - Is the user an allowed to view all (non-billing) site configuration for this site?
   *   receive_admin_alerts - boolean - Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.
   *   notify_on_all_site_warnings - boolean - Should the user receive site warnings via email?
   *   notify_on_all_sso_failures - boolean - Should the user receive sso/scim/ldap configuration/sync failures via email?
   *   notify_on_all_user_security_events - boolean - Should the user receive user security events via email?
   *   notify_on_all_pending_work_failures - boolean - Should the user receive pending work failures via email?
   *   notify_on_all_siem_http_destination_failures - boolean - Should the user receive siem failures via email?
   *   notify_on_all_sync_failures - boolean - Should the user receive sync failures via email?
   *   notify_on_all_automation_failures - boolean - Should the user receive automation failures via email?
   *   notify_on_all_expectation_failures - boolean - Should the user receive expectation failures and misses via email?
   *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
   *   require_password_change - boolean - Is a password change required upon next user login?
   *   responsible_group_id - int64 - ID of the internal Group responsible for this Partner User, overriding the Partner default.
   *   responsible_user_id - int64 - ID of the internal User responsible for this Partner User, overriding the Partner default.
   *   restapi_permission - boolean - Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
   *   s3_compatible_endpoint_permission - boolean - Can the user access the S3-compatible endpoint? Defaults to true.
   *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
   *   sftp_permission - boolean - Can the user access with SFTP?
   *   site_admin - boolean - Is the user an administrator for this site?
   *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
   *   ssl_required - string - SSL required setting
   *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
   *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
   *   require_2fa - string - 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.
   *   tags - string - Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
   *   time_zone - string - User time zone
   *   user_root - string - If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.
   *   user_home - string - Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.
   *   workspace_admin - boolean - Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.
   *   username - string - User's username
   *   workspace_id - int64 - Workspace ID. Only Site Administrators can change this field. Values supplied by Workspace Administrators, Group Administrators, or other non-Site Administrators using `/user` are ignored.
   *   clear_2fa - boolean - If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
   *   convert_to_partner_user - boolean - Required when assigning a Partner to an existing non-Partner user. If true, convert the user by assigning the partner_id provided.
   */
  public User update() throws RuntimeException {
    return update(null, null, null);
  }

  public User update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public User update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public User update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("avatar_file") && !(parameters.get("avatar_file") instanceof byte[] || parameters.get("avatar_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: avatar_file must be of type byte[] or Attachment parameters[\"avatar_file\"]");
    }

    if (parameters.containsKey("avatar_delete") && !(parameters.get("avatar_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: avatar_delete must be of type Boolean parameters[\"avatar_delete\"]");
    }

    if (parameters.containsKey("change_password") && !(parameters.get("change_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: change_password must be of type String parameters[\"change_password\"]");
    }

    if (parameters.containsKey("change_password_confirmation") && !(parameters.get("change_password_confirmation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: change_password_confirmation must be of type String parameters[\"change_password_confirmation\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    if (parameters.containsKey("grant_permission") && !(parameters.get("grant_permission") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: grant_permission must be of type String parameters[\"grant_permission\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }

    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }

    if (parameters.containsKey("imported_password_hash") && !(parameters.get("imported_password_hash") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: imported_password_hash must be of type String parameters[\"imported_password_hash\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("password_confirmation") && !(parameters.get("password_confirmation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password_confirmation must be of type String parameters[\"password_confirmation\"]");
    }

    if (parameters.containsKey("announcements_read") && !(parameters.get("announcements_read") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: announcements_read must be of type Boolean parameters[\"announcements_read\"]");
    }

    if (parameters.containsKey("ai_assistant_personality_id") && !(parameters.get("ai_assistant_personality_id") instanceof Long || parameters.get("ai_assistant_personality_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ai_assistant_personality_id must be of type Long or Integer parameters[\"ai_assistant_personality_id\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("attachments_permission") && !(parameters.get("attachments_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: attachments_permission must be of type Boolean parameters[\"attachments_permission\"]");
    }

    if (parameters.containsKey("authenticate_until") && !(parameters.get("authenticate_until") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authenticate_until must be of type String parameters[\"authenticate_until\"]");
    }

    if (parameters.containsKey("authentication_method") && !(parameters.get("authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authentication_method must be of type String parameters[\"authentication_method\"]");
    }

    if (parameters.containsKey("billing_permission") && !(parameters.get("billing_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: billing_permission must be of type Boolean parameters[\"billing_permission\"]");
    }

    if (parameters.containsKey("bypass_user_lifecycle_rules") && !(parameters.get("bypass_user_lifecycle_rules") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_user_lifecycle_rules must be of type Boolean parameters[\"bypass_user_lifecycle_rules\"]");
    }

    if (parameters.containsKey("bypass_site_allowed_ips") && !(parameters.get("bypass_site_allowed_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_site_allowed_ips must be of type Boolean parameters[\"bypass_site_allowed_ips\"]");
    }

    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }

    if (parameters.containsKey("desktop_configuration_profile_id") && !(parameters.get("desktop_configuration_profile_id") instanceof Long || parameters.get("desktop_configuration_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: desktop_configuration_profile_id must be of type Long or Integer parameters[\"desktop_configuration_profile_id\"]");
    }

    if (parameters.containsKey("default_workspace_id") && !(parameters.get("default_workspace_id") instanceof Long || parameters.get("default_workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: default_workspace_id must be of type Long or Integer parameters[\"default_workspace_id\"]");
    }

    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }

    if (parameters.containsKey("filesystem_layout") && !(parameters.get("filesystem_layout") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filesystem_layout must be of type String parameters[\"filesystem_layout\"]");
    }

    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }

    if (parameters.containsKey("header_text") && !(parameters.get("header_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: header_text must be of type String parameters[\"header_text\"]");
    }

    if (parameters.containsKey("integration_centric_profile_id") && !(parameters.get("integration_centric_profile_id") instanceof Long || parameters.get("integration_centric_profile_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: integration_centric_profile_id must be of type Long or Integer parameters[\"integration_centric_profile_id\"]");
    }

    if (parameters.containsKey("language") && !(parameters.get("language") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: language must be of type String parameters[\"language\"]");
    }

    if (parameters.containsKey("notification_daily_send_time") && !(parameters.get("notification_daily_send_time") instanceof Long || parameters.get("notification_daily_send_time") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: notification_daily_send_time must be of type Long or Integer parameters[\"notification_daily_send_time\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("company") && !(parameters.get("company") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: company must be of type String parameters[\"company\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }

    if (parameters.containsKey("office_integration_enabled") && !(parameters.get("office_integration_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: office_integration_enabled must be of type Boolean parameters[\"office_integration_enabled\"]");
    }

    if (parameters.containsKey("partner_admin") && !(parameters.get("partner_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: partner_admin must be of type Boolean parameters[\"partner_admin\"]");
    }

    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }

    if (parameters.containsKey("password_validity_days") && !(parameters.get("password_validity_days") instanceof Long || parameters.get("password_validity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: password_validity_days must be of type Long or Integer parameters[\"password_validity_days\"]");
    }

    if (parameters.containsKey("primary_group_id") && !(parameters.get("primary_group_id") instanceof Long || parameters.get("primary_group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: primary_group_id must be of type Long or Integer parameters[\"primary_group_id\"]");
    }

    if (parameters.containsKey("readonly_site_admin") && !(parameters.get("readonly_site_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: readonly_site_admin must be of type Boolean parameters[\"readonly_site_admin\"]");
    }

    if (parameters.containsKey("receive_admin_alerts") && !(parameters.get("receive_admin_alerts") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: receive_admin_alerts must be of type Boolean parameters[\"receive_admin_alerts\"]");
    }

    if (parameters.containsKey("notify_on_all_site_warnings") && !(parameters.get("notify_on_all_site_warnings") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_site_warnings must be of type Boolean parameters[\"notify_on_all_site_warnings\"]");
    }

    if (parameters.containsKey("notify_on_all_sso_failures") && !(parameters.get("notify_on_all_sso_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_sso_failures must be of type Boolean parameters[\"notify_on_all_sso_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_user_security_events") && !(parameters.get("notify_on_all_user_security_events") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_user_security_events must be of type Boolean parameters[\"notify_on_all_user_security_events\"]");
    }

    if (parameters.containsKey("notify_on_all_pending_work_failures") && !(parameters.get("notify_on_all_pending_work_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_pending_work_failures must be of type Boolean parameters[\"notify_on_all_pending_work_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_siem_http_destination_failures") && !(parameters.get("notify_on_all_siem_http_destination_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_siem_http_destination_failures must be of type Boolean parameters[\"notify_on_all_siem_http_destination_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_sync_failures") && !(parameters.get("notify_on_all_sync_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_sync_failures must be of type Boolean parameters[\"notify_on_all_sync_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_automation_failures") && !(parameters.get("notify_on_all_automation_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_automation_failures must be of type Boolean parameters[\"notify_on_all_automation_failures\"]");
    }

    if (parameters.containsKey("notify_on_all_expectation_failures") && !(parameters.get("notify_on_all_expectation_failures") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_all_expectation_failures must be of type Boolean parameters[\"notify_on_all_expectation_failures\"]");
    }

    if (parameters.containsKey("require_login_by") && !(parameters.get("require_login_by") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_login_by must be of type String parameters[\"require_login_by\"]");
    }

    if (parameters.containsKey("require_password_change") && !(parameters.get("require_password_change") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_password_change must be of type Boolean parameters[\"require_password_change\"]");
    }

    if (parameters.containsKey("responsible_group_id") && !(parameters.get("responsible_group_id") instanceof Long || parameters.get("responsible_group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_group_id must be of type Long or Integer parameters[\"responsible_group_id\"]");
    }

    if (parameters.containsKey("responsible_user_id") && !(parameters.get("responsible_user_id") instanceof Long || parameters.get("responsible_user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: responsible_user_id must be of type Long or Integer parameters[\"responsible_user_id\"]");
    }

    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint_permission") && !(parameters.get("s3_compatible_endpoint_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint_permission must be of type Boolean parameters[\"s3_compatible_endpoint_permission\"]");
    }

    if (parameters.containsKey("self_managed") && !(parameters.get("self_managed") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: self_managed must be of type Boolean parameters[\"self_managed\"]");
    }

    if (parameters.containsKey("sftp_permission") && !(parameters.get("sftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_permission must be of type Boolean parameters[\"sftp_permission\"]");
    }

    if (parameters.containsKey("site_admin") && !(parameters.get("site_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: site_admin must be of type Boolean parameters[\"site_admin\"]");
    }

    if (parameters.containsKey("skip_welcome_screen") && !(parameters.get("skip_welcome_screen") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_welcome_screen must be of type Boolean parameters[\"skip_welcome_screen\"]");
    }

    if (parameters.containsKey("ssl_required") && !(parameters.get("ssl_required") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ssl_required must be of type String parameters[\"ssl_required\"]");
    }

    if (parameters.containsKey("sso_strategy_id") && !(parameters.get("sso_strategy_id") instanceof Long || parameters.get("sso_strategy_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: sso_strategy_id must be of type Long or Integer parameters[\"sso_strategy_id\"]");
    }

    if (parameters.containsKey("subscribe_to_newsletter") && !(parameters.get("subscribe_to_newsletter") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: subscribe_to_newsletter must be of type Boolean parameters[\"subscribe_to_newsletter\"]");
    }

    if (parameters.containsKey("require_2fa") && !(parameters.get("require_2fa") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa must be of type String parameters[\"require_2fa\"]");
    }

    if (parameters.containsKey("tags") && !(parameters.get("tags") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: tags must be of type String parameters[\"tags\"]");
    }

    if (parameters.containsKey("time_zone") && !(parameters.get("time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: time_zone must be of type String parameters[\"time_zone\"]");
    }

    if (parameters.containsKey("user_root") && !(parameters.get("user_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_root must be of type String parameters[\"user_root\"]");
    }

    if (parameters.containsKey("user_home") && !(parameters.get("user_home") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_home must be of type String parameters[\"user_home\"]");
    }

    if (parameters.containsKey("workspace_admin") && !(parameters.get("workspace_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: workspace_admin must be of type Boolean parameters[\"workspace_admin\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }

    if (parameters.containsKey("clear_2fa") && !(parameters.get("clear_2fa") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: clear_2fa must be of type Boolean parameters[\"clear_2fa\"]");
    }

    if (parameters.containsKey("convert_to_partner_user") && !(parameters.get("convert_to_partner_user") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: convert_to_partner_user must be of type Boolean parameters[\"convert_to_partner_user\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("avatar_file") instanceof byte[]) {
      parameters.put("avatar_file", Attachment.fromBytes((byte[]) parameters.get("avatar_file")));
    }

    String apiPath = String.format("/users/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<User> typeReference = new TypeReference<User>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   new_owner_id - int64 - Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.
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

    if (parameters.containsKey("new_owner_id") && !(parameters.get("new_owner_id") instanceof Long || parameters.get("new_owner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: new_owner_id must be of type Long or Integer parameters[\"new_owner_id\"]");
    }

    String apiPath = String.format("/users/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
