package com.files.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import org.junit.After;
import org.junit.Test;

public class UploadFileAdmissionTest {
  private final ExecutorService callers = Executors.newCachedThreadPool();

  @After
  public void tearDown() {
    callers.shutdownNow();
  }

  // An admission of the test's own, whose targets and clock the test sets, and the uploads it admitted.
  private static final class Gate {
    final AtomicReference<UploadFileAdmission.Startup> targets = new AtomicReference<>();
    final AtomicLong now = new AtomicLong();
    final UploadFileAdmission admission = new UploadFileAdmission(targets::get, now::get);
    final List<String> admitted = new CopyOnWriteArrayList<>();
    final List<UploadFileAdmission.Permit> held = new CopyOnWriteArrayList<>();

    void step() {
      now.addAndGet(UploadFileAdmission.SUPPLY_STEP_NANOS);
    }
  }

  @Test(timeout = 20000)
  public void waitingUploadsAreAdmittedInTheOrderTheyAskedAndAPlaceGivenBackGoesToTheFirstOfThem() throws Exception {
    Gate gate = new Gate();
    // No target yet: six places, all taken.
    for (int i = 0; i < 6; i++) {
      try (OperationScope scope = scope()) {
        gate.held.add(gate.admission.admit(scope));
      }
    }
    final Future<?> first = ask(gate, "first");
    awaitTrue(() -> gate.admission.waiting() == 1);
    final Future<?> second = ask(gate, "second");
    awaitTrue(() -> gate.admission.waiting() == 2);

    gate.held.remove(0).close();
    first.get(10, TimeUnit.SECONDS);
    assertEquals("the place went to the first waiting, at once", 6, gate.admission.running());
    // One asking only now waits behind the second, though it never saw the first wait.
    final Future<?> late = ask(gate, "late");
    awaitTrue(() -> gate.admission.waiting() == 2);
    assertFalse(second.isDone());

    gate.held.remove(0).close();
    second.get(10, TimeUnit.SECONDS);
    assertFalse("no place for the late one yet", late.isDone());
    gate.held.remove(0).close();
    late.get(10, TimeUnit.SECONDS);
    assertEquals(Arrays.asList("first", "second", "late"), gate.admitted);
  }

  @Test(timeout = 30000)
  public void theDefaultStartupAddsUploadsByStepsWhilePartsAreTooFewUntilEveryOfferedUploadRuns() throws Exception {
    // A warm start at one part, with no part waiting: four places (the floor), and 140 uploads asking,
    // none of which ends.
    Gate warm = new Gate();
    warm.targets.set(new UploadFileAdmission.Startup(1, true, true, 0));
    for (int i = 0; i < 140; i++) {
      ask(warm, "upload-" + i);
    }
    awaitTrue(() -> warm.admitted.size() == 4 && warm.admission.waiting() == 136);
    stays(warm, 4);
    // Each step of the clock admits one more.
    for (int expected = 5; expected <= 7; expected++) {
      warm.step();
      final int count = expected;
      awaitTrue(() -> warm.admitted.size() == count);
      stays(warm, expected);
    }
    // With enough parts waiting across the targets, the steps admit no more.
    warm.targets.set(new UploadFileAdmission.Startup(1, true, true, 3));
    for (int step = 0; step < 3; step++) {
      warm.step();
      Thread.sleep(150);
    }
    stays(warm, 7);
    // A raise of the warm start, from one part to 40, supplies uploads in proportion: here every one
    // offered, more than the CLI's 128 files of a job, and no more than were offered.
    warm.targets.set(new UploadFileAdmission.Startup(40, true, true, 3));
    awaitTrue(() -> warm.admitted.size() == 140);
    assertEquals(0, warm.admission.waiting());

    // A target that did not warm-start, such as a Files Agent's at two parts: its uploads get one more
    // a step too while its parts are too few to keep it busy, and no more once they are not.
    Gate agent = new Gate();
    agent.targets.set(new UploadFileAdmission.Startup(2, false, false, 0));
    for (int i = 0; i < 10; i++) {
      ask(agent, "agent-" + i);
    }
    awaitTrue(() -> agent.admitted.size() == 4 && agent.admission.waiting() == 6);
    stays(agent, 4);
    for (int expected = 5; expected <= 6; expected++) {
      agent.step();
      final int count = expected;
      awaitTrue(() -> agent.admitted.size() == count);
      stays(agent, expected);
    }
    agent.targets.set(new UploadFileAdmission.Startup(2, false, false, 2));
    for (int step = 0; step < 3; step++) {
      agent.step();
      Thread.sleep(150);
    }
    stays(agent, 6);
  }

  private Future<?> ask(Gate gate, String name) {
    return callers.submit(() -> {
      try (OperationScope scope = scope()) {
        gate.held.add(gate.admission.admit(scope));
        gate.admitted.add(name);
      }
      return null;
    });
  }

  private static OperationScope scope() {
    return OperationScope.open("the test", null, null, URI.create("https://app.files.com"));
  }

  // The admitted count holds while the first waiting upload looks again more than once.
  private static void stays(Gate gate, int count) throws InterruptedException {
    Thread.sleep(300);
    assertEquals(count, gate.admitted.size());
    assertEquals(count, gate.admission.running());
  }

  private static void awaitTrue(BooleanSupplier condition) throws InterruptedException {
    long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
    while (!condition.getAsBoolean()) {
      assertTrue("condition not reached", System.nanoTime() < end);
      Thread.sleep(5);
    }
  }
}
