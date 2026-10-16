package com.files;

import static org.junit.Assert.assertTrue;

import com.files.net.ClientRuntime;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class DecimalSerializationTest {

  @Test
  public void serializeParameters_writes_bigdecimal_as_string_and_double_as_number() throws Exception {
    Method method = ClientRuntime.class.getDeclaredMethod("serialize", Map.class);
    method.setAccessible(true);

    HashMap<String, Object> params = new HashMap<>();
    params.put("amount", new BigDecimal("1.23"));
    params.put("ratio", 1.23d);

    String json = (String) method.invoke(null, params);

    assertTrue("Expected BigDecimal to serialize as JSON string: " + json, json.contains("\"amount\":\"1.23\""));
    assertTrue("Expected double to serialize as JSON number: " + json, json.contains("\"ratio\":1.23"));
  }
}
