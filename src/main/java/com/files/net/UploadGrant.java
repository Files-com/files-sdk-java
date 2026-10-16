package com.files.net;

import com.files.exceptions.ApiErrorException;
import com.files.models.DirectConnectionInfo;
import com.files.models.FileUploadPart;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// What one upload may do, as the SDK reads it from the parts Files.com negotiates: where its parts
// go, and whether the SDK may choose their sizes. The two are separate.
//
// Where the parts go comes from what Files.com says about the destination, never from a URL's host:
// complete direct connection information means a Files Agent; anything else is treated as S3,
// Files.com's own buckets and a customer's alike.
//
// Choosing sizes is allowed only where every part is a part of one ordinary S3 multipart upload
// whose signed URL binds neither a part's length nor its bytes, so each part may be sent with
// exactly the bytes the SDK chose:
//
//   action                  put/write: an ordinary write, not an append or a restart
//   http_method             PUT
//   send                    exactly the part's size as its Content-Length, and its bytes as the body
//   headers, parameters     none
//   direct_connection_info  none
//   upload_uri              an S3 UploadPart request signed with SigV4: partNumber, naming this part,
//                           and uploadId once each; X-Amz-Algorithm AWS4-HMAC-SHA256; a credential
//                           scoped to s3; X-Amz-SignedHeaders host alone; a signature; and nothing that
//                           binds a length, digest or checksum
//
// Everything else follows the sizes Files.com plans, which is always supported. The URL is only read
// here; it is sent exactly as given.
final class UploadGrant {
  private static final String[] BINDING_QUERY_NAMES = {
      "x-amz-content-sha256", "x-amz-sdk-checksum-algorithm", "x-amz-decoded-content-length", "content-md5", "content-length"
  };

  private final UploadDestination destination;
  private final S3Upload s3Upload;

  private UploadGrant(UploadDestination destination, S3Upload s3Upload) {
    this.destination = destination;
    this.s3Upload = s3Upload;
  }

  // The grant of the upload's first answer, whose first part is number firstNumber.
  static UploadGrant of(List<FileUploadPart> parts, long firstNumber) {
    for (FileUploadPart part : parts) {
      if (hasDirectConnection(part)) {
        return new UploadGrant(UploadDestination.FILES_AGENT, null);
      }
    }
    S3Upload upload = S3Upload.of(parts.get(0), firstNumber);
    for (int i = 1; i < parts.size() && upload != null; i++) {
      if (!upload.sameUpload(S3Upload.of(parts.get(i), firstNumber + i))) {
        upload = null;
      }
    }
    return new UploadGrant(UploadDestination.S3, upload);
  }

  UploadDestination destination() {
    return destination;
  }

  // Whether the SDK may choose the sizes of the upload's parts.
  boolean permitsChosenSizes() {
    return s3Upload != null;
  }

  // The S3 upload the parts go to, as a digest of its resource and upload ID, for a checkpoint to
  // compare with a later grant; null when the SDK may not choose sizes.
  String uploadIdentity() {
    return s3Upload == null ? null : s3Upload.identity();
  }

  // Checks a later answer, for more parts or a renewed URL, while the upload chooses its part sizes:
  // the part must still belong to the same S3 upload, with nothing binding its length or bytes.
  void checkStillPermitted(FileUploadPart part, long number, String path) {
    if (!s3Upload.sameUpload(S3Upload.of(part, number))) {
      throw new ApiErrorException.ApiConnectionException("For part " + number + " of the upload to " + path
          + ", Files.com gave a URL that is not for the same S3 multipart upload as the upload's other parts, or that fixes the"
          + " part's length or bytes, which the upload chooses, so the upload was stopped before sending the part");
    }
  }

  // Complete direct connection information, as the Go and .NET SDKs judge it: version 1, a server
  // name, at least one address, a direct URI and a CA certificate. The SDK does not connect
  // directly; the information only identifies the destination.
  static boolean hasDirectConnection(FileUploadPart part) {
    return isComplete(part.getDirectConnectionInfo());
  }

  // Whether direct connection information, of an upload's part or a file to download, is complete.
  static boolean isComplete(DirectConnectionInfo info) {
    return info != null && info.getVersion() != null && info.getVersion() == 1
        && notEmpty(info.getServerName()) && info.getAddresses() != null && info.getAddresses().length > 0
        && notEmpty(info.getDirectUri()) && notEmpty(info.getCaPem());
  }

  private static boolean notEmpty(String value) {
    return value != null && !value.isEmpty();
  }

  // One ordinary S3 multipart upload, as a part's answer shows it, or null.
  static final class S3Upload {
    private final String resource;
    private final String uploadId;

    private S3Upload(String resource, String uploadId) {
      this.resource = resource;
      this.uploadId = uploadId;
    }

    boolean sameUpload(S3Upload other) {
      return other != null && resource.equals(other.resource) && uploadId.equals(other.uploadId);
    }

    // The SHA-256 of the resource and upload ID, so that neither is kept.
    String identity() {
      MessageDigest digest = UploadSource.sha256();
      digest.update(resource.getBytes(StandardCharsets.UTF_8));
      digest.update((byte) 0);
      digest.update(uploadId.getBytes(StandardCharsets.UTF_8));
      StringBuilder hex = new StringBuilder("sha256:");
      for (byte b : digest.digest()) {
        hex.append(String.format("%02x", b & 0xff));
      }
      return hex.toString();
    }

    static S3Upload of(FileUploadPart part, long number) {
      if (!"put/write".equals(part.getAction()) || !"PUT".equals(part.getHttpMethod()) || part.getDirectConnectionInfo() != null
          || (part.getPartNumber() != null && part.getPartNumber() != number) || !isEmpty(part.getHeaders())
          || !isEmpty(part.getParameters()) || !isOrdinarySend(part.getSend())) {
        return null;
      }
      String uri = part.getUploadUri();
      if (uri == null || uri.indexOf('#') >= 0) {
        return null;
      }
      int query = uri.indexOf('?');
      if (query <= 0) {
        return null;
      }
      Map<String, List<String>> parameters = new HashMap<String, List<String>>();
      for (String pair : uri.substring(query + 1).split("&", -1)) {
        int equals = pair.indexOf('=');
        if (equals <= 0) {
          return null;
        }
        String name;
        String value;
        try {
          name = URLDecoder.decode(pair.substring(0, equals), "UTF-8");
          value = URLDecoder.decode(pair.substring(equals + 1), "UTF-8");
        } catch (UnsupportedEncodingException | IllegalArgumentException e) {
          return null;
        }
        String lower = name.toLowerCase(Locale.ROOT);
        if (lower.startsWith("x-amz-checksum")) {
          return null;
        }
        for (String binding : BINDING_QUERY_NAMES) {
          if (binding.equals(lower)) {
            return null;
          }
        }
        List<String> values = parameters.get(name);
        if (values == null) {
          values = new ArrayList<String>();
          parameters.put(name, values);
        }
        values.add(value);
      }
      String uploadId = single(parameters, "uploadId");
      String credential = single(parameters, "X-Amz-Credential");
      if (!Long.toString(number).equals(single(parameters, "partNumber")) || uploadId == null || uploadId.isEmpty()
          || !"AWS4-HMAC-SHA256".equals(single(parameters, "X-Amz-Algorithm")) || credential == null
          || !credential.endsWith("/s3/aws4_request") || !"host".equals(single(parameters, "X-Amz-SignedHeaders"))
          || single(parameters, "X-Amz-Signature") == null || single(parameters, "X-Amz-Signature").isEmpty()) {
        return null;
      }
      return new S3Upload(uri.substring(0, query), uploadId);
    }

    private static String single(Map<String, List<String>> parameters, String name) {
      List<String> values = parameters.get(name);
      return values != null && values.size() == 1 ? values.get(0) : null;
    }

    private static boolean isEmpty(Object field) {
      return field == null || (field instanceof Map && ((Map<?, ?>) field).isEmpty());
    }

    // Exactly the part's size as its Content-Length and its bytes as the body.
    private static boolean isOrdinarySend(Object send) {
      if (!(send instanceof Map) || ((Map<?, ?>) send).size() != 2) {
        return false;
      }
      Map<?, ?> instructions = (Map<?, ?>) send;
      return "required-header Content-Length".equalsIgnoreCase(String.valueOf(instructions.get("partsize")))
          && "body".equals(instructions.get("partdata"));
    }
  }
}
