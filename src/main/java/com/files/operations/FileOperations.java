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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * Parameters:
   *   action - string - Can be blank, `redirect` or `stat`.  If set to `stat`, we will return file information but without a download URL, and without logging a download.  If set to `redirect` we will serve a 302 redirect directly to the file.  This is used for integrations with Zapier, and is not recommended for most integrations.
   *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
   *   with_previews - boolean - Include file preview information?
   *   with_priority_color - boolean - Include file priority color information?
   *   with_direct_connection_info - boolean - Include optional direct connection information for a direct Agent transfer attempt?
   */
  public File download() throws RuntimeException {
    return download(null, null, null);
  }

  public File download(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return download(path, parameters, null);
  }

  public File download(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return download(null, parameters, options);
  }

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
  public FileUploadPart create() throws RuntimeException {
    return create(null, null, null);
  }

  public FileUploadPart create(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return create(path, parameters, null);
  }

  public FileUploadPart create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return create(null, parameters, options);
  }

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
   * Parameters:
   *   custom_metadata - object - Custom metadata map of keys and values. Limited to 32 keys, 256 characters per key and 1024 characters per value.
   *   provided_mtime - string - Modified time of file.
   *   priority_color - string - Priority/Bookmark color of file.
   */
  public File update() throws RuntimeException {
    return update(null, null, null);
  }

  public File update(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return update(path, parameters, null);
  }

  public File update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

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
   * Parameters:
   *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    delete(path, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

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

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(path, parameters, options);
  }

  /**
   * Parameters:
   *   path (required) - string - Path to operate on.
   *   preview_size - string - Request a preview size.  Can be `small` (default), `large`, `xlarge`, or `pdf`.
   *   with_previews - boolean - Include file preview information?
   *   with_priority_color - boolean - Include file priority color information?
   */
  public File find() throws RuntimeException {
    return find(null, null, null);
  }

  public File find(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return find(path, parameters, null);
  }

  public File find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

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

  public File get() throws RuntimeException {
    return get(null, null, null);
  }

  public File get(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(path, parameters, options);
  }

  /**
   * List the contents of a ZIP file
   */
  public ListIterator<ZipListEntry> zipListContents() throws RuntimeException {
    return zipListContents(null, null, null);
  }

  public ListIterator<ZipListEntry> zipListContents(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return zipListContents(path, parameters, null);
  }

  public ListIterator<ZipListEntry> zipListContents(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return zipListContents(null, parameters, options);
  }

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
   * Parameters:
   *   recursive - boolean - If true, will recursively delete folders.  Otherwise, will error on non-empty folders.
   */
  public FileAction beginDelete() throws RuntimeException {
    return beginDelete(null, null, null);
  }

  public FileAction beginDelete(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginDelete(path, parameters, null);
  }

  public FileAction beginDelete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginDelete(null, parameters, options);
  }

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
   * Parameters:
   *   destination (required) - string - Copy destination path.
   *   copy_behaviors - boolean - If copying a folder, also copy supported behaviors, email notification subscriptions, and per-folder branding to the destination folder tree?
   *   structure - boolean - Copy structure only?
   *   overwrite - boolean - Overwrite existing file(s) in the destination?
   */
  public FileAction copy() throws RuntimeException {
    return copy(null, null, null);
  }

  public FileAction copy(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return copy(path, parameters, null);
  }

  public FileAction copy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return copy(null, parameters, options);
  }

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
   * Parameters:
   *   destination (required) - string - Move destination path.
   *   overwrite - boolean - Overwrite existing file(s) in the destination?
   */
  public FileAction move() throws RuntimeException {
    return move(null, null, null);
  }

  public FileAction move(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return move(path, parameters, null);
  }

  public FileAction move(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return move(null, parameters, options);
  }

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
   * Parameters:
   *   destination (required) - string - Destination file path for the transformed output.
   *   transform_type (required) - string - Transform type. Supported values are `image_convert`, `document_convert`, and `files_transform_script_execute`.
   *   target_format (required) - string - Destination format to create.
   *   script - string - Files TransformScript source. Required when transform_type is `files_transform_script_execute`.
   *   width - int64 - Maximum output width for image_convert.
   *   height - int64 - Maximum output height for image_convert.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction transform() throws RuntimeException {
    return transform(null, null, null);
  }

  public FileAction transform(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return transform(path, parameters, null);
  }

  public FileAction transform(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return transform(null, parameters, options);
  }

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
   * Parameters:
   *   destination (required) - string - Destination file path for the decrypted file.
   *   gpg_key_ids - array(int64) - GPG Key IDs to decrypt with. If omitted, every accessible private GPG key in the source workspace is used.
   *   gpg_key_partner_id - int64 - Partner ID whose GPG keys should be used for decryption.
   *   use_all_private_keys - boolean - Use every accessible private GPG key in the source workspace for decryption.
   *   ignore_mdc_error - boolean - Ignore errors from the MDC (modification detection code) check.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction gpgDecrypt() throws RuntimeException {
    return gpgDecrypt(null, null, null);
  }

  public FileAction gpgDecrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgDecrypt(path, parameters, null);
  }

  public FileAction gpgDecrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return gpgDecrypt(null, parameters, options);
  }

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
   * Parameters:
   *   destination (required) - string - Destination file path for the encrypted file.
   *   gpg_key_ids - array(int64) - GPG Key IDs to encrypt with.
   *   gpg_key_partner_id - int64 - Partner ID whose GPG keys should be used for encryption.
   *   signing_key_id - int64 - Optional GPG Key ID to sign with.
   *   armor - boolean - Output ASCII-armored encrypted data.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction gpgEncrypt() throws RuntimeException {
    return gpgEncrypt(null, null, null);
  }

  public FileAction gpgEncrypt(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return gpgEncrypt(path, parameters, null);
  }

  public FileAction gpgEncrypt(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return gpgEncrypt(null, parameters, options);
  }

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
   * Parameters:
   *   destination (required) - string - Destination folder path for extracted files.
   *   filename - string - Optional single entry filename to extract.
   *   overwrite - boolean - Overwrite existing files in the destination?
   */
  public FileAction unzip() throws RuntimeException {
    return unzip(null, null, null);
  }

  public FileAction unzip(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return unzip(path, parameters, null);
  }

  public FileAction unzip(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return unzip(null, parameters, options);
  }

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
   * Parameters:
   *   paths (required) - array(string) - Paths to include in the ZIP.
   *   destination (required) - string - Destination file path for the ZIP.
   *   overwrite - boolean - Overwrite existing file in the destination?
   */
  public FileAction zip() throws RuntimeException {
    return zip(null, null);
  }

  public FileAction zip(HashMap<String, Object> parameters) throws RuntimeException {
    return zip(parameters, null);
  }

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
  public FileUploadPart beginUpload() throws RuntimeException {
    return beginUpload(null, null, null);
  }

  public FileUploadPart beginUpload(String path, HashMap<String, Object> parameters) throws RuntimeException {
    return beginUpload(path, parameters, null);
  }

  public FileUploadPart beginUpload(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return beginUpload(null, parameters, options);
  }

  public FileUploadPart beginUpload(String path, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
    TypeReference<FileUploadPart> typeReference = new TypeReference<FileUploadPart>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }
}
