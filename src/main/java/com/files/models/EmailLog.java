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
public class EmailLog implements ModelInterface {
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

  // One mapper for every EmailLog, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code EmailLog} with no attributes set.
   */
  public EmailLog() {
    this(null, null);
  }

  /**
   * Creates a {@code EmailLog} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public EmailLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code EmailLog} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public EmailLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Start Time of Action. Deprecrated: Use created_at.
   */
  @JsonProperty("timestamp")
  public Date timestamp;

  /**
   * Gets {@link #timestamp}.
   *
   * @return the value of {@link #timestamp}
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * Log Message
   */
  @JsonProperty("message")
  public String message;

  /**
   * Gets {@link #message}.
   *
   * @return the value of {@link #message}
   */
  public String getMessage() {
    return message;
  }

  /**
   * Status of E-Mail delivery
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * Subject line of E-Mail
   */
  @JsonProperty("subject")
  public String subject;

  /**
   * Gets {@link #subject}.
   *
   * @return the value of {@link #subject}
   */
  public String getSubject() {
    return subject;
  }

  /**
   * To field of E-Mail
   */
  @JsonProperty("to")
  public String to;

  /**
   * Gets {@link #to}.
   *
   * @return the value of {@link #to}
   */
  public String getTo() {
    return to;
  }

  /**
   * CC field of E-Mail
   */
  @JsonProperty("cc")
  public String cc;

  /**
   * Gets {@link #cc}.
   *
   * @return the value of {@link #cc}
   */
  public String getCc() {
    return cc;
  }

  /**
   * How was the email delivered?  `customer_smtp` or `files.com`
   */
  @JsonProperty("delivery_method")
  public String deliveryMethod;

  /**
   * Gets {@link #deliveryMethod}.
   *
   * @return the value of {@link #deliveryMethod}
   */
  public String getDeliveryMethod() {
    return deliveryMethod;
  }

  /**
   * Customer SMTP Hostname used.
   */
  @JsonProperty("smtp_hostname")
  public String smtpHostname;

  /**
   * Gets {@link #smtpHostname}.
   *
   * @return the value of {@link #smtpHostname}
   */
  public String getSmtpHostname() {
    return smtpHostname;
  }

  /**
   * Customer SMTP IP address as resolved for use (useful for troubleshooting DNS issues with customer SMTP).
   */
  @JsonProperty("smtp_ip")
  public String smtpIp;

  /**
   * Gets {@link #smtpIp}.
   *
   * @return the value of {@link #smtpIp}
   */
  public String getSmtpIp() {
    return smtpIp;
  }

  /**
   * Start Time of Action
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
   * List Email Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EmailLog> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Email Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EmailLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Email Logs
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 1000, 1,000 or less is recommended).</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `status` and `created_at`. Valid field combinations are `[ status ]`, `[ created_at ]` or `[ status, created_at ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ status ]`, `[ created_at ]` or `[ status, created_at ]`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ status ]`, `[ created_at ]` or `[ status, created_at ]`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ status ]`, `[ created_at ]` or `[ status, created_at ]`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ status ]`, `[ created_at ]` or `[ status, created_at ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code EmailLog} records, in a {@code ListIterator}
   */
  public static ListIterator<EmailLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().emailLogs().list(parameters, options);
  }

  /**
   * List Email Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EmailLog> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Email Logs
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<EmailLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
