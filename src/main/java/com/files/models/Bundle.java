package com.files.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Bundle {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public Bundle() {
    this(null, null);
  }

  public Bundle(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Bundle(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Bundle code.  This code forms the end part of the Public URL.
  */
  @Getter
  @Setter
  @JsonProperty("code")
  public String code;

  /**
  * Public URL of Share Link
  */
  @Getter
  @Setter
  @JsonProperty("url")
  public String url;

  /**
  * Public description
  */
  @Getter
  @Setter
  @JsonProperty("description")
  public String description;

  /**
  * Is this bundle password protected?
  */
  @Getter
  @Setter
  @JsonProperty("password_protected")
  public Boolean passwordProtected;

  /**
  * Permissions that apply to Folders in this Share Link.
  */
  @Getter
  @Setter
  @JsonProperty("permissions")
  public String permissions;

  /**
  * Restrict users to previewing files only?
  */
  @Getter
  @Setter
  @JsonProperty("preview_only")
  public Boolean previewOnly;

  /**
  * Show a registration page that captures the downloader's name and email address?
  */
  @Getter
  @Setter
  @JsonProperty("require_registration")
  public Boolean requireRegistration;

  /**
  * Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  */
  @Getter
  @Setter
  @JsonProperty("require_share_recipient")
  public Boolean requireShareRecipient;

  /**
  * Legal text that must be agreed to prior to accessing Bundle.
  */
  @Getter
  @Setter
  @JsonProperty("clickwrap_body")
  public String clickwrapBody;

  /**
  * Custom Form to use
  */
  @Getter
  @Setter
  @JsonProperty("form_field_set")
  public FormFieldSet formFieldSet;

  /**
  * BundleRegistrations can be saved without providing name?
  */
  @Getter
  @Setter
  @JsonProperty("skip_name")
  public Boolean skipName;

  /**
  * BundleRegistrations can be saved without providing email?
  */
  @Getter
  @Setter
  @JsonProperty("skip_email")
  public Boolean skipEmail;

  /**
  * BundleRegistrations can be saved without providing company?
  */
  @Getter
  @Setter
  @JsonProperty("skip_company")
  public Boolean skipCompany;

  /**
  * Bundle ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Bundle created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  */
  @Getter
  @Setter
  @JsonProperty("dont_separate_submissions_by_folder")
  public Boolean dontSeparateSubmissionsByFolder;

  /**
  * Bundle expiration date/time
  */
  @Getter
  @Setter
  @JsonProperty("expires_at")
  public Date expiresAt;

  /**
  * Maximum number of times bundle can be accessed
  */
  @Getter
  @Setter
  @JsonProperty("max_uses")
  public Long maxUses;

  /**
  * Bundle internal note
  */
  @Getter
  @Setter
  @JsonProperty("note")
  public String note;

  /**
  * Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
  */
  @Getter
  @Setter
  @JsonProperty("path_template")
  public String pathTemplate;

  /**
  * Bundle creator user ID
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * Bundle creator username
  */
  @Getter
  @Setter
  @JsonProperty("username")
  public String username;

  /**
  * ID of the clickwrap to use with this bundle.
  */
  @Getter
  @Setter
  @JsonProperty("clickwrap_id")
  public Long clickwrapId;

  /**
  * ID of the associated inbox, if available.
  */
  @Getter
  @Setter
  @JsonProperty("inbox_id")
  public Long inboxId;

  /**
  * Preview watermark image applied to all bundle items.
  */
  @Getter
  @Setter
  @JsonProperty("watermark_attachment")
  public Image watermarkAttachment;

  /**
  * Preview watermark settings applied to all bundle items. Uses the same keys as Behavior.value
  */
  @Getter
  @Setter
  @JsonProperty("watermark_value")
  public Map<String, String> watermarkValue;

  /**
  * Does this bundle have an associated inbox?
  */
  @Getter
  @Setter
  @JsonProperty("has_inbox")
  public Boolean hasInbox;

  /**
  * A list of paths in this bundle
  */
  @Getter
  @Setter
  @JsonProperty("paths")
  public Object[] paths;

  /**
  * Password for this bundle.
  */
  @Getter
  @Setter
  @JsonProperty("password")
  public String password;

  /**
  * Id of Form Field Set to use with this bundle
  */
  @Getter
  @Setter
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  /**
  * Preview watermark image applied to all bundle items.
  */
  @Getter
  @Setter
  @JsonProperty("watermark_attachment_file")
  public byte[] watermarkAttachmentFile;

  /**
  * If true, will delete the file stored in watermark_attachment
  */
  @Getter
  @Setter
  @JsonProperty("watermark_attachment_delete")
  public Boolean watermarkAttachmentDelete;

  /**
  * Send email(s) with a link to bundle
  *
  * Parameters:
  *   to - array(string) - A list of email addresses to share this bundle with. Required unless `recipients` is used.
  *   note - string - Note to include in email.
  *   recipients - array(object) - A list of recipients to share this bundle with. Required unless `to` is used.
  */
  public Bundle share(HashMap<String, Object> parameters) {
    return share(parameters);
  }

  /**
  * Parameters:
  *   paths - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   description - string - Public description
  *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  *   expires_at - string - Bundle expiration date/time
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   note - string - Bundle internal note
  *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
  *   permissions - string - Permissions that apply to Folders in this Share Link.
  *   preview_only - boolean - Restrict users to previewing files only?
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  *   skip_email - boolean - BundleRegistrations can be saved without providing email?
  *   skip_name - boolean - BundleRegistrations can be saved without providing name?
  *   skip_company - boolean - BundleRegistrations can be saved without providing company?
  *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
  *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
  */
  public Bundle update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public Bundle delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      Bundle newObject = Bundle.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either 'asc' or 'desc' direction (e.g. sort_by[last_login_at]=desc). Valid fields are `created_at` and `code`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `created_at`.
  *   filter_like - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `created_at`.
  */
  public static List<Bundle> list() throws IOException{
    return list(null,null);
  }
  public static List<Bundle> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Bundle> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }

    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }

    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }

    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Map<String, String> parameters[\"filter_like\"]");
    }

    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }

    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }

    String url = String.format("%s%s/bundles", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<Bundle>> typeReference = new TypeReference<List<Bundle>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Bundle> all() throws IOException {
    return all(null, null);
  }

  public static List<Bundle> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Bundle ID.
  */
  public static List<Bundle> find() throws IOException{
    return find(null, null,null);
  }
  public static List<Bundle> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Bundle> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<Bundle> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/bundles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<List<Bundle>> typeReference = new TypeReference<List<Bundle>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Bundle> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Bundle> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   paths (required) - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
  *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  *   expires_at - string - Bundle expiration date/time
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   description - string - Public description
  *   note - string - Bundle internal note
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
  *   permissions - string - Permissions that apply to Folders in this Share Link.
  *   preview_only - boolean - Restrict users to previewing files only?
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  *   skip_email - boolean - BundleRegistrations can be saved without providing email?
  *   skip_name - boolean - BundleRegistrations can be saved without providing name?
  *   skip_company - boolean - BundleRegistrations can be saved without providing company?
  *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
  */
  public static Bundle create() throws IOException{
    return create(null,null);
  }
  public static Bundle create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Bundle create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long parameters[\"user_id\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("form_field_set_id") && !(parameters.get("form_field_set_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: form_field_set_id must be of type Long parameters[\"form_field_set_id\"]");
    }

    if (parameters.containsKey("dont_separate_submissions_by_folder") && !(parameters.get("dont_separate_submissions_by_folder") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: dont_separate_submissions_by_folder must be of type Boolean parameters[\"dont_separate_submissions_by_folder\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long parameters[\"max_uses\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }

    if (parameters.containsKey("path_template") && !(parameters.get("path_template") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path_template must be of type String parameters[\"path_template\"]");
    }

    if (parameters.containsKey("permissions") && !(parameters.get("permissions") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permissions must be of type String parameters[\"permissions\"]");
    }

    if (parameters.containsKey("preview_only") && !(parameters.get("preview_only") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: preview_only must be of type Boolean parameters[\"preview_only\"]");
    }

    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }

    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long parameters[\"clickwrap_id\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long parameters[\"inbox_id\"]");
    }

    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }

    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }

    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }

    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }

    if (parameters.containsKey("watermark_attachment_file") && !(parameters.get("watermark_attachment_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_file must be of type byte[] parameters[\"watermark_attachment_file\"]");
    }

    if (!parameters.containsKey("paths") || parameters.get("paths") == null) {
      throw new NullPointerException("Parameter missing: paths parameters[\"paths\"]");
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
  public static Bundle share() throws IOException{
    return share(null, null,null);
  }
  public static Bundle share(Long id,  HashMap<String, Object> parameters) throws IOException {
    return share(id, parameters, null);
  }

  public static Bundle share(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return share(null, parameters, options);
  }

  public static Bundle share(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("to") && !(parameters.get("to") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: to must be of type String[] parameters[\"to\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("recipients") && !(parameters.get("recipients") instanceof Object[] )) {
      throw new IllegalArgumentException("Bad parameter: recipients must be of type Object[] parameters[\"recipients\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/bundles/%s/share", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   paths - array(string) - A list of paths to include in this bundle.
  *   password - string - Password for this bundle.
  *   form_field_set_id - int64 - Id of Form Field Set to use with this bundle
  *   clickwrap_id - int64 - ID of the clickwrap to use with this bundle.
  *   code - string - Bundle code.  This code forms the end part of the Public URL.
  *   description - string - Public description
  *   dont_separate_submissions_by_folder - boolean - Do not create subfolders for files uploaded to this share. Note: there are subtle security pitfalls with allowing anonymous uploads from multiple users to live in the same folder. We strongly discourage use of this option unless absolutely required.
  *   expires_at - string - Bundle expiration date/time
  *   inbox_id - int64 - ID of the associated inbox, if available.
  *   max_uses - int64 - Maximum number of times bundle can be accessed
  *   note - string - Bundle internal note
  *   path_template - string - Template for creating submission subfolders. Can use the uploader's name, email address, ip, company, and any custom form data.
  *   permissions - string - Permissions that apply to Folders in this Share Link.
  *   preview_only - boolean - Restrict users to previewing files only?
  *   require_registration - boolean - Show a registration page that captures the downloader's name and email address?
  *   require_share_recipient - boolean - Only allow access to recipients who have explicitly received the share via an email sent through the Files.com UI?
  *   skip_email - boolean - BundleRegistrations can be saved without providing email?
  *   skip_name - boolean - BundleRegistrations can be saved without providing name?
  *   skip_company - boolean - BundleRegistrations can be saved without providing company?
  *   watermark_attachment_delete - boolean - If true, will delete the file stored in watermark_attachment
  *   watermark_attachment_file - file - Preview watermark image applied to all bundle items.
  */
  public static Bundle update() throws IOException{
    return update(null, null,null);
  }
  public static Bundle update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static Bundle update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static Bundle update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("form_field_set_id") && !(parameters.get("form_field_set_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: form_field_set_id must be of type Long parameters[\"form_field_set_id\"]");
    }

    if (parameters.containsKey("clickwrap_id") && !(parameters.get("clickwrap_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: clickwrap_id must be of type Long parameters[\"clickwrap_id\"]");
    }

    if (parameters.containsKey("code") && !(parameters.get("code") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: code must be of type String parameters[\"code\"]");
    }

    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }

    if (parameters.containsKey("dont_separate_submissions_by_folder") && !(parameters.get("dont_separate_submissions_by_folder") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: dont_separate_submissions_by_folder must be of type Boolean parameters[\"dont_separate_submissions_by_folder\"]");
    }

    if (parameters.containsKey("expires_at") && !(parameters.get("expires_at") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: expires_at must be of type String parameters[\"expires_at\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long parameters[\"inbox_id\"]");
    }

    if (parameters.containsKey("max_uses") && !(parameters.get("max_uses") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_uses must be of type Long parameters[\"max_uses\"]");
    }

    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }

    if (parameters.containsKey("path_template") && !(parameters.get("path_template") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path_template must be of type String parameters[\"path_template\"]");
    }

    if (parameters.containsKey("permissions") && !(parameters.get("permissions") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: permissions must be of type String parameters[\"permissions\"]");
    }

    if (parameters.containsKey("preview_only") && !(parameters.get("preview_only") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: preview_only must be of type Boolean parameters[\"preview_only\"]");
    }

    if (parameters.containsKey("require_registration") && !(parameters.get("require_registration") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_registration must be of type Boolean parameters[\"require_registration\"]");
    }

    if (parameters.containsKey("require_share_recipient") && !(parameters.get("require_share_recipient") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_share_recipient must be of type Boolean parameters[\"require_share_recipient\"]");
    }

    if (parameters.containsKey("skip_email") && !(parameters.get("skip_email") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: skip_email must be of type Boolean parameters[\"skip_email\"]");
    }

    if (parameters.containsKey("skip_name") && !(parameters.get("skip_name") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: skip_name must be of type Boolean parameters[\"skip_name\"]");
    }

    if (parameters.containsKey("skip_company") && !(parameters.get("skip_company") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: skip_company must be of type Boolean parameters[\"skip_company\"]");
    }

    if (parameters.containsKey("watermark_attachment_delete") && !(parameters.get("watermark_attachment_delete") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_delete must be of type Boolean parameters[\"watermark_attachment_delete\"]");
    }

    if (parameters.containsKey("watermark_attachment_file") && !(parameters.get("watermark_attachment_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: watermark_attachment_file must be of type byte[] parameters[\"watermark_attachment_file\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/bundles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static Bundle delete() throws IOException{
    return delete(null, null,null);
  }
  public static Bundle delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static Bundle delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Bundle delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/bundles/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<Bundle> typeReference = new TypeReference<Bundle>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Bundle destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Bundle destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


