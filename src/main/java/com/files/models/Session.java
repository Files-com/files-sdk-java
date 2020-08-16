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
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Session {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Session() {
    this(null, null);
  }

  public Session(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Session(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  private String id;

  /**
  * Session language
  */
  @Getter
  @Setter
  @JsonProperty("language")
  private String language;

  /**
  * Login token. If set, this token will allow your user to log in via browser at the domain in `login_token_domain`.
  */
  @Getter
  @Setter
  @JsonProperty("login_token")
  private String loginToken;

  /**
  * Domain to use with `login_token`.
  */
  @Getter
  @Setter
  @JsonProperty("login_token_domain")
  private String loginTokenDomain;

  /**
  * Maximum number of files to retrieve per folder for a directory listing.  This is based on the user's plan.
  */
  @Getter
  @Setter
  @JsonProperty("max_dir_listing_size")
  private Long maxDirListingSize;

  /**
  * Can access multiple regions?
  */
  @Getter
  @Setter
  @JsonProperty("multiple_regions")
  private Boolean multipleRegions;

  /**
  * Is this session read only?
  */
  @Getter
  @Setter
  @JsonProperty("read_only")
  private Boolean readOnly;

  /**
  * Initial root path to start the user's session in.
  */
  @Getter
  @Setter
  @JsonProperty("root_path")
  private String rootPath;

  /**
  * Site ID
  */
  @Getter
  @Setter
  @JsonProperty("site_id")
  private Long siteId;

  /**
  * Is SSL required for this user?  (If so, ensure all your communications with this user use SSL.)
  */
  @Getter
  @Setter
  @JsonProperty("ssl_required")
  private Boolean sslRequired;

  /**
  * Is strong TLS disabled for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure TLS versions for this user.)
  */
  @Getter
  @Setter
  @JsonProperty("tls_disabled")
  private Boolean tlsDisabled;

  /**
  * If true, this user needs to add a Two Factor Authentication method before performing any further actions.
  */
  @Getter
  @Setter
  @JsonProperty("two_factor_setup_needed")
  private Boolean twoFactorSetupNeeded;

  /**
  * Sent only if 2FA setup is needed. Is SMS two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_sms")
  private Boolean allowed2faMethodSms;

  /**
  * Sent only if 2FA setup is needed. Is TOTP two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_totp")
  private Boolean allowed2faMethodTotp;

  /**
  * Sent only if 2FA setup is needed. Is U2F two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_u2f")
  private Boolean allowed2faMethodU2f;

  /**
  * Sent only if 2FA setup is needed. Is Yubikey two factor authentication allowed?
  */
  @Getter
  @Setter
  @JsonProperty("allowed_2fa_method_yubi")
  private Boolean allowed2faMethodYubi;

  /**
  * Allow the user to provide file/folder modified at dates?  If false, the server will always use the current date/time.
  */
  @Getter
  @Setter
  @JsonProperty("use_provided_modified_at")
  private Boolean useProvidedModifiedAt;

  /**
  * Does this user want to use Windows line-ending emulation?  (CR vs CRLF)
  */
  @Getter
  @Setter
  @JsonProperty("windows_mode_ftp")
  private Boolean windowsModeFtp;

  /**
  * Username to sign in as
  */
  @Getter
  @Setter
  @JsonProperty("username")
  private String username;

  /**
  * Password for sign in
  */
  @Getter
  @Setter
  @JsonProperty("password")
  private String password;

  /**
  * If this user has a 2FA device, provide its OTP or code here.
  */
  @Getter
  @Setter
  @JsonProperty("otp")
  private String otp;

  /**
  * Identifier for a partially-completed login
  */
  @Getter
  @Setter
  @JsonProperty("partial_session_id")
  private String partialSessionId;


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The Session Object doesn't support updates.");
    } else {
      Session newObject = Session.create(parameters, this.options).get(0);
    }
  }

  /**
  * Parameters:
  *   username - string - Username to sign in as
  *   password - string - Password for sign in
  *   otp - string - If this user has a 2FA device, provide its OTP or code here.
  *   partial_session_id - string - Identifier for a partially-completed login
  */
  public static List<Session> create() throws IOException{
    return create(null,null);
  }
  public static List<Session> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static List<Session> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/sessions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Session>> typeReference = new TypeReference<List<Session>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   format - string
  *   session - object
  */
  public static List<Session> delete() throws IOException{
    return delete(null,null);
  }
  public static List<Session> delete( HashMap<String, Object> parameters) throws IOException {
    return delete(parameters, null);
  }


  public static List<Session> delete( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("format") && !(parameters.get("format") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: format must be of type String parameters[\"format\"]");
    }

    if (parameters.containsKey("session") && !(parameters.get("session") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: session must be of type Object parameters[\"session\"]");
    }

    String url = String.format("%s%s/sessions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Session>> typeReference = new TypeReference<List<Session>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<Session> destroy() throws IOException {
    return destroy(null, null);
  }

  public static List<Session> destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(parameters, options);
  }

}


