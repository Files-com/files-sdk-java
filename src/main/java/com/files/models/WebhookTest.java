package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class WebhookTest {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public WebhookTest() {
    this(null, null);
  }

  public WebhookTest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public WebhookTest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Status HTTP code
  */
  @Getter
  @Setter
  @JsonProperty("code")
  private Long code;

  /**
  * Error message
  */
  @Getter
  @Setter
  @JsonProperty("message")
  private String message;

  /**
  * Status message
  */
  @Getter
  @Setter
  @JsonProperty("status")
  private String status;

  /**
  * Additional data
  */
  @Getter
  @Setter
  @JsonProperty("data")
  private Map<String, String> data;

  /**
  * The success status of the webhook test
  */
  @Getter
  @Setter
  @JsonProperty("success")
  private Boolean success;

  /**
  * URL for testing the webhook.
  */
  @Getter
  @Setter
  @JsonProperty("url")
  private String url;

  /**
  * HTTP method(GET or POST).
  */
  @Getter
  @Setter
  @JsonProperty("method")
  private String method;

  /**
  * HTTP encoding method.  Can be JSON, XML, or RAW (form data).
  */
  @Getter
  @Setter
  @JsonProperty("encoding")
  private String encoding;

  /**
  * Additional request headers.
  */
  @Getter
  @Setter
  @JsonProperty("headers")
  private Map<String, String> headers;

  /**
  * Additional body parameters.
  */
  @Getter
  @Setter
  @JsonProperty("body")
  private Map<String, String> body;

  /**
  * action for test body
  */
  @Getter
  @Setter
  @JsonProperty("action")
  private String action;


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
  *   action - string - action for test body
  */
  public static WebhookTest create() throws IOException{
    return create(null,null);
  }
  public static WebhookTest create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static WebhookTest create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("url") && !(parameters.get("url") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: url must be of type String parameters[\"url\"]");
    }

    if (parameters.containsKey("method") && !(parameters.get("method") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: method must be of type String parameters[\"method\"]");
    }

    if (parameters.containsKey("encoding") && !(parameters.get("encoding") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: encoding must be of type String parameters[\"encoding\"]");
    }

    if (parameters.containsKey("headers") && !(parameters.get("headers") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: headers must be of type Map<String, String> parameters[\"headers\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: body must be of type Map<String, String> parameters[\"body\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (!parameters.containsKey("url") || parameters.get("url") == null) {
      throw new NullPointerException("Parameter missing: url parameters[\"url\"]");
    }
    String url = String.format("%s%s/webhook_tests", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<WebhookTest> typeReference = new TypeReference<WebhookTest>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}


