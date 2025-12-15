# Files.Models.User

## Example User Object

```
{
  "id": 1,
  "username": "user",
  "admin_group_ids": [
    1
  ],
  "allowed_ips": "10.0.0.0/8\n127.0.0.1",
  "attachments_permission": true,
  "api_keys_count": 1,
  "authenticate_until": "2000-01-01T01:00:00Z",
  "authentication_method": "password",
  "avatar_url": "example",
  "billable": true,
  "billing_permission": true,
  "bypass_site_allowed_ips": true,
  "bypass_user_lifecycle_rules": true,
  "created_at": "2000-01-01T01:00:00Z",
  "dav_permission": true,
  "disabled": true,
  "disabled_expired_or_inactive": true,
  "email": "john.doe@files.com",
  "filesystem_layout": "site_root",
  "first_login_at": "2000-01-01T01:00:00Z",
  "ftp_permission": true,
  "group_ids": "example",
  "header_text": "User-specific message.",
  "language": "en",
  "last_login_at": "2000-01-01T01:00:00Z",
  "last_web_login_at": "2000-01-01T01:00:00Z",
  "last_ftp_login_at": "2000-01-01T01:00:00Z",
  "last_sftp_login_at": "2000-01-01T01:00:00Z",
  "last_dav_login_at": "2000-01-01T01:00:00Z",
  "last_desktop_login_at": "2000-01-01T01:00:00Z",
  "last_restapi_login_at": "2000-01-01T01:00:00Z",
  "last_api_use_at": "2000-01-01T01:00:00Z",
  "last_active_at": "2000-01-01T01:00:00Z",
  "last_protocol_cipher": "example",
  "lockout_expires": "2000-01-01T01:00:00Z",
  "name": "John Doe",
  "company": "ACME Corp.",
  "notes": "Internal notes on this user.",
  "notification_daily_send_time": 18,
  "office_integration_enabled": true,
  "partner_admin": true,
  "partner_id": 1,
  "partner_name": "example",
  "password_set_at": "2000-01-01T01:00:00Z",
  "password_validity_days": 1,
  "public_keys_count": 1,
  "receive_admin_alerts": true,
  "require_2fa": "always_require",
  "require_login_by": "2000-01-01T01:00:00Z",
  "active_2fa": true,
  "require_password_change": true,
  "password_expired": true,
  "readonly_site_admin": true,
  "restapi_permission": true,
  "self_managed": true,
  "sftp_permission": true,
  "site_admin": true,
  "site_id": 1,
  "skip_welcome_screen": true,
  "ssl_required": "always_require",
  "sso_strategy_id": 1,
  "subscribe_to_newsletter": true,
  "externally_managed": true,
  "tags": "example",
  "time_zone": "Pacific Time (US & Canada)",
  "type_of_2fa": "yubi",
  "type_of_2fa_for_display": "yubi",
  "user_root": "example",
  "user_home": "example",
  "days_remaining_until_password_expire": 1,
  "password_expire_at": "2000-01-01T01:00:00Z"
}
```

* `id` / `id`  (int64): User ID
* `username` / `username`  (string): User's username
* `admin_group_ids` / `adminGroupIds`  (array(int64)): List of group IDs of which this user is an administrator
* `allowed_ips` / `allowedIps`  (string): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` / `attachmentsPermission`  (boolean): If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
* `api_keys_count` / `apiKeysCount`  (int64): Number of API keys associated with this user
* `authenticate_until` / `authenticateUntil`  (date-time): Scheduled Date/Time at which user will be deactivated
* `authentication_method` / `authenticationMethod`  (string): How is this user authenticated?
* `avatar_url` / `avatarUrl`  (string): URL holding the user's avatar
* `billable` / `billable`  (boolean): Is this a billable user record?
* `billing_permission` / `billingPermission`  (boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_site_allowed_ips` / `bypassSiteAllowedIps`  (boolean): Allow this user to skip site-wide IP blacklists?
* `bypass_user_lifecycle_rules` / `bypassUserLifecycleRules`  (boolean): Exempt this user from user lifecycle rules?
* `created_at` / `createdAt`  (date-time): When this user was created
* `dav_permission` / `davPermission`  (boolean): Can the user connect with WebDAV?
* `disabled` / `disabled`  (boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
* `disabled_expired_or_inactive` / `disabledExpiredOrInactive`  (boolean): Computed property that returns true if user disabled or expired or inactive.
* `email` / `email`  (email): User email address
* `filesystem_layout` / `filesystemLayout`  (string): File system layout
* `first_login_at` / `firstLoginAt`  (date-time): User's first login time
* `ftp_permission` / `ftpPermission`  (boolean): Can the user access with FTP/FTPS?
* `group_ids` / `groupIds`  (string): Comma-separated list of group IDs of which this user is a member
* `header_text` / `headerText`  (string): Text to display to the user in the header of the UI
* `language` / `language`  (string): Preferred language
* `last_login_at` / `lastLoginAt`  (date-time): User's most recent login time via any protocol
* `last_web_login_at` / `lastWebLoginAt`  (date-time): User's most recent login time via web
* `last_ftp_login_at` / `lastFtpLoginAt`  (date-time): User's most recent login time via FTP
* `last_sftp_login_at` / `lastSftpLoginAt`  (date-time): User's most recent login time via SFTP
* `last_dav_login_at` / `lastDavLoginAt`  (date-time): User's most recent login time via WebDAV
* `last_desktop_login_at` / `lastDesktopLoginAt`  (date-time): User's most recent login time via Desktop app
* `last_restapi_login_at` / `lastRestapiLoginAt`  (date-time): User's most recent login time via Rest API
* `last_api_use_at` / `lastApiUseAt`  (date-time): User's most recent API use time
* `last_active_at` / `lastActiveAt`  (date-time): User's most recent activity time, which is the latest of most recent login, most recent API use, enablement, or creation
* `last_protocol_cipher` / `lastProtocolCipher`  (string): The most recent protocol and cipher used
* `lockout_expires` / `lockoutExpires`  (date-time): Time in the future that the user will no longer be locked out if applicable
* `name` / `name`  (string): User's full name
* `company` / `company`  (string): User's company
* `notes` / `notes`  (string): Any internal notes on the user
* `notification_daily_send_time` / `notificationDailySendTime`  (int64): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `office_integration_enabled` / `officeIntegrationEnabled`  (boolean): Enable integration with Office for the web?
* `partner_admin` / `partnerAdmin`  (boolean): Is this user a Partner administrator?
* `partner_id` / `partnerId`  (int64): Partner ID if this user belongs to a Partner
* `partner_name` / `partnerName`  (string): Name of the Partner if this user belongs to a Partner
* `password_set_at` / `passwordSetAt`  (date-time): Last time the user's password was set
* `password_validity_days` / `passwordValidityDays`  (int64): Number of days to allow user to use the same password
* `public_keys_count` / `publicKeysCount`  (int64): Number of public keys associated with this user
* `receive_admin_alerts` / `receiveAdminAlerts`  (boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_2fa` / `require2fa`  (string): 2FA required setting
* `require_login_by` / `requireLoginBy`  (date-time): Require user to login by specified date otherwise it will be disabled.
* `active_2fa` / `active2fa`  (boolean): Is 2fa active for the user?
* `require_password_change` / `requirePasswordChange`  (boolean): Is a password change required upon next user login?
* `password_expired` / `passwordExpired`  (boolean): Is user's password expired?
* `readonly_site_admin` / `readonlySiteAdmin`  (boolean): Is the user an allowed to view all (non-billing) site configuration for this site?
* `restapi_permission` / `restapiPermission`  (boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
* `self_managed` / `selfManaged`  (boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` / `sftpPermission`  (boolean): Can the user access with SFTP?
* `site_admin` / `siteAdmin`  (boolean): Is the user an administrator for this site?
* `site_id` / `siteId`  (int64): Site ID
* `skip_welcome_screen` / `skipWelcomeScreen`  (boolean): Skip Welcome page in the UI?
* `ssl_required` / `sslRequired`  (string): SSL required setting
* `sso_strategy_id` / `ssoStrategyId`  (int64): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` / `subscribeToNewsletter`  (boolean): Is the user subscribed to the newsletter?
* `externally_managed` / `externallyManaged`  (boolean): Is this user managed by a SsoStrategy?
* `tags` / `tags`  (string): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `time_zone` / `timeZone`  (string): User time zone
* `type_of_2fa` / `typeOf2fa`  (string): Type(s) of 2FA methods in use, for programmatic use.  Will be either `sms`, `totp`, `webauthn`, `yubi`, `email`, or multiple values sorted alphabetically and joined by an underscore.  Does not specify whether user has more than one of a given method.
* `type_of_2fa_for_display` / `typeOf2faForDisplay`  (string): Type(s) of 2FA methods in use, formatted for displaying in the UI.  Unlike `type_of_2fa`, this value will make clear when a user has more than 1 of the same type of method.
* `user_root` / `userRoot`  (string): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
* `user_home` / `userHome`  (string): Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
* `days_remaining_until_password_expire` / `daysRemainingUntilPasswordExpire`  (int64): Number of days remaining until password expires
* `password_expire_at` / `passwordExpireAt`  (date-time): Password expiration datetime
* `avatar_file` / `avatarFile`  (file): An image file for your user avatar.
* `avatar_delete` / `avatarDelete`  (boolean): If true, the avatar will be deleted.
* `change_password` / `changePassword`  (string): Used for changing a password on an existing user.
* `change_password_confirmation` / `changePasswordConfirmation`  (string): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
* `grant_permission` / `grantPermission`  (string): Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
* `group_id` / `groupId`  (int64): Group ID to associate this user with.
* `imported_password_hash` / `importedPasswordHash`  (string): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
* `password` / `password`  (string): User password.
* `password_confirmation` / `passwordConfirmation`  (string): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` / `announcementsRead`  (boolean): Signifies that the user has read all the announcements in the UI.
* `clear_2fa` / `clear2fa`  (boolean): If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
* `convert_to_partner_user` / `convertToPartnerUser`  (boolean): If true, convert this user to a partner user by assigning the partner_id provided.


---

## List Users

```
ListIterator<User> user = User.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `authenticate_until`, `email`, `last_desktop_login_at`, `last_login_at`, `name`, `company`, `password_validity_days`, `ssl_required`, `username`, `site_admin` or `disabled`.
* `filter` (Map<String, String>): If set, return records where the specified field is equal to the supplied value. Valid fields are `username`, `name`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until`, `not_site_admin`, `disabled` or `partner_id`. Valid field combinations are `[ site_admin, username ]` and `[ not_site_admin, username ]`.
* `filter_gt` (Map<String, String>): If set, return records where the specified field is greater than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
* `filter_gteq` (Map<String, String>): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
* `filter_prefix` (Map<String, String>): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `username`, `name`, `email` or `company`.
* `filter_lt` (Map<String, String>): If set, return records where the specified field is less than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
* `filter_lteq` (Map<String, String>): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
* `ids` (String): comma-separated list of User IDs
* `include_parent_site_users` (Boolean): Include users from the parent site.
* `search` (String): Searches for partial matches of name, username, or email.


---

## Show User

```
User user = User.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.


---

## Create User

```
User user = User.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `avatar_file` (byte[]): An image file for your user avatar.
* `avatar_delete` (Boolean): If true, the avatar will be deleted.
* `change_password` (String): Used for changing a password on an existing user.
* `change_password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
* `email` (String): User's email.
* `grant_permission` (String): Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
* `group_id` (Long): Group ID to associate this user with.
* `group_ids` (String): A list of group ids to associate this user with.  Comma delimited.
* `imported_password_hash` (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
* `password` (String): User password.
* `password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` (Boolean): Signifies that the user has read all the announcements in the UI.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
* `authenticate_until` (String): Scheduled Date/Time at which user will be deactivated
* `authentication_method` (String): How is this user authenticated?
* `billing_permission` (Boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_user_lifecycle_rules` (Boolean): Exempt this user from user lifecycle rules?
* `bypass_site_allowed_ips` (Boolean): Allow this user to skip site-wide IP blacklists?
* `dav_permission` (Boolean): Can the user connect with WebDAV?
* `disabled` (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
* `filesystem_layout` (String): File system layout
* `ftp_permission` (Boolean): Can the user access with FTP/FTPS?
* `header_text` (String): Text to display to the user in the header of the UI
* `language` (String): Preferred language
* `notification_daily_send_time` (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `name` (String): User's full name
* `company` (String): User's company
* `notes` (String): Any internal notes on the user
* `office_integration_enabled` (Boolean): Enable integration with Office for the web?
* `partner_admin` (Boolean): Is this user a Partner administrator?
* `partner_id` (Long): Partner ID if this user belongs to a Partner
* `password_validity_days` (Long): Number of days to allow user to use the same password
* `readonly_site_admin` (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?
* `receive_admin_alerts` (Boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_login_by` (String): Require user to login by specified date otherwise it will be disabled.
* `require_password_change` (Boolean): Is a password change required upon next user login?
* `restapi_permission` (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
* `self_managed` (Boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` (Boolean): Can the user access with SFTP?
* `site_admin` (Boolean): Is the user an administrator for this site?
* `skip_welcome_screen` (Boolean): Skip Welcome page in the UI?
* `ssl_required` (String): SSL required setting
* `sso_strategy_id` (Long): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` (Boolean): Is the user subscribed to the newsletter?
* `require_2fa` (String): 2FA required setting
* `tags` (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `time_zone` (String): User time zone
* `user_root` (String): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
* `user_home` (String): Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
* `username` (String): Required - User's username


---

## Unlock user who has been locked out due to failed logins

```
void user = User.unlock(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.


---

## Resend user welcome email

```
void user = User.resendWelcomeEmail(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.


---

## Trigger 2FA Reset process for user who has lost access to their existing 2FA methods

```
void user = User.user2faReset(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.


---

## Update User

```
User user = User.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.
* `avatar_file` (byte[]): An image file for your user avatar.
* `avatar_delete` (Boolean): If true, the avatar will be deleted.
* `change_password` (String): Used for changing a password on an existing user.
* `change_password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
* `email` (String): User's email.
* `grant_permission` (String): Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
* `group_id` (Long): Group ID to associate this user with.
* `group_ids` (String): A list of group ids to associate this user with.  Comma delimited.
* `imported_password_hash` (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
* `password` (String): User password.
* `password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` (Boolean): Signifies that the user has read all the announcements in the UI.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
* `authenticate_until` (String): Scheduled Date/Time at which user will be deactivated
* `authentication_method` (String): How is this user authenticated?
* `billing_permission` (Boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_user_lifecycle_rules` (Boolean): Exempt this user from user lifecycle rules?
* `bypass_site_allowed_ips` (Boolean): Allow this user to skip site-wide IP blacklists?
* `dav_permission` (Boolean): Can the user connect with WebDAV?
* `disabled` (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
* `filesystem_layout` (String): File system layout
* `ftp_permission` (Boolean): Can the user access with FTP/FTPS?
* `header_text` (String): Text to display to the user in the header of the UI
* `language` (String): Preferred language
* `notification_daily_send_time` (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `name` (String): User's full name
* `company` (String): User's company
* `notes` (String): Any internal notes on the user
* `office_integration_enabled` (Boolean): Enable integration with Office for the web?
* `partner_admin` (Boolean): Is this user a Partner administrator?
* `partner_id` (Long): Partner ID if this user belongs to a Partner
* `password_validity_days` (Long): Number of days to allow user to use the same password
* `readonly_site_admin` (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?
* `receive_admin_alerts` (Boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_login_by` (String): Require user to login by specified date otherwise it will be disabled.
* `require_password_change` (Boolean): Is a password change required upon next user login?
* `restapi_permission` (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
* `self_managed` (Boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` (Boolean): Can the user access with SFTP?
* `site_admin` (Boolean): Is the user an administrator for this site?
* `skip_welcome_screen` (Boolean): Skip Welcome page in the UI?
* `ssl_required` (String): SSL required setting
* `sso_strategy_id` (Long): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` (Boolean): Is the user subscribed to the newsletter?
* `require_2fa` (String): 2FA required setting
* `tags` (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `time_zone` (String): User time zone
* `user_root` (String): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
* `user_home` (String): Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
* `username` (String): User's username
* `clear_2fa` (Boolean): If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
* `convert_to_partner_user` (Boolean): If true, convert this user to a partner user by assigning the partner_id provided.


---

## Delete User

```
void user = User.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.
* `new_owner_id` (Long): Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.


---

## Unlock user who has been locked out due to failed logins

```
User user = User.find(id);

HashMap<String, Object> parameters = new HashMap<>();

user.unlock(parameters);
```

### Parameters

* `id` (Long): Required - User ID.


---

## Resend user welcome email

```
User user = User.find(id);

HashMap<String, Object> parameters = new HashMap<>();

user.resendWelcomeEmail(parameters);
```

### Parameters

* `id` (Long): Required - User ID.


---

## Trigger 2FA Reset process for user who has lost access to their existing 2FA methods

```
User user = User.find(id);

HashMap<String, Object> parameters = new HashMap<>();

user.user2faReset(parameters);
```

### Parameters

* `id` (Long): Required - User ID.


---

## Update User

```
User user = User.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("avatar_delete", false);
parameters.put("email", "john.doe@files.com");
parameters.put("group_id", 1);
parameters.put("group_ids", "example");
parameters.put("announcements_read", false);
parameters.put("allowed_ips", "10.0.0.0/8\n127.0.0.1");
parameters.put("attachments_permission", true);
parameters.put("authenticate_until", "2000-01-01T01:00:00Z");
parameters.put("authentication_method", "password");
parameters.put("billing_permission", false);
parameters.put("bypass_user_lifecycle_rules", false);
parameters.put("bypass_site_allowed_ips", false);
parameters.put("dav_permission", true);
parameters.put("disabled", true);
parameters.put("filesystem_layout", "site_root");
parameters.put("ftp_permission", true);
parameters.put("header_text", "User-specific message.");
parameters.put("language", "en");
parameters.put("notification_daily_send_time", 18);
parameters.put("name", "John Doe");
parameters.put("company", "ACME Corp.");
parameters.put("notes", "Internal notes on this user.");
parameters.put("office_integration_enabled", true);
parameters.put("partner_admin", true);
parameters.put("partner_id", 1);
parameters.put("password_validity_days", 1);
parameters.put("readonly_site_admin", true);
parameters.put("receive_admin_alerts", true);
parameters.put("require_login_by", "2000-01-01T01:00:00Z");
parameters.put("require_password_change", true);
parameters.put("restapi_permission", true);
parameters.put("self_managed", true);
parameters.put("sftp_permission", true);
parameters.put("site_admin", true);
parameters.put("skip_welcome_screen", true);
parameters.put("ssl_required", "always_require");
parameters.put("sso_strategy_id", 1);
parameters.put("subscribe_to_newsletter", true);
parameters.put("require_2fa", "always_require");
parameters.put("tags", "example");
parameters.put("time_zone", "Pacific Time (US & Canada)");
parameters.put("user_root", "example");
parameters.put("user_home", "example");
parameters.put("username", "user");
parameters.put("clear_2fa", false);
parameters.put("convert_to_partner_user", false);

user.update(parameters);
```

### Parameters

* `id` (Long): Required - User ID.
* `avatar_file` (byte[]): An image file for your user avatar.
* `avatar_delete` (Boolean): If true, the avatar will be deleted.
* `change_password` (String): Used for changing a password on an existing user.
* `change_password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
* `email` (String): User's email.
* `grant_permission` (String): Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
* `group_id` (Long): Group ID to associate this user with.
* `group_ids` (String): A list of group ids to associate this user with.  Comma delimited.
* `imported_password_hash` (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
* `password` (String): User password.
* `password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` (Boolean): Signifies that the user has read all the announcements in the UI.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
* `authenticate_until` (String): Scheduled Date/Time at which user will be deactivated
* `authentication_method` (String): How is this user authenticated?
* `billing_permission` (Boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_user_lifecycle_rules` (Boolean): Exempt this user from user lifecycle rules?
* `bypass_site_allowed_ips` (Boolean): Allow this user to skip site-wide IP blacklists?
* `dav_permission` (Boolean): Can the user connect with WebDAV?
* `disabled` (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
* `filesystem_layout` (String): File system layout
* `ftp_permission` (Boolean): Can the user access with FTP/FTPS?
* `header_text` (String): Text to display to the user in the header of the UI
* `language` (String): Preferred language
* `notification_daily_send_time` (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `name` (String): User's full name
* `company` (String): User's company
* `notes` (String): Any internal notes on the user
* `office_integration_enabled` (Boolean): Enable integration with Office for the web?
* `partner_admin` (Boolean): Is this user a Partner administrator?
* `partner_id` (Long): Partner ID if this user belongs to a Partner
* `password_validity_days` (Long): Number of days to allow user to use the same password
* `readonly_site_admin` (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?
* `receive_admin_alerts` (Boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_login_by` (String): Require user to login by specified date otherwise it will be disabled.
* `require_password_change` (Boolean): Is a password change required upon next user login?
* `restapi_permission` (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
* `self_managed` (Boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` (Boolean): Can the user access with SFTP?
* `site_admin` (Boolean): Is the user an administrator for this site?
* `skip_welcome_screen` (Boolean): Skip Welcome page in the UI?
* `ssl_required` (String): SSL required setting
* `sso_strategy_id` (Long): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` (Boolean): Is the user subscribed to the newsletter?
* `require_2fa` (String): 2FA required setting
* `tags` (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
* `time_zone` (String): User time zone
* `user_root` (String): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
* `user_home` (String): Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
* `username` (String): User's username
* `clear_2fa` (Boolean): If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
* `convert_to_partner_user` (Boolean): If true, convert this user to a partner user by assigning the partner_id provided.


---

## Delete User

```
User user = User.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("new_owner_id", 1);

user.delete(parameters);
```

### Parameters

* `id` (Long): Required - User ID.
* `new_owner_id` (Long): Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.
