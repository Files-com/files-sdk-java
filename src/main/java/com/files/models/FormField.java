package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class FormField {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public FormField() {
    this(null, null);
  }

  public FormField(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FormField(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Form field id
  */
  @Getter
  @JsonProperty("id")
  private Long id;

  /**
  * Label to be displayed
  */
  @Getter
  @JsonProperty("label")
  private String label;

  /**
  * Is this a required field?
  */
  @Getter
  @JsonProperty("required")
  private Boolean required;

  /**
  * Help text to be displayed
  */
  @Getter
  @JsonProperty("help_text")
  private String helpText;

  /**
  * Type of Field
  */
  @Getter
  @JsonProperty("field_type")
  private String fieldType;

  /**
  * Options to display for radio and dropdown
  */
  @Getter
  @JsonProperty("options_for_select")
  private String optionsForSelect;

  /**
  * Default option for radio and dropdown
  */
  @Getter
  @JsonProperty("default_option")
  private String defaultOption;

  /**
  * Form field set id
  */
  @Getter
  @JsonProperty("form_field_set_id")
  private Long formFieldSetId;



}


