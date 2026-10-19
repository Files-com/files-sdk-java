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
import com.files.util.ModelUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session implements ModelInterface {
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

  // One mapper for every Session, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Session} with no attributes set.
   */
  public Session() {
    this(null, null);
  }

  /**
   * Creates a {@code Session} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Session(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Session} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Session(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Session ID
   */
  @JsonProperty("id")
  public String id;

  /**
   * Gets {@link #id}.
   *
   * @return the value of {@link #id}
   */
  public String getId() {
    return id;
  }

  /**
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Session language
   */
  @JsonProperty("language")
  public String language;

  /**
   * Gets {@link #language}.
   *
   * @return the value of {@link #language}
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Sets {@link #language}.
   *
   * @param language the new value
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Is this session read only?
   */
  @JsonProperty("read_only")
  public Boolean readOnly;

  /**
   * Gets {@link #readOnly}.
   *
   * @return the value of {@link #readOnly}
   */
  public Boolean getReadOnly() {
    return readOnly;
  }

  /**
   * Sets {@link #readOnly}.
   *
   * @param readOnly the new value
   */
  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  /**
   * Are insecure SFTP ciphers allowed for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure SSH ciphers for this user.)
   */
  @JsonProperty("sftp_insecure_ciphers")
  public Boolean sftpInsecureCiphers;

  /**
   * Gets {@link #sftpInsecureCiphers}.
   *
   * @return the value of {@link #sftpInsecureCiphers}
   */
  public Boolean getSftpInsecureCiphers() {
    return sftpInsecureCiphers;
  }

  /**
   * Sets {@link #sftpInsecureCiphers}.
   *
   * @param sftpInsecureCiphers the new value
   */
  public void setSftpInsecureCiphers(Boolean sftpInsecureCiphers) {
    this.sftpInsecureCiphers = sftpInsecureCiphers;
  }

  /**
   * Username to sign in as
   */
  @JsonProperty("username")
  public String username;

  /**
   * Gets {@link #username}.
   *
   * @return the value of {@link #username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets {@link #username}.
   *
   * @param username the new value
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Password for sign in
   */
  @JsonProperty("password")
  public String password;

  /**
   * Gets {@link #password}.
   *
   * @return the value of {@link #password}
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets {@link #password}.
   *
   * @param password the new value
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * If this user has a 2FA device, provide its OTP or code here.
   */
  @JsonProperty("otp")
  public String otp;

  /**
   * Gets {@link #otp}.
   *
   * @return the value of {@link #otp}
   */
  public String getOtp() {
    return otp;
  }

  /**
   * Sets {@link #otp}.
   *
   * @param otp the new value
   */
  public void setOtp(String otp) {
    this.otp = otp;
  }

  /**
   * Identifier for a partially-completed login
   */
  @JsonProperty("partial_session_id")
  public String partialSessionId;

  /**
   * Gets {@link #partialSessionId}.
   *
   * @return the value of {@link #partialSessionId}
   */
  public String getPartialSessionId() {
    return partialSessionId;
  }

  /**
   * Sets {@link #partialSessionId}.
   *
   * @param partialSessionId the new value
   */
  public void setPartialSessionId(String partialSessionId) {
    this.partialSessionId = partialSessionId;
  }


  /**
   * Creates this Session from its current attributes.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    refreshFrom(filesClient().sessions().create(parameters, this.options));
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    return ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Session canonical) {
    this.id = canonical.id;
    this.language = canonical.language;
    this.readOnly = canonical.readOnly;
    this.sftpInsecureCiphers = canonical.sftpInsecureCiphers;
    this.username = canonical.username;
    this.password = canonical.password;
    this.otp = canonical.otp;
    this.partialSessionId = canonical.partialSessionId;
  }

  /**
   * Create user session (log in)
   *
   * @see #create(HashMap, HashMap)
   */
  public static Session create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create user session (log in)
   *
   * @see #create(HashMap, HashMap)
   */
  public static Session create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create user session (log in)
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code username} (String): Username to sign in as</li>
   * <li>{@code password} (String): Password for sign in</li>
   * <li>{@code otp} (String): If this user has a 2FA device, provide its OTP or code here.</li>
   * <li>{@code partial_session_id} (String): Identifier for a partially-completed login</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Session}
   */
  public static Session create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().sessions().create(parameters, options);
  }


  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null);
  }

  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters) throws RuntimeException {
    delete(parameters, null);
  }


  /**
   * Delete user session (log out)
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().sessions().delete(parameters, options);
  }

  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null);
  }

  /**
   * Delete user session (log out)
   *
   * @see #delete(HashMap, HashMap)
   */
  public static void destroy(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(parameters, options);
  }

}
