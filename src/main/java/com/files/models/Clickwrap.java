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


  public Clickwrap() {
    this(null, null);
  }

  public Clickwrap(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
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
   * Body text of Clickwrap (supports Markdown formatting).
   */
  @JsonProperty("body")
  public String body;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).
   */
  @JsonProperty("use_with_users")
  public String useWithUsers;

  public String getUseWithUsers() {
    return useWithUsers;
  }

  public void setUseWithUsers(String useWithUsers) {
    this.useWithUsers = useWithUsers;
  }

  /**
   * Use this Clickwrap for Bundles?
   */
  @JsonProperty("use_with_bundles")
  public String useWithBundles;

  public String getUseWithBundles() {
    return useWithBundles;
  }

  public void setUseWithBundles(String useWithBundles) {
    this.useWithBundles = useWithBundles;
  }

  /**
   * Use this Clickwrap for Inboxes?
   */
  @JsonProperty("use_with_inboxes")
  public String useWithInboxes;

  public String getUseWithInboxes() {
    return useWithInboxes;
  }

  public void setUseWithInboxes(String useWithInboxes) {
    this.useWithInboxes = useWithInboxes;
  }

  /**
   * Updates this Clickwrap. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
   *   body - string - Body text of Clickwrap (supports Markdown formatting).
   *   use_with_bundles - string - Use this Clickwrap for Bundles?
   *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
   *   use_with_users - string - Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).
   */
  public Clickwrap update(HashMap<String, Object> parameters) throws IOException {
    Clickwrap updated = filesClient().clickwraps().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().clickwraps().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
   */
  public static ListIterator<Clickwrap> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Clickwrap> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Clickwrap> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().list(parameters, options);
  }

  public static ListIterator<Clickwrap> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Clickwrap> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Clickwrap ID.
   */
  public static Clickwrap find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Clickwrap find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Clickwrap find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Clickwrap find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().find(id, parameters, options);
  }

  public static Clickwrap get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Clickwrap get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
   *   body - string - Body text of Clickwrap (supports Markdown formatting).
   *   use_with_bundles - string - Use this Clickwrap for Bundles?
   *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
   *   use_with_users - string - Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).
   */
  public static Clickwrap create() throws RuntimeException {
    return create(null, null);
  }

  public static Clickwrap create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Clickwrap create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().create(parameters, options);
  }


  /**
   * Parameters:
   *   name - string - Name of the Clickwrap agreement (used when selecting from multiple Clickwrap agreements.)
   *   body - string - Body text of Clickwrap (supports Markdown formatting).
   *   use_with_bundles - string - Use this Clickwrap for Bundles?
   *   use_with_inboxes - string - Use this Clickwrap for Inboxes?
   *   use_with_users - string - Use this Clickwrap for Users?  Values: `none`, `require` (new user signup via email invitation only), `require_all_users_once` (show to all users at their next web login; once accepted, not shown again), `require_all_users_always` (show to all users on every web login).
   */
  public static Clickwrap update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Clickwrap update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Clickwrap update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Clickwrap update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().clickwraps().update(id, parameters, options);
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
    FilesClient.defaultClient().clickwraps().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
