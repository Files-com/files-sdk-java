package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.FileMigration;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * Parameters:
   *   id (required) - int64 - File Migration ID.
   */
  public FileMigration find() throws RuntimeException {
    return find(null, null, null);
  }

  public FileMigration find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public FileMigration find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

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

  public FileMigration get() throws RuntimeException {
    return get(null, null, null);
  }

  public FileMigration get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }
}
