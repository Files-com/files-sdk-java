# Files.Models.Site

## Example Site Object

```
{
  "name": "My Site",
  "allowed_2fa_method_sms": true,
  "allowed_2fa_method_totp": true,
  "allowed_2fa_method_u2f": true,
  "allowed_2fa_method_yubi": true,
  "admin_user_id": 1,
  "allow_bundle_names": true,
  "allowed_ips": "",
  "ask_about_overwrites": true,
  "bundle_expiration": 1,
  "bundle_password_required": true,
  "color2_left": "#0066a7",
  "color2_link": "#d34f5d",
  "color2_text": "#0066a7",
  "color2_top": "#000000",
  "color2_top_text": "#ffffff",
  "created_at": "2000-01-01T01:00:00Z",
  "currency": "USD",
  "custom_namespace": true,
  "days_to_retain_backups": 30,
  "default_time_zone": "Pacific Time (US & Canada)",
  "desktop_app": true,
  "desktop_app_session_ip_pinning": true,
  "desktop_app_session_lifetime": 1,
  "disable_notifications": true,
  "disable_password_reset": true,
  "domain": "my-custom-domain.com",
  "email": "john.doe@files.com",
  "reply_to_email": "jane.doe@files.com",
  "non_sso_groups_allowed": true,
  "non_sso_users_allowed": true,
  "folder_permissions_groups_only": true,
  "hipaa": true,
  "icon128": "",
  "icon16": "",
  "icon32": "",
  "icon48": "",
  "immutable_files_set_at": "2000-01-01T01:00:00Z",
  "include_password_in_welcome_email": true,
  "language": "en",
  "ldap_base_dn": "",
  "ldap_domain": "mysite.com",
  "ldap_enabled": true,
  "ldap_group_action": "disabled",
  "ldap_group_exclusion": "",
  "ldap_group_inclusion": "",
  "ldap_host": "ldap.site.com",
  "ldap_host_2": "ldap2.site.com",
  "ldap_host_3": "ldap3.site.com",
  "ldap_port": 1,
  "ldap_secure": true,
  "ldap_type": "open_ldap",
  "ldap_user_action": "disabled",
  "ldap_user_include_groups": "",
  "ldap_username": "[ldap username]",
  "ldap_username_field": "sAMAccountName",
  "login_help_text": "Login page help text.",
  "logo": "",
  "max_prior_passwords": 1,
  "next_billing_amount": 1.0,
  "next_billing_date": "Apr 20",
  "office_integration_available": true,
  "opt_out_global": true,
  "overage_notified_at": "2000-01-01T01:00:00Z",
  "overage_notify": true,
  "overdue": true,
  "password_min_length": 1,
  "password_require_letter": true,
  "password_require_mixed": true,
  "password_require_number": true,
  "password_require_special": true,
  "password_require_unbreached": true,
  "password_requirements_apply_to_bundles": true,
  "password_validity_days": 1,
  "phone": "555-555-5555",
  "require_2fa": true,
  "require_2fa_stop_time": "2000-01-01T01:00:00Z",
  "require_2fa_user_type": "`site_admins`",
  "session": "",
  "session_pinned_by_ip": true,
  "sftp_user_root_enabled": true,
  "sharing_enabled": true,
  "show_request_access_link": true,
  "site_footer": "",
  "site_header": "",
  "smtp_address": "smtp.my-mail-server.com",
  "smtp_authentication": "plain",
  "smtp_from": "me@my-mail-server.com",
  "smtp_port": 25,
  "smtp_username": "mail",
  "session_expiry": 6.0,
  "ssl_required": true,
  "subdomain": "mysite",
  "switch_to_plan_date": "2000-01-01T01:00:00Z",
  "tls_disabled": true,
  "trial_days_left": 1,
  "trial_until": "2000-01-01T01:00:00Z",
  "updated_at": "2000-01-01T01:00:00Z",
  "use_provided_modified_at": true,
  "user": "",
  "user_lockout": true,
  "user_lockout_lock_period": 1,
  "user_lockout_tries": 1,
  "user_lockout_within": 6,
  "user_requests_enabled": true,
  "welcome_custom_text": "Welcome to my site!",
  "welcome_email_cc": "",
  "welcome_email_enabled": true,
  "welcome_screen": "user_controlled",
  "windows_mode_ftp": true,
  "disable_users_from_inactivity_period_days": 1
}
```

* `name` / `name`  (string): Site name
* `allowed_2fa_method_sms` / `allowed2faMethodSms`  (boolean): Is SMS two factor authentication allowed?
* `allowed_2fa_method_totp` / `allowed2faMethodTotp`  (boolean): Is TOTP two factor authentication allowed?
* `allowed_2fa_method_u2f` / `allowed2faMethodU2f`  (boolean): Is U2F two factor authentication allowed?
* `allowed_2fa_method_yubi` / `allowed2faMethodYubi`  (boolean): Is yubikey two factor authentication allowed?
* `admin_user_id` / `adminUserId`  (int64): User ID for the main site administrator
* `allow_bundle_names` / `allowBundleNames`  (boolean): Are manual Bundle names allowed?
* `allowed_ips` / `allowedIps`  (string): List of allowed IP addresses
* `ask_about_overwrites` / `askAboutOverwrites`  (boolean): If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
* `bundle_expiration` / `bundleExpiration`  (int64): Site-wide Bundle expiration in days
* `bundle_password_required` / `bundlePasswordRequired`  (boolean): Do Bundles require password protection?
* `color2_left` / `color2Left`  (string): Page link and button color
* `color2_link` / `color2Link`  (string): Top bar link color
* `color2_text` / `color2Text`  (string): Page link and button color
* `color2_top` / `color2Top`  (string): Top bar background color
* `color2_top_text` / `color2TopText`  (string): Top bar text color
* `created_at` / `createdAt`  (date-time): Time this site was created
* `currency` / `currency`  (string): Preferred currency
* `custom_namespace` / `customNamespace`  (boolean): Is this site using a custom namespace for users?
* `days_to_retain_backups` / `daysToRetainBackups`  (int64): Number of days to keep deleted files
* `default_time_zone` / `defaultTimeZone`  (string): Site default time zone
* `desktop_app` / `desktopApp`  (boolean): Is the desktop app enabled?
* `desktop_app_session_ip_pinning` / `desktopAppSessionIpPinning`  (boolean): Is desktop app session IP pinning enabled?
* `desktop_app_session_lifetime` / `desktopAppSessionLifetime`  (int64): Desktop app session lifetime (in hours)
* `disable_notifications` / `disableNotifications`  (boolean): Are notifications disabled?
* `disable_password_reset` / `disablePasswordReset`  (boolean): Is password reset disabled?
* `domain` / `domain`  (string): Custom domain
* `email` / `email`  (email): Main email for this site
* `reply_to_email` / `replyToEmail`  (email): Reply-to email for this site
* `non_sso_groups_allowed` / `nonSsoGroupsAllowed`  (boolean): If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
* `non_sso_users_allowed` / `nonSsoUsersAllowed`  (boolean): If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
* `folder_permissions_groups_only` / `folderPermissionsGroupsOnly`  (boolean): If true, permissions for this site must be bound to a group (not a user). Otherwise, permissions must be bound to a user.
* `hipaa` / `hipaa`  (boolean): Is there a signed HIPAA BAA between Files.com and this site?
* `icon128` / `icon128` : Branded icon 128x128
* `icon16` / `icon16` : Branded icon 16x16
* `icon32` / `icon32` : Branded icon 32x32
* `icon48` / `icon48` : Branded icon 48x48
* `immutable_files_set_at` / `immutableFilesSetAt`  (date-time): Can files be modified?
* `include_password_in_welcome_email` / `includePasswordInWelcomeEmail`  (boolean): Include password in emails to new users?
* `language` / `language`  (string): Site default language
* `ldap_base_dn` / `ldapBaseDn`  (string): Base DN for looking up users in LDAP server
* `ldap_domain` / `ldapDomain`  (string): Domain name that will be appended to usernames
* `ldap_enabled` / `ldapEnabled`  (boolean): Main LDAP setting: is LDAP enabled?
* `ldap_group_action` / `ldapGroupAction`  (string): Should we sync groups from LDAP server?
* `ldap_group_exclusion` / `ldapGroupExclusion`  (string): Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
* `ldap_group_inclusion` / `ldapGroupInclusion`  (string): Comma or newline separated list of group names (with optional wildcards) to include when syncing.
* `ldap_host` / `ldapHost`  (string): LDAP host
* `ldap_host_2` / `ldapHost2`  (string): LDAP backup host
* `ldap_host_3` / `ldapHost3`  (string): LDAP backup host
* `ldap_port` / `ldapPort`  (int64): LDAP port
* `ldap_secure` / `ldapSecure`  (boolean): Use secure LDAP?
* `ldap_type` / `ldapType`  (string): LDAP type
* `ldap_user_action` / `ldapUserAction`  (string): Should we sync users from LDAP server?
* `ldap_user_include_groups` / `ldapUserIncludeGroups`  (string): Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
* `ldap_username` / `ldapUsername`  (string): Username for signing in to LDAP server.
* `ldap_username_field` / `ldapUsernameField`  (string): LDAP username field
* `login_help_text` / `loginHelpText`  (string): Login help text
* `logo` / `logo` : Branded logo
* `max_prior_passwords` / `maxPriorPasswords`  (int64): Number of prior passwords to disallow
* `next_billing_amount` / `nextBillingAmount`  (double): Next billing amount
* `next_billing_date` / `nextBillingDate`  (string): Next billing date
* `office_integration_available` / `officeIntegrationAvailable`  (boolean): Allow users to use Office for the web?
* `opt_out_global` / `optOutGlobal`  (boolean): Use servers in the USA only?
* `overage_notified_at` / `overageNotifiedAt`  (date-time): Last time the site was notified about an overage
* `overage_notify` / `overageNotify`  (boolean): Notify site email of overages?
* `overdue` / `overdue`  (boolean): Is this site's billing overdue?
* `password_min_length` / `passwordMinLength`  (int64): Shortest password length for users
* `password_require_letter` / `passwordRequireLetter`  (boolean): Require a letter in passwords?
* `password_require_mixed` / `passwordRequireMixed`  (boolean): Require lower and upper case letters in passwords?
* `password_require_number` / `passwordRequireNumber`  (boolean): Require a number in passwords?
* `password_require_special` / `passwordRequireSpecial`  (boolean): Require special characters in password?
* `password_require_unbreached` / `passwordRequireUnbreached`  (boolean): Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
* `password_requirements_apply_to_bundles` / `passwordRequirementsApplyToBundles`  (boolean): Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
* `password_validity_days` / `passwordValidityDays`  (int64): Number of days password is valid
* `phone` / `phone`  (string): Site phone number
* `require_2fa` / `require2fa`  (boolean): Require two-factor authentication for all users?
* `require_2fa_stop_time` / `require2faStopTime`  (date-time): If set, requirement for two-factor authentication has been scheduled to end on this date-time.
* `require_2fa_user_type` / `require2faUserType`  (string): What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
* `session` / `session` : Current session
* `session_pinned_by_ip` / `sessionPinnedByIp`  (boolean): Are sessions locked to the same IP? (i.e. do users need to log in again if they change IPs?)
* `sftp_user_root_enabled` / `sftpUserRootEnabled`  (boolean): Use user FTP roots also for SFTP?
* `sharing_enabled` / `sharingEnabled`  (boolean): Allow bundle creation
* `show_request_access_link` / `showRequestAccessLink`  (boolean): Show request access link for users without access?  Currently unused.
* `site_footer` / `siteFooter`  (string): Custom site footer text
* `site_header` / `siteHeader`  (string): Custom site header text
* `smtp_address` / `smtpAddress`  (string): SMTP server hostname or IP
* `smtp_authentication` / `smtpAuthentication`  (string): SMTP server authentication type
* `smtp_from` / `smtpFrom`  (string): From address to use when mailing through custom SMTP
* `smtp_port` / `smtpPort`  (int64): SMTP server port
* `smtp_username` / `smtpUsername`  (string): SMTP server username
* `session_expiry` / `sessionExpiry`  (double): Session expiry in hours
* `ssl_required` / `sslRequired`  (boolean): Is SSL required?  Disabling this is insecure.
* `subdomain` / `subdomain`  (string): Site subdomain
* `switch_to_plan_date` / `switchToPlanDate`  (date-time): If switching plans, when does the new plan take effect?
* `tls_disabled` / `tlsDisabled`  (boolean): Is TLS disabled(site setting)?
* `trial_days_left` / `trialDaysLeft`  (int64): Number of days left in trial
* `trial_until` / `trialUntil`  (date-time): When does this Site trial expire?
* `updated_at` / `updatedAt`  (date-time): Last time this Site was updated
* `use_provided_modified_at` / `useProvidedModifiedAt`  (boolean): Allow uploaders to set `provided_modified_at` for uploaded files?
* `user` / `user` : User of current session
* `user_lockout` / `userLockout`  (boolean): Will users be locked out after incorrect login attempts?
* `user_lockout_lock_period` / `userLockoutLockPeriod`  (int64): How many hours to lock user out for failed password?
* `user_lockout_tries` / `userLockoutTries`  (int64): Number of login tries within `user_lockout_within` hours before users are locked out
* `user_lockout_within` / `userLockoutWithin`  (int64): Number of hours for user lockout window
* `user_requests_enabled` / `userRequestsEnabled`  (boolean): Enable User Requests feature
* `welcome_custom_text` / `welcomeCustomText`  (string): Custom text send in user welcome email
* `welcome_email_cc` / `welcomeEmailCc`  (email): Include this email in welcome emails if enabled
* `welcome_email_enabled` / `welcomeEmailEnabled`  (boolean): Will the welcome email be sent to new users?
* `welcome_screen` / `welcomeScreen`  (string): Does the welcome screen appear?
* `windows_mode_ftp` / `windowsModeFtp`  (boolean): Does FTP user Windows emulation mode?
* `disable_users_from_inactivity_period_days` / `disableUsersFromInactivityPeriodDays`  (int64): If greater than zero, users will unable to login if they do not show activity within this number of days.


---

## Show site settings

```
Site site = Site.get(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `format` (String): 
* `site` (Map<String, String>): 


---

## Get the most recent usage snapshot (usage data for billing purposes) for a Site

```
Site site = Site.getUsage(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `format` (String): 
* `site` (Map<String, String>): 


---

## Update site settings

```
Site site = Site.update(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `name` (String): Site name
* `subdomain` (String): Site subdomain
* `domain` (String): Custom domain
* `email` (String): Main email for this site
* `reply_to_email` (String): Reply-to email for this site
* `allow_bundle_names` (Boolean): Are manual Bundle names allowed?
* `bundle_expiration` (Long): Site-wide Bundle expiration in days
* `overage_notify` (Boolean): Notify site email of overages?
* `welcome_email_enabled` (Boolean): Will the welcome email be sent to new users?
* `ask_about_overwrites` (Boolean): If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
* `show_request_access_link` (Boolean): Show request access link for users without access?  Currently unused.
* `welcome_email_cc` (String): Include this email in welcome emails if enabled
* `welcome_custom_text` (String): Custom text send in user welcome email
* `language` (String): Site default language
* `windows_mode_ftp` (Boolean): Does FTP user Windows emulation mode?
* `default_time_zone` (String): Site default time zone
* `desktop_app` (Boolean): Is the desktop app enabled?
* `desktop_app_session_ip_pinning` (Boolean): Is desktop app session IP pinning enabled?
* `desktop_app_session_lifetime` (Long): Desktop app session lifetime (in hours)
* `folder_permissions_groups_only` (Boolean): If true, permissions for this site must be bound to a group (not a user). Otherwise, permissions must be bound to a user.
* `welcome_screen` (String): Does the welcome screen appear?
* `office_integration_available` (Boolean): Allow users to use Office for the web?
* `session_expiry` (Double): Session expiry in hours
* `ssl_required` (Boolean): Is SSL required?  Disabling this is insecure.
* `tls_disabled` (Boolean): Is TLS disabled(site setting)?
* `user_lockout` (Boolean): Will users be locked out after incorrect login attempts?
* `user_lockout_tries` (Long): Number of login tries within `user_lockout_within` hours before users are locked out
* `user_lockout_within` (Long): Number of hours for user lockout window
* `user_lockout_lock_period` (Long): How many hours to lock user out for failed password?
* `include_password_in_welcome_email` (Boolean): Include password in emails to new users?
* `allowed_ips` (String): List of allowed IP addresses
* `days_to_retain_backups` (Long): Number of days to keep deleted files
* `max_prior_passwords` (Long): Number of prior passwords to disallow
* `password_validity_days` (Long): Number of days password is valid
* `password_min_length` (Long): Shortest password length for users
* `password_require_letter` (Boolean): Require a letter in passwords?
* `password_require_mixed` (Boolean): Require lower and upper case letters in passwords?
* `password_require_special` (Boolean): Require special characters in password?
* `password_require_number` (Boolean): Require a number in passwords?
* `password_require_unbreached` (Boolean): Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
* `sftp_user_root_enabled` (Boolean): Use user FTP roots also for SFTP?
* `disable_password_reset` (Boolean): Is password reset disabled?
* `immutable_files` (Boolean): Are files protected from modification?
* `session_pinned_by_ip` (Boolean): Are sessions locked to the same IP? (i.e. do users need to log in again if they change IPs?)
* `bundle_password_required` (Boolean): Do Bundles require password protection?
* `password_requirements_apply_to_bundles` (Boolean): Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
* `opt_out_global` (Boolean): Use servers in the USA only?
* `use_provided_modified_at` (Boolean): Allow uploaders to set `provided_modified_at` for uploaded files?
* `custom_namespace` (Boolean): Is this site using a custom namespace for users?
* `disable_users_from_inactivity_period_days` (Long): If greater than zero, users will unable to login if they do not show activity within this number of days.
* `non_sso_groups_allowed` (Boolean): If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
* `non_sso_users_allowed` (Boolean): If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
* `sharing_enabled` (Boolean): Allow bundle creation
* `user_requests_enabled` (Boolean): Enable User Requests feature
* `allowed_2fa_method_sms` (Boolean): Is SMS two factor authentication allowed?
* `allowed_2fa_method_u2f` (Boolean): Is U2F two factor authentication allowed?
* `allowed_2fa_method_totp` (Boolean): Is TOTP two factor authentication allowed?
* `allowed_2fa_method_yubi` (Boolean): Is yubikey two factor authentication allowed?
* `require_2fa` (Boolean): Require two-factor authentication for all users?
* `require_2fa_user_type` (String): What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
* `color2_top` (String): Top bar background color
* `color2_left` (String): Page link and button color
* `color2_link` (String): Top bar link color
* `color2_text` (String): Page link and button color
* `color2_top_text` (String): Top bar text color
* `site_header` (String): Custom site header text
* `site_footer` (String): Custom site footer text
* `login_help_text` (String): Login help text
* `smtp_address` (String): SMTP server hostname or IP
* `smtp_authentication` (String): SMTP server authentication type
* `smtp_from` (String): From address to use when mailing through custom SMTP
* `smtp_username` (String): SMTP server username
* `smtp_port` (Long): SMTP server port
* `ldap_enabled` (Boolean): Main LDAP setting: is LDAP enabled?
* `ldap_type` (String): LDAP type
* `ldap_host` (String): LDAP host
* `ldap_host_2` (String): LDAP backup host
* `ldap_host_3` (String): LDAP backup host
* `ldap_port` (Long): LDAP port
* `ldap_secure` (Boolean): Use secure LDAP?
* `ldap_username` (String): Username for signing in to LDAP server.
* `ldap_username_field` (String): LDAP username field
* `ldap_domain` (String): Domain name that will be appended to usernames
* `ldap_user_action` (String): Should we sync users from LDAP server?
* `ldap_group_action` (String): Should we sync groups from LDAP server?
* `ldap_user_include_groups` (String): Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
* `ldap_group_exclusion` (String): Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
* `ldap_group_inclusion` (String): Comma or newline separated list of group names (with optional wildcards) to include when syncing.
* `ldap_base_dn` (String): Base DN for looking up users in LDAP server
* `icon16_file` (byte[]): 
* `icon16_delete` (Boolean): If true, will delete the file stored in icon16
* `icon32_file` (byte[]): 
* `icon32_delete` (Boolean): If true, will delete the file stored in icon32
* `icon48_file` (byte[]): 
* `icon48_delete` (Boolean): If true, will delete the file stored in icon48
* `icon128_file` (byte[]): 
* `icon128_delete` (Boolean): If true, will delete the file stored in icon128
* `logo_file` (byte[]): 
* `logo_delete` (Boolean): If true, will delete the file stored in logo
* `disable_2fa_with_delay` (Boolean): If set to true, we will begin the process of disabling 2FA on this site.
* `ldap_password_change` (String): New LDAP password.
* `ldap_password_change_confirmation` (String): Confirm new LDAP password.
* `smtp_password` (String): Password for SMTP server.
