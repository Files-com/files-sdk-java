package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Site() {
    this(null, null);
  }

  public Site(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Site(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  * Do incoming emails in the Inboxes require checking for SPF/DKIM/DMARC?
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
  * Letsencrypt chain to use when registering SSL Certificate for domain.
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
  * List of site settings managed by the parent site
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
  @JsonProperty("next_billing_amount")
  public Double nextBillingAmount;

  public Double getNextBillingAmount() {
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
  * Is SFTP enabled?
  */
  @JsonProperty("sftp_enabled")
  public Boolean sftpEnabled;

  public Boolean getSftpEnabled() {
    return sftpEnabled;
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
  * Custom text send in user welcome email
  */
  @JsonProperty("welcome_custom_text")
  public String welcomeCustomText;

  public String getWelcomeCustomText() {
    return welcomeCustomText;
  }

  /**
  * Custom footer text for system-generated emails. Supports standard strftime date/time patterns like %Y (4-digit year), %m (month), %d (day).
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
  * Allow group admins set password authentication method
  */
  @JsonProperty("group_admins_can_set_user_password")
  public Boolean groupAdminsCanSetUserPassword;

  public Boolean getGroupAdminsCanSetUserPassword() {
    return groupAdminsCanSetUserPassword;
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();





    String url = String.format("%s%s/site", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();





    String url = String.format("%s%s/site/usage", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<UsageSnapshot> typeReference = new TypeReference<UsageSnapshot>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Site name
  *   subdomain - string - Site subdomain
  *   domain - string - Custom domain
  *   domain_hsts_header - boolean - Send HSTS (HTTP Strict Transport Security) header when visitors access the site via a custom domain?
  *   domain_letsencrypt_chain - string - Letsencrypt chain to use when registering SSL Certificate for domain.
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
  *   additional_text_file_types - array(string) - Additional extensions that are considered text files
  *   bundle_require_note - boolean - Do Bundles require internal notes?
  *   bundle_send_shared_receipts - boolean - Do Bundle creators receive receipts of invitations?
  *   calculate_file_checksums_crc32 - boolean - Calculate CRC32 checksums for files?
  *   calculate_file_checksums_md5 - boolean - Calculate MD5 checksums for files?
  *   calculate_file_checksums_sha1 - boolean - Calculate SHA1 checksums for files?
  *   calculate_file_checksums_sha256 - boolean - Calculate SHA256 checksums for files?
  *   legacy_checksums_mode - boolean - Use legacy checksums mode?
  *   migrate_remote_server_sync_to_sync - boolean - If true, we will migrate all remote server syncs to the new Sync model.
  *   as2_message_retention_days - int64 - Number of days to retain AS2 messages (incoming and outgoing).
  *   session_expiry_minutes - int64 - Session expiry in minutes
  *   ssl_required - boolean - Is SSL required?  Disabling this is insecure.
  *   sftp_insecure_ciphers - boolean - If true, we will allow weak and known insecure ciphers to be used for SFTP connections.  Enabling this setting severely weakens the security of your site and it is not recommend, except as a last resort for compatibility.
  *   sftp_insecure_diffie_hellman - boolean - If true, we will allow weak Diffie Hellman parameters to be used within ciphers for SFTP that are otherwise on our secure list.  This has the effect of making the cipher weaker than our normal threshold for security, but is required to support certain legacy or broken SSH and MFT clients.  Enabling this weakens security, but not nearly as much as enabling the full `sftp_insecure_ciphers` option.
  *   disable_files_certificate_generation - boolean - If set, Files.com will not set the CAA records required to generate future SSL certificates for this domain.
  *   user_lockout - boolean - Will users be locked out after incorrect login attempts?
  *   user_lockout_tries - int64 - Number of login tries within `user_lockout_within` hours before users are locked out
  *   user_lockout_within - int64 - Number of hours for user lockout window
  *   user_lockout_lock_period - int64 - How many hours to lock user out for failed password?
  *   include_password_in_welcome_email - boolean - Include password in emails to new users?
  *   allowed_countries - string - Comma separated list of allowed Country codes
  *   allowed_ips - string - List of allowed IP addresses
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
  *   bundle_registration_notifications - string - Do Bundle owners receive registration notification?
  *   bundle_activity_notifications - string - Do Bundle owners receive activity notifications?
  *   bundle_upload_receipt_notifications - string - Do Bundle uploaders receive upload confirmation notifications?
  *   document_edits_in_bundle_allowed - boolean - If true, allow public viewers of Bundles with full permissions to use document editing integrations.
  *   password_requirements_apply_to_bundles - boolean - Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
  *   prevent_root_permissions_for_non_site_admins - boolean - If true, we will prevent non-administrators from receiving any permissions directly on the root folder.  This is commonly used to prevent the accidental application of permissions.
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
  *   ftp_enabled - boolean - Is FTP enabled?
  *   sftp_enabled - boolean - Is SFTP enabled?
  *   users_can_create_api_keys - boolean - Allow users to create their own API keys?
  *   users_can_create_ssh_keys - boolean - Allow users to create their own SSH keys?
  *   show_user_notifications_log_in_link - boolean - Show log in link in user notifications?
  *   sftp_host_key_type - string - Sftp Host Key Type
  *   active_sftp_host_key_id - int64 - Id of the currently selected custom SFTP Host Key
  *   protocol_access_groups_only - boolean - If true, protocol access permissions on users will be ignored, and only protocol access permissions set on Groups will be honored.  Make sure that your current user is a member of a group with API permission when changing this value to avoid locking yourself out of your site.
  *   revoke_bundle_access_on_disable_or_delete - boolean - Auto-removes bundles for disabled/deleted users and enforces bundle expiry within user access period.
  *   bundle_watermark_value - object - Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
  *   group_admins_can_set_user_password - boolean - Allow group admins set password authentication method
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
  *   email_footer_custom_text - string - Custom footer text for system-generated emails. Supports standard strftime date/time patterns like %Y (4-digit year), %m (month), %d (day).
  *   smtp_address - string - SMTP server hostname or IP
  *   smtp_authentication - string - SMTP server authentication type
  *   smtp_from - string - From address to use when mailing through custom SMTP
  *   smtp_username - string - SMTP server username
  *   smtp_port - int64 - SMTP server port
  *   ldap_enabled - boolean - Main LDAP setting: is LDAP enabled?
  *   ldap_type - string - LDAP type
  *   ldap_host - string - LDAP host
  *   ldap_host_2 - string - LDAP backup host
  *   ldap_host_3 - string - LDAP backup host
  *   ldap_port - int64 - LDAP port
  *   ldap_secure - boolean - Use secure LDAP?
  *   ldap_username - string - Username for signing in to LDAP server.
  *   ldap_username_field - string - LDAP username field
  *   ldap_domain - string - Domain name that will be appended to usernames
  *   ldap_user_action - string - Should we sync users from LDAP server?
  *   ldap_group_action - string - Should we sync groups from LDAP server?
  *   ldap_user_include_groups - string - Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
  *   ldap_group_exclusion - string - Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
  *   ldap_group_inclusion - string - Comma or newline separated list of group names (with optional wildcards) to include when syncing.
  *   ldap_base_dn - string - Base DN for looking up users in LDAP server
  *   uploads_via_email_authentication - boolean - Do incoming emails in the Inboxes require checking for SPF/DKIM/DMARC?
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
  *   smtp_password - string - Password for SMTP server.
  */
  public static Site update() throws RuntimeException {
    return update(null, null);
  }

  public static Site update(HashMap<String, Object> parameters) throws RuntimeException {
    return update(parameters, null);
  }


  public static Site update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



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
    if (parameters.containsKey("additional_text_file_types") && !(parameters.get("additional_text_file_types") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: additional_text_file_types must be of type String[] parameters[\"additional_text_file_types\"]");
    }
    if (parameters.containsKey("bundle_require_note") && !(parameters.get("bundle_require_note") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_require_note must be of type Boolean parameters[\"bundle_require_note\"]");
    }
    if (parameters.containsKey("bundle_send_shared_receipts") && !(parameters.get("bundle_send_shared_receipts") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_send_shared_receipts must be of type Boolean parameters[\"bundle_send_shared_receipts\"]");
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
    if (parameters.containsKey("ftp_enabled") && !(parameters.get("ftp_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_enabled must be of type Boolean parameters[\"ftp_enabled\"]");
    }
    if (parameters.containsKey("sftp_enabled") && !(parameters.get("sftp_enabled") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_enabled must be of type Boolean parameters[\"sftp_enabled\"]");
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
    if (parameters.containsKey("protocol_access_groups_only") && !(parameters.get("protocol_access_groups_only") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: protocol_access_groups_only must be of type Boolean parameters[\"protocol_access_groups_only\"]");
    }
    if (parameters.containsKey("revoke_bundle_access_on_disable_or_delete") && !(parameters.get("revoke_bundle_access_on_disable_or_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: revoke_bundle_access_on_disable_or_delete must be of type Boolean parameters[\"revoke_bundle_access_on_disable_or_delete\"]");
    }
    if (parameters.containsKey("bundle_watermark_value") && !(parameters.get("bundle_watermark_value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: bundle_watermark_value must be of type Object parameters[\"bundle_watermark_value\"]");
    }
    if (parameters.containsKey("group_admins_can_set_user_password") && !(parameters.get("group_admins_can_set_user_password") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: group_admins_can_set_user_password must be of type Boolean parameters[\"group_admins_can_set_user_password\"]");
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
    if (parameters.containsKey("icon16_file") && !(parameters.get("icon16_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: icon16_file must be of type byte[] parameters[\"icon16_file\"]");
    }
    if (parameters.containsKey("icon16_delete") && !(parameters.get("icon16_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon16_delete must be of type Boolean parameters[\"icon16_delete\"]");
    }
    if (parameters.containsKey("icon32_file") && !(parameters.get("icon32_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: icon32_file must be of type byte[] parameters[\"icon32_file\"]");
    }
    if (parameters.containsKey("icon32_delete") && !(parameters.get("icon32_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon32_delete must be of type Boolean parameters[\"icon32_delete\"]");
    }
    if (parameters.containsKey("icon48_file") && !(parameters.get("icon48_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: icon48_file must be of type byte[] parameters[\"icon48_file\"]");
    }
    if (parameters.containsKey("icon48_delete") && !(parameters.get("icon48_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon48_delete must be of type Boolean parameters[\"icon48_delete\"]");
    }
    if (parameters.containsKey("icon128_file") && !(parameters.get("icon128_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: icon128_file must be of type byte[] parameters[\"icon128_file\"]");
    }
    if (parameters.containsKey("icon128_delete") && !(parameters.get("icon128_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: icon128_delete must be of type Boolean parameters[\"icon128_delete\"]");
    }
    if (parameters.containsKey("logo_file") && !(parameters.get("logo_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: logo_file must be of type byte[] parameters[\"logo_file\"]");
    }
    if (parameters.containsKey("logo_delete") && !(parameters.get("logo_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: logo_delete must be of type Boolean parameters[\"logo_delete\"]");
    }
    if (parameters.containsKey("bundle_watermark_attachment_file") && !(parameters.get("bundle_watermark_attachment_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: bundle_watermark_attachment_file must be of type byte[] parameters[\"bundle_watermark_attachment_file\"]");
    }
    if (parameters.containsKey("bundle_watermark_attachment_delete") && !(parameters.get("bundle_watermark_attachment_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: bundle_watermark_attachment_delete must be of type Boolean parameters[\"bundle_watermark_attachment_delete\"]");
    }
    if (parameters.containsKey("login_page_background_image_file") && !(parameters.get("login_page_background_image_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: login_page_background_image_file must be of type byte[] parameters[\"login_page_background_image_file\"]");
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
    if (parameters.containsKey("smtp_password") && !(parameters.get("smtp_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: smtp_password must be of type String parameters[\"smtp_password\"]");
    }


    String url = String.format("%s%s/site", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


}
