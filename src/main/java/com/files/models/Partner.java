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
public class Partner implements ModelInterface {
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


  public Partner() {
    this(null, null);
  }

  public Partner(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Partner(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
  */
  @JsonProperty("allow_bypassing_2fa_policies")
  public Boolean allowBypassing2faPolicies;

  public Boolean getAllowBypassing2faPolicies() {
    return allowBypassing2faPolicies;
  }

  public void setAllowBypassing2faPolicies(Boolean allowBypassing2faPolicies) {
    this.allowBypassing2faPolicies = allowBypassing2faPolicies;
  }

  /**
  * Allow Partner Admins to change or reset credentials for users belonging to this Partner.
  */
  @JsonProperty("allow_credential_changes")
  public Boolean allowCredentialChanges;

  public Boolean getAllowCredentialChanges() {
    return allowCredentialChanges;
  }

  public void setAllowCredentialChanges(Boolean allowCredentialChanges) {
    this.allowCredentialChanges = allowCredentialChanges;
  }

  /**
  * Allow Partner Admins to provide GPG keys.
  */
  @JsonProperty("allow_providing_gpg_keys")
  public Boolean allowProvidingGpgKeys;

  public Boolean getAllowProvidingGpgKeys() {
    return allowProvidingGpgKeys;
  }

  public void setAllowProvidingGpgKeys(Boolean allowProvidingGpgKeys) {
    this.allowProvidingGpgKeys = allowProvidingGpgKeys;
  }

  /**
  * Allow Partner Admins to create users.
  */
  @JsonProperty("allow_user_creation")
  public Boolean allowUserCreation;

  public Boolean getAllowUserCreation() {
    return allowUserCreation;
  }

  public void setAllowUserCreation(Boolean allowUserCreation) {
    this.allowUserCreation = allowUserCreation;
  }

  /**
  * The unique ID of the Partner.
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
  * ID of the Workspace associated with this Partner.
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
  * The name of the Partner.
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
  * Notes about this Partner.
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
  * Array of User IDs that are Partner Admins for this Partner.
  */
  @JsonProperty("partner_admin_ids")
  public Long[] partnerAdminIds;

  public Long[] getPartnerAdminIds() {
    return partnerAdminIds;
  }

  public void setPartnerAdminIds(Long[] partnerAdminIds) {
    this.partnerAdminIds = partnerAdminIds;
  }

  /**
  * The root folder path for this Partner.
  */
  @JsonProperty("root_folder")
  public String rootFolder;

  public String getRootFolder() {
    return rootFolder;
  }

  public void setRootFolder(String rootFolder) {
    this.rootFolder = rootFolder;
  }

  /**
  * Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  */
  @JsonProperty("tags")
  public String tags;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  /**
  * Array of User IDs that belong to this Partner.
  */
  @JsonProperty("user_ids")
  public Long[] userIds;

  public Long[] getUserIds() {
    return userIds;
  }

  public void setUserIds(Long[] userIds) {
    this.userIds = userIds;
  }

  /**
  * Parameters:
  *   allow_bypassing_2fa_policies - boolean - Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
  *   allow_credential_changes - boolean - Allow Partner Admins to change or reset credentials for users belonging to this Partner.
  *   allow_providing_gpg_keys - boolean - Allow Partner Admins to provide GPG keys.
  *   allow_user_creation - boolean - Allow Partner Admins to create users.
  *   notes - string - Notes about this Partner.
  *   root_folder - string - The root folder path for this Partner.
  *   tags - string - Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  *   name - string - The name of the Partner.
  */
  public Partner update(HashMap<String, Object> parameters) throws IOException {
    return Partner.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Partner.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Partner.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
  */
  public static ListIterator<Partner> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/partners", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Partner>> typeReference = new TypeReference<List<Partner>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Partner> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Partner ID.
  */
  public static Partner find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Partner find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Partner find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Partner find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Partner> typeReference = new TypeReference<Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Partner get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Partner get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   allow_bypassing_2fa_policies - boolean - Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
  *   allow_credential_changes - boolean - Allow Partner Admins to change or reset credentials for users belonging to this Partner.
  *   allow_providing_gpg_keys - boolean - Allow Partner Admins to provide GPG keys.
  *   allow_user_creation - boolean - Allow Partner Admins to create users.
  *   notes - string - Notes about this Partner.
  *   root_folder - string - The root folder path for this Partner.
  *   tags - string - Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  *   name (required) - string - The name of the Partner.
  *   workspace_id - int64 - ID of the Workspace associated with this Partner.
  */
  public static Partner create() throws RuntimeException {
    return create(null, null);
  }

  public static Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("allow_bypassing_2fa_policies") && !(parameters.get("allow_bypassing_2fa_policies") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_bypassing_2fa_policies must be of type Boolean parameters[\"allow_bypassing_2fa_policies\"]");
    }
    if (parameters.containsKey("allow_credential_changes") && !(parameters.get("allow_credential_changes") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_credential_changes must be of type Boolean parameters[\"allow_credential_changes\"]");
    }
    if (parameters.containsKey("allow_providing_gpg_keys") && !(parameters.get("allow_providing_gpg_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_providing_gpg_keys must be of type Boolean parameters[\"allow_providing_gpg_keys\"]");
    }
    if (parameters.containsKey("allow_user_creation") && !(parameters.get("allow_user_creation") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_creation must be of type Boolean parameters[\"allow_user_creation\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("root_folder") && !(parameters.get("root_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: root_folder must be of type String parameters[\"root_folder\"]");
    }
    if (parameters.containsKey("tags") && !(parameters.get("tags") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: tags must be of type String parameters[\"tags\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }


    String url = String.format("%s%s/partners", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Partner> typeReference = new TypeReference<Partner>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   allow_bypassing_2fa_policies - boolean - Allow Partner Admins to change Two-Factor Authentication requirements for Partner Users.
  *   allow_credential_changes - boolean - Allow Partner Admins to change or reset credentials for users belonging to this Partner.
  *   allow_providing_gpg_keys - boolean - Allow Partner Admins to provide GPG keys.
  *   allow_user_creation - boolean - Allow Partner Admins to create users.
  *   notes - string - Notes about this Partner.
  *   root_folder - string - The root folder path for this Partner.
  *   tags - string - Comma-separated list of Tags for this Partner. Tags are used for other features, such as UserLifecycleRules, which can target specific tags.  Tags must only contain lowercase letters, numbers, and hyphens.
  *   name - string - The name of the Partner.
  */
  public static Partner update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Partner update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Partner update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("allow_bypassing_2fa_policies") && !(parameters.get("allow_bypassing_2fa_policies") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_bypassing_2fa_policies must be of type Boolean parameters[\"allow_bypassing_2fa_policies\"]");
    }
    if (parameters.containsKey("allow_credential_changes") && !(parameters.get("allow_credential_changes") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_credential_changes must be of type Boolean parameters[\"allow_credential_changes\"]");
    }
    if (parameters.containsKey("allow_providing_gpg_keys") && !(parameters.get("allow_providing_gpg_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_providing_gpg_keys must be of type Boolean parameters[\"allow_providing_gpg_keys\"]");
    }
    if (parameters.containsKey("allow_user_creation") && !(parameters.get("allow_user_creation") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: allow_user_creation must be of type Boolean parameters[\"allow_user_creation\"]");
    }
    if (parameters.containsKey("notes") && !(parameters.get("notes") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: notes must be of type String parameters[\"notes\"]");
    }
    if (parameters.containsKey("root_folder") && !(parameters.get("root_folder") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: root_folder must be of type String parameters[\"root_folder\"]");
    }
    if (parameters.containsKey("tags") && !(parameters.get("tags") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: tags must be of type String parameters[\"tags\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }



    String url = String.format("%s%s/partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Partner> typeReference = new TypeReference<Partner>() {};
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



    String url = String.format("%s%s/partners/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
