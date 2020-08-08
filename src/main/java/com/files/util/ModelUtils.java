package com.files.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class ModelUtils {
  public static HashMap<String, Object> toParameterMap(String object) {
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
    HashMap<String, Object> parameters;
    try {
      parameters = mapper.readValue(object, typeRef);
    } catch (JsonProcessingException e) {
      parameters = null;
    }
    return parameters;
  }
}
