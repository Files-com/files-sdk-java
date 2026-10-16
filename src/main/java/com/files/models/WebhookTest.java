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
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookTest implements ModelInterface {
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

  // One mapper for every WebhookTest, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public WebhookTest() {
    this(null, null);
  }

  public WebhookTest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public WebhookTest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Status HTTP code
   */
  @JsonProperty("code")
  public Long code;

  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
    this.code = code;
  }

  /**
   * Error message
   */
  @JsonProperty("message")
  public String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Status message
   */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Additional data
   */
  @JsonProperty("data")
  public Auto data;

  public Auto getData() {
    return data;
  }

  public void setData(Auto data) {
    this.data = data;
  }

  /**
   * The success status of the webhook test
   */
  @JsonProperty("success")
  public Boolean success;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  /**
   * URL for testing the webhook.
   */
  @JsonProperty("url")
  public String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * HTTP method(GET or POST).
   */
  @JsonProperty("method")
  public String method;

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  /**
   * HTTP encoding method.  Can be JSON, XML, or RAW (form data).
   */
  @JsonProperty("encoding")
  public String encoding;

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  /**
   * Additional request headers.
   */
  @JsonProperty("headers")
  public Object headers;

  public Object getHeaders() {
    return headers;
  }

  public void setHeaders(Object headers) {
    this.headers = headers;
  }

  /**
   * Additional body parameters.
   */
  @JsonProperty("body")
  public Object body;

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  /**
   * raw body text
   */
  @JsonProperty("raw_body")
  public String rawBody;

  public String getRawBody() {
    return rawBody;
  }

  public void setRawBody(String rawBody) {
    this.rawBody = rawBody;
  }

  /**
   * Send the file data as the request body?
   */
  @JsonProperty("file_as_body")
  public Boolean fileAsBody;

  public Boolean getFileAsBody() {
    return fileAsBody;
  }

  public void setFileAsBody(Boolean fileAsBody) {
    this.fileAsBody = fileAsBody;
  }

  /**
   * Send the file data as a named parameter in the request POST body
   */
  @JsonProperty("file_form_field")
  public String fileFormField;

  public String getFileFormField() {
    return fileFormField;
  }

  public void setFileFormField(String fileFormField) {
    this.fileFormField = fileFormField;
  }

  /**
   * action for test body
   */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Use dedicated IPs for sending the webhook?
   */
  @JsonProperty("use_dedicated_ips")
  public Boolean useDedicatedIps;

  public Boolean getUseDedicatedIps() {
    return useDedicatedIps;
  }

  public void setUseDedicatedIps(Boolean useDedicatedIps) {
    this.useDedicatedIps = useDedicatedIps;
  }


  /**
   * Creates this WebhookTest from its current attributes.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    refreshFrom(filesClient().webhookTests().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(WebhookTest canonical) {
    this.code = canonical.code;
    this.message = canonical.message;
    this.status = canonical.status;
    this.data = canonical.data;
    this.success = canonical.success;
    this.url = canonical.url;
    this.method = canonical.method;
    this.encoding = canonical.encoding;
    this.headers = canonical.headers;
    this.body = canonical.body;
    this.rawBody = canonical.rawBody;
    this.fileAsBody = canonical.fileAsBody;
    this.fileFormField = canonical.fileFormField;
    this.action = canonical.action;
    this.useDedicatedIps = canonical.useDedicatedIps;
  }

  /**
   * Parameters:
   *   url (required) - string - URL for testing the webhook.
   *   method - string - HTTP method(GET or POST).
   *   encoding - string - HTTP encoding method.  Can be JSON, XML, or RAW (form data).
   *   headers - object - Additional request headers.
   *   body - object - Additional body parameters.
   *   raw_body - string - raw body text
   *   file_as_body - boolean - Send the file data as the request body?
   *   file_form_field - string - Send the file data as a named parameter in the request POST body
   *   action - string - action for test body
   *   use_dedicated_ips - boolean - Use dedicated IPs for sending the webhook?
   */
  public static WebhookTest create() throws RuntimeException {
    return create(null, null);
  }

  public static WebhookTest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static WebhookTest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().webhookTests().create(parameters, options);
  }


}
