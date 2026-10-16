package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.As2Partner;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * As2Partner operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#as2Partners()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.As2Partner} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class As2PartnerOperations extends ResourceOperations {
  public As2PartnerOperations(FilesClient client) {
    super(client);
  }

  /**
   * Parameters:
   *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
   *   per_page - int64 - Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
   *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `as2_station_id` or `name`.
   *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `as2_station_id` and `workspace_id`. Valid field combinations are `[ workspace_id, as2_station_id ]`.
   */
  public ListIterator<As2Partner> list() throws RuntimeException {
    return list(null, null);
  }

  public ListIterator<As2Partner> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  public ListIterator<As2Partner> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Object parameters[\"sort_by\"]");
    }

    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Object parameters[\"filter\"]");
    }

    String apiPath = "/as2_partners";
    TypeReference<List<As2Partner>> typeReference = new TypeReference<List<As2Partner>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public ListIterator<As2Partner> all() throws RuntimeException {
    return all(null, null);
  }

  public ListIterator<As2Partner> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Parameters:
   *   id (required) - int64 - As2 Partner ID.
   */
  public As2Partner find() throws RuntimeException {
    return find(null, null, null);
  }

  public As2Partner find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  public As2Partner find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  public As2Partner find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/as2_partners/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  public As2Partner get() throws RuntimeException {
    return get(null, null, null);
  }

  public As2Partner get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Parameters:
   *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   *   http_auth_username - string - Username to send to server for HTTP Authentication.
   *   http_auth_password - string - Password to send to server for HTTP Authentication.
   *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   *   default_mime_type - string - Default mime type of the file attached to the encrypted message
   *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
   *   as2_station_id (required) - int64 - ID of the AS2 Station associated with this partner.
   *   name (required) - string - The partner's formal AS2 name.
   *   uri (required) - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
   *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
   */
  public As2Partner create() throws RuntimeException {
    return create(null, null);
  }

  public As2Partner create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  public As2Partner create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("as2_station_id") || parameters.get("as2_station_id") == null) {
      throw new NullPointerException("Parameter missing: as2_station_id parameters[\"as2_station_id\"]");
    }

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (!parameters.containsKey("uri") || parameters.get("uri") == null) {
      throw new NullPointerException("Parameter missing: uri parameters[\"uri\"]");
    }

    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }

    if (parameters.containsKey("http_auth_username") && !(parameters.get("http_auth_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_username must be of type String parameters[\"http_auth_username\"]");
    }

    if (parameters.containsKey("http_auth_password") && !(parameters.get("http_auth_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_password must be of type String parameters[\"http_auth_password\"]");
    }

    if (parameters.containsKey("mdn_validation_level") && !(parameters.get("mdn_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: mdn_validation_level must be of type String parameters[\"mdn_validation_level\"]");
    }

    if (parameters.containsKey("signature_validation_level") && !(parameters.get("signature_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: signature_validation_level must be of type String parameters[\"signature_validation_level\"]");
    }

    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }

    if (parameters.containsKey("default_mime_type") && !(parameters.get("default_mime_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: default_mime_type must be of type String parameters[\"default_mime_type\"]");
    }

    if (parameters.containsKey("additional_http_headers") && !(parameters.get("additional_http_headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: additional_http_headers must be of type Object parameters[\"additional_http_headers\"]");
    }

    if (parameters.containsKey("as2_station_id") && !(parameters.get("as2_station_id") instanceof Long || parameters.get("as2_station_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: as2_station_id must be of type Long or Integer parameters[\"as2_station_id\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("uri") && !(parameters.get("uri") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: uri must be of type String parameters[\"uri\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("pkcs12") && !(parameters.get("pkcs12") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12 must be of type String parameters[\"pkcs12\"]");
    }

    if (parameters.containsKey("pkcs12_password") && !(parameters.get("pkcs12_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12_password must be of type String parameters[\"pkcs12_password\"]");
    }

    String apiPath = "/as2_partners";
    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Parameters:
   *   enable_dedicated_ips - boolean - If `true`, we will use your site's dedicated IPs for all outbound connections to this AS2 Partner.
   *   http_auth_username - string - Username to send to server for HTTP Authentication.
   *   http_auth_password - string - Password to send to server for HTTP Authentication.
   *   mdn_validation_level - string - How should Files.com evaluate message transfer success based on a partner's MDN response?  This setting does not affect MDN storage; all MDNs received from a partner are always stored. `none`: MDN is stored for informational purposes only, a successful HTTPS transfer is a successful AS2 transfer. `weak`: Inspect the MDN for MIC and Disposition only. `normal`: `weak` plus validate MDN signature matches body, `strict`: `normal` but do not allow signatures from self-signed or incorrectly purposed certificates. `auto`: Automatically set the correct value for this setting based on next mdn received.
   *   signature_validation_level - string - Should Files.com require signatures on incoming AS2 messages?  `normal`: require that incoming messages are signed with a valid matching signature. `none`: Unsigned incoming messages are allowed. `auto`: A one-time starting state. The next message received is always accepted and switches this to `normal` if it carries a valid signature, or `none` if it does not.
   *   server_certificate - string - Should we require that the remote HTTP server have a valid SSL Certificate for HTTPS? (This only applies to Outgoing AS2 message from Files.com to a Partner.)
   *   default_mime_type - string - Default mime type of the file attached to the encrypted message
   *   additional_http_headers - object - Additional HTTP Headers for outgoing message sent to this partner.
   *   name - string - The partner's formal AS2 name.
   *   uri - string - Public URI where we will send the AS2 messages (via HTTP/HTTPS).
   *   public_certificate - string - Public certificate for AS2 Partner.  Note: This is the certificate for AS2 message security, not a certificate used for HTTPS authentication.
   *   pkcs12 - string - Base64-encoded PKCS#12 (.pfx or .p12) file containing the partner's public certificate. Provide this instead of public_certificate. Any private key in the file is discarded. A public-only file must contain exactly one certificate.
   *   pkcs12_password - string - Password for pkcs12. The file and password are used only for import; the extracted public certificate is stored as PEM.
   */
  public As2Partner update() throws RuntimeException {
    return update(null, null, null);
  }

  public As2Partner update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  public As2Partner update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  public As2Partner update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    if (parameters.containsKey("enable_dedicated_ips") && !(parameters.get("enable_dedicated_ips") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: enable_dedicated_ips must be of type Boolean parameters[\"enable_dedicated_ips\"]");
    }

    if (parameters.containsKey("http_auth_username") && !(parameters.get("http_auth_username") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_username must be of type String parameters[\"http_auth_username\"]");
    }

    if (parameters.containsKey("http_auth_password") && !(parameters.get("http_auth_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: http_auth_password must be of type String parameters[\"http_auth_password\"]");
    }

    if (parameters.containsKey("mdn_validation_level") && !(parameters.get("mdn_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: mdn_validation_level must be of type String parameters[\"mdn_validation_level\"]");
    }

    if (parameters.containsKey("signature_validation_level") && !(parameters.get("signature_validation_level") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: signature_validation_level must be of type String parameters[\"signature_validation_level\"]");
    }

    if (parameters.containsKey("server_certificate") && !(parameters.get("server_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: server_certificate must be of type String parameters[\"server_certificate\"]");
    }

    if (parameters.containsKey("default_mime_type") && !(parameters.get("default_mime_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: default_mime_type must be of type String parameters[\"default_mime_type\"]");
    }

    if (parameters.containsKey("additional_http_headers") && !(parameters.get("additional_http_headers") instanceof Object)) {
      throw new IllegalArgumentException("Bad parameter: additional_http_headers must be of type Object parameters[\"additional_http_headers\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("uri") && !(parameters.get("uri") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: uri must be of type String parameters[\"uri\"]");
    }

    if (parameters.containsKey("public_certificate") && !(parameters.get("public_certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: public_certificate must be of type String parameters[\"public_certificate\"]");
    }

    if (parameters.containsKey("pkcs12") && !(parameters.get("pkcs12") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12 must be of type String parameters[\"pkcs12\"]");
    }

    if (parameters.containsKey("pkcs12_password") && !(parameters.get("pkcs12_password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: pkcs12_password must be of type String parameters[\"pkcs12_password\"]");
    }

    String apiPath = String.format("/as2_partners/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<As2Partner> typeReference = new TypeReference<As2Partner>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  public void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = (Long) parameters.get("id");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }

    if (!(id instanceof Long || parameters.get("id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long or Integer parameters[\"id\"]");
    }

    String apiPath = String.format("/as2_partners/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
