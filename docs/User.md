# Files.Models.User

## Example User Object

```
{
  "id": 1,
  "username": "user",
  "admin_group_ids": [
    1
  ],
  "allowed_ips": "127.0.0.1",
  "attachments_permission": true,
  "api_keys_count": 1,
  "authenticate_until": "2000-01-01T01:00:00Z",
  "authentication_method": "password",
  "avatar_url": "",
  "billing_permission": true,
  "bypass_site_allowed_ips": true,
  "bypass_inactive_disable": true,
  "created_at": "2000-01-01T01:00:00Z",
  "dav_permission": true,
  "disabled": true,
  "email": "john.doe@files.com",
  "ftp_permission": true,
  "group_ids": "",
  "header_text": "User-specific message.",
  "language": "en",
  "last_login_at": "2000-01-01T01:00:00Z",
  "last_protocol_cipher": "",
  "lockout_expires": "2000-01-01T01:00:00Z",
  "name": "John Doe",
  "company": "ACME Corp.",
  "notes": "Internal notes on this user.",
  "notification_daily_send_time": 18,
  "office_integration_enabled": true,
  "password_set_at": "2000-01-01T01:00:00Z",
  "password_validity_days": 1,
  "public_keys_count": 1,
  "receive_admin_alerts": true,
  "require_2fa": "always_require",
  "active_2fa": true,
  "require_password_change": true,
  "restapi_permission": true,
  "self_managed": true,
  "sftp_permission": true,
  "site_admin": true,
  "skip_welcome_screen": true,
  "ssl_required": "always_require",
  "sso_strategy_id": 1,
  "subscribe_to_newsletter": true,
  "externally_managed": true,
  "time_zone": "Pacific Time (US & Canada)",
  "type_of_2fa": "",
  "user_root": ""
}
```

* `id` / `id`  (int64): User ID
* `username` / `username`  (string): User's username
* `admin_group_ids` / `adminGroupIds`  (array): List of group IDs of which this user is an administrator
* `allowed_ips` / `allowedIps`  (string): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` / `attachmentsPermission`  (boolean): DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
* `api_keys_count` / `apiKeysCount`  (int64): Number of api keys associated with this user
* `authenticate_until` / `authenticateUntil`  (date-time): Scheduled Date/Time at which user will be deactivated
* `authentication_method` / `authenticationMethod`  (string): How is this user authenticated?
* `avatar_url` / `avatarUrl`  (string): URL holding the user's avatar
* `billing_permission` / `billingPermission`  (boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_site_allowed_ips` / `bypassSiteAllowedIps`  (boolean): Allow this user to skip site-wide IP blacklists?
* `bypass_inactive_disable` / `bypassInactiveDisable`  (boolean): Exempt this user from being disabled based on inactivity?
* `created_at` / `createdAt`  (date-time): When this user was created
* `dav_permission` / `davPermission`  (boolean): Can the user connect with WebDAV?
* `disabled` / `disabled`  (boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
* `email` / `email`  (email): User email address
* `ftp_permission` / `ftpPermission`  (boolean): Can the user access with FTP/FTPS?
* `group_ids` / `groupIds`  (string): Comma-separated list of group IDs of which this user is a member
* `header_text` / `headerText`  (string): Text to display to the user in the header of the UI
* `language` / `language`  (string): Preferred language
* `last_login_at` / `lastLoginAt`  (date-time): User's last login time
* `last_protocol_cipher` / `lastProtocolCipher`  (string): The last protocol and cipher used
* `lockout_expires` / `lockoutExpires`  (date-time): Time in the future that the user will no longer be locked out if applicable
* `name` / `name`  (string): User's full name
* `company` / `company`  (string): User's company
* `notes` / `notes`  (string): Any internal notes on the user
* `notification_daily_send_time` / `notificationDailySendTime`  (int64): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `office_integration_enabled` / `officeIntegrationEnabled`  (boolean): Enable integration with Office for the web?
* `password_set_at` / `passwordSetAt`  (date-time): Last time the user's password was set
* `password_validity_days` / `passwordValidityDays`  (int64): Number of days to allow user to use the same password
* `public_keys_count` / `publicKeysCount`  (int64): Number of public keys associated with this user
* `receive_admin_alerts` / `receiveAdminAlerts`  (boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_2fa` / `require2fa`  (string): 2FA required setting
* `active_2fa` / `active2fa`  (boolean): Is 2fa active for the user?
* `require_password_change` / `requirePasswordChange`  (boolean): Is a password change required upon next user login?
* `restapi_permission` / `restapiPermission`  (boolean): Can this user access the REST API?
* `self_managed` / `selfManaged`  (boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` / `sftpPermission`  (boolean): Can the user access with SFTP?
* `site_admin` / `siteAdmin`  (boolean): Is the user an administrator for this site?
* `skip_welcome_screen` / `skipWelcomeScreen`  (boolean): Skip Welcome page in the UI?
* `ssl_required` / `sslRequired`  (string): SSL required setting
* `sso_strategy_id` / `ssoStrategyId`  (int64): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` / `subscribeToNewsletter`  (boolean): Is the user subscribed to the newsletter?
* `externally_managed` / `externallyManaged`  (boolean): Is this user managed by a SsoStrategy?
* `time_zone` / `timeZone`  (string): User time zone
* `type_of_2fa` / `typeOf2fa`  (string): Type(s) of 2FA methods in use.  Will be either `sms`, `totp`, `u2f`, `yubi`, or multiple values sorted alphabetically and joined by an underscore.
* `user_root` / `userRoot`  (string): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
* `avatar_file` / `avatarFile`  (file): An image file for your user avatar.
* `avatar_delete` / `avatarDelete`  (boolean): If true, the avatar will be deleted.
* `change_password` / `changePassword`  (string): Used for changing a password on an existing user.
* `change_password_confirmation` / `changePasswordConfirmation`  (string): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
* `grant_permission` / `grantPermission`  (string): Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, or `history`.
* `group_id` / `groupId`  (int64): Group ID to associate this user with.
* `password` / `password`  (string): User password.
* `password_confirmation` / `passwordConfirmation`  (string): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` / `announcementsRead`  (boolean): Signifies that the user has read all the announcements in the UI.


---

## List Users

```
List<User> user = User.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `sort_by` (Map<String, String>): If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `authenticate_until`, `email`, `last_desktop_login_at`, `last_login_at`, `username`, `company`, `name`, `site_admin`, `receive_admin_alerts`, `password_validity_days`, `ssl_required` or `not_site_admin`.
* `filter` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`.
* `filter_gt` (Map<String, String>): If set, return records where the specifiied field is greater than the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`.
* `filter_gteq` (Map<String, String>): If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`.
* `filter_like` (Map<String, String>): If set, return records where the specifiied field is equal to the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`.
* `filter_lt` (Map<String, String>): If set, return records where the specifiied field is less than the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`.
* `filter_lteq` (Map<String, String>): If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `username`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until` or `not_site_admin`.
* `ids` (String): comma-separated list of User IDs
* `q[username]` (String): List users matching username.
* `q[email]` (String): List users matching email.
* `q[notes]` (String): List users matching notes field.
* `q[admin]` (String): If `true`, list only admin users.
* `q[allowed_ips]` (String): If set, list only users with overridden allowed IP setting.
* `q[password_validity_days]` (String): If set, list only users with overridden password validity days setting.
* `q[ssl_required]` (String): If set, list only users with overridden SSL required setting.
* `search` (String): Searches for partial matches of name, username, or email.


---

## Show User

```
List<User> user = User.find(
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
* `grant_permission` (String): Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, or `history`.
* `group_id` (Long): Group ID to associate this user with.
* `group_ids` (String): A list of group ids to associate this user with.  Comma delimited.
* `password` (String): User password.
* `password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` (Boolean): Signifies that the user has read all the announcements in the UI.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` (Boolean): DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
* `authenticate_until` (String): Scheduled Date/Time at which user will be deactivated
* `authentication_method` (String): How is this user authenticated?
* `billing_permission` (Boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_inactive_disable` (Boolean): Exempt this user from being disabled based on inactivity?
* `bypass_site_allowed_ips` (Boolean): Allow this user to skip site-wide IP blacklists?
* `dav_permission` (Boolean): Can the user connect with WebDAV?
* `disabled` (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
* `ftp_permission` (Boolean): Can the user access with FTP/FTPS?
* `header_text` (String): Text to display to the user in the header of the UI
* `language` (String): Preferred language
* `notification_daily_send_time` (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `name` (String): User's full name
* `company` (String): User's company
* `notes` (String): Any internal notes on the user
* `office_integration_enabled` (Boolean): Enable integration with Office for the web?
* `password_validity_days` (Long): Number of days to allow user to use the same password
* `receive_admin_alerts` (Boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_password_change` (Boolean): Is a password change required upon next user login?
* `restapi_permission` (Boolean): Can this user access the REST API?
* `self_managed` (Boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` (Boolean): Can the user access with SFTP?
* `site_admin` (Boolean): Is the user an administrator for this site?
* `skip_welcome_screen` (Boolean): Skip Welcome page in the UI?
* `ssl_required` (String): SSL required setting
* `sso_strategy_id` (Long): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` (Boolean): Is the user subscribed to the newsletter?
* `require_2fa` (String): 2FA required setting
* `time_zone` (String): User time zone
* `user_root` (String): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
* `username` (String): User's username


---

## Unlock user who has been locked out due to failed logins

```
User user = User.unlock(
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
User user = User.resendWelcomeEmail(
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
User user = User.user2faReset(
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
* `grant_permission` (String): Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, or `history`.
* `group_id` (Long): Group ID to associate this user with.
* `group_ids` (String): A list of group ids to associate this user with.  Comma delimited.
* `password` (String): User password.
* `password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` (Boolean): Signifies that the user has read all the announcements in the UI.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` (Boolean): DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
* `authenticate_until` (String): Scheduled Date/Time at which user will be deactivated
* `authentication_method` (String): How is this user authenticated?
* `billing_permission` (Boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_inactive_disable` (Boolean): Exempt this user from being disabled based on inactivity?
* `bypass_site_allowed_ips` (Boolean): Allow this user to skip site-wide IP blacklists?
* `dav_permission` (Boolean): Can the user connect with WebDAV?
* `disabled` (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
* `ftp_permission` (Boolean): Can the user access with FTP/FTPS?
* `header_text` (String): Text to display to the user in the header of the UI
* `language` (String): Preferred language
* `notification_daily_send_time` (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `name` (String): User's full name
* `company` (String): User's company
* `notes` (String): Any internal notes on the user
* `office_integration_enabled` (Boolean): Enable integration with Office for the web?
* `password_validity_days` (Long): Number of days to allow user to use the same password
* `receive_admin_alerts` (Boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_password_change` (Boolean): Is a password change required upon next user login?
* `restapi_permission` (Boolean): Can this user access the REST API?
* `self_managed` (Boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` (Boolean): Can the user access with SFTP?
* `site_admin` (Boolean): Is the user an administrator for this site?
* `skip_welcome_screen` (Boolean): Skip Welcome page in the UI?
* `ssl_required` (String): SSL required setting
* `sso_strategy_id` (Long): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` (Boolean): Is the user subscribed to the newsletter?
* `require_2fa` (String): 2FA required setting
* `time_zone` (String): User time zone
* `user_root` (String): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
* `username` (String): User's username


---

## Delete User

```
User user = User.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - User ID.


---

## Unlock user who has been locked out due to failed logins

```
User user = User.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


User.Unlock
```

### Parameters

* `id` (Long): Required - User ID.


---

## Resend user welcome email

```
User user = User.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


User.ResendWelcomeEmail
```

### Parameters

* `id` (Long): Required - User ID.


---

## Trigger 2FA Reset process for user who has lost access to their existing 2FA methods

```
User user = User.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


User.User2faReset
```

### Parameters

* `id` (Long): Required - User ID.


---

## Update User

```
User user = User.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();

parameters.put("avatar_delete", true);
parameters.put("email", "john.doe@files.com");
parameters.put("group_id", 1);
parameters.put("announcements_read", true);
parameters.put("allowed_ips", "127.0.0.1");
parameters.put("attachments_permission", true);
parameters.put("authenticate_until", "2000-01-01T01:00:00Z");
parameters.put("authentication_method", "password");
parameters.put("billing_permission", true);
parameters.put("bypass_inactive_disable", true);
parameters.put("bypass_site_allowed_ips", true);
parameters.put("dav_permission", true);
parameters.put("disabled", true);
parameters.put("ftp_permission", true);
parameters.put("header_text", "User-specific message.");
parameters.put("language", "en");
parameters.put("notification_daily_send_time", 18);
parameters.put("name", "John Doe");
parameters.put("company", "ACME Corp.");
parameters.put("notes", "Internal notes on this user.");
parameters.put("office_integration_enabled", true);
parameters.put("password_validity_days", 1);
parameters.put("receive_admin_alerts", true);
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
parameters.put("time_zone", "Pacific Time (US & Canada)");
parameters.put("username", "user");

User.Update(parameters);
```

### Parameters

* `id` (Long): Required - User ID.
* `avatar_file` (byte[]): An image file for your user avatar.
* `avatar_delete` (Boolean): If true, the avatar will be deleted.
* `change_password` (String): Used for changing a password on an existing user.
* `change_password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
* `email` (String): User's email.
* `grant_permission` (String): Permission to grant on the user root.  Can be blank or `full`, `read`, `write`, `list`, or `history`.
* `group_id` (Long): Group ID to associate this user with.
* `group_ids` (String): A list of group ids to associate this user with.  Comma delimited.
* `password` (String): User password.
* `password_confirmation` (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.
* `announcements_read` (Boolean): Signifies that the user has read all the announcements in the UI.
* `allowed_ips` (String): A list of allowed IPs if applicable.  Newline delimited
* `attachments_permission` (Boolean): DEPRECATED: Can the user create Bundles (aka Share Links)? Use the bundle permission instead.
* `authenticate_until` (String): Scheduled Date/Time at which user will be deactivated
* `authentication_method` (String): How is this user authenticated?
* `billing_permission` (Boolean): Allow this user to perform operations on the account, payments, and invoices?
* `bypass_inactive_disable` (Boolean): Exempt this user from being disabled based on inactivity?
* `bypass_site_allowed_ips` (Boolean): Allow this user to skip site-wide IP blacklists?
* `dav_permission` (Boolean): Can the user connect with WebDAV?
* `disabled` (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes.  Users can be automatically disabled after an inactivity period via a Site setting.
* `ftp_permission` (Boolean): Can the user access with FTP/FTPS?
* `header_text` (String): Text to display to the user in the header of the UI
* `language` (String): Preferred language
* `notification_daily_send_time` (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
* `name` (String): User's full name
* `company` (String): User's company
* `notes` (String): Any internal notes on the user
* `office_integration_enabled` (Boolean): Enable integration with Office for the web?
* `password_validity_days` (Long): Number of days to allow user to use the same password
* `receive_admin_alerts` (Boolean): Should the user receive admin alerts such a certificate expiration notifications and overages?
* `require_password_change` (Boolean): Is a password change required upon next user login?
* `restapi_permission` (Boolean): Can this user access the REST API?
* `self_managed` (Boolean): Does this user manage it's own credentials or is it a shared/bot user?
* `sftp_permission` (Boolean): Can the user access with SFTP?
* `site_admin` (Boolean): Is the user an administrator for this site?
* `skip_welcome_screen` (Boolean): Skip Welcome page in the UI?
* `ssl_required` (String): SSL required setting
* `sso_strategy_id` (Long): SSO (Single Sign On) strategy ID for the user, if applicable.
* `subscribe_to_newsletter` (Boolean): Is the user subscribed to the newsletter?
* `require_2fa` (String): 2FA required setting
* `time_zone` (String): User time zone
* `user_root` (String): Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set.)  Note that this is not used for API, Desktop, or Web interface.
* `username` (String): User's username


---

## Delete User

```
User user = User.ListFor(path)[0];

HashMap<String, Object> parameters = new HashMap<>();


User.Delete
```

### Parameters

* `id` (Long): Required - User ID.
