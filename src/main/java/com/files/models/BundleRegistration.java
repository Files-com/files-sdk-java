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
public class BundleRegistration implements ModelInterface {
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

  // One mapper for every BundleRegistration, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code BundleRegistration} with no attributes set.
   */
  public BundleRegistration() {
    this(null, null);
  }

  /**
   * Creates a {@code BundleRegistration} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public BundleRegistration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code BundleRegistration} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public BundleRegistration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Registration cookie code
   */
  @JsonProperty("code")
  public String code;

  /**
   * Gets {@link #code}.
   *
   * @return the value of {@link #code}
   */
  public String getCode() {
    return code;
  }

  /**
   * Registrant name
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
   * Registrant company name
   */
  @JsonProperty("company")
  public String company;

  /**
   * Gets {@link #company}.
   *
   * @return the value of {@link #company}
   */
  public String getCompany() {
    return company;
  }

  /**
   * Registrant email address
   */
  @JsonProperty("email")
  public String email;

  /**
   * Gets {@link #email}.
   *
   * @return the value of {@link #email}
   */
  public String getEmail() {
    return email;
  }

  /**
   * Registrant IP Address
   */
  @JsonProperty("ip")
  public String ip;

  /**
   * Gets {@link #ip}.
   *
   * @return the value of {@link #ip}
   */
  public String getIp() {
    return ip;
  }

  /**
   * InboxRegistration cookie code, if there is an associated InboxRegistration
   */
  @JsonProperty("inbox_code")
  public String inboxCode;

  /**
   * Gets {@link #inboxCode}.
   *
   * @return the value of {@link #inboxCode}
   */
  public String getInboxCode() {
    return inboxCode;
  }

  /**
   * Clickwrap text that was shown to the registrant
   */
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  /**
   * Gets {@link #clickwrapBody}.
   *
   * @return the value of {@link #clickwrapBody}
   */
  public String getClickwrapBody() {
    return clickwrapBody;
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
   * Bundle URL code
   */
  @JsonProperty("bundle_code")
  public String bundleCode;

  /**
   * Gets {@link #bundleCode}.
   *
   * @return the value of {@link #bundleCode}
   */
  public String getBundleCode() {
    return bundleCode;
  }

  /**
   * Id of associated bundle
   */
  @JsonProperty("bundle_id")
  public Long bundleId;

  /**
   * Gets {@link #bundleId}.
   *
   * @return the value of {@link #bundleId}
   */
  public Long getBundleId() {
    return bundleId;
  }

  /**
   * Id of associated bundle recipient
   */
  @JsonProperty("bundle_recipient_id")
  public Long bundleRecipientId;

  /**
   * Gets {@link #bundleRecipientId}.
   *
   * @return the value of {@link #bundleRecipientId}
   */
  public Long getBundleRecipientId() {
    return bundleRecipientId;
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
   * Registration creation date/time
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
   * List Share Link Registrations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleRegistration> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Share Link Registrations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleRegistration> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Share Link Registrations
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `bundle_id` or `created_at`.</li>
   * <li>{@code bundle_id} (Long): ID of the associated Bundle</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code BundleRegistration} records, in a {@code ListIterator}
   */
  public static ListIterator<BundleRegistration> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().bundleRegistrations().list(parameters, options);
  }

  /**
   * List Share Link Registrations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleRegistration> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Share Link Registrations
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<BundleRegistration> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
