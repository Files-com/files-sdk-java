package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public class UsageSnapshot {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public UsageSnapshot() {
    this(null, null);
  }

  public UsageSnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public UsageSnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Site usage ID
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Site usage report start date/time
  */
  @Getter
  @JsonProperty("start_at")
  private Date startAt;

  /**
  * Site usage report end date/time
  */
  @Getter
  @JsonProperty("end_at")
  private Date endAt;

  /**
  * Site usage report created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * Current site usage as of report
  */
  @Getter
  @JsonProperty("current_storage")
  private Double currentStorage;

  /**
  * Site usage report highest usage in time period
  */
  @Getter
  @JsonProperty("high_water_storage")
  private Double highWaterStorage;

  /**
  * Number of downloads in report time period
  */
  @Getter
  @JsonProperty("total_downloads")
  private Long totalDownloads;

  /**
  * Number of uploads in time period
  */
  @Getter
  @JsonProperty("total_uploads")
  private Long totalUploads;

  /**
  * The last time this site usage report was updated
  */
  @Getter
  @JsonProperty("updated_at")
  private Date updatedAt;

  /**
  * A map of root folders to their total usage
  */
  @Getter
  @JsonProperty("usage_by_top_level_dir")
  private Map<String, String> usageByTopLevelDir;

  /**
  * Usage for root folder
  */
  @Getter
  @JsonProperty("root_storage")
  private Double rootStorage;

  /**
  * Usage for files that are deleted but uploaded within last 30 days
  */
  @Getter
  @JsonProperty("deleted_files_counted_in_minimum")
  private Double deletedFilesCountedInMinimum;

  /**
  * Usage for files that are deleted but retained as backups
  */
  @Getter
  @JsonProperty("deleted_files_storage")
  private Double deletedFilesStorage;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<UsageSnapshot> list() throws IOException{
    return list(null,null);
  }
  public static List<UsageSnapshot> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<UsageSnapshot> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    String url = String.format("%s%s/usage_snapshots", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<UsageSnapshot>> typeReference = new TypeReference<List<UsageSnapshot>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<UsageSnapshot> all() throws IOException {
    return all(null, null);
  }

  public static List<UsageSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


