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
public class FormFieldSet implements ModelInterface {
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

  // One mapper for every FormFieldSet, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code FormFieldSet} with no attributes set.
   */
  public FormFieldSet() {
    this(null, null);
  }

  /**
   * Creates a {@code FormFieldSet} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public FormFieldSet(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code FormFieldSet} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public FormFieldSet(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Form field set id
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
   * Title to be displayed
   */
  @JsonProperty("title")
  public String title;

  /**
   * Gets {@link #title}.
   *
   * @return the value of {@link #title}
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets {@link #title}.
   *
   * @param title the new value
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   */
  @JsonProperty("require_complete_metadata_capture")
  public Boolean requireCompleteMetadataCapture;

  /**
   * Gets {@link #requireCompleteMetadataCapture}.
   *
   * @return the value of {@link #requireCompleteMetadataCapture}
   */
  public Boolean getRequireCompleteMetadataCapture() {
    return requireCompleteMetadataCapture;
  }

  /**
   * Sets {@link #requireCompleteMetadataCapture}.
   *
   * @param requireCompleteMetadataCapture the new value
   */
  public void setRequireCompleteMetadataCapture(Boolean requireCompleteMetadataCapture) {
    this.requireCompleteMetadataCapture = requireCompleteMetadataCapture;
  }

  /**
   * Layout of the form
   */
  @JsonProperty("form_layout")
  public Long[] formLayout;

  /**
   * Gets {@link #formLayout}.
   *
   * @return the value of {@link #formLayout}
   */
  public Long[] getFormLayout() {
    return formLayout;
  }

  /**
   * Sets {@link #formLayout}.
   *
   * @param formLayout the new value
   */
  public void setFormLayout(Long[] formLayout) {
    this.formLayout = formLayout;
  }

  /**
   * Associated form field definitions; authenticated form field set responses include historical definitions, while form_layout identifies current fields
   */
  @JsonProperty("form_fields")
  public FormField[] formFields;

  /**
   * Gets {@link #formFields}.
   *
   * @return the value of {@link #formFields}
   */
  public FormField[] getFormFields() {
    return formFields;
  }

  /**
   * Sets {@link #formFields}.
   *
   * @param formFields the new value
   */
  public void setFormFields(FormField[] formFields) {
    this.formFields = formFields;
  }

  /**
   * Any associated InboxRegistrations or BundleRegistrations can be saved without providing name
   */
  @JsonProperty("skip_name")
  public Boolean skipName;

  /**
   * Gets {@link #skipName}.
   *
   * @return the value of {@link #skipName}
   */
  public Boolean getSkipName() {
    return skipName;
  }

  /**
   * Sets {@link #skipName}.
   *
   * @param skipName the new value
   */
  public void setSkipName(Boolean skipName) {
    this.skipName = skipName;
  }

  /**
   * Any associated InboxRegistrations or BundleRegistrations can be saved without providing email
   */
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  /**
   * Gets {@link #skipEmail}.
   *
   * @return the value of {@link #skipEmail}
   */
  public Boolean getSkipEmail() {
    return skipEmail;
  }

  /**
   * Sets {@link #skipEmail}.
   *
   * @param skipEmail the new value
   */
  public void setSkipEmail(Boolean skipEmail) {
    this.skipEmail = skipEmail;
  }

  /**
   * Any associated InboxRegistrations or BundleRegistrations can be saved without providing company
   */
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  /**
   * Gets {@link #skipCompany}.
   *
   * @return the value of {@link #skipCompany}
   */
  public Boolean getSkipCompany() {
    return skipCompany;
  }

  /**
   * Sets {@link #skipCompany}.
   *
   * @param skipCompany the new value
   */
  public void setSkipCompany(Boolean skipCompany) {
    this.skipCompany = skipCompany;
  }

  /**
   * Form Field Set is in use by an active Inbox / Bundle / Inbox Registration / Bundle Registration
   */
  @JsonProperty("in_use")
  public Boolean inUse;

  /**
   * Gets {@link #inUse}.
   *
   * @return the value of {@link #inUse}
   */
  public Boolean getInUse() {
    return inUse;
  }

  /**
   * Sets {@link #inUse}.
   *
   * @param inUse the new value
   */
  public void setInUse(Boolean inUse) {
    this.inUse = inUse;
  }

  /**
   * User ID.  Provide a value of `0` to operate the current session's user.
   */
  @JsonProperty("user_id")
  public Long userId;

  /**
   * Gets {@link #userId}.
   *
   * @return the value of {@link #userId}
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets {@link #userId}.
   *
   * @param userId the new value
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Workspace ID
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
   * Updates this FormFieldSet. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Form Field Set
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code title} (String): Title to be displayed</li>
   * <li>{@code require_complete_metadata_capture} (Boolean): Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code skip_email} (Boolean): Skip validating form email</li>
   * <li>{@code skip_name} (Boolean): Skip validating form name</li>
   * <li>{@code skip_company} (Boolean): Skip validating company</li>
   * <li>{@code form_fields} (Object[]): </li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FormFieldSet}
   */
  public FormFieldSet update(HashMap<String, Object> parameters) throws IOException {
    FormFieldSet updated = filesClient().formFieldSets().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Form Field Set
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().formFieldSets().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Form Field Set
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this FormFieldSet: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().formFieldSets().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.formFields != null) {
      parameters.put("form_fields", this.formFields);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(FormFieldSet canonical) {
    this.id = canonical.id;
    this.title = canonical.title;
    this.requireCompleteMetadataCapture = canonical.requireCompleteMetadataCapture;
    this.formLayout = canonical.formLayout;
    this.formFields = canonical.formFields;
    this.skipName = canonical.skipName;
    this.skipEmail = canonical.skipEmail;
    this.skipCompany = canonical.skipCompany;
    this.inUse = canonical.inUse;
    this.userId = canonical.userId;
    this.workspaceId = canonical.workspaceId;
  }

  /**
   * List Form Field Sets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<FormFieldSet> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Form Field Sets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<FormFieldSet> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Form Field Sets
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FormFieldSet} records, in a {@code ListIterator}
   */
  public static ListIterator<FormFieldSet> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().list(parameters, options);
  }

  /**
   * List Form Field Sets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<FormFieldSet> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Form Field Sets
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<FormFieldSet> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Form Field Set
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FormFieldSet find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Form Field Set
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FormFieldSet find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Form Field Set
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FormFieldSet find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Form Field Set
   *
   * @param id Form Field Set ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FormFieldSet}
   */
  public static FormFieldSet find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().find(id, parameters, options);
  }

  /**
   * Show Form Field Set
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FormFieldSet get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Form Field Set
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static FormFieldSet get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create Form Field Set
   *
   * @see #create(HashMap, HashMap)
   */
  public static FormFieldSet create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create Form Field Set
   *
   * @see #create(HashMap, HashMap)
   */
  public static FormFieldSet create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create Form Field Set
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code user_id} (Long): User ID.  Provide a value of `0` to operate the current session's user.</li>
   * <li>{@code title} (String): Title to be displayed</li>
   * <li>{@code require_complete_metadata_capture} (Boolean): Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code skip_email} (Boolean): Skip validating form email</li>
   * <li>{@code skip_name} (Boolean): Skip validating form name</li>
   * <li>{@code skip_company} (Boolean): Skip validating company</li>
   * <li>{@code form_fields} (Object[]): </li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FormFieldSet}
   */
  public static FormFieldSet create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().create(parameters, options);
  }


  /**
   * Update Form Field Set
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static FormFieldSet update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Form Field Set
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static FormFieldSet update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Form Field Set
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static FormFieldSet update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Form Field Set
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code title} (String): Title to be displayed</li>
   * <li>{@code require_complete_metadata_capture} (Boolean): Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture</li>
   * <li>{@code workspace_id} (Long): Workspace ID</li>
   * <li>{@code skip_email} (Boolean): Skip validating form email</li>
   * <li>{@code skip_name} (Boolean): Skip validating form name</li>
   * <li>{@code skip_company} (Boolean): Skip validating company</li>
   * <li>{@code form_fields} (Object[]): </li>
   * </ul>
   *
   * @param id Form Field Set ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FormFieldSet}
   */
  public static FormFieldSet update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().update(id, parameters, options);
  }


  /**
   * Delete Form Field Set
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Form Field Set
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Form Field Set
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Form Field Set
   *
   * @param id Form Field Set ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().formFieldSets().delete(id, parameters, options);
  }

  /**
   * Delete Form Field Set
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Form Field Set
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
