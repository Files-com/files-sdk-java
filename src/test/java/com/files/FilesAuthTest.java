package com.files;

import com.files.exceptions.ApiErrorException;
import com.files.models.File;
import com.files.models.Folder;
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
  public void listAllUsers() throws Exception {
    // Setting API Key
    FilesClient.apiKey = apiKey;
    // Requesting all userss
    ListIterator<User> allUsers = User.all();
    assert(allUsers.all().size() == 1);
    int count = 0;
    for (User user : allUsers.listAutoPaging()) {
      count++;
    }
    assert(count == 1);
  }

  @Test
  public void listFolder() throws Exception {
    // Setting API Key
    FilesClient.apiKey = apiKey;

    HashMap<String, Object> parameters = new HashMap<>();
    HashMap<String, String> sortBy = new HashMap<>();
    sortBy.put("path", "asc");
    parameters.put("sort_by", sortBy);
    parameters.put("preview_size", "large");
    ListIterator<File> items = Folder.listFor("test", parameters);
    assert(items.all().size() == 1);
    int count = 0;
    for (File item : items.listAutoPaging()) {
      count++;
    }
    assert(count == 1);
  }

  @Test
  public void findUser() throws Exception {
    User user = User.find((long)1, null);
    assert("user".equals(user.username));
    assert("example".equals(user.email));
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
  public void apiKeyInOptionsTest() throws Exception {
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("api_key", apiKey);
    ListIterator<User> allUsers = User.all(null, parameters);
    assert(allUsers.all().size() == 1);
  }

  @Test
  @Ignore // Doesn't work with mock-sever at this time due to session bug.
  public void sessionInOptionsTest() throws Exception {
    HashMap<String, Object> sessionParameters = new HashMap<>();
    sessionParameters.put("username", "test-user");
    sessionParameters.put("password", "test-pass");
    Session session = Session.create(sessionParameters);
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("session_id", session.getId());
    ListIterator<User> allUsers = User.all(null, parameters);
    assert(allUsers.all().size() == 1);
  }

  @Test
  public void noApiKey() throws IOException {
    try {
      ListIterator<User> allUsers = User.all(null, null);
    } catch (RuntimeException e) {
      if (e instanceof ApiErrorException.AuthenticationException) {
        assert (e instanceof ApiErrorException.AuthenticationException);
      } else {
        throw e;
      }
    }
  }
}
