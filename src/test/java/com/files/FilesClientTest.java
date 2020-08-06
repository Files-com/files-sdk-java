package com.files;

import com.files.models.User;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class FilesClientTest {

  @Test
  public void listAllUsers() throws IOException {
    FilesClient.setProperty("apiRoot", "http://files-mock-server:4041");
    // Setting API Key
    FilesClient.apiKey = "mock-server-api-key";
    // Requesting all users
    List<User> allUsers = User.all();
    assert(allUsers.size() == 1);
  }
}
