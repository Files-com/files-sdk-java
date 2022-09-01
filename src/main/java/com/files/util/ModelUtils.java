package com.files.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class ModelUtils {
  public static HashMap<String, Object> toParameterMap(String object) {
    ObjectMapper mapper = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .build();
    
    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
    HashMap<String, Object> parameters;
    try {
      parameters = mapper.readValue(object, typeRef);
    } catch (JsonProcessingException e) {
      parameters = null;
    }
    return parameters;
  }

  public static String forceMandatoryUriEncode(String uri) {
    return uri.replaceAll(";", "%3B");
  }
}
