package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
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
public class InboxRegistration implements ModelInterface {
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

  // One mapper for every InboxRegistration, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public InboxRegistration() {
    this(null, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
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

  public String getCode() {
    return code;
  }

  /**
   * Registrant name
   */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  /**
   * Registrant company name
   */
  @JsonProperty("company")
  public String company;

  public String getCompany() {
    return company;
  }

  /**
   * Registrant email address
   */
  @JsonProperty("email")
  public String email;

  public String getEmail() {
    return email;
  }

  /**
   * Registrant IP Address
   */
  @JsonProperty("ip")
  public String ip;

  public String getIp() {
    return ip;
  }

  /**
   * Clickwrap text that was shown to the registrant
   */
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  public String getClickwrapBody() {
    return clickwrapBody;
  }

  /**
   * Id of associated form field set
   */
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  public Long getFormFieldSetId() {
    return formFieldSetId;
  }

  /**
   * Data for form field set with form field ids as keys and user data as values
   */
  @JsonProperty("form_field_data")
  public Object formFieldData;

  public Object getFormFieldData() {
    return formFieldData;
  }

  /**
   * Id of associated inbox
   */
  @JsonProperty("inbox_id")
  public Long inboxId;

  public Long getInboxId() {
    return inboxId;
  }

  /**
   * Id of associated inbox recipient
   */
  @JsonProperty("inbox_recipient_id")
  public Long inboxRecipientId;

  public Long getInboxRecipientId() {
    return inboxRecipientId;
  }

  /**
   * Title of associated inbox
   */
  @JsonProperty("inbox_title")
  public String inboxTitle;

  public String getInboxTitle() {
    return inboxTitle;
  }

  /**
   * Registration creation date/time
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }


  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   folder_behavior_id - int64 - ID of the associated Inbox. This is required if the user is not a site admin.
   */
  public static ListIterator<InboxRegistration> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<InboxRegistration> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<InboxRegistration> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().inboxRegistrations().list(parameters, options);
  }

  public static ListIterator<InboxRegistration> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<InboxRegistration> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
