package com.files.net;

import com.files.exceptions.ApiErrorException;
import com.files.exceptions.UnsupportedUploadException;
import com.files.models.FileUploadPart;
import com.files.util.UrlUtils;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.regex.Pattern;

// Divides an upload into parts. The first answer from Files.com decides how (see UploadGrant).
//
// Where the SDK may choose sizes and the upload's PartSizing lets it, PartSizes sizes each part,
// and Files.com's partsize is not used. Otherwise each part holds the partsize bytes its own
// negotiation gave, or what remains of a file of known size if that is less, so the server's rules,
// such as equal sizes for some destinations, hold; next_partsize is then only a hint for how many
// parts to ask for at once. Either way a part's number, offset and length are fixed once it is
// prepared, and never change, even when its URL is renewed.
//
// While every part so far may be sent in parallel, the planner negotiates several parts per
// begin_upload request, up to the upload's current concurrency target and no more than the file still
// needs; otherwise it negotiates one at a time.
final class PartPlanner {
  private final UploadSession session;
  private final long totalLength;
  private final IntSupplier maxPartsPerRequest;
  private final PartSizing sizing;
  private final long streamBound;
  private final long bufferLimit;
  private final ArrayDeque<FileUploadPart> negotiated = new ArrayDeque<FileUploadPart>();
  private long nextNumber = 1;
  private long nextOffset;
  private long nextSizeHint;
  private long availableParts = Long.MAX_VALUE;
  private boolean finished;
  // Also cleared by a renewal on a part thread.
  private volatile boolean everyPartParallel = true;
  // An earlier run's record of the upload, which this run continues (resume), or null.
  private UploadCheckpoint.Saved resumed;
  // Decided by the upload's first answer.
  private UploadGrant grant;
  private PartSizes sizes;
  private PartSizes.Fallback fallback;

  // totalLength is -1 when the length is not known until the source ends; streamBound is then the
  // most it may hold. bufferLimit is the most bytes a part may hold in memory, or -1 when the source
  // does not hold whole parts in memory.
  PartPlanner(UploadSession session, long totalLength, IntSupplier maxPartsPerRequest, PartSizing sizing, long streamBound,
      long bufferLimit) {
    this.session = session;
    this.totalLength = totalLength;
    this.maxPartsPerRequest = maxPartsPerRequest;
    this.sizing = sizing;
    this.streamBound = streamBound;
    this.bufferLimit = bufferLimit;
  }

  // Plans from a part that was negotiated already, such as one returned by File.beginUpload, which
  // also decides how the upload is divided.
  void seed(FileUploadPart first) {
    negotiated.add(first);
    decide(Collections.singletonList(first), nextNumber);
  }

  // Continues the upload an earlier run recorded (UploadCheckpoint), from its reference: the parts it
  // planned keep their numbers and boundaries, and are sent again exactly as planned unless it had
  // them accepted, and new parts follow them. Files.com's first answer must still be for the upload
  // recorded, and every later one too, before any part is sent: the same S3 upload where the SDK chose
  // the sizes, and otherwise the recorded size for each part recorded.
  void resume(UploadCheckpoint.Saved saved) {
    resumed = saved;
  }

  // For a resumed upload that has nothing left to send: asks Files.com for its last part again, so
  // that the upload is shown to be recognized, and to be the one recorded, before it is completed.
  void confirmResumed(OperationScope scope) {
    if (resumed == null || grant != null || resumed.planned.isEmpty()) {
      return;
    }
    long last = resumed.planned.lastKey();
    decide(checkNumbering(session.next(last, 1, scope), last), last);
  }

  // Whether the SDK chose the part sizes.
  boolean sizesChosen() {
    return sizes != null;
  }

  // The S3 upload the parts go to, where the SDK chose their sizes (UploadGrant.uploadIdentity).
  String uploadIdentity() {
    return grant == null ? null : grant.uploadIdentity();
  }

  long partCount() {
    return nextNumber - 1;
  }

  long plannedBytes() {
    return nextOffset;
  }

  boolean everyPartParallel() {
    return everyPartParallel;
  }

  // Where the parts go, once the first answer has been read; null before.
  UploadDestination destination() {
    return grant == null ? null : grant.destination();
  }

  // Whether the SDK chose the part sizes, and how.
  PartSizeSource sizeSource() {
    return sizes == null ? PartSizeSource.SERVER : sizing.getMode() == PartSizing.Mode.FIXED ? PartSizeSource.FIXED
        : PartSizeSource.AUTOMATIC;
  }

  // The sizes the SDK chooses, or null when they are Files.com's.
  PartSizes sizes() {
    return sizes;
  }

  // Why sizes the SDK could have chosen are Files.com's instead, or null.
  PartSizes.Fallback fallback() {
    return fallback;
  }

  // The next part to prepare, or null once the whole source has been planned. An empty source is
  // one empty part. Its length is the most it may hold; commit gives the length it has.
  Planned next(OperationScope scope) {
    if (resumed != null) {
      // Parts an earlier run had accepted are not sent again; a URL negotiated for one is not needed.
      while (resumed.accepted.containsKey(nextNumber)) {
        if (!negotiated.isEmpty()) {
          negotiated.removeFirst();
        }
        nextOffset = Math.addExact(nextOffset, resumed.planned.get(nextNumber)[1]);
        nextNumber++;
      }
    }
    if (finished || (totalLength >= 0 && nextNumber > 1 && nextOffset >= totalLength)) {
      return null;
    }
    if (totalLength < 0 && nextNumber > 1 && nextOffset >= streamBound) {
      // The stream holds more than the most it may hold: stop before sending the bytes past it.
      throw new IllegalStateException("The stream uploaded to " + session.path() + " holds more than the " + streamBound
          + " bytes the upload allows it (UploadOptions.Builder.maxStreamLength), so the upload was not completed");
    }
    if (nextNumber > availableParts) {
      throw new ApiErrorException.ApiConnectionException("The upload to " + session.path() + " needs more than the "
          + availableParts + " parts Files.com allows");
    }
    if (negotiated.isEmpty()) {
      negotiate(scope);
    }
    FileUploadPart part = negotiated.removeFirst();
    if (part.getAvailableParts() != null && part.getAvailableParts() > 0) {
      availableParts = Math.min(availableParts, part.getAvailableParts());
    }
    long length;
    int sizeEpoch = 0;
    long[] recorded = resumed != null ? resumed.planned.get(nextNumber) : null;
    if (recorded != null) {
      // Planned by an earlier run, and perhaps sent: sent again exactly as planned, if at all.
      length = recorded[1];
      checkRecorded(part, nextNumber, length);
    } else if (sizes != null) {
      grant.checkStillPermitted(part, nextNumber, session.path());
      PartSizes.Choice chosen = sizes.choice();
      length = sizes.lengthFor(nextNumber, nextOffset, chosen);
      sizeEpoch = chosen.epoch;
    } else {
      long remaining = totalLength >= 0 ? totalLength - nextOffset : Long.MAX_VALUE;
      long partsize = part.getPartsize() == null ? 0 : part.getPartsize();
      if (remaining > 0 && partsize <= 0) {
        throw new ApiErrorException.ApiConnectionException("Files.com gave no size for part " + nextNumber + " of the upload to "
            + session.path());
      }
      length = Math.min(remaining, partsize);
      nextSizeHint = part.getNextPartsize() != null && part.getNextPartsize() > 0 ? part.getNextPartsize() : partsize;
    }
    Target target = Target.of(part, nextNumber, session.path());
    if (recorded != null) {
      checkResendable(target, nextNumber);
    }
    if (!target.parallelParts) {
      everyPartParallel = false;
    }
    return new Planned(nextNumber, nextOffset, length, sizeEpoch, target, recorded != null);
  }

  // A part an earlier run planned and did not record as accepted may have been sent by that run all
  // the same, so sending it now may send it again: only where Files.com's answer to this run lets
  // parts be retried (retry_parts). Otherwise the upload must start over, and the part is not sent.
  // Every instruction a part is sent with comes from next or renew, which both check it.
  private void checkResendable(Target target, long number) {
    if (!target.retryParts) {
      throw resumed.notResendable(number);
    }
  }

  // A part an earlier run planned must still be one of the same upload, of the size recorded: where
  // the SDK chose its size, a part of the same S3 upload whose URL binds no length; otherwise,
  // Files.com's size for it, which a last part may exceed.
  private void checkRecorded(FileUploadPart part, long number, long length) {
    if (resumed.sizesChosen) {
      grant.checkStillPermitted(part, number, session.path());
      return;
    }
    long partsize = part.getPartsize() == null ? 0 : part.getPartsize();
    boolean last = totalLength >= 0 && nextOffset + length == totalLength;
    if (last ? partsize < length : partsize != length) {
      throw resumed.changed("Files.com now plans part " + number + " of the upload with " + partsize + " bytes, not the " + length
          + " its checkpoint records");
    }
    nextSizeHint = part.getNextPartsize() != null && part.getNextPartsize() > 0 ? part.getNextPartsize() : partsize;
  }

  // The prepared part holds length bytes; after the last part of a source of unknown length, the
  // planner is finished.
  void commit(Planned part, long length, boolean sourceEnded) {
    nextNumber++;
    nextOffset = Math.addExact(nextOffset, length);
    if (sourceEnded) {
      finished = true;
    }
  }

  // A new URL for a planned part whose URL is about to expire or has. The part keeps its number,
  // offset and length, so Files.com must still plan the same size for it. Safe to call from a
  // part's thread while planning continues.
  Target renew(Planned part, long length, boolean last, OperationScope scope) {
    FileUploadPart renewed = checkNumbering(session.next(part.number, 1, scope), part.number).get(0);
    if (sizes != null) {
      // The SDK chose the part's size: the new URL must be for the same S3 upload, binding no length.
      grant.checkStillPermitted(renewed, part.number, session.path());
    } else {
      long partsize = renewed.getPartsize() == null ? 0 : renewed.getPartsize();
      boolean sameSize = last ? partsize >= length : partsize == length;
      if (!sameSize) {
        throw new ApiErrorException.ApiConnectionException("Files.com changed the size of part " + part.number + " of the upload to "
            + session.path() + " from " + length + " to " + partsize + " bytes when renewing its URL");
      }
    }
    Target target = Target.of(renewed, part.number, session.path());
    if (part.recorded) {
      checkResendable(target, part.number);
    }
    if (!target.parallelParts) {
      everyPartParallel = false;
    }
    return target;
  }

  private void negotiate(OperationScope scope) {
    List<FileUploadPart> parts = session.ref() == null
        ? session.begin(totalLength, scope)
        : session.next(nextNumber, partsToRequest(), scope);
    negotiated.addAll(checkNumbering(parts, nextNumber));
    if (grant == null) {
      decide(parts, nextNumber);
    }
  }

  // Reads the grant from the upload's first answer, whose first part is number firstNumber, and plans
  // its sizes when the SDK chooses them. A resumed upload keeps the sizing it was recorded with.
  private void decide(List<FileUploadPart> first, long firstNumber) {
    grant = UploadGrant.of(first, firstNumber);
    if (resumed != null) {
      if (!resumed.sizesChosen) {
        return;
      }
      if (!grant.permitsChosenSizes() || !resumed.uploadIdentity.equals(grant.uploadIdentity())) {
        throw resumed.changed("Files.com now gives the upload another S3 upload, or one whose part sizes the SDK may not choose");
      }
    }
    if (!grant.permitsChosenSizes() || sizing.getMode() == PartSizing.Mode.SERVER_PLANNED) {
      if (sizing.getMode() == PartSizing.Mode.FIXED) {
        throw new IllegalStateException("The upload to " + session.path() + " cannot be sent in fixed parts: Files.com's instructions"
            + " for it do not let the SDK choose part sizes, so nothing was sent");
      }
      return;
    }
    long advertised = first.get(0).getAvailableParts() == null ? 0 : first.get(0).getAvailableParts();
    PartSizes.Plan plan = PartSizes.plan(totalLength, streamBound, advertised, sizing, bufferLimit, session.path());
    sizes = plan.sizes;
    fallback = plan.fallback;
  }

  private long partsToRequest() {
    int most = maxPartsPerRequest.getAsInt();
    if (!everyPartParallel || most <= 1) {
      return 1;
    }
    long partsNeeded;
    if (sizes != null) {
      long left = sizes.maxParts() - (nextNumber - 1);
      long preferred = sizes.preferredSize();
      partsNeeded = totalLength >= 0 ? Math.min(left, PartSizes.ceilDiv(totalLength - nextOffset, preferred)) : left;
    } else {
      if (nextSizeHint <= 0 || totalLength < 0) {
        return 1;
      }
      long remaining = totalLength - nextOffset;
      partsNeeded = remaining / nextSizeHint + (remaining % nextSizeHint == 0 ? 0 : 1);
    }
    return Math.max(1, Math.min(most, partsNeeded));
  }

  // The parts of one response, which must be numbered consecutively from firstNumber and belong to
  // this upload.
  private List<FileUploadPart> checkNumbering(List<FileUploadPart> parts, long firstNumber) {
    for (int i = 0; i < parts.size(); i++) {
      long expected = firstNumber + i;
      FileUploadPart part = parts.get(i);
      if (part.getPartNumber() != null && part.getPartNumber() != expected) {
        throw new ApiErrorException.ApiConnectionException("Files.com returned upload part " + part.getPartNumber() + " instead of part " + expected);
      }
      if (part.getRef() != null && !part.getRef().isEmpty() && session.ref() != null && !part.getRef().equals(session.ref())) {
        throw new ApiErrorException.ApiConnectionException("Files.com returned upload part " + expected + " for another upload");
      }
    }
    return parts;
  }

  // A part as planned: its number, offset and the most bytes it may hold, the size epoch its length
  // was chosen in when the SDK chooses sizes, its instructions, and whether an earlier run planned it
  // (resume).
  static final class Planned {
    final long number;
    final long offset;
    final long maxLength;
    final int sizeEpoch;
    final Target target;
    final boolean recorded;

    Planned(long number, long offset, long maxLength, int sizeEpoch, Target target, boolean recorded) {
      this.number = number;
      this.offset = offset;
      this.maxLength = maxLength;
      this.sizeEpoch = sizeEpoch;
      this.target = target;
      this.recorded = recorded;
    }
  }

  // Where and how to send one part, as a begin_upload response negotiated it. Only instructions
  // this SDK knows how to follow are accepted; anything else fails before the part's bytes are
  // sent rather than being guessed at.
  static final class Target {
    private static final Pattern METHOD = Pattern.compile("[A-Za-z]+");

    final String method;
    // Sent exactly as given: signed URLs are never rewritten.
    final String uri;
    final List<Map.Entry<String, String>> headers;
    final String contentLength;
    final Instant expiresAt;
    final boolean parallelParts;
    final boolean retryParts;

    private Target(String method, String uri, List<Map.Entry<String, String>> headers, String contentLength, Instant expiresAt,
        boolean parallelParts, boolean retryParts) {
      this.method = method;
      this.uri = uri;
      this.headers = headers;
      this.contentLength = contentLength;
      this.expiresAt = expiresAt;
      this.parallelParts = parallelParts;
      this.retryParts = retryParts;
    }

    boolean expiresWithin(Duration time) {
      return expiresAt != null && !expiresAt.isAfter(Instant.now().plus(time));
    }

    static Target of(FileUploadPart part, long number, String path) {
      if (part.getUploadUri() == null || part.getUploadUri().isEmpty() || part.getHttpMethod() == null
          || !METHOD.matcher(part.getHttpMethod()).matches()) {
        throw new ApiErrorException.ApiConnectionException("Files.com did not say where or how to send part " + number + " of the upload to " + path);
      }
      checkSendInstructions(part.getSend(), number, path);
      if (!strings(part.getParameters(), "parameters", number, path).isEmpty()) {
        throw new UnsupportedUploadException("Files.com asked for part " + number + " of the upload to " + path
            + " to be sent with parameters, which this version of the SDK cannot send");
      }
      List<Map.Entry<String, String>> headers = new ArrayList<Map.Entry<String, String>>();
      String contentLength = null;
      for (Map.Entry<String, String> header : strings(part.getHeaders(), "headers", number, path)) {
        if ("Content-Length".equalsIgnoreCase(header.getKey())) {
          contentLength = header.getValue().trim();
        } else {
          headers.add(header);
        }
      }
      return new Target(part.getHttpMethod().toUpperCase(Locale.ROOT), part.getUploadUri(), Collections.unmodifiableList(headers),
          contentLength, expiresAt(part), Boolean.TRUE.equals(part.getParallelParts()), Boolean.TRUE.equals(part.getRetryParts()));
    }

    // The earlier of the part's expires time and the expiry its signed URL states.
    private static Instant expiresAt(FileUploadPart part) {
      Instant stated = null;
      if (part.getExpires() != null) {
        try {
          stated = OffsetDateTime.parse(part.getExpires()).toInstant();
        } catch (DateTimeParseException e) {
          stated = null;
        }
      }
      Instant signed = UrlUtils.expiresAt(part.getUploadUri());
      if (stated == null) {
        return signed;
      }
      return signed != null && signed.isBefore(stated) ? signed : stated;
    }

    // The only format Files.com asks for: the part's size in the Content-Length header and its
    // bytes as the request body.
    private static void checkSendInstructions(Object send, long number, String path) {
      for (Map.Entry<String, String> instruction : strings(send, "send instructions", number, path)) {
        boolean understood = ("partsize".equals(instruction.getKey()) && "required-header Content-Length".equalsIgnoreCase(instruction.getValue()))
            || ("partdata".equals(instruction.getKey()) && "body".equals(instruction.getValue()));
        if (!understood) {
          throw new UnsupportedUploadException("Files.com asked for part " + number + " of the upload to " + path + " to be sent with "
              + instruction.getKey() + " as \"" + instruction.getValue() + "\", which this version of the SDK cannot do");
        }
      }
    }

    // A JSON object of strings from a part's field; empty when the field is missing or null.
    private static List<Map.Entry<String, String>> strings(Object field, String name, long number, String path) {
      List<Map.Entry<String, String>> strings = new ArrayList<Map.Entry<String, String>>();
      if (field == null) {
        return strings;
      }
      if (!(field instanceof Map)) {
        throw new ApiErrorException.ApiConnectionException("The " + name + " of part " + number + " of the upload to " + path + " are not an object");
      }
      for (Map.Entry<?, ?> entry : ((Map<?, ?>) field).entrySet()) {
        if (!(entry.getValue() instanceof String)) {
          throw new ApiErrorException.ApiConnectionException("The " + name + " of part " + number + " of the upload to " + path
              + " include " + entry.getKey() + ", which is not a string");
        }
        strings.add(new SimpleImmutableEntry<String, String>(String.valueOf(entry.getKey()), (String) entry.getValue()));
      }
      return strings;
    }
  }
}
