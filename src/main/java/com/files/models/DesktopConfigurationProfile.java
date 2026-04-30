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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DesktopConfigurationProfile implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public DesktopConfigurationProfile() {
    this(null, null);
  }

  public DesktopConfigurationProfile(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public DesktopConfigurationProfile(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Desktop Configuration Profile ID
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
  * Profile name
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
  * Workspace ID
  */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
  * Whether this profile applies to all users in the Workspace by default
  */
  @JsonProperty("use_for_all_users")
  public Boolean useForAllUsers;

  public Boolean getUseForAllUsers() {
    return useForAllUsers;
  }

  public void setUseForAllUsers(Boolean useForAllUsers) {
    this.useForAllUsers = useForAllUsers;
  }

  /**
  * Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile
  */
  @JsonProperty("disable_drive_mounting")
  public Boolean disableDriveMounting;

  public Boolean getDisableDriveMounting() {
    return disableDriveMounting;
  }

  public void setDisableDriveMounting(Boolean disableDriveMounting) {
    this.disableDriveMounting = disableDriveMounting;
  }

  /**
  * Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.
  */
  @JsonProperty("mount_mappings")
  public Object mountMappings;

  public Object getMountMappings() {
    return mountMappings;
  }

  public void setMountMappings(Object mountMappings) {
    this.mountMappings = mountMappings;
  }

  /**
  * Parameters:
  *   name - string - Profile name
  *   workspace_id - int64 - Workspace ID
  *   mount_mappings - object - Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.
  *   use_for_all_users - boolean - Whether this profile applies to all users in the Workspace by default
  *   disable_drive_mounting - boolean - Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile
  */
  public DesktopConfigurationProfile update(HashMap<String, Object> parameters) throws IOException {
    return DesktopConfigurationProfile.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    DesktopConfigurationProfile.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    DesktopConfigurationProfile.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
  */
  public static ListIterator<DesktopConfigurationProfile> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<DesktopConfigurationProfile> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<DesktopConfigurationProfile> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



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


    String url = String.format("%s%s/desktop_configuration_profiles", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<DesktopConfigurationProfile>> typeReference = new TypeReference<List<DesktopConfigurationProfile>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<DesktopConfigurationProfile> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<DesktopConfigurationProfile> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Desktop Configuration Profile ID.
  */
  public static DesktopConfigurationProfile find() throws RuntimeException {
    return find(null, null, null);
  }

  public static DesktopConfigurationProfile find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static DesktopConfigurationProfile find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static DesktopConfigurationProfile find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/desktop_configuration_profiles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<DesktopConfigurationProfile> typeReference = new TypeReference<DesktopConfigurationProfile>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static DesktopConfigurationProfile get() throws RuntimeException {
    return get(null, null, null);
  }

  public static DesktopConfigurationProfile get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name (required) - string - Profile name
  *   mount_mappings (required) - object - Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.
  *   workspace_id - int64 - Workspace ID
  *   use_for_all_users - boolean - Whether this profile applies to all users in the Workspace by default
  *   disable_drive_mounting - boolean - Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile
  */
  public static DesktopConfigurationProfile create() throws RuntimeException {
    return create(null, null);
  }

  public static DesktopConfigurationProfile create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static DesktopConfigurationProfile create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }
    if (!parameters.containsKey("mount_mappings") || parameters.get("mount_mappings") == null) {
      throw new NullPointerException("Parameter missing: mount_mappings parameters[\"mount_mappings\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("mount_mappings") && !(parameters.get("mount_mappings") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: mount_mappings must be of type Object parameters[\"mount_mappings\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("use_for_all_users") && !(parameters.get("use_for_all_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_for_all_users must be of type Boolean parameters[\"use_for_all_users\"]");
    }
    if (parameters.containsKey("disable_drive_mounting") && !(parameters.get("disable_drive_mounting") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_drive_mounting must be of type Boolean parameters[\"disable_drive_mounting\"]");
    }


    String url = String.format("%s%s/desktop_configuration_profiles", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<DesktopConfigurationProfile> typeReference = new TypeReference<DesktopConfigurationProfile>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Profile name
  *   workspace_id - int64 - Workspace ID
  *   mount_mappings - object - Mount point mappings for the desktop app. Keys must be a single uppercase Windows drive letter other than A, B, or C, and values are Files.com paths to mount there.
  *   use_for_all_users - boolean - Whether this profile applies to all users in the Workspace by default
  *   disable_drive_mounting - boolean - Whether the desktop app should hide drive mounting, prevent new drive mounts, and unmount active drive mounts for users with this profile
  */
  public static DesktopConfigurationProfile update() throws RuntimeException {
    return update(null, null, null);
  }

  public static DesktopConfigurationProfile update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static DesktopConfigurationProfile update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static DesktopConfigurationProfile update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }
    if (parameters.containsKey("mount_mappings") && !(parameters.get("mount_mappings") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: mount_mappings must be of type Object parameters[\"mount_mappings\"]");
    }
    if (parameters.containsKey("use_for_all_users") && !(parameters.get("use_for_all_users") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_for_all_users must be of type Boolean parameters[\"use_for_all_users\"]");
    }
    if (parameters.containsKey("disable_drive_mounting") && !(parameters.get("disable_drive_mounting") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: disable_drive_mounting must be of type Boolean parameters[\"disable_drive_mounting\"]");
    }



    String url = String.format("%s%s/desktop_configuration_profiles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<DesktopConfigurationProfile> typeReference = new TypeReference<DesktopConfigurationProfile>() {};
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



    String url = String.format("%s%s/desktop_configuration_profiles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
