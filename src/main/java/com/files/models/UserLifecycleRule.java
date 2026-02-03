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
public class UserLifecycleRule implements ModelInterface {
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


  public UserLifecycleRule() {
    this(null, null);
  }

  public UserLifecycleRule(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public UserLifecycleRule(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * User Lifecycle Rule ID
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
  * User authentication method for which the rule will apply.
  */
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  public String getAuthenticationMethod() {
    return authenticationMethod;
  }

  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  /**
  * Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
  */
  @JsonProperty("group_ids")
  public Long[] groupIds;

  public Long[] getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(Long[] groupIds) {
    this.groupIds = groupIds;
  }

  /**
  * Action to take on inactive users (disable or delete)
  */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  /**
  * Number of days of inactivity before the rule applies
  */
  @JsonProperty("inactivity_days")
  public Long inactivityDays;

  public Long getInactivityDays() {
    return inactivityDays;
  }

  public void setInactivityDays(Long inactivityDays) {
    this.inactivityDays = inactivityDays;
  }

  /**
  * If true, the rule will apply to folder admins.
  */
  @JsonProperty("include_folder_admins")
  public Boolean includeFolderAdmins;

  public Boolean getIncludeFolderAdmins() {
    return includeFolderAdmins;
  }

  public void setIncludeFolderAdmins(Boolean includeFolderAdmins) {
    this.includeFolderAdmins = includeFolderAdmins;
  }

  /**
  * If true, the rule will apply to site admins.
  */
  @JsonProperty("include_site_admins")
  public Boolean includeSiteAdmins;

  public Boolean getIncludeSiteAdmins() {
    return includeSiteAdmins;
  }

  public void setIncludeSiteAdmins(Boolean includeSiteAdmins) {
    this.includeSiteAdmins = includeSiteAdmins;
  }

  /**
  * User Lifecycle Rule name
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
  * If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  */
  @JsonProperty("partner_tag")
  public String partnerTag;

  public String getPartnerTag() {
    return partnerTag;
  }

  public void setPartnerTag(String partnerTag) {
    this.partnerTag = partnerTag;
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
  * State of the users to apply the rule to (inactive or disabled)
  */
  @JsonProperty("user_state")
  public String userState;

  public String getUserState() {
    return userState;
  }

  public void setUserState(String userState) {
    this.userState = userState;
  }

  /**
  * If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  */
  @JsonProperty("user_tag")
  public String userTag;

  public String getUserTag() {
    return userTag;
  }

  public void setUserTag(String userTag) {
    this.userTag = userTag;
  }

  /**
  * Parameters:
  *   action - string - Action to take on inactive users (disable or delete)
  *   authentication_method - string - User authentication method for which the rule will apply.
  *   group_ids - array(int64) - Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
  *   inactivity_days - int64 - Number of days of inactivity before the rule applies
  *   include_site_admins - boolean - If true, the rule will apply to site admins.
  *   include_folder_admins - boolean - If true, the rule will apply to folder admins.
  *   name - string - User Lifecycle Rule name
  *   partner_tag - string - If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  *   user_state - string - State of the users to apply the rule to (inactive or disabled)
  *   user_tag - string - If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  */
  public UserLifecycleRule update(HashMap<String, Object> parameters) throws IOException {
    return UserLifecycleRule.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    UserLifecycleRule.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    UserLifecycleRule.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`.
  */
  public static ListIterator<UserLifecycleRule> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<UserLifecycleRule> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<UserLifecycleRule> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String url = String.format("%s%s/user_lifecycle_rules", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<UserLifecycleRule>> typeReference = new TypeReference<List<UserLifecycleRule>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<UserLifecycleRule> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<UserLifecycleRule> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - User Lifecycle Rule ID.
  */
  public static UserLifecycleRule find() throws RuntimeException {
    return find(null, null, null);
  }

  public static UserLifecycleRule find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static UserLifecycleRule find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static UserLifecycleRule find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/user_lifecycle_rules/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<UserLifecycleRule> typeReference = new TypeReference<UserLifecycleRule>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static UserLifecycleRule get() throws RuntimeException {
    return get(null, null, null);
  }

  public static UserLifecycleRule get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   action - string - Action to take on inactive users (disable or delete)
  *   authentication_method - string - User authentication method for which the rule will apply.
  *   group_ids - array(int64) - Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
  *   inactivity_days - int64 - Number of days of inactivity before the rule applies
  *   include_site_admins - boolean - If true, the rule will apply to site admins.
  *   include_folder_admins - boolean - If true, the rule will apply to folder admins.
  *   name - string - User Lifecycle Rule name
  *   partner_tag - string - If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  *   user_state - string - State of the users to apply the rule to (inactive or disabled)
  *   user_tag - string - If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  */
  public static UserLifecycleRule create() throws RuntimeException {
    return create(null, null);
  }

  public static UserLifecycleRule create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static UserLifecycleRule create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("authentication_method") && !(parameters.get("authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authentication_method must be of type String parameters[\"authentication_method\"]");
    }
    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type Long[] parameters[\"group_ids\"]");
    }
    if (parameters.containsKey("inactivity_days") && !(parameters.get("inactivity_days") instanceof Long || parameters.get("inactivity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_days must be of type Long or Integer parameters[\"inactivity_days\"]");
    }
    if (parameters.containsKey("include_site_admins") && !(parameters.get("include_site_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_site_admins must be of type Boolean parameters[\"include_site_admins\"]");
    }
    if (parameters.containsKey("include_folder_admins") && !(parameters.get("include_folder_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_folder_admins must be of type Boolean parameters[\"include_folder_admins\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("partner_tag") && !(parameters.get("partner_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partner_tag must be of type String parameters[\"partner_tag\"]");
    }
    if (parameters.containsKey("user_state") && !(parameters.get("user_state") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_state must be of type String parameters[\"user_state\"]");
    }
    if (parameters.containsKey("user_tag") && !(parameters.get("user_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_tag must be of type String parameters[\"user_tag\"]");
    }


    String url = String.format("%s%s/user_lifecycle_rules", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<UserLifecycleRule> typeReference = new TypeReference<UserLifecycleRule>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   action - string - Action to take on inactive users (disable or delete)
  *   authentication_method - string - User authentication method for which the rule will apply.
  *   group_ids - array(int64) - Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
  *   inactivity_days - int64 - Number of days of inactivity before the rule applies
  *   include_site_admins - boolean - If true, the rule will apply to site admins.
  *   include_folder_admins - boolean - If true, the rule will apply to folder admins.
  *   name - string - User Lifecycle Rule name
  *   partner_tag - string - If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  *   user_state - string - State of the users to apply the rule to (inactive or disabled)
  *   user_tag - string - If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
  */
  public static UserLifecycleRule update() throws RuntimeException {
    return update(null, null, null);
  }

  public static UserLifecycleRule update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static UserLifecycleRule update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static UserLifecycleRule update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("authentication_method") && !(parameters.get("authentication_method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: authentication_method must be of type String parameters[\"authentication_method\"]");
    }
    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type Long[] parameters[\"group_ids\"]");
    }
    if (parameters.containsKey("inactivity_days") && !(parameters.get("inactivity_days") instanceof Long || parameters.get("inactivity_days") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inactivity_days must be of type Long or Integer parameters[\"inactivity_days\"]");
    }
    if (parameters.containsKey("include_site_admins") && !(parameters.get("include_site_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_site_admins must be of type Boolean parameters[\"include_site_admins\"]");
    }
    if (parameters.containsKey("include_folder_admins") && !(parameters.get("include_folder_admins") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: include_folder_admins must be of type Boolean parameters[\"include_folder_admins\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("partner_tag") && !(parameters.get("partner_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partner_tag must be of type String parameters[\"partner_tag\"]");
    }
    if (parameters.containsKey("user_state") && !(parameters.get("user_state") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_state must be of type String parameters[\"user_state\"]");
    }
    if (parameters.containsKey("user_tag") && !(parameters.get("user_tag") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: user_tag must be of type String parameters[\"user_tag\"]");
    }



    String url = String.format("%s%s/user_lifecycle_rules/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<UserLifecycleRule> typeReference = new TypeReference<UserLifecycleRule>() {};
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



    String url = String.format("%s%s/user_lifecycle_rules/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
