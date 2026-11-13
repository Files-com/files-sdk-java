package com.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.files.exceptions.ApiErrorException;
import com.files.net.DownloadOptions;
import com.files.net.DownloadResult;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FilesDownloadDateTest {
  private static final String BASELINE = "Wed, 21 Oct 2015 07:28:00 GMT";
  private static final String NEWER = "Wed, 21 Oct 2015 07:28:01 GMT";
  private static final String CONTENTS = "0123456789";

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private FilesClient client(FakeFiles files) {
    return FilesClient.builder().apiKey("date-test-key").apiRoot(files.root()).retryDelay(Duration.ofMillis(1)).build();
  }

  private DownloadOptions parts() {
    return DownloadOptions.builder().partSize(4).build();
  }

  private void firstWithoutVersion(FakeFiles files, String date) {
    files.contents = CONTENTS.getBytes(StandardCharsets.UTF_8);
    files.describedSize = (long) files.contents.length;
    files.ranges = true;
    files.rangeAnswer = (start, attempt) -> {
      FakeFiles.Answer answer = FakeFiles.Answer.status(206, CONTENTS.substring(0, 4)).header("Content-Range", "bytes 0-3/10");
      return date == null ? answer : answer.header("Last-Modified", date);
    };
  }

  @Test(timeout = 20000)
  public void aDateSurvivesTheWholeResponseFallbackAndUrlRenewal() throws Exception {
    try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
      firstWithoutVersion(files, BASELINE);
      files.download = attempt -> attempt == 1
          ? FakeFiles.Answer.status(403, "<Error><Code>ExpiredToken</Code></Error>")
          : FakeFiles.Answer.status(200, CONTENTS).header("Last-Modified", BASELINE);
      Path destination = folder.getRoot().toPath().resolve("renewed.bin");

      DownloadResult result = client.transfers().download("remote.bin", destination, parts());

      assertEquals(CONTENTS, new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
      assertFalse(result.isVersionBound());
      assertEquals(1, files.rangeRequests.size());
      assertEquals(2, files.downloads.size());
      assertEquals(2, files.describes.size());
      for (FakeFiles.Received request : files.downloads) {
        assertEquals(BASELINE, request.header("If-Unmodified-Since"));
        assertNull(request.header("If-Match"));
      }
    }
  }

  @Test(timeout = 20000)
  public void aNewerSameSizeFileIsNeverPublishedEvenWhenTheConditionIsIgnored() throws Exception {
    for (boolean obeys : new boolean[]{true, false}) {
      try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
        firstWithoutVersion(files, BASELINE);
        files.download = attempt -> obeys && BASELINE.equals(files.downloads.get(files.downloads.size() - 1).header("If-Unmodified-Since"))
            ? FakeFiles.Answer.status(412, "")
            : FakeFiles.Answer.status(200, "9876543210").header("Last-Modified", NEWER);
        Path destination = folder.getRoot().toPath().resolve("changed-" + obeys + ".bin");
        Files.write(destination, "old".getBytes(StandardCharsets.UTF_8));

        try {
          client.transfers().download("remote.bin", destination, parts());
          fail("A newer source must stop the download");
        } catch (ApiErrorException.ApiConnectionException e) {
          assertTrue(e.getMessage(), e.getMessage().contains("changed while it was being downloaded"));
        }
        assertEquals("old", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
        assertEquals(1, files.downloads.size());
      }
    }
  }

  @Test(timeout = 20000)
  public void absentOrInvalidDatesKeepWholeResponseCompatibility() throws Exception {
    for (String date : new String[]{null, "not an HTTP date", BASELINE + " garbage", "Wed, 32 Oct 2015 07:28:00 GMT",
        "Wednesday, 21-Oct-15 07:28:00 GMT garbage", "Wednesday, 32-Oct-15 07:28:00 GMT"}) {
      try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
        firstWithoutVersion(files, date);
        files.download = attempt -> FakeFiles.Answer.status(200, CONTENTS).header("Last-Modified", BASELINE);
        Path destination = folder.getRoot().toPath().resolve("compatible-" + (date == null ? 0 : date.hashCode()) + ".bin");
        DownloadResult result = client.transfers().download("remote.bin", destination, parts());
        assertFalse(result.isVersionBound());
        assertEquals(CONTENTS, new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
        assertNull(files.downloads.get(0).header("If-Unmodified-Since"));
      }
    }
  }

  @Test(timeout = 20000)
  public void clientsCanDisableDateChecksAndOneDownloadCanOverrideThem() throws Exception {
    Boolean[][] settings = {{false, null}, {true, false}, {false, true}};
    for (int i = 0; i < settings.length; i++) {
      boolean clientSetting = settings[i][0];
      Boolean override = settings[i][1];
      boolean enabled = override != null ? override : clientSetting;
      try (FakeFiles files = new FakeFiles(); FilesClient client = FilesClient.builder().apiKey("date-test-key").apiRoot(files.root())
               .checkDownloadModificationTime(clientSetting).build()) {
        firstWithoutVersion(files, BASELINE);
        files.download = attempt -> FakeFiles.Answer.status(200, "9876543210").header("Last-Modified", NEWER);
        DownloadOptions.Builder options = DownloadOptions.builder().partSize(4);
        if (override != null) {
          options.checkDownloadModificationTime(override);
        }
        Path destination = folder.getRoot().toPath().resolve("optout-" + i + ".bin");
        if (enabled) {
          try {
            client.transfers().download("remote.bin", destination, options.build());
            fail("An enabled per-download override must keep the source-change check");
          } catch (ApiErrorException.ApiConnectionException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("changed while it was being downloaded"));
          }
          assertFalse(Files.exists(destination));
        } else {
          client.transfers().download("remote.bin", destination, options.build());
          assertEquals("9876543210", new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
        }
        assertEquals(enabled ? BASELINE : null, files.downloads.get(0).header("If-Unmodified-Since"));
        for (FakeFiles.Received api : files.describes) {
          assertFalse(api.rawPath, api.rawPath.contains("check_download_modification_time"));
          assertNull(api.header("check_download_modification_time"));
        }
      }
    }
  }

  @Test(timeout = 20000)
  public void aLocalOptionIsCopiedBeforeTheDownloadBeginsAndDoesNotDisableStrongChecks() throws Exception {
    try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
      firstWithoutVersion(files, BASELINE);
      HashMap<String, Object> overrides = new HashMap<>();
      overrides.put("check_download_modification_time", false);
      DownloadOptions options = DownloadOptions.builder().partSize(4).requestOptions(overrides).build();
      overrides.put("check_download_modification_time", true);
      files.download = attempt -> FakeFiles.Answer.status(200, CONTENTS).header("Last-Modified", NEWER);
      Path destination = folder.getRoot().toPath().resolve("copied-option.bin");
      client.transfers().download("remote.bin", destination, options);
      assertNull(files.downloads.get(0).header("If-Unmodified-Since"));
      files.rangeRequests.clear();
      files.rangeAnswer = (start, attempt) -> FakeFiles.Answer.status(206, CONTENTS.substring((int) start, (int) Math.min(start + 4, 10)))
          .header("Content-Range", "bytes " + start + "-" + (Math.min(start + 4, 10) - 1) + "/10")
          .header("ETag", start == 0 ? "\"v1\"" : "\"v2\"");
      Path changed = folder.getRoot().toPath().resolve("strong-optout.bin");
      try {
        client.transfers().download("remote.bin", changed, options);
        fail("Opting out of dates must keep strong version checks");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertTrue(e.getMessage(), e.getMessage().contains("changed while it was being downloaded"));
      }
      assertFalse(Files.exists(changed));
      assertEquals("\"v1\"", files.rangeRequests.get(1).header("If-Match"));
      assertNull(files.rangeRequests.get(1).header("If-Unmodified-Since"));
    }
  }

  @Test(timeout = 20000)
  public void validHttpDateFormsAreNormalizedForLaterRequests() throws Exception {
    String[][] dates = {
        {BASELINE, BASELINE},
        {"Wednesday, 21-Oct-15 07:28:00 GMT", BASELINE},
        {"Wed Oct 21 07:28:00 2015", BASELINE},
        {"Sunday, 06-Nov-94 08:49:37 GMT", "Sun, 06 Nov 1994 08:49:37 GMT"}
    };
    for (int i = 0; i < dates.length; i++) {
      String initial = dates[i][0];
      String canonical = dates[i][1];
      try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
        firstWithoutVersion(files, initial);
        files.download = attempt -> FakeFiles.Answer.status(200, CONTENTS).header("Last-Modified", canonical);
        Path destination = folder.getRoot().toPath().resolve("http-date-" + i + ".bin");
        client.transfers().download("remote.bin", destination, parts());
        assertEquals(CONTENTS, new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
        assertEquals(initial, canonical, files.downloads.get(0).header("If-Unmodified-Since"));
      }
    }
  }

  @Test(timeout = 20000)
  public void aWholeResponseRetryKeepsTheDateFromItsInterruptedBody() throws Exception {
    try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
      files.contents = CONTENTS.getBytes(StandardCharsets.UTF_8);
      files.describedSize = (long) files.contents.length;
      files.download = attempt -> attempt == 1 ? FakeFiles.Answer.cutShortAfter(2).header("Last-Modified", BASELINE)
          : BASELINE.equals(files.downloads.get(files.downloads.size() - 1).header("If-Unmodified-Since"))
          ? FakeFiles.Answer.status(412, "") : FakeFiles.Answer.status(200, "9876543210");
      Path destination = folder.getRoot().toPath().resolve("retry.bin");
      try {
        client.transfers().download("remote.bin", destination);
        fail("The interrupted response's date must be used on retry");
      } catch (ApiErrorException.ApiConnectionException e) {
        assertTrue(e.getMessage(), e.getMessage().contains("changed while it was being downloaded"));
      }
      assertFalse(Files.exists(destination));
      assertEquals(2, files.downloads.size());
    }
  }

  @Test(timeout = 20000)
  public void anEarlierRetryDateDoesNotReplaceTheOriginalBaseline() throws Exception {
    try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
      files.contents = CONTENTS.getBytes(StandardCharsets.UTF_8);
      files.describedSize = (long) files.contents.length;
      files.download = attempt -> attempt <= 2 ? FakeFiles.Answer.cutShortAfter(2).header("Last-Modified",
          attempt == 1 ? BASELINE : "Wed, 21 Oct 2015 07:27:59 GMT")
          : FakeFiles.Answer.status(200, CONTENTS).header("Last-Modified", BASELINE);
      Path destination = folder.getRoot().toPath().resolve("baseline.bin");
      client.transfers().download("remote.bin", destination);
      assertEquals(CONTENTS, new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
      assertEquals(3, files.downloads.size());
      for (FakeFiles.Received request : files.downloads.subList(1, files.downloads.size())) {
        assertEquals(BASELINE, request.header("If-Unmodified-Since"));
      }
    }
  }

  @Test(timeout = 20000)
  public void strongEntityTagsTakePrecedenceOverDates() throws Exception {
    try (FakeFiles files = new FakeFiles(); FilesClient client = client(files)) {
      files.contents = CONTENTS.getBytes(StandardCharsets.UTF_8);
      files.describedSize = (long) files.contents.length;
      files.ranges = true;
      files.etag = "\"v1\"";
      files.rangeAnswer = (start, attempt) -> FakeFiles.Answer.status(206, CONTENTS.substring((int) start, (int) Math.min(start + 4, 10)))
          .header("Content-Range", "bytes " + start + "-" + (Math.min(start + 4, 10) - 1) + "/10")
          .header("ETag", files.etag).header("Last-Modified", start == 0 ? BASELINE : NEWER);
      Path destination = folder.getRoot().toPath().resolve("strong.bin");
      DownloadResult result = client.transfers().download("remote.bin", destination, parts());
      assertTrue(result.isVersionBound());
      assertEquals(CONTENTS, new String(Files.readAllBytes(destination), StandardCharsets.UTF_8));
      for (FakeFiles.Received request : files.rangeRequests.subList(1, files.rangeRequests.size())) {
        assertEquals(files.etag, request.header("If-Match"));
        assertNull(request.header("If-Unmodified-Since"));
      }
    }
  }
}
