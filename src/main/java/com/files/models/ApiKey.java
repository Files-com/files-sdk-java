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

public class ApiKey {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public ApiKey() {
    this(null, null);
  }

  public ApiKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ApiKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  private Long id;

  /**
  * Unique label that describes this API key.  Useful for external systems where you may have API keys from multiple accounts and want a human-readable label for each key.
  */
  @Getter
  @Setter
  @JsonProperty("descriptive_label")
  private String descriptiveLabel;

  /**
  * Time which API Key was created
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * API Key expiration date
  */
  @Getter
  @Setter
  @JsonProperty("expires_at")
  private Date expiresAt;

  /**
  * API Key actual key string
  */
  @Getter
  @Setter
  @JsonProperty("key")
  private String key;

  /**
  * API Key last used - note this value is only updated once per 3 hour period, so the 'actual' time of last use may be up to 3 hours later than this timestamp.
  */
  @Getter
  @Setter
  @JsonProperty("last_use_at")
  private Date lastUseAt;

  /**
  * Internal name for the API Key.  For your use.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  /**
  * Folder path restriction for this api key. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  private String path;

  /**
  * Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  @Getter
  @Setter
  @JsonProperty("permission_set")
  private String permissionSet;

  /**
  * If this API key represents a Desktop app, what platform was it created on?
  */
  @Getter
  @Setter
  @JsonProperty("platform")
  private String platform;

  /**
  * User ID for the owner of this API Key.  May be blank for Site-wide API Keys.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Parameters:
  *   name - string - Internal name for the API Key.  For your use.
  *   expires_at - string - API Key expiration date
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  public ApiKey update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public ApiKey delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      ApiKey newObject = ApiKey.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
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


  public static List<ApiKey> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Map<String, String> parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }

    String url = String.format("%s%s/api_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<ApiKey> all() throws IOException {
    return all(null, null);
  }

  public static List<ApiKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   format - string
  *   api_key - object
  */
  public static List<ApiKey> findCurrent() throws IOException{
    return findCurrent(null,null);
  }
  public static List<ApiKey> findCurrent( HashMap<String, Object> parameters) throws IOException {
    return findCurrent(parameters, null);
  }


  public static List<ApiKey> findCurrent( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("format") && !(parameters.get("format") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: format must be of type String parameters[\"format\"]");
    }

    if (parameters.containsKey("api_key") && !(parameters.get("api_key") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: api_key must be of type Map<String, String> parameters[\"api_key\"]");
    }

    String url = String.format("%s%s/api_key", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<ApiKey>> typeReference = new TypeReference<List<ApiKey>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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
  public static ApiKey create() throws IOException{
    return create(null,null);
  }
  public static ApiKey create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static ApiKey create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   expires_at - string - API Key expiration date
  *   name - string - Internal name for the API Key.  For your use.
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  public static ApiKey updateCurrent() throws IOException{
    return updateCurrent(null,null);
  }
  public static ApiKey updateCurrent( HashMap<String, Object> parameters) throws IOException {
    return updateCurrent(parameters, null);
  }


  public static ApiKey updateCurrent( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Internal name for the API Key.  For your use.
  *   expires_at - string - API Key expiration date
  *   permission_set - string - Permissions for this API Key.  Keys with the `desktop_app` permission set only have the ability to do the functions provided in our Desktop App (File and Share Link operations).  Additional permission sets may become available in the future, such as for a Site Admin to give a key with no administrator privileges.  If you have ideas for permission sets, please let us know.
  */
  public static ApiKey update() throws IOException{
    return update(null, null,null);
  }
  public static ApiKey update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static ApiKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static ApiKey update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   format - string
  *   api_key - object
  */
  public static ApiKey deleteCurrent() throws IOException{
    return deleteCurrent(null,null);
  }
  public static ApiKey deleteCurrent( HashMap<String, Object> parameters) throws IOException {
    return deleteCurrent(parameters, null);
  }


  public static ApiKey deleteCurrent( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("format") && !(parameters.get("format") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: format must be of type String parameters[\"format\"]");
    }

    if (parameters.containsKey("api_key") && !(parameters.get("api_key") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: api_key must be of type Map<String, String> parameters[\"api_key\"]");
    }

    String url = String.format("%s%s/api_key", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }


  /**
  */
  public static ApiKey delete() throws IOException{
    return delete(null, null,null);
  }
  public static ApiKey delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static ApiKey delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static ApiKey delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    TypeReference<ApiKey> typeReference = new TypeReference<ApiKey>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static ApiKey destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static ApiKey destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


