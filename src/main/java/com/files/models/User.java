package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public User() {
    this(null, null);
  }

  public User(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public User(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * User ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * User's username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  public String username;

  /**
  * List of group IDs of which this user is an administrator
  */
  @Getter
  @Setter
  @JsonProperty("admin_group_ids")
  public Object[] adminGroupIds;

  /**
  * A list of allowed IPs if applicable.  Newline delimited
  */
  @Getter
  @Setter
  @JsonProperty("allowed_ips")
  public String allowedIps;

  /**
  * DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
  */
  @Getter
  @Setter
  @JsonProperty("attachments_permission")
  public Boolean attachmentsPermission;

  /**
  * Number of api keys associated with this user
  */
  @Getter
  @Setter
  @JsonProperty("api_keys_count")
  public Long apiKeysCount;

  /**
  * Scheduled Date/Time at which user will be deactivated
  */
  @Getter
  @Setter
  @JsonProperty("authenticate_until")
  public Date authenticateUntil;

  /**
  * How is this user authenticated?
  */
  @Getter
  @Setter
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  /**
  * URL holding the user's avatar
  */
  @Getter
  @Setter
  @JsonProperty("avatar_url")
  public String avatarUrl;

  /**
  * Allow this user to perform operations on the account, payments, and invoices?
  */
  @Getter
  @Setter
  @JsonProperty("billing_permission")
  public Boolean billingPermission;

  /**
  * Allow this user to skip site-wide IP blacklists?
  */
  @Getter
  @Setter
  @JsonProperty("bypass_site_allowed_ips")
  public Boolean bypassSiteAllowedIps;

  /**
  * Exempt this user from being disabled based on inactivity?
  */
  @Getter
  @Setter
  @JsonProperty("bypass_inactive_disable")
  public Boolean bypassInactiveDisable;

  /**
  * When this user was created
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Can the user connect with WebDAV?
  */
  @Getter
  @Setter
  @JsonProperty("dav_permission")
  public Boolean davPermission;

  /**
  * Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
  */
  @Getter
  @Setter
  @JsonProperty("disabled")
  public Boolean disabled;

  /**
  * User email address
  */
  @Getter
  @Setter
  @JsonProperty("email")
  public String email;

  /**
  * User's first login time
  */
  @Getter
  @Setter
  @JsonProperty("first_login_at")
  public Date firstLoginAt;

  /**
  * Can the user access with FTP/FTPS?
  */
  @Getter
  @Setter
  @JsonProperty("ftp_permission")
  public Boolean ftpPermission;

  /**
  * Comma-separated list of group IDs of which this user is a member
  */
  @Getter
  @Setter
  @JsonProperty("group_ids")
  public String groupIds;

  /**
  * Text to display to the user in the header of the UI
  */
  @Getter
  @Setter
  @JsonProperty("header_text")
  public String headerText;

  /**
  * Preferred language
  */
  @Getter
  @Setter
  @JsonProperty("language")
  public String language;

  /**
  * User's most recent login time via any protocol
  */
  @Getter
  @Setter
  @JsonProperty("last_login_at")
  public Date lastLoginAt;

  /**
  * User's most recent login time via web
  */
  @Getter
  @Setter
  @JsonProperty("last_web_login_at")
  public Date lastWebLoginAt;

  /**
  * User's most recent login time via FTP
  */
  @Getter
  @Setter
  @JsonProperty("last_ftp_login_at")
  public Date lastFtpLoginAt;

  /**
  * User's most recent login time via SFTP
  */
  @Getter
  @Setter
  @JsonProperty("last_sftp_login_at")
  public Date lastSftpLoginAt;

  /**
  * User's most recent login time via WebDAV
  */
  @Getter
  @Setter
  @JsonProperty("last_dav_login_at")
  public Date lastDavLoginAt;

  /**
  * User's most recent login time via Desktop app
  */
  @Getter
  @Setter
  @JsonProperty("last_desktop_login_at")
  public Date lastDesktopLoginAt;

  /**
  * User's most recent login time via Rest API
  */
  @Getter
  @Setter
  @JsonProperty("last_restapi_login_at")
  public Date lastRestapiLoginAt;

  /**
  * User's most recent API use time
  */
  @Getter
  @Setter
  @JsonProperty("last_api_use_at")
  public Date lastApiUseAt;

  /**
  * User's most recent activity time, which is the latest of most recent login, most recent API use, enablement, or creation
  */
  @Getter
  @Setter
  @JsonProperty("last_active_at")
  public Date lastActiveAt;

  /**
  * The most recent protocol and cipher used
  */
  @Getter
  @Setter
  @JsonProperty("last_protocol_cipher")
  public String lastProtocolCipher;

  /**
  * Time in the future that the user will no longer be locked out if applicable
  */
  @Getter
  @Setter
  @JsonProperty("lockout_expires")
  public Date lockoutExpires;

  /**
  * User's full name
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * User's company
  */
  @Getter
  @Setter
  @JsonProperty("company")
  public String company;

  /**
  * Any internal notes on the user
  */
  @Getter
  @Setter
  @JsonProperty("notes")
  public String notes;

  /**
  * Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  */
  @Getter
  @Setter
  @JsonProperty("notification_daily_send_time")
  public Long notificationDailySendTime;

  /**
  * Enable integration with Office for the web?
  */
  @Getter
  @Setter
  @JsonProperty("office_integration_enabled")
  public Boolean officeIntegrationEnabled;

  /**
  * Last time the user's password was set
  */
  @Getter
  @Setter
  @JsonProperty("password_set_at")
  public Date passwordSetAt;

  /**
  * Number of days to allow user to use the same password
  */
  @Getter
  @Setter
  @JsonProperty("password_validity_days")
  public Long passwordValidityDays;

  /**
  * Number of public keys associated with this user
  */
  @Getter
  @Setter
  @JsonProperty("public_keys_count")
  public Long publicKeysCount;

  /**
  * Should the user receive admin alerts such a certificate expiration notifications and overages?
  */
  @Getter
  @Setter
  @JsonProperty("receive_admin_alerts")
  public Boolean receiveAdminAlerts;

  /**
  * 2FA required setting
  */
  @Getter
  @Setter
  @JsonProperty("require_2fa")
  public String require2fa;

  /**
  * Require user to login by specified date otherwise it will be disabled.
  */
  @Getter
  @Setter
  @JsonProperty("require_login_by")
  public Date requireLoginBy;

  /**
  * Is 2fa active for the user?
  */
  @Getter
  @Setter
  @JsonProperty("active_2fa")
  public Boolean active2fa;

  /**
  * Is a password change required upon next user login?
  */
  @Getter
  @Setter
  @JsonProperty("require_password_change")
  public Boolean requirePasswordChange;

  /**
  * Is user's password expired?
  */
  @Getter
  @Setter
  @JsonProperty("password_expired")
  public Boolean passwordExpired;

  /**
  * Can this user access the REST API?
  */
  @Getter
  @Setter
  @JsonProperty("restapi_permission")
  public Boolean restapiPermission;

  /**
  * Does this user manage it's own credentials or is it a shared/bot user?
  */
  @Getter
  @Setter
  @JsonProperty("self_managed")
  public Boolean selfManaged;

  /**
  * Can the user access with SFTP?
  */
  @Getter
  @Setter
  @JsonProperty("sftp_permission")
  public Boolean sftpPermission;

  /**
  * Is the user an administrator for this site?
  */
  @Getter
  @Setter
  @JsonProperty("site_admin")
  public Boolean siteAdmin;

  /**
  * Skip Welcome page in the UI?
  */
  @Getter
  @Setter
  @JsonProperty("skip_welcome_screen")
  public Boolean skipWelcomeScreen;

  /**
  * SSL required setting
  */
  @Getter
  @Setter
  @JsonProperty("ssl_required")
  public String sslRequired;

  /**
  * SSO (Single Sign On) strategy ID for the user, if applicable.
  */
  @Getter
  @Setter
  @JsonProperty("sso_strategy_id")
  public Long ssoStrategyId;

  /**
  * Is the user subscribed to the newsletter?
  */
  @Getter
  @Setter
  @JsonProperty("subscribe_to_newsletter")
  public Boolean subscribeToNewsletter;

  /**
  * Is this user managed by a SsoStrategy?
  */
  @Getter
  @Setter
  @JsonProperty("externally_managed")
  public Boolean externallyManaged;

  /**
  * User time zone
  */
  @Getter
  @Setter
  @JsonProperty("time_zone")
  public String timeZone;

  /**
  * Type(s) of 2FA methods in use.  Will be either `sms`, `totp`, `u2f`, `yubi`, or multiple values sorted alphabetically and joined by an underscore.
  */
  @Getter
  @Setter
  @JsonProperty("type_of_2fa")
  public String typeOf2fa;

  /**
  * Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
  */
  @Getter
  @Setter
  @JsonProperty("user_root")
  public String userRoot;

  /**
  * Number of days remaining until password expires
  */
  @Getter
  @Setter
  @JsonProperty("days_remaining_until_password_expire")
  public Long daysRemainingUntilPasswordExpire;

  /**
  * Password expiration datetime
  */
  @Getter
  @Setter
  @JsonProperty("password_expire_at")
  public Date passwordExpireAt;

  /**
  * An image file for your user avatar.
  */
  @Getter
  @Setter
  @JsonProperty("avatar_file")
  public byte[] avatarFile;

  /**
  * If true, the avatar will be deleted.
  */
  @Getter
  @Setter
  @JsonProperty("avatar_delete")
  public Boolean avatarDelete;

  /**
  * Used for changing a password on an existing user.
  */
  @Getter
  @Setter
  @JsonProperty("change_password")
  public String changePassword;

  /**
  * Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  */
  @Getter
  @Setter
  @JsonProperty("change_password_confirmation")
  public String changePasswordConfirmation;

  /**
  * Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  */
  @Getter
  @Setter
  @JsonProperty("grant_permission")
  public String grantPermission;

  /**
  * Group ID to associate this user with.
  */
  @Getter
  @Setter
  @JsonProperty("group_id")
  public Long groupId;

  /**
  * Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash menthods are MD5, SHA1, and SHA256.
  */
  @Getter
  @Setter
  @JsonProperty("imported_password_hash")
  public String importedPasswordHash;

  /**
  * User password.
  */
  @Getter
  @Setter
  @JsonProperty("password")
  public String password;

  /**
  * Optional, but if provided, we will ensure that it matches the value sent in `password`.
  */
  @Getter
  @Setter
  @JsonProperty("password_confirmation")
  public String passwordConfirmation;

  /**
  * Signifies that the user has read all the announcements in the UI.
  */
  @Getter
  @Setter
  @JsonProperty("announcements_read")
  public Boolean announcementsRead;

  /**
  * Unlock user who has been locked out due to failed logins
  */
  public User unlock(HashMap<String, Object> parameters) {
    return unlock(parameters);
  }

  /**
  * Resend user welcome email
  */
  public User resendWelcomeEmail(HashMap<String, Object> parameters) {
    return resendWelcomeEmail(parameters);
  }

  /**
  * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
  */
  public User user2faReset(HashMap<String, Object> parameters) {
    return user2faReset(parameters);
  }

  /**
  * Parameters:
  *   avatar_file - file - An image file for your user avatar.
  *   avatar_delete - boolean - If true, the avatar will be deleted.
  *   change_password - string - Used for changing a password on an existing user.
  *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  *   email - string - User's email.
  *   grant_permission - string - Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  *   group_id - int64 - Group ID to associate this user with.
  *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
  *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash menthods are MD5, SHA1, and SHA256.
  *   password - string - User password.
  *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
  *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   attachments_permission - boolean - DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
  *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
  *   authentication_method - string - How is this user authenticated?
  *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
  *   bypass_inactive_disable - boolean - Exempt this user from being disabled based on inactivity?
  *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
  *   dav_permission - boolean - Can the user connect with WebDAV?
  *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
  *   ftp_permission - boolean - Can the user access with FTP/FTPS?
  *   header_text - string - Text to display to the user in the header of the UI
  *   language - string - Preferred language
  *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  *   name - string - User's full name
  *   company - string - User's company
  *   notes - string - Any internal notes on the user
  *   office_integration_enabled - boolean - Enable integration with Office for the web?
  *   password_validity_days - int64 - Number of days to allow user to use the same password
  *   receive_admin_alerts - boolean - Should the user receive admin alerts such a certificate expiration notifications and overages?
  *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
  *   require_password_change - boolean - Is a password change required upon next user login?
  *   restapi_permission - boolean - Can this user access the REST API?
  *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
  *   sftp_permission - boolean - Can the user access with SFTP?
  *   site_admin - boolean - Is the user an administrator for this site?
  *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
  *   ssl_required - string - SSL required setting
  *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
  *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
  *   require_2fa - string - 2FA required setting
  *   time_zone - string - User time zone
  *   user_root - string - Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
  *   username - string - User's username
  */
  public User update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }
  

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      User newObject = User.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[authenticate_until]=desc`). Valid fields are `authenticate_until`, `email`, `last_desktop_login_at`, `last_login_at`, `username`, `company`, `name`, `site_admin`, `receive_admin_alerts`, `password_validity_days`, `ssl_required` or `not_site_admin`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`. Valid field combinations are `[ not_site_admin, username ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `username`, `email` or `company`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   ids - string - comma-separated list of User IDs
  *   q[username] - string - List users matching username.
  *   q[email] - string - List users matching email.
  *   q[notes] - string - List users matching notes field.
  *   q[admin] - string - If `true`, list only admin users.
  *   q[allowed_ips] - string - If set, list only users with overridden allowed IP setting.
  *   q[password_validity_days] - string - If set, list only users with overridden password validity days setting.
  *   q[ssl_required] - string - If set, list only users with overridden SSL required setting.
  *   search - string - Searches for partial matches of name, username, or email.
  */
  public static ListIterator<User> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<User> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<User> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }
    if (parameters.containsKey("ids") && !(parameters.get("ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ids must be of type String parameters[\"ids\"]");
    }
    if (parameters.containsKey("search") && !(parameters.get("search") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: search must be of type String parameters[\"search\"]");
    }



    String url = String.format("%s%s/users", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<User> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<User> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - User ID.
  */
  public static ListIterator<User> find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ListIterator<User> find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ListIterator<User> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ListIterator<User> find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/users/%s", urlParts);

    TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<User> get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ListIterator<User> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   avatar_file - file - An image file for your user avatar.
  *   avatar_delete - boolean - If true, the avatar will be deleted.
  *   change_password - string - Used for changing a password on an existing user.
  *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  *   email - string - User's email.
  *   grant_permission - string - Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  *   group_id - int64 - Group ID to associate this user with.
  *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
  *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash menthods are MD5, SHA1, and SHA256.
  *   password - string - User password.
  *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
  *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   attachments_permission - boolean - DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
  *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
  *   authentication_method - string - How is this user authenticated?
  *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
  *   bypass_inactive_disable - boolean - Exempt this user from being disabled based on inactivity?
  *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
  *   dav_permission - boolean - Can the user connect with WebDAV?
  *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
  *   ftp_permission - boolean - Can the user access with FTP/FTPS?
  *   header_text - string - Text to display to the user in the header of the UI
  *   language - string - Preferred language
  *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  *   name - string - User's full name
  *   company - string - User's company
  *   notes - string - Any internal notes on the user
  *   office_integration_enabled - boolean - Enable integration with Office for the web?
  *   password_validity_days - int64 - Number of days to allow user to use the same password
  *   receive_admin_alerts - boolean - Should the user receive admin alerts such a certificate expiration notifications and overages?
  *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
  *   require_password_change - boolean - Is a password change required upon next user login?
  *   restapi_permission - boolean - Can this user access the REST API?
  *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
  *   sftp_permission - boolean - Can the user access with SFTP?
  *   site_admin - boolean - Is the user an administrator for this site?
  *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
  *   ssl_required - string - SSL required setting
  *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
  *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
  *   require_2fa - string - 2FA required setting
  *   time_zone - string - User time zone
  *   user_root - string - Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
  *   username - string - User's username
  */
  public static User create() throws RuntimeException {
    return create(null, null);
  }

  public static User create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static User create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("avatar_file") && !(parameters.get("avatar_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: avatar_file must be of type byte[] parameters[\"avatar_file\"]");
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
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
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
    if (parameters.containsKey("bypass_inactive_disable") && !(parameters.get("bypass_inactive_disable") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_inactive_disable must be of type Boolean parameters[\"bypass_inactive_disable\"]");
    }
    if (parameters.containsKey("bypass_site_allowed_ips") && !(parameters.get("bypass_site_allowed_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_site_allowed_ips must be of type Boolean parameters[\"bypass_site_allowed_ips\"]");
    }
    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }
    if (parameters.containsKey("header_text") && !(parameters.get("header_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: header_text must be of type String parameters[\"header_text\"]");
    }
    if (parameters.containsKey("language") && !(parameters.get("language") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: language must be of type String parameters[\"language\"]");
    }
    if (parameters.containsKey("notification_daily_send_time") && !(parameters.get("notification_daily_send_time") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: notification_daily_send_time must be of type Long parameters[\"notification_daily_send_time\"]");
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
    if (parameters.containsKey("password_validity_days") && !(parameters.get("password_validity_days") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: password_validity_days must be of type Long parameters[\"password_validity_days\"]");
    }
    if (parameters.containsKey("receive_admin_alerts") && !(parameters.get("receive_admin_alerts") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: receive_admin_alerts must be of type Boolean parameters[\"receive_admin_alerts\"]");
    }
    if (parameters.containsKey("require_login_by") && !(parameters.get("require_login_by") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_login_by must be of type String parameters[\"require_login_by\"]");
    }
    if (parameters.containsKey("require_password_change") && !(parameters.get("require_password_change") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_password_change must be of type Boolean parameters[\"require_password_change\"]");
    }
    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
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
    if (parameters.containsKey("sso_strategy_id") && !(parameters.get("sso_strategy_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: sso_strategy_id must be of type Long parameters[\"sso_strategy_id\"]");
    }
    if (parameters.containsKey("subscribe_to_newsletter") && !(parameters.get("subscribe_to_newsletter") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: subscribe_to_newsletter must be of type Boolean parameters[\"subscribe_to_newsletter\"]");
    }
    if (parameters.containsKey("require_2fa") && !(parameters.get("require_2fa") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa must be of type String parameters[\"require_2fa\"]");
    }
    if (parameters.containsKey("time_zone") && !(parameters.get("time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: time_zone must be of type String parameters[\"time_zone\"]");
    }
    if (parameters.containsKey("user_root") && !(parameters.get("user_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_root must be of type String parameters[\"user_root\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }



    String url = String.format("%s%s/users", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Unlock user who has been locked out due to failed logins
  */
  public static User unlock() throws RuntimeException {
    return unlock(null, null, null);
  }

  public static User unlock(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return unlock(id, parameters, null);
  }

  public static User unlock(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return unlock(null, parameters, options);
  }

  public static User unlock(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/users/%s/unlock", urlParts);

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Resend user welcome email
  */
  public static User resendWelcomeEmail() throws RuntimeException {
    return resendWelcomeEmail(null, null, null);
  }

  public static User resendWelcomeEmail(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return resendWelcomeEmail(id, parameters, null);
  }

  public static User resendWelcomeEmail(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return resendWelcomeEmail(null, parameters, options);
  }

  public static User resendWelcomeEmail(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/users/%s/resend_welcome_email", urlParts);

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
  */
  public static User user2faReset() throws RuntimeException {
    return user2faReset(null, null, null);
  }

  public static User user2faReset(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return user2faReset(id, parameters, null);
  }

  public static User user2faReset(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return user2faReset(null, parameters, options);
  }

  public static User user2faReset(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/users/%s/2fa/reset", urlParts);

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   avatar_file - file - An image file for your user avatar.
  *   avatar_delete - boolean - If true, the avatar will be deleted.
  *   change_password - string - Used for changing a password on an existing user.
  *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  *   email - string - User's email.
  *   grant_permission - string - Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  *   group_id - int64 - Group ID to associate this user with.
  *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
  *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash menthods are MD5, SHA1, and SHA256.
  *   password - string - User password.
  *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
  *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   attachments_permission - boolean - DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
  *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
  *   authentication_method - string - How is this user authenticated?
  *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
  *   bypass_inactive_disable - boolean - Exempt this user from being disabled based on inactivity?
  *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
  *   dav_permission - boolean - Can the user connect with WebDAV?
  *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
  *   ftp_permission - boolean - Can the user access with FTP/FTPS?
  *   header_text - string - Text to display to the user in the header of the UI
  *   language - string - Preferred language
  *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  *   name - string - User's full name
  *   company - string - User's company
  *   notes - string - Any internal notes on the user
  *   office_integration_enabled - boolean - Enable integration with Office for the web?
  *   password_validity_days - int64 - Number of days to allow user to use the same password
  *   receive_admin_alerts - boolean - Should the user receive admin alerts such a certificate expiration notifications and overages?
  *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
  *   require_password_change - boolean - Is a password change required upon next user login?
  *   restapi_permission - boolean - Can this user access the REST API?
  *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
  *   sftp_permission - boolean - Can the user access with SFTP?
  *   site_admin - boolean - Is the user an administrator for this site?
  *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
  *   ssl_required - string - SSL required setting
  *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
  *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
  *   require_2fa - string - 2FA required setting
  *   time_zone - string - User time zone
  *   user_root - string - Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
  *   username - string - User's username
  */
  public static User update() throws RuntimeException {
    return update(null, null, null);
  }

  public static User update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static User update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static User update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("avatar_file") && !(parameters.get("avatar_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: avatar_file must be of type byte[] parameters[\"avatar_file\"]");
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
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
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
    if (parameters.containsKey("bypass_inactive_disable") && !(parameters.get("bypass_inactive_disable") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_inactive_disable must be of type Boolean parameters[\"bypass_inactive_disable\"]");
    }
    if (parameters.containsKey("bypass_site_allowed_ips") && !(parameters.get("bypass_site_allowed_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bypass_site_allowed_ips must be of type Boolean parameters[\"bypass_site_allowed_ips\"]");
    }
    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }
    if (parameters.containsKey("header_text") && !(parameters.get("header_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: header_text must be of type String parameters[\"header_text\"]");
    }
    if (parameters.containsKey("language") && !(parameters.get("language") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: language must be of type String parameters[\"language\"]");
    }
    if (parameters.containsKey("notification_daily_send_time") && !(parameters.get("notification_daily_send_time") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: notification_daily_send_time must be of type Long parameters[\"notification_daily_send_time\"]");
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
    if (parameters.containsKey("password_validity_days") && !(parameters.get("password_validity_days") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: password_validity_days must be of type Long parameters[\"password_validity_days\"]");
    }
    if (parameters.containsKey("receive_admin_alerts") && !(parameters.get("receive_admin_alerts") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: receive_admin_alerts must be of type Boolean parameters[\"receive_admin_alerts\"]");
    }
    if (parameters.containsKey("require_login_by") && !(parameters.get("require_login_by") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_login_by must be of type String parameters[\"require_login_by\"]");
    }
    if (parameters.containsKey("require_password_change") && !(parameters.get("require_password_change") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_password_change must be of type Boolean parameters[\"require_password_change\"]");
    }
    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
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
    if (parameters.containsKey("sso_strategy_id") && !(parameters.get("sso_strategy_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: sso_strategy_id must be of type Long parameters[\"sso_strategy_id\"]");
    }
    if (parameters.containsKey("subscribe_to_newsletter") && !(parameters.get("subscribe_to_newsletter") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: subscribe_to_newsletter must be of type Boolean parameters[\"subscribe_to_newsletter\"]");
    }
    if (parameters.containsKey("require_2fa") && !(parameters.get("require_2fa") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa must be of type String parameters[\"require_2fa\"]");
    }
    if (parameters.containsKey("time_zone") && !(parameters.get("time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: time_zone must be of type String parameters[\"time_zone\"]");
    }
    if (parameters.containsKey("user_root") && !(parameters.get("user_root") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_root must be of type String parameters[\"user_root\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/users/%s", urlParts);

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/users/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
