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
public class Session implements ModelInterface {
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


  public Session() {
    this(null, null);
  }

  public Session(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Session(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Session ID
  */
  @JsonProperty("id")
  public String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  /**
  * Session language
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
  * Is this session read only?
  */
  @JsonProperty("read_only")
  public Boolean readOnly;

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  /**
  * Are insecure SFTP ciphers allowed for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure SSH ciphers for this user.)
  */
  @JsonProperty("sftp_insecure_ciphers")
  public Boolean sftpInsecureCiphers;

  public Boolean getSftpInsecureCiphers() {
    return sftpInsecureCiphers;
  }

  public void setSftpInsecureCiphers(Boolean sftpInsecureCiphers) {
    this.sftpInsecureCiphers = sftpInsecureCiphers;
  }

  /**
  * Username to sign in as
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
  * Password for sign in
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
  * If this user has a 2FA device, provide its OTP or code here.
  */
  @JsonProperty("otp")
  public String otp;

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  /**
  * Identifier for a partially-completed login
  */
  @JsonProperty("partial_session_id")
  public String partialSessionId;

  public String getPartialSessionId() {
    return partialSessionId;
  }

  public void setPartialSessionId(String partialSessionId) {
    this.partialSessionId = partialSessionId;
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Session.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   username - string - Username to sign in as
  *   password - string - Password for sign in
  *   otp - string - If this user has a 2FA device, provide its OTP or code here.
  *   partial_session_id - string - Identifier for a partially-completed login
  */
  public static Session create() throws RuntimeException {
    return create(null, null);
  }

  public static Session create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Session create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }
    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }
    if (parameters.containsKey("otp") && !(parameters.get("otp") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: otp must be of type String parameters[\"otp\"]");
    }
    if (parameters.containsKey("partial_session_id") && !(parameters.get("partial_session_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partial_session_id must be of type String parameters[\"partial_session_id\"]");
    }


    String url = String.format("%s%s/sessions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Session> typeReference = new TypeReference<Session>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null);
  }

  public static void delete(HashMap<String, Object> parameters) throws RuntimeException {
    delete(parameters, null);
  }


  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();





    String url = String.format("%s%s/sessions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null);
  }

  public static void destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(parameters, options);
  }

}
