package com.files.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.ListIterator;
import com.files.net.ClientRuntime;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.FilesInputStream;
import com.files.util.ModelUtils;
import com.files.util.PathUtils;
import com.files.util.UrlUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileAction implements ModelInterface {
  private HashMap<String, Object> options;
  private FilesClient filesClient;

  /**
   * Sets the request options, such as {@code api_key} or {@code workspace_id}, that this object's
   * own operations send. The map is copied; options that control a single call, such as a
   * {@code cancellation} token or a {@code timeout}, are not kept.
   */
  public void setOptions(HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
  }

  @JsonIgnore
  @Override
  public void setClient(FilesClient client) {
    this.filesClient = client;
  }

  // The client this object's own operations run with: the one that returned it, or one set with
  // setClient, and otherwise the default client as it is when the operation runs.
  private FilesClient filesClient() {
    return filesClient != null ? filesClient : FilesClient.defaultClient();
  }

  // One mapper for every FileAction, built once: a configured Jackson mapper is safe to share
  // between threads, and it uses a copy of its date format each time it reads or writes a date.
  private static final ObjectMapper MAPPER = JsonMapper
      .builder()
      .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
      .addModule(new SimpleModule().addSerializer(BigDecimal.class, ToStringSerializer.instance))
      .build();

  private static final long FIRST_POLL_DELAY_MILLIS = 1000;
  private static final long MAX_POLL_DELAY_MILLIS = 10000;

  /**
   * Waits, up to the timeout, for the copy or move that returned this action to finish, and returns
   * its completed file migration. This is opt-in: {@code File.copy} and {@code File.move} still
   * return this action as soon as the API accepts the request, without waiting.
   *
   * <p>The wait looks up this action's {@link #getFileMigrationId() file migration}, as
   * {@link FileMigration#find(Long, HashMap, HashMap)} does, with the client that returned this
   * action and the options, such as {@code api_key} and {@code api_root}, of the request that
   * returned it. It polls at once, then after one
   * second, doubling the delay up to ten seconds. A migration status of {@code completed} ends the
   * wait; {@code failed} or {@code cancelled} ends it with an
   * {@link UnsuccessfulMigrationException}; {@code pending}, {@code processing} and any status this
   * SDK does not know are treated as not finished.
   *
   * <p>The timeout bounds the wait: when it elapses, this method throws {@code TimeoutException},
   * even while a status request is still in flight. Interrupting the waiting thread ends the wait
   * the same way, with {@code InterruptedException}. Each status request runs on a background
   * daemon thread that this method starts for the wait. When the wait ends, this method submits no
   * further status requests and cancels a status request still in flight: its request is aborted,
   * and its result discarded.
   *
   * <p>A status request that fails, for example with an {@code ApiErrorException} or an
   * {@code ApiConnectionException}, ends the wait with that same exception.
   *
   * <p>The API documents a file migration ID only when the action's status is {@code pending}. An
   * action without one has nothing to poll, and this method throws {@code IllegalStateException};
   * it does not assume that such an action has finished. {@code File.delete} returns no action, so
   * deletes cannot be waited for.
   *
   * <pre>{@code
   * FileAction action = File.copy("reports/2024", parameters);
   * try {
   *   FileMigration migration = action.waitForCompletion(10, TimeUnit.MINUTES);
   *   System.out.println("Copied " + migration.getFilesMoved() + " files");
   * } catch (FileAction.UnsuccessfulMigrationException e) {
   *   System.err.println(e.getMessage());
   * } catch (TimeoutException e) {
   *   // Completion was not confirmed within the wait; the migration can be looked up again later.
   *   System.err.println("Completion not confirmed; migration " + action.getFileMigrationId());
   * }
   * }</pre>
   *
   * @param timeout the longest time to wait; must be positive
   * @param unit the unit of {@code timeout}; must not be {@code null}
   * @return the file migration, with status {@code completed}
   * @throws UnsuccessfulMigrationException if the migration ends as {@code failed} or
   *     {@code cancelled}
   * @throws java.util.concurrent.TimeoutException if the migration has not finished when the
   *     timeout elapses
   * @throws InterruptedException if the waiting thread is interrupted
   * @throws IllegalStateException if this action has no file migration ID
   * @throws IllegalArgumentException if {@code timeout} is not positive
   * @throws NullPointerException if {@code unit} is {@code null}
   */
  public FileMigration waitForCompletion(long timeout, java.util.concurrent.TimeUnit unit)
      throws InterruptedException, java.util.concurrent.TimeoutException {
    if (unit == null) {
      throw new NullPointerException("unit");
    }
    if (timeout <= 0) {
      throw new IllegalArgumentException("timeout must be positive");
    }
    final Long migrationId = fileMigrationId;
    if (migrationId == null) {
      throw new IllegalStateException("This file action has no file migration to wait for; its status is " + status);
    }
    // Each status request carries the wait's own cancellation, which ends one still in flight when
    // the wait ends.
    final com.files.CancellationToken stopPolling = new com.files.CancellationToken();
    final HashMap<String, Object> requestOptions = options != null ? new HashMap<String, Object>(options) : new HashMap<String, Object>();
    requestOptions.put(com.files.net.ClientRuntime.CANCELLATION_OPTION, stopPolling);
    final FilesClient client = filesClient();
    final long timeoutNanos = unit.toNanos(timeout);
    final long start = System.nanoTime();
    java.util.concurrent.ExecutorService poller = java.util.concurrent.Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "files-sdk-file-migration-" + migrationId);
      thread.setDaemon(true);
      return thread;
    });
    try {
      String lastStatus = null;
      long pollDelayMillis = FIRST_POLL_DELAY_MILLIS;
      while (true) {
        long remaining = timeoutNanos - (System.nanoTime() - start);
        if (remaining <= 0) {
          throw notFinished(migrationId, lastStatus);
        }
        java.util.concurrent.Future<FileMigration> poll =
            poller.submit(() -> client.fileMigrations().find(migrationId, null, requestOptions));
        FileMigration migration;
        try {
          migration = poll.get(remaining, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (java.util.concurrent.TimeoutException e) {
          poll.cancel(true);
          throw notFinished(migrationId, lastStatus);
        } catch (InterruptedException e) {
          poll.cancel(true);
          throw e;
        } catch (java.util.concurrent.ExecutionException e) {
          Throwable failure = e.getCause();
          if (failure instanceof RuntimeException) {
            throw (RuntimeException) failure;
          }
          if (failure instanceof Error) {
            throw (Error) failure;
          }
          throw new RuntimeException(failure);
        }

        lastStatus = migration.getStatus();
        if ("completed".equals(lastStatus)) {
          return migration;
        }
        if ("failed".equals(lastStatus) || "cancelled".equals(lastStatus)) {
          throw new UnsuccessfulMigrationException(migration);
        }

        remaining = timeoutNanos - (System.nanoTime() - start);
        if (remaining <= 0) {
          throw notFinished(migrationId, lastStatus);
        }
        java.util.concurrent.TimeUnit.NANOSECONDS.sleep(
            Math.min(remaining, java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(pollDelayMillis)));
        pollDelayMillis = Math.min(pollDelayMillis * 2, MAX_POLL_DELAY_MILLIS);
      }
    } finally {
      stopPolling.cancel();
      poller.shutdownNow();
    }
  }

  private static java.util.concurrent.TimeoutException notFinished(Long migrationId, String lastStatus) {
    return new java.util.concurrent.TimeoutException("File migration " + migrationId
        + " did not finish before the wait timed out; its last status was "
        + (lastStatus == null ? "not yet received" : lastStatus));
  }

  /**
   * Thrown by {@link FileAction#waitForCompletion} when the file migration ends with the status
   * {@code failed} or {@code cancelled}. The message gives the migration's ID, status and, if it
   * has one, its failure message.
   */
  public static class UnsuccessfulMigrationException extends com.files.exceptions.SdkException {
    private static final long serialVersionUID = 1L;

    private final transient FileMigration fileMigration;

    private UnsuccessfulMigrationException(FileMigration fileMigration) {
      super("File migration " + fileMigration.getId() + " ended with status " + fileMigration.getStatus()
          + (fileMigration.getFailureMessage() == null ? "" : ": " + fileMigration.getFailureMessage()));
      this.fileMigration = fileMigration;
    }

    /**
     * Returns the file migration as last read, with its status, failure message and log URL.
     *
     * @return the file migration
     */
    public FileMigration getFileMigration() {
      return fileMigration;
    }
  }

  public FileAction() {
    this(null, null);
  }

  public FileAction(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public FileAction(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = ClientRuntime.retainedOptions(options);
    try {
      ObjectReader objectReader = MAPPER.readerForUpdating(this);
      objectReader.readValue(MAPPER.writeValueAsString(parameters));
    } catch (JsonProcessingException e) {
      // TODO: error generation on constructor
    }
  }


  /**
   * Status of file operation.
   */
  @JsonProperty("status")
  public String status;

  public String getStatus() {
    return status;
  }

  /**
   * If status is pending, this is the id of the File Migration to check for status updates.  Delete actions include it whenever the deletion runs as a File Migration, including while it is processing.
   */
  @JsonProperty("file_migration_id")
  public Long fileMigrationId;

  public Long getFileMigrationId() {
    return fileMigrationId;
  }


}
