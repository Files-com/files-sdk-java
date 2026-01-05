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
public class UserCipherUse implements ModelInterface {
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


  public UserCipherUse() {
    this(null, null);
  }

  public UserCipherUse(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public UserCipherUse(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * UserCipherUse ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * ID of the user who performed this access
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  /**
  * Username of the user who performed this access
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  /**
  * The protocol and cipher employed
  */
  @JsonProperty("protocol_cipher")
  public String protocolCipher;

  public String getProtocolCipher() {
    return protocolCipher;
  }

  /**
  * The earliest recorded use of this combination of interface and protocol and cipher (for this user)
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Is this cipher considered insecure?
  */
  @JsonProperty("insecure")
  public Boolean insecure;

  public Boolean getInsecure() {
    return insecure;
  }

  /**
  * The interface accessed
  */
  @JsonProperty("interface")
  public String interfaceName;

  public String getInterfaceName() {
    return interfaceName;
  }

  /**
  * The most recent use of this combination of interface and protocol and cipher (for this user)
  */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }


  /**
  * Parameters:
  *   user_id - int64 - User ID. If provided, will return uses for this user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `updated_at`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `insecure` and `updated_at`. Valid field combinations are `[ insecure, updated_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `updated_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `updated_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `updated_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `updated_at`.
  */
  public static ListIterator<UserCipherUse> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<UserCipherUse> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<UserCipherUse> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/user_cipher_uses", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<UserCipherUse>> typeReference = new TypeReference<List<UserCipherUse>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<UserCipherUse> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<UserCipherUse> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
