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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class User implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
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
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * User's username
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
  * List of group IDs of which this user is an administrator
  */
  @JsonProperty("admin_group_ids")
  public Long[] adminGroupIds;

  public Long[] getAdminGroupIds() {
    return adminGroupIds;
  }

  public void setAdminGroupIds(Long[] adminGroupIds) {
    this.adminGroupIds = adminGroupIds;
  }

  /**
  * A list of allowed IPs if applicable.  Newline delimited
  */
  @JsonProperty("allowed_ips")
  public String allowedIps;

  public String getAllowedIps() {
    return allowedIps;
  }

  public void setAllowedIps(String allowedIps) {
    this.allowedIps = allowedIps;
  }

  /**
  * If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
  */
  @JsonProperty("attachments_permission")
  public Boolean attachmentsPermission;

  public Boolean getAttachmentsPermission() {
    return attachmentsPermission;
  }

  public void setAttachmentsPermission(Boolean attachmentsPermission) {
    this.attachmentsPermission = attachmentsPermission;
  }

  /**
  * Number of API keys associated with this user
  */
  @JsonProperty("api_keys_count")
  public Long apiKeysCount;

  public Long getApiKeysCount() {
    return apiKeysCount;
  }

  public void setApiKeysCount(Long apiKeysCount) {
    this.apiKeysCount = apiKeysCount;
  }

  /**
  * Scheduled Date/Time at which user will be deactivated
  */
  @JsonProperty("authenticate_until")
  public Date authenticateUntil;

  public Date getAuthenticateUntil() {
    return authenticateUntil;
  }

  public void setAuthenticateUntil(Date authenticateUntil) {
    this.authenticateUntil = authenticateUntil;
  }

  /**
  * How is this user authenticated?
  */
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  public String getAuthenticationMethod() {
    return authenticationMethod;
  }

  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  /**
  * URL holding the user's avatar
  */
  @JsonProperty("avatar_url")
  public String avatarUrl;

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  /**
  * Is this a billable user record?
  */
  @JsonProperty("billable")
  public Boolean billable;

  public Boolean getBillable() {
    return billable;
  }

  public void setBillable(Boolean billable) {
    this.billable = billable;
  }

  /**
  * Allow this user to perform operations on the account, payments, and invoices?
  */
  @JsonProperty("billing_permission")
  public Boolean billingPermission;

  public Boolean getBillingPermission() {
    return billingPermission;
  }

  public void setBillingPermission(Boolean billingPermission) {
    this.billingPermission = billingPermission;
  }

  /**
  * Allow this user to skip site-wide IP blacklists?
  */
  @JsonProperty("bypass_site_allowed_ips")
  public Boolean bypassSiteAllowedIps;

  public Boolean getBypassSiteAllowedIps() {
    return bypassSiteAllowedIps;
  }

  public void setBypassSiteAllowedIps(Boolean bypassSiteAllowedIps) {
    this.bypassSiteAllowedIps = bypassSiteAllowedIps;
  }

  /**
  * Exempt this user from user lifecycle rules?
  */
  @JsonProperty("bypass_user_lifecycle_rules")
  public Boolean bypassUserLifecycleRules;

  public Boolean getBypassUserLifecycleRules() {
    return bypassUserLifecycleRules;
  }

  public void setBypassUserLifecycleRules(Boolean bypassUserLifecycleRules) {
    this.bypassUserLifecycleRules = bypassUserLifecycleRules;
  }

  /**
  * When this user was created
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Can the user connect with WebDAV?
  */
  @JsonProperty("dav_permission")
  public Boolean davPermission;

  public Boolean getDavPermission() {
    return davPermission;
  }

  public void setDavPermission(Boolean davPermission) {
    this.davPermission = davPermission;
  }

  /**
  * Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
  */
  @JsonProperty("disabled")
  public Boolean disabled;

  public Boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  /**
  * Computed property that returns true if user disabled or expired or inactive.
  */
  @JsonProperty("disabled_expired_or_inactive")
  public Boolean disabledExpiredOrInactive;

  public Boolean getDisabledExpiredOrInactive() {
    return disabledExpiredOrInactive;
  }

  public void setDisabledExpiredOrInactive(Boolean disabledExpiredOrInactive) {
    this.disabledExpiredOrInactive = disabledExpiredOrInactive;
  }

  /**
  * User email address
  */
  @JsonProperty("email")
  public String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
  * File system layout
  */
  @JsonProperty("filesystem_layout")
  public String filesystemLayout;

  public String getFilesystemLayout() {
    return filesystemLayout;
  }

  public void setFilesystemLayout(String filesystemLayout) {
    this.filesystemLayout = filesystemLayout;
  }

  /**
  * User's first login time
  */
  @JsonProperty("first_login_at")
  public Date firstLoginAt;

  public Date getFirstLoginAt() {
    return firstLoginAt;
  }

  public void setFirstLoginAt(Date firstLoginAt) {
    this.firstLoginAt = firstLoginAt;
  }

  /**
  * Can the user access with FTP/FTPS?
  */
  @JsonProperty("ftp_permission")
  public Boolean ftpPermission;

  public Boolean getFtpPermission() {
    return ftpPermission;
  }

  public void setFtpPermission(Boolean ftpPermission) {
    this.ftpPermission = ftpPermission;
  }

  /**
  * Comma-separated list of group IDs of which this user is a member
  */
  @JsonProperty("group_ids")
  public String groupIds;

  public String getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(String groupIds) {
    this.groupIds = groupIds;
  }

  /**
  * Text to display to the user in the header of the UI
  */
  @JsonProperty("header_text")
  public String headerText;

  public String getHeaderText() {
    return headerText;
  }

  public void setHeaderText(String headerText) {
    this.headerText = headerText;
  }

  /**
  * Preferred language
  */
  @JsonProperty("language")
  public String language;

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  /**
  * User's most recent login time via any protocol
  */
  @JsonProperty("last_login_at")
  public Date lastLoginAt;

  public Date getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(Date lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  /**
  * User's most recent login time via web
  */
  @JsonProperty("last_web_login_at")
  public Date lastWebLoginAt;

  public Date getLastWebLoginAt() {
    return lastWebLoginAt;
  }

  public void setLastWebLoginAt(Date lastWebLoginAt) {
    this.lastWebLoginAt = lastWebLoginAt;
  }

  /**
  * User's most recent login time via FTP
  */
  @JsonProperty("last_ftp_login_at")
  public Date lastFtpLoginAt;

  public Date getLastFtpLoginAt() {
    return lastFtpLoginAt;
  }

  public void setLastFtpLoginAt(Date lastFtpLoginAt) {
    this.lastFtpLoginAt = lastFtpLoginAt;
  }

  /**
  * User's most recent login time via SFTP
  */
  @JsonProperty("last_sftp_login_at")
  public Date lastSftpLoginAt;

  public Date getLastSftpLoginAt() {
    return lastSftpLoginAt;
  }

  public void setLastSftpLoginAt(Date lastSftpLoginAt) {
    this.lastSftpLoginAt = lastSftpLoginAt;
  }

  /**
  * User's most recent login time via WebDAV
  */
  @JsonProperty("last_dav_login_at")
  public Date lastDavLoginAt;

  public Date getLastDavLoginAt() {
    return lastDavLoginAt;
  }

  public void setLastDavLoginAt(Date lastDavLoginAt) {
    this.lastDavLoginAt = lastDavLoginAt;
  }

  /**
  * User's most recent login time via Desktop app
  */
  @JsonProperty("last_desktop_login_at")
  public Date lastDesktopLoginAt;

  public Date getLastDesktopLoginAt() {
    return lastDesktopLoginAt;
  }

  public void setLastDesktopLoginAt(Date lastDesktopLoginAt) {
    this.lastDesktopLoginAt = lastDesktopLoginAt;
  }

  /**
  * User's most recent login time via Rest API
  */
  @JsonProperty("last_restapi_login_at")
  public Date lastRestapiLoginAt;

  public Date getLastRestapiLoginAt() {
    return lastRestapiLoginAt;
  }

  public void setLastRestapiLoginAt(Date lastRestapiLoginAt) {
    this.lastRestapiLoginAt = lastRestapiLoginAt;
  }

  /**
  * User's most recent API use time
  */
  @JsonProperty("last_api_use_at")
  public Date lastApiUseAt;

  public Date getLastApiUseAt() {
    return lastApiUseAt;
  }

  public void setLastApiUseAt(Date lastApiUseAt) {
    this.lastApiUseAt = lastApiUseAt;
  }

  /**
  * User's most recent activity time, which is the latest of most recent login, most recent API use, enablement, or creation
  */
  @JsonProperty("last_active_at")
  public Date lastActiveAt;

  public Date getLastActiveAt() {
    return lastActiveAt;
  }

  public void setLastActiveAt(Date lastActiveAt) {
    this.lastActiveAt = lastActiveAt;
  }

  /**
  * The most recent protocol and cipher used
  */
  @JsonProperty("last_protocol_cipher")
  public String lastProtocolCipher;

  public String getLastProtocolCipher() {
    return lastProtocolCipher;
  }

  public void setLastProtocolCipher(String lastProtocolCipher) {
    this.lastProtocolCipher = lastProtocolCipher;
  }

  /**
  * Time in the future that the user will no longer be locked out if applicable
  */
  @JsonProperty("lockout_expires")
  public Date lockoutExpires;

  public Date getLockoutExpires() {
    return lockoutExpires;
  }

  public void setLockoutExpires(Date lockoutExpires) {
    this.lockoutExpires = lockoutExpires;
  }

  /**
  * User's full name
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * User's company
  */
  @JsonProperty("company")
  public String company;

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  /**
  * Any internal notes on the user
  */
  @JsonProperty("notes")
  public String notes;

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
  * Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  */
  @JsonProperty("notification_daily_send_time")
  public Long notificationDailySendTime;

  public Long getNotificationDailySendTime() {
    return notificationDailySendTime;
  }

  public void setNotificationDailySendTime(Long notificationDailySendTime) {
    this.notificationDailySendTime = notificationDailySendTime;
  }

  /**
  * Enable integration with Office for the web?
  */
  @JsonProperty("office_integration_enabled")
  public Boolean officeIntegrationEnabled;

  public Boolean getOfficeIntegrationEnabled() {
    return officeIntegrationEnabled;
  }

  public void setOfficeIntegrationEnabled(Boolean officeIntegrationEnabled) {
    this.officeIntegrationEnabled = officeIntegrationEnabled;
  }

  /**
  * Is this user a Partner administrator?
  */
  @JsonProperty("partner_admin")
  public Boolean partnerAdmin;

  public Boolean getPartnerAdmin() {
    return partnerAdmin;
  }

  public void setPartnerAdmin(Boolean partnerAdmin) {
    this.partnerAdmin = partnerAdmin;
  }

  /**
  * Partner ID if this user belongs to a Partner
  */
  @JsonProperty("partner_id")
  public Long partnerId;

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
  * Name of the Partner if this user belongs to a Partner
  */
  @JsonProperty("partner_name")
  public String partnerName;

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
  * Last time the user's password was set
  */
  @JsonProperty("password_set_at")
  public Date passwordSetAt;

  public Date getPasswordSetAt() {
    return passwordSetAt;
  }

  public void setPasswordSetAt(Date passwordSetAt) {
    this.passwordSetAt = passwordSetAt;
  }

  /**
  * Number of days to allow user to use the same password
  */
  @JsonProperty("password_validity_days")
  public Long passwordValidityDays;

  public Long getPasswordValidityDays() {
    return passwordValidityDays;
  }

  public void setPasswordValidityDays(Long passwordValidityDays) {
    this.passwordValidityDays = passwordValidityDays;
  }

  /**
  * Number of public keys associated with this user
  */
  @JsonProperty("public_keys_count")
  public Long publicKeysCount;

  public Long getPublicKeysCount() {
    return publicKeysCount;
  }

  public void setPublicKeysCount(Long publicKeysCount) {
    this.publicKeysCount = publicKeysCount;
  }

  /**
  * Should the user receive admin alerts such a certificate expiration notifications and overages?
  */
  @JsonProperty("receive_admin_alerts")
  public Boolean receiveAdminAlerts;

  public Boolean getReceiveAdminAlerts() {
    return receiveAdminAlerts;
  }

  public void setReceiveAdminAlerts(Boolean receiveAdminAlerts) {
    this.receiveAdminAlerts = receiveAdminAlerts;
  }

  /**
  * 2FA required setting
  */
  @JsonProperty("require_2fa")
  public String require2fa;

  public String getRequire2fa() {
    return require2fa;
  }

  public void setRequire2fa(String require2fa) {
    this.require2fa = require2fa;
  }

  /**
  * Require user to login by specified date otherwise it will be disabled.
  */
  @JsonProperty("require_login_by")
  public Date requireLoginBy;

  public Date getRequireLoginBy() {
    return requireLoginBy;
  }

  public void setRequireLoginBy(Date requireLoginBy) {
    this.requireLoginBy = requireLoginBy;
  }

  /**
  * Is 2fa active for the user?
  */
  @JsonProperty("active_2fa")
  public Boolean active2fa;

  public Boolean getActive2fa() {
    return active2fa;
  }

  public void setActive2fa(Boolean active2fa) {
    this.active2fa = active2fa;
  }

  /**
  * Is a password change required upon next user login?
  */
  @JsonProperty("require_password_change")
  public Boolean requirePasswordChange;

  public Boolean getRequirePasswordChange() {
    return requirePasswordChange;
  }

  public void setRequirePasswordChange(Boolean requirePasswordChange) {
    this.requirePasswordChange = requirePasswordChange;
  }

  /**
  * Is user's password expired?
  */
  @JsonProperty("password_expired")
  public Boolean passwordExpired;

  public Boolean getPasswordExpired() {
    return passwordExpired;
  }

  public void setPasswordExpired(Boolean passwordExpired) {
    this.passwordExpired = passwordExpired;
  }

  /**
  * Is the user an allowed to view all (non-billing) site configuration for this site?
  */
  @JsonProperty("readonly_site_admin")
  public Boolean readonlySiteAdmin;

  public Boolean getReadonlySiteAdmin() {
    return readonlySiteAdmin;
  }

  public void setReadonlySiteAdmin(Boolean readonlySiteAdmin) {
    this.readonlySiteAdmin = readonlySiteAdmin;
  }

  /**
  * Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
  */
  @JsonProperty("restapi_permission")
  public Boolean restapiPermission;

  public Boolean getRestapiPermission() {
    return restapiPermission;
  }

  public void setRestapiPermission(Boolean restapiPermission) {
    this.restapiPermission = restapiPermission;
  }

  /**
  * Does this user manage it's own credentials or is it a shared/bot user?
  */
  @JsonProperty("self_managed")
  public Boolean selfManaged;

  public Boolean getSelfManaged() {
    return selfManaged;
  }

  public void setSelfManaged(Boolean selfManaged) {
    this.selfManaged = selfManaged;
  }

  /**
  * Can the user access with SFTP?
  */
  @JsonProperty("sftp_permission")
  public Boolean sftpPermission;

  public Boolean getSftpPermission() {
    return sftpPermission;
  }

  public void setSftpPermission(Boolean sftpPermission) {
    this.sftpPermission = sftpPermission;
  }

  /**
  * Is the user an administrator for this site?
  */
  @JsonProperty("site_admin")
  public Boolean siteAdmin;

  public Boolean getSiteAdmin() {
    return siteAdmin;
  }

  public void setSiteAdmin(Boolean siteAdmin) {
    this.siteAdmin = siteAdmin;
  }

  /**
  * Is the user a Workspace administrator?  Applicable only to the workspace ID related to this user, if one is set.
  */
  @JsonProperty("workspace_admin")
  public Boolean workspaceAdmin;

  public Boolean getWorkspaceAdmin() {
    return workspaceAdmin;
  }

  public void setWorkspaceAdmin(Boolean workspaceAdmin) {
    this.workspaceAdmin = workspaceAdmin;
  }

  /**
  * Site ID
  */
  @JsonProperty("site_id")
  public Long siteId;

  public Long getSiteId() {
    return siteId;
  }

  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
  * Workspace ID
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
  * Skip Welcome page in the UI?
  */
  @JsonProperty("skip_welcome_screen")
  public Boolean skipWelcomeScreen;

  public Boolean getSkipWelcomeScreen() {
    return skipWelcomeScreen;
  }

  public void setSkipWelcomeScreen(Boolean skipWelcomeScreen) {
    this.skipWelcomeScreen = skipWelcomeScreen;
  }

  /**
  * SSL required setting
  */
  @JsonProperty("ssl_required")
  public String sslRequired;

  public String getSslRequired() {
    return sslRequired;
  }

  public void setSslRequired(String sslRequired) {
    this.sslRequired = sslRequired;
  }

  /**
  * SSO (Single Sign On) strategy ID for the user, if applicable.
  */
  @JsonProperty("sso_strategy_id")
  public Long ssoStrategyId;

  public Long getSsoStrategyId() {
    return ssoStrategyId;
  }

  public void setSsoStrategyId(Long ssoStrategyId) {
    this.ssoStrategyId = ssoStrategyId;
  }

  /**
  * Is the user subscribed to the newsletter?
  */
  @JsonProperty("subscribe_to_newsletter")
  public Boolean subscribeToNewsletter;

  public Boolean getSubscribeToNewsletter() {
    return subscribeToNewsletter;
  }

  public void setSubscribeToNewsletter(Boolean subscribeToNewsletter) {
    this.subscribeToNewsletter = subscribeToNewsletter;
  }

  /**
  * Is this user managed by a SsoStrategy?
  */
  @JsonProperty("externally_managed")
  public Boolean externallyManaged;

  public Boolean getExternallyManaged() {
    return externallyManaged;
  }

  public void setExternallyManaged(Boolean externallyManaged) {
    this.externallyManaged = externallyManaged;
  }

  /**
  * Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  */
  @JsonProperty("tags")
  public String tags;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  /**
  * User time zone
  */
  @JsonProperty("time_zone")
  public String timeZone;

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /**
  * Type(s) of 2FA methods in use, for programmatic use.  Will be either `sms`, `totp`, `webauthn`, `yubi`, `email`, or multiple values sorted alphabetically and joined by an underscore.  Does not specify whether user has more than one of a given method.
  */
  @JsonProperty("type_of_2fa")
  public String typeOf2fa;

  public String getTypeOf2fa() {
    return typeOf2fa;
  }

  public void setTypeOf2fa(String typeOf2fa) {
    this.typeOf2fa = typeOf2fa;
  }

  /**
  * Type(s) of 2FA methods in use, formatted for displaying in the UI.  Unlike `type_of_2fa`, this value will make clear when a user has more than 1 of the same type of method.
  */
  @JsonProperty("type_of_2fa_for_display")
  public String typeOf2faForDisplay;

  public String getTypeOf2faForDisplay() {
    return typeOf2faForDisplay;
  }

  public void setTypeOf2faForDisplay(String typeOf2faForDisplay) {
    this.typeOf2faForDisplay = typeOf2faForDisplay;
  }

  /**
  * Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
  */
  @JsonProperty("user_root")
  public String userRoot;

  public String getUserRoot() {
    return userRoot;
  }

  public void setUserRoot(String userRoot) {
    this.userRoot = userRoot;
  }

  /**
  * Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
  */
  @JsonProperty("user_home")
  public String userHome;

  public String getUserHome() {
    return userHome;
  }

  public void setUserHome(String userHome) {
    this.userHome = userHome;
  }

  /**
  * Number of days remaining until password expires
  */
  @JsonProperty("days_remaining_until_password_expire")
  public Long daysRemainingUntilPasswordExpire;

  public Long getDaysRemainingUntilPasswordExpire() {
    return daysRemainingUntilPasswordExpire;
  }

  public void setDaysRemainingUntilPasswordExpire(Long daysRemainingUntilPasswordExpire) {
    this.daysRemainingUntilPasswordExpire = daysRemainingUntilPasswordExpire;
  }

  /**
  * Password expiration datetime
  */
  @JsonProperty("password_expire_at")
  public Date passwordExpireAt;

  public Date getPasswordExpireAt() {
    return passwordExpireAt;
  }

  public void setPasswordExpireAt(Date passwordExpireAt) {
    this.passwordExpireAt = passwordExpireAt;
  }

  /**
  * An image file for your user avatar.
  */
  @JsonProperty("avatar_file")
  public byte[] avatarFile;

  public byte[] getAvatarFile() {
    return avatarFile;
  }

  public void setAvatarFile(byte[] avatarFile) {
    this.avatarFile = avatarFile;
  }

  /**
  * If true, the avatar will be deleted.
  */
  @JsonProperty("avatar_delete")
  public Boolean avatarDelete;

  public Boolean getAvatarDelete() {
    return avatarDelete;
  }

  public void setAvatarDelete(Boolean avatarDelete) {
    this.avatarDelete = avatarDelete;
  }

  /**
  * Used for changing a password on an existing user.
  */
  @JsonProperty("change_password")
  public String changePassword;

  public String getChangePassword() {
    return changePassword;
  }

  public void setChangePassword(String changePassword) {
    this.changePassword = changePassword;
  }

  /**
  * Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  */
  @JsonProperty("change_password_confirmation")
  public String changePasswordConfirmation;

  public String getChangePasswordConfirmation() {
    return changePasswordConfirmation;
  }

  public void setChangePasswordConfirmation(String changePasswordConfirmation) {
    this.changePasswordConfirmation = changePasswordConfirmation;
  }

  /**
  * Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  */
  @JsonProperty("grant_permission")
  public String grantPermission;

  public String getGrantPermission() {
    return grantPermission;
  }

  public void setGrantPermission(String grantPermission) {
    this.grantPermission = grantPermission;
  }

  /**
  * Group ID to associate this user with.
  */
  @JsonProperty("group_id")
  public Long groupId;

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
  * Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
  */
  @JsonProperty("imported_password_hash")
  public String importedPasswordHash;

  public String getImportedPasswordHash() {
    return importedPasswordHash;
  }

  public void setImportedPasswordHash(String importedPasswordHash) {
    this.importedPasswordHash = importedPasswordHash;
  }

  /**
  * User password.
  */
  @JsonProperty("password")
  public String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
  * Optional, but if provided, we will ensure that it matches the value sent in `password`.
  */
  @JsonProperty("password_confirmation")
  public String passwordConfirmation;

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  /**
  * Signifies that the user has read all the announcements in the UI.
  */
  @JsonProperty("announcements_read")
  public Boolean announcementsRead;

  public Boolean getAnnouncementsRead() {
    return announcementsRead;
  }

  public void setAnnouncementsRead(Boolean announcementsRead) {
    this.announcementsRead = announcementsRead;
  }

  /**
  * If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
  */
  @JsonProperty("clear_2fa")
  public Boolean clear2fa;

  public Boolean getClear2fa() {
    return clear2fa;
  }

  public void setClear2fa(Boolean clear2fa) {
    this.clear2fa = clear2fa;
  }

  /**
  * If true, convert this user to a partner user by assigning the partner_id provided.
  */
  @JsonProperty("convert_to_partner_user")
  public Boolean convertToPartnerUser;

  public Boolean getConvertToPartnerUser() {
    return convertToPartnerUser;
  }

  public void setConvertToPartnerUser(Boolean convertToPartnerUser) {
    this.convertToPartnerUser = convertToPartnerUser;
  }

  /**
  * Unlock user who has been locked out due to failed logins
  */
  public void unlock(HashMap<String, Object> parameters) throws IOException {
    User.unlock(this.id, parameters, this.options);
  }

  /**
  * Resend user welcome email
  */
  public void resendWelcomeEmail(HashMap<String, Object> parameters) throws IOException {
    User.resendWelcomeEmail(this.id, parameters, this.options);
  }

  /**
  * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
  */
  public void user2faReset(HashMap<String, Object> parameters) throws IOException {
    User.user2faReset(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   avatar_file - file - An image file for your user avatar.
  *   avatar_delete - boolean - If true, the avatar will be deleted.
  *   change_password - string - Used for changing a password on an existing user.
  *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  *   email - string - User's email.
  *   grant_permission - string - Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  *   group_id - int64 - Group ID to associate this user with.
  *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
  *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
  *   password - string - User password.
  *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
  *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   attachments_permission - boolean - DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
  *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
  *   authentication_method - string - How is this user authenticated?
  *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
  *   bypass_user_lifecycle_rules - boolean - Exempt this user from user lifecycle rules?
  *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
  *   dav_permission - boolean - Can the user connect with WebDAV?
  *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
  *   filesystem_layout - string - File system layout
  *   ftp_permission - boolean - Can the user access with FTP/FTPS?
  *   header_text - string - Text to display to the user in the header of the UI
  *   language - string - Preferred language
  *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  *   name - string - User's full name
  *   company - string - User's company
  *   notes - string - Any internal notes on the user
  *   office_integration_enabled - boolean - Enable integration with Office for the web?
  *   partner_admin - boolean - Is this user a Partner administrator?
  *   partner_id - int64 - Partner ID if this user belongs to a Partner
  *   password_validity_days - int64 - Number of days to allow user to use the same password
  *   readonly_site_admin - boolean - Is the user an allowed to view all (non-billing) site configuration for this site?
  *   receive_admin_alerts - boolean - Should the user receive admin alerts such a certificate expiration notifications and overages?
  *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
  *   require_password_change - boolean - Is a password change required upon next user login?
  *   restapi_permission - boolean - Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
  *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
  *   sftp_permission - boolean - Can the user access with SFTP?
  *   site_admin - boolean - Is the user an administrator for this site?
  *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
  *   ssl_required - string - SSL required setting
  *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
  *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
  *   require_2fa - string - 2FA required setting
  *   tags - string - Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  *   time_zone - string - User time zone
  *   user_root - string - Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
  *   user_home - string - Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
  *   workspace_admin - boolean - Is the user a Workspace administrator?  Applicable only to the workspace ID related to this user, if one is set.
  *   username - string - User's username
  *   clear_2fa - boolean - If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
  *   convert_to_partner_user - boolean - If true, convert this user to a partner user by assigning the partner_id provided.
  */
  public User update(HashMap<String, Object> parameters) throws IOException {
    return User.update(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   new_owner_id - int64 - Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    User.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    User.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id`, `company`, `name`, `disabled`, `authenticate_until`, `username`, `email`, `last_desktop_login_at`, `last_login_at`, `site_admin`, `password_validity_days` or `ssl_required`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `username`, `name`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until`, `not_site_admin`, `disabled`, `partner_id` or `workspace_id`. Valid field combinations are `[ site_admin, username ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `username`, `name`, `email` or `company`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.
  *   ids - string - comma-separated list of User IDs
  *   include_parent_site_users - boolean - Include users from the parent site.
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
  public static User find() throws RuntimeException {
    return find(null, null, null);
  }

  public static User find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static User find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static User find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static User get() throws RuntimeException {
    return get(null, null, null);
  }

  public static User get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   avatar_file - file - An image file for your user avatar.
  *   avatar_delete - boolean - If true, the avatar will be deleted.
  *   change_password - string - Used for changing a password on an existing user.
  *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  *   email - string - User's email.
  *   grant_permission - string - Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  *   group_id - int64 - Group ID to associate this user with.
  *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
  *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
  *   password - string - User password.
  *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
  *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   attachments_permission - boolean - DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
  *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
  *   authentication_method - string - How is this user authenticated?
  *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
  *   bypass_user_lifecycle_rules - boolean - Exempt this user from user lifecycle rules?
  *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
  *   dav_permission - boolean - Can the user connect with WebDAV?
  *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
  *   filesystem_layout - string - File system layout
  *   ftp_permission - boolean - Can the user access with FTP/FTPS?
  *   header_text - string - Text to display to the user in the header of the UI
  *   language - string - Preferred language
  *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  *   name - string - User's full name
  *   company - string - User's company
  *   notes - string - Any internal notes on the user
  *   office_integration_enabled - boolean - Enable integration with Office for the web?
  *   partner_admin - boolean - Is this user a Partner administrator?
  *   partner_id - int64 - Partner ID if this user belongs to a Partner
  *   password_validity_days - int64 - Number of days to allow user to use the same password
  *   readonly_site_admin - boolean - Is the user an allowed to view all (non-billing) site configuration for this site?
  *   receive_admin_alerts - boolean - Should the user receive admin alerts such a certificate expiration notifications and overages?
  *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
  *   require_password_change - boolean - Is a password change required upon next user login?
  *   restapi_permission - boolean - Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
  *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
  *   sftp_permission - boolean - Can the user access with SFTP?
  *   site_admin - boolean - Is the user an administrator for this site?
  *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
  *   ssl_required - string - SSL required setting
  *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
  *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
  *   require_2fa - string - 2FA required setting
  *   tags - string - Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  *   time_zone - string - User time zone
  *   user_root - string - Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
  *   user_home - string - Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
  *   workspace_admin - boolean - Is the user a Workspace administrator?  Applicable only to the workspace ID related to this user, if one is set.
  *   username (required) - string - User's username
  *   workspace_id - int64 - Workspace ID
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


    if (!parameters.containsKey("username") || parameters.get("username") == null) {
      throw new NullPointerException("Parameter missing: username parameters[\"username\"]");
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
    if (parameters.containsKey("readonly_site_admin") && !(parameters.get("readonly_site_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: readonly_site_admin must be of type Boolean parameters[\"readonly_site_admin\"]");
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


    String url = String.format("%s%s/users", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Unlock user who has been locked out due to failed logins
  */
  public static void unlock() throws RuntimeException {
    unlock(null, null, null);
  }

  public static void unlock(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    unlock(id, parameters, null);
  }

  public static void unlock(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    unlock(null, parameters, options);
  }

  public static void unlock(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/users/%s/unlock", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Resend user welcome email
  */
  public static void resendWelcomeEmail() throws RuntimeException {
    resendWelcomeEmail(null, null, null);
  }

  public static void resendWelcomeEmail(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resendWelcomeEmail(id, parameters, null);
  }

  public static void resendWelcomeEmail(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resendWelcomeEmail(null, parameters, options);
  }

  public static void resendWelcomeEmail(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/users/%s/resend_welcome_email", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
  */
  public static void user2faReset() throws RuntimeException {
    user2faReset(null, null, null);
  }

  public static void user2faReset(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    user2faReset(id, parameters, null);
  }

  public static void user2faReset(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    user2faReset(null, parameters, options);
  }

  public static void user2faReset(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/users/%s/2fa/reset", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   avatar_file - file - An image file for your user avatar.
  *   avatar_delete - boolean - If true, the avatar will be deleted.
  *   change_password - string - Used for changing a password on an existing user.
  *   change_password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
  *   email - string - User's email.
  *   grant_permission - string - Permission to grant on the User Root upon user creation. Can be blank or `full`, `read`, `write`, `list`, `read+write`, or `list+write`
  *   group_id - int64 - Group ID to associate this user with.
  *   group_ids - string - A list of group ids to associate this user with.  Comma delimited.
  *   imported_password_hash - string - Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
  *   password - string - User password.
  *   password_confirmation - string - Optional, but if provided, we will ensure that it matches the value sent in `password`.
  *   announcements_read - boolean - Signifies that the user has read all the announcements in the UI.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   attachments_permission - boolean - DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
  *   authenticate_until - string - Scheduled Date/Time at which user will be deactivated
  *   authentication_method - string - How is this user authenticated?
  *   billing_permission - boolean - Allow this user to perform operations on the account, payments, and invoices?
  *   bypass_user_lifecycle_rules - boolean - Exempt this user from user lifecycle rules?
  *   bypass_site_allowed_ips - boolean - Allow this user to skip site-wide IP blacklists?
  *   dav_permission - boolean - Can the user connect with WebDAV?
  *   disabled - boolean - Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
  *   filesystem_layout - string - File system layout
  *   ftp_permission - boolean - Can the user access with FTP/FTPS?
  *   header_text - string - Text to display to the user in the header of the UI
  *   language - string - Preferred language
  *   notification_daily_send_time - int64 - Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
  *   name - string - User's full name
  *   company - string - User's company
  *   notes - string - Any internal notes on the user
  *   office_integration_enabled - boolean - Enable integration with Office for the web?
  *   partner_admin - boolean - Is this user a Partner administrator?
  *   partner_id - int64 - Partner ID if this user belongs to a Partner
  *   password_validity_days - int64 - Number of days to allow user to use the same password
  *   readonly_site_admin - boolean - Is the user an allowed to view all (non-billing) site configuration for this site?
  *   receive_admin_alerts - boolean - Should the user receive admin alerts such a certificate expiration notifications and overages?
  *   require_login_by - string - Require user to login by specified date otherwise it will be disabled.
  *   require_password_change - boolean - Is a password change required upon next user login?
  *   restapi_permission - boolean - Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
  *   self_managed - boolean - Does this user manage it's own credentials or is it a shared/bot user?
  *   sftp_permission - boolean - Can the user access with SFTP?
  *   site_admin - boolean - Is the user an administrator for this site?
  *   skip_welcome_screen - boolean - Skip Welcome page in the UI?
  *   ssl_required - string - SSL required setting
  *   sso_strategy_id - int64 - SSO (Single Sign On) strategy ID for the user, if applicable.
  *   subscribe_to_newsletter - boolean - Is the user subscribed to the newsletter?
  *   require_2fa - string - 2FA required setting
  *   tags - string - Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  *   time_zone - string - User time zone
  *   user_root - string - Root folder for FTP (and optionally SFTP if the appropriate site-wide setting is set).  Note that this is not used for API, Desktop, or Web interface.
  *   user_home - string - Home folder for FTP/SFTP.  Note that this is not used for API, Desktop, or Web interface.
  *   workspace_admin - boolean - Is the user a Workspace administrator?  Applicable only to the workspace ID related to this user, if one is set.
  *   username - string - User's username
  *   clear_2fa - boolean - If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
  *   convert_to_partner_user - boolean - If true, convert this user to a partner user by assigning the partner_id provided.
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


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
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
    if (parameters.containsKey("readonly_site_admin") && !(parameters.get("readonly_site_admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: readonly_site_admin must be of type Boolean parameters[\"readonly_site_admin\"]");
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
    if (parameters.containsKey("clear_2fa") && !(parameters.get("clear_2fa") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: clear_2fa must be of type Boolean parameters[\"clear_2fa\"]");
    }
    if (parameters.containsKey("convert_to_partner_user") && !(parameters.get("convert_to_partner_user") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: convert_to_partner_user must be of type Boolean parameters[\"convert_to_partner_user\"]");
    }



    String url = String.format("%s%s/users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<User> typeReference = new TypeReference<User>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   new_owner_id - int64 - Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.
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


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("new_owner_id") && !(parameters.get("new_owner_id") instanceof Long || parameters.get("new_owner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: new_owner_id must be of type Long or Integer parameters[\"new_owner_id\"]");
    }



    String url = String.format("%s%s/users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
