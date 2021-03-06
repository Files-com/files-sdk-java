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

public class Permission {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Permission() {
    this(null, null);
  }

  public Permission(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Permission(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Permission ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Folder path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  private String path;

  /**
  * User ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * User's username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  private String username;

  /**
  * Group ID
  */
  @Getter
  @Setter
  @JsonProperty("group_id")
  private Long groupId;

  /**
  * Group name if applicable
  */
  @Getter
  @Setter
  @JsonProperty("group_name")
  private String groupName;

  /**
  * Permission type
  */
  @Getter
  @Setter
  @JsonProperty("permission")
  private String permission;

  /**
  * Does this permission apply to subfolders?
  */
  @Getter
  @Setter
  @JsonProperty("recursive")
  private Boolean recursive;

  /**
  */
  public Permission delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The Permission Object doesn't support updates.");
    } else {
      Permission newObject = Permission.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `group_id`, `path`, `user_id` or `permission`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `group_id`, `user_id` or `path`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `group_id`, `user_id` or `path`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `group_id`, `user_id` or `path`.
  *   path - string - DEPRECATED: Permission path.  If provided, will scope permissions to this path. Use `filter[path]` instead.
  *   group_id - string - DEPRECATED: Group ID.  If provided, will scope permissions to this group. Use `filter[group_id]` instead.`
  *   user_id - string - DEPRECATED: User ID.  If provided, will scope permissions to this user. Use `filter[user_id]` instead.`
  *   include_groups - boolean - If searching by user or group, also include user's permissions that are inherited from its groups?
  */
  public static List<Permission> list() throws IOException{
    return list(null,null);
  }
  public static List<Permission> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Permission> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

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

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type String parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type String parameters[\"user_id\"]");
    }

    if (parameters.containsKey("include_groups") && !(parameters.get("include_groups") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: include_groups must be of type Boolean parameters[\"include_groups\"]");
    }

    String url = String.format("%s%s/permissions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Permission>> typeReference = new TypeReference<List<Permission>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Permission> all() throws IOException {
    return all(null, null);
  }

  public static List<Permission> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   group_id - int64 - Group ID
  *   path - string - Folder path
  *   permission - string -  Permission type.  Can be `admin`, `full`, `readonly`, `writeonly`, `list`, or `history`
  *   recursive - boolean - Apply to subfolders recursively?
  *   user_id - int64 - User ID.  Provide `username` or `user_id`
  *   username - string - User username.  Provide `username` or `user_id`
  */
  public static Permission create() throws IOException{
    return create(null,null);
  }
  public static Permission create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Permission create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("permission") && !(parameters.get("permission") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permission must be of type String parameters[\"permission\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    String url = String.format("%s%s/permissions", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<Permission> typeReference = new TypeReference<Permission>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  */
  public static Permission delete() throws IOException{
    return delete(null, null,null);
  }
  public static Permission delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static Permission delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Permission delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/permissions/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Permission> typeReference = new TypeReference<Permission>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Permission destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Permission destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


