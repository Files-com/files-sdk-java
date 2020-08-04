package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

public class Session {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public Session(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper=new ObjectMapper();
      ObjectReader objectReader=objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Session ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public String id;

  /**
  * Session language
  */
  @Getter
  @Setter
  @JsonProperty("language")
  public String language;

  /**
  * Login token. If set, this token will allow your user to log in via browser at the domain in `login_token_domain`.
  */
  @Getter
  @Setter
  @JsonProperty("login_token")
  public String loginToken;

  /**
  * Domain to use with `login_token`.
  */
  @Getter
  @Setter
  @JsonProperty("login_token_domain")
  public String loginTokenDomain;

  /**
  * Maximum number of files to retrieve per folder for a directory listing.  This is based on the user's plan.
  */
  @Getter
  @Setter
  @JsonProperty("max_dir_listing_size")
  public Long maxDirListingSize;

  /**
  * Can access multiple regions?
  */
  @Getter
  @Setter
  @JsonProperty("multiple_regions")
  public Boolean multipleRegions;

  /**
  * Is this session read only?
  */
  @Getter
  @Setter
  @JsonProperty("read_only")
  public Boolean readOnly;

  /**
  * Initial root path to start the user's session in.
  */
  @Getter
  @Setter
  @JsonProperty("root_path")
  public String rootPath;

  /**
  * Site ID
  */
  @Getter
  @Setter
  @JsonProperty("site_id")
  public Long siteId;

  /**
  * Is SSL required for this user?  (If so, ensure all your communications with this user use SSL.)
  */
  @Getter
  @Setter
  @JsonProperty("ssl_required")
  public Boolean sslRequired;

  /**
  * Is strong TLS disabled for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure TLS versions for this user.)
  */
  @Getter
  @Setter
  @JsonProperty("tls_disabled")
  public Boolean tlsDisabled;

  /**
  * If true, this user needs to add a Two Factor Authentication method before performing any further actions.
  */
  @Getter
  @Setter
  @JsonProperty("two_factor_setup_needed")
  public Boolean twoFactorSetupNeeded;

  /**
  * Sent only if 2FA setup is needed. Is SMS two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_sms")
  public Boolean allowed2faMethodSms;

  /**
  * Sent only if 2FA setup is needed. Is TOTP two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_totp")
  public Boolean allowed2faMethodTotp;

  /**
  * Sent only if 2FA setup is needed. Is U2F two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_u2f")
  public Boolean allowed2faMethodU2f;

  /**
  * Sent only if 2FA setup is needed. Is Yubikey two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_yubi")
  public Boolean allowed2faMethodYubi;

  /**
  * Allow the user to provide file/folder modified at dates?  If false, the server will always use the current date/time.
  */
  @Getter
  @Setter
  @JsonProperty("use_provided_modified_at")
  public Boolean useProvidedModifiedAt;

  /**
  * Does this user want to use Windows line-ending emulation?  (CR vs CRLF)
  */
  @Getter
  @Setter
  @JsonProperty("windows_mode_ftp")
  public Boolean windowsModeFtp;

  /**
  * Username to sign in as
  */
  @Getter
  @Setter
  @JsonProperty("username")
  public String username;

  /**
  * Password for sign in
  */
  @Getter
  @Setter
  @JsonProperty("password")
  public String password;

  /**
  * If this user has a 2FA device, provide its OTP or code here.
  */
  @Getter
  @Setter
  @JsonProperty("otp")
  public String otp;

  /**
  * Identifier for a partially-completed login
  */
  @Getter
  @Setter
  @JsonProperty("partial_session_id")
  public String partialSessionId;


  public void save() {
    if (this.attributes.get("id") != null) {
      throw new UnsupportedOperationException("The Session Object doesn't support updates.");
    } else {
      Session newObj = Session.create(this.attributes, this.options);
      this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   username - string - Username to sign in as
  *   password - string - Password for sign in
  *   otp - string - If this user has a 2FA device, provide its OTP or code here.
  *   partial_session_id - string - Identifier for a partially-completed login
  */
  public static Session create( HashMap<String, Object> parameters) {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Session create( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("otp") && !(parameters.get("otp") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: otp must be of type String parameters[\"otp\"]");
    }

    if (parameters.containsKey("partial_session_id") && !(parameters.get("partial_session_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: partial_session_id must be of type String parameters[\"partial_session_id\"]");
    }

    // TODO: Send request
    return (Session) null;
  }


  /**
  */
  public static Session delete( HashMap<String, Object> parameters) {
    return delete(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static Session delete( HashMap<String, Object> parameters, HashMap<String, Object> options) {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    // TODO: Send request
    return (Session) null;
  }

  public static Session destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    return delete(parameters, options);
  }

}


