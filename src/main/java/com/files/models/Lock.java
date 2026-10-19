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
public class Lock implements ModelInterface {
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

  // One mapper for every Lock, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Lock} with no attributes set.
   */
  public Lock() {
    this(null, null);
  }

  /**
   * Creates a {@code Lock} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Lock(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Lock} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Lock(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Lock timeout in seconds
   */
  @JsonProperty("timeout")
  public Long timeout;

  /**
   * Gets {@link #timeout}.
   *
   * @return the value of {@link #timeout}
   */
  public Long getTimeout() {
    return timeout;
  }

  /**
   * Sets {@link #timeout}.
   *
   * @param timeout the new value
   */
  public void setTimeout(Long timeout) {
    this.timeout = timeout;
  }

  /**
   * Deprecated WebDAV lock-depth field. Use recursive to determine whether the lock applies to subfolders.
   */
  @JsonProperty("depth")
  public String depth;

  /**
   * Gets {@link #depth}.
   *
   * @return the value of {@link #depth}
   */
  public String getDepth() {
    return depth;
  }

  /**
   * Sets {@link #depth}.
   *
   * @param depth the new value
   */
  public void setDepth(String depth) {
    this.depth = depth;
  }

  /**
   * Does lock apply to subfolders?
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
   * Arbitrary descriptive label for the lock. Does not change the lock creator or permissions.
   */
  @JsonProperty("owner")
  public String owner;

  /**
   * Gets {@link #owner}.
   *
   * @return the value of {@link #owner}
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Sets {@link #owner}.
   *
   * @param owner the new value
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * Deprecated WebDAV lock-scope field. Use exclusive to determine whether the lock is exclusive.
   */
  @JsonProperty("scope")
  public String scope;

  /**
   * Gets {@link #scope}.
   *
   * @return the value of {@link #scope}
   */
  public String getScope() {
    return scope;
  }

  /**
   * Sets {@link #scope}.
   *
   * @param scope the new value
   */
  public void setScope(String scope) {
    this.scope = scope;
  }

  /**
   * Is lock exclusive?
   */
  @JsonProperty("exclusive")
  public Boolean exclusive;

  /**
   * Gets {@link #exclusive}.
   *
   * @return the value of {@link #exclusive}
   */
  public Boolean getExclusive() {
    return exclusive;
  }

  /**
   * Sets {@link #exclusive}.
   *
   * @param exclusive the new value
   */
  public void setExclusive(Boolean exclusive) {
    this.exclusive = exclusive;
  }

  /**
   * Lock token.  Use to release lock.
   */
  @JsonProperty("token")
  public String token;

  /**
   * Gets {@link #token}.
   *
   * @return the value of {@link #token}
   */
  public String getToken() {
    return token;
  }

  /**
   * Sets {@link #token}.
   *
   * @param token the new value
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Deprecated WebDAV lock-type field.
   */
  @JsonProperty("type")
  public String type;

  /**
   * Gets {@link #type}.
   *
   * @return the value of {@link #type}
   */
  public String getType() {
    return type;
  }

  /**
   * Sets {@link #type}.
   *
   * @param type the new value
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Can lock be modified by users other than its creator?
   */
  @JsonProperty("allow_access_by_any_user")
  public Boolean allowAccessByAnyUser;

  /**
   * Gets {@link #allowAccessByAnyUser}.
   *
   * @return the value of {@link #allowAccessByAnyUser}
   */
  public Boolean getAllowAccessByAnyUser() {
    return allowAccessByAnyUser;
  }

  /**
   * Sets {@link #allowAccessByAnyUser}.
   *
   * @param allowAccessByAnyUser the new value
   */
  public void setAllowAccessByAnyUser(Boolean allowAccessByAnyUser) {
    this.allowAccessByAnyUser = allowAccessByAnyUser;
  }

  /**
   * Lock creator user ID
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
   * Lock creator username
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
   * Require this existing, unexpired token before refreshing or replacing a lock. Set token to the same value to refresh, or a different value to replace.
   */
  @JsonProperty("expected_token")
  public String expectedToken;

  /**
   * Gets {@link #expectedToken}.
   *
   * @return the value of {@link #expectedToken}
   */
  public String getExpectedToken() {
    return expectedToken;
  }

  /**
   * Sets {@link #expectedToken}.
   *
   * @param expectedToken the new value
   */
  public void setExpectedToken(String expectedToken) {
    this.expectedToken = expectedToken;
  }

  /**
   * Delete Lock
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code token} (String): Required. Lock token</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().locks().delete(this.path, parameters, this.options);
  }

  /**
   * Delete Lock
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Creates this Lock from its current attributes.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    refreshFrom(filesClient().locks().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Lock canonical) {
    this.path = canonical.path;
    this.timeout = canonical.timeout;
    this.depth = canonical.depth;
    this.recursive = canonical.recursive;
    this.owner = canonical.owner;
    this.scope = canonical.scope;
    this.exclusive = canonical.exclusive;
    this.token = canonical.token;
    this.type = canonical.type;
    this.allowAccessByAnyUser = canonical.allowAccessByAnyUser;
    this.userId = canonical.userId;
    this.username = canonical.username;
    this.expectedToken = canonical.expectedToken;
  }

  /**
   * List Locks by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<Lock> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  /**
   * List Locks by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<Lock> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  /**
   * List Locks by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<Lock> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  /**
   * List Locks by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code include_children} (Boolean): Include locks from children objects?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Lock} records, in a {@code ListIterator}
   */
  public static ListIterator<Lock> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().locks().listFor(path, parameters, options);
  }


  /**
   * Create Lock
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static Lock create() throws RuntimeException {
    return create(null, null, null);
  }

  /**
   * Create Lock
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static Lock create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  /**
   * Create Lock
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static Lock create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  /**
   * Create Lock
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code token} (String): Lock token. With expected_token, use the same value to refresh or a different value to replace the existing token.</li>
   * <li>{@code expected_token} (String): Require this existing, unexpired token before refreshing or replacing a lock. Set token to the same value to refresh, or a different value to replace.</li>
   * <li>{@code allow_access_by_any_user} (Boolean): Can lock be modified by users other than its creator?</li>
   * <li>{@code exclusive} (Boolean): Is lock exclusive?</li>
   * <li>{@code recursive} (Boolean): Does lock apply to subfolders?</li>
   * <li>{@code owner} (String): Arbitrary descriptive label for the lock. Does not change the lock creator or permissions.</li>
   * <li>{@code timeout} (Long): Lock timeout in seconds</li>
   * </ul>
   *
   * @param path Path
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Lock}
   */
  public static Lock create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().locks().create(path, parameters, options);
  }


  /**
   * Delete Lock
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Lock
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  /**
   * Delete Lock
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Lock
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code token} (String): Required. Lock token</li>
   * </ul>
   *
   * @param path Path
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().locks().delete(path, parameters, options);
  }

  /**
   * Delete Lock
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Lock
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

}
