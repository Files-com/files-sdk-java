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
public class ChildSiteManagementPolicy implements ModelInterface {
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

  // One mapper for every ChildSiteManagementPolicy, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  public ChildSiteManagementPolicy() {
    this(null, null);
  }

  public ChildSiteManagementPolicy(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ChildSiteManagementPolicy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Policy ID.
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
   * Type of policy.  Valid values: `settings`.
   */
  @JsonProperty("policy_type")
  public String policyType;

  public String getPolicyType() {
    return policyType;
  }

  public void setPolicyType(String policyType) {
    this.policyType = policyType;
  }

  /**
   * Name for this policy.
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
   * Description for this policy.
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
   * Policy configuration data. On a Child Site, contains only managed setting names with null values. Settings policies accept site settings plus an optional `folder_behaviors` array for parent-managed root behaviors on child sites. For more information, refer to the Value Hash section of the developer documentation.
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
   * IDs of child sites that this policy has been applied to. This field is read-only.
   */
  @JsonProperty("applied_child_site_ids")
  public Long[] appliedChildSiteIds;

  public Long[] getAppliedChildSiteIds() {
    return appliedChildSiteIds;
  }

  public void setAppliedChildSiteIds(Long[] appliedChildSiteIds) {
    this.appliedChildSiteIds = appliedChildSiteIds;
  }

  /**
   * IDs of child sites excluded from this default policy.
   */
  @JsonProperty("skip_child_site_ids")
  public Long[] skipChildSiteIds;

  public Long[] getSkipChildSiteIds() {
    return skipChildSiteIds;
  }

  public void setSkipChildSiteIds(Long[] skipChildSiteIds) {
    this.skipChildSiteIds = skipChildSiteIds;
  }

  /**
   * IDs of child sites explicitly assigned to this non-default policy.
   */
  @JsonProperty("child_site_ids")
  public Long[] childSiteIds;

  public Long[] getChildSiteIds() {
    return childSiteIds;
  }

  public void setChildSiteIds(Long[] childSiteIds) {
    this.childSiteIds = childSiteIds;
  }

  /**
   * Whether this policy applies to child sites not explicitly assigned to another policy.
   */
  @JsonProperty("default_policy")
  public Boolean defaultPolicy;

  public Boolean getDefaultPolicy() {
    return defaultPolicy;
  }

  public void setDefaultPolicy(Boolean defaultPolicy) {
    this.defaultPolicy = defaultPolicy;
  }

  /**
   * When this policy was created.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * When this policy was last updated.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this ChildSiteManagementPolicy. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
   *   skip_child_site_ids - array(int64) - IDs of child sites excluded from this default policy.
   *   child_site_ids - array(int64) - IDs of child sites explicitly assigned to this non-default policy.
   *   default_policy - boolean - Whether this policy applies to child sites not explicitly assigned to another policy.
   *   policy_type - string - Type of policy.  Valid values: `settings`.
   *   name - string - Name for this policy.
   *   description - string - Description for this policy.
   */
  public ChildSiteManagementPolicy update(HashMap<String, Object> parameters) throws IOException {
    ChildSiteManagementPolicy updated = filesClient().childSiteManagementPolicies().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().childSiteManagementPolicies().delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this ChildSiteManagementPolicy: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().childSiteManagementPolicies().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.skipChildSiteIds != null) {
      parameters.put("skip_child_site_ids", this.skipChildSiteIds);
    }
    if (this.childSiteIds != null) {
      parameters.put("child_site_ids", this.childSiteIds);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(ChildSiteManagementPolicy canonical) {
    this.id = canonical.id;
    this.policyType = canonical.policyType;
    this.name = canonical.name;
    this.description = canonical.description;
    this.value = canonical.value;
    this.appliedChildSiteIds = canonical.appliedChildSiteIds;
    this.skipChildSiteIds = canonical.skipChildSiteIds;
    this.childSiteIds = canonical.childSiteIds;
    this.defaultPolicy = canonical.defaultPolicy;
    this.createdAt = canonical.createdAt;
    this.updatedAt = canonical.updatedAt;
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public static ListIterator<ChildSiteManagementPolicy> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().list(parameters, options);
  }

  public static ListIterator<ChildSiteManagementPolicy> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ChildSiteManagementPolicy> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Child Site Management Policy ID.
   */
  public static ChildSiteManagementPolicy find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ChildSiteManagementPolicy find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ChildSiteManagementPolicy find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ChildSiteManagementPolicy find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().find(id, parameters, options);
  }

  public static ChildSiteManagementPolicy get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ChildSiteManagementPolicy get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
   *   skip_child_site_ids - array(int64) - IDs of child sites excluded from this default policy.
   *   child_site_ids - array(int64) - IDs of child sites explicitly assigned to this non-default policy.
   *   default_policy - boolean - Whether this policy applies to child sites not explicitly assigned to another policy.
   *   policy_type (required) - string - Type of policy.  Valid values: `settings`.
   *   name - string - Name for this policy.
   *   description - string - Description for this policy.
   */
  public static ChildSiteManagementPolicy create() throws RuntimeException {
    return create(null, null);
  }

  public static ChildSiteManagementPolicy create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static ChildSiteManagementPolicy create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().create(parameters, options);
  }


  /**
   * Parameters:
   *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
   *   skip_child_site_ids - array(int64) - IDs of child sites excluded from this default policy.
   *   child_site_ids - array(int64) - IDs of child sites explicitly assigned to this non-default policy.
   *   default_policy - boolean - Whether this policy applies to child sites not explicitly assigned to another policy.
   *   policy_type - string - Type of policy.  Valid values: `settings`.
   *   name - string - Name for this policy.
   *   description - string - Description for this policy.
   */
  public static ChildSiteManagementPolicy update() throws RuntimeException {
    return update(null, null, null);
  }

  public static ChildSiteManagementPolicy update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static ChildSiteManagementPolicy update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static ChildSiteManagementPolicy update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().update(id, parameters, options);
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
    FilesClient.defaultClient().childSiteManagementPolicies().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
