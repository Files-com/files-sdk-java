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
public class SsoStrategy implements ModelInterface {
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


  public SsoStrategy() {
    this(null, null);
  }

  public SsoStrategy(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SsoStrategy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * SSO Protocol
  */
  @JsonProperty("protocol")
  public String protocol;

  public String getProtocol() {
    return protocol;
  }

  /**
  * Provider name
  */
  @JsonProperty("provider")
  public String provider;

  public String getProvider() {
    return provider;
  }

  /**
  * Custom label for the SSO provider on the login page.
  */
  @JsonProperty("label")
  public String label;

  public String getLabel() {
    return label;
  }

  /**
  * URL holding a custom logo for the SSO provider on the login page.
  */
  @JsonProperty("logo_url")
  public String logoUrl;

  public String getLogoUrl() {
    return logoUrl;
  }

  /**
  * ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Count of users with this SSO Strategy
  */
  @JsonProperty("user_count")
  public Long userCount;

  public Long getUserCount() {
    return userCount;
  }

  /**
  * Identity provider sha256 cert fingerprint if saml_provider_metadata_url is not available.
  */
  @JsonProperty("saml_provider_cert_fingerprint")
  public String samlProviderCertFingerprint;

  public String getSamlProviderCertFingerprint() {
    return samlProviderCertFingerprint;
  }

  /**
  * Identity provider issuer url
  */
  @JsonProperty("saml_provider_issuer_url")
  public String samlProviderIssuerUrl;

  public String getSamlProviderIssuerUrl() {
    return samlProviderIssuerUrl;
  }

  /**
  * Custom identity provider metadata
  */
  @JsonProperty("saml_provider_metadata_content")
  public String samlProviderMetadataContent;

  public String getSamlProviderMetadataContent() {
    return samlProviderMetadataContent;
  }

  /**
  * Metadata URL for the SAML identity provider
  */
  @JsonProperty("saml_provider_metadata_url")
  public String samlProviderMetadataUrl;

  public String getSamlProviderMetadataUrl() {
    return samlProviderMetadataUrl;
  }

  /**
  * Identity provider SLO endpoint
  */
  @JsonProperty("saml_provider_slo_target_url")
  public String samlProviderSloTargetUrl;

  public String getSamlProviderSloTargetUrl() {
    return samlProviderSloTargetUrl;
  }

  /**
  * Identity provider SSO endpoint if saml_provider_metadata_url is not available.
  */
  @JsonProperty("saml_provider_sso_target_url")
  public String samlProviderSsoTargetUrl;

  public String getSamlProviderSsoTargetUrl() {
    return samlProviderSsoTargetUrl;
  }

  /**
  * SCIM authentication type.
  */
  @JsonProperty("scim_authentication_method")
  public String scimAuthenticationMethod;

  public String getScimAuthenticationMethod() {
    return scimAuthenticationMethod;
  }

  /**
  * SCIM username.
  */
  @JsonProperty("scim_username")
  public String scimUsername;

  public String getScimUsername() {
    return scimUsername;
  }

  /**
  * SCIM OAuth Access Token.
  */
  @JsonProperty("scim_oauth_access_token")
  public String scimOauthAccessToken;

  public String getScimOauthAccessToken() {
    return scimOauthAccessToken;
  }

  /**
  * SCIM OAuth Access Token Expiration Time.
  */
  @JsonProperty("scim_oauth_access_token_expires_at")
  public String scimOauthAccessTokenExpiresAt;

  public String getScimOauthAccessTokenExpiresAt() {
    return scimOauthAccessTokenExpiresAt;
  }

  /**
  * Subdomain or domain name for your auth provider.   Example: `https://[subdomain].okta.com/`
  */
  @JsonProperty("subdomain")
  public String subdomain;

  public String getSubdomain() {
    return subdomain;
  }

  /**
  * Auto-provision users?
  */
  @JsonProperty("provision_users")
  public Boolean provisionUsers;

  public Boolean getProvisionUsers() {
    return provisionUsers;
  }

  /**
  * Auto-provision group membership based on group memberships on the SSO side?
  */
  @JsonProperty("provision_groups")
  public Boolean provisionGroups;

  public Boolean getProvisionGroups() {
    return provisionGroups;
  }

  /**
  * Auto-deprovision users?
  */
  @JsonProperty("deprovision_users")
  public Boolean deprovisionUsers;

  public Boolean getDeprovisionUsers() {
    return deprovisionUsers;
  }

  /**
  * Auto-deprovision group membership based on group memberships on the SSO side?
  */
  @JsonProperty("deprovision_groups")
  public Boolean deprovisionGroups;

  public Boolean getDeprovisionGroups() {
    return deprovisionGroups;
  }

  /**
  * Method used for deprovisioning users.
  */
  @JsonProperty("deprovision_behavior")
  public String deprovisionBehavior;

  public String getDeprovisionBehavior() {
    return deprovisionBehavior;
  }

  /**
  * Comma-separated list of group names for groups to automatically add all auto-provisioned users to.
  */
  @JsonProperty("provision_group_default")
  public String provisionGroupDefault;

  public String getProvisionGroupDefault() {
    return provisionGroupDefault;
  }

  /**
  * Comma-separated list of group names for groups (with optional wildcards) that will be excluded from auto-provisioning.
  */
  @JsonProperty("provision_group_exclusion")
  public String provisionGroupExclusion;

  public String getProvisionGroupExclusion() {
    return provisionGroupExclusion;
  }

  /**
  * Comma-separated list of group names for groups (with optional wildcards) that will be auto-provisioned.
  */
  @JsonProperty("provision_group_inclusion")
  public String provisionGroupInclusion;

  public String getProvisionGroupInclusion() {
    return provisionGroupInclusion;
  }

  /**
  * Comma or newline separated list of group names (with optional wildcards) to require membership for user provisioning.
  */
  @JsonProperty("provision_group_required")
  public String provisionGroupRequired;

  public String getProvisionGroupRequired() {
    return provisionGroupRequired;
  }

  /**
  * Comma-separated list of group names whose members will be created with email_signup authentication.
  */
  @JsonProperty("provision_email_signup_groups")
  public String provisionEmailSignupGroups;

  public String getProvisionEmailSignupGroups() {
    return provisionEmailSignupGroups;
  }

  /**
  * Comma-separated list of group names whose members will be created as Read-Only Site Admins.
  */
  @JsonProperty("provision_readonly_site_admin_groups")
  public String provisionReadonlySiteAdminGroups;

  public String getProvisionReadonlySiteAdminGroups() {
    return provisionReadonlySiteAdminGroups;
  }

  /**
  * Comma-separated list of group names whose members will be created as Site Admins.
  */
  @JsonProperty("provision_site_admin_groups")
  public String provisionSiteAdminGroups;

  public String getProvisionSiteAdminGroups() {
    return provisionSiteAdminGroups;
  }

  /**
  * Comma-separated list of group names whose members will be provisioned as Group Admins.
  */
  @JsonProperty("provision_group_admin_groups")
  public String provisionGroupAdminGroups;

  public String getProvisionGroupAdminGroups() {
    return provisionGroupAdminGroups;
  }

  /**
  */
  @JsonProperty("provision_attachments_permission")
  public Boolean provisionAttachmentsPermission;

  public Boolean getProvisionAttachmentsPermission() {
    return provisionAttachmentsPermission;
  }

  /**
  * Auto-provisioned users get WebDAV permission?
  */
  @JsonProperty("provision_dav_permission")
  public Boolean provisionDavPermission;

  public Boolean getProvisionDavPermission() {
    return provisionDavPermission;
  }

  /**
  * Auto-provisioned users get FTP permission?
  */
  @JsonProperty("provision_ftp_permission")
  public Boolean provisionFtpPermission;

  public Boolean getProvisionFtpPermission() {
    return provisionFtpPermission;
  }

  /**
  * Auto-provisioned users get SFTP permission?
  */
  @JsonProperty("provision_sftp_permission")
  public Boolean provisionSftpPermission;

  public Boolean getProvisionSftpPermission() {
    return provisionSftpPermission;
  }

  /**
  * Default time zone for auto provisioned users.
  */
  @JsonProperty("provision_time_zone")
  public String provisionTimeZone;

  public String getProvisionTimeZone() {
    return provisionTimeZone;
  }

  /**
  * Default company for auto provisioned users.
  */
  @JsonProperty("provision_company")
  public String provisionCompany;

  public String getProvisionCompany() {
    return provisionCompany;
  }

  /**
  * 2FA required setting for auto provisioned users.
  */
  @JsonProperty("provision_require_2fa")
  public String provisionRequire2fa;

  public String getProvisionRequire2fa() {
    return provisionRequire2fa;
  }

  /**
  * File System layout to use for auto provisioned users.
  */
  @JsonProperty("provision_filesystem_layout")
  public String provisionFilesystemLayout;

  public String getProvisionFilesystemLayout() {
    return provisionFilesystemLayout;
  }

  /**
  * URL-friendly, unique identifier for Azure SAML configuration
  */
  @JsonProperty("provider_identifier")
  public String providerIdentifier;

  public String getProviderIdentifier() {
    return providerIdentifier;
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
  * Domain name that will be appended to LDAP usernames
  */
  @JsonProperty("ldap_domain")
  public String ldapDomain;

  public String getLdapDomain() {
    return ldapDomain;
  }

  /**
  * Is strategy enabled?  This may become automatically set to `false` after a high number and duration of failures.
  */
  @JsonProperty("enabled")
  public Boolean enabled;

  public Boolean getEnabled() {
    return enabled;
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
  * Synchronize provisioning data with the SSO remote server
  */
  public void sync(HashMap<String, Object> parameters) throws IOException {
    SsoStrategy.sync(this.id, parameters, this.options);
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
  */
  public static ListIterator<SsoStrategy> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SsoStrategy> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SsoStrategy> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/sso_strategies", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<SsoStrategy>> typeReference = new TypeReference<List<SsoStrategy>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<SsoStrategy> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SsoStrategy> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Sso Strategy ID.
  */
  public static SsoStrategy find() throws RuntimeException {
    return find(null, null, null);
  }

  public static SsoStrategy find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static SsoStrategy find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static SsoStrategy find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/sso_strategies/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<SsoStrategy> typeReference = new TypeReference<SsoStrategy>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static SsoStrategy get() throws RuntimeException {
    return get(null, null, null);
  }

  public static SsoStrategy get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Synchronize provisioning data with the SSO remote server
  */
  public static void sync() throws RuntimeException {
    sync(null, null, null);
  }

  public static void sync(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    sync(id, parameters, null);
  }

  public static void sync(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    sync(null, parameters, options);
  }

  public static void sync(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/sso_strategies/%s/sync", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


}
