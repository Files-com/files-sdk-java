package com.files.models;

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
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements ModelInterface {
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

  // One mapper for every User, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code User} with no attributes set.
   */
  public User() {
    this(null, null);
  }

  /**
   * Creates a {@code User} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public User(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code User} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public User(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * User ID
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
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * User's username
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * List of group IDs of which this user is an administrator
   */
  @JsonProperty("admin_group_ids")
  public Long[] adminGroupIds;

  /**
   * Gets {@link #adminGroupIds}.
   *
   * @return the value of {@link #adminGroupIds}
   */
  public Long[] getAdminGroupIds() {
    return adminGroupIds;
  }

  /**
   * Sets {@link #adminGroupIds}.
   *
   * @param adminGroupIds the new value
   */
  public void setAdminGroupIds(Long[] adminGroupIds) {
    this.adminGroupIds = adminGroupIds;
  }

  /**
   * A list of allowed IPs if applicable.  Newline delimited
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
   * Sets {@link #allowedIps}.
   *
   * @param allowedIps the new value
   */
  public void setAllowedIps(String allowedIps) {
    this.allowedIps = allowedIps;
  }

  /**
   * If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.
   */
  @JsonProperty("attachments_permission")
  public Boolean attachmentsPermission;

  /**
   * Gets {@link #attachmentsPermission}.
   *
   * @return the value of {@link #attachmentsPermission}
   */
  public Boolean getAttachmentsPermission() {
    return attachmentsPermission;
  }

  /**
   * Sets {@link #attachmentsPermission}.
   *
   * @param attachmentsPermission the new value
   */
  public void setAttachmentsPermission(Boolean attachmentsPermission) {
    this.attachmentsPermission = attachmentsPermission;
  }

  /**
   * Number of API keys associated with this user
   */
  @JsonProperty("api_keys_count")
  public Long apiKeysCount;

  /**
   * Gets {@link #apiKeysCount}.
   *
   * @return the value of {@link #apiKeysCount}
   */
  public Long getApiKeysCount() {
    return apiKeysCount;
  }

  /**
   * Sets {@link #apiKeysCount}.
   *
   * @param apiKeysCount the new value
   */
  public void setApiKeysCount(Long apiKeysCount) {
    this.apiKeysCount = apiKeysCount;
  }

  /**
   * Scheduled Date/Time at which user will be deactivated
   */
  @JsonProperty("authenticate_until")
  public Date authenticateUntil;

  /**
   * Gets {@link #authenticateUntil}.
   *
   * @return the value of {@link #authenticateUntil}
   */
  public Date getAuthenticateUntil() {
    return authenticateUntil;
  }

  /**
   * Sets {@link #authenticateUntil}.
   *
   * @param authenticateUntil the new value
   */
  public void setAuthenticateUntil(Date authenticateUntil) {
    this.authenticateUntil = authenticateUntil;
  }

  /**
   * How is this user authenticated?
   */
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  /**
   * Gets {@link #authenticationMethod}.
   *
   * @return the value of {@link #authenticationMethod}
   */
  public String getAuthenticationMethod() {
    return authenticationMethod;
  }

  /**
   * Sets {@link #authenticationMethod}.
   *
   * @param authenticationMethod the new value
   */
  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  /**
   * URL holding the user's avatar
   */
  @JsonProperty("avatar_url")
  public String avatarUrl;

  /**
   * Gets {@link #avatarUrl}.
   *
   * @return the value of {@link #avatarUrl}
   */
  public String getAvatarUrl() {
    return avatarUrl;
  }

  /**
   * Sets {@link #avatarUrl}.
   *
   * @param avatarUrl the new value
   */
  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  /**
   * Is this a billable user record?
   */
  @JsonProperty("billable")
  public Boolean billable;

  /**
   * Gets {@link #billable}.
   *
   * @return the value of {@link #billable}
   */
  public Boolean getBillable() {
    return billable;
  }

  /**
   * Sets {@link #billable}.
   *
   * @param billable the new value
   */
  public void setBillable(Boolean billable) {
    this.billable = billable;
  }

  /**
   * Allow this user to perform operations on the account, payments, and invoices?
   */
  @JsonProperty("billing_permission")
  public Boolean billingPermission;

  /**
   * Gets {@link #billingPermission}.
   *
   * @return the value of {@link #billingPermission}
   */
  public Boolean getBillingPermission() {
    return billingPermission;
  }

  /**
   * Sets {@link #billingPermission}.
   *
   * @param billingPermission the new value
   */
  public void setBillingPermission(Boolean billingPermission) {
    this.billingPermission = billingPermission;
  }

  /**
   * Allow this user to skip site-wide IP blacklists?
   */
  @JsonProperty("bypass_site_allowed_ips")
  public Boolean bypassSiteAllowedIps;

  /**
   * Gets {@link #bypassSiteAllowedIps}.
   *
   * @return the value of {@link #bypassSiteAllowedIps}
   */
  public Boolean getBypassSiteAllowedIps() {
    return bypassSiteAllowedIps;
  }

  /**
   * Sets {@link #bypassSiteAllowedIps}.
   *
   * @param bypassSiteAllowedIps the new value
   */
  public void setBypassSiteAllowedIps(Boolean bypassSiteAllowedIps) {
    this.bypassSiteAllowedIps = bypassSiteAllowedIps;
  }

  /**
   * Exempt this user from user lifecycle rules?
   */
  @JsonProperty("bypass_user_lifecycle_rules")
  public Boolean bypassUserLifecycleRules;

  /**
   * Gets {@link #bypassUserLifecycleRules}.
   *
   * @return the value of {@link #bypassUserLifecycleRules}
   */
  public Boolean getBypassUserLifecycleRules() {
    return bypassUserLifecycleRules;
  }

  /**
   * Sets {@link #bypassUserLifecycleRules}.
   *
   * @param bypassUserLifecycleRules the new value
   */
  public void setBypassUserLifecycleRules(Boolean bypassUserLifecycleRules) {
    this.bypassUserLifecycleRules = bypassUserLifecycleRules;
  }

  /**
   * When this user was created
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
   * Can the user connect with WebDAV?
   */
  @JsonProperty("dav_permission")
  public Boolean davPermission;

  /**
   * Gets {@link #davPermission}.
   *
   * @return the value of {@link #davPermission}
   */
  public Boolean getDavPermission() {
    return davPermission;
  }

  /**
   * Sets {@link #davPermission}.
   *
   * @param davPermission the new value
   */
  public void setDavPermission(Boolean davPermission) {
    this.davPermission = davPermission;
  }

  /**
   * Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.
   */
  @JsonProperty("disabled")
  public Boolean disabled;

  /**
   * Gets {@link #disabled}.
   *
   * @return the value of {@link #disabled}
   */
  public Boolean getDisabled() {
    return disabled;
  }

  /**
   * Sets {@link #disabled}.
   *
   * @param disabled the new value
   */
  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * Computed property that returns true if user disabled or expired or inactive.
   */
  @JsonProperty("disabled_expired_or_inactive")
  public Boolean disabledExpiredOrInactive;

  /**
   * Gets {@link #disabledExpiredOrInactive}.
   *
   * @return the value of {@link #disabledExpiredOrInactive}
   */
  public Boolean getDisabledExpiredOrInactive() {
    return disabledExpiredOrInactive;
  }

  /**
   * Sets {@link #disabledExpiredOrInactive}.
   *
   * @param disabledExpiredOrInactive the new value
   */
  public void setDisabledExpiredOrInactive(Boolean disabledExpiredOrInactive) {
    this.disabledExpiredOrInactive = disabledExpiredOrInactive;
  }

  /**
   * AI Assistant Personality ID assigned directly to this user, if any.
   */
  @JsonProperty("ai_assistant_personality_id")
  public Long aiAssistantPersonalityId;

  /**
   * Gets {@link #aiAssistantPersonalityId}.
   *
   * @return the value of {@link #aiAssistantPersonalityId}
   */
  public Long getAiAssistantPersonalityId() {
    return aiAssistantPersonalityId;
  }

  /**
   * Sets {@link #aiAssistantPersonalityId}.
   *
   * @param aiAssistantPersonalityId the new value
   */
  public void setAiAssistantPersonalityId(Long aiAssistantPersonalityId) {
    this.aiAssistantPersonalityId = aiAssistantPersonalityId;
  }

  /**
   * Desktop Configuration Profile ID assigned directly to this user, if any.
   */
  @JsonProperty("desktop_configuration_profile_id")
  public Long desktopConfigurationProfileId;

  /**
   * Gets {@link #desktopConfigurationProfileId}.
   *
   * @return the value of {@link #desktopConfigurationProfileId}
   */
  public Long getDesktopConfigurationProfileId() {
    return desktopConfigurationProfileId;
  }

  /**
   * Sets {@link #desktopConfigurationProfileId}.
   *
   * @param desktopConfigurationProfileId the new value
   */
  public void setDesktopConfigurationProfileId(Long desktopConfigurationProfileId) {
    this.desktopConfigurationProfileId = desktopConfigurationProfileId;
  }

  /**
   * Integration Centric Profile ID assigned directly to this user, if any.
   */
  @JsonProperty("integration_centric_profile_id")
  public Long integrationCentricProfileId;

  /**
   * Gets {@link #integrationCentricProfileId}.
   *
   * @return the value of {@link #integrationCentricProfileId}
   */
  public Long getIntegrationCentricProfileId() {
    return integrationCentricProfileId;
  }

  /**
   * Sets {@link #integrationCentricProfileId}.
   *
   * @param integrationCentricProfileId the new value
   */
  public void setIntegrationCentricProfileId(Long integrationCentricProfileId) {
    this.integrationCentricProfileId = integrationCentricProfileId;
  }

  /**
   * User email address
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
   * Sets {@link #email}.
   *
   * @param email the new value
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * File system layout
   */
  @JsonProperty("filesystem_layout")
  public String filesystemLayout;

  /**
   * Gets {@link #filesystemLayout}.
   *
   * @return the value of {@link #filesystemLayout}
   */
  public String getFilesystemLayout() {
    return filesystemLayout;
  }

  /**
   * Sets {@link #filesystemLayout}.
   *
   * @param filesystemLayout the new value
   */
  public void setFilesystemLayout(String filesystemLayout) {
    this.filesystemLayout = filesystemLayout;
  }

  /**
   * User's first login time
   */
  @JsonProperty("first_login_at")
  public Date firstLoginAt;

  /**
   * Gets {@link #firstLoginAt}.
   *
   * @return the value of {@link #firstLoginAt}
   */
  public Date getFirstLoginAt() {
    return firstLoginAt;
  }

  /**
   * Sets {@link #firstLoginAt}.
   *
   * @param firstLoginAt the new value
   */
  public void setFirstLoginAt(Date firstLoginAt) {
    this.firstLoginAt = firstLoginAt;
  }

  /**
   * Can the user access with FTP/FTPS?
   */
  @JsonProperty("ftp_permission")
  public Boolean ftpPermission;

  /**
   * Gets {@link #ftpPermission}.
   *
   * @return the value of {@link #ftpPermission}
   */
  public Boolean getFtpPermission() {
    return ftpPermission;
  }

  /**
   * Sets {@link #ftpPermission}.
   *
   * @param ftpPermission the new value
   */
  public void setFtpPermission(Boolean ftpPermission) {
    this.ftpPermission = ftpPermission;
  }

  /**
   * Comma-separated list of group IDs of which this user is a member
   */
  @JsonProperty("group_ids")
  public String groupIds;

  /**
   * Gets {@link #groupIds}.
   *
   * @return the value of {@link #groupIds}
   */
  public String getGroupIds() {
    return groupIds;
  }

  /**
   * Sets {@link #groupIds}.
   *
   * @param groupIds the new value
   */
  public void setGroupIds(String groupIds) {
    this.groupIds = groupIds;
  }

  /**
   * Text to display to the user in the header of the UI
   */
  @JsonProperty("header_text")
  public String headerText;

  /**
   * Gets {@link #headerText}.
   *
   * @return the value of {@link #headerText}
   */
  public String getHeaderText() {
    return headerText;
  }

  /**
   * Sets {@link #headerText}.
   *
   * @param headerText the new value
   */
  public void setHeaderText(String headerText) {
    this.headerText = headerText;
  }

  /**
   * Preferred language
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
   * Sets {@link #language}.
   *
   * @param language the new value
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * User's most recent login time via any protocol
   */
  @JsonProperty("last_login_at")
  public Date lastLoginAt;

  /**
   * Gets {@link #lastLoginAt}.
   *
   * @return the value of {@link #lastLoginAt}
   */
  public Date getLastLoginAt() {
    return lastLoginAt;
  }

  /**
   * Sets {@link #lastLoginAt}.
   *
   * @param lastLoginAt the new value
   */
  public void setLastLoginAt(Date lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  /**
   * User's most recent login time via web
   */
  @JsonProperty("last_web_login_at")
  public Date lastWebLoginAt;

  /**
   * Gets {@link #lastWebLoginAt}.
   *
   * @return the value of {@link #lastWebLoginAt}
   */
  public Date getLastWebLoginAt() {
    return lastWebLoginAt;
  }

  /**
   * Sets {@link #lastWebLoginAt}.
   *
   * @param lastWebLoginAt the new value
   */
  public void setLastWebLoginAt(Date lastWebLoginAt) {
    this.lastWebLoginAt = lastWebLoginAt;
  }

  /**
   * User's most recent login time via FTP
   */
  @JsonProperty("last_ftp_login_at")
  public Date lastFtpLoginAt;

  /**
   * Gets {@link #lastFtpLoginAt}.
   *
   * @return the value of {@link #lastFtpLoginAt}
   */
  public Date getLastFtpLoginAt() {
    return lastFtpLoginAt;
  }

  /**
   * Sets {@link #lastFtpLoginAt}.
   *
   * @param lastFtpLoginAt the new value
   */
  public void setLastFtpLoginAt(Date lastFtpLoginAt) {
    this.lastFtpLoginAt = lastFtpLoginAt;
  }

  /**
   * User's most recent login time via SFTP
   */
  @JsonProperty("last_sftp_login_at")
  public Date lastSftpLoginAt;

  /**
   * Gets {@link #lastSftpLoginAt}.
   *
   * @return the value of {@link #lastSftpLoginAt}
   */
  public Date getLastSftpLoginAt() {
    return lastSftpLoginAt;
  }

  /**
   * Sets {@link #lastSftpLoginAt}.
   *
   * @param lastSftpLoginAt the new value
   */
  public void setLastSftpLoginAt(Date lastSftpLoginAt) {
    this.lastSftpLoginAt = lastSftpLoginAt;
  }

  /**
   * User's most recent login time via WebDAV
   */
  @JsonProperty("last_dav_login_at")
  public Date lastDavLoginAt;

  /**
   * Gets {@link #lastDavLoginAt}.
   *
   * @return the value of {@link #lastDavLoginAt}
   */
  public Date getLastDavLoginAt() {
    return lastDavLoginAt;
  }

  /**
   * Sets {@link #lastDavLoginAt}.
   *
   * @param lastDavLoginAt the new value
   */
  public void setLastDavLoginAt(Date lastDavLoginAt) {
    this.lastDavLoginAt = lastDavLoginAt;
  }

  /**
   * User's most recent login time via Desktop app
   */
  @JsonProperty("last_desktop_login_at")
  public Date lastDesktopLoginAt;

  /**
   * Gets {@link #lastDesktopLoginAt}.
   *
   * @return the value of {@link #lastDesktopLoginAt}
   */
  public Date getLastDesktopLoginAt() {
    return lastDesktopLoginAt;
  }

  /**
   * Sets {@link #lastDesktopLoginAt}.
   *
   * @param lastDesktopLoginAt the new value
   */
  public void setLastDesktopLoginAt(Date lastDesktopLoginAt) {
    this.lastDesktopLoginAt = lastDesktopLoginAt;
  }

  /**
   * User's most recent login time via Rest API
   */
  @JsonProperty("last_restapi_login_at")
  public Date lastRestapiLoginAt;

  /**
   * Gets {@link #lastRestapiLoginAt}.
   *
   * @return the value of {@link #lastRestapiLoginAt}
   */
  public Date getLastRestapiLoginAt() {
    return lastRestapiLoginAt;
  }

  /**
   * Sets {@link #lastRestapiLoginAt}.
   *
   * @param lastRestapiLoginAt the new value
   */
  public void setLastRestapiLoginAt(Date lastRestapiLoginAt) {
    this.lastRestapiLoginAt = lastRestapiLoginAt;
  }

  /**
   * User's most recent API use time
   */
  @JsonProperty("last_api_use_at")
  public Date lastApiUseAt;

  /**
   * Gets {@link #lastApiUseAt}.
   *
   * @return the value of {@link #lastApiUseAt}
   */
  public Date getLastApiUseAt() {
    return lastApiUseAt;
  }

  /**
   * Sets {@link #lastApiUseAt}.
   *
   * @param lastApiUseAt the new value
   */
  public void setLastApiUseAt(Date lastApiUseAt) {
    this.lastApiUseAt = lastApiUseAt;
  }

  /**
   * User's most recent activity time, which is the latest of most recent login, most recent API use, enablement, or creation
   */
  @JsonProperty("last_active_at")
  public Date lastActiveAt;

  /**
   * Gets {@link #lastActiveAt}.
   *
   * @return the value of {@link #lastActiveAt}
   */
  public Date getLastActiveAt() {
    return lastActiveAt;
  }

  /**
   * Sets {@link #lastActiveAt}.
   *
   * @param lastActiveAt the new value
   */
  public void setLastActiveAt(Date lastActiveAt) {
    this.lastActiveAt = lastActiveAt;
  }

  /**
   * The most recent protocol and cipher used
   */
  @JsonProperty("last_protocol_cipher")
  public String lastProtocolCipher;

  /**
   * Gets {@link #lastProtocolCipher}.
   *
   * @return the value of {@link #lastProtocolCipher}
   */
  public String getLastProtocolCipher() {
    return lastProtocolCipher;
  }

  /**
   * Sets {@link #lastProtocolCipher}.
   *
   * @param lastProtocolCipher the new value
   */
  public void setLastProtocolCipher(String lastProtocolCipher) {
    this.lastProtocolCipher = lastProtocolCipher;
  }

  /**
   * Time in the future that the user will no longer be locked out if applicable
   */
  @JsonProperty("lockout_expires")
  public Date lockoutExpires;

  /**
   * Gets {@link #lockoutExpires}.
   *
   * @return the value of {@link #lockoutExpires}
   */
  public Date getLockoutExpires() {
    return lockoutExpires;
  }

  /**
   * Sets {@link #lockoutExpires}.
   *
   * @param lockoutExpires the new value
   */
  public void setLockoutExpires(Date lockoutExpires) {
    this.lockoutExpires = lockoutExpires;
  }

  /**
   * User's full name
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
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * User's company
   */
  @JsonProperty("company")
  public String company;

  /**
   * Gets {@link #company}.
   *
   * @return the value of {@link #company}
   */
  public String getCompany() {
    return company;
  }

  /**
   * Sets {@link #company}.
   *
   * @param company the new value
   */
  public void setCompany(String company) {
    this.company = company;
  }

  /**
   * Any internal notes on the user
   */
  @JsonProperty("notes")
  public String notes;

  /**
   * Gets {@link #notes}.
   *
   * @return the value of {@link #notes}
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Sets {@link #notes}.
   *
   * @param notes the new value
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * Hour of the day at which daily notifications should be sent. Can be in range 0 to 23
   */
  @JsonProperty("notification_daily_send_time")
  public Long notificationDailySendTime;

  /**
   * Gets {@link #notificationDailySendTime}.
   *
   * @return the value of {@link #notificationDailySendTime}
   */
  public Long getNotificationDailySendTime() {
    return notificationDailySendTime;
  }

  /**
   * Sets {@link #notificationDailySendTime}.
   *
   * @param notificationDailySendTime the new value
   */
  public void setNotificationDailySendTime(Long notificationDailySendTime) {
    this.notificationDailySendTime = notificationDailySendTime;
  }

  /**
   * Enable integration with Office for the web?
   */
  @JsonProperty("office_integration_enabled")
  public Boolean officeIntegrationEnabled;

  /**
   * Gets {@link #officeIntegrationEnabled}.
   *
   * @return the value of {@link #officeIntegrationEnabled}
   */
  public Boolean getOfficeIntegrationEnabled() {
    return officeIntegrationEnabled;
  }

  /**
   * Sets {@link #officeIntegrationEnabled}.
   *
   * @param officeIntegrationEnabled the new value
   */
  public void setOfficeIntegrationEnabled(Boolean officeIntegrationEnabled) {
    this.officeIntegrationEnabled = officeIntegrationEnabled;
  }

  /**
   * Is this user a Partner administrator?
   */
  @JsonProperty("partner_admin")
  public Boolean partnerAdmin;

  /**
   * Gets {@link #partnerAdmin}.
   *
   * @return the value of {@link #partnerAdmin}
   */
  public Boolean getPartnerAdmin() {
    return partnerAdmin;
  }

  /**
   * Sets {@link #partnerAdmin}.
   *
   * @param partnerAdmin the new value
   */
  public void setPartnerAdmin(Boolean partnerAdmin) {
    this.partnerAdmin = partnerAdmin;
  }

  /**
   * Partner ID if this user belongs to a Partner
   */
  @JsonProperty("partner_id")
  public Long partnerId;

  /**
   * Gets {@link #partnerId}.
   *
   * @return the value of {@link #partnerId}
   */
  public Long getPartnerId() {
    return partnerId;
  }

  /**
   * Sets {@link #partnerId}.
   *
   * @param partnerId the new value
   */
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * Name of the Partner if this user belongs to a Partner
   */
  @JsonProperty("partner_name")
  public String partnerName;

  /**
   * Gets {@link #partnerName}.
   *
   * @return the value of {@link #partnerName}
   */
  public String getPartnerName() {
    return partnerName;
  }

  /**
   * Sets {@link #partnerName}.
   *
   * @param partnerName the new value
   */
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
   * Last time the user's password was set
   */
  @JsonProperty("password_set_at")
  public Date passwordSetAt;

  /**
   * Gets {@link #passwordSetAt}.
   *
   * @return the value of {@link #passwordSetAt}
   */
  public Date getPasswordSetAt() {
    return passwordSetAt;
  }

  /**
   * Sets {@link #passwordSetAt}.
   *
   * @param passwordSetAt the new value
   */
  public void setPasswordSetAt(Date passwordSetAt) {
    this.passwordSetAt = passwordSetAt;
  }

  /**
   * Number of days to allow user to use the same password
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
   * Sets {@link #passwordValidityDays}.
   *
   * @param passwordValidityDays the new value
   */
  public void setPasswordValidityDays(Long passwordValidityDays) {
    this.passwordValidityDays = passwordValidityDays;
  }

  /**
   * Primary group ID for Group Admin scoping
   */
  @JsonProperty("primary_group_id")
  public Long primaryGroupId;

  /**
   * Gets {@link #primaryGroupId}.
   *
   * @return the value of {@link #primaryGroupId}
   */
  public Long getPrimaryGroupId() {
    return primaryGroupId;
  }

  /**
   * Sets {@link #primaryGroupId}.
   *
   * @param primaryGroupId the new value
   */
  public void setPrimaryGroupId(Long primaryGroupId) {
    this.primaryGroupId = primaryGroupId;
  }

  /**
   * Number of public keys associated with this user
   */
  @JsonProperty("public_keys_count")
  public Long publicKeysCount;

  /**
   * Gets {@link #publicKeysCount}.
   *
   * @return the value of {@link #publicKeysCount}
   */
  public Long getPublicKeysCount() {
    return publicKeysCount;
  }

  /**
   * Sets {@link #publicKeysCount}.
   *
   * @param publicKeysCount the new value
   */
  public void setPublicKeysCount(Long publicKeysCount) {
    this.publicKeysCount = publicKeysCount;
  }

  /**
   * Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.
   */
  @JsonProperty("receive_admin_alerts")
  public Boolean receiveAdminAlerts;

  /**
   * Gets {@link #receiveAdminAlerts}.
   *
   * @return the value of {@link #receiveAdminAlerts}
   */
  public Boolean getReceiveAdminAlerts() {
    return receiveAdminAlerts;
  }

  /**
   * Sets {@link #receiveAdminAlerts}.
   *
   * @param receiveAdminAlerts the new value
   */
  public void setReceiveAdminAlerts(Boolean receiveAdminAlerts) {
    this.receiveAdminAlerts = receiveAdminAlerts;
  }

  /**
   * Should the user receive site warnings via email?
   */
  @JsonProperty("notify_on_all_site_warnings")
  public Boolean notifyOnAllSiteWarnings;

  /**
   * Gets {@link #notifyOnAllSiteWarnings}.
   *
   * @return the value of {@link #notifyOnAllSiteWarnings}
   */
  public Boolean getNotifyOnAllSiteWarnings() {
    return notifyOnAllSiteWarnings;
  }

  /**
   * Sets {@link #notifyOnAllSiteWarnings}.
   *
   * @param notifyOnAllSiteWarnings the new value
   */
  public void setNotifyOnAllSiteWarnings(Boolean notifyOnAllSiteWarnings) {
    this.notifyOnAllSiteWarnings = notifyOnAllSiteWarnings;
  }

  /**
   * Should the user receive sso/scim/ldap configuration/sync failures via email?
   */
  @JsonProperty("notify_on_all_sso_failures")
  public Boolean notifyOnAllSsoFailures;

  /**
   * Gets {@link #notifyOnAllSsoFailures}.
   *
   * @return the value of {@link #notifyOnAllSsoFailures}
   */
  public Boolean getNotifyOnAllSsoFailures() {
    return notifyOnAllSsoFailures;
  }

  /**
   * Sets {@link #notifyOnAllSsoFailures}.
   *
   * @param notifyOnAllSsoFailures the new value
   */
  public void setNotifyOnAllSsoFailures(Boolean notifyOnAllSsoFailures) {
    this.notifyOnAllSsoFailures = notifyOnAllSsoFailures;
  }

  /**
   * Should the user receive user security events via email?
   */
  @JsonProperty("notify_on_all_user_security_events")
  public Boolean notifyOnAllUserSecurityEvents;

  /**
   * Gets {@link #notifyOnAllUserSecurityEvents}.
   *
   * @return the value of {@link #notifyOnAllUserSecurityEvents}
   */
  public Boolean getNotifyOnAllUserSecurityEvents() {
    return notifyOnAllUserSecurityEvents;
  }

  /**
   * Sets {@link #notifyOnAllUserSecurityEvents}.
   *
   * @param notifyOnAllUserSecurityEvents the new value
   */
  public void setNotifyOnAllUserSecurityEvents(Boolean notifyOnAllUserSecurityEvents) {
    this.notifyOnAllUserSecurityEvents = notifyOnAllUserSecurityEvents;
  }

  /**
   * Should the user receive pending work failures via email?
   */
  @JsonProperty("notify_on_all_pending_work_failures")
  public Boolean notifyOnAllPendingWorkFailures;

  /**
   * Gets {@link #notifyOnAllPendingWorkFailures}.
   *
   * @return the value of {@link #notifyOnAllPendingWorkFailures}
   */
  public Boolean getNotifyOnAllPendingWorkFailures() {
    return notifyOnAllPendingWorkFailures;
  }

  /**
   * Sets {@link #notifyOnAllPendingWorkFailures}.
   *
   * @param notifyOnAllPendingWorkFailures the new value
   */
  public void setNotifyOnAllPendingWorkFailures(Boolean notifyOnAllPendingWorkFailures) {
    this.notifyOnAllPendingWorkFailures = notifyOnAllPendingWorkFailures;
  }

  /**
   * Should the user receive siem failures via email?
   */
  @JsonProperty("notify_on_all_siem_http_destination_failures")
  public Boolean notifyOnAllSiemHttpDestinationFailures;

  /**
   * Gets {@link #notifyOnAllSiemHttpDestinationFailures}.
   *
   * @return the value of {@link #notifyOnAllSiemHttpDestinationFailures}
   */
  public Boolean getNotifyOnAllSiemHttpDestinationFailures() {
    return notifyOnAllSiemHttpDestinationFailures;
  }

  /**
   * Sets {@link #notifyOnAllSiemHttpDestinationFailures}.
   *
   * @param notifyOnAllSiemHttpDestinationFailures the new value
   */
  public void setNotifyOnAllSiemHttpDestinationFailures(Boolean notifyOnAllSiemHttpDestinationFailures) {
    this.notifyOnAllSiemHttpDestinationFailures = notifyOnAllSiemHttpDestinationFailures;
  }

  /**
   * Should the user receive sync failures via email?
   */
  @JsonProperty("notify_on_all_sync_failures")
  public Boolean notifyOnAllSyncFailures;

  /**
   * Gets {@link #notifyOnAllSyncFailures}.
   *
   * @return the value of {@link #notifyOnAllSyncFailures}
   */
  public Boolean getNotifyOnAllSyncFailures() {
    return notifyOnAllSyncFailures;
  }

  /**
   * Sets {@link #notifyOnAllSyncFailures}.
   *
   * @param notifyOnAllSyncFailures the new value
   */
  public void setNotifyOnAllSyncFailures(Boolean notifyOnAllSyncFailures) {
    this.notifyOnAllSyncFailures = notifyOnAllSyncFailures;
  }

  /**
   * Should the user receive automation failures via email?
   */
  @JsonProperty("notify_on_all_automation_failures")
  public Boolean notifyOnAllAutomationFailures;

  /**
   * Gets {@link #notifyOnAllAutomationFailures}.
   *
   * @return the value of {@link #notifyOnAllAutomationFailures}
   */
  public Boolean getNotifyOnAllAutomationFailures() {
    return notifyOnAllAutomationFailures;
  }

  /**
   * Sets {@link #notifyOnAllAutomationFailures}.
   *
   * @param notifyOnAllAutomationFailures the new value
   */
  public void setNotifyOnAllAutomationFailures(Boolean notifyOnAllAutomationFailures) {
    this.notifyOnAllAutomationFailures = notifyOnAllAutomationFailures;
  }

  /**
   * Should the user receive expectation failures and misses via email?
   */
  @JsonProperty("notify_on_all_expectation_failures")
  public Boolean notifyOnAllExpectationFailures;

  /**
   * Gets {@link #notifyOnAllExpectationFailures}.
   *
   * @return the value of {@link #notifyOnAllExpectationFailures}
   */
  public Boolean getNotifyOnAllExpectationFailures() {
    return notifyOnAllExpectationFailures;
  }

  /**
   * Sets {@link #notifyOnAllExpectationFailures}.
   *
   * @param notifyOnAllExpectationFailures the new value
   */
  public void setNotifyOnAllExpectationFailures(Boolean notifyOnAllExpectationFailures) {
    this.notifyOnAllExpectationFailures = notifyOnAllExpectationFailures;
  }

  /**
   * 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.
   */
  @JsonProperty("require_2fa")
  public String require2fa;

  /**
   * Gets {@link #require2fa}.
   *
   * @return the value of {@link #require2fa}
   */
  public String getRequire2fa() {
    return require2fa;
  }

  /**
   * Sets {@link #require2fa}.
   *
   * @param require2fa the new value
   */
  public void setRequire2fa(String require2fa) {
    this.require2fa = require2fa;
  }

  /**
   * Require user to login by specified date otherwise it will be disabled.
   */
  @JsonProperty("require_login_by")
  public Date requireLoginBy;

  /**
   * Gets {@link #requireLoginBy}.
   *
   * @return the value of {@link #requireLoginBy}
   */
  public Date getRequireLoginBy() {
    return requireLoginBy;
  }

  /**
   * Sets {@link #requireLoginBy}.
   *
   * @param requireLoginBy the new value
   */
  public void setRequireLoginBy(Date requireLoginBy) {
    this.requireLoginBy = requireLoginBy;
  }

  /**
   * Is 2fa active for the user?
   */
  @JsonProperty("active_2fa")
  public Boolean active2fa;

  /**
   * Gets {@link #active2fa}.
   *
   * @return the value of {@link #active2fa}
   */
  public Boolean getActive2fa() {
    return active2fa;
  }

  /**
   * Sets {@link #active2fa}.
   *
   * @param active2fa the new value
   */
  public void setActive2fa(Boolean active2fa) {
    this.active2fa = active2fa;
  }

  /**
   * Is a password change required upon next user login?
   */
  @JsonProperty("require_password_change")
  public Boolean requirePasswordChange;

  /**
   * Gets {@link #requirePasswordChange}.
   *
   * @return the value of {@link #requirePasswordChange}
   */
  public Boolean getRequirePasswordChange() {
    return requirePasswordChange;
  }

  /**
   * Sets {@link #requirePasswordChange}.
   *
   * @param requirePasswordChange the new value
   */
  public void setRequirePasswordChange(Boolean requirePasswordChange) {
    this.requirePasswordChange = requirePasswordChange;
  }

  /**
   * Is user's password expired?
   */
  @JsonProperty("password_expired")
  public Boolean passwordExpired;

  /**
   * Gets {@link #passwordExpired}.
   *
   * @return the value of {@link #passwordExpired}
   */
  public Boolean getPasswordExpired() {
    return passwordExpired;
  }

  /**
   * Sets {@link #passwordExpired}.
   *
   * @param passwordExpired the new value
   */
  public void setPasswordExpired(Boolean passwordExpired) {
    this.passwordExpired = passwordExpired;
  }

  /**
   * ID of the internal Group responsible for this Partner User, overriding the Partner default.
   */
  @JsonProperty("responsible_group_id")
  public Long responsibleGroupId;

  /**
   * Gets {@link #responsibleGroupId}.
   *
   * @return the value of {@link #responsibleGroupId}
   */
  public Long getResponsibleGroupId() {
    return responsibleGroupId;
  }

  /**
   * Sets {@link #responsibleGroupId}.
   *
   * @param responsibleGroupId the new value
   */
  public void setResponsibleGroupId(Long responsibleGroupId) {
    this.responsibleGroupId = responsibleGroupId;
  }

  /**
   * ID of the internal User responsible for this Partner User, overriding the Partner default.
   */
  @JsonProperty("responsible_user_id")
  public Long responsibleUserId;

  /**
   * Gets {@link #responsibleUserId}.
   *
   * @return the value of {@link #responsibleUserId}
   */
  public Long getResponsibleUserId() {
    return responsibleUserId;
  }

  /**
   * Sets {@link #responsibleUserId}.
   *
   * @param responsibleUserId the new value
   */
  public void setResponsibleUserId(Long responsibleUserId) {
    this.responsibleUserId = responsibleUserId;
  }

  /**
   * Is the user an allowed to view all (non-billing) site configuration for this site?
   */
  @JsonProperty("readonly_site_admin")
  public Boolean readonlySiteAdmin;

  /**
   * Gets {@link #readonlySiteAdmin}.
   *
   * @return the value of {@link #readonlySiteAdmin}
   */
  public Boolean getReadonlySiteAdmin() {
    return readonlySiteAdmin;
  }

  /**
   * Sets {@link #readonlySiteAdmin}.
   *
   * @param readonlySiteAdmin the new value
   */
  public void setReadonlySiteAdmin(Boolean readonlySiteAdmin) {
    this.readonlySiteAdmin = readonlySiteAdmin;
  }

  /**
   * Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)
   */
  @JsonProperty("restapi_permission")
  public Boolean restapiPermission;

  /**
   * Gets {@link #restapiPermission}.
   *
   * @return the value of {@link #restapiPermission}
   */
  public Boolean getRestapiPermission() {
    return restapiPermission;
  }

  /**
   * Sets {@link #restapiPermission}.
   *
   * @param restapiPermission the new value
   */
  public void setRestapiPermission(Boolean restapiPermission) {
    this.restapiPermission = restapiPermission;
  }

  /**
   * Can the user access the S3-compatible endpoint? Defaults to true.
   */
  @JsonProperty("s3_compatible_endpoint_permission")
  public Boolean s3CompatibleEndpointPermission;

  /**
   * Gets {@link #s3CompatibleEndpointPermission}.
   *
   * @return the value of {@link #s3CompatibleEndpointPermission}
   */
  public Boolean getS3CompatibleEndpointPermission() {
    return s3CompatibleEndpointPermission;
  }

  /**
   * Sets {@link #s3CompatibleEndpointPermission}.
   *
   * @param s3CompatibleEndpointPermission the new value
   */
  public void setS3CompatibleEndpointPermission(Boolean s3CompatibleEndpointPermission) {
    this.s3CompatibleEndpointPermission = s3CompatibleEndpointPermission;
  }

  /**
   * Does this user manage it's own credentials or is it a shared/bot user?
   */
  @JsonProperty("self_managed")
  public Boolean selfManaged;

  /**
   * Gets {@link #selfManaged}.
   *
   * @return the value of {@link #selfManaged}
   */
  public Boolean getSelfManaged() {
    return selfManaged;
  }

  /**
   * Sets {@link #selfManaged}.
   *
   * @param selfManaged the new value
   */
  public void setSelfManaged(Boolean selfManaged) {
    this.selfManaged = selfManaged;
  }

  /**
   * Can the user access with SFTP?
   */
  @JsonProperty("sftp_permission")
  public Boolean sftpPermission;

  /**
   * Gets {@link #sftpPermission}.
   *
   * @return the value of {@link #sftpPermission}
   */
  public Boolean getSftpPermission() {
    return sftpPermission;
  }

  /**
   * Sets {@link #sftpPermission}.
   *
   * @param sftpPermission the new value
   */
  public void setSftpPermission(Boolean sftpPermission) {
    this.sftpPermission = sftpPermission;
  }

  /**
   * Is the user an administrator for this site?
   */
  @JsonProperty("site_admin")
  public Boolean siteAdmin;

  /**
   * Gets {@link #siteAdmin}.
   *
   * @return the value of {@link #siteAdmin}
   */
  public Boolean getSiteAdmin() {
    return siteAdmin;
  }

  /**
   * Sets {@link #siteAdmin}.
   *
   * @param siteAdmin the new value
   */
  public void setSiteAdmin(Boolean siteAdmin) {
    this.siteAdmin = siteAdmin;
  }

  /**
   * Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.
   */
  @JsonProperty("workspace_admin")
  public Boolean workspaceAdmin;

  /**
   * Gets {@link #workspaceAdmin}.
   *
   * @return the value of {@link #workspaceAdmin}
   */
  public Boolean getWorkspaceAdmin() {
    return workspaceAdmin;
  }

  /**
   * Sets {@link #workspaceAdmin}.
   *
   * @param workspaceAdmin the new value
   */
  public void setWorkspaceAdmin(Boolean workspaceAdmin) {
    this.workspaceAdmin = workspaceAdmin;
  }

  /**
   * Site ID
   */
  @JsonProperty("site_id")
  public Long siteId;

  /**
   * Gets {@link #siteId}.
   *
   * @return the value of {@link #siteId}
   */
  public Long getSiteId() {
    return siteId;
  }

  /**
   * Sets {@link #siteId}.
   *
   * @param siteId the new value
   */
  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
   * ID of the Workspace the user belongs to. 0 is the Default Workspace.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * Workspace ID the user should land in by default when more than one Workspace is available.
   */
  @JsonProperty("default_workspace_id")
  public Long defaultWorkspaceId;

  /**
   * Gets {@link #defaultWorkspaceId}.
   *
   * @return the value of {@link #defaultWorkspaceId}
   */
  public Long getDefaultWorkspaceId() {
    return defaultWorkspaceId;
  }

  /**
   * Sets {@link #defaultWorkspaceId}.
   *
   * @param defaultWorkspaceId the new value
   */
  public void setDefaultWorkspaceId(Long defaultWorkspaceId) {
    this.defaultWorkspaceId = defaultWorkspaceId;
  }

  /**
   * Skip Welcome page in the UI?
   */
  @JsonProperty("skip_welcome_screen")
  public Boolean skipWelcomeScreen;

  /**
   * Gets {@link #skipWelcomeScreen}.
   *
   * @return the value of {@link #skipWelcomeScreen}
   */
  public Boolean getSkipWelcomeScreen() {
    return skipWelcomeScreen;
  }

  /**
   * Sets {@link #skipWelcomeScreen}.
   *
   * @param skipWelcomeScreen the new value
   */
  public void setSkipWelcomeScreen(Boolean skipWelcomeScreen) {
    this.skipWelcomeScreen = skipWelcomeScreen;
  }

  /**
   * SSL required setting
   */
  @JsonProperty("ssl_required")
  public String sslRequired;

  /**
   * Gets {@link #sslRequired}.
   *
   * @return the value of {@link #sslRequired}
   */
  public String getSslRequired() {
    return sslRequired;
  }

  /**
   * Sets {@link #sslRequired}.
   *
   * @param sslRequired the new value
   */
  public void setSslRequired(String sslRequired) {
    this.sslRequired = sslRequired;
  }

  /**
   * SSO (Single Sign On) strategy ID for the user, if applicable.
   */
  @JsonProperty("sso_strategy_id")
  public Long ssoStrategyId;

  /**
   * Gets {@link #ssoStrategyId}.
   *
   * @return the value of {@link #ssoStrategyId}
   */
  public Long getSsoStrategyId() {
    return ssoStrategyId;
  }

  /**
   * Sets {@link #ssoStrategyId}.
   *
   * @param ssoStrategyId the new value
   */
  public void setSsoStrategyId(Long ssoStrategyId) {
    this.ssoStrategyId = ssoStrategyId;
  }

  /**
   * Is the user subscribed to the newsletter?
   */
  @JsonProperty("subscribe_to_newsletter")
  public Boolean subscribeToNewsletter;

  /**
   * Gets {@link #subscribeToNewsletter}.
   *
   * @return the value of {@link #subscribeToNewsletter}
   */
  public Boolean getSubscribeToNewsletter() {
    return subscribeToNewsletter;
  }

  /**
   * Sets {@link #subscribeToNewsletter}.
   *
   * @param subscribeToNewsletter the new value
   */
  public void setSubscribeToNewsletter(Boolean subscribeToNewsletter) {
    this.subscribeToNewsletter = subscribeToNewsletter;
  }

  /**
   * Is this user managed by a SsoStrategy?
   */
  @JsonProperty("externally_managed")
  public Boolean externallyManaged;

  /**
   * Gets {@link #externallyManaged}.
   *
   * @return the value of {@link #externallyManaged}
   */
  public Boolean getExternallyManaged() {
    return externallyManaged;
  }

  /**
   * Sets {@link #externallyManaged}.
   *
   * @param externallyManaged the new value
   */
  public void setExternallyManaged(Boolean externallyManaged) {
    this.externallyManaged = externallyManaged;
  }

  /**
   * Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
   */
  @JsonProperty("tags")
  public String tags;

  /**
   * Gets {@link #tags}.
   *
   * @return the value of {@link #tags}
   */
  public String getTags() {
    return tags;
  }

  /**
   * Sets {@link #tags}.
   *
   * @param tags the new value
   */
  public void setTags(String tags) {
    this.tags = tags;
  }

  /**
   * User time zone
   */
  @JsonProperty("time_zone")
  public String timeZone;

  /**
   * Gets {@link #timeZone}.
   *
   * @return the value of {@link #timeZone}
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * Sets {@link #timeZone}.
   *
   * @param timeZone the new value
   */
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /**
   * Type(s) of 2FA methods in use, for programmatic use.  Will be either `sms`, `totp`, `webauthn`, `yubi`, `email`, or multiple values sorted alphabetically and joined by an underscore.  Does not specify whether user has more than one of a given method.
   */
  @JsonProperty("type_of_2fa")
  public String typeOf2fa;

  /**
   * Gets {@link #typeOf2fa}.
   *
   * @return the value of {@link #typeOf2fa}
   */
  public String getTypeOf2fa() {
    return typeOf2fa;
  }

  /**
   * Sets {@link #typeOf2fa}.
   *
   * @param typeOf2fa the new value
   */
  public void setTypeOf2fa(String typeOf2fa) {
    this.typeOf2fa = typeOf2fa;
  }

  /**
   * Type(s) of 2FA methods in use, formatted for displaying in the UI.  Unlike `type_of_2fa`, this value will make clear when a user has more than 1 of the same type of method.
   */
  @JsonProperty("type_of_2fa_for_display")
  public String typeOf2faForDisplay;

  /**
   * Gets {@link #typeOf2faForDisplay}.
   *
   * @return the value of {@link #typeOf2faForDisplay}
   */
  public String getTypeOf2faForDisplay() {
    return typeOf2faForDisplay;
  }

  /**
   * Sets {@link #typeOf2faForDisplay}.
   *
   * @param typeOf2faForDisplay the new value
   */
  public void setTypeOf2faForDisplay(String typeOf2faForDisplay) {
    this.typeOf2faForDisplay = typeOf2faForDisplay;
  }

  /**
   * If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.
   */
  @JsonProperty("user_root")
  public String userRoot;

  /**
   * Gets {@link #userRoot}.
   *
   * @return the value of {@link #userRoot}
   */
  public String getUserRoot() {
    return userRoot;
  }

  /**
   * Sets {@link #userRoot}.
   *
   * @param userRoot the new value
   */
  public void setUserRoot(String userRoot) {
    this.userRoot = userRoot;
  }

  /**
   * Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.
   */
  @JsonProperty("user_home")
  public String userHome;

  /**
   * Gets {@link #userHome}.
   *
   * @return the value of {@link #userHome}
   */
  public String getUserHome() {
    return userHome;
  }

  /**
   * Sets {@link #userHome}.
   *
   * @param userHome the new value
   */
  public void setUserHome(String userHome) {
    this.userHome = userHome;
  }

  /**
   * Number of days remaining until password expires
   */
  @JsonProperty("days_remaining_until_password_expire")
  public Long daysRemainingUntilPasswordExpire;

  /**
   * Gets {@link #daysRemainingUntilPasswordExpire}.
   *
   * @return the value of {@link #daysRemainingUntilPasswordExpire}
   */
  public Long getDaysRemainingUntilPasswordExpire() {
    return daysRemainingUntilPasswordExpire;
  }

  /**
   * Sets {@link #daysRemainingUntilPasswordExpire}.
   *
   * @param daysRemainingUntilPasswordExpire the new value
   */
  public void setDaysRemainingUntilPasswordExpire(Long daysRemainingUntilPasswordExpire) {
    this.daysRemainingUntilPasswordExpire = daysRemainingUntilPasswordExpire;
  }

  /**
   * Password expiration datetime
   */
  @JsonProperty("password_expire_at")
  public Date passwordExpireAt;

  /**
   * Gets {@link #passwordExpireAt}.
   *
   * @return the value of {@link #passwordExpireAt}
   */
  public Date getPasswordExpireAt() {
    return passwordExpireAt;
  }

  /**
   * Sets {@link #passwordExpireAt}.
   *
   * @param passwordExpireAt the new value
   */
  public void setPasswordExpireAt(Date passwordExpireAt) {
    this.passwordExpireAt = passwordExpireAt;
  }

  /**
   * An image file for your user avatar.
   */
  @JsonProperty("avatar_file")
  public byte[] avatarFile;

  /**
   * Gets {@link #avatarFile}.
   *
   * @return the value of {@link #avatarFile}
   */
  public byte[] getAvatarFile() {
    return avatarFile;
  }

  /**
   * Sets {@link #avatarFile}.
   *
   * @param avatarFile the new value
   */
  public void setAvatarFile(byte[] avatarFile) {
    this.avatarFile = avatarFile;
  }

  /**
   * If true, the avatar will be deleted.
   */
  @JsonProperty("avatar_delete")
  public Boolean avatarDelete;

  /**
   * Gets {@link #avatarDelete}.
   *
   * @return the value of {@link #avatarDelete}
   */
  public Boolean getAvatarDelete() {
    return avatarDelete;
  }

  /**
   * Sets {@link #avatarDelete}.
   *
   * @param avatarDelete the new value
   */
  public void setAvatarDelete(Boolean avatarDelete) {
    this.avatarDelete = avatarDelete;
  }

  /**
   * Used for changing a password on an existing user.
   */
  @JsonProperty("change_password")
  public String changePassword;

  /**
   * Gets {@link #changePassword}.
   *
   * @return the value of {@link #changePassword}
   */
  public String getChangePassword() {
    return changePassword;
  }

  /**
   * Sets {@link #changePassword}.
   *
   * @param changePassword the new value
   */
  public void setChangePassword(String changePassword) {
    this.changePassword = changePassword;
  }

  /**
   * Optional, but if provided, we will ensure that it matches the value sent in `change_password`.
   */
  @JsonProperty("change_password_confirmation")
  public String changePasswordConfirmation;

  /**
   * Gets {@link #changePasswordConfirmation}.
   *
   * @return the value of {@link #changePasswordConfirmation}
   */
  public String getChangePasswordConfirmation() {
    return changePasswordConfirmation;
  }

  /**
   * Sets {@link #changePasswordConfirmation}.
   *
   * @param changePasswordConfirmation the new value
   */
  public void setChangePasswordConfirmation(String changePasswordConfirmation) {
    this.changePasswordConfirmation = changePasswordConfirmation;
  }

  /**
   * Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.
   */
  @JsonProperty("grant_permission")
  public String grantPermission;

  /**
   * Gets {@link #grantPermission}.
   *
   * @return the value of {@link #grantPermission}
   */
  public String getGrantPermission() {
    return grantPermission;
  }

  /**
   * Sets {@link #grantPermission}.
   *
   * @param grantPermission the new value
   */
  public void setGrantPermission(String grantPermission) {
    this.grantPermission = grantPermission;
  }

  /**
   * Group ID to associate this user with.
   */
  @JsonProperty("group_id")
  public Long groupId;

  /**
   * Gets {@link #groupId}.
   *
   * @return the value of {@link #groupId}
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * Sets {@link #groupId}.
   *
   * @param groupId the new value
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.
   */
  @JsonProperty("imported_password_hash")
  public String importedPasswordHash;

  /**
   * Gets {@link #importedPasswordHash}.
   *
   * @return the value of {@link #importedPasswordHash}
   */
  public String getImportedPasswordHash() {
    return importedPasswordHash;
  }

  /**
   * Sets {@link #importedPasswordHash}.
   *
   * @param importedPasswordHash the new value
   */
  public void setImportedPasswordHash(String importedPasswordHash) {
    this.importedPasswordHash = importedPasswordHash;
  }

  /**
   * User password.
   */
  @JsonProperty("password")
  public String password;

  /**
   * Gets {@link #password}.
   *
   * @return the value of {@link #password}
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets {@link #password}.
   *
   * @param password the new value
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Optional, but if provided, we will ensure that it matches the value sent in `password`.
   */
  @JsonProperty("password_confirmation")
  public String passwordConfirmation;

  /**
   * Gets {@link #passwordConfirmation}.
   *
   * @return the value of {@link #passwordConfirmation}
   */
  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  /**
   * Sets {@link #passwordConfirmation}.
   *
   * @param passwordConfirmation the new value
   */
  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  /**
   * Signifies that the user has read all the announcements in the UI.
   */
  @JsonProperty("announcements_read")
  public Boolean announcementsRead;

  /**
   * Gets {@link #announcementsRead}.
   *
   * @return the value of {@link #announcementsRead}
   */
  public Boolean getAnnouncementsRead() {
    return announcementsRead;
  }

  /**
   * Sets {@link #announcementsRead}.
   *
   * @param announcementsRead the new value
   */
  public void setAnnouncementsRead(Boolean announcementsRead) {
    this.announcementsRead = announcementsRead;
  }

  /**
   * If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.
   */
  @JsonProperty("clear_2fa")
  public Boolean clear2fa;

  /**
   * Gets {@link #clear2fa}.
   *
   * @return the value of {@link #clear2fa}
   */
  public Boolean getClear2fa() {
    return clear2fa;
  }

  /**
   * Sets {@link #clear2fa}.
   *
   * @param clear2fa the new value
   */
  public void setClear2fa(Boolean clear2fa) {
    this.clear2fa = clear2fa;
  }

  /**
   * Required when assigning a Partner to an existing non-Partner user. If true, convert the user by assigning the partner_id provided.
   */
  @JsonProperty("convert_to_partner_user")
  public Boolean convertToPartnerUser;

  /**
   * Gets {@link #convertToPartnerUser}.
   *
   * @return the value of {@link #convertToPartnerUser}
   */
  public Boolean getConvertToPartnerUser() {
    return convertToPartnerUser;
  }

  /**
   * Sets {@link #convertToPartnerUser}.
   *
   * @param convertToPartnerUser the new value
   */
  public void setConvertToPartnerUser(Boolean convertToPartnerUser) {
    this.convertToPartnerUser = convertToPartnerUser;
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @param parameters the request's parameters, by their API names
   */
  public void unlock(HashMap<String, Object> parameters) throws IOException {
    filesClient().users().unlock(this.id, parameters, this.options);
  }

  /**
   * Resend user welcome email
   *
   * @param parameters the request's parameters, by their API names
   */
  public void resendWelcomeEmail(HashMap<String, Object> parameters) throws IOException {
    filesClient().users().resendWelcomeEmail(this.id, parameters, this.options);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @param parameters the request's parameters, by their API names
   */
  public void user2faReset(HashMap<String, Object> parameters) throws IOException {
    filesClient().users().user2faReset(this.id, parameters, this.options);
  }

  /**
   * Updates this User. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code avatar_file} (byte[] or Attachment): An image file for your user avatar.</li>
   * <li>{@code avatar_delete} (Boolean): If true, the avatar will be deleted.</li>
   * <li>{@code change_password} (String): Used for changing a password on an existing user.</li>
   * <li>{@code change_password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.</li>
   * <li>{@code email} (String): User's email.</li>
   * <li>{@code grant_permission} (String): Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.</li>
   * <li>{@code group_id} (Long): Group ID to associate this user with.</li>
   * <li>{@code group_ids} (String): A list of group ids to associate this user with.  Comma delimited.</li>
   * <li>{@code imported_password_hash} (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.</li>
   * <li>{@code password} (String): User password.</li>
   * <li>{@code password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.</li>
   * <li>{@code announcements_read} (Boolean): Signifies that the user has read all the announcements in the UI.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned directly to this user, if any.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code attachments_permission} (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.</li>
   * <li>{@code authenticate_until} (String): Scheduled Date/Time at which user will be deactivated</li>
   * <li>{@code authentication_method} (String): How is this user authenticated?</li>
   * <li>{@code billing_permission} (Boolean): Allow this user to perform operations on the account, payments, and invoices?</li>
   * <li>{@code bypass_user_lifecycle_rules} (Boolean): Exempt this user from user lifecycle rules?</li>
   * <li>{@code bypass_site_allowed_ips} (Boolean): Allow this user to skip site-wide IP blacklists?</li>
   * <li>{@code dav_permission} (Boolean): Can the user connect with WebDAV?</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned directly to this user, if any.</li>
   * <li>{@code default_workspace_id} (Long): Workspace ID the user should land in by default when more than one Workspace is available.</li>
   * <li>{@code disabled} (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.</li>
   * <li>{@code filesystem_layout} (String): File system layout</li>
   * <li>{@code ftp_permission} (Boolean): Can the user access with FTP/FTPS?</li>
   * <li>{@code header_text} (String): Text to display to the user in the header of the UI</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned directly to this user, if any.</li>
   * <li>{@code language} (String): Preferred language</li>
   * <li>{@code notification_daily_send_time} (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23</li>
   * <li>{@code name} (String): User's full name</li>
   * <li>{@code company} (String): User's company</li>
   * <li>{@code notes} (String): Any internal notes on the user</li>
   * <li>{@code office_integration_enabled} (Boolean): Enable integration with Office for the web?</li>
   * <li>{@code partner_admin} (Boolean): Is this user a Partner administrator?</li>
   * <li>{@code partner_id} (Long): Partner ID if this user belongs to a Partner</li>
   * <li>{@code password_validity_days} (Long): Number of days to allow user to use the same password</li>
   * <li>{@code primary_group_id} (Long): Primary group ID for Group Admin scoping</li>
   * <li>{@code readonly_site_admin} (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?</li>
   * <li>{@code receive_admin_alerts} (Boolean): Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.</li>
   * <li>{@code notify_on_all_site_warnings} (Boolean): Should the user receive site warnings via email?</li>
   * <li>{@code notify_on_all_sso_failures} (Boolean): Should the user receive sso/scim/ldap configuration/sync failures via email?</li>
   * <li>{@code notify_on_all_user_security_events} (Boolean): Should the user receive user security events via email?</li>
   * <li>{@code notify_on_all_pending_work_failures} (Boolean): Should the user receive pending work failures via email?</li>
   * <li>{@code notify_on_all_siem_http_destination_failures} (Boolean): Should the user receive siem failures via email?</li>
   * <li>{@code notify_on_all_sync_failures} (Boolean): Should the user receive sync failures via email?</li>
   * <li>{@code notify_on_all_automation_failures} (Boolean): Should the user receive automation failures via email?</li>
   * <li>{@code notify_on_all_expectation_failures} (Boolean): Should the user receive expectation failures and misses via email?</li>
   * <li>{@code require_login_by} (String): Require user to login by specified date otherwise it will be disabled.</li>
   * <li>{@code require_password_change} (Boolean): Is a password change required upon next user login?</li>
   * <li>{@code responsible_group_id} (Long): ID of the internal Group responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code responsible_user_id} (Long): ID of the internal User responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code restapi_permission} (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): Can the user access the S3-compatible endpoint? Defaults to true.</li>
   * <li>{@code self_managed} (Boolean): Does this user manage it's own credentials or is it a shared/bot user?</li>
   * <li>{@code sftp_permission} (Boolean): Can the user access with SFTP?</li>
   * <li>{@code site_admin} (Boolean): Is the user an administrator for this site?</li>
   * <li>{@code skip_welcome_screen} (Boolean): Skip Welcome page in the UI?</li>
   * <li>{@code ssl_required} (String): SSL required setting</li>
   * <li>{@code sso_strategy_id} (Long): SSO (Single Sign On) strategy ID for the user, if applicable.</li>
   * <li>{@code subscribe_to_newsletter} (Boolean): Is the user subscribed to the newsletter?</li>
   * <li>{@code require_2fa} (String): 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.</li>
   * <li>{@code tags} (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code time_zone} (String): User time zone</li>
   * <li>{@code user_root} (String): If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.</li>
   * <li>{@code user_home} (String): Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.</li>
   * <li>{@code workspace_admin} (Boolean): Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.</li>
   * <li>{@code username} (String): User's username</li>
   * <li>{@code workspace_id} (Long): Workspace ID. Only Site Administrators can change this field. Values supplied by Workspace Administrators, Group Administrators, or other non-Site Administrators using `/user` are ignored.</li>
   * <li>{@code clear_2fa} (Boolean): If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.</li>
   * <li>{@code convert_to_partner_user} (Boolean): Required when assigning a Partner to an existing non-Partner user. If true, convert the user by assigning the partner_id provided.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code User}
   */
  public User update(HashMap<String, Object> parameters) throws IOException {
    User updated = filesClient().users().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code new_owner_id} (Long): Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().users().delete(this.id, parameters, this.options);
  }

  /**
   * Delete User
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this User: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().users().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.avatarFile != null) {
      parameters.put("avatar_file", this.avatarFile);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(User canonical) {
    this.id = canonical.id;
    this.username = canonical.username;
    this.adminGroupIds = canonical.adminGroupIds;
    this.allowedIps = canonical.allowedIps;
    this.attachmentsPermission = canonical.attachmentsPermission;
    this.apiKeysCount = canonical.apiKeysCount;
    this.authenticateUntil = canonical.authenticateUntil;
    this.authenticationMethod = canonical.authenticationMethod;
    this.avatarUrl = canonical.avatarUrl;
    this.billable = canonical.billable;
    this.billingPermission = canonical.billingPermission;
    this.bypassSiteAllowedIps = canonical.bypassSiteAllowedIps;
    this.bypassUserLifecycleRules = canonical.bypassUserLifecycleRules;
    this.createdAt = canonical.createdAt;
    this.davPermission = canonical.davPermission;
    this.disabled = canonical.disabled;
    this.disabledExpiredOrInactive = canonical.disabledExpiredOrInactive;
    this.aiAssistantPersonalityId = canonical.aiAssistantPersonalityId;
    this.desktopConfigurationProfileId = canonical.desktopConfigurationProfileId;
    this.integrationCentricProfileId = canonical.integrationCentricProfileId;
    this.email = canonical.email;
    this.filesystemLayout = canonical.filesystemLayout;
    this.firstLoginAt = canonical.firstLoginAt;
    this.ftpPermission = canonical.ftpPermission;
    this.groupIds = canonical.groupIds;
    this.headerText = canonical.headerText;
    this.language = canonical.language;
    this.lastLoginAt = canonical.lastLoginAt;
    this.lastWebLoginAt = canonical.lastWebLoginAt;
    this.lastFtpLoginAt = canonical.lastFtpLoginAt;
    this.lastSftpLoginAt = canonical.lastSftpLoginAt;
    this.lastDavLoginAt = canonical.lastDavLoginAt;
    this.lastDesktopLoginAt = canonical.lastDesktopLoginAt;
    this.lastRestapiLoginAt = canonical.lastRestapiLoginAt;
    this.lastApiUseAt = canonical.lastApiUseAt;
    this.lastActiveAt = canonical.lastActiveAt;
    this.lastProtocolCipher = canonical.lastProtocolCipher;
    this.lockoutExpires = canonical.lockoutExpires;
    this.name = canonical.name;
    this.company = canonical.company;
    this.notes = canonical.notes;
    this.notificationDailySendTime = canonical.notificationDailySendTime;
    this.officeIntegrationEnabled = canonical.officeIntegrationEnabled;
    this.partnerAdmin = canonical.partnerAdmin;
    this.partnerId = canonical.partnerId;
    this.partnerName = canonical.partnerName;
    this.passwordSetAt = canonical.passwordSetAt;
    this.passwordValidityDays = canonical.passwordValidityDays;
    this.primaryGroupId = canonical.primaryGroupId;
    this.publicKeysCount = canonical.publicKeysCount;
    this.receiveAdminAlerts = canonical.receiveAdminAlerts;
    this.notifyOnAllSiteWarnings = canonical.notifyOnAllSiteWarnings;
    this.notifyOnAllSsoFailures = canonical.notifyOnAllSsoFailures;
    this.notifyOnAllUserSecurityEvents = canonical.notifyOnAllUserSecurityEvents;
    this.notifyOnAllPendingWorkFailures = canonical.notifyOnAllPendingWorkFailures;
    this.notifyOnAllSiemHttpDestinationFailures = canonical.notifyOnAllSiemHttpDestinationFailures;
    this.notifyOnAllSyncFailures = canonical.notifyOnAllSyncFailures;
    this.notifyOnAllAutomationFailures = canonical.notifyOnAllAutomationFailures;
    this.notifyOnAllExpectationFailures = canonical.notifyOnAllExpectationFailures;
    this.require2fa = canonical.require2fa;
    this.requireLoginBy = canonical.requireLoginBy;
    this.active2fa = canonical.active2fa;
    this.requirePasswordChange = canonical.requirePasswordChange;
    this.passwordExpired = canonical.passwordExpired;
    this.responsibleGroupId = canonical.responsibleGroupId;
    this.responsibleUserId = canonical.responsibleUserId;
    this.readonlySiteAdmin = canonical.readonlySiteAdmin;
    this.restapiPermission = canonical.restapiPermission;
    this.s3CompatibleEndpointPermission = canonical.s3CompatibleEndpointPermission;
    this.selfManaged = canonical.selfManaged;
    this.sftpPermission = canonical.sftpPermission;
    this.siteAdmin = canonical.siteAdmin;
    this.workspaceAdmin = canonical.workspaceAdmin;
    this.siteId = canonical.siteId;
    this.workspaceId = canonical.workspaceId;
    this.defaultWorkspaceId = canonical.defaultWorkspaceId;
    this.skipWelcomeScreen = canonical.skipWelcomeScreen;
    this.sslRequired = canonical.sslRequired;
    this.ssoStrategyId = canonical.ssoStrategyId;
    this.subscribeToNewsletter = canonical.subscribeToNewsletter;
    this.externallyManaged = canonical.externallyManaged;
    this.tags = canonical.tags;
    this.timeZone = canonical.timeZone;
    this.typeOf2fa = canonical.typeOf2fa;
    this.typeOf2faForDisplay = canonical.typeOf2faForDisplay;
    this.userRoot = canonical.userRoot;
    this.userHome = canonical.userHome;
    this.daysRemainingUntilPasswordExpire = canonical.daysRemainingUntilPasswordExpire;
    this.passwordExpireAt = canonical.passwordExpireAt;
    this.avatarFile = canonical.avatarFile;
    this.avatarDelete = canonical.avatarDelete;
    this.changePassword = canonical.changePassword;
    this.changePasswordConfirmation = canonical.changePasswordConfirmation;
    this.grantPermission = canonical.grantPermission;
    this.groupId = canonical.groupId;
    this.importedPasswordHash = canonical.importedPasswordHash;
    this.password = canonical.password;
    this.passwordConfirmation = canonical.passwordConfirmation;
    this.announcementsRead = canonical.announcementsRead;
    this.clear2fa = canonical.clear2fa;
    this.convertToPartnerUser = canonical.convertToPartnerUser;
  }

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<User> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<User> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Users
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id`, `company`, `name`, `disabled`, `authenticate_until`, `username`, `email`, `site_admin`, `last_desktop_login_at`, `last_login_at`, `password_validity_days` or `ssl_required`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `username`, `name`, `email`, `company`, `site_admin`, `password_validity_days`, `ssl_required`, `last_login_at`, `authenticate_until`, `not_site_admin`, `disabled`, `partner_id`, `primary_group_id` or `workspace_id`. Valid field combinations are `[ site_admin, username ]`, `[ not_site_admin, username ]`, `[ workspace_id, username ]`, `[ company, name ]`, `[ workspace_id, name ]`, `[ workspace_id, email ]`, `[ workspace_id, company ]`, `[ workspace_id, site_admin ]`, `[ workspace_id, not_site_admin ]`, `[ workspace_id, disabled ]`, `[ workspace_id, partner_id ]`, `[ workspace_id, site_admin, username ]`, `[ workspace_id, not_site_admin, username ]`, `[ workspace_id, disabled, username ]`, `[ workspace_id, partner_id, username ]` or `[ workspace_id, company, name ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `username`, `name`, `email` or `company`. Valid field combinations are `[ company, name ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `password_validity_days`, `last_login_at` or `authenticate_until`.</li>
   * <li>{@code ids} (String): comma-separated list of User IDs</li>
   * <li>{@code include_parent_site_users} (Boolean): Include users from the parent site.</li>
   * <li>{@code include_default_workspace_users} (Boolean): When scoped to a Custom Workspace, include Default Workspace users who have permissions in that Workspace. Available to Site Administrators, and to Workspace Administrators and Folder Admins in that Workspace when the site shares its Default Workspace directory with Workspaces.</li>
   * <li>{@code search} (String): Searches for partial matches of name, username, or email.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User} records, in a {@code ListIterator}
   */
  public static ListIterator<User> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().users().list(parameters, options);
  }

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<User> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Users
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<User> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static User find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static User find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static User find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User}
   */
  public static User find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().users().find(id, parameters, options);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static User get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static User get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User
   *
   * @see #create(HashMap, HashMap)
   */
  public static User create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User
   *
   * @see #create(HashMap, HashMap)
   */
  public static User create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code avatar_file} (byte[] or Attachment): An image file for your user avatar.</li>
   * <li>{@code avatar_delete} (Boolean): If true, the avatar will be deleted.</li>
   * <li>{@code change_password} (String): Used for changing a password on an existing user.</li>
   * <li>{@code change_password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.</li>
   * <li>{@code email} (String): User's email.</li>
   * <li>{@code grant_permission} (String): Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.</li>
   * <li>{@code group_id} (Long): Group ID to associate this user with.</li>
   * <li>{@code group_ids} (String): A list of group ids to associate this user with.  Comma delimited.</li>
   * <li>{@code imported_password_hash} (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.</li>
   * <li>{@code password} (String): User password.</li>
   * <li>{@code password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.</li>
   * <li>{@code announcements_read} (Boolean): Signifies that the user has read all the announcements in the UI.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned directly to this user, if any.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code attachments_permission} (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.</li>
   * <li>{@code authenticate_until} (String): Scheduled Date/Time at which user will be deactivated</li>
   * <li>{@code authentication_method} (String): How is this user authenticated?</li>
   * <li>{@code billing_permission} (Boolean): Allow this user to perform operations on the account, payments, and invoices?</li>
   * <li>{@code bypass_user_lifecycle_rules} (Boolean): Exempt this user from user lifecycle rules?</li>
   * <li>{@code bypass_site_allowed_ips} (Boolean): Allow this user to skip site-wide IP blacklists?</li>
   * <li>{@code dav_permission} (Boolean): Can the user connect with WebDAV?</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned directly to this user, if any.</li>
   * <li>{@code default_workspace_id} (Long): Workspace ID the user should land in by default when more than one Workspace is available.</li>
   * <li>{@code disabled} (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.</li>
   * <li>{@code filesystem_layout} (String): File system layout</li>
   * <li>{@code ftp_permission} (Boolean): Can the user access with FTP/FTPS?</li>
   * <li>{@code header_text} (String): Text to display to the user in the header of the UI</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned directly to this user, if any.</li>
   * <li>{@code language} (String): Preferred language</li>
   * <li>{@code notification_daily_send_time} (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23</li>
   * <li>{@code name} (String): User's full name</li>
   * <li>{@code company} (String): User's company</li>
   * <li>{@code notes} (String): Any internal notes on the user</li>
   * <li>{@code office_integration_enabled} (Boolean): Enable integration with Office for the web?</li>
   * <li>{@code partner_admin} (Boolean): Is this user a Partner administrator?</li>
   * <li>{@code partner_id} (Long): Partner ID if this user belongs to a Partner</li>
   * <li>{@code password_validity_days} (Long): Number of days to allow user to use the same password</li>
   * <li>{@code primary_group_id} (Long): Primary group ID for Group Admin scoping</li>
   * <li>{@code readonly_site_admin} (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?</li>
   * <li>{@code receive_admin_alerts} (Boolean): Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.</li>
   * <li>{@code notify_on_all_site_warnings} (Boolean): Should the user receive site warnings via email?</li>
   * <li>{@code notify_on_all_sso_failures} (Boolean): Should the user receive sso/scim/ldap configuration/sync failures via email?</li>
   * <li>{@code notify_on_all_user_security_events} (Boolean): Should the user receive user security events via email?</li>
   * <li>{@code notify_on_all_pending_work_failures} (Boolean): Should the user receive pending work failures via email?</li>
   * <li>{@code notify_on_all_siem_http_destination_failures} (Boolean): Should the user receive siem failures via email?</li>
   * <li>{@code notify_on_all_sync_failures} (Boolean): Should the user receive sync failures via email?</li>
   * <li>{@code notify_on_all_automation_failures} (Boolean): Should the user receive automation failures via email?</li>
   * <li>{@code notify_on_all_expectation_failures} (Boolean): Should the user receive expectation failures and misses via email?</li>
   * <li>{@code require_login_by} (String): Require user to login by specified date otherwise it will be disabled.</li>
   * <li>{@code require_password_change} (Boolean): Is a password change required upon next user login?</li>
   * <li>{@code responsible_group_id} (Long): ID of the internal Group responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code responsible_user_id} (Long): ID of the internal User responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code restapi_permission} (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): Can the user access the S3-compatible endpoint? Defaults to true.</li>
   * <li>{@code self_managed} (Boolean): Does this user manage it's own credentials or is it a shared/bot user?</li>
   * <li>{@code sftp_permission} (Boolean): Can the user access with SFTP?</li>
   * <li>{@code site_admin} (Boolean): Is the user an administrator for this site?</li>
   * <li>{@code skip_welcome_screen} (Boolean): Skip Welcome page in the UI?</li>
   * <li>{@code ssl_required} (String): SSL required setting</li>
   * <li>{@code sso_strategy_id} (Long): SSO (Single Sign On) strategy ID for the user, if applicable.</li>
   * <li>{@code subscribe_to_newsletter} (Boolean): Is the user subscribed to the newsletter?</li>
   * <li>{@code require_2fa} (String): 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.</li>
   * <li>{@code tags} (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code time_zone} (String): User time zone</li>
   * <li>{@code user_root} (String): If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.</li>
   * <li>{@code user_home} (String): Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.</li>
   * <li>{@code workspace_admin} (Boolean): Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.</li>
   * <li>{@code username} (String): Required. User's username</li>
   * <li>{@code workspace_id} (Long): ID of the Workspace the user belongs to. 0 is the Default Workspace.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User}
   */
  public static User create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().users().create(parameters, options);
  }


  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @see #unlock(Long, HashMap, HashMap)
   */
  public static void unlock() throws RuntimeException {
    unlock(null, null, null);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @see #unlock(Long, HashMap, HashMap)
   */
  public static void unlock(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    unlock(id, parameters, null);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @see #unlock(Long, HashMap, HashMap)
   */
  public static void unlock(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    unlock(null, parameters, options);
  }

  /**
   * Unlock user who has been locked out due to failed logins
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void unlock(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().users().unlock(id, parameters, options);
  }


  /**
   * Resend user welcome email
   *
   * @see #resendWelcomeEmail(Long, HashMap, HashMap)
   */
  public static void resendWelcomeEmail() throws RuntimeException {
    resendWelcomeEmail(null, null, null);
  }

  /**
   * Resend user welcome email
   *
   * @see #resendWelcomeEmail(Long, HashMap, HashMap)
   */
  public static void resendWelcomeEmail(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    resendWelcomeEmail(id, parameters, null);
  }

  /**
   * Resend user welcome email
   *
   * @see #resendWelcomeEmail(Long, HashMap, HashMap)
   */
  public static void resendWelcomeEmail(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    resendWelcomeEmail(null, parameters, options);
  }

  /**
   * Resend user welcome email
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void resendWelcomeEmail(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().users().resendWelcomeEmail(id, parameters, options);
  }


  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @see #user2faReset(Long, HashMap, HashMap)
   */
  public static void user2faReset() throws RuntimeException {
    user2faReset(null, null, null);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @see #user2faReset(Long, HashMap, HashMap)
   */
  public static void user2faReset(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    user2faReset(id, parameters, null);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @see #user2faReset(Long, HashMap, HashMap)
   */
  public static void user2faReset(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    user2faReset(null, parameters, options);
  }

  /**
   * Trigger 2FA Reset process for user who has lost access to their existing 2FA methods
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void user2faReset(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().users().user2faReset(id, parameters, options);
  }


  /**
   * Update User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static User update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static User update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update User
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static User update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code avatar_file} (byte[] or Attachment): An image file for your user avatar.</li>
   * <li>{@code avatar_delete} (Boolean): If true, the avatar will be deleted.</li>
   * <li>{@code change_password} (String): Used for changing a password on an existing user.</li>
   * <li>{@code change_password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `change_password`.</li>
   * <li>{@code email} (String): User's email.</li>
   * <li>{@code grant_permission} (String): Permission to grant on the User Root upon creation, or on the Workspace root when the User Root is blank. For the Default Workspace, a blank User Root uses the site root. `admin` on a Custom Workspace's root grants Workspace Administrator access; on a subfolder it grants Folder Admin access. Can be blank or `admin`, `full`, `read`, `write`, `list`, `read+write`, `list+write`, or `history`.</li>
   * <li>{@code group_id} (Long): Group ID to associate this user with.</li>
   * <li>{@code group_ids} (String): A list of group ids to associate this user with.  Comma delimited.</li>
   * <li>{@code imported_password_hash} (String): Pre-calculated hash of the user's password. If supplied, this will be used to authenticate the user on first login. Supported hash methods are MD5, SHA1, and SHA256.</li>
   * <li>{@code password} (String): User password.</li>
   * <li>{@code password_confirmation} (String): Optional, but if provided, we will ensure that it matches the value sent in `password`.</li>
   * <li>{@code announcements_read} (Boolean): Signifies that the user has read all the announcements in the UI.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned directly to this user, if any.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code attachments_permission} (Boolean): DEPRECATED: If `true`, the user can user create Bundles (aka Share Links). Use the bundle permission instead.</li>
   * <li>{@code authenticate_until} (String): Scheduled Date/Time at which user will be deactivated</li>
   * <li>{@code authentication_method} (String): How is this user authenticated?</li>
   * <li>{@code billing_permission} (Boolean): Allow this user to perform operations on the account, payments, and invoices?</li>
   * <li>{@code bypass_user_lifecycle_rules} (Boolean): Exempt this user from user lifecycle rules?</li>
   * <li>{@code bypass_site_allowed_ips} (Boolean): Allow this user to skip site-wide IP blacklists?</li>
   * <li>{@code dav_permission} (Boolean): Can the user connect with WebDAV?</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned directly to this user, if any.</li>
   * <li>{@code default_workspace_id} (Long): Workspace ID the user should land in by default when more than one Workspace is available.</li>
   * <li>{@code disabled} (Boolean): Is user disabled? Disabled users cannot log in, and do not count for billing purposes. Users can be automatically disabled after an inactivity period via a Site setting or schedule to be deactivated after specific date.</li>
   * <li>{@code filesystem_layout} (String): File system layout</li>
   * <li>{@code ftp_permission} (Boolean): Can the user access with FTP/FTPS?</li>
   * <li>{@code header_text} (String): Text to display to the user in the header of the UI</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned directly to this user, if any.</li>
   * <li>{@code language} (String): Preferred language</li>
   * <li>{@code notification_daily_send_time} (Long): Hour of the day at which daily notifications should be sent. Can be in range 0 to 23</li>
   * <li>{@code name} (String): User's full name</li>
   * <li>{@code company} (String): User's company</li>
   * <li>{@code notes} (String): Any internal notes on the user</li>
   * <li>{@code office_integration_enabled} (Boolean): Enable integration with Office for the web?</li>
   * <li>{@code partner_admin} (Boolean): Is this user a Partner administrator?</li>
   * <li>{@code partner_id} (Long): Partner ID if this user belongs to a Partner</li>
   * <li>{@code password_validity_days} (Long): Number of days to allow user to use the same password</li>
   * <li>{@code primary_group_id} (Long): Primary group ID for Group Admin scoping</li>
   * <li>{@code readonly_site_admin} (Boolean): Is the user an allowed to view all (non-billing) site configuration for this site?</li>
   * <li>{@code receive_admin_alerts} (Boolean): Deprecated. Use notify_on_all_site_warnings and granular failure notification preferences instead.</li>
   * <li>{@code notify_on_all_site_warnings} (Boolean): Should the user receive site warnings via email?</li>
   * <li>{@code notify_on_all_sso_failures} (Boolean): Should the user receive sso/scim/ldap configuration/sync failures via email?</li>
   * <li>{@code notify_on_all_user_security_events} (Boolean): Should the user receive user security events via email?</li>
   * <li>{@code notify_on_all_pending_work_failures} (Boolean): Should the user receive pending work failures via email?</li>
   * <li>{@code notify_on_all_siem_http_destination_failures} (Boolean): Should the user receive siem failures via email?</li>
   * <li>{@code notify_on_all_sync_failures} (Boolean): Should the user receive sync failures via email?</li>
   * <li>{@code notify_on_all_automation_failures} (Boolean): Should the user receive automation failures via email?</li>
   * <li>{@code notify_on_all_expectation_failures} (Boolean): Should the user receive expectation failures and misses via email?</li>
   * <li>{@code require_login_by} (String): Require user to login by specified date otherwise it will be disabled.</li>
   * <li>{@code require_password_change} (Boolean): Is a password change required upon next user login?</li>
   * <li>{@code responsible_group_id} (Long): ID of the internal Group responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code responsible_user_id} (Long): ID of the internal User responsible for this Partner User, overriding the Partner default.</li>
   * <li>{@code restapi_permission} (Boolean): Can this user access the Web app, Desktop app, SDKs, or REST API?  (All of these tools use the API internally, so this is one unified permission set.)</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): Can the user access the S3-compatible endpoint? Defaults to true.</li>
   * <li>{@code self_managed} (Boolean): Does this user manage it's own credentials or is it a shared/bot user?</li>
   * <li>{@code sftp_permission} (Boolean): Can the user access with SFTP?</li>
   * <li>{@code site_admin} (Boolean): Is the user an administrator for this site?</li>
   * <li>{@code skip_welcome_screen} (Boolean): Skip Welcome page in the UI?</li>
   * <li>{@code ssl_required} (String): SSL required setting</li>
   * <li>{@code sso_strategy_id} (Long): SSO (Single Sign On) strategy ID for the user, if applicable.</li>
   * <li>{@code subscribe_to_newsletter} (Boolean): Is the user subscribed to the newsletter?</li>
   * <li>{@code require_2fa} (String): 2FA required setting. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.</li>
   * <li>{@code tags} (String): Comma-separated list of Tags for this user. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code time_zone} (String): User time zone</li>
   * <li>{@code user_root} (String): If filesystem layout is user_root, this path is the root path the user is fixed to for all interfaces. If the filesystem layout is site_root or partner_root, this acts as a root folder only for FTP and SFTP (SFTP applicability also requires a site-wide setting to be set). For partner_root layout, this path is relative to the Partner root folder for all callers and blank opts out of an additional protocol root. In this situation, this path is not applied to the API, Desktop, or Web interface.</li>
   * <li>{@code user_home} (String): Home folder for FTP/SFTP. For users with the partner_root filesystem layout, this path is relative to the Partner root folder. In all other cases, it is an absolute path. Only applies to FTP and SFTP, and not any other interface.</li>
   * <li>{@code workspace_admin} (Boolean): Whether the user is an administrator of their own Custom Workspace. Does not reflect administration granted through Permissions.</li>
   * <li>{@code username} (String): User's username</li>
   * <li>{@code workspace_id} (Long): Workspace ID. Only Site Administrators can change this field. Values supplied by Workspace Administrators, Group Administrators, or other non-Site Administrators using `/user` are ignored.</li>
   * <li>{@code clear_2fa} (Boolean): If true when changing authentication_method from `password` to `sso`, remove all two-factor methods. Ignored in all other cases.</li>
   * <li>{@code convert_to_partner_user} (Boolean): Required when assigning a Partner to an existing non-Partner user. If true, convert the user by assigning the partner_id provided.</li>
   * </ul>
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code User}
   */
  public static User update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().users().update(id, parameters, options);
  }


  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code new_owner_id} (Long): Provide a User ID here to transfer ownership of certain resources such as Automations and Share Links (Bundles) to that new user.</li>
   * </ul>
   *
   * @param id User ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().users().delete(id, parameters, options);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
