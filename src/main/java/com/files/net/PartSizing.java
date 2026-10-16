package com.files.net;

/**
 * How an upload's part sizes are chosen. Immutable.
 *
 * <p>Files.com plans every upload's parts, and its plan is always followed where the SDK may not
 * choose sizes itself. The SDK may choose them only for an ordinary multipart upload to S3 whose
 * signed URLs bind neither a part's length nor its bytes, as Files.com's instructions for the
 * upload show them; a destination that needs its parts sent another way, such as a Files Agent,
 * always gets the parts Files.com plans. Where the SDK chooses, every part but the last holds at
 * least 5 MiB and at most 1 GiB (and no more than the upload may hold in memory, for a stream), and
 * the upload has at most 10,000 parts, or fewer if Files.com allows fewer.
 *
 * <ul>
 *   <li>{@link #automatic()}, the default, chooses sizes for the file where the SDK may, starting
 *       from 8 MiB for files up to 256 MiB, 16 MiB up to 4 GiB, 32 MiB up to 64 GiB and 64 MiB above
 *       that, smaller so that a file has at least 16 parts and larger where 10,000 parts would not
 *       hold it. It sends the parts Files.com plans everywhere else, and also where its sizes cannot
 *       hold the upload, such as a stream of unknown length larger than its parts held in memory
 *       could reach.</li>
 *   <li>{@link #serverPlanned()} always sends the parts Files.com plans.</li>
 *   <li>{@link #fixed(long)} sends parts of one size the caller chooses, the last holding what
 *       remains. The upload fails before anything is sent where the SDK may not choose sizes or
 *       where that size breaks the limits above.</li>
 * </ul>
 */
public final class PartSizing {
  /** How part sizes are chosen. */
  public enum Mode {
    /** Sizes chosen by the SDK where it may, and Files.com's plan elsewhere. */
    AUTOMATIC,
    /** Files.com's plan. */
    SERVER_PLANNED,
    /** One size the caller chose. */
    FIXED
  }

  private static final PartSizing AUTOMATIC = new PartSizing(Mode.AUTOMATIC, 0);
  private static final PartSizing SERVER_PLANNED = new PartSizing(Mode.SERVER_PLANNED, 0);

  private final Mode mode;
  private final long partSize;

  private PartSizing(Mode mode, long partSize) {
    this.mode = mode;
    this.partSize = partSize;
  }

  /** Sizes chosen by the SDK where it may, and the parts Files.com plans elsewhere. The default. */
  public static PartSizing automatic() {
    return AUTOMATIC;
  }

  /** The parts Files.com plans, always. */
  public static PartSizing serverPlanned() {
    return SERVER_PLANNED;
  }

  /**
   * Parts of {@code partSize} bytes, the last holding what remains.
   *
   * @param partSize the size of every part but the last, in bytes
   * @return the sizing
   * @throws IllegalArgumentException if {@code partSize} is less than 1
   */
  public static PartSizing fixed(long partSize) {
    if (partSize < 1) {
      throw new IllegalArgumentException("A fixed part size must be at least 1 byte");
    }
    return new PartSizing(Mode.FIXED, partSize);
  }

  /** How part sizes are chosen. */
  public Mode getMode() {
    return mode;
  }

  /** The fixed part size, in bytes, or 0 unless the mode is {@link Mode#FIXED}. */
  public long getPartSize() {
    return partSize;
  }

  @Override
  public String toString() {
    return mode == Mode.FIXED ? "PartSizing{FIXED, partSize=" + partSize + "}" : "PartSizing{" + mode + "}";
  }
}
