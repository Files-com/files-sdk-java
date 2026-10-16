package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.WebhookTest;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebhookTest operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#webhookTests()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.WebhookTest} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class WebhookTestOperations extends ResourceOperations {
  public WebhookTestOperations(FilesClient client) {
    super(client);
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
  public WebhookTest create() throws RuntimeException {
    return create(null, null);
  }

  public WebhookTest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public WebhookTest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("headers") && !(parameters.get("headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: headers must be of type Object parameters[\"headers\"]");
    }

    if (parameters.containsKey("body") && !(parameters.get("body") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: body must be of type Object parameters[\"body\"]");
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

    String apiPath = "/webhook_tests";
    TypeReference<WebhookTest> typeReference = new TypeReference<WebhookTest>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
