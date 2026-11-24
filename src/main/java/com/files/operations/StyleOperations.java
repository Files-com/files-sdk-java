package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.Style;
import com.files.net.Attachment;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;

/**
 * Style operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#styles()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Style} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class StyleOperations extends ResourceOperations {
  public StyleOperations(FilesClient client) {
    super(client);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public Style find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public Style find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public Style find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Style
   *
   * @param path Style path.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Style}
   */
  public Style find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/styles/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<Style> typeReference = new TypeReference<Style>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public Style get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public Style get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
   * Update Style
   *
   * @see #update(String, HashMap, HashMap)
   */
  public Style update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Style
   *
   * @see #update(String, HashMap, HashMap)
   */
  public Style update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  /**
   * Update Style
   *
   * @see #update(String, HashMap, HashMap)
   */
  public Style update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Style
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code file} (byte[] or Attachment): Logo for custom branding. Required when creating a new style.</li>
   * <li>{@code logo_click_href} (String): URL to open when a public visitor clicks the logo.</li>
   * </ul>
   *
   * @param path Style path.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Style}
   */
  public Style update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("file") && !(parameters.get("file") instanceof byte[] || parameters.get("file") instanceof Attachment)) {
      throw new IllegalArgumentException("Bad parameter: file must be of type byte[] or Attachment parameters[\"file\"]");
    }

    if (parameters.containsKey("logo_click_href") && !(parameters.get("logo_click_href") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: logo_click_href must be of type String parameters[\"logo_click_href\"]");
    }

    // A byte[] for a file parameter is sent as the file's content.
    if (parameters.get("file") instanceof byte[]) {
      parameters.put("file", Attachment.fromBytes((byte[]) parameters.get("file")));
    }

    String apiPath = String.format("/styles/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<Style> typeReference = new TypeReference<Style>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Style
   *
   * @param path Style path.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/styles/%s", UrlUtils.encodeUrlPath(path));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }
}
