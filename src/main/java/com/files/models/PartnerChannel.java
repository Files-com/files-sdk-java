package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
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
public class PartnerChannel implements ModelInterface {
  private HashMap<String, Object> options;

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public PartnerChannel() {
    this(null, null);
  }

  public PartnerChannel(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public PartnerChannel(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * The unique ID of the Partner Channel.
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
  * ID of the Workspace associated with this Partner Channel.
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
  * ID of the Partner this Channel belongs to.
  */
  @JsonProperty("partner_id")
  public Long partnerId;

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
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
  * Optional route path for files uploaded by the Partner.
  */
  @JsonProperty("from_partner_route_path")
  public String fromPartnerRoutePath;

  public String getFromPartnerRoutePath() {
    return fromPartnerRoutePath;
  }

  public void setFromPartnerRoutePath(String fromPartnerRoutePath) {
    this.fromPartnerRoutePath = fromPartnerRoutePath;
  }

  /**
  * Optional route path for files delivered to the Partner.
  */
  @JsonProperty("to_partner_route_path")
  public String toPartnerRoutePath;

  public String getToPartnerRoutePath() {
    return toPartnerRoutePath;
  }

  public void setToPartnerRoutePath(String toPartnerRoutePath) {
    this.toPartnerRoutePath = toPartnerRoutePath;
  }

  /**
  * Resolved to-Partner folder name after Channel override and default.
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
  * Resolved from-Partner folder name after Channel override and default.
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
  * Resolved Channel folder path.
  */
  @JsonProperty("channel_path")
  public String channelPath;

  public String getChannelPath() {
    return channelPath;
  }

  public void setChannelPath(String channelPath) {
    this.channelPath = channelPath;
  }

  /**
  * Resolved to-Partner folder path.
  */
  @JsonProperty("to_partner_folder_path")
  public String toPartnerFolderPath;

  public String getToPartnerFolderPath() {
    return toPartnerFolderPath;
  }

  public void setToPartnerFolderPath(String toPartnerFolderPath) {
    this.toPartnerFolderPath = toPartnerFolderPath;
  }

  /**
  * Resolved from-Partner folder path.
  */
  @JsonProperty("from_partner_folder_path")
  public String fromPartnerFolderPath;

  public String getFromPartnerFolderPath() {
    return fromPartnerFolderPath;
  }

  public void setFromPartnerFolderPath(String fromPartnerFolderPath) {
    this.fromPartnerFolderPath = fromPartnerFolderPath;
  }

  /**
  * Parameters:
  *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
  *   from_partner_route_path - string - Optional route path for files uploaded by the Partner.
  *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
  *   to_partner_route_path - string - Optional route path for files delivered to the Partner.
  *   path - string - Channel path relative to the Partner root folder.
  */
  public PartnerChannel update(HashMap<String, Object> parameters) throws IOException {
    return PartnerChannel.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    PartnerChannel.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    PartnerChannel.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path` or `partner_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `partner_id` and `workspace_id`. Valid field combinations are `[ workspace_id, partner_id ]`.
  */
  public static ListIterator<PartnerChannel> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<PartnerChannel> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<PartnerChannel> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }


    String url = String.format("%s%s/partner_channels", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<PartnerChannel>> typeReference = new TypeReference<List<PartnerChannel>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<PartnerChannel> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<PartnerChannel> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Partner Channel ID.
  */
  public static PartnerChannel find() throws RuntimeException {
    return find(null, null, null);
  }

  public static PartnerChannel find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static PartnerChannel find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static PartnerChannel find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/partner_channels/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<PartnerChannel> typeReference = new TypeReference<PartnerChannel>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static PartnerChannel get() throws RuntimeException {
    return get(null, null, null);
  }

  public static PartnerChannel get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
  *   from_partner_route_path - string - Optional route path for files uploaded by the Partner.
  *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
  *   to_partner_route_path - string - Optional route path for files delivered to the Partner.
  *   partner_id (required) - int64 - ID of the Partner this Channel belongs to.
  *   path (required) - string - Channel path relative to the Partner root folder.
  *   workspace_id - int64 - ID of the Workspace associated with this Partner Channel.
  */
  public static PartnerChannel create() throws RuntimeException {
    return create(null, null);
  }

  public static PartnerChannel create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static PartnerChannel create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("partner_id") || parameters.get("partner_id") == null) {
      throw new NullPointerException("Parameter missing: partner_id parameters[\"partner_id\"]");
    }
    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("from_partner_folder_name") && !(parameters.get("from_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_folder_name must be of type String parameters[\"from_partner_folder_name\"]");
    }
    if (parameters.containsKey("from_partner_route_path") && !(parameters.get("from_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_route_path must be of type String parameters[\"from_partner_route_path\"]");
    }
    if (parameters.containsKey("to_partner_folder_name") && !(parameters.get("to_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_folder_name must be of type String parameters[\"to_partner_folder_name\"]");
    }
    if (parameters.containsKey("to_partner_route_path") && !(parameters.get("to_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_route_path must be of type String parameters[\"to_partner_route_path\"]");
    }
    if (parameters.containsKey("partner_id") && !(parameters.get("partner_id") instanceof Long || parameters.get("partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: partner_id must be of type Long or Integer parameters[\"partner_id\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("workspace_id") && !(parameters.get("workspace_id") instanceof Long || parameters.get("workspace_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: workspace_id must be of type Long or Integer parameters[\"workspace_id\"]");
    }


    String url = String.format("%s%s/partner_channels", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<PartnerChannel> typeReference = new TypeReference<PartnerChannel>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   from_partner_folder_name - string - Optional Channel-level from-Partner folder name override.
  *   from_partner_route_path - string - Optional route path for files uploaded by the Partner.
  *   to_partner_folder_name - string - Optional Channel-level to-Partner folder name override.
  *   to_partner_route_path - string - Optional route path for files delivered to the Partner.
  *   path - string - Channel path relative to the Partner root folder.
  */
  public static PartnerChannel update() throws RuntimeException {
    return update(null, null, null);
  }

  public static PartnerChannel update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static PartnerChannel update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static PartnerChannel update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("from_partner_folder_name") && !(parameters.get("from_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_folder_name must be of type String parameters[\"from_partner_folder_name\"]");
    }
    if (parameters.containsKey("from_partner_route_path") && !(parameters.get("from_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: from_partner_route_path must be of type String parameters[\"from_partner_route_path\"]");
    }
    if (parameters.containsKey("to_partner_folder_name") && !(parameters.get("to_partner_folder_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_folder_name must be of type String parameters[\"to_partner_folder_name\"]");
    }
    if (parameters.containsKey("to_partner_route_path") && !(parameters.get("to_partner_route_path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: to_partner_route_path must be of type String parameters[\"to_partner_route_path\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }



    String url = String.format("%s%s/partner_channels/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<PartnerChannel> typeReference = new TypeReference<PartnerChannel>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/partner_channels/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
