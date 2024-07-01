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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Notification() {
    this(null, null);
  }

  public Notification(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Notification(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Notification ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Folder path to notify on This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * ID of Group to receive notifications
  */
  @Getter
  @Setter
  @JsonProperty("group_id")
  public Long groupId;

  /**
  * Group name, if a Group ID is set
  */
  @Getter
  @Setter
  @JsonProperty("group_name")
  public String groupName;

  /**
  * If set, will only notify on actions made by a member of one of the specified groups
  */
  @Getter
  @Setter
  @JsonProperty("triggering_group_ids")
  public Long[] triggeringGroupIds;

  /**
  * If set, will onlynotify on actions made one of the specified users
  */
  @Getter
  @Setter
  @JsonProperty("triggering_user_ids")
  public Long[] triggeringUserIds;

  /**
  * Notify when actions are performed by a share recipient?
  */
  @Getter
  @Setter
  @JsonProperty("trigger_by_share_recipients")
  public Boolean triggerByShareRecipients;

  /**
  * If true, will send notifications about a user's own activity to that user.  If false, only activity performed by other users (or anonymous users) will be sent in notifications.
  */
  @Getter
  @Setter
  @JsonProperty("notify_user_actions")
  public Boolean notifyUserActions;

  /**
  * Trigger on files copied to this path?
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_copy")
  public Boolean notifyOnCopy;

  /**
  * Trigger on files deleted in this path?
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_delete")
  public Boolean notifyOnDelete;

  /**
  * Trigger on files downloaded in this path?
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_download")
  public Boolean notifyOnDownload;

  /**
  * Trigger on files moved to this path?
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_move")
  public Boolean notifyOnMove;

  /**
  * Trigger on files created/uploaded/updated/changed in this path?
  */
  @Getter
  @Setter
  @JsonProperty("notify_on_upload")
  public Boolean notifyOnUpload;

  /**
  * Apply notification recursively?  This will enable notifications for each subfolder.
  */
  @Getter
  @Setter
  @JsonProperty("recursive")
  public Boolean recursive;

  /**
  * The time interval that notifications are aggregated to
  */
  @Getter
  @Setter
  @JsonProperty("send_interval")
  public String sendInterval;

  /**
  * Custom message to include in notification emails
  */
  @Getter
  @Setter
  @JsonProperty("message")
  public String message;

  /**
  * Array of filenames (possibly with wildcards) to scope trigger
  */
  @Getter
  @Setter
  @JsonProperty("triggering_filenames")
  public String[] triggeringFilenames;

  /**
  * Is the user unsubscribed from this notification?
  */
  @Getter
  @Setter
  @JsonProperty("unsubscribed")
  public Boolean unsubscribed;

  /**
  * The reason that the user unsubscribed
  */
  @Getter
  @Setter
  @JsonProperty("unsubscribed_reason")
  public String unsubscribedReason;

  /**
  * Notification user ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Notification username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  public String username;

  /**
  * If true, it means that the recipient at this user's email address has manually unsubscribed from all emails, or had their email "hard bounce", which means that we are unable to send mail to this user's current email address. Notifications will resume if the user changes their email address.
  */
  @Getter
  @Setter
  @JsonProperty("suppressed_email")
  public Boolean suppressedEmail;

  /**
  * Parameters:
  *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
  *   notify_on_delete - boolean - Trigger on files deleted in this path?
  *   notify_on_download - boolean - Trigger on files downloaded in this path?
  *   notify_on_move - boolean - Trigger on files moved to this path?
  *   notify_on_upload - boolean - Trigger on files created/uploaded/updated/changed in this path?
  *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
  *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
  *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
  *   message - string - Custom message to include in notification emails
  *   triggering_filenames - array(string) - Array of filenames (possibly with wildcards) to scope trigger
  *   triggering_group_ids - array(int64) - If set, will only notify on actions made by a member of one of the specified groups
  *   triggering_user_ids - array(int64) - If set, will onlynotify on actions made one of the specified users
  *   trigger_by_share_recipients - boolean - Notify when actions are performed by a share recipient?
  */
  public Notification update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      Notification.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string
  *   page - int64
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[path]=desc`). Valid fields are `path`, `user_id` or `group_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `user_id` or `group_id`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.
  *   path - string - Show notifications for this Path.
  *   include_ancestors - boolean - If `include_ancestors` is `true` and `path` is specified, include notifications for any parent paths. Ignored if `path` is not specified.
  *   group_id - string
  */
  public static ListIterator<Notification> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Notification> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Notification> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("include_ancestors") && !(parameters.get("include_ancestors") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_ancestors must be of type Boolean parameters[\"include_ancestors\"]");
    }
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type String parameters[\"group_id\"]");
    }


    String url = String.format("%s%s/notifications", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Notification>> typeReference = new TypeReference<List<Notification>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Notification> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Notification> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Notification ID.
  */
  public static Notification find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Notification find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Notification find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Notification find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/notifications/%s", urlParts);

    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Notification get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Notification get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - The id of the user to notify. Provide `user_id`, `username` or `group_id`.
  *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
  *   notify_on_delete - boolean - Trigger on files deleted in this path?
  *   notify_on_download - boolean - Trigger on files downloaded in this path?
  *   notify_on_move - boolean - Trigger on files moved to this path?
  *   notify_on_upload - boolean - Trigger on files created/uploaded/updated/changed in this path?
  *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
  *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
  *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
  *   message - string - Custom message to include in notification emails
  *   triggering_filenames - array(string) - Array of filenames (possibly with wildcards) to scope trigger
  *   triggering_group_ids - array(int64) - If set, will only notify on actions made by a member of one of the specified groups
  *   triggering_user_ids - array(int64) - If set, will onlynotify on actions made one of the specified users
  *   trigger_by_share_recipients - boolean - Notify when actions are performed by a share recipient?
  *   group_id - int64 - The ID of the group to notify.  Provide `user_id`, `username` or `group_id`.
  *   path - string - Path
  *   username - string - The username of the user to notify.  Provide `user_id`, `username` or `group_id`.
  */
  public static Notification create() throws RuntimeException {
    return create(null, null);
  }

  public static Notification create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Notification create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }
    if (parameters.containsKey("notify_on_copy") && !(parameters.get("notify_on_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_copy must be of type Boolean parameters[\"notify_on_copy\"]");
    }
    if (parameters.containsKey("notify_on_delete") && !(parameters.get("notify_on_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_delete must be of type Boolean parameters[\"notify_on_delete\"]");
    }
    if (parameters.containsKey("notify_on_download") && !(parameters.get("notify_on_download") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_download must be of type Boolean parameters[\"notify_on_download\"]");
    }
    if (parameters.containsKey("notify_on_move") && !(parameters.get("notify_on_move") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_move must be of type Boolean parameters[\"notify_on_move\"]");
    }
    if (parameters.containsKey("notify_on_upload") && !(parameters.get("notify_on_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_upload must be of type Boolean parameters[\"notify_on_upload\"]");
    }
    if (parameters.containsKey("notify_user_actions") && !(parameters.get("notify_user_actions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_user_actions must be of type Boolean parameters[\"notify_user_actions\"]");
    }
    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }
    if (parameters.containsKey("send_interval") && !(parameters.get("send_interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: send_interval must be of type String parameters[\"send_interval\"]");
    }
    if (parameters.containsKey("message") && !(parameters.get("message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: message must be of type String parameters[\"message\"]");
    }
    if (parameters.containsKey("triggering_filenames") && !(parameters.get("triggering_filenames") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_filenames must be of type String[] parameters[\"triggering_filenames\"]");
    }
    if (parameters.containsKey("triggering_group_ids") && !(parameters.get("triggering_group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_group_ids must be of type Long[] parameters[\"triggering_group_ids\"]");
    }
    if (parameters.containsKey("triggering_user_ids") && !(parameters.get("triggering_user_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_user_ids must be of type Long[] parameters[\"triggering_user_ids\"]");
    }
    if (parameters.containsKey("trigger_by_share_recipients") && !(parameters.get("trigger_by_share_recipients") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: trigger_by_share_recipients must be of type Boolean parameters[\"trigger_by_share_recipients\"]");
    }
    if (parameters.containsKey("group_id") && !(parameters.get("group_id") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: group_id must be of type Long parameters[\"group_id\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }


    String url = String.format("%s%s/notifications", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   notify_on_copy - boolean - If `true`, copying or moving resources into this path will trigger a notification, in addition to just uploads.
  *   notify_on_delete - boolean - Trigger on files deleted in this path?
  *   notify_on_download - boolean - Trigger on files downloaded in this path?
  *   notify_on_move - boolean - Trigger on files moved to this path?
  *   notify_on_upload - boolean - Trigger on files created/uploaded/updated/changed in this path?
  *   notify_user_actions - boolean - If `true` actions initiated by the user will still result in a notification
  *   recursive - boolean - If `true`, enable notifications for each subfolder in this path
  *   send_interval - string - The time interval that notifications are aggregated by.  Can be `five_minutes`, `fifteen_minutes`, `hourly`, or `daily`.
  *   message - string - Custom message to include in notification emails
  *   triggering_filenames - array(string) - Array of filenames (possibly with wildcards) to scope trigger
  *   triggering_group_ids - array(int64) - If set, will only notify on actions made by a member of one of the specified groups
  *   triggering_user_ids - array(int64) - If set, will onlynotify on actions made one of the specified users
  *   trigger_by_share_recipients - boolean - Notify when actions are performed by a share recipient?
  */
  public static Notification update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Notification update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Notification update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Notification update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("notify_on_copy") && !(parameters.get("notify_on_copy") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_copy must be of type Boolean parameters[\"notify_on_copy\"]");
    }
    if (parameters.containsKey("notify_on_delete") && !(parameters.get("notify_on_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_delete must be of type Boolean parameters[\"notify_on_delete\"]");
    }
    if (parameters.containsKey("notify_on_download") && !(parameters.get("notify_on_download") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_download must be of type Boolean parameters[\"notify_on_download\"]");
    }
    if (parameters.containsKey("notify_on_move") && !(parameters.get("notify_on_move") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_move must be of type Boolean parameters[\"notify_on_move\"]");
    }
    if (parameters.containsKey("notify_on_upload") && !(parameters.get("notify_on_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_on_upload must be of type Boolean parameters[\"notify_on_upload\"]");
    }
    if (parameters.containsKey("notify_user_actions") && !(parameters.get("notify_user_actions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: notify_user_actions must be of type Boolean parameters[\"notify_user_actions\"]");
    }
    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }
    if (parameters.containsKey("send_interval") && !(parameters.get("send_interval") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: send_interval must be of type String parameters[\"send_interval\"]");
    }
    if (parameters.containsKey("message") && !(parameters.get("message") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: message must be of type String parameters[\"message\"]");
    }
    if (parameters.containsKey("triggering_filenames") && !(parameters.get("triggering_filenames") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_filenames must be of type String[] parameters[\"triggering_filenames\"]");
    }
    if (parameters.containsKey("triggering_group_ids") && !(parameters.get("triggering_group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_group_ids must be of type Long[] parameters[\"triggering_group_ids\"]");
    }
    if (parameters.containsKey("triggering_user_ids") && !(parameters.get("triggering_user_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: triggering_user_ids must be of type Long[] parameters[\"triggering_user_ids\"]");
    }
    if (parameters.containsKey("trigger_by_share_recipients") && !(parameters.get("trigger_by_share_recipients") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: trigger_by_share_recipients must be of type Boolean parameters[\"trigger_by_share_recipients\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/notifications/%s", urlParts);

    TypeReference<Notification> typeReference = new TypeReference<Notification>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
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

    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/notifications/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
