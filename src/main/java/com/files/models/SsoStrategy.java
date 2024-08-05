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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsoStrategy {
  private HashMap<String, Object> options;
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
  * Count of users with this SSO Strategy
  */
  @Getter
  @JsonProperty("user_count")
  public Long userCount;

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
  * Comma-separated list of group names whose members will be provisioned as Group Admins.
  */
  @Getter
  @JsonProperty("provision_group_admin_groups")
  public String provisionGroupAdminGroups;

  /**
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
  * 2FA required setting for auto provisioned users.
  */
  @Getter
  @JsonProperty("provision_require_2fa")
  public String provisionRequire2fa;

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
  public void sync() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    SsoStrategy.sync(this.id, parameters);
  }



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
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
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/sso_strategies/%s", urlParts);

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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/sso_strategies/%s/sync", urlParts);

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


}
