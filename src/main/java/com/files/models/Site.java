package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.net.ClientRuntime;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
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


  /**
   * Creates a {@code Site} with no attributes set.
   */
  public Site() {
    this(null, null);
  }

  /**
   * Creates a {@code Site} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Site(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Site} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Site name
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Additional extensions that are considered text files
   */
  @JsonProperty("additional_text_file_types")
  public String[] additionalTextFileTypes;

  /**
   * Gets {@link #additionalTextFileTypes}.
   *
   * @return the value of {@link #additionalTextFileTypes}
   */
  public String[] getAdditionalTextFileTypes() {
    return additionalTextFileTypes;
  }

  /**
   * Availability settings for AI features. Each feature requires the site_admins, workspace_admins, folder_admins, and all_users keys. Optional selected_group_members defaults to false; when true, members of at least one group in group_ids get access regardless of the other options. All availability options are additive: any enabled option matching the user grants access. Optional group_ids is an array of integer IDs of active groups on this site, from any workspace. Omitted or empty group_ids grants no access through selected_group_members and does not affect other options. Disabling all AI features overrides these settings.
   */
  @JsonProperty("ai_feature_availability")
  public Object aiFeatureAvailability;

  /**
   * Gets {@link #aiFeatureAvailability}.
   *
   * @return the value of {@link #aiFeatureAvailability}
   */
  public Object getAiFeatureAvailability() {
    return aiFeatureAvailability;
  }

  /**
   * Is SMS two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_sms")
  public Boolean allowed2faMethodSms;

  /**
   * Gets {@link #allowed2faMethodSms}.
   *
   * @return the value of {@link #allowed2faMethodSms}
   */
  public Boolean getAllowed2faMethodSms() {
    return allowed2faMethodSms;
  }

  /**
   * Is TOTP two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_totp")
  public Boolean allowed2faMethodTotp;

  /**
   * Gets {@link #allowed2faMethodTotp}.
   *
   * @return the value of {@link #allowed2faMethodTotp}
   */
  public Boolean getAllowed2faMethodTotp() {
    return allowed2faMethodTotp;
  }

  /**
   * Is WebAuthn two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_webauthn")
  public Boolean allowed2faMethodWebauthn;

  /**
   * Gets {@link #allowed2faMethodWebauthn}.
   *
   * @return the value of {@link #allowed2faMethodWebauthn}
   */
  public Boolean getAllowed2faMethodWebauthn() {
    return allowed2faMethodWebauthn;
  }

  /**
   * Is yubikey two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_yubi")
  public Boolean allowed2faMethodYubi;

  /**
   * Gets {@link #allowed2faMethodYubi}.
   *
   * @return the value of {@link #allowed2faMethodYubi}
   */
  public Boolean getAllowed2faMethodYubi() {
    return allowed2faMethodYubi;
  }

  /**
   * Is OTP via email two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_email")
  public Boolean allowed2faMethodEmail;

  /**
   * Gets {@link #allowed2faMethodEmail}.
   *
   * @return the value of {@link #allowed2faMethodEmail}
   */
  public Boolean getAllowed2faMethodEmail() {
    return allowed2faMethodEmail;
  }

  /**
   * Is OTP via static codes for two factor authentication allowed?
   */
  @JsonProperty("allowed_2fa_method_static")
  public Boolean allowed2faMethodStatic;

  /**
   * Gets {@link #allowed2faMethodStatic}.
   *
   * @return the value of {@link #allowed2faMethodStatic}
   */
  public Boolean getAllowed2faMethodStatic() {
    return allowed2faMethodStatic;
  }

  /**
   * Are users allowed to configure their two factor authentication to be bypassed for FTP/SFTP/WebDAV?
   */
  @JsonProperty("allowed_2fa_method_bypass_for_ftp_sftp_dav")
  public Boolean allowed2faMethodBypassForFtpSftpDav;

  /**
   * Gets {@link #allowed2faMethodBypassForFtpSftpDav}.
   *
   * @return the value of {@link #allowed2faMethodBypassForFtpSftpDav}
   */
  public Boolean getAllowed2faMethodBypassForFtpSftpDav() {
    return allowed2faMethodBypassForFtpSftpDav;
  }

  /**
   * User ID for the main site administrator
   */
  @JsonProperty("admin_user_id")
  public Long adminUserId;

  /**
   * Gets {@link #adminUserId}.
   *
   * @return the value of {@link #adminUserId}
   */
  public Long getAdminUserId() {
    return adminUserId;
  }

  /**
   * Allow admins to bypass the locked subfolders setting.
   */
  @JsonProperty("admins_bypass_locked_subfolders")
  public Boolean adminsBypassLockedSubfolders;

  /**
   * Gets {@link #adminsBypassLockedSubfolders}.
   *
   * @return the value of {@link #adminsBypassLockedSubfolders}
   */
  public Boolean getAdminsBypassLockedSubfolders() {
    return adminsBypassLockedSubfolders;
  }

  /**
   * Are manual Bundle names allowed?
   */
  @JsonProperty("allow_bundle_names")
  public Boolean allowBundleNames;

  /**
   * Gets {@link #allowBundleNames}.
   *
   * @return the value of {@link #allowBundleNames}
   */
  public Boolean getAllowBundleNames() {
    return allowBundleNames;
  }

  /**
   * Allow the site-wide two-factor authentication requirement to be overriden on a per-user-basis?
   */
  @JsonProperty("allow_user_level_2fa_override")
  public Boolean allowUserLevel2faOverride;

  /**
   * Gets {@link #allowUserLevel2faOverride}.
   *
   * @return the value of {@link #allowUserLevel2faOverride}
   */
  public Boolean getAllowUserLevel2faOverride() {
    return allowUserLevel2faOverride;
  }

  /**
   * Allow the site-wide allowed IP restriction to be overriden on a per-user-basis?
   */
  @JsonProperty("allow_user_level_allowed_ip_override")
  public Boolean allowUserLevelAllowedIpOverride;

  /**
   * Gets {@link #allowUserLevelAllowedIpOverride}.
   *
   * @return the value of {@link #allowUserLevelAllowedIpOverride}
   */
  public Boolean getAllowUserLevelAllowedIpOverride() {
    return allowUserLevelAllowedIpOverride;
  }

  /**
   * Allow the site-wide FTP SSL requirement to be overriden on a per-user-basis?
   */
  @JsonProperty("allow_user_level_ssl_override")
  public Boolean allowUserLevelSslOverride;

  /**
   * Gets {@link #allowUserLevelSslOverride}.
   *
   * @return the value of {@link #allowUserLevelSslOverride}
   */
  public Boolean getAllowUserLevelSslOverride() {
    return allowUserLevelSslOverride;
  }

  /**
   * Comma separated list of allowed Country codes
   */
  @JsonProperty("allowed_countries")
  public String allowedCountries;

  /**
   * Gets {@link #allowedCountries}.
   *
   * @return the value of {@link #allowedCountries}
   */
  public String getAllowedCountries() {
    return allowedCountries;
  }

  /**
   * List of allowed IP addresses
   */
  @JsonProperty("allowed_ips")
  public String allowedIps;

  /**
   * Gets {@link #allowedIps}.
   *
   * @return the value of {@link #allowedIps}
   */
  public String getAllowedIps() {
    return allowedIps;
  }

  /**
   * Create parent directories if they do not exist during uploads?  This is primarily used to work around broken upload clients that assume servers will perform this step.
   */
  @JsonProperty("always_mkdir_parents")
  public Boolean alwaysMkdirParents;

  /**
   * Gets {@link #alwaysMkdirParents}.
   *
   * @return the value of {@link #alwaysMkdirParents}
   */
  public Boolean getAlwaysMkdirParents() {
    return alwaysMkdirParents;
  }

  /**
   * Number of days to retain AS2 messages (incoming and outgoing).
   */
  @JsonProperty("as2_message_retention_days")
  public Long as2MessageRetentionDays;

  /**
   * Gets {@link #as2MessageRetentionDays}.
   *
   * @return the value of {@link #as2MessageRetentionDays}
   */
  public Long getAs2MessageRetentionDays() {
    return as2MessageRetentionDays;
  }

  /**
   * If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
   */
  @JsonProperty("ask_about_overwrites")
  public Boolean askAboutOverwrites;

  /**
   * Gets {@link #askAboutOverwrites}.
   *
   * @return the value of {@link #askAboutOverwrites}
   */
  public Boolean getAskAboutOverwrites() {
    return askAboutOverwrites;
  }

  /**
   * Do Bundle owners receive activity notifications?
   */
  @JsonProperty("bundle_activity_notifications")
  public String bundleActivityNotifications;

  /**
   * Gets {@link #bundleActivityNotifications}.
   *
   * @return the value of {@link #bundleActivityNotifications}
   */
  public String getBundleActivityNotifications() {
    return bundleActivityNotifications;
  }

  /**
   * Site-wide Bundle expiration in days
   */
  @JsonProperty("bundle_expiration")
  public Long bundleExpiration;

  /**
   * Gets {@link #bundleExpiration}.
   *
   * @return the value of {@link #bundleExpiration}
   */
  public Long getBundleExpiration() {
    return bundleExpiration;
  }

  /**
   * Custom error message to show when bundle is not found.
   */
  @JsonProperty("bundle_not_found_message")
  public String bundleNotFoundMessage;

  /**
   * Gets {@link #bundleNotFoundMessage}.
   *
   * @return the value of {@link #bundleNotFoundMessage}
   */
  public String getBundleNotFoundMessage() {
    return bundleNotFoundMessage;
  }

  /**
   * Do Bundles require password protection?
   */
  @JsonProperty("bundle_password_required")
  public Boolean bundlePasswordRequired;

  /**
   * Gets {@link #bundlePasswordRequired}.
   *
   * @return the value of {@link #bundlePasswordRequired}
   */
  public Boolean getBundlePasswordRequired() {
    return bundlePasswordRequired;
  }

  /**
   * If true, new Share Links created by a user with a primary group will default to that group as owner.
   */
  @JsonProperty("bundles_default_owned_by_primary_group")
  public Boolean bundlesDefaultOwnedByPrimaryGroup;

  /**
   * Gets {@link #bundlesDefaultOwnedByPrimaryGroup}.
   *
   * @return the value of {@link #bundlesDefaultOwnedByPrimaryGroup}
   */
  public Boolean getBundlesDefaultOwnedByPrimaryGroup() {
    return bundlesDefaultOwnedByPrimaryGroup;
  }

  /**
   * List of email domains to disallow when entering a Bundle/Inbox recipients
   */
  @JsonProperty("bundle_recipient_blacklist_domains")
  public String[] bundleRecipientBlacklistDomains;

  /**
   * Gets {@link #bundleRecipientBlacklistDomains}.
   *
   * @return the value of {@link #bundleRecipientBlacklistDomains}
   */
  public String[] getBundleRecipientBlacklistDomains() {
    return bundleRecipientBlacklistDomains;
  }

  /**
   * Disallow free email domains for Bundle/Inbox recipients?
   */
  @JsonProperty("bundle_recipient_blacklist_free_email_domains")
  public Boolean bundleRecipientBlacklistFreeEmailDomains;

  /**
   * Gets {@link #bundleRecipientBlacklistFreeEmailDomains}.
   *
   * @return the value of {@link #bundleRecipientBlacklistFreeEmailDomains}
   */
  public Boolean getBundleRecipientBlacklistFreeEmailDomains() {
    return bundleRecipientBlacklistFreeEmailDomains;
  }

  /**
   * Do Bundle owners receive registration notification?
   */
  @JsonProperty("bundle_registration_notifications")
  public String bundleRegistrationNotifications;

  /**
   * Gets {@link #bundleRegistrationNotifications}.
   *
   * @return the value of {@link #bundleRegistrationNotifications}
   */
  public String getBundleRegistrationNotifications() {
    return bundleRegistrationNotifications;
  }

  /**
   * Do Bundles require registration?
   */
  @JsonProperty("bundle_require_registration")
  public Boolean bundleRequireRegistration;

  /**
   * Gets {@link #bundleRequireRegistration}.
   *
   * @return the value of {@link #bundleRequireRegistration}
   */
  public Boolean getBundleRequireRegistration() {
    return bundleRequireRegistration;
  }

  /**
   * Do Bundles require recipients for sharing?
   */
  @JsonProperty("bundle_require_share_recipient")
  public Boolean bundleRequireShareRecipient;

  /**
   * Gets {@link #bundleRequireShareRecipient}.
   *
   * @return the value of {@link #bundleRequireShareRecipient}
   */
  public Boolean getBundleRequireShareRecipient() {
    return bundleRequireShareRecipient;
  }

  /**
   * If true, new Share Links must send a one-time password to the recipient when they register. Requires bundle_require_share_recipient and cannot be enabled with bundle_password_required.
   */
  @JsonProperty("bundle_send_one_time_password_to_recipient_at_registration")
  public Boolean bundleSendOneTimePasswordToRecipientAtRegistration;

  /**
   * Gets {@link #bundleSendOneTimePasswordToRecipientAtRegistration}.
   *
   * @return the value of {@link #bundleSendOneTimePasswordToRecipientAtRegistration}
   */
  public Boolean getBundleSendOneTimePasswordToRecipientAtRegistration() {
    return bundleSendOneTimePasswordToRecipientAtRegistration;
  }

  /**
   * Do Bundles require internal notes?
   */
  @JsonProperty("bundle_require_note")
  public Boolean bundleRequireNote;

  /**
   * Gets {@link #bundleRequireNote}.
   *
   * @return the value of {@link #bundleRequireNote}
   */
  public Boolean getBundleRequireNote() {
    return bundleRequireNote;
  }

  /**
   * Do Bundle creators receive receipts of invitations?
   */
  @JsonProperty("bundle_send_shared_receipts")
  public Boolean bundleSendSharedReceipts;

  /**
   * Gets {@link #bundleSendSharedReceipts}.
   *
   * @return the value of {@link #bundleSendSharedReceipts}
   */
  public Boolean getBundleSendSharedReceipts() {
    return bundleSendSharedReceipts;
  }

  /**
   * Do Bundle uploaders receive upload confirmation notifications?
   */
  @JsonProperty("bundle_upload_receipt_notifications")
  public String bundleUploadReceiptNotifications;

  /**
   * Gets {@link #bundleUploadReceiptNotifications}.
   *
   * @return the value of {@link #bundleUploadReceiptNotifications}
   */
  public String getBundleUploadReceiptNotifications() {
    return bundleUploadReceiptNotifications;
  }

  /**
   * Preview watermark image applied to all bundle items.
   */
  @JsonProperty("bundle_watermark_attachment")
  public Image bundleWatermarkAttachment;

  /**
   * Gets {@link #bundleWatermarkAttachment}.
   *
   * @return the value of {@link #bundleWatermarkAttachment}
   */
  public Image getBundleWatermarkAttachment() {
    return bundleWatermarkAttachment;
  }

  /**
   * Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
   */
  @JsonProperty("bundle_watermark_value")
  public Object bundleWatermarkValue;

  /**
   * Gets {@link #bundleWatermarkValue}.
   *
   * @return the value of {@link #bundleWatermarkValue}
   */
  public Object getBundleWatermarkValue() {
    return bundleWatermarkValue;
  }

  /**
   * Calculate CRC32 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_crc32")
  public Boolean calculateFileChecksumsCrc32;

  /**
   * Gets {@link #calculateFileChecksumsCrc32}.
   *
   * @return the value of {@link #calculateFileChecksumsCrc32}
   */
  public Boolean getCalculateFileChecksumsCrc32() {
    return calculateFileChecksumsCrc32;
  }

  /**
   * Calculate MD5 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_md5")
  public Boolean calculateFileChecksumsMd5;

  /**
   * Gets {@link #calculateFileChecksumsMd5}.
   *
   * @return the value of {@link #calculateFileChecksumsMd5}
   */
  public Boolean getCalculateFileChecksumsMd5() {
    return calculateFileChecksumsMd5;
  }

  /**
   * Calculate SHA1 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_sha1")
  public Boolean calculateFileChecksumsSha1;

  /**
   * Gets {@link #calculateFileChecksumsSha1}.
   *
   * @return the value of {@link #calculateFileChecksumsSha1}
   */
  public Boolean getCalculateFileChecksumsSha1() {
    return calculateFileChecksumsSha1;
  }

  /**
   * Calculate SHA256 checksums for files?
   */
  @JsonProperty("calculate_file_checksums_sha256")
  public Boolean calculateFileChecksumsSha256;

  /**
   * Gets {@link #calculateFileChecksumsSha256}.
   *
   * @return the value of {@link #calculateFileChecksumsSha256}
   */
  public Boolean getCalculateFileChecksumsSha256() {
    return calculateFileChecksumsSha256;
  }

  /**
   * If true, administrators on this site's Child Sites, including Folder Admins, can list this site's Default Workspace users and groups and grant them folder permissions and notifications on Child Site paths.
   */
  @JsonProperty("child_site_folder_admins_can_see_parent_site_directory")
  public Boolean childSiteFolderAdminsCanSeeParentSiteDirectory;

  /**
   * Gets {@link #childSiteFolderAdminsCanSeeParentSiteDirectory}.
   *
   * @return the value of {@link #childSiteFolderAdminsCanSeeParentSiteDirectory}
   */
  public Boolean getChildSiteFolderAdminsCanSeeParentSiteDirectory() {
    return childSiteFolderAdminsCanSeeParentSiteDirectory;
  }

  /**
   * If true, Workspace Administrators and Folder Admins in a Custom Workspace can list the Default Workspace users and groups that have permissions in that Workspace and choose them as Email Notification recipients.
   */
  @JsonProperty("workspace_folder_admins_can_see_default_workspace_directory")
  public Boolean workspaceFolderAdminsCanSeeDefaultWorkspaceDirectory;

  /**
   * Gets {@link #workspaceFolderAdminsCanSeeDefaultWorkspaceDirectory}.
   *
   * @return the value of {@link #workspaceFolderAdminsCanSeeDefaultWorkspaceDirectory}
   */
  public Boolean getWorkspaceFolderAdminsCanSeeDefaultWorkspaceDirectory() {
    return workspaceFolderAdminsCanSeeDefaultWorkspaceDirectory;
  }

  /**
   * Require email authentication, virus, and spam checks for incoming emails to Inboxes and Incoming Email Automations in every Workspace on this site?
   */
  @JsonProperty("uploads_via_email_authentication")
  public Boolean uploadsViaEmailAuthentication;

  /**
   * Gets {@link #uploadsViaEmailAuthentication}.
   *
   * @return the value of {@link #uploadsViaEmailAuthentication}
   */
  public Boolean getUploadsViaEmailAuthentication() {
    return uploadsViaEmailAuthentication;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color2_left")
  public String color2Left;

  /**
   * Gets {@link #color2Left}.
   *
   * @return the value of {@link #color2Left}
   */
  public String getColor2Left() {
    return color2Left;
  }

  /**
   * Top bar link color
   */
  @JsonProperty("color2_link")
  public String color2Link;

  /**
   * Gets {@link #color2Link}.
   *
   * @return the value of {@link #color2Link}
   */
  public String getColor2Link() {
    return color2Link;
  }

  /**
   * Page link and button color
   */
  @JsonProperty("color2_text")
  public String color2Text;

  /**
   * Gets {@link #color2Text}.
   *
   * @return the value of {@link #color2Text}
   */
  public String getColor2Text() {
    return color2Text;
  }

  /**
   * Top bar background color
   */
  @JsonProperty("color2_top")
  public String color2Top;

  /**
   * Gets {@link #color2Top}.
   *
   * @return the value of {@link #color2Top}
   */
  public String getColor2Top() {
    return color2Top;
  }

  /**
   * Top bar text color
   */
  @JsonProperty("color2_top_text")
  public String color2TopText;

  /**
   * Gets {@link #color2TopText}.
   *
   * @return the value of {@link #color2TopText}
   */
  public String getColor2TopText() {
    return color2TopText;
  }

  /**
   * Site main contact name
   */
  @JsonProperty("contact_name")
  public String contactName;

  /**
   * Gets {@link #contactName}.
   *
   * @return the value of {@link #contactName}
   */
  public String getContactName() {
    return contactName;
  }

  /**
   * Time this site was created
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Preferred currency
   */
  @JsonProperty("currency")
  public String currency;

  /**
   * Gets {@link #currency}.
   *
   * @return the value of {@link #currency}
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Is this site using a custom namespace for users?
   */
  @JsonProperty("custom_namespace")
  public Boolean customNamespace;

  /**
   * Gets {@link #customNamespace}.
   *
   * @return the value of {@link #customNamespace}
   */
  public Boolean getCustomNamespace() {
    return customNamespace;
  }

  /**
   * Is WebDAV enabled?
   */
  @JsonProperty("dav_enabled")
  public Boolean davEnabled;

  /**
   * Gets {@link #davEnabled}.
   *
   * @return the value of {@link #davEnabled}
   */
  public Boolean getDavEnabled() {
    return davEnabled;
  }

  /**
   * Use user FTP roots also for WebDAV?
   */
  @JsonProperty("dav_user_root_enabled")
  public Boolean davUserRootEnabled;

  /**
   * Gets {@link #davUserRootEnabled}.
   *
   * @return the value of {@link #davUserRootEnabled}
   */
  public Boolean getDavUserRootEnabled() {
    return davUserRootEnabled;
  }

  /**
   * Number of days to keep deleted files
   */
  @JsonProperty("days_to_retain_backups")
  public Long daysToRetainBackups;

  /**
   * Gets {@link #daysToRetainBackups}.
   *
   * @return the value of {@link #daysToRetainBackups}
   */
  public Long getDaysToRetainBackups() {
    return daysToRetainBackups;
  }

  /**
   * If true, allow public viewers of Bundles with full permissions to use document editing integrations.
   */
  @JsonProperty("document_edits_in_bundle_allowed")
  public Boolean documentEditsInBundleAllowed;

  /**
   * Gets {@link #documentEditsInBundleAllowed}.
   *
   * @return the value of {@link #documentEditsInBundleAllowed}
   */
  public Boolean getDocumentEditsInBundleAllowed() {
    return documentEditsInBundleAllowed;
  }

  /**
   * Site default time zone
   */
  @JsonProperty("default_time_zone")
  public String defaultTimeZone;

  /**
   * Gets {@link #defaultTimeZone}.
   *
   * @return the value of {@link #defaultTimeZone}
   */
  public String getDefaultTimeZone() {
    return defaultTimeZone;
  }

  /**
   * Is the desktop app enabled?
   */
  @JsonProperty("desktop_app")
  public Boolean desktopApp;

  /**
   * Gets {@link #desktopApp}.
   *
   * @return the value of {@link #desktopApp}
   */
  public Boolean getDesktopApp() {
    return desktopApp;
  }

  /**
   * Is desktop app session IP pinning enabled?
   */
  @JsonProperty("desktop_app_session_ip_pinning")
  public Boolean desktopAppSessionIpPinning;

  /**
   * Gets {@link #desktopAppSessionIpPinning}.
   *
   * @return the value of {@link #desktopAppSessionIpPinning}
   */
  public Boolean getDesktopAppSessionIpPinning() {
    return desktopAppSessionIpPinning;
  }

  /**
   * Desktop app session lifetime (in hours)
   */
  @JsonProperty("desktop_app_session_lifetime")
  public Long desktopAppSessionLifetime;

  /**
   * Gets {@link #desktopAppSessionLifetime}.
   *
   * @return the value of {@link #desktopAppSessionLifetime}
   */
  public Long getDesktopAppSessionLifetime() {
    return desktopAppSessionLifetime;
  }

  /**
   * Use legacy checksums mode?
   */
  @JsonProperty("legacy_checksums_mode")
  public Boolean legacyChecksumsMode;

  /**
   * Gets {@link #legacyChecksumsMode}.
   *
   * @return the value of {@link #legacyChecksumsMode}
   */
  public Boolean getLegacyChecksumsMode() {
    return legacyChecksumsMode;
  }

  /**
   * If true, we will migrate all remote server syncs to the new Sync model.
   */
  @JsonProperty("migrate_remote_server_sync_to_sync")
  public Boolean migrateRemoteServerSyncToSync;

  /**
   * Gets {@link #migrateRemoteServerSyncToSync}.
   *
   * @return the value of {@link #migrateRemoteServerSyncToSync}
   */
  public Boolean getMigrateRemoteServerSyncToSync() {
    return migrateRemoteServerSyncToSync;
  }

  /**
   * Is OAuth DCR (dynamic client registration) for MCP enabled?
   */
  @JsonProperty("mcp_dcr_enabled")
  public Boolean mcpDcrEnabled;

  /**
   * Gets {@link #mcpDcrEnabled}.
   *
   * @return the value of {@link #mcpDcrEnabled}
   */
  public Boolean getMcpDcrEnabled() {
    return mcpDcrEnabled;
  }

  /**
   * Is the mobile app enabled?
   */
  @JsonProperty("mobile_app")
  public Boolean mobileApp;

  /**
   * Gets {@link #mobileApp}.
   *
   * @return the value of {@link #mobileApp}
   */
  public Boolean getMobileApp() {
    return mobileApp;
  }

  /**
   * Is mobile app session IP pinning enabled?
   */
  @JsonProperty("mobile_app_session_ip_pinning")
  public Boolean mobileAppSessionIpPinning;

  /**
   * Gets {@link #mobileAppSessionIpPinning}.
   *
   * @return the value of {@link #mobileAppSessionIpPinning}
   */
  public Boolean getMobileAppSessionIpPinning() {
    return mobileAppSessionIpPinning;
  }

  /**
   * Mobile app session lifetime (in hours)
   */
  @JsonProperty("mobile_app_session_lifetime")
  public Long mobileAppSessionLifetime;

  /**
   * Gets {@link #mobileAppSessionLifetime}.
   *
   * @return the value of {@link #mobileAppSessionLifetime}
   */
  public Long getMobileAppSessionLifetime() {
    return mobileAppSessionLifetime;
  }

  /**
   * Comma separated list of disallowed Country codes
   */
  @JsonProperty("disallowed_countries")
  public String disallowedCountries;

  /**
   * Gets {@link #disallowedCountries}.
   *
   * @return the value of {@link #disallowedCountries}
   */
  public String getDisallowedCountries() {
    return disallowedCountries;
  }

  /**
   * If true, all AI features are disabled for this site.
   */
  @JsonProperty("disable_all_ai_features")
  public Boolean disableAllAiFeatures;

  /**
   * Gets {@link #disableAllAiFeatures}.
   *
   * @return the value of {@link #disableAllAiFeatures}
   */
  public Boolean getDisableAllAiFeatures() {
    return disableAllAiFeatures;
  }

  /**
   * If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.
   */
  @JsonProperty("disable_files_certificate_generation")
  public Boolean disableFilesCertificateGeneration;

  /**
   * Gets {@link #disableFilesCertificateGeneration}.
   *
   * @return the value of {@link #disableFilesCertificateGeneration}
   */
  public Boolean getDisableFilesCertificateGeneration() {
    return disableFilesCertificateGeneration;
  }

  /**
   * Are notifications disabled?
   */
  @JsonProperty("disable_notifications")
  public Boolean disableNotifications;

  /**
   * Gets {@link #disableNotifications}.
   *
   * @return the value of {@link #disableNotifications}
   */
  public Boolean getDisableNotifications() {
    return disableNotifications;
  }

  /**
   * If true, file operations produce file_action.activity.v1 EventChannel events in all workspaces. Defaults to false; subscription filters and workspace access still control delivery.
   */
  @JsonProperty("file_activity_events_enabled")
  public Boolean fileActivityEventsEnabled;

  /**
   * Gets {@link #fileActivityEventsEnabled}.
   *
   * @return the value of {@link #fileActivityEventsEnabled}
   */
  public Boolean getFileActivityEventsEnabled() {
    return fileActivityEventsEnabled;
  }

  /**
   * Is password reset disabled?
   */
  @JsonProperty("disable_password_reset")
  public Boolean disablePasswordReset;

  /**
   * Gets {@link #disablePasswordReset}.
   *
   * @return the value of {@link #disablePasswordReset}
   */
  public Boolean getDisablePasswordReset() {
    return disablePasswordReset;
  }

  /**
   * Custom domain
   */
  @JsonProperty("domain")
  public String domain;

  /**
   * Gets {@link #domain}.
   *
   * @return the value of {@link #domain}
   */
  public String getDomain() {
    return domain;
  }

  /**
   * Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?
   */
  @JsonProperty("domain_hsts_header")
  public Boolean domainHstsHeader;

  /**
   * Gets {@link #domainHstsHeader}.
   *
   * @return the value of {@link #domainHstsHeader}
   */
  public Boolean getDomainHstsHeader() {
    return domainHstsHeader;
  }

  /**
   * Letsencrypt chain to use when registering SSL Certificate for domain. No longer used as of 2026.
   */
  @JsonProperty("domain_letsencrypt_chain")
  public String domainLetsencryptChain;

  /**
   * Gets {@link #domainLetsencryptChain}.
   *
   * @return the value of {@link #domainLetsencryptChain}
   */
  public String getDomainLetsencryptChain() {
    return domainLetsencryptChain;
  }

  /**
   * Main email for this site
   */
  @JsonProperty("email")
  public String email;

  /**
   * Gets {@link #email}.
   *
   * @return the value of {@link #email}
   */
  public String getEmail() {
    return email;
  }

  /**
   * Are FedRAMP security restrictions enabled for this site?
   */
  @JsonProperty("fedramp")
  public Boolean fedramp;

  /**
   * Gets {@link #fedramp}.
   *
   * @return the value of {@link #fedramp}
   */
  public Boolean getFedramp() {
    return fedramp;
  }

  /**
   * Allow other Files.com sites to use this site's API keys for native Files.com Remote Server connections? Defaults to true and applies to all keys and workspaces on this site. When false, new pairings and access through existing connections are rejected without revoking keys or deleting connections. Re-enabling permits access again with usable keys. Does not disable this site's connections to other sites, ordinary API access, or Connected Sites.
   */
  @JsonProperty("files_com_remote_server_enabled")
  public Boolean filesComRemoteServerEnabled;

  /**
   * Gets {@link #filesComRemoteServerEnabled}.
   *
   * @return the value of {@link #filesComRemoteServerEnabled}
   */
  public Boolean getFilesComRemoteServerEnabled() {
    return filesComRemoteServerEnabled;
  }

  /**
   * Is FTP enabled?
   */
  @JsonProperty("ftp_enabled")
  public Boolean ftpEnabled;

  /**
   * Gets {@link #ftpEnabled}.
   *
   * @return the value of {@link #ftpEnabled}
   */
  public Boolean getFtpEnabled() {
    return ftpEnabled;
  }

  /**
   * Reply-to email for this site
   */
  @JsonProperty("reply_to_email")
  public String replyToEmail;

  /**
   * Gets {@link #replyToEmail}.
   *
   * @return the value of {@link #replyToEmail}
   */
  public String getReplyToEmail() {
    return replyToEmail;
  }

  /**
   * If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
   */
  @JsonProperty("non_sso_groups_allowed")
  public Boolean nonSsoGroupsAllowed;

  /**
   * Gets {@link #nonSsoGroupsAllowed}.
   *
   * @return the value of {@link #nonSsoGroupsAllowed}
   */
  public Boolean getNonSsoGroupsAllowed() {
    return nonSsoGroupsAllowed;
  }

  /**
   * If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
   */
  @JsonProperty("non_sso_users_allowed")
  public Boolean nonSsoUsersAllowed;

  /**
   * Gets {@link #nonSsoUsersAllowed}.
   *
   * @return the value of {@link #nonSsoUsersAllowed}
   */
  public Boolean getNonSsoUsersAllowed() {
    return nonSsoUsersAllowed;
  }

  /**
   * If true, permissions for this site must be bound to a group (not a user).
   */
  @JsonProperty("folder_permissions_groups_only")
  public Boolean folderPermissionsGroupsOnly;

  /**
   * Gets {@link #folderPermissionsGroupsOnly}.
   *
   * @return the value of {@link #folderPermissionsGroupsOnly}
   */
  public Boolean getFolderPermissionsGroupsOnly() {
    return folderPermissionsGroupsOnly;
  }

  /**
   * Allow group admins to create users in their groups
   */
  @JsonProperty("group_admins_can_add_users")
  public Boolean groupAdminsCanAddUsers;

  /**
   * Gets {@link #groupAdminsCanAddUsers}.
   *
   * @return the value of {@link #groupAdminsCanAddUsers}
   */
  public Boolean getGroupAdminsCanAddUsers() {
    return groupAdminsCanAddUsers;
  }

  /**
   * Allow group admins to add or remove existing users in their groups
   */
  @JsonProperty("group_admins_can_manage_group_memberships")
  public Boolean groupAdminsCanManageGroupMemberships;

  /**
   * Gets {@link #groupAdminsCanManageGroupMemberships}.
   *
   * @return the value of {@link #groupAdminsCanManageGroupMemberships}
   */
  public Boolean getGroupAdminsCanManageGroupMemberships() {
    return groupAdminsCanManageGroupMemberships;
  }

  /**
   * Allow group admins to delete users in their groups
   */
  @JsonProperty("group_admins_can_delete_users")
  public Boolean groupAdminsCanDeleteUsers;

  /**
   * Gets {@link #groupAdminsCanDeleteUsers}.
   *
   * @return the value of {@link #groupAdminsCanDeleteUsers}
   */
  public Boolean getGroupAdminsCanDeleteUsers() {
    return groupAdminsCanDeleteUsers;
  }

  /**
   * Allow group admins to enable or disable users in their groups
   */
  @JsonProperty("group_admins_can_enable_disable_users")
  public Boolean groupAdminsCanEnableDisableUsers;

  /**
   * Gets {@link #groupAdminsCanEnableDisableUsers}.
   *
   * @return the value of {@link #groupAdminsCanEnableDisableUsers}
   */
  public Boolean getGroupAdminsCanEnableDisableUsers() {
    return groupAdminsCanEnableDisableUsers;
  }

  /**
   * Allow group admins to modify users in their groups
   */
  @JsonProperty("group_admins_can_modify_users")
  public Boolean groupAdminsCanModifyUsers;

  /**
   * Gets {@link #groupAdminsCanModifyUsers}.
   *
   * @return the value of {@link #groupAdminsCanModifyUsers}
   */
  public Boolean getGroupAdminsCanModifyUsers() {
    return groupAdminsCanModifyUsers;
  }

  /**
   * Allow group admins to exempt users in their groups from lifecycle rules
   */
  @JsonProperty("group_admins_can_bypass_user_lifecycle_rules")
  public Boolean groupAdminsCanBypassUserLifecycleRules;

  /**
   * Gets {@link #groupAdminsCanBypassUserLifecycleRules}.
   *
   * @return the value of {@link #groupAdminsCanBypassUserLifecycleRules}
   */
  public Boolean getGroupAdminsCanBypassUserLifecycleRules() {
    return groupAdminsCanBypassUserLifecycleRules;
  }

  /**
   * Allow group admins to reset passwords for users in their groups
   */
  @JsonProperty("group_admins_can_reset_passwords")
  public Boolean groupAdminsCanResetPasswords;

  /**
   * Gets {@link #groupAdminsCanResetPasswords}.
   *
   * @return the value of {@link #groupAdminsCanResetPasswords}
   */
  public Boolean getGroupAdminsCanResetPasswords() {
    return groupAdminsCanResetPasswords;
  }

  /**
   * Allow group admins to set password authentication method
   */
  @JsonProperty("group_admins_can_set_user_password")
  public Boolean groupAdminsCanSetUserPassword;

  /**
   * Gets {@link #groupAdminsCanSetUserPassword}.
   *
   * @return the value of {@link #groupAdminsCanSetUserPassword}
   */
  public Boolean getGroupAdminsCanSetUserPassword() {
    return groupAdminsCanSetUserPassword;
  }

  /**
   * Is there a signed HIPAA BAA between Files.com and this site?
   */
  @JsonProperty("hipaa")
  public Boolean hipaa;

  /**
   * Gets {@link #hipaa}.
   *
   * @return the value of {@link #hipaa}
   */
  public Boolean getHipaa() {
    return hipaa;
  }

  /**
   * Branded icon 128x128
   */
  @JsonProperty("icon128")
  public Image icon128;

  /**
   * Gets {@link #icon128}.
   *
   * @return the value of {@link #icon128}
   */
  public Image getIcon128() {
    return icon128;
  }

  /**
   * Branded icon 16x16
   */
  @JsonProperty("icon16")
  public Image icon16;

  /**
   * Gets {@link #icon16}.
   *
   * @return the value of {@link #icon16}
   */
  public Image getIcon16() {
    return icon16;
  }

  /**
   * Branded icon 32x32
   */
  @JsonProperty("icon32")
  public Image icon32;

  /**
   * Gets {@link #icon32}.
   *
   * @return the value of {@link #icon32}
   */
  public Image getIcon32() {
    return icon32;
  }

  /**
   * Branded icon 48x48
   */
  @JsonProperty("icon48")
  public Image icon48;

  /**
   * Gets {@link #icon48}.
   *
   * @return the value of {@link #icon48}
   */
  public Image getIcon48() {
    return icon48;
  }

  /**
   * Can files be modified?
   */
  @JsonProperty("immutable_files_set_at")
  public Date immutableFilesSetAt;

  /**
   * Gets {@link #immutableFilesSetAt}.
   *
   * @return the value of {@link #immutableFilesSetAt}
   */
  public Date getImmutableFilesSetAt() {
    return immutableFilesSetAt;
  }

  /**
   * Include password in emails to new users?
   */
  @JsonProperty("include_password_in_welcome_email")
  public Boolean includePasswordInWelcomeEmail;

  /**
   * Gets {@link #includePasswordInWelcomeEmail}.
   *
   * @return the value of {@link #includePasswordInWelcomeEmail}
   */
  public Boolean getIncludePasswordInWelcomeEmail() {
    return includePasswordInWelcomeEmail;
  }

  /**
   * Site default language
   */
  @JsonProperty("language")
  public String language;

  /**
   * Gets {@link #language}.
   *
   * @return the value of {@link #language}
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Base DN for looking up users in LDAP server
   */
  @JsonProperty("ldap_base_dn")
  public String ldapBaseDn;

  /**
   * Gets {@link #ldapBaseDn}.
   *
   * @return the value of {@link #ldapBaseDn}
   */
  public String getLdapBaseDn() {
    return ldapBaseDn;
  }

  /**
   * Domain name that will be appended to usernames
   */
  @JsonProperty("ldap_domain")
  public String ldapDomain;

  /**
   * Gets {@link #ldapDomain}.
   *
   * @return the value of {@link #ldapDomain}
   */
  public String getLdapDomain() {
    return ldapDomain;
  }

  /**
   * Main LDAP setting: is LDAP enabled?
   */
  @JsonProperty("ldap_enabled")
  public Boolean ldapEnabled;

  /**
   * Gets {@link #ldapEnabled}.
   *
   * @return the value of {@link #ldapEnabled}
   */
  public Boolean getLdapEnabled() {
    return ldapEnabled;
  }

  /**
   * Should we sync groups from LDAP server?
   */
  @JsonProperty("ldap_group_action")
  public String ldapGroupAction;

  /**
   * Gets {@link #ldapGroupAction}.
   *
   * @return the value of {@link #ldapGroupAction}
   */
  public String getLdapGroupAction() {
    return ldapGroupAction;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
   */
  @JsonProperty("ldap_group_exclusion")
  public String ldapGroupExclusion;

  /**
   * Gets {@link #ldapGroupExclusion}.
   *
   * @return the value of {@link #ldapGroupExclusion}
   */
  public String getLdapGroupExclusion() {
    return ldapGroupExclusion;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) to include when syncing.
   */
  @JsonProperty("ldap_group_inclusion")
  public String ldapGroupInclusion;

  /**
   * Gets {@link #ldapGroupInclusion}.
   *
   * @return the value of {@link #ldapGroupInclusion}
   */
  public String getLdapGroupInclusion() {
    return ldapGroupInclusion;
  }

  /**
   * LDAP host
   */
  @JsonProperty("ldap_host")
  public String ldapHost;

  /**
   * Gets {@link #ldapHost}.
   *
   * @return the value of {@link #ldapHost}
   */
  public String getLdapHost() {
    return ldapHost;
  }

  /**
   * LDAP backup host
   */
  @JsonProperty("ldap_host_2")
  public String ldapHost2;

  /**
   * Gets {@link #ldapHost2}.
   *
   * @return the value of {@link #ldapHost2}
   */
  public String getLdapHost2() {
    return ldapHost2;
  }

  /**
   * LDAP backup host
   */
  @JsonProperty("ldap_host_3")
  public String ldapHost3;

  /**
   * Gets {@link #ldapHost3}.
   *
   * @return the value of {@link #ldapHost3}
   */
  public String getLdapHost3() {
    return ldapHost3;
  }

  /**
   * LDAP port
   */
  @JsonProperty("ldap_port")
  public Long ldapPort;

  /**
   * Gets {@link #ldapPort}.
   *
   * @return the value of {@link #ldapPort}
   */
  public Long getLdapPort() {
    return ldapPort;
  }

  /**
   * Use secure LDAP?
   */
  @JsonProperty("ldap_secure")
  public Boolean ldapSecure;

  /**
   * Gets {@link #ldapSecure}.
   *
   * @return the value of {@link #ldapSecure}
   */
  public Boolean getLdapSecure() {
    return ldapSecure;
  }

  /**
   * How to validate the LDAP server certificate. `require_match` validates the certificate chain and hostname; `allow_any` disables certificate validation.
   */
  @JsonProperty("ldap_server_certificate")
  public String ldapServerCertificate;

  /**
   * Gets {@link #ldapServerCertificate}.
   *
   * @return the value of {@link #ldapServerCertificate}
   */
  public String getLdapServerCertificate() {
    return ldapServerCertificate;
  }

  /**
   * LDAP type
   */
  @JsonProperty("ldap_type")
  public String ldapType;

  /**
   * Gets {@link #ldapType}.
   *
   * @return the value of {@link #ldapType}
   */
  public String getLdapType() {
    return ldapType;
  }

  /**
   * Should we sync users from LDAP server?
   */
  @JsonProperty("ldap_user_action")
  public String ldapUserAction;

  /**
   * Gets {@link #ldapUserAction}.
   *
   * @return the value of {@link #ldapUserAction}
   */
  public String getLdapUserAction() {
    return ldapUserAction;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
   */
  @JsonProperty("ldap_user_include_groups")
  public String ldapUserIncludeGroups;

  /**
   * Gets {@link #ldapUserIncludeGroups}.
   *
   * @return the value of {@link #ldapUserIncludeGroups}
   */
  public String getLdapUserIncludeGroups() {
    return ldapUserIncludeGroups;
  }

  /**
   * Username for signing in to LDAP server.
   */
  @JsonProperty("ldap_username")
  public String ldapUsername;

  /**
   * Gets {@link #ldapUsername}.
   *
   * @return the value of {@link #ldapUsername}
   */
  public String getLdapUsername() {
    return ldapUsername;
  }

  /**
   * LDAP username field
   */
  @JsonProperty("ldap_username_field")
  public String ldapUsernameField;

  /**
   * Gets {@link #ldapUsernameField}.
   *
   * @return the value of {@link #ldapUsernameField}
   */
  public String getLdapUsernameField() {
    return ldapUsernameField;
  }

  /**
   * Login help text
   */
  @JsonProperty("login_help_text")
  public String loginHelpText;

  /**
   * Gets {@link #loginHelpText}.
   *
   * @return the value of {@link #loginHelpText}
   */
  public String getLoginHelpText() {
    return loginHelpText;
  }

  /**
   * Branded logo
   */
  @JsonProperty("logo")
  public Image logo;

  /**
   * Gets {@link #logo}.
   *
   * @return the value of {@link #logo}
   */
  public Image getLogo() {
    return logo;
  }

  /**
   * Branded login page background
   */
  @JsonProperty("login_page_background_image")
  public Image loginPageBackgroundImage;

  /**
   * Gets {@link #loginPageBackgroundImage}.
   *
   * @return the value of {@link #loginPageBackgroundImage}
   */
  public Image getLoginPageBackgroundImage() {
    return loginPageBackgroundImage;
  }

  /**
   * Number of prior passwords to disallow
   */
  @JsonProperty("max_prior_passwords")
  public Long maxPriorPasswords;

  /**
   * Gets {@link #maxPriorPasswords}.
   *
   * @return the value of {@link #maxPriorPasswords}
   */
  public Long getMaxPriorPasswords() {
    return maxPriorPasswords;
  }

  /**
   * Site settings managed by the parent site. Each key identifies a managed setting, with a null value. Non-secret effective values are returned in their own Site settings fields.
   */
  @JsonProperty("managed_site_settings")
  public Object managedSiteSettings;

  /**
   * Gets {@link #managedSiteSettings}.
   *
   * @return the value of {@link #managedSiteSettings}
   */
  public Object getManagedSiteSettings() {
    return managedSiteSettings;
  }

  /**
   * A message to show users when they connect via FTP or SFTP.
   */
  @JsonProperty("motd_text")
  public String motdText;

  /**
   * Gets {@link #motdText}.
   *
   * @return the value of {@link #motdText}
   */
  public String getMotdText() {
    return motdText;
  }

  /**
   * Show message to users connecting via FTP
   */
  @JsonProperty("motd_use_for_ftp")
  public Boolean motdUseForFtp;

  /**
   * Gets {@link #motdUseForFtp}.
   *
   * @return the value of {@link #motdUseForFtp}
   */
  public Boolean getMotdUseForFtp() {
    return motdUseForFtp;
  }

  /**
   * Show message to users connecting via SFTP
   */
  @JsonProperty("motd_use_for_sftp")
  public Boolean motdUseForSftp;

  /**
   * Gets {@link #motdUseForSftp}.
   *
   * @return the value of {@link #motdUseForSftp}
   */
  public Boolean getMotdUseForSftp() {
    return motdUseForSftp;
  }

  /**
   * Next billing amount
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("next_billing_amount")
  public BigDecimal nextBillingAmount;

  /**
   * Gets {@link #nextBillingAmount}.
   *
   * @return the value of {@link #nextBillingAmount}
   */
  public BigDecimal getNextBillingAmount() {
    return nextBillingAmount;
  }

  /**
   * Next billing date
   */
  @JsonProperty("next_billing_date")
  public String nextBillingDate;

  /**
   * Gets {@link #nextBillingDate}.
   *
   * @return the value of {@link #nextBillingDate}
   */
  public String getNextBillingDate() {
    return nextBillingDate;
  }

  /**
   * If true, allows users to use a document editing integration.
   */
  @JsonProperty("office_integration_available")
  public Boolean officeIntegrationAvailable;

  /**
   * Gets {@link #officeIntegrationAvailable}.
   *
   * @return the value of {@link #officeIntegrationAvailable}
   */
  public Boolean getOfficeIntegrationAvailable() {
    return officeIntegrationAvailable;
  }

  /**
   * Which document editing integration to support. Files.com Editor or Microsoft Office for the Web.
   */
  @JsonProperty("office_integration_type")
  public String officeIntegrationType;

  /**
   * Gets {@link #officeIntegrationType}.
   *
   * @return the value of {@link #officeIntegrationType}
   */
  public String getOfficeIntegrationType() {
    return officeIntegrationType;
  }

  /**
   * Link to scheduling a meeting with our Sales team
   */
  @JsonProperty("oncehub_link")
  public String oncehubLink;

  /**
   * Gets {@link #oncehubLink}.
   *
   * @return the value of {@link #oncehubLink}
   */
  public String getOncehubLink() {
    return oncehubLink;
  }

  /**
   * Use servers in the USA only?
   */
  @JsonProperty("opt_out_global")
  public Boolean optOutGlobal;

  /**
   * Gets {@link #optOutGlobal}.
   *
   * @return the value of {@link #optOutGlobal}
   */
  public Boolean getOptOutGlobal() {
    return optOutGlobal;
  }

  /**
   * Is this site's billing overdue?
   */
  @JsonProperty("overdue")
  public Boolean overdue;

  /**
   * Gets {@link #overdue}.
   *
   * @return the value of {@link #overdue}
   */
  public Boolean getOverdue() {
    return overdue;
  }

  /**
   * Shortest password length for users
   */
  @JsonProperty("password_min_length")
  public Long passwordMinLength;

  /**
   * Gets {@link #passwordMinLength}.
   *
   * @return the value of {@link #passwordMinLength}
   */
  public Long getPasswordMinLength() {
    return passwordMinLength;
  }

  /**
   * Require a letter in passwords?
   */
  @JsonProperty("password_require_letter")
  public Boolean passwordRequireLetter;

  /**
   * Gets {@link #passwordRequireLetter}.
   *
   * @return the value of {@link #passwordRequireLetter}
   */
  public Boolean getPasswordRequireLetter() {
    return passwordRequireLetter;
  }

  /**
   * Require lower and upper case letters in passwords?
   */
  @JsonProperty("password_require_mixed")
  public Boolean passwordRequireMixed;

  /**
   * Gets {@link #passwordRequireMixed}.
   *
   * @return the value of {@link #passwordRequireMixed}
   */
  public Boolean getPasswordRequireMixed() {
    return passwordRequireMixed;
  }

  /**
   * Require a number in passwords?
   */
  @JsonProperty("password_require_number")
  public Boolean passwordRequireNumber;

  /**
   * Gets {@link #passwordRequireNumber}.
   *
   * @return the value of {@link #passwordRequireNumber}
   */
  public Boolean getPasswordRequireNumber() {
    return passwordRequireNumber;
  }

  /**
   * Require special characters in password?
   */
  @JsonProperty("password_require_special")
  public Boolean passwordRequireSpecial;

  /**
   * Gets {@link #passwordRequireSpecial}.
   *
   * @return the value of {@link #passwordRequireSpecial}
   */
  public Boolean getPasswordRequireSpecial() {
    return passwordRequireSpecial;
  }

  /**
   * Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
   */
  @JsonProperty("password_require_unbreached")
  public Boolean passwordRequireUnbreached;

  /**
   * Gets {@link #passwordRequireUnbreached}.
   *
   * @return the value of {@link #passwordRequireUnbreached}
   */
  public Boolean getPasswordRequireUnbreached() {
    return passwordRequireUnbreached;
  }

  /**
   * Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
   */
  @JsonProperty("password_requirements_apply_to_bundles")
  public Boolean passwordRequirementsApplyToBundles;

  /**
   * Gets {@link #passwordRequirementsApplyToBundles}.
   *
   * @return the value of {@link #passwordRequirementsApplyToBundles}
   */
  public Boolean getPasswordRequirementsApplyToBundles() {
    return passwordRequirementsApplyToBundles;
  }

  /**
   * Number of days password is valid
   */
  @JsonProperty("password_validity_days")
  public Long passwordValidityDays;

  /**
   * Gets {@link #passwordValidityDays}.
   *
   * @return the value of {@link #passwordValidityDays}
   */
  public Long getPasswordValidityDays() {
    return passwordValidityDays;
  }

  /**
   * Site phone number
   */
  @JsonProperty("phone")
  public String phone;

  /**
   * Gets {@link #phone}.
   *
   * @return the value of {@link #phone}
   */
  public String getPhone() {
    return phone;
  }

  /**
   * If true, we will ensure that all internal communications with any remote server are made through the primary region of the site. This setting overrides individual remote server settings.
   */
  @JsonProperty("pin_all_remote_servers_to_site_region")
  public Boolean pinAllRemoteServersToSiteRegion;

  /**
   * Gets {@link #pinAllRemoteServersToSiteRegion}.
   *
   * @return the value of {@link #pinAllRemoteServersToSiteRegion}
   */
  public Boolean getPinAllRemoteServersToSiteRegion() {
    return pinAllRemoteServersToSiteRegion;
  }

  /**
   * If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.
   */
  @JsonProperty("prevent_root_permissions_for_non_site_admins")
  public Boolean preventRootPermissionsForNonSiteAdmins;

  /**
   * Gets {@link #preventRootPermissionsForNonSiteAdmins}.
   *
   * @return the value of {@link #preventRootPermissionsForNonSiteAdmins}
   */
  public Boolean getPreventRootPermissionsForNonSiteAdmins() {
    return preventRootPermissionsForNonSiteAdmins;
  }

  /**
   * If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.
   */
  @JsonProperty("protocol_access_groups_only")
  public Boolean protocolAccessGroupsOnly;

  /**
   * Gets {@link #protocolAccessGroupsOnly}.
   *
   * @return the value of {@link #protocolAccessGroupsOnly}
   */
  public Boolean getProtocolAccessGroupsOnly() {
    return protocolAccessGroupsOnly;
  }

  /**
   * Require two-factor authentication for all users?
   */
  @JsonProperty("require_2fa")
  public Boolean require2fa;

  /**
   * Gets {@link #require2fa}.
   *
   * @return the value of {@link #require2fa}
   */
  public Boolean getRequire2fa() {
    return require2fa;
  }

  /**
   * If true, only site admins may create, modify, or delete any behavior at the site root, or a skip that would disable one.
   */
  @JsonProperty("restrict_root_folder_behaviors_to_site_admins")
  public Boolean restrictRootFolderBehaviorsToSiteAdmins;

  /**
   * Gets {@link #restrictRootFolderBehaviorsToSiteAdmins}.
   *
   * @return the value of {@link #restrictRootFolderBehaviorsToSiteAdmins}
   */
  public Boolean getRestrictRootFolderBehaviorsToSiteAdmins() {
    return restrictRootFolderBehaviorsToSiteAdmins;
  }

  /**
   * If true, supported protective behaviors at the site root also apply within named workspaces. Requires restrict_root_folder_behaviors_to_site_admins to be enabled.
   */
  @JsonProperty("root_folder_behaviors_apply_to_workspaces")
  public Boolean rootFolderBehaviorsApplyToWorkspaces;

  /**
   * Gets {@link #rootFolderBehaviorsApplyToWorkspaces}.
   *
   * @return the value of {@link #rootFolderBehaviorsApplyToWorkspaces}
   */
  public Boolean getRootFolderBehaviorsApplyToWorkspaces() {
    return rootFolderBehaviorsApplyToWorkspaces;
  }

  /**
   * If true, SSO users using the default user-level two-factor authentication setting are exempt from the site-wide two-factor authentication requirement.
   */
  @JsonProperty("require_2fa_exempt_all_sso_users")
  public Boolean require2faExemptAllSsoUsers;

  /**
   * Gets {@link #require2faExemptAllSsoUsers}.
   *
   * @return the value of {@link #require2faExemptAllSsoUsers}
   */
  public Boolean getRequire2faExemptAllSsoUsers() {
    return require2faExemptAllSsoUsers;
  }

  /**
   * If set, requirement for two-factor authentication has been scheduled to end on this date-time.
   */
  @JsonProperty("require_2fa_stop_time")
  public Date require2faStopTime;

  /**
   * Gets {@link #require2faStopTime}.
   *
   * @return the value of {@link #require2faStopTime}
   */
  public Date getRequire2faStopTime() {
    return require2faStopTime;
  }

  /**
   * Auto-removes bundles for disabled/deleted users and enforces bundle expiry within user access period.
   */
  @JsonProperty("revoke_bundle_access_on_disable_or_delete")
  public Boolean revokeBundleAccessOnDisableOrDelete;

  /**
   * Gets {@link #revokeBundleAccessOnDisableOrDelete}.
   *
   * @return the value of {@link #revokeBundleAccessOnDisableOrDelete}
   */
  public Boolean getRevokeBundleAccessOnDisableOrDelete() {
    return revokeBundleAccessOnDisableOrDelete;
  }

  /**
   * What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
   */
  @JsonProperty("require_2fa_user_type")
  public String require2faUserType;

  /**
   * Gets {@link #require2faUserType}.
   *
   * @return the value of {@link #require2faUserType}
   */
  public String getRequire2faUserType() {
    return require2faUserType;
  }

  /**
   * If true, we will hide the 'Remember Me' box on Inbox and Bundle registration pages, requiring that the user logout and log back in every time they visit the page.
   */
  @JsonProperty("require_logout_from_bundles_and_inboxes")
  public Boolean requireLogoutFromBundlesAndInboxes;

  /**
   * Gets {@link #requireLogoutFromBundlesAndInboxes}.
   *
   * @return the value of {@link #requireLogoutFromBundlesAndInboxes}
   */
  public Boolean getRequireLogoutFromBundlesAndInboxes() {
    return requireLogoutFromBundlesAndInboxes;
  }

  /**
   * Current session
   */
  @JsonProperty("session")
  public Session session;

  /**
   * Gets {@link #session}.
   *
   * @return the value of {@link #session}
   */
  public Session getSession() {
    return session;
  }

  /**
   * Is the S3-compatible endpoint enabled for all users and workspaces on this site? Defaults to true. When false, user and group S3 permissions do not allow access.
   */
  @JsonProperty("s3_compatible_endpoint_enabled")
  public Boolean s3CompatibleEndpointEnabled;

  /**
   * Gets {@link #s3CompatibleEndpointEnabled}.
   *
   * @return the value of {@link #s3CompatibleEndpointEnabled}
   */
  public Boolean getS3CompatibleEndpointEnabled() {
    return s3CompatibleEndpointEnabled;
  }

  /**
   * Is SFTP enabled?
   */
  @JsonProperty("sftp_enabled")
  public Boolean sftpEnabled;

  /**
   * Gets {@link #sftpEnabled}.
   *
   * @return the value of {@link #sftpEnabled}
   */
  public Boolean getSftpEnabled() {
    return sftpEnabled;
  }

  /**
   * Finalize partial SFTP uploads from interrupted connections? Default: true.
   */
  @JsonProperty("sftp_finalize_partial_uploads")
  public Boolean sftpFinalizePartialUploads;

  /**
   * Gets {@link #sftpFinalizePartialUploads}.
   *
   * @return the value of {@link #sftpFinalizePartialUploads}
   */
  public Boolean getSftpFinalizePartialUploads() {
    return sftpFinalizePartialUploads;
  }

  /**
   * Sftp Host Key Type
   */
  @JsonProperty("sftp_host_key_type")
  public String sftpHostKeyType;

  /**
   * Gets {@link #sftpHostKeyType}.
   *
   * @return the value of {@link #sftpHostKeyType}
   */
  public String getSftpHostKeyType() {
    return sftpHostKeyType;
  }

  /**
   * Id of the currently selected custom SFTP Host Key
   */
  @JsonProperty("active_sftp_host_key_id")
  public Long activeSftpHostKeyId;

  /**
   * Gets {@link #activeSftpHostKeyId}.
   *
   * @return the value of {@link #activeSftpHostKeyId}
   */
  public Long getActiveSftpHostKeyId() {
    return activeSftpHostKeyId;
  }

  /**
   * Ids of the selected custom SFTP Host Keys
   */
  @JsonProperty("active_sftp_host_key_ids")
  public Long[] activeSftpHostKeyIds;

  /**
   * Gets {@link #activeSftpHostKeyIds}.
   *
   * @return the value of {@link #activeSftpHostKeyIds}
   */
  public Long[] getActiveSftpHostKeyIds() {
    return activeSftpHostKeyIds;
  }

  /**
   * If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.
   */
  @JsonProperty("sftp_insecure_ciphers")
  public Boolean sftpInsecureCiphers;

  /**
   * Gets {@link #sftpInsecureCiphers}.
   *
   * @return the value of {@link #sftpInsecureCiphers}
   */
  public Boolean getSftpInsecureCiphers() {
    return sftpInsecureCiphers;
  }

  /**
   * If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.
   */
  @JsonProperty("sftp_insecure_diffie_hellman")
  public Boolean sftpInsecureDiffieHellman;

  /**
   * Gets {@link #sftpInsecureDiffieHellman}.
   *
   * @return the value of {@link #sftpInsecureDiffieHellman}
   */
  public Boolean getSftpInsecureDiffieHellman() {
    return sftpInsecureDiffieHellman;
  }

  /**
   * Use user FTP roots also for SFTP?
   */
  @JsonProperty("sftp_user_root_enabled")
  public Boolean sftpUserRootEnabled;

  /**
   * Gets {@link #sftpUserRootEnabled}.
   *
   * @return the value of {@link #sftpUserRootEnabled}
   */
  public Boolean getSftpUserRootEnabled() {
    return sftpUserRootEnabled;
  }

  /**
   * Allow bundle creation
   */
  @JsonProperty("sharing_enabled")
  public Boolean sharingEnabled;

  /**
   * Gets {@link #sharingEnabled}.
   *
   * @return the value of {@link #sharingEnabled}
   */
  public Boolean getSharingEnabled() {
    return sharingEnabled;
  }

  /**
   * Show log in link in user notifications?
   */
  @JsonProperty("show_user_notifications_log_in_link")
  public Boolean showUserNotificationsLogInLink;

  /**
   * Gets {@link #showUserNotificationsLogInLink}.
   *
   * @return the value of {@link #showUserNotificationsLogInLink}
   */
  public Boolean getShowUserNotificationsLogInLink() {
    return showUserNotificationsLogInLink;
  }

  /**
   * Show request access link for users without access?  Currently unused.
   */
  @JsonProperty("show_request_access_link")
  public Boolean showRequestAccessLink;

  /**
   * Gets {@link #showRequestAccessLink}.
   *
   * @return the value of {@link #showRequestAccessLink}
   */
  public Boolean getShowRequestAccessLink() {
    return showRequestAccessLink;
  }

  /**
   * Custom site footer text for authenticated pages
   */
  @JsonProperty("site_footer")
  public String siteFooter;

  /**
   * Gets {@link #siteFooter}.
   *
   * @return the value of {@link #siteFooter}
   */
  public String getSiteFooter() {
    return siteFooter;
  }

  /**
   * Custom site header text for authenticated pages
   */
  @JsonProperty("site_header")
  public String siteHeader;

  /**
   * Gets {@link #siteHeader}.
   *
   * @return the value of {@link #siteHeader}
   */
  public String getSiteHeader() {
    return siteHeader;
  }

  /**
   * Custom site footer text for public pages
   */
  @JsonProperty("site_public_footer")
  public String sitePublicFooter;

  /**
   * Gets {@link #sitePublicFooter}.
   *
   * @return the value of {@link #sitePublicFooter}
   */
  public String getSitePublicFooter() {
    return sitePublicFooter;
  }

  /**
   * Custom site header text for public pages
   */
  @JsonProperty("site_public_header")
  public String sitePublicHeader;

  /**
   * Gets {@link #sitePublicHeader}.
   *
   * @return the value of {@link #sitePublicHeader}
   */
  public String getSitePublicHeader() {
    return sitePublicHeader;
  }

  /**
   * SMTP server hostname or IP
   */
  @JsonProperty("smtp_address")
  public String smtpAddress;

  /**
   * Gets {@link #smtpAddress}.
   *
   * @return the value of {@link #smtpAddress}
   */
  public String getSmtpAddress() {
    return smtpAddress;
  }

  /**
   * SMTP server authentication type
   */
  @JsonProperty("smtp_authentication")
  public String smtpAuthentication;

  /**
   * Gets {@link #smtpAuthentication}.
   *
   * @return the value of {@link #smtpAuthentication}
   */
  public String getSmtpAuthentication() {
    return smtpAuthentication;
  }

  /**
   * From address to use when mailing through custom SMTP
   */
  @JsonProperty("smtp_from")
  public String smtpFrom;

  /**
   * Gets {@link #smtpFrom}.
   *
   * @return the value of {@link #smtpFrom}
   */
  public String getSmtpFrom() {
    return smtpFrom;
  }

  /**
   * SMTP server port
   */
  @JsonProperty("smtp_port")
  public Long smtpPort;

  /**
   * Gets {@link #smtpPort}.
   *
   * @return the value of {@link #smtpPort}
   */
  public Long getSmtpPort() {
    return smtpPort;
  }

  /**
   * Custom SMTP encryption mode: if_available (default) uses STARTTLS when offered and otherwise sends credentials and messages unencrypted. A certificate-verified STARTTLS connection automatically changes if_available to require unless smtp_ssl is managed by a parent policy. require requires STARTTLS before authentication; require_implicit uses TLS from connection start; never disables TLS. TLS verifies the server certificate against smtp_address. Encryption is never automatically downgraded.
   */
  @JsonProperty("smtp_ssl")
  public String smtpSsl;

  /**
   * Gets {@link #smtpSsl}.
   *
   * @return the value of {@link #smtpSsl}
   */
  public String getSmtpSsl() {
    return smtpSsl;
  }

  /**
   * SMTP server username
   */
  @JsonProperty("smtp_username")
  public String smtpUsername;

  /**
   * Gets {@link #smtpUsername}.
   *
   * @return the value of {@link #smtpUsername}
   */
  public String getSmtpUsername() {
    return smtpUsername;
  }

  /**
   * Session expiry in minutes
   */
  @JsonProperty("session_expiry_minutes")
  public Long sessionExpiryMinutes;

  /**
   * Gets {@link #sessionExpiryMinutes}.
   *
   * @return the value of {@link #sessionExpiryMinutes}
   */
  public Long getSessionExpiryMinutes() {
    return sessionExpiryMinutes;
  }

  /**
   * Allow snapshot share links creation
   */
  @JsonProperty("snapshot_sharing_enabled")
  public Boolean snapshotSharingEnabled;

  /**
   * Gets {@link #snapshotSharingEnabled}.
   *
   * @return the value of {@link #snapshotSharingEnabled}
   */
  public Boolean getSnapshotSharingEnabled() {
    return snapshotSharingEnabled;
  }

  /**
   * Is SSL required?  Disabling this is insecure.
   */
  @JsonProperty("ssl_required")
  public Boolean sslRequired;

  /**
   * Gets {@link #sslRequired}.
   *
   * @return the value of {@link #sslRequired}
   */
  public Boolean getSslRequired() {
    return sslRequired;
  }

  /**
   * Site subdomain
   */
  @JsonProperty("subdomain")
  public String subdomain;

  /**
   * Gets {@link #subdomain}.
   *
   * @return the value of {@link #subdomain}
   */
  public String getSubdomain() {
    return subdomain;
  }

  /**
   * If switching plans, when does the new plan take effect?
   */
  @JsonProperty("switch_to_plan_date")
  public Date switchToPlanDate;

  /**
   * Gets {@link #switchToPlanDate}.
   *
   * @return the value of {@link #switchToPlanDate}
   */
  public Date getSwitchToPlanDate() {
    return switchToPlanDate;
  }

  /**
   * Number of days left in trial
   */
  @JsonProperty("trial_days_left")
  public Long trialDaysLeft;

  /**
   * Gets {@link #trialDaysLeft}.
   *
   * @return the value of {@link #trialDaysLeft}
   */
  public Long getTrialDaysLeft() {
    return trialDaysLeft;
  }

  /**
   * When does this Site trial expire?
   */
  @JsonProperty("trial_until")
  public Date trialUntil;

  /**
   * Gets {@link #trialUntil}.
   *
   * @return the value of {@link #trialUntil}
   */
  public Date getTrialUntil() {
    return trialUntil;
  }

  /**
   * If using custom SMTP, should we use dedicated IPs to deliver emails?
   */
  @JsonProperty("use_dedicated_ips_for_smtp")
  public Boolean useDedicatedIpsForSmtp;

  /**
   * Gets {@link #useDedicatedIpsForSmtp}.
   *
   * @return the value of {@link #useDedicatedIpsForSmtp}
   */
  public Boolean getUseDedicatedIpsForSmtp() {
    return useDedicatedIpsForSmtp;
  }

  /**
   * Allow uploaders to set `provided_modified_at` for uploaded files?
   */
  @JsonProperty("use_provided_modified_at")
  public Boolean useProvidedModifiedAt;

  /**
   * Gets {@link #useProvidedModifiedAt}.
   *
   * @return the value of {@link #useProvidedModifiedAt}
   */
  public Boolean getUseProvidedModifiedAt() {
    return useProvidedModifiedAt;
  }

  /**
   * User of current session
   */
  @JsonProperty("user")
  public User user;

  /**
   * Gets {@link #user}.
   *
   * @return the value of {@link #user}
   */
  public User getUser() {
    return user;
  }

  /**
   * Will users be locked out after incorrect login attempts?
   */
  @JsonProperty("user_lockout")
  public Boolean userLockout;

  /**
   * Gets {@link #userLockout}.
   *
   * @return the value of {@link #userLockout}
   */
  public Boolean getUserLockout() {
    return userLockout;
  }

  /**
   * How many hours to lock user out for failed password?
   */
  @JsonProperty("user_lockout_lock_period")
  public Long userLockoutLockPeriod;

  /**
   * Gets {@link #userLockoutLockPeriod}.
   *
   * @return the value of {@link #userLockoutLockPeriod}
   */
  public Long getUserLockoutLockPeriod() {
    return userLockoutLockPeriod;
  }

  /**
   * Number of login tries within `user_lockout_within` hours before users are locked out
   */
  @JsonProperty("user_lockout_tries")
  public Long userLockoutTries;

  /**
   * Gets {@link #userLockoutTries}.
   *
   * @return the value of {@link #userLockoutTries}
   */
  public Long getUserLockoutTries() {
    return userLockoutTries;
  }

  /**
   * Number of hours for user lockout window
   */
  @JsonProperty("user_lockout_within")
  public Long userLockoutWithin;

  /**
   * Gets {@link #userLockoutWithin}.
   *
   * @return the value of {@link #userLockoutWithin}
   */
  public Long getUserLockoutWithin() {
    return userLockoutWithin;
  }

  /**
   * Enable User Requests feature
   */
  @JsonProperty("user_requests_enabled")
  public Boolean userRequestsEnabled;

  /**
   * Gets {@link #userRequestsEnabled}.
   *
   * @return the value of {@link #userRequestsEnabled}
   */
  public Boolean getUserRequestsEnabled() {
    return userRequestsEnabled;
  }

  /**
   * Send email to site admins when a user request is received?
   */
  @JsonProperty("user_requests_notify_admins")
  public Boolean userRequestsNotifyAdmins;

  /**
   * Gets {@link #userRequestsNotifyAdmins}.
   *
   * @return the value of {@link #userRequestsNotifyAdmins}
   */
  public Boolean getUserRequestsNotifyAdmins() {
    return userRequestsNotifyAdmins;
  }

  /**
   * Allow users to create their own API keys?
   */
  @JsonProperty("users_can_create_api_keys")
  public Boolean usersCanCreateApiKeys;

  /**
   * Gets {@link #usersCanCreateApiKeys}.
   *
   * @return the value of {@link #usersCanCreateApiKeys}
   */
  public Boolean getUsersCanCreateApiKeys() {
    return usersCanCreateApiKeys;
  }

  /**
   * Allow users to create their own SSH keys?
   */
  @JsonProperty("users_can_create_ssh_keys")
  public Boolean usersCanCreateSshKeys;

  /**
   * Gets {@link #usersCanCreateSshKeys}.
   *
   * @return the value of {@link #usersCanCreateSshKeys}
   */
  public Boolean getUsersCanCreateSshKeys() {
    return usersCanCreateSshKeys;
  }

  /**
   * How usernames are displayed in the web UI. Can be `username_only`, `full_name_only`, `full_name_username`, `full_name_company`, or `full_name_username_company`.
   */
  @JsonProperty("username_display")
  public String usernameDisplay;

  /**
   * Gets {@link #usernameDisplay}.
   *
   * @return the value of {@link #usernameDisplay}
   */
  public String getUsernameDisplay() {
    return usernameDisplay;
  }

  /**
   * Custom text send in user welcome email
   */
  @JsonProperty("welcome_custom_text")
  public String welcomeCustomText;

  /**
   * Gets {@link #welcomeCustomText}.
   *
   * @return the value of {@link #welcomeCustomText}
   */
  public String getWelcomeCustomText() {
    return welcomeCustomText;
  }

  /**
   * Custom footer text for system-generated emails (as Markdown). Supports standard strftime date/time patterns like %Y (4-digit year), %m (month), %d (day).
   */
  @JsonProperty("email_footer_custom_text")
  public String emailFooterCustomText;

  /**
   * Gets {@link #emailFooterCustomText}.
   *
   * @return the value of {@link #emailFooterCustomText}
   */
  public String getEmailFooterCustomText() {
    return emailFooterCustomText;
  }

  /**
   * Include this email in welcome emails if enabled
   */
  @JsonProperty("welcome_email_cc")
  public String welcomeEmailCc;

  /**
   * Gets {@link #welcomeEmailCc}.
   *
   * @return the value of {@link #welcomeEmailCc}
   */
  public String getWelcomeEmailCc() {
    return welcomeEmailCc;
  }

  /**
   * Include this email subject in welcome emails if enabled
   */
  @JsonProperty("welcome_email_subject")
  public String welcomeEmailSubject;

  /**
   * Gets {@link #welcomeEmailSubject}.
   *
   * @return the value of {@link #welcomeEmailSubject}
   */
  public String getWelcomeEmailSubject() {
    return welcomeEmailSubject;
  }

  /**
   * Will the welcome email be sent to new users?
   */
  @JsonProperty("welcome_email_enabled")
  public Boolean welcomeEmailEnabled;

  /**
   * Gets {@link #welcomeEmailEnabled}.
   *
   * @return the value of {@link #welcomeEmailEnabled}
   */
  public Boolean getWelcomeEmailEnabled() {
    return welcomeEmailEnabled;
  }

  /**
   * Does the welcome screen appear?
   */
  @JsonProperty("welcome_screen")
  public String welcomeScreen;

  /**
   * Gets {@link #welcomeScreen}.
   *
   * @return the value of {@link #welcomeScreen}
   */
  public String getWelcomeScreen() {
    return welcomeScreen;
  }

  /**
   * Does FTP user Windows emulation mode?
   */
  @JsonProperty("windows_mode_ftp")
  public Boolean windowsModeFtp;

  /**
   * Gets {@link #windowsModeFtp}.
   *
   * @return the value of {@link #windowsModeFtp}
   */
  public Boolean getWindowsModeFtp() {
    return windowsModeFtp;
  }


  /**
   * Show Site Settings
   *
   * @see #get(HashMap, HashMap)
   */
  public static Site get() throws RuntimeException {
    return get(null, null);
  }

  /**
   * Show Site Settings
   *
   * @see #get(HashMap, HashMap)
   */
  public static Site get(HashMap<String, Object> parameters) throws RuntimeException {
    return get(parameters, null);
  }


  /**
   * Show Site Settings
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Site}
   */
  public static Site get(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sites().get(parameters, options);
  }


  /**
   * Get the most recent usage snapshot (usage data for billing purposes) for a Site
   *
   * @see #getUsage(HashMap, HashMap)
   */
  public static UsageSnapshot getUsage() throws RuntimeException {
    return getUsage(null, null);
  }

  /**
   * Get the most recent usage snapshot (usage data for billing purposes) for a Site
   *
   * @see #getUsage(HashMap, HashMap)
   */
  public static UsageSnapshot getUsage(HashMap<String, Object> parameters) throws RuntimeException {
    return getUsage(parameters, null);
  }


  /**
   * Get the most recent usage snapshot (usage data for billing purposes) for a Site
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UsageSnapshot}
   */
  public static UsageSnapshot getUsage(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sites().getUsage(parameters, options);
  }


  /**
   * Update Site Settings
   *
   * @see #update(HashMap, HashMap)
   */
  public static Site update() throws RuntimeException {
    return update(null, null);
  }

  /**
   * Update Site Settings
   *
   * @see #update(HashMap, HashMap)
   */
  public static Site update(HashMap<String, Object> parameters) throws RuntimeException {
    return update(parameters, null);
  }


  /**
   * Update Site Settings
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Site name</li>
   * <li>{@code subdomain} (String): Site subdomain</li>
   * <li>{@code domain} (String): Custom domain</li>
   * <li>{@code domain_hsts_header} (Boolean): Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?</li>
   * <li>{@code domain_letsencrypt_chain} (String): Letsencrypt chain to use when registering SSL Certificate for domain. No longer used as of 2026.</li>
   * <li>{@code email} (String): Main email for this site</li>
   * <li>{@code reply_to_email} (String): Reply-to email for this site</li>
   * <li>{@code allow_bundle_names} (Boolean): Are manual Bundle names allowed?</li>
   * <li>{@code bundle_expiration} (Long): Site-wide Bundle expiration in days</li>
   * <li>{@code welcome_email_enabled} (Boolean): Will the welcome email be sent to new users?</li>
   * <li>{@code ask_about_overwrites} (Boolean): If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.</li>
   * <li>{@code show_request_access_link} (Boolean): Show request access link for users without access?  Currently unused.</li>
   * <li>{@code always_mkdir_parents} (Boolean): Create parent directories if they do not exist during uploads?  This is primarily used to work around broken upload clients that assume servers will perform this step.</li>
   * <li>{@code welcome_email_cc} (String): Include this email in welcome emails if enabled</li>
   * <li>{@code welcome_email_subject} (String): Include this email subject in welcome emails if enabled</li>
   * <li>{@code welcome_custom_text} (String): Custom text send in user welcome email</li>
   * <li>{@code language} (String): Site default language</li>
   * <li>{@code windows_mode_ftp} (Boolean): Does FTP user Windows emulation mode?</li>
   * <li>{@code default_time_zone} (String): Site default time zone</li>
   * <li>{@code desktop_app} (Boolean): Is the desktop app enabled?</li>
   * <li>{@code desktop_app_session_ip_pinning} (Boolean): Is desktop app session IP pinning enabled?</li>
   * <li>{@code desktop_app_session_lifetime} (Long): Desktop app session lifetime (in hours)</li>
   * <li>{@code mobile_app} (Boolean): Is the mobile app enabled?</li>
   * <li>{@code mobile_app_session_ip_pinning} (Boolean): Is mobile app session IP pinning enabled?</li>
   * <li>{@code mobile_app_session_lifetime} (Long): Mobile app session lifetime (in hours)</li>
   * <li>{@code folder_permissions_groups_only} (Boolean): If true, permissions for this site must be bound to a group (not a user).</li>
   * <li>{@code welcome_screen} (String): Does the welcome screen appear?</li>
   * <li>{@code office_integration_available} (Boolean): If true, allows users to use a document editing integration.</li>
   * <li>{@code office_integration_type} (String): Which document editing integration to support. Files.com Editor or Microsoft Office for the Web.</li>
   * <li>{@code pin_all_remote_servers_to_site_region} (Boolean): If true, we will ensure that all internal communications with any remote server are made through the primary region of the site. This setting overrides individual remote server settings.</li>
   * <li>{@code motd_text} (String): A message to show users when they connect via FTP or SFTP.</li>
   * <li>{@code motd_use_for_ftp} (Boolean): Show message to users connecting via FTP</li>
   * <li>{@code motd_use_for_sftp} (Boolean): Show message to users connecting via SFTP</li>
   * <li>{@code left_navigation_visibility} (Object): Visibility settings for account navigation</li>
   * <li>{@code disable_all_ai_features} (Boolean): If true, all AI features are disabled for this site.</li>
   * <li>{@code ai_feature_availability} (Object): Availability settings for AI features. Each feature requires the site_admins, workspace_admins, folder_admins, and all_users keys. Optional selected_group_members defaults to false; when true, members of at least one group in group_ids get access regardless of the other options. All availability options are additive: any enabled option matching the user grants access. Optional group_ids is an array of integer IDs of active groups on this site, from any workspace. Omitted or empty group_ids grants no access through selected_group_members and does not affect other options. Disabling all AI features overrides these settings.</li>
   * <li>{@code mcp_dcr_enabled} (Boolean): Is OAuth DCR (dynamic client registration) for MCP enabled?</li>
   * <li>{@code additional_text_file_types} (String[]): Additional extensions that are considered text files</li>
   * <li>{@code bundle_require_note} (Boolean): Do Bundles require internal notes?</li>
   * <li>{@code bundle_send_shared_receipts} (Boolean): Do Bundle creators receive receipts of invitations?</li>
   * <li>{@code bundles_default_owned_by_primary_group} (Boolean): If true, new Share Links created by a user with a primary group will default to that group as owner.</li>
   * <li>{@code calculate_file_checksums_crc32} (Boolean): Calculate CRC32 checksums for files?</li>
   * <li>{@code calculate_file_checksums_md5} (Boolean): Calculate MD5 checksums for files?</li>
   * <li>{@code calculate_file_checksums_sha1} (Boolean): Calculate SHA1 checksums for files?</li>
   * <li>{@code calculate_file_checksums_sha256} (Boolean): Calculate SHA256 checksums for files?</li>
   * <li>{@code legacy_checksums_mode} (Boolean): Use legacy checksums mode?</li>
   * <li>{@code migrate_remote_server_sync_to_sync} (Boolean): If true, we will migrate all remote server syncs to the new Sync model.</li>
   * <li>{@code as2_message_retention_days} (Long): Number of days to retain AS2 messages (incoming and outgoing).</li>
   * <li>{@code file_activity_events_enabled} (Boolean): If true, file operations produce file_action.activity.v1 EventChannel events in all workspaces. Defaults to false; subscription filters and workspace access still control delivery.</li>
   * <li>{@code username_display} (String): How usernames are displayed in the web UI. Can be `username_only`, `full_name_only`, `full_name_username`, `full_name_company`, or `full_name_username_company`.</li>
   * <li>{@code session_expiry_minutes} (Long): Session expiry in minutes</li>
   * <li>{@code ssl_required} (Boolean): Is SSL required?  Disabling this is insecure.</li>
   * <li>{@code sftp_insecure_ciphers} (Boolean): If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.</li>
   * <li>{@code sftp_insecure_diffie_hellman} (Boolean): If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.</li>
   * <li>{@code disable_files_certificate_generation} (Boolean): If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.</li>
   * <li>{@code fedramp} (Boolean): Are FedRAMP security restrictions enabled for this site?</li>
   * <li>{@code user_lockout} (Boolean): Will users be locked out after incorrect login attempts?</li>
   * <li>{@code user_lockout_tries} (Long): Number of login tries within `user_lockout_within` hours before users are locked out</li>
   * <li>{@code user_lockout_within} (Long): Number of hours for user lockout window</li>
   * <li>{@code user_lockout_lock_period} (Long): How many hours to lock user out for failed password?</li>
   * <li>{@code include_password_in_welcome_email} (Boolean): Include password in emails to new users?</li>
   * <li>{@code allowed_countries} (String): Comma separated list of allowed Country codes</li>
   * <li>{@code allowed_ips} (String): List of allowed IP addresses</li>
   * <li>{@code allow_user_level_2fa_override} (Boolean): Allow the site-wide two-factor authentication requirement to be overriden on a per-user-basis?</li>
   * <li>{@code allow_user_level_allowed_ip_override} (Boolean): Allow the site-wide allowed IP restriction to be overriden on a per-user-basis?</li>
   * <li>{@code allow_user_level_ssl_override} (Boolean): Allow the site-wide FTP SSL requirement to be overriden on a per-user-basis?</li>
   * <li>{@code disallowed_countries} (String): Comma separated list of disallowed Country codes</li>
   * <li>{@code days_to_retain_backups} (Long): Number of days to keep deleted files</li>
   * <li>{@code max_prior_passwords} (Long): Number of prior passwords to disallow</li>
   * <li>{@code password_validity_days} (Long): Number of days password is valid</li>
   * <li>{@code password_min_length} (Long): Shortest password length for users</li>
   * <li>{@code password_require_letter} (Boolean): Require a letter in passwords?</li>
   * <li>{@code password_require_mixed} (Boolean): Require lower and upper case letters in passwords?</li>
   * <li>{@code password_require_special} (Boolean): Require special characters in password?</li>
   * <li>{@code password_require_number} (Boolean): Require a number in passwords?</li>
   * <li>{@code password_require_unbreached} (Boolean): Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)</li>
   * <li>{@code require_logout_from_bundles_and_inboxes} (Boolean): If true, we will hide the 'Remember Me' box on Inbox and Bundle registration pages, requiring that the user logout and log back in every time they visit the page.</li>
   * <li>{@code dav_user_root_enabled} (Boolean): Use user FTP roots also for WebDAV?</li>
   * <li>{@code sftp_user_root_enabled} (Boolean): Use user FTP roots also for SFTP?</li>
   * <li>{@code disable_password_reset} (Boolean): Is password reset disabled?</li>
   * <li>{@code immutable_files} (Boolean): Are files protected from modification?</li>
   * <li>{@code bundle_not_found_message} (String): Custom error message to show when bundle is not found.</li>
   * <li>{@code bundle_password_required} (Boolean): Do Bundles require password protection?</li>
   * <li>{@code bundle_require_registration} (Boolean): Do Bundles require registration?</li>
   * <li>{@code bundle_require_share_recipient} (Boolean): Do Bundles require recipients for sharing?</li>
   * <li>{@code bundle_send_one_time_password_to_recipient_at_registration} (Boolean): If true, new Share Links must send a one-time password to the recipient when they register. Requires bundle_require_share_recipient and cannot be enabled with bundle_password_required.</li>
   * <li>{@code bundle_registration_notifications} (String): Do Bundle owners receive registration notification?</li>
   * <li>{@code bundle_activity_notifications} (String): Do Bundle owners receive activity notifications?</li>
   * <li>{@code bundle_upload_receipt_notifications} (String): Do Bundle uploaders receive upload confirmation notifications?</li>
   * <li>{@code document_edits_in_bundle_allowed} (Boolean): If true, allow public viewers of Bundles with full permissions to use document editing integrations.</li>
   * <li>{@code password_requirements_apply_to_bundles} (Boolean): Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?</li>
   * <li>{@code prevent_root_permissions_for_non_site_admins} (Boolean): If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.</li>
   * <li>{@code restrict_root_folder_behaviors_to_site_admins} (Boolean): If true, only site admins may create, modify, or delete any behavior at the site root, or a skip that would disable one.</li>
   * <li>{@code root_folder_behaviors_apply_to_workspaces} (Boolean): If true, supported protective behaviors at the site root also apply within named workspaces. Requires restrict_root_folder_behaviors_to_site_admins to be enabled.</li>
   * <li>{@code opt_out_global} (Boolean): Use servers in the USA only?</li>
   * <li>{@code use_provided_modified_at} (Boolean): Allow uploaders to set `provided_modified_at` for uploaded files?</li>
   * <li>{@code custom_namespace} (Boolean): Is this site using a custom namespace for users?</li>
   * <li>{@code non_sso_groups_allowed} (Boolean): If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.</li>
   * <li>{@code non_sso_users_allowed} (Boolean): If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.</li>
   * <li>{@code sharing_enabled} (Boolean): Allow bundle creation</li>
   * <li>{@code snapshot_sharing_enabled} (Boolean): Allow snapshot share links creation</li>
   * <li>{@code user_requests_enabled} (Boolean): Enable User Requests feature</li>
   * <li>{@code user_requests_notify_admins} (Boolean): Send email to site admins when a user request is received?</li>
   * <li>{@code dav_enabled} (Boolean): Is WebDAV enabled?</li>
   * <li>{@code files_com_remote_server_enabled} (Boolean): Allow other Files.com sites to use this site's API keys for native Files.com Remote Server connections? Defaults to true and applies to all keys and workspaces on this site. When false, new pairings and access through existing connections are rejected without revoking keys or deleting connections. Re-enabling permits access again with usable keys. Does not disable this site's connections to other sites, ordinary API access, or Connected Sites.</li>
   * <li>{@code ftp_enabled} (Boolean): Is FTP enabled?</li>
   * <li>{@code s3_compatible_endpoint_enabled} (Boolean): Is the S3-compatible endpoint enabled for all users and workspaces on this site? Defaults to true. When false, user and group S3 permissions do not allow access.</li>
   * <li>{@code sftp_enabled} (Boolean): Is SFTP enabled?</li>
   * <li>{@code sftp_finalize_partial_uploads} (Boolean): Finalize partial SFTP uploads from interrupted connections? Default: true.</li>
   * <li>{@code users_can_create_api_keys} (Boolean): Allow users to create their own API keys?</li>
   * <li>{@code users_can_create_ssh_keys} (Boolean): Allow users to create their own SSH keys?</li>
   * <li>{@code show_user_notifications_log_in_link} (Boolean): Show log in link in user notifications?</li>
   * <li>{@code sftp_host_key_type} (String): Sftp Host Key Type</li>
   * <li>{@code active_sftp_host_key_id} (Long): Id of the currently selected custom SFTP Host Key</li>
   * <li>{@code active_sftp_host_key_ids} (Long[]): Ids of the selected custom SFTP Host Keys</li>
   * <li>{@code protocol_access_groups_only} (Boolean): If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.</li>
   * <li>{@code revoke_bundle_access_on_disable_or_delete} (Boolean): Auto-removes bundles for disabled/deleted users and enforces bundle expiry within user access period.</li>
   * <li>{@code group_admins_can_add_users} (Boolean): Allow group admins to create users in their groups</li>
   * <li>{@code group_admins_can_manage_group_memberships} (Boolean): Allow group admins to add or remove existing users in their groups</li>
   * <li>{@code group_admins_can_delete_users} (Boolean): Allow group admins to delete users in their groups</li>
   * <li>{@code group_admins_can_enable_disable_users} (Boolean): Allow group admins to enable or disable users in their groups</li>
   * <li>{@code group_admins_can_modify_users} (Boolean): Allow group admins to modify users in their groups</li>
   * <li>{@code group_admins_can_bypass_user_lifecycle_rules} (Boolean): Allow group admins to exempt users in their groups from lifecycle rules</li>
   * <li>{@code group_admins_can_reset_passwords} (Boolean): Allow group admins to reset passwords for users in their groups</li>
   * <li>{@code group_admins_can_set_user_password} (Boolean): Allow group admins to set password authentication method</li>
   * <li>{@code child_site_folder_admins_can_see_parent_site_directory} (Boolean): If true, administrators on this site's Child Sites, including Folder Admins, can list this site's Default Workspace users and groups and grant them folder permissions and notifications on Child Site paths.</li>
   * <li>{@code workspace_folder_admins_can_see_default_workspace_directory} (Boolean): If true, Workspace Administrators and Folder Admins in a Custom Workspace can list the Default Workspace users and groups that have permissions in that Workspace and choose them as Email Notification recipients.</li>
   * <li>{@code bundle_recipient_blacklist_free_email_domains} (Boolean): Disallow free email domains for Bundle/Inbox recipients?</li>
   * <li>{@code bundle_recipient_blacklist_domains} (String[]): List of email domains to disallow when entering a Bundle/Inbox recipients</li>
   * <li>{@code admins_bypass_locked_subfolders} (Boolean): Allow admins to bypass the locked subfolders setting.</li>
   * <li>{@code allowed_2fa_method_sms} (Boolean): Is SMS two factor authentication allowed?</li>
   * <li>{@code allowed_2fa_method_totp} (Boolean): Is TOTP two factor authentication allowed?</li>
   * <li>{@code allowed_2fa_method_webauthn} (Boolean): Is WebAuthn two factor authentication allowed?</li>
   * <li>{@code allowed_2fa_method_yubi} (Boolean): Is yubikey two factor authentication allowed?</li>
   * <li>{@code allowed_2fa_method_email} (Boolean): Is OTP via email two factor authentication allowed?</li>
   * <li>{@code allowed_2fa_method_static} (Boolean): Is OTP via static codes for two factor authentication allowed?</li>
   * <li>{@code allowed_2fa_method_bypass_for_ftp_sftp_dav} (Boolean): Are users allowed to configure their two factor authentication to be bypassed for FTP/SFTP/WebDAV?</li>
   * <li>{@code require_2fa} (Boolean): Require two-factor authentication for all users?</li>
   * <li>{@code require_2fa_exempt_all_sso_users} (Boolean): If true, SSO users using the default user-level two-factor authentication setting are exempt from the site-wide two-factor authentication requirement.</li>
   * <li>{@code require_2fa_user_type} (String): What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?</li>
   * <li>{@code color2_top} (String): Top bar background color</li>
   * <li>{@code color2_left} (String): Page link and button color</li>
   * <li>{@code color2_link} (String): Top bar link color</li>
   * <li>{@code color2_text} (String): Page link and button color</li>
   * <li>{@code color2_top_text} (String): Top bar text color</li>
   * <li>{@code site_header} (String): Custom site header text for authenticated pages</li>
   * <li>{@code site_footer} (String): Custom site footer text for authenticated pages</li>
   * <li>{@code site_public_header} (String): Custom site header text for public pages</li>
   * <li>{@code site_public_footer} (String): Custom site footer text for public pages</li>
   * <li>{@code login_help_text} (String): Login help text</li>
   * <li>{@code use_dedicated_ips_for_smtp} (Boolean): If using custom SMTP, should we use dedicated IPs to deliver emails?</li>
   * <li>{@code email_footer_custom_text} (String): Custom footer text for system-generated emails (as Markdown). Supports standard strftime date/time patterns like %Y (4-digit year), %m (month), %d (day).</li>
   * <li>{@code smtp_address} (String): SMTP server hostname or IP</li>
   * <li>{@code smtp_authentication} (String): SMTP server authentication type</li>
   * <li>{@code smtp_from} (String): From address to use when mailing through custom SMTP</li>
   * <li>{@code smtp_username} (String): SMTP server username</li>
   * <li>{@code smtp_port} (Long): SMTP server port</li>
   * <li>{@code smtp_ssl} (String): Custom SMTP encryption mode: if_available (default) uses STARTTLS when offered and otherwise sends credentials and messages unencrypted. A certificate-verified STARTTLS connection automatically changes if_available to require unless smtp_ssl is managed by a parent policy. require requires STARTTLS before authentication; require_implicit uses TLS from connection start; never disables TLS. TLS verifies the server certificate against smtp_address. Encryption is never automatically downgraded.</li>
   * <li>{@code ldap_enabled} (Boolean): Main LDAP setting: is LDAP enabled?</li>
   * <li>{@code ldap_type} (String): LDAP type</li>
   * <li>{@code ldap_host} (String): LDAP host</li>
   * <li>{@code ldap_host_2} (String): LDAP backup host</li>
   * <li>{@code ldap_host_3} (String): LDAP backup host</li>
   * <li>{@code ldap_port} (Long): LDAP port</li>
   * <li>{@code ldap_secure} (Boolean): Use secure LDAP?</li>
   * <li>{@code ldap_server_certificate} (String): How to validate the LDAP server certificate. `require_match` validates the certificate chain and hostname; `allow_any` disables certificate validation.</li>
   * <li>{@code ldap_username} (String): Username for signing in to LDAP server.</li>
   * <li>{@code ldap_username_field} (String): LDAP username field</li>
   * <li>{@code ldap_domain} (String): Domain name that will be appended to usernames</li>
   * <li>{@code ldap_user_action} (String): Should we sync users from LDAP server?</li>
   * <li>{@code ldap_group_action} (String): Should we sync groups from LDAP server?</li>
   * <li>{@code ldap_user_include_groups} (String): Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.</li>
   * <li>{@code ldap_group_exclusion} (String): Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.</li>
   * <li>{@code ldap_group_inclusion} (String): Comma or newline separated list of group names (with optional wildcards) to include when syncing.</li>
   * <li>{@code ldap_base_dn} (String): Base DN for looking up users in LDAP server</li>
   * <li>{@code uploads_via_email_authentication} (Boolean): Require email authentication, virus, and spam checks for incoming emails to Inboxes and Incoming Email Automations in every Workspace on this site?</li>
   * <li>{@code bundle_watermark_value} (Object): Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value</li>
   * <li>{@code icon16_file} (byte[] or Attachment): </li>
   * <li>{@code icon16_delete} (Boolean): If true, will delete the file stored in icon16</li>
   * <li>{@code icon32_file} (byte[] or Attachment): </li>
   * <li>{@code icon32_delete} (Boolean): If true, will delete the file stored in icon32</li>
   * <li>{@code icon48_file} (byte[] or Attachment): </li>
   * <li>{@code icon48_delete} (Boolean): If true, will delete the file stored in icon48</li>
   * <li>{@code icon128_file} (byte[] or Attachment): </li>
   * <li>{@code icon128_delete} (Boolean): If true, will delete the file stored in icon128</li>
   * <li>{@code logo_file} (byte[] or Attachment): </li>
   * <li>{@code logo_delete} (Boolean): If true, will delete the file stored in logo</li>
   * <li>{@code bundle_watermark_attachment_file} (byte[] or Attachment): </li>
   * <li>{@code bundle_watermark_attachment_delete} (Boolean): If true, will delete the file stored in bundle_watermark_attachment</li>
   * <li>{@code login_page_background_image_file} (byte[] or Attachment): </li>
   * <li>{@code login_page_background_image_delete} (Boolean): If true, will delete the file stored in login_page_background_image</li>
   * <li>{@code disable_2fa_with_delay} (Boolean): If set to true, we will begin the process of disabling 2FA on this site.</li>
   * <li>{@code ldap_password_change} (String): New LDAP password.</li>
   * <li>{@code ldap_password_change_confirmation} (String): Confirm new LDAP password.</li>
   * <li>{@code redirect_old_subdomain} (Boolean): If true, and if changing the site subdomain, then create a redirect from the previous Files.com subdomain to the new Files.com subdomain.</li>
   * <li>{@code smtp_password} (String): Password for SMTP server.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Site}
   */
  public static Site update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sites().update(parameters, options);
  }


}
