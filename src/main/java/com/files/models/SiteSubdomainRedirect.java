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
import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteSubdomainRedirect implements ModelInterface {
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

  // One mapper for every SiteSubdomainRedirect, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code SiteSubdomainRedirect} with no attributes set.
   */
  public SiteSubdomainRedirect() {
    this(null, null);
  }

  /**
   * Creates a {@code SiteSubdomainRedirect} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public SiteSubdomainRedirect(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code SiteSubdomainRedirect} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public SiteSubdomainRedirect(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Site subdomain redirect ID.
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
   * Files.com subdomain that continues to route to the current site subdomain.
   */
  @JsonProperty("subdomain")
  public String subdomain;

  /**
   * Gets {@link #subdomain}.
   *
   * @return the value of {@link #subdomain}
   */
  public String getSubdomain() {
    return subdomain;
  }

  /**
   * When this redirect was created.
   */
  @JsonProperty("created_at")
  public Date createdAt;

  /**
   * Gets {@link #createdAt}.
   *
   * @return the value of {@link #createdAt}
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * When this redirect was last updated.
   */
  @JsonProperty("updated_at")
  public Date updatedAt;

  /**
   * Gets {@link #updatedAt}.
   *
   * @return the value of {@link #updatedAt}
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().siteSubdomainRedirects().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * List Site Subdomain Redirects
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiteSubdomainRedirect> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Site Subdomain Redirects
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiteSubdomainRedirect> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Site Subdomain Redirects
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `id`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiteSubdomainRedirect} records, in a {@code ListIterator}
   */
  public static ListIterator<SiteSubdomainRedirect> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siteSubdomainRedirects().list(parameters, options);
  }

  /**
   * List Site Subdomain Redirects
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiteSubdomainRedirect> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Site Subdomain Redirects
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<SiteSubdomainRedirect> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Site Subdomain Redirect
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiteSubdomainRedirect find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Site Subdomain Redirect
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiteSubdomainRedirect find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Site Subdomain Redirect
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiteSubdomainRedirect find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Site Subdomain Redirect
   *
   * @param id Site Subdomain Redirect ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code SiteSubdomainRedirect}
   */
  public static SiteSubdomainRedirect find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().siteSubdomainRedirects().find(id, parameters, options);
  }

  /**
   * Show Site Subdomain Redirect
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiteSubdomainRedirect get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Site Subdomain Redirect
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static SiteSubdomainRedirect get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @param id Site Subdomain Redirect ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().siteSubdomainRedirects().delete(id, parameters, options);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Site Subdomain Redirect
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
