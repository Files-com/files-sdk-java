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


  public FormFieldSet() {
    this(null, null);
  }

  public FormFieldSet(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Title to be displayed
   */
  @JsonProperty("title")
  public String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   */
  @JsonProperty("require_complete_metadata_capture")
  public Boolean requireCompleteMetadataCapture;

  public Boolean getRequireCompleteMetadataCapture() {
    return requireCompleteMetadataCapture;
  }

  public void setRequireCompleteMetadataCapture(Boolean requireCompleteMetadataCapture) {
    this.requireCompleteMetadataCapture = requireCompleteMetadataCapture;
  }

  /**
   * Layout of the form
   */
  @JsonProperty("form_layout")
  public Long[] formLayout;

  public Long[] getFormLayout() {
    return formLayout;
  }

  public void setFormLayout(Long[] formLayout) {
    this.formLayout = formLayout;
  }

  /**
   * Associated form field definitions; authenticated form field set responses include historical definitions, while form_layout identifies current fields
   */
  @JsonProperty("form_fields")
  public FormField[] formFields;

  public FormField[] getFormFields() {
    return formFields;
  }

  public void setFormFields(FormField[] formFields) {
    this.formFields = formFields;
  }

  /**
   * Any associated InboxRegistrations or BundleRegistrations can be saved without providing name
   */
  @JsonProperty("skip_name")
  public Boolean skipName;

  public Boolean getSkipName() {
    return skipName;
  }

  public void setSkipName(Boolean skipName) {
    this.skipName = skipName;
  }

  /**
   * Any associated InboxRegistrations or BundleRegistrations can be saved without providing email
   */
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  public Boolean getSkipEmail() {
    return skipEmail;
  }

  public void setSkipEmail(Boolean skipEmail) {
    this.skipEmail = skipEmail;
  }

  /**
   * Any associated InboxRegistrations or BundleRegistrations can be saved without providing company
   */
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  public Boolean getSkipCompany() {
    return skipCompany;
  }

  public void setSkipCompany(Boolean skipCompany) {
    this.skipCompany = skipCompany;
  }

  /**
   * Form Field Set is in use by an active Inbox / Bundle / Inbox Registration / Bundle Registration
   */
  @JsonProperty("in_use")
  public Boolean inUse;

  public Boolean getInUse() {
    return inUse;
  }

  public void setInUse(Boolean inUse) {
    this.inUse = inUse;
  }

  /**
   * User ID.  Provide a value of `0` to operate the current session's user.
   */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Workspace ID
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
   * Updates this FormFieldSet. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   title - string - Title to be displayed
   *   require_complete_metadata_capture - boolean - Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   *   workspace_id - int64 - Workspace ID
   *   skip_email - boolean - Skip validating form email
   *   skip_name - boolean - Skip validating form name
   *   skip_company - boolean - Skip validating company
   *   form_fields - array(object)
   */
  public FormFieldSet update(HashMap<String, Object> parameters) throws IOException {
    FormFieldSet updated = filesClient().formFieldSets().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().formFieldSets().delete(this.id, parameters, this.options);
  }

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
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   */
  public static ListIterator<FormFieldSet> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<FormFieldSet> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<FormFieldSet> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().list(parameters, options);
  }

  public static ListIterator<FormFieldSet> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<FormFieldSet> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - Form Field Set ID.
   */
  public static FormFieldSet find() throws RuntimeException {
    return find(null, null, null);
  }

  public static FormFieldSet find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static FormFieldSet find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static FormFieldSet find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().find(id, parameters, options);
  }

  public static FormFieldSet get() throws RuntimeException {
    return get(null, null, null);
  }

  public static FormFieldSet get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
   *   title - string - Title to be displayed
   *   require_complete_metadata_capture - boolean - Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   *   workspace_id - int64 - Workspace ID
   *   skip_email - boolean - Skip validating form email
   *   skip_name - boolean - Skip validating form name
   *   skip_company - boolean - Skip validating company
   *   form_fields - array(object)
   */
  public static FormFieldSet create() throws RuntimeException {
    return create(null, null);
  }

  public static FormFieldSet create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static FormFieldSet create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().create(parameters, options);
  }


  /**
   * Parameters:
   *   title - string - Title to be displayed
   *   require_complete_metadata_capture - boolean - Reject answers over 1024 characters or more than 32 answered custom fields to ensure complete file metadata capture
   *   workspace_id - int64 - Workspace ID
   *   skip_email - boolean - Skip validating form email
   *   skip_name - boolean - Skip validating form name
   *   skip_company - boolean - Skip validating company
   *   form_fields - array(object)
   */
  public static FormFieldSet update() throws RuntimeException {
    return update(null, null, null);
  }

  public static FormFieldSet update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static FormFieldSet update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static FormFieldSet update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().formFieldSets().update(id, parameters, options);
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
    FilesClient.defaultClient().formFieldSets().delete(id, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
