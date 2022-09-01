package com.files.models;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class ActionWebhookFailure {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = JsonMapper
    .builder()
    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
    .build();

  public ActionWebhookFailure() {
    this(null, null);
  }

  public ActionWebhookFailure(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public ActionWebhookFailure(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * retry Action Webhook Failure
  */
  public ActionWebhookFailure retry(HashMap<String, Object> parameters) {
    return retry(parameters);
  }



  /**
  * retry Action Webhook Failure
  */
  public static ActionWebhookFailure retry() throws IOException{
    return retry(null, null,null);
  }
  public static ActionWebhookFailure retry(Long id,  HashMap<String, Object> parameters) throws IOException {
    return retry(id, parameters, null);
  }

  public static ActionWebhookFailure retry(HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    return retry(null, parameters, options);
  }

  public static ActionWebhookFailure retry(Long id,  HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (id != null){
      parameters.put("id",id);
    }
    if (parameters.containsKey("id") && !(parameters.get("id") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: id must be of type Long parameters[\"id\"]");
    }

    if (!parameters.containsKey("id") || parameters.get("id") == null) {
      throw new NullPointerException("Parameter missing: id parameters[\"id\"]");
    }
    String url = String.format("%s%s/action_webhook_failures/%s/retry", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase(), id);
    TypeReference<ActionWebhookFailure> typeReference = new TypeReference<ActionWebhookFailure>() {};
    return FilesClient.requestItem(url, RequestMethods.POST, typeReference, parameters, options);
  }


}


