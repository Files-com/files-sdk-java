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
public class UsageSnapshot implements ModelInterface {
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

  // One mapper for every UsageSnapshot, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code UsageSnapshot} with no attributes set.
   */
  public UsageSnapshot() {
    this(null, null);
  }

  /**
   * Creates a {@code UsageSnapshot} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public UsageSnapshot(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code UsageSnapshot} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public UsageSnapshot(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Usage snapshot ID
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
   * Usage snapshot start date/time
   */
  @JsonProperty("start_at")
  public Date startAt;

  /**
   * Gets {@link #startAt}.
   *
   * @return the value of {@link #startAt}
   */
  public Date getStartAt() {
    return startAt;
  }

  /**
   * Usage snapshot end date/time
   */
  @JsonProperty("end_at")
  public Date endAt;

  /**
   * Gets {@link #endAt}.
   *
   * @return the value of {@link #endAt}
   */
  public Date getEndAt() {
    return endAt;
  }

  /**
   * Highest user count number in time period
   */
  @JsonProperty("high_water_user_count")
  public Long highWaterUserCount;

  /**
   * Gets {@link #highWaterUserCount}.
   *
   * @return the value of {@link #highWaterUserCount}
   */
  public Long getHighWaterUserCount() {
    return highWaterUserCount;
  }

  /**
   * Total Transformation and AI credits used in this usage period. Updated daily. Does not include credits consumed today.
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
   * Current total Storage Usage GB as of end date (not necessarily high water mark, which is used for billing)
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
   * Highest Storage Usage GB recorded in time period (used for billing)
   */
  @JsonProperty("high_water_storage")
  public Long highWaterStorage;

  /**
   * Gets {@link #highWaterStorage}.
   *
   * @return the value of {@link #highWaterStorage}
   */
  public Long getHighWaterStorage() {
    return highWaterStorage;
  }

  /**
   * Storage Usage for root folder as of end date (not necessarily high water mark, which is used for billing)
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
   * Storage Usage for files that are deleted but uploaded within last 30 days as of end date (not necessarily high water mark, which is used for billing)
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
   * Storage Usage for files that are deleted but retained as backups as of end date (not necessarily high water mark, which is used for billing)
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
   * Storage + Transfer Usage - Total Billable amount
   */
  @JsonProperty("total_billable_usage")
  public Long totalBillableUsage;

  /**
   * Gets {@link #totalBillableUsage}.
   *
   * @return the value of {@link #totalBillableUsage}
   */
  public Long getTotalBillableUsage() {
    return totalBillableUsage;
  }

  /**
   * Transfer usage for period - Total Billable amount
   */
  @JsonProperty("total_billable_transfer_usage")
  public Long totalBillableTransferUsage;

  /**
   * Gets {@link #totalBillableTransferUsage}.
   *
   * @return the value of {@link #totalBillableTransferUsage}
   */
  public Long getTotalBillableTransferUsage() {
    return totalBillableTransferUsage;
  }

  /**
   * Transfer Usage for period - Outbound GB from Files Native Storage
   */
  @JsonProperty("bytes_sent")
  public Long bytesSent;

  /**
   * Gets {@link #bytesSent}.
   *
   * @return the value of {@link #bytesSent}
   */
  public Long getBytesSent() {
    return bytesSent;
  }

  /**
   * Transfer Usage for period - Inbound GB to Remote Servers (Sync/Mount)
   */
  @JsonProperty("sync_bytes_received")
  public Long syncBytesReceived;

  /**
   * Gets {@link #syncBytesReceived}.
   *
   * @return the value of {@link #syncBytesReceived}
   */
  public Long getSyncBytesReceived() {
    return syncBytesReceived;
  }

  /**
   * Transfer Usage for period - Outbound GB from Remote Servers (Sync/Mount)
   */
  @JsonProperty("sync_bytes_sent")
  public Long syncBytesSent;

  /**
   * Gets {@link #syncBytesSent}.
   *
   * @return the value of {@link #syncBytesSent}
   */
  public Long getSyncBytesSent() {
    return syncBytesSent;
  }

  /**
   * Storage Usage - map of root folders to their usage as of end date (not necessarily high water mark, which is used for billing)
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
   * List Usage Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageSnapshot> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Usage Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageSnapshot> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Usage Snapshots
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UsageSnapshot} records, in a {@code ListIterator}
   */
  public static ListIterator<UsageSnapshot> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().usageSnapshots().list(parameters, options);
  }

  /**
   * List Usage Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageSnapshot> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Usage Snapshots
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UsageSnapshot> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
