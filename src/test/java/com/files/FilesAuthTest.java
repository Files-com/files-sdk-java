package com.files;

import com.files.exceptions.ApiErrorException;
import com.files.models.File;
import com.files.models.Session;
import com.files.models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FilesAuthTest {
  private static final String apiKey = "mock-server-api-key";
  @Before
  public void setApiRoot() {
    FilesClient.setProperty("apiRoot", "http://files-mock-server:4041");
  }

  @Test
  public void listAllUsers() throws IOException {
    // Setting API Key
    FilesClient.apiKey = apiKey;
    // Requesting all userss
    List<User> allUsers = User.all();
    assert(allUsers.size() == 1);
  }

  @Test
  @Ignore // Doesn't work with mock-sever at this time due to session bug.
  public void session() throws IOException {
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("username", "test-user");
    parameters.put("password", "test-pass");
    Session session = Session.create(parameters);
    System.out.println(session.getId());
  }

  @Test
  public void apiKeyInOptionsTest() throws IOException {
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("api_key", apiKey);
    List<User> allUsers = User.all(null, parameters);
    assert(allUsers.size() == 1);
  }

  @Test
  @Ignore // Doesn't work with mock-sever at this time due to session bug.
  public void sessionInOptionsTest() throws IOException {
    HashMap<String, Object> sessionParameters = new HashMap<>();
    sessionParameters.put("username", "test-user");
    sessionParameters.put("password", "test-pass");
    Session session = Session.create(sessionParameters);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("session_id", session.getId());
    List<User> allUsers = User.all(null, parameters);
    assert(allUsers.size() == 1);
  }

  @Test
  public void noApiKey() throws IOException {
    try {
      List<User> allUsers = User.all(null, null);
    } catch (IOException e) {
      if (e instanceof ApiErrorException.ApiAuthenticationException) {
        assert (e instanceof ApiErrorException.ApiAuthenticationException);
      } else {
        throw e;
      }
    }
  }
}