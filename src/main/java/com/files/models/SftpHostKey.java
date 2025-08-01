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
import com.files.util.UrlUtils;
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
public class SftpHostKey implements ModelInterface {
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


  public SftpHostKey() {
    this(null, null);
  }

  public SftpHostKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public SftpHostKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * SFTP Host Key ID
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
  * The friendly name of this SFTP Host Key.
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
  * MD5 Fingerprint of the public key
  */
  @JsonProperty("fingerprint_md5")
  public String fingerprintMd5;

  public String getFingerprintMd5() {
    return fingerprintMd5;
  }

  public void setFingerprintMd5(String fingerprintMd5) {
    this.fingerprintMd5 = fingerprintMd5;
  }

  /**
  * SHA256 Fingerprint of the public key
  */
  @JsonProperty("fingerprint_sha256")
  public String fingerprintSha256;

  public String getFingerprintSha256() {
    return fingerprintSha256;
  }

  public void setFingerprintSha256(String fingerprintSha256) {
    this.fingerprintSha256 = fingerprintSha256;
  }

  /**
  * The private key data.
  */
  @JsonProperty("private_key")
  public String privateKey;

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
  * Parameters:
  *   name - string - The friendly name of this SFTP Host Key.
  *   private_key - string - The private key data.
  */
  public SftpHostKey update(HashMap<String, Object> parameters) throws IOException {
    return SftpHostKey.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    SftpHostKey.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    SftpHostKey.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  */
  public static ListIterator<SftpHostKey> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<SftpHostKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<SftpHostKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }


    String url = String.format("%s%s/sftp_host_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<SftpHostKey>> typeReference = new TypeReference<List<SftpHostKey>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<SftpHostKey> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<SftpHostKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Sftp Host Key ID.
  */
  public static SftpHostKey find() throws RuntimeException {
    return find(null, null, null);
  }

  public static SftpHostKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static SftpHostKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static SftpHostKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/sftp_host_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<SftpHostKey> typeReference = new TypeReference<SftpHostKey>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static SftpHostKey get() throws RuntimeException {
    return get(null, null, null);
  }

  public static SftpHostKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   name - string - The friendly name of this SFTP Host Key.
  *   private_key - string - The private key data.
  */
  public static SftpHostKey create() throws RuntimeException {
    return create(null, null);
  }

  public static SftpHostKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static SftpHostKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }


    String url = String.format("%s%s/sftp_host_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<SftpHostKey> typeReference = new TypeReference<SftpHostKey>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - The friendly name of this SFTP Host Key.
  *   private_key - string - The private key data.
  */
  public static SftpHostKey update() throws RuntimeException {
    return update(null, null, null);
  }

  public static SftpHostKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static SftpHostKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static SftpHostKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }



    String url = String.format("%s%s/sftp_host_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    TypeReference<SftpHostKey> typeReference = new TypeReference<SftpHostKey>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }



    String url = String.format("%s%s/sftp_host_keys/%s", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), UrlUtils.encodeUrlPath(String.valueOf(id)));

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
