package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.Session;
import com.files.net.HttpMethods.RequestMethods;
import java.util.HashMap;

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
   * Create user session (log in)
   *
   * @see #create(HashMap, HashMap)
   */
  public Session create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create user session (log in)
   *
   * @see #create(HashMap, HashMap)
   */
  public Session create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create user session (log in)
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code username} (String): Username to sign in as</li>
   * <li>{@code password} (String): Password for sign in</li>
   * <li>{@code otp} (String): If this user has a 2FA device, provide its OTP or code here.</li>
   * <li>{@code partial_session_id} (String): Identifier for a partially-completed login</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Session}
   */
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
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null);
  }

  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters) throws RuntimeException {
    delete(parameters, null);
  }

  /**
   * Delete user session (log out)
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    String apiPath = "/sessions";
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null);
  }

  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public void destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(parameters, options);
  }
}
