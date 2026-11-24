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
import java.util.Date;
import java.util.HashMap;

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

  // One mapper for every ChildSiteManagementPolicy, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code ChildSiteManagementPolicy} with no attributes set.
   */
  public ChildSiteManagementPolicy() {
    this(null, null);
  }

  /**
   * Creates a {@code ChildSiteManagementPolicy} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public ChildSiteManagementPolicy(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code ChildSiteManagementPolicy} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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
   * Type of policy.  Valid values: `settings`.
   */
  @JsonProperty("policy_type")
  public String policyType;

  /**
   * Gets {@link #policyType}.
   *
   * @return the value of {@link #policyType}
   */
  public String getPolicyType() {
    return policyType;
  }

  /**
   * Sets {@link #policyType}.
   *
   * @param policyType the new value
   */
  public void setPolicyType(String policyType) {
    this.policyType = policyType;
  }

  /**
   * Name for this policy.
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
   * Description for this policy.
   */
  @JsonProperty("description")
  public String description;

  /**
   * Gets {@link #description}.
   *
   * @return the value of {@link #description}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets {@link #description}.
   *
   * @param description the new value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Policy configuration data. On a Child Site, contains only managed setting names with null values. Settings policies accept site settings plus an optional `folder_behaviors` array for parent-managed root behaviors on child sites. For more information, refer to the Value Hash section of the developer documentation.
   */
  @JsonProperty("value")
  public Object value;

  /**
   * Gets {@link #value}.
   *
   * @return the value of {@link #value}
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets {@link #value}.
   *
   * @param value the new value
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * IDs of child sites that this policy has been applied to. This field is read-only.
   */
  @JsonProperty("applied_child_site_ids")
  public Long[] appliedChildSiteIds;

  /**
   * Gets {@link #appliedChildSiteIds}.
   *
   * @return the value of {@link #appliedChildSiteIds}
   */
  public Long[] getAppliedChildSiteIds() {
    return appliedChildSiteIds;
  }

  /**
   * Sets {@link #appliedChildSiteIds}.
   *
   * @param appliedChildSiteIds the new value
   */
  public void setAppliedChildSiteIds(Long[] appliedChildSiteIds) {
    this.appliedChildSiteIds = appliedChildSiteIds;
  }

  /**
   * IDs of child sites excluded from this default policy.
   */
  @JsonProperty("skip_child_site_ids")
  public Long[] skipChildSiteIds;

  /**
   * Gets {@link #skipChildSiteIds}.
   *
   * @return the value of {@link #skipChildSiteIds}
   */
  public Long[] getSkipChildSiteIds() {
    return skipChildSiteIds;
  }

  /**
   * Sets {@link #skipChildSiteIds}.
   *
   * @param skipChildSiteIds the new value
   */
  public void setSkipChildSiteIds(Long[] skipChildSiteIds) {
    this.skipChildSiteIds = skipChildSiteIds;
  }

  /**
   * IDs of child sites explicitly assigned to this non-default policy.
   */
  @JsonProperty("child_site_ids")
  public Long[] childSiteIds;

  /**
   * Gets {@link #childSiteIds}.
   *
   * @return the value of {@link #childSiteIds}
   */
  public Long[] getChildSiteIds() {
    return childSiteIds;
  }

  /**
   * Sets {@link #childSiteIds}.
   *
   * @param childSiteIds the new value
   */
  public void setChildSiteIds(Long[] childSiteIds) {
    this.childSiteIds = childSiteIds;
  }

  /**
   * Whether this policy applies to child sites not explicitly assigned to another policy.
   */
  @JsonProperty("default_policy")
  public Boolean defaultPolicy;

  /**
   * Gets {@link #defaultPolicy}.
   *
   * @return the value of {@link #defaultPolicy}
   */
  public Boolean getDefaultPolicy() {
    return defaultPolicy;
  }

  /**
   * Sets {@link #defaultPolicy}.
   *
   * @param defaultPolicy the new value
   */
  public void setDefaultPolicy(Boolean defaultPolicy) {
    this.defaultPolicy = defaultPolicy;
  }

  /**
   * When this policy was created.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * When this policy was last updated.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Updates this ChildSiteManagementPolicy. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Child Site Management Policy
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code value} (Object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.</li>
   * <li>{@code skip_child_site_ids} (Long[]): IDs of child sites excluded from this default policy.</li>
   * <li>{@code child_site_ids} (Long[]): IDs of child sites explicitly assigned to this non-default policy.</li>
   * <li>{@code default_policy} (Boolean): Whether this policy applies to child sites not explicitly assigned to another policy.</li>
   * <li>{@code policy_type} (String): Type of policy.  Valid values: `settings`.</li>
   * <li>{@code name} (String): Name for this policy.</li>
   * <li>{@code description} (String): Description for this policy.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ChildSiteManagementPolicy}
   */
  public ChildSiteManagementPolicy update(HashMap<String, Object> parameters) throws IOException {
    ChildSiteManagementPolicy updated = filesClient().childSiteManagementPolicies().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Child Site Management Policy
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().childSiteManagementPolicies().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Child Site Management Policy
   *
   * @see #delete(HashMap)
   */
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
   * List Child Site Management Policies. On a Child Site, returns only the policy covering that site. Its value contains managed setting names with null values
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ChildSiteManagementPolicy> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Child Site Management Policies. On a Child Site, returns only the policy covering that site. Its value contains managed setting names with null values
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Child Site Management Policies. On a Child Site, returns only the policy covering that site. Its value contains managed setting names with null values
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ChildSiteManagementPolicy} records, in a {@code ListIterator}
   */
  public static ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().list(parameters, options);
  }

  /**
   * List Child Site Management Policies. On a Child Site, returns only the policy covering that site. Its value contains managed setting names with null values
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ChildSiteManagementPolicy> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Child Site Management Policies. On a Child Site, returns only the policy covering that site. Its value contains managed setting names with null values
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<ChildSiteManagementPolicy> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Child Site Management Policy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Child Site Management Policy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Child Site Management Policy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Child Site Management Policy
   *
   * @param id Child Site Management Policy ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ChildSiteManagementPolicy}
   */
  public static ChildSiteManagementPolicy find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().find(id, parameters, options);
  }

  /**
   * Show Child Site Management Policy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Child Site Management Policy
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Child Site Management Policy
   *
   * @see #create(HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Child Site Management Policy
   *
   * @see #create(HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Child Site Management Policy
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code value} (Object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.</li>
   * <li>{@code skip_child_site_ids} (Long[]): IDs of child sites excluded from this default policy.</li>
   * <li>{@code child_site_ids} (Long[]): IDs of child sites explicitly assigned to this non-default policy.</li>
   * <li>{@code default_policy} (Boolean): Whether this policy applies to child sites not explicitly assigned to another policy.</li>
   * <li>{@code policy_type} (String): Required. Type of policy.  Valid values: `settings`.</li>
   * <li>{@code name} (String): Name for this policy.</li>
   * <li>{@code description} (String): Description for this policy.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ChildSiteManagementPolicy}
   */
  public static ChildSiteManagementPolicy create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().create(parameters, options);
  }


  /**
   * Update Child Site Management Policy
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Child Site Management Policy
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Child Site Management Policy
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static ChildSiteManagementPolicy update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Child Site Management Policy
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code value} (Object): Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.</li>
   * <li>{@code skip_child_site_ids} (Long[]): IDs of child sites excluded from this default policy.</li>
   * <li>{@code child_site_ids} (Long[]): IDs of child sites explicitly assigned to this non-default policy.</li>
   * <li>{@code default_policy} (Boolean): Whether this policy applies to child sites not explicitly assigned to another policy.</li>
   * <li>{@code policy_type} (String): Type of policy.  Valid values: `settings`.</li>
   * <li>{@code name} (String): Name for this policy.</li>
   * <li>{@code description} (String): Description for this policy.</li>
   * </ul>
   *
   * @param id Child Site Management Policy ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ChildSiteManagementPolicy}
   */
  public static ChildSiteManagementPolicy update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().childSiteManagementPolicies().update(id, parameters, options);
  }


  /**
   * Delete Child Site Management Policy
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Child Site Management Policy
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Child Site Management Policy
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Child Site Management Policy
   *
   * @param id Child Site Management Policy ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().childSiteManagementPolicies().delete(id, parameters, options);
  }

  /**
   * Delete Child Site Management Policy
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Child Site Management Policy
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
