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
public class PublicKey implements ModelInterface {
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


  public PublicKey() {
    this(null, null);
  }

  public PublicKey(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public PublicKey(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Public key ID
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
  * Public key title
  */
  @JsonProperty("title")
  public String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
  * Public key created at date/time
  */
  @JsonProperty("created_at")
  public Date createdAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  /**
  * Public key fingerprint (MD5)
  */
  @JsonProperty("fingerprint")
  public String fingerprint;

  public String getFingerprint() {
    return fingerprint;
  }

  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  /**
  * Public key fingerprint (SHA256)
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
  * Key's most recent login time via SFTP
  */
  @JsonProperty("last_login_at")
  public Date lastLoginAt;

  public Date getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(Date lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  /**
  * Username of the user this public key is associated with
  */
  @JsonProperty("username")
  public String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
  * User ID this public key is associated with
  */
  @JsonProperty("user_id")
  public Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
  * Actual contents of SSH key.
  */
  @JsonProperty("public_key")
  public String publicKey;

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  /**
  * Parameters:
  *   title (required) - string - Internal reference for key.
  */
  public PublicKey update(HashMap<String, Object> parameters) throws IOException {
    return PublicKey.update(this.id, parameters, this.options);
  }

  /**
  */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    PublicKey.delete(this.id, parameters, this.options);
  }

  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    PublicKey.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `created_at`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `created_at`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal the supplied value. Valid fields are `created_at`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `created_at`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal the supplied value. Valid fields are `created_at`.
  */
  public static ListIterator<PublicKey> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<PublicKey> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<PublicKey> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
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


    String url = String.format("%s%s/public_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<PublicKey>> typeReference = new TypeReference<List<PublicKey>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<PublicKey> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<PublicKey> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Public Key ID.
  */
  public static PublicKey find() throws RuntimeException {
    return find(null, null, null);
  }

  public static PublicKey find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public static PublicKey find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public static PublicKey find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/public_keys/%s", urlParts);

    TypeReference<PublicKey> typeReference = new TypeReference<PublicKey>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static PublicKey get() throws RuntimeException {
    return get(null, null, null);
  }

  public static PublicKey get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   user_id - int64 - User ID.  Provide a value of `0` to operate the current session's user.
  *   title (required) - string - Internal reference for key.
  *   public_key (required) - string - Actual contents of SSH key.
  */
  public static PublicKey create() throws RuntimeException {
    return create(null, null);
  }

  public static PublicKey create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static PublicKey create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("title") || parameters.get("title") == null) {
      throw new NullPointerException("Parameter missing: title parameters[\"title\"]");
    }
    if (!parameters.containsKey("public_key") || parameters.get("public_key") == null) {
      throw new NullPointerException("Parameter missing: public_key parameters[\"public_key\"]");
    }

    if (parameters.containsKey("user_id") && !(parameters.get("user_id") instanceof Long || parameters.get("user_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: user_id must be of type Long or Integer parameters[\"user_id\"]");
    }
    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }
    if (parameters.containsKey("public_key") && !(parameters.get("public_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_key must be of type String parameters[\"public_key\"]");
    }


    String url = String.format("%s%s/public_keys", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<PublicKey> typeReference = new TypeReference<PublicKey>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   title (required) - string - Internal reference for key.
  */
  public static PublicKey update() throws RuntimeException {
    return update(null, null, null);
  }

  public static PublicKey update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public static PublicKey update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public static PublicKey update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }


    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }
    if (!parameters.containsKey("title") || parameters.get("title") == null) {
      throw new NullPointerException("Parameter missing: title parameters[\"title\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }
    if (parameters.containsKey("title") && !(parameters.get("title") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: title must be of type String parameters[\"title\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/public_keys/%s", urlParts);

    TypeReference<PublicKey> typeReference = new TypeReference<PublicKey>() {};
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


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex) {
        // NOOP
      }
    }

    String url = String.format("%s%s/public_keys/%s", urlParts);

    FilesClient.apiRequest(url, RequestMethods.DELETE, parameters, options);
  }

  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
