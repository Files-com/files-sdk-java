# Files.Models.Site

## Example Site Object

```
{
  "id": 1,
  "name": "My Site",
  "additional_text_file_types": [
    "example"
  ],
  "allowed_2fa_method_sms": true,
  "allowed_2fa_method_totp": true,
  "allowed_2fa_method_u2f": true,
  "allowed_2fa_method_webauthn": true,
  "allowed_2fa_method_yubi": true,
  "allowed_2fa_method_email": true,
  "allowed_2fa_method_static": true,
  "allowed_2fa_method_bypass_for_ftp_sftp_dav": true,
  "admin_user_id": 1,
  "admins_bypass_locked_subfolders": true,
  "allow_bundle_names": true,
  "allowed_countries": "US,DE",
  "allowed_ips": "example",
  "always_mkdir_parents": true,
  "ask_about_overwrites": true,
  "bundle_activity_notifications": "never",
  "bundle_expiration": 1,
  "bundle_not_found_message": "example",
  "bundle_password_required": true,
  "bundle_recipient_blacklist_domains": [
    "example"
  ],
  "bundle_recipient_blacklist_free_email_domains": true,
  "bundle_registration_notifications": "never",
  "bundle_require_registration": true,
  "bundle_require_share_recipient": true,
  "bundle_require_note": true,
  "bundle_upload_receipt_notifications": "never",
  "bundle_watermark_attachment": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "bundle_watermark_value": {
    "key": "example value"
  },
  "uploads_via_email_authentication": true,
  "color2_left": "#0066a7",
  "color2_link": "#d34f5d",
  "color2_text": "#0066a7",
  "color2_top": "#000000",
  "color2_top_text": "#ffffff",
  "contact_name": "John Doe",
  "created_at": "2000-01-01T01:00:00Z",
  "currency": "USD",
  "custom_namespace": true,
  "dav_enabled": true,
  "dav_user_root_enabled": true,
  "days_to_retain_backups": 30,
  "default_time_zone": "Pacific Time (US & Canada)",
  "desktop_app": true,
  "desktop_app_session_ip_pinning": true,
  "desktop_app_session_lifetime": 1,
  "mobile_app": true,
  "mobile_app_session_ip_pinning": true,
  "mobile_app_session_lifetime": 1,
  "disallowed_countries": "US,DE",
  "disable_files_certificate_generation": true,
  "disable_notifications": true,
  "disable_password_reset": true,
  "domain": "my-custom-domain.com",
  "domain_hsts_header": true,
  "domain_letsencrypt_chain": "example",
  "email": "example",
  "ftp_enabled": true,
  "reply_to_email": "example",
  "non_sso_groups_allowed": true,
  "non_sso_users_allowed": true,
  "folder_permissions_groups_only": true,
  "hipaa": true,
  "icon128": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "icon16": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "icon32": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "icon48": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "immutable_files_set_at": "2000-01-01T01:00:00Z",
  "include_password_in_welcome_email": true,
  "language": "en",
  "ldap_base_dn": "example",
  "ldap_domain": "mysite.com",
  "ldap_enabled": true,
  "ldap_group_action": "disabled",
  "ldap_group_exclusion": "example",
  "ldap_group_inclusion": "example",
  "ldap_host": "ldap.site.com",
  "ldap_host_2": "ldap2.site.com",
  "ldap_host_3": "ldap3.site.com",
  "ldap_port": 1,
  "ldap_secure": true,
  "ldap_type": "open_ldap",
  "ldap_user_action": "disabled",
  "ldap_user_include_groups": "example",
  "ldap_username": "[ldap username]",
  "ldap_username_field": "sAMAccountName",
  "login_help_text": "Login page help text.",
  "logo": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "login_page_background_image": {
    "name": "My logo",
    "uri": "https://mysite.files.com/.../my_image.png"
  },
  "max_prior_passwords": 1,
  "motd_text": "example",
  "motd_use_for_ftp": true,
  "motd_use_for_sftp": true,
  "next_billing_amount": 1.0,
  "next_billing_date": "Apr 20",
  "office_integration_available": true,
  "office_integration_type": "example",
  "oncehub_link": "https://go.oncehub.com/files",
  "opt_out_global": true,
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
  "pin_all_remote_servers_to_site_region": true,
  "prevent_root_permissions_for_non_site_admins": true,
  "protocol_access_groups_only": true,
  "require_2fa": true,
  "require_2fa_stop_time": "2000-01-01T01:00:00Z",
  "require_2fa_user_type": "`site_admins`",
  "require_logout_from_bundles_and_inboxes": true,
  "session": {
    "id": "60525f92e859c4c3d74cb02fd176b1525901b525",
    "language": "en",
    "login_token": "@tok-randomcode",
    "login_token_domain": "https://mysite.files.com",
    "max_dir_listing_size": 1,
    "multiple_regions": true,
    "read_only": true,
    "root_path": "example",
    "sftp_insecure_ciphers": false,
    "site_id": 1,
    "ssl_required": true,
    "timeout_at": "2000-01-01T01:00:00Z",
    "tls_disabled": false,
    "two_factor_setup_needed": false,
    "allowed_2fa_method_sms": true,
    "allowed_2fa_method_totp": true,
    "allowed_2fa_method_u2f": true,
    "allowed_2fa_method_webauthn": true,
    "allowed_2fa_method_yubi": true,
    "use_provided_modified_at": true,
    "windows_mode_ftp": false,
    "user_belongs_to_parent_site": false
  },
  "session_pinned_by_ip": true,
  "sftp_enabled": true,
  "sftp_host_key_type": "default",
  "active_sftp_host_key_id": 1,
  "sftp_insecure_ciphers": true,
  "sftp_insecure_diffie_hellman": true,
  "sftp_user_root_enabled": true,
  "sharing_enabled": true,
  "show_request_access_link": true,
  "site_footer": "example",
  "site_header": "example",
  "smtp_address": "smtp.my-mail-server.com",
  "smtp_authentication": "plain",
  "smtp_from": "me@my-mail-server.com",
  "smtp_port": 25,
  "smtp_username": "mail",
  "session_expiry": 6.0,
  "session_expiry_minutes": 360,
  "ssl_required": true,
  "subdomain": "mysite",
  "switch_to_plan_date": "2000-01-01T01:00:00Z",
  "tls_disabled": true,
  "trial_days_left": 1,
  "trial_until": "2000-01-01T01:00:00Z",
  "use_dedicated_ips_for_smtp": true,
  "use_provided_modified_at": true,
  "user": {
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
    "billing_permission": false,
    "bypass_site_allowed_ips": false,
    "bypass_inactive_disable": false,
    "created_at": "2000-01-01T01:00:00Z",
    "dav_permission": true,
    "disabled": true,
    "disabled_expired_or_inactive": true,
    "email": "example",
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
    "skip_welcome_screen": true,
    "ssl_required": "always_require",
    "sso_strategy_id": 1,
    "subscribe_to_newsletter": true,
    "externally_managed": true,
    "time_zone": "Pacific Time (US & Canada)",
    "type_of_2fa": "yubi",
    "type_of_2fa_for_display": "yubi",
    "updated_at": "2000-01-01T01:00:00Z",
    "user_root": "example",
    "days_remaining_until_password_expire": 1,
    "password_expire_at": "2000-01-01T01:00:00Z"
  },
  "user_lockout": true,
  "user_lockout_lock_period": 1,
  "user_lockout_tries": 1,
  "user_lockout_within": 6,
  "user_requests_enabled": true,
  "user_requests_notify_admins": true,
  "users_can_create_api_keys": true,
  "users_can_create_ssh_keys": true,
  "welcome_custom_text": "Welcome to my site!",
  "welcome_email_cc": "example",
  "welcome_email_subject": "example",
  "welcome_email_enabled": true,
  "welcome_screen": "user_controlled",
  "windows_mode_ftp": true,
  "disable_users_from_inactivity_period_days": 1,
  "group_admins_can_set_user_password": true
}
```

* `id` / `id`  (int64): Site Id
* `name` / `name`  (string): Site name
* `additional_text_file_types` / `additionalTextFileTypes`  (array(string)): Additional extensions that are considered text files
* `allowed_2fa_method_sms` / `allowed2faMethodSms`  (boolean): Is SMS two factor authentication allowed?
* `allowed_2fa_method_totp` / `allowed2faMethodTotp`  (boolean): Is TOTP two factor authentication allowed?
* `allowed_2fa_method_u2f` / `allowed2faMethodU2f`  (boolean): Is U2F two factor authentication allowed?
* `allowed_2fa_method_webauthn` / `allowed2faMethodWebauthn`  (boolean): Is WebAuthn two factor authentication allowed?
* `allowed_2fa_method_yubi` / `allowed2faMethodYubi`  (boolean): Is yubikey two factor authentication allowed?
* `allowed_2fa_method_email` / `allowed2faMethodEmail`  (boolean): Is OTP via email two factor authentication allowed?
* `allowed_2fa_method_static` / `allowed2faMethodStatic`  (boolean): Is OTP via static codes for two factor authentication allowed?
* `allowed_2fa_method_bypass_for_ftp_sftp_dav` / `allowed2faMethodBypassForFtpSftpDav`  (boolean): Are users allowed to configure their two factor authentication to be bypassed for FTP/SFTP/WebDAV?
* `admin_user_id` / `adminUserId`  (int64): User ID for the main site administrator
* `admins_bypass_locked_subfolders` / `adminsBypassLockedSubfolders`  (boolean): Allow admins to bypass the locked subfolders setting.
* `allow_bundle_names` / `allowBundleNames`  (boolean): Are manual Bundle names allowed?
* `allowed_countries` / `allowedCountries`  (string): Comma separated list of allowed Country codes
* `allowed_ips` / `allowedIps`  (string): List of allowed IP addresses
* `always_mkdir_parents` / `alwaysMkdirParents`  (boolean): Create parent directories if they do not exist during uploads?  This is primarily used to work around broken upload clients that assume servers will perform this step.
* `ask_about_overwrites` / `askAboutOverwrites`  (boolean): If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
* `bundle_activity_notifications` / `bundleActivityNotifications`  (string): Do Bundle owners receive activity notifications?
* `bundle_expiration` / `bundleExpiration`  (int64): Site-wide Bundle expiration in days
* `bundle_not_found_message` / `bundleNotFoundMessage`  (string): Custom error message to show when bundle is not found.
* `bundle_password_required` / `bundlePasswordRequired`  (boolean): Do Bundles require password protection?
* `bundle_recipient_blacklist_domains` / `bundleRecipientBlacklistDomains`  (array(string)): List of email domains to disallow when entering a Bundle/Inbox recipients
* `bundle_recipient_blacklist_free_email_domains` / `bundleRecipientBlacklistFreeEmailDomains`  (boolean): Disallow free email domains for Bundle/Inbox recipients?
* `bundle_registration_notifications` / `bundleRegistrationNotifications`  (string): Do Bundle owners receive registration notification?
* `bundle_require_registration` / `bundleRequireRegistration`  (boolean): Do Bundles require registration?
* `bundle_require_share_recipient` / `bundleRequireShareRecipient`  (boolean): Do Bundles require recipients for sharing?
* `bundle_require_note` / `bundleRequireNote`  (boolean): Do Bundles require internal notes?
* `bundle_upload_receipt_notifications` / `bundleUploadReceiptNotifications`  (string): Do Bundle uploaders receive upload confirmation notifications?
* `bundle_watermark_attachment` / `bundleWatermarkAttachment`  (image): Preview watermark image applied to all bundle items.
* `bundle_watermark_value` / `bundleWatermarkValue`  (object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
* `uploads_via_email_authentication` / `uploadsViaEmailAuthentication`  (boolean): Do incoming emails in the Inboxes require checking for SPF/DKIM/DMARC?
* `color2_left` / `color2Left`  (string): Page link and button color
* `color2_link` / `color2Link`  (string): Top bar link color
* `color2_text` / `color2Text`  (string): Page link and button color
* `color2_top` / `color2Top`  (string): Top bar background color
* `color2_top_text` / `color2TopText`  (string): Top bar text color
* `contact_name` / `contactName`  (string): Site main contact name
* `created_at` / `createdAt`  (date-time): Time this site was created
* `currency` / `currency`  (string): Preferred currency
* `custom_namespace` / `customNamespace`  (boolean): Is this site using a custom namespace for users?
* `dav_enabled` / `davEnabled`  (boolean): Is WebDAV enabled?
* `dav_user_root_enabled` / `davUserRootEnabled`  (boolean): Use user FTP roots also for WebDAV?
* `days_to_retain_backups` / `daysToRetainBackups`  (int64): Number of days to keep deleted files
* `default_time_zone` / `defaultTimeZone`  (string): Site default time zone
* `desktop_app` / `desktopApp`  (boolean): Is the desktop app enabled?
* `desktop_app_session_ip_pinning` / `desktopAppSessionIpPinning`  (boolean): Is desktop app session IP pinning enabled?
* `desktop_app_session_lifetime` / `desktopAppSessionLifetime`  (int64): Desktop app session lifetime (in hours)
* `mobile_app` / `mobileApp`  (boolean): Is the mobile app enabled?
* `mobile_app_session_ip_pinning` / `mobileAppSessionIpPinning`  (boolean): Is mobile app session IP pinning enabled?
* `mobile_app_session_lifetime` / `mobileAppSessionLifetime`  (int64): Mobile app session lifetime (in hours)
* `disallowed_countries` / `disallowedCountries`  (string): Comma separated list of disallowed Country codes
* `disable_files_certificate_generation` / `disableFilesCertificateGeneration`  (boolean): If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.
* `disable_notifications` / `disableNotifications`  (boolean): Are notifications disabled?
* `disable_password_reset` / `disablePasswordReset`  (boolean): Is password reset disabled?
* `domain` / `domain`  (string): Custom domain
* `domain_hsts_header` / `domainHstsHeader`  (boolean): Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?
* `domain_letsencrypt_chain` / `domainLetsencryptChain`  (string): Letsencrypt chain to use when registering SSL Certificate for domain.
* `email` / `email`  (email): Main email for this site
* `ftp_enabled` / `ftpEnabled`  (boolean): Is FTP enabled?
* `reply_to_email` / `replyToEmail`  (email): Reply-to email for this site
* `non_sso_groups_allowed` / `nonSsoGroupsAllowed`  (boolean): If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
* `non_sso_users_allowed` / `nonSsoUsersAllowed`  (boolean): If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
* `folder_permissions_groups_only` / `folderPermissionsGroupsOnly`  (boolean): If true, permissions for this site must be bound to a group (not a user). Otherwise, permissions must be bound to a user.
* `hipaa` / `hipaa`  (boolean): Is there a signed HIPAA BAA between Files.com and this site?
* `icon128` / `icon128`  (image): Branded icon 128x128
* `icon16` / `icon16`  (image): Branded icon 16x16
* `icon32` / `icon32`  (image): Branded icon 32x32
* `icon48` / `icon48`  (image): Branded icon 48x48
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
* `logo` / `logo`  (image): Branded logo
* `login_page_background_image` / `loginPageBackgroundImage`  (image): Branded login page background
* `max_prior_passwords` / `maxPriorPasswords`  (int64): Number of prior passwords to disallow
* `motd_text` / `motdText`  (string): A message to show users when they connect via FTP or SFTP.
* `motd_use_for_ftp` / `motdUseForFtp`  (boolean): Show message to users connecting via FTP
* `motd_use_for_sftp` / `motdUseForSftp`  (boolean): Show message to users connecting via SFTP
* `next_billing_amount` / `nextBillingAmount`  (double): Next billing amount
* `next_billing_date` / `nextBillingDate`  (string): Next billing date
* `office_integration_available` / `officeIntegrationAvailable`  (boolean): Allow users to use Office for the web?
* `office_integration_type` / `officeIntegrationType`  (string): Office integration application used to edit and view the MS Office documents
* `oncehub_link` / `oncehubLink`  (string): Link to scheduling a meeting with our Sales team
* `opt_out_global` / `optOutGlobal`  (boolean): Use servers in the USA only?
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
* `pin_all_remote_servers_to_site_region` / `pinAllRemoteServersToSiteRegion`  (boolean): If true, we will ensure that all internal communications with any remote server are made through the primary region of the site. This setting overrides individual remote server settings.
* `prevent_root_permissions_for_non_site_admins` / `preventRootPermissionsForNonSiteAdmins`  (boolean): If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.
* `protocol_access_groups_only` / `protocolAccessGroupsOnly`  (boolean): If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.
* `require_2fa` / `require2fa`  (boolean): Require two-factor authentication for all users?
* `require_2fa_stop_time` / `require2faStopTime`  (date-time): If set, requirement for two-factor authentication has been scheduled to end on this date-time.
* `require_2fa_user_type` / `require2faUserType`  (string): What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
* `require_logout_from_bundles_and_inboxes` / `requireLogoutFromBundlesAndInboxes`  (boolean): If true, we will hide the 'Remember Me' box on Inbox and Bundle registration pages, requiring that the user logout and log back in every time they visit the page.
* `session` / `session`  (session): Current session
* `session_pinned_by_ip` / `sessionPinnedByIp`  (boolean): Are sessions locked to the same IP? (i.e. do users need to log in again if they change IPs?)
* `sftp_enabled` / `sftpEnabled`  (boolean): Is SFTP enabled?
* `sftp_host_key_type` / `sftpHostKeyType`  (string): Sftp Host Key Type
* `active_sftp_host_key_id` / `activeSftpHostKeyId`  (int64): Id of the currently selected custom SFTP Host Key
* `sftp_insecure_ciphers` / `sftpInsecureCiphers`  (boolean): If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.
* `sftp_insecure_diffie_hellman` / `sftpInsecureDiffieHellman`  (boolean): If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.
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
* `session_expiry_minutes` / `sessionExpiryMinutes`  (int64): Session expiry in minutes
* `ssl_required` / `sslRequired`  (boolean): Is SSL required?  Disabling this is insecure.
* `subdomain` / `subdomain`  (string): Site subdomain
* `switch_to_plan_date` / `switchToPlanDate`  (date-time): If switching plans, when does the new plan take effect?
* `tls_disabled` / `tlsDisabled`  (boolean): DO NOT ENABLE. This setting allows TLSv1.0 and TLSv1.1 to be used on your site.  We intend to remove this capability entirely in early 2024.  If set, the `sftp_insecure_ciphers` flag will be automatically set to true.
* `trial_days_left` / `trialDaysLeft`  (int64): Number of days left in trial
* `trial_until` / `trialUntil`  (date-time): When does this Site trial expire?
* `use_dedicated_ips_for_smtp` / `useDedicatedIpsForSmtp`  (boolean): If using custom SMTP, should we use dedicated IPs to deliver emails?
* `use_provided_modified_at` / `useProvidedModifiedAt`  (boolean): Allow uploaders to set `provided_modified_at` for uploaded files?
* `user` / `user`  (user): User of current session
* `user_lockout` / `userLockout`  (boolean): Will users be locked out after incorrect login attempts?
* `user_lockout_lock_period` / `userLockoutLockPeriod`  (int64): How many hours to lock user out for failed password?
* `user_lockout_tries` / `userLockoutTries`  (int64): Number of login tries within `user_lockout_within` hours before users are locked out
* `user_lockout_within` / `userLockoutWithin`  (int64): Number of hours for user lockout window
* `user_requests_enabled` / `userRequestsEnabled`  (boolean): Enable User Requests feature
* `user_requests_notify_admins` / `userRequestsNotifyAdmins`  (boolean): Send email to site admins when a user request is received?
* `users_can_create_api_keys` / `usersCanCreateApiKeys`  (boolean): Allow users to create their own API keys?
* `users_can_create_ssh_keys` / `usersCanCreateSshKeys`  (boolean): Allow users to create their own SSH keys?
* `welcome_custom_text` / `welcomeCustomText`  (string): Custom text send in user welcome email
* `welcome_email_cc` / `welcomeEmailCc`  (email): Include this email in welcome emails if enabled
* `welcome_email_subject` / `welcomeEmailSubject`  (string): Include this email subject in welcome emails if enabled
* `welcome_email_enabled` / `welcomeEmailEnabled`  (boolean): Will the welcome email be sent to new users?
* `welcome_screen` / `welcomeScreen`  (string): Does the welcome screen appear?
* `windows_mode_ftp` / `windowsModeFtp`  (boolean): Does FTP user Windows emulation mode?
* `disable_users_from_inactivity_period_days` / `disableUsersFromInactivityPeriodDays`  (int64): If greater than zero, users will unable to login if they do not show activity within this number of days.
* `group_admins_can_set_user_password` / `groupAdminsCanSetUserPassword`  (boolean): Allow group admins set password authentication method


---

## Show site settings

```
Site site = Site.get(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


---

## Get the most recent usage snapshot (usage data for billing purposes) for a Site

```
UsageSnapshot site = Site.getUsage(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```


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
* `domain_hsts_header` (Boolean): Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?
* `domain_letsencrypt_chain` (String): Letsencrypt chain to use when registering SSL Certificate for domain.
* `email` (String): Main email for this site
* `reply_to_email` (String): Reply-to email for this site
* `allow_bundle_names` (Boolean): Are manual Bundle names allowed?
* `bundle_expiration` (Long): Site-wide Bundle expiration in days
* `welcome_email_enabled` (Boolean): Will the welcome email be sent to new users?
* `ask_about_overwrites` (Boolean): If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
* `show_request_access_link` (Boolean): Show request access link for users without access?  Currently unused.
* `always_mkdir_parents` (Boolean): Create parent directories if they do not exist during uploads?  This is primarily used to work around broken upload clients that assume servers will perform this step.
* `welcome_email_cc` (String): Include this email in welcome emails if enabled
* `welcome_email_subject` (String): Include this email subject in welcome emails if enabled
* `welcome_custom_text` (String): Custom text send in user welcome email
* `language` (String): Site default language
* `windows_mode_ftp` (Boolean): Does FTP user Windows emulation mode?
* `default_time_zone` (String): Site default time zone
* `desktop_app` (Boolean): Is the desktop app enabled?
* `desktop_app_session_ip_pinning` (Boolean): Is desktop app session IP pinning enabled?
* `desktop_app_session_lifetime` (Long): Desktop app session lifetime (in hours)
* `mobile_app` (Boolean): Is the mobile app enabled?
* `mobile_app_session_ip_pinning` (Boolean): Is mobile app session IP pinning enabled?
* `mobile_app_session_lifetime` (Long): Mobile app session lifetime (in hours)
* `folder_permissions_groups_only` (Boolean): If true, permissions for this site must be bound to a group (not a user). Otherwise, permissions must be bound to a user.
* `welcome_screen` (String): Does the welcome screen appear?
* `office_integration_available` (Boolean): Allow users to use Office for the web?
* `office_integration_type` (String): Office integration application used to edit and view the MS Office documents
* `pin_all_remote_servers_to_site_region` (Boolean): If true, we will ensure that all internal communications with any remote server are made through the primary region of the site. This setting overrides individual remote server settings.
* `motd_text` (String): A message to show users when they connect via FTP or SFTP.
* `motd_use_for_ftp` (Boolean): Show message to users connecting via FTP
* `motd_use_for_sftp` (Boolean): Show message to users connecting via SFTP
* `left_navigation_visibility` (Map<String, String>): Visibility settings for account navigation
* `additional_text_file_types` (String[]): Additional extensions that are considered text files
* `bundle_require_note` (Boolean): Do Bundles require internal notes?
* `session_expiry` (Double): Session expiry in hours
* `ssl_required` (Boolean): Is SSL required?  Disabling this is insecure.
* `tls_disabled` (Boolean): DO NOT ENABLE. This setting allows TLSv1.0 and TLSv1.1 to be used on your site.  We intend to remove this capability entirely in early 2024.  If set, the `sftp_insecure_ciphers` flag will be automatically set to true.
* `sftp_insecure_ciphers` (Boolean): If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.
* `sftp_insecure_diffie_hellman` (Boolean): If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.
* `disable_files_certificate_generation` (Boolean): If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.
* `user_lockout` (Boolean): Will users be locked out after incorrect login attempts?
* `user_lockout_tries` (Long): Number of login tries within `user_lockout_within` hours before users are locked out
* `user_lockout_within` (Long): Number of hours for user lockout window
* `user_lockout_lock_period` (Long): How many hours to lock user out for failed password?
* `include_password_in_welcome_email` (Boolean): Include password in emails to new users?
* `allowed_countries` (String): Comma separated list of allowed Country codes
* `allowed_ips` (String): List of allowed IP addresses
* `disallowed_countries` (String): Comma separated list of disallowed Country codes
* `days_to_retain_backups` (Long): Number of days to keep deleted files
* `max_prior_passwords` (Long): Number of prior passwords to disallow
* `password_validity_days` (Long): Number of days password is valid
* `password_min_length` (Long): Shortest password length for users
* `password_require_letter` (Boolean): Require a letter in passwords?
* `password_require_mixed` (Boolean): Require lower and upper case letters in passwords?
* `password_require_special` (Boolean): Require special characters in password?
* `password_require_number` (Boolean): Require a number in passwords?
* `password_require_unbreached` (Boolean): Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
* `require_logout_from_bundles_and_inboxes` (Boolean): If true, we will hide the 'Remember Me' box on Inbox and Bundle registration pages, requiring that the user logout and log back in every time they visit the page.
* `dav_user_root_enabled` (Boolean): Use user FTP roots also for WebDAV?
* `sftp_user_root_enabled` (Boolean): Use user FTP roots also for SFTP?
* `disable_password_reset` (Boolean): Is password reset disabled?
* `immutable_files` (Boolean): Are files protected from modification?
* `session_pinned_by_ip` (Boolean): Are sessions locked to the same IP? (i.e. do users need to log in again if they change IPs?)
* `bundle_not_found_message` (String): Custom error message to show when bundle is not found.
* `bundle_password_required` (Boolean): Do Bundles require password protection?
* `bundle_require_registration` (Boolean): Do Bundles require registration?
* `bundle_require_share_recipient` (Boolean): Do Bundles require recipients for sharing?
* `bundle_registration_notifications` (String): Do Bundle owners receive registration notification?
* `bundle_activity_notifications` (String): Do Bundle owners receive activity notifications?
* `bundle_upload_receipt_notifications` (String): Do Bundle uploaders receive upload confirmation notifications?
* `password_requirements_apply_to_bundles` (Boolean): Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
* `prevent_root_permissions_for_non_site_admins` (Boolean): If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.
* `opt_out_global` (Boolean): Use servers in the USA only?
* `use_provided_modified_at` (Boolean): Allow uploaders to set `provided_modified_at` for uploaded files?
* `custom_namespace` (Boolean): Is this site using a custom namespace for users?
* `disable_users_from_inactivity_period_days` (Long): If greater than zero, users will unable to login if they do not show activity within this number of days.
* `non_sso_groups_allowed` (Boolean): If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
* `non_sso_users_allowed` (Boolean): If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
* `sharing_enabled` (Boolean): Allow bundle creation
* `user_requests_enabled` (Boolean): Enable User Requests feature
* `user_requests_notify_admins` (Boolean): Send email to site admins when a user request is received?
* `dav_enabled` (Boolean): Is WebDAV enabled?
* `ftp_enabled` (Boolean): Is FTP enabled?
* `sftp_enabled` (Boolean): Is SFTP enabled?
* `users_can_create_api_keys` (Boolean): Allow users to create their own API keys?
* `users_can_create_ssh_keys` (Boolean): Allow users to create their own SSH keys?
* `sftp_host_key_type` (String): Sftp Host Key Type
* `active_sftp_host_key_id` (Long): Id of the currently selected custom SFTP Host Key
* `protocol_access_groups_only` (Boolean): If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.
* `bundle_watermark_value` (Map<String, String>): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
* `group_admins_can_set_user_password` (Boolean): Allow group admins set password authentication method
* `bundle_recipient_blacklist_free_email_domains` (Boolean): Disallow free email domains for Bundle/Inbox recipients?
* `bundle_recipient_blacklist_domains` (String[]): List of email domains to disallow when entering a Bundle/Inbox recipients
* `admins_bypass_locked_subfolders` (Boolean): Allow admins to bypass the locked subfolders setting.
* `allowed_2fa_method_sms` (Boolean): Is SMS two factor authentication allowed?
* `allowed_2fa_method_u2f` (Boolean): Is U2F two factor authentication allowed?
* `allowed_2fa_method_totp` (Boolean): Is TOTP two factor authentication allowed?
* `allowed_2fa_method_webauthn` (Boolean): Is WebAuthn two factor authentication allowed?
* `allowed_2fa_method_yubi` (Boolean): Is yubikey two factor authentication allowed?
* `allowed_2fa_method_email` (Boolean): Is OTP via email two factor authentication allowed?
* `allowed_2fa_method_static` (Boolean): Is OTP via static codes for two factor authentication allowed?
* `allowed_2fa_method_bypass_for_ftp_sftp_dav` (Boolean): Are users allowed to configure their two factor authentication to be bypassed for FTP/SFTP/WebDAV?
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
* `use_dedicated_ips_for_smtp` (Boolean): If using custom SMTP, should we use dedicated IPs to deliver emails?
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
* `uploads_via_email_authentication` (Boolean): Do incoming emails in the Inboxes require checking for SPF/DKIM/DMARC?
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
* `bundle_watermark_attachment_file` (byte[]): 
* `bundle_watermark_attachment_delete` (Boolean): If true, will delete the file stored in bundle_watermark_attachment
* `login_page_background_image_file` (byte[]): 
* `login_page_background_image_delete` (Boolean): If true, will delete the file stored in login_page_background_image
* `disable_2fa_with_delay` (Boolean): If set to true, we will begin the process of disabling 2FA on this site.
* `ldap_password_change` (String): New LDAP password.
* `ldap_password_change_confirmation` (String): Confirm new LDAP password.
* `smtp_password` (String): Password for SMTP server.
* `session_expiry_minutes` (Long): Session expiry in minutes
