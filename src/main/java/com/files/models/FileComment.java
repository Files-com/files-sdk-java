package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileComment implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every FileComment, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code FileComment} with no attributes set.
   */
  public FileComment() {
    this(null, null);
  }

  /**
   * Creates a {@code FileComment} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public FileComment(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code FileComment} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public FileComment(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * File Comment ID
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Comment body.
   */
  @JsonProperty("body")
  public String body;

  /**
   * Gets {@link #body}.
   *
   * @return the value of {@link #body}
   */
  public String getBody() {
    return body;
  }

  /**
   * Sets {@link #body}.
   *
   * @param body the new value
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Reactions to this comment.
   */
  @JsonProperty("reactions")
  public FileCommentReaction[] reactions;

  /**
   * Gets {@link #reactions}.
   *
   * @return the value of {@link #reactions}
   */
  public FileCommentReaction[] getReactions() {
    return reactions;
  }

  /**
   * Sets {@link #reactions}.
   *
   * @param reactions the new value
   */
  public void setReactions(FileCommentReaction[] reactions) {
    this.reactions = reactions;
  }

  /**
   * File path.
   */
  @JsonProperty("path")
  public String path;

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Updates this FileComment. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update File Comment
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code body} (String): Required. Comment body.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileComment}
   */
  public FileComment update(HashMap<String, Object> parameters) throws IOException {
    FileComment updated = filesClient().fileComments().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete File Comment
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().fileComments().delete(this.id, parameters, this.options);
  }

  /**
   * Delete File Comment
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this FileComment: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().fileComments().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(FileComment canonical) {
    this.id = canonical.id;
    this.body = canonical.body;
    this.reactions = canonical.reactions;
    this.path = canonical.path;
  }

  /**
   * List File Comments by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<FileComment> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  /**
   * List File Comments by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<FileComment> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  /**
   * List File Comments by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<FileComment> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  /**
   * List File Comments by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileComment} records, in a {@code ListIterator}
   */
  public static ListIterator<FileComment> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().fileComments().listFor(path, parameters, options);
  }


  /**
   * Create File Comment
   *
   * @see #create(HashMap, HashMap)
   */
  public static FileComment create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create File Comment
   *
   * @see #create(HashMap, HashMap)
   */
  public static FileComment create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create File Comment
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code body} (String): Required. Comment body.</li>
   * <li>{@code path} (String): Required. File path.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileComment}
   */
  public static FileComment create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().fileComments().create(parameters, options);
  }


  /**
   * Update File Comment
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static FileComment update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update File Comment
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static FileComment update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update File Comment
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static FileComment update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update File Comment
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code body} (String): Required. Comment body.</li>
   * </ul>
   *
   * @param id File Comment ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileComment}
   */
  public static FileComment update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().fileComments().update(id, parameters, options);
  }


  /**
   * Delete File Comment
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete File Comment
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete File Comment
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete File Comment
   *
   * @param id File Comment ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().fileComments().delete(id, parameters, options);
  }

  /**
   * Delete File Comment
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete File Comment
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
