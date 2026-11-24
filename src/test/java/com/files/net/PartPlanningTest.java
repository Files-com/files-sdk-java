package com.files.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.files.FilesClient;
import com.files.models.FileUploadPart;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import org.junit.Test;

// How an upload is divided into parts, by the real planners, from Files.com's metadata alone: no file
// of the sizes planned is made, and no part is sent.
public class PartPlanningTest {
  private static final ObjectMapper JSON = new ObjectMapper();

  @Test
  public void aKnownSizeAboveOneTebibyteAndAtFiveTebibytesIsPlannedInPartsWithinTheLimits() {
    // S3's smallest part but the last, and the largest part this SDK plans.
    final long smallestPart = 5L * 1024 * 1024;
    final long largestPart = 1024L * 1024 * 1024;
    for (long total : new long[]{(1L << 40) + 1, 5L << 40}) {
      PartSizes.Plan plan = PartSizes.plan(total, -1, 0, PartSizing.automatic(), -1, "huge.bin");
      assertNull(total + " bytes", plan.fallback);
      List<Long> lengths = lengths(plan.sizes, total);
      assertTrue(total + " bytes in " + lengths.size() + " parts", lengths.size() <= 10_000);
      for (int i = 0; i < lengths.size(); i++) {
        long length = lengths.get(i);
        String what = total + " bytes: part " + (i + 1) + " of " + length + " bytes";
        assertTrue(what, length <= largestPart);
        assertTrue(what, i == lengths.size() - 1 || length >= smallestPart);
      }
    }

    // Files.com allows 5120 parts: 5 TiB fits them only in parts of exactly 1 GiB. With one part fewer,
    // the SDK leaves the sizes to Files.com rather than choose its own; the upload is not refused.
    PartSizes.Plan fits = PartSizes.plan(5L << 40, -1, 5120, PartSizing.automatic(), -1, "huge.bin");
    assertNull(fits.fallback);
    List<Long> lengths = lengths(fits.sizes, 5L << 40);
    assertEquals(5120, lengths.size());
    for (long length : lengths) {
      assertEquals(largestPart, length);
    }
    PartSizes.Plan fewer = PartSizes.plan(5L << 40, -1, 5119, PartSizing.automatic(), -1, "huge.bin");
    assertNull(fewer.sizes);
    assertEquals(PartSizes.Fallback.PART_LIMIT, fewer.fallback);
  }

  @Test(timeout = 30000)
  public void aServerSizedUploadIsPlannedPastTenThousandPartsWhenFilesComAdvertisesNoLimit() throws Exception {
    // Files.com plans parts of 5 MiB and advertises no most parts. Its URLs are not an S3 multipart
    // upload's, so the SDK keeps Files.com's sizes. The planner asks for at most 100 parts at once.
    final long partSize = 5L * 1024 * 1024;
    final long total = 10_001L * partSize;
    try (BeginUploadService service = new BeginUploadService();
         FilesClient client = client(service.root());
         ClientRuntime runtime = runtime(client, service.root());
         OperationScope scope = runtime.open("planning", new HashMap<String, Object>())) {
      service.parts = number -> serverPart(service.root(), number, partSize);
      UploadSession session = new UploadSession(runtime, "big.bin", new HashMap<String, Object>(), null, 0);
      PartPlanner planner = new PartPlanner(session, total, () -> 100, PartSizing.automatic(), -1, -1);

      for (long number = 1; number <= 10_001; number++) {
        PartPlanner.Planned planned = planner.next(scope);
        assertEquals(number, planned.number);
        assertEquals((number - 1) * partSize, planned.offset);
        assertEquals(partSize, planned.maxLength);
        planner.commit(planned, planned.maxLength, false);
      }
      assertNull("the whole file is planned", planner.next(scope));
      assertEquals(10_001, planner.partCount());
      assertEquals(total, planner.plannedBytes());
      assertEquals(PartSizeSource.SERVER, planner.sizeSource());
      assertFalse(planner.sizesChosen());

      // One request starts the upload with its first part; each later one asks for the next 100.
      List<JsonNode> requests = new ArrayList<>(service.requests);
      assertEquals(101, requests.size());
      assertFalse(requests.get(0).has("ref"));
      assertEquals(total, requests.get(0).get("size").asLong());
      for (int k = 1; k < requests.size(); k++) {
        JsonNode request = requests.get(k);
        assertEquals("put-1", request.get("ref").asText());
        assertEquals(2 + 100L * (k - 1), request.get("part").asLong());
        assertEquals(100, request.get("parts").asLong());
      }
      assertTrue("no part is sent, and nothing else asked for: " + service.others, service.others.isEmpty());
    }
  }

  @Test(timeout = 30000)
  public void completeDirectConnectionInfoDecidesTheDestinationAndAnythingLessKeepsTheS3Grant() throws Exception {
    ObjectNode complete = JSON.createObjectNode();
    complete.put("version", 1);
    complete.put("server_name", "agent.example.com");
    complete.putArray("addresses").add("192.0.2.10:443");
    complete.put("direct_uri", "https://agent.example.com/transfer?signature=example");
    complete.put("ca_pem", "-----BEGIN CERTIFICATE-----\nexample\n-----END CERTIFICATE-----\n");
    // The first answer is an S3 multipart upload's either way. Complete version 1 direct connection
    // information makes the destination a Files.com agent; anything less leaves it S3. Whether the
    // SDK may choose part sizes is separate: only when there is no direct connection information.
    Map<String, ObjectNode> rows = new LinkedHashMap<>();
    rows.put("complete, version 1", complete);
    rows.put("none", null);
    for (String field : new String[]{"version", "server_name", "addresses", "direct_uri", "ca_pem"}) {
      ObjectNode without = complete.deepCopy();
      without.remove(field);
      rows.put("without " + field, without);
    }
    ObjectNode version2 = complete.deepCopy();
    version2.put("version", 2);
    rows.put("version 2", version2);

    try (BeginUploadService service = new BeginUploadService();
         FilesClient client = client(service.root());
         ClientRuntime runtime = runtime(client, service.root());
         OperationScope scope = runtime.open("planning", new HashMap<String, Object>())) {
      for (Map.Entry<String, ObjectNode> row : rows.entrySet()) {
        service.parts = number -> {
          ObjectNode part = s3Part(service.root(), number);
          if (row.getValue() != null) {
            part.set("direct_connection_info", row.getValue());
          }
          return part;
        };
        List<FileUploadPart> first = new UploadSession(runtime, "agent.bin", new HashMap<String, Object>(), null, 0).begin(1000, scope);
        UploadGrant grant = UploadGrant.of(first, 1);

        boolean direct = row.getValue() == complete;
        assertEquals(row.getKey(), direct ? UploadDestination.FILES_AGENT : UploadDestination.S3, grant.destination());
        assertEquals(row.getKey(), row.getValue() == null, grant.permitsChosenSizes());
      }
      assertEquals("one request for each row", rows.size(), service.requests.size());
      assertTrue("nothing else asked for: " + service.others, service.others.isEmpty());
    }
  }

  // The lengths the plan gives the parts of a file of total bytes, in order, until they hold all of it.
  private static List<Long> lengths(PartSizes sizes, long total) {
    List<Long> lengths = new ArrayList<>();
    long offset = 0;
    while (offset < total) {
      assertTrue("no more than 10000 parts", lengths.size() < 10_000);
      long length = sizes.lengthFor(lengths.size() + 1, offset, sizes.choice());
      assertTrue("part " + (lengths.size() + 1) + " of " + length + " bytes", length > 0);
      lengths.add(length);
      offset += length;
    }
    assertEquals("the parts hold exactly the file", total, offset);
    return lengths;
  }

  private static FilesClient client(String root) {
    return FilesClient.builder().apiKey("planning-key").apiRoot(root).build();
  }

  // A runtime as a client has one, with a transport of its own, sending to the loopback service. It
  // retries nothing, so a refused request fails at once.
  private static ClientRuntime runtime(FilesClient client, String root) {
    ClientRuntime.Settings settings = new ClientRuntime.Settings.Builder("PartPlanningTest")
        .apiKey("planning-key")
        .apiRoot(root)
        .maxRetries(0)
        .build();
    return new ClientRuntime(client, settings, HttpTransport.builder().build(), true, false);
  }

  // A part as Files.com's begin_upload describes one, to be sent with PUT to uploadUri, in parallel with
  // the others and retried as needed.
  private static ObjectNode part(long number, String uploadUri) {
    ObjectNode part = JSON.createObjectNode();
    ObjectNode send = part.putObject("send");
    send.put("partsize", "required-header Content-Length");
    send.put("partdata", "body");
    part.put("action", "put/write");
    part.put("ask_about_overwrites", false);
    part.put("expires", "2099-01-01T00:00:00Z");
    part.putObject("headers");
    part.put("http_method", "PUT");
    part.put("parallel_parts", true);
    part.put("retry_parts", true);
    part.putObject("parameters");
    part.put("part_number", number);
    part.put("ref", "put-1");
    part.put("upload_uri", uploadUri);
    return part;
  }

  // A part whose size Files.com plans, with no most parts, at a URL that is not an S3 multipart upload's.
  private static ObjectNode serverPart(String root, long number, long size) {
    ObjectNode part = part(number, root + "/storage/put-1/" + number + "?X-Amz-Signature=STORAGE-SECRET");
    part.put("partsize", size);
    part.put("next_partsize", size);
    return part;
  }

  // A part of an S3 multipart upload, as the S3 grant of the transfer tests gives it.
  private static ObjectNode s3Part(String root, long number) {
    ObjectNode part = part(number, root + "/storage/put-1/object?partNumber=" + number + "&uploadId=UPLOAD-1"
        + "&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIDEXAMPLE%2F20261014%2Fus-east-1%2Fs3%2Faws4_request"
        + "&X-Amz-SignedHeaders=host&X-Amz-Signature=STORAGE-SECRET");
    part.put("available_parts", 10000);
    part.put("partsize", 5);
    return part;
  }

  // Files.com's begin_upload on the loopback interface: it keeps each request and answers with the parts
  // it asks for, as parts describes them. It keeps any other request, and refuses it.
  private static final class BeginUploadService implements AutoCloseable {
    final List<JsonNode> requests = new CopyOnWriteArrayList<>();
    final List<String> others = new CopyOnWriteArrayList<>();
    volatile Function<Long, ObjectNode> parts;
    private final HttpServer server;

    BeginUploadService() throws IOException {
      server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
      server.createContext("/api/rest/v1/file_actions/begin_upload/", this::begin);
      server.createContext("/", this::other);
      server.start();
    }

    String root() {
      return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    private void begin(HttpExchange exchange) throws IOException {
      JsonNode request = JSON.readTree(readAll(exchange.getRequestBody()));
      requests.add(request);
      long first = request.hasNonNull("part") ? request.get("part").asLong() : 1;
      long count = request.hasNonNull("parts") ? request.get("parts").asLong() : 1;
      ArrayNode answer = JSON.createArrayNode();
      for (long number = first; number < first + count; number++) {
        answer.add(parts.apply(number));
      }
      respond(exchange, 200, JSON.writeValueAsBytes(answer));
    }

    private void other(HttpExchange exchange) throws IOException {
      others.add(exchange.getRequestMethod() + " " + exchange.getRequestURI());
      readAll(exchange.getRequestBody());
      respond(exchange, 404, new byte[0]);
    }

    @Override
    public void close() {
      server.stop(0);
    }
  }

  private static void respond(HttpExchange exchange, int status, byte[] body) throws IOException {
    exchange.getResponseHeaders().add("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, body.length == 0 ? -1 : body.length);
    try (OutputStream out = exchange.getResponseBody()) {
      if (body.length > 0) {
        out.write(body);
      }
    }
  }

  private static byte[] readAll(InputStream in) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[8192];
    int read;
    while ((read = in.read(buffer)) >= 0) {
      out.write(buffer, 0, read);
    }
    return out.toByteArray();
  }
}
