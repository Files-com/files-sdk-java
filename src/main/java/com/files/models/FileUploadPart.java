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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadPart implements ModelInterface {
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

  public File putInputStream(InputStream inputStream, Date date) throws IOException, InterruptedException {
    return putInputStream(inputStream, date, 6);
  }

  public File putInputStream(InputStream inputStream, Date date, int threadCount) throws IOException, InterruptedException {
    final RequestMethods requestMethod = httpMethod == "post" ? RequestMethods.POST : RequestMethods.PUT;
    final boolean isParallel = this.parallelParts && threadCount > 1;
    final java.util.concurrent.ExecutorService executor = isParallel ? java.util.concurrent.Executors.newFixedThreadPool(threadCount) : null;
    final java.util.concurrent.Semaphore semaphore = isParallel ? new java.util.concurrent.Semaphore(threadCount * 2) : null;
    final List<java.util.concurrent.Future<Void>> concurrentUploads = new java.util.ArrayList<>();
    BufferedInputStream bufferedInputStream = null;
    FileUploadPart part = this;

    try {
      bufferedInputStream = new BufferedInputStream(inputStream);

      while (true) {
        final byte[] buffer = new byte[part.partsize.intValue()];
        final int bytesRead = bufferedInputStream.read(buffer);
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
      if (bufferedInputStream != null) {
        bufferedInputStream.close();
      }
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
  @JsonProperty("send")
  public Map<String, String> send;

  public Map<String, String> getSend() {
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
  public Map<String, String> headers;

  public Map<String, String> getHeaders() {
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
  public Map<String, String> parameters;

  public Map<String, String> getParameters() {
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


}
