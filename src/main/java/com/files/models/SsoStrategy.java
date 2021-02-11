package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class SsoStrategy {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public SsoStrategy() {
    this(null, null);
  }

  public SsoStrategy(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SsoStrategy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * SSO Protocol
  */
  @Getter
  @JsonProperty("protocol")
  private Object[] protocol;

  /**
  * Provider name
  */
  @Getter
  @JsonProperty("provider")
  private String provider;

  /**
  * Custom label for the SSO provider on the login page.
  */
  @Getter
  @JsonProperty("label")
  private String label;

  /**
  * URL holding a custom logo for the SSO provider on the login page.
  */
  @Getter
  @JsonProperty("logo_url")
  private String logoUrl;

  /**
  * ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Identity provider sha256 cert fingerprint if saml_provider_metadata_url is not available.
  */
  @Getter
  @JsonProperty("saml_provider_cert_fingerprint")
  private String samlProviderCertFingerprint;

  /**
  * Identity provider issuer url
  */
  @Getter
  @JsonProperty("saml_provider_issuer_url")
  private String samlProviderIssuerUrl;

  /**
  * Custom identity provider metadata
  */
  @Getter
  @JsonProperty("saml_provider_metadata_content")
  private String samlProviderMetadataContent;

  /**
  * Metadata URL for the SAML identity provider
  */
  @Getter
  @JsonProperty("saml_provider_metadata_url")
  private String samlProviderMetadataUrl;

  /**
  * Identity provider SLO endpoint
  */
  @Getter
  @JsonProperty("saml_provider_slo_target_url")
  private String samlProviderSloTargetUrl;

  /**
  * Identity provider SSO endpoint if saml_provider_metadata_url is not available.
  */
  @Getter
  @JsonProperty("saml_provider_sso_target_url")
  private String samlProviderSsoTargetUrl;

  /**
  * SCIM authentication type.
  */
  @Getter
  @JsonProperty("scim_authentication_method")
  private String scimAuthenticationMethod;

  /**
  * SCIM username.
  */
  @Getter
  @JsonProperty("scim_username")
  private String scimUsername;

  /**
  * SCIM OAuth Access Token.
  */
  @Getter
  @JsonProperty("scim_oauth_access_token")
  private String scimOauthAccessToken;

  /**
  * SCIM OAuth Access Token Expiration Time.
  */
  @Getter
  @JsonProperty("scim_oauth_access_token_expires_at")
  private String scimOauthAccessTokenExpiresAt;

  /**
  * Subdomain
  */
  @Getter
  @JsonProperty("subdomain")
  private String subdomain;

  /**
  * Auto-provision users?
  */
  @Getter
  @JsonProperty("provision_users")
  private Boolean provisionUsers;

  /**
  * Auto-provision group membership based on group memberships on the SSO side?
  */
  @Getter
  @JsonProperty("provision_groups")
  private Boolean provisionGroups;

  /**
  * Auto-deprovision users?
  */
  @Getter
  @JsonProperty("deprovision_users")
  private Boolean deprovisionUsers;

  /**
  * Auto-deprovision group membership based on group memberships on the SSO side?
  */
  @Getter
  @JsonProperty("deprovision_groups")
  private Boolean deprovisionGroups;

  /**
  * Method used for deprovisioning users.
  */
  @Getter
  @JsonProperty("deprovision_behavior")
  private String deprovisionBehavior;

  /**
  * Comma-separated list of group names for groups to automatically add all auto-provisioned users to.
  */
  @Getter
  @JsonProperty("provision_group_default")
  private String provisionGroupDefault;

  /**
  * Comma-separated list of group names for groups (with optional wildcards) that will be excluded from auto-provisioning.
  */
  @Getter
  @JsonProperty("provision_group_exclusion")
  private String provisionGroupExclusion;

  /**
  * Comma-separated list of group names for groups (with optional wildcards) that will be auto-provisioned.
  */
  @Getter
  @JsonProperty("provision_group_inclusion")
  private String provisionGroupInclusion;

  /**
  * Comma or newline separated list of group names (with optional wildcards) to require membership for user provisioning.
  */
  @Getter
  @JsonProperty("provision_group_required")
  private String provisionGroupRequired;

  /**
  * Comma-separated list of group names whose members will be created as Site Admins.
  */
  @Getter
  @JsonProperty("provision_site_admin_groups")
  private String provisionSiteAdminGroups;

  /**
  * DEPRECATED: Auto-provisioned users get Sharing permission. Use a Group with the Bundle permission instead.
  */
  @Getter
  @JsonProperty("provision_attachments_permission")
  private Boolean provisionAttachmentsPermission;

  /**
  * Auto-provisioned users get WebDAV permission?
  */
  @Getter
  @JsonProperty("provision_dav_permission")
  private Boolean provisionDavPermission;

  /**
  * Auto-provisioned users get FTP permission?
  */
  @Getter
  @JsonProperty("provision_ftp_permission")
  private Boolean provisionFtpPermission;

  /**
  * Auto-provisioned users get SFTP permission?
  */
  @Getter
  @JsonProperty("provision_sftp_permission")
  private Boolean provisionSftpPermission;

  /**
  * Default time zone for auto provisioned users.
  */
  @Getter
  @JsonProperty("provision_time_zone")
  private String provisionTimeZone;

  /**
  * Default company for auto provisioned users.
  */
  @Getter
  @JsonProperty("provision_company")
  private String provisionCompany;

  /**
  * Base DN for looking up users in LDAP server
  */
  @Getter
  @JsonProperty("ldap_base_dn")
  private String ldapBaseDn;

  /**
  * Domain name that will be appended to LDAP usernames
  */
  @Getter
  @JsonProperty("ldap_domain")
  private String ldapDomain;

  /**
  * Is strategy enabled?
  */
  @Getter
  @JsonProperty("enabled")
  private Boolean enabled;

  /**
  * LDAP host
  */
  @Getter
  @JsonProperty("ldap_host")
  private String ldapHost;

  /**
  * LDAP backup host
  */
  @Getter
  @JsonProperty("ldap_host_2")
  private String ldapHost2;

  /**
  * LDAP backup host
  */
  @Getter
  @JsonProperty("ldap_host_3")
  private String ldapHost3;

  /**
  * LDAP port
  */
  @Getter
  @JsonProperty("ldap_port")
  private Long ldapPort;

  /**
  * Use secure LDAP?
  */
  @Getter
  @JsonProperty("ldap_secure")
  private Boolean ldapSecure;

  /**
  * Username for signing in to LDAP server.
  */
  @Getter
  @JsonProperty("ldap_username")
  private String ldapUsername;

  /**
  * LDAP username field
  */
  @Getter
  @JsonProperty("ldap_username_field")
  private String ldapUsernameField;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<SsoStrategy> list() throws IOException{
    return list(null,null);
  }
  public static List<SsoStrategy> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<SsoStrategy> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    String url = String.format("%s%s/sso_strategies", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<SsoStrategy>> typeReference = new TypeReference<List<SsoStrategy>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<SsoStrategy> all() throws IOException {
    return all(null, null);
  }

  public static List<SsoStrategy> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Sso Strategy ID.
  */
  public static List<SsoStrategy> find() throws IOException{
    return find(null, null,null);
  }
  public static List<SsoStrategy> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<SsoStrategy> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<SsoStrategy> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/sso_strategies/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<SsoStrategy>> typeReference = new TypeReference<List<SsoStrategy>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<SsoStrategy> get() throws IOException {
    return get(null, null, null);
  }

  public static List<SsoStrategy> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

}


