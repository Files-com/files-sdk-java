package com.files;

import com.files.exceptions.ApiErrorException;
import com.files.models.File;
import com.files.models.Session;
import com.files.models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FilesFileTest {
  private static final String apiKey = "mock-server-api-key";

  @Before
  public void setApiRoot() {
    FilesClient.setProperty("apiRoot", "http://files-mock-server:4041");
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void getFileStream() throws IOException {
    FilesClient.apiKey = "...";
    File file = File.download("test.txt", null);
    try(InputStream inputStream = file.getInputStream()) {
      String text = new BufferedReader(
          new InputStreamReader(inputStream, StandardCharsets.UTF_8))
          .lines()
          .collect(Collectors.joining("\n"));
      System.out.println(text);
    }
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void saveLocalFile() throws IOException {
    FilesClient.apiKey = "...";
    File file = File.download("test.txt", null);
    file.saveAsLocalFile("/tmp/");
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void putOutputStream() throws IOException {
    FilesClient.apiKey = "...";
    String exampleString = "Sample Test Data";
    File file = File.create("test.txt", null);
    InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

  }
}