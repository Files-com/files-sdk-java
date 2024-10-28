package com.files;

import com.files.models.File;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class FilesFileTest {
  private static final String apiKey = "mock-server-api-key";

  @Before
  public void setApiRoot() {
    FilesClient.apiKey = apiKey;
    FilesClient.setProperty("apiRoot", TestSettingsHelper.getInstance().getMockServerApiRoot());
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void getFileStream() throws IOException {
    File file = File.download("test.txt", null);
    try (InputStream inputStream = file.getInputStream()) {
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
    File file = File.download("test.txt", null);
    file.saveAsLocalFile("/tmp/");
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void putInputStream() throws IOException, InterruptedException {
    String exampleString = "Sample Test Data";
    InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes());
    File transferred = File.create("testUpload.txt", null).putInputStream(inputStream, new Date());
    assert(transferred.getSize() == exampleString.getBytes().length);
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void putBufferedInputStream() throws IOException {
    String exampleString = "Sample Test Data";
    InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes());
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
    File transferred = File.create("testUpload2.txt", null).putBufferedInputStream(bufferedInputStream, exampleString.getBytes().length, new Date());
    assert(transferred.getSize() == exampleString.getBytes().length);
  }

  @Test
  @Ignore // Not currently working with files-mock-server
  public void putFile() throws IOException {
    File transferred = File.create("README-test.md", null).putLocalFile("README.md");
    assert(transferred.getSize() == new java.io.File("README.md").getAbsoluteFile().length());
  }
}
