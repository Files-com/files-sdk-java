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

public class Notification {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Notification() {
    this(null, null);
  }

  public Notification(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Notification(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Notification ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  private Long id;

  /**
  * Folder path to notify on This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  private String path;

  /**
  * Notification group id
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
  * Trigger notification on notification user actions?
  */
  @Getter
  @Setter
  @JsonProperty("notify_user_actions")
  private Boolean notifyUserActions;

  /**
  * Triggers notification when moving or copying files to this path
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_copy")
  private Boolean notifyOnCopy;

  /**
  * Enable notifications for each subfolder in this path
  */
  @Getter
  @Setter
  @JsonProperty("recursive")
  private Boolean recursive;

  /**
  * The time interval that notifications are aggregated to
  */
  @Getter
  @Setter
  @JsonProperty("send_interval")
  private String sendInterval;

  /**
  * Is the user unsubscribed from this notification?
  */
  @Getter
  @Setter
  @JsonProperty("unsubscribed")
  private Boolean unsubscribed;

  /**
  * The reason that the user unsubscribed
  */
  @Getter
  @Setter
  @JsonProperty("unsubscribed_reason")
  private String unsubscribedReason;

  /**
  * Notification user ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  private Long userId;

  /**
  * Notification username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  private String username;

  /**
  * If true, it means that the recipient at this user's email address has manually unsubscribed from all emails, or had their email "hard bounce", which means that we are unable to send mail to this user's current email address. Notifications will resume if the user changes their email address.
  */
  @Getter
  @Setter
  @JsonProperty("suppressed_email")
  private Boolean suppressedEmail;

  /**
  * Parameters:
  *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
  *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
  *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
  *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
  */
  public Notification update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public Notification delete(HashMap<String, Object> parameters) {
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
      Notification newObject = Notification.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - DEPRECATED: Show notifications for this User ID. Use `filter[user_id]` instead.
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `site_id`, `path`, `user_id` or `group_id`.
  *   filter - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
  *   filter_gt - object - If set, return records where the specifiied field is greater than the supplied value. Valid fields are `user_id`, `group_id` or `path`.
  *   filter_gteq - object - If set, return records where the specifiied field is greater than or equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
  *   filter_like - object - If set, return records where the specifiied field is equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
  *   filter_lt - object - If set, return records where the specifiied field is less than the supplied value. Valid fields are `user_id`, `group_id` or `path`.
  *   filter_lteq - object - If set, return records where the specifiied field is less than or equal to the supplied value. Valid fields are `user_id`, `group_id` or `path`.
  *   group_id - int64 - DEPRECATED: Show notifications for this Group ID. Use `filter[group_id]` instead.
  *   path - string - Show notifications for this Path.
  *   include_ancestors - boolean - If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.
  */
  public static List<Notification> list() throws IOException{
    return list(null,null);
  }
  public static List<Notification> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Notification> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("include_ancestors") && !(parameters.get("include_ancestors") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: include_ancestors must be of type Boolean parameters[\"include_ancestors\"]");
    }

    String url = String.format("%s%s/notifications", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Notification>> typeReference = new TypeReference<List<Notification>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Notification> all() throws IOException {
    return all(null, null);
  }

  public static List<Notification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Notification ID.
  */
  public static List<Notification> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Notification> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Notification> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<Notification> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/notifications/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Notification>> typeReference = new TypeReference<List<Notification>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Notification> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Notification> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - The id of the user to notify. Provide `user_id`, `username` or `group_id`.
  *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
  *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
  *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
  *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
  *   group_id - int64 - The ID of the group to notify.  Provide `user_id`, `username` or `group_id`.
  *   path - string - Path
  *   username - string - The username of the user to notify.  Provide `user_id`, `username` or `group_id`.
  */
  public static Notification create() throws IOException{
    return create(null,null);
  }
  public static Notification create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Notification create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("notify_on_copy") && !(parameters.get("notify_on_copy") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: notify_on_copy must be of type Boolean parameters[\"notify_on_copy\"]");
    }

    if (parameters.containsKey("notify_user_actions") && !(parameters.get("notify_user_actions") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: notify_user_actions must be of type Boolean parameters[\"notify_user_actions\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("send_interval") && !(parameters.get("send_interval") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: send_interval must be of type String parameters[\"send_interval\"]");
    }

    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    String url = String.format("%s%s/notifications", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
  *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
  *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
  *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
  */
  public static Notification update() throws IOException{
    return update(null, null,null);
  }
  public static Notification update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static Notification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static Notification update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("notify_on_copy") && !(parameters.get("notify_on_copy") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: notify_on_copy must be of type Boolean parameters[\"notify_on_copy\"]");
    }

    if (parameters.containsKey("notify_user_actions") && !(parameters.get("notify_user_actions") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: notify_user_actions must be of type Boolean parameters[\"notify_user_actions\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    if (parameters.containsKey("send_interval") && !(parameters.get("send_interval") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: send_interval must be of type String parameters[\"send_interval\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/notifications/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static Notification delete() throws IOException{
    return delete(null, null,null);
  }
  public static Notification delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static Notification delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Notification delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
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
    String url = String.format("%s%s/notifications/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Notification destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Notification destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


