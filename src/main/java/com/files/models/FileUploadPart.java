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
public class FileUploadPart implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();

  public File putInputStream(InputStream inputStream, Date date) throws IOException, InterruptedException {
    return putInputStream(inputStream, date, 6);
  }

  public File putInputStream(InputStream inputStream, Date date, int threadCount) throws IOException, InterruptedException {
    final RequestMethods requestMethod = httpMethod == "post" ? RequestMethods.POST : RequestMethods.PUT;
    final boolean isParallel = this.parallelParts && threadCount > 1;
    final java.util.concurrent.ExecutorService executor = isParallel ? java.util.concurrent.Executors.newFixedThreadPool(threadCount) : null;
    final java.util.concurrent.Semaphore semaphore = isParallel ? new java.util.concurrent.Semaphore(threadCount * 2) : null;
    final List<java.util.concurrent.Future<Void>> concurrentUploads = new java.util.ArrayList<>();
    FileUploadPart part = this;

    try {
      while (true) {
        final byte[] buffer = new byte[part.partsize.intValue()];
        final int bytesRead = inputStream.read(buffer);
        if (bytesRead == -1) {
          break;
        }

        if (isParallel) {
          semaphore.acquire();

          final FileUploadPart currentPart = part;
          concurrentUploads.add(executor.submit(() -> {
            try {
              FilesClient.putBuffer(currentPart.uploadUri, requestMethod, currentPart.path, buffer, bytesRead);
            } finally {
              semaphore.release();
            }
            return null;
          }));
        } else {
          FilesClient.putBuffer(part.uploadUri, requestMethod, part.path, buffer, bytesRead);
        }

        if (bytesRead < part.partsize) {
          break;
        }

        final HashMap<String, Object> parameters = new HashMap<>(part.parameters);
        parameters.put("ref", part.ref);
        parameters.put("part", part.partNumber + 1);
        part = File.create(part.path, parameters, part.options);
      }

      for (java.util.concurrent.Future<Void> future : concurrentUploads) {
        try {
          future.get();
        } catch (java.util.concurrent.ExecutionException e) {
          throw new IOException(e.getCause());
        }
      }
    } finally {
      if (executor != null) {
        executor.shutdown();
      }
    }

    return part.completeUpload();
  }

  public File putBufferedInputStream(BufferedInputStream inputStream, long length, Date date) throws IOException {
    final RequestMethods requestMethod = httpMethod == "post" ? RequestMethods.POST : RequestMethods.PUT;
    FilesClient.putBufferedInputStream(this.uploadUri, requestMethod, this.path, inputStream, length);
    return completeUpload();
  }

  public File putLocalFile(String source) throws IOException {
    java.io.File file = new java.io.File(source);
    long fileLength = file.length();
    BufferedInputStream bufferedInputStream = null;
    try {
      bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
      return putBufferedInputStream(bufferedInputStream, fileLength, null);
    } finally {
      if (bufferedInputStream != null) {
        bufferedInputStream.close();
      }
    }
  }

  private File completeUpload() throws IOException {
    final HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("action", "end");
    parameters.put("ref", this.ref);
    return File.completeUpload(this.path, parameters, this.options);
  }

  public FileUploadPart() {
    this(null, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileUploadPart(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Content-Type and File to send
  */
  @Getter
  @JsonProperty("send")
  public Map<String, String> send;

  /**
  * Type of upload
  */
  @Getter
  @JsonProperty("action")
  public String action;

  /**
  * If `true`, this file exists and you may wish to ask the user for overwrite confirmation
  */
  @Getter
  @JsonProperty("ask_about_overwrites")
  public Boolean askAboutOverwrites;

  /**
  * Number of parts in the upload
  */
  @Getter
  @JsonProperty("available_parts")
  public Long availableParts;

  /**
  * Date/time of when this Upload part expires and the URL cannot be used any more
  */
  @Getter
  @JsonProperty("expires")
  public String expires;

  /**
  * Additional upload headers to provide as part of the upload
  */
  @Getter
  @JsonProperty("headers")
  public Map<String, String> headers;

  /**
  * HTTP Method to use for uploading the part, usually `PUT`
  */
  @Getter
  @JsonProperty("http_method")
  public String httpMethod;

  /**
  * Size in bytes for this part
  */
  @Getter
  @JsonProperty("next_partsize")
  public Long nextPartsize;

  /**
  * If `true`, multiple parts may be uploaded in parallel.  If `false`, be sure to only upload one part at a time, in order.
  */
  @Getter
  @JsonProperty("parallel_parts")
  public Boolean parallelParts;

  /**
  * If `true`, parts may be retried. If `false`, a part cannot be retried and the upload should be restarted.
  */
  @Getter
  @JsonProperty("retry_parts")
  public Boolean retryParts;

  /**
  * Additional HTTP parameters to send with the upload
  */
  @Getter
  @JsonProperty("parameters")
  public Map<String, String> parameters;

  /**
  * Number of this upload part
  */
  @Getter
  @JsonProperty("part_number")
  public Long partNumber;

  /**
  * Size in bytes for the next upload part
  */
  @Getter
  @JsonProperty("partsize")
  public Long partsize;

  /**
  * New file path. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Reference name for this upload part
  */
  @Getter
  @JsonProperty("ref")
  public String ref;

  /**
  * URI to upload this part to
  */
  @Getter
  @JsonProperty("upload_uri")
  public String uploadUri;



}
