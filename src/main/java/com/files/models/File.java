package com.files.models;

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
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class File implements ModelInterface {
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

  // One mapper for every File, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();

  // Opens a download; it is not an attribute to serialize when saving the File.
  @JsonIgnore
  public FilesInputStream getInputStream() throws IOException {
    return getInputStream(0L, 0L);
  }

  public FilesInputStream getInputStream(long start) throws IOException {
    return getInputStream(start, 0L);
  }

  public FilesInputStream getInputStream(long start, long end) throws IOException {
    return filesClient().transfers().openDownload(this);
  }

  /**
   * Saves the File's contents to the local path, with the client that returned this File. The
   * contents are checked before they replace anything at the path; see
   * {@link com.files.net.Transfers#download(File, java.nio.file.Path, com.files.net.DownloadOptions)}.
   */
  public void saveAsLocalFile(String path) throws IOException {
    filesClient().transfers().download(this, java.nio.file.Paths.get(path), null);
  }

  public static File completeUpload(String path, HashMap<String, Object> parameters) throws IOException {
    return completeUpload(path, parameters, null);
  }

  public static File completeUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    options = options != null ? new HashMap<String, Object>(options) : new HashMap<String, Object>();

    if (path != null) {
      parameters.put("path", path);
    }
    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }
    if (!parameters.containsKey("action")) {
      parameters.put("action", "put");
    }
    String url = String.format("/files/%s", UrlUtils.encodeUrlPath((String) parameters.get("path")));
    TypeReference<File> typeReference = new TypeReference<File>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }

  private static String underscoreDestinationPath(String root, Long id, String relativePath) {
    return PathUtils.normalize("_/" + root + "/" + id.toString() + "/" + (relativePath == null ? "" : relativePath));
  }

  public static FileUploadPart uploadToRemoteServer(Long remoteServerId, String destinationPath) throws RuntimeException {
    return uploadToRemoteServer(remoteServerId, destinationPath, null, null);
  }

  public static FileUploadPart uploadToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return uploadToRemoteServer(remoteServerId, destinationPath, parameters, null);
  }

  public static FileUploadPart uploadToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath), parameters, options);
  }

  public static FileAction copyToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return copyToRemoteServer(path, remoteServerId, destinationPath, parameters, null);
  }

  public static FileAction copyToRemoteServer(String path, Long remoteServerId, String destinationPath) throws RuntimeException {
    return copyToRemoteServer(path, remoteServerId, destinationPath, null, null);
  }

  public static FileAction copyToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return copy(path, parameters, options);
  }

  public static FileAction moveToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return moveToRemoteServer(path, remoteServerId, destinationPath, parameters, null);
  }

  public static FileAction moveToRemoteServer(String path, Long remoteServerId, String destinationPath) throws RuntimeException {
    return moveToRemoteServer(path, remoteServerId, destinationPath, null, null);
  }

  public static FileAction moveToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return move(path, parameters, options);
  }

  public FileAction copyToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return copy(parameters);
  }

  public FileAction copyToRemoteServer(Long remoteServerId, String destinationPath) throws IOException {
    return copyToRemoteServer(remoteServerId, destinationPath, null);
  }

  public FileAction moveToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return move(parameters);
  }

  public FileAction moveToRemoteServer(Long remoteServerId, String destinationPath) throws IOException {
    return moveToRemoteServer(remoteServerId, destinationPath, null);
  }

  public static FileUploadPart uploadToSnapshot(Long snapshotId, String destinationPath) throws RuntimeException {
    return uploadToSnapshot(snapshotId, destinationPath, null, null);
  }

  public static FileUploadPart uploadToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return uploadToSnapshot(snapshotId, destinationPath, parameters, null);
  }

  public static FileUploadPart uploadToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(underscoreDestinationPath("Snapshots", snapshotId, destinationPath), parameters, options);
  }

  public static FileAction copyToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return copyToSnapshot(path, snapshotId, destinationPath, parameters, null);
  }

  public static FileAction copyToSnapshot(String path, Long snapshotId, String destinationPath) throws RuntimeException {
    return copyToSnapshot(path, snapshotId, destinationPath, null, null);
  }

  public static FileAction copyToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return copy(path, parameters, options);
  }

  public static FileAction moveToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return moveToSnapshot(path, snapshotId, destinationPath, parameters, null);
  }

  public static FileAction moveToSnapshot(String path, Long snapshotId, String destinationPath) throws RuntimeException {
    return moveToSnapshot(path, snapshotId, destinationPath, null, null);
  }

  public static FileAction moveToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return move(path, parameters, options);
  }

  public FileAction copyToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return copy(parameters);
  }

  public FileAction copyToSnapshot(Long snapshotId, String destinationPath) throws IOException {
    return copyToSnapshot(snapshotId, destinationPath, null);
  }

  public FileAction moveToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return move(parameters);
  }

  public FileAction moveToSnapshot(Long snapshotId, String destinationPath) throws IOException {
    return moveToSnapshot(snapshotId, destinationPath, null);
  }

  public static FileUploadPart uploadToChildSite(Long siteId, String destinationPath) throws RuntimeException {
    return uploadToChildSite(siteId, destinationPath, null, null);
  }

  public static FileUploadPart uploadToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return uploadToChildSite(siteId, destinationPath, parameters, null);
  }

  public static FileUploadPart uploadToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(underscoreDestinationPath("Sites", siteId, destinationPath), parameters, options);
  }

  public static FileAction copyToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return copyToChildSite(path, siteId, destinationPath, parameters, null);
  }

  public static FileAction copyToChildSite(String path, Long siteId, String destinationPath) throws RuntimeException {
    return copyToChildSite(path, siteId, destinationPath, null, null);
  }

  public static FileAction copyToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return copy(path, parameters, options);
  }

  public static FileAction moveToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return moveToChildSite(path, siteId, destinationPath, parameters, null);
  }

  public static FileAction moveToChildSite(String path, Long siteId, String destinationPath) throws RuntimeException {
    return moveToChildSite(path, siteId, destinationPath, null, null);
  }

  public static FileAction moveToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return move(path, parameters, options);
  }

  public FileAction copyToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return copy(parameters);
  }

  public FileAction copyToChildSite(Long siteId, String destinationPath) throws IOException {
    return copyToChildSite(siteId, destinationPath, null);
  }

  public FileAction moveToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return move(parameters);
  }

  public FileAction moveToChildSite(Long siteId, String destinationPath) throws IOException {
    return moveToChildSite(siteId, destinationPath, null);
  }


  public File() {
    this(null, null);
  }

  public File(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public File(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
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
   * ID of the Sync that created the file/folder
   */
  @JsonProperty("created_by_sync_id")
  public Long createdBySyncId;

  public Long getCreatedBySyncId() {
    return createdBySyncId;
  }

  public void setCreatedBySyncId(Long createdBySyncId) {
    this.createdBySyncId = createdBySyncId;
  }

  /**
   * Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
   */
  @JsonProperty("custom_metadata")
  public Object customMetadata;

  public Object getCustomMetadata() {
    return customMetadata;
  }

  public void setCustomMetadata(Object customMetadata) {
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
   * ID of the Sync that last modified the file/folder
   */
  @JsonProperty("last_modified_by_sync_id")
  public Long lastModifiedBySyncId;

  public Long getLastModifiedBySyncId() {
    return lastModifiedBySyncId;
  }

  public void setLastModifiedBySyncId(Long lastModifiedBySyncId) {
    this.lastModifiedBySyncId = lastModifiedBySyncId;
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
   * Optional direct connection information for direct Agent transfer attempts
   */
  @JsonProperty("direct_connection_info")
  public DirectConnectionInfo directConnectionInfo;

  public DirectConnectionInfo getDirectConnectionInfo() {
    return directConnectionInfo;
  }

  public void setDirectConnectionInfo(DirectConnectionInfo directConnectionInfo) {
    this.directConnectionInfo = directConnectionInfo;
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
   * The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
   */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Length of file.
   */
  @JsonProperty("length")
  public Long length;

  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
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

  /**
   * Part if uploading a part.
   */
  @JsonProperty("part")
  public Long part;

  public Long getPart() {
    return part;
  }

  public void setPart(Long part) {
    this.part = part;
  }

  /**
   * How many parts to fetch?
   */
  @JsonProperty("parts")
  public Long parts;

  public Long getParts() {
    return parts;
  }

  public void setParts(Long parts) {
    this.parts = parts;
  }

  /**
   */
  @JsonProperty("ref")
  public String ref;

  public String getRef() {
    return ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  /**
   * File byte offset to restart from.
   */
  @JsonProperty("restart")
  public Long restart;

  public Long getRestart() {
    return restart;
  }

  public void setRestart(Long restart) {
    this.restart = restart;
  }

  /**
   * If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?
   */
  @JsonProperty("copy_behaviors")
  public Boolean copyBehaviors;

  public Boolean getCopyBehaviors() {
    return copyBehaviors;
  }

  public void setCopyBehaviors(Boolean copyBehaviors) {
    this.copyBehaviors = copyBehaviors;
  }

  /**
   * If copying folder, copy just the structure?
   */
  @JsonProperty("structure")
  public String structure;

  public String getStructure() {
    return structure;
  }

  public void setStructure(String structure) {
    this.structure = structure;
  }

  /**
   * Allow file rename instead of overwrite?
   */
  @JsonProperty("with_rename")
  public Boolean withRename;

  public Boolean getWithRename() {
    return withRename;
  }

  public void setWithRename(Boolean withRename) {
    this.withRename = withRename;
  }

  /**
   * If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.
   */
  @JsonProperty("buffered_upload")
  public Boolean bufferedUpload;

  public Boolean getBufferedUpload() {
    return bufferedUpload;
  }

  public void setBufferedUpload(Boolean bufferedUpload) {
    this.bufferedUpload = bufferedUpload;
  }

  /**
   * Include optional direct connection information for a direct Agent transfer attempt?
   */
  @JsonProperty("with_direct_connection_info")
  public Boolean withDirectConnectionInfo;

  public Boolean getWithDirectConnectionInfo() {
    return withDirectConnectionInfo;
  }

  public void setWithDirectConnectionInfo(Boolean withDirectConnectionInfo) {
    this.withDirectConnectionInfo = withDirectConnectionInfo;
  }

  /**
   * Download File
   *
   * Parameters:
   *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
   *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
   *   with_previews - boolean - Include file preview information?
   *   with_priority_color - boolean - Include file priority color information?
   *   with_direct_connection_info - boolean - Include optional direct connection information for a direct Agent transfer attempt?
   */
  public File download(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().download(this.path, parameters, this.options);
  }

  /**
   * Updates this File. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Parameters:
   *   custom_metadata - object - Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
   *   provided_mtime - string - Modified time of file.
   *   priority_color - string - Priority/Bookmark color of file.
   */
  public File update(HashMap<String, Object> parameters) throws IOException {
    File updated = filesClient().files().update(this.path, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Parameters:
   *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().files().delete(this.path, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  /**
   * List the contents of a ZIP file
   */
  public ListIterator<ZipListEntry> zipListContents(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().zipListContents(this.path, parameters, this.options);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * Parameters:
   *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
   */
  public FileAction beginDelete(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().beginDelete(this.path, parameters, this.options);
  }

  /**
   * Copy File/Folder
   *
   * Parameters:
   *   destination (required) - string - Copy destination path.
   *   copy_behaviors - boolean - If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?
   *   structure - boolean - Copy structure only?
   *   overwrite - boolean - Overwrite existing file(s) in the destination?
   */
  public FileAction copy(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().copy(this.path, parameters, this.options);
  }

  /**
   * Move File/Folder
   *
   * Parameters:
   *   destination (required) - string - Move destination path.
   *   overwrite - boolean - Overwrite existing file(s) in the destination?
   */
  public FileAction move(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().move(this.path, parameters, this.options);
  }

  /**
   * Transform a file and save the output to a destination path
   *
   * Parameters:
   *   destination (required) - string - Destination file path for the transformed output.
   *   transform_type (required) - string - Transform type. Supported values are `image_convert`, `document_convert`, and `files_transform_script_execute`.
   *   target_format (required) - string - Destination format to create.
   *   script - string - Files TransformScript source. Required when transform_type is `files_transform_script_execute`.
   *   width - int64 - Maximum output width for image_convert.
   *   height - int64 - Maximum output height for image_convert.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction transform(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().transform(this.path, parameters, this.options);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * Parameters:
   *   destination (required) - string - Destination file path for the decrypted file.
   *   gpg_key_ids - array(int64) - GPG Key IDs to decrypt with. If omitted, every accessible private GPG key in the source workspace is used.
   *   gpg_key_partner_id - int64 - Partner ID whose GPG keys should be used for decryption.
   *   use_all_private_keys - boolean - Use every accessible private GPG key in the source workspace for decryption.
   *   ignore_mdc_error - boolean - Ignore errors from the MDC (modification detection code) check.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction gpgDecrypt(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().gpgDecrypt(this.path, parameters, this.options);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * Parameters:
   *   destination (required) - string - Destination file path for the encrypted file.
   *   gpg_key_ids - array(int64) - GPG Key IDs to encrypt with.
   *   gpg_key_partner_id - int64 - Partner ID whose GPG keys should be used for encryption.
   *   signing_key_id - int64 - Optional GPG Key ID to sign with.
   *   armor - boolean - Output ASCII-armored encrypted data.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction gpgEncrypt(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().gpgEncrypt(this.path, parameters, this.options);
  }

  /**
   * Extract a ZIP file to a destination folder
   *
   * Parameters:
   *   destination (required) - string - Destination folder path for extracted files.
   *   filename - string - Optional single entry filename to extract.
   *   overwrite - boolean - Overwrite existing files in the destination?
   */
  public FileAction unzip(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().unzip(this.path, parameters, this.options);
  }

  /**
   * Begin File Upload
   *
   * Parameters:
   *   mkdir_parents - boolean - Create parent directories if they do not exist?
   *   part - int64 - Part if uploading a part.
   *   parts - int64 - How many parts to fetch?
   *   ref - string -
   *   restart - int64 - File byte offset to restart from.
   *   size - int64 - Total bytes of file being uploaded (include bytes being retained if appending/restarting).
   *   with_rename - boolean - Allow file rename instead of overwrite?
   *   buffered_upload - boolean - If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.
   *   with_direct_connection_info - boolean - Include optional direct connection information for a direct Agent transfer attempt?
   */
  public FileUploadPart beginUpload(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().beginUpload(this.path, parameters, this.options);
  }


  /**
   * Creates this File from its current attributes.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    filesClient().files().create(parameters, this.options);
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(File canonical) {
    this.path = canonical.path;
    this.createdById = canonical.createdById;
    this.createdByApiKeyId = canonical.createdByApiKeyId;
    this.createdByAs2IncomingMessageId = canonical.createdByAs2IncomingMessageId;
    this.createdByAutomationId = canonical.createdByAutomationId;
    this.createdByBundleRegistrationId = canonical.createdByBundleRegistrationId;
    this.createdByInboxId = canonical.createdByInboxId;
    this.createdByRemoteServerId = canonical.createdByRemoteServerId;
    this.createdBySyncId = canonical.createdBySyncId;
    this.customMetadata = canonical.customMetadata;
    this.displayName = canonical.displayName;
    this.type = canonical.type;
    this.size = canonical.size;
    this.createdAt = canonical.createdAt;
    this.lastModifiedById = canonical.lastModifiedById;
    this.lastModifiedByApiKeyId = canonical.lastModifiedByApiKeyId;
    this.lastModifiedByAutomationId = canonical.lastModifiedByAutomationId;
    this.lastModifiedByBundleRegistrationId = canonical.lastModifiedByBundleRegistrationId;
    this.lastModifiedByRemoteServerId = canonical.lastModifiedByRemoteServerId;
    this.lastModifiedBySyncId = canonical.lastModifiedBySyncId;
    this.mtime = canonical.mtime;
    this.providedMtime = canonical.providedMtime;
    this.crc32 = canonical.crc32;
    this.md5 = canonical.md5;
    this.sha1 = canonical.sha1;
    this.sha256 = canonical.sha256;
    this.mimeType = canonical.mimeType;
    this.region = canonical.region;
    this.permissions = canonical.permissions;
    this.subfoldersLocked = canonical.subfoldersLocked;
    this.isLocked = canonical.isLocked;
    this.downloadUri = canonical.downloadUri;
    this.directConnectionInfo = canonical.directConnectionInfo;
    this.priorityColor = canonical.priorityColor;
    this.previewId = canonical.previewId;
    this.preview = canonical.preview;
    this.action = canonical.action;
    this.length = canonical.length;
    this.mkdirParents = canonical.mkdirParents;
    this.part = canonical.part;
    this.parts = canonical.parts;
    this.ref = canonical.ref;
    this.restart = canonical.restart;
    this.copyBehaviors = canonical.copyBehaviors;
    this.structure = canonical.structure;
    this.withRename = canonical.withRename;
    this.bufferedUpload = canonical.bufferedUpload;
    this.withDirectConnectionInfo = canonical.withDirectConnectionInfo;
  }

  /**
   * Download File
   *
   * Parameters:
   *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
   *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
   *   with_previews - boolean - Include file preview information?
   *   with_priority_color - boolean - Include file priority color information?
   *   with_direct_connection_info - boolean - Include optional direct connection information for a direct Agent transfer attempt?
   */
  public static File download() throws RuntimeException {
    return download(null, null, null);
  }

  public static File download(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return download(path, parameters, null);
  }

  public static File download(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return download(null, parameters, options);
  }

  public static File download(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().download(path, parameters, options);
  }


  /**
   * Parameters:
   *   path (required) - string - Path to operate on.
   *   action - string - The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
   *   custom_metadata - object - Custom metadata map to save when `action=end` completes the upload.  Replaces existing metadata; an empty map clears it.  No separate metadata-edit permission is required.  Supported on native files and configured remote mounts, excluding remote server automount paths.  Limited to 32 keys, 256 characters per key and 1024 characters per value.
   *   etags[etag] (required) - array(string) - etag identifier.
   *   etags[part] (required) - array(int64) - Part number.
   *   length - int64 - Length of file.
   *   mkdir_parents - boolean - Create parent directories if they do not exist?
   *   part - int64 - Part if uploading a part.
   *   parts - int64 - How many parts to fetch?
   *   provided_mtime - string - User provided modification time.
   *   ref - string -
   *   restart - int64 - File byte offset to restart from.
   *   size - int64 - Size of file.
   *   copy_behaviors - boolean - If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?
   *   structure - string - If copying folder, copy just the structure?
   *   with_rename - boolean - Allow file rename instead of overwrite?
   *   buffered_upload - boolean - If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.
   *   with_direct_connection_info - boolean - Include optional direct connection information for a direct Agent transfer attempt?
   */
  public static FileUploadPart create() throws RuntimeException {
    return create(null, null, null);
  }

  public static FileUploadPart create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  public static FileUploadPart create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  public static FileUploadPart create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().create(path, parameters, options);
  }


  /**
   * Parameters:
   *   custom_metadata - object - Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
   *   provided_mtime - string - Modified time of file.
   *   priority_color - string - Priority/Bookmark color of file.
   */
  public static File update() throws RuntimeException {
    return update(null, null, null);
  }

  public static File update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  public static File update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static File update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().update(path, parameters, options);
  }


  /**
   * Parameters:
   *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().files().delete(path, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

  /**
   * Parameters:
   *   path (required) - string - Path to operate on.
   *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
   *   with_previews - boolean - Include file preview information?
   *   with_priority_color - boolean - Include file priority color information?
   */
  public static File find() throws RuntimeException {
    return find(null, null, null);
  }

  public static File find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  public static File find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static File find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().find(path, parameters, options);
  }

  public static File get() throws RuntimeException {
    return get(null, null, null);
  }

  public static File get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
   * List the contents of a ZIP file
   */
  public static ListIterator<ZipListEntry> zipListContents() throws RuntimeException {
    return zipListContents(null, null, null);
  }

  public static ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return zipListContents(path, parameters, null);
  }

  public static ListIterator<ZipListEntry> zipListContents(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return zipListContents(null, parameters, options);
  }

  public static ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().zipListContents(path, parameters, options);
  }


  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * Parameters:
   *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
   */
  public static FileAction beginDelete() throws RuntimeException {
    return beginDelete(null, null, null);
  }

  public static FileAction beginDelete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginDelete(path, parameters, null);
  }

  public static FileAction beginDelete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginDelete(null, parameters, options);
  }

  public static FileAction beginDelete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().beginDelete(path, parameters, options);
  }


  /**
   * Copy File/Folder
   *
   * Parameters:
   *   destination (required) - string - Copy destination path.
   *   copy_behaviors - boolean - If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?
   *   structure - boolean - Copy structure only?
   *   overwrite - boolean - Overwrite existing file(s) in the destination?
   */
  public static FileAction copy() throws RuntimeException {
    return copy(null, null, null);
  }

  public static FileAction copy(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return copy(path, parameters, null);
  }

  public static FileAction copy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return copy(null, parameters, options);
  }

  public static FileAction copy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().copy(path, parameters, options);
  }


  /**
   * Move File/Folder
   *
   * Parameters:
   *   destination (required) - string - Move destination path.
   *   overwrite - boolean - Overwrite existing file(s) in the destination?
   */
  public static FileAction move() throws RuntimeException {
    return move(null, null, null);
  }

  public static FileAction move(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return move(path, parameters, null);
  }

  public static FileAction move(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return move(null, parameters, options);
  }

  public static FileAction move(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().move(path, parameters, options);
  }


  /**
   * Transform a file and save the output to a destination path
   *
   * Parameters:
   *   destination (required) - string - Destination file path for the transformed output.
   *   transform_type (required) - string - Transform type. Supported values are `image_convert`, `document_convert`, and `files_transform_script_execute`.
   *   target_format (required) - string - Destination format to create.
   *   script - string - Files TransformScript source. Required when transform_type is `files_transform_script_execute`.
   *   width - int64 - Maximum output width for image_convert.
   *   height - int64 - Maximum output height for image_convert.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public static FileAction transform() throws RuntimeException {
    return transform(null, null, null);
  }

  public static FileAction transform(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return transform(path, parameters, null);
  }

  public static FileAction transform(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return transform(null, parameters, options);
  }

  public static FileAction transform(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().transform(path, parameters, options);
  }


  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * Parameters:
   *   destination (required) - string - Destination file path for the decrypted file.
   *   gpg_key_ids - array(int64) - GPG Key IDs to decrypt with. If omitted, every accessible private GPG key in the source workspace is used.
   *   gpg_key_partner_id - int64 - Partner ID whose GPG keys should be used for decryption.
   *   use_all_private_keys - boolean - Use every accessible private GPG key in the source workspace for decryption.
   *   ignore_mdc_error - boolean - Ignore errors from the MDC (modification detection code) check.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public static FileAction gpgDecrypt() throws RuntimeException {
    return gpgDecrypt(null, null, null);
  }

  public static FileAction gpgDecrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgDecrypt(path, parameters, null);
  }

  public static FileAction gpgDecrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return gpgDecrypt(null, parameters, options);
  }

  public static FileAction gpgDecrypt(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().gpgDecrypt(path, parameters, options);
  }


  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * Parameters:
   *   destination (required) - string - Destination file path for the encrypted file.
   *   gpg_key_ids - array(int64) - GPG Key IDs to encrypt with.
   *   gpg_key_partner_id - int64 - Partner ID whose GPG keys should be used for encryption.
   *   signing_key_id - int64 - Optional GPG Key ID to sign with.
   *   armor - boolean - Output ASCII-armored encrypted data.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public static FileAction gpgEncrypt() throws RuntimeException {
    return gpgEncrypt(null, null, null);
  }

  public static FileAction gpgEncrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgEncrypt(path, parameters, null);
  }

  public static FileAction gpgEncrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return gpgEncrypt(null, parameters, options);
  }

  public static FileAction gpgEncrypt(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().gpgEncrypt(path, parameters, options);
  }


  /**
   * Extract a ZIP file to a destination folder
   *
   * Parameters:
   *   destination (required) - string - Destination folder path for extracted files.
   *   filename - string - Optional single entry filename to extract.
   *   overwrite - boolean - Overwrite existing files in the destination?
   */
  public static FileAction unzip() throws RuntimeException {
    return unzip(null, null, null);
  }

  public static FileAction unzip(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return unzip(path, parameters, null);
  }

  public static FileAction unzip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return unzip(null, parameters, options);
  }

  public static FileAction unzip(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().unzip(path, parameters, options);
  }


  /**
   * Parameters:
   *   paths (required) - array(string) - Paths to include in the ZIP.
   *   destination (required) - string - Destination file path for the ZIP.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public static FileAction zip() throws RuntimeException {
    return zip(null, null);
  }

  public static FileAction zip(HashMap<String, Object> parameters) throws RuntimeException {
    return zip(parameters, null);
  }


  public static FileAction zip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().zip(parameters, options);
  }


  /**
   * Begin File Upload
   *
   * Parameters:
   *   mkdir_parents - boolean - Create parent directories if they do not exist?
   *   part - int64 - Part if uploading a part.
   *   parts - int64 - How many parts to fetch?
   *   ref - string -
   *   restart - int64 - File byte offset to restart from.
   *   size - int64 - Total bytes of file being uploaded (include bytes being retained if appending/restarting).
   *   with_rename - boolean - Allow file rename instead of overwrite?
   *   buffered_upload - boolean - If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.
   *   with_direct_connection_info - boolean - Include optional direct connection information for a direct Agent transfer attempt?
   */
  public static FileUploadPart beginUpload() throws RuntimeException {
    return beginUpload(null, null, null);
  }

  public static FileUploadPart beginUpload(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginUpload(path, parameters, null);
  }

  public static FileUploadPart beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginUpload(null, parameters, options);
  }

  public static FileUploadPart beginUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().beginUpload(path, parameters, options);
  }


}
