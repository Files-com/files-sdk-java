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
public class Group implements ModelInterface {
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

  // One mapper for every Group, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Group} with no attributes set.
   */
  public Group() {
    this(null, null);
  }

  /**
   * Creates a {@code Group} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Group(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Group} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Group(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Group ID
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
   * Group name
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
   * A list of allowed IPs if applicable.  Newline delimited
   */
  @JsonProperty("allowed_ips")
  public String allowedIps;

  /**
   * Gets {@link #allowedIps}.
   *
   * @return the value of {@link #allowedIps}
   */
  public String getAllowedIps() {
    return allowedIps;
  }

  /**
   * Sets {@link #allowedIps}.
   *
   * @param allowedIps the new value
   */
  public void setAllowedIps(String allowedIps) {
    this.allowedIps = allowedIps;
  }

  /**
   * Comma-delimited list of user IDs who are group administrators (separated by commas)
   */
  @JsonProperty("admin_ids")
  public String adminIds;

  /**
   * Gets {@link #adminIds}.
   *
   * @return the value of {@link #adminIds}
   */
  public String getAdminIds() {
    return adminIds;
  }

  /**
   * Sets {@link #adminIds}.
   *
   * @param adminIds the new value
   */
  public void setAdminIds(String adminIds) {
    this.adminIds = adminIds;
  }

  /**
   * Notes about this group
   */
  @JsonProperty("notes")
  public String notes;

  /**
   * Gets {@link #notes}.
   *
   * @return the value of {@link #notes}
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Sets {@link #notes}.
   *
   * @param notes the new value
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * Comma-delimited list of user IDs who belong to this group (separated by commas)
   */
  @JsonProperty("user_ids")
  public String userIds;

  /**
   * Gets {@link #userIds}.
   *
   * @return the value of {@link #userIds}
   */
  public String getUserIds() {
    return userIds;
  }

  /**
   * Sets {@link #userIds}.
   *
   * @param userIds the new value
   */
  public void setUserIds(String userIds) {
    this.userIds = userIds;
  }

  /**
   * Comma-delimited list of usernames who belong to this group (separated by commas)
   */
  @JsonProperty("usernames")
  public String usernames;

  /**
   * Gets {@link #usernames}.
   *
   * @return the value of {@link #usernames}
   */
  public String getUsernames() {
    return usernames;
  }

  /**
   * Sets {@link #usernames}.
   *
   * @param usernames the new value
   */
  public void setUsernames(String usernames) {
    this.usernames = usernames;
  }

  /**
   * AI Assistant Personality ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user or Partner assignment overrides it.
   */
  @JsonProperty("ai_assistant_personality_id")
  public Long aiAssistantPersonalityId;

  /**
   * Gets {@link #aiAssistantPersonalityId}.
   *
   * @return the value of {@link #aiAssistantPersonalityId}
   */
  public Long getAiAssistantPersonalityId() {
    return aiAssistantPersonalityId;
  }

  /**
   * Sets {@link #aiAssistantPersonalityId}.
   *
   * @param aiAssistantPersonalityId the new value
   */
  public void setAiAssistantPersonalityId(Long aiAssistantPersonalityId) {
    this.aiAssistantPersonalityId = aiAssistantPersonalityId;
  }

  /**
   * If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.
   */
  @JsonProperty("ftp_permission")
  public Boolean ftpPermission;

  /**
   * Gets {@link #ftpPermission}.
   *
   * @return the value of {@link #ftpPermission}
   */
  public Boolean getFtpPermission() {
    return ftpPermission;
  }

  /**
   * Sets {@link #ftpPermission}.
   *
   * @param ftpPermission the new value
   */
  public void setFtpPermission(Boolean ftpPermission) {
    this.ftpPermission = ftpPermission;
  }

  /**
   * If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.
   */
  @JsonProperty("sftp_permission")
  public Boolean sftpPermission;

  /**
   * Gets {@link #sftpPermission}.
   *
   * @return the value of {@link #sftpPermission}
   */
  public Boolean getSftpPermission() {
    return sftpPermission;
  }

  /**
   * Sets {@link #sftpPermission}.
   *
   * @param sftpPermission the new value
   */
  public void setSftpPermission(Boolean sftpPermission) {
    this.sftpPermission = sftpPermission;
  }

  /**
   * If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.
   */
  @JsonProperty("dav_permission")
  public Boolean davPermission;

  /**
   * Gets {@link #davPermission}.
   *
   * @return the value of {@link #davPermission}
   */
  public Boolean getDavPermission() {
    return davPermission;
  }

  /**
   * Sets {@link #davPermission}.
   *
   * @param davPermission the new value
   */
  public void setDavPermission(Boolean davPermission) {
    this.davPermission = davPermission;
  }

  /**
   * If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.
   */
  @JsonProperty("restapi_permission")
  public Boolean restapiPermission;

  /**
   * Gets {@link #restapiPermission}.
   *
   * @return the value of {@link #restapiPermission}
   */
  public Boolean getRestapiPermission() {
    return restapiPermission;
  }

  /**
   * Sets {@link #restapiPermission}.
   *
   * @param restapiPermission the new value
   */
  public void setRestapiPermission(Boolean restapiPermission) {
    this.restapiPermission = restapiPermission;
  }

  /**
   * If true, users in this group can access the S3-compatible endpoint. This will override a false value of `s3_compatible_endpoint_permission` on the user level. Defaults to false.
   */
  @JsonProperty("s3_compatible_endpoint_permission")
  public Boolean s3CompatibleEndpointPermission;

  /**
   * Gets {@link #s3CompatibleEndpointPermission}.
   *
   * @return the value of {@link #s3CompatibleEndpointPermission}
   */
  public Boolean getS3CompatibleEndpointPermission() {
    return s3CompatibleEndpointPermission;
  }

  /**
   * Sets {@link #s3CompatibleEndpointPermission}.
   *
   * @param s3CompatibleEndpointPermission the new value
   */
  public void setS3CompatibleEndpointPermission(Boolean s3CompatibleEndpointPermission) {
    this.s3CompatibleEndpointPermission = s3CompatibleEndpointPermission;
  }

  /**
   * Desktop Configuration Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.
   */
  @JsonProperty("desktop_configuration_profile_id")
  public Long desktopConfigurationProfileId;

  /**
   * Gets {@link #desktopConfigurationProfileId}.
   *
   * @return the value of {@link #desktopConfigurationProfileId}
   */
  public Long getDesktopConfigurationProfileId() {
    return desktopConfigurationProfileId;
  }

  /**
   * Sets {@link #desktopConfigurationProfileId}.
   *
   * @param desktopConfigurationProfileId the new value
   */
  public void setDesktopConfigurationProfileId(Long desktopConfigurationProfileId) {
    this.desktopConfigurationProfileId = desktopConfigurationProfileId;
  }

  /**
   * Integration Centric Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.
   */
  @JsonProperty("integration_centric_profile_id")
  public Long integrationCentricProfileId;

  /**
   * Gets {@link #integrationCentricProfileId}.
   *
   * @return the value of {@link #integrationCentricProfileId}
   */
  public Long getIntegrationCentricProfileId() {
    return integrationCentricProfileId;
  }

  /**
   * Sets {@link #integrationCentricProfileId}.
   *
   * @param integrationCentricProfileId the new value
   */
  public void setIntegrationCentricProfileId(Long integrationCentricProfileId) {
    this.integrationCentricProfileId = integrationCentricProfileId;
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
   * Workspace ID
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
   * Updates this Group. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Group notes.</li>
   * <li>{@code user_ids} (String): A list of user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code admin_ids} (String): A list of group admin user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user or Partner assignment overrides it.</li>
   * <li>{@code ftp_permission} (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.</li>
   * <li>{@code sftp_permission} (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.</li>
   * <li>{@code dav_permission} (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.</li>
   * <li>{@code restapi_permission} (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): If true, users in this group can access the S3-compatible endpoint. This will override a false value of `s3_compatible_endpoint_permission` on the user level. Defaults to false.</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code name} (String): Group name.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Group}
   */
  public Group update(HashMap<String, Object> parameters) throws IOException {
    Group updated = filesClient().groups().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Group
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().groups().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Group
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Group: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().groups().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Group canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.allowedIps = canonical.allowedIps;
    this.adminIds = canonical.adminIds;
    this.notes = canonical.notes;
    this.userIds = canonical.userIds;
    this.usernames = canonical.usernames;
    this.aiAssistantPersonalityId = canonical.aiAssistantPersonalityId;
    this.ftpPermission = canonical.ftpPermission;
    this.sftpPermission = canonical.sftpPermission;
    this.davPermission = canonical.davPermission;
    this.restapiPermission = canonical.restapiPermission;
    this.s3CompatibleEndpointPermission = canonical.s3CompatibleEndpointPermission;
    this.desktopConfigurationProfileId = canonical.desktopConfigurationProfileId;
    this.integrationCentricProfileId = canonical.integrationCentricProfileId;
    this.siteId = canonical.siteId;
    this.workspaceId = canonical.workspaceId;
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Group> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Group> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Groups
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `workspace_id` or `name`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `workspace_id`. Valid field combinations are `[ workspace_id, name ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.</li>
   * <li>{@code ids} (String): Comma-separated list of group ids to include in results.</li>
   * <li>{@code include_parent_site_groups} (Boolean): Include groups from the parent site.</li>
   * <li>{@code include_default_workspace_groups} (Boolean): When scoped to a Custom Workspace, include Default Workspace groups that have permissions in that Workspace. Available to Site Administrators, and to Workspace Administrators and Folder Admins in that Workspace when the site shares its Default Workspace directory with Workspaces.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group} records, in a {@code ListIterator}
   */
  public static ListIterator<Group> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groups().list(parameters, options);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Group> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Groups
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Group> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Group find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Group find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Group find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Group
   *
   * @param id Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group}
   */
  public static Group find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groups().find(id, parameters, options);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Group get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Group
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Group get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Group
   *
   * @see #create(HashMap, HashMap)
   */
  public static Group create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Group
   *
   * @see #create(HashMap, HashMap)
   */
  public static Group create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Group notes.</li>
   * <li>{@code user_ids} (String): A list of user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code admin_ids} (String): A list of group admin user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user or Partner assignment overrides it.</li>
   * <li>{@code ftp_permission} (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.</li>
   * <li>{@code sftp_permission} (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.</li>
   * <li>{@code dav_permission} (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.</li>
   * <li>{@code restapi_permission} (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): If true, users in this group can access the S3-compatible endpoint. This will override a false value of `s3_compatible_endpoint_permission` on the user level. Defaults to false.</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code name} (String): Required. Group name.</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group}
   */
  public static Group create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groups().create(parameters, options);
  }


  /**
   * Update Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Group update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Group update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Group
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Group update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Group
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code notes} (String): Group notes.</li>
   * <li>{@code user_ids} (String): A list of user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code admin_ids} (String): A list of group admin user ids. If sent as a string, should be comma-delimited.</li>
   * <li>{@code ai_assistant_personality_id} (Long): AI Assistant Personality ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user or Partner assignment overrides it.</li>
   * <li>{@code ftp_permission} (Boolean): If true, users in this group can use FTP to login.  This will override a false value of `ftp_permission` on the user level.</li>
   * <li>{@code sftp_permission} (Boolean): If true, users in this group can use SFTP to login.  This will override a false value of `sftp_permission` on the user level.</li>
   * <li>{@code dav_permission} (Boolean): If true, users in this group can use WebDAV to login.  This will override a false value of `dav_permission` on the user level.</li>
   * <li>{@code restapi_permission} (Boolean): If true, users in this group can use the REST API to login.  This will override a false value of `restapi_permission` on the user level.</li>
   * <li>{@code s3_compatible_endpoint_permission} (Boolean): If true, users in this group can access the S3-compatible endpoint. This will override a false value of `s3_compatible_endpoint_permission` on the user level. Defaults to false.</li>
   * <li>{@code desktop_configuration_profile_id} (Long): Desktop Configuration Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code integration_centric_profile_id} (Long): Integration Centric Profile ID assigned to this Group, if any. Users in the Group inherit it unless a direct per-user assignment overrides it.</li>
   * <li>{@code allowed_ips} (String): A list of allowed IPs if applicable.  Newline delimited</li>
   * <li>{@code name} (String): Group name.</li>
   * </ul>
   *
   * @param id Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Group}
   */
  public static Group update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().groups().update(id, parameters, options);
  }


  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Group
   *
   * @param id Group ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().groups().delete(id, parameters, options);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Group
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
