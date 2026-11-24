package com.files.operations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.ResourceOperations;
import com.files.models.Certificate;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.util.HashMap;
import java.util.List;

/**
 * Certificate operations that run with one client: its API root, credentials, workspace
 * and connections. Get them with {@link FilesClient#certificates()}. The
 * objects and listings they return keep using that client and the request options they were
 * requested with.
 *
 * <p>Each method matches the static {@link com.files.models.Certificate} method of the same name, which
 * runs with {@link FilesClient#defaultClient()}. The maps you pass are copied, never changed.
 */
public final class CertificateOperations extends ResourceOperations {
  public CertificateOperations(FilesClient client) {
    super(client);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Certificate> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Certificate> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }

  /**
   * List Certificates
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Certificate} records, in a {@code ListIterator}
   */
  public ListIterator<Certificate> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }

    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }

    String apiPath = "/certificates";
    TypeReference<List<Certificate>> typeReference = new TypeReference<List<Certificate>>() {};
    return requestList(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Certificate> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public ListIterator<Certificate> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Certificate find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Certificate find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Certificate find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(null, parameters, options);
  }

  /**
   * Show Certificate
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Certificate}
   */
  public Certificate find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/certificates/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Certificate> typeReference = new TypeReference<Certificate>() {};
    return requestItem(RequestMethods.GET, apiPath, typeReference, parameters, options);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Certificate get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public Certificate get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create CSR or import existing SSL Certificate
   *
   * @see #create(HashMap, HashMap)
   */
  public Certificate create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create CSR or import existing SSL Certificate
   *
   * @see #create(HashMap, HashMap)
   */
  public Certificate create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }

  /**
   * Create CSR or import existing SSL Certificate
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Required. Internal name of the SSL certificate.</li>
   * <li>{@code certificate_domain} (String): Domain for certificate.</li>
   * <li>{@code certificate_extra_domains} (String[]): Additional domains for the certificate CSR.</li>
   * <li>{@code certificate_country} (String): Country.</li>
   * <li>{@code certificate_state_or_province} (String): State or province.</li>
   * <li>{@code certificate_city_or_locale} (String): City or locale.</li>
   * <li>{@code certificate_company_name} (String): Company name.</li>
   * <li>{@code csr_ou1} (String): Department name or organization unit 1</li>
   * <li>{@code csr_ou2} (String): Department name or organization unit 2</li>
   * <li>{@code csr_ou3} (String): Department name or organization unit 3</li>
   * <li>{@code certificate_email_address} (String): Email address for the certificate owner.</li>
   * <li>{@code key_type} (String): Any supported key type.  Defaults to `RSA-4096`.</li>
   * <li>{@code certificate} (String): The certificate.  PEM or PKCS7 formats accepted.</li>
   * <li>{@code private_key} (String): Certificate private key.</li>
   * <li>{@code password} (String): Certificate password.</li>
   * <li>{@code intermediates} (String): Intermediate certificates.  PEM or PKCS7 formats accepted.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Certificate}
   */
  public Certificate create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = copy(parameters);
    options = copy(options);

    if (!parameters.containsKey("name") || parameters.get("name") == null) {
      throw new NullPointerException("Parameter missing: name parameters[\"name\"]");
    }

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("certificate_domain") && !(parameters.get("certificate_domain") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate_domain must be of type String parameters[\"certificate_domain\"]");
    }

    if (parameters.containsKey("certificate_extra_domains") && !(parameters.get("certificate_extra_domains") instanceof String[])) {
      throw new IllegalArgumentException("Bad parameter: certificate_extra_domains must be of type String[] parameters[\"certificate_extra_domains\"]");
    }

    if (parameters.containsKey("certificate_country") && !(parameters.get("certificate_country") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate_country must be of type String parameters[\"certificate_country\"]");
    }

    if (parameters.containsKey("certificate_state_or_province") && !(parameters.get("certificate_state_or_province") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate_state_or_province must be of type String parameters[\"certificate_state_or_province\"]");
    }

    if (parameters.containsKey("certificate_city_or_locale") && !(parameters.get("certificate_city_or_locale") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate_city_or_locale must be of type String parameters[\"certificate_city_or_locale\"]");
    }

    if (parameters.containsKey("certificate_company_name") && !(parameters.get("certificate_company_name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate_company_name must be of type String parameters[\"certificate_company_name\"]");
    }

    if (parameters.containsKey("csr_ou1") && !(parameters.get("csr_ou1") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: csr_ou1 must be of type String parameters[\"csr_ou1\"]");
    }

    if (parameters.containsKey("csr_ou2") && !(parameters.get("csr_ou2") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: csr_ou2 must be of type String parameters[\"csr_ou2\"]");
    }

    if (parameters.containsKey("csr_ou3") && !(parameters.get("csr_ou3") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: csr_ou3 must be of type String parameters[\"csr_ou3\"]");
    }

    if (parameters.containsKey("certificate_email_address") && !(parameters.get("certificate_email_address") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate_email_address must be of type String parameters[\"certificate_email_address\"]");
    }

    if (parameters.containsKey("key_type") && !(parameters.get("key_type") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: key_type must be of type String parameters[\"key_type\"]");
    }

    if (parameters.containsKey("certificate") && !(parameters.get("certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate must be of type String parameters[\"certificate\"]");
    }

    if (parameters.containsKey("private_key") && !(parameters.get("private_key") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: private_key must be of type String parameters[\"private_key\"]");
    }

    if (parameters.containsKey("password") && !(parameters.get("password") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: password must be of type String parameters[\"password\"]");
    }

    if (parameters.containsKey("intermediates") && !(parameters.get("intermediates") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: intermediates must be of type String parameters[\"intermediates\"]");
    }

    String apiPath = "/certificates";
    TypeReference<Certificate> typeReference = new TypeReference<Certificate>() {};
    return requestItem(RequestMethods.POST, apiPath, typeReference, parameters, options);
  }

  /**
   * Activate SSL Certificate
   *
   * @see #activate(Long, HashMap, HashMap)
   */
  public void activate() throws RuntimeException {
    activate(null, null, null);
  }

  /**
   * Activate SSL Certificate
   *
   * @see #activate(Long, HashMap, HashMap)
   */
  public void activate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    activate(id, parameters, null);
  }

  /**
   * Activate SSL Certificate
   *
   * @see #activate(Long, HashMap, HashMap)
   */
  public void activate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    activate(null, parameters, options);
  }

  /**
   * Activate SSL Certificate
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code replace_cert} (String): Leave blank or set to `any` to replace any currently active certificate, if applicable.  Set to `new_ips` to activate this certificate as an additional certificate on your Site by allocating a new set of Dedicated IPs (may require a Plan upgrade).  Set to the ID of a currently active certificate to replace that certificate on its set of dedicated IPs.  A certificate belonging to a Custom Domain activates only for that domain: leave blank, use `any`, or specify that domain's current certificate ID.</li>
   * </ul>
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void activate(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("replace_cert") && !(parameters.get("replace_cert") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: replace_cert must be of type String parameters[\"replace_cert\"]");
    }

    String apiPath = String.format("/certificates/%s/activate", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @see #deactivate(Long, HashMap, HashMap)
   */
  public void deactivate() throws RuntimeException {
    deactivate(null, null, null);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @see #deactivate(Long, HashMap, HashMap)
   */
  public void deactivate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    deactivate(id, parameters, null);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @see #deactivate(Long, HashMap, HashMap)
   */
  public void deactivate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    deactivate(null, parameters, options);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void deactivate(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/certificates/%s/deactivate", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @see #replaceWithFiles(Long, HashMap, HashMap)
   */
  public void replaceWithFiles() throws RuntimeException {
    replaceWithFiles(null, null, null);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @see #replaceWithFiles(Long, HashMap, HashMap)
   */
  public void replaceWithFiles(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    replaceWithFiles(id, parameters, null);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @see #replaceWithFiles(Long, HashMap, HashMap)
   */
  public void replaceWithFiles(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    replaceWithFiles(null, parameters, options);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public void replaceWithFiles(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    String apiPath = String.format("/certificates/%s/replace_with_files_certificate", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.POST, apiPath, parameters, options);
  }

  /**
   * Update Certificate
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Certificate update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Certificate
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Certificate update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Certificate
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public Certificate update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return update(null, parameters, options);
  }

  /**
   * Update Certificate
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Internal certificate name.</li>
   * <li>{@code intermediates} (String): Any intermediate certificates.  PEM or PKCS7 formats accepted.</li>
   * <li>{@code certificate} (String): The certificate.  PEM or PKCS7 formats accepted.</li>
   * </ul>
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code Certificate}
   */
  public Certificate update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("intermediates") && !(parameters.get("intermediates") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: intermediates must be of type String parameters[\"intermediates\"]");
    }

    if (parameters.containsKey("certificate") && !(parameters.get("certificate") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: certificate must be of type String parameters[\"certificate\"]");
    }

    String apiPath = String.format("/certificates/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    TypeReference<Certificate> typeReference = new TypeReference<Certificate>() {};
    return requestItem(RequestMethods.PATCH, apiPath, typeReference, parameters, options);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Certificate
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
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

    String apiPath = String.format("/certificates/%s", UrlUtils.encodeUrlPath(String.valueOf(id)));
    request(RequestMethods.DELETE, apiPath, parameters, options);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }
}
