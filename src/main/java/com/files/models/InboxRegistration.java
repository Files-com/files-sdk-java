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

public class InboxRegistration {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public InboxRegistration() {
    this(null, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public InboxRegistration(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Registration cookie code
  */
  @Getter
  @JsonProperty("code")
  private String code;

  /**
  * Registrant name
  */
  @Getter
  @JsonProperty("name")
  private String name;

  /**
  * Registrant company name
  */
  @Getter
  @JsonProperty("company")
  private String company;

  /**
  * Registrant email address
  */
  @Getter
  @JsonProperty("email")
  private String email;

  /**
  * Id of associated form field set
  */
  @Getter
  @JsonProperty("form_field_set_id")
  private Long formFieldSetId;

  /**
  * Data for form field set with form field ids as keys and user data as values
  */
  @Getter
  @JsonProperty("form_field_data")
  private String formFieldData;



}


