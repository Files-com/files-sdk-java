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
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class App {
  private HashMap<String, Object> options;
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
  @Getter
  @JsonProperty("app_type")
  public String appType;

  /**
  * Collection of named links to documentation
  */
  @Getter
  @JsonProperty("documentation_links")
  public Map<String, String> documentationLinks;

  /**
  * Long description for the in-App landing page
  */
  @Getter
  @JsonProperty("extended_description")
  public String extendedDescription;

  /**
  * Long form description of the App
  */
  @Getter
  @JsonProperty("extended_description_for_marketing_site")
  public String extendedDescriptionForMarketingSite;

  /**
  * Link to external homepage
  */
  @Getter
  @JsonProperty("external_homepage_url")
  public String externalHomepageUrl;

  /**
  * Is featured on the App listing?
  */
  @Getter
  @JsonProperty("featured")
  public Boolean featured;

  /**
  * Associated Folder Behavior type, if any
  */
  @Getter
  @JsonProperty("folder_behavior_type")
  public String folderBehaviorType;

  /**
  * App icon
  */
  @Getter
  @JsonProperty("icon_url")
  public String iconUrl;

  /**
  * Logo thumbnail for the App
  */
  @Getter
  @JsonProperty("logo_thumbnail_url")
  public String logoThumbnailUrl;

  /**
  * Full size logo for the App
  */
  @Getter
  @JsonProperty("logo_url")
  public String logoUrl;

  /**
  * Marketing introdution of the App
  */
  @Getter
  @JsonProperty("marketing_intro")
  public String marketingIntro;

  /**
  * Marketing video page
  */
  @Getter
  @JsonProperty("marketing_youtube_url")
  public String marketingYoutubeUrl;

  /**
  * Name of the App
  */
  @Getter
  @JsonProperty("name")
  public String name;

  /**
  * Package manager install command
  */
  @Getter
  @JsonProperty("package_manager_install_command")
  public String packageManagerInstallCommand;

  /**
  * Associated Remote Server type, if any
  */
  @Getter
  @JsonProperty("remote_server_type")
  public String remoteServerType;

  /**
  * Screenshots of the App
  */
  @Getter
  @JsonProperty("screenshot_list_urls")
  public String[] screenshotListUrls;

  /**
  * Link to SDK installation instructions
  */
  @Getter
  @JsonProperty("sdk_installation_instructions_link")
  public String sdkInstallationInstructionsLink;

  /**
  * Short description of the App
  */
  @Getter
  @JsonProperty("short_description")
  public String shortDescription;

  /**
  * Associated SSO Strategy type, if any
  */
  @Getter
  @JsonProperty("sso_strategy_type")
  public String ssoStrategyType;

  /**
  * Tutorial video page
  */
  @Getter
  @JsonProperty("tutorial_youtube_url")
  public String tutorialYoutubeUrl;



  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[name]=desc`). Valid fields are `name` and `app_type`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `name` and `app_type`. Valid field combinations are `[ name, app_type ]` and `[ app_type, name ]`.
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
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long)) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
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
