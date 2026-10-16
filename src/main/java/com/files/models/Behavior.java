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
public class Behavior implements ModelInterface {
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

  // One mapper for every Behavior, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public Behavior() {
    this(null, null);
  }

  public Behavior(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Behavior(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Folder behavior ID
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
   * Folder path.  Note that Behavior paths cannot be updated once initially set.  You will need to remove and re-create the behavior on the new path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
   * URL for attached file
   */
  @JsonProperty("attachment_url")
  public String attachmentUrl;

  public String getAttachmentUrl() {
    return attachmentUrl;
  }

  public void setAttachmentUrl(String attachmentUrl) {
    this.attachmentUrl = attachmentUrl;
  }

  /**
   * Behavior type.
   */
  @JsonProperty("behavior")
  public String behavior;

  public String getBehavior() {
    return behavior;
  }

  public void setBehavior(String behavior) {
    this.behavior = behavior;
  }

  /**
   * Name for this behavior.
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
   * Description for this behavior.
   */
  @JsonProperty("description")
  public String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Settings for this behavior.  See the section above for an example value to provide here.  Formatting is different for each Behavior type.  May be sent as nested JSON or a single JSON-encoded string.  If using XML encoding for the API call, this data must be sent as a JSON-encoded string.
   */
  @JsonProperty("value")
  public Object value;

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Public URL for this publicly hosted folder when the `Serve Publicly` behavior has a key configured. When a Custom Domain with `public_hosting` destination is attached to this behavior, the URL uses that domain. Otherwise it uses the site's `subdomain.hosted-by-files.com` host, unless `custom_domain_only` is enabled, in which case no URL is returned.
   */
  @JsonProperty("public_hosting_url")
  public String publicHostingUrl;

  public String getPublicHostingUrl() {
    return publicHostingUrl;
  }

  public void setPublicHostingUrl(String publicHostingUrl) {
    this.publicHostingUrl = publicHostingUrl;
  }

  /**
   * If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
   */
  @JsonProperty("disable_parent_folder_behavior")
  public Boolean disableParentFolderBehavior;

  public Boolean getDisableParentFolderBehavior() {
    return disableParentFolderBehavior;
  }

  public void setDisableParentFolderBehavior(Boolean disableParentFolderBehavior) {
    this.disableParentFolderBehavior = disableParentFolderBehavior;
  }

  /**
   * Whether this behavior is recursive for this record. `always` behaviors are always `true`, `never` behaviors are always `false`, and `sometimes` behaviors may be either value.
   */
  @JsonProperty("recursive")
  public Boolean recursive;

  public Boolean getRecursive() {
    return recursive;
  }

  public void setRecursive(Boolean recursive) {
    this.recursive = recursive;
  }

  /**
   * If true, this behavior is inherited from a higher scope rather than owned by the requested workspace.
   */
  @JsonProperty("inherited")
  public Boolean inherited;

  public Boolean getInherited() {
    return inherited;
  }

  public void setInherited(Boolean inherited) {
    this.inherited = inherited;
  }

  /**
   * If true, this behavior is controlled by a parent-site policy and cannot be modified locally.
   */
  @JsonProperty("managed")
  public Boolean managed;

  public Boolean getManaged() {
    return managed;
  }

  public void setManaged(Boolean managed) {
    this.managed = managed;
  }

  /**
   * If true, this behavior may only be modified by a site admin because it is at the site root or disables a root behavior.
   */
  @JsonProperty("root_behavior_site_admin_only")
  public Boolean rootBehaviorSiteAdminOnly;

  public Boolean getRootBehaviorSiteAdminOnly() {
    return rootBehaviorSiteAdminOnly;
  }

  public void setRootBehaviorSiteAdminOnly(Boolean rootBehaviorSiteAdminOnly) {
    this.rootBehaviorSiteAdminOnly = rootBehaviorSiteAdminOnly;
  }

  /**
   * Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
   */
  @JsonProperty("attachment_file")
  public byte[] attachmentFile;

  public byte[] getAttachmentFile() {
    return attachmentFile;
  }

  public void setAttachmentFile(byte[] attachmentFile) {
    this.attachmentFile = attachmentFile;
  }

  /**
   * If `true`, delete the file stored in `attachment`.
   */
  @JsonProperty("attachment_delete")
  public Boolean attachmentDelete;

  public Boolean getAttachmentDelete() {
    return attachmentDelete;
  }

  public void setAttachmentDelete(Boolean attachmentDelete) {
    this.attachmentDelete = attachmentDelete;
  }

  /**
   * Updates this Behavior. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   value - object - This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.
   *   attachment_file - file - Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
   *   disable_parent_folder_behavior - boolean - If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
   *   recursive - boolean - Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.
   *   name - string - Name for this behavior.
   *   description - string - Description for this behavior.
   *   attachment_delete - boolean - If `true`, delete the file stored in `attachment`.
   */
  public Behavior update(HashMap<String, Object> parameters) throws IOException {
    Behavior updated = filesClient().behaviors().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().behaviors().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Behavior: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().behaviors().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.attachmentFile != null) {
      parameters.put("attachment_file", this.attachmentFile);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Behavior canonical) {
    this.id = canonical.id;
    this.path = canonical.path;
    this.attachmentUrl = canonical.attachmentUrl;
    this.behavior = canonical.behavior;
    this.name = canonical.name;
    this.description = canonical.description;
    this.value = canonical.value;
    this.publicHostingUrl = canonical.publicHostingUrl;
    this.disableParentFolderBehavior = canonical.disableParentFolderBehavior;
    this.recursive = canonical.recursive;
    this.inherited = canonical.inherited;
    this.managed = canonical.managed;
    this.rootBehaviorSiteAdminOnly = canonical.rootBehaviorSiteAdminOnly;
    this.attachmentFile = canonical.attachmentFile;
    this.attachmentDelete = canonical.attachmentDelete;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `clickwrap_id`, `form_field_set_id`, `impacts_ui`, `remote_server_id` or `behavior`. Valid field combinations are `[ impacts_ui, behavior ]` and `[ behavior, remote_server_id ]`.
   */
  public static ListIterator<Behavior> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Behavior> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Behavior> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().behaviors().list(parameters, options);
  }

  public static ListIterator<Behavior> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Behavior> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Behavior ID.
   */
  public static Behavior find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Behavior find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Behavior find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Behavior find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().behaviors().find(id, parameters, options);
  }

  public static Behavior get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Behavior get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `behavior`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `impacts_ui` and `behavior`. Valid field combinations are `[ impacts_ui, behavior ]`.
   *   path (required) - string - Path to operate on.
   *   ancestor_behaviors - boolean - If `true`, behaviors above this path are shown.
   */
  public static ListIterator<Behavior> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  public static ListIterator<Behavior> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  public static ListIterator<Behavior> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  public static ListIterator<Behavior> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().behaviors().listFor(path, parameters, options);
  }


  /**
   * Parameters:
   *   value - object - This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.
   *   attachment_file - file - Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
   *   disable_parent_folder_behavior - boolean - If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
   *   recursive - boolean - Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.
   *   name - string - Name for this behavior.
   *   description - string - Description for this behavior.
   *   path (required) - string - Path where this behavior should apply.
   *   behavior (required) - string - Behavior type.
   */
  public static Behavior create() throws RuntimeException {
    return create(null, null);
  }

  public static Behavior create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Behavior create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().behaviors().create(parameters, options);
  }


  /**
   * Parameters:
   *   url (required) - string - URL for testing the webhook.
   *   method - string - HTTP request method (GET or POST).
   *   encoding - string - Encoding type for the webhook payload. Can be JSON, XML, or RAW (form data).
   *   headers - object - Additional request headers to send via HTTP.
   *   body - object - Additional body parameters to include in the webhook payload.
   *   action - string - Action for test body.
   */
  public static void webhookTest() throws RuntimeException {
    webhookTest(null, null);
  }

  public static void webhookTest(HashMap<String, Object> parameters) throws RuntimeException {
    webhookTest(parameters, null);
  }


  public static void webhookTest(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().behaviors().webhookTest(parameters, options);
  }


  /**
   * Parameters:
   *   value - object - This field stores data specific to the type of behavior. See The Behavior Types section for the accepted value for each type of behavior.
   *   attachment_file - file - Certain behaviors may require a file, for instance, the `watermark` behavior requires a watermark image. Attach that file here.
   *   disable_parent_folder_behavior - boolean - If true, disables the inherited behavior for this folder and its children. Valid only for behavior types that child folders may override, and requires recursive to be true. Rejected for all other behavior types.
   *   recursive - boolean - Whether the behavior should apply to child folders. This is only configurable for behavior types whose recursion mode is `sometimes`; `always` behaviors stay recursive and `never` behaviors stay non-recursive.
   *   name - string - Name for this behavior.
   *   description - string - Description for this behavior.
   *   attachment_delete - boolean - If `true`, delete the file stored in `attachment`.
   */
  public static Behavior update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Behavior update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Behavior update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Behavior update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().behaviors().update(id, parameters, options);
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
    FilesClient.defaultClient().behaviors().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
