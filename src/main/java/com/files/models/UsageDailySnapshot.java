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
public class UsageDailySnapshot implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public UsageDailySnapshot() {
    this(null, null);
  }

  public UsageDailySnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public UsageDailySnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * ID of the usage record
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * The date of this usage record
  */
  @Getter
  @JsonProperty("date")
  public Date date;

  /**
  * True if the API usage fields `read_api_usage` and `write_api_usage` can be relied upon.  If this is false, we suggest hiding that value from any UI.
  */
  @Getter
  @JsonProperty("api_usage_available")
  public Boolean apiUsageAvailable;

  /**
  * Read API Calls used on this day. Note: only updated for days before the current day.
  */
  @Getter
  @JsonProperty("read_api_usage")
  public Long readApiUsage;

  /**
  * Write API Calls used on this day. Note: only updated for days before the current day.
  */
  @Getter
  @JsonProperty("write_api_usage")
  public Long writeApiUsage;

  /**
  * Number of billable users as of this day.
  */
  @Getter
  @JsonProperty("user_count")
  public Long userCount;

  /**
  * GB of Files Native Storage used on this day.
  */
  @Getter
  @JsonProperty("current_storage")
  public Long currentStorage;

  /**
  * GB of Files Native Storage used on this day for files that have been deleted and are stored as backups.
  */
  @Getter
  @JsonProperty("deleted_files_storage")
  public Long deletedFilesStorage;

  /**
  * GB of Files Native Storage used on this day for files that have been permanently deleted but were uploaded less than 30 days ago, and are still billable.
  */
  @Getter
  @JsonProperty("deleted_files_counted_in_minimum")
  public Long deletedFilesCountedInMinimum;

  /**
  * GB of Files Native Storage used for the root folder.  Included here because this value will not be part of `usage_by_top_level_dir`
  */
  @Getter
  @JsonProperty("root_storage")
  public Long rootStorage;

  /**
  * Usage broken down by each top-level folder
  */
  @Getter
  @JsonProperty("usage_by_top_level_dir")
  public Map<String, String> usageByTopLevelDir;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `date` and `usage_snapshot_id`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ date, usage_snapshot_id ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `date`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `date`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `date`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `date`.
  */
  public static ListIterator<UsageDailySnapshot> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<UsageDailySnapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<UsageDailySnapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }


    String url = String.format("%s%s/usage_daily_snapshots", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<UsageDailySnapshot>> typeReference = new TypeReference<List<UsageDailySnapshot>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<UsageDailySnapshot> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<UsageDailySnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
