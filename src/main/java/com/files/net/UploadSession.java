package com.files.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.files.exceptions.ApiErrorException;
import com.files.exceptions.SdkException;
import com.files.exceptions.UploadCompletionUnknownException;
import com.files.models.File;
import com.files.models.FileUploadPart;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.UrlUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

// One upload's requests to the Files.com API: its begin_upload negotiations and the request that
// completes it. Every request uses the client and request options the upload started with, and
// the destination path exactly as the caller spelled it. The parts themselves go to the
// negotiated storage URLs, not through here.
final class UploadSession {
  private static final TypeReference<File> FILE = new TypeReference<File>() {};

  private final ClientRuntime runtime;
  private final String path;
  private final HashMap<String, Object> options;
  private final Boolean mkdirParents;
  private final int maxRetries;
  private volatile String ref;

  UploadSession(ClientRuntime runtime, String path, HashMap<String, Object> options, Boolean mkdirParents, int maxRetries) {
    this.runtime = runtime;
    this.path = path;
    this.options = options;
    this.mkdirParents = mkdirParents;
    this.maxRetries = maxRetries;
  }

  String path() {
    return path;
  }

  // The upload's reference, which every later request carries.
  String ref() {
    return ref;
  }

  // Continues an upload that was begun elsewhere, such as with File.beginUpload.
  void resume(String existingRef) {
    this.ref = existingRef;
  }

  // Starts the upload and negotiates its first part. size is -1 when it is not known.
  List<FileUploadPart> begin(long size, OperationScope scope) {
    HashMap<String, Object> parameters = parameters();
    if (size >= 0) {
      parameters.put("size", size);
    }
    List<FileUploadPart> parts = negotiate(parameters, scope);
    String negotiatedRef = parts.get(0).getRef();
    if (negotiatedRef == null || negotiatedRef.isEmpty()) {
      throw new ApiErrorException.ApiConnectionException("Files.com did not start the upload: its response has no upload reference");
    }
    ref = negotiatedRef;
    return parts;
  }

  // Negotiates count parts from firstPart on, or a new URL for a part already planned.
  List<FileUploadPart> next(long firstPart, long count, OperationScope scope) {
    HashMap<String, Object> parameters = parameters();
    parameters.put("ref", ref);
    parameters.put("part", firstPart);
    if (count > 1) {
      parameters.put("parts", count);
    }
    return negotiate(parameters, scope);
  }

  // Every begin_upload request, the first, each continuation and each renewal of a part's URL, asks
  // for direct connection information. The answer includes it when the destination is a Files Agent
  // or a remote server integration, which only identifies the destination: the SDK does not connect
  // directly.
  private List<FileUploadPart> negotiate(HashMap<String, Object> parameters, OperationScope scope) {
    parameters.put("with_direct_connection_info", true);
    FilesResponse response = runtime.send(RequestMethods.POST, "/file_actions/begin_upload/" + UrlUtils.encodeUrlPath(path),
        parameters, options, scope, maxRetries);
    List<FileUploadPart> parts = new ArrayList<FileUploadPart>();
    try {
      JsonNode body = ClientRuntime.MAPPER.readTree(response.getBody());
      if (body != null && body.isArray()) {
        for (JsonNode part : body) {
          parts.add(ClientRuntime.MAPPER.treeToValue(part, FileUploadPart.class));
        }
      } else if (body != null && body.isObject()) {
        parts.add(ClientRuntime.MAPPER.treeToValue(body, FileUploadPart.class));
      }
    } catch (JsonProcessingException | IllegalArgumentException e) {
      throw ClientRuntime.undecodableResponse();
    }
    if (parts.isEmpty() || parts.contains(null)) {
      throw new ApiErrorException.ApiConnectionException("Files.com returned no upload part " + parameters.getOrDefault("part", 1));
    }
    return parts;
  }

  // Completes the upload. parts must be every part, in order. ETags are sent when every part
  // returned one and left out when none did, which destinations that take parts one at a time
  // without ETags allow; a mixture cannot describe the upload, so it is refused before anything is
  // sent.
  //
  // The request is never repeated once it may have reached Files.com: Files.com discards an
  // upload's reference when it completes it, so a repeat after a lost answer would report a
  // completed upload as missing. Only an attempt that failed before the request was sent in full,
  // such as one that could not connect, is retried. A client error in Files.com's own format means
  // it refused the request and completed nothing, and a failure before the request reached
  // Files.com completed nothing either; any other failure, cancellation included, leaves the
  // outcome unknown: UploadCompletionUnknownException, with the failure as its cause.
  File finish(long size, List<UploadedPart> parts, Instant modifiedTime, OperationScope scope) {
    HashMap<String, Object> parameters = parameters();
    parameters.put("action", "end");
    parameters.put("ref", ref);
    parameters.put("size", size);
    if (modifiedTime != null) {
      parameters.put("provided_mtime", modifiedTime.truncatedTo(ChronoUnit.SECONDS).toString());
    }
    List<UploadedPart> withoutETag = new ArrayList<UploadedPart>();
    for (UploadedPart part : parts) {
      if (part.getETag() == null) {
        withoutETag.add(part);
      }
    }
    if (withoutETag.isEmpty()) {
      List<Map<String, Object>> etags = new ArrayList<Map<String, Object>>();
      for (UploadedPart part : parts) {
        Map<String, Object> etag = new LinkedHashMap<String, Object>();
        etag.put("etag", part.getETag());
        etag.put("part", part.getNumber());
        etags.add(etag);
      }
      parameters.put("etags", etags);
    } else if (withoutETag.size() < parts.size()) {
      throw new ApiErrorException.ApiConnectionException("The storage server accepted " + withoutETag.size() + " of the "
          + parts.size() + " parts (the first is part " + withoutETag.get(0).getNumber()
          + ") without an ETag, and the rest with one, so the upload cannot be completed");
    }

    scope.check();
    FilesResponse response;
    AtomicBoolean reached = new AtomicBoolean();
    try {
      response = runtime.send(RequestMethods.POST, "/files/" + UrlUtils.encodeUrlPath(path), parameters, options, scope, maxRetries,
          true, reached);
    } catch (ApiErrorException e) {
      if (e.getHttpCode() >= 400 && e.getHttpCode() < 500) {
        throw e;
      }
      throw new UploadCompletionUnknownException(path, size, parts, e);
    } catch (SdkException | IllegalStateException e) {
      if (!reached.get()) {
        // Nothing reached Files.com, so nothing was completed.
        throw e;
      }
      throw new UploadCompletionUnknownException(path, size, parts, e);
    }
    File file;
    try {
      file = runtime.readItem(response.getBody(), FILE);
    } catch (ApiErrorException.ApiConnectionException e) {
      throw new UploadCompletionUnknownException(path, size, parts, e);
    }
    runtime.bind(file, options);
    return file;
  }

  private HashMap<String, Object> parameters() {
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    if (mkdirParents != null) {
      parameters.put("mkdir_parents", mkdirParents);
    }
    return parameters;
  }
}
