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

public class UserCipherUse {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public UserCipherUse() {
    this(null, null);
  }

  public UserCipherUse(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public UserCipherUse(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * UserCipherUse ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * The protocol and cipher employed
  */
  @Getter
  @JsonProperty("protocol_cipher")
  private String protocolCipher;

  /**
  * The earliest recorded use of this combination of interface and protocol and cipher (for this user)
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * The interface accessed
  */
  @Getter
  @JsonProperty("interface")
  private String interfaceName;

  /**
  * The most recent use of this combination of interface and protocol and cipher (for this user)
  */
  @Getter
  @JsonProperty("updated_at")
  private Date updatedAt;

  /**
  * ID of the user who performed this access
  */
  @Getter
  @JsonProperty("user_id")
  private Long userId;



  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  */
  public static List<UserCipherUse> list() throws IOException{
    return list(null,null);
  }
  public static List<UserCipherUse> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<UserCipherUse> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    String url = String.format("%s%s/user_cipher_uses", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<UserCipherUse>> typeReference = new TypeReference<List<UserCipherUse>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<UserCipherUse> all() throws IOException {
    return all(null, null);
  }

  public static List<UserCipherUse> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


