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

public class GroupUser {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public GroupUser() {
    this(null, null);
  }

  public GroupUser(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public GroupUser(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Group name
  */
  @Getter
  @Setter
  @JsonProperty("group_name")
  private String groupName;

  /**
  * Group ID
  */
  @Getter
  @Setter
  @JsonProperty("group_id")
  private Long groupId;

  /**
  * User ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Is this user an administrator of this group?
  */
  @Getter
  @Setter
  @JsonProperty("admin")
  private Boolean admin;

  /**
  * A list of usernames for users in this group
  */
  @Getter
  @Setter
  @JsonProperty("usernames")
  private Object[] usernames;

  /**
  * Group User ID.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public GroupUser update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID from which to remove user.
  *   user_id (required) - int64 - User ID to remove from group.
  */
  public GroupUser delete(HashMap<String, Object> parameters) {
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
      GroupUser newObject = GroupUser.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  If provided, will return group_users of this user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   group_id - int64 - Group ID.  If provided, will return group_users of this group.
  */
  public static List<GroupUser> list() throws IOException{
    return list(null,null);
  }
  public static List<GroupUser> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<GroupUser> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    String url = String.format("%s%s/group_users", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<GroupUser>> typeReference = new TypeReference<List<GroupUser>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<GroupUser> all() throws IOException {
    return all(null, null);
  }

  public static List<GroupUser> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public static GroupUser create() throws IOException{
    return create(null,null);
  }
  public static GroupUser create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static GroupUser create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
    }

    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    String url = String.format("%s%s/group_users", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<GroupUser> typeReference = new TypeReference<GroupUser>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public static GroupUser update() throws IOException{
    return update(null, null,null);
  }
  public static GroupUser update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static GroupUser update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static GroupUser update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    String url = String.format("%s%s/group_users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<GroupUser> typeReference = new TypeReference<GroupUser>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID from which to remove user.
  *   user_id (required) - int64 - User ID to remove from group.
  */
  public static GroupUser delete() throws IOException{
    return delete(null, null,null);
  }
  public static GroupUser delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static GroupUser delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static GroupUser delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }
    String url = String.format("%s%s/group_users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<GroupUser> typeReference = new TypeReference<GroupUser>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static GroupUser destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static GroupUser destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


