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

public class ApiKey {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public ApiKey() {
    this(null, null);
  }

  public ApiKey(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public ApiKey(HashMap<String, Object> attributes, HashMap<String, Object> options) {
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
  * API Key ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Unique label that describes this API key.  Useful for external systems where you may have API keys from multiple accounts and want a human-readable label for each key.
  */
  @Getter
  @Setter
  @JsonProperty("descriptive_label")
  public String descriptiveLabel;

  /**
  * Time which API Key was created
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * API Key expiration date
  */
  @Getter
  @Setter
  @JsonProperty("expires_at")
  public Date expiresAt;

  /**
  * API Key actual key string
  */
  @Getter
  @Setter
  @JsonProperty("key")
  public String key;

  /**
  * API Key last used - note this value is only updated once per 3 hour period, so the 'actual' time of last use may be up to 3 hours later than this timestamp.
  */
  @Getter
  @Setter
  @JsonProperty("last_use_at")
  public Date lastUseAt;

  /**
  * Internal name for the API Key.  For your use.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * Folder path restriction for this api key. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  @Getter
  @Setter
  @JsonProperty("permission_set")
  public String permissionSet;

  /**
  * If this API key represents a Desktop app, what platform was it created on?
  */
  @Getter
  @Setter
  @JsonProperty("platform")
  public String platform;

  /**
  * User ID for the owner of this API Key.  May be blank for Site-wide API Keys.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Parameters:
  *   name - string - Internal name for the API Key.  For your use.
  *   expires_at - string - API Key expiration date
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  public ApiKey update(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (ApiKey) null;
  }

  /**
  */
  public ApiKey delete(HashMap<String, Object> parameters) {
    // TODO: Fill in operation implementation
    return (ApiKey) null;
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    if (this.attributes.get("id") != null) {
      update(this.attributes);
    } else {
      ApiKey.create(this.attributes, this.options);
      // TODO save this.attributes = newObj.attributes;
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `deleted_at` and `expires_at`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `expires_at`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `expires_at`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `expires_at`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `expires_at`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `expires_at`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `expires_at`.
  */
  public static List<ApiKey> list() throws IOException{
    return list(null,null);
  }
  public static List<ApiKey> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Object parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }

    String url = String.format("%s%s/api_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<ApiKey> all() throws IOException {
    return all(null, null);
  }

  public static List<ApiKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  */
  public static List<ApiKey> findCurrent() throws IOException{
    return findCurrent(null,null);
  }
  public static List<ApiKey> findCurrent( HashMap<String, Object> parameters) throws IOException {
    return findCurrent(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> findCurrent( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    String url = String.format("%s%s/api_key", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   id (required) - int64 - Api Key ID.
  */
  public static List<ApiKey> find() throws IOException{
    return find(null, null,null);
  }
  public static List<ApiKey> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<ApiKey> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/api_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<ApiKey> get() throws IOException {
    return get(null, null, null);
  }

  public static List<ApiKey> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   name - string - Internal name for the API Key.  For your use.
  *   expires_at - string - API Key expiration date
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  *   path - string - Folder path restriction for this api key.
  */
  public static List<ApiKey> create() throws IOException{
    return create(null,null);
  }
  public static List<ApiKey> create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String url = String.format("%s%s/api_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   expires_at - string - API Key expiration date
  *   name - string - Internal name for the API Key.  For your use.
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  public static List<ApiKey> updateCurrent() throws IOException{
    return updateCurrent(null,null);
  }
  public static List<ApiKey> updateCurrent( HashMap<String, Object> parameters) throws IOException {
    return updateCurrent(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> updateCurrent( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }

    String url = String.format("%s%s/api_key", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Internal name for the API Key.  For your use.
  *   expires_at - string - API Key expiration date
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  public static List<ApiKey> update() throws IOException{
    return update(null, null,null);
  }
  public static List<ApiKey> update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static List<ApiKey> update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("permission_set") && !(parameters.get("permission_set") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permission_set must be of type String parameters[\"permission_set\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/api_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static List<ApiKey> deleteCurrent() throws IOException{
    return deleteCurrent(null,null);
  }
  public static List<ApiKey> deleteCurrent( HashMap<String, Object> parameters) throws IOException {
    return deleteCurrent(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> deleteCurrent( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    String url = String.format("%s%s/api_key", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }


  /**
  */
  public static List<ApiKey> delete() throws IOException{
    return delete(null, null,null);
  }
  public static List<ApiKey> delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static List<ApiKey> delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  // TODO: Use types for path_and_primary_params
  public static List<ApiKey> delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/api_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.request(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static List<ApiKey> destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static List<ApiKey> destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


