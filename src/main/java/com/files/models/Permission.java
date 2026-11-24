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
public class Permission implements ModelInterface {
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

  // One mapper for every Permission, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Permission} with no attributes set.
   */
  public Permission() {
    this(null, null);
  }

  /**
   * Creates a {@code Permission} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Permission(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Permission} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Permission(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Permission ID
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
   * Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * User ID
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Username (if applicable)
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Group ID
   */
  @JsonProperty("group_id")
  public Long groupId;

  /**
   * Gets {@link #groupId}.
   *
   * @return the value of {@link #groupId}
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * Sets {@link #groupId}.
   *
   * @param groupId the new value
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * Group name (if applicable)
   */
  @JsonProperty("group_name")
  public String groupName;

  /**
   * Gets {@link #groupName}.
   *
   * @return the value of {@link #groupName}
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Sets {@link #groupName}.
   *
   * @param groupName the new value
   */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**
   * Group IDs when this Permission requires membership in every listed group.
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
   * Group names when this permission requires multiple groups
   */
  @JsonProperty("group_names")
  public String[] groupNames;

  /**
   * Gets {@link #groupNames}.
   *
   * @return the value of {@link #groupNames}
   */
  public String[] getGroupNames() {
    return groupNames;
  }

  /**
   * Sets {@link #groupNames}.
   *
   * @param groupNames the new value
   */
  public void setGroupNames(String[] groupNames) {
    this.groupNames = groupNames;
  }

  /**
   * Partner ID (if applicable)
   */
  @JsonProperty("partner_id")
  public Long partnerId;

  /**
   * Gets {@link #partnerId}.
   *
   * @return the value of {@link #partnerId}
   */
  public Long getPartnerId() {
    return partnerId;
  }

  /**
   * Sets {@link #partnerId}.
   *
   * @param partnerId the new value
   */
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * Partner name (if applicable)
   */
  @JsonProperty("partner_name")
  public String partnerName;

  /**
   * Gets {@link #partnerName}.
   *
   * @return the value of {@link #partnerName}
   */
  public String getPartnerName() {
    return partnerName;
  }

  /**
   * Sets {@link #partnerName}.
   *
   * @param partnerName the new value
   */
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
   * Permission type.  See the table referenced in the documentation for an explanation of each permission.
   */
  @JsonProperty("permission")
  public String permission;

  /**
   * Gets {@link #permission}.
   *
   * @return the value of {@link #permission}
   */
  public String getPermission() {
    return permission;
  }

  /**
   * Sets {@link #permission}.
   *
   * @param permission the new value
   */
  public void setPermission(String permission) {
    this.permission = permission;
  }

  /**
   * Whether this Permission applies to subfolders. Must be true for `admin` Permissions.
   */
  @JsonProperty("recursive")
  public Boolean recursive;

  /**
   * Gets {@link #recursive}.
   *
   * @return the value of {@link #recursive}
   */
  public Boolean getRecursive() {
    return recursive;
  }

  /**
   * Sets {@link #recursive}.
   *
   * @param recursive the new value
   */
  public void setRecursive(Boolean recursive) {
    this.recursive = recursive;
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
   * Delete Permission
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().permissions().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Permission
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Creates this Permission.
   * On success, this object's attributes are replaced by the server's response.
   * Permissions cannot be updated, so saving one that already has an id throws
   * {@link UnsupportedOperationException}.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      throw new UnsupportedOperationException("The Permission Object doesn't support updates.");
    }
    refreshFrom(filesClient().permissions().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.groupIds != null) {
      parameters.put("group_ids", ModelUtils.commaDelimited(this.groupIds));
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Permission canonical) {
    this.id = canonical.id;
    this.path = canonical.path;
    this.userId = canonical.userId;
    this.username = canonical.username;
    this.groupId = canonical.groupId;
    this.groupName = canonical.groupName;
    this.groupIds = canonical.groupIds;
    this.groupNames = canonical.groupNames;
    this.partnerId = canonical.partnerId;
    this.partnerName = canonical.partnerName;
    this.permission = canonical.permission;
    this.recursive = canonical.recursive;
    this.siteId = canonical.siteId;
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Permission> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Permission> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Permissions
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `site_id`, `group_id`, `path`, `user_id`, `partner_id` or `id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `path`, `group_id`, `partner_id` or `user_id`. Valid field combinations are `[ group_id, path ]`, `[ partner_id, path ]`, `[ user_id, path ]`, `[ user_id, group_id ]`, `[ user_id, group_id, path ]`, `[ user_id, group_id, partner_id ]` or `[ user_id, group_id, partner_id, path ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `path`.</li>
   * <li>{@code path} (String): Permission path.  If provided, will scope all permissions(including upward) to this path.</li>
   * <li>{@code include_groups} (Boolean): If searching by user or group, also include user's permissions that are inherited from its groups?</li>
   * <li>{@code group_id} (String): </li>
   * <li>{@code partner_id} (String): </li>
   * <li>{@code user_id} (String): </li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Permission} records, in a {@code ListIterator}
   */
  public static ListIterator<Permission> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().permissions().list(parameters, options);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Permission> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Permissions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Permission> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Create Permission
   *
   * @see #create(HashMap, HashMap)
   */
  public static Permission create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Permission
   *
   * @see #create(HashMap, HashMap)
   */
  public static Permission create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Permission
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code path} (String): Required. Folder path</li>
   * <li>{@code group_id} (Long): Group ID. Provide `group_name` or `group_id`</li>
   * <li>{@code group_ids} (String): Group IDs when the Permission requires membership in every listed group. If sent as a string, it should be comma-delimited.</li>
   * <li>{@code permission} (String): Permission type.  Can be `admin`, `full`, `readonly`, `writeonly`, `list`, or `history`</li>
   * <li>{@code recursive} (Boolean): Apply to subfolders recursively? Must be true for `admin` Permissions.</li>
   * <li>{@code partner_id} (Long): Partner ID if this Permission belongs to a partner.</li>
   * <li>{@code user_id} (Long): User ID.  Provide `username` or `user_id`</li>
   * <li>{@code username} (String): User username.  Provide `username` or `user_id`</li>
   * <li>{@code group_name} (String): Group name.  Provide `group_name` or `group_id`</li>
   * <li>{@code site_id} (Long): Site ID. If not provided, will default to current site. Used when creating a permission for a child site.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Permission}
   */
  public static Permission create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().permissions().create(parameters, options);
  }


  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Permission
   *
   * @param id Permission ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().permissions().delete(id, parameters, options);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Permission
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
