package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ResourceOperations;
import com.files.models.FileCommentReaction;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;

/**
 * FileCommentReaction operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#fileCommentReactions()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.FileCommentReaction} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class FileCommentReactionOperations extends ResourceOperations {
  public FileCommentReactionOperations(FilesClient client) {
    super(client);
  }

  /**
   * Create File Comment Reaction
   *
   * @see #create(HashMap, HashMap)
   */
  public FileCommentReaction create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create File Comment Reaction
   *
   * @see #create(HashMap, HashMap)
   */
  public FileCommentReaction create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create File Comment Reaction
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code file_comment_id} (Long): Required. ID of file comment to attach reaction to.</li>
   * <li>{@code emoji} (String): Required. Emoji to react with.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileCommentReaction}
   */
  public FileCommentReaction create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("file_comment_id") || parameters.get("file_comment_id") == null) {
      throw new NullPointerException("Parameter missing: file_comment_id parameters[\"file_comment_id\"]");
    }

    if (!parameters.containsKey("emoji") || parameters.get("emoji") == null) {
      throw new NullPointerException("Parameter missing: emoji parameters[\"emoji\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }

    if (parameters.containsKey("file_comment_id") && !(parameters.get("file_comment_id") instanceof Long || parameters.get("file_comment_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: file_comment_id must be of type Long or Integer parameters[\"file_comment_id\"]");
    }

    if (parameters.containsKey("emoji") && !(parameters.get("emoji") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: emoji must be of type String parameters[\"emoji\"]");
    }

    String apiPath = "/file_comment_reactions";
    TypeReference<FileCommentReaction> typeReference = new TypeReference<FileCommentReaction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete File Comment Reaction
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete File Comment Reaction
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete File Comment Reaction
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete File Comment Reaction
   *
   * @param id File Comment Reaction ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/file_comment_reactions/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete File Comment Reaction
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete File Comment Reaction
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
