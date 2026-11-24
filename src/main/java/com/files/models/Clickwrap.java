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
public class Clickwrap implements ModelInterface {
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

  // One mapper for every Clickwrap, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Clickwrap} with no attributes set.
   */
  public Clickwrap() {
    this(null, null);
  }

  /**
   * Creates a {@code Clickwrap} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Clickwrap(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Clickwrap} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Clickwrap(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Clickwrap ID
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
   * Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
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
   * Body text of Clickwrap (supports Markdown formatting).
   */
  @JsonProperty("body")
  public String body;

  /**
   * Gets {@link #body}.
   *
   * @return the value of {@link #body}
   */
  public String getBody() {
    return body;
  }

  /**
   * Sets {@link #body}.
   *
   * @param body the new value
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).
   */
  @JsonProperty("use_with_users")
  public String useWithUsers;

  /**
   * Gets {@link #useWithUsers}.
   *
   * @return the value of {@link #useWithUsers}
   */
  public String getUseWithUsers() {
    return useWithUsers;
  }

  /**
   * Sets {@link #useWithUsers}.
   *
   * @param useWithUsers the new value
   */
  public void setUseWithUsers(String useWithUsers) {
    this.useWithUsers = useWithUsers;
  }

  /**
   * Use this Clickwrap for Bundles?
   */
  @JsonProperty("use_with_bundles")
  public String useWithBundles;

  /**
   * Gets {@link #useWithBundles}.
   *
   * @return the value of {@link #useWithBundles}
   */
  public String getUseWithBundles() {
    return useWithBundles;
  }

  /**
   * Sets {@link #useWithBundles}.
   *
   * @param useWithBundles the new value
   */
  public void setUseWithBundles(String useWithBundles) {
    this.useWithBundles = useWithBundles;
  }

  /**
   * Use this Clickwrap for Inboxes?
   */
  @JsonProperty("use_with_inboxes")
  public String useWithInboxes;

  /**
   * Gets {@link #useWithInboxes}.
   *
   * @return the value of {@link #useWithInboxes}
   */
  public String getUseWithInboxes() {
    return useWithInboxes;
  }

  /**
   * Sets {@link #useWithInboxes}.
   *
   * @param useWithInboxes the new value
   */
  public void setUseWithInboxes(String useWithInboxes) {
    this.useWithInboxes = useWithInboxes;
  }

  /**
   * Updates this Clickwrap. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Clickwrap
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)</li>
   * <li>{@code body} (String): Body text of Clickwrap (supports Markdown formatting).</li>
   * <li>{@code use_with_bundles} (String): Use this Clickwrap for Bundles?</li>
   * <li>{@code use_with_inboxes} (String): Use this Clickwrap for Inboxes?</li>
   * <li>{@code use_with_users} (String): Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Clickwrap}
   */
  public Clickwrap update(HashMap<String, Object> parameters) throws IOException {
    Clickwrap updated = filesClient().clickwraps().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Clickwrap
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().clickwraps().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Clickwrap: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().clickwraps().create(parameters, this.options));
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
  private void refreshFrom(Clickwrap canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.body = canonical.body;
    this.useWithUsers = canonical.useWithUsers;
    this.useWithBundles = canonical.useWithBundles;
    this.useWithInboxes = canonical.useWithInboxes;
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Clickwrap> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Clickwrap> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Clickwraps
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap} records, in a {@code ListIterator}
   */
  public static ListIterator<Clickwrap> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().list(parameters, options);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Clickwrap> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Clickwraps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Clickwrap> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Clickwrap find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Clickwrap find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Clickwrap find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Clickwrap
   *
   * @param id Clickwrap ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap}
   */
  public static Clickwrap find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().find(id, parameters, options);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Clickwrap get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Clickwrap
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Clickwrap get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Clickwrap
   *
   * @see #create(HashMap, HashMap)
   */
  public static Clickwrap create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Clickwrap
   *
   * @see #create(HashMap, HashMap)
   */
  public static Clickwrap create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Clickwrap
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)</li>
   * <li>{@code body} (String): Body text of Clickwrap (supports Markdown formatting).</li>
   * <li>{@code use_with_bundles} (String): Use this Clickwrap for Bundles?</li>
   * <li>{@code use_with_inboxes} (String): Use this Clickwrap for Inboxes?</li>
   * <li>{@code use_with_users} (String): Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap}
   */
  public static Clickwrap create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().create(parameters, options);
  }


  /**
   * Update Clickwrap
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Clickwrap update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Clickwrap
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Clickwrap update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Clickwrap
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Clickwrap update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Clickwrap
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)</li>
   * <li>{@code body} (String): Body text of Clickwrap (supports Markdown formatting).</li>
   * <li>{@code use_with_bundles} (String): Use this Clickwrap for Bundles?</li>
   * <li>{@code use_with_inboxes} (String): Use this Clickwrap for Inboxes?</li>
   * <li>{@code use_with_users} (String): Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).</li>
   * </ul>
   *
   * @param id Clickwrap ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Clickwrap}
   */
  public static Clickwrap update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().update(id, parameters, options);
  }


  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Clickwrap
   *
   * @param id Clickwrap ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().clickwraps().delete(id, parameters, options);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Clickwrap
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
