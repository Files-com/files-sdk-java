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
public class Group implements ModelInterface {
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


  public Group() {
    this(null, null);
  }

  public Group(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Group(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Group ID
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
  * Group name
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * A list of allowed IPs if applicable.  Newline delimited
  */
  @JsonProperty("allowed_ips")
  public String allowedIps;

  public String getAllowedIps() {
    return allowedIps;
  }

  public void setAllowedIps(String allowedIps) {
    this.allowedIps = allowedIps;
  }

  /**
  * Comma-delimited list of user IDs who are group administrators (separated by commas)
  */
  @JsonProperty("admin_ids")
  public String adminIds;

  public String getAdminIds() {
    return adminIds;
  }

  public void setAdminIds(String adminIds) {
    this.adminIds = adminIds;
  }

  /**
  * Notes about this group
  */
  @JsonProperty("notes")
  public String notes;

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
  * Comma-delimited list of user IDs who belong to this group (separated by commas)
  */
  @JsonProperty("user_ids")
  public String userIds;

  public String getUserIds() {
    return userIds;
  }

  public void setUserIds(String userIds) {
    this.userIds = userIds;
  }

  /**
  * Comma-delimited list of usernames who belong to this group (separated by commas)
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
  * If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
  */
  @JsonProperty("ftp_permission")
  public Boolean ftpPermission;

  public Boolean getFtpPermission() {
    return ftpPermission;
  }

  public void setFtpPermission(Boolean ftpPermission) {
    this.ftpPermission = ftpPermission;
  }

  /**
  * If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
  */
  @JsonProperty("sftp_permission")
  public Boolean sftpPermission;

  public Boolean getSftpPermission() {
    return sftpPermission;
  }

  public void setSftpPermission(Boolean sftpPermission) {
    this.sftpPermission = sftpPermission;
  }

  /**
  * If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
  */
  @JsonProperty("dav_permission")
  public Boolean davPermission;

  public Boolean getDavPermission() {
    return davPermission;
  }

  public void setDavPermission(Boolean davPermission) {
    this.davPermission = davPermission;
  }

  /**
  * If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
  */
  @JsonProperty("restapi_permission")
  public Boolean restapiPermission;

  public Boolean getRestapiPermission() {
    return restapiPermission;
  }

  public void setRestapiPermission(Boolean restapiPermission) {
    this.restapiPermission = restapiPermission;
  }

  /**
  * Site ID
  */
  @JsonProperty("site_id")
  public Long siteId;

  public Long getSiteId() {
    return siteId;
  }

  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
  * Parameters:
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  *   ftp_permission - boolean - If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
  *   sftp_permission - boolean - If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
  *   dav_permission - boolean - If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
  *   restapi_permission - boolean - If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   name - string - Group name.
  */
  public Group update(HashMap<String, Object> parameters) throws IOException {
    return Group.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Group.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Group.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id` and `name`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `name`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
  *   ids - string - Comma-separated list of group ids to include in results.
  *   include_parent_site_groups - boolean - Include groups from the parent site.
  */
  public static ListIterator<Group> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Group> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Group> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
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
    if (parameters.containsKey("ids") && !(parameters.get("ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ids must be of type String parameters[\"ids\"]");
    }
    if (parameters.containsKey("include_parent_site_groups") && !(parameters.get("include_parent_site_groups") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_parent_site_groups must be of type Boolean parameters[\"include_parent_site_groups\"]");
    }


    String url = String.format("%s%s/groups", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Group>> typeReference = new TypeReference<List<Group>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Group> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Group> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Group ID.
  */
  public static Group find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Group find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Group find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Group find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/groups/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Group get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Group get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  *   ftp_permission - boolean - If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
  *   sftp_permission - boolean - If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
  *   dav_permission - boolean - If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
  *   restapi_permission - boolean - If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   name (required) - string - Group name.
  */
  public static Group create() throws RuntimeException {
    return create(null, null);
  }

  public static Group create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Group create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }
    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }
    if (parameters.containsKey("sftp_permission") && !(parameters.get("sftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_permission must be of type Boolean parameters[\"sftp_permission\"]");
    }
    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }
    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
    }
    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }


    String url = String.format("%s%s/groups", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Group> typeReference = new TypeReference<Group>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   notes - string - Group notes.
  *   user_ids - string - A list of user ids. If sent as a string, should be comma-delimited.
  *   admin_ids - string - A list of group admin user ids. If sent as a string, should be comma-delimited.
  *   ftp_permission - boolean - If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
  *   sftp_permission - boolean - If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
  *   dav_permission - boolean - If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
  *   restapi_permission - boolean - If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
  *   allowed_ips - string - A list of allowed IPs if applicable.  Newline delimited
  *   name - string - Group name.
  */
  public static Group update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Group update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Group update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Group update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("admin_ids") && !(parameters.get("admin_ids") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: admin_ids must be of type String parameters[\"admin_ids\"]");
    }
    if (parameters.containsKey("ftp_permission") && !(parameters.get("ftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ftp_permission must be of type Boolean parameters[\"ftp_permission\"]");
    }
    if (parameters.containsKey("sftp_permission") && !(parameters.get("sftp_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: sftp_permission must be of type Boolean parameters[\"sftp_permission\"]");
    }
    if (parameters.containsKey("dav_permission") && !(parameters.get("dav_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dav_permission must be of type Boolean parameters[\"dav_permission\"]");
    }
    if (parameters.containsKey("restapi_permission") && !(parameters.get("restapi_permission") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restapi_permission must be of type Boolean parameters[\"restapi_permission\"]");
    }
    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }



    String url = String.format("%s%s/groups/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Group> typeReference = new TypeReference<Group>() {};
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

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/groups/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
