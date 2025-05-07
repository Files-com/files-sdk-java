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
public class FormField implements ModelInterface {
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


  public FormField() {
    this(null, null);
  }

  public FormField(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FormField(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try {
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
  * Form field id
  */
  @JsonProperty("id")
  public Long id;

  public Long getId() {
    return id;
  }

  /**
  * Label to be displayed
  */
  @JsonProperty("label")
  public String label;

  public String getLabel() {
    return label;
  }

  /**
  * Is this a required field?
  */
  @JsonProperty("required")
  public Boolean required;

  public Boolean getRequired() {
    return required;
  }

  /**
  * Help text to be displayed
  */
  @JsonProperty("help_text")
  public String helpText;

  public String getHelpText() {
    return helpText;
  }

  /**
  * Type of Field
  */
  @JsonProperty("field_type")
  public String fieldType;

  public String getFieldType() {
    return fieldType;
  }

  /**
  * Options to display for radio and dropdown
  */
  @JsonProperty("options_for_select")
  public String[] optionsForSelect;

  public String[] getOptionsForSelect() {
    return optionsForSelect;
  }

  /**
  * Default option for radio and dropdown
  */
  @JsonProperty("default_option")
  public String defaultOption;

  public String getDefaultOption() {
    return defaultOption;
  }

  /**
  * Form field set id
  */
  @JsonProperty("form_field_set_id")
  public Long formFieldSetId;

  public Long getFormFieldSetId() {
    return formFieldSetId;
  }


}
