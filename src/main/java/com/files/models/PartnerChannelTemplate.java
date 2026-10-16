package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerChannelTemplate implements ModelInterface {
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

  // One mapper for every PartnerChannelTemplate, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public PartnerChannelTemplate() {
    this(null, null);
  }

  public PartnerChannelTemplate(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public PartnerChannelTemplate(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The unique ID of the Partner Channel Template.
   */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * ID of the Workspace associated with this Partner Channel Template.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  public Long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   */
  @JsonProperty("direction")
  public String direction;

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  /**
   * Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Template must remain one-way.
   */
  @JsonProperty("use_channel_root")
  public Boolean useChannelRoot;

  public Boolean getUseChannelRoot() {
    return useChannelRoot;
  }

  public void setUseChannelRoot(Boolean useChannelRoot) {
    this.useChannelRoot = useChannelRoot;
  }

  /**
   * The name of the Partner Channel Template.
   */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Channel path relative to the Partner root folder. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Optional Channel-level to-Partner folder name override.
   */
  @JsonProperty("to_partner_folder_name")
  public String toPartnerFolderName;

  public String getToPartnerFolderName() {
    return toPartnerFolderName;
  }

  public void setToPartnerFolderName(String toPartnerFolderName) {
    this.toPartnerFolderName = toPartnerFolderName;
  }

  /**
   * Optional Channel-level from-Partner folder name override.
   */
  @JsonProperty("from_partner_folder_name")
  public String fromPartnerFolderName;

  public String getFromPartnerFolderName() {
    return fromPartnerFolderName;
  }

  public void setFromPartnerFolderName(String fromPartnerFolderName) {
    this.fromPartnerFolderName = fromPartnerFolderName;
  }

  /**
   * Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
   */
  @JsonProperty("from_partner_route_path_pattern")
  public String fromPartnerRoutePathPattern;

  public String getFromPartnerRoutePathPattern() {
    return fromPartnerRoutePathPattern;
  }

  public void setFromPartnerRoutePathPattern(String fromPartnerRoutePathPattern) {
    this.fromPartnerRoutePathPattern = fromPartnerRoutePathPattern;
  }

  /**
   * Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
   */
  @JsonProperty("to_partner_route_path_pattern")
  public String toPartnerRoutePathPattern;

  public String getToPartnerRoutePathPattern() {
    return toPartnerRoutePathPattern;
  }

  public void setToPartnerRoutePathPattern(String toPartnerRoutePathPattern) {
    this.toPartnerRoutePathPattern = toPartnerRoutePathPattern;
  }

  /**
   * Managed folder paths inside the to-Partner folder.
   */
  @JsonProperty("to_partner_managed_folder_paths")
  public String[] toPartnerManagedFolderPaths;

  public String[] getToPartnerManagedFolderPaths() {
    return toPartnerManagedFolderPaths;
  }

  public void setToPartnerManagedFolderPaths(String[] toPartnerManagedFolderPaths) {
    this.toPartnerManagedFolderPaths = toPartnerManagedFolderPaths;
  }

  /**
   * Managed folder paths inside the from-Partner folder.
   */
  @JsonProperty("from_partner_managed_folder_paths")
  public String[] fromPartnerManagedFolderPaths;

  public String[] getFromPartnerManagedFolderPaths() {
    return fromPartnerManagedFolderPaths;
  }

  public void setFromPartnerManagedFolderPaths(String[] fromPartnerManagedFolderPaths) {
    this.fromPartnerManagedFolderPaths = fromPartnerManagedFolderPaths;
  }

  /**
   * Resolved to-Partner subfolder name. Null when the direction is disabled or uses the Channel folder directly.
   */
  @JsonProperty("effective_to_partner_folder_name")
  public String effectiveToPartnerFolderName;

  public String getEffectiveToPartnerFolderName() {
    return effectiveToPartnerFolderName;
  }

  public void setEffectiveToPartnerFolderName(String effectiveToPartnerFolderName) {
    this.effectiveToPartnerFolderName = effectiveToPartnerFolderName;
  }

  /**
   * Resolved from-Partner subfolder name. Null when the direction is disabled or uses the Channel folder directly.
   */
  @JsonProperty("effective_from_partner_folder_name")
  public String effectiveFromPartnerFolderName;

  public String getEffectiveFromPartnerFolderName() {
    return effectiveFromPartnerFolderName;
  }

  public void setEffectiveFromPartnerFolderName(String effectiveFromPartnerFolderName) {
    this.effectiveFromPartnerFolderName = effectiveFromPartnerFolderName;
  }

  /**
   * Updates this PartnerChannelTemplate. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   direction - string - Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   *   use_channel_root - boolean - Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Template must remain one-way.
   *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
   *   from_partner_managed_folder_paths - array(string) - Managed folder paths inside the from-Partner folder.
   *   from_partner_route_path_pattern - string - Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
   *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
   *   to_partner_managed_folder_paths - array(string) - Managed folder paths inside the to-Partner folder.
   *   to_partner_route_path_pattern - string - Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
   *   name - string - The name of the Partner Channel Template.
   *   path - string - Channel path relative to the Partner root folder.
   */
  public PartnerChannelTemplate update(HashMap<String, Object> parameters) throws IOException {
    PartnerChannelTemplate updated = filesClient().partnerChannelTemplates().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().partnerChannelTemplates().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this PartnerChannelTemplate: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().partnerChannelTemplates().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.fromPartnerManagedFolderPaths != null) {
      parameters.put("from_partner_managed_folder_paths", this.fromPartnerManagedFolderPaths);
    }
    if (this.toPartnerManagedFolderPaths != null) {
      parameters.put("to_partner_managed_folder_paths", this.toPartnerManagedFolderPaths);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(PartnerChannelTemplate canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.direction = canonical.direction;
    this.useChannelRoot = canonical.useChannelRoot;
    this.name = canonical.name;
    this.path = canonical.path;
    this.toPartnerFolderName = canonical.toPartnerFolderName;
    this.fromPartnerFolderName = canonical.fromPartnerFolderName;
    this.fromPartnerRoutePathPattern = canonical.fromPartnerRoutePathPattern;
    this.toPartnerRoutePathPattern = canonical.toPartnerRoutePathPattern;
    this.toPartnerManagedFolderPaths = canonical.toPartnerManagedFolderPaths;
    this.fromPartnerManagedFolderPaths = canonical.fromPartnerManagedFolderPaths;
    this.effectiveToPartnerFolderName = canonical.effectiveToPartnerFolderName;
    this.effectiveFromPartnerFolderName = canonical.effectiveFromPartnerFolderName;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id` and `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `workspace_id`.
   */
  public static ListIterator<PartnerChannelTemplate> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<PartnerChannelTemplate> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<PartnerChannelTemplate> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannelTemplates().list(parameters, options);
  }

  public static ListIterator<PartnerChannelTemplate> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<PartnerChannelTemplate> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Partner Channel Template ID.
   */
  public static PartnerChannelTemplate find() throws RuntimeException {
    return find(null, null, null);
  }

  public static PartnerChannelTemplate find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static PartnerChannelTemplate find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static PartnerChannelTemplate find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannelTemplates().find(id, parameters, options);
  }

  public static PartnerChannelTemplate get() throws RuntimeException {
    return get(null, null, null);
  }

  public static PartnerChannelTemplate get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   direction - string - Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   *   use_channel_root - boolean - Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Template must remain one-way.
   *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
   *   from_partner_managed_folder_paths - array(string) - Managed folder paths inside the from-Partner folder.
   *   from_partner_route_path_pattern - string - Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
   *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
   *   to_partner_managed_folder_paths - array(string) - Managed folder paths inside the to-Partner folder.
   *   to_partner_route_path_pattern - string - Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
   *   name (required) - string - The name of the Partner Channel Template.
   *   path (required) - string - Channel path relative to the Partner root folder.
   *   workspace_id - int64 - ID of the Workspace associated with this Partner Channel Template.
   */
  public static PartnerChannelTemplate create() throws RuntimeException {
    return create(null, null);
  }

  public static PartnerChannelTemplate create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static PartnerChannelTemplate create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannelTemplates().create(parameters, options);
  }


  /**
   * Parameters:
   *   direction - string - Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   *   use_channel_root - boolean - Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Template must remain one-way.
   *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
   *   from_partner_managed_folder_paths - array(string) - Managed folder paths inside the from-Partner folder.
   *   from_partner_route_path_pattern - string - Optional route path pattern for files uploaded by the Partner. Supports {{partner_name}}.
   *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
   *   to_partner_managed_folder_paths - array(string) - Managed folder paths inside the to-Partner folder.
   *   to_partner_route_path_pattern - string - Optional route path pattern for files delivered to the Partner. Supports {{partner_name}}.
   *   name - string - The name of the Partner Channel Template.
   *   path - string - Channel path relative to the Partner root folder.
   */
  public static PartnerChannelTemplate update() throws RuntimeException {
    return update(null, null, null);
  }

  public static PartnerChannelTemplate update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static PartnerChannelTemplate update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static PartnerChannelTemplate update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannelTemplates().update(id, parameters, options);
  }


  /**
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerChannelTemplates().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
