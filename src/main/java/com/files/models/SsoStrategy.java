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
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsoStrategy implements ModelInterface {
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

  // One mapper for every SsoStrategy, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SsoStrategy} with no attributes set.
   */
  public SsoStrategy() {
    this(null, null);
  }

  /**
   * Creates a {@code SsoStrategy} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SsoStrategy(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SsoStrategy} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SsoStrategy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * SSO Protocol
   */
  @JsonProperty("protocol")
  public String protocol;

  /**
   * Gets {@link #protocol}.
   *
   * @return the value of {@link #protocol}
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * Provider name
   */
  @JsonProperty("provider")
  public String provider;

  /**
   * Gets {@link #provider}.
   *
   * @return the value of {@link #provider}
   */
  public String getProvider() {
    return provider;
  }

  /**
   * Custom label for the SSO provider on the login page.
   */
  @JsonProperty("label")
  public String label;

  /**
   * Gets {@link #label}.
   *
   * @return the value of {@link #label}
   */
  public String getLabel() {
    return label;
  }

  /**
   * URL holding a custom logo for the SSO provider on the login page.
   */
  @JsonProperty("logo_url")
  public String logoUrl;

  /**
   * Gets {@link #logoUrl}.
   *
   * @return the value of {@link #logoUrl}
   */
  public String getLogoUrl() {
    return logoUrl;
  }

  /**
   * ID
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
   * Is strategy enabled?  This may become automatically set to `false` after a high number and duration of failures.
   */
  @JsonProperty("enabled")
  public Boolean enabled;

  /**
   * Gets {@link #enabled}.
   *
   * @return the value of {@link #enabled}
   */
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * Count of users with this SSO Strategy
   */
  @JsonProperty("user_count")
  public Long userCount;

  /**
   * Gets {@link #userCount}.
   *
   * @return the value of {@link #userCount}
   */
  public Long getUserCount() {
    return userCount;
  }

  /**
   * Identity provider sha256 cert fingerprint if saml_provider_metadata_url is not available.
   */
  @JsonProperty("saml_provider_cert_fingerprint")
  public String samlProviderCertFingerprint;

  /**
   * Gets {@link #samlProviderCertFingerprint}.
   *
   * @return the value of {@link #samlProviderCertFingerprint}
   */
  public String getSamlProviderCertFingerprint() {
    return samlProviderCertFingerprint;
  }

  /**
   * Identity provider issuer url
   */
  @JsonProperty("saml_provider_issuer_url")
  public String samlProviderIssuerUrl;

  /**
   * Gets {@link #samlProviderIssuerUrl}.
   *
   * @return the value of {@link #samlProviderIssuerUrl}
   */
  public String getSamlProviderIssuerUrl() {
    return samlProviderIssuerUrl;
  }

  /**
   * Custom identity provider metadata
   */
  @JsonProperty("saml_provider_metadata_content")
  public String samlProviderMetadataContent;

  /**
   * Gets {@link #samlProviderMetadataContent}.
   *
   * @return the value of {@link #samlProviderMetadataContent}
   */
  public String getSamlProviderMetadataContent() {
    return samlProviderMetadataContent;
  }

  /**
   * Metadata URL for the SAML identity provider
   */
  @JsonProperty("saml_provider_metadata_url")
  public String samlProviderMetadataUrl;

  /**
   * Gets {@link #samlProviderMetadataUrl}.
   *
   * @return the value of {@link #samlProviderMetadataUrl}
   */
  public String getSamlProviderMetadataUrl() {
    return samlProviderMetadataUrl;
  }

  /**
   * Identity provider SLO endpoint
   */
  @JsonProperty("saml_provider_slo_target_url")
  public String samlProviderSloTargetUrl;

  /**
   * Gets {@link #samlProviderSloTargetUrl}.
   *
   * @return the value of {@link #samlProviderSloTargetUrl}
   */
  public String getSamlProviderSloTargetUrl() {
    return samlProviderSloTargetUrl;
  }

  /**
   * Identity provider SSO endpoint if saml_provider_metadata_url is not available.
   */
  @JsonProperty("saml_provider_sso_target_url")
  public String samlProviderSsoTargetUrl;

  /**
   * Gets {@link #samlProviderSsoTargetUrl}.
   *
   * @return the value of {@link #samlProviderSsoTargetUrl}
   */
  public String getSamlProviderSsoTargetUrl() {
    return samlProviderSsoTargetUrl;
  }

  /**
   * SCIM authentication type.
   */
  @JsonProperty("scim_authentication_method")
  public String scimAuthenticationMethod;

  /**
   * Gets {@link #scimAuthenticationMethod}.
   *
   * @return the value of {@link #scimAuthenticationMethod}
   */
  public String getScimAuthenticationMethod() {
    return scimAuthenticationMethod;
  }

  /**
   * SCIM username.
   */
  @JsonProperty("scim_username")
  public String scimUsername;

  /**
   * Gets {@link #scimUsername}.
   *
   * @return the value of {@link #scimUsername}
   */
  public String getScimUsername() {
    return scimUsername;
  }

  /**
   * SCIM OAuth Access Token. Only returned when the token is first issued or reset.
   */
  @JsonProperty("scim_oauth_access_token")
  public String scimOauthAccessToken;

  /**
   * Gets {@link #scimOauthAccessToken}.
   *
   * @return the value of {@link #scimOauthAccessToken}
   */
  public String getScimOauthAccessToken() {
    return scimOauthAccessToken;
  }

  /**
   * SCIM OAuth Access Token Expiration Time.
   */
  @JsonProperty("scim_oauth_access_token_expires_at")
  public String scimOauthAccessTokenExpiresAt;

  /**
   * Gets {@link #scimOauthAccessTokenExpiresAt}.
   *
   * @return the value of {@link #scimOauthAccessTokenExpiresAt}
   */
  public String getScimOauthAccessTokenExpiresAt() {
    return scimOauthAccessTokenExpiresAt;
  }

  /**
   * Subdomain or domain name for your auth provider.   Example: `https://[subdomain].okta.com/`
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
   * Auto-provision users?
   */
  @JsonProperty("provision_users")
  public Boolean provisionUsers;

  /**
   * Gets {@link #provisionUsers}.
   *
   * @return the value of {@link #provisionUsers}
   */
  public Boolean getProvisionUsers() {
    return provisionUsers;
  }

  /**
   * Auto-provision group membership based on group memberships on the SSO side?
   */
  @JsonProperty("provision_groups")
  public Boolean provisionGroups;

  /**
   * Gets {@link #provisionGroups}.
   *
   * @return the value of {@link #provisionGroups}
   */
  public Boolean getProvisionGroups() {
    return provisionGroups;
  }

  /**
   * Auto-deprovision users?
   */
  @JsonProperty("deprovision_users")
  public Boolean deprovisionUsers;

  /**
   * Gets {@link #deprovisionUsers}.
   *
   * @return the value of {@link #deprovisionUsers}
   */
  public Boolean getDeprovisionUsers() {
    return deprovisionUsers;
  }

  /**
   * Auto-deprovision group membership based on group memberships on the SSO side?
   */
  @JsonProperty("deprovision_groups")
  public Boolean deprovisionGroups;

  /**
   * Gets {@link #deprovisionGroups}.
   *
   * @return the value of {@link #deprovisionGroups}
   */
  public Boolean getDeprovisionGroups() {
    return deprovisionGroups;
  }

  /**
   * Method used for deprovisioning users.
   */
  @JsonProperty("deprovision_behavior")
  public String deprovisionBehavior;

  /**
   * Gets {@link #deprovisionBehavior}.
   *
   * @return the value of {@link #deprovisionBehavior}
   */
  public String getDeprovisionBehavior() {
    return deprovisionBehavior;
  }

  /**
   * Comma-separated list of group names for groups to automatically add all auto-provisioned users to.
   */
  @JsonProperty("provision_group_default")
  public String provisionGroupDefault;

  /**
   * Gets {@link #provisionGroupDefault}.
   *
   * @return the value of {@link #provisionGroupDefault}
   */
  public String getProvisionGroupDefault() {
    return provisionGroupDefault;
  }

  /**
   * Comma-separated list of group names for groups (with optional wildcards) that will be excluded from auto-provisioning.
   */
  @JsonProperty("provision_group_exclusion")
  public String provisionGroupExclusion;

  /**
   * Gets {@link #provisionGroupExclusion}.
   *
   * @return the value of {@link #provisionGroupExclusion}
   */
  public String getProvisionGroupExclusion() {
    return provisionGroupExclusion;
  }

  /**
   * Comma-separated list of group names for groups (with optional wildcards) that will be auto-provisioned.
   */
  @JsonProperty("provision_group_inclusion")
  public String provisionGroupInclusion;

  /**
   * Gets {@link #provisionGroupInclusion}.
   *
   * @return the value of {@link #provisionGroupInclusion}
   */
  public String getProvisionGroupInclusion() {
    return provisionGroupInclusion;
  }

  /**
   * Comma or newline separated list of group names (with optional wildcards) to require membership for user provisioning.
   */
  @JsonProperty("provision_group_required")
  public String provisionGroupRequired;

  /**
   * Gets {@link #provisionGroupRequired}.
   *
   * @return the value of {@link #provisionGroupRequired}
   */
  public String getProvisionGroupRequired() {
    return provisionGroupRequired;
  }

  /**
   * Comma-separated list of group names whose members will be created with email_signup authentication.
   */
  @JsonProperty("provision_email_signup_groups")
  public String provisionEmailSignupGroups;

  /**
   * Gets {@link #provisionEmailSignupGroups}.
   *
   * @return the value of {@link #provisionEmailSignupGroups}
   */
  public String getProvisionEmailSignupGroups() {
    return provisionEmailSignupGroups;
  }

  /**
   * Comma-separated list of group names used to grant or remove Read-Only Site Admin access for users on this SSO provider. When this list is nonempty and a directory sync changes a user's group membership, membership in at least one listed group grants this access; membership in none removes it, including access granted manually. Directory syncs include SCIM requests from any provider on the site, LDAP syncs, and sign-ins that set the user's groups from the identity provider. Membership changes made any other way, including through the web app, API, SDKs, or CLI, do not affect administrator access.
   */
  @JsonProperty("provision_readonly_site_admin_groups")
  public String provisionReadonlySiteAdminGroups;

  /**
   * Gets {@link #provisionReadonlySiteAdminGroups}.
   *
   * @return the value of {@link #provisionReadonlySiteAdminGroups}
   */
  public String getProvisionReadonlySiteAdminGroups() {
    return provisionReadonlySiteAdminGroups;
  }

  /**
   * Comma-separated list of group names used to grant or remove Site Admin access for users on this SSO provider. When this list is nonempty and a directory sync changes a user's group membership, membership in at least one listed group grants this access; membership in none removes it, including access granted manually. Directory syncs include SCIM requests from any provider on the site, LDAP syncs, and sign-ins that set the user's groups from the identity provider. Membership changes made any other way, including through the web app, API, SDKs, or CLI, do not affect administrator access.
   */
  @JsonProperty("provision_site_admin_groups")
  public String provisionSiteAdminGroups;

  /**
   * Gets {@link #provisionSiteAdminGroups}.
   *
   * @return the value of {@link #provisionSiteAdminGroups}
   */
  public String getProvisionSiteAdminGroups() {
    return provisionSiteAdminGroups;
  }

  /**
   * Comma-separated list of group names whose members will be provisioned as Group Admins.
   */
  @JsonProperty("provision_group_admin_groups")
  public String provisionGroupAdminGroups;

  /**
   * Gets {@link #provisionGroupAdminGroups}.
   *
   * @return the value of {@link #provisionGroupAdminGroups}
   */
  public String getProvisionGroupAdminGroups() {
    return provisionGroupAdminGroups;
  }

  /**
   * Deprecated default for the attachments_permission value on auto-provisioned users.
   */
  @JsonProperty("provision_attachments_permission")
  public Boolean provisionAttachmentsPermission;

  /**
   * Gets {@link #provisionAttachmentsPermission}.
   *
   * @return the value of {@link #provisionAttachmentsPermission}
   */
  public Boolean getProvisionAttachmentsPermission() {
    return provisionAttachmentsPermission;
  }

  /**
   * Auto-provisioned users get WebDAV permission?
   */
  @JsonProperty("provision_dav_permission")
  public Boolean provisionDavPermission;

  /**
   * Gets {@link #provisionDavPermission}.
   *
   * @return the value of {@link #provisionDavPermission}
   */
  public Boolean getProvisionDavPermission() {
    return provisionDavPermission;
  }

  /**
   * Auto-provisioned users get FTP permission?
   */
  @JsonProperty("provision_ftp_permission")
  public Boolean provisionFtpPermission;

  /**
   * Gets {@link #provisionFtpPermission}.
   *
   * @return the value of {@link #provisionFtpPermission}
   */
  public Boolean getProvisionFtpPermission() {
    return provisionFtpPermission;
  }

  /**
   * Auto-provisioned users get SFTP permission?
   */
  @JsonProperty("provision_sftp_permission")
  public Boolean provisionSftpPermission;

  /**
   * Gets {@link #provisionSftpPermission}.
   *
   * @return the value of {@link #provisionSftpPermission}
   */
  public Boolean getProvisionSftpPermission() {
    return provisionSftpPermission;
  }

  /**
   * Default time zone for auto provisioned users.
   */
  @JsonProperty("provision_time_zone")
  public String provisionTimeZone;

  /**
   * Gets {@link #provisionTimeZone}.
   *
   * @return the value of {@link #provisionTimeZone}
   */
  public String getProvisionTimeZone() {
    return provisionTimeZone;
  }

  /**
   * Default company for auto provisioned users.
   */
  @JsonProperty("provision_company")
  public String provisionCompany;

  /**
   * Gets {@link #provisionCompany}.
   *
   * @return the value of {@link #provisionCompany}
   */
  public String getProvisionCompany() {
    return provisionCompany;
  }

  /**
   * 2FA required setting for auto provisioned users. `use_system_setting` uses the site-wide setting, including SSO exemptions. `always_require` and `never_require` override the site-wide setting when user-level overrides are allowed.
   */
  @JsonProperty("provision_require_2fa")
  public String provisionRequire2fa;

  /**
   * Gets {@link #provisionRequire2fa}.
   *
   * @return the value of {@link #provisionRequire2fa}
   */
  public String getProvisionRequire2fa() {
    return provisionRequire2fa;
  }

  /**
   * File System layout to use for auto provisioned users.
   */
  @JsonProperty("provision_filesystem_layout")
  public String provisionFilesystemLayout;

  /**
   * Gets {@link #provisionFilesystemLayout}.
   *
   * @return the value of {@link #provisionFilesystemLayout}
   */
  public String getProvisionFilesystemLayout() {
    return provisionFilesystemLayout;
  }

  /**
   * URL-friendly, unique identifier for Azure SAML configuration
   */
  @JsonProperty("provider_identifier")
  public String providerIdentifier;

  /**
   * Gets {@link #providerIdentifier}.
   *
   * @return the value of {@link #providerIdentifier}
   */
  public String getProviderIdentifier() {
    return providerIdentifier;
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
   * Domain name that will be appended to LDAP usernames
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
   * Should this strategy be displayed on the login page?
   */
  @JsonProperty("display_on_login_page")
  public Boolean displayOnLoginPage;

  /**
   * Gets {@link #displayOnLoginPage}.
   *
   * @return the value of {@link #displayOnLoginPage}
   */
  public Boolean getDisplayOnLoginPage() {
    return displayOnLoginPage;
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
   * Use LDAP server settings for scheduled provisioning while using this SSO provider for authentication?
   */
  @JsonProperty("ldap_provisioning_enabled")
  public Boolean ldapProvisioningEnabled;

  /**
   * Gets {@link #ldapProvisioningEnabled}.
   *
   * @return the value of {@link #ldapProvisioningEnabled}
   */
  public Boolean getLdapProvisioningEnabled() {
    return ldapProvisioningEnabled;
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
   * LDAP server type
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
   * Synchronize provisioning data with the SSO remote server
   *
   * @param parameters the request's parameters, by their API names
   */
  public void sync(HashMap<String, Object> parameters) throws IOException {
    filesClient().ssoStrategies().sync(this.id, parameters, this.options);
  }


  /**
   * List SSO Strategies
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoStrategy> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List SSO Strategies
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoStrategy> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List SSO Strategies
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SsoStrategy} records, in a {@code ListIterator}
   */
  public static ListIterator<SsoStrategy> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ssoStrategies().list(parameters, options);
  }

  /**
   * List SSO Strategies
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoStrategy> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List SSO Strategies
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SsoStrategy> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show SSO Strategy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoStrategy find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show SSO Strategy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoStrategy find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show SSO Strategy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoStrategy find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show SSO Strategy
   *
   * @param id Sso Strategy ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SsoStrategy}
   */
  public static SsoStrategy find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().ssoStrategies().find(id, parameters, options);
  }

  /**
   * Show SSO Strategy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoStrategy get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show SSO Strategy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SsoStrategy get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Synchronize provisioning data with the SSO remote server
   *
   * @see #sync(Long, HashMap, HashMap)
   */
  public static void sync() throws RuntimeException {
    sync(null, null, null);
  }

  /**
   * Synchronize provisioning data with the SSO remote server
   *
   * @see #sync(Long, HashMap, HashMap)
   */
  public static void sync(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    sync(id, parameters, null);
  }

  /**
   * Synchronize provisioning data with the SSO remote server
   *
   * @see #sync(Long, HashMap, HashMap)
   */
  public static void sync(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    sync(null, parameters, options);
  }

  /**
   * Synchronize provisioning data with the SSO remote server
   *
   * @param id Sso Strategy ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void sync(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().ssoStrategies().sync(id, parameters, options);
  }


}
