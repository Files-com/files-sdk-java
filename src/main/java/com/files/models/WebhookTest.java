package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookTest {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public WebhookTest() {
    this(null, null);
  }

  public WebhookTest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public WebhookTest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Status HTTP code
  */
  @Getter
  @Setter
  @JsonProperty("code")
  public Long code;

  /**
  * Error message
  */
  @Getter
  @Setter
  @JsonProperty("message")
  public String message;

  /**
  * Status message
  */
  @Getter
  @Setter
  @JsonProperty("status")
  public String status;

  /**
  * Additional data
  */
  @Getter
  @Setter
  @JsonProperty("data")
  public Auto data;

  /**
  * The success status of the webhook test
  */
  @Getter
  @Setter
  @JsonProperty("success")
  public Boolean success;

  /**
  * URL for testing the webhook.
  */
  @Getter
  @Setter
  @JsonProperty("url")
  public String url;

  /**
  * HTTP method(GET or POST).
  */
  @Getter
  @Setter
  @JsonProperty("method")
  public String method;

  /**
  * HTTP encoding method.  Can be JSON, XML, or RAW (form data).
  */
  @Getter
  @Setter
  @JsonProperty("encoding")
  public String encoding;

  /**
  * Additional request headers.
  */
  @Getter
  @Setter
  @JsonProperty("headers")
  public Map<String, String> headers;

  /**
  * Additional body parameters.
  */
  @Getter
  @Setter
  @JsonProperty("body")
  public Map<String, String> body;

  /**
  * raw body text
  */
  @Getter
  @Setter
  @JsonProperty("raw_body")
  public String rawBody;

  /**
  * Send the file data as the request body?
  */
  @Getter
  @Setter
  @JsonProperty("file_as_body")
  public Boolean fileAsBody;

  /**
  * Send the file data as a named parameter in the request POST body
  */
  @Getter
  @Setter
  @JsonProperty("file_form_field")
  public String fileFormField;

  /**
  * action for test body
  */
  @Getter
  @Setter
  @JsonProperty("action")
  public String action;

  /**
  * Use dedicated IPs for sending the webhook?
  */
  @Getter
  @Setter
  @JsonProperty("use_dedicated_ips")
  public Boolean useDedicatedIps;


  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      throw new UnsupportedOperationException("The WebhookTest Object doesn't support updates.");
    } else {
      WebhookTest newObject = WebhookTest.create(parameters, this.options);
    }
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("url") || parameters.get("url") == null) {
      throw new NullPointerException("Parameter missing: url parameters[\"url\"]");
    }

    if (parameters.containsKey("url") && !(parameters.get("url") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: url must be of type String parameters[\"url\"]");
    }
    if (parameters.containsKey("method") && !(parameters.get("method") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: method must be of type String parameters[\"method\"]");
    }
    if (parameters.containsKey("encoding") && !(parameters.get("encoding") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: encoding must be of type String parameters[\"encoding\"]");
    }
    if (parameters.containsKey("headers") && !(parameters.get("headers") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: headers must be of type Map<String, String> parameters[\"headers\"]");
    }
    if (parameters.containsKey("body") && !(parameters.get("body") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type Map<String, String> parameters[\"body\"]");
    }
    if (parameters.containsKey("raw_body") && !(parameters.get("raw_body") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: raw_body must be of type String parameters[\"raw_body\"]");
    }
    if (parameters.containsKey("file_as_body") && !(parameters.get("file_as_body") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: file_as_body must be of type Boolean parameters[\"file_as_body\"]");
    }
    if (parameters.containsKey("file_form_field") && !(parameters.get("file_form_field") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: file_form_field must be of type String parameters[\"file_form_field\"]");
    }
    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }
    if (parameters.containsKey("use_dedicated_ips") && !(parameters.get("use_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_dedicated_ips must be of type Boolean parameters[\"use_dedicated_ips\"]");
    }


    String url = String.format("%s%s/webhook_tests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<WebhookTest> typeReference = new TypeReference<WebhookTest>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
