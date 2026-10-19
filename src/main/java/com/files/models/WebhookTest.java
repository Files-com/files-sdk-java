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

  // One mapper for every WebhookTest, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code WebhookTest} with no attributes set.
   */
  public WebhookTest() {
    this(null, null);
  }

  /**
   * Creates a {@code WebhookTest} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public WebhookTest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code WebhookTest} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #code}.
   *
   * @return the value of {@link #code}
   */
  public Long getCode() {
    return code;
  }

  /**
   * Sets {@link #code}.
   *
   * @param code the new value
   */
  public void setCode(Long code) {
    this.code = code;
  }

  /**
   * Error message
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
   * Sets {@link #message}.
   *
   * @param message the new value
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Status message
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
   * Sets {@link #status}.
   *
   * @param status the new value
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Additional data
   */
  @JsonProperty("data")
  public Auto data;

  /**
   * Gets {@link #data}.
   *
   * @return the value of {@link #data}
   */
  public Auto getData() {
    return data;
  }

  /**
   * Sets {@link #data}.
   *
   * @param data the new value
   */
  public void setData(Auto data) {
    this.data = data;
  }

  /**
   * The success status of the webhook test
   */
  @JsonProperty("success")
  public Boolean success;

  /**
   * Gets {@link #success}.
   *
   * @return the value of {@link #success}
   */
  public Boolean getSuccess() {
    return success;
  }

  /**
   * Sets {@link #success}.
   *
   * @param success the new value
   */
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  /**
   * URL for testing the webhook.
   */
  @JsonProperty("url")
  public String url;

  /**
   * Gets {@link #url}.
   *
   * @return the value of {@link #url}
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets {@link #url}.
   *
   * @param url the new value
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * HTTP method(GET or POST).
   */
  @JsonProperty("method")
  public String method;

  /**
   * Gets {@link #method}.
   *
   * @return the value of {@link #method}
   */
  public String getMethod() {
    return method;
  }

  /**
   * Sets {@link #method}.
   *
   * @param method the new value
   */
  public void setMethod(String method) {
    this.method = method;
  }

  /**
   * HTTP encoding method.  Can be JSON, XML, or RAW (form data).
   */
  @JsonProperty("encoding")
  public String encoding;

  /**
   * Gets {@link #encoding}.
   *
   * @return the value of {@link #encoding}
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * Sets {@link #encoding}.
   *
   * @param encoding the new value
   */
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  /**
   * Additional request headers.
   */
  @JsonProperty("headers")
  public Object headers;

  /**
   * Gets {@link #headers}.
   *
   * @return the value of {@link #headers}
   */
  public Object getHeaders() {
    return headers;
  }

  /**
   * Sets {@link #headers}.
   *
   * @param headers the new value
   */
  public void setHeaders(Object headers) {
    this.headers = headers;
  }

  /**
   * Additional body parameters.
   */
  @JsonProperty("body")
  public Object body;

  /**
   * Gets {@link #body}.
   *
   * @return the value of {@link #body}
   */
  public Object getBody() {
    return body;
  }

  /**
   * Sets {@link #body}.
   *
   * @param body the new value
   */
  public void setBody(Object body) {
    this.body = body;
  }

  /**
   * raw body text
   */
  @JsonProperty("raw_body")
  public String rawBody;

  /**
   * Gets {@link #rawBody}.
   *
   * @return the value of {@link #rawBody}
   */
  public String getRawBody() {
    return rawBody;
  }

  /**
   * Sets {@link #rawBody}.
   *
   * @param rawBody the new value
   */
  public void setRawBody(String rawBody) {
    this.rawBody = rawBody;
  }

  /**
   * Send the file data as the request body?
   */
  @JsonProperty("file_as_body")
  public Boolean fileAsBody;

  /**
   * Gets {@link #fileAsBody}.
   *
   * @return the value of {@link #fileAsBody}
   */
  public Boolean getFileAsBody() {
    return fileAsBody;
  }

  /**
   * Sets {@link #fileAsBody}.
   *
   * @param fileAsBody the new value
   */
  public void setFileAsBody(Boolean fileAsBody) {
    this.fileAsBody = fileAsBody;
  }

  /**
   * Send the file data as a named parameter in the request POST body
   */
  @JsonProperty("file_form_field")
  public String fileFormField;

  /**
   * Gets {@link #fileFormField}.
   *
   * @return the value of {@link #fileFormField}
   */
  public String getFileFormField() {
    return fileFormField;
  }

  /**
   * Sets {@link #fileFormField}.
   *
   * @param fileFormField the new value
   */
  public void setFileFormField(String fileFormField) {
    this.fileFormField = fileFormField;
  }

  /**
   * action for test body
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
   * Sets {@link #action}.
   *
   * @param action the new value
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Use dedicated IPs for sending the webhook?
   */
  @JsonProperty("use_dedicated_ips")
  public Boolean useDedicatedIps;

  /**
   * Gets {@link #useDedicatedIps}.
   *
   * @return the value of {@link #useDedicatedIps}
   */
  public Boolean getUseDedicatedIps() {
    return useDedicatedIps;
  }

  /**
   * Sets {@link #useDedicatedIps}.
   *
   * @param useDedicatedIps the new value
   */
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
   * Create Webhook Test
   *
   * @see #create(HashMap, HashMap)
   */
  public static WebhookTest create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Webhook Test
   *
   * @see #create(HashMap, HashMap)
   */
  public static WebhookTest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Webhook Test
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code url} (String): Required. URL for testing the webhook.</li>
   * <li>{@code method} (String): HTTP method(GET or POST).</li>
   * <li>{@code encoding} (String): HTTP encoding method.  Can be JSON, XML, or RAW (form data).</li>
   * <li>{@code headers} (Object): Additional request headers.</li>
   * <li>{@code body} (Object): Additional body parameters.</li>
   * <li>{@code raw_body} (String): raw body text</li>
   * <li>{@code file_as_body} (Boolean): Send the file data as the request body?</li>
   * <li>{@code file_form_field} (String): Send the file data as a named parameter in the request POST body</li>
   * <li>{@code action} (String): action for test body</li>
   * <li>{@code use_dedicated_ips} (Boolean): Use dedicated IPs for sending the webhook?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code WebhookTest}
   */
  public static WebhookTest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().webhookTests().create(parameters, options);
  }


}
