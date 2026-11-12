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
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;

// One upload's requests to the Files.com API: its begin_upload negotiations and the request that
// completes it. Every request uses the client and request options the upload started with, and
// the destination path exactly as the caller spelled it. The parts themselves go to the
// negotiated storage URLs, not through here.
final class UploadSession {
  private static final TypeReference<File> FILE = new TypeReference<File>() {};
  // The parameter asking Files.com to complete the upload in the background where its destination
  // does that, such as a Remote Server mount or a Files Agent, answering 202 while it is not done. The
  // SDK names it itself, whatever schema it was generated from: Files.com completes files it stores
  // itself as before, and so does a server that does not know the parameter.
  static final String ASYNC_FINALIZE = "async_finalize";
  // How long the upload waits after an answer that Files.com is still completing it before asking
  // again, for each answer up to the third: one, two, then three seconds, counted for each upload's
  // completion (finalizationPollMillis). Files.com itself holds each request for a few seconds
  // before it answers so.
  static final long FINALIZATION_POLL_MILLIS = 1000;
  static final int FINALIZATION_POLL_STEPS = 3;

  private final ClientRuntime runtime;
  private final String path;
  private final HashMap<String, Object> options;
  private final Boolean mkdirParents;
  private final int maxRetries;
  private volatile String ref;

  // Told of each answer that Files.com is completing the upload in the background, on the thread that
  // completes the upload, before it waits to ask again.
  interface FinalizationListener {
    // progress is the percentage of the completion Files.com gave, if any.
    void pending(OptionalInt progress);
  }

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
  // The request asks Files.com to complete the upload in the background where its destination does
  // that (ASYNC_FINALIZE). Files.com may then answer 202, that it is still completing the upload
  // with this reference; finalizing is told, with the percentage it gave, and one, two, then three
  // seconds later (finalizationPollMillis) the same request is sent again (poll), which Files.com
  // answers from the completion it began, never completing the upload twice, until it answers with
  // the file or with the error that ended the upload. A 202 that is not that answer, or finalizing throwing, leaves
  // the outcome unknown, as Files.com may still complete the upload.
  //
  // Until Files.com answers that it is completing the upload, the request is never repeated once it
  // may have reached Files.com: Files.com discards an upload's reference when it completes it, so a
  // repeat after a lost answer would report a completed upload as missing. Only an attempt that
  // failed before the request was sent in full, such as one that could not connect, is retried. A
  // client error in Files.com's own format means it refused the request and completed nothing, and a
  // failure before the request reached Files.com completed nothing either; any other failure,
  // cancellation included, leaves the outcome unknown: UploadCompletionUnknownException, with the
  // failure as its cause.
  File finish(long size, List<UploadedPart> parts, Instant modifiedTime, OperationScope scope, FinalizationListener finalizing) {
    HashMap<String, Object> parameters = parameters();
    parameters.put("action", "end");
    parameters.put("ref", ref);
    parameters.put("size", size);
    parameters.put(ASYNC_FINALIZE, true);
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
      response = runtime.send(RequestMethods.POST, finishTarget(), parameters, options, scope, maxRetries, true, reached);
    } catch (ApiErrorException e) {
      if (e.getHttpCode() >= 400 && e.getHttpCode() < 500) {
        throw e;
      }
      throw sentUnknown(size, parts, e);
    } catch (SdkException | IllegalStateException e) {
      if (!reached.get()) {
        // Nothing reached Files.com, so nothing was completed.
        throw e;
      }
      throw sentUnknown(size, parts, e);
    }
    for (int pendingAnswers = 1; response.getStatusCode() == 202; pendingAnswers++) {
      JsonNode pending = pendingAnswer(response.getBody());
      if (pending == null) {
        throw new UploadCompletionUnknownException(path, size, parts, ref, "Files.com accepted the request to complete the upload to " + path
            + " without saying that it is completing it, so it is not known whether the file was completed. Check it before uploading it"
            + " again.", null);
      }
      try {
        finalizing.pending(progressOf(pending));
      } catch (RuntimeException e) {
        throw stoppedWaiting(size, parts, e);
      }
      response = poll(parameters, size, parts, scope, finalizationPollMillis(pendingAnswers));
    }
    File file;
    try {
      file = runtime.readItem(response.getBody(), FILE);
    } catch (ApiErrorException.ApiConnectionException e) {
      throw sentUnknown(size, parts, e);
    }
    runtime.bind(file, options);
    return file;
  }

  // Waits, then asks Files.com again about the upload it is completing in the background: the same
  // request, which, unlike the first, is sent again after a failure that may be temporary, as any API
  // request is, since Files.com answers it from the completion it began. An error in Files.com's own
  // format other than throttling is the upload's outcome: the error that ended it, or Files.com's
  // refusal of the request. Anything else, such as a cancellation, the time limit or failures that
  // went on past the retries, stops the wait with the outcome unknown.
  private FilesResponse poll(HashMap<String, Object> parameters, long size, List<UploadedPart> parts, OperationScope scope, long waitMillis) {
    try {
      scope.sleep(waitMillis);
      return runtime.send(RequestMethods.POST, finishTarget(), parameters, options, scope, maxRetries);
    } catch (ApiErrorException e) {
      if (e.getHttpCode() >= 400 && e.getHttpCode() < 500 && e.getHttpCode() != 429 && !(e instanceof ApiErrorException.RateLimitedException)) {
        throw e;
      }
      throw stoppedWaiting(size, parts, e);
    } catch (SdkException | IllegalStateException | OperationScope.Stopped e) {
      throw stoppedWaiting(size, parts, e);
    }
  }

  // The wait before the request that follows the pendingAnswers-th answer that Files.com is still
  // completing the upload.
  static long finalizationPollMillis(final int pendingAnswers) {
    return FINALIZATION_POLL_MILLIS * Math.min(pendingAnswers, FINALIZATION_POLL_STEPS);
  }

  private String finishTarget() {
    return "/files/" + UrlUtils.encodeUrlPath(path);
  }

  // Files.com's answer that it is completing the upload in the background, or null when the 202 body
  // is not one: a JSON object whose status is "pending" and whose ref is this upload's.
  private JsonNode pendingAnswer(String body) {
    JsonNode answer;
    try {
      answer = ClientRuntime.MAPPER.readTree(body);
    } catch (JsonProcessingException e) {
      return null;
    }
    if (answer == null || !answer.isObject() || !"pending".equals(text(answer.get("status"))) || !ref.equals(text(answer.get("ref")))) {
      return null;
    }
    return answer;
  }

  // The percentage a pending answer gives: a JSON whole number from 0 to 100. Any other value, or none,
  // is no percentage.
  private static OptionalInt progressOf(JsonNode pending) {
    JsonNode progress = pending.get("progress");
    if (progress == null || !progress.isIntegralNumber() || !progress.canConvertToInt() || progress.intValue() < 0 || progress.intValue() > 100) {
      return OptionalInt.empty();
    }
    return OptionalInt.of(progress.intValue());
  }

  private static String text(JsonNode node) {
    return node != null && node.isTextual() ? node.textValue() : null;
  }

  // The request completing the upload failed after it may have reached Files.com.
  private UploadCompletionUnknownException sentUnknown(long size, List<UploadedPart> parts, Throwable cause) {
    return new UploadCompletionUnknownException(path, size, parts, ref, "The request to complete the upload to " + path + " failed ("
        + cause.getClass().getSimpleName() + ") after it may have reached Files.com, so it is not known whether the file was completed."
        + " Check it before uploading it again.", cause);
  }

  // The upload stopped waiting for Files.com to complete it in the background.
  private UploadCompletionUnknownException stoppedWaiting(long size, List<UploadedPart> parts, Throwable cause) {
    return new UploadCompletionUnknownException(path, size, parts, ref, "The upload to " + path + " stopped waiting ("
        + cause.getClass().getSimpleName() + ") while Files.com was completing it in the background, so it is not known whether the file"
        + " was completed: Files.com may still complete it. Check it before uploading it again.", cause);
  }

  private HashMap<String, Object> parameters() {
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    if (mkdirParents != null) {
      parameters.put("mkdir_parents", mkdirParents);
    }
    return parameters;
  }
}
