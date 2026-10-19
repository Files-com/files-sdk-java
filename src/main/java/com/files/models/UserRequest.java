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
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest implements ModelInterface {
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

  // One mapper for every UserRequest, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code UserRequest} with no attributes set.
   */
  public UserRequest() {
    this(null, null);
  }

  /**
   * Creates a {@code UserRequest} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public UserRequest(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code UserRequest} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public UserRequest(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * ID
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
   * Sets {@link #id}.
   *
   * @param id the new value
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * User's full name
   */
  @JsonProperty("name")
  public String name;

  /**
   * Gets {@link #name}.
   *
   * @return the value of {@link #name}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets {@link #name}.
   *
   * @param name the new value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * User email address
   */
  @JsonProperty("email")
  public String email;

  /**
   * Gets {@link #email}.
   *
   * @return the value of {@link #email}
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets {@link #email}.
   *
   * @param email the new value
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Details of the user's request
   */
  @JsonProperty("details")
  public String details;

  /**
   * Gets {@link #details}.
   *
   * @return the value of {@link #details}
   */
  public String getDetails() {
    return details;
  }

  /**
   * Sets {@link #details}.
   *
   * @param details the new value
   */
  public void setDetails(String details) {
    this.details = details;
  }

  /**
   * User's company name
   */
  @JsonProperty("company")
  public String company;

  /**
   * Gets {@link #company}.
   *
   * @return the value of {@link #company}
   */
  public String getCompany() {
    return company;
  }

  /**
   * Sets {@link #company}.
   *
   * @param company the new value
   */
  public void setCompany(String company) {
    this.company = company;
  }

  /**
   * Delete User Request
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().userRequests().delete(this.id, parameters, this.options);
  }

  /**
   * Delete User Request
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserRequest> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserRequest> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List User Requests
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserRequest} records, in a {@code ListIterator}
   */
  public static ListIterator<UserRequest> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userRequests().list(parameters, options);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserRequest> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List User Requests
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<UserRequest> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserRequest find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserRequest find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserRequest find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show User Request
   *
   * @param id User Request ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserRequest}
   */
  public static UserRequest find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userRequests().find(id, parameters, options);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserRequest get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show User Request
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static UserRequest get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create User Request
   *
   * @see #create(HashMap, HashMap)
   */
  public static UserRequest create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create User Request
   *
   * @see #create(HashMap, HashMap)
   */
  public static UserRequest create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  /**
   * Create User Request
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Name of user requested</li>
   * <li>{@code email} (String): Required. Email of user requested</li>
   * <li>{@code details} (String): Required. Details of the user request</li>
   * <li>{@code company} (String): Company of the user requested</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code UserRequest}
   */
  public static UserRequest create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().userRequests().create(parameters, options);
  }


  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete User Request
   *
   * @param id User Request ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().userRequests().delete(id, parameters, options);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete User Request
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
