package com.files;

import static org.junit.Assert.assertEquals;

import com.files.util.BufferPool;
import org.junit.Test;

public class BufferPoolTest {
  // What the pool reserves beyond its cached arrays: the arrays it has lent out. Creating or
  // closing a buffer must leave it unchanged unless a pool loan starts or ends.
  private static long reservedForLoans() {
    long cached = (long) BufferPool.availableTinyBufferCount() * BufferPool.T_BUFFER_SIZE
        + (long) BufferPool.availableSmallBufferCount() * BufferPool.S_BUFFER_SIZE
        + (long) BufferPool.availableMediumBufferCount() * BufferPool.M_BUFFER_SIZE
        + (long) BufferPool.availableLargeBufferCount() * BufferPool.L_BUFFER_SIZE;
    return BufferPool.getTotalAllocated() - cached;
  }

  @Test
  public void reservationsCoverExactlyTheArraysThePoolHolds() {
    long lent = reservedForLoans();

    // Direct buffers hold no reservation, so closing them, healthy or not, gives none back.
    new BufferPool.Buffer(1).close();
    assertEquals(lent, reservedForLoans());
    new BufferPool.Buffer(new byte[3]).closeAsUnhealthy();
    assertEquals(lent, reservedForLoans());
    new BufferPool.Buffer(BufferPool.T_BUFFER_SIZE).closeAsUnhealthy();
    assertEquals(lent, reservedForLoans());

    // A direct array of a size class enters the cache only with a reservation that covers it.
    new BufferPool.Buffer(new byte[BufferPool.T_BUFFER_SIZE]).close();
    assertEquals(lent, reservedForLoans());

    // A pool loan keeps its reservation while lent and while cached, and gives it back exactly
    // once when it is closed as unhealthy.
    BufferPool.Buffer loan = BufferPool.needBuffer(1);
    int loanSize = loan.getBuffer().length;
    assertEquals(lent + loanSize, reservedForLoans());
    loan.close();
    assertEquals(lent, reservedForLoans());

    BufferPool.Buffer unhealthy = BufferPool.needBuffer(1);
    int unhealthySize = unhealthy.getBuffer().length;
    int reservedWhileLent = BufferPool.getTotalAllocated();
    unhealthy.closeAsUnhealthy();
    assertEquals(reservedWhileLent - unhealthySize, BufferPool.getTotalAllocated());
    assertEquals(lent, reservedForLoans());
  }
}
