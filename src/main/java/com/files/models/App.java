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
public class App implements ModelInterface {
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


  public App() {
    this(null, null);
  }

  public App(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public App(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * The type of the App
  */
  @JsonProperty("app_type")
  public String appType;

  public String getAppType() {
    return appType;
  }

  /**
  * Collection of named links to documentation
  */
  @JsonProperty("documentation_links")
  public Map<String, String> documentationLinks;

  public Map<String, String> getDocumentationLinks() {
    return documentationLinks;
  }

  /**
  * Long description for the in-App landing page
  */
  @JsonProperty("extended_description")
  public String extendedDescription;

  public String getExtendedDescription() {
    return extendedDescription;
  }

  /**
  * Long form description of the App
  */
  @JsonProperty("extended_description_for_marketing_site")
  public String extendedDescriptionForMarketingSite;

  public String getExtendedDescriptionForMarketingSite() {
    return extendedDescriptionForMarketingSite;
  }

  /**
  * Link to external homepage
  */
  @JsonProperty("external_homepage_url")
  public String externalHomepageUrl;

  public String getExternalHomepageUrl() {
    return externalHomepageUrl;
  }

  /**
  * Is featured on the App listing?
  */
  @JsonProperty("featured")
  public Boolean featured;

  public Boolean getFeatured() {
    return featured;
  }

  /**
  * Associated Folder Behavior type, if any
  */
  @JsonProperty("folder_behavior_type")
  public String folderBehaviorType;

  public String getFolderBehaviorType() {
    return folderBehaviorType;
  }

  /**
  * App icon
  */
  @JsonProperty("icon_url")
  public String iconUrl;

  public String getIconUrl() {
    return iconUrl;
  }

  /**
  * Logo thumbnail for the App
  */
  @JsonProperty("logo_thumbnail_url")
  public String logoThumbnailUrl;

  public String getLogoThumbnailUrl() {
    return logoThumbnailUrl;
  }

  /**
  * Full size logo for the App
  */
  @JsonProperty("logo_url")
  public String logoUrl;

  public String getLogoUrl() {
    return logoUrl;
  }

  /**
  * Marketing introdution of the App
  */
  @JsonProperty("marketing_intro")
  public String marketingIntro;

  public String getMarketingIntro() {
    return marketingIntro;
  }

  /**
  * Marketing video page
  */
  @JsonProperty("marketing_youtube_url")
  public String marketingYoutubeUrl;

  public String getMarketingYoutubeUrl() {
    return marketingYoutubeUrl;
  }

  /**
  * Name of the App
  */
  @JsonProperty("name")
  public String name;

  public String getName() {
    return name;
  }

  /**
  * Package manager install command
  */
  @JsonProperty("package_manager_install_command")
  public String packageManagerInstallCommand;

  public String getPackageManagerInstallCommand() {
    return packageManagerInstallCommand;
  }

  /**
  * Associated Remote Server type, if any
  */
  @JsonProperty("remote_server_type")
  public String remoteServerType;

  public String getRemoteServerType() {
    return remoteServerType;
  }

  /**
  * Screenshots of the App
  */
  @JsonProperty("screenshot_list_urls")
  public String[] screenshotListUrls;

  public String[] getScreenshotListUrls() {
    return screenshotListUrls;
  }

  /**
  * Link to SDK installation instructions
  */
  @JsonProperty("sdk_installation_instructions_link")
  public String sdkInstallationInstructionsLink;

  public String getSdkInstallationInstructionsLink() {
    return sdkInstallationInstructionsLink;
  }

  /**
  * Short description of the App
  */
  @JsonProperty("short_description")
  public String shortDescription;

  public String getShortDescription() {
    return shortDescription;
  }

  /**
  * Associated SSO Strategy type, if any
  */
  @JsonProperty("sso_strategy_type")
  public String ssoStrategyType;

  public String getSsoStrategyType() {
    return ssoStrategyType;
  }

  /**
  * Associated SIEM type, if any
  */
  @JsonProperty("siem_type")
  public String siemType;

  public String getSiemType() {
    return siemType;
  }

  /**
  * Tutorial video page
  */
  @JsonProperty("tutorial_youtube_url")
  public String tutorialYoutubeUrl;

  public String getTutorialYoutubeUrl() {
    return tutorialYoutubeUrl;
  }


  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `name` and `app_type`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`. Valid field combinations are `[ name, app_type ]`.
  *   filter_prefix - object - If set, return records where the specified field is prefixed by the supplied value. Valid fields are `name`.
  */
  public static ListIterator<App> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<App> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<App> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();



    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long || parameters.get("per_page") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long or Integer parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_prefix") && !(parameters.get("filter_prefix") instanceof Map)) {
      throw new IllegalArgumentException("Bad parameter: filter_prefix must be of type Map<String, String> parameters[\"filter_prefix\"]");
    }


    String url = String.format("%s%s/apps", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<App>> typeReference = new TypeReference<List<App>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<App> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<App> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

}
