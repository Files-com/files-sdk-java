package com.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

public class ResponseError {
  public String detail;
  public String error;
  public String[] errors;
  @JsonProperty("http-code")
  public int httpCode;
  public String instance;
  @JsonProperty("model_errors")
  public HashMap<String, String[]> modelErrors;
  public String title;
  public String type;
}
