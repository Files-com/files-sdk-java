package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Site implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every Site, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Site() {
    this(null, null);
  }

  public Site(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Site(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Site Id
   */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
   * Site name
   */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  /**
   * Additional extensions that are considered text files
   */
  @JsonProperty("additional_text_file_types")
  public String[] additionalTextFileTypes;

  public String[] getAdditionalTextFileTypes() {
    return additionalTextFileTypes;
  }

  /**
   * Availability settings for AI features. Each feature requires the site_admins, workspace_admins, folder_admins, and all_users keys. Optional selected_group_members defaults to false; when true, members of at least one group in group_ids get access regardless of the other options. All availability options are additive: any enabled option matching the user grants access. Optional group_ids is an array of integer IDs of active groups on this site, from any workspace. Omitted or empty group_ids grants no access through selected_group_members and does not affect other options. Disabling all AI features overrides these settings.
   */
  @JsonProperty("ai_feature_availability")
  public Object aiFeatureAvailability;

  public Object getAiFeatureAvailability() {
    return aiFeatureAvailability;
  }

  /**
   * Is SMS two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_sms")
  public Boolean allowed2faMethodSms;

  public Boolean getAllowed2faMethodSms() {
    return allowed2faMethodSms;
  }

  /**
   * Is TOTP two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_totp")
  public Boolean allowed2faMethodTotp;

  public Boolean getAllowed2faMethodTotp() {
    return allowed2faMethodTotp;
  }

  /**
   * Is WebAuthn two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_webauthn")
  public Boolean allowed2faMethodWebauthn;

  public Boolean getAllowed2faMethodWebauthn() {
    return allowed2faMethodWebauthn;
  }

  /**
   * Is yubikey two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_yubi")
  public Boolean allowed2faMethodYubi;

  public Boolean getAllowed2faMethodYubi() {
    return allowed2faMethodYubi;
  }

  /**
   * Is OTP via email two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_email")
  public Boolean allowed2faMethodEmail;

  public Boolean getAllowed2faMethodEmail() {
    return allowed2faMethodEmail;
  }

  /**
   * Is OTP via static codes for two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_static")
  public Boolean allowed2faMethodStatic;

  public Boolean getAllowed2faMethodStatic() {
    return allowed2faMethodStatic;
  }

  /**
   * Are users allowed to configure their two factor authentication to be bypassed for FTP/SFTP/WebDAV?
   */
  @JsonProperty("allowed_2fa_method_bypass_for_ftp_sftp_dav")
  public Boolean allowed2faMethodBypassForFtpSftpDav;

  public Boolean getAllowed2faMethodBypassForFtpSftpDav() {
    return allowed2faMethodBypassForFtpSftpDav;
  }

  /**
   * User ID for the main site administrator
   */
  @JsonProperty("admin_user_id")
  public Long adminUserId;

  public Long getAdminUserId() {
    return adminUserId;
  }

  /**
   * Allow admins to bypass the locked subfolders setting.
   */
  @JsonProperty("admins_bypass_locked_subfolders")
  public Boolean adminsBypassLockedSubfolders;

  public Boolean getAdminsBypassLockedSubfolders() {
    return adminsBypassLockedSubfolders;
  }

  /**
   * Are manual Bundle names allowed?
   */
  @JsonProperty("allow_bundle_names")
  public Boolean allowBundleNames;

  public Boolean getAllowBundleNames() {
    return allowBundleNames;
  }

  /**
   * Allow the site-wide two-factor authentication requirement to be overriden on a per-user-basis?
   */
  @JsonProperty("allow_user_level_2fa_override")
  public Boolean allowUserLevel2faOverride;

  public Boolean getAllowUserLevel2faOverride() {
    return allowUserLevel2faOverride;
  }

  /**
   * Allow the site-wide allowed IP restriction to be overriden on a per-user-basis?
   */
  @JsonProperty("allow_user_level_allowed_ip_override")
  public Boolean allowUserLevelAllowedIpOverride;

  public Boolean getAllowUserLevelAllowedIpOverride() {
    return allowUserLevelAllowedIpOverride;
  }

  /**
   * Allow the site-wide FTP SSL requirement to be overriden on a per-user-basis?
   */
  @JsonProperty("allow_user_level_ssl_override")
  public Boolean allowUserLevelSslOverride;

  public Boolean getAllowUserLevelSslOverride() {
    return allowUserLevelSslOverride;
  }

  /**
   * Comma separated list of allowed Country codes
   */
  @JsonProperty("allowed_countries")
  public String allowedCountries;

  public String getAllowedCountries() {
    return allowedCountries;
  }

  /**
   * List of allowed IP addresses
   */
  @JsonProperty("allowed_ips")
  public String allowedIps;

  public String getAllowedIps() {
    return allowedIps;
  }

  /**
   * Create parent directories if they do not exist during uploads?  This is primarily used to work around broken upload clients that assume servers will perform this step.
   */
  @JsonProperty("always_mkdir_parents")
  public Boolean alwaysMkdirParents;

  public Boolean getAlwaysMkdirParents() {
    return alwaysMkdirParents;
  }

  /**
   * Number of days to retain AS2 messages (incoming and outgoing).
   */
  @JsonProperty("as2_message_retention_days")
  public Long as2MessageRetentionDays;

  public Long getAs2MessageRetentionDays() {
    return as2MessageRetentionDays;
  }

  /**
   * If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
   */
  @JsonProperty("ask_about_overwrites")
  public Boolean askAboutOverwrites;

  public Boolean getAskAboutOverwrites() {
    return askAboutOverwrites;
  }

  /**
   * Do Bundle owners receive activity notifications?
   */
  @JsonProperty("bundle_activity_notifications")
  public String bundleActivityNotifications;

  public String getBundleActivityNotifications() {
    return bundleActivityNotifications;
  }

  /**
   * Site-wide Bundle expiration in days
   */
  @JsonProperty("bundle_expiration")
  public Long bundleExpiration;

  public Long getBundleExpiration() {
    return bundleExpiration;
  }

  /**
   * Custom error message to show when bundle is not found.
   */
  @JsonProperty("bundle_not_found_message")
  public String bundleNotFoundMessage;

  public String getBundleNotFoundMessage() {
    return bundleNotFoundMessage;
  }

  /**
   * Do Bundles require password protection?
   */
  @JsonProperty("bundle_password_required")
  public Boolean bundlePasswordRequired;

  public Boolean getBundlePasswordRequired() {
    return bundlePasswordRequired;
  }

  /**
   * If true, new Share Links created by a user with a primary group will default to that group as owner.
   */
  @JsonProperty("bundles_default_owned_by_primary_group")
  public Boolean bundlesDefaultOwnedByPrimaryGroup;

  public Boolean getBundlesDefaultOwnedByPrimaryGroup() {
    return bundlesDefaultOwnedByPrimaryGroup;
  }

  /**
   * List of email domains to disallow when entering a Bundle/Inbox recipients
   */
  @JsonProperty("bundle_recipient_blacklist_domains")
  public String[] bundleRecipientBlacklistDomains;

  public String[] getBundleRecipientBlacklistDomains() {
    return bundleRecipientBlacklistDomains;
  }

  /**
   * Disallow free email domains for Bundle/Inbox recipients?
   */
  @JsonProperty("bundle_recipient_blacklist_free_email_domains")
  public Boolean bundleRecipientBlacklistFreeEmailDomains;

  public Boolean getBundleRecipientBlacklistFreeEmailDomains() {
    return bundleRecipientBlacklistFreeEmailDomains;
  }

  /**
   * Do Bundle owners receive registration notification?
   */
  @JsonProperty("bundle_registration_notifications")
  public String bundleRegistrationNotifications;

  public String getBundleRegistrationNotifications() {
    return bundleRegistrationNotifications;
  }

  /**
   * Do Bundles require registration?
   */
  @JsonProperty("bundle_require_registration")
  public Boolean bundleRequireRegistration;

  public Boolean getBundleRequireRegistration() {
    return bundleRequireRegistration;
  }

  /**
   * Do Bundles require recipients for sharing?
   */
  @JsonProperty("bundle_require_share_recipient")
  public Boolean bundleRequireShareRecipient;

  public Boolean getBundleRequireShareRecipient() {
    return bundleRequireShareRecipient;
  }

  /**
   * If true, new Share Links must send a one-time password to the recipient when they register. Requires bundle_require_share_recipient and cannot be enabled with bundle_password_required.
   */
  @JsonProperty("bundle_send_one_time_password_to_recipient_at_registration")
  public Boolean bundleSendOneTimePasswordToRecipientAtRegistration;

  public Boolean getBundleSendOneTimePasswordToRecipientAtRegistration() {
    return bundleSendOneTimePasswordToRecipientAtRegistration;
  }

  /**
   * Do Bundles require internal notes?
   */
  @JsonProperty("bundle_require_note")
  public Boolean bundleRequireNote;

  public Boolean getBundleRequireNote() {
    return bundleRequireNote;
  }

  /**
   * Do Bundle creators receive receipts of invitations?
   */
  @JsonProperty("bundle_send_shared_receipts")
  public Boolean bundleSendSharedReceipts;

  public Boolean getBundleSendSharedReceipts() {
    return bundleSendSharedReceipts;
  }

  /**
   * Do Bundle uploaders receive upload confirmation notifications?
   */
  @JsonProperty("bundle_upload_receipt_notifications")
  public String bundleUploadReceiptNotifications;

  public String getBundleUploadReceiptNotifications() {
    return bundleUploadReceiptNotifications;
  }

  /**
   * Preview watermark image applied to all bundle items.
   */
  @JsonProperty("bundle_watermark_attachment")
  public Image bundleWatermarkAttachment;

  public Image getBundleWatermarkAttachment() {
    return bundleWatermarkAttachment;
  }

  /**
   * Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   */
  @JsonProperty("bundle_watermark_value")
  public Object bundleWatermarkValue;

  public Object getBundleWatermarkValue() {
    return bundleWatermarkValue;
  }

  /**
   * Calculate CRC32 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_crc32")
  public Boolean calculateFileChecksumsCrc32;

  public Boolean getCalculateFileChecksumsCrc32() {
    return calculateFileChecksumsCrc32;
  }

  /**
   * Calculate MD5 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_md5")
  public Boolean calculateFileChecksumsMd5;

  public Boolean getCalculateFileChecksumsMd5() {
    return calculateFileChecksumsMd5;
  }

  /**
   * Calculate SHA1 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_sha1")
  public Boolean calculateFileChecksumsSha1;

  public Boolean getCalculateFileChecksumsSha1() {
    return calculateFileChecksumsSha1;
  }

  /**
   * Calculate SHA256 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_sha256")
  public Boolean calculateFileChecksumsSha256;

  public Boolean getCalculateFileChecksumsSha256() {
    return calculateFileChecksumsSha256;
  }

  /**
   * If true, administrators on this site's Child Sites, including Folder Admins, can list this site's Default Workspace users and groups and grant them folder permissions and notifications on Child Site paths.
   */
  @JsonProperty("child_site_folder_admins_can_see_parent_site_directory")
  public Boolean childSiteFolderAdminsCanSeeParentSiteDirectory;

  public Boolean getChildSiteFolderAdminsCanSeeParentSiteDirectory() {
    return childSiteFolderAdminsCanSeeParentSiteDirectory;
  }

  /**
   * If true, Workspace Administrators and Folder Admins in a Custom Workspace can list the Default Workspace users and groups that have permissions in that Workspace and choose them as Email Notification recipients.
   */
  @JsonProperty("workspace_folder_admins_can_see_default_workspace_directory")
  public Boolean workspaceFolderAdminsCanSeeDefaultWorkspaceDirectory;

  public Boolean getWorkspaceFolderAdminsCanSeeDefaultWorkspaceDirectory() {
    return workspaceFolderAdminsCanSeeDefaultWorkspaceDirectory;
  }

  /**
   * Require email authentication, virus, and spam checks for incoming emails to Inboxes and Incoming Email Automations in every Workspace on this site?
   */
  @JsonProperty("uploads_via_email_authentication")
  public Boolean uploadsViaEmailAuthentication;

  public Boolean getUploadsViaEmailAuthentication() {
    return uploadsViaEmailAuthentication;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color2_left")
  public String color2Left;

  public String getColor2Left() {
    return color2Left;
  }

  /**
   * Top bar link color
   */
  @JsonProperty("color2_link")
  public String color2Link;

  public String getColor2Link() {
    return color2Link;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color2_text")
  public String color2Text;

  public String getColor2Text() {
    return color2Text;
  }

  /**
   * Top bar background color
   */
  @JsonProperty("color2_top")
  public String color2Top;

  public String getColor2Top() {
    return color2Top;
  }

  /**
   * Top bar text color
   */
  @JsonProperty("color2_top_text")
  public String color2TopText;

  public String getColor2TopText() {
    return color2TopText;
  }

  /**
   * Site main contact name
   */
  @JsonProperty("contact_name")
  public String contactName;

  public String getContactName() {
    return contactName;
  }

  /**
   * Time this site was created
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Preferred currency
   */
  @JsonProperty("currency")
  public String currency;

  public String getCurrency() {
    return currency;
  }

  /**
   * Is this site using a custom namespace for users?
   */
  @JsonProperty("custom_namespace")
  public Boolean customNamespace;

  public Boolean getCustomNamespace() {
    return customNamespace;
  }

  /**
   * Is WebDAV enabled?
   */
  @JsonProperty("dav_enabled")
  public Boolean davEnabled;

  public Boolean getDavEnabled() {
    return davEnabled;
  }

  /**
   * Use user FTP roots also for WebDAV?
   */
  @JsonProperty("dav_user_root_enabled")
  public Boolean davUserRootEnabled;

  public Boolean getDavUserRootEnabled() {
    return davUserRootEnabled;
  }

  /**
   * Number of days to keep deleted files
   */
  @JsonProperty("days_to_retain_backups")
  public Long daysToRetainBackups;

  public Long getDaysToRetainBackups() {
    return daysToRetainBackups;
  }

  /**
   * If true, allow public viewers of Bundles with full permissions to use document editing integrations.
   */
  @JsonProperty("document_edits_in_bundle_allowed")
  public Boolean documentEditsInBundleAllowed;

  public Boolean getDocumentEditsInBundleAllowed() {
    return documentEditsInBundleAllowed;
  }

  /**
   * Site default time zone
   */
  @JsonProperty("default_time_zone")
  public String defaultTimeZone;

  public String getDefaultTimeZone() {
    return defaultTimeZone;
  }

  /**
   * Is the desktop app enabled?
   */
  @JsonProperty("desktop_app")
  public Boolean desktopApp;

  public Boolean getDesktopApp() {
    return desktopApp;
  }

  /**
   * Is desktop app session IP pinning enabled?
   */
  @JsonProperty("desktop_app_session_ip_pinning")
  public Boolean desktopAppSessionIpPinning;

  public Boolean getDesktopAppSessionIpPinning() {
    return desktopAppSessionIpPinning;
  }

  /**
   * Desktop app session lifetime (in hours)
   */
  @JsonProperty("desktop_app_session_lifetime")
  public Long desktopAppSessionLifetime;

  public Long getDesktopAppSessionLifetime() {
    return desktopAppSessionLifetime;
  }

  /**
   * Use legacy checksums mode?
   */
  @JsonProperty("legacy_checksums_mode")
  public Boolean legacyChecksumsMode;

  public Boolean getLegacyChecksumsMode() {
    return legacyChecksumsMode;
  }

  /**
   * If true, we will migrate all remote server syncs to the new Sync model.
   */
  @JsonProperty("migrate_remote_server_sync_to_sync")
  public Boolean migrateRemoteServerSyncToSync;

  public Boolean getMigrateRemoteServerSyncToSync() {
    return migrateRemoteServerSyncToSync;
  }

  /**
   * Is OAuth DCR (dynamic client registration) for MCP enabled?
   */
  @JsonProperty("mcp_dcr_enabled")
  public Boolean mcpDcrEnabled;

  public Boolean getMcpDcrEnabled() {
    return mcpDcrEnabled;
  }

  /**
   * Is the mobile app enabled?
   */
  @JsonProperty("mobile_app")
  public Boolean mobileApp;

  public Boolean getMobileApp() {
    return mobileApp;
  }

  /**
   * Is mobile app session IP pinning enabled?
   */
  @JsonProperty("mobile_app_session_ip_pinning")
  public Boolean mobileAppSessionIpPinning;

  public Boolean getMobileAppSessionIpPinning() {
    return mobileAppSessionIpPinning;
  }

  /**
   * Mobile app session lifetime (in hours)
   */
  @JsonProperty("mobile_app_session_lifetime")
  public Long mobileAppSessionLifetime;

  public Long getMobileAppSessionLifetime() {
    return mobileAppSessionLifetime;
  }

  /**
   * Comma separated list of disallowed Country codes
   */
  @JsonProperty("disallowed_countries")
  public String disallowedCountries;

  public String getDisallowedCountries() {
    return disallowedCountries;
  }

  /**
   * If true, all AI features are disabled for this site.
   */
  @JsonProperty("disable_all_ai_features")
  public Boolean disableAllAiFeatures;

  public Boolean getDisableAllAiFeatures() {
    return disableAllAiFeatures;
  }

  /**
   * If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.
   */
  @JsonProperty("disable_files_certificate_generation")
  public Boolean disableFilesCertificateGeneration;

  public Boolean getDisableFilesCertificateGeneration() {
    return disableFilesCertificateGeneration;
  }

  /**
   * Are notifications disabled?
   */
  @JsonProperty("disable_notifications")
  public Boolean disableNotifications;

  public Boolean getDisableNotifications() {
    return disableNotifications;
  }

  /**
   * If true, file operations produce file_action.activity.v1 EventChannel events in all workspaces. Defaults to false; subscription filters and workspace access still control delivery.
   */
  @JsonProperty("file_activity_events_enabled")
  public Boolean fileActivityEventsEnabled;

  public Boolean getFileActivityEventsEnabled() {
    return fileActivityEventsEnabled;
  }

  /**
   * Is password reset disabled?
   */
  @JsonProperty("disable_password_reset")
  public Boolean disablePasswordReset;

  public Boolean getDisablePasswordReset() {
    return disablePasswordReset;
  }

  /**
   * Custom domain
   */
  @JsonProperty("domain")
  public String domain;

  public String getDomain() {
    return domain;
  }

  /**
   * Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?
   */
  @JsonProperty("domain_hsts_header")
  public Boolean domainHstsHeader;

  public Boolean getDomainHstsHeader() {
    return domainHstsHeader;
  }

  /**
   * Letsencrypt chain to use when registering SSL Certificate for domain. No longer used as of 2026.
   */
  @JsonProperty("domain_letsencrypt_chain")
  public String domainLetsencryptChain;

  public String getDomainLetsencryptChain() {
    return domainLetsencryptChain;
  }

  /**
   * Main email for this site
   */
  @JsonProperty("email")
  public String email;

  public String getEmail() {
    return email;
  }

  /**
   * Are FedRAMP security restrictions enabled for this site?
   */
  @JsonProperty("fedramp")
  public Boolean fedramp;

  public Boolean getFedramp() {
    return fedramp;
  }

  /**
   * Allow other Files.com sites to use this site's API keys for native Files.com Remote Server connections? Defaults to true and applies to all keys and workspaces on this site. When false, new pairings and access through existing connections are rejected without revoking keys or deleting connections. Re-enabling permits access again with usable keys. Does not disable this site's connections to other sites, ordinary API access, or Connected Sites.
   */
  @JsonProperty("files_com_remote_server_enabled")
  public Boolean filesComRemoteServerEnabled;

  public Boolean getFilesComRemoteServerEnabled() {
    return filesComRemoteServerEnabled;
  }

  /**
   * Is FTP enabled?
   */
  @JsonProperty("ftp_enabled")
  public Boolean ftpEnabled;

  public Boolean getFtpEnabled() {
    return ftpEnabled;
  }

  /**
   * Reply-to email for this site
   */
  @JsonProperty("reply_to_email")
  public String replyToEmail;

  public String getReplyToEmail() {
    return replyToEmail;
  }

  /**
   * If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
   */
  @JsonProperty("non_sso_groups_allowed")
  public Boolean nonSsoGroupsAllowed;

  public Boolean getNonSsoGroupsAllowed() {
    return nonSsoGroupsAllowed;
  }

  /**
   * If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
   */
  @JsonProperty("non_sso_users_allowed")
  public Boolean nonSsoUsersAllowed;

  public Boolean getNonSsoUsersAllowed() {
    return nonSsoUsersAllowed;
  }

  /**
   * If true, permissions for this site must be bound to a group (not a user).
   */
  @JsonProperty("folder_permissions_groups_only")
  public Boolean folderPermissionsGroupsOnly;

  public Boolean getFolderPermissionsGroupsOnly() {
    return folderPermissionsGroupsOnly;
  }

  /**
   * Allow group admins to create users in their groups
   */
  @JsonProperty("group_admins_can_add_users")
  public Boolean groupAdminsCanAddUsers;

  public Boolean getGroupAdminsCanAddUsers() {
    return groupAdminsCanAddUsers;
  }

  /**
   * Allow group admins to add or remove existing users in their groups
   */
  @JsonProperty("group_admins_can_manage_group_memberships")
  public Boolean groupAdminsCanManageGroupMemberships;

  public Boolean getGroupAdminsCanManageGroupMemberships() {
    return groupAdminsCanManageGroupMemberships;
  }

  /**
   * Allow group admins to delete users in their groups
   */
  @JsonProperty("group_admins_can_delete_users")
  public Boolean groupAdminsCanDeleteUsers;

  public Boolean getGroupAdminsCanDeleteUsers() {
    return groupAdminsCanDeleteUsers;
  }

  /**
   * Allow group admins to enable or disable users in their groups
   */
  @JsonProperty("group_admins_can_enable_disable_users")
  public Boolean groupAdminsCanEnableDisableUsers;

  public Boolean getGroupAdminsCanEnableDisableUsers() {
    return groupAdminsCanEnableDisableUsers;
  }

  /**
   * Allow group admins to modify users in their groups
   */
  @JsonProperty("group_admins_can_modify_users")
  public Boolean groupAdminsCanModifyUsers;

  public Boolean getGroupAdminsCanModifyUsers() {
    return groupAdminsCanModifyUsers;
  }

  /**
   * Allow group admins to exempt users in their groups from lifecycle rules
   */
  @JsonProperty("group_admins_can_bypass_user_lifecycle_rules")
  public Boolean groupAdminsCanBypassUserLifecycleRules;

  public Boolean getGroupAdminsCanBypassUserLifecycleRules() {
    return groupAdminsCanBypassUserLifecycleRules;
  }

  /**
   * Allow group admins to reset passwords for users in their groups
   */
  @JsonProperty("group_admins_can_reset_passwords")
  public Boolean groupAdminsCanResetPasswords;

  public Boolean getGroupAdminsCanResetPasswords() {
    return groupAdminsCanResetPasswords;
  }

  /**
   * Allow group admins to set password authentication method
   */
  @JsonProperty("group_admins_can_set_user_password")
  public Boolean groupAdminsCanSetUserPassword;

  public Boolean getGroupAdminsCanSetUserPassword() {
    return groupAdminsCanSetUserPassword;
  }

  /**
   * Is there a signed HIPAA BAA between Files.com and this site?
   */
  @JsonProperty("hipaa")
  public Boolean hipaa;

  public Boolean getHipaa() {
    return hipaa;
  }

  /**
   * Branded icon 128x128
   */
  @JsonProperty("icon128")
  public Image icon128;

  public Image getIcon128() {
    return icon128;
  }

  /**
   * Branded icon 16x16
   */
  @JsonProperty("icon16")
  public Image icon16;

  public Image getIcon16() {
    return icon16;
  }

  /**
   * Branded icon 32x32
   */
  @JsonProperty("icon32")
  public Image icon32;

  public Image getIcon32() {
    return icon32;
  }

  /**
   * Branded icon 48x48
   */
  @JsonProperty("icon48")
  public Image icon48;

  public Image getIcon48() {
    return icon48;
  }

  /**
   * Can files be modified?
   */
  @JsonProperty("immutable_files_set_at")
  public Date immutableFilesSetAt;

  public Date getImmutableFilesSetAt() {
    return immutableFilesSetAt;
  }

  /**
   * Include password in emails to new users?
   */
  @JsonProperty("include_password_in_welcome_email")
  public Boolean includePasswordInWelcomeEmail;

  public Boolean getIncludePasswordInWelcomeEmail() {
    return includePasswordInWelcomeEmail;
  }

  /**
   * Site default language
   */
  @JsonProperty("language")
  public String language;

  public String getLanguage() {
    return language;
  }

  /**
   * Base DN for looking up users in LDAP server
   */
  @JsonProperty("ldap_base_dn")
  public String ldapBaseDn;

  public String getLdapBaseDn() {
    return ldapBaseDn;
  }

  /**
   * Domain name that will be appended to usernames
   */
  @JsonProperty("ldap_domain")
  public String ldapDomain;

  public String getLdapDomain() {
    return ldapDomain;
  }

  /**
   * Main LDAP setting: is LDAP enabled?
   */
  @JsonProperty("ldap_enabled")
  public Boolean ldapEnabled;

  public Boolean getLdapEnabled() {
    return ldapEnabled;
  }

  /**
   * Should we sync groups from LDAP server?
   */
  @JsonProperty("ldap_group_action")
  public String ldapGroupAction;

  public String getLdapGroupAction() {
    return ldapGroupAction;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
   */
  @JsonProperty("ldap_group_exclusion")
  public String ldapGroupExclusion;

  public String getLdapGroupExclusion() {
    return ldapGroupExclusion;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) to include when syncing.
   */
  @JsonProperty("ldap_group_inclusion")
  public String ldapGroupInclusion;

  public String getLdapGroupInclusion() {
    return ldapGroupInclusion;
  }

  /**
   * LDAP host
   */
  @JsonProperty("ldap_host")
  public String ldapHost;

  public String getLdapHost() {
    return ldapHost;
  }

  /**
   * LDAP backup host
   */
  @JsonProperty("ldap_host_2")
  public String ldapHost2;

  public String getLdapHost2() {
    return ldapHost2;
  }

  /**
   * LDAP backup host
   */
  @JsonProperty("ldap_host_3")
  public String ldapHost3;

  public String getLdapHost3() {
    return ldapHost3;
  }

  /**
   * LDAP port
   */
  @JsonProperty("ldap_port")
  public Long ldapPort;

  public Long getLdapPort() {
    return ldapPort;
  }

  /**
   * Use secure LDAP?
   */
  @JsonProperty("ldap_secure")
  public Boolean ldapSecure;

  public Boolean getLdapSecure() {
    return ldapSecure;
  }

  /**
   * How to validate the LDAP server certificate. `require_match` validates the certificate chain and hostname; `allow_any` disables certificate validation.
   */
  @JsonProperty("ldap_server_certificate")
  public String ldapServerCertificate;

  public String getLdapServerCertificate() {
    return ldapServerCertificate;
  }

  /**
   * LDAP type
   */
  @JsonProperty("ldap_type")
  public String ldapType;

  public String getLdapType() {
    return ldapType;
  }

  /**
   * Should we sync users from LDAP server?
   */
  @JsonProperty("ldap_user_action")
  public String ldapUserAction;

  public String getLdapUserAction() {
    return ldapUserAction;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
   */
  @JsonProperty("ldap_user_include_groups")
  public String ldapUserIncludeGroups;

  public String getLdapUserIncludeGroups() {
    return ldapUserIncludeGroups;
  }

  /**
   * Username for signing in to LDAP server.
   */
  @JsonProperty("ldap_username")
  public String ldapUsername;

  public String getLdapUsername() {
    return ldapUsername;
  }

  /**
   * LDAP username field
   */
  @JsonProperty("ldap_username_field")
  public String ldapUsernameField;

  public String getLdapUsernameField() {
    return ldapUsernameField;
  }

  /**
   * Login help text
   */
  @JsonProperty("login_help_text")
  public String loginHelpText;

  public String getLoginHelpText() {
    return loginHelpText;
  }

  /**
   * Branded logo
   */
  @JsonProperty("logo")
  public Image logo;

  public Image getLogo() {
    return logo;
  }

  /**
   * Branded login page background
   */
  @JsonProperty("login_page_background_image")
  public Image loginPageBackgroundImage;

  public Image getLoginPageBackgroundImage() {
    return loginPageBackgroundImage;
  }

  /**
   * Number of prior passwords to disallow
   */
  @JsonProperty("max_prior_passwords")
  public Long maxPriorPasswords;

  public Long getMaxPriorPasswords() {
    return maxPriorPasswords;
  }

  /**
   * Site settings managed by the parent site. Each key identifies a managed setting, with a null value. Non-secret effective values are returned in their own Site settings fields.
   */
  @JsonProperty("managed_site_settings")
  public Object managedSiteSettings;

  public Object getManagedSiteSettings() {
    return managedSiteSettings;
  }

  /**
   * A message to show users when they connect via FTP or SFTP.
   */
  @JsonProperty("motd_text")
  public String motdText;

  public String getMotdText() {
    return motdText;
  }

  /**
   * Show message to users connecting via FTP
   */
  @JsonProperty("motd_use_for_ftp")
  public Boolean motdUseForFtp;

  public Boolean getMotdUseForFtp() {
    return motdUseForFtp;
  }

  /**
   * Show message to users connecting via SFTP
   */
  @JsonProperty("motd_use_for_sftp")
  public Boolean motdUseForSftp;

  public Boolean getMotdUseForSftp() {
    return motdUseForSftp;
  }

  /**
   * Next billing amount
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("next_billing_amount")
  public BigDecimal nextBillingAmount;

  public BigDecimal getNextBillingAmount() {
    return nextBillingAmount;
  }

  /**
   * Next billing date
   */
  @JsonProperty("next_billing_date")
  public String nextBillingDate;

  public String getNextBillingDate() {
    return nextBillingDate;
  }

  /**
   * If true, allows users to use a document editing integration.
   */
  @JsonProperty("office_integration_available")
  public Boolean officeIntegrationAvailable;

  public Boolean getOfficeIntegrationAvailable() {
    return officeIntegrationAvailable;
  }

  /**
   * Which document editing integration to support. Files.com Editor or Microsoft Office for the Web.
   */
  @JsonProperty("office_integration_type")
  public String officeIntegrationType;

  public String getOfficeIntegrationType() {
    return officeIntegrationType;
  }

  /**
   * Link to scheduling a meeting with our Sales team
   */
  @JsonProperty("oncehub_link")
  public String oncehubLink;

  public String getOncehubLink() {
    return oncehubLink;
  }

  /**
   * Use servers in the USA only?
   */
  @JsonProperty("opt_out_global")
  public Boolean optOutGlobal;

  public Boolean getOptOutGlobal() {
    return optOutGlobal;
  }

  /**
   * Is this site's billing overdue?
   */
  @JsonProperty("overdue")
  public Boolean overdue;

  public Boolean getOverdue() {
    return overdue;
  }

  /**
   * Shortest password length for users
   */
  @JsonProperty("password_min_length")
  public Long passwordMinLength;

  public Long getPasswordMinLength() {
    return passwordMinLength;
  }

  /**
   * Require a letter in passwords?
   */
  @JsonProperty("password_require_letter")
  public Boolean passwordRequireLetter;

  public Boolean getPasswordRequireLetter() {
    return passwordRequireLetter;
  }

  /**
   * Require lower and upper case letters in passwords?
   */
  @JsonProperty("password_require_mixed")
  public Boolean passwordRequireMixed;

  public Boolean getPasswordRequireMixed() {
    return passwordRequireMixed;
  }

  /**
   * Require a number in passwords?
   */
  @JsonProperty("password_require_number")
  public Boolean passwordRequireNumber;

  public Boolean getPasswordRequireNumber() {
    return passwordRequireNumber;
  }

  /**
   * Require special characters in password?
   */
  @JsonProperty("password_require_special")
  public Boolean passwordRequireSpecial;

  public Boolean getPasswordRequireSpecial() {
    return passwordRequireSpecial;
  }

  /**
   * Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
   */
  @JsonProperty("password_require_unbreached")
  public Boolean passwordRequireUnbreached;

  public Boolean getPasswordRequireUnbreached() {
    return passwordRequireUnbreached;
  }

  /**
   * Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
   */
  @JsonProperty("password_requirements_apply_to_bundles")
  public Boolean passwordRequirementsApplyToBundles;

  public Boolean getPasswordRequirementsApplyToBundles() {
    return passwordRequirementsApplyToBundles;
  }

  /**
   * Number of days password is valid
   */
  @JsonProperty("password_validity_days")
  public Long passwordValidityDays;

  public Long getPasswordValidityDays() {
    return passwordValidityDays;
  }

  /**
   * Site phone number
   */
  @JsonProperty("phone")
  public String phone;

  public String getPhone() {
    return phone;
  }

  /**
   * If true, we will ensure that all internal communications with any remote server are made through the primary region of the site. This setting overrides individual remote server settings.
   */
  @JsonProperty("pin_all_remote_servers_to_site_region")
  public Boolean pinAllRemoteServersToSiteRegion;

  public Boolean getPinAllRemoteServersToSiteRegion() {
    return pinAllRemoteServersToSiteRegion;
  }

  /**
   * If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.
   */
  @JsonProperty("prevent_root_permissions_for_non_site_admins")
  public Boolean preventRootPermissionsForNonSiteAdmins;

  public Boolean getPreventRootPermissionsForNonSiteAdmins() {
    return preventRootPermissionsForNonSiteAdmins;
  }

  /**
   * If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.
   */
  @JsonProperty("protocol_access_groups_only")
  public Boolean protocolAccessGroupsOnly;

  public Boolean getProtocolAccessGroupsOnly() {
    return protocolAccessGroupsOnly;
  }

  /**
   * Require two-factor authentication for all users?
   */
  @JsonProperty("require_2fa")
  public Boolean require2fa;

  public Boolean getRequire2fa() {
    return require2fa;
  }

  /**
   * If true, only site admins may create, modify, or delete any behavior at the site root, or a skip that would disable one.
   */
  @JsonProperty("restrict_root_folder_behaviors_to_site_admins")
  public Boolean restrictRootFolderBehaviorsToSiteAdmins;

  public Boolean getRestrictRootFolderBehaviorsToSiteAdmins() {
    return restrictRootFolderBehaviorsToSiteAdmins;
  }

  /**
   * If true, supported protective behaviors at the site root also apply within named workspaces. Requires restrict_root_folder_behaviors_to_site_admins to be enabled.
   */
  @JsonProperty("root_folder_behaviors_apply_to_workspaces")
  public Boolean rootFolderBehaviorsApplyToWorkspaces;

  public Boolean getRootFolderBehaviorsApplyToWorkspaces() {
    return rootFolderBehaviorsApplyToWorkspaces;
  }

  /**
   * If true, SSO users using the default user-level two-factor authentication setting are exempt from the site-wide two-factor authentication requirement.
   */
  @JsonProperty("require_2fa_exempt_all_sso_users")
  public Boolean require2faExemptAllSsoUsers;

  public Boolean getRequire2faExemptAllSsoUsers() {
    return require2faExemptAllSsoUsers;
  }

  /**
   * If set, requirement for two-factor authentication has been scheduled to end on this date-time.
   */
  @JsonProperty("require_2fa_stop_time")
  public Date require2faStopTime;

  public Date getRequire2faStopTime() {
    return require2faStopTime;
  }

  /**
   * Auto-removes bundles for disabled/deleted users and enforces bundle expiry within user access period.
   */
  @JsonProperty("revoke_bundle_access_on_disable_or_delete")
  public Boolean revokeBundleAccessOnDisableOrDelete;

  public Boolean getRevokeBundleAccessOnDisableOrDelete() {
    return revokeBundleAccessOnDisableOrDelete;
  }

  /**
   * What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
   */
  @JsonProperty("require_2fa_user_type")
  public String require2faUserType;

  public String getRequire2faUserType() {
    return require2faUserType;
  }

  /**
   * If true, we will hide the 'Remember Me' box on Inbox and Bundle registration pages, requiring that the user logout and log back in every time they visit the page.
   */
  @JsonProperty("require_logout_from_bundles_and_inboxes")
  public Boolean requireLogoutFromBundlesAndInboxes;

  public Boolean getRequireLogoutFromBundlesAndInboxes() {
    return requireLogoutFromBundlesAndInboxes;
  }

  /**
   * Current session
   */
  @JsonProperty("session")
  public Session session;

  public Session getSession() {
    return session;
  }

  /**
   * Is the S3-compatible endpoint enabled for all users and workspaces on this site? Defaults to true. When false, user and group S3 permissions do not allow access.
   */
  @JsonProperty("s3_compatible_endpoint_enabled")
  public Boolean s3CompatibleEndpointEnabled;

  public Boolean getS3CompatibleEndpointEnabled() {
    return s3CompatibleEndpointEnabled;
  }

  /**
   * Is SFTP enabled?
   */
  @JsonProperty("sftp_enabled")
  public Boolean sftpEnabled;

  public Boolean getSftpEnabled() {
    return sftpEnabled;
  }

  /**
   * Finalize partial SFTP uploads from interrupted connections? Default: true.
   */
  @JsonProperty("sftp_finalize_partial_uploads")
  public Boolean sftpFinalizePartialUploads;

  public Boolean getSftpFinalizePartialUploads() {
    return sftpFinalizePartialUploads;
  }

  /**
   * Sftp Host Key Type
   */
  @JsonProperty("sftp_host_key_type")
  public String sftpHostKeyType;

  public String getSftpHostKeyType() {
    return sftpHostKeyType;
  }

  /**
   * Id of the currently selected custom SFTP Host Key
   */
  @JsonProperty("active_sftp_host_key_id")
  public Long activeSftpHostKeyId;

  public Long getActiveSftpHostKeyId() {
    return activeSftpHostKeyId;
  }

  /**
   * Ids of the selected custom SFTP Host Keys
   */
  @JsonProperty("active_sftp_host_key_ids")
  public Long[] activeSftpHostKeyIds;

  public Long[] getActiveSftpHostKeyIds() {
    return activeSftpHostKeyIds;
  }

  /**
   * If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.
   */
  @JsonProperty("sftp_insecure_ciphers")
  public Boolean sftpInsecureCiphers;

  public Boolean getSftpInsecureCiphers() {
    return sftpInsecureCiphers;
  }

  /**
   * If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.
   */
  @JsonProperty("sftp_insecure_diffie_hellman")
  public Boolean sftpInsecureDiffieHellman;

  public Boolean getSftpInsecureDiffieHellman() {
    return sftpInsecureDiffieHellman;
  }

  /**
   * Use user FTP roots also for SFTP?
   */
  @JsonProperty("sftp_user_root_enabled")
  public Boolean sftpUserRootEnabled;

  public Boolean getSftpUserRootEnabled() {
    return sftpUserRootEnabled;
  }

  /**
   * Allow bundle creation
   */
  @JsonProperty("sharing_enabled")
  public Boolean sharingEnabled;

  public Boolean getSharingEnabled() {
    return sharingEnabled;
  }

  /**
   * Show log in link in user notifications?
   */
  @JsonProperty("show_user_notifications_log_in_link")
  public Boolean showUserNotificationsLogInLink;

  public Boolean getShowUserNotificationsLogInLink() {
    return showUserNotificationsLogInLink;
  }

  /**
   * Show request access link for users without access?  Currently unused.
   */
  @JsonProperty("show_request_access_link")
  public Boolean showRequestAccessLink;

  public Boolean getShowRequestAccessLink() {
    return showRequestAccessLink;
  }

  /**
   * Custom site footer text for authenticated pages
   */
  @JsonProperty("site_footer")
  public String siteFooter;

  public String getSiteFooter() {
    return siteFooter;
  }

  /**
   * Custom site header text for authenticated pages
   */
  @JsonProperty("site_header")
  public String siteHeader;

  public String getSiteHeader() {
    return siteHeader;
  }

  /**
   * Custom site footer text for public pages
   */
  @JsonProperty("site_public_footer")
  public String sitePublicFooter;

  public String getSitePublicFooter() {
    return sitePublicFooter;
  }

  /**
   * Custom site header text for public pages
   */
  @JsonProperty("site_public_header")
  public String sitePublicHeader;

  public String getSitePublicHeader() {
    return sitePublicHeader;
  }

  /**
   * SMTP server hostname or IP
   */
  @JsonProperty("smtp_address")
  public String smtpAddress;

  public String getSmtpAddress() {
    return smtpAddress;
  }

  /**
   * SMTP server authentication type
   */
  @JsonProperty("smtp_authentication")
  public String smtpAuthentication;

  public String getSmtpAuthentication() {
    return smtpAuthentication;
  }

  /**
   * From address to use when mailing through custom SMTP
   */
  @JsonProperty("smtp_from")
  public String smtpFrom;

  public String getSmtpFrom() {
    return smtpFrom;
  }

  /**
   * SMTP server port
   */
  @JsonProperty("smtp_port")
  public Long smtpPort;

  public Long getSmtpPort() {
    return smtpPort;
  }

  /**
   * Custom SMTP encryption mode: if_available (default) uses STARTTLS when offered and otherwise sends credentials and messages unencrypted. A certificate-verified STARTTLS connection automatically changes if_available to require unless smtp_ssl is managed by a parent policy. require requires STARTTLS before authentication; require_implicit uses TLS from connection start; never disables TLS. TLS verifies the server certificate against smtp_address. Encryption is never automatically downgraded.
   */
  @JsonProperty("smtp_ssl")
  public String smtpSsl;

  public String getSmtpSsl() {
    return smtpSsl;
  }

  /**
   * SMTP server username
   */
  @JsonProperty("smtp_username")
  public String smtpUsername;

  public String getSmtpUsername() {
    return smtpUsername;
  }

  /**
   * Session expiry in minutes
   */
  @JsonProperty("session_expiry_minutes")
  public Long sessionExpiryMinutes;

  public Long getSessionExpiryMinutes() {
    return sessionExpiryMinutes;
  }

  /**
   * Allow snapshot share links creation
   */
  @JsonProperty("snapshot_sharing_enabled")
  public Boolean snapshotSharingEnabled;

  public Boolean getSnapshotSharingEnabled() {
    return snapshotSharingEnabled;
  }

  /**
   * Is SSL required?  Disabling this is insecure.
   */
  @JsonProperty("ssl_required")
  public Boolean sslRequired;

  public Boolean getSslRequired() {
    return sslRequired;
  }

  /**
   * Site subdomain
   */
  @JsonProperty("subdomain")
  public String subdomain;

  public String getSubdomain() {
    return subdomain;
  }

  /**
   * If switching plans, when does the new plan take effect?
   */
  @JsonProperty("switch_to_plan_date")
  public Date switchToPlanDate;

  public Date getSwitchToPlanDate() {
    return switchToPlanDate;
  }

  /**
   * Number of days left in trial
   */
  @JsonProperty("trial_days_left")
  public Long trialDaysLeft;

  public Long getTrialDaysLeft() {
    return trialDaysLeft;
  }

  /**
   * When does this Site trial expire?
   */
  @JsonProperty("trial_until")
  public Date trialUntil;

  public Date getTrialUntil() {
    return trialUntil;
  }

  /**
   * If using custom SMTP, should we use dedicated IPs to deliver emails?
   */
  @JsonProperty("use_dedicated_ips_for_smtp")
  public Boolean useDedicatedIpsForSmtp;

  public Boolean getUseDedicatedIpsForSmtp() {
    return useDedicatedIpsForSmtp;
  }

  /**
   * Allow uploaders to set `provided_modified_at` for uploaded files?
   */
  @JsonProperty("use_provided_modified_at")
  public Boolean useProvidedModifiedAt;

  public Boolean getUseProvidedModifiedAt() {
    return useProvidedModifiedAt;
  }

  /**
   * User of current session
   */
  @JsonProperty("user")
  public User user;

  public User getUser() {
    return user;
  }

  /**
   * Will users be locked out after incorrect login attempts?
   */
  @JsonProperty("user_lockout")
  public Boolean userLockout;

  public Boolean getUserLockout() {
    return userLockout;
  }

  /**
   * How many hours to lock user out for failed password?
   */
  @JsonProperty("user_lockout_lock_period")
  public Long userLockoutLockPeriod;

  public Long getUserLockoutLockPeriod() {
    return userLockoutLockPeriod;
  }

  /**
   * Number of login tries within `user_lockout_within` hours before users are locked out
   */
  @JsonProperty("user_lockout_tries")
  public Long userLockoutTries;

  public Long getUserLockoutTries() {
    return userLockoutTries;
  }

  /**
   * Number of hours for user lockout window
   */
  @JsonProperty("user_lockout_within")
  public Long userLockoutWithin;

  public Long getUserLockoutWithin() {
    return userLockoutWithin;
  }

  /**
   * Enable User Requests feature
   */
  @JsonProperty("user_requests_enabled")
  public Boolean userRequestsEnabled;

  public Boolean getUserRequestsEnabled() {
    return userRequestsEnabled;
  }

  /**
   * Send email to site admins when a user request is received?
   */
  @JsonProperty("user_requests_notify_admins")
  public Boolean userRequestsNotifyAdmins;

  public Boolean getUserRequestsNotifyAdmins() {
    return userRequestsNotifyAdmins;
  }

  /**
   * Allow users to create their own API keys?
   */
  @JsonProperty("users_can_create_api_keys")
  public Boolean usersCanCreateApiKeys;

  public Boolean getUsersCanCreateApiKeys() {
    return usersCanCreateApiKeys;
  }

  /**
   * Allow users to create their own SSH keys?
   */
  @JsonProperty("users_can_create_ssh_keys")
  public Boolean usersCanCreateSshKeys;

  public Boolean getUsersCanCreateSshKeys() {
    return usersCanCreateSshKeys;
  }

  /**
   * How usernames are displayed in the web UI. Can be `username_only`, `full_name_only`, `full_name_username`, `full_name_company`, or `full_name_username_company`.
   */
  @JsonProperty("username_display")
  public String usernameDisplay;

  public String getUsernameDisplay() {
    return usernameDisplay;
  }

  /**
   * Custom text send in user welcome email
   */
  @JsonProperty("welcome_custom_text")
  public String welcomeCustomText;

  public String getWelcomeCustomText() {
    return welcomeCustomText;
  }

  /**
   * Custom footer text for system-generated emails (as Markdown). Supports standard strftime date/time patterns like %Y (4-digit year), %m (month), %d (day).
   */
  @JsonProperty("email_footer_custom_text")
  public String emailFooterCustomText;

  public String getEmailFooterCustomText() {
    return emailFooterCustomText;
  }

  /**
   * Include this email in welcome emails if enabled
   */
  @JsonProperty("welcome_email_cc")
  public String welcomeEmailCc;

  public String getWelcomeEmailCc() {
    return welcomeEmailCc;
  }

  /**
   * Include this email subject in welcome emails if enabled
   */
  @JsonProperty("welcome_email_subject")
  public String welcomeEmailSubject;

  public String getWelcomeEmailSubject() {
    return welcomeEmailSubject;
  }

  /**
   * Will the welcome email be sent to new users?
   */
  @JsonProperty("welcome_email_enabled")
  public Boolean welcomeEmailEnabled;

  public Boolean getWelcomeEmailEnabled() {
    return welcomeEmailEnabled;
  }

  /**
   * Does the welcome screen appear?
   */
  @JsonProperty("welcome_screen")
  public String welcomeScreen;

  public String getWelcomeScreen() {
    return welcomeScreen;
  }

  /**
   * Does FTP user Windows emulation mode?
   */
  @JsonProperty("windows_mode_ftp")
  public Boolean windowsModeFtp;

  public Boolean getWindowsModeFtp() {
    return windowsModeFtp;
  }


  /**
   */
  public static Site get() throws RuntimeException {
    return get(null, null);
  }

  public static Site get(HashMap<String, Object> parameters) throws RuntimeException {
    return get(parameters, null);
  }


  public static Site get(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sites().get(parameters, options);
  }


  /**
   */
  public static UsageSnapshot getUsage() throws RuntimeException {
    return getUsage(null, null);
  }

  public static UsageSnapshot getUsage(HashMap<String, Object> parameters) throws RuntimeException {
    return getUsage(parameters, null);
  }


  public static UsageSnapshot getUsage(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sites().getUsage(parameters, options);
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
  public static Site update() throws RuntimeException {
    return update(null, null);
  }

  public static Site update(HashMap<String, Object> parameters) throws RuntimeException {
    return update(parameters, null);
  }


  public static Site update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sites().update(parameters, options);
  }


}
