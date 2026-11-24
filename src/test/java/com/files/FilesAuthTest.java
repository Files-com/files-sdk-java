package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.files.exceptions.ApiErrorException;
import com.files.models.File;
import com.files.models.Folder;
import com.files.models.Session;
import com.files.models.User;
import java.io.IOException;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FilesAuthTest {
  private static final String apiKey = "mock-server-api-key";

  @Before
  public void setApiRoot() {
    FilesClient.setProperty("apiRoot", TestSettingsHelper.getInstance().getMockServerApiRoot());
  }

  @Test
  public void listAllUsers() throws Exception {
    // Setting API Key
    FilesClient.apiKey = apiKey;
    // Requesting all users
    ListIterator<User> allUsers = User.all();
    assertEquals(1, allUsers.all().size());
    int count = 0;
    // all() leaves the listing at its end, so start it over before iterating again.
    for (User user : allUsers.reset().listAutoPaging()) {
      count++;
    }
    assertEquals(1, count);
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
    assertEquals(1, items.all().size());
    int count = 0;
    // all() leaves the listing at its end, so start it over before iterating again.
    for (File item : items.reset().listAutoPaging()) {
      count++;
    }
    assertEquals(1, count);
  }

  @Test
  public void findUser() throws Exception {
    User user = User.find((long) 1, null);
    assertEquals("user", user.username);
    assertEquals("john.doe@files.com", user.email);
  }

  @Test
  public void updateUser() throws Exception {
    User user = User.find((long) 1, null);
    assertEquals("user", user.username);
    assertEquals("john.doe@files.com", user.email);
    // Set and Save pattern
    user.setUsername("different");
    user.save();
    // Params pattern
    HashMap<String, Object> data = new HashMap<>();
    data.put("id", user.id);
    data.put("username", "alsoDifferent");
    User.update(data, null);
    // Mock server doesn't support updates yet, above code tests that the method invocation itself does not throw Exception
  }

  @Test
  public void resendWelcomeEmailForUser() throws Exception {
    User user = User.find((long) 1, null);
    assertEquals("user", user.username);
    assertEquals(Long.valueOf(1), user.id);
    user.resendWelcomeEmail(null);
    // Mock server should return 200 OK
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
    assertEquals(1, allUsers.all().size());
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
    assertEquals(1, allUsers.all().size());
  }

  @Test
  public void noApiKey() throws IOException {
    try {
      ListIterator<User> allUsers = User.all(null, null);
    } catch (RuntimeException e) {
      if (e instanceof ApiErrorException.AuthenticationException) {
        assertTrue(e instanceof ApiErrorException.AuthenticationException);
      } else {
        throw e;
      }
    }
  }
}
