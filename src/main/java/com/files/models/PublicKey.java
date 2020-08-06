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

public class PublicKey {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public PublicKey() {
    this(null, null);
  }

  public PublicKey(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public PublicKey(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * Public key ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Public key title
  */
  @Getter
  @Setter
  @JsonProperty("title")
  public String title;

  /**
  * Public key created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Public key fingerprint
  */
  @Getter
  @Setter
  @JsonProperty("fingerprint")
  public String fingerprint;

  /**
  * User ID.  Provide a value of `0` to operate the current session's user.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Actual contents of SSH key.
  */
  @Getter
  @Setter
  @JsonProperty("public_key")
  public String publicKey;

  /**
  * Parameters:
  *   title (required) - string - Internal reference for key.
  */
  public PublicKey update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (PublicKey) null;
  }

  /**
  */
  public PublicKey delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (PublicKey) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      PublicKey.create(this.attributes, this.options);
      // TODO save this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<PublicKey> list() throws IOException{
    return list(null,null);
  }
  public static List<PublicKey> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<PublicKey> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    String url = String.format("%s%s/public_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<PublicKey> all() throws IOException {
    return all(null, null);
  }

  public static List<PublicKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Public Key ID.
  */
  public static List<PublicKey> find() throws IOException{
    return find(null, null,null);
  }
  public static List<PublicKey> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<PublicKey> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<PublicKey> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/public_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<PublicKey> get() throws IOException {
    return get(null, null, null);
  }

  public static List<PublicKey> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   title (required) - string - Internal reference for key.
  *   public_key (required) - string - Actual contents of SSH key.
  */
  public static List<PublicKey> create() throws IOException{
    return create(null,null);
  }
  public static List<PublicKey> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<PublicKey> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }

    if (!parameters.containsKey("title") || parameters.get("title") == null) {
      throw new NullPointerException("Parameter missing: title parameters[\"title\"]");
    }
    if (!parameters.containsKey("public_key") || parameters.get("public_key") == null) {
      throw new NullPointerException("Parameter missing: public_key parameters[\"public_key\"]");
    }
    String url = String.format("%s%s/public_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   title (required) - string - Internal reference for key.
  */
  public static List<PublicKey> update() throws IOException{
    return update(null, null,null);
  }
  public static List<PublicKey> update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static List<PublicKey> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<PublicKey> update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("title") || parameters.get("title") == null) {
      throw new NullPointerException("Parameter missing: title parameters[\"title\"]");
    }
    String url = String.format("%s%s/public_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static List<PublicKey> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<PublicKey> delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static List<PublicKey> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<PublicKey> delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/public_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<PublicKey> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<PublicKey> destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


