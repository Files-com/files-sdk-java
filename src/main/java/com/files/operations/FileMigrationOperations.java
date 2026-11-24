package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.FileMigration;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;

/**
 * FileMigration operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#fileMigrations()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.FileMigration} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class FileMigrationOperations extends ResourceOperations {
  public FileMigrationOperations(FilesClient client) {
    super(client);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public FileMigration find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public FileMigration find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public FileMigration find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show File Migration
   *
   * @param id File Migration ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileMigration}
   */
  public FileMigration find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/file_migrations/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<FileMigration> typeReference = new TypeReference<FileMigration>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public FileMigration get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show File Migration
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public FileMigration get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }
}
