package com.files.net;

// The sizes of the parts of an upload whose sizes the SDK chooses (see UploadGrant), within the
// rules of an S3 multipart upload and the SDK's own planning limits: every part but the last of at
// least 5 MiB; parts of at most 1 GiB, or of at most the buffer space a stream's parts may use, since
// a stream's part is held whole until the storage server accepts it; at most 10,000 parts, or fewer
// when Files.com allows fewer; and at most 5 TiB, the largest file Files.com plans uploads for.
//
// Each part is sized as it is planned, from the size the upload prefers at that moment, which
// PartSizeFeedback may change for the parts not yet planned, and made larger if a smaller one would
// leave the rest more parts than allowed. For a file, the rest is what
// remains of it. For a stream of unknown length it is what remains of the most the stream may hold,
// its bound: a part is sized as the most it may hold, ends early where the stream does, and never goes
// past the bound. plan proves before the first part is sent that the file, or the bound, fits in the
// parts allowed (ceil(bytes / largest) <= parts), and lengthFor keeps that so to the last part with
// arithmetic that cannot overflow (bytes left - length <= (parts left - 1) * largest).
final class PartSizes {
  static final long MIB = 1024L * 1024;
  static final long MIN_NONFINAL_PART = 5 * MIB;
  static final long MAX_PART = 1024 * MIB;
  static final long MAX_PARTS = 10000;
  static final long MAX_FILE = 5L * 1024 * 1024 * MIB;
  // The automatic starting sizes, before the limits and the workload rule.
  static final long UNKNOWN_LENGTH_START = 8 * MIB;
  static final long PREFERRED_CEILING = 256 * MIB;
  static final long WORKLOAD_PARTS = 16;

  // Why an upload that could choose its sizes sends the parts Files.com plans instead.
  enum Fallback {
    // The SDK's sizes cannot hold the file within the parts allowed; Files.com may plan larger parts.
    PART_LIMIT,
    // A stream's parts, held in memory, cannot be large enough for the most it may hold.
    MEMORY_LIMIT
  }

  private final Object lock = new Object();
  private final long bytes;
  private final boolean lengthKnown;
  private final long maxParts;
  private final long floor;
  private final long ceiling;
  private final long largest;
  private final boolean fixed;
  private Choice current;

  private PartSizes(long bytes, boolean lengthKnown, long maxParts, long floor, long ceiling, long largest, boolean fixed, long start) {
    this.bytes = bytes;
    this.lengthKnown = lengthKnown;
    this.maxParts = maxParts;
    this.floor = floor;
    this.ceiling = ceiling;
    this.largest = largest;
    this.fixed = fixed;
    this.current = new Choice(start, 0);
  }

  // The result of planning: the sizes, or why Files.com's plan is used instead.
  static final class Plan {
    final PartSizes sizes;
    final Fallback fallback;

    private Plan(PartSizes sizes, Fallback fallback) {
      this.sizes = sizes;
      this.fallback = fallback;
    }
  }

  // Plans an upload of total bytes, or, when total is -1, of a stream of unknown length that may hold
  // at most bound bytes. availableParts is the part count Files.com advertised, or 0 for none;
  // bufferLimit is the most bytes a part may hold in memory, or -1 when parts are not held in memory.
  // Throws IllegalStateException, before anything is sent, where Files.com's sizes could not help
  // either: a fixed size the caller asked for that breaks the limits, or a stream whose parts the
  // buffer space cannot hold.
  static Plan plan(long total, long bound, long availableParts, PartSizing sizing, long bufferLimit, String path) {
    boolean lengthKnown = total >= 0;
    boolean isFixed = sizing.getMode() == PartSizing.Mode.FIXED;
    long bytes = lengthKnown ? total : bound;
    if (bytes > MAX_FILE) {
      if (isFixed) {
        throw new IllegalStateException((lengthKnown ? "The " + total + "-byte upload to " + path : "The upload to " + path
            + ", of a stream that may hold " + bound + " bytes,") + " is larger than the " + MAX_FILE
            + " bytes the SDK chooses part sizes for, so it cannot be sent in fixed parts");
      }
      return new Plan(null, Fallback.PART_LIMIT);
    }
    long parts = availableParts > 0 ? Math.min(MAX_PARTS, availableParts) : MAX_PARTS;
    boolean bufferBound = bufferLimit >= 0 && bufferLimit < MAX_PART;
    long largest = bufferBound ? bufferLimit : MAX_PART;
    String what = lengthKnown ? "the " + bytes + "-byte upload to " + path : "the upload to " + path + ", of a stream that may hold " + bytes
        + " bytes,";
    long fewestParts = ceilDiv(bytes, largest);
    boolean partsTooSmall = bytes > largest && largest < MIN_NONFINAL_PART;
    if ((partsTooSmall || fewestParts > parts) && !lengthKnown && bufferBound && !isFixed) {
      // The stream's bound is not lowered to hide that its parts cannot be large enough.
      return new Plan(null, Fallback.MEMORY_LIMIT);
    }
    if (partsTooSmall) {
      throw new IllegalStateException("The parts of " + what + " must be at least " + MIN_NONFINAL_PART + " bytes, more than the "
          + bufferLimit + " bytes its transfer limiter may buffer. " + BUFFER_ADVICE);
    }
    if (fewestParts > parts) {
      if (!bufferBound && !isFixed) {
        return new Plan(null, Fallback.PART_LIMIT);
      }
      throw new IllegalStateException(capitalized(what) + " needs at least " + fewestParts + " parts of at most " + largest
          + " bytes, more than the " + parts + " parts the upload may have." + (bufferBound ? " " + BUFFER_ADVICE : ""));
    }
    if (isFixed) {
      long size = sizing.getPartSize();
      long largestPart = Math.min(size, bytes);
      if ((bytes > size && size < MIN_NONFINAL_PART) || largestPart > MAX_PART) {
        throw new IllegalStateException("With a fixed part size of " + size + " bytes, " + what + " would have parts outside the"
            + " sizes S3 allows: from " + MIN_NONFINAL_PART + " to " + MAX_PART + " bytes, the last part excepted");
      }
      if (largestPart > largest) {
        throw new IllegalStateException("With a fixed part size of " + size + " bytes, " + what + " needs parts of " + largestPart
            + " bytes, more than the " + largest + " bytes its transfer limiter may buffer. " + BUFFER_ADVICE);
      }
      if (bytes > size && ceilDiv(bytes, size) > parts) {
        throw new IllegalStateException("With fixed parts of " + size + " bytes, " + what + " needs " + ceilDiv(bytes, size)
            + " parts, more than the " + parts + " parts the upload may have");
      }
      return new Plan(new PartSizes(bytes, lengthKnown, parts, MIN_NONFINAL_PART, size, largest, true, size), null);
    }
    long floor = Math.min(MIN_NONFINAL_PART, largest);
    long ceiling = Math.min(Math.min(MAX_PART, Math.max(PREFERRED_CEILING, floor)), largest);
    long start;
    if (lengthKnown) {
      start = preferredSizeFor(bytes);
      if (bytes / start < WORKLOAD_PARTS) {
        start = Math.min(start, bytes / WORKLOAD_PARTS);
      }
    } else {
      start = UNKNOWN_LENGTH_START;
    }
    start = Math.min(Math.max(start, floor), ceiling);
    if (lengthKnown) {
      start = Math.max(start, ceilDiv(bytes, parts));
    }
    return new Plan(new PartSizes(bytes, lengthKnown, parts, floor, Math.max(start, ceiling), largest, false, start), null);
  }

  private static final String BUFFER_ADVICE = "Allow more with a TransferLimiter, or upload from a file, which does not hold whole"
      + " parts in memory.";

  // The automatic starting size for a file of known length.
  static long preferredSizeFor(long bytes) {
    if (bytes <= 256 * MIB) {
      return 8 * MIB;
    }
    if (bytes <= 4096 * MIB) {
      return 16 * MIB;
    }
    if (bytes <= 65536 * MIB) {
      return 32 * MIB;
    }
    return 64 * MIB;
  }

  // For a stream of unknown length, the most it may hold.
  long bound() {
    return bytes;
  }

  boolean lengthKnown() {
    return lengthKnown;
  }

  long maxParts() {
    return maxParts;
  }

  // The size the next part would get, unless it holds the rest.
  long preferredSize() {
    return choice().size;
  }

  // The size the upload prefers now, with its epoch.
  Choice choice() {
    synchronized (lock) {
      return current;
    }
  }

  // Whether the sizes are the caller's fixed size, which never changes.
  boolean isFixed() {
    return fixed;
  }

  // PartSizeFeedback prefers another size for the parts not yet planned, within floor and ceiling:
  // a new size epoch.
  void change(long size) {
    synchronized (lock) {
      current = new Choice(Math.min(Math.max(size, floor), ceiling), current.epoch + 1);
    }
  }

  // The length of part number, starting at offset, from the preferred size chosen: for a stream of
  // unknown length, the most the part may hold. Called once per part, in order, as it is planned.
  long lengthFor(long number, long offset, Choice chosen) {
    long remaining = bytes - offset;
    long partsLeft = maxParts - (number - 1);
    if (partsLeft < 1) {
      // plan proved this cannot happen: a defect, not a limit.
      throw new IllegalStateException("Part " + number + " is more than the " + maxParts + " parts the upload may have");
    }
    long length = fixed ? chosen.size : Math.max(chosen.size, floor);
    // Leave at most partsLeft - 1 parts of the largest size for the rest.
    long restCapacity = partsLeft - 1 > Long.MAX_VALUE / largest ? Long.MAX_VALUE : (partsLeft - 1) * largest;
    if (remaining - length > restCapacity) {
      length = remaining - restCapacity;
    }
    if (lengthKnown ? length >= remaining : length > remaining) {
      length = remaining;
    }
    if (length > largest) {
      // plan proved this cannot happen: a defect, not a limit.
      throw new IllegalStateException("Part " + number + " cannot be planned within the limits: " + remaining + " bytes remain for "
          + partsLeft + " parts of at most " + largest + " bytes");
    }
    return length;
  }

  // The largest size a later change may prefer, and the smallest.
  long ceiling() {
    return ceiling;
  }

  long floor() {
    return floor;
  }

  // A preferred part size and the size epoch it belongs to, read together.
  static final class Choice {
    final long size;
    final int epoch;

    Choice(long size, int epoch) {
      this.size = size;
      this.epoch = epoch;
    }
  }

  static long ceilDiv(long n, long d) {
    return n <= 0 ? 0 : n / d + (n % d == 0 ? 0 : 1);
  }

  private static String capitalized(String text) {
    return Character.toUpperCase(text.charAt(0)) + text.substring(1);
  }
}
