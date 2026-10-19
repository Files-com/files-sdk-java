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
import com.files.net.ClientRuntime;
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Style implements ModelInterface {
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

  // One mapper for every Style, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Style} with no attributes set.
   */
  public Style() {
    this(null, null);
  }

  /**
   * Creates a {@code Style} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Style(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Style} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Style(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Style ID
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
   * Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Logo
   */
  @JsonProperty("logo")
  public Image logo;

  /**
   * Gets {@link #logo}.
   *
   * @return the value of {@link #logo}
   */
  public Image getLogo() {
    return logo;
  }

  /**
   * Sets {@link #logo}.
   *
   * @param logo the new value
   */
  public void setLogo(Image logo) {
    this.logo = logo;
  }

  /**
   * URL to open when a public visitor clicks the logo
   */
  @JsonProperty("logo_click_href")
  public String logoClickHref;

  /**
   * Gets {@link #logoClickHref}.
   *
   * @return the value of {@link #logoClickHref}
   */
  public String getLogoClickHref() {
    return logoClickHref;
  }

  /**
   * Sets {@link #logoClickHref}.
   *
   * @param logoClickHref the new value
   */
  public void setLogoClickHref(String logoClickHref) {
    this.logoClickHref = logoClickHref;
  }

  /**
   * Logo thumbnail
   */
  @JsonProperty("thumbnail")
  public Image thumbnail;

  /**
   * Gets {@link #thumbnail}.
   *
   * @return the value of {@link #thumbnail}
   */
  public Image getThumbnail() {
    return thumbnail;
  }

  /**
   * Sets {@link #thumbnail}.
   *
   * @param thumbnail the new value
   */
  public void setThumbnail(Image thumbnail) {
    this.thumbnail = thumbnail;
  }

  /**
   * Logo for custom branding. Required when creating a new style.
   */
  @JsonProperty("file")
  public byte[] file;

  /**
   * Gets {@link #file}.
   *
   * @return the value of {@link #file}
   */
  public byte[] getFile() {
    return file;
  }

  /**
   * Sets {@link #file}.
   *
   * @param file the new value
   */
  public void setFile(byte[] file) {
    this.file = file;
  }

  /**
   * Updates this Style. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Style
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code file} (byte[] or Attachment): Logo for custom branding. Required when creating a new style.</li>
   * <li>{@code logo_click_href} (String): URL to open when a public visitor clicks the logo.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Style}
   */
  public Style update(HashMap<String, Object> parameters) throws IOException {
    Style updated = filesClient().styles().update(this.path, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Style
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().styles().delete(this.path, parameters, this.options);
  }

  /**
   * Delete Style
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Style by updating it with its current attributes.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    update(saveParameters());
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.file != null) {
      parameters.put("file", this.file);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Style canonical) {
    this.id = canonical.id;
    this.path = canonical.path;
    this.logo = canonical.logo;
    this.logoClickHref = canonical.logoClickHref;
    this.thumbnail = canonical.thumbnail;
    this.file = canonical.file;
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static Style find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static Style find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static Style find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Style find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().styles().find(path, parameters, options);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static Style get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Style
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static Style get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
   * Update Style
   *
   * @see #update(String, HashMap, HashMap)
   */
  public static Style update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Style
   *
   * @see #update(String, HashMap, HashMap)
   */
  public static Style update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  /**
   * Update Style
   *
   * @see #update(String, HashMap, HashMap)
   */
  public static Style update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Style update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().styles().update(path, parameters, options);
  }


  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Style
   *
   * @param path Style path.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().styles().delete(path, parameters, options);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Style
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

}
