package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
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
  * Is this session read only?
  */
  @Getter
  @Setter
  @JsonProperty("read_only")
  public Boolean readOnly;

  /**
  * Are insecure SFTP ciphers allowed for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure SSH ciphers for this user.)
  */
  @Getter
  @Setter
  @JsonProperty("sftp_insecure_ciphers")
  public Boolean sftpInsecureCiphers;

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


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The Session Object doesn't support updates.");
    } else {
      Session newObject = Session.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   username - string - Username to sign in as
  *   password - string - Password for sign in
  *   otp - string - If this user has a 2FA device, provide its OTP or code here.
  *   partial_session_id - string - Identifier for a partially-completed login
  */
  public static Session create() throws IOException {
    return create(null, null);
  }

  public static Session create(HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Session create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
  public static Session delete() throws IOException {
    return delete(null, null);
  }

  public static Session delete(HashMap<String, Object> parameters) throws IOException {
    return delete(parameters, null);
  }


  public static Session delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();





    String url = String.format("%s%s/sessions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Session> typeReference = new TypeReference<Session>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Session destroy() throws IOException {
    return destroy(null, null);
  }

  public static Session destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(parameters, options);
  }

}
