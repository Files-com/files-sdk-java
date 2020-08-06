package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class SsoStrategy {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public SsoStrategy() {
    this(null, null);
  }

  public SsoStrategy(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public SsoStrategy(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * SSO Protocol
  */
  @Getter
  @JsonProperty("protocol")
  public Object[] protocol;

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
  * Auto-provisioned users get Sharing permission?
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
  * Is strategy enabled?
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
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<SsoStrategy> list() throws IOException{
    return list(null,null);
  }
  public static List<SsoStrategy> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<SsoStrategy> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    String url = String.format("%s%s/sso_strategies", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<SsoStrategy>> typeReference = new TypeReference<List<SsoStrategy>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
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

  // TODO: Use types for path_and_primary_params
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
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<SsoStrategy> get() throws IOException {
    return get(null, null, null);
  }

  public static List<SsoStrategy> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

}


