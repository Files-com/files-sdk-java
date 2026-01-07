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
public class Bundle implements ModelInterface {
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


  public Bundle() {
    this(null, null);
  }

  public Bundle(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Bundle(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Bundle code.  This code forms the end part of the Public URL.
  */
  @JsonProperty("code")
  public String code;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  /**
  * Page link and button color
  */
  @JsonProperty("color_left")
  public String colorLeft;

  public String getColorLeft() {
    return colorLeft;
  }

  public void setColorLeft(String colorLeft) {
    this.colorLeft = colorLeft;
  }

  /**
  * Top bar link color
  */
  @JsonProperty("color_link")
  public String colorLink;

  public String getColorLink() {
    return colorLink;
  }

  public void setColorLink(String colorLink) {
    this.colorLink = colorLink;
  }

  /**
  * Page link and button color
  */
  @JsonProperty("color_text")
  public String colorText;

  public String getColorText() {
    return colorText;
  }

  public void setColorText(String colorText) {
    this.colorText = colorText;
  }

  /**
  * Top bar background color
  */
  @JsonProperty("color_top")
  public String colorTop;

  public String getColorTop() {
    return colorTop;
  }

  public void setColorTop(String colorTop) {
    this.colorTop = colorTop;
  }

  /**
  * Top bar text color
  */
  @JsonProperty("color_top_text")
  public String colorTopText;

  public String getColorTopText() {
    return colorTopText;
  }

  public void setColorTopText(String colorTopText) {
    this.colorTopText = colorTopText;
  }

  /**
  * Public URL of Share Link
  */
  @JsonProperty("url")
  public String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
  * Public description
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
  * Bundle expiration date/time
  */
  @JsonProperty("expires_at")
  public Date expiresAt;

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
  * Is this bundle password protected?
  */
  @JsonProperty("password_protected")
  public Boolean passwordProtected;

  public Boolean getPasswordProtected() {
    return passwordProtected;
  }

  public void setPasswordProtected(Boolean passwordProtected) {
    this.passwordProtected = passwordProtected;
  }

  /**
  * Permissions that apply to Folders in this Share Link.
  */
  @JsonProperty("permissions")
  public String permissions;

  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  /**
  */
  @JsonProperty("preview_only")
  public Boolean previewOnly;

  public Boolean getPreviewOnly() {
    return previewOnly;
  }

  public void setPreviewOnly(Boolean previewOnly) {
    this.previewOnly = previewOnly;
  }

  /**
  * Show a registration page that captures the downloader's name and email address?
  */
  @JsonProperty("require_registration")
  public Boolean requireRegistration;

  public Boolean getRequireRegistration() {
    return requireRegistration;
  }

  public void setRequireRegistration(Boolean requireRegistration) {
    this.requireRegistration = requireRegistration;
  }

  /**
  * Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  */
  @JsonProperty("require_share_recipient")
  public Boolean requireShareRecipient;

  public Boolean getRequireShareRecipient() {
    return requireShareRecipient;
  }

  public void setRequireShareRecipient(Boolean requireShareRecipient) {
    this.requireShareRecipient = requireShareRecipient;
  }

  /**
  * If true, we will hide the 'Remember Me' box on the Bundle registration page, requiring that the user logout and log back in every time they visit the page.
  */
  @JsonProperty("require_logout")
  public Boolean requireLogout;

  public Boolean getRequireLogout() {
    return requireLogout;
  }

  public void setRequireLogout(Boolean requireLogout) {
    this.requireLogout = requireLogout;
  }

  /**
  * Legal text that must be agreed to prior to accessing Bundle.
  */
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  public String getClickwrapBody() {
    return clickwrapBody;
  }

  public void setClickwrapBody(String clickwrapBody) {
    this.clickwrapBody = clickwrapBody;
  }

  /**
  * Custom Form to use
  */
  @JsonProperty("form_field_set")
  public FormFieldSet formFieldSet;

  public FormFieldSet getFormFieldSet() {
    return formFieldSet;
  }

  public void setFormFieldSet(FormFieldSet formFieldSet) {
    this.formFieldSet = formFieldSet;
  }

  /**
  * BundleRegistrations can be saved without providing name?
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
  * BundleRegistrations can be saved without providing email?
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
  * Date when share will start to be accessible. If `nil` access granted right after create.
  */
  @JsonProperty("start_access_on_date")
  public Date startAccessOnDate;

  public Date getStartAccessOnDate() {
    return startAccessOnDate;
  }

  public void setStartAccessOnDate(Date startAccessOnDate) {
    this.startAccessOnDate = startAccessOnDate;
  }

  /**
  * BundleRegistrations can be saved without providing company?
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
  * Bundle ID
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
  * Bundle created at date/time
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  */
  @JsonProperty("dont_separate_submissions_by_folder")
  public Boolean dontSeparateSubmissionsByFolder;

  public Boolean getDontSeparateSubmissionsByFolder() {
    return dontSeparateSubmissionsByFolder;
  }

  public void setDontSeparateSubmissionsByFolder(Boolean dontSeparateSubmissionsByFolder) {
    this.dontSeparateSubmissionsByFolder = dontSeparateSubmissionsByFolder;
  }

  /**
  * Maximum number of times bundle can be accessed
  */
  @JsonProperty("max_uses")
  public Long maxUses;

  public Long getMaxUses() {
    return maxUses;
  }

  public void setMaxUses(Long maxUses) {
    this.maxUses = maxUses;
  }

  /**
  * Bundle internal note
  */
  @JsonProperty("note")
  public String note;

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  /**
  * Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
  */
  @JsonProperty("path_template")
  public String pathTemplate;

  public String getPathTemplate() {
    return pathTemplate;
  }

  public void setPathTemplate(String pathTemplate) {
    this.pathTemplate = pathTemplate;
  }

  /**
  * Timezone to use when rendering timestamps in path templates.
  */
  @JsonProperty("path_template_time_zone")
  public String pathTemplateTimeZone;

  public String getPathTemplateTimeZone() {
    return pathTemplateTimeZone;
  }

  public void setPathTemplateTimeZone(String pathTemplateTimeZone) {
    this.pathTemplateTimeZone = pathTemplateTimeZone;
  }

  /**
  * Send delivery receipt to the uploader. Note: For writable share only
  */
  @JsonProperty("send_email_receipt_to_uploader")
  public Boolean sendEmailReceiptToUploader;

  public Boolean getSendEmailReceiptToUploader() {
    return sendEmailReceiptToUploader;
  }

  public void setSendEmailReceiptToUploader(Boolean sendEmailReceiptToUploader) {
    this.sendEmailReceiptToUploader = sendEmailReceiptToUploader;
  }

  /**
  * ID of the snapshot containing this bundle's contents.
  */
  @JsonProperty("snapshot_id")
  public Long snapshotId;

  public Long getSnapshotId() {
    return snapshotId;
  }

  public void setSnapshotId(Long snapshotId) {
    this.snapshotId = snapshotId;
  }

  /**
  * Bundle creator user ID
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
  * Bundle creator username
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
  * ID of the clickwrap to use with this bundle.
  */
  @JsonProperty("clickwrap_id")
  public Long clickwrapId;

  public Long getClickwrapId() {
    return clickwrapId;
  }

  public void setClickwrapId(Long clickwrapId) {
    this.clickwrapId = clickwrapId;
  }

  /**
  * ID of the associated inbox, if available.
  */
  @JsonProperty("inbox_id")
  public Long inboxId;

  public Long getInboxId() {
    return inboxId;
  }

  public void setInboxId(Long inboxId) {
    this.inboxId = inboxId;
  }

  /**
  * Preview watermark image applied to all bundle items.
  */
  @JsonProperty("watermark_attachment")
  public Image watermarkAttachment;

  public Image getWatermarkAttachment() {
    return watermarkAttachment;
  }

  public void setWatermarkAttachment(Image watermarkAttachment) {
    this.watermarkAttachment = watermarkAttachment;
  }

  /**
  * Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
  */
  @JsonProperty("watermark_value")
  public Object watermarkValue;

  public Object getWatermarkValue() {
    return watermarkValue;
  }

  public void setWatermarkValue(Object watermarkValue) {
    this.watermarkValue = watermarkValue;
  }

  /**
  * Does this bundle have an associated inbox?
  */
  @JsonProperty("has_inbox")
  public Boolean hasInbox;

  public Boolean getHasInbox() {
    return hasInbox;
  }

  public void setHasInbox(Boolean hasInbox) {
    this.hasInbox = hasInbox;
  }

  /**
  * Should folder uploads be prevented?
  */
  @JsonProperty("dont_allow_folders_in_uploads")
  public Boolean dontAllowFoldersInUploads;

  public Boolean getDontAllowFoldersInUploads() {
    return dontAllowFoldersInUploads;
  }

  public void setDontAllowFoldersInUploads(Boolean dontAllowFoldersInUploads) {
    this.dontAllowFoldersInUploads = dontAllowFoldersInUploads;
  }

  /**
  * A list of paths in this bundle.  For performance reasons, this is not provided when listing bundles.
  */
  @JsonProperty("paths")
  public String[] paths;

  public String[] getPaths() {
    return paths;
  }

  public void setPaths(String[] paths) {
    this.paths = paths;
  }

  /**
  * A list of bundlepaths in this bundle.  For performance reasons, this is not provided when listing bundles.
  */
  @JsonProperty("bundlepaths")
  public Object[] bundlepaths;

  public Object[] getBundlepaths() {
    return bundlepaths;
  }

  public void setBundlepaths(Object[] bundlepaths) {
    this.bundlepaths = bundlepaths;
  }

  /**
  * Password for this bundle.
  */
  @JsonProperty("password")
  public String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
  * Id of Form Field Set to use with this bundle
  */
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  public Long getFormFieldSetId() {
    return formFieldSetId;
  }

  public void setFormFieldSetId(Long formFieldSetId) {
    this.formFieldSetId = formFieldSetId;
  }

  /**
  * If true, create a snapshot of this bundle's contents.
  */
  @JsonProperty("create_snapshot")
  public Boolean createSnapshot;

  public Boolean getCreateSnapshot() {
    return createSnapshot;
  }

  public void setCreateSnapshot(Boolean createSnapshot) {
    this.createSnapshot = createSnapshot;
  }

  /**
  * If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
  */
  @JsonProperty("finalize_snapshot")
  public Boolean finalizeSnapshot;

  public Boolean getFinalizeSnapshot() {
    return finalizeSnapshot;
  }

  public void setFinalizeSnapshot(Boolean finalizeSnapshot) {
    this.finalizeSnapshot = finalizeSnapshot;
  }

  /**
  * Preview watermark image applied to all bundle items.
  */
  @JsonProperty("watermark_attachment_file")
  public byte[] watermarkAttachmentFile;

  public byte[] getWatermarkAttachmentFile() {
    return watermarkAttachmentFile;
  }

  public void setWatermarkAttachmentFile(byte[] watermarkAttachmentFile) {
    this.watermarkAttachmentFile = watermarkAttachmentFile;
  }

  /**
  * If true, will delete the file stored in watermark_attachment
  */
  @JsonProperty("watermark_attachment_delete")
  public Boolean watermarkAttachmentDelete;

  public Boolean getWatermarkAttachmentDelete() {
    return watermarkAttachmentDelete;
  }

  public void setWatermarkAttachmentDelete(Boolean watermarkAttachmentDelete) {
    this.watermarkAttachmentDelete = watermarkAttachmentDelete;
  }

  /**
  * Send email(s) with a link to bundle
  *
  * Parameters:
  *   to - array(string) - A list of email addresses to share this bundle with. Required unless `recipients` is used.
  *   note - string - Note to include in email.
  *   recipients - array(object) - A list of recipients to share this bundle with. Required unless `to` is used.
  */
  public void share(HashMap<String, Object> parameters) throws IOException {
    Bundle.share(this.id, parameters, this.options);
  }

  /**
  * Parameters:
  *   paths - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
  *   description - string - Public description
  *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  *   expires_at - string - Bundle expiration date/time
  *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   note - string - Bundle internal note
  *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
  *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
  *   permissions - string - Permissions that apply to Folders in this Share Link.
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
  *   skip_company - boolean - BundleRegistrations can be saved without providing company?
  *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
  *   skip_email - boolean - BundleRegistrations can be saved without providing email?
  *   skip_name - boolean - BundleRegistrations can be saved without providing name?
  *   user_id - int64 - The owning user id. Only site admins can set this.
  *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
  *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
  */
  public Bundle update(HashMap<String, Object> parameters) throws IOException {
    return Bundle.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    Bundle.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Bundle.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `expires_at`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`, `expires_at`, `code` or `user_id`. Valid field combinations are `[ user_id, expires_at ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at` and `expires_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at` and `expires_at`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `code`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at` and `expires_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at` and `expires_at`.
  */
  public static ListIterator<Bundle> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Bundle> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Bundle> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
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
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Object parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Object parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Object parameters[\"filter_prefix\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Object parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Object parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/bundles", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Bundle>> typeReference = new TypeReference<List<Bundle>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Bundle> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Bundle> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Bundle ID.
  */
  public static Bundle find() throws RuntimeException {
    return find(null, null, null);
  }

  public static Bundle find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static Bundle find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static Bundle find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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



    String url = String.format("%s%s/bundles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static Bundle get() throws RuntimeException {
    return get(null, null, null);
  }

  public static Bundle get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   paths (required) - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
  *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
  *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  *   expires_at - string - Bundle expiration date/time
  *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   description - string - Public description
  *   note - string - Bundle internal note
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
  *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
  *   permissions - string - Permissions that apply to Folders in this Share Link.
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
  *   skip_email - boolean - BundleRegistrations can be saved without providing email?
  *   skip_name - boolean - BundleRegistrations can be saved without providing name?
  *   skip_company - boolean - BundleRegistrations can be saved without providing company?
  *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
  *   snapshot_id - int64 - ID of the snapshot containing this bundle's contents.
  *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
  */
  public static Bundle create() throws RuntimeException {
    return create(null, null);
  }

  public static Bundle create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Bundle create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("paths") || parameters.get("paths") == null) {
      throw new NullPointerException("Parameter missing: paths parameters[\"paths\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }
    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }
    if (parameters.containsKey("form_field_set_id") && !(parameters.get("form_field_set_id") instanceof Long || parameters.get("form_field_set_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: form_field_set_id must be of type Long or Integer parameters[\"form_field_set_id\"]");
    }
    if (parameters.containsKey("create_snapshot") && !(parameters.get("create_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: create_snapshot must be of type Boolean parameters[\"create_snapshot\"]");
    }
    if (parameters.containsKey("dont_separate_submissions_by_folder") && !(parameters.get("dont_separate_submissions_by_folder") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dont_separate_submissions_by_folder must be of type Boolean parameters[\"dont_separate_submissions_by_folder\"]");
    }
    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }
    if (parameters.containsKey("finalize_snapshot") && !(parameters.get("finalize_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: finalize_snapshot must be of type Boolean parameters[\"finalize_snapshot\"]");
    }
    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long || parameters.get("max_uses") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long or Integer parameters[\"max_uses\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }
    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }
    if (parameters.containsKey("path_template") && !(parameters.get("path_template") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template must be of type String parameters[\"path_template\"]");
    }
    if (parameters.containsKey("path_template_time_zone") && !(parameters.get("path_template_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template_time_zone must be of type String parameters[\"path_template_time_zone\"]");
    }
    if (parameters.containsKey("permissions") && !(parameters.get("permissions") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permissions must be of type String parameters[\"permissions\"]");
    }
    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }
    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long || parameters.get("clickwrap_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long or Integer parameters[\"clickwrap_id\"]");
    }
    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }
    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }
    if (parameters.containsKey("send_email_receipt_to_uploader") && !(parameters.get("send_email_receipt_to_uploader") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: send_email_receipt_to_uploader must be of type Boolean parameters[\"send_email_receipt_to_uploader\"]");
    }
    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }
    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }
    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }
    if (parameters.containsKey("start_access_on_date") && !(parameters.get("start_access_on_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_access_on_date must be of type String parameters[\"start_access_on_date\"]");
    }
    if (parameters.containsKey("snapshot_id") && !(parameters.get("snapshot_id") instanceof Long || parameters.get("snapshot_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: snapshot_id must be of type Long or Integer parameters[\"snapshot_id\"]");
    }
    if (parameters.containsKey("watermark_attachment_file") && !(parameters.get("watermark_attachment_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_file must be of type byte[] parameters[\"watermark_attachment_file\"]");
    }


    String url = String.format("%s%s/bundles", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Send email(s) with a link to bundle
  *
  * Parameters:
  *   to - array(string) - A list of email addresses to share this bundle with. Required unless `recipients` is used.
  *   note - string - Note to include in email.
  *   recipients - array(object) - A list of recipients to share this bundle with. Required unless `to` is used.
  */
  public static void share() throws RuntimeException {
    share(null, null, null);
  }

  public static void share(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    share(id, parameters, null);
  }

  public static void share(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    share(null, parameters, options);
  }

  public static void share(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("to") && !(parameters.get("to") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: to must be of type String[] parameters[\"to\"]");
    }
    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }
    if (parameters.containsKey("recipients") && !(parameters.get("recipients") instanceof Object[])) {
      throw new IllegalArgumentException("Bad parameter: recipients must be of type Object[] parameters[\"recipients\"]");
    }



    String url = String.format("%s%s/bundles/%s/share", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.POST, parameters, options);
  }


  /**
  * Parameters:
  *   paths - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   create_snapshot - boolean - If true, create a snapshot of this bundle's contents.
  *   description - string - Public description
  *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  *   expires_at - string - Bundle expiration date/time
  *   finalize_snapshot - boolean - If true, finalize the snapshot of this bundle's contents. Note that `create_snapshot` must also be true.
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   note - string - Bundle internal note
  *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, `strftime` directives, and any custom form data.
  *   path_template_time_zone - string - Timezone to use when rendering timestamps in path templates.
  *   permissions - string - Permissions that apply to Folders in this Share Link.
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  *   send_email_receipt_to_uploader - boolean - Send delivery receipt to the uploader. Note: For writable share only
  *   skip_company - boolean - BundleRegistrations can be saved without providing company?
  *   start_access_on_date - string - Date when share will start to be accessible. If `nil` access granted right after create.
  *   skip_email - boolean - BundleRegistrations can be saved without providing email?
  *   skip_name - boolean - BundleRegistrations can be saved without providing name?
  *   user_id - int64 - The owning user id. Only site admins can set this.
  *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
  *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
  */
  public static Bundle update() throws RuntimeException {
    return update(null, null, null);
  }

  public static Bundle update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static Bundle update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static Bundle update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }
    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }
    if (parameters.containsKey("form_field_set_id") && !(parameters.get("form_field_set_id") instanceof Long || parameters.get("form_field_set_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: form_field_set_id must be of type Long or Integer parameters[\"form_field_set_id\"]");
    }
    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long || parameters.get("clickwrap_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long or Integer parameters[\"clickwrap_id\"]");
    }
    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }
    if (parameters.containsKey("create_snapshot") && !(parameters.get("create_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: create_snapshot must be of type Boolean parameters[\"create_snapshot\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("dont_separate_submissions_by_folder") && !(parameters.get("dont_separate_submissions_by_folder") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: dont_separate_submissions_by_folder must be of type Boolean parameters[\"dont_separate_submissions_by_folder\"]");
    }
    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }
    if (parameters.containsKey("finalize_snapshot") && !(parameters.get("finalize_snapshot") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: finalize_snapshot must be of type Boolean parameters[\"finalize_snapshot\"]");
    }
    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }
    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long || parameters.get("max_uses") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long or Integer parameters[\"max_uses\"]");
    }
    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }
    if (parameters.containsKey("path_template") && !(parameters.get("path_template") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template must be of type String parameters[\"path_template\"]");
    }
    if (parameters.containsKey("path_template_time_zone") && !(parameters.get("path_template_time_zone") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path_template_time_zone must be of type String parameters[\"path_template_time_zone\"]");
    }
    if (parameters.containsKey("permissions") && !(parameters.get("permissions") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: permissions must be of type String parameters[\"permissions\"]");
    }
    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }
    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }
    if (parameters.containsKey("send_email_receipt_to_uploader") && !(parameters.get("send_email_receipt_to_uploader") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: send_email_receipt_to_uploader must be of type Boolean parameters[\"send_email_receipt_to_uploader\"]");
    }
    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }
    if (parameters.containsKey("start_access_on_date") && !(parameters.get("start_access_on_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: start_access_on_date must be of type String parameters[\"start_access_on_date\"]");
    }
    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }
    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }
    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("watermark_attachment_delete") && !(parameters.get("watermark_attachment_delete") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_delete must be of type Boolean parameters[\"watermark_attachment_delete\"]");
    }
    if (parameters.containsKey("watermark_attachment_file") && !(parameters.get("watermark_attachment_file") instanceof byte[])) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_file must be of type byte[] parameters[\"watermark_attachment_file\"]");
    }



    String url = String.format("%s%s/bundles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
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



    String url = String.format("%s%s/bundles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
