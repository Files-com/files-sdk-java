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
  /**
   * Opens the File's contents, the whole file, as a stream to read and close.
   *
   * @see #getInputStream(long, long)
   */
  @JsonIgnore
  public FilesInputStream getInputStream() throws IOException {
    return getInputStream(0L, 0L);
  }

  /**
   * Opens the File's contents as a stream to read and close. {@code start} is not used: the whole
   * file is downloaded.
   *
   * @see #getInputStream(long, long)
   */
  public FilesInputStream getInputStream(long start) throws IOException {
    return getInputStream(start, 0L);
  }

  /**
   * Opens the File's contents as a stream to read and close, with the client that returned this
   * File and the request options it keeps; see
   * {@link com.files.net.Transfers#openDownload(File, com.files.net.DownloadOptions)}. {@code start}
   * and {@code end} are not used: the whole file is downloaded.
   */
  public FilesInputStream getInputStream(long start, long end) throws IOException {
    return filesClient().transfers().openDownload(this, ownDownloadOptions());
  }

  // The options of this File's own downloads: the request options it keeps, such as api_key; never a
  // previous call's cancellation or time limit, which it does not keep.
  private com.files.net.DownloadOptions ownDownloadOptions() {
    return com.files.net.DownloadOptions.builder().requestOptions(options).build();
  }

  /**
   * Saves the File's contents to the local path, with the client that returned this File and the
   * request options it keeps. The contents are checked before they replace anything at the path; see
   * {@link com.files.net.Transfers#download(File, java.nio.file.Path, com.files.net.DownloadOptions)}.
   */
  public void saveAsLocalFile(String path) throws IOException {
    filesClient().transfers().download(this, java.nio.file.Paths.get(path), ownDownloadOptions());
  }

  /**
   * Sends Files.com's request for the file at {@code path} with the parameters you give, and no request
   * options.
   *
   * @see #completeUpload(String, HashMap, HashMap)
   */
  public static File completeUpload(String path, HashMap<String, Object> parameters) throws IOException {
    return completeUpload(path, parameters, null);
  }

  /**
   * Sends Files.com's request for the file at {@code path} with the parameters you give, as to
   * complete an upload with its {@code ref}, and returns the {@code File} in Files.com's answer. When
   * the parameters give no {@code action}, it sends {@code action=put}. The maps you pass are copied.
   * It sends no file data and waits for nothing further.
   *
   * @param path the remote path; if null, the {@code path} in {@code parameters}, which must then be set
   * @param parameters the request's parameters, by their API names; may be null
   * @param options the request options; may be null
   * @return the {@code File} in Files.com's answer
   */
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

  /**
   * Starts an upload to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, without sending any file data.
   *
   * @see #uploadToRemoteServer(Long, String, HashMap, HashMap)
   */
  public static FileUploadPart uploadToRemoteServer(Long remoteServerId, String destinationPath) throws RuntimeException {
    return uploadToRemoteServer(remoteServerId, destinationPath, null, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, without sending any file data.
   *
   * @see #uploadToRemoteServer(Long, String, HashMap, HashMap)
   */
  public static FileUploadPart uploadToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return uploadToRemoteServer(remoteServerId, destinationPath, parameters, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, without sending any file data: {@code create}
   * for {@code _/RemoteServers/<id>/<destinationPath>}, normalized, which returns the
   * {@code FileUploadPart} for sending the first part. Sending the parts and completing the upload
   * are left to you. To upload a local file, use
   * {@code client.transfers().uploadToRemoteServer(source, remoteServerId, destinationPath, options)},
   * which sends its bytes and completes the upload.
   */
  public static FileUploadPart uploadToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath), parameters, options);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with no request options.
   *
   * @see #copyToRemoteServer(String, Long, String, HashMap, HashMap)
   */
  public static FileAction copyToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return copyToRemoteServer(path, remoteServerId, destinationPath, parameters, null);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with no other parameters or request options.
   *
   * @see #copyToRemoteServer(String, Long, String, HashMap, HashMap)
   */
  public static FileAction copyToRemoteServer(String path, Long remoteServerId, String destinationPath) throws RuntimeException {
    return copyToRemoteServer(path, remoteServerId, destinationPath, null, null);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Remote Server
   * {@code remoteServerId}: {@code copy} with the destination
   * {@code _/RemoteServers/<id>/<destinationPath>}, normalized, and your other parameters
   * and options as given. The parameters you pass are not changed.
   */
  public static FileAction copyToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return copy(path, parameters, options);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with no request options.
   *
   * @see #moveToRemoteServer(String, Long, String, HashMap, HashMap)
   */
  public static FileAction moveToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return moveToRemoteServer(path, remoteServerId, destinationPath, parameters, null);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with no other parameters or request options.
   *
   * @see #moveToRemoteServer(String, Long, String, HashMap, HashMap)
   */
  public static FileAction moveToRemoteServer(String path, Long remoteServerId, String destinationPath) throws RuntimeException {
    return moveToRemoteServer(path, remoteServerId, destinationPath, null, null);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Remote Server
   * {@code remoteServerId}: {@code move} with the destination
   * {@code _/RemoteServers/<id>/<destinationPath>}, normalized, and your other parameters
   * and options as given. The parameters you pass are not changed.
   */
  public static FileAction moveToRemoteServer(String path, Long remoteServerId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return move(path, parameters, options);
  }

  /**
   * Copies this file or folder to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with this file's client and request options:
   * {@code copy} with the destination {@code _/RemoteServers/<id>/<destinationPath>},
   * normalized, and your other parameters as given. The parameters you pass are not changed.
   */
  public FileAction copyToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return copy(parameters);
  }

  /**
   * Copies this file or folder to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with no other parameters.
   *
   * @see #copyToRemoteServer(Long, String, HashMap)
   */
  public FileAction copyToRemoteServer(Long remoteServerId, String destinationPath) throws IOException {
    return copyToRemoteServer(remoteServerId, destinationPath, null);
  }

  /**
   * Moves this file or folder to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with this file's client and request options:
   * {@code move} with the destination {@code _/RemoteServers/<id>/<destinationPath>},
   * normalized, and your other parameters as given. The parameters you pass are not changed.
   */
  public FileAction moveToRemoteServer(Long remoteServerId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("RemoteServers", remoteServerId, destinationPath));
    return move(parameters);
  }

  /**
   * Moves this file or folder to {@code destinationPath} in Remote Server
   * {@code remoteServerId}, with no other parameters.
   *
   * @see #moveToRemoteServer(Long, String, HashMap)
   */
  public FileAction moveToRemoteServer(Long remoteServerId, String destinationPath) throws IOException {
    return moveToRemoteServer(remoteServerId, destinationPath, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Snapshot
   * {@code snapshotId}, without sending any file data.
   *
   * @see #uploadToSnapshot(Long, String, HashMap, HashMap)
   */
  public static FileUploadPart uploadToSnapshot(Long snapshotId, String destinationPath) throws RuntimeException {
    return uploadToSnapshot(snapshotId, destinationPath, null, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Snapshot
   * {@code snapshotId}, without sending any file data.
   *
   * @see #uploadToSnapshot(Long, String, HashMap, HashMap)
   */
  public static FileUploadPart uploadToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return uploadToSnapshot(snapshotId, destinationPath, parameters, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Snapshot
   * {@code snapshotId}, without sending any file data: {@code create}
   * for {@code _/Snapshots/<id>/<destinationPath>}, normalized, which returns the
   * {@code FileUploadPart} for sending the first part. Sending the parts and completing the upload
   * are left to you. To upload a local file, use
   * {@code client.transfers().uploadToSnapshot(source, snapshotId, destinationPath, options)},
   * which sends its bytes and completes the upload.
   */
  public static FileUploadPart uploadToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(underscoreDestinationPath("Snapshots", snapshotId, destinationPath), parameters, options);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with no request options.
   *
   * @see #copyToSnapshot(String, Long, String, HashMap, HashMap)
   */
  public static FileAction copyToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return copyToSnapshot(path, snapshotId, destinationPath, parameters, null);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with no other parameters or request options.
   *
   * @see #copyToSnapshot(String, Long, String, HashMap, HashMap)
   */
  public static FileAction copyToSnapshot(String path, Long snapshotId, String destinationPath) throws RuntimeException {
    return copyToSnapshot(path, snapshotId, destinationPath, null, null);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Snapshot
   * {@code snapshotId}: {@code copy} with the destination
   * {@code _/Snapshots/<id>/<destinationPath>}, normalized, and your other parameters
   * and options as given. The parameters you pass are not changed.
   */
  public static FileAction copyToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return copy(path, parameters, options);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with no request options.
   *
   * @see #moveToSnapshot(String, Long, String, HashMap, HashMap)
   */
  public static FileAction moveToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return moveToSnapshot(path, snapshotId, destinationPath, parameters, null);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with no other parameters or request options.
   *
   * @see #moveToSnapshot(String, Long, String, HashMap, HashMap)
   */
  public static FileAction moveToSnapshot(String path, Long snapshotId, String destinationPath) throws RuntimeException {
    return moveToSnapshot(path, snapshotId, destinationPath, null, null);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Snapshot
   * {@code snapshotId}: {@code move} with the destination
   * {@code _/Snapshots/<id>/<destinationPath>}, normalized, and your other parameters
   * and options as given. The parameters you pass are not changed.
   */
  public static FileAction moveToSnapshot(String path, Long snapshotId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return move(path, parameters, options);
  }

  /**
   * Copies this file or folder to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with this file's client and request options:
   * {@code copy} with the destination {@code _/Snapshots/<id>/<destinationPath>},
   * normalized, and your other parameters as given. The parameters you pass are not changed.
   */
  public FileAction copyToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return copy(parameters);
  }

  /**
   * Copies this file or folder to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with no other parameters.
   *
   * @see #copyToSnapshot(Long, String, HashMap)
   */
  public FileAction copyToSnapshot(Long snapshotId, String destinationPath) throws IOException {
    return copyToSnapshot(snapshotId, destinationPath, null);
  }

  /**
   * Moves this file or folder to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with this file's client and request options:
   * {@code move} with the destination {@code _/Snapshots/<id>/<destinationPath>},
   * normalized, and your other parameters as given. The parameters you pass are not changed.
   */
  public FileAction moveToSnapshot(Long snapshotId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Snapshots", snapshotId, destinationPath));
    return move(parameters);
  }

  /**
   * Moves this file or folder to {@code destinationPath} in Snapshot
   * {@code snapshotId}, with no other parameters.
   *
   * @see #moveToSnapshot(Long, String, HashMap)
   */
  public FileAction moveToSnapshot(Long snapshotId, String destinationPath) throws IOException {
    return moveToSnapshot(snapshotId, destinationPath, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Child Site
   * {@code siteId}, without sending any file data.
   *
   * @see #uploadToChildSite(Long, String, HashMap, HashMap)
   */
  public static FileUploadPart uploadToChildSite(Long siteId, String destinationPath) throws RuntimeException {
    return uploadToChildSite(siteId, destinationPath, null, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Child Site
   * {@code siteId}, without sending any file data.
   *
   * @see #uploadToChildSite(Long, String, HashMap, HashMap)
   */
  public static FileUploadPart uploadToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return uploadToChildSite(siteId, destinationPath, parameters, null);
  }

  /**
   * Starts an upload to {@code destinationPath} in Child Site
   * {@code siteId}, without sending any file data: {@code create}
   * for {@code _/Sites/<id>/<destinationPath>}, normalized, which returns the
   * {@code FileUploadPart} for sending the first part. Sending the parts and completing the upload
   * are left to you. To upload a local file, use
   * {@code client.transfers().uploadToChildSite(source, siteId, destinationPath, options)},
   * which sends its bytes and completes the upload.
   */
  public static FileUploadPart uploadToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(underscoreDestinationPath("Sites", siteId, destinationPath), parameters, options);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Child Site
   * {@code siteId}, with no request options.
   *
   * @see #copyToChildSite(String, Long, String, HashMap, HashMap)
   */
  public static FileAction copyToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return copyToChildSite(path, siteId, destinationPath, parameters, null);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Child Site
   * {@code siteId}, with no other parameters or request options.
   *
   * @see #copyToChildSite(String, Long, String, HashMap, HashMap)
   */
  public static FileAction copyToChildSite(String path, Long siteId, String destinationPath) throws RuntimeException {
    return copyToChildSite(path, siteId, destinationPath, null, null);
  }

  /**
   * Copies the file or folder at {@code path} to {@code destinationPath} in Child Site
   * {@code siteId}: {@code copy} with the destination
   * {@code _/Sites/<id>/<destinationPath>}, normalized, and your other parameters
   * and options as given. The parameters you pass are not changed.
   */
  public static FileAction copyToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return copy(path, parameters, options);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Child Site
   * {@code siteId}, with no request options.
   *
   * @see #moveToChildSite(String, Long, String, HashMap, HashMap)
   */
  public static FileAction moveToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters) throws RuntimeException {
    return moveToChildSite(path, siteId, destinationPath, parameters, null);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Child Site
   * {@code siteId}, with no other parameters or request options.
   *
   * @see #moveToChildSite(String, Long, String, HashMap, HashMap)
   */
  public static FileAction moveToChildSite(String path, Long siteId, String destinationPath) throws RuntimeException {
    return moveToChildSite(path, siteId, destinationPath, null, null);
  }

  /**
   * Moves the file or folder at {@code path} to {@code destinationPath} in Child Site
   * {@code siteId}: {@code move} with the destination
   * {@code _/Sites/<id>/<destinationPath>}, normalized, and your other parameters
   * and options as given. The parameters you pass are not changed.
   */
  public static FileAction moveToChildSite(String path, Long siteId, String destinationPath, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return move(path, parameters, options);
  }

  /**
   * Copies this file or folder to {@code destinationPath} in Child Site
   * {@code siteId}, with this file's client and request options:
   * {@code copy} with the destination {@code _/Sites/<id>/<destinationPath>},
   * normalized, and your other parameters as given. The parameters you pass are not changed.
   */
  public FileAction copyToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return copy(parameters);
  }

  /**
   * Copies this file or folder to {@code destinationPath} in Child Site
   * {@code siteId}, with no other parameters.
   *
   * @see #copyToChildSite(Long, String, HashMap)
   */
  public FileAction copyToChildSite(Long siteId, String destinationPath) throws IOException {
    return copyToChildSite(siteId, destinationPath, null);
  }

  /**
   * Moves this file or folder to {@code destinationPath} in Child Site
   * {@code siteId}, with this file's client and request options:
   * {@code move} with the destination {@code _/Sites/<id>/<destinationPath>},
   * normalized, and your other parameters as given. The parameters you pass are not changed.
   */
  public FileAction moveToChildSite(Long siteId, String destinationPath, HashMap<String, Object> parameters) throws IOException {
    parameters = parameters != null ? new HashMap<String, Object>(parameters) : new HashMap<String, Object>();
    parameters.put("destination", underscoreDestinationPath("Sites", siteId, destinationPath));
    return move(parameters);
  }

  /**
   * Moves this file or folder to {@code destinationPath} in Child Site
   * {@code siteId}, with no other parameters.
   *
   * @see #moveToChildSite(Long, String, HashMap)
   */
  public FileAction moveToChildSite(Long siteId, String destinationPath) throws IOException {
    return moveToChildSite(siteId, destinationPath, null);
  }


  /**
   * Creates a {@code File} with no attributes set.
   */
  public File() {
    this(null, null);
  }

  /**
   * Creates a {@code File} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public File(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code File} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #path}.
   *
   * @return the value of {@link #path}
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets {@link #path}.
   *
   * @param path the new value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * User ID of the User who created the file/folder
   */
  @JsonProperty("created_by_id")
  public Long createdById;

  /**
   * Gets {@link #createdById}.
   *
   * @return the value of {@link #createdById}
   */
  public Long getCreatedById() {
    return createdById;
  }

  /**
   * Sets {@link #createdById}.
   *
   * @param createdById the new value
   */
  public void setCreatedById(Long createdById) {
    this.createdById = createdById;
  }

  /**
   * ID of the API key that created the file/folder
   */
  @JsonProperty("created_by_api_key_id")
  public Long createdByApiKeyId;

  /**
   * Gets {@link #createdByApiKeyId}.
   *
   * @return the value of {@link #createdByApiKeyId}
   */
  public Long getCreatedByApiKeyId() {
    return createdByApiKeyId;
  }

  /**
   * Sets {@link #createdByApiKeyId}.
   *
   * @param createdByApiKeyId the new value
   */
  public void setCreatedByApiKeyId(Long createdByApiKeyId) {
    this.createdByApiKeyId = createdByApiKeyId;
  }

  /**
   * ID of the AS2 Incoming Message that created the file/folder
   */
  @JsonProperty("created_by_as2_incoming_message_id")
  public Long createdByAs2IncomingMessageId;

  /**
   * Gets {@link #createdByAs2IncomingMessageId}.
   *
   * @return the value of {@link #createdByAs2IncomingMessageId}
   */
  public Long getCreatedByAs2IncomingMessageId() {
    return createdByAs2IncomingMessageId;
  }

  /**
   * Sets {@link #createdByAs2IncomingMessageId}.
   *
   * @param createdByAs2IncomingMessageId the new value
   */
  public void setCreatedByAs2IncomingMessageId(Long createdByAs2IncomingMessageId) {
    this.createdByAs2IncomingMessageId = createdByAs2IncomingMessageId;
  }

  /**
   * ID of the Automation that created the file/folder
   */
  @JsonProperty("created_by_automation_id")
  public Long createdByAutomationId;

  /**
   * Gets {@link #createdByAutomationId}.
   *
   * @return the value of {@link #createdByAutomationId}
   */
  public Long getCreatedByAutomationId() {
    return createdByAutomationId;
  }

  /**
   * Sets {@link #createdByAutomationId}.
   *
   * @param createdByAutomationId the new value
   */
  public void setCreatedByAutomationId(Long createdByAutomationId) {
    this.createdByAutomationId = createdByAutomationId;
  }

  /**
   * ID of the Bundle Registration that created the file/folder
   */
  @JsonProperty("created_by_bundle_registration_id")
  public Long createdByBundleRegistrationId;

  /**
   * Gets {@link #createdByBundleRegistrationId}.
   *
   * @return the value of {@link #createdByBundleRegistrationId}
   */
  public Long getCreatedByBundleRegistrationId() {
    return createdByBundleRegistrationId;
  }

  /**
   * Sets {@link #createdByBundleRegistrationId}.
   *
   * @param createdByBundleRegistrationId the new value
   */
  public void setCreatedByBundleRegistrationId(Long createdByBundleRegistrationId) {
    this.createdByBundleRegistrationId = createdByBundleRegistrationId;
  }

  /**
   * ID of the Inbox that created the file/folder
   */
  @JsonProperty("created_by_inbox_id")
  public Long createdByInboxId;

  /**
   * Gets {@link #createdByInboxId}.
   *
   * @return the value of {@link #createdByInboxId}
   */
  public Long getCreatedByInboxId() {
    return createdByInboxId;
  }

  /**
   * Sets {@link #createdByInboxId}.
   *
   * @param createdByInboxId the new value
   */
  public void setCreatedByInboxId(Long createdByInboxId) {
    this.createdByInboxId = createdByInboxId;
  }

  /**
   * ID of the Remote Server that created the file/folder
   */
  @JsonProperty("created_by_remote_server_id")
  public Long createdByRemoteServerId;

  /**
   * Gets {@link #createdByRemoteServerId}.
   *
   * @return the value of {@link #createdByRemoteServerId}
   */
  public Long getCreatedByRemoteServerId() {
    return createdByRemoteServerId;
  }

  /**
   * Sets {@link #createdByRemoteServerId}.
   *
   * @param createdByRemoteServerId the new value
   */
  public void setCreatedByRemoteServerId(Long createdByRemoteServerId) {
    this.createdByRemoteServerId = createdByRemoteServerId;
  }

  /**
   * ID of the Sync that created the file/folder
   */
  @JsonProperty("created_by_sync_id")
  public Long createdBySyncId;

  /**
   * Gets {@link #createdBySyncId}.
   *
   * @return the value of {@link #createdBySyncId}
   */
  public Long getCreatedBySyncId() {
    return createdBySyncId;
  }

  /**
   * Sets {@link #createdBySyncId}.
   *
   * @param createdBySyncId the new value
   */
  public void setCreatedBySyncId(Long createdBySyncId) {
    this.createdBySyncId = createdBySyncId;
  }

  /**
   * Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
   */
  @JsonProperty("custom_metadata")
  public Object customMetadata;

  /**
   * Gets {@link #customMetadata}.
   *
   * @return the value of {@link #customMetadata}
   */
  public Object getCustomMetadata() {
    return customMetadata;
  }

  /**
   * Sets {@link #customMetadata}.
   *
   * @param customMetadata the new value
   */
  public void setCustomMetadata(Object customMetadata) {
    this.customMetadata = customMetadata;
  }

  /**
   * File/Folder display name
   */
  @JsonProperty("display_name")
  public String displayName;

  /**
   * Gets {@link #displayName}.
   *
   * @return the value of {@link #displayName}
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets {@link #displayName}.
   *
   * @param displayName the new value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Type: `directory` or `file`.
   */
  @JsonProperty("type")
  public String type;

  /**
   * Gets {@link #type}.
   *
   * @return the value of {@link #type}
   */
  public String getType() {
    return type;
  }

  /**
   * Sets {@link #type}.
   *
   * @param type the new value
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * File/Folder size
   */
  @JsonProperty("size")
  public Long size;

  /**
   * Gets {@link #size}.
   *
   * @return the value of {@link #size}
   */
  public Long getSize() {
    return size;
  }

  /**
   * Sets {@link #size}.
   *
   * @param size the new value
   */
  public void setSize(Long size) {
    this.size = size;
  }

  /**
   * File created date/time
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
   * User ID of the User who last modified the file/folder
   */
  @JsonProperty("last_modified_by_id")
  public Long lastModifiedById;

  /**
   * Gets {@link #lastModifiedById}.
   *
   * @return the value of {@link #lastModifiedById}
   */
  public Long getLastModifiedById() {
    return lastModifiedById;
  }

  /**
   * Sets {@link #lastModifiedById}.
   *
   * @param lastModifiedById the new value
   */
  public void setLastModifiedById(Long lastModifiedById) {
    this.lastModifiedById = lastModifiedById;
  }

  /**
   * ID of the API key that last modified the file/folder
   */
  @JsonProperty("last_modified_by_api_key_id")
  public Long lastModifiedByApiKeyId;

  /**
   * Gets {@link #lastModifiedByApiKeyId}.
   *
   * @return the value of {@link #lastModifiedByApiKeyId}
   */
  public Long getLastModifiedByApiKeyId() {
    return lastModifiedByApiKeyId;
  }

  /**
   * Sets {@link #lastModifiedByApiKeyId}.
   *
   * @param lastModifiedByApiKeyId the new value
   */
  public void setLastModifiedByApiKeyId(Long lastModifiedByApiKeyId) {
    this.lastModifiedByApiKeyId = lastModifiedByApiKeyId;
  }

  /**
   * ID of the Automation that last modified the file/folder
   */
  @JsonProperty("last_modified_by_automation_id")
  public Long lastModifiedByAutomationId;

  /**
   * Gets {@link #lastModifiedByAutomationId}.
   *
   * @return the value of {@link #lastModifiedByAutomationId}
   */
  public Long getLastModifiedByAutomationId() {
    return lastModifiedByAutomationId;
  }

  /**
   * Sets {@link #lastModifiedByAutomationId}.
   *
   * @param lastModifiedByAutomationId the new value
   */
  public void setLastModifiedByAutomationId(Long lastModifiedByAutomationId) {
    this.lastModifiedByAutomationId = lastModifiedByAutomationId;
  }

  /**
   * ID of the Bundle Registration that last modified the file/folder
   */
  @JsonProperty("last_modified_by_bundle_registration_id")
  public Long lastModifiedByBundleRegistrationId;

  /**
   * Gets {@link #lastModifiedByBundleRegistrationId}.
   *
   * @return the value of {@link #lastModifiedByBundleRegistrationId}
   */
  public Long getLastModifiedByBundleRegistrationId() {
    return lastModifiedByBundleRegistrationId;
  }

  /**
   * Sets {@link #lastModifiedByBundleRegistrationId}.
   *
   * @param lastModifiedByBundleRegistrationId the new value
   */
  public void setLastModifiedByBundleRegistrationId(Long lastModifiedByBundleRegistrationId) {
    this.lastModifiedByBundleRegistrationId = lastModifiedByBundleRegistrationId;
  }

  /**
   * ID of the Remote Server that last modified the file/folder
   */
  @JsonProperty("last_modified_by_remote_server_id")
  public Long lastModifiedByRemoteServerId;

  /**
   * Gets {@link #lastModifiedByRemoteServerId}.
   *
   * @return the value of {@link #lastModifiedByRemoteServerId}
   */
  public Long getLastModifiedByRemoteServerId() {
    return lastModifiedByRemoteServerId;
  }

  /**
   * Sets {@link #lastModifiedByRemoteServerId}.
   *
   * @param lastModifiedByRemoteServerId the new value
   */
  public void setLastModifiedByRemoteServerId(Long lastModifiedByRemoteServerId) {
    this.lastModifiedByRemoteServerId = lastModifiedByRemoteServerId;
  }

  /**
   * ID of the Sync that last modified the file/folder
   */
  @JsonProperty("last_modified_by_sync_id")
  public Long lastModifiedBySyncId;

  /**
   * Gets {@link #lastModifiedBySyncId}.
   *
   * @return the value of {@link #lastModifiedBySyncId}
   */
  public Long getLastModifiedBySyncId() {
    return lastModifiedBySyncId;
  }

  /**
   * Sets {@link #lastModifiedBySyncId}.
   *
   * @param lastModifiedBySyncId the new value
   */
  public void setLastModifiedBySyncId(Long lastModifiedBySyncId) {
    this.lastModifiedBySyncId = lastModifiedBySyncId;
  }

  /**
   * File last modified date/time, according to the server.  This is the timestamp of the last Files.com operation of the file, regardless of what modified timestamp was sent.
   */
  @JsonProperty("mtime")
  public Date mtime;

  /**
   * Gets {@link #mtime}.
   *
   * @return the value of {@link #mtime}
   */
  public Date getMtime() {
    return mtime;
  }

  /**
   * Sets {@link #mtime}.
   *
   * @param mtime the new value
   */
  public void setMtime(Date mtime) {
    this.mtime = mtime;
  }

  /**
   * File last modified date/time, according to the client who set it.  Files.com allows desktop, FTP, SFTP, and WebDAV clients to set modified at times.  This allows Desktop&lt;-&gt;Cloud syncing to preserve modified at times.
   */
  @JsonProperty("provided_mtime")
  public Date providedMtime;

  /**
   * Gets {@link #providedMtime}.
   *
   * @return the value of {@link #providedMtime}
   */
  public Date getProvidedMtime() {
    return providedMtime;
  }

  /**
   * Sets {@link #providedMtime}.
   *
   * @param providedMtime the new value
   */
  public void setProvidedMtime(Date providedMtime) {
    this.providedMtime = providedMtime;
  }

  /**
   * File CRC32 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("crc32")
  public String crc32;

  /**
   * Gets {@link #crc32}.
   *
   * @return the value of {@link #crc32}
   */
  public String getCrc32() {
    return crc32;
  }

  /**
   * Sets {@link #crc32}.
   *
   * @param crc32 the new value
   */
  public void setCrc32(String crc32) {
    this.crc32 = crc32;
  }

  /**
   * File MD5 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("md5")
  public String md5;

  /**
   * Gets {@link #md5}.
   *
   * @return the value of {@link #md5}
   */
  public String getMd5() {
    return md5;
  }

  /**
   * Sets {@link #md5}.
   *
   * @param md5 the new value
   */
  public void setMd5(String md5) {
    this.md5 = md5;
  }

  /**
   * File SHA1 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("sha1")
  public String sha1;

  /**
   * Gets {@link #sha1}.
   *
   * @return the value of {@link #sha1}
   */
  public String getSha1() {
    return sha1;
  }

  /**
   * Sets {@link #sha1}.
   *
   * @param sha1 the new value
   */
  public void setSha1(String sha1) {
    this.sha1 = sha1;
  }

  /**
   * File SHA256 checksum. This is sometimes delayed, so if you get a blank response, wait and try again.
   */
  @JsonProperty("sha256")
  public String sha256;

  /**
   * Gets {@link #sha256}.
   *
   * @return the value of {@link #sha256}
   */
  public String getSha256() {
    return sha256;
  }

  /**
   * Sets {@link #sha256}.
   *
   * @param sha256 the new value
   */
  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  /**
   * MIME Type.  This is determined by the filename extension and is not stored separately internally.
   */
  @JsonProperty("mime_type")
  public String mimeType;

  /**
   * Gets {@link #mimeType}.
   *
   * @return the value of {@link #mimeType}
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * Sets {@link #mimeType}.
   *
   * @param mimeType the new value
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Region location
   */
  @JsonProperty("region")
  public String region;

  /**
   * Gets {@link #region}.
   *
   * @return the value of {@link #region}
   */
  public String getRegion() {
    return region;
  }

  /**
   * Sets {@link #region}.
   *
   * @param region the new value
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * A short string representing the current user's permissions.  Can be `r` (Read),`w` (Write),`d` (Delete), `l` (List) or any combination
   */
  @JsonProperty("permissions")
  public String permissions;

  /**
   * Gets {@link #permissions}.
   *
   * @return the value of {@link #permissions}
   */
  public String getPermissions() {
    return permissions;
  }

  /**
   * Sets {@link #permissions}.
   *
   * @param permissions the new value
   */
  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  /**
   * Are subfolders locked and unable to be modified?
   */
  @JsonProperty("subfolders_locked?")
  public Boolean subfoldersLocked;

  /**
   * Gets {@link #subfoldersLocked}.
   *
   * @return the value of {@link #subfoldersLocked}
   */
  public Boolean getSubfoldersLocked() {
    return subfoldersLocked;
  }

  /**
   * Sets {@link #subfoldersLocked}.
   *
   * @param subfoldersLocked the new value
   */
  public void setSubfoldersLocked(Boolean subfoldersLocked) {
    this.subfoldersLocked = subfoldersLocked;
  }

  /**
   * Is this folder locked and unable to be modified?
   */
  @JsonProperty("is_locked")
  public Boolean isLocked;

  /**
   * Gets {@link #isLocked}.
   *
   * @return the value of {@link #isLocked}
   */
  public Boolean getIsLocked() {
    return isLocked;
  }

  /**
   * Sets {@link #isLocked}.
   *
   * @param isLocked the new value
   */
  public void setIsLocked(Boolean isLocked) {
    this.isLocked = isLocked;
  }

  /**
   * Link to download file. Provided only in response to a download request.
   */
  @JsonProperty("download_uri")
  public String downloadUri;

  /**
   * Gets {@link #downloadUri}.
   *
   * @return the value of {@link #downloadUri}
   */
  public String getDownloadUri() {
    return downloadUri;
  }

  /**
   * Sets {@link #downloadUri}.
   *
   * @param downloadUri the new value
   */
  public void setDownloadUri(String downloadUri) {
    this.downloadUri = downloadUri;
  }

  /**
   * Optional direct connection information for direct Agent transfer attempts
   */
  @JsonProperty("direct_connection_info")
  public DirectConnectionInfo directConnectionInfo;

  /**
   * Gets {@link #directConnectionInfo}.
   *
   * @return the value of {@link #directConnectionInfo}
   */
  public DirectConnectionInfo getDirectConnectionInfo() {
    return directConnectionInfo;
  }

  /**
   * Sets {@link #directConnectionInfo}.
   *
   * @param directConnectionInfo the new value
   */
  public void setDirectConnectionInfo(DirectConnectionInfo directConnectionInfo) {
    this.directConnectionInfo = directConnectionInfo;
  }

  /**
   * Bookmark/priority color of file/folder
   */
  @JsonProperty("priority_color")
  public String priorityColor;

  /**
   * Gets {@link #priorityColor}.
   *
   * @return the value of {@link #priorityColor}
   */
  public String getPriorityColor() {
    return priorityColor;
  }

  /**
   * Sets {@link #priorityColor}.
   *
   * @param priorityColor the new value
   */
  public void setPriorityColor(String priorityColor) {
    this.priorityColor = priorityColor;
  }

  /**
   * File preview ID
   */
  @JsonProperty("preview_id")
  public Long previewId;

  /**
   * Gets {@link #previewId}.
   *
   * @return the value of {@link #previewId}
   */
  public Long getPreviewId() {
    return previewId;
  }

  /**
   * Sets {@link #previewId}.
   *
   * @param previewId the new value
   */
  public void setPreviewId(Long previewId) {
    this.previewId = previewId;
  }

  /**
   * File preview
   */
  @JsonProperty("preview")
  public Preview preview;

  /**
   * Gets {@link #preview}.
   *
   * @return the value of {@link #preview}
   */
  public Preview getPreview() {
    return preview;
  }

  /**
   * Sets {@link #preview}.
   *
   * @param preview the new value
   */
  public void setPreview(Preview preview) {
    this.preview = preview;
  }

  /**
   * The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist
   */
  @JsonProperty("action")
  public String action;

  /**
   * Gets {@link #action}.
   *
   * @return the value of {@link #action}
   */
  public String getAction() {
    return action;
  }

  /**
   * Sets {@link #action}.
   *
   * @param action the new value
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Length of file.
   */
  @JsonProperty("length")
  public Long length;

  /**
   * Gets {@link #length}.
   *
   * @return the value of {@link #length}
   */
  public Long getLength() {
    return length;
  }

  /**
   * Sets {@link #length}.
   *
   * @param length the new value
   */
  public void setLength(Long length) {
    this.length = length;
  }

  /**
   * Create parent directories if they do not exist?
   */
  @JsonProperty("mkdir_parents")
  public Boolean mkdirParents;

  /**
   * Gets {@link #mkdirParents}.
   *
   * @return the value of {@link #mkdirParents}
   */
  public Boolean getMkdirParents() {
    return mkdirParents;
  }

  /**
   * Sets {@link #mkdirParents}.
   *
   * @param mkdirParents the new value
   */
  public void setMkdirParents(Boolean mkdirParents) {
    this.mkdirParents = mkdirParents;
  }

  /**
   * Part if uploading a part.
   */
  @JsonProperty("part")
  public Long part;

  /**
   * Gets {@link #part}.
   *
   * @return the value of {@link #part}
   */
  public Long getPart() {
    return part;
  }

  /**
   * Sets {@link #part}.
   *
   * @param part the new value
   */
  public void setPart(Long part) {
    this.part = part;
  }

  /**
   * How many parts to fetch?
   */
  @JsonProperty("parts")
  public Long parts;

  /**
   * Gets {@link #parts}.
   *
   * @return the value of {@link #parts}
   */
  public Long getParts() {
    return parts;
  }

  /**
   * Sets {@link #parts}.
   *
   * @param parts the new value
   */
  public void setParts(Long parts) {
    this.parts = parts;
  }

  /**
   * Upload reference used to request further parts or finalize the existing upload.
   */
  @JsonProperty("ref")
  public String ref;

  /**
   * Gets {@link #ref}.
   *
   * @return the value of {@link #ref}
   */
  public String getRef() {
    return ref;
  }

  /**
   * Sets {@link #ref}.
   *
   * @param ref the new value
   */
  public void setRef(String ref) {
    this.ref = ref;
  }

  /**
   * File byte offset to restart from.
   */
  @JsonProperty("restart")
  public Long restart;

  /**
   * Gets {@link #restart}.
   *
   * @return the value of {@link #restart}
   */
  public Long getRestart() {
    return restart;
  }

  /**
   * Sets {@link #restart}.
   *
   * @param restart the new value
   */
  public void setRestart(Long restart) {
    this.restart = restart;
  }

  /**
   * If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?
   */
  @JsonProperty("copy_behaviors")
  public Boolean copyBehaviors;

  /**
   * Gets {@link #copyBehaviors}.
   *
   * @return the value of {@link #copyBehaviors}
   */
  public Boolean getCopyBehaviors() {
    return copyBehaviors;
  }

  /**
   * Sets {@link #copyBehaviors}.
   *
   * @param copyBehaviors the new value
   */
  public void setCopyBehaviors(Boolean copyBehaviors) {
    this.copyBehaviors = copyBehaviors;
  }

  /**
   * If copying folder, copy just the structure?
   */
  @JsonProperty("structure")
  public String structure;

  /**
   * Gets {@link #structure}.
   *
   * @return the value of {@link #structure}
   */
  public String getStructure() {
    return structure;
  }

  /**
   * Sets {@link #structure}.
   *
   * @param structure the new value
   */
  public void setStructure(String structure) {
    this.structure = structure;
  }

  /**
   * Allow file rename instead of overwrite?
   */
  @JsonProperty("with_rename")
  public Boolean withRename;

  /**
   * Gets {@link #withRename}.
   *
   * @return the value of {@link #withRename}
   */
  public Boolean getWithRename() {
    return withRename;
  }

  /**
   * Sets {@link #withRename}.
   *
   * @param withRename the new value
   */
  public void setWithRename(Boolean withRename) {
    this.withRename = withRename;
  }

  /**
   * If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.
   */
  @JsonProperty("buffered_upload")
  public Boolean bufferedUpload;

  /**
   * Gets {@link #bufferedUpload}.
   *
   * @return the value of {@link #bufferedUpload}
   */
  public Boolean getBufferedUpload() {
    return bufferedUpload;
  }

  /**
   * Sets {@link #bufferedUpload}.
   *
   * @param bufferedUpload the new value
   */
  public void setBufferedUpload(Boolean bufferedUpload) {
    this.bufferedUpload = bufferedUpload;
  }

  /**
   * Include optional direct connection information for a direct Agent transfer attempt?
   */
  @JsonProperty("with_direct_connection_info")
  public Boolean withDirectConnectionInfo;

  /**
   * Gets {@link #withDirectConnectionInfo}.
   *
   * @return the value of {@link #withDirectConnectionInfo}
   */
  public Boolean getWithDirectConnectionInfo() {
    return withDirectConnectionInfo;
  }

  /**
   * Sets {@link #withDirectConnectionInfo}.
   *
   * @param withDirectConnectionInfo the new value
   */
  public void setWithDirectConnectionInfo(Boolean withDirectConnectionInfo) {
    this.withDirectConnectionInfo = withDirectConnectionInfo;
  }

  /**
   * Download File
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.</li>
   * <li>{@code preview_size} (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.</li>
   * <li>{@code with_previews} (Boolean): Include file preview information?</li>
   * <li>{@code with_priority_color} (Boolean): Include file priority color information?</li>
   * <li>{@code with_direct_connection_info} (Boolean): Include optional direct connection information for a direct Agent transfer attempt?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code File}
   */
  public File download(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().download(this.path, parameters, this.options);
  }

  /**
   * Updates this File. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update File/Folder Metadata
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code custom_metadata} (Object): Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.</li>
   * <li>{@code provided_mtime} (String): Modified time of file.</li>
   * <li>{@code priority_color} (String): Priority/Bookmark color of file.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code File}
   */
  public File update(HashMap<String, Object> parameters) throws IOException {
    File updated = filesClient().files().update(this.path, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete File/Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code recursive} (Boolean): If true, will recursively delete folders.  Otherwise, will error on non-empty folders.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().files().delete(this.path, parameters, this.options);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  /**
   * List the contents of a ZIP file
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code ZipListEntry} records, in a {@code ListIterator}
   */
  public ListIterator<ZipListEntry> zipListContents(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().zipListContents(this.path, parameters, this.options);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code recursive} (Boolean): If true, will recursively delete folders.  Otherwise, will error on non-empty folders.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction beginDelete(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().beginDelete(this.path, parameters, this.options);
  }

  /**
   * Copy File/Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Copy destination path.</li>
   * <li>{@code copy_behaviors} (Boolean): If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?</li>
   * <li>{@code structure} (Boolean): Copy structure only?</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file(s) in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction copy(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().copy(this.path, parameters, this.options);
  }

  /**
   * Move File/Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Move destination path.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file(s) in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction move(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().move(this.path, parameters, this.options);
  }

  /**
   * Transform a file and save the output to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination file path for the transformed output.</li>
   * <li>{@code transform_type} (String): Required. Transform type. Supported values are `image_convert`, `document_convert`, and `files_transform_script_execute`.</li>
   * <li>{@code target_format} (String): Required. Destination format to create.</li>
   * <li>{@code script} (String): Files TransformScript source. Required when transform_type is `files_transform_script_execute`.</li>
   * <li>{@code width} (Long): Maximum output width for image_convert.</li>
   * <li>{@code height} (Long): Maximum output height for image_convert.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction transform(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().transform(this.path, parameters, this.options);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination file path for the decrypted file.</li>
   * <li>{@code gpg_key_ids} (Long[]): GPG Key IDs to decrypt with. If omitted, every accessible private GPG key in the source workspace is used.</li>
   * <li>{@code gpg_key_partner_id} (Long): Partner ID whose GPG keys should be used for decryption.</li>
   * <li>{@code use_all_private_keys} (Boolean): Use every accessible private GPG key in the source workspace for decryption.</li>
   * <li>{@code ignore_mdc_error} (Boolean): Ignore errors from the MDC (modification detection code) check.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction gpgDecrypt(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().gpgDecrypt(this.path, parameters, this.options);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination file path for the encrypted file.</li>
   * <li>{@code gpg_key_ids} (Long[]): GPG Key IDs to encrypt with.</li>
   * <li>{@code gpg_key_partner_id} (Long): Partner ID whose GPG keys should be used for encryption.</li>
   * <li>{@code signing_key_id} (Long): Optional GPG Key ID to sign with.</li>
   * <li>{@code armor} (Boolean): Output ASCII-armored encrypted data.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction gpgEncrypt(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().gpgEncrypt(this.path, parameters, this.options);
  }

  /**
   * Extract a ZIP file to a destination folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination folder path for extracted files.</li>
   * <li>{@code filename} (String): Optional single entry filename to extract.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing files in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileAction}
   */
  public FileAction unzip(HashMap<String, Object> parameters) throws IOException {
    return filesClient().files().unzip(this.path, parameters, this.options);
  }

  /**
   * Begin File Upload
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code mkdir_parents} (Boolean): Create parent directories if they do not exist?</li>
   * <li>{@code part} (Long): Part if uploading a part.</li>
   * <li>{@code parts} (Long): How many parts to fetch?</li>
   * <li>{@code ref} (String): </li>
   * <li>{@code restart} (Long): File byte offset to restart from.</li>
   * <li>{@code size} (Long): Total bytes of file being uploaded (include bytes being retained if appending/restarting).</li>
   * <li>{@code with_rename} (Boolean): Allow file rename instead of overwrite?</li>
   * <li>{@code buffered_upload} (Boolean): If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.</li>
   * <li>{@code with_direct_connection_info} (Boolean): Include optional direct connection information for a direct Agent transfer attempt?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code FileUploadPart}
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
   * @see #download(String, HashMap, HashMap)
   */
  public static File download() throws RuntimeException {
    return download(null, null, null);
  }

  /**
   * Download File
   *
   * @see #download(String, HashMap, HashMap)
   */
  public static File download(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return download(path, parameters, null);
  }

  /**
   * Download File
   *
   * @see #download(String, HashMap, HashMap)
   */
  public static File download(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return download(null, parameters, options);
  }

  /**
   * Download File
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.</li>
   * <li>{@code preview_size} (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.</li>
   * <li>{@code with_previews} (Boolean): Include file preview information?</li>
   * <li>{@code with_priority_color} (Boolean): Include file priority color information?</li>
   * <li>{@code with_direct_connection_info} (Boolean): Include optional direct connection information for a direct Agent transfer attempt?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code File}
   */
  public static File download(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().download(path, parameters, options);
  }


  /**
   * Upload File
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static FileUploadPart create() throws RuntimeException {
    return create(null, null, null);
  }

  /**
   * Upload File
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static FileUploadPart create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  /**
   * Upload File
   *
   * @see #create(String, HashMap, HashMap)
   */
  public static FileUploadPart create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

  /**
   * Upload File
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code action} (String): The action to perform.  Can be `append`, `attachment`, `end`, `upload`, `put`, or may not exist</li>
   * <li>{@code custom_metadata} (Object): Custom metadata map to save when `action=end` completes the upload.  Replaces existing metadata; an empty map clears it.  No separate metadata-edit permission is required.  Supported on native files and configured remote mounts, excluding remote server automount paths.  Limited to 32 keys, 256 characters per key and 1024 characters per value.</li>
   * <li>{@code etags[etag]} (String[]): etag identifier.</li>
   * <li>{@code etags[part]} (Long[]): Part number.</li>
   * <li>{@code length} (Long): Length of file.</li>
   * <li>{@code mkdir_parents} (Boolean): Create parent directories if they do not exist?</li>
   * <li>{@code part} (Long): Part if uploading a part.</li>
   * <li>{@code parts} (Long): How many parts to fetch?</li>
   * <li>{@code provided_mtime} (String): User provided modification time.</li>
   * <li>{@code ref} (String): </li>
   * <li>{@code restart} (Long): File byte offset to restart from.</li>
   * <li>{@code size} (Long): Size of file.</li>
   * <li>{@code copy_behaviors} (Boolean): If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?</li>
   * <li>{@code structure} (String): If copying folder, copy just the structure?</li>
   * <li>{@code with_rename} (Boolean): Allow file rename instead of overwrite?</li>
   * <li>{@code buffered_upload} (Boolean): If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.</li>
   * <li>{@code with_direct_connection_info} (Boolean): Include optional direct connection information for a direct Agent transfer attempt?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileUploadPart}
   */
  public static FileUploadPart create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().create(path, parameters, options);
  }


  /**
   * Update File/Folder Metadata
   *
   * @see #update(String, HashMap, HashMap)
   */
  public static File update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update File/Folder Metadata
   *
   * @see #update(String, HashMap, HashMap)
   */
  public static File update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  /**
   * Update File/Folder Metadata
   *
   * @see #update(String, HashMap, HashMap)
   */
  public static File update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update File/Folder Metadata
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code custom_metadata} (Object): Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.</li>
   * <li>{@code provided_mtime} (String): Modified time of file.</li>
   * <li>{@code priority_color} (String): Priority/Bookmark color of file.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code File}
   */
  public static File update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().update(path, parameters, options);
  }


  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete File/Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code recursive} (Boolean): If true, will recursively delete folders.  Otherwise, will error on non-empty folders.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().files().delete(path, parameters, options);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public static void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static File find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static File find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static File find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Find File/Folder by Path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code preview_size} (String): Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.</li>
   * <li>{@code with_previews} (Boolean): Include file preview information?</li>
   * <li>{@code with_priority_color} (Boolean): Include file priority color information?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code File}
   */
  public static File find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().find(path, parameters, options);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static File get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public static File get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
   * List the contents of a ZIP file
   *
   * @see #zipListContents(String, HashMap, HashMap)
   */
  public static ListIterator<ZipListEntry> zipListContents() throws RuntimeException {
    return zipListContents(null, null, null);
  }

  /**
   * List the contents of a ZIP file
   *
   * @see #zipListContents(String, HashMap, HashMap)
   */
  public static ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return zipListContents(path, parameters, null);
  }

  /**
   * List the contents of a ZIP file
   *
   * @see #zipListContents(String, HashMap, HashMap)
   */
  public static ListIterator<ZipListEntry> zipListContents(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return zipListContents(null, parameters, options);
  }

  /**
   * List the contents of a ZIP file
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code ZipListEntry} records, in a {@code ListIterator}
   */
  public static ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().zipListContents(path, parameters, options);
  }


  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * @see #beginDelete(String, HashMap, HashMap)
   */
  public static FileAction beginDelete() throws RuntimeException {
    return beginDelete(null, null, null);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * @see #beginDelete(String, HashMap, HashMap)
   */
  public static FileAction beginDelete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginDelete(path, parameters, null);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * @see #beginDelete(String, HashMap, HashMap)
   */
  public static FileAction beginDelete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginDelete(null, parameters, options);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code recursive} (Boolean): If true, will recursively delete folders.  Otherwise, will error on non-empty folders.</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction beginDelete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().beginDelete(path, parameters, options);
  }


  /**
   * Copy File/Folder
   *
   * @see #copy(String, HashMap, HashMap)
   */
  public static FileAction copy() throws RuntimeException {
    return copy(null, null, null);
  }

  /**
   * Copy File/Folder
   *
   * @see #copy(String, HashMap, HashMap)
   */
  public static FileAction copy(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return copy(path, parameters, null);
  }

  /**
   * Copy File/Folder
   *
   * @see #copy(String, HashMap, HashMap)
   */
  public static FileAction copy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return copy(null, parameters, options);
  }

  /**
   * Copy File/Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Copy destination path.</li>
   * <li>{@code copy_behaviors} (Boolean): If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?</li>
   * <li>{@code structure} (Boolean): Copy structure only?</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file(s) in the destination?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction copy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().copy(path, parameters, options);
  }


  /**
   * Move File/Folder
   *
   * @see #move(String, HashMap, HashMap)
   */
  public static FileAction move() throws RuntimeException {
    return move(null, null, null);
  }

  /**
   * Move File/Folder
   *
   * @see #move(String, HashMap, HashMap)
   */
  public static FileAction move(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return move(path, parameters, null);
  }

  /**
   * Move File/Folder
   *
   * @see #move(String, HashMap, HashMap)
   */
  public static FileAction move(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return move(null, parameters, options);
  }

  /**
   * Move File/Folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Move destination path.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file(s) in the destination?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction move(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().move(path, parameters, options);
  }


  /**
   * Transform a file and save the output to a destination path
   *
   * @see #transform(String, HashMap, HashMap)
   */
  public static FileAction transform() throws RuntimeException {
    return transform(null, null, null);
  }

  /**
   * Transform a file and save the output to a destination path
   *
   * @see #transform(String, HashMap, HashMap)
   */
  public static FileAction transform(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return transform(path, parameters, null);
  }

  /**
   * Transform a file and save the output to a destination path
   *
   * @see #transform(String, HashMap, HashMap)
   */
  public static FileAction transform(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return transform(null, parameters, options);
  }

  /**
   * Transform a file and save the output to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination file path for the transformed output.</li>
   * <li>{@code transform_type} (String): Required. Transform type. Supported values are `image_convert`, `document_convert`, and `files_transform_script_execute`.</li>
   * <li>{@code target_format} (String): Required. Destination format to create.</li>
   * <li>{@code script} (String): Files TransformScript source. Required when transform_type is `files_transform_script_execute`.</li>
   * <li>{@code width} (Long): Maximum output width for image_convert.</li>
   * <li>{@code height} (Long): Maximum output height for image_convert.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction transform(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().transform(path, parameters, options);
  }


  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * @see #gpgDecrypt(String, HashMap, HashMap)
   */
  public static FileAction gpgDecrypt() throws RuntimeException {
    return gpgDecrypt(null, null, null);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * @see #gpgDecrypt(String, HashMap, HashMap)
   */
  public static FileAction gpgDecrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgDecrypt(path, parameters, null);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * @see #gpgDecrypt(String, HashMap, HashMap)
   */
  public static FileAction gpgDecrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return gpgDecrypt(null, parameters, options);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination file path for the decrypted file.</li>
   * <li>{@code gpg_key_ids} (Long[]): GPG Key IDs to decrypt with. If omitted, every accessible private GPG key in the source workspace is used.</li>
   * <li>{@code gpg_key_partner_id} (Long): Partner ID whose GPG keys should be used for decryption.</li>
   * <li>{@code use_all_private_keys} (Boolean): Use every accessible private GPG key in the source workspace for decryption.</li>
   * <li>{@code ignore_mdc_error} (Boolean): Ignore errors from the MDC (modification detection code) check.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction gpgDecrypt(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().gpgDecrypt(path, parameters, options);
  }


  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * @see #gpgEncrypt(String, HashMap, HashMap)
   */
  public static FileAction gpgEncrypt() throws RuntimeException {
    return gpgEncrypt(null, null, null);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * @see #gpgEncrypt(String, HashMap, HashMap)
   */
  public static FileAction gpgEncrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgEncrypt(path, parameters, null);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * @see #gpgEncrypt(String, HashMap, HashMap)
   */
  public static FileAction gpgEncrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return gpgEncrypt(null, parameters, options);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination file path for the encrypted file.</li>
   * <li>{@code gpg_key_ids} (Long[]): GPG Key IDs to encrypt with.</li>
   * <li>{@code gpg_key_partner_id} (Long): Partner ID whose GPG keys should be used for encryption.</li>
   * <li>{@code signing_key_id} (Long): Optional GPG Key ID to sign with.</li>
   * <li>{@code armor} (Boolean): Output ASCII-armored encrypted data.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction gpgEncrypt(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().gpgEncrypt(path, parameters, options);
  }


  /**
   * Extract a ZIP file to a destination folder
   *
   * @see #unzip(String, HashMap, HashMap)
   */
  public static FileAction unzip() throws RuntimeException {
    return unzip(null, null, null);
  }

  /**
   * Extract a ZIP file to a destination folder
   *
   * @see #unzip(String, HashMap, HashMap)
   */
  public static FileAction unzip(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return unzip(path, parameters, null);
  }

  /**
   * Extract a ZIP file to a destination folder
   *
   * @see #unzip(String, HashMap, HashMap)
   */
  public static FileAction unzip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return unzip(null, parameters, options);
  }

  /**
   * Extract a ZIP file to a destination folder
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code destination} (String): Required. Destination folder path for extracted files.</li>
   * <li>{@code filename} (String): Optional single entry filename to extract.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing files in the destination?</li>
   * </ul>
   *
   * @param path ZIP file path to extract.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction unzip(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().unzip(path, parameters, options);
  }


  /**
   * Create a ZIP from one or more paths and save it to a destination path
   *
   * @see #zip(HashMap, HashMap)
   */
  public static FileAction zip() throws RuntimeException {
    return zip(null, null);
  }

  /**
   * Create a ZIP from one or more paths and save it to a destination path
   *
   * @see #zip(HashMap, HashMap)
   */
  public static FileAction zip(HashMap<String, Object> parameters) throws RuntimeException {
    return zip(parameters, null);
  }


  /**
   * Create a ZIP from one or more paths and save it to a destination path
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code paths} (String[]): Required. Paths to include in the ZIP.</li>
   * <li>{@code destination} (String): Required. Destination file path for the ZIP.</li>
   * <li>{@code overwrite} (Boolean): Overwrite existing file in the destination?</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileAction}
   */
  public static FileAction zip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().zip(parameters, options);
  }


  /**
   * Begin File Upload
   *
   * @see #beginUpload(String, HashMap, HashMap)
   */
  public static FileUploadPart beginUpload() throws RuntimeException {
    return beginUpload(null, null, null);
  }

  /**
   * Begin File Upload
   *
   * @see #beginUpload(String, HashMap, HashMap)
   */
  public static FileUploadPart beginUpload(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginUpload(path, parameters, null);
  }

  /**
   * Begin File Upload
   *
   * @see #beginUpload(String, HashMap, HashMap)
   */
  public static FileUploadPart beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginUpload(null, parameters, options);
  }

  /**
   * Begin File Upload
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code mkdir_parents} (Boolean): Create parent directories if they do not exist?</li>
   * <li>{@code part} (Long): Part if uploading a part.</li>
   * <li>{@code parts} (Long): How many parts to fetch?</li>
   * <li>{@code ref} (String): </li>
   * <li>{@code restart} (Long): File byte offset to restart from.</li>
   * <li>{@code size} (Long): Total bytes of file being uploaded (include bytes being retained if appending/restarting).</li>
   * <li>{@code with_rename} (Boolean): Allow file rename instead of overwrite?</li>
   * <li>{@code buffered_upload} (Boolean): If true, and the path refers to a destination not stored on Files.com (such as a remote server mount), the upload will be uploaded first to Files.com before being sent to the remote server mount. This can allow clients to upload using parallel parts to a remote server destination that does not offer parallel parts support natively.</li>
   * <li>{@code with_direct_connection_info} (Boolean): Include optional direct connection information for a direct Agent transfer attempt?</li>
   * </ul>
   *
   * @param path Path to operate on.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code FileUploadPart}
   */
  public static FileUploadPart beginUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().files().beginUpload(path, parameters, options);
  }


}
