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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BundleAction implements ModelInterface {
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

  // One mapper for every BundleAction, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code BundleAction} with no attributes set.
   */
  public BundleAction() {
    this(null, null);
  }

  /**
   * Creates a {@code BundleAction} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public BundleAction(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code BundleAction} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public BundleAction(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Type of action
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
   * Object that contains bundle registration information
   */
  @JsonProperty("bundle_registration")
  public BundleRegistration bundleRegistration;

  /**
   * Gets {@link #bundleRegistration}.
   *
   * @return the value of {@link #bundleRegistration}
   */
  public BundleRegistration getBundleRegistration() {
    return bundleRegistration;
  }

  /**
   * Action occurrence date/time
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * The destination path for this bundle action, if applicable
   */
  @JsonProperty("destination")
  public String destination;

  /**
   * Gets {@link #destination}.
   *
   * @return the value of {@link #destination}
   */
  public String getDestination() {
    return destination;
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
   * The source path for this bundle action, if applicable
   */
  @JsonProperty("source")
  public String source;

  /**
   * Gets {@link #source}.
   *
   * @return the value of {@link #source}
   */
  public String getSource() {
    return source;
  }

  /**
   * Identifier of the associated bundle registration
   */
  @JsonProperty("bundle_registration_id")
  public Long bundleRegistrationId;

  /**
   * Gets {@link #bundleRegistrationId}.
   *
   * @return the value of {@link #bundleRegistrationId}
   */
  public Long getBundleRegistrationId() {
    return bundleRegistrationId;
  }

  /**
   * Workspace ID. `0` means the default workspace.
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
   * Name of the registrant who performed the action
   */
  @JsonProperty("bundle_registration_name")
  public String bundleRegistrationName;

  /**
   * Gets {@link #bundleRegistrationName}.
   *
   * @return the value of {@link #bundleRegistrationName}
   */
  public String getBundleRegistrationName() {
    return bundleRegistrationName;
  }

  /**
   * Email of the registrant
   */
  @JsonProperty("bundle_registration_email")
  public String bundleRegistrationEmail;

  /**
   * Gets {@link #bundleRegistrationEmail}.
   *
   * @return the value of {@link #bundleRegistrationEmail}
   */
  public String getBundleRegistrationEmail() {
    return bundleRegistrationEmail;
  }

  /**
   * IP address of the registrant
   */
  @JsonProperty("bundle_registration_ip")
  public String bundleRegistrationIp;

  /**
   * Gets {@link #bundleRegistrationIp}.
   *
   * @return the value of {@link #bundleRegistrationIp}
   */
  public String getBundleRegistrationIp() {
    return bundleRegistrationIp;
  }

  /**
   * Id of associated form field set
   */
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  /**
   * Gets {@link #formFieldSetId}.
   *
   * @return the value of {@link #formFieldSetId}
   */
  public Long getFormFieldSetId() {
    return formFieldSetId;
  }

  /**
   * Data for form field set with form field ids as keys and user data as values
   */
  @JsonProperty("form_field_data")
  public Object formFieldData;

  /**
   * Gets {@link #formFieldData}.
   *
   * @return the value of {@link #formFieldData}
   */
  public Object getFormFieldData() {
    return formFieldData;
  }


  /**
   * List Bundle Actions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleAction> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Bundle Actions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleAction> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Bundle Actions
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `created_at` or `bundle_registration_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `bundle_id` or `bundle_registration_id`. Valid field combinations are `[ bundle_id, created_at ]`, `[ bundle_registration_id, created_at ]`, `[ bundle_id, bundle_registration_id ]` or `[ bundle_id, bundle_registration_id, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.</li>
   * <li>{@code bundle_id} (Long): Bundle ID</li>
   * <li>{@code bundle_registration_id} (Long): Bundle Registration ID</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleAction} records, in a {@code ListIterator}
   */
  public static ListIterator<BundleAction> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundleActions().list(parameters, options);
  }

  /**
   * List Bundle Actions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleAction> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Bundle Actions
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleAction> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
