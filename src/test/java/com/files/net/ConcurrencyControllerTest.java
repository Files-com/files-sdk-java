package com.files.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import org.junit.Test;

// The adaptive concurrency controller's decisions, on a clock the test controls, against the
// conformance scenarios of the adaptive transfer specification (verification/adaptive-upload-spec.md)
// and the links the reference implementation was checked on: the real controller, admission and part
// size feedback, fed modeled links and answers at the network boundary.
public class ConcurrencyControllerTest {
  private static final long MIB = 1024L * 1024;
  private static final long SECOND = TimeUnit.SECONDS.toNanos(1);
  private static final long MINUTE = TimeUnit.MINUTES.toNanos(1);

  // A default upload to S3 over the link of a slow connection, after one a live default upload
  // flooded: 7.8 MB/s, of which one request carries 6.8, for the 23 parts of 16 MiB of a 371 MB file,
  // where each new connection carries nothing for its first 3 seconds. Judging only windows on warm
  // connections, one part at a time is its baseline; it tries one part more, whose two requests only
  // wait for each other, and goes back to 1: never more than 2 parts at once. An aggressive startup
  // sends every part at once instead.
  @Test
  public void aDefaultStartupOnASlowLinkSendsAtMostOnePartMoreThanItsWarmConnectionsPaidFor() {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(371030394, 16 * MIB), 7.8e6, 6.8e6, 3 * SECOND);

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertTrue(evidence, driver.peakInFlight <= 2);
    assertEquals(evidence, 1, driver.controller.target());
    assertFalse(evidence, driver.decisions().stream().anyMatch(d -> d.target == ConcurrencyDecision.WARM_START_FAST_LINK));
    assertEquals(evidence, 1, driver.snapshot().getMinTarget());
  }

  @Test
  public void anAggressiveStartupOnTheSameSlowLinkSendsEveryPartAtOnce() {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.AGGRESSIVE, 0), parts(371030394, 16 * MIB), 7.8e6, 6.8e6, 3 * SECOND);

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertEquals(evidence, 23, driver.peakInFlight);
    assertFalse(evidence, driver.decisions().stream().anyMatch(ConcurrencyControllerTest::isWarmStart));
  }

  // 2 GiB in 128 parts of 16 MiB over the same slow link. It measures one part at a time, tries one
  // part more until the two requests it then sends have been answered, and then for at most the window
  // under way and the next, which judges it, goes back to 1 because its two requests only waited,
  // rests in windows of the policy's size, two of 32 answered parts, and tries again: never more than
  // 2 parts at once.
  @Test
  public void aDefaultStartupThatWentBackOnASlowLinkTriesOnlyOnePartMoreAgain() {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(2L << 30, 16 * MIB), 7.8e6, 6.8e6, 3 * SECOND);

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertTrue(evidence, driver.peakInFlight <= 2);
    List<ConcurrencyController.Decision> changes = driver.decisions().stream().filter(d -> d.current != d.previous)
        .collect(Collectors.toList());
    assertTrue("Only one part more at a time, and back: " + evidence, changes.stream().allMatch(
        d -> (d.target == ConcurrencyDecision.WARM_START_RAISED && d.previous == 1 && d.current == 2)
            || (d.target == ConcurrencyDecision.WARM_START_SETTLED && d.previous == 2 && d.current == 1)));
    List<ConcurrencyController.Decision> raised = changes.stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_RAISED)
        .collect(Collectors.toList());
    assertTrue("It tried one part more again after resting: " + evidence, raised.size() >= 2);
    for (ConcurrencyController.Decision settled : changes) {
      if (settled.target != ConcurrencyDecision.WARM_START_SETTLED) {
        continue;
      }
      assertTrue("Back, short of the 50% more a second part had to add: " + evidence,
          settled.baseline > 0 && settled.throughput < settled.baseline * 1.5);
      assertTrue("Its two requests waited for each other: " + evidence, settled.queueEstimate >= 0.5);
      ConcurrencyController.Decision tried = null;
      for (ConcurrencyController.Decision d : raised) {
        if (d.at < settled.at) {
          tried = d;
        }
      }
      assertNotNull(evidence, tried);
      long triedAt = tried.at;
      long sentThenAnswered = driver.requests.stream().filter(r -> r[0] == triedAt).mapToLong(r -> r[1]).max().getAsLong();
      assertTrue("The requests it sent then were answered within the policy's window: " + evidence,
          driver.answeredBy(sentThenAnswered) - driver.answeredBy(triedAt) < 32);
      int windows = driver.measuredBy(settled.at) - driver.measuredBy(sentThenAnswered);
      assertTrue("Judged once the requests it sent then had been answered, by the window then under way or the next: " + evidence,
          settled.at >= sentThenAnswered && windows >= 1 && windows <= 2);
      ConcurrencyController.Decision next = raised.stream().filter(d -> d.at > settled.at).findFirst().orElse(null);
      assertTrue("It rested for two windows of 32 answered parts first: " + evidence,
          next == null || driver.answeredBy(next.at) - driver.answeredBy(settled.at) >= 64);
    }
    assertEquals(evidence, 1, driver.snapshot().getMinTarget());
  }

  // The same, where the link becomes fast (2 GB/s, 100 MB/s a request) while it rests: the first
  // window of answered parts that shows it takes it to the policy's start, 150, at once, with the
  // policy's minimum again.
  @Test
  public void aDefaultStartupGoesToThePolicysStartOnceItsAnswersShowAFastLink() {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(2L << 30, 16 * MIB), 7.8e6, 6.8e6, 3 * SECOND);
    driver.run(10 * MINUTE, () -> driver.decisions().stream().anyMatch(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED));
    int peakWhileSlow = driver.peakInFlight;
    driver.changeLink(2e9, 100e6, -1);

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertEquals(evidence, 2, peakWhileSlow);
    ConcurrencyController.Decision promoted = last(driver.decisions().stream().filter(ConcurrencyControllerTest::isWarmStart)
        .collect(Collectors.toList()));
    assertEquals(evidence, "WARM_START_FAST_LINK 1->150", promoted.target + " " + promoted.previous + "->" + promoted.current);
    assertEquals("The policy's minimum again: " + evidence, 8, driver.snapshot().getMinTarget());
    assertTrue(evidence, driver.done());
  }

  // 2 GiB in parts of 16 MiB over a link of 7.8 MB/s of which one request carries 5.9, where the
  // second request stays slow for its whole part. One part at a time, the slow request is in the
  // baseline, which holds more answers until their variation is known, so it is not the baseline
  // alone; the window at 2 shows both requests waiting, and the second part goes back. Very slow, it
  // varies so much that the baseline holds a window of the policy's size, 32 answers, first. Either
  // way, no part is added from a window whose requests were queued, and never more than 2 at once.
  @Test
  public void aDefaultStartupKeepsNoPartAddedWhileItsRequestsQueue() {
    assertNoPartKeptWhileQueued(3.6e6);
  }

  @Test
  public void aDefaultStartupWithAVerySlowRequestWaitsForAFullWindowBeforeAddingAPart() {
    assertNoPartKeptWhileQueued(1.1e6);
  }

  private static void assertNoPartKeptWhileQueued(double slowRequestBytesPerSecond) {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(2L << 30, 16 * MIB), 7.8e6, 5.9e6, 100_000_000L);
    driver.slowRequest(2, slowRequestBytesPerSecond);

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertTrue(evidence, driver.peakInFlight <= 2);
    List<ConcurrencyController.Decision> raised = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_RAISED)
        .collect(Collectors.toList());
    assertTrue("Parts added only from windows whose requests were not queued: " + evidence,
        !raised.isEmpty() && raised.stream().allMatch(d -> d.queueEstimate < 0.5));
    ConcurrencyController.Decision settled = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED)
        .findFirst().orElse(null);
    assertNotNull(evidence, settled);
    assertEquals(evidence, "2->1", settled.previous + "->" + settled.current);
    assertTrue("Back for its queued requests: " + evidence, settled.queueEstimate >= 0.5);
    assertTrue("The slow request was not the baseline alone: " + evidence, settled.baseline > slowRequestBytesPerSecond * 1.2);
    if (slowRequestBytesPerSecond < 2e6) {
      assertTrue("No part more until the baseline held a window of the policy's size: " + evidence,
          driver.answeredBy(raised.get(0).at) >= 33);
    }
    assertEquals(evidence, 1, driver.snapshot().getMinTarget());
  }

  // The same link. After the first part more goes back, four requests of the rest's second window
  // travel on a slow connection, of 2 MB/s, holding that window's throughput down. The second part
  // then delivers more than the 50% more it must add to that window, only because the window was
  // held down: both its requests only wait. It goes back for that queue.
  @Test
  public void aDefaultStartupKeepsNoPartAddedOnTheGainOverAWindowASlowConnectionHeldDown() {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(2L << 30, 16 * MIB), 7.8e6, 5.9e6, 100_000_000L);
    driver.run(10 * MINUTE, () -> driver.decisions().stream().anyMatch(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED));
    ConcurrencyController.Decision first = last(driver.decisions());
    for (int number = driver.sent + 40; number < driver.sent + 44; number++) {
      driver.slowRequest(number, 2e6);
    }

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertEquals(evidence, "WARM_START_SETTLED 2->1", first.target + " " + first.previous + "->" + first.current);
    ConcurrencyController.Decision held = driver.decisions().stream()
        .filter(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED && d.at > first.at).findFirst().orElse(null);
    assertNotNull("The second part went back again: " + evidence, held);
    assertEquals(evidence, "2->1", held.previous + "->" + held.current);
    assertTrue("Its gain over the window held down would have kept it: " + evidence, held.throughput >= held.baseline * 1.5);
    assertTrue("Back for its queued requests: " + evidence, held.queueEstimate >= 0.5);
    assertTrue(evidence, driver.peakInFlight <= 2);
  }

  // 64 parts of 1 MiB over a link of 7 MB/s of which one request carries 2.92 MB/s: two parts at once
  // deliver twice as much. The request added at 2 opens a connection, which carries nothing while it
  // connects. Answered somewhat later than the others, it is judged once both requests have been
  // answered, and kept. Where the link carries only what one request does, it goes. Where it is not
  // answered before the target has had a window of the policy's size, the window then closing judges
  // it as it is: no raise waits on a request that may never be answered.
  @Test
  public void aPartAddedAnsweredAboutOnePartLaterIsKept() {
    assertPartAddedJudgedOnceAnswered(350, 7e6, "kept");
  }

  @Test
  public void aPartAddedWhoseConnectingCountsInItsTimeIsNotTakenForQueueing() {
    assertPartAddedJudgedOnceAnswered(500, 7e6, "kept");
  }

  @Test
  public void aPartAddedAnsweredAfterTwoWindowsOfTheOtherRequestIsKept() {
    assertPartAddedJudgedOnceAnswered(1100, 7e6, "kept");
  }

  @Test
  public void aPartAddedOnALinkOneRequestFillsIsWithdrawn() {
    assertPartAddedJudgedOnceAnswered(500, 2.92e6, "withdrawn");
  }

  @Test
  public void aPartAddedNotAnsweredWithinAPolicyWindowIsJudgedWithoutIt() {
    assertPartAddedJudgedOnceAnswered(30000, 7e6, "judged without it");
  }

  private static void assertPartAddedJudgedOnceAnswered(int connectMillis, double linkBytesPerSecond, String outcome) {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(64 * MIB, MIB), linkBytesPerSecond, 2.92e6,
        TimeUnit.MILLISECONDS.toNanos(connectMillis));

    driver.run(10 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    ConcurrencyController.Decision raised = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_RAISED)
        .findFirst().get();
    assertEquals(evidence, "1->2", raised.previous + "->" + raised.current);
    ConcurrencyController.Decision withdrawn = driver.decisions().stream()
        .filter(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED && d.previous == 2 && d.current == 1).findFirst().orElse(null);
    if ("kept".equals(outcome)) {
      assertNull("The part added delivers its share once both requests are answered: " + evidence, withdrawn);
      assertTrue("It tried one part more from 2: " + evidence,
          driver.decisions().stream().anyMatch(d -> d.target == ConcurrencyDecision.WARM_START_RAISED && d.previous == 2));
      return;
    }
    assertNotNull(evidence, withdrawn);
    assertTrue(evidence, driver.peakInFlight <= 2);
    if ("judged without it".equals(outcome)) {
      assertTrue("Judged once the target had a window of the policy's size: " + evidence,
          driver.answeredBy(withdrawn.at) - driver.answeredBy(raised.at) >= 32);
      assertTrue("Before the request added was answered: " + evidence,
          withdrawn.at < raised.at + TimeUnit.MILLISECONDS.toNanos(connectMillis));
    }
  }

  // 8 GiB in 32 MiB parts over a fast link (2 GB/s). Where each connection moves 120 MB/s, its first
  // answered part already shows a fast link, and it goes straight to the policy's start, or the cap.
  // Where each moves 100 MB/s, just under 96 MiB/s, its first window at 2 shows it. Where each moves
  // 25 MB/s and takes a second to start, it rises by half again as many parts at a time until a window
  // shows the fast link. Either way it reaches the policy's start and is never left lower.
  @Test
  public void aConnectionFastEnoughAloneGoesToThePolicysStartAfterTheFirstAnswer() {
    assertFastLinkReachesStart(120e6, 40, 0, 0, 1);
  }

  @Test
  public void connectionsJustUnderTheFastLinkAddOnePartFirst() {
    assertFastLinkReachesStart(100e6, 40, 0, 1, 5);
  }

  @Test
  public void slowerConnectionsThatWarmUpSlowlyRiseByHalfAgainToThePolicysStart() {
    assertFastLinkReachesStart(25e6, 1000, 0, -1, -1);
  }

  @Test
  public void aFastLinkGoesToTheCapBelowThePolicysStart() {
    assertFastLinkReachesStart(120e6, 40, 12, 0, 1);
  }

  private static void assertFastLinkReachesStart(double requestBytesPerSecond, int connectMillis, int cap, int raises, int answeredAtStart) {
    LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, cap), parts(8L << 30, 32 * MIB), 2e9, requestBytesPerSecond,
        TimeUnit.MILLISECONDS.toNanos(connectMillis));

    driver.run(5 * MINUTE, null);

    String evidence = driver.describe();
    int start = cap > 0 ? cap : 150;
    List<ConcurrencyController.Decision> warm = driver.decisions().stream().filter(ConcurrencyControllerTest::isWarmStart)
        .collect(Collectors.toList());
    assertTrue("Up to the policy's start, never settling below it: " + evidence, !warm.isEmpty() && last(warm).current == start
        && warm.stream().noneMatch(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED));
    assertTrue("Only raises by half again, rounded up, before it: " + evidence, warm.subList(0, warm.size() - 1).stream().allMatch(
        d -> d.target == ConcurrencyDecision.WARM_START_RAISED && d.current == d.previous + Math.max(1, (d.previous + 1) / 2)));
    if (raises >= 0) {
      assertEquals(evidence, raises + 1, warm.size());
      assertEquals("Decided on the first answers that could show it: " + evidence, answeredAtStart, driver.answeredBy(last(warm).at));
    } else {
      assertTrue(evidence, warm.size() > 2);
    }
    assertEquals(evidence, start, driver.peakInFlight);
    assertTrue(evidence, driver.done());
  }

  // 1 GiB in parts of 1 MiB over a link of 2 GB/s where no request waits for another, but each takes
  // as long to answer as one of a cloud batch's requests did, from 44 to 108 ms; a new connection
  // takes 70 ms more. Judging windows that hold enough answers for that variation, compared by the
  // median of their answers' times, it reaches the policy's start once its answered parts show the
  // fast link, within a quarter of the upload, taking back at most one raise for variation alone.
  @Test
  public void aDefaultStartupOnAFastLinkWhoseAnswersVaryReachesThePolicysStart() {
    for (int draw = 1; draw <= 12; draw++) {
      LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(1L << 30, MIB), 2e9, 1e9, TimeUnit.MILLISECONDS.toNanos(70));
      driver.varyRequests(cloudAnswerTimes(draw, 1024, TimeUnit.MILLISECONDS.toNanos(1)));

      driver.run(5 * MINUTE, null);

      List<ConcurrencyController.Decision> warm = driver.decisions().stream().filter(ConcurrencyControllerTest::isWarmStart)
          .collect(Collectors.toList());
      String evidence = "draw " + draw + ": " + driver.describe();
      assertTrue(evidence, driver.done());
      assertTrue("No part more before the baseline held two answers after the first: " + evidence,
          !warm.isEmpty() && driver.answeredBy(warm.get(0).at) >= 3);
      assertTrue("At most one raise taken back for the answers' variation: " + evidence,
          warm.stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED).count() <= 1);
      ConcurrencyController.Decision promoted = last(warm);
      assertTrue("The policy's start, once answered parts showed the fast link: " + evidence,
          promoted.target == ConcurrencyDecision.WARM_START_FAST_LINK && promoted.current == 150);
      assertTrue("Within a quarter of the upload's 1,024 parts: " + evidence, driver.answeredBy(promoted.at) <= 256);
      assertEquals(evidence, 150, driver.peakInFlight);
    }
  }

  // 2 GiB in parts of 32 MiB over a link of 2 GB/s, where a request carries its part in some 280 ms,
  // but each takes as long as those cloud times scaled to that, and a new connection takes 300 ms to
  // start carrying, so that the first answer does not show the fast link. The first answers that show
  // it decide, within its first six answers, not once the larger window is over.
  @Test
  public void aDefaultStartupWhoseAnswersVaryTakesTheFastLinkFromTheFirstAnswersThatShowIt() {
    for (int draw = 1; draw <= 8; draw++) {
      LinkDriver driver = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(2L << 30, 32 * MIB), 2e9, 1e9, TimeUnit.MILLISECONDS.toNanos(300));
      driver.varyRequests(cloudAnswerTimes(draw, 64, TimeUnit.MILLISECONDS.toNanos(280) / 65));

      driver.run(5 * MINUTE, null);

      List<ConcurrencyController.Decision> warm = driver.decisions().stream().filter(ConcurrencyControllerTest::isWarmStart)
          .collect(Collectors.toList());
      String evidence = "draw " + draw + ": " + driver.describe();
      assertTrue(evidence, driver.done());
      assertEquals(evidence, "WARM_START_FAST_LINK 1->150",
          warm.stream().map(d -> d.target + " " + d.previous + "->" + d.current).collect(Collectors.joining(", ")));
      assertTrue("Decided by the first answers that showed it: " + evidence, driver.answeredBy(warm.get(0).at) <= 6);
    }
  }

  // A failure before any answer has shown how fast the link is lowers the target to 1, where it
  // rests: it tries one part more only after two windows of 32 answered parts, and at 2 its answers
  // show the fast link and it goes to the policy's start, where failures lower it only to the
  // policy's minimum. Where the first answer shows a fast link, a failure lowers the target as the
  // policy would without a warm start: 35%.
  @Test
  public void aFailureDuringADefaultStartupLowersItTo1WhereItRestsBeforeTryingOnePartMore() {
    LinkDriver failed = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(8L << 30, 32 * MIB), 2e9, 100e6, 0);
    failed.admit();
    failed.fail();
    assertEquals("target, lowest target: " + failed.describe(), "1 1", failed.controller.target() + " " + failed.snapshot().getMinTarget());
    failed.run(5 * MINUTE, null);
    List<ConcurrencyController.Decision> warm = failed.decisions().stream().filter(ConcurrencyControllerTest::isWarmStart)
        .collect(Collectors.toList());
    assertTrue("One part more first: " + failed.describe(), !warm.isEmpty() && warm.get(0).target == ConcurrencyDecision.WARM_START_RAISED
        && warm.get(0).previous == 1 && warm.get(0).current == 2);
    assertTrue("After resting for two windows of 32 answered parts: " + failed.describe(), failed.answeredBy(warm.get(0).at) >= 64);
    assertEquals("The policy's start, with its minimum: " + failed.describe(), "WARM_START_FAST_LINK 150 8",
        last(warm).target + " " + failed.controller.target() + " " + failed.snapshot().getMinTarget());

    LinkDriver fast = new LinkDriver(s3(UploadStartup.DEFAULT, 0), parts(8L << 30, 32 * MIB), 2e9, 120e6, 0);
    fast.run(SECOND, null);
    assertEquals("target, lowest target: " + fast.describe(), "150 8", fast.controller.target() + " " + fast.snapshot().getMinTarget());
    fast.fail();
    assertEquals("35% lower, as the policy lowers it: " + fast.describe(), 97, fast.controller.target());
  }

  // A default upload that chooses its part sizes, 4 GiB in 16 MiB parts to start with, over a slow
  // link of 7 MB/s, of which one request carries 6.8, where storage takes 0.8 s to answer each part. A
  // second part never adds the 50% more its warm start asks for, so it goes back to 1 and rests; larger
  // parts do pay. So while it rests, and only then, it tries larger parts: a size probe starts in a
  // rest's hold window, and its windows leave the rest where it was, so no part is added while it is
  // under way and no size changes while a part added is judged.
  @Test
  public void aDefaultStartupTriesLargerPartsOnlyWhileItRests() {
    LinkDriver driver = sizedDriver(4L << 30, false, 7e6, TimeUnit.MILLISECONDS.toNanos(800));

    driver.run(60 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertNoSizeAndTargetChangeTogether(driver, evidence);
    List<ConcurrencyController.Decision> sizes = driver.sizeDecisions();
    List<ConcurrencyController.Decision> raised = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_RAISED)
        .collect(Collectors.toList());
    assertTrue(evidence, driver.peakInFlight <= 2);
    assertEquals("Larger parts, kept for what they delivered: " + evidence, "PART_SIZE_GREW 32, PART_SIZE_PROBE_ACCEPTED 32",
        sizes.subList(0, 2).stream().map(s -> s.size + " " + s.current / MIB).collect(Collectors.joining(", ")));
    assertTrue("Only once it had gone back and rested: " + evidence, sizes.get(0).at > driver.decisions().stream()
        .filter(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED).findFirst().get().at);
    ConcurrencyController.Decision next = raised.stream().filter(d -> d.at > sizes.get(1).at).findFirst().orElse(null);
    assertNotNull("One part more again, from a window after the size probe's: " + evidence, next);
    assertTrue("Tried from a window of the larger parts: " + evidence, next.throughput >= sizes.get(1).baseline * 1.1);
    assertTrue(evidence, driver.answeredLengths.stream().filter(length -> length == 32 * MIB).count() >= 32);
  }

  // Where larger parts never pay (no wait to answer, 16 GiB in 32 MiB parts, the 7.8 MB/s link) and
  // size probes start as soon as they may, every rest still tries one part more after its one size
  // probe.
  @Test
  public void sizeProbesThatNeverPayStillLeaveEveryRestEndingInOnePartMore() {
    LinkDriver driver = sizedDriver(16L << 30, true, 7.8e6, 0);

    driver.run(60 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertNoSizeAndTargetChangeTogether(driver, evidence);
    List<ConcurrencyController.Decision> sizes = driver.sizeDecisions();
    List<ConcurrencyController.Decision> raised = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_RAISED)
        .collect(Collectors.toList());
    assertTrue(evidence, driver.peakInFlight <= 2);
    assertTrue("One part more again after every rest: " + evidence, raised.size() >= 3);
    for (ConcurrencyController.Decision settled : driver.decisions()) {
      if (settled.target != ConcurrencyDecision.WARM_START_SETTLED) {
        continue;
      }
      long restEnd = raised.stream().filter(d -> d.at > settled.at).mapToLong(d -> d.at).findFirst().orElse(Long.MAX_VALUE);
      assertTrue("One size probe a rest: " + evidence,
          sizes.stream().filter(s -> "PART_SIZE_GREW".equals(s.size) && s.at > settled.at && s.at < restEnd).count() <= 1);
    }
    assertTrue(evidence, sizes.stream().filter(s -> "PART_SIZE_GREW".equals(s.size)).count() >= 2
        && sizes.stream().noneMatch(s -> "PART_SIZE_PROBE_ACCEPTED".equals(s.size)));
  }

  // Where the link becomes fast as a size probe's first part is answered, the window of the probe's
  // parts shows it and takes the upload to the policy's start, and the size goes back.
  @Test
  public void aFastLinkDuringASizeProbeGoesToThePolicysStartAndTheSizeGoesBack() {
    LinkDriver driver = sizedDriver(4L << 30, false, 7e6, TimeUnit.MILLISECONDS.toNanos(800));
    driver.run(60 * MINUTE, () -> driver.answeredLengths.contains(32 * MIB));
    final int answeredAtChange = driver.answeredLengths.size();
    driver.changeLink(2e9, 1e9, 0);

    driver.run(60 * MINUTE, null);

    String evidence = driver.describe();
    assertTrue(evidence, driver.done());
    assertNoSizeAndTargetChangeTogether(driver, evidence);
    List<ConcurrencyController.Decision> promotions = driver.decisions().stream()
        .filter(d -> d.target == ConcurrencyDecision.WARM_START_FAST_LINK).collect(Collectors.toList());
    assertEquals(evidence, 1, promotions.size());
    ConcurrencyController.Decision promoted = promotions.get(0);
    assertEquals(evidence, "1->150", promoted.previous + "->" + promoted.current);
    assertEquals("The window of the probe's parts showed it: " + evidence, 32, driver.answeredBy(promoted.at) - answeredAtChange);
    int at = driver.records.indexOf(promoted);
    ConcurrencyController.Decision interrupted = at + 1 < driver.records.size() ? driver.records.get(at + 1) : null;
    assertEquals("The size went back: " + evidence, "PART_SIZE_PROBE_REJECTED 32->16",
        interrupted == null ? "none" : interrupted.size + " " + interrupted.previous / MIB + "->" + interrupted.current / MIB);
    assertTrue(evidence, driver.records.subList(0, at).stream().noneMatch(s -> "PART_SIZE_PROBE_ACCEPTED".equals(s.size)));
    assertEquals(evidence, 8, driver.snapshot().getMinTarget());
  }

  // While it warm-starts, never a size decision while one part more is being judged, nor one part
  // more while a size probe is under way.
  private static void assertNoSizeAndTargetChangeTogether(LinkDriver driver, String evidence) {
    boolean partAdded = false;
    boolean sizeProbe = false;
    for (ConcurrencyController.Decision record : driver.records) {
      if (record.target == ConcurrencyDecision.WARM_START_FAST_LINK) {
        break;
      }
      if (record.size != null) {
        assertFalse("No size changed while one part more was judged: " + evidence, partAdded);
        sizeProbe = "PART_SIZE_GREW".equals(record.size);
      } else if (isWarmStart(record)) {
        assertFalse("No part added during a size probe: " + evidence, sizeProbe && record.target == ConcurrencyDecision.WARM_START_RAISED);
        partAdded = record.target == ConcurrencyDecision.WARM_START_RAISED;
      }
    }
  }

  private static LinkDriver sizedDriver(long total, boolean eager, double linkBytesPerSecond, long answerWait) {
    PartSizes.Plan plan = PartSizes.plan(total, -1, 0, PartSizing.automatic(), -1, "remote.bin");
    assertNull(plan.fallback);
    PartSizeFeedback feedback = eager ? new PartSizeFeedback(plan.sizes, 1, 1, 0) : new PartSizeFeedback(plan.sizes);
    return new LinkDriver(s3(UploadStartup.DEFAULT, 0), plan.sizes, feedback, total, linkBytesPerSecond, 6.8e6, 3 * SECOND, answerWait);
  }

  // The target's requests, served two at a time: however many are in flight, a window's requests take
  // as long as two need for all of them, so a raise from 2 to 3 measures no more and is withdrawn, each
  // time: never more than 3 parts at once, and no raise kept.
  @Test
  public void aRaisedTargetThatDoesNotRaiseThroughputIsWithdrawn() {
    ControllerDriver driver = new ControllerDriver(new TuningBuilder(2, 8).windowAttempts(4).probeGain(30).build());

    for (int round = 0; round < 32; round++) {
      driver.window(TimeUnit.MILLISECONDS.toNanos(30 * driver.controller.target()));
    }

    String evidence = driver.records.toString();
    List<ConcurrencyController.Decision> rejected = driver.records.stream().filter(d -> d.target == ConcurrencyDecision.PROBE_REJECTED)
        .collect(Collectors.toList());
    assertTrue("Raises were measured and withdrawn, again and again: " + evidence, rejected.size() >= 2);
    assertTrue("Each went back to 2, short of the 30% more asked for: " + evidence,
        rejected.stream().allMatch(d -> d.previous == 3 && d.current == 2 && d.throughput < d.baseline * 1.3));
    assertTrue("No raise was kept: " + evidence, driver.records.stream().noneMatch(d -> d.target == ConcurrencyDecision.PROBE_ACCEPTED));
    assertTrue("Never more than 3 parts at once: " + evidence, driver.records.stream().allMatch(d -> d.current <= 3));
  }

  @Test
  public void partsAddedAboveThePlateauMustEachEarnTheirGain() {
    ControllerDriver driver = new ControllerDriver(new TuningBuilder(2, 4).windowAttempts(2).growthStep(2).plateau(2).probeGain(1)
        .gainPerAddedPart(20).probeWindows(1).build());

    driver.window(SECOND);
    assertEquals("The first window grows the target by two.", 4, driver.controller.target());
    // Four parts deliver 25% more than two: more than the probe gain, less than 2 x 20%.
    driver.window(1600 * TimeUnit.MILLISECONDS.toNanos(1));

    assertEquals(2, driver.controller.target());
    assertEquals(ConcurrencyDecision.PROBE_REJECTED, last(driver.records).target);
  }

  @Test
  public void queueingPastTheGrowthLimitHoldsGrowth() {
    assertLatency(0, ConcurrencyDecision.GROWTH_HELD_FOR_LATENCY, 4);
  }

  @Test
  public void queueingPastTheBackoffLimitWithLessThroughputLowersTheTarget() {
    assertLatency(1.5, ConcurrencyDecision.SHRANK_FOR_LATENCY, 2);
  }

  private static void assertLatency(double backoffQueueLimit, ConcurrencyDecision decision, int target) {
    ControllerDriver driver = new ControllerDriver(new TuningBuilder(4, 8).windowAttempts(4).growthInterval(10).latencyGrowthQueueLimit(1)
        .latencyBackoffQueueLimit(backoffQueueLimit).build());

    driver.window(SECOND);
    // The same four requests take twice as long per byte: an estimated two of them are queued.
    driver.window(2 * SECOND);

    assertEquals(decision, last(driver.records).target);
    assertEquals(target, driver.controller.target());
  }

  // Windows whose admitted attempts waited for a shared limiter, or that had no part ready, or sent
  // parts one at a time as Files.com required, are not judged: they neither grow the target nor count
  // against a probe, and the snapshot says where the time went.
  @Test
  public void windowsHeldBelowTheTargetByTheLimiterAMissingPartOrSerialPartsAreNotJudged() {
    for (ConcurrencyController.BelowTarget reason : new ConcurrencyController.BelowTarget[]{ConcurrencyController.BelowTarget.SHARED_LIMITER,
        ConcurrencyController.BelowTarget.NO_PART_READY, ConcurrencyController.BelowTarget.SERIAL_PARTS}) {
      ControllerDriver driver = new ControllerDriver(new TuningBuilder(4, 8).build());
      if (reason == ConcurrencyController.BelowTarget.SERIAL_PARTS) {
        driver.controller.useSerial(driver.now);
      }
      for (int round = 0; round < 12; round++) {
        // One request sent at a time; for the limiter, three more admitted and waiting for its slots.
        int waiting = reason == ConcurrencyController.BelowTarget.SHARED_LIMITER ? 3 : 0;
        for (int i = 0; i < waiting; i++) {
          driver.controller.granted(driver.now);
        }
        driver.oneAtATime(1, SECOND);
        for (int i = 0; i < waiting; i++) {
          driver.controller.released(driver.now, false, 0);
        }
      }
      String evidence = reason + ": " + driver.records;
      TransferConcurrency snapshot = driver.controller.snapshot(driver.now);
      assertEquals(evidence, 4, driver.controller.target());
      assertEquals(evidence, 0, snapshot.getGrows() + snapshot.getProbesRejected());
      assertTrue(evidence, driver.records.stream().filter(d -> d.target == ConcurrencyDecision.WINDOW_NOT_MEASURED && d.reason == reason)
          .count() >= 3);
      assertEquals(evidence, 0, snapshot.getMeasuredWindows());
      long below = reason == ConcurrencyController.BelowTarget.SHARED_LIMITER ? snapshot.getTimeWaitingForLimiter().toNanos()
          : reason == ConcurrencyController.BelowTarget.NO_PART_READY ? snapshot.getTimeWithoutReadyPart().toNanos()
          : snapshot.getTimeSendingSerially().toNanos();
      assertTrue(evidence, below > snapshot.getTimeAtTarget().toNanos());
    }
  }

  // The part sizes of a file of 1 GiB whose parts may hold at most 64 MiB in memory: from 16 MiB up to
  // 64 MiB; and of one of 8 GiB with the same bound, from 32 MiB.
  private static PartSizes plan(long total) {
    PartSizes.Plan plan = PartSizes.plan(total, -1, 0, PartSizing.automatic(), 64 * MIB, "up/file.bin");
    assertNull(plan.fallback);
    return plan.sizes;
  }

  // A file of a shared scope keeps its size feedback under the settled shared target, where each
  // request takes exactly 25 ms whatever its size. The file whose sizes are judged, the first
  // attached, doubles its parts from 16 MiB to its 64 MiB ceiling, each probe kept, each judged by
  // what its own requests delivered against its own level before it. The other file's 32 MiB parts
  // share every round: none of them counts toward the first file's level or probes, and the other
  // file's size is not judged while the first file's is.
  @Test
  public void aSharedScopesFileGrowsItsPartsFromItsOwnParts() {
    long duration = TimeUnit.MILLISECONDS.toNanos(25);
    ControllerDriver driver = new ControllerDriver(new TuningBuilder(2, 2).build());
    PartSizes a = plan(1L << 30);
    PartSizes b = plan(8L << 30);
    driver.attachSizing("up/a.bin", a, new PartSizeFeedback(a, 1, 1, 4));
    driver.attachSizing("up/b.bin", b, new PartSizeFeedback(b, 1, 1, 4));
    for (int round = 0; round < 200 && driver.sizing().stream().noneMatch(d -> "PART_SIZE_PROBE_REJECTED".equals(d.size)
        || ("PART_SIZE_PROBE_ACCEPTED".equals(d.size) && d.current == 64 * MIB)); round++) {
      driver.sharedRound(duration);
    }

    String evidence = driver.records.toString();
    List<ConcurrencyController.Decision> sizing = driver.sizing();
    assertEquals("The first file's parts doubled to the ceiling, each probe kept: " + evidence,
        "PART_SIZE_GREW 32, PART_SIZE_PROBE_ACCEPTED 32, PART_SIZE_GREW 64, PART_SIZE_PROBE_ACCEPTED 64",
        sizing.stream().map(d -> d.size + " " + d.current / MIB).collect(Collectors.joining(", ")));
    for (ConcurrencyController.Decision kept : sizing) {
      if ("PART_SIZE_PROBE_ACCEPTED".equals(kept.size)) {
        assertEquals("Judged by what the file's own requests delivered: " + evidence, kept.current / 0.025, kept.throughput, 1);
        assertEquals("Against the level its own requests delivered before: " + evidence, kept.current / 2 / 0.025, kept.baseline, 1);
      }
    }
    List<Long> first = driver.sentSizes("up/a.bin");
    List<Long> ordered = new ArrayList<Long>(first);
    java.util.Collections.sort(ordered);
    assertEquals("No part of the first file is smaller than the one before: " + evidence, ordered, first);
    assertEquals(16 * MIB, (long) first.get(0));
    assertEquals(64 * MIB, (long) last(first));
    assertTrue("The other file kept its size while the first file's was judged: " + evidence,
        driver.sentSizes("up/b.bin").stream().allMatch(size -> size == 32 * MIB) && driver.sizeOf("up/b.bin") == 32 * MIB);
    assertEquals("The shared target stayed settled at its ceiling", 2, driver.controller.target());
  }

  // Each of the file's requests takes exactly its bytes at 2 MiB/s, however many are in flight, so
  // only more requests at once deliver more. The target grows from 1 to 2 and keeps the gain; the
  // file's sizes are judged only once that target is settled, from a level measured at it as what the
  // file's own requests deliver, 2 MiB/s, never the target's 4 MiB/s; its probe of larger parts finds
  // the same 2 MiB/s and is given up.
  @Test
  public void aSharedTargetChangeIsNotTakenForASizeGain() {
    double bytesPerSecond = 2 * MIB;
    ControllerDriver driver = new ControllerDriver(new TuningBuilder(1, 2).windowAttempts(4).build());
    PartSizes a = plan(1L << 30);
    driver.attachSizing("up/a.bin", a, new PartSizeFeedback(a, 1, 1, 4));
    for (int round = 0; round < 100 && driver.sizing().stream().noneMatch(d -> "PART_SIZE_PROBE_REJECTED".equals(d.size)
        || "PART_SIZE_PROBE_ACCEPTED".equals(d.size)); round++) {
      driver.sizedRound(bytesPerSecond);
    }

    String evidence = driver.records.toString();
    ConcurrencyController.Decision kept = driver.decisions().stream()
        .filter(d -> d.target == ConcurrencyDecision.PROBE_ACCEPTED && d.current == 2).findFirst().orElse(null);
    assertNotNull("More requests at once delivered more: " + evidence, kept);
    assertEquals("The target's two requests deliver 4 MiB/s together", 2 * bytesPerSecond, kept.throughput, 1);
    assertTrue("A whole window of the file's parts was answered while the target changed: " + evidence,
        driver.partsAnswered(0, kept.at) >= 4);
    ConcurrencyController.Decision probe = driver.sizing().stream().filter(d -> "PART_SIZE_GREW".equals(d.size)).findFirst().orElse(null);
    assertNotNull("The file still tried larger parts at the settled target: " + evidence, probe);
    assertEquals(16 * MIB, probe.previous);
    assertEquals(32 * MIB, probe.current);
    assertTrue("The size probe started only once the target was settled: " + evidence, probe.at > kept.at);
    assertTrue("Measured anew at the settled target: " + evidence, driver.partsAnswered(kept.at, probe.at) >= 4);
    assertEquals("The level is what each of the file's requests delivers", bytesPerSecond, probe.baseline, 1);
    assertEquals(1, driver.sizing().stream().filter(d -> "PART_SIZE_GREW".equals(d.size)).count());
    ConcurrencyController.Decision rejected = driver.sizing().stream().filter(d -> "PART_SIZE_PROBE_REJECTED".equals(d.size)).findFirst()
        .orElse(null);
    assertNotNull("Larger parts delivered nothing more: " + evidence, rejected);
    assertEquals(bytesPerSecond, rejected.throughput, 1);
    assertEquals("The size went back", 16 * MIB, rejected.current);
    assertEquals("The shared target kept its gain", 2, driver.controller.target());
  }

  // The scope a default upload's files share warm-starts, and a file's sizes are still judged by its
  // own parts alone: the warm start's rest windows, which hold every file's parts, neither judge the
  // file's size nor count toward the windows it must measure. Over a link of 4 MiB/s however many
  // parts share it, the warm start goes back to 1 and rests; when the link becomes fast (200 MiB/s a
  // request), it goes to the policy's start, here the cap of 16, a settled target, and only from then
  // on does the file measure its own windows, of the policy's 32 parts.
  @Test
  public void aSharedWarmStartLeavesEachFilesSizeToItsOwnParts() {
    ControllerDriver driver = new ControllerDriver(new ConcurrencyController(s3(UploadStartup.DEFAULT, 16), null, true));
    PartSizes a = PartSizes.plan(4L << 30, -1, 0, PartSizing.automatic(), -1, "up/a.bin").sizes;
    driver.attachSizing("up/a.bin", a, new PartSizeFeedback(a));
    assertEquals("The shared scope starts at 1 part at a time", 1, driver.controller.target());
    for (int round = 0; round < 400 && driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_RAISED).count() < 2;
         round++) {
      driver.sizedRound(4 * MIB / (double) driver.controller.target());
    }
    for (int round = 0; round < 100 && driver.sizing().stream().noneMatch(d -> "PART_SIZE_GREW".equals(d.size)); round++) {
      driver.sizedRound(200 * MIB);
    }

    String evidence = driver.records.toString();
    ConcurrencyController.Decision settled = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_SETTLED)
        .findFirst().orElse(null);
    ConcurrencyController.Decision promoted = driver.decisions().stream().filter(d -> d.target == ConcurrencyDecision.WARM_START_FAST_LINK)
        .findFirst().orElse(null);
    ConcurrencyController.Decision grew = driver.sizing().stream().filter(d -> "PART_SIZE_GREW".equals(d.size)).findFirst().orElse(null);
    assertTrue("It went back and rested, then reached the policy's start and tried larger parts: " + evidence,
        settled != null && promoted != null && grew != null);
    assertEquals(16, promoted.current);
    assertTrue("No size decision while the warm start rested: " + evidence, driver.sizing().stream().allMatch(d -> d.at > promoted.at));
    assertTrue("The file measured its own windows at the settled target, none from the rests, before larger parts: " + evidence,
        driver.partsAnswered(promoted.at, grew.at) >= 2 * 32);
  }

  // A static transfer measures windows for its progress, and keeps its concurrency whatever they show.
  @Test
  public void aStaticTransferKeepsItsConcurrencyWhateverItMeasures() {
    ConcurrencyController controller = new ConcurrencyController(ConcurrencySettings.fixed(3, UploadDestination.S3, UploadStartup.DEFAULT),
        null, true);
    ControllerDriver driver = new ControllerDriver(controller);
    for (int round = 0; round < 20; round++) {
      driver.window(round % 2 == 0 ? SECOND : 4 * SECOND);
    }
    driver.fail(3);
    driver.throttle(3, 0);

    assertEquals(3, controller.target());
    TransferConcurrency snapshot = controller.snapshot(driver.now);
    assertEquals(ConcurrencyMode.STATIC, snapshot.getMode());
    assertEquals(0, snapshot.getGrows() + snapshot.getShrinks());
    assertTrue(snapshot.getLastThroughputBytesPerSecond() > 0);
    assertEquals(0, snapshot.getCooldownRemaining().toNanos());
  }

  // Failures of attempts started together, in one epoch, lower the target once; an attempt started
  // after the target was lowered belongs to the new epoch and can lower it again.
  @Test
  public void failuresOfAttemptsStartedTogetherLowerTheTargetOnceAndALaterOneAgain() {
    AtomicLong clock = new AtomicLong(SECOND);
    ConcurrencyController controller = new ConcurrencyController(automatic(new TuningBuilder(8, 8).build()), null, false);
    AttemptAdmission admission = new AttemptAdmission(controller, clock::get);
    OperationScope scope = scope();
    AttemptAdmission.Attempt[] burst = {admission.admit(false, scope), admission.admit(false, scope)};
    AttemptAdmission.Attempt late = admission.admit(false, scope);
    assertTrue(burst[0].tryStart() && burst[1].tryStart());

    burst[0].complete(temporaryFailure(), false);
    burst[1].complete(temporaryFailure(), false);
    assertEquals("Both failures of attempts started together count once, lowering 8 to 4.", 4, controller.target());
    assertTrue(late.tryStart());
    late.complete(temporaryFailure(), false);
    assertEquals(2, controller.target());
  }

  // Throttling answers of attempts started together lower the target once, but each pauses new
  // attempts for at least its Retry-After, never shortening a pause: an attempt admitted before the
  // pause cannot start during it, retries and new parts are admitted only after it, and an attempt
  // already running when it began is not measured when it completes during it.
  @Test
  public void throttlingPausesNewAttemptsForTheLongestRetryAfterAndLowersTheTargetOncePerEpoch() throws Exception {
    AtomicLong clock = new AtomicLong(SECOND);
    ConcurrencyController controller = new ConcurrencyController(s3(UploadStartup.AGGRESSIVE, 20), null, false);
    AttemptAdmission admission = new AttemptAdmission(controller, clock::get);
    OperationScope scope = scope();
    List<AttemptAdmission.Attempt> running = new ArrayList<AttemptAdmission.Attempt>();
    for (int i = 0; i < 4; i++) {
      AttemptAdmission.Attempt attempt = admission.admit(false, scope);
      assertTrue(attempt.tryStart());
      running.add(attempt);
    }
    AttemptAdmission.Attempt admittedBefore = admission.admit(false, scope);

    running.get(0).complete(throttled(TimeUnit.SECONDS.toNanos(2)), false);
    running.get(1).complete(throttled(TimeUnit.SECONDS.toNanos(1)), false);
    assertEquals("Two throttles of one epoch lower 20 by 10% once", 18, controller.target());
    TransferConcurrency paused = controller.snapshot(clock.get());
    assertEquals("A shorter Retry-After does not shorten the pause", 2, paused.getCooldownRemaining().getSeconds());
    assertFalse("An attempt admitted before the pause cannot start during it", admittedBefore.tryStart());

    clock.addAndGet(TimeUnit.MILLISECONDS.toNanos(500));
    int measuredBefore = controller.snapshot(clock.get()).getMeasuredWindows() + controller.snapshot(clock.get()).getUnmeasuredWindows();
    running.get(2).complete(new ConcurrencyController.Sample(true, false, false, -1, 100, SECOND, 0), false);
    running.get(3).complete(new ConcurrencyController.Sample(true, false, false, -1, 100, SECOND, 0), false);
    TransferConcurrency drained = controller.snapshot(clock.get());
    assertEquals("Completions during the pause are not measured", measuredBefore,
        drained.getMeasuredWindows() + drained.getUnmeasuredWindows());

    // Admission waits on another thread until the pause ends, on the test's clock.
    java.util.concurrent.ExecutorService waiter = java.util.concurrent.Executors.newSingleThreadExecutor();
    try {
      java.util.concurrent.Future<AttemptAdmission.Attempt> admitted = waiter.submit(() -> admission.admit(true, scope));
      Thread.sleep(100);
      assertFalse("Still paused: no attempt admitted", admitted.isDone());
      clock.addAndGet(TimeUnit.MILLISECONDS.toNanos(1500));
      AttemptAdmission.Attempt after = admitted.get(5, TimeUnit.SECONDS);
      assertTrue("Admitted and started once the pause is over", after.tryStart());
      after.close();
    } finally {
      waiter.shutdownNow();
    }
  }

  // A default upload's warm start judges a raise only once the attempts in flight as it rose have
  // ended, answered or not: windows that the other attempts' answers close meanwhile judge nothing.
  @Test
  public void aWarmStartJudgesARaiseOnlyOnceTheAttemptsStartedAsItRoseHaveEnded() {
    for (boolean answered : new boolean[]{true, false}) {
      AtomicLong clock = new AtomicLong(SECOND);
      ConcurrencyController controller = new ConcurrencyController(s3(UploadStartup.DEFAULT, 0), null, true);
      AttemptAdmission admission = new AttemptAdmission(controller, clock::get);
      OperationScope scope = scope();
      List<ConcurrencyController.Decision> decisions = new ArrayList<ConcurrencyController.Decision>();
      ArrayDeque<AttemptAdmission.Attempt> inFlight = new ArrayDeque<AttemptAdmission.Attempt>();
      for (int i = 0; i < 3; i++) {
        AttemptAdmission.Attempt first = start(admission, scope);
        clock.addAndGet(TimeUnit.MILLISECONDS.toNanos(20));
        decisions.addAll(first.complete(answer(), true));
      }
      assertEquals("WARM_START_RAISED 1->2", describe(decisions));
      AttemptAdmission.Attempt late = start(admission, scope);
      inFlight.add(start(admission, scope));

      for (int i = 0; i < 6 && decisions.size() == 1; i++) {
        turn(admission, scope, clock, inFlight, decisions);
      }
      assertEquals("No window judged the raise while an attempt started as it rose was in flight: " + describe(decisions), 1,
          decisions.size());
      if (answered) {
        decisions.addAll(late.complete(answer(), true));
      } else {
        late.close();
      }
      inFlight.add(start(admission, scope));
      for (int i = 0; i < 6 && decisions.size() == 1; i++) {
        turn(admission, scope, clock, inFlight, decisions);
      }

      assertTrue("The first window after it ended judged the raise: " + describe(decisions),
          decisions.size() > 1 && decisions.get(1).previous == 2);
      while (!inFlight.isEmpty()) {
        inFlight.removeFirst().close();
      }
    }
  }

  // Answers the attempt in flight longest, after 20 ms, and starts another in its place unless that
  // judged the raise.
  private static void turn(AttemptAdmission admission, OperationScope scope, AtomicLong clock, ArrayDeque<AttemptAdmission.Attempt> inFlight,
      List<ConcurrencyController.Decision> decisions) {
    clock.addAndGet(TimeUnit.MILLISECONDS.toNanos(20));
    int judged = decisions.size();
    decisions.addAll(inFlight.removeFirst().complete(answer(), true));
    if (decisions.size() == judged) {
      inFlight.add(start(admission, scope));
    }
  }

  private static AttemptAdmission.Attempt start(AttemptAdmission admission, OperationScope scope) {
    AttemptAdmission.Attempt attempt = admission.admit(false, scope);
    assertTrue(attempt.tryStart());
    return attempt;
  }

  private static ConcurrencyController.Sample answer() {
    return new ConcurrencyController.Sample(true, false, false, -1, 1, TimeUnit.MILLISECONDS.toNanos(1), 0);
  }

  private static ConcurrencyController.Sample temporaryFailure() {
    return new ConcurrencyController.Sample(false, false, true, -1, 8, TimeUnit.MILLISECONDS.toNanos(10), 0);
  }

  private static ConcurrencyController.Sample throttled(long retryAfterNanos) {
    return new ConcurrencyController.Sample(false, true, true, retryAfterNanos, 8, TimeUnit.MILLISECONDS.toNanos(10), 0);
  }

  private static String describe(List<ConcurrencyController.Decision> decisions) {
    return decisions.stream().map(d -> d.target + " " + d.previous + "->" + d.current).collect(Collectors.joining(", "));
  }

  private static OperationScope scope() {
    return OperationScope.open("the test", null, null, URI.create("https://app.files.com"));
  }

  private static boolean isWarmStart(ConcurrencyController.Decision decision) {
    return decision.target == ConcurrencyDecision.WARM_START_RAISED || decision.target == ConcurrencyDecision.WARM_START_FAST_LINK
        || decision.target == ConcurrencyDecision.WARM_START_SETTLED;
  }

  private static <T> T last(List<T> list) {
    return list.get(list.size() - 1);
  }

  // The settings of an automatic upload to S3 with the startup and cap given (0 for none).
  private static ConcurrencySettings s3(UploadStartup startup, int cap) {
    return ConcurrencySettings.automatic(ConcurrencyTuning.scaledS3(), cap, startup);
  }

  private static ConcurrencySettings automatic(ConcurrencyTuning tuning) {
    return ConcurrencySettings.automatic(tuning, 0, UploadStartup.AGGRESSIVE);
  }

  // The lengths of a file's parts of size bytes, the last holding the rest.
  private static List<Long> parts(long length, long size) {
    List<Long> parts = new ArrayList<Long>();
    for (long offset = 0; offset < length; offset += size) {
      parts.add(Math.min(size, length - offset));
    }
    return parts;
  }

  // How long the requests of a cloud batch of one-part files of 1 MiB took to answer, in milliseconds,
  // each times scale, count of them: the fifth to the ninety-fifth percentile of one upload's, a tenth
  // apart, picked by a fixed sequence of pseudo-random draws (a linear congruential generator from draw),
  // so that every run draws the same.
  private static List<Long> cloudAnswerTimes(int draw, int count, long scale) {
    int[] percentiles = {44, 47, 51, 56, 60, 65, 71, 78, 89, 108};
    List<Long> times = new ArrayList<Long>();
    long state = draw;
    for (int i = 0; i < count; i++) {
      state = (state * 1664525 + 1013904223) & 0xFFFFFFFFL;
      times.add(scale * percentiles[(int) ((state >>> 16) % percentiles.length)]);
    }
    return times;
  }

  // Automatic tuning that isolates the behavior a test is about: short windows, a step at a time
  // after each measured window, and no latency rules.
  private static final class TuningBuilder {
    private final int start;
    private final int max;
    private int windowAttempts = 3;
    private int growthInterval = 1;
    private int growthStep = 1;
    private double probeGain = 10;
    private int plateau;
    private double gainPerAddedPart = 5;
    private int probeWindows = 2;
    private double latencyGrowthQueueLimit;
    private double latencyBackoffQueueLimit;

    TuningBuilder(int start, int max) {
      this.start = start;
      this.max = max;
      this.plateau = max;
    }

    TuningBuilder windowAttempts(int value) {
      windowAttempts = value;
      return this;
    }

    TuningBuilder growthInterval(int value) {
      growthInterval = value;
      return this;
    }

    TuningBuilder growthStep(int value) {
      growthStep = value;
      return this;
    }

    TuningBuilder probeGain(double value) {
      probeGain = value;
      return this;
    }

    TuningBuilder plateau(int value) {
      plateau = value;
      return this;
    }

    TuningBuilder gainPerAddedPart(double value) {
      gainPerAddedPart = value;
      return this;
    }

    TuningBuilder probeWindows(int value) {
      probeWindows = value;
      return this;
    }

    TuningBuilder latencyGrowthQueueLimit(double value) {
      latencyGrowthQueueLimit = value;
      return this;
    }

    TuningBuilder latencyBackoffQueueLimit(double value) {
      latencyBackoffQueueLimit = value;
      return this;
    }

    ConcurrencyTuning build() {
      return new ConcurrencyTuning(UploadDestination.S3, start, 1, max, max, 1L << 30, 0, windowAttempts, growthInterval, growthStep, probeGain,
          plateau, gainPerAddedPart, probeWindows, 50, 1, latencyGrowthQueueLimit, latencyBackoffQueueLimit, -1, 0, 0, 0, 0, -1, 0, 50,
          SECOND);
    }
  }

  // Feeds a controller whole windows of successful requests on a clock it controls: every window runs
  // the target's requests together, each delivering 100 bytes over the window's duration.
  private static final class ControllerDriver {
    final ConcurrencyController controller;
    final List<ConcurrencyController.Decision> records = new ArrayList<ConcurrencyController.Decision>();
    long now = SECOND;
    // As a shared scope's controller: every attached file in attach order, with the sizes of its parts
    // sent, and when each of the first file's parts was answered.
    final List<String> paths = new ArrayList<String>();
    final List<PartSizes> sizes = new ArrayList<PartSizes>();
    final List<PartSizeFeedback> feedback = new ArrayList<PartSizeFeedback>();
    final List<List<Long>> sent = new ArrayList<List<Long>>();
    final List<Long> firstAnswered = new ArrayList<Long>();

    ControllerDriver(ConcurrencyTuning tuning) {
      this(new ConcurrencyController(automatic(tuning), null, false));
    }

    ControllerDriver(ConcurrencyController controller) {
      this.controller = controller;
    }

    void window(long duration) {
      int requests = controller.target();
      int epoch = controller.epoch();
      List<Long> attempts = send(requests);
      now += duration;
      for (int i = 0; i < requests; i++) {
        controller.record(now, epoch, new ConcurrencyController.Sample(true, false, false, -1, 100, duration, 0), records);
        controller.released(now, true, attempts.get(i));
      }
    }

    // Attaches the size feedback of a file of the shared scope, which plans its parts with sizes, as an
    // upload of a scope does that chooses its sizes. The first attached is the one sizedRound feeds.
    void attachSizing(String path, PartSizes plan, PartSizeFeedback sizer) {
      paths.add(path);
      sizes.add(plan);
      feedback.add(sizer);
      sent.add(new ArrayList<Long>());
      controller.attachSizing(now, sizer);
    }

    List<Long> sentSizes(String path) {
      return sent.get(paths.indexOf(path));
    }

    long sizeOf(String path) {
      return sizes.get(paths.indexOf(path)).choice().size;
    }

    // One round of the target's requests, shared by the attached files in turn (request i is a part
    // of file i % count), each in the size its file chooses as the round starts: they start
    // together, all take exactly duration whatever their sizes, and are answered together in order.
    void sharedRound(long duration) {
      int requests = controller.target();
      int epoch = controller.epoch();
      List<PartSizes.Choice> chosen = new ArrayList<PartSizes.Choice>();
      for (PartSizes plan : sizes) {
        chosen.add(plan.choice());
      }
      List<Long> attempts = send(requests);
      now += duration;
      for (int i = 0; i < requests; i++) {
        int file = i % sizes.size();
        PartSizes.Choice size = chosen.get(file);
        controller.record(now, epoch, new ConcurrencyController.Sample(true, false, false, -1, size.size, duration, size.epoch,
            feedback.get(file)), records);
        controller.released(now, true, attempts.get(i));
        sent.get(file).add(size.size);
        if (file == 0) {
          firstAnswered.add(now);
        }
      }
    }

    // One round of the target's requests, all parts of the first attached file in the size it
    // chooses now: they start together, each takes exactly its bytes at bytesPerSecond, however many
    // are in flight, and they are answered together.
    void sizedRound(double bytesPerSecond) {
      int requests = controller.target();
      int epoch = controller.epoch();
      PartSizes.Choice size = sizes.get(0).choice();
      List<Long> attempts = send(requests);
      long duration = (long) (size.size / bytesPerSecond * 1e9);
      now += duration;
      for (int i = 0; i < requests; i++) {
        controller.record(now, epoch, new ConcurrencyController.Sample(true, false, false, -1, size.size, duration, size.epoch, feedback.get(0)),
            records);
        controller.released(now, true, attempts.get(i));
        sent.get(0).add(size.size);
        firstAnswered.add(now);
      }
    }

    // How many of the first attached file's parts were answered after from and no later than to.
    int partsAnswered(long from, long to) {
      return (int) firstAnswered.stream().filter(at -> at > from && at <= to).count();
    }

    List<ConcurrencyController.Decision> sizing() {
      return records.stream().filter(d -> d.size != null).collect(Collectors.toList());
    }

    List<ConcurrencyController.Decision> decisions() {
      return records.stream().filter(d -> d.target != null).collect(Collectors.toList());
    }

    // Requests sent and answered one after another, each of 100 bytes taking duration.
    void oneAtATime(int requests, long duration) {
      for (int i = 0; i < Math.max(requests, controller.target()); i++) {
        int epoch = controller.epoch();
        long attempt = send(1).get(0);
        now += duration;
        controller.record(now, epoch, new ConcurrencyController.Sample(true, false, false, -1, 100, duration, 0), records);
        controller.released(now, true, attempt);
      }
    }

    void fail(int requests) {
      int epoch = controller.epoch();
      for (long attempt : send(requests)) {
        controller.record(now, epoch, temporaryFailure(), records);
        controller.released(now, true, attempt);
      }
    }

    void throttle(int requests, long retryAfter) {
      int epoch = controller.epoch();
      for (long attempt : send(requests)) {
        controller.record(now, epoch, throttled(retryAfter), records);
        controller.released(now, true, attempt);
      }
    }

    private List<Long> send(int requests) {
      List<Long> attempts = new ArrayList<Long>();
      for (int i = 0; i < requests; i++) {
        controller.granted(now);
        attempts.add(controller.sending(now));
      }
      return attempts;
    }
  }

  // Sends one upload's parts through its real controller over a modeled link, on a clock the test
  // controls, a millisecond at a time, as AttemptAdmission would: a part starts while fewer than the
  // target are admitted and being sent, and nothing is paused; its answer is recorded and its permit
  // given back. A request that finds no connection idle opens one, which carries nothing for connect;
  // an answered request leaves its connection idle, and the next request carries on it at once. Each
  // request has its share of the link, never more than requestBytesPerSecond, and is answered once the
  // link has carried all of it (and answerWait has passed), with the time since it was sent, its
  // connecting included, as its duration. An upload that chooses its part sizes plans each part as it
  // sends it, in the size chosen then.
  private static final class LinkDriver {
    private static final long TICK = TimeUnit.MILLISECONDS.toNanos(1);

    final ConcurrencyController controller;
    final List<ConcurrencyController.Decision> records = new ArrayList<ConcurrencyController.Decision>();
    final List<Long> answered = new ArrayList<Long>();
    final List<Long> answeredLengths = new ArrayList<Long>();
    // When each request answered was sent and answered.
    final List<long[]> requests = new ArrayList<long[]>();
    // How many windows the controller had measured, and when.
    final List<long[]> measured = new ArrayList<long[]>();
    final ArrayDeque<Long> parts;
    final PartSizes sizes;
    int sent;
    int peakInFlight;
    private final List<Request> sending = new ArrayList<Request>();
    private final Map<Integer, Double> slowRequests = new HashMap<Integer, Double>();
    private final long connect;
    private long planned;
    private long unplanned;
    private double linkBytesPerSecond;
    private double requestBytesPerSecond;
    private long answerWait;
    private int idleConnections;
    private List<Long> requestTimes;
    private long now = SECOND;

    LinkDriver(ConcurrencySettings settings, List<Long> partLengths, double linkBytesPerSecond, double requestBytesPerSecond, long connect) {
      this(settings, null, null, partLengths, 0, linkBytesPerSecond, requestBytesPerSecond, connect, 0);
    }

    LinkDriver(ConcurrencySettings settings, PartSizes sizes, PartSizeFeedback feedback, long sizedBytes, double linkBytesPerSecond,
        double requestBytesPerSecond, long connect, long answerWait) {
      this(settings, sizes, feedback, new ArrayList<Long>(), sizedBytes, linkBytesPerSecond, requestBytesPerSecond, connect, answerWait);
    }

    private LinkDriver(ConcurrencySettings settings, PartSizes sizes, PartSizeFeedback feedback, List<Long> partLengths, long sizedBytes,
        double linkBytesPerSecond, double requestBytesPerSecond, long connect, long answerWait) {
      this.controller = new ConcurrencyController(settings, feedback, settings.warmStarts());
      this.sizes = sizes;
      this.parts = new ArrayDeque<Long>(partLengths);
      this.unplanned = sizedBytes;
      this.linkBytesPerSecond = linkBytesPerSecond;
      this.requestBytesPerSecond = requestBytesPerSecond;
      this.connect = connect;
      this.answerWait = answerWait;
    }

    boolean done() {
      return parts.isEmpty() && unplanned == 0 && sending.isEmpty();
    }

    TransferConcurrency snapshot() {
      return controller.snapshot(now);
    }

    List<ConcurrencyController.Decision> decisions() {
      return records.stream().filter(d -> d.target != null).collect(Collectors.toList());
    }

    List<ConcurrencyController.Decision> sizeDecisions() {
      return records.stream().filter(d -> d.size != null).collect(Collectors.toList());
    }

    int measuredBy(long at) {
      int windows = 0;
      for (long[] measure : measured) {
        if (measure[0] <= at) {
          windows = (int) measure[1];
        }
      }
      return windows;
    }

    int answeredBy(long at) {
      return (int) answered.stream().filter(time -> time <= at).count();
    }

    // Runs until every part has been answered, for limit, or until stop says so.
    void run(long limit, BooleanSupplier stop) {
      long end = now + limit;
      while (!done() && now < end && !(stop != null && stop.getAsBoolean())) {
        admit();
        now += TICK;
        transfer();
      }
    }

    // From now on, the link carries linkBytesPerSecond, of which a request carries at most
    // requestBytesPerSecond, and storage answers after answerWait, unless it is negative.
    void changeLink(double linkBytesPerSecond, double requestBytesPerSecond, long answerWait) {
      this.linkBytesPerSecond = linkBytesPerSecond;
      this.requestBytesPerSecond = requestBytesPerSecond;
      if (answerWait >= 0) {
        this.answerWait = answerWait;
      }
    }

    // The number-th request sent (from 1) carries at most bytesPerSecond.
    void slowRequest(int number, double bytesPerSecond) {
      slowRequests.put(number, bytesPerSecond);
    }

    // Each request sent carries its part alone in the next of times, in turn, however many are in
    // flight while the link has room.
    void varyRequests(List<Long> times) {
      requestTimes = times;
    }

    void admit() {
      while ((!parts.isEmpty() || unplanned > 0) && controller.permitted() < controller.target() && controller.inFlight() < controller.target()
          && !controller.isPaused(now)) {
        controller.granted(now);
        long attempt = controller.sending(now);
        boolean reused = idleConnections > 0;
        if (reused) {
          idleConnections--;
        }
        long length;
        int sizeEpoch = 0;
        if (sizes == null) {
          length = parts.removeFirst();
        } else {
          PartSizes.Choice chosen = sizes.choice();
          length = sizes.lengthFor(sent + 1, planned, chosen);
          sizeEpoch = chosen.epoch;
          planned += length;
          unplanned -= length;
        }
        sending.add(new Request(length, sizeEpoch, now, controller.epoch(), reused, ++sent, attempt));
      }
      peakInFlight = Math.max(peakInFlight, sending.size());
    }

    // The first request being sent fails as a connection failure would.
    void fail() {
      Request request = sending.remove(0);
      controller.record(now, request.epoch, new ConcurrencyController.Sample(false, false, true, -1, 0, now - request.startedAt, 0), records);
      controller.released(now, true, request.attempt);
    }

    String describe() {
      return "peak " + peakInFlight + ", target " + controller.target() + ", at " + (now - SECOND) / 1e9 + " s; "
          + records.stream().map(d -> (d.at - SECOND) / 1_000_000 + "ms " + d).collect(Collectors.joining("; "));
    }

    private void transfer() {
      List<Request> connected = new ArrayList<Request>();
      for (Request request : sending) {
        if (request.carried < request.length && (request.reused || now - request.startedAt >= connect)) {
          connected.add(request);
        }
      }
      // A request that cannot carry its equal share carries what it can; the others share the rest.
      double equal = connected.isEmpty() ? 0 : linkBytesPerSecond / connected.size();
      List<Request> limited = new ArrayList<Request>();
      double left = linkBytesPerSecond;
      for (Request request : connected) {
        if (limit(request) < equal) {
          limited.add(request);
          left -= limit(request);
        }
      }
      int others = connected.size() - limited.size();
      for (Request request : connected) {
        double bytesPerSecond = limited.contains(request) ? limit(request) : Math.min(limit(request), left / others);
        request.carried = Math.min(request.length, request.carried + (bytesPerSecond / 1000));
        request.carriedAt = request.carried >= request.length ? now : 0;
      }
      for (Request request : new ArrayList<Request>(sending)) {
        if (request.carried < request.length || now - request.carriedAt < answerWait) {
          continue;
        }
        sending.remove(request);
        idleConnections++;
        answered.add(now);
        answeredLengths.add(request.length);
        requests.add(new long[]{request.startedAt, now});
        controller.record(now, request.epoch, new ConcurrencyController.Sample(true, false, false, -1, request.length, now - request.startedAt,
            request.sizeEpoch), records);
        controller.released(now, true, request.attempt);
        measured.add(new long[]{now, controller.snapshot(now).getMeasuredWindows()});
      }
    }

    // How fast a request can carry, by itself.
    private double limit(Request request) {
      double own = requestTimes == null ? requestBytesPerSecond
          : Math.min(requestBytesPerSecond, request.length / (requestTimes.get((request.number - 1) % requestTimes.size()) / 1e9));
      Double slow = slowRequests.get(request.number);
      return slow != null ? Math.min(slow, own) : own;
    }

    private static final class Request {
      final long length;
      final int sizeEpoch;
      final long startedAt;
      final int epoch;
      final boolean reused;
      final int number;
      // The number the controller gave it as it was sent.
      final long attempt;
      double carried;
      long carriedAt;

      Request(long length, int sizeEpoch, long startedAt, int epoch, boolean reused, int number, long attempt) {
        this.length = length;
        this.sizeEpoch = sizeEpoch;
        this.startedAt = startedAt;
        this.epoch = epoch;
        this.reused = reused;
        this.number = number;
        this.attempt = attempt;
      }
    }
  }
}
