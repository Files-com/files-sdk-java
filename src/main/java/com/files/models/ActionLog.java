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
public class ActionLog implements ModelInterface {
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


  public ActionLog() {
    this(null, null);
  }

  public ActionLog(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionLog(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * The type of action performed
  */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  /**
  * Time the action was performed
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Destination path, for moves and copies
  */
  @JsonProperty("destination")
  public String destination;

  public String getDestination() {
    return destination;
  }

  /**
  * Failure type, if applicable
  */
  @JsonProperty("failure_type")
  public String failureType;

  public String getFailureType() {
    return failureType;
  }

  /**
  * Folder involved in the action
  */
  @JsonProperty("folder")
  public String folder;

  public String getFolder() {
    return folder;
  }

  /**
  * Interface used to perform the action
  */
  @JsonProperty("interface")
  public String interfaceName;

  public String getInterfaceName() {
    return interfaceName;
  }

  /**
  * IP address from which the action was performed
  */
  @JsonProperty("ip")
  public String ip;

  public String getIp() {
    return ip;
  }

  /**
  * File or Folder ID associated with the action
  */
  @JsonProperty("metadata_dm_id")
  public Long metadataDmId;

  public Long getMetadataDmId() {
    return metadataDmId;
  }

  /**
  * Parent File or Folder ID associated with the action
  */
  @JsonProperty("parent_metadata_dm_id")
  public Long parentMetadataDmId;

  public Long getParentMetadataDmId() {
    return parentMetadataDmId;
  }

  /**
  * File path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
  * Site ID where the action took place
  */
  @JsonProperty("site_id")
  public Long siteId;

  public Long getSiteId() {
    return siteId;
  }

  /**
  * Source path, for moves and copies
  */
  @JsonProperty("src")
  public String src;

  public String getSrc() {
    return src;
  }

  /**
  * User ID who performed the action
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  /**
  * Username who performed the action
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `folder`, `path`, `src`, `destination`, `created_at`, `user_id` or `username`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `folder`, `path`, `src`, `destination` or `username`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`. Valid field combinations are `[ folder ]`, `[ path ]`, `[ src ]`, `[ destination ]`, `[ created_at ]`, `[ user_id ]`, `[ username ]`, `[ folder, path ]`, `[ folder, src ]`, `[ folder, destination ]`, `[ folder, created_at ]`, `[ folder, user_id ]`, `[ folder, username ]`, `[ path, src ]`, `[ path, destination ]`, `[ path, created_at ]`, `[ path, user_id ]`, `[ path, username ]`, `[ src, destination ]`, `[ src, created_at ]`, `[ src, user_id ]`, `[ src, username ]`, `[ destination, created_at ]`, `[ destination, user_id ]`, `[ destination, username ]`, `[ created_at, user_id ]`, `[ created_at, username ]`, `[ user_id, username ]`, `[ folder, path, src ]`, `[ folder, path, destination ]`, `[ folder, path, created_at ]`, `[ folder, path, user_id ]`, `[ folder, path, username ]`, `[ folder, src, destination ]`, `[ folder, src, created_at ]`, `[ folder, src, user_id ]`, `[ folder, src, username ]`, `[ folder, destination, created_at ]`, `[ folder, destination, user_id ]`, `[ folder, destination, username ]`, `[ folder, created_at, user_id ]`, `[ folder, created_at, username ]`, `[ folder, user_id, username ]`, `[ path, src, destination ]`, `[ path, src, created_at ]`, `[ path, src, user_id ]`, `[ path, src, username ]`, `[ path, destination, created_at ]`, `[ path, destination, user_id ]`, `[ path, destination, username ]`, `[ path, created_at, user_id ]`, `[ path, created_at, username ]`, `[ path, user_id, username ]`, `[ src, destination, created_at ]`, `[ src, destination, user_id ]`, `[ src, destination, username ]`, `[ src, created_at, user_id ]`, `[ src, created_at, username ]`, `[ src, user_id, username ]`, `[ destination, created_at, user_id ]`, `[ destination, created_at, username ]`, `[ destination, user_id, username ]`, `[ created_at, user_id, username ]`, `[ folder, path, src, destination ]`, `[ folder, path, src, created_at ]`, `[ folder, path, src, user_id ]`, `[ folder, path, src, username ]`, `[ folder, path, destination, created_at ]`, `[ folder, path, destination, user_id ]`, `[ folder, path, destination, username ]`, `[ folder, path, created_at, user_id ]`, `[ folder, path, created_at, username ]`, `[ folder, path, user_id, username ]`, `[ folder, src, destination, created_at ]`, `[ folder, src, destination, user_id ]`, `[ folder, src, destination, username ]`, `[ folder, src, created_at, user_id ]`, `[ folder, src, created_at, username ]`, `[ folder, src, user_id, username ]`, `[ folder, destination, created_at, user_id ]`, `[ folder, destination, created_at, username ]`, `[ folder, destination, user_id, username ]`, `[ folder, created_at, user_id, username ]`, `[ path, src, destination, created_at ]`, `[ path, src, destination, user_id ]`, `[ path, src, destination, username ]`, `[ path, src, created_at, user_id ]`, `[ path, src, created_at, username ]`, `[ path, src, user_id, username ]`, `[ path, destination, created_at, user_id ]`, `[ path, destination, created_at, username ]`, `[ path, destination, user_id, username ]`, `[ path, created_at, user_id, username ]`, `[ src, destination, created_at, user_id ]`, `[ src, destination, created_at, username ]`, `[ src, destination, user_id, username ]`, `[ src, created_at, user_id, username ]`, `[ destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at ]`, `[ folder, path, src, destination, user_id ]`, `[ folder, path, src, destination, username ]`, `[ folder, path, src, created_at, user_id ]`, `[ folder, path, src, created_at, username ]`, `[ folder, path, src, user_id, username ]`, `[ folder, path, destination, created_at, user_id ]`, `[ folder, path, destination, created_at, username ]`, `[ folder, path, destination, user_id, username ]`, `[ folder, path, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id ]`, `[ folder, src, destination, created_at, username ]`, `[ folder, src, destination, user_id, username ]`, `[ folder, src, created_at, user_id, username ]`, `[ folder, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id ]`, `[ path, src, destination, created_at, username ]`, `[ path, src, destination, user_id, username ]`, `[ path, src, created_at, user_id, username ]`, `[ path, destination, created_at, user_id, username ]`, `[ src, destination, created_at, user_id, username ]`, `[ folder, path, src, destination, created_at, user_id ]`, `[ folder, path, src, destination, created_at, username ]`, `[ folder, path, src, destination, user_id, username ]`, `[ folder, path, src, created_at, user_id, username ]`, `[ folder, path, destination, created_at, user_id, username ]`, `[ folder, src, destination, created_at, user_id, username ]`, `[ path, src, destination, created_at, user_id, username ]` or `[ folder, path, src, destination, created_at, user_id, username ]`.
  */
  public static ListIterator<ActionLog> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<ActionLog> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<ActionLog> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
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


    String url = String.format("%s%s/action_logs", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<ActionLog>> typeReference = new TypeReference<List<ActionLog>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<ActionLog> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<ActionLog> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
