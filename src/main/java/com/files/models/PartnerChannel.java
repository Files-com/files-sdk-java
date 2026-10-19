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
public class PartnerChannel implements ModelInterface {
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

  // One mapper for every PartnerChannel, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code PartnerChannel} with no attributes set.
   */
  public PartnerChannel() {
    this(null, null);
  }

  /**
   * Creates a {@code PartnerChannel} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public PartnerChannel(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code PartnerChannel} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public PartnerChannel(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The unique ID of the Partner Channel.
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
   * ID of the Workspace associated with this Partner Channel.
   */
  @JsonProperty("workspace_id")
  public Long workspaceId;

  /**
   * Gets {@link #workspaceId}.
   *
   * @return the value of {@link #workspaceId}
   */
  public Long getWorkspaceId() {
    return workspaceId;
  }

  /**
   * Sets {@link #workspaceId}.
   *
   * @param workspaceId the new value
   */
  public void setWorkspaceId(Long workspaceId) {
    this.workspaceId = workspaceId;
  }

  /**
   * Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.
   */
  @JsonProperty("direction")
  public String direction;

  /**
   * Gets {@link #direction}.
   *
   * @return the value of {@link #direction}
   */
  public String getDirection() {
    return direction;
  }

  /**
   * Sets {@link #direction}.
   *
   * @param direction the new value
   */
  public void setDirection(String direction) {
    this.direction = direction;
  }

  /**
   * Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.
   */
  @JsonProperty("use_channel_root")
  public Boolean useChannelRoot;

  /**
   * Gets {@link #useChannelRoot}.
   *
   * @return the value of {@link #useChannelRoot}
   */
  public Boolean getUseChannelRoot() {
    return useChannelRoot;
  }

  /**
   * Sets {@link #useChannelRoot}.
   *
   * @param useChannelRoot the new value
   */
  public void setUseChannelRoot(Boolean useChannelRoot) {
    this.useChannelRoot = useChannelRoot;
  }

  /**
   * ID of the Partner this Channel belongs to.
   */
  @JsonProperty("partner_id")
  public Long partnerId;

  /**
   * Gets {@link #partnerId}.
   *
   * @return the value of {@link #partnerId}
   */
  public Long getPartnerId() {
    return partnerId;
  }

  /**
   * Sets {@link #partnerId}.
   *
   * @param partnerId the new value
   */
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * ID of the Partner Channel Template that manages this Channel, if any.
   */
  @JsonProperty("partner_channel_template_id")
  public Long partnerChannelTemplateId;

  /**
   * Gets {@link #partnerChannelTemplateId}.
   *
   * @return the value of {@link #partnerChannelTemplateId}
   */
  public Long getPartnerChannelTemplateId() {
    return partnerChannelTemplateId;
  }

  /**
   * Sets {@link #partnerChannelTemplateId}.
   *
   * @param partnerChannelTemplateId the new value
   */
  public void setPartnerChannelTemplateId(Long partnerChannelTemplateId) {
    this.partnerChannelTemplateId = partnerChannelTemplateId;
  }

  /**
   * Channel path relative to the Partner root folder. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * Optional Channel-level to-Partner folder name override.
   */
  @JsonProperty("to_partner_folder_name")
  public String toPartnerFolderName;

  /**
   * Gets {@link #toPartnerFolderName}.
   *
   * @return the value of {@link #toPartnerFolderName}
   */
  public String getToPartnerFolderName() {
    return toPartnerFolderName;
  }

  /**
   * Sets {@link #toPartnerFolderName}.
   *
   * @param toPartnerFolderName the new value
   */
  public void setToPartnerFolderName(String toPartnerFolderName) {
    this.toPartnerFolderName = toPartnerFolderName;
  }

  /**
   * Optional Channel-level from-Partner folder name override.
   */
  @JsonProperty("from_partner_folder_name")
  public String fromPartnerFolderName;

  /**
   * Gets {@link #fromPartnerFolderName}.
   *
   * @return the value of {@link #fromPartnerFolderName}
   */
  public String getFromPartnerFolderName() {
    return fromPartnerFolderName;
  }

  /**
   * Sets {@link #fromPartnerFolderName}.
   *
   * @param fromPartnerFolderName the new value
   */
  public void setFromPartnerFolderName(String fromPartnerFolderName) {
    this.fromPartnerFolderName = fromPartnerFolderName;
  }

  /**
   * Optional route path for files uploaded by the Partner.
   */
  @JsonProperty("from_partner_route_path")
  public String fromPartnerRoutePath;

  /**
   * Gets {@link #fromPartnerRoutePath}.
   *
   * @return the value of {@link #fromPartnerRoutePath}
   */
  public String getFromPartnerRoutePath() {
    return fromPartnerRoutePath;
  }

  /**
   * Sets {@link #fromPartnerRoutePath}.
   *
   * @param fromPartnerRoutePath the new value
   */
  public void setFromPartnerRoutePath(String fromPartnerRoutePath) {
    this.fromPartnerRoutePath = fromPartnerRoutePath;
  }

  /**
   * Optional route path for files delivered to the Partner.
   */
  @JsonProperty("to_partner_route_path")
  public String toPartnerRoutePath;

  /**
   * Gets {@link #toPartnerRoutePath}.
   *
   * @return the value of {@link #toPartnerRoutePath}
   */
  public String getToPartnerRoutePath() {
    return toPartnerRoutePath;
  }

  /**
   * Sets {@link #toPartnerRoutePath}.
   *
   * @param toPartnerRoutePath the new value
   */
  public void setToPartnerRoutePath(String toPartnerRoutePath) {
    this.toPartnerRoutePath = toPartnerRoutePath;
  }

  /**
   * Managed folder paths inside the to-Partner folder.
   */
  @JsonProperty("to_partner_managed_folder_paths")
  public String[] toPartnerManagedFolderPaths;

  /**
   * Gets {@link #toPartnerManagedFolderPaths}.
   *
   * @return the value of {@link #toPartnerManagedFolderPaths}
   */
  public String[] getToPartnerManagedFolderPaths() {
    return toPartnerManagedFolderPaths;
  }

  /**
   * Sets {@link #toPartnerManagedFolderPaths}.
   *
   * @param toPartnerManagedFolderPaths the new value
   */
  public void setToPartnerManagedFolderPaths(String[] toPartnerManagedFolderPaths) {
    this.toPartnerManagedFolderPaths = toPartnerManagedFolderPaths;
  }

  /**
   * Managed folder paths inside the from-Partner folder.
   */
  @JsonProperty("from_partner_managed_folder_paths")
  public String[] fromPartnerManagedFolderPaths;

  /**
   * Gets {@link #fromPartnerManagedFolderPaths}.
   *
   * @return the value of {@link #fromPartnerManagedFolderPaths}
   */
  public String[] getFromPartnerManagedFolderPaths() {
    return fromPartnerManagedFolderPaths;
  }

  /**
   * Sets {@link #fromPartnerManagedFolderPaths}.
   *
   * @param fromPartnerManagedFolderPaths the new value
   */
  public void setFromPartnerManagedFolderPaths(String[] fromPartnerManagedFolderPaths) {
    this.fromPartnerManagedFolderPaths = fromPartnerManagedFolderPaths;
  }

  /**
   * Resolved to-Partner subfolder name. Null when the direction is disabled or uses the Channel folder directly.
   */
  @JsonProperty("effective_to_partner_folder_name")
  public String effectiveToPartnerFolderName;

  /**
   * Gets {@link #effectiveToPartnerFolderName}.
   *
   * @return the value of {@link #effectiveToPartnerFolderName}
   */
  public String getEffectiveToPartnerFolderName() {
    return effectiveToPartnerFolderName;
  }

  /**
   * Sets {@link #effectiveToPartnerFolderName}.
   *
   * @param effectiveToPartnerFolderName the new value
   */
  public void setEffectiveToPartnerFolderName(String effectiveToPartnerFolderName) {
    this.effectiveToPartnerFolderName = effectiveToPartnerFolderName;
  }

  /**
   * Resolved from-Partner subfolder name. Null when the direction is disabled or uses the Channel folder directly.
   */
  @JsonProperty("effective_from_partner_folder_name")
  public String effectiveFromPartnerFolderName;

  /**
   * Gets {@link #effectiveFromPartnerFolderName}.
   *
   * @return the value of {@link #effectiveFromPartnerFolderName}
   */
  public String getEffectiveFromPartnerFolderName() {
    return effectiveFromPartnerFolderName;
  }

  /**
   * Sets {@link #effectiveFromPartnerFolderName}.
   *
   * @param effectiveFromPartnerFolderName the new value
   */
  public void setEffectiveFromPartnerFolderName(String effectiveFromPartnerFolderName) {
    this.effectiveFromPartnerFolderName = effectiveFromPartnerFolderName;
  }

  /**
   * Resolved Channel folder path.
   */
  @JsonProperty("channel_path")
  public String channelPath;

  /**
   * Gets {@link #channelPath}.
   *
   * @return the value of {@link #channelPath}
   */
  public String getChannelPath() {
    return channelPath;
  }

  /**
   * Sets {@link #channelPath}.
   *
   * @param channelPath the new value
   */
  public void setChannelPath(String channelPath) {
    this.channelPath = channelPath;
  }

  /**
   * Resolved to-Partner folder path.
   */
  @JsonProperty("to_partner_folder_path")
  public String toPartnerFolderPath;

  /**
   * Gets {@link #toPartnerFolderPath}.
   *
   * @return the value of {@link #toPartnerFolderPath}
   */
  public String getToPartnerFolderPath() {
    return toPartnerFolderPath;
  }

  /**
   * Sets {@link #toPartnerFolderPath}.
   *
   * @param toPartnerFolderPath the new value
   */
  public void setToPartnerFolderPath(String toPartnerFolderPath) {
    this.toPartnerFolderPath = toPartnerFolderPath;
  }

  /**
   * Resolved from-Partner folder path.
   */
  @JsonProperty("from_partner_folder_path")
  public String fromPartnerFolderPath;

  /**
   * Gets {@link #fromPartnerFolderPath}.
   *
   * @return the value of {@link #fromPartnerFolderPath}
   */
  public String getFromPartnerFolderPath() {
    return fromPartnerFolderPath;
  }

  /**
   * Sets {@link #fromPartnerFolderPath}.
   *
   * @param fromPartnerFolderPath the new value
   */
  public void setFromPartnerFolderPath(String fromPartnerFolderPath) {
    this.fromPartnerFolderPath = fromPartnerFolderPath;
  }

  /**
   * Updates this PartnerChannel. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Partner Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code direction} (String): Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.</li>
   * <li>{@code use_channel_root} (Boolean): Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.</li>
   * <li>{@code from_partner_folder_name} (String): Optional Channel-level from-Partner folder name override.</li>
   * <li>{@code from_partner_managed_folder_paths} (String[]): Managed folder paths inside the from-Partner folder.</li>
   * <li>{@code from_partner_route_path} (String): Optional route path for files uploaded by the Partner.</li>
   * <li>{@code to_partner_folder_name} (String): Optional Channel-level to-Partner folder name override.</li>
   * <li>{@code to_partner_managed_folder_paths} (String[]): Managed folder paths inside the to-Partner folder.</li>
   * <li>{@code to_partner_route_path} (String): Optional route path for files delivered to the Partner.</li>
   * <li>{@code path} (String): Channel path relative to the Partner root folder.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code PartnerChannel}
   */
  public PartnerChannel update(HashMap<String, Object> parameters) throws IOException {
    PartnerChannel updated = filesClient().partnerChannels().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Partner Channel
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().partnerChannels().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this PartnerChannel: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().partnerChannels().create(parameters, this.options));
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
  private void refreshFrom(PartnerChannel canonical) {
    this.id = canonical.id;
    this.workspaceId = canonical.workspaceId;
    this.direction = canonical.direction;
    this.useChannelRoot = canonical.useChannelRoot;
    this.partnerId = canonical.partnerId;
    this.partnerChannelTemplateId = canonical.partnerChannelTemplateId;
    this.path = canonical.path;
    this.toPartnerFolderName = canonical.toPartnerFolderName;
    this.fromPartnerFolderName = canonical.fromPartnerFolderName;
    this.fromPartnerRoutePath = canonical.fromPartnerRoutePath;
    this.toPartnerRoutePath = canonical.toPartnerRoutePath;
    this.toPartnerManagedFolderPaths = canonical.toPartnerManagedFolderPaths;
    this.fromPartnerManagedFolderPaths = canonical.fromPartnerManagedFolderPaths;
    this.effectiveToPartnerFolderName = canonical.effectiveToPartnerFolderName;
    this.effectiveFromPartnerFolderName = canonical.effectiveFromPartnerFolderName;
    this.channelPath = canonical.channelPath;
    this.toPartnerFolderPath = canonical.toPartnerFolderPath;
    this.fromPartnerFolderPath = canonical.fromPartnerFolderPath;
  }

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerChannel> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerChannel> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Partner Channels
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path` or `partner_id`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `partner_id` and `workspace_id`. Valid field combinations are `[ workspace_id, partner_id ]`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel} records, in a {@code ListIterator}
   */
  public static ListIterator<PartnerChannel> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannels().list(parameters, options);
  }

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerChannel> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Partner Channels
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<PartnerChannel> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PartnerChannel find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PartnerChannel find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PartnerChannel find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Partner Channel
   *
   * @param id Partner Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel}
   */
  public static PartnerChannel find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannels().find(id, parameters, options);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PartnerChannel get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Partner Channel
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static PartnerChannel get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Partner Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public static PartnerChannel create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Partner Channel
   *
   * @see #create(HashMap, HashMap)
   */
  public static PartnerChannel create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Partner Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code direction} (String): Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.</li>
   * <li>{@code use_channel_root} (Boolean): Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.</li>
   * <li>{@code from_partner_folder_name} (String): Optional Channel-level from-Partner folder name override.</li>
   * <li>{@code from_partner_managed_folder_paths} (String[]): Managed folder paths inside the from-Partner folder.</li>
   * <li>{@code from_partner_route_path} (String): Optional route path for files uploaded by the Partner.</li>
   * <li>{@code to_partner_folder_name} (String): Optional Channel-level to-Partner folder name override.</li>
   * <li>{@code to_partner_managed_folder_paths} (String[]): Managed folder paths inside the to-Partner folder.</li>
   * <li>{@code to_partner_route_path} (String): Optional route path for files delivered to the Partner.</li>
   * <li>{@code partner_id} (Long): Required. ID of the Partner this Channel belongs to.</li>
   * <li>{@code path} (String): Required. Channel path relative to the Partner root folder.</li>
   * <li>{@code workspace_id} (Long): ID of the Workspace associated with this Partner Channel.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel}
   */
  public static PartnerChannel create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannels().create(parameters, options);
  }


  /**
   * Update Partner Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static PartnerChannel update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Partner Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static PartnerChannel update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Partner Channel
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static PartnerChannel update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Partner Channel
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code direction} (String): Channel directions. `two_way` enables both directions, `to_partner` enables outgoing downloads, and `from_partner` enables incoming uploads.</li>
   * <li>{@code use_channel_root} (Boolean): Use the Channel folder directly for a one-way exchange. Defaults to false. Cannot be changed after creation. Folder name overrides must be blank when enabled, and the Channel must remain one-way.</li>
   * <li>{@code from_partner_folder_name} (String): Optional Channel-level from-Partner folder name override.</li>
   * <li>{@code from_partner_managed_folder_paths} (String[]): Managed folder paths inside the from-Partner folder.</li>
   * <li>{@code from_partner_route_path} (String): Optional route path for files uploaded by the Partner.</li>
   * <li>{@code to_partner_folder_name} (String): Optional Channel-level to-Partner folder name override.</li>
   * <li>{@code to_partner_managed_folder_paths} (String[]): Managed folder paths inside the to-Partner folder.</li>
   * <li>{@code to_partner_route_path} (String): Optional route path for files delivered to the Partner.</li>
   * <li>{@code path} (String): Channel path relative to the Partner root folder.</li>
   * </ul>
   *
   * @param id Partner Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code PartnerChannel}
   */
  public static PartnerChannel update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().partnerChannels().update(id, parameters, options);
  }


  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Partner Channel
   *
   * @param id Partner Channel ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().partnerChannels().delete(id, parameters, options);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Partner Channel
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
