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
public class InboxRecipient implements ModelInterface {
  @Setter
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .build();


  public InboxRecipient() {
    this(null, null);
  }

  public InboxRecipient(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InboxRecipient(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * The recipient's company.
  */
  @Getter
  @Setter
  @JsonProperty("company")
  public String company;

  /**
  * The recipient's name.
  */
  @Getter
  @Setter
  @JsonProperty("name")
  public String name;

  /**
  * A note sent to the recipient with the inbox.
  */
  @Getter
  @Setter
  @JsonProperty("note")
  public String note;

  /**
  * The recipient's email address.
  */
  @Getter
  @Setter
  @JsonProperty("recipient")
  public String recipient;

  /**
  * When the Inbox was shared with this recipient.
  */
  @Getter
  @Setter
  @JsonProperty("sent_at")
  public Date sentAt;

  /**
  * Inbox to share.
  */
  @Getter
  @Setter
  @JsonProperty("inbox_id")
  public Long inboxId;

  /**
  * Set to true to share the link with the recipient upon creation.
  */
  @Getter
  @Setter
  @JsonProperty("share_after_create")
  public Boolean shareAfterCreate;

  public void save() throws IOException {
    HashMap<String, Object> parameters = ModelUtils.toParameterMap(objectMapper.writeValueAsString(this));
    InboxRecipient.create(parameters, this.options);
  }

  /**
  * Parameters:
  *   cursor - string - Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
  *   per_page - int64 - Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
  *   sort_by - object - If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are .
  *   filter - object - If set, return records where the specified field is equal to the supplied value. Valid fields are `has_registrations`.
  *   inbox_id (required) - int64 - List recipients for the inbox with this ID.
  */
  public static ListIterator<InboxRecipient> list() throws RuntimeException {
    return list(null, null);
  }

  public static ListIterator<InboxRecipient> list(HashMap<String, Object> parameters) throws RuntimeException {
    return list(parameters, null);
  }


  public static ListIterator<InboxRecipient> list(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("inbox_id") || parameters.get("inbox_id") == null) {
      throw new NullPointerException("Parameter missing: inbox_id parameters[\"inbox_id\"]");
    }

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
    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }


    String url = String.format("%s%s/inbox_recipients", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<List<InboxRecipient>> typeReference = new TypeReference<List<InboxRecipient>>() {};
    return FilesClient.requestList(url, RequestMethods.GET, typeReference, parameters, options);
  }

  public static ListIterator<InboxRecipient> all() throws RuntimeException {
    return all(null, null);
  }

  public static ListIterator<InboxRecipient> all(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return list(parameters, options);
  }

  /**
  * Parameters:
  *   inbox_id (required) - int64 - Inbox to share.
  *   recipient (required) - string - Email address to share this inbox with.
  *   name - string - Name of recipient.
  *   company - string - Company of recipient.
  *   note - string - Note to include in email.
  *   share_after_create - boolean - Set to true to share the link with the recipient upon creation.
  */
  public static InboxRecipient create() throws RuntimeException {
    return create(null, null);
  }

  public static InboxRecipient create(HashMap<String, Object> parameters) throws RuntimeException {
    return create(parameters, null);
  }


  public static InboxRecipient create(HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();


    if (!parameters.containsKey("inbox_id") || parameters.get("inbox_id") == null) {
      throw new NullPointerException("Parameter missing: inbox_id parameters[\"inbox_id\"]");
    }
    if (!parameters.containsKey("recipient") || parameters.get("recipient") == null) {
      throw new NullPointerException("Parameter missing: recipient parameters[\"recipient\"]");
    }

    if (parameters.containsKey("inbox_id") && !(parameters.get("inbox_id") instanceof Long || parameters.get("inbox_id") instanceof Integer)) {
      throw new IllegalArgumentException("Bad parameter: inbox_id must be of type Long or Integer parameters[\"inbox_id\"]");
    }
    if (parameters.containsKey("recipient") && !(parameters.get("recipient") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: recipient must be of type String parameters[\"recipient\"]");
    }
    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }
    if (parameters.containsKey("company") && !(parameters.get("company") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: company must be of type String parameters[\"company\"]");
    }
    if (parameters.containsKey("note") && !(parameters.get("note") instanceof String)) {
      throw new IllegalArgumentException("Bad parameter: note must be of type String parameters[\"note\"]");
    }
    if (parameters.containsKey("share_after_create") && !(parameters.get("share_after_create") instanceof Boolean)) {
      throw new IllegalArgumentException("Bad parameter: share_after_create must be of type Boolean parameters[\"share_after_create\"]");
    }


    String url = String.format("%s%s/inbox_recipients", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());

    TypeReference<InboxRecipient> typeReference = new TypeReference<InboxRecipient>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}
