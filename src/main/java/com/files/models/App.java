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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class App implements ModelInterface {
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

  // One mapper for every App, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();


  /**
   * Creates a {@code App} with no attributes set.
   */
  public App() {
    this(null, null);
  }

  /**
   * Creates a {@code App} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public App(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code App} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
  public App(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * The type of the App
   */
  @JsonProperty("app_type")
  public String appType;

  /**
   * Gets {@link #appType}.
   *
   * @return the value of {@link #appType}
   */
  public String getAppType() {
    return appType;
  }

  /**
   * Collection of named links to documentation
   */
  @JsonProperty("documentation_links")
  public Object documentationLinks;

  /**
   * Gets {@link #documentationLinks}.
   *
   * @return the value of {@link #documentationLinks}
   */
  public Object getDocumentationLinks() {
    return documentationLinks;
  }

  /**
   * Long description for the in-App landing page
   */
  @JsonProperty("extended_description")
  public String extendedDescription;

  /**
   * Gets {@link #extendedDescription}.
   *
   * @return the value of {@link #extendedDescription}
   */
  public String getExtendedDescription() {
    return extendedDescription;
  }

  /**
   * Long form description of the App
   */
  @JsonProperty("extended_description_for_marketing_site")
  public String extendedDescriptionForMarketingSite;

  /**
   * Gets {@link #extendedDescriptionForMarketingSite}.
   *
   * @return the value of {@link #extendedDescriptionForMarketingSite}
   */
  public String getExtendedDescriptionForMarketingSite() {
    return extendedDescriptionForMarketingSite;
  }

  /**
   * Link to external homepage
   */
  @JsonProperty("external_homepage_url")
  public String externalHomepageUrl;

  /**
   * Gets {@link #externalHomepageUrl}.
   *
   * @return the value of {@link #externalHomepageUrl}
   */
  public String getExternalHomepageUrl() {
    return externalHomepageUrl;
  }

  /**
   * Is featured on the App listing?
   */
  @JsonProperty("featured")
  public Boolean featured;

  /**
   * Gets {@link #featured}.
   *
   * @return the value of {@link #featured}
   */
  public Boolean getFeatured() {
    return featured;
  }

  /**
   * Associated Folder Behavior type, if any
   */
  @JsonProperty("folder_behavior_type")
  public String folderBehaviorType;

  /**
   * Gets {@link #folderBehaviorType}.
   *
   * @return the value of {@link #folderBehaviorType}
   */
  public String getFolderBehaviorType() {
    return folderBehaviorType;
  }

  /**
   * App icon
   */
  @JsonProperty("icon_url")
  public String iconUrl;

  /**
   * Gets {@link #iconUrl}.
   *
   * @return the value of {@link #iconUrl}
   */
  public String getIconUrl() {
    return iconUrl;
  }

  /**
   * Logo thumbnail for the App
   */
  @JsonProperty("logo_thumbnail_url")
  public String logoThumbnailUrl;

  /**
   * Gets {@link #logoThumbnailUrl}.
   *
   * @return the value of {@link #logoThumbnailUrl}
   */
  public String getLogoThumbnailUrl() {
    return logoThumbnailUrl;
  }

  /**
   * Full size logo for the App
   */
  @JsonProperty("logo_url")
  public String logoUrl;

  /**
   * Gets {@link #logoUrl}.
   *
   * @return the value of {@link #logoUrl}
   */
  public String getLogoUrl() {
    return logoUrl;
  }

  /**
   * Marketing introdution of the App
   */
  @JsonProperty("marketing_intro")
  public String marketingIntro;

  /**
   * Gets {@link #marketingIntro}.
   *
   * @return the value of {@link #marketingIntro}
   */
  public String getMarketingIntro() {
    return marketingIntro;
  }

  /**
   * Marketing video page
   */
  @JsonProperty("marketing_youtube_url")
  public String marketingYoutubeUrl;

  /**
   * Gets {@link #marketingYoutubeUrl}.
   *
   * @return the value of {@link #marketingYoutubeUrl}
   */
  public String getMarketingYoutubeUrl() {
    return marketingYoutubeUrl;
  }

  /**
   * Name of the App
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
   * Package manager install command
   */
  @JsonProperty("package_manager_install_command")
  public String packageManagerInstallCommand;

  /**
   * Gets {@link #packageManagerInstallCommand}.
   *
   * @return the value of {@link #packageManagerInstallCommand}
   */
  public String getPackageManagerInstallCommand() {
    return packageManagerInstallCommand;
  }

  /**
   * Associated Remote Server type, if any
   */
  @JsonProperty("remote_server_type")
  public String remoteServerType;

  /**
   * Gets {@link #remoteServerType}.
   *
   * @return the value of {@link #remoteServerType}
   */
  public String getRemoteServerType() {
    return remoteServerType;
  }

  /**
   * Screenshots of the App
   */
  @JsonProperty("screenshot_list_urls")
  public String[] screenshotListUrls;

  /**
   * Gets {@link #screenshotListUrls}.
   *
   * @return the value of {@link #screenshotListUrls}
   */
  public String[] getScreenshotListUrls() {
    return screenshotListUrls;
  }

  /**
   * Link to SDK installation instructions
   */
  @JsonProperty("sdk_installation_instructions_link")
  public String sdkInstallationInstructionsLink;

  /**
   * Gets {@link #sdkInstallationInstructionsLink}.
   *
   * @return the value of {@link #sdkInstallationInstructionsLink}
   */
  public String getSdkInstallationInstructionsLink() {
    return sdkInstallationInstructionsLink;
  }

  /**
   * Short description of the App
   */
  @JsonProperty("short_description")
  public String shortDescription;

  /**
   * Gets {@link #shortDescription}.
   *
   * @return the value of {@link #shortDescription}
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * Associated SSO Strategy type, if any
   */
  @JsonProperty("sso_strategy_type")
  public String ssoStrategyType;

  /**
   * Gets {@link #ssoStrategyType}.
   *
   * @return the value of {@link #ssoStrategyType}
   */
  public String getSsoStrategyType() {
    return ssoStrategyType;
  }

  /**
   * Associated SIEM type, if any
   */
  @JsonProperty("siem_type")
  public String siemType;

  /**
   * Gets {@link #siemType}.
   *
   * @return the value of {@link #siemType}
   */
  public String getSiemType() {
    return siemType;
  }

  /**
   * Tutorial video page
   */
  @JsonProperty("tutorial_youtube_url")
  public String tutorialYoutubeUrl;

  /**
   * Gets {@link #tutorialYoutubeUrl}.
   *
   * @return the value of {@link #tutorialYoutubeUrl}
   */
  public String getTutorialYoutubeUrl() {
    return tutorialYoutubeUrl;
  }


  /**
   * List Apps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<App> list() throws RuntimeException {
    return list(null, null);
  }

  /**
   * List Apps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<App> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  /**
   * List Apps
   * <p>The parameters map may hold:</p>
   * <ul>
   * <li>{@code cursor} (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.</li>
   * <li>{@code per_page} (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).</li>
   * <li>{@code sort_by} (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name` and `app_type`.</li>
   * <li>{@code filter} (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`. Valid field combinations are `[ name, app_type ]`.</li>
   * <li>{@code filter_prefix} (Object): If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.</li>
   * </ul>
   *
   * @param parameters the request's parameters, by their API names
   * @param options the request options for this call, such as {@code api_key} or {@code workspace_id}
   * @return the {@code App} records, in a {@code ListIterator}
   */
  public static ListIterator<App> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return FilesClient.defaultClient().apps().list(parameters, options);
  }

  /**
   * List Apps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<App> all() throws RuntimeException {
    return all(null, null);
  }

  /**
   * List Apps
   *
   * @see #list(HashMap, HashMap)
   */
  public static ListIterator<App> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
