package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.File;
import com.files.models.FileAction;
import com.files.models.FileUploadPart;
import com.files.models.ZipListEntry;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * File operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#files()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.File} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class FileOperations extends ResourceOperations {
  public FileOperations(FilesClient client) {
    super(client);
  }

  /**
   * Download File
   *
   * @see #download(String, HashMap, HashMap)
   */
  public File download() throws RuntimeException {
    return download(null, null, null);
  }

  /**
   * Download File
   *
   * @see #download(String, HashMap, HashMap)
   */
  public File download(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return download(path, parameters, null);
  }

  /**
   * Download File
   *
   * @see #download(String, HashMap, HashMap)
   */
  public File download(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public File download(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }

    if (parameters.containsKey("with_previews") && !(parameters.get("with_previews") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_previews must be of type Boolean parameters[\"with_previews\"]");
    }

    if (parameters.containsKey("with_priority_color") && !(parameters.get("with_priority_color") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_priority_color must be of type Boolean parameters[\"with_priority_color\"]");
    }

    if (parameters.containsKey("with_direct_connection_info") && !(parameters.get("with_direct_connection_info") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_direct_connection_info must be of type Boolean parameters[\"with_direct_connection_info\"]");
    }

    String apiPath = String.format("/files/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<File> typeReference = new TypeReference<File>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Upload File
   *
   * @see #create(String, HashMap, HashMap)
   */
  public FileUploadPart create() throws RuntimeException {
    return create(null, null, null);
  }

  /**
   * Upload File
   *
   * @see #create(String, HashMap, HashMap)
   */
  public FileUploadPart create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  /**
   * Upload File
   *
   * @see #create(String, HashMap, HashMap)
   */
  public FileUploadPart create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileUploadPart create(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path != null && parameters.get("path") == null) {
      parameters.put("path", path);
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    if (parameters.containsKey("custom_metadata") && !(parameters.get("custom_metadata") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: custom_metadata must be of type Object parameters[\"custom_metadata\"]");
    }

    if (parameters.containsKey("length") && !(parameters.get("length") instanceof Long || parameters.get("length") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: length must be of type Long or Integer parameters[\"length\"]");
    }

    if (parameters.containsKey("mkdir_parents") && !(parameters.get("mkdir_parents") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: mkdir_parents must be of type Boolean parameters[\"mkdir_parents\"]");
    }

    if (parameters.containsKey("part") && !(parameters.get("part") instanceof Long || parameters.get("part") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: part must be of type Long or Integer parameters[\"part\"]");
    }

    if (parameters.containsKey("parts") && !(parameters.get("parts") instanceof Long || parameters.get("parts") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: parts must be of type Long or Integer parameters[\"parts\"]");
    }

    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }

    if (parameters.containsKey("ref") && !(parameters.get("ref") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ref must be of type String parameters[\"ref\"]");
    }

    if (parameters.containsKey("restart") && !(parameters.get("restart") instanceof Long || parameters.get("restart") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: restart must be of type Long or Integer parameters[\"restart\"]");
    }

    if (parameters.containsKey("size") && !(parameters.get("size") instanceof Long || parameters.get("size") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: size must be of type Long or Integer parameters[\"size\"]");
    }

    if (parameters.containsKey("copy_behaviors") && !(parameters.get("copy_behaviors") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: copy_behaviors must be of type Boolean parameters[\"copy_behaviors\"]");
    }

    if (parameters.containsKey("structure") && !(parameters.get("structure") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: structure must be of type String parameters[\"structure\"]");
    }

    if (parameters.containsKey("with_rename") && !(parameters.get("with_rename") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_rename must be of type Boolean parameters[\"with_rename\"]");
    }

    if (parameters.containsKey("buffered_upload") && !(parameters.get("buffered_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: buffered_upload must be of type Boolean parameters[\"buffered_upload\"]");
    }

    if (parameters.containsKey("with_direct_connection_info") && !(parameters.get("with_direct_connection_info") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_direct_connection_info must be of type Boolean parameters[\"with_direct_connection_info\"]");
    }

    if (!parameters.containsKey("action")) {
      parameters.put("action", "put");
    }

    String apiPath = String.format("/files/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileUploadPart> typeReference = new TypeReference<FileUploadPart>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Update File/Folder Metadata
   *
   * @see #update(String, HashMap, HashMap)
   */
  public File update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update File/Folder Metadata
   *
   * @see #update(String, HashMap, HashMap)
   */
  public File update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  /**
   * Update File/Folder Metadata
   *
   * @see #update(String, HashMap, HashMap)
   */
  public File update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public File update(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("custom_metadata") && !(parameters.get("custom_metadata") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: custom_metadata must be of type Object parameters[\"custom_metadata\"]");
    }

    if (parameters.containsKey("provided_mtime") && !(parameters.get("provided_mtime") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: provided_mtime must be of type String parameters[\"provided_mtime\"]");
    }

    if (parameters.containsKey("priority_color") && !(parameters.get("priority_color") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: priority_color must be of type String parameters[\"priority_color\"]");
    }

    String apiPath = String.format("/files/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<File> typeReference = new TypeReference<File>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public void delete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    String apiPath = String.format("/files/%s", UrlUtils.encodeUrlPath(path));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete File/Folder
   *
   * @see #delete(String, HashMap, HashMap)
   */
  public void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public File find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public File find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public File find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public File find(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("preview_size") && !(parameters.get("preview_size") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: preview_size must be of type String parameters[\"preview_size\"]");
    }

    if (parameters.containsKey("with_previews") && !(parameters.get("with_previews") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_previews must be of type Boolean parameters[\"with_previews\"]");
    }

    if (parameters.containsKey("with_priority_color") && !(parameters.get("with_priority_color") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_priority_color must be of type Boolean parameters[\"with_priority_color\"]");
    }

    String apiPath = String.format("/file_actions/metadata/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<File> typeReference = new TypeReference<File>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public File get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Find File/Folder by Path
   *
   * @see #find(String, HashMap, HashMap)
   */
  public File get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
   * List the contents of a ZIP file
   *
   * @see #zipListContents(String, HashMap, HashMap)
   */
  public ListIterator<ZipListEntry> zipListContents() throws RuntimeException {
    return zipListContents(null, null, null);
  }

  /**
   * List the contents of a ZIP file
   *
   * @see #zipListContents(String, HashMap, HashMap)
   */
  public ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return zipListContents(path, parameters, null);
  }

  /**
   * List the contents of a ZIP file
   *
   * @see #zipListContents(String, HashMap, HashMap)
   */
  public ListIterator<ZipListEntry> zipListContents(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    String apiPath = String.format("/file_actions/zip_list/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<ZipListEntry>> typeReference = new TypeReference<List<ZipListEntry>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * @see #beginDelete(String, HashMap, HashMap)
   */
  public FileAction beginDelete() throws RuntimeException {
    return beginDelete(null, null, null);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * @see #beginDelete(String, HashMap, HashMap)
   */
  public FileAction beginDelete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginDelete(path, parameters, null);
  }

  /**
   * Delete File/Folder and return its File Action.  A pending or processing deletion continues in the background after this returns; check its File Migration for completion.  Waiting for the File Migration does not cancel the deletion
   *
   * @see #beginDelete(String, HashMap, HashMap)
   */
  public FileAction beginDelete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction beginDelete(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("recursive") && !(parameters.get("recursive") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: recursive must be of type Boolean parameters[\"recursive\"]");
    }

    String apiPath = String.format("/file_actions/delete/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Copy File/Folder
   *
   * @see #copy(String, HashMap, HashMap)
   */
  public FileAction copy() throws RuntimeException {
    return copy(null, null, null);
  }

  /**
   * Copy File/Folder
   *
   * @see #copy(String, HashMap, HashMap)
   */
  public FileAction copy(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return copy(path, parameters, null);
  }

  /**
   * Copy File/Folder
   *
   * @see #copy(String, HashMap, HashMap)
   */
  public FileAction copy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction copy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("copy_behaviors") && !(parameters.get("copy_behaviors") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: copy_behaviors must be of type Boolean parameters[\"copy_behaviors\"]");
    }

    if (parameters.containsKey("structure") && !(parameters.get("structure") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: structure must be of type Boolean parameters[\"structure\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = String.format("/file_actions/copy/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Move File/Folder
   *
   * @see #move(String, HashMap, HashMap)
   */
  public FileAction move() throws RuntimeException {
    return move(null, null, null);
  }

  /**
   * Move File/Folder
   *
   * @see #move(String, HashMap, HashMap)
   */
  public FileAction move(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return move(path, parameters, null);
  }

  /**
   * Move File/Folder
   *
   * @see #move(String, HashMap, HashMap)
   */
  public FileAction move(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction move(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = String.format("/file_actions/move/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Transform a file and save the output to a destination path
   *
   * @see #transform(String, HashMap, HashMap)
   */
  public FileAction transform() throws RuntimeException {
    return transform(null, null, null);
  }

  /**
   * Transform a file and save the output to a destination path
   *
   * @see #transform(String, HashMap, HashMap)
   */
  public FileAction transform(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return transform(path, parameters, null);
  }

  /**
   * Transform a file and save the output to a destination path
   *
   * @see #transform(String, HashMap, HashMap)
   */
  public FileAction transform(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction transform(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!parameters.containsKey("transform_type") || parameters.get("transform_type") == null) {
      throw new NullPointerException("Parameter missing: transform_type parameters[\"transform_type\"]");
    }

    if (!parameters.containsKey("target_format") || parameters.get("target_format") == null) {
      throw new NullPointerException("Parameter missing: target_format parameters[\"target_format\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("transform_type") && !(parameters.get("transform_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: transform_type must be of type String parameters[\"transform_type\"]");
    }

    if (parameters.containsKey("target_format") && !(parameters.get("target_format") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: target_format must be of type String parameters[\"target_format\"]");
    }

    if (parameters.containsKey("script") && !(parameters.get("script") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: script must be of type String parameters[\"script\"]");
    }

    if (parameters.containsKey("width") && !(parameters.get("width") instanceof Long || parameters.get("width") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: width must be of type Long or Integer parameters[\"width\"]");
    }

    if (parameters.containsKey("height") && !(parameters.get("height") instanceof Long || parameters.get("height") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: height must be of type Long or Integer parameters[\"height\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = String.format("/file_actions/transform/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * @see #gpgDecrypt(String, HashMap, HashMap)
   */
  public FileAction gpgDecrypt() throws RuntimeException {
    return gpgDecrypt(null, null, null);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * @see #gpgDecrypt(String, HashMap, HashMap)
   */
  public FileAction gpgDecrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgDecrypt(path, parameters, null);
  }

  /**
   * Decrypt a GPG-encrypted file and save it to a destination path
   *
   * @see #gpgDecrypt(String, HashMap, HashMap)
   */
  public FileAction gpgDecrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction gpgDecrypt(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("gpg_key_ids") && !(parameters.get("gpg_key_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: gpg_key_ids must be of type Long[] parameters[\"gpg_key_ids\"]");
    }

    if (parameters.containsKey("gpg_key_partner_id") && !(parameters.get("gpg_key_partner_id") instanceof Long || parameters.get("gpg_key_partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: gpg_key_partner_id must be of type Long or Integer parameters[\"gpg_key_partner_id\"]");
    }

    if (parameters.containsKey("use_all_private_keys") && !(parameters.get("use_all_private_keys") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: use_all_private_keys must be of type Boolean parameters[\"use_all_private_keys\"]");
    }

    if (parameters.containsKey("ignore_mdc_error") && !(parameters.get("ignore_mdc_error") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: ignore_mdc_error must be of type Boolean parameters[\"ignore_mdc_error\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = String.format("/file_actions/gpg_decrypt/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * @see #gpgEncrypt(String, HashMap, HashMap)
   */
  public FileAction gpgEncrypt() throws RuntimeException {
    return gpgEncrypt(null, null, null);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * @see #gpgEncrypt(String, HashMap, HashMap)
   */
  public FileAction gpgEncrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgEncrypt(path, parameters, null);
  }

  /**
   * Encrypt a file with GPG and save it to a destination path
   *
   * @see #gpgEncrypt(String, HashMap, HashMap)
   */
  public FileAction gpgEncrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction gpgEncrypt(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (path == null) {
      throw new NullPointerException("Argument or Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (!(path instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("gpg_key_ids") && !(parameters.get("gpg_key_ids") instanceof Long[])) {
      throw new IllegalArgumentException("Bad parameter: gpg_key_ids must be of type Long[] parameters[\"gpg_key_ids\"]");
    }

    if (parameters.containsKey("gpg_key_partner_id") && !(parameters.get("gpg_key_partner_id") instanceof Long || parameters.get("gpg_key_partner_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: gpg_key_partner_id must be of type Long or Integer parameters[\"gpg_key_partner_id\"]");
    }

    if (parameters.containsKey("signing_key_id") && !(parameters.get("signing_key_id") instanceof Long || parameters.get("signing_key_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: signing_key_id must be of type Long or Integer parameters[\"signing_key_id\"]");
    }

    if (parameters.containsKey("armor") && !(parameters.get("armor") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: armor must be of type Boolean parameters[\"armor\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = String.format("/file_actions/gpg_encrypt/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Extract a ZIP file to a destination folder
   *
   * @see #unzip(String, HashMap, HashMap)
   */
  public FileAction unzip() throws RuntimeException {
    return unzip(null, null, null);
  }

  /**
   * Extract a ZIP file to a destination folder
   *
   * @see #unzip(String, HashMap, HashMap)
   */
  public FileAction unzip(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return unzip(path, parameters, null);
  }

  /**
   * Extract a ZIP file to a destination folder
   *
   * @see #unzip(String, HashMap, HashMap)
   */
  public FileAction unzip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public FileAction unzip(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (path == null && parameters.containsKey("path") && parameters.get("path") != null) {
      path = (String) parameters.get("path");
    }

    if (!parameters.containsKey("path") || parameters.get("path") == null) {
      throw new NullPointerException("Parameter missing: path parameters[\"path\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("filename") && !(parameters.get("filename") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: filename must be of type String parameters[\"filename\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = "/file_actions/unzip";
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Create a ZIP from one or more paths and save it to a destination path
   *
   * @see #zip(HashMap, HashMap)
   */
  public FileAction zip() throws RuntimeException {
    return zip(null, null);
  }

  /**
   * Create a ZIP from one or more paths and save it to a destination path
   *
   * @see #zip(HashMap, HashMap)
   */
  public FileAction zip(HashMap<String, Object> parameters) throws RuntimeException {
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
  public FileAction zip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("paths") || parameters.get("paths") == null) {
      throw new NullPointerException("Parameter missing: paths parameters[\"paths\"]");
    }

    if (!parameters.containsKey("destination") || parameters.get("destination") == null) {
      throw new NullPointerException("Parameter missing: destination parameters[\"destination\"]");
    }

    if (parameters.containsKey("paths") && !(parameters.get("paths") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: paths must be of type String[] parameters[\"paths\"]");
    }

    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }

    if (parameters.containsKey("overwrite") && !(parameters.get("overwrite") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: overwrite must be of type Boolean parameters[\"overwrite\"]");
    }

    String apiPath = "/file_actions/zip";
    TypeReference<FileAction> typeReference = new TypeReference<FileAction>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Begin File Upload
   *
   * @see #beginUpload(String, HashMap, HashMap)
   */
  public List<FileUploadPart> beginUpload() throws RuntimeException {
    return beginUpload(null, null, null);
  }

  /**
   * Begin File Upload
   *
   * @see #beginUpload(String, HashMap, HashMap)
   */
  public List<FileUploadPart> beginUpload(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginUpload(path, parameters, null);
  }

  /**
   * Begin File Upload
   *
   * @see #beginUpload(String, HashMap, HashMap)
   */
  public List<FileUploadPart> beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
   * @return the {@code List<FileUploadPart>}
   */
  public List<FileUploadPart> beginUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

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

    if (parameters.containsKey("part") && !(parameters.get("part") instanceof Long || parameters.get("part") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: part must be of type Long or Integer parameters[\"part\"]");
    }

    if (parameters.containsKey("parts") && !(parameters.get("parts") instanceof Long || parameters.get("parts") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: parts must be of type Long or Integer parameters[\"parts\"]");
    }

    if (parameters.containsKey("ref") && !(parameters.get("ref") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: ref must be of type String parameters[\"ref\"]");
    }

    if (parameters.containsKey("restart") && !(parameters.get("restart") instanceof Long || parameters.get("restart") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: restart must be of type Long or Integer parameters[\"restart\"]");
    }

    if (parameters.containsKey("size") && !(parameters.get("size") instanceof Long || parameters.get("size") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: size must be of type Long or Integer parameters[\"size\"]");
    }

    if (parameters.containsKey("with_rename") && !(parameters.get("with_rename") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_rename must be of type Boolean parameters[\"with_rename\"]");
    }

    if (parameters.containsKey("buffered_upload") && !(parameters.get("buffered_upload") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: buffered_upload must be of type Boolean parameters[\"buffered_upload\"]");
    }

    if (parameters.containsKey("with_direct_connection_info") && !(parameters.get("with_direct_connection_info") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: with_direct_connection_info must be of type Boolean parameters[\"with_direct_connection_info\"]");
    }

    String apiPath = String.format("/file_actions/begin_upload/%s", UrlUtils.encodeUrlPath(path));
    TypeReference<List<FileUploadPart>> typeReference = new TypeReference<List<FileUploadPart>>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
