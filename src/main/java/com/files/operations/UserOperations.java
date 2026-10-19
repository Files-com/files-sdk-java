package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.User;
import com.files.net.Attachment;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

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
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<User> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<User> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Users
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id`, `company`, `name`, `disabled`, `authenticate_until`, `username`, `email`, `site_admin`, `last_desktop_login_at`, `last_login_at`, `password_validity_days` or `ssl_required`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `username`, `name`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until`, `not_site_admin`, `disabled`, `partner_id`, `primary_group_id` or `workspace_id`. Valid field combinations are `[ site_admin, username ]`, `[ not_site_admin, username ]`, `[ workspace_id, username ]`, `[ company, name ]`, `[ workspace_id, name ]`, `[ workspace_id, email ]`, `[ workspace_id, company ]`, `[ workspace_id, site_admin ]`, `[ workspace_id, not_site_admin ]`, `[ workspace_id, disabled ]`, `[ workspace_id, partner_id ]`, `[ workspace_id, site_admin, username ]`, `[ workspace_id, not_site_admin, username ]`, `[ workspace_id, disabled, username ]`, `[ workspace_id, partner_id, username ]` or `[ workspace_id, company, name ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `username`, `name`, `email` or `company`. Valid field combinations are `[ company, name ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code ids} (String): comma-separated list of User IDs</li>
   * <li>{@code include_parent_site_users} (Boolean): Include users from the parent site.</li>
   * <li>{@code include_default_workspace_users} (Boolean): When scoped to a Custom Workspace, include Default Workspace users who have permissions in that Workspace. Available to Site Administrators, and to Workspace Administrators and Folder Admins in that Workspace when the site shares its Default Workspace directory with Workspaces.</li>
   * <li>{@code search} (String): Searches for partial matches of name, username, or email.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User} records, in a {@code ListIterator}
   */
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

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<User> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<User> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public User find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public User find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public User find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User}
   */
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

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public User get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public User get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User
   *
   * @see #create(HashMap, HashMap)
   */
  public User create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User
   *
   * @see #create(HashMap, HashMap)
   */
  public User create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code avatar_file} (byte[] or Attachment): An image file for your user avatar.</li>
   * <li>{@code avatar_delete} (Boolean): If true, the avatar will be deleted.</li>
   * <li>{@code change_password} (String): Used for changing a password on an existing user.</li>
   * <li>{@code change_password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.</li>
   * <li>{@code email} (String): User's email.</li>
   * <li>{@code grant_permission} (String): Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.</li>
   * <li>{@code group_id} (Long): Group ID to associate this user with.</li>
   * <li>{@code group_ids} (String): A list of group ids to associate this user with.  Comma delimited.</li>
   * <li>{@code imported_password_hash} (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.</li>
   * <li>{@code password} (String): User password.</li>
   * <li>{@code password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.</li>
   * <li>{@code announcements_read} (Boolean): Signifies that the user has read all the announcements in the UI.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned directly to this user, if any.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code attachments_permission} (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.</li>
   * <li>{@code authenticate_until} (String): Scheduled Date/Time at which user will be deactivated</li>
   * <li>{@code authentication_method} (String): How is this user authenticated?</li>
   * <li>{@code billing_permission} (Boolean): Allow this user to perform operations on the account, payments, and invoices?</li>
   * <li>{@code bypass_user_lifecycle_rules} (Boolean): Exempt this user from user lifecycle rules?</li>
   * <li>{@code bypass_site_allowed_ips} (Boolean): Allow this user to skip site-wide IP blacklists?</li>
   * <li>{@code dav_permission} (Boolean): Can the user connect with WebDAV?</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned directly to this user, if any.</li>
   * <li>{@code default_workspace_id} (Long): Workspace ID the user should land in by default when more than one Workspace is available.</li>
   * <li>{@code disabled} (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.</li>
   * <li>{@code filesystem_layout} (String): File system layout</li>
   * <li>{@code ftp_permission} (Boolean): Can the user access with FTP/FTPS?</li>
   * <li>{@code header_text} (String): Text to display to the user in the header of the UI</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned directly to this user, if any.</li>
   * <li>{@code language} (String): Preferred language</li>
   * <li>{@code notification_daily_send_time} (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23</li>
   * <li>{@code name} (String): User's full name</li>
   * <li>{@code company} (String): User's company</li>
   * <li>{@code notes} (String): Any internal notes on the user</li>
   * <li>{@code office_integration_enabled} (Boolean): Enable integration with Office for the web?</li>
   * <li>{@code partner_admin} (Boolean): Is this user a Partner administrator?</li>
   * <li>{@code partner_id} (Long): Partner ID if this user belongs to a Partner</li>
   * <li>{@code password_validity_days} (Long): Number of days to allow user to use the same password</li>
   * <li>{@code primary_group_id} (Long): Primary group ID for Group Admin scoping</li>
   * <li>{@code readonly_site_admin} (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?</li>
   * <li>{@code receive_admin_alerts} (Boolean): Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.</li>
   * <li>{@code notify_on_all_site_warnings} (Boolean): Should the user receive site warnings via email?</li>
   * <li>{@code notify_on_all_sso_failures} (Boolean): Should the user receive sso/scim/ldap configuration/sync failures via email?</li>
   * <li>{@code notify_on_all_user_security_events} (Boolean): Should the user receive user security events via email?</li>
   * <li>{@code notify_on_all_pending_work_failures} (Boolean): Should the user receive pending work failures via email?</li>
   * <li>{@code notify_on_all_siem_http_destination_failures} (Boolean): Should the user receive siem failures via email?</li>
   * <li>{@code notify_on_all_sync_failures} (Boolean): Should the user receive sync failures via email?</li>
   * <li>{@code notify_on_all_automation_failures} (Boolean): Should the user receive automation failures via email?</li>
   * <li>{@code notify_on_all_expectation_failures} (Boolean): Should the user receive expectation failures and misses via email?</li>
   * <li>{@code require_login_by} (String): Require user to login by specified date otherwise it will be disabled.</li>
   * <li>{@code require_password_change} (Boolean): Is a password change required upon next user login?</li>
   * <li>{@code responsible_group_id} (Long): ID of the internal Group responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code responsible_user_id} (Long): ID of the internal User responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code restapi_permission} (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): Can the user access the S3-compatible endpoint? Defaults to true.</li>
   * <li>{@code self_managed} (Boolean): Does this user manage it's own credentials or is it a shared/bot user?</li>
   * <li>{@code sftp_permission} (Boolean): Can the user access with SFTP?</li>
   * <li>{@code site_admin} (Boolean): Is the user an administrator for this site?</li>
   * <li>{@code skip_welcome_screen} (Boolean): Skip Welcome page in the UI?</li>
   * <li>{@code ssl_required} (String): SSL required setting</li>
   * <li>{@code sso_strategy_id} (Long): SSO (Single Sign On) strategy ID for the user, if applicable.</li>
   * <li>{@code subscribe_to_newsletter} (Boolean): Is the user subscribed to the newsletter?</li>
   * <li>{@code require_2fa} (String): 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.</li>
   * <li>{@code tags} (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code time_zone} (String): User time zone</li>
   * <li>{@code user_root} (String): If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.</li>
   * <li>{@code user_home} (String): Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.</li>
   * <li>{@code workspace_admin} (Boolean): Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.</li>
   * <li>{@code username} (String): Required. User's username</li>
   * <li>{@code workspace_id} (Long): ID of the Workspace the user belongs to. 0 is the Default Workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User}
   */
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
   *
   * @see #unlock(Long, HashMap, HashMap)
   */
  public void unlock() throws RuntimeException {
    unlock(null, null, null);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @see #unlock(Long, HashMap, HashMap)
   */
  public void unlock(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    unlock(id, parameters, null);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @see #unlock(Long, HashMap, HashMap)
   */
  public void unlock(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    unlock(null, parameters, options);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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
   *
   * @see #resendWelcomeEmail(Long, HashMap, HashMap)
   */
  public void resendWelcomeEmail() throws RuntimeException {
    resendWelcomeEmail(null, null, null);
  }

  /**
   * Resend user welcome email
   *
   * @see #resendWelcomeEmail(Long, HashMap, HashMap)
   */
  public void resendWelcomeEmail(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resendWelcomeEmail(id, parameters, null);
  }

  /**
   * Resend user welcome email
   *
   * @see #resendWelcomeEmail(Long, HashMap, HashMap)
   */
  public void resendWelcomeEmail(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resendWelcomeEmail(null, parameters, options);
  }

  /**
   * Resend user welcome email
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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
   *
   * @see #user2faReset(Long, HashMap, HashMap)
   */
  public void user2faReset() throws RuntimeException {
    user2faReset(null, null, null);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @see #user2faReset(Long, HashMap, HashMap)
   */
  public void user2faReset(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    user2faReset(id, parameters, null);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @see #user2faReset(Long, HashMap, HashMap)
   */
  public void user2faReset(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    user2faReset(null, parameters, options);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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
   * Update User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public User update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public User update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public User update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code avatar_file} (byte[] or Attachment): An image file for your user avatar.</li>
   * <li>{@code avatar_delete} (Boolean): If true, the avatar will be deleted.</li>
   * <li>{@code change_password} (String): Used for changing a password on an existing user.</li>
   * <li>{@code change_password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.</li>
   * <li>{@code email} (String): User's email.</li>
   * <li>{@code grant_permission} (String): Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.</li>
   * <li>{@code group_id} (Long): Group ID to associate this user with.</li>
   * <li>{@code group_ids} (String): A list of group ids to associate this user with.  Comma delimited.</li>
   * <li>{@code imported_password_hash} (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.</li>
   * <li>{@code password} (String): User password.</li>
   * <li>{@code password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.</li>
   * <li>{@code announcements_read} (Boolean): Signifies that the user has read all the announcements in the UI.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned directly to this user, if any.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code attachments_permission} (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.</li>
   * <li>{@code authenticate_until} (String): Scheduled Date/Time at which user will be deactivated</li>
   * <li>{@code authentication_method} (String): How is this user authenticated?</li>
   * <li>{@code billing_permission} (Boolean): Allow this user to perform operations on the account, payments, and invoices?</li>
   * <li>{@code bypass_user_lifecycle_rules} (Boolean): Exempt this user from user lifecycle rules?</li>
   * <li>{@code bypass_site_allowed_ips} (Boolean): Allow this user to skip site-wide IP blacklists?</li>
   * <li>{@code dav_permission} (Boolean): Can the user connect with WebDAV?</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned directly to this user, if any.</li>
   * <li>{@code default_workspace_id} (Long): Workspace ID the user should land in by default when more than one Workspace is available.</li>
   * <li>{@code disabled} (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.</li>
   * <li>{@code filesystem_layout} (String): File system layout</li>
   * <li>{@code ftp_permission} (Boolean): Can the user access with FTP/FTPS?</li>
   * <li>{@code header_text} (String): Text to display to the user in the header of the UI</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned directly to this user, if any.</li>
   * <li>{@code language} (String): Preferred language</li>
   * <li>{@code notification_daily_send_time} (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23</li>
   * <li>{@code name} (String): User's full name</li>
   * <li>{@code company} (String): User's company</li>
   * <li>{@code notes} (String): Any internal notes on the user</li>
   * <li>{@code office_integration_enabled} (Boolean): Enable integration with Office for the web?</li>
   * <li>{@code partner_admin} (Boolean): Is this user a Partner administrator?</li>
   * <li>{@code partner_id} (Long): Partner ID if this user belongs to a Partner</li>
   * <li>{@code password_validity_days} (Long): Number of days to allow user to use the same password</li>
   * <li>{@code primary_group_id} (Long): Primary group ID for Group Admin scoping</li>
   * <li>{@code readonly_site_admin} (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?</li>
   * <li>{@code receive_admin_alerts} (Boolean): Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.</li>
   * <li>{@code notify_on_all_site_warnings} (Boolean): Should the user receive site warnings via email?</li>
   * <li>{@code notify_on_all_sso_failures} (Boolean): Should the user receive sso/scim/ldap configuration/sync failures via email?</li>
   * <li>{@code notify_on_all_user_security_events} (Boolean): Should the user receive user security events via email?</li>
   * <li>{@code notify_on_all_pending_work_failures} (Boolean): Should the user receive pending work failures via email?</li>
   * <li>{@code notify_on_all_siem_http_destination_failures} (Boolean): Should the user receive siem failures via email?</li>
   * <li>{@code notify_on_all_sync_failures} (Boolean): Should the user receive sync failures via email?</li>
   * <li>{@code notify_on_all_automation_failures} (Boolean): Should the user receive automation failures via email?</li>
   * <li>{@code notify_on_all_expectation_failures} (Boolean): Should the user receive expectation failures and misses via email?</li>
   * <li>{@code require_login_by} (String): Require user to login by specified date otherwise it will be disabled.</li>
   * <li>{@code require_password_change} (Boolean): Is a password change required upon next user login?</li>
   * <li>{@code responsible_group_id} (Long): ID of the internal Group responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code responsible_user_id} (Long): ID of the internal User responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code restapi_permission} (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): Can the user access the S3-compatible endpoint? Defaults to true.</li>
   * <li>{@code self_managed} (Boolean): Does this user manage it's own credentials or is it a shared/bot user?</li>
   * <li>{@code sftp_permission} (Boolean): Can the user access with SFTP?</li>
   * <li>{@code site_admin} (Boolean): Is the user an administrator for this site?</li>
   * <li>{@code skip_welcome_screen} (Boolean): Skip Welcome page in the UI?</li>
   * <li>{@code ssl_required} (String): SSL required setting</li>
   * <li>{@code sso_strategy_id} (Long): SSO (Single Sign On) strategy ID for the user, if applicable.</li>
   * <li>{@code subscribe_to_newsletter} (Boolean): Is the user subscribed to the newsletter?</li>
   * <li>{@code require_2fa} (String): 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.</li>
   * <li>{@code tags} (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code time_zone} (String): User time zone</li>
   * <li>{@code user_root} (String): If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.</li>
   * <li>{@code user_home} (String): Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.</li>
   * <li>{@code workspace_admin} (Boolean): Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.</li>
   * <li>{@code username} (String): User's username</li>
   * <li>{@code workspace_id} (Long): Workspace ID. Only Site Administrators can change this field. Values supplied by Workspace Administrators, Group Administrators, or other non-Site Administrators using `/user` are ignored.</li>
   * <li>{@code clear_2fa} (Boolean): If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.</li>
   * <li>{@code convert_to_partner_user} (Boolean): Required when assigning a Partner to an existing non-Partner user. If true, convert the user by assigning the partner_id provided.</li>
   * </ul>
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User}
   */
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
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code new_owner_id} (Long): Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.</li>
   * </ul>
   *
   * @param id User ID.
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

    if (parameters.containsKey("new_owner_id") && !(parameters.get("new_owner_id") instanceof Long || parameters.get("new_owner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: new_owner_id must be of type Long or Integer parameters[\"new_owner_id\"]");
    }

    String apiPath = String.format("/users/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
