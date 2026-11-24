package com.files.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.files.FilesClient;
import com.files.net.ClientRuntime;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;

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

  /**
   * Sets the client this object's own operations run with.
   *
   * @param client the client
   */
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
   * Waits, up to the timeout, for the copy, move or delete that returned this action to finish, and
   * returns its completed file migration. This is opt-in: {@code File.copy}, {@code File.move} and
   * {@code File.beginDelete} still return this action as soon as the API accepts the request,
   * without waiting. To wait for an action that may have finished already, without a file
   * migration, use {@link #awaitCompletion(long, java.util.concurrent.TimeUnit)}.
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
   * <p>The API gives a file migration ID when the action continues in the background: a copy or
   * move whose status is {@code pending}, and a delete run as a file migration, including while it
   * is {@code processing}. An action without one has nothing to poll, and this method throws
   * {@code IllegalStateException}; it does not assume that such an action has finished, even when
   * its status says so. {@code File.delete} returns no action, so a delete made with it cannot be
   * waited for; one made with {@code File.beginDelete} can.
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
   * Waits, up to the timeout, for the copy, move or delete that returned this action to finish,
   * including one that had finished when the API answered. This is opt-in, like
   * {@link #waitForCompletion(long, java.util.concurrent.TimeUnit)}, which it uses for an action that
   * continues in the background; it returns nothing, as an action that finished at once has no file
   * migration.
   *
   * <ul>
   *   <li>{@code completed} or {@code skipped}: returns at once, without asking the API. A skipped
   *       action had no work to do; {@link #getStatus()} tells it from one that was carried out.</li>
   *   <li>{@code failed} or {@code cancelled}: throws {@link UnsuccessfulActionException} at once,
   *       without asking the API.</li>
   *   <li>Any other status, or none, with a {@link #getFileMigrationId() file migration ID}: waits for
   *       that file migration exactly as {@code waitForCompletion} does, with this action's client and
   *       request options, and with its timeout, interruption and failures, including
   *       {@link UnsuccessfulMigrationException}.</li>
   *   <li>Any other status, or none, without one: throws {@code IllegalStateException}, as nothing
   *       shows that the action finished and nothing can be polled.</li>
   * </ul>
   *
   * <p>Ending the wait, by its timeout or an interruption, does not stop the action itself.
   *
   * <pre>{@code
   * FileAction deletion = File.beginDelete("reports/2023", parameters, null);
   * deletion.awaitCompletion(10, TimeUnit.MINUTES);
   * // The folder is gone, or was already, so its path can be used again.
   * }</pre>
   *
   * @param timeout the longest time to wait; must be positive
   * @param unit the unit of {@code timeout}; must not be {@code null}
   * @throws UnsuccessfulActionException if the action's status is {@code failed} or
   *     {@code cancelled}
   * @throws UnsuccessfulMigrationException if the file migration waited for ends as {@code failed}
   *     or {@code cancelled}
   * @throws java.util.concurrent.TimeoutException if the file migration has not finished when the
   *     timeout elapses
   * @throws InterruptedException if the waiting thread is interrupted
   * @throws IllegalStateException if the action has not finished and has no file migration ID
   * @throws IllegalArgumentException if {@code timeout} is not positive
   * @throws NullPointerException if {@code unit} is {@code null}
   */
  public void awaitCompletion(long timeout, java.util.concurrent.TimeUnit unit)
      throws InterruptedException, java.util.concurrent.TimeoutException {
    if (unit == null) {
      throw new NullPointerException("unit");
    }
    if (timeout <= 0) {
      throw new IllegalArgumentException("timeout must be positive");
    }
    final String actionStatus = getStatus();
    if ("completed".equals(actionStatus) || "skipped".equals(actionStatus)) {
      return;
    }
    if ("failed".equals(actionStatus) || "cancelled".equals(actionStatus)) {
      throw new UnsuccessfulActionException(this);
    }
    if (getFileMigrationId() == null) {
      throw new IllegalStateException("This file action has not finished and has no file migration to wait for; its status is "
          + actionStatus);
    }
    waitForCompletion(timeout, unit);
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

  /**
   * Thrown by {@link FileAction#awaitCompletion} when the action itself reports the status
   * {@code failed} or {@code cancelled}. The API gives no failure message for an action, so the
   * message gives only its status and, if it has one, its file migration ID.
   */
  public static class UnsuccessfulActionException extends com.files.exceptions.SdkException {
    private static final long serialVersionUID = 1L;

    private final transient FileAction fileAction;

    private UnsuccessfulActionException(FileAction fileAction) {
      super("The file action ended with status " + fileAction.getStatus()
          + (fileAction.getFileMigrationId() == null ? "" : " (file migration " + fileAction.getFileMigrationId() + ")"));
      this.fileAction = fileAction;
    }

    /**
     * Returns the file action as the API returned it, with its status.
     *
     * @return the file action
     */
    public FileAction getFileAction() {
      return fileAction;
    }
  }

  /**
   * Creates a {@code FileAction} with no attributes set.
   */
  public FileAction() {
    this(null, null);
  }

  /**
   * Creates a {@code FileAction} with the given attributes.
   *
   * @param parameters the attributes, by their API names
   */
  public FileAction(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  /**
   * Creates a {@code FileAction} with the given attributes and the request options its own
   * operations send.
   *
   * @param parameters the attributes, by their API names
   * @param options the request options, such as {@code api_key} or {@code workspace_id}
   */
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

  /**
   * Gets {@link #status}.
   *
   * @return the value of {@link #status}
   */
  public String getStatus() {
    return status;
  }

  /**
   * If status is pending, this is the id of the File Migration to check for status updates.  Delete actions include it whenever the deletion runs as a File Migration, including while it is processing.
   */
  @JsonProperty("file_migration_id")
  public Long fileMigrationId;

  /**
   * Gets {@link #fileMigrationId}.
   *
   * @return the value of {@link #fileMigrationId}
   */
  public Long getFileMigrationId() {
    return fileMigrationId;
  }


}
