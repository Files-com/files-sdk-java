package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLifecycleRule implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every UserLifecycleRule, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code UserLifecycleRule} with no attributes set.
   */
  public UserLifecycleRule() {
    this(null, null);
  }

  /**
   * Creates a {@code UserLifecycleRule} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public UserLifecycleRule(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code UserLifecycleRule} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public UserLifecycleRule(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * User Lifecycle Rule ID
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.
   */
  @JsonProperty("authentication_method")
  public String authenticationMethod;

  /**
   * Gets {@link #authenticationMethod}.
   *
   * @return the value of {@link #authenticationMethod}
   */
  public String getAuthenticationMethod() {
    return authenticationMethod;
  }

  /**
   * Sets {@link #authenticationMethod}.
   *
   * @param authenticationMethod the new value
   */
  public void setAuthenticationMethod(String authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  /**
   * Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.
   */
  @JsonProperty("group_ids")
  public Long[] groupIds;

  /**
   * Gets {@link #groupIds}.
   *
   * @return the value of {@link #groupIds}
   */
  public Long[] getGroupIds() {
    return groupIds;
  }

  /**
   * Sets {@link #groupIds}.
   *
   * @param groupIds the new value
   */
  public void setGroupIds(Long[] groupIds) {
    this.groupIds = groupIds;
  }

  /**
   * Action to take on inactive users (disable or delete)
   */
  @JsonProperty("action")
  public String action;

  /**
   * Gets {@link #action}.
   *
   * @return the value of {@link #action}
   */
  public String getAction() {
    return action;
  }

  /**
   * Sets {@link #action}.
   *
   * @param action the new value
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Number of days of inactivity before the rule applies
   */
  @JsonProperty("inactivity_days")
  public Long inactivityDays;

  /**
   * Gets {@link #inactivityDays}.
   *
   * @return the value of {@link #inactivityDays}
   */
  public Long getInactivityDays() {
    return inactivityDays;
  }

  /**
   * Sets {@link #inactivityDays}.
   *
   * @param inactivityDays the new value
   */
  public void setInactivityDays(Long inactivityDays) {
    this.inactivityDays = inactivityDays;
  }

  /**
   * If true, the rule will apply to folder admins.
   */
  @JsonProperty("include_folder_admins")
  public Boolean includeFolderAdmins;

  /**
   * Gets {@link #includeFolderAdmins}.
   *
   * @return the value of {@link #includeFolderAdmins}
   */
  public Boolean getIncludeFolderAdmins() {
    return includeFolderAdmins;
  }

  /**
   * Sets {@link #includeFolderAdmins}.
   *
   * @param includeFolderAdmins the new value
   */
  public void setIncludeFolderAdmins(Boolean includeFolderAdmins) {
    this.includeFolderAdmins = includeFolderAdmins;
  }

  /**
   * If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.
   */
  @JsonProperty("include_site_admins")
  public Boolean includeSiteAdmins;

  /**
   * Gets {@link #includeSiteAdmins}.
   *
   * @return the value of {@link #includeSiteAdmins}
   */
  public Boolean getIncludeSiteAdmins() {
    return includeSiteAdmins;
  }

  /**
   * Sets {@link #includeSiteAdmins}.
   *
   * @param includeSiteAdmins the new value
   */
  public void setIncludeSiteAdmins(Boolean includeSiteAdmins) {
    this.includeSiteAdmins = includeSiteAdmins;
  }

  /**
   * If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.
   */
  @JsonProperty("apply_to_all_workspaces")
  public Boolean applyToAllWorkspaces;

  /**
   * Gets {@link #applyToAllWorkspaces}.
   *
   * @return the value of {@link #applyToAllWorkspaces}
   */
  public Boolean getApplyToAllWorkspaces() {
    return applyToAllWorkspaces;
  }

  /**
   * Sets {@link #applyToAllWorkspaces}.
   *
   * @param applyToAllWorkspaces the new value
   */
  public void setApplyToAllWorkspaces(Boolean applyToAllWorkspaces) {
    this.applyToAllWorkspaces = applyToAllWorkspaces;
  }

  /**
   * User Lifecycle Rule name
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * If true, users will be emailed before the rule disables or deletes them.
   */
  @JsonProperty("notify_users")
  public Boolean notifyUsers;

  /**
   * Gets {@link #notifyUsers}.
   *
   * @return the value of {@link #notifyUsers}
   */
  public Boolean getNotifyUsers() {
    return notifyUsers;
  }

  /**
   * Sets {@link #notifyUsers}.
   *
   * @param notifyUsers the new value
   */
  public void setNotifyUsers(Boolean notifyUsers) {
    this.notifyUsers = notifyUsers;
  }

  /**
   * If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
   */
  @JsonProperty("partner_tag")
  public String partnerTag;

  /**
   * Gets {@link #partnerTag}.
   *
   * @return the value of {@link #partnerTag}
   */
  public String getPartnerTag() {
    return partnerTag;
  }

  /**
   * Sets {@link #partnerTag}.
   *
   * @param partnerTag the new value
   */
  public void setPartnerTag(String partnerTag) {
    this.partnerTag = partnerTag;
  }

  /**
   * Site ID
   */
  @JsonProperty("site_id")
  public Long siteId;

  /**
   * Gets {@link #siteId}.
   *
   * @return the value of {@link #siteId}
   */
  public Long getSiteId() {
    return siteId;
  }

  /**
   * Sets {@link #siteId}.
   *
   * @param siteId the new value
   */
  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  /**
   * Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * State of the users to apply the rule to (inactive or disabled)
   */
  @JsonProperty("user_state")
  public String userState;

  /**
   * Gets {@link #userState}.
   *
   * @return the value of {@link #userState}
   */
  public String getUserState() {
    return userState;
  }

  /**
   * Sets {@link #userState}.
   *
   * @param userState the new value
   */
  public void setUserState(String userState) {
    this.userState = userState;
  }

  /**
   * If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.
   */
  @JsonProperty("user_tag")
  public String userTag;

  /**
   * Gets {@link #userTag}.
   *
   * @return the value of {@link #userTag}
   */
  public String getUserTag() {
    return userTag;
  }

  /**
   * Sets {@link #userTag}.
   *
   * @param userTag the new value
   */
  public void setUserTag(String userTag) {
    this.userTag = userTag;
  }

  /**
   * Updates this UserLifecycleRule. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update User Lifecycle Rule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Action to take on inactive users (disable or delete)</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code authentication_method} (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.</li>
   * <li>{@code group_ids} (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.</li>
   * <li>{@code inactivity_days} (Long): Number of days of inactivity before the rule applies</li>
   * <li>{@code include_site_admins} (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code include_folder_admins} (Boolean): If true, the rule will apply to folder admins.</li>
   * <li>{@code name} (String): User Lifecycle Rule name</li>
   * <li>{@code notify_users} (Boolean): If true, users will be emailed before the rule disables or deletes them.</li>
   * <li>{@code partner_tag} (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code user_state} (String): State of the users to apply the rule to (inactive or disabled)</li>
   * <li>{@code user_tag} (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code workspace_id} (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code UserLifecycleRule}
   */
  public UserLifecycleRule update(HashMap<String, Object> parameters) throws IOException {
    UserLifecycleRule updated = filesClient().userLifecycleRules().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().userLifecycleRules().delete(this.id, parameters, this.options);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this UserLifecycleRule: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().userLifecycleRules().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.groupIds != null) {
      parameters.put("group_ids", this.groupIds);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(UserLifecycleRule canonical) {
    this.id = canonical.id;
    this.authenticationMethod = canonical.authenticationMethod;
    this.groupIds = canonical.groupIds;
    this.action = canonical.action;
    this.inactivityDays = canonical.inactivityDays;
    this.includeFolderAdmins = canonical.includeFolderAdmins;
    this.includeSiteAdmins = canonical.includeSiteAdmins;
    this.applyToAllWorkspaces = canonical.applyToAllWorkspaces;
    this.name = canonical.name;
    this.notifyUsers = canonical.notifyUsers;
    this.partnerTag = canonical.partnerTag;
    this.siteId = canonical.siteId;
    this.workspaceId = canonical.workspaceId;
    this.userState = canonical.userState;
    this.userTag = canonical.userTag;
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserLifecycleRule> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserLifecycleRule> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List User Lifecycle Rules
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id` and `workspace_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule} records, in a {@code ListIterator}
   */
  public static ListIterator<UserLifecycleRule> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userLifecycleRules().list(parameters, options);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserLifecycleRule> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User Lifecycle Rules
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserLifecycleRule> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @param id User Lifecycle Rule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule}
   */
  public static UserLifecycleRule find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userLifecycleRules().find(id, parameters, options);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User Lifecycle Rule
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User Lifecycle Rule
   *
   * @see #create(HashMap, HashMap)
   */
  public static UserLifecycleRule create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User Lifecycle Rule
   *
   * @see #create(HashMap, HashMap)
   */
  public static UserLifecycleRule create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create User Lifecycle Rule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Action to take on inactive users (disable or delete)</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code authentication_method} (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.</li>
   * <li>{@code group_ids} (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.</li>
   * <li>{@code inactivity_days} (Long): Number of days of inactivity before the rule applies</li>
   * <li>{@code include_site_admins} (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code include_folder_admins} (Boolean): If true, the rule will apply to folder admins.</li>
   * <li>{@code name} (String): User Lifecycle Rule name</li>
   * <li>{@code notify_users} (Boolean): If true, users will be emailed before the rule disables or deletes them.</li>
   * <li>{@code partner_tag} (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code user_state} (String): State of the users to apply the rule to (inactive or disabled)</li>
   * <li>{@code user_tag} (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code workspace_id} (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule}
   */
  public static UserLifecycleRule create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userLifecycleRules().create(parameters, options);
  }


  /**
   * Update User Lifecycle Rule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update User Lifecycle Rule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update User Lifecycle Rule
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static UserLifecycleRule update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update User Lifecycle Rule
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Action to take on inactive users (disable or delete)</li>
   * <li>{@code apply_to_all_workspaces} (Boolean): If true, a Default Workspace rule also applies to users in all Custom Workspaces. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code authentication_method} (String): User authentication method for which the rule will apply. Use `all_non_sso` to target every non-SSO authentication method with one rule.</li>
   * <li>{@code group_ids} (Long[]): Array of Group IDs to which the rule applies. If empty or not set, the rule applies to all users.</li>
   * <li>{@code inactivity_days} (Long): Number of days of inactivity before the rule applies</li>
   * <li>{@code include_site_admins} (Boolean): If true, the rule includes Site Administrators, who always belong to the Default Workspace. Can only be enabled when `workspace_id` is `0`.</li>
   * <li>{@code include_folder_admins} (Boolean): If true, the rule will apply to folder admins.</li>
   * <li>{@code name} (String): User Lifecycle Rule name</li>
   * <li>{@code notify_users} (Boolean): If true, users will be emailed before the rule disables or deletes them.</li>
   * <li>{@code partner_tag} (String): If provided, only users belonging to Partners with this tag at the Partner level will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code user_state} (String): State of the users to apply the rule to (inactive or disabled)</li>
   * <li>{@code user_tag} (String): If provided, only users with this tag will be affected by the rule. Tags must only contain lowercase letters, numbers, and hyphens.</li>
   * <li>{@code workspace_id} (Long): Workspace whose users the rule applies to. `0` means the Default Workspace. A Custom Workspace rule applies only to users who belong to that Workspace, regardless of access granted to other users.</li>
   * </ul>
   *
   * @param id User Lifecycle Rule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserLifecycleRule}
   */
  public static UserLifecycleRule update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userLifecycleRules().update(id, parameters, options);
  }


  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @param id User Lifecycle Rule ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().userLifecycleRules().delete(id, parameters, options);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User Lifecycle Rule
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
