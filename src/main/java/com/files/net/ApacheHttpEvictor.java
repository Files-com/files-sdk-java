package com.files.net;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class ApacheHttpEvictor {
  private final ScheduledExecutorService scheduler;
  private final PoolingHttpClientConnectionManager connectionManager;

  public ApacheHttpEvictor(PoolingHttpClientConnectionManager connectionManager, long idleTimeoutMillis) {
    this.connectionManager = connectionManager;
    this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r);
      t.setName("http-connection-evictor");
      t.setDaemon(true);
      return t;
    });

    scheduler.scheduleAtFixedRate(() -> {
      try {
        connectionManager.closeExpiredConnections();
        connectionManager.closeIdleConnections(idleTimeoutMillis, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
        // Should never be here
      }
    }, 30, 30, TimeUnit.SECONDS);
  }

  public void shutdown() {
    scheduler.shutdown();
  }
}
