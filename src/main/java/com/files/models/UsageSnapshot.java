package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class UsageSnapshot {
  private HashMap<String, Object> attributes;
  private HashMap<String, Object> options;

  public UsageSnapshot() {
    this(null, null);
  }

  public UsageSnapshot(HashMap<String, Object> attributes) {
    this(attributes, null);
  }

  public UsageSnapshot(HashMap<String, Object> attributes, HashMap<String, Object> options) {
    this.attributes = attributes;
    this.options = options;
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(attributes));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Site usage ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Site usage report start date/time
  */
  @Getter
  @JsonProperty("start_at")
  public Date startAt;

  /**
  * Site usage report end date/time
  */
  @Getter
  @JsonProperty("end_at")
  public Date endAt;

  /**
  * Site usage report created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Current site usage as of report
  */
  @Getter
  @JsonProperty("current_storage")
  public Double currentStorage;

  /**
  * Site usage report highest usage in time period
  */
  @Getter
  @JsonProperty("high_water_storage")
  public Double highWaterStorage;

  /**
  * Number of downloads in report time period
  */
  @Getter
  @JsonProperty("total_downloads")
  public Long totalDownloads;

  /**
  * Number of uploads in time period
  */
  @Getter
  @JsonProperty("total_uploads")
  public Long totalUploads;

  /**
  * The last time this site usage report was updated
  */
  @Getter
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
  * A map of root folders to their total usage
  */
  @Getter
  @JsonProperty("usage_by_top_level_dir")
  public Object usageByTopLevelDir;

  /**
  * Usage for root folder
  */
  @Getter
  @JsonProperty("root_storage")
  public Double rootStorage;

  /**
  * Usage for files that are deleted but uploaded within last 30 days
  */
  @Getter
  @JsonProperty("deleted_files_counted_in_minimum")
  public Double deletedFilesCountedInMinimum;

  /**
  * Usage for files that are deleted but retained as backups
  */
  @Getter
  @JsonProperty("deleted_files_storage")
  public Double deletedFilesStorage;



  /**
  * Parameters:
  *   page - int64 - Current page number.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   action - string - Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
  */
  public static List<UsageSnapshot> list() throws IOException{
    return list(null,null);
  }
  public static List<UsageSnapshot> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  // TODO: Use types for path_and_primary_params
  public static List<UsageSnapshot> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("page") && !(parameters.get("page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: page must be of type Long parameters[\"page\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }

    if (parameters.containsKey("action") && !(parameters.get("action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: action must be of type String parameters[\"action\"]");
    }

    String url = String.format("%s%s/usage_snapshots", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<List<UsageSnapshot>> typeReference = new TypeReference<List<UsageSnapshot>>() {};
    return FilesClient.request(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<UsageSnapshot> all() throws IOException {
    return all(null, null);
  }

  public static List<UsageSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

}


