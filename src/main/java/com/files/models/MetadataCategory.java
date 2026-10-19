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
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataCategory implements ModelInterface {
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

  // One mapper for every MetadataCategory, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code MetadataCategory} with no attributes set.
   */
  public MetadataCategory() {
    this(null, null);
  }

  /**
   * Creates a {@code MetadataCategory} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public MetadataCategory(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code MetadataCategory} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public MetadataCategory(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Metadata Category ID
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
   * Name of the metadata category.
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Map of key names to arrays of allowed values. An empty array means free-form text.
   */
  @JsonProperty("definitions")
  public Map<String, String[]> definitions;

  /**
   * Gets {@link #definitions}.
   *
   * @return the value of {@link #definitions}
   */
  public Map<String, String[]> getDefinitions() {
    return definitions;
  }

  /**
   * Sets {@link #definitions}.
   *
   * @param definitions the new value
   */
  public void setDefinitions(Map<String, String[]> definitions) {
    this.definitions = definitions;
  }

  /**
   * Metadata keys that should appear as columns in the UI by default.
   */
  @JsonProperty("default_columns")
  public String[] defaultColumns;

  /**
   * Gets {@link #defaultColumns}.
   *
   * @return the value of {@link #defaultColumns}
   */
  public String[] getDefaultColumns() {
    return defaultColumns;
  }

  /**
   * Sets {@link #defaultColumns}.
   *
   * @param defaultColumns the new value
   */
  public void setDefaultColumns(String[] defaultColumns) {
    this.defaultColumns = defaultColumns;
  }

  /**
   * Updates this MetadataCategory. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Metadata Category
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the metadata category.</li>
   * <li>{@code default_columns} (String[]): Metadata keys that should appear as columns in the UI by default.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code MetadataCategory}
   */
  public MetadataCategory update(HashMap<String, Object> parameters) throws IOException {
    MetadataCategory updated = filesClient().metadataCategories().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Metadata Category
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().metadataCategories().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this MetadataCategory: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().metadataCategories().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.defaultColumns != null) {
      parameters.put("default_columns", this.defaultColumns);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(MetadataCategory canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.definitions = canonical.definitions;
    this.defaultColumns = canonical.defaultColumns;
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Metadata Categories
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory} records, in a {@code ListIterator}
   */
  public static ListIterator<MetadataCategory> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().metadataCategories().list(parameters, options);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Metadata Categories
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static MetadataCategory find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static MetadataCategory find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static MetadataCategory find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Metadata Category
   *
   * @param id Metadata Category ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory}
   */
  public static MetadataCategory find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().metadataCategories().find(id, parameters, options);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static MetadataCategory get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Metadata Category
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static MetadataCategory get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * List Metadata Categories by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  /**
   * List Metadata Categories by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  /**
   * List Metadata Categories by Path
   *
   * @see #listFor(String, HashMap, HashMap)
   */
  public static ListIterator<MetadataCategory> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  /**
   * List Metadata Categories by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory} records, in a {@code ListIterator}
   */
  public static ListIterator<MetadataCategory> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().metadataCategories().listFor(path, parameters, options);
  }


  /**
   * Create Metadata Category
   *
   * @see #create(HashMap, HashMap)
   */
  public static MetadataCategory create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Metadata Category
   *
   * @see #create(HashMap, HashMap)
   */
  public static MetadataCategory create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Metadata Category
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name of the metadata category.</li>
   * <li>{@code default_columns} (String[]): Metadata keys that should appear as columns in the UI by default.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory}
   */
  public static MetadataCategory create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().metadataCategories().create(parameters, options);
  }


  /**
   * Update Metadata Category
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static MetadataCategory update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Metadata Category
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static MetadataCategory update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Metadata Category
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static MetadataCategory update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Metadata Category
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Name of the metadata category.</li>
   * <li>{@code default_columns} (String[]): Metadata keys that should appear as columns in the UI by default.</li>
   * </ul>
   *
   * @param id Metadata Category ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code MetadataCategory}
   */
  public static MetadataCategory update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().metadataCategories().update(id, parameters, options);
  }


  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Metadata Category
   *
   * @param id Metadata Category ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().metadataCategories().delete(id, parameters, options);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Metadata Category
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
