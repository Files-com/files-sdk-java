package com.files.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class SsoStrategy {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

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
  public String protocol;

  /**
  * Provider name
  */
  @Getter
  @JsonProperty("provider")
  public String provider;

  /**
  * Custom label for the SSO provider on the login page.
  */
  @Getter
  @JsonProperty("label")
  public String label;

  /**
  * URL holding a custom logo for the SSO provider on the login page.
  */
  @Getter
  @JsonProperty("logo_url")
  public String logoUrl;

  /**
  * ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Identity provider sha256 cert fingerprint if saml_provider_metadata_url is not available.
  */
  @Getter
  @JsonProperty("saml_provider_cert_fingerprint")
  public String samlProviderCertFingerprint;

  /**
  * Identity provider issuer url
  */
  @Getter
  @JsonProperty("saml_provider_issuer_url")
  public String samlProviderIssuerUrl;

  /**
  * Custom identity provider metadata
  */
  @Getter
  @JsonProperty("saml_provider_metadata_content")
  public String samlProviderMetadataContent;

  /**
  * Metadata URL for the SAML identity provider
  */
  @Getter
  @JsonProperty("saml_provider_metadata_url")
  public String samlProviderMetadataUrl;

  /**
  * Identity provider SLO endpoint
  */
  @Getter
  @JsonProperty("saml_provider_slo_target_url")
  public String samlProviderSloTargetUrl;

  /**
  * Identity provider SSO endpoint if saml_provider_metadata_url is not available.
  */
  @Getter
  @JsonProperty("saml_provider_sso_target_url")
  public String samlProviderSsoTargetUrl;

  /**
  * SCIM authentication type.
  */
  @Getter
  @JsonProperty("scim_authentication_method")
  public String scimAuthenticationMethod;

  /**
  * SCIM username.
  */
  @Getter
  @JsonProperty("scim_username")
  public String scimUsername;

  /**
  * SCIM OAuth Access Token.
  */
  @Getter
  @JsonProperty("scim_oauth_access_token")
  public String scimOauthAccessToken;

  /**
  * SCIM OAuth Access Token Expiration Time.
  */
  @Getter
  @JsonProperty("scim_oauth_access_token_expires_at")
  public String scimOauthAccessTokenExpiresAt;

  /**
  * Subdomain
  */
  @Getter
  @JsonProperty("subdomain")
  public String subdomain;

  /**
  * Auto-provision users?
  */
  @Getter
  @JsonProperty("provision_users")
  public Boolean provisionUsers;

  /**
  * Auto-provision group membership based on group memberships on the SSO side?
  */
  @Getter
  @JsonProperty("provision_groups")
  public Boolean provisionGroups;

  /**
  * Auto-deprovision users?
  */
  @Getter
  @JsonProperty("deprovision_users")
  public Boolean deprovisionUsers;

  /**
  * Auto-deprovision group membership based on group memberships on the SSO side?
  */
  @Getter
  @JsonProperty("deprovision_groups")
  public Boolean deprovisionGroups;

  /**
  * Method used for deprovisioning users.
  */
  @Getter
  @JsonProperty("deprovision_behavior")
  public String deprovisionBehavior;

  /**
  * Comma-separated list of group names for groups to automatically add all auto-provisioned users to.
  */
  @Getter
  @JsonProperty("provision_group_default")
  public String provisionGroupDefault;

  /**
  * Comma-separated list of group names for groups (with optional wildcards) that will be excluded from auto-provisioning.
  */
  @Getter
  @JsonProperty("provision_group_exclusion")
  public String provisionGroupExclusion;

  /**
  * Comma-separated list of group names for groups (with optional wildcards) that will be auto-provisioned.
  */
  @Getter
  @JsonProperty("provision_group_inclusion")
  public String provisionGroupInclusion;

  /**
  * Comma or newline separated list of group names (with optional wildcards) to require membership for user provisioning.
  */
  @Getter
  @JsonProperty("provision_group_required")
  public String provisionGroupRequired;

  /**
  * Comma-separated list of group names whose members will be created with email_signup authentication.
  */
  @Getter
  @JsonProperty("provision_email_signup_groups")
  public String provisionEmailSignupGroups;

  /**
  * Comma-separated list of group names whose members will be created as Site Admins.
  */
  @Getter
  @JsonProperty("provision_site_admin_groups")
  public String provisionSiteAdminGroups;

  /**
  * DEPRECATED: Auto-provisioned users get Sharing permission. Use a Group with the Bundle permission instead.
  */
  @Getter
  @JsonProperty("provision_attachments_permission")
  public Boolean provisionAttachmentsPermission;

  /**
  * Auto-provisioned users get WebDAV permission?
  */
  @Getter
  @JsonProperty("provision_dav_permission")
  public Boolean provisionDavPermission;

  /**
  * Auto-provisioned users get FTP permission?
  */
  @Getter
  @JsonProperty("provision_ftp_permission")
  public Boolean provisionFtpPermission;

  /**
  * Auto-provisioned users get SFTP permission?
  */
  @Getter
  @JsonProperty("provision_sftp_permission")
  public Boolean provisionSftpPermission;

  /**
  * Default time zone for auto provisioned users.
  */
  @Getter
  @JsonProperty("provision_time_zone")
  public String provisionTimeZone;

  /**
  * Default company for auto provisioned users.
  */
  @Getter
  @JsonProperty("provision_company")
  public String provisionCompany;

  /**
  * Base DN for looking up users in LDAP server
  */
  @Getter
  @JsonProperty("ldap_base_dn")
  public String ldapBaseDn;

  /**
  * Domain name that will be appended to LDAP usernames
  */
  @Getter
  @JsonProperty("ldap_domain")
  public String ldapDomain;

  /**
  * Is strategy enabled?  This may become automatically set to `false` after a high number and duration of failures.
  */
  @Getter
  @JsonProperty("enabled")
  public Boolean enabled;

  /**
  * LDAP host
  */
  @Getter
  @JsonProperty("ldap_host")
  public String ldapHost;

  /**
  * LDAP backup host
  */
  @Getter
  @JsonProperty("ldap_host_2")
  public String ldapHost2;

  /**
  * LDAP backup host
  */
  @Getter
  @JsonProperty("ldap_host_3")
  public String ldapHost3;

  /**
  * LDAP port
  */
  @Getter
  @JsonProperty("ldap_port")
  public Long ldapPort;

  /**
  * Use secure LDAP?
  */
  @Getter
  @JsonProperty("ldap_secure")
  public Boolean ldapSecure;

  /**
  * Username for signing in to LDAP server.
  */
  @Getter
  @JsonProperty("ldap_username")
  public String ldapUsername;

  /**
  * LDAP username field
  */
  @Getter
  @JsonProperty("ldap_username_field")
  public String ldapUsernameField;

  /**
  * Synchronize provisioning data with the SSO remote server
  */
  public SsoStrategy sync(HashMap<String, Object> parameters) {
    return sync(parameters);
  }



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<SsoStrategy> list() throws IOException {
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
  public static List<SsoStrategy> find() throws IOException {
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

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/sso_strategies/%s", urlParts);

    TypeReference<List<SsoStrategy>> typeReference = new TypeReference<List<SsoStrategy>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<SsoStrategy> get() throws IOException {
    return get(null, null, null);
  }

  public static List<SsoStrategy> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Synchronize provisioning data with the SSO remote server
  */
  public static SsoStrategy sync() throws IOException {
    return sync(null, null,null);
  }
  public static SsoStrategy sync(Long id,  HashMap<String, Object> parameters) throws IOException {
    return sync(id, parameters, null);
  }

  public static SsoStrategy sync(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return sync(null, parameters, options);
  }

  public static SsoStrategy sync(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/sso_strategies/%s/sync", urlParts);

    TypeReference<SsoStrategy> typeReference = new TypeReference<SsoStrategy>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}


