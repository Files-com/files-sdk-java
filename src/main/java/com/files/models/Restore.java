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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restore implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public Restore() {
    this(null, null);
  }

  public Restore(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Restore(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Restore all files deleted after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.
  */
  @Getter
  @Setter
  @JsonProperty("earliest_date")
  public Date earliestDate;

  /**
  * Restore Record ID.
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Number of directories that were successfully restored.
  */
  @Getter
  @Setter
  @JsonProperty("dirs_restored")
  public Long dirsRestored;

  /**
  * Number of directories that were not able to be restored.
  */
  @Getter
  @Setter
  @JsonProperty("dirs_errored")
  public Long dirsErrored;

  /**
  * Total number of directories processed.
  */
  @Getter
  @Setter
  @JsonProperty("dirs_total")
  public Long dirsTotal;

  /**
  * Number of files successfully restored.
  */
  @Getter
  @Setter
  @JsonProperty("files_restored")
  public Long filesRestored;

  /**
  * Number of files that were not able to be restored.
  */
  @Getter
  @Setter
  @JsonProperty("files_errored")
  public Long filesErrored;

  /**
  * Total number of files processed.
  */
  @Getter
  @Setter
  @JsonProperty("files_total")
  public Long filesTotal;

  /**
  * Prefix of the files/folders to restore. To restore a folder, add a trailing slash to the folder name. Do not use a leading slash. To restore all deleted items, specify an empty string (`''`) in the prefix field or omit the field from the request.
  */
  @Getter
  @Setter
  @JsonProperty("prefix")
  public String prefix;

  /**
  * If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.
  */
  @Getter
  @Setter
  @JsonProperty("restore_in_place")
  public Boolean restoreInPlace;

  /**
  * If true, we will also restore any Permissions that match the same path prefix from the same dates.
  */
  @Getter
  @Setter
  @JsonProperty("restore_deleted_permissions")
  public Boolean restoreDeletedPermissions;

  /**
  * Status of the restoration process.
  */
  @Getter
  @Setter
  @JsonProperty("status")
  public String status;

  /**
  * If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.
  */
  @Getter
  @Setter
  @JsonProperty("update_timestamps")
  public Boolean updateTimestamps;

  /**
  * Error messages received while restoring files and/or directories. Only present if there were errors.
  */
  @Getter
  @Setter
  @JsonProperty("error_messages")
  public String[] errorMessages;

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    Restore.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<Restore> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<Restore> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<Restore> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/restores", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Restore>> typeReference = new TypeReference<List<Restore>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<Restore> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<Restore> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   earliest_date (required) - string - Restore all files deleted after this date/time. Don't set this earlier than you need. Can not be greater than 365 days prior to the restore request.
  *   prefix - string - Prefix of the files/folders to restore. To restore a folder, add a trailing slash to the folder name. Do not use a leading slash. To restore all deleted items, specify an empty string (`''`) in the prefix field or omit the field from the request.
  *   restore_deleted_permissions - boolean - If true, we will also restore any Permissions that match the same path prefix from the same dates.
  *   restore_in_place - boolean - If true, we will restore the files in place (into their original paths). If false, we will create a new restoration folder in the root and restore files there.
  *   update_timestamps - boolean - If true, we will update the last modified timestamp of restored files to today's date. If false, we might trigger File Expiration to delete the file again.
  */
  public static Restore create() throws RuntimeException {
    return create(null, null);
  }

  public static Restore create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static Restore create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("earliest_date") || parameters.get("earliest_date") == null) {
      throw new NullPointerException("Parameter missing: earliest_date parameters[\"earliest_date\"]");
    }

    if (parameters.containsKey("earliest_date") && !(parameters.get("earliest_date") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: earliest_date must be of type String parameters[\"earliest_date\"]");
    }
    if (parameters.containsKey("prefix") && !(parameters.get("prefix") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: prefix must be of type String parameters[\"prefix\"]");
    }
    if (parameters.containsKey("restore_deleted_permissions") && !(parameters.get("restore_deleted_permissions") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restore_deleted_permissions must be of type Boolean parameters[\"restore_deleted_permissions\"]");
    }
    if (parameters.containsKey("restore_in_place") && !(parameters.get("restore_in_place") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: restore_in_place must be of type Boolean parameters[\"restore_in_place\"]");
    }
    if (parameters.containsKey("update_timestamps") && !(parameters.get("update_timestamps") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: update_timestamps must be of type Boolean parameters[\"update_timestamps\"]");
    }


    String url = String.format("%s%s/restores", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Restore> typeReference = new TypeReference<Restore>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
