package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.WebhookTest;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;

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
   * Create Webhook Test
   *
   * @see #create(HashMap, HashMap)
   */
  public WebhookTest create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Webhook Test
   *
   * @see #create(HashMap, HashMap)
   */
  public WebhookTest create(HashMap<String, Object> parameters) throws RuntimeException {
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
