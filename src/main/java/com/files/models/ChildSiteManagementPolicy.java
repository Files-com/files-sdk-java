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

  public void setOptions(HashMap<String, Object> options) {
    this.options = options;
  }

  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public ChildSiteManagementPolicy() {
    this(null, null);
  }

  public ChildSiteManagementPolicy(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ChildSiteManagementPolicy(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
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
  * Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
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
  * IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
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
  * Parameters:
  *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
  *   skip_child_site_ids - array(int64) - IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
  *   policy_type - string - Type of policy.  Valid values: `settings`.
  *   name - string - Name for this policy.
  *   description - string - Description for this policy.
  */
  public ChildSiteManagementPolicy update(HashMap<String, Object> parameters) throws IOException {
    return ChildSiteManagementPolicy.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    ChildSiteManagementPolicy.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    ChildSiteManagementPolicy.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<ChildSiteManagementPolicy> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ChildSiteManagementPolicy> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/child_site_management_policies", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ChildSiteManagementPolicy>> typeReference = new TypeReference<List<ChildSiteManagementPolicy>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
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



    String url = String.format("%s%s/child_site_management_policies/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ChildSiteManagementPolicy> typeReference = new TypeReference<ChildSiteManagementPolicy>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
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
  *   skip_child_site_ids - array(int64) - IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
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
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("policy_type") || parameters.get("policy_type") == null) {
      throw new NullPointerException("Parameter missing: policy_type parameters[\"policy_type\"]");
    }

    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }
    if (parameters.containsKey("skip_child_site_ids") && !(parameters.get("skip_child_site_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: skip_child_site_ids must be of type Long[] parameters[\"skip_child_site_ids\"]");
    }
    if (parameters.containsKey("policy_type") && !(parameters.get("policy_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: policy_type must be of type String parameters[\"policy_type\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }


    String url = String.format("%s%s/child_site_management_policies", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<ChildSiteManagementPolicy> typeReference = new TypeReference<ChildSiteManagementPolicy>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   value - object - Policy configuration data. Attributes differ by policy type. For more information, refer to the Value Hash section of the developer documentation.
  *   skip_child_site_ids - array(int64) - IDs of child sites that this policy has been exempted from. If `skip_child_site_ids` is empty, the policy will be applied to all child sites. To apply a policy to a child site that has been exempted, remove it from `skip_child_site_ids` or set it to an empty array (`[]`).
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
    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Object parameters[\"value\"]");
    }
    if (parameters.containsKey("skip_child_site_ids") && !(parameters.get("skip_child_site_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: skip_child_site_ids must be of type Long[] parameters[\"skip_child_site_ids\"]");
    }
    if (parameters.containsKey("policy_type") && !(parameters.get("policy_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: policy_type must be of type String parameters[\"policy_type\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }



    String url = String.format("%s%s/child_site_management_policies/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<ChildSiteManagementPolicy> typeReference = new TypeReference<ChildSiteManagementPolicy>() {};
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



    String url = String.format("%s%s/child_site_management_policies/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
