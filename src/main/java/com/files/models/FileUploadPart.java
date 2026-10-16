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
import com.files.net.ClientRuntime;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadPart implements ModelInterface {
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

  // One mapper for every FileUploadPart, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();

  /**
   * Sends the rest of this upload from the stream, until it ends, and completes it: this part and
   * each part after it hold the bytes Files.com plans for them. The stream is read, never closed.
   * See {@link com.files.net.Transfers#continueUpload(FileUploadPart, InputStream, long, com.files.net.UploadOptions)}.
   *
   * @param date the modification time to record for the file, or null to let Files.com choose
   */
  public File putInputStream(InputStream inputStream, Date date) throws IOException, InterruptedException {
    return putInputStream(inputStream, date, 6);
  }

  /**
   * Like {@link #putInputStream(InputStream, Date)}, sending up to {@code threadCount} parts at the
   * same time when Files.com allows it.
   */
  public File putInputStream(InputStream inputStream, Date date, int threadCount) throws IOException, InterruptedException {
    return filesClient().transfers().continueUpload(this, inputStream, -1, uploadOptions(date, Math.max(1, Math.min(threadCount, 1024)))).getFile();
  }

  /**
   * Sends the stream's next {@code length} bytes as this upload's parts and completes it. The stream
   * is read, never closed.
   */
  public File putBufferedInputStream(BufferedInputStream inputStream, long length, Date date) throws IOException {
    return filesClient().transfers().continueUpload(this, inputStream, length, uploadOptions(date, com.files.net.UploadOptions.DEFAULT_MAX_CONCURRENCY)).getFile();
  }

  /**
   * Sends the local file as this upload's parts and completes it.
   */
  public File putLocalFile(String source) throws IOException {
    return filesClient().transfers().continueUpload(this, java.nio.file.Paths.get(source), uploadOptions(null, com.files.net.UploadOptions.DEFAULT_MAX_CONCURRENCY)).getFile();
  }

  private com.files.net.UploadOptions uploadOptions(Date date, int concurrency) {
    return com.files.net.UploadOptions.builder()
        .maxConcurrency(concurrency)
        .modifiedTime(date != null ? date.toInstant() : null)
        .requestOptions(this.options)
        .build();
  }

  public FileUploadPart() {
    this(null, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Content-Type and File to send
   */
  @JsonProperty("send")
  public Object send;

  public Object getSend() {
    return send;
  }

  /**
   * Type of upload
   */
  @JsonProperty("action")
  public String action;

  public String getAction() {
    return action;
  }

  /**
   * If `true`, this file exists and you may wish to ask the user for overwrite confirmation
   */
  @JsonProperty("ask_about_overwrites")
  public Boolean askAboutOverwrites;

  public Boolean getAskAboutOverwrites() {
    return askAboutOverwrites;
  }

  /**
   * Number of parts in the upload
   */
  @JsonProperty("available_parts")
  public Long availableParts;

  public Long getAvailableParts() {
    return availableParts;
  }

  /**
   * Date/time of when this Upload part expires and the URL cannot be used any more
   */
  @JsonProperty("expires")
  public String expires;

  public String getExpires() {
    return expires;
  }

  /**
   * Additional upload headers to provide as part of the upload
   */
  @JsonProperty("headers")
  public Object headers;

  public Object getHeaders() {
    return headers;
  }

  /**
   * HTTP Method to use for uploading the part, usually `PUT`
   */
  @JsonProperty("http_method")
  public String httpMethod;

  public String getHttpMethod() {
    return httpMethod;
  }

  /**
   * Size in bytes for this part
   */
  @JsonProperty("next_partsize")
  public Long nextPartsize;

  public Long getNextPartsize() {
    return nextPartsize;
  }

  /**
   * If `true`, multiple parts may be uploaded in parallel.  If `false`, be sure to only upload one part at a time, in order.
   */
  @JsonProperty("parallel_parts")
  public Boolean parallelParts;

  public Boolean getParallelParts() {
    return parallelParts;
  }

  /**
   * If `true`, parts may be retried. If `false`, a part cannot be retried and the upload should be restarted.
   */
  @JsonProperty("retry_parts")
  public Boolean retryParts;

  public Boolean getRetryParts() {
    return retryParts;
  }

  /**
   * Additional HTTP parameters to send with the upload
   */
  @JsonProperty("parameters")
  public Object parameters;

  public Object getParameters() {
    return parameters;
  }

  /**
   * Number of this upload part
   */
  @JsonProperty("part_number")
  public Long partNumber;

  public Long getPartNumber() {
    return partNumber;
  }

  /**
   * Size in bytes for the next upload part
   */
  @JsonProperty("partsize")
  public Long partsize;

  public Long getPartsize() {
    return partsize;
  }

  /**
   * New file path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
   */
  @JsonProperty("path")
  public String path;

  public String getPath() {
    return path;
  }

  /**
   * Reference name for this upload part
   */
  @JsonProperty("ref")
  public String ref;

  public String getRef() {
    return ref;
  }

  /**
   * URI to upload this part to
   */
  @JsonProperty("upload_uri")
  public String uploadUri;

  public String getUploadUri() {
    return uploadUri;
  }

  /**
   * Optional direct connection information for direct Agent transfer attempts
   */
  @JsonProperty("direct_connection_info")
  public DirectConnectionInfo directConnectionInfo;

  public DirectConnectionInfo getDirectConnectionInfo() {
    return directConnectionInfo;
  }


}
