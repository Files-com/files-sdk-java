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
public class Folder implements ModelInterface {
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


  public Folder() {
    this(null, null);
  }

  public Folder(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Folder(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * File/Folder path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
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
  * User ID of the User who created the file/folder
  */
  @JsonProperty("created_by_id")
  public Long createdById;

  public Long getCreatedById() {
    return createdById;
  }

  public void setCreatedById(Long createdById) {
    this.createdById = createdById;
  }

  /**
  * ID of the API key that created the file/folder
  */
  @JsonProperty("created_by_api_key_id")
  public Long createdByApiKeyId;

  public Long getCreatedByApiKeyId() {
    return createdByApiKeyId;
  }

  public void setCreatedByApiKeyId(Long createdByApiKeyId) {
    this.createdByApiKeyId = createdByApiKeyId;
  }

  /**
  * ID of the AS2 Incoming Message that created the file/folder
  */
  @JsonProperty("created_by_as2_incoming_message_id")
  public Long createdByAs2IncomingMessageId;

  public Long getCreatedByAs2IncomingMessageId() {
    return createdByAs2IncomingMessageId;
  }

  public void setCreatedByAs2IncomingMessageId(Long createdByAs2IncomingMessageId) {
    this.createdByAs2IncomingMessageId = createdByAs2IncomingMessageId;
  }

  /**
  * ID of the Automation that created the file/folder
  */
  @JsonProperty("created_by_automation_id")
  public Long createdByAutomationId;

  public Long getCreatedByAutomationId() {
    return createdByAutomationId;
  }

  public void setCreatedByAutomationId(Long createdByAutomationId) {
    this.createdByAutomationId = createdByAutomationId;
  }

  /**
  * ID of the Bundle Registration that created the file/folder
  */
  @JsonProperty("created_by_bundle_registration_id")
  public Long createdByBundleRegistrationId;

  public Long getCreatedByBundleRegistrationId() {
    return createdByBundleRegistrationId;
  }

  public void setCreatedByBundleRegistrationId(Long createdByBundleRegistrationId) {
    this.createdByBundleRegistrationId = createdByBundleRegistrationId;
  }

  /**
  * ID of the Inbox that created the file/folder
  */
  @JsonProperty("created_by_inbox_id")
  public Long createdByInboxId;

  public Long getCreatedByInboxId() {
    return createdByInboxId;
  }

  public void setCreatedByInboxId(Long createdByInboxId) {
    this.createdByInboxId = createdByInboxId;
  }

  /**
  * ID of the Remote Server that created the file/folder
  */
  @JsonProperty("created_by_remote_server_id")
  public Long createdByRemoteServerId;

  public Long getCreatedByRemoteServerId() {
    return createdByRemoteServerId;
  }

  public void setCreatedByRemoteServerId(Long createdByRemoteServerId) {
    this.createdByRemoteServerId = createdByRemoteServerId;
  }

  /**
  * ID of the Remote Server Sync that created the file/folder
  */
  @JsonProperty("created_by_remote_server_sync_id")
  public Long createdByRemoteServerSyncId;

  public Long getCreatedByRemoteServerSyncId() {
    return createdByRemoteServerSyncId;
  }

  public void setCreatedByRemoteServerSyncId(Long createdByRemoteServerSyncId) {
    this.createdByRemoteServerSyncId = createdByRemoteServerSyncId;
  }

  /**
  * Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
  */
  @JsonProperty("custom_metadata")
  public Map<String, String> customMetadata;

  public Map<String, String> getCustomMetadata() {
    return customMetadata;
  }

  public void setCustomMetadata(Map<String, String> customMetadata) {
    this.customMetadata = customMetadata;
  }

  /**
  * File/Folder display name
  */
  @JsonProperty("display_name")
  public String displayName;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
  * Type: `directory` or `file`.
  */
  @JsonProperty("type")
  public String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  /**
  * File/Folder size
  */
  @JsonProperty("size")
  public Long size;

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  /**
  * File created date/time
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * User ID of the User who last modified the file/folder
  */
  @JsonProperty("last_modified_by_id")
  public Long lastModifiedById;

  public Long getLastModifiedById() {
    return lastModifiedById;
  }

  public void setLastModifiedById(Long lastModifiedById) {
    this.lastModifiedById = lastModifiedById;
  }

  /**
  * ID of the API key that last modified the file/folder
  */
  @JsonProperty("last_modified_by_api_key_id")
  public Long lastModifiedByApiKeyId;

  public Long getLastModifiedByApiKeyId() {
    return lastModifiedByApiKeyId;
  }

  public void setLastModifiedByApiKeyId(Long lastModifiedByApiKeyId) {
    this.lastModifiedByApiKeyId = lastModifiedByApiKeyId;
  }

  /**
  * ID of the Automation that last modified the file/folder
  */
  @JsonProperty("last_modified_by_automation_id")
  public Long lastModifiedByAutomationId;

  public Long getLastModifiedByAutomationId() {
    return lastModifiedByAutomationId;
  }

  public void setLastModifiedByAutomationId(Long lastModifiedByAutomationId) {
    this.lastModifiedByAutomationId = lastModifiedByAutomationId;
  }

  /**
  * ID of the Bundle Registration that last modified the file/folder
  */
  @JsonProperty("last_modified_by_bundle_registration_id")
  public Long lastModifiedByBundleRegistrationId;

  public Long getLastModifiedByBundleRegistrationId() {
    return lastModifiedByBundleRegistrationId;
  }

  public void setLastModifiedByBundleRegistrationId(Long lastModifiedByBundleRegistrationId) {
    this.lastModifiedByBundleRegistrationId = lastModifiedByBundleRegistrationId;
  }

  /**
  * ID of the Remote Server that last modified the file/folder
  */
  @JsonProperty("last_modified_by_remote_server_id")
  public Long lastModifiedByRemoteServerId;

  public Long getLastModifiedByRemoteServerId() {
    return lastModifiedByRemoteServerId;
  }

  public void setLastModifiedByRemoteServerId(Long lastModifiedByRemoteServerId) {
    this.lastModifiedByRemoteServerId = lastModifiedByRemoteServerId;
  }

  /**
  * ID of the Remote Server Sync that last modified the file/folder
  */
  @JsonProperty("last_modified_by_remote_server_sync_id")
  public Long lastModifiedByRemoteServerSyncId;

  public Long getLastModifiedByRemoteServerSyncId() {
    return lastModifiedByRemoteServerSyncId;
  }

  public void setLastModifiedByRemoteServerSyncId(Long lastModifiedByRemoteServerSyncId) {
    this.lastModifiedByRemoteServerSyncId = lastModifiedByRemoteServerSyncId;
  }

  /**
  * File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
  */
  @JsonProperty("mtime")
  public Date mtime;

  public Date getMtime() {
    return mtime;
  }

  public void setMtime(Date mtime) {
    this.mtime = mtime;
  }

  /**
  * File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop :Cloud syncing to preserve modified at times.
  */
  @JsonProperty("provided_mtime")
  public Date providedMtime;

  public Date getProvidedMtime() {
    return providedMtime;
  }

  public void setProvidedMtime(Date providedMtime) {
    this.providedMtime = providedMtime;
  }

  /**
  * File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @JsonProperty("crc32")
  public String crc32;

  public String getCrc32() {
    return crc32;
  }

  public void setCrc32(String crc32) {
    this.crc32 = crc32;
  }

  /**
  * File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @JsonProperty("md5")
  public String md5;

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  /**
  * File SHA1 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @JsonProperty("sha1")
  public String sha1;

  public String getSha1() {
    return sha1;
  }

  public void setSha1(String sha1) {
    this.sha1 = sha1;
  }

  /**
  * File SHA256 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
  */
  @JsonProperty("sha256")
  public String sha256;

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  /**
  * MIME Type.  This is determined by the filename extension and is not stored separately internally.
  */
  @JsonProperty("mime_type")
  public String mimeType;

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
  * Region location
  */
  @JsonProperty("region")
  public String region;

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  /**
  * A short string representing the current user's permissions.  Can be `r` (Read),`w` (Write),`d` (Delete), `l` (List) or any combination
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
  * Are subfolders locked and unable to be modified?
  */
  @JsonProperty("subfolders_locked?")
  public Boolean subfoldersLocked;

  public Boolean getSubfoldersLocked() {
    return subfoldersLocked;
  }

  public void setSubfoldersLocked(Boolean subfoldersLocked) {
    this.subfoldersLocked = subfoldersLocked;
  }

  /**
  * Is this folder locked and unable to be modified?
  */
  @JsonProperty("is_locked")
  public Boolean isLocked;

  public Boolean getIsLocked() {
    return isLocked;
  }

  public void setIsLocked(Boolean isLocked) {
    this.isLocked = isLocked;
  }

  /**
  * Link to download file. Provided only in response to a download request.
  */
  @JsonProperty("download_uri")
  public String downloadUri;

  public String getDownloadUri() {
    return downloadUri;
  }

  public void setDownloadUri(String downloadUri) {
    this.downloadUri = downloadUri;
  }

  /**
  * Bookmark/priority color of file/folder
  */
  @JsonProperty("priority_color")
  public String priorityColor;

  public String getPriorityColor() {
    return priorityColor;
  }

  public void setPriorityColor(String priorityColor) {
    this.priorityColor = priorityColor;
  }

  /**
  * File preview ID
  */
  @JsonProperty("preview_id")
  public Long previewId;

  public Long getPreviewId() {
    return previewId;
  }

  public void setPreviewId(Long previewId) {
    this.previewId = previewId;
  }

  /**
  * File preview
  */
  @JsonProperty("preview")
  public Preview preview;

  public Preview getPreview() {
    return preview;
  }

  public void setPreview(Preview preview) {
    this.preview = preview;
  }

  /**
  * Create parent directories if they do not exist?
  */
  @JsonProperty("mkdir_parents")
  public Boolean mkdirParents;

  public Boolean getMkdirParents() {
    return mkdirParents;
  }

  public void setMkdirParents(Boolean mkdirParents) {
    this.mkdirParents = mkdirParents;
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Folder.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   path (required) - string - Path to operate on.
  *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
  *   sort_by - object - Search by field and direction. Valid fields are `path`, `size`, `modified_at_datetime`, `provided_modified_at`.  Valid directions are `asc` and `desc`.  Defaults to `{"path":"asc"}`.
  *   search - string - If specified, will search the folders/files list by name. Ignores text before last `/`. This is the same API used by the search bar in the web UI when running 'Search This Folder'.  Search results are a best effort, not real time, and not guaranteed to perfectly match the latest folder listing.  Results may be truncated if more than 1,000 possible matches exist.  This field should only be used for ad-hoc (human) searching, and not as part of an automated process.
  *   search_custom_metadata_key - string - If provided, the search string in `search` will search for files where this custom metadata key matches the value sent in `search`.  Set this to `*` to allow any metadata key to match the value sent in `search`.
  *   search_all - boolean - Search entire site?  If true, we will search the entire site.  Do not provide a path when using this parameter.  This is the same API used by the search bar in the web UI when running 'Search All Files'.  Search results are a best effort, not real time, and not guaranteed to match every file.  This field should only be used for ad-hoc (human) searching, and not as part of an automated process.
  *   with_previews - boolean - Include file previews?
  *   with_priority_color - boolean - Include file priority color information?
  *   type - string - Type of objects to return.  Can be `folder` or `file`.
  *   modified_at_datetime - string - If provided, will only return files/folders modified after this time. Can be used only in combination with `type` filter.
  */
  public static ListIterator<File> listFor() throws RuntimeException {
    return listFor(null, null, null);
  }

  public static ListIterator<File> listFor(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return listFor(path, parameters, null);
  }

  public static ListIterator<File> listFor(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return listFor(null, parameters, options);
  }

  public static ListIterator<File> listFor(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("search") && !(parameters.get("search") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: search must be of type String parameters[\"search\"]");
    }
    if (parameters.containsKey("search_custom_metadata_key") && !(parameters.get("search_custom_metadata_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: search_custom_metadata_key must be of type String parameters[\"search_custom_metadata_key\"]");
    }
    if (parameters.containsKey("search_all") && !(parameters.get("search_all") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: search_all must be of type Boolean parameters[\"search_all\"]");
    }
    if (parameters.containsKey("with_previews") && !(parameters.get("with_previews") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_previews must be of type Boolean parameters[\"with_previews\"]");
    }
    if (parameters.containsKey("with_priority_color") && !(parameters.get("with_priority_color") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_priority_color must be of type Boolean parameters[\"with_priority_color\"]");
    }
    if (parameters.containsKey("type") && !(parameters.get("type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: type must be of type String parameters[\"type\"]");
    }
    if (parameters.containsKey("modified_at_datetime") && !(parameters.get("modified_at_datetime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: modified_at_datetime must be of type String parameters[\"modified_at_datetime\"]");
    }



    String url = String.format("%s%s/folders/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(path));

    TypeReference<List<File>> typeReference = new TypeReference<List<File>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   path (required) - string - Path to operate on.
  *   mkdir_parents - boolean - Create parent directories if they do not exist?
  *   provided_mtime - string - User provided modification time.
  */
  public static Folder create() throws RuntimeException {
    return create(null, null, null);
  }

  public static Folder create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  public static Folder create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  public static Folder create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }


    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("mkdir_parents") && !(parameters.get("mkdir_parents") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mkdir_parents must be of type Boolean parameters[\"mkdir_parents\"]");
    }
    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }



    String url = String.format("%s%s/folders/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(path));

    TypeReference<Folder> typeReference = new TypeReference<Folder>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
