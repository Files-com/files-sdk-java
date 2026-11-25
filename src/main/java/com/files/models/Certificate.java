package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Certificate implements ModelInterface {
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

  // One mapper for every Certificate, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code Certificate} with no attributes set.
   */
  public Certificate() {
    this(null, null);
  }

  /**
   * Creates a {@code Certificate} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public Certificate(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code Certificate} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public Certificate(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Certificate ID
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
   * Descriptive name of certificate
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
   * Custom Domain that owns this certificate, including while the certificate is inactive.
   */
  @JsonProperty("custom_domain_id")
  public Long customDomainId;

  /**
   * Gets {@link #customDomainId}.
   *
   * @return the value of {@link #customDomainId}
   */
  public Long getCustomDomainId() {
    return customDomainId;
  }

  /**
   * Sets {@link #customDomainId}.
   *
   * @param customDomainId the new value
   */
  public void setCustomDomainId(Long customDomainId) {
    this.customDomainId = customDomainId;
  }

  /**
   * Full text of SSL certificate
   */
  @JsonProperty("certificate")
  public String certificate;

  /**
   * Gets {@link #certificate}.
   *
   * @return the value of {@link #certificate}
   */
  public String getCertificate() {
    return certificate;
  }

  /**
   * Sets {@link #certificate}.
   *
   * @param certificate the new value
   */
  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  /**
   * Certificate created at
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
   * Certificate status. (One of `Request Generated`, `New`, `Active`, `Active/Expired`, `Expired`, or `Available`)
   */
  @JsonProperty("display_status")
  public String displayStatus;

  /**
   * Gets {@link #displayStatus}.
   *
   * @return the value of {@link #displayStatus}
   */
  public String getDisplayStatus() {
    return displayStatus;
  }

  /**
   * Sets {@link #displayStatus}.
   *
   * @param displayStatus the new value
   */
  public void setDisplayStatus(String displayStatus) {
    this.displayStatus = displayStatus;
  }

  /**
   * Domains on this certificate other than files.com domains
   */
  @JsonProperty("domains")
  public String[] domains;

  /**
   * Gets {@link #domains}.
   *
   * @return the value of {@link #domains}
   */
  public String[] getDomains() {
    return domains;
  }

  /**
   * Sets {@link #domains}.
   *
   * @param domains the new value
   */
  public void setDomains(String[] domains) {
    this.domains = domains;
  }

  /**
   * Certificate expiration date/time
   */
  @JsonProperty("expires_at")
  public Date expiresAt;

  /**
   * Gets {@link #expiresAt}.
   *
   * @return the value of {@link #expiresAt}
   */
  public Date getExpiresAt() {
    return expiresAt;
  }

  /**
   * Sets {@link #expiresAt}.
   *
   * @param expiresAt the new value
   */
  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  /**
   * Is this certificate automatically managed and renewed by Files.com?
   */
  @JsonProperty("brick_managed")
  public Boolean brickManaged;

  /**
   * Gets {@link #brickManaged}.
   *
   * @return the value of {@link #brickManaged}
   */
  public Boolean getBrickManaged() {
    return brickManaged;
  }

  /**
   * Sets {@link #brickManaged}.
   *
   * @param brickManaged the new value
   */
  public void setBrickManaged(Boolean brickManaged) {
    this.brickManaged = brickManaged;
  }

  /**
   * Intermediate certificates
   */
  @JsonProperty("intermediates")
  public String intermediates;

  /**
   * Gets {@link #intermediates}.
   *
   * @return the value of {@link #intermediates}
   */
  public String getIntermediates() {
    return intermediates;
  }

  /**
   * Sets {@link #intermediates}.
   *
   * @param intermediates the new value
   */
  public void setIntermediates(String intermediates) {
    this.intermediates = intermediates;
  }

  /**
   * A list of IP addresses associated with this SSL Certificate
   */
  @JsonProperty("ip_addresses")
  public String[] ipAddresses;

  /**
   * Gets {@link #ipAddresses}.
   *
   * @return the value of {@link #ipAddresses}
   */
  public String[] getIpAddresses() {
    return ipAddresses;
  }

  /**
   * Sets {@link #ipAddresses}.
   *
   * @param ipAddresses the new value
   */
  public void setIpAddresses(String[] ipAddresses) {
    this.ipAddresses = ipAddresses;
  }

  /**
   * X509 issuer
   */
  @JsonProperty("issuer")
  public String issuer;

  /**
   * Gets {@link #issuer}.
   *
   * @return the value of {@link #issuer}
   */
  public String getIssuer() {
    return issuer;
  }

  /**
   * Sets {@link #issuer}.
   *
   * @param issuer the new value
   */
  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  /**
   * Type of key
   */
  @JsonProperty("key_type")
  public String keyType;

  /**
   * Gets {@link #keyType}.
   *
   * @return the value of {@link #keyType}
   */
  public String getKeyType() {
    return keyType;
  }

  /**
   * Sets {@link #keyType}.
   *
   * @param keyType the new value
   */
  public void setKeyType(String keyType) {
    this.keyType = keyType;
  }

  /**
   * Certificate signing request text
   */
  @JsonProperty("request")
  public String request;

  /**
   * Gets {@link #request}.
   *
   * @return the value of {@link #request}
   */
  public String getRequest() {
    return request;
  }

  /**
   * Sets {@link #request}.
   *
   * @param request the new value
   */
  public void setRequest(String request) {
    this.request = request;
  }

  /**
   * Certificate status (Request Generated, New, Active, Active/Expired, Expired, or Available)
   */
  @JsonProperty("status")
  public String status;

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets {@link #status}.
   *
   * @param status the new value
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * X509 Subject name
   */
  @JsonProperty("subject")
  public String subject;

  /**
   * Gets {@link #subject}.
   *
   * @return the value of {@link #subject}
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Sets {@link #subject}.
   *
   * @param subject the new value
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Domain for certificate.
   */
  @JsonProperty("certificate_domain")
  public String certificateDomain;

  /**
   * Gets {@link #certificateDomain}.
   *
   * @return the value of {@link #certificateDomain}
   */
  public String getCertificateDomain() {
    return certificateDomain;
  }

  /**
   * Sets {@link #certificateDomain}.
   *
   * @param certificateDomain the new value
   */
  public void setCertificateDomain(String certificateDomain) {
    this.certificateDomain = certificateDomain;
  }

  /**
   * Additional domains for the certificate CSR.
   */
  @JsonProperty("certificate_extra_domains")
  public String[] certificateExtraDomains;

  /**
   * Gets {@link #certificateExtraDomains}.
   *
   * @return the value of {@link #certificateExtraDomains}
   */
  public String[] getCertificateExtraDomains() {
    return certificateExtraDomains;
  }

  /**
   * Sets {@link #certificateExtraDomains}.
   *
   * @param certificateExtraDomains the new value
   */
  public void setCertificateExtraDomains(String[] certificateExtraDomains) {
    this.certificateExtraDomains = certificateExtraDomains;
  }

  /**
   * Country.
   */
  @JsonProperty("certificate_country")
  public String certificateCountry;

  /**
   * Gets {@link #certificateCountry}.
   *
   * @return the value of {@link #certificateCountry}
   */
  public String getCertificateCountry() {
    return certificateCountry;
  }

  /**
   * Sets {@link #certificateCountry}.
   *
   * @param certificateCountry the new value
   */
  public void setCertificateCountry(String certificateCountry) {
    this.certificateCountry = certificateCountry;
  }

  /**
   * State or province.
   */
  @JsonProperty("certificate_state_or_province")
  public String certificateStateOrProvince;

  /**
   * Gets {@link #certificateStateOrProvince}.
   *
   * @return the value of {@link #certificateStateOrProvince}
   */
  public String getCertificateStateOrProvince() {
    return certificateStateOrProvince;
  }

  /**
   * Sets {@link #certificateStateOrProvince}.
   *
   * @param certificateStateOrProvince the new value
   */
  public void setCertificateStateOrProvince(String certificateStateOrProvince) {
    this.certificateStateOrProvince = certificateStateOrProvince;
  }

  /**
   * City or locale.
   */
  @JsonProperty("certificate_city_or_locale")
  public String certificateCityOrLocale;

  /**
   * Gets {@link #certificateCityOrLocale}.
   *
   * @return the value of {@link #certificateCityOrLocale}
   */
  public String getCertificateCityOrLocale() {
    return certificateCityOrLocale;
  }

  /**
   * Sets {@link #certificateCityOrLocale}.
   *
   * @param certificateCityOrLocale the new value
   */
  public void setCertificateCityOrLocale(String certificateCityOrLocale) {
    this.certificateCityOrLocale = certificateCityOrLocale;
  }

  /**
   * Company name.
   */
  @JsonProperty("certificate_company_name")
  public String certificateCompanyName;

  /**
   * Gets {@link #certificateCompanyName}.
   *
   * @return the value of {@link #certificateCompanyName}
   */
  public String getCertificateCompanyName() {
    return certificateCompanyName;
  }

  /**
   * Sets {@link #certificateCompanyName}.
   *
   * @param certificateCompanyName the new value
   */
  public void setCertificateCompanyName(String certificateCompanyName) {
    this.certificateCompanyName = certificateCompanyName;
  }

  /**
   * Department name or organization unit 1
   */
  @JsonProperty("csr_ou1")
  public String csrOu1;

  /**
   * Gets {@link #csrOu1}.
   *
   * @return the value of {@link #csrOu1}
   */
  public String getCsrOu1() {
    return csrOu1;
  }

  /**
   * Sets {@link #csrOu1}.
   *
   * @param csrOu1 the new value
   */
  public void setCsrOu1(String csrOu1) {
    this.csrOu1 = csrOu1;
  }

  /**
   * Department name or organization unit 2
   */
  @JsonProperty("csr_ou2")
  public String csrOu2;

  /**
   * Gets {@link #csrOu2}.
   *
   * @return the value of {@link #csrOu2}
   */
  public String getCsrOu2() {
    return csrOu2;
  }

  /**
   * Sets {@link #csrOu2}.
   *
   * @param csrOu2 the new value
   */
  public void setCsrOu2(String csrOu2) {
    this.csrOu2 = csrOu2;
  }

  /**
   * Department name or organization unit 3
   */
  @JsonProperty("csr_ou3")
  public String csrOu3;

  /**
   * Gets {@link #csrOu3}.
   *
   * @return the value of {@link #csrOu3}
   */
  public String getCsrOu3() {
    return csrOu3;
  }

  /**
   * Sets {@link #csrOu3}.
   *
   * @param csrOu3 the new value
   */
  public void setCsrOu3(String csrOu3) {
    this.csrOu3 = csrOu3;
  }

  /**
   * Email address for the certificate owner.
   */
  @JsonProperty("certificate_email_address")
  public String certificateEmailAddress;

  /**
   * Gets {@link #certificateEmailAddress}.
   *
   * @return the value of {@link #certificateEmailAddress}
   */
  public String getCertificateEmailAddress() {
    return certificateEmailAddress;
  }

  /**
   * Sets {@link #certificateEmailAddress}.
   *
   * @param certificateEmailAddress the new value
   */
  public void setCertificateEmailAddress(String certificateEmailAddress) {
    this.certificateEmailAddress = certificateEmailAddress;
  }

  /**
   * Certificate private key.
   */
  @JsonProperty("private_key")
  public String privateKey;

  /**
   * Gets {@link #privateKey}.
   *
   * @return the value of {@link #privateKey}
   */
  public String getPrivateKey() {
    return privateKey;
  }

  /**
   * Sets {@link #privateKey}.
   *
   * @param privateKey the new value
   */
  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
   * Certificate password.
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
   * Activate SSL Certificate
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code replace_cert} (String): Leave blank or set to `any` to replace any currently active certificate, if applicable.  Set to `new_ips` to activate this certificate as an additional certificate on your Site by allocating a new set of Dedicated IPs (may require a Plan upgrade).  Set to the ID of a currently active certificate to replace that certificate on its set of dedicated IPs.  A certificate belonging to a Custom Domain activates only for that domain: leave blank, use `any`, or specify that domain's current certificate ID.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   */
  public void activate(HashMap<String, Object> parameters) throws IOException {
    filesClient().certificates().activate(this.id, parameters, this.options);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @param parameters the request's parameters, by their API names
   */
  public void deactivate(HashMap<String, Object> parameters) throws IOException {
    filesClient().certificates().deactivate(this.id, parameters, this.options);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @param parameters the request's parameters, by their API names
   */
  public void replaceWithFiles(HashMap<String, Object> parameters) throws IOException {
    filesClient().certificates().replaceWithFiles(this.id, parameters, this.options);
  }

  /**
   * Updates this Certificate. On success, this object's attributes are replaced by the server's response, which is also returned.
   * <p>Update Certificate
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code name} (String): Internal certificate name.</li>
   * <li>{@code intermediates} (String): Any intermediate certificates.  PEM or PKCS7 formats accepted.</li>
   * <li>{@code certificate} (String): The certificate.  PEM or PKCS7 formats accepted.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @return the {@code Certificate}
   */
  public Certificate update(HashMap<String, Object> parameters) throws IOException {
    Certificate updated = filesClient().certificates().update(this.id, parameters, this.options);
    refreshFrom(updated);
    return updated;
  }

  /**
   * Delete Certificate
   *
   * @param parameters the request's parameters, by their API names
   */
  public void delete(HashMap<String, Object> parameters) throws IOException {
    filesClient().certificates().delete(this.id, parameters, this.options);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(HashMap)
   */
  public void destroy(HashMap<String, Object> parameters) throws IOException {
    delete(parameters);
  }


  /**
   * Saves this Certificate: creates it when it has no id, and otherwise updates it.
   * On success, this object's attributes are replaced by the server's response.
   */
  public void save() throws IOException {
    HashMap<String, Object> parameters = saveParameters();
    if (parameters.get("id") != null) {
      update(parameters);
    } else {
      refreshFrom(filesClient().certificates().create(parameters, this.options));
    }
  }

  // Save sends the attributes in their JSON form, so dates, for example, become the API's date
  // text. Where an operation validates a parameter as a Java array, file, or exact decimal instead,
  // the attribute's own value is sent, and an array for a string parameter is comma-delimited.
  private HashMap<String, Object> saveParameters() throws JsonProcessingException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(MAPPER.writeValueAsString(this));
    if (this.certificateExtraDomains != null) {
      parameters.put("certificate_extra_domains", this.certificateExtraDomains);
    }
    return parameters;
  }

  // Replaces every attribute with the server's representation of this record after a successful
  // save or update. Request options are not attributes, so they stay as they are.
  private void refreshFrom(Certificate canonical) {
    this.id = canonical.id;
    this.name = canonical.name;
    this.customDomainId = canonical.customDomainId;
    this.certificate = canonical.certificate;
    this.createdAt = canonical.createdAt;
    this.displayStatus = canonical.displayStatus;
    this.domains = canonical.domains;
    this.expiresAt = canonical.expiresAt;
    this.brickManaged = canonical.brickManaged;
    this.intermediates = canonical.intermediates;
    this.ipAddresses = canonical.ipAddresses;
    this.issuer = canonical.issuer;
    this.keyType = canonical.keyType;
    this.request = canonical.request;
    this.status = canonical.status;
    this.subject = canonical.subject;
    this.certificateDomain = canonical.certificateDomain;
    this.certificateExtraDomains = canonical.certificateExtraDomains;
    this.certificateCountry = canonical.certificateCountry;
    this.certificateStateOrProvince = canonical.certificateStateOrProvince;
    this.certificateCityOrLocale = canonical.certificateCityOrLocale;
    this.certificateCompanyName = canonical.certificateCompanyName;
    this.csrOu1 = canonical.csrOu1;
    this.csrOu2 = canonical.csrOu2;
    this.csrOu3 = canonical.csrOu3;
    this.certificateEmailAddress = canonical.certificateEmailAddress;
    this.privateKey = canonical.privateKey;
    this.password = canonical.password;
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Certificate> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Certificate> list(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static ListIterator<Certificate> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().certificates().list(parameters, options);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Certificate> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Certificates
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<Certificate> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Certificate find() throws RuntimeException {
    return find(null, null, null);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Certificate find(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return find(id, parameters, null);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Certificate find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Certificate find(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().certificates().find(id, parameters, options);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Certificate get() throws RuntimeException {
    return get(null, null, null);
  }

  /**
   * Show Certificate
   *
   * @see #find(Long, HashMap, HashMap)
   */
  public static Certificate get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return find(id, parameters, options);
  }

  /**
   * Create CSR or import existing SSL Certificate
   *
   * @see #create(HashMap, HashMap)
   */
  public static Certificate create() throws RuntimeException {
    return create(null, null);
  }

  /**
   * Create CSR or import existing SSL Certificate
   *
   * @see #create(HashMap, HashMap)
   */
  public static Certificate create(HashMap<String, Object> parameters) throws RuntimeException {
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
  public static Certificate create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().certificates().create(parameters, options);
  }


  /**
   * Activate SSL Certificate
   *
   * @see #activate(Long, HashMap, HashMap)
   */
  public static void activate() throws RuntimeException {
    activate(null, null, null);
  }

  /**
   * Activate SSL Certificate
   *
   * @see #activate(Long, HashMap, HashMap)
   */
  public static void activate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    activate(id, parameters, null);
  }

  /**
   * Activate SSL Certificate
   *
   * @see #activate(Long, HashMap, HashMap)
   */
  public static void activate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static void activate(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().certificates().activate(id, parameters, options);
  }


  /**
   * Deactivate SSL Certificate
   *
   * @see #deactivate(Long, HashMap, HashMap)
   */
  public static void deactivate() throws RuntimeException {
    deactivate(null, null, null);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @see #deactivate(Long, HashMap, HashMap)
   */
  public static void deactivate(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    deactivate(id, parameters, null);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @see #deactivate(Long, HashMap, HashMap)
   */
  public static void deactivate(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    deactivate(null, parameters, options);
  }

  /**
   * Deactivate SSL Certificate
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void deactivate(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().certificates().deactivate(id, parameters, options);
  }


  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @see #replaceWithFiles(Long, HashMap, HashMap)
   */
  public static void replaceWithFiles() throws RuntimeException {
    replaceWithFiles(null, null, null);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @see #replaceWithFiles(Long, HashMap, HashMap)
   */
  public static void replaceWithFiles(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    replaceWithFiles(id, parameters, null);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @see #replaceWithFiles(Long, HashMap, HashMap)
   */
  public static void replaceWithFiles(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    replaceWithFiles(null, parameters, options);
  }

  /**
   * Replace this active custom SSL Certificate for the Primary Custom Domain with a Files.com SSL Certificate.  The Primary Custom Domain's CNAME record must point at the Site's subdomain.  The Files.com certificate is issued and activated in the background
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void replaceWithFiles(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().certificates().replaceWithFiles(id, parameters, options);
  }


  /**
   * Update Certificate
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Certificate update() throws RuntimeException {
    return update(null, null, null);
  }

  /**
   * Update Certificate
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Certificate update(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    return update(id, parameters, null);
  }

  /**
   * Update Certificate
   *
   * @see #update(Long, HashMap, HashMap)
   */
  public static Certificate update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
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
  public static Certificate update(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().certificates().update(id, parameters, options);
  }


  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete() throws RuntimeException {
    delete(null, null, null);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(Long id, HashMap<String, Object> parameters) throws RuntimeException {
    delete(id, parameters, null);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(null, parameters, options);
  }

  /**
   * Delete Certificate
   *
   * @param id Certificate ID.
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   */
  public static void delete(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    FilesClient.defaultClient().certificates().delete(id, parameters, options);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy() throws RuntimeException {
    destroy(null, null, null);
  }

  /**
   * Delete Certificate
   *
   * @see #delete(Long, HashMap, HashMap)
   */
  public static void destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    delete(id, parameters, options);
  }

}
