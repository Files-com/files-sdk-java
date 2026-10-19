package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsageDailySnapshot implements ModelInterface {
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

  // One mapper for every UsageDailySnapshot, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code UsageDailySnapshot} with no attributes set.
   */
  public UsageDailySnapshot() {
    this(null, null);
  }

  /**
   * Creates a {@code UsageDailySnapshot} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public UsageDailySnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code UsageDailySnapshot} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public UsageDailySnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * ID of the usage record
   */
  @JsonProperty("id")
  public Long id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public Long getId() {
    return id;
  }

  /**
   * The date of this usage record
   */
  @JsonProperty("date")
  public Date date;

  /**
   * Gets {@link #date}.
   *
   * @return the value of {@link #date}
   */
  public Date getDate() {
    return date;
  }

  /**
   * True if the API usage fields `read_api_usage` and `write_api_usage` can be relied upon.  If this is false, we suggest hiding that value from any UI.
   */
  @JsonProperty("api_usage_available")
  public Boolean apiUsageAvailable;

  /**
   * Gets {@link #apiUsageAvailable}.
   *
   * @return the value of {@link #apiUsageAvailable}
   */
  public Boolean getApiUsageAvailable() {
    return apiUsageAvailable;
  }

  /**
   * Read API Calls used on this day. Note: only updated for days before the current day.
   */
  @JsonProperty("read_api_usage")
  public Long readApiUsage;

  /**
   * Gets {@link #readApiUsage}.
   *
   * @return the value of {@link #readApiUsage}
   */
  public Long getReadApiUsage() {
    return readApiUsage;
  }

  /**
   * Write API Calls used on this day. Note: only updated for days before the current day.
   */
  @JsonProperty("write_api_usage")
  public Long writeApiUsage;

  /**
   * Gets {@link #writeApiUsage}.
   *
   * @return the value of {@link #writeApiUsage}
   */
  public Long getWriteApiUsage() {
    return writeApiUsage;
  }

  /**
   * Transformation and AI credits used on this day. Note: only updated for days before the current day.
   */
  @JsonProperty("transformation_credits_usage")
  public Long transformationCreditsUsage;

  /**
   * Gets {@link #transformationCreditsUsage}.
   *
   * @return the value of {@link #transformationCreditsUsage}
   */
  public Long getTransformationCreditsUsage() {
    return transformationCreditsUsage;
  }

  /**
   * Number of billable users as of this day.
   */
  @JsonProperty("user_count")
  public Long userCount;

  /**
   * Gets {@link #userCount}.
   *
   * @return the value of {@link #userCount}
   */
  public Long getUserCount() {
    return userCount;
  }

  /**
   * GB of Files Native Storage used on this day.
   */
  @JsonProperty("current_storage")
  public Long currentStorage;

  /**
   * Gets {@link #currentStorage}.
   *
   * @return the value of {@link #currentStorage}
   */
  public Long getCurrentStorage() {
    return currentStorage;
  }

  /**
   * GB of Files Native Storage used on this day for files that have been deleted and are stored as backups.
   */
  @JsonProperty("deleted_files_storage")
  public Long deletedFilesStorage;

  /**
   * Gets {@link #deletedFilesStorage}.
   *
   * @return the value of {@link #deletedFilesStorage}
   */
  public Long getDeletedFilesStorage() {
    return deletedFilesStorage;
  }

  /**
   * GB of Files Native Storage used on this day for files that have been permanently deleted but were uploaded less than 30 days ago, and are still billable.
   */
  @JsonProperty("deleted_files_counted_in_minimum")
  public Long deletedFilesCountedInMinimum;

  /**
   * Gets {@link #deletedFilesCountedInMinimum}.
   *
   * @return the value of {@link #deletedFilesCountedInMinimum}
   */
  public Long getDeletedFilesCountedInMinimum() {
    return deletedFilesCountedInMinimum;
  }

  /**
   * GB of Files Native Storage used for the root folder.  Included here because this value will not be part of `usage_by_top_level_dir`
   */
  @JsonProperty("root_storage")
  public Long rootStorage;

  /**
   * Gets {@link #rootStorage}.
   *
   * @return the value of {@link #rootStorage}
   */
  public Long getRootStorage() {
    return rootStorage;
  }

  /**
   * Usage broken down by each top-level folder
   */
  @JsonProperty("usage_by_top_level_dir")
  public UsageByTopLevelDir[] usageByTopLevelDir;

  /**
   * Gets {@link #usageByTopLevelDir}.
   *
   * @return the value of {@link #usageByTopLevelDir}
   */
  public UsageByTopLevelDir[] getUsageByTopLevelDir() {
    return usageByTopLevelDir;
  }


  /**
   * List Usage Daily Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageDailySnapshot> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Usage Daily Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageDailySnapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Usage Daily Snapshots
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `date`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `date` and `usage_snapshot_id`. Valid field combinations are `[ usage_snapshot_id, date ]`.</li>
   * <li>{@code filter_gt} (Object): If set, return records where the specified field is greater than the supplied value. Valid fields are `date`.</li>
   * <li>{@code filter_gteq} (Object): If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `date`.</li>
   * <li>{@code filter_lt} (Object): If set, return records where the specified field is less than the supplied value. Valid fields are `date`.</li>
   * <li>{@code filter_lteq} (Object): If set, return records where the specified field is less than or equal the supplied value. Valid fields are `date`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UsageDailySnapshot} records, in a {@code ListIterator}
   */
  public static ListIterator<UsageDailySnapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().usageDailySnapshots().list(parameters, options);
  }

  /**
   * List Usage Daily Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageDailySnapshot> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Usage Daily Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageDailySnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
