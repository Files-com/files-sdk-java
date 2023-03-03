package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Automation {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public Automation() {
    this(null, null);
  }

  public Automation(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Automation(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Automation ID
  */
  @Getter
  @Setter
  @JsonProperty("id")
  public Long id;

  /**
  * Automation type
  */
  @Getter
  @Setter
  @JsonProperty("automation")
  public String automation;

  /**
  * Indicates if the automation has been deleted.
  */
  @Getter
  @Setter
  @JsonProperty("deleted")
  public Boolean deleted;

  /**
  * If true, this automation will not run.
  */
  @Getter
  @Setter
  @JsonProperty("disabled")
  public Boolean disabled;

  /**
  * How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
  */
  @Getter
  @Setter
  @JsonProperty("trigger")
  public String trigger;

  /**
  * If trigger is `daily`, this specifies how often to run this automation.  One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  */
  @Getter
  @Setter
  @JsonProperty("interval")
  public String interval;

  /**
  * Time when automation was last modified. Does not change for name or description updates.
  */
  @Getter
  @Setter
  @JsonProperty("last_modified_at")
  public Date lastModifiedAt;

  /**
  * Name for this automation.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * If trigger is `custom_schedule`, Custom schedule description for when the automation should be run.
  */
  @Getter
  @Setter
  @JsonProperty("schedule")
  public Map<String, String> schedule;

  /**
  * Source Path
  */
  @Getter
  @Setter
  @JsonProperty("source")
  public String source;

  /**
  * Destination Path
  */
  @Getter
  @Setter
  @JsonProperty("destinations")
  public Object[] destinations;

  /**
  * If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  */
  @Getter
  @Setter
  @JsonProperty("destination_replace_from")
  public String destinationReplaceFrom;

  /**
  * If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  */
  @Getter
  @Setter
  @JsonProperty("destination_replace_to")
  public String destinationReplaceTo;

  /**
  * Description for the this Automation.
  */
  @Getter
  @Setter
  @JsonProperty("description")
  public String description;

  /**
  * Path on which this Automation runs.  Supports globs. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
  */
  @Getter
  @Setter
  @JsonProperty("path")
  public String path;

  /**
  * User ID of the Automation's creator.
  */
  @Getter
  @Setter
  @JsonProperty("user_id")
  public Long userId;

  /**
  * IDs of remote sync folder behaviors to run by this Automation
  */
  @Getter
  @Setter
  @JsonProperty("sync_ids")
  public Object[] syncIds;

  /**
  * IDs of Users for the Automation (i.e. who to Request File from)
  */
  @Getter
  @Setter
  @JsonProperty("user_ids")
  public Object[] userIds;

  /**
  * IDs of Groups for the Automation (i.e. who to Request File from)
  */
  @Getter
  @Setter
  @JsonProperty("group_ids")
  public Object[] groupIds;

  /**
  * If trigger is `webhook`, this is the URL of the webhook to trigger the Automation.
  */
  @Getter
  @Setter
  @JsonProperty("webhook_url")
  public String webhookUrl;

  /**
  * If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
  */
  @Getter
  @Setter
  @JsonProperty("trigger_actions")
  public Object[] triggerActions;

  /**
  * A Hash of attributes specific to the automation type.
  */
  @Getter
  @Setter
  @JsonProperty("value")
  public Map<String, String> value;

  /**
  * DEPRECATED: Destination Path. Use `destinations` instead.
  */
  @Getter
  @Setter
  @JsonProperty("destination")
  public String destination;

  /**
  * Parameters:
  *   source - string - Source Path
  *   destination - string - DEPRECATED: Destination Path. Use `destinations` instead.
  *   destinations - array(string) - A list of String destination paths or Hash of folder_path and optional file_path.
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs.
  *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   schedule - object - Custom schedule for running this automation.
  *   description - string - Description for the this Automation.
  *   disabled - boolean - If true, this automation will not run.
  *   name - string - Name for this automation.
  *   trigger - string - How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
  *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
  *   value - object - A Hash of attributes specific to the automation type.
  *   automation - string - Automation type
  */
  public Automation update(HashMap<String, Object> parameters) {
    return update(parameters);
  }

  /**
  */
  public Automation delete(HashMap<String, Object> parameters) {
    return delete(parameters);
  }

  public void destroy(HashMap<String, Object> parameters) {
    delete(parameters);
  }

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    if (parameters.containsKey("id") && parameters.get("id") != null) {
      update(parameters);
    } else {
      Automation newObject = Automation.create(parameters, this.options);
    }
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction (e.g. `sort_by[automation]=desc`). Valid fields are `automation`, `disabled`, `last_modified_at` or `name`.
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `automation`, `last_modified_at` or `disabled`. Valid field combinations are `[ automation, disabled ]` and `[ disabled, automation ]`.
  *   filter_gt - object - If set, return records where the specified field is greater than the supplied value. Valid fields are `automation`, `last_modified_at` or `disabled`. Valid field combinations are `[ automation, disabled ]` and `[ disabled, automation ]`.
  *   filter_gteq - object - If set, return records where the specified field is greater than or equal to the supplied value. Valid fields are `automation`, `last_modified_at` or `disabled`. Valid field combinations are `[ automation, disabled ]` and `[ disabled, automation ]`.
  *   filter_like - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `automation`, `last_modified_at` or `disabled`. Valid field combinations are `[ automation, disabled ]` and `[ disabled, automation ]`.
  *   filter_lt - object - If set, return records where the specified field is less than the supplied value. Valid fields are `automation`, `last_modified_at` or `disabled`. Valid field combinations are `[ automation, disabled ]` and `[ disabled, automation ]`.
  *   filter_lteq - object - If set, return records where the specified field is less than or equal to the supplied value. Valid fields are `automation`, `last_modified_at` or `disabled`. Valid field combinations are `[ automation, disabled ]` and `[ disabled, automation ]`.
  *   with_deleted - boolean - Set to true to include deleted automations in the results.
  *   automation - string - DEPRECATED: Type of automation to filter by. Use `filter[automation]` instead.
  */
  public static List<Automation> list() throws IOException {
    return list(null,null);
  }
  public static List<Automation> list( HashMap<String, Object> parameters) throws IOException {
    return list(parameters, null);
  }


  public static List<Automation> list( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("cursor") && !(parameters.get("cursor") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: cursor must be of type String parameters[\"cursor\"]");
    }
    if (parameters.containsKey("per_page") && !(parameters.get("per_page") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: per_page must be of type Long parameters[\"per_page\"]");
    }
    if (parameters.containsKey("sort_by") && !(parameters.get("sort_by") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: sort_by must be of type Map<String, String> parameters[\"sort_by\"]");
    }
    if (parameters.containsKey("filter") && !(parameters.get("filter") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter must be of type Map<String, String> parameters[\"filter\"]");
    }
    if (parameters.containsKey("filter_gt") && !(parameters.get("filter_gt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gt must be of type Map<String, String> parameters[\"filter_gt\"]");
    }
    if (parameters.containsKey("filter_gteq") && !(parameters.get("filter_gteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_gteq must be of type Map<String, String> parameters[\"filter_gteq\"]");
    }
    if (parameters.containsKey("filter_like") && !(parameters.get("filter_like") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_like must be of type Map<String, String> parameters[\"filter_like\"]");
    }
    if (parameters.containsKey("filter_lt") && !(parameters.get("filter_lt") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lt must be of type Map<String, String> parameters[\"filter_lt\"]");
    }
    if (parameters.containsKey("filter_lteq") && !(parameters.get("filter_lteq") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: filter_lteq must be of type Map<String, String> parameters[\"filter_lteq\"]");
    }
    if (parameters.containsKey("with_deleted") && !(parameters.get("with_deleted") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: with_deleted must be of type Boolean parameters[\"with_deleted\"]");
    }
    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }



    String url = String.format("%s%s/automations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<Automation>> typeReference = new TypeReference<List<Automation>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Automation> all() throws IOException {
    return all(null, null);
  }

  public static List<Automation> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   id (required) - int64 - Automation ID.
  */
  public static List<Automation> find() throws IOException {
    return find(null, null,null);
  }
  public static List<Automation> find(Long id,  HashMap<String, Object> parameters) throws IOException {
    return find(id, parameters, null);
  }

  public static List<Automation> find(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(null, parameters, options);
  }

  public static List<Automation> find(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/automations/%s", urlParts);

    TypeReference<List<Automation>> typeReference = new TypeReference<List<Automation>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static List<Automation> get() throws IOException {
    return get(null, null, null);
  }

  public static List<Automation> get(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return find(id, parameters, options);
  }

  /**
  * Parameters:
  *   source - string - Source Path
  *   destination - string - DEPRECATED: Destination Path. Use `destinations` instead.
  *   destinations - array(string) - A list of String destination paths or Hash of folder_path and optional file_path.
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs.
  *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   schedule - object - Custom schedule for running this automation.
  *   description - string - Description for the this Automation.
  *   disabled - boolean - If true, this automation will not run.
  *   name - string - Name for this automation.
  *   trigger - string - How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
  *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
  *   value - object - A Hash of attributes specific to the automation type.
  *   automation (required) - string - Automation type
  */
  public static Automation create() throws IOException {
    return create(null,null);
  }
  public static Automation create( HashMap<String, Object> parameters) throws IOException {
    return create(parameters, null);
  }


  public static Automation create( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }
    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("destinations") && !(parameters.get("destinations") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: destinations must be of type String[] parameters[\"destinations\"]");
    }
    if (parameters.containsKey("destination_replace_from") && !(parameters.get("destination_replace_from") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_from must be of type String parameters[\"destination_replace_from\"]");
    }
    if (parameters.containsKey("destination_replace_to") && !(parameters.get("destination_replace_to") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_to must be of type String parameters[\"destination_replace_to\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("sync_ids") && !(parameters.get("sync_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: sync_ids must be of type String parameters[\"sync_ids\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }
    if (parameters.containsKey("schedule") && !(parameters.get("schedule") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: schedule must be of type Map<String, String> parameters[\"schedule\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("trigger_actions") && !(parameters.get("trigger_actions") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: trigger_actions must be of type String[] parameters[\"trigger_actions\"]");
    }
    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Map<String, String> parameters[\"value\"]");
    }
    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    if (!parameters.containsKey("automation") || parameters.get("automation") == null) {
      throw new NullPointerException("Parameter missing: automation parameters[\"automation\"]");
    }


    String url = String.format("%s%s/automations", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   source - string - Source Path
  *   destination - string - DEPRECATED: Destination Path. Use `destinations` instead.
  *   destinations - array(string) - A list of String destination paths or Hash of folder_path and optional file_path.
  *   destination_replace_from - string - If set, this string in the destination path will be replaced with the value in `destination_replace_to`.
  *   destination_replace_to - string - If set, this string will replace the value `destination_replace_from` in the destination filename. You can use special patterns here.
  *   interval - string - How often to run this automation? One of: `day`, `week`, `week_end`, `month`, `month_end`, `quarter`, `quarter_end`, `year`, `year_end`
  *   path - string - Path on which this Automation runs.  Supports globs.
  *   sync_ids - string - A list of sync IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   user_ids - string - A list of user IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   group_ids - string - A list of group IDs the automation is associated with. If sent as a string, it should be comma-delimited.
  *   schedule - object - Custom schedule for running this automation.
  *   description - string - Description for the this Automation.
  *   disabled - boolean - If true, this automation will not run.
  *   name - string - Name for this automation.
  *   trigger - string - How this automation is triggered to run. One of: `realtime`, `daily`, `custom_schedule`, `webhook`, `email`, or `action`.
  *   trigger_actions - array(string) - If trigger is `action`, this is the list of action types on which to trigger the automation. Valid actions are create, read, update, destroy, move, copy
  *   value - object - A Hash of attributes specific to the automation type.
  *   automation - string - Automation type
  */
  public static Automation update() throws IOException {
    return update(null, null,null);
  }
  public static Automation update(Long id,  HashMap<String, Object> parameters) throws IOException {
    return update(id, parameters, null);
  }

  public static Automation update(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return update(null, parameters, options);
  }

  public static Automation update(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }
    if (parameters.containsKey("source") && !(parameters.get("source") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: source must be of type String parameters[\"source\"]");
    }
    if (parameters.containsKey("destination") && !(parameters.get("destination") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination must be of type String parameters[\"destination\"]");
    }
    if (parameters.containsKey("destinations") && !(parameters.get("destinations") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: destinations must be of type String[] parameters[\"destinations\"]");
    }
    if (parameters.containsKey("destination_replace_from") && !(parameters.get("destination_replace_from") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_from must be of type String parameters[\"destination_replace_from\"]");
    }
    if (parameters.containsKey("destination_replace_to") && !(parameters.get("destination_replace_to") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: destination_replace_to must be of type String parameters[\"destination_replace_to\"]");
    }
    if (parameters.containsKey("interval") && !(parameters.get("interval") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: interval must be of type String parameters[\"interval\"]");
    }
    if (parameters.containsKey("path") && !(parameters.get("path") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: path must be of type String parameters[\"path\"]");
    }
    if (parameters.containsKey("sync_ids") && !(parameters.get("sync_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: sync_ids must be of type String parameters[\"sync_ids\"]");
    }
    if (parameters.containsKey("user_ids") && !(parameters.get("user_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: user_ids must be of type String parameters[\"user_ids\"]");
    }
    if (parameters.containsKey("group_ids") && !(parameters.get("group_ids") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: group_ids must be of type String parameters[\"group_ids\"]");
    }
    if (parameters.containsKey("schedule") && !(parameters.get("schedule") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: schedule must be of type Map<String, String> parameters[\"schedule\"]");
    }
    if (parameters.containsKey("description") && !(parameters.get("description") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: description must be of type String parameters[\"description\"]");
    }
    if (parameters.containsKey("disabled") && !(parameters.get("disabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: disabled must be of type Boolean parameters[\"disabled\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("trigger") && !(parameters.get("trigger") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: trigger must be of type String parameters[\"trigger\"]");
    }
    if (parameters.containsKey("trigger_actions") && !(parameters.get("trigger_actions") instanceof String[] )) {
      throw new IllegalArgumentException("Bad parameter: trigger_actions must be of type String[] parameters[\"trigger_actions\"]");
    }
    if (parameters.containsKey("value") && !(parameters.get("value") instanceof Map )) {
      throw new IllegalArgumentException("Bad parameter: value must be of type Map<String, String> parameters[\"value\"]");
    }
    if (parameters.containsKey("automation") && !(parameters.get("automation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: automation must be of type String parameters[\"automation\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/automations/%s", urlParts);

    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


  /**
  */
  public static Automation delete() throws IOException {
    return delete(null, null,null);
  }
  public static Automation delete(Long id,  HashMap<String, Object> parameters) throws IOException {
    return delete(id, parameters, null);
  }

  public static Automation delete(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(null, parameters, options);
  }

  public static Automation delete(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id == null && parameters.containsKey("id") && parameters.get("id") != null) {
      id = ((Long) parameters.get("id"));
    }


    if (!(id instanceof Long) ) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (id == null) {
      throw new NullPointerException("Argument or Parameter missing: id parameters[\"id\"]");
    }


    String urlParts[] = {FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), String.valueOf(id)};

    for (int i = 2; i < urlParts.length; i++) {
      try {
        urlParts[i] = new URI(null, null, urlParts[i], null).getRawPath();
      } catch (URISyntaxException ex){
      }
    }

    String url = String.format("%s%s/automations/%s", urlParts);

    TypeReference<Automation> typeReference = new TypeReference<Automation>() {};
    return FilesClient.requestItem(url, RequestMethods.DELETE, typeReference, parameters, options);
  }

  public static Automation destroy() throws IOException {
    return destroy(null, null, null);
  }

  public static Automation destroy(Long id, HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return delete(id, parameters, options);
  }

}


