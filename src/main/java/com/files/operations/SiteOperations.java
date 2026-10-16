package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.Site;
import com.files.models.UsageSnapshot;
import com.files.net.Attachment;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;

/**
 * Site operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#sites()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Site} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SiteOperations extends ResourceOperations {
  public SiteOperations(FilesClient client) {
    super(client);
  }

  /**
   */
  public Site get() throws RuntimeException {
    return get(null, null);
  }

  public Site get(HashMap<String, Object> parameters) throws RuntimeException {
    return get(parameters, null);
  }

  public Site get(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/site";
    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public UsageSnapshot getUsage() throws RuntimeException {
    return getUsage(null, null);
  }

  public UsageSnapshot getUsage(HashMap<String, Object> parameters) throws RuntimeException {
    return getUsage(parameters, null);
  }

  public UsageSnapshot getUsage(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/site/usage";
    TypeReference<UsageSnapshot> typeReference = new TypeReference<UsageSnapshot>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   name - string - Site name
   *   subdomain - string - Site subdomain
   *   domain - string - Custom domain
   *   domain_hsts_header - boolean - Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?
   *   domain_letsencrypt_chain - string - Letsencrypt chain to use when registering SSL Certificate for domain. No longer used as of 2026.
   *   email - string - Main email for this site
   *   reply_to_email - string - Reply-to email for this site
   *   allow_bundle_names - boolean - Are manual Bundle names allowed?
   *   bundle_expiration - int64 - Site-wide Bundle expiration in days
   *   welcome_email_enabled - boolean - Will the welcome email be sent to new users?
   *   ask_about_overwrites - boolean - If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
   *   show_request_access_link - boolean - Show request access link for users without access?  Currently unused.
   *   always_mkdir_parents - boolean - Create parent directories if they do not exist during uploads?  This is primarily used to work around broken upload clients that assume servers will perform this step.
   *   welcome_email_cc - string - Include this email in welcome emails if enabled
   *   welcome_email_subject - string - Include this email subject in welcome emails if enabled
   *   welcome_custom_text - string - Custom text send in user welcome email
   *   language - string - Site default language
   *   windows_mode_ftp - boolean - Does FTP user Windows emulation mode?
   *   default_time_zone - string - Site default time zone
   *   desktop_app - boolean - Is the desktop app enabled?
   *   desktop_app_session_ip_pinning - boolean - Is desktop app session IP pinning enabled?
   *   desktop_app_session_lifetime - int64 - Desktop app session lifetime (in hours)
   *   mobile_app - boolean - Is the mobile app enabled?
   *   mobile_app_session_ip_pinning - boolean - Is mobile app session IP pinning enabled?
   *   mobile_app_session_lifetime - int64 - Mobile app session lifetime (in hours)
   *   folder_permissions_groups_only - boolean - If true, permissions for this site must be bound to a group (not a user).
   *   welcome_screen - string - Does the welcome screen appear?
   *   office_integration_available - boolean - If true, allows users to use a document editing integration.
   *   office_integration_type - string - Which document editing integration to support. Files.com Editor or Microsoft Office for the Web.
   *   pin_all_remote_servers_to_site_region - boolean - If true, we will ensure that all internal communications with any remote server are made through the primary region of the site. This setting overrides individual remote server settings.
   *   motd_text - string - A message to show users when they connect via FTP or SFTP.
   *   motd_use_for_ftp - boolean - Show message to users connecting via FTP
   *   motd_use_for_sftp - boolean - Show message to users connecting via SFTP
   *   left_navigation_visibility - object - Visibility settings for account navigation
   *   disable_all_ai_features - boolean - If true, all AI features are disabled for this site.
   *   ai_feature_availability - object - Availability settings for AI features. Each feature requires the site_admins, workspace_admins, folder_admins, and all_users keys. Optional selected_group_members defaults to false; when true, members of at least one group in group_ids get access regardless of the other options. All availability options are additive: any enabled option matching the user grants access. Optional group_ids is an array of integer IDs of active groups on this site, from any workspace. Omitted or empty group_ids grants no access through selected_group_members and does not affect other options. Disabling all AI features overrides these settings.
   *   mcp_dcr_enabled - boolean - Is OAuth DCR (dynamic client registration) for MCP enabled?
   *   additional_text_file_types - array(string) - Additional extensions that are considered text files
   *   bundle_require_note - boolean - Do Bundles require internal notes?
   *   bundle_send_shared_receipts - boolean - Do Bundle creators receive receipts of invitations?
   *   bundles_default_owned_by_primary_group - boolean - If true, new Share Links created by a user with a primary group will default to that group as owner.
   *   calculate_file_checksums_crc32 - boolean - Calculate CRC32 checksums for files?
   *   calculate_file_checksums_md5 - boolean - Calculate MD5 checksums for files?
   *   calculate_file_checksums_sha1 - boolean - Calculate SHA1 checksums for files?
   *   calculate_file_checksums_sha256 - boolean - Calculate SHA256 checksums for files?
   *   legacy_checksums_mode - boolean - Use legacy checksums mode?
   *   migrate_remote_server_sync_to_sync - boolean - If true, we will migrate all remote server syncs to the new Sync model.
   *   as2_message_retention_days - int64 - Number of days to retain AS2 messages (incoming and outgoing).
   *   file_activity_events_enabled - boolean - If true, file operations produce file_action.activity.v1 EventChannel events in all workspaces. Defaults to false; subscription filters and workspace access still control delivery.
   *   username_display - string - How usernames are displayed in the web UI. Can be `username_only`, `full_name_only`, `full_name_username`, `full_name_company`, or `full_name_username_company`.
   *   session_expiry_minutes - int64 - Session expiry in minutes
   *   ssl_required - boolean - Is SSL required?  Disabling this is insecure.
   *   sftp_insecure_ciphers - boolean - If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.
   *   sftp_insecure_diffie_hellman - boolean - If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.
   *   disable_files_certificate_generation - boolean - If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.
   *   fedramp - boolean - Are FedRAMP security restrictions enabled for this site?
   *   user_lockout - boolean - Will users be locked out after incorrect login attempts?
   *   user_lockout_tries - int64 - Number of login tries within `user_lockout_within` hours before users are locked out
   *   user_lockout_within - int64 - Number of hours for user lockout window
   *   user_lockout_lock_period - int64 - How many hours to lock user out for failed password?
   *   include_password_in_welcome_email - boolean - Include password in emails to new users?
   *   allowed_countries - string - Comma separated list of allowed Country codes
   *   allowed_ips - string - List of allowed IP addresses
   *   allow_user_level_2fa_override - boolean - Allow the site-wide two-factor authentication requirement to be overriden on a per-user-basis?
   *   allow_user_level_allowed_ip_override - boolean - Allow the site-wide allowed IP restriction to be overriden on a per-user-basis?
   *   allow_user_level_ssl_override - boolean - Allow the site-wide FTP SSL requirement to be overriden on a per-user-basis?
   *   disallowed_countries - string - Comma separated list of disallowed Country codes
   *   days_to_retain_backups - int64 - Number of days to keep deleted files
   *   max_prior_passwords - int64 - Number of prior passwords to disallow
   *   password_validity_days - int64 - Number of days password is valid
   *   password_min_length - int64 - Shortest password length for users
   *   password_require_letter - boolean - Require a letter in passwords?
   *   password_require_mixed - boolean - Require lower and upper case letters in passwords?
   *   password_require_special - boolean - Require special characters in password?
   *   password_require_number - boolean - Require a number in passwords?
   *   password_require_unbreached - boolean - Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
   *   require_logout_from_bundles_and_inboxes - boolean - If true, we will hide the 'Remember Me' box on Inbox and Bundle registration pages, requiring that the user logout and log back in every time they visit the page.
   *   dav_user_root_enabled - boolean - Use user FTP roots also for WebDAV?
   *   sftp_user_root_enabled - boolean - Use user FTP roots also for SFTP?
   *   disable_password_reset - boolean - Is password reset disabled?
   *   immutable_files - boolean - Are files protected from modification?
   *   bundle_not_found_message - string - Custom error message to show when bundle is not found.
   *   bundle_password_required - boolean - Do Bundles require password protection?
   *   bundle_require_registration - boolean - Do Bundles require registration?
   *   bundle_require_share_recipient - boolean - Do Bundles require recipients for sharing?
   *   bundle_send_one_time_password_to_recipient_at_registration - boolean - If true, new Share Links must send a one-time password to the recipient when they register. Requires bundle_require_share_recipient and cannot be enabled with bundle_password_required.
   *   bundle_registration_notifications - string - Do Bundle owners receive registration notification?
   *   bundle_activity_notifications - string - Do Bundle owners receive activity notifications?
   *   bundle_upload_receipt_notifications - string - Do Bundle uploaders receive upload confirmation notifications?
   *   document_edits_in_bundle_allowed - boolean - If true, allow public viewers of Bundles with full permissions to use document editing integrations.
   *   password_requirements_apply_to_bundles - boolean - Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
   *   prevent_root_permissions_for_non_site_admins - boolean - If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.
   *   restrict_root_folder_behaviors_to_site_admins - boolean - If true, only site admins may create, modify, or delete any behavior at the site root, or a skip that would disable one.
   *   root_folder_behaviors_apply_to_workspaces - boolean - If true, supported protective behaviors at the site root also apply within named workspaces. Requires restrict_root_folder_behaviors_to_site_admins to be enabled.
   *   opt_out_global - boolean - Use servers in the USA only?
   *   use_provided_modified_at - boolean - Allow uploaders to set `provided_modified_at` for uploaded files?
   *   custom_namespace - boolean - Is this site using a custom namespace for users?
   *   non_sso_groups_allowed - boolean - If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
   *   non_sso_users_allowed - boolean - If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
   *   sharing_enabled - boolean - Allow bundle creation
   *   snapshot_sharing_enabled - boolean - Allow snapshot share links creation
   *   user_requests_enabled - boolean - Enable User Requests feature
   *   user_requests_notify_admins - boolean - Send email to site admins when a user request is received?
   *   dav_enabled - boolean - Is WebDAV enabled?
   *   files_com_remote_server_enabled - boolean - Allow other Files.com sites to use this site's API keys for native Files.com Remote Server connections? Defaults to true and applies to all keys and workspaces on this site. When false, new pairings and access through existing connections are rejected without revoking keys or deleting connections. Re-enabling permits access again with usable keys. Does not disable this site's connections to other sites, ordinary API access, or Connected Sites.
   *   ftp_enabled - boolean - Is FTP enabled?
   *   s3_compatible_endpoint_enabled - boolean - Is the S3-compatible endpoint enabled for all users and workspaces on this site? Defaults to true. When false, user and group S3 permissions do not allow access.
   *   sftp_enabled - boolean - Is SFTP enabled?
   *   sftp_finalize_partial_uploads - boolean - Finalize partial SFTP uploads from interrupted connections? Default: true.
   *   users_can_create_api_keys - boolean - Allow users to create their own API keys?
   *   users_can_create_ssh_keys - boolean - Allow users to create their own SSH keys?
   *   show_user_notifications_log_in_link - boolean - Show log in link in user notifications?
   *   sftp_host_key_type - string - Sftp Host Key Type
   *   active_sftp_host_key_id - int64 - Id of the currently selected custom SFTP Host Key
   *   active_sftp_host_key_ids - array(int64) - Ids of the selected custom SFTP Host Keys
   *   protocol_access_groups_only - boolean - If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.
   *   revoke_bundle_access_on_disable_or_delete - boolean - Auto-removes bundles for disabled/deleted users and enforces bundle expiry within user access period.
   *   group_admins_can_add_users - boolean - Allow group admins to create users in their groups
   *   group_admins_can_manage_group_memberships - boolean - Allow group admins to add or remove existing users in their groups
   *   group_admins_can_delete_users - boolean - Allow group admins to delete users in their groups
   *   group_admins_can_enable_disable_users - boolean - Allow group admins to enable or disable users in their groups
   *   group_admins_can_modify_users - boolean - Allow group admins to modify users in their groups
   *   group_admins_can_bypass_user_lifecycle_rules - boolean - Allow group admins to exempt users in their groups from lifecycle rules
   *   group_admins_can_reset_passwords - boolean - Allow group admins to reset passwords for users in their groups
   *   group_admins_can_set_user_password - boolean - Allow group admins to set password authentication method
   *   child_site_folder_admins_can_see_parent_site_directory - boolean - If true, administrators on this site's Child Sites, including Folder Admins, can list this site's Default Workspace users and groups and grant them folder permissions and notifications on Child Site paths.
   *   workspace_folder_admins_can_see_default_workspace_directory - boolean - If true, Workspace Administrators and Folder Admins in a Custom Workspace can list the Default Workspace users and groups that have permissions in that Workspace and choose them as Email Notification recipients.
   *   bundle_recipient_blacklist_free_email_domains - boolean - Disallow free email domains for Bundle/Inbox recipients?
   *   bundle_recipient_blacklist_domains - array(string) - List of email domains to disallow when entering a Bundle/Inbox recipients
   *   admins_bypass_locked_subfolders - boolean - Allow admins to bypass the locked subfolders setting.
   *   allowed_2fa_method_sms - boolean - Is SMS two factor authentication allowed?
   *   allowed_2fa_method_totp - boolean - Is TOTP two factor authentication allowed?
   *   allowed_2fa_method_webauthn - boolean - Is WebAuthn two factor authentication allowed?
   *   allowed_2fa_method_yubi - boolean - Is yubikey two factor authentication allowed?
   *   allowed_2fa_method_email - boolean - Is OTP via email two factor authentication allowed?
   *   allowed_2fa_method_static - boolean - Is OTP via static codes for two factor authentication allowed?
   *   allowed_2fa_method_bypass_for_ftp_sftp_dav - boolean - Are users allowed to configure their two factor authentication to be bypassed for FTP/SFTP/WebDAV?
   *   require_2fa - boolean - Require two-factor authentication for all users?
   *   require_2fa_exempt_all_sso_users - boolean - If true, SSO users using the default user-level two-factor authentication setting are exempt from the site-wide two-factor authentication requirement.
   *   require_2fa_user_type - string - What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
   *   color2_top - string - Top bar background color
   *   color2_left - string - Page link and button color
   *   color2_link - string - Top bar link color
   *   color2_text - string - Page link and button color
   *   color2_top_text - string - Top bar text color
   *   site_header - string - Custom site header text for authenticated pages
   *   site_footer - string - Custom site footer text for authenticated pages
   *   site_public_header - string - Custom site header text for public pages
   *   site_public_footer - string - Custom site footer text for public pages
   *   login_help_text - string - Login help text
   *   use_dedicated_ips_for_smtp - boolean - If using custom SMTP, should we use dedicated IPs to deliver emails?
   *   email_footer_custom_text - string - Custom footer text for system-generated emails (as Markdown). Supports standard strftime date/time patterns like %Y (4-digit year), %m (month), %d (day).
   *   smtp_address - string - SMTP server hostname or IP
   *   smtp_authentication - string - SMTP server authentication type
   *   smtp_from - string - From address to use when mailing through custom SMTP
   *   smtp_username - string - SMTP server username
   *   smtp_port - int64 - SMTP server port
   *   smtp_ssl - string - Custom SMTP encryption mode: if_available (default) uses STARTTLS when offered and otherwise sends credentials and messages unencrypted. A certificate-verified STARTTLS connection automatically changes if_available to require unless smtp_ssl is managed by a parent policy. require requires STARTTLS before authentication; require_implicit uses TLS from connection start; never disables TLS. TLS verifies the server certificate against smtp_address. Encryption is never automatically downgraded.
   *   ldap_enabled - boolean - Main LDAP setting: is LDAP enabled?
   *   ldap_type - string - LDAP type
   *   ldap_host - string - LDAP host
   *   ldap_host_2 - string - LDAP backup host
   *   ldap_host_3 - string - LDAP backup host
   *   ldap_port - int64 - LDAP port
   *   ldap_secure - boolean - Use secure LDAP?
   *   ldap_server_certificate - string - How to validate the LDAP server certificate. `require_match` validates the certificate chain and hostname; `allow_any` disables certificate validation.
   *   ldap_username - string - Username for signing in to LDAP server.
   *   ldap_username_field - string - LDAP username field
   *   ldap_domain - string - Domain name that will be appended to usernames
   *   ldap_user_action - string - Should we sync users from LDAP server?
   *   ldap_group_action - string - Should we sync groups from LDAP server?
   *   ldap_user_include_groups - string - Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
   *   ldap_group_exclusion - string - Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
   *   ldap_group_inclusion - string - Comma or newline separated list of group names (with optional wildcards) to include when syncing.
   *   ldap_base_dn - string - Base DN for looking up users in LDAP server
   *   uploads_via_email_authentication - boolean - Require email authentication, virus, and spam checks for incoming emails to Inboxes and Incoming Email Automations in every Workspace on this site?
   *   bundle_watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   *   icon16_file - file
   *   icon16_delete - boolean - If true, will delete the file stored in icon16
   *   icon32_file - file
   *   icon32_delete - boolean - If true, will delete the file stored in icon32
   *   icon48_file - file
   *   icon48_delete - boolean - If true, will delete the file stored in icon48
   *   icon128_file - file
   *   icon128_delete - boolean - If true, will delete the file stored in icon128
   *   logo_file - file
   *   logo_delete - boolean - If true, will delete the file stored in logo
   *   bundle_watermark_attachment_file - file
   *   bundle_watermark_attachment_delete - boolean - If true, will delete the file stored in bundle_watermark_attachment
   *   login_page_background_image_file - file
   *   login_page_background_image_delete - boolean - If true, will delete the file stored in login_page_background_image
   *   disable_2fa_with_delay - boolean - If set to true, we will begin the process of disabling 2FA on this site.
   *   ldap_password_change - string - New LDAP password.
   *   ldap_password_change_confirmation - string - Confirm new LDAP password.
   *   redirect_old_subdomain - boolean - If true, and if changing the site subdomain, then create a redirect from the previous Files.com subdomain to the new Files.com subdomain.
   *   smtp_password - string - Password for SMTP server.
   */
  public Site update() throws RuntimeException {
    return update(null, null);
  }

  public Site update(HashMap<String, Object> parameters) throws RuntimeException {
    return update(parameters, null);
  }

  public Site update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("subdomain") && !(parameters.get("subdomain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: subdomain must be of type String parameters[\"subdomain\"]");
    }

    if (parameters.containsKey("domain") && !(parameters.get("domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: domain must be of type String parameters[\"domain\"]");
    }

    if (parameters.containsKey("domain_hsts_header") && !(parameters.get("domain_hsts_header") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: domain_hsts_header must be of type Boolean parameters[\"domain_hsts_header\"]");
    }

    if (parameters.containsKey("domain_letsencrypt_chain") && !(parameters.get("domain_letsencrypt_chain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: domain_letsencrypt_chain must be of type String parameters[\"domain_letsencrypt_chain\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    if (parameters.containsKey("reply_to_email") && !(parameters.get("reply_to_email") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: reply_to_email must be of type String parameters[\"reply_to_email\"]");
    }

    if (parameters.containsKey("allow_bundle_names") && !(parameters.get("allow_bundle_names") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_bundle_names must be of type Boolean parameters[\"allow_bundle_names\"]");
    }

    if (parameters.containsKey("bundle_expiration") && !(parameters.get("bundle_expiration") instanceof Long || parameters.get("bundle_expiration") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: bundle_expiration must be of type Long or Integer parameters[\"bundle_expiration\"]");
    }

    if (parameters.containsKey("welcome_email_enabled") && !(parameters.get("welcome_email_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: welcome_email_enabled must be of type Boolean parameters[\"welcome_email_enabled\"]");
    }

    if (parameters.containsKey("ask_about_overwrites") && !(parameters.get("ask_about_overwrites") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ask_about_overwrites must be of type Boolean parameters[\"ask_about_overwrites\"]");
    }

    if (parameters.containsKey("show_request_access_link") && !(parameters.get("show_request_access_link") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: show_request_access_link must be of type Boolean parameters[\"show_request_access_link\"]");
    }

    if (parameters.containsKey("always_mkdir_parents") && !(parameters.get("always_mkdir_parents") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: always_mkdir_parents must be of type Boolean parameters[\"always_mkdir_parents\"]");
    }

    if (parameters.containsKey("welcome_email_cc") && !(parameters.get("welcome_email_cc") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: welcome_email_cc must be of type String parameters[\"welcome_email_cc\"]");
    }

    if (parameters.containsKey("welcome_email_subject") && !(parameters.get("welcome_email_subject") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: welcome_email_subject must be of type String parameters[\"welcome_email_subject\"]");
    }

    if (parameters.containsKey("welcome_custom_text") && !(parameters.get("welcome_custom_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: welcome_custom_text must be of type String parameters[\"welcome_custom_text\"]");
    }

    if (parameters.containsKey("language") && !(parameters.get("language") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: language must be of type String parameters[\"language\"]");
    }

    if (parameters.containsKey("windows_mode_ftp") && !(parameters.get("windows_mode_ftp") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: windows_mode_ftp must be of type Boolean parameters[\"windows_mode_ftp\"]");
    }

    if (parameters.containsKey("default_time_zone") && !(parameters.get("default_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: default_time_zone must be of type String parameters[\"default_time_zone\"]");
    }

    if (parameters.containsKey("desktop_app") && !(parameters.get("desktop_app") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: desktop_app must be of type Boolean parameters[\"desktop_app\"]");
    }

    if (parameters.containsKey("desktop_app_session_ip_pinning") && !(parameters.get("desktop_app_session_ip_pinning") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: desktop_app_session_ip_pinning must be of type Boolean parameters[\"desktop_app_session_ip_pinning\"]");
    }

    if (parameters.containsKey("desktop_app_session_lifetime") && !(parameters.get("desktop_app_session_lifetime") instanceof Long || parameters.get("desktop_app_session_lifetime") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: desktop_app_session_lifetime must be of type Long or Integer parameters[\"desktop_app_session_lifetime\"]");
    }

    if (parameters.containsKey("mobile_app") && !(parameters.get("mobile_app") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mobile_app must be of type Boolean parameters[\"mobile_app\"]");
    }

    if (parameters.containsKey("mobile_app_session_ip_pinning") && !(parameters.get("mobile_app_session_ip_pinning") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mobile_app_session_ip_pinning must be of type Boolean parameters[\"mobile_app_session_ip_pinning\"]");
    }

    if (parameters.containsKey("mobile_app_session_lifetime") && !(parameters.get("mobile_app_session_lifetime") instanceof Long || parameters.get("mobile_app_session_lifetime") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: mobile_app_session_lifetime must be of type Long or Integer parameters[\"mobile_app_session_lifetime\"]");
    }

    if (parameters.containsKey("folder_permissions_groups_only") && !(parameters.get("folder_permissions_groups_only") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: folder_permissions_groups_only must be of type Boolean parameters[\"folder_permissions_groups_only\"]");
    }

    if (parameters.containsKey("welcome_screen") && !(parameters.get("welcome_screen") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: welcome_screen must be of type String parameters[\"welcome_screen\"]");
    }

    if (parameters.containsKey("office_integration_available") && !(parameters.get("office_integration_available") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: office_integration_available must be of type Boolean parameters[\"office_integration_available\"]");
    }

    if (parameters.containsKey("office_integration_type") && !(parameters.get("office_integration_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: office_integration_type must be of type String parameters[\"office_integration_type\"]");
    }

    if (parameters.containsKey("pin_all_remote_servers_to_site_region") && !(parameters.get("pin_all_remote_servers_to_site_region") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: pin_all_remote_servers_to_site_region must be of type Boolean parameters[\"pin_all_remote_servers_to_site_region\"]");
    }

    if (parameters.containsKey("motd_text") && !(parameters.get("motd_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: motd_text must be of type String parameters[\"motd_text\"]");
    }

    if (parameters.containsKey("motd_use_for_ftp") && !(parameters.get("motd_use_for_ftp") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: motd_use_for_ftp must be of type Boolean parameters[\"motd_use_for_ftp\"]");
    }

    if (parameters.containsKey("motd_use_for_sftp") && !(parameters.get("motd_use_for_sftp") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: motd_use_for_sftp must be of type Boolean parameters[\"motd_use_for_sftp\"]");
    }

    if (parameters.containsKey("left_navigation_visibility") && !(parameters.get("left_navigation_visibility") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: left_navigation_visibility must be of type Object parameters[\"left_navigation_visibility\"]");
    }

    if (parameters.containsKey("disable_all_ai_features") && !(parameters.get("disable_all_ai_features") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_all_ai_features must be of type Boolean parameters[\"disable_all_ai_features\"]");
    }

    if (parameters.containsKey("ai_feature_availability") && !(parameters.get("ai_feature_availability") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: ai_feature_availability must be of type Object parameters[\"ai_feature_availability\"]");
    }

    if (parameters.containsKey("mcp_dcr_enabled") && !(parameters.get("mcp_dcr_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mcp_dcr_enabled must be of type Boolean parameters[\"mcp_dcr_enabled\"]");
    }

    if (parameters.containsKey("additional_text_file_types") && !(parameters.get("additional_text_file_types") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: additional_text_file_types must be of type String[] parameters[\"additional_text_file_types\"]");
    }

    if (parameters.containsKey("bundle_require_note") && !(parameters.get("bundle_require_note") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_require_note must be of type Boolean parameters[\"bundle_require_note\"]");
    }

    if (parameters.containsKey("bundle_send_shared_receipts") && !(parameters.get("bundle_send_shared_receipts") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_send_shared_receipts must be of type Boolean parameters[\"bundle_send_shared_receipts\"]");
    }

    if (parameters.containsKey("bundles_default_owned_by_primary_group") && !(parameters.get("bundles_default_owned_by_primary_group") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundles_default_owned_by_primary_group must be of type Boolean parameters[\"bundles_default_owned_by_primary_group\"]");
    }

    if (parameters.containsKey("calculate_file_checksums_crc32") && !(parameters.get("calculate_file_checksums_crc32") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: calculate_file_checksums_crc32 must be of type Boolean parameters[\"calculate_file_checksums_crc32\"]");
    }

    if (parameters.containsKey("calculate_file_checksums_md5") && !(parameters.get("calculate_file_checksums_md5") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: calculate_file_checksums_md5 must be of type Boolean parameters[\"calculate_file_checksums_md5\"]");
    }

    if (parameters.containsKey("calculate_file_checksums_sha1") && !(parameters.get("calculate_file_checksums_sha1") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: calculate_file_checksums_sha1 must be of type Boolean parameters[\"calculate_file_checksums_sha1\"]");
    }

    if (parameters.containsKey("calculate_file_checksums_sha256") && !(parameters.get("calculate_file_checksums_sha256") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: calculate_file_checksums_sha256 must be of type Boolean parameters[\"calculate_file_checksums_sha256\"]");
    }

    if (parameters.containsKey("legacy_checksums_mode") && !(parameters.get("legacy_checksums_mode") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: legacy_checksums_mode must be of type Boolean parameters[\"legacy_checksums_mode\"]");
    }

    if (parameters.containsKey("migrate_remote_server_sync_to_sync") && !(parameters.get("migrate_remote_server_sync_to_sync") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: migrate_remote_server_sync_to_sync must be of type Boolean parameters[\"migrate_remote_server_sync_to_sync\"]");
    }

    if (parameters.containsKey("as2_message_retention_days") && !(parameters.get("as2_message_retention_days") instanceof Long || parameters.get("as2_message_retention_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: as2_message_retention_days must be of type Long or Integer parameters[\"as2_message_retention_days\"]");
    }

    if (parameters.containsKey("file_activity_events_enabled") && !(parameters.get("file_activity_events_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: file_activity_events_enabled must be of type Boolean parameters[\"file_activity_events_enabled\"]");
    }

    if (parameters.containsKey("username_display") && !(parameters.get("username_display") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username_display must be of type String parameters[\"username_display\"]");
    }

    if (parameters.containsKey("session_expiry_minutes") && !(parameters.get("session_expiry_minutes") instanceof Long || parameters.get("session_expiry_minutes") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: session_expiry_minutes must be of type Long or Integer parameters[\"session_expiry_minutes\"]");
    }

    if (parameters.containsKey("ssl_required") && !(parameters.get("ssl_required") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ssl_required must be of type Boolean parameters[\"ssl_required\"]");
    }

    if (parameters.containsKey("sftp_insecure_ciphers") && !(parameters.get("sftp_insecure_ciphers") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_insecure_ciphers must be of type Boolean parameters[\"sftp_insecure_ciphers\"]");
    }

    if (parameters.containsKey("sftp_insecure_diffie_hellman") && !(parameters.get("sftp_insecure_diffie_hellman") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_insecure_diffie_hellman must be of type Boolean parameters[\"sftp_insecure_diffie_hellman\"]");
    }

    if (parameters.containsKey("disable_files_certificate_generation") && !(parameters.get("disable_files_certificate_generation") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_files_certificate_generation must be of type Boolean parameters[\"disable_files_certificate_generation\"]");
    }

    if (parameters.containsKey("fedramp") && !(parameters.get("fedramp") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: fedramp must be of type Boolean parameters[\"fedramp\"]");
    }

    if (parameters.containsKey("user_lockout") && !(parameters.get("user_lockout") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: user_lockout must be of type Boolean parameters[\"user_lockout\"]");
    }

    if (parameters.containsKey("user_lockout_tries") && !(parameters.get("user_lockout_tries") instanceof Long || parameters.get("user_lockout_tries") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_lockout_tries must be of type Long or Integer parameters[\"user_lockout_tries\"]");
    }

    if (parameters.containsKey("user_lockout_within") && !(parameters.get("user_lockout_within") instanceof Long || parameters.get("user_lockout_within") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_lockout_within must be of type Long or Integer parameters[\"user_lockout_within\"]");
    }

    if (parameters.containsKey("user_lockout_lock_period") && !(parameters.get("user_lockout_lock_period") instanceof Long || parameters.get("user_lockout_lock_period") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_lockout_lock_period must be of type Long or Integer parameters[\"user_lockout_lock_period\"]");
    }

    if (parameters.containsKey("include_password_in_welcome_email") && !(parameters.get("include_password_in_welcome_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_password_in_welcome_email must be of type Boolean parameters[\"include_password_in_welcome_email\"]");
    }

    if (parameters.containsKey("allowed_countries") && !(parameters.get("allowed_countries") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_countries must be of type String parameters[\"allowed_countries\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("allow_user_level_2fa_override") && !(parameters.get("allow_user_level_2fa_override") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_level_2fa_override must be of type Boolean parameters[\"allow_user_level_2fa_override\"]");
    }

    if (parameters.containsKey("allow_user_level_allowed_ip_override") && !(parameters.get("allow_user_level_allowed_ip_override") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_level_allowed_ip_override must be of type Boolean parameters[\"allow_user_level_allowed_ip_override\"]");
    }

    if (parameters.containsKey("allow_user_level_ssl_override") && !(parameters.get("allow_user_level_ssl_override") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_level_ssl_override must be of type Boolean parameters[\"allow_user_level_ssl_override\"]");
    }

    if (parameters.containsKey("disallowed_countries") && !(parameters.get("disallowed_countries") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: disallowed_countries must be of type String parameters[\"disallowed_countries\"]");
    }

    if (parameters.containsKey("days_to_retain_backups") && !(parameters.get("days_to_retain_backups") instanceof Long || parameters.get("days_to_retain_backups") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: days_to_retain_backups must be of type Long or Integer parameters[\"days_to_retain_backups\"]");
    }

    if (parameters.containsKey("max_prior_passwords") && !(parameters.get("max_prior_passwords") instanceof Long || parameters.get("max_prior_passwords") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_prior_passwords must be of type Long or Integer parameters[\"max_prior_passwords\"]");
    }

    if (parameters.containsKey("password_validity_days") && !(parameters.get("password_validity_days") instanceof Long || parameters.get("password_validity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: password_validity_days must be of type Long or Integer parameters[\"password_validity_days\"]");
    }

    if (parameters.containsKey("password_min_length") && !(parameters.get("password_min_length") instanceof Long || parameters.get("password_min_length") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: password_min_length must be of type Long or Integer parameters[\"password_min_length\"]");
    }

    if (parameters.containsKey("password_require_letter") && !(parameters.get("password_require_letter") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: password_require_letter must be of type Boolean parameters[\"password_require_letter\"]");
    }

    if (parameters.containsKey("password_require_mixed") && !(parameters.get("password_require_mixed") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: password_require_mixed must be of type Boolean parameters[\"password_require_mixed\"]");
    }

    if (parameters.containsKey("password_require_special") && !(parameters.get("password_require_special") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: password_require_special must be of type Boolean parameters[\"password_require_special\"]");
    }

    if (parameters.containsKey("password_require_number") && !(parameters.get("password_require_number") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: password_require_number must be of type Boolean parameters[\"password_require_number\"]");
    }

    if (parameters.containsKey("password_require_unbreached") && !(parameters.get("password_require_unbreached") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: password_require_unbreached must be of type Boolean parameters[\"password_require_unbreached\"]");
    }

    if (parameters.containsKey("require_logout_from_bundles_and_inboxes") && !(parameters.get("require_logout_from_bundles_and_inboxes") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_logout_from_bundles_and_inboxes must be of type Boolean parameters[\"require_logout_from_bundles_and_inboxes\"]");
    }

    if (parameters.containsKey("dav_user_root_enabled") && !(parameters.get("dav_user_root_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_user_root_enabled must be of type Boolean parameters[\"dav_user_root_enabled\"]");
    }

    if (parameters.containsKey("sftp_user_root_enabled") && !(parameters.get("sftp_user_root_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_user_root_enabled must be of type Boolean parameters[\"sftp_user_root_enabled\"]");
    }

    if (parameters.containsKey("disable_password_reset") && !(parameters.get("disable_password_reset") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_password_reset must be of type Boolean parameters[\"disable_password_reset\"]");
    }

    if (parameters.containsKey("immutable_files") && !(parameters.get("immutable_files") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: immutable_files must be of type Boolean parameters[\"immutable_files\"]");
    }

    if (parameters.containsKey("bundle_not_found_message") && !(parameters.get("bundle_not_found_message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: bundle_not_found_message must be of type String parameters[\"bundle_not_found_message\"]");
    }

    if (parameters.containsKey("bundle_password_required") && !(parameters.get("bundle_password_required") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_password_required must be of type Boolean parameters[\"bundle_password_required\"]");
    }

    if (parameters.containsKey("bundle_require_registration") && !(parameters.get("bundle_require_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_require_registration must be of type Boolean parameters[\"bundle_require_registration\"]");
    }

    if (parameters.containsKey("bundle_require_share_recipient") && !(parameters.get("bundle_require_share_recipient") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_require_share_recipient must be of type Boolean parameters[\"bundle_require_share_recipient\"]");
    }

    if (parameters.containsKey("bundle_send_one_time_password_to_recipient_at_registration") && !(parameters.get("bundle_send_one_time_password_to_recipient_at_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_send_one_time_password_to_recipient_at_registration must be of type Boolean parameters[\"bundle_send_one_time_password_to_recipient_at_registration\"]");
    }

    if (parameters.containsKey("bundle_registration_notifications") && !(parameters.get("bundle_registration_notifications") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: bundle_registration_notifications must be of type String parameters[\"bundle_registration_notifications\"]");
    }

    if (parameters.containsKey("bundle_activity_notifications") && !(parameters.get("bundle_activity_notifications") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: bundle_activity_notifications must be of type String parameters[\"bundle_activity_notifications\"]");
    }

    if (parameters.containsKey("bundle_upload_receipt_notifications") && !(parameters.get("bundle_upload_receipt_notifications") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: bundle_upload_receipt_notifications must be of type String parameters[\"bundle_upload_receipt_notifications\"]");
    }

    if (parameters.containsKey("document_edits_in_bundle_allowed") && !(parameters.get("document_edits_in_bundle_allowed") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: document_edits_in_bundle_allowed must be of type Boolean parameters[\"document_edits_in_bundle_allowed\"]");
    }

    if (parameters.containsKey("password_requirements_apply_to_bundles") && !(parameters.get("password_requirements_apply_to_bundles") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: password_requirements_apply_to_bundles must be of type Boolean parameters[\"password_requirements_apply_to_bundles\"]");
    }

    if (parameters.containsKey("prevent_root_permissions_for_non_site_admins") && !(parameters.get("prevent_root_permissions_for_non_site_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: prevent_root_permissions_for_non_site_admins must be of type Boolean parameters[\"prevent_root_permissions_for_non_site_admins\"]");
    }

    if (parameters.containsKey("restrict_root_folder_behaviors_to_site_admins") && !(parameters.get("restrict_root_folder_behaviors_to_site_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restrict_root_folder_behaviors_to_site_admins must be of type Boolean parameters[\"restrict_root_folder_behaviors_to_site_admins\"]");
    }

    if (parameters.containsKey("root_folder_behaviors_apply_to_workspaces") && !(parameters.get("root_folder_behaviors_apply_to_workspaces") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: root_folder_behaviors_apply_to_workspaces must be of type Boolean parameters[\"root_folder_behaviors_apply_to_workspaces\"]");
    }

    if (parameters.containsKey("opt_out_global") && !(parameters.get("opt_out_global") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: opt_out_global must be of type Boolean parameters[\"opt_out_global\"]");
    }

    if (parameters.containsKey("use_provided_modified_at") && !(parameters.get("use_provided_modified_at") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_provided_modified_at must be of type Boolean parameters[\"use_provided_modified_at\"]");
    }

    if (parameters.containsKey("custom_namespace") && !(parameters.get("custom_namespace") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: custom_namespace must be of type Boolean parameters[\"custom_namespace\"]");
    }

    if (parameters.containsKey("non_sso_groups_allowed") && !(parameters.get("non_sso_groups_allowed") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: non_sso_groups_allowed must be of type Boolean parameters[\"non_sso_groups_allowed\"]");
    }

    if (parameters.containsKey("non_sso_users_allowed") && !(parameters.get("non_sso_users_allowed") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: non_sso_users_allowed must be of type Boolean parameters[\"non_sso_users_allowed\"]");
    }

    if (parameters.containsKey("sharing_enabled") && !(parameters.get("sharing_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sharing_enabled must be of type Boolean parameters[\"sharing_enabled\"]");
    }

    if (parameters.containsKey("snapshot_sharing_enabled") && !(parameters.get("snapshot_sharing_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: snapshot_sharing_enabled must be of type Boolean parameters[\"snapshot_sharing_enabled\"]");
    }

    if (parameters.containsKey("user_requests_enabled") && !(parameters.get("user_requests_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: user_requests_enabled must be of type Boolean parameters[\"user_requests_enabled\"]");
    }

    if (parameters.containsKey("user_requests_notify_admins") && !(parameters.get("user_requests_notify_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: user_requests_notify_admins must be of type Boolean parameters[\"user_requests_notify_admins\"]");
    }

    if (parameters.containsKey("dav_enabled") && !(parameters.get("dav_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_enabled must be of type Boolean parameters[\"dav_enabled\"]");
    }

    if (parameters.containsKey("files_com_remote_server_enabled") && !(parameters.get("files_com_remote_server_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: files_com_remote_server_enabled must be of type Boolean parameters[\"files_com_remote_server_enabled\"]");
    }

    if (parameters.containsKey("ftp_enabled") && !(parameters.get("ftp_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_enabled must be of type Boolean parameters[\"ftp_enabled\"]");
    }

    if (parameters.containsKey("s3_compatible_endpoint_enabled") && !(parameters.get("s3_compatible_endpoint_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: s3_compatible_endpoint_enabled must be of type Boolean parameters[\"s3_compatible_endpoint_enabled\"]");
    }

    if (parameters.containsKey("sftp_enabled") && !(parameters.get("sftp_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_enabled must be of type Boolean parameters[\"sftp_enabled\"]");
    }

    if (parameters.containsKey("sftp_finalize_partial_uploads") && !(parameters.get("sftp_finalize_partial_uploads") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_finalize_partial_uploads must be of type Boolean parameters[\"sftp_finalize_partial_uploads\"]");
    }

    if (parameters.containsKey("users_can_create_api_keys") && !(parameters.get("users_can_create_api_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: users_can_create_api_keys must be of type Boolean parameters[\"users_can_create_api_keys\"]");
    }

    if (parameters.containsKey("users_can_create_ssh_keys") && !(parameters.get("users_can_create_ssh_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: users_can_create_ssh_keys must be of type Boolean parameters[\"users_can_create_ssh_keys\"]");
    }

    if (parameters.containsKey("show_user_notifications_log_in_link") && !(parameters.get("show_user_notifications_log_in_link") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: show_user_notifications_log_in_link must be of type Boolean parameters[\"show_user_notifications_log_in_link\"]");
    }

    if (parameters.containsKey("sftp_host_key_type") && !(parameters.get("sftp_host_key_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: sftp_host_key_type must be of type String parameters[\"sftp_host_key_type\"]");
    }

    if (parameters.containsKey("active_sftp_host_key_id") && !(parameters.get("active_sftp_host_key_id") instanceof Long || parameters.get("active_sftp_host_key_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: active_sftp_host_key_id must be of type Long or Integer parameters[\"active_sftp_host_key_id\"]");
    }

    if (parameters.containsKey("active_sftp_host_key_ids") && !(parameters.get("active_sftp_host_key_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: active_sftp_host_key_ids must be of type Long[] parameters[\"active_sftp_host_key_ids\"]");
    }

    if (parameters.containsKey("protocol_access_groups_only") && !(parameters.get("protocol_access_groups_only") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: protocol_access_groups_only must be of type Boolean parameters[\"protocol_access_groups_only\"]");
    }

    if (parameters.containsKey("revoke_bundle_access_on_disable_or_delete") && !(parameters.get("revoke_bundle_access_on_disable_or_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: revoke_bundle_access_on_disable_or_delete must be of type Boolean parameters[\"revoke_bundle_access_on_disable_or_delete\"]");
    }

    if (parameters.containsKey("group_admins_can_add_users") && !(parameters.get("group_admins_can_add_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_add_users must be of type Boolean parameters[\"group_admins_can_add_users\"]");
    }

    if (parameters.containsKey("group_admins_can_manage_group_memberships") && !(parameters.get("group_admins_can_manage_group_memberships") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_manage_group_memberships must be of type Boolean parameters[\"group_admins_can_manage_group_memberships\"]");
    }

    if (parameters.containsKey("group_admins_can_delete_users") && !(parameters.get("group_admins_can_delete_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_delete_users must be of type Boolean parameters[\"group_admins_can_delete_users\"]");
    }

    if (parameters.containsKey("group_admins_can_enable_disable_users") && !(parameters.get("group_admins_can_enable_disable_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_enable_disable_users must be of type Boolean parameters[\"group_admins_can_enable_disable_users\"]");
    }

    if (parameters.containsKey("group_admins_can_modify_users") && !(parameters.get("group_admins_can_modify_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_modify_users must be of type Boolean parameters[\"group_admins_can_modify_users\"]");
    }

    if (parameters.containsKey("group_admins_can_bypass_user_lifecycle_rules") && !(parameters.get("group_admins_can_bypass_user_lifecycle_rules") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_bypass_user_lifecycle_rules must be of type Boolean parameters[\"group_admins_can_bypass_user_lifecycle_rules\"]");
    }

    if (parameters.containsKey("group_admins_can_reset_passwords") && !(parameters.get("group_admins_can_reset_passwords") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_reset_passwords must be of type Boolean parameters[\"group_admins_can_reset_passwords\"]");
    }

    if (parameters.containsKey("group_admins_can_set_user_password") && !(parameters.get("group_admins_can_set_user_password") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_set_user_password must be of type Boolean parameters[\"group_admins_can_set_user_password\"]");
    }

    if (parameters.containsKey("child_site_folder_admins_can_see_parent_site_directory") && !(parameters.get("child_site_folder_admins_can_see_parent_site_directory") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: child_site_folder_admins_can_see_parent_site_directory must be of type Boolean parameters[\"child_site_folder_admins_can_see_parent_site_directory\"]");
    }

    if (parameters.containsKey("workspace_folder_admins_can_see_default_workspace_directory") && !(parameters.get("workspace_folder_admins_can_see_default_workspace_directory") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: workspace_folder_admins_can_see_default_workspace_directory must be of type Boolean parameters[\"workspace_folder_admins_can_see_default_workspace_directory\"]");
    }

    if (parameters.containsKey("bundle_recipient_blacklist_free_email_domains") && !(parameters.get("bundle_recipient_blacklist_free_email_domains") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_recipient_blacklist_free_email_domains must be of type Boolean parameters[\"bundle_recipient_blacklist_free_email_domains\"]");
    }

    if (parameters.containsKey("bundle_recipient_blacklist_domains") && !(parameters.get("bundle_recipient_blacklist_domains") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: bundle_recipient_blacklist_domains must be of type String[] parameters[\"bundle_recipient_blacklist_domains\"]");
    }

    if (parameters.containsKey("admins_bypass_locked_subfolders") && !(parameters.get("admins_bypass_locked_subfolders") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: admins_bypass_locked_subfolders must be of type Boolean parameters[\"admins_bypass_locked_subfolders\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_sms") && !(parameters.get("allowed_2fa_method_sms") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_sms must be of type Boolean parameters[\"allowed_2fa_method_sms\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_totp") && !(parameters.get("allowed_2fa_method_totp") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_totp must be of type Boolean parameters[\"allowed_2fa_method_totp\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_webauthn") && !(parameters.get("allowed_2fa_method_webauthn") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_webauthn must be of type Boolean parameters[\"allowed_2fa_method_webauthn\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_yubi") && !(parameters.get("allowed_2fa_method_yubi") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_yubi must be of type Boolean parameters[\"allowed_2fa_method_yubi\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_email") && !(parameters.get("allowed_2fa_method_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_email must be of type Boolean parameters[\"allowed_2fa_method_email\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_static") && !(parameters.get("allowed_2fa_method_static") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_static must be of type Boolean parameters[\"allowed_2fa_method_static\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_bypass_for_ftp_sftp_dav") && !(parameters.get("allowed_2fa_method_bypass_for_ftp_sftp_dav") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_bypass_for_ftp_sftp_dav must be of type Boolean parameters[\"allowed_2fa_method_bypass_for_ftp_sftp_dav\"]");
    }

    if (parameters.containsKey("require_2fa") && !(parameters.get("require_2fa") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa must be of type Boolean parameters[\"require_2fa\"]");
    }

    if (parameters.containsKey("require_2fa_exempt_all_sso_users") && !(parameters.get("require_2fa_exempt_all_sso_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa_exempt_all_sso_users must be of type Boolean parameters[\"require_2fa_exempt_all_sso_users\"]");
    }

    if (parameters.containsKey("require_2fa_user_type") && !(parameters.get("require_2fa_user_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: require_2fa_user_type must be of type String parameters[\"require_2fa_user_type\"]");
    }

    if (parameters.containsKey("color2_top") && !(parameters.get("color2_top") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: color2_top must be of type String parameters[\"color2_top\"]");
    }

    if (parameters.containsKey("color2_left") && !(parameters.get("color2_left") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: color2_left must be of type String parameters[\"color2_left\"]");
    }

    if (parameters.containsKey("color2_link") && !(parameters.get("color2_link") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: color2_link must be of type String parameters[\"color2_link\"]");
    }

    if (parameters.containsKey("color2_text") && !(parameters.get("color2_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: color2_text must be of type String parameters[\"color2_text\"]");
    }

    if (parameters.containsKey("color2_top_text") && !(parameters.get("color2_top_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: color2_top_text must be of type String parameters[\"color2_top_text\"]");
    }

    if (parameters.containsKey("site_header") && !(parameters.get("site_header") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: site_header must be of type String parameters[\"site_header\"]");
    }

    if (parameters.containsKey("site_footer") && !(parameters.get("site_footer") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: site_footer must be of type String parameters[\"site_footer\"]");
    }

    if (parameters.containsKey("site_public_header") && !(parameters.get("site_public_header") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: site_public_header must be of type String parameters[\"site_public_header\"]");
    }

    if (parameters.containsKey("site_public_footer") && !(parameters.get("site_public_footer") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: site_public_footer must be of type String parameters[\"site_public_footer\"]");
    }

    if (parameters.containsKey("login_help_text") && !(parameters.get("login_help_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: login_help_text must be of type String parameters[\"login_help_text\"]");
    }

    if (parameters.containsKey("use_dedicated_ips_for_smtp") && !(parameters.get("use_dedicated_ips_for_smtp") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_dedicated_ips_for_smtp must be of type Boolean parameters[\"use_dedicated_ips_for_smtp\"]");
    }

    if (parameters.containsKey("email_footer_custom_text") && !(parameters.get("email_footer_custom_text") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: email_footer_custom_text must be of type String parameters[\"email_footer_custom_text\"]");
    }

    if (parameters.containsKey("smtp_address") && !(parameters.get("smtp_address") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_address must be of type String parameters[\"smtp_address\"]");
    }

    if (parameters.containsKey("smtp_authentication") && !(parameters.get("smtp_authentication") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_authentication must be of type String parameters[\"smtp_authentication\"]");
    }

    if (parameters.containsKey("smtp_from") && !(parameters.get("smtp_from") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_from must be of type String parameters[\"smtp_from\"]");
    }

    if (parameters.containsKey("smtp_username") && !(parameters.get("smtp_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_username must be of type String parameters[\"smtp_username\"]");
    }

    if (parameters.containsKey("smtp_port") && !(parameters.get("smtp_port") instanceof Long || parameters.get("smtp_port") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: smtp_port must be of type Long or Integer parameters[\"smtp_port\"]");
    }

    if (parameters.containsKey("smtp_ssl") && !(parameters.get("smtp_ssl") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_ssl must be of type String parameters[\"smtp_ssl\"]");
    }

    if (parameters.containsKey("ldap_enabled") && !(parameters.get("ldap_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ldap_enabled must be of type Boolean parameters[\"ldap_enabled\"]");
    }

    if (parameters.containsKey("ldap_type") && !(parameters.get("ldap_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_type must be of type String parameters[\"ldap_type\"]");
    }

    if (parameters.containsKey("ldap_host") && !(parameters.get("ldap_host") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_host must be of type String parameters[\"ldap_host\"]");
    }

    if (parameters.containsKey("ldap_host_2") && !(parameters.get("ldap_host_2") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_host_2 must be of type String parameters[\"ldap_host_2\"]");
    }

    if (parameters.containsKey("ldap_host_3") && !(parameters.get("ldap_host_3") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_host_3 must be of type String parameters[\"ldap_host_3\"]");
    }

    if (parameters.containsKey("ldap_port") && !(parameters.get("ldap_port") instanceof Long || parameters.get("ldap_port") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: ldap_port must be of type Long or Integer parameters[\"ldap_port\"]");
    }

    if (parameters.containsKey("ldap_secure") && !(parameters.get("ldap_secure") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ldap_secure must be of type Boolean parameters[\"ldap_secure\"]");
    }

    if (parameters.containsKey("ldap_server_certificate") && !(parameters.get("ldap_server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_server_certificate must be of type String parameters[\"ldap_server_certificate\"]");
    }

    if (parameters.containsKey("ldap_username") && !(parameters.get("ldap_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_username must be of type String parameters[\"ldap_username\"]");
    }

    if (parameters.containsKey("ldap_username_field") && !(parameters.get("ldap_username_field") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_username_field must be of type String parameters[\"ldap_username_field\"]");
    }

    if (parameters.containsKey("ldap_domain") && !(parameters.get("ldap_domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_domain must be of type String parameters[\"ldap_domain\"]");
    }

    if (parameters.containsKey("ldap_user_action") && !(parameters.get("ldap_user_action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_user_action must be of type String parameters[\"ldap_user_action\"]");
    }

    if (parameters.containsKey("ldap_group_action") && !(parameters.get("ldap_group_action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_group_action must be of type String parameters[\"ldap_group_action\"]");
    }

    if (parameters.containsKey("ldap_user_include_groups") && !(parameters.get("ldap_user_include_groups") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_user_include_groups must be of type String parameters[\"ldap_user_include_groups\"]");
    }

    if (parameters.containsKey("ldap_group_exclusion") && !(parameters.get("ldap_group_exclusion") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_group_exclusion must be of type String parameters[\"ldap_group_exclusion\"]");
    }

    if (parameters.containsKey("ldap_group_inclusion") && !(parameters.get("ldap_group_inclusion") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_group_inclusion must be of type String parameters[\"ldap_group_inclusion\"]");
    }

    if (parameters.containsKey("ldap_base_dn") && !(parameters.get("ldap_base_dn") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_base_dn must be of type String parameters[\"ldap_base_dn\"]");
    }

    if (parameters.containsKey("uploads_via_email_authentication") && !(parameters.get("uploads_via_email_authentication") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: uploads_via_email_authentication must be of type Boolean parameters[\"uploads_via_email_authentication\"]");
    }

    if (parameters.containsKey("bundle_watermark_value") && !(parameters.get("bundle_watermark_value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: bundle_watermark_value must be of type Object parameters[\"bundle_watermark_value\"]");
    }

    if (parameters.containsKey("icon16_file") && !(parameters.get("icon16_file") instanceof byte[] || parameters.get("icon16_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: icon16_file must be of type byte[] or Attachment parameters[\"icon16_file\"]");
    }

    if (parameters.containsKey("icon16_delete") && !(parameters.get("icon16_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon16_delete must be of type Boolean parameters[\"icon16_delete\"]");
    }

    if (parameters.containsKey("icon32_file") && !(parameters.get("icon32_file") instanceof byte[] || parameters.get("icon32_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: icon32_file must be of type byte[] or Attachment parameters[\"icon32_file\"]");
    }

    if (parameters.containsKey("icon32_delete") && !(parameters.get("icon32_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon32_delete must be of type Boolean parameters[\"icon32_delete\"]");
    }

    if (parameters.containsKey("icon48_file") && !(parameters.get("icon48_file") instanceof byte[] || parameters.get("icon48_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: icon48_file must be of type byte[] or Attachment parameters[\"icon48_file\"]");
    }

    if (parameters.containsKey("icon48_delete") && !(parameters.get("icon48_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon48_delete must be of type Boolean parameters[\"icon48_delete\"]");
    }

    if (parameters.containsKey("icon128_file") && !(parameters.get("icon128_file") instanceof byte[] || parameters.get("icon128_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: icon128_file must be of type byte[] or Attachment parameters[\"icon128_file\"]");
    }

    if (parameters.containsKey("icon128_delete") && !(parameters.get("icon128_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon128_delete must be of type Boolean parameters[\"icon128_delete\"]");
    }

    if (parameters.containsKey("logo_file") && !(parameters.get("logo_file") instanceof byte[] || parameters.get("logo_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: logo_file must be of type byte[] or Attachment parameters[\"logo_file\"]");
    }

    if (parameters.containsKey("logo_delete") && !(parameters.get("logo_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: logo_delete must be of type Boolean parameters[\"logo_delete\"]");
    }

    if (parameters.containsKey("bundle_watermark_attachment_file") && !(parameters.get("bundle_watermark_attachment_file") instanceof byte[] || parameters.get("bundle_watermark_attachment_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: bundle_watermark_attachment_file must be of type byte[] or Attachment parameters[\"bundle_watermark_attachment_file\"]");
    }

    if (parameters.containsKey("bundle_watermark_attachment_delete") && !(parameters.get("bundle_watermark_attachment_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_watermark_attachment_delete must be of type Boolean parameters[\"bundle_watermark_attachment_delete\"]");
    }

    if (parameters.containsKey("login_page_background_image_file") && !(parameters.get("login_page_background_image_file") instanceof byte[] || parameters.get("login_page_background_image_file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: login_page_background_image_file must be of type byte[] or Attachment parameters[\"login_page_background_image_file\"]");
    }

    if (parameters.containsKey("login_page_background_image_delete") && !(parameters.get("login_page_background_image_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: login_page_background_image_delete must be of type Boolean parameters[\"login_page_background_image_delete\"]");
    }

    if (parameters.containsKey("disable_2fa_with_delay") && !(parameters.get("disable_2fa_with_delay") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_2fa_with_delay must be of type Boolean parameters[\"disable_2fa_with_delay\"]");
    }

    if (parameters.containsKey("ldap_password_change") && !(parameters.get("ldap_password_change") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_password_change must be of type String parameters[\"ldap_password_change\"]");
    }

    if (parameters.containsKey("ldap_password_change_confirmation") && !(parameters.get("ldap_password_change_confirmation") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ldap_password_change_confirmation must be of type String parameters[\"ldap_password_change_confirmation\"]");
    }

    if (parameters.containsKey("redirect_old_subdomain") && !(parameters.get("redirect_old_subdomain") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: redirect_old_subdomain must be of type Boolean parameters[\"redirect_old_subdomain\"]");
    }

    if (parameters.containsKey("smtp_password") && !(parameters.get("smtp_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_password must be of type String parameters[\"smtp_password\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("icon16_file") instanceof byte[]) {
      parameters.put("icon16_file", Attachment.fromBytes((byte[]) parameters.get("icon16_file")));
    }
    if (parameters.get("icon32_file") instanceof byte[]) {
      parameters.put("icon32_file", Attachment.fromBytes((byte[]) parameters.get("icon32_file")));
    }
    if (parameters.get("icon48_file") instanceof byte[]) {
      parameters.put("icon48_file", Attachment.fromBytes((byte[]) parameters.get("icon48_file")));
    }
    if (parameters.get("icon128_file") instanceof byte[]) {
      parameters.put("icon128_file", Attachment.fromBytes((byte[]) parameters.get("icon128_file")));
    }
    if (parameters.get("logo_file") instanceof byte[]) {
      parameters.put("logo_file", Attachment.fromBytes((byte[]) parameters.get("logo_file")));
    }
    if (parameters.get("bundle_watermark_attachment_file") instanceof byte[]) {
      parameters.put("bundle_watermark_attachment_file", Attachment.fromBytes((byte[]) parameters.get("bundle_watermark_attachment_file")));
    }
    if (parameters.get("login_page_background_image_file") instanceof byte[]) {
      parameters.put("login_page_background_image_file", Attachment.fromBytes((byte[]) parameters.get("login_page_background_image_file")));
    }

    String apiPath = "/site";
    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }
}
