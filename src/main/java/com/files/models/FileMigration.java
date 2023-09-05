package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileMigration {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();


  public FileMigration() {
    this(null, null);
  }

  public FileMigration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileMigration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * File migration ID
  */
  @Getter
  @JsonProperty("id")
  public Long id;

  /**
  * Source path This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @JsonProperty("path")
  public String path;

  /**
  * Destination path
  */
  @Getter
  @JsonProperty("dest_path")
  public String destPath;

  /**
  * Number of files processed
  */
  @Getter
  @JsonProperty("files_moved")
  public Long filesMoved;

  /**
  * Deprecated: used to return a count of the applicable files.  Currently returns 0 always.  On remote servers, it is not possible to reliably determine the number of affected files for every migration operation.
  */
  @Getter
  @JsonProperty("files_total")
  public Long filesTotal;

  /**
  * The type of operation
  */
  @Getter
  @JsonProperty("operation")
  public String operation;

  /**
  * Region
  */
  @Getter
  @JsonProperty("region")
  public String region;

  /**
  * Status
  */
  @Getter
  @JsonProperty("status")
  public String status;

  /**
  * Link to download the log file for this migration.
  */
  @Getter
  @JsonProperty("log_url")
  public String logUrl;



  /**
  * Parameters:
  *   id (required) - int64 - File Migration ID.
  */
  public static ListIterator<FileMigration> find() throws RuntimeException {
    return find(null, null, null);
  }

  public static ListIterator<FileMigration> find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static ListIterator<FileMigration> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static ListIterator<FileMigration> find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (!(id instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/file_migrations/%s", urlParts);

    TypeReference<List<FileMigration>> typeReference = new TypeReference<List<FileMigration>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<FileMigration> get() throws RuntimeException {
    return get(null, null, null);
  }

  public static ListIterator<FileMigration> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

}
