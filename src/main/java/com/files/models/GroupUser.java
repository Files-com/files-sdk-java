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
public class GroupUser implements ModelInterface {
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


  public GroupUser() {
    this(null, null);
  }

  public GroupUser(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public GroupUser(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Group name
  */
  @JsonProperty("group_name")
  public String groupName;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
  * Group ID
  */
  @JsonProperty("group_id")
  public Long groupId;

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
  * User ID
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
  * Is this user an administrator of this group?
  */
  @JsonProperty("admin")
  public Boolean admin;

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  /**
  * Comma-delimited list of usernames who belong to this group (separated by commas).
  */
  @JsonProperty("usernames")
  public String usernames;

  public String getUsernames() {
    return usernames;
  }

  public void setUsernames(String usernames) {
    this.usernames = usernames;
  }

  /**
  * Group User ID.
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public GroupUser update(HashMap<String, Object> parameters) throws IOException {
    return GroupUser.update(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID from which to remove user.
  *   user_id (required) - int64 - User ID to remove from group.
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    GroupUser.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    GroupUser.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  If provided, will return group_users of this user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   group_id - int64 - Group ID.  If provided, will return group_users of this group.
  */
  public static ListIterator<GroupUser> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<GroupUser> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<GroupUser> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }


    String url = String.format("%s%s/group_users", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<GroupUser>> typeReference = new TypeReference<List<GroupUser>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<GroupUser> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<GroupUser> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID to add user to.
  *   user_id (required) - int64 - User ID to add to group.
  *   admin - boolean - Is the user a group administrator?
  */
  public static GroupUser create() throws RuntimeException {
    return create(null, null);
  }

  public static GroupUser create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static GroupUser create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }
    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
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
  public static GroupUser update() throws RuntimeException {
    return update(null, null, null);
  }

  public static GroupUser update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static GroupUser update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static GroupUser update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }
    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("admin") && !(parameters.get("admin") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: admin must be of type Boolean parameters[\"admin\"]");
    }



    String url = String.format("%s%s/group_users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<GroupUser> typeReference = new TypeReference<GroupUser>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   group_id (required) - int64 - Group ID from which to remove user.
  *   user_id (required) - int64 - User ID to remove from group.
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("group_id") || parameters.get("group_id") == null) {
      throw new NullPointerException("Parameter missing: group_id parameters[\"group_id\"]");
    }
    if (!parameters.containsKey("user_id") || parameters.get("user_id") == null) {
      throw new NullPointerException("Parameter missing: user_id parameters[\"user_id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long || parameters.get("group_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long or Integer parameters[\"group_id\"]");
    }
    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }



    String url = String.format("%s%s/group_users/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
