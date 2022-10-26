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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class UsageSnapshot {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

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
  * Usage snapshot ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Usage snapshot start date/time
  */
  @Getter
  @JsonProperty("start_at")
  public Date startAt;

  /**
  * Usage snapshot end date/time
  */
  @Getter
  @JsonProperty("end_at")
  public Date endAt;

  /**
  * DEPRECATED: Usage snapshot created at date/time
  */
  @Getter
  @JsonProperty("created_at")
  public Date createdAt;

  /**
  * Highest user count number in time period
  */
  @Getter
  @JsonProperty("high_water_user_count")
  public Double highWaterUserCount;

  /**
  * Current total Storage Usage GB as of end date (not necessarily high water mark, which is used for billing)
  */
  @Getter
  @JsonProperty("current_storage")
  public Double currentStorage;

  /**
  * Highest Storage Usage GB recorded in time period (used for billing)
  */
  @Getter
  @JsonProperty("high_water_storage")
  public Double highWaterStorage;

  /**
  * DEPRECATED: Number of downloads in report time period
  */
  @Getter
  @JsonProperty("total_downloads")
  public Long totalDownloads;

  /**
  * DEPRECATED: Number of uploads in time period
  */
  @Getter
  @JsonProperty("total_uploads")
  public Long totalUploads;

  /**
  * DEPRECATED: The last time this site usage report was updated
  */
  @Getter
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
  * Storage Usage - map of root folders to their usage as of end date (not necessarily high water mark, which is used for billing)
  */
  @Getter
  @JsonProperty("usage_by_top_level_dir")
  public Map<String, String> usageByTopLevelDir;

  /**
  * Storage Usage for root folder as of end date (not necessarily high water mark, which is used for billing)
  */
  @Getter
  @JsonProperty("root_storage")
  public Double rootStorage;

  /**
  * Storage Usage for files that are deleted but uploaded within last 30 days as of end date (not necessarily high water mark, which is used for billing)
  */
  @Getter
  @JsonProperty("deleted_files_counted_in_minimum")
  public Double deletedFilesCountedInMinimum;

  /**
  * Storage Usage for files that are deleted but retained as backups as of end date (not necessarily high water mark, which is used for billing)
  */
  @Getter
  @JsonProperty("deleted_files_storage")
  public Double deletedFilesStorage;

  /**
  * Storage + Transfer Usage - Total Billable amount
  */
  @Getter
  @JsonProperty("total_billable_usage")
  public Double totalBillableUsage;

  /**
  * Transfer usage for period - Total Billable amount
  */
  @Getter
  @JsonProperty("total_billable_transfer_usage")
  public Double totalBillableTransferUsage;

  /**
  * Transfer Usage for period - Outbound GB from Files Native Storage
  */
  @Getter
  @JsonProperty("bytes_sent")
  public Double bytesSent;

  /**
  * Transfer Usage for period - Inbound GB to Remote Servers (Sync/Mount)
  */
  @Getter
  @JsonProperty("sync_bytes_received")
  public Double syncBytesReceived;

  /**
  * Transfer Usage for period - Outbound GB from Remote Servers (Sync/Mount)
  */
  @Getter
  @JsonProperty("sync_bytes_sent")
  public Double syncBytesSent;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  Send a cursor value to resume an existing list from the point at which you left off.  Get a cursor from an existing list via either the X-Files-Cursor-Next header or the X-Files-Cursor-Prev header.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static List<UsageSnapshot> list() throws IOException {
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


