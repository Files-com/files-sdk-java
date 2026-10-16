package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Session;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Session operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#sessions()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Session} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class SessionOperations extends ResourceOperations {
  public SessionOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   username - string - Username to sign in as
   *   password - string - Password for sign in
   *   otp - string - If this user has a 2FA device, provide its OTP or code here.
   *   partial_session_id - string - Identifier for a partially-completed login
   */
  public Session create() throws RuntimeException {
    return create(null, null);
  }

  public Session create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public Session create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("username") && !(parameters.get("username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: username must be of type String parameters[\"username\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("otp") && !(parameters.get("otp") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: otp must be of type String parameters[\"otp\"]");
    }

    if (parameters.containsKey("partial_session_id") && !(parameters.get("partial_session_id") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: partial_session_id must be of type String parameters[\"partial_session_id\"]");
    }

    String apiPath = "/sessions";
    TypeReference<Session> typeReference = new TypeReference<Session>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public void delete() throws RuntimeException {
    delete(null, null);
  }

  public void delete(HashMap<String, Object> parameters) throws RuntimeException {
    delete(parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/sessions";
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null);
  }

  public void destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(parameters, options);
  }
}
