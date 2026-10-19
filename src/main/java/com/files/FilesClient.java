package com.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.files.models.Session;
import com.files.net.ApacheHttpEvictor;
import com.files.net.ClientRuntime;
import com.files.net.FilesResponse;
import com.files.net.HttpMethods;
import com.files.net.HttpMethods.RequestMethods;
import com.files.net.HttpTransport;
import com.files.net.Transfers;
import com.files.operations.ActionLogOperations;
import com.files.operations.ActionNotificationExportOperations;
import com.files.operations.ActionNotificationExportResultOperations;
import com.files.operations.AiAssistantPersonalityOperations;
import com.files.operations.AiTaskOperations;
import com.files.operations.ApiKeyOperations;
import com.files.operations.ApiRequestLogOperations;
import com.files.operations.AppOperations;
import com.files.operations.As2IncomingMessageOperations;
import com.files.operations.As2OutgoingMessageOperations;
import com.files.operations.As2PartnerOperations;
import com.files.operations.As2StationOperations;
import com.files.operations.AutomationLogOperations;
import com.files.operations.AutomationOperations;
import com.files.operations.AutomationRunOperations;
import com.files.operations.BandwidthSnapshotOperations;
import com.files.operations.BehaviorOperations;
import com.files.operations.BundleActionOperations;
import com.files.operations.BundleDownloadOperations;
import com.files.operations.BundleNotificationOperations;
import com.files.operations.BundleOperations;
import com.files.operations.BundleRecipientOperations;
import com.files.operations.BundleRegistrationOperations;
import com.files.operations.ChatSessionOperations;
import com.files.operations.ChildSiteManagementPolicyOperations;
import com.files.operations.ClickwrapOperations;
import com.files.operations.CustomDomainOperations;
import com.files.operations.DesktopConfigurationProfileOperations;
import com.files.operations.DnsRecordOperations;
import com.files.operations.EmailIncomingMessageOperations;
import com.files.operations.EmailLogOperations;
import com.files.operations.EventChannelOperations;
import com.files.operations.EventDeliveryAttemptOperations;
import com.files.operations.EventRecordOperations;
import com.files.operations.EventSubscriptionOperations;
import com.files.operations.EventTargetOperations;
import com.files.operations.ExavaultApiRequestLogOperations;
import com.files.operations.ExpectationEvaluationOperations;
import com.files.operations.ExpectationIncidentOperations;
import com.files.operations.ExpectationOperations;
import com.files.operations.ExternalEventOperations;
import com.files.operations.FileCommentOperations;
import com.files.operations.FileCommentReactionOperations;
import com.files.operations.FileMigrationLogOperations;
import com.files.operations.FileMigrationOperations;
import com.files.operations.FileOperations;
import com.files.operations.FolderOperations;
import com.files.operations.FormFieldSetOperations;
import com.files.operations.FtpActionLogOperations;
import com.files.operations.GpgKeyOperations;
import com.files.operations.GroupOperations;
import com.files.operations.GroupUserOperations;
import com.files.operations.HistoryExportOperations;
import com.files.operations.HistoryExportResultOperations;
import com.files.operations.HistoryOperations;
import com.files.operations.HolidayCalendarOperations;
import com.files.operations.HolidayRegionOperations;
import com.files.operations.InboundS3LogOperations;
import com.files.operations.InboxRecipientOperations;
import com.files.operations.InboxRegistrationOperations;
import com.files.operations.InboxUploadOperations;
import com.files.operations.IntegrationCentricProfileOperations;
import com.files.operations.InvoiceOperations;
import com.files.operations.IpAddressOperations;
import com.files.operations.KeyLifecycleRuleOperations;
import com.files.operations.LockOperations;
import com.files.operations.MetadataCategoryOperations;
import com.files.operations.NotificationOperations;
import com.files.operations.OutboundConnectionLogOperations;
import com.files.operations.PartnerChannelOperations;
import com.files.operations.PartnerChannelTemplateOperations;
import com.files.operations.PartnerOperations;
import com.files.operations.PartnerSiteOperations;
import com.files.operations.PartnerSiteRequestOperations;
import com.files.operations.PaymentOperations;
import com.files.operations.PendingWorkEventOperations;
import com.files.operations.PermissionOperations;
import com.files.operations.PublicHostingRequestLogOperations;
import com.files.operations.PublicKeyOperations;
import com.files.operations.RemoteBandwidthSnapshotOperations;
import com.files.operations.RemoteMountBackendOperations;
import com.files.operations.RemoteServerCredentialOperations;
import com.files.operations.RemoteServerOperations;
import com.files.operations.RequestOperations;
import com.files.operations.RestoreOperations;
import com.files.operations.ScheduleOperations;
import com.files.operations.ScheduledExportOperations;
import com.files.operations.ScimLogOperations;
import com.files.operations.SecretOperations;
import com.files.operations.SessionOperations;
import com.files.operations.SettingsChangeOperations;
import com.files.operations.SftpActionLogOperations;
import com.files.operations.SftpHostKeyOperations;
import com.files.operations.ShareGroupOperations;
import com.files.operations.SiemHttpDestinationEventOperations;
import com.files.operations.SiemHttpDestinationOperations;
import com.files.operations.SiteOperations;
import com.files.operations.SiteSubdomainRedirectOperations;
import com.files.operations.SnapshotOperations;
import com.files.operations.SsoEventOperations;
import com.files.operations.SsoStrategyOperations;
import com.files.operations.StyleOperations;
import com.files.operations.SyncLogOperations;
import com.files.operations.SyncOperations;
import com.files.operations.SyncRunOperations;
import com.files.operations.UsageDailySnapshotOperations;
import com.files.operations.UsageSnapshotOperations;
import com.files.operations.UserAdditionalEmailRecipientOperations;
import com.files.operations.UserCipherUseOperations;
import com.files.operations.UserLifecycleRuleOperations;
import com.files.operations.UserOperations;
import com.files.operations.UserRequestOperations;
import com.files.operations.UserSecurityEventOperations;
import com.files.operations.UserSftpClientUseOperations;
import com.files.operations.WebDavActionLogOperations;
import com.files.operations.WebhookTestOperations;
import com.files.operations.WorkspaceOperations;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * A Files.com API client: an API root, credentials, a workspace, retry and time limits, and the
 * connections its requests use. Make one with {@link #builder()}:
 *
 * <pre>{@code
 * try (FilesClient client = FilesClient.builder().apiKey("my-key").build()) {
 *   for (User user : client.users().list().listAutoPaging()) {
 *     System.out.println(user.getUsername());
 *   }
 * }
 * }</pre>
 *
 * <p>A client's settings never change after it is built, and several clients, with different
 * credentials or sites, can be used at the same time. Objects and listings a client returns keep
 * using it and the request options they were requested with: saving a returned
 * {@link com.files.models.User}, or loading the next page of a listing, sends the same
 * credentials to the same site. A client is safe to use from many threads.
 *
 * <p>The static methods of the models, such as {@code User.list()}, and the static request methods
 * of this class use {@link #defaultClient()}, which follows the static settings of this class:
 * {@link #apiKey}, {@link #session}, {@link #workspaceId}, {@link #language} and
 * {@link #setProperty(String, String)}. These are plain static fields that this class does not
 * synchronize or validate; setting one changes it for every later call in the JVM made through the
 * default client, while objects and listings already returned keep the settings they were
 * requested with. Other HTTP requests in the JVM are not affected.
 *
 * <p><b>Request options.</b> For one call, the options map passed to a model method, an operations
 * method or a request method can replace the credentials with the {@code "session_id"} or
 * {@code "api_key"} option (each a {@code String}), the workspace with the {@code "workspace_id"}
 * option, where {@code null} sends none, and the API root with the {@code "api_root"} option (a
 * {@code String}, such as {@code https://MY-SUBDOMAIN.files.com}); objects and listings the call
 * returns keep them. The language has no request option. Every API request except session
 * creation (a POST to a URL containing {@code sessions}) authenticates with the first of these that
 * is set: the {@code "session_id"} option, the {@code "api_key"} option, the client's session, then
 * its API key. When none is set, the request fails with an authentication error before it is sent.
 * Credentials go only to the API root the call starts at, or to the server Files.com relocates the
 * site to; a redirect anywhere else carries none.
 *
 * <p><b>Retries.</b> A request that fails in a way that may be temporary is sent again, up to
 * {@link Builder#maxRetries(int)} times (3 by default): after a connection or read failure, HTTP
 * 429, or HTTP 500 or above, whatever the request's method. HTTP 501 is never retried, and neither
 * is a request that cannot be sent as written, a server certificate or host name that does not
 * verify, or redirects that loop or go past the limit. The first retry waits
 * {@link Builder#retryDelay(Duration)} (100 ms), and each later one twice as long as the one before,
 * up to {@link Builder#maxRetryDelay(Duration)}; after HTTP 429 or 503, a longer Retry-After within
 * that maximum is waited instead. When Files.com answers that the site must be reached through
 * another server, the client follows as its
 * {@link Builder#regionRelocation(RegionRelocationPolicy) region relocation policy} allows, and
 * that uses one of the same retries. A request that changes data may take effect twice when an
 * earlier attempt reached Files.com but its answer was lost or was a server error. The request that
 * completes an upload is the exception: once it may have reached Files.com it is never sent again,
 * and unless Files.com refused it, a failure then leaves the upload's outcome unknown
 * ({@link com.files.exceptions.UploadCompletionUnknownException}). The default client reads its
 * retry limit and wait from {@link FilesConfig} again before each attempt of its API requests and of
 * the storage requests of {@link #getFileInputStream} and {@link #putBuffer}; transfers, and every
 * other client, keep the values they began with.
 *
 * <p><b>Cancellation.</b> A call stops when its {@link CancellationToken}, given as the {@code
 * cancellation} request option, is cancelled; when its time limit, the {@code timeout} request
 * option (a {@link Duration}) or {@link Builder#callTimeout(Duration)}, passes; or when the thread
 * making it is interrupted. Its request is aborted, and the call throws
 * {@link com.files.exceptions.OperationCancelledException}. The {@code max_retries} request option
 * (an Integer) replaces the client's retry limit for one call.
 */
public final class FilesClient implements Closeable {
  /**
   * The API key of {@link #defaultClient()}, sent as the {@code X-FilesAPI-Key} header by its API
   * requests that have no {@code "session_id"} or {@code "api_key"} option when {@link #session} is
   * not set. Not used when {@code null} or empty.
   */
  public static String apiKey;
  /**
   * The language of {@link #defaultClient()}, sent as the {@code Accept-Language} header of its API
   * requests when not {@code null}.
   */
  public static String language;
  /**
   * The workspace ID of {@link #defaultClient()}, sent as the {@code X-Files-Workspace-Id} header by
   * its authenticated API requests whose options have no {@code "workspace_id"} entry. Not sent when
   * {@code null}.
   */
  public static Long workspaceId;
  /**
   * The session of {@link #defaultClient()}. Its ID is sent as the {@code X-FilesAPI-Auth} header by
   * the default client's API requests that have no {@code "session_id"} or {@code "api_key"} option,
   * in preference to {@link #apiKey}. Not used when {@code null} or when its ID is {@code null} or
   * empty.
   */
  public static Session session;
  /**
   * The HTTP connection pool of {@link #defaultClient()}. It is created when this class is
   * initialized, allowing {@link FilesConfig#getUpstreamMaxConnections()} connections in total and
   * 20 per route, as configured at that moment. Assigning another pool here makes the default
   * client's later calls use it; objects and listings already returned keep the pool they were
   * requested with. The SDK never closes either pool.
   */
  public static PoolingHttpClientConnectionManager connectionManager;
  private static final ApacheHttpEvictor evictor;
  private static final Object DEFAULT_CLIENT_LOCK = new Object();
  private static volatile FilesClient defaultClient;
  private static HttpTransport defaultTransport;
  private static volatile HttpClientConnectionManager defaultTransportPool;

  static {
    connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(FilesConfig.getInstance().getUpstreamMaxConnections());
    connectionManager.setDefaultMaxPerRoute(20);
    evictor = new ApacheHttpEvictor(connectionManager, FilesConfig.getInstance().getUpstreamTimeout());
  }

  private final ClientRuntime runtime;

  private FilesClient(ClientRuntime.Settings settings, HttpTransport transport, boolean ownsTransport, boolean followsConfiguration) {
    this.runtime = new ClientRuntime(this, settings, transport, ownsTransport, followsConfiguration);
  }

  /**
   * Starts building a client with the SDK's defaults, never the static settings of this class.
   *
   * @return a new builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * The client the static methods of the models use. It has the static settings of this class as
   * they are now: {@link #apiKey}, {@link #session}, {@link #workspaceId}, {@link #language} and
   * the {@link #setProperty(String, String)} properties, and it sends its requests over the
   * connections of {@link #connectionManager}, which it never closes. When those settings change,
   * the next call returns a new client with the new settings; a client returned earlier, and the
   * objects and listings it returned, keep the settings they were made with. Closing the default
   * client stops its work, and the next call returns a new one.
   */
  public static FilesClient defaultClient() {
    ClientRuntime.Settings settings = staticSettings();
    FilesClient current = defaultClient;
    if (current != null && !current.isClosed() && current.runtime.settings().equals(settings)
        && defaultTransportPool == connectionManager) {
      return current;
    }
    synchronized (DEFAULT_CLIENT_LOCK) {
      HttpClientConnectionManager pool = connectionManager;
      if (defaultTransport == null || defaultTransportPool != pool) {
        defaultTransport = HttpTransport.builder().connectionManager(pool).build();
        defaultTransportPool = pool;
        defaultClient = null;
      }
      current = defaultClient;
      if (current == null || current.isClosed() || !current.runtime.settings().equals(settings)) {
        current = new FilesClient(settings, defaultTransport, false, true);
        defaultClient = current;
      }
      return current;
    }
  }

  private static ClientRuntime.Settings staticSettings() {
    FilesConfig config = FilesConfig.getInstance();
    Session currentSession = session;
    return new ClientRuntime.Settings.Builder(config.getUserAgent())
        .apiKey(apiKey)
        .sessionId(currentSession != null ? currentSession.getId() : null)
        .workspaceId(workspaceId)
        .language(language)
        .apiRoot(config.getApiRoot())
        .apiBase(config.getApiBase())
        .maxRetries(config.getMaxNetworkRetries())
        .retryDelay(Duration.ofMillis(config.getInitialRetryDelayMillis()))
        .maxRetryDelay(Duration.ofSeconds(config.getMaximumRetrySeconds()))
        .connectTimeoutMillis(config.getConnectTimeoutMillis())
        .readTimeoutMillis(config.getReadTimeoutMillis())
        .regionRelocation(config.getRegionRelocation() ? RegionRelocationPolicy.FILES_COM_HOSTS : RegionRelocationPolicy.DISABLED)
        .build();
  }

  ClientRuntime runtime() {
    return runtime;
  }

  /**
   * The API root this client's requests go to now: the one it was built with, or the one Files.com
   * relocated the site to.
   */
  public URI apiRoot() {
    return runtime.apiRoot();
  }

  /**
   * Uploads and downloads that run with this client.
   */
  public Transfers transfers() {
    runtime.ensureOpen();
    return runtime.transfers();
  }

  /**
   * Returns whether {@link #close()} has been called.
   *
   * @return {@code true} once the client is closed
   */
  public boolean isClosed() {
    return runtime.isClosed();
  }

  /**
   * Closes the client. Its calls and transfers still running are cancelled: their requests are
   * aborted, and they throw {@link com.files.exceptions.OperationCancelledException}. Close waits
   * for them to stop, including any transfer thread; a transfer blocked reading your own stream
   * stops once that read returns. It keeps waiting if the closing thread is interrupted, and then
   * returns with the thread's interrupt status set. Then the client's own transport is closed; a
   * transport you gave it with {@link Builder#transport(HttpTransport)} stays open, and so does the
   * Apache client that transport uses. Afterwards the client, and the objects and listings it
   * returned, throw {@link IllegalStateException} when used to send a request.
   *
   * <p>Closing it again waits the same way and has no other effect. Only a close made while its
   * thread is doing the work of a call or transfer, of this client or another, such as in a
   * progress listener, does not wait: that work cannot end meanwhile, and the work it would wait
   * for may be waiting for it. It cancels the client's work and returns at once; the client's
   * transport is closed once the last of that work has stopped.
   */
  @Override
  public void close() {
    runtime.close();
  }

  /**
   * ActionLog operations that run with this client.
   */
  public ActionLogOperations actionLogs() {
    runtime.ensureOpen();
    return new ActionLogOperations(this);
  }

  /**
   * ActionNotificationExport operations that run with this client.
   */
  public ActionNotificationExportOperations actionNotificationExports() {
    runtime.ensureOpen();
    return new ActionNotificationExportOperations(this);
  }

  /**
   * ActionNotificationExportResult operations that run with this client.
   */
  public ActionNotificationExportResultOperations actionNotificationExportResults() {
    runtime.ensureOpen();
    return new ActionNotificationExportResultOperations(this);
  }

  /**
   * AiAssistantPersonality operations that run with this client.
   */
  public AiAssistantPersonalityOperations aiAssistantPersonalities() {
    runtime.ensureOpen();
    return new AiAssistantPersonalityOperations(this);
  }

  /**
   * AiTask operations that run with this client.
   */
  public AiTaskOperations aiTasks() {
    runtime.ensureOpen();
    return new AiTaskOperations(this);
  }

  /**
   * ApiKey operations that run with this client.
   */
  public ApiKeyOperations apiKeys() {
    runtime.ensureOpen();
    return new ApiKeyOperations(this);
  }

  /**
   * ApiRequestLog operations that run with this client.
   */
  public ApiRequestLogOperations apiRequestLogs() {
    runtime.ensureOpen();
    return new ApiRequestLogOperations(this);
  }

  /**
   * App operations that run with this client.
   */
  public AppOperations apps() {
    runtime.ensureOpen();
    return new AppOperations(this);
  }

  /**
   * As2IncomingMessage operations that run with this client.
   */
  public As2IncomingMessageOperations as2IncomingMessages() {
    runtime.ensureOpen();
    return new As2IncomingMessageOperations(this);
  }

  /**
   * As2OutgoingMessage operations that run with this client.
   */
  public As2OutgoingMessageOperations as2OutgoingMessages() {
    runtime.ensureOpen();
    return new As2OutgoingMessageOperations(this);
  }

  /**
   * As2Partner operations that run with this client.
   */
  public As2PartnerOperations as2Partners() {
    runtime.ensureOpen();
    return new As2PartnerOperations(this);
  }

  /**
   * As2Station operations that run with this client.
   */
  public As2StationOperations as2Stations() {
    runtime.ensureOpen();
    return new As2StationOperations(this);
  }

  /**
   * Automation operations that run with this client.
   */
  public AutomationOperations automations() {
    runtime.ensureOpen();
    return new AutomationOperations(this);
  }

  /**
   * AutomationLog operations that run with this client.
   */
  public AutomationLogOperations automationLogs() {
    runtime.ensureOpen();
    return new AutomationLogOperations(this);
  }

  /**
   * AutomationRun operations that run with this client.
   */
  public AutomationRunOperations automationRuns() {
    runtime.ensureOpen();
    return new AutomationRunOperations(this);
  }

  /**
   * BandwidthSnapshot operations that run with this client.
   */
  public BandwidthSnapshotOperations bandwidthSnapshots() {
    runtime.ensureOpen();
    return new BandwidthSnapshotOperations(this);
  }

  /**
   * Behavior operations that run with this client.
   */
  public BehaviorOperations behaviors() {
    runtime.ensureOpen();
    return new BehaviorOperations(this);
  }

  /**
   * Bundle operations that run with this client.
   */
  public BundleOperations bundles() {
    runtime.ensureOpen();
    return new BundleOperations(this);
  }

  /**
   * BundleAction operations that run with this client.
   */
  public BundleActionOperations bundleActions() {
    runtime.ensureOpen();
    return new BundleActionOperations(this);
  }

  /**
   * BundleDownload operations that run with this client.
   */
  public BundleDownloadOperations bundleDownloads() {
    runtime.ensureOpen();
    return new BundleDownloadOperations(this);
  }

  /**
   * BundleNotification operations that run with this client.
   */
  public BundleNotificationOperations bundleNotifications() {
    runtime.ensureOpen();
    return new BundleNotificationOperations(this);
  }

  /**
   * BundleRecipient operations that run with this client.
   */
  public BundleRecipientOperations bundleRecipients() {
    runtime.ensureOpen();
    return new BundleRecipientOperations(this);
  }

  /**
   * BundleRegistration operations that run with this client.
   */
  public BundleRegistrationOperations bundleRegistrations() {
    runtime.ensureOpen();
    return new BundleRegistrationOperations(this);
  }

  /**
   * ChatSession operations that run with this client.
   */
  public ChatSessionOperations chatSessions() {
    runtime.ensureOpen();
    return new ChatSessionOperations(this);
  }

  /**
   * ChildSiteManagementPolicy operations that run with this client.
   */
  public ChildSiteManagementPolicyOperations childSiteManagementPolicies() {
    runtime.ensureOpen();
    return new ChildSiteManagementPolicyOperations(this);
  }

  /**
   * Clickwrap operations that run with this client.
   */
  public ClickwrapOperations clickwraps() {
    runtime.ensureOpen();
    return new ClickwrapOperations(this);
  }

  /**
   * CustomDomain operations that run with this client.
   */
  public CustomDomainOperations customDomains() {
    runtime.ensureOpen();
    return new CustomDomainOperations(this);
  }

  /**
   * DesktopConfigurationProfile operations that run with this client.
   */
  public DesktopConfigurationProfileOperations desktopConfigurationProfiles() {
    runtime.ensureOpen();
    return new DesktopConfigurationProfileOperations(this);
  }

  /**
   * DnsRecord operations that run with this client.
   */
  public DnsRecordOperations dnsRecords() {
    runtime.ensureOpen();
    return new DnsRecordOperations(this);
  }

  /**
   * EmailIncomingMessage operations that run with this client.
   */
  public EmailIncomingMessageOperations emailIncomingMessages() {
    runtime.ensureOpen();
    return new EmailIncomingMessageOperations(this);
  }

  /**
   * EmailLog operations that run with this client.
   */
  public EmailLogOperations emailLogs() {
    runtime.ensureOpen();
    return new EmailLogOperations(this);
  }

  /**
   * EventChannel operations that run with this client.
   */
  public EventChannelOperations eventChannels() {
    runtime.ensureOpen();
    return new EventChannelOperations(this);
  }

  /**
   * EventDeliveryAttempt operations that run with this client.
   */
  public EventDeliveryAttemptOperations eventDeliveryAttempts() {
    runtime.ensureOpen();
    return new EventDeliveryAttemptOperations(this);
  }

  /**
   * EventRecord operations that run with this client.
   */
  public EventRecordOperations eventRecords() {
    runtime.ensureOpen();
    return new EventRecordOperations(this);
  }

  /**
   * EventSubscription operations that run with this client.
   */
  public EventSubscriptionOperations eventSubscriptions() {
    runtime.ensureOpen();
    return new EventSubscriptionOperations(this);
  }

  /**
   * EventTarget operations that run with this client.
   */
  public EventTargetOperations eventTargets() {
    runtime.ensureOpen();
    return new EventTargetOperations(this);
  }

  /**
   * ExavaultApiRequestLog operations that run with this client.
   */
  public ExavaultApiRequestLogOperations exavaultApiRequestLogs() {
    runtime.ensureOpen();
    return new ExavaultApiRequestLogOperations(this);
  }

  /**
   * Expectation operations that run with this client.
   */
  public ExpectationOperations expectations() {
    runtime.ensureOpen();
    return new ExpectationOperations(this);
  }

  /**
   * ExpectationEvaluation operations that run with this client.
   */
  public ExpectationEvaluationOperations expectationEvaluations() {
    runtime.ensureOpen();
    return new ExpectationEvaluationOperations(this);
  }

  /**
   * ExpectationIncident operations that run with this client.
   */
  public ExpectationIncidentOperations expectationIncidents() {
    runtime.ensureOpen();
    return new ExpectationIncidentOperations(this);
  }

  /**
   * ExternalEvent operations that run with this client.
   */
  public ExternalEventOperations externalEvents() {
    runtime.ensureOpen();
    return new ExternalEventOperations(this);
  }

  /**
   * File operations that run with this client.
   */
  public FileOperations files() {
    runtime.ensureOpen();
    return new FileOperations(this);
  }

  /**
   * FileComment operations that run with this client.
   */
  public FileCommentOperations fileComments() {
    runtime.ensureOpen();
    return new FileCommentOperations(this);
  }

  /**
   * FileCommentReaction operations that run with this client.
   */
  public FileCommentReactionOperations fileCommentReactions() {
    runtime.ensureOpen();
    return new FileCommentReactionOperations(this);
  }

  /**
   * FileMigration operations that run with this client.
   */
  public FileMigrationOperations fileMigrations() {
    runtime.ensureOpen();
    return new FileMigrationOperations(this);
  }

  /**
   * FileMigrationLog operations that run with this client.
   */
  public FileMigrationLogOperations fileMigrationLogs() {
    runtime.ensureOpen();
    return new FileMigrationLogOperations(this);
  }

  /**
   * Folder operations that run with this client.
   */
  public FolderOperations folders() {
    runtime.ensureOpen();
    return new FolderOperations(this);
  }

  /**
   * FormFieldSet operations that run with this client.
   */
  public FormFieldSetOperations formFieldSets() {
    runtime.ensureOpen();
    return new FormFieldSetOperations(this);
  }

  /**
   * FtpActionLog operations that run with this client.
   */
  public FtpActionLogOperations ftpActionLogs() {
    runtime.ensureOpen();
    return new FtpActionLogOperations(this);
  }

  /**
   * GpgKey operations that run with this client.
   */
  public GpgKeyOperations gpgKeys() {
    runtime.ensureOpen();
    return new GpgKeyOperations(this);
  }

  /**
   * Group operations that run with this client.
   */
  public GroupOperations groups() {
    runtime.ensureOpen();
    return new GroupOperations(this);
  }

  /**
   * GroupUser operations that run with this client.
   */
  public GroupUserOperations groupUsers() {
    runtime.ensureOpen();
    return new GroupUserOperations(this);
  }

  /**
   * History operations that run with this client.
   */
  public HistoryOperations histories() {
    runtime.ensureOpen();
    return new HistoryOperations(this);
  }

  /**
   * HistoryExport operations that run with this client.
   */
  public HistoryExportOperations historyExports() {
    runtime.ensureOpen();
    return new HistoryExportOperations(this);
  }

  /**
   * HistoryExportResult operations that run with this client.
   */
  public HistoryExportResultOperations historyExportResults() {
    runtime.ensureOpen();
    return new HistoryExportResultOperations(this);
  }

  /**
   * HolidayCalendar operations that run with this client.
   */
  public HolidayCalendarOperations holidayCalendars() {
    runtime.ensureOpen();
    return new HolidayCalendarOperations(this);
  }

  /**
   * HolidayRegion operations that run with this client.
   */
  public HolidayRegionOperations holidayRegions() {
    runtime.ensureOpen();
    return new HolidayRegionOperations(this);
  }

  /**
   * InboundS3Log operations that run with this client.
   */
  public InboundS3LogOperations inboundS3Logs() {
    runtime.ensureOpen();
    return new InboundS3LogOperations(this);
  }

  /**
   * InboxRecipient operations that run with this client.
   */
  public InboxRecipientOperations inboxRecipients() {
    runtime.ensureOpen();
    return new InboxRecipientOperations(this);
  }

  /**
   * InboxRegistration operations that run with this client.
   */
  public InboxRegistrationOperations inboxRegistrations() {
    runtime.ensureOpen();
    return new InboxRegistrationOperations(this);
  }

  /**
   * InboxUpload operations that run with this client.
   */
  public InboxUploadOperations inboxUploads() {
    runtime.ensureOpen();
    return new InboxUploadOperations(this);
  }

  /**
   * IntegrationCentricProfile operations that run with this client.
   */
  public IntegrationCentricProfileOperations integrationCentricProfiles() {
    runtime.ensureOpen();
    return new IntegrationCentricProfileOperations(this);
  }

  /**
   * Invoice operations that run with this client.
   */
  public InvoiceOperations invoices() {
    runtime.ensureOpen();
    return new InvoiceOperations(this);
  }

  /**
   * IpAddress operations that run with this client.
   */
  public IpAddressOperations ipAddresses() {
    runtime.ensureOpen();
    return new IpAddressOperations(this);
  }

  /**
   * KeyLifecycleRule operations that run with this client.
   */
  public KeyLifecycleRuleOperations keyLifecycleRules() {
    runtime.ensureOpen();
    return new KeyLifecycleRuleOperations(this);
  }

  /**
   * Lock operations that run with this client.
   */
  public LockOperations locks() {
    runtime.ensureOpen();
    return new LockOperations(this);
  }

  /**
   * MetadataCategory operations that run with this client.
   */
  public MetadataCategoryOperations metadataCategories() {
    runtime.ensureOpen();
    return new MetadataCategoryOperations(this);
  }

  /**
   * Notification operations that run with this client.
   */
  public NotificationOperations notifications() {
    runtime.ensureOpen();
    return new NotificationOperations(this);
  }

  /**
   * OutboundConnectionLog operations that run with this client.
   */
  public OutboundConnectionLogOperations outboundConnectionLogs() {
    runtime.ensureOpen();
    return new OutboundConnectionLogOperations(this);
  }

  /**
   * Partner operations that run with this client.
   */
  public PartnerOperations partners() {
    runtime.ensureOpen();
    return new PartnerOperations(this);
  }

  /**
   * PartnerChannel operations that run with this client.
   */
  public PartnerChannelOperations partnerChannels() {
    runtime.ensureOpen();
    return new PartnerChannelOperations(this);
  }

  /**
   * PartnerChannelTemplate operations that run with this client.
   */
  public PartnerChannelTemplateOperations partnerChannelTemplates() {
    runtime.ensureOpen();
    return new PartnerChannelTemplateOperations(this);
  }

  /**
   * PartnerSite operations that run with this client.
   */
  public PartnerSiteOperations partnerSites() {
    runtime.ensureOpen();
    return new PartnerSiteOperations(this);
  }

  /**
   * PartnerSiteRequest operations that run with this client.
   */
  public PartnerSiteRequestOperations partnerSiteRequests() {
    runtime.ensureOpen();
    return new PartnerSiteRequestOperations(this);
  }

  /**
   * Payment operations that run with this client.
   */
  public PaymentOperations payments() {
    runtime.ensureOpen();
    return new PaymentOperations(this);
  }

  /**
   * PendingWorkEvent operations that run with this client.
   */
  public PendingWorkEventOperations pendingWorkEvents() {
    runtime.ensureOpen();
    return new PendingWorkEventOperations(this);
  }

  /**
   * Permission operations that run with this client.
   */
  public PermissionOperations permissions() {
    runtime.ensureOpen();
    return new PermissionOperations(this);
  }

  /**
   * PublicHostingRequestLog operations that run with this client.
   */
  public PublicHostingRequestLogOperations publicHostingRequestLogs() {
    runtime.ensureOpen();
    return new PublicHostingRequestLogOperations(this);
  }

  /**
   * PublicKey operations that run with this client.
   */
  public PublicKeyOperations publicKeys() {
    runtime.ensureOpen();
    return new PublicKeyOperations(this);
  }

  /**
   * RemoteBandwidthSnapshot operations that run with this client.
   */
  public RemoteBandwidthSnapshotOperations remoteBandwidthSnapshots() {
    runtime.ensureOpen();
    return new RemoteBandwidthSnapshotOperations(this);
  }

  /**
   * RemoteMountBackend operations that run with this client.
   */
  public RemoteMountBackendOperations remoteMountBackends() {
    runtime.ensureOpen();
    return new RemoteMountBackendOperations(this);
  }

  /**
   * RemoteServer operations that run with this client.
   */
  public RemoteServerOperations remoteServers() {
    runtime.ensureOpen();
    return new RemoteServerOperations(this);
  }

  /**
   * RemoteServerCredential operations that run with this client.
   */
  public RemoteServerCredentialOperations remoteServerCredentials() {
    runtime.ensureOpen();
    return new RemoteServerCredentialOperations(this);
  }

  /**
   * Request operations that run with this client.
   */
  public RequestOperations requests() {
    runtime.ensureOpen();
    return new RequestOperations(this);
  }

  /**
   * Restore operations that run with this client.
   */
  public RestoreOperations restores() {
    runtime.ensureOpen();
    return new RestoreOperations(this);
  }

  /**
   * Schedule operations that run with this client.
   */
  public ScheduleOperations schedules() {
    runtime.ensureOpen();
    return new ScheduleOperations(this);
  }

  /**
   * ScheduledExport operations that run with this client.
   */
  public ScheduledExportOperations scheduledExports() {
    runtime.ensureOpen();
    return new ScheduledExportOperations(this);
  }

  /**
   * ScimLog operations that run with this client.
   */
  public ScimLogOperations scimLogs() {
    runtime.ensureOpen();
    return new ScimLogOperations(this);
  }

  /**
   * Secret operations that run with this client.
   */
  public SecretOperations secrets() {
    runtime.ensureOpen();
    return new SecretOperations(this);
  }

  /**
   * Session operations that run with this client.
   */
  public SessionOperations sessions() {
    runtime.ensureOpen();
    return new SessionOperations(this);
  }

  /**
   * SettingsChange operations that run with this client.
   */
  public SettingsChangeOperations settingsChanges() {
    runtime.ensureOpen();
    return new SettingsChangeOperations(this);
  }

  /**
   * SftpActionLog operations that run with this client.
   */
  public SftpActionLogOperations sftpActionLogs() {
    runtime.ensureOpen();
    return new SftpActionLogOperations(this);
  }

  /**
   * SftpHostKey operations that run with this client.
   */
  public SftpHostKeyOperations sftpHostKeys() {
    runtime.ensureOpen();
    return new SftpHostKeyOperations(this);
  }

  /**
   * ShareGroup operations that run with this client.
   */
  public ShareGroupOperations shareGroups() {
    runtime.ensureOpen();
    return new ShareGroupOperations(this);
  }

  /**
   * SiemHttpDestination operations that run with this client.
   */
  public SiemHttpDestinationOperations siemHttpDestinations() {
    runtime.ensureOpen();
    return new SiemHttpDestinationOperations(this);
  }

  /**
   * SiemHttpDestinationEvent operations that run with this client.
   */
  public SiemHttpDestinationEventOperations siemHttpDestinationEvents() {
    runtime.ensureOpen();
    return new SiemHttpDestinationEventOperations(this);
  }

  /**
   * Site operations that run with this client.
   */
  public SiteOperations sites() {
    runtime.ensureOpen();
    return new SiteOperations(this);
  }

  /**
   * SiteSubdomainRedirect operations that run with this client.
   */
  public SiteSubdomainRedirectOperations siteSubdomainRedirects() {
    runtime.ensureOpen();
    return new SiteSubdomainRedirectOperations(this);
  }

  /**
   * Snapshot operations that run with this client.
   */
  public SnapshotOperations snapshots() {
    runtime.ensureOpen();
    return new SnapshotOperations(this);
  }

  /**
   * SsoEvent operations that run with this client.
   */
  public SsoEventOperations ssoEvents() {
    runtime.ensureOpen();
    return new SsoEventOperations(this);
  }

  /**
   * SsoStrategy operations that run with this client.
   */
  public SsoStrategyOperations ssoStrategies() {
    runtime.ensureOpen();
    return new SsoStrategyOperations(this);
  }

  /**
   * Style operations that run with this client.
   */
  public StyleOperations styles() {
    runtime.ensureOpen();
    return new StyleOperations(this);
  }

  /**
   * Sync operations that run with this client.
   */
  public SyncOperations syncs() {
    runtime.ensureOpen();
    return new SyncOperations(this);
  }

  /**
   * SyncLog operations that run with this client.
   */
  public SyncLogOperations syncLogs() {
    runtime.ensureOpen();
    return new SyncLogOperations(this);
  }

  /**
   * SyncRun operations that run with this client.
   */
  public SyncRunOperations syncRuns() {
    runtime.ensureOpen();
    return new SyncRunOperations(this);
  }

  /**
   * UsageDailySnapshot operations that run with this client.
   */
  public UsageDailySnapshotOperations usageDailySnapshots() {
    runtime.ensureOpen();
    return new UsageDailySnapshotOperations(this);
  }

  /**
   * UsageSnapshot operations that run with this client.
   */
  public UsageSnapshotOperations usageSnapshots() {
    runtime.ensureOpen();
    return new UsageSnapshotOperations(this);
  }

  /**
   * User operations that run with this client.
   */
  public UserOperations users() {
    runtime.ensureOpen();
    return new UserOperations(this);
  }

  /**
   * UserAdditionalEmailRecipient operations that run with this client.
   */
  public UserAdditionalEmailRecipientOperations userAdditionalEmailRecipients() {
    runtime.ensureOpen();
    return new UserAdditionalEmailRecipientOperations(this);
  }

  /**
   * UserCipherUse operations that run with this client.
   */
  public UserCipherUseOperations userCipherUses() {
    runtime.ensureOpen();
    return new UserCipherUseOperations(this);
  }

  /**
   * UserLifecycleRule operations that run with this client.
   */
  public UserLifecycleRuleOperations userLifecycleRules() {
    runtime.ensureOpen();
    return new UserLifecycleRuleOperations(this);
  }

  /**
   * UserRequest operations that run with this client.
   */
  public UserRequestOperations userRequests() {
    runtime.ensureOpen();
    return new UserRequestOperations(this);
  }

  /**
   * UserSecurityEvent operations that run with this client.
   */
  public UserSecurityEventOperations userSecurityEvents() {
    runtime.ensureOpen();
    return new UserSecurityEventOperations(this);
  }

  /**
   * UserSftpClientUse operations that run with this client.
   */
  public UserSftpClientUseOperations userSftpClientUses() {
    runtime.ensureOpen();
    return new UserSftpClientUseOperations(this);
  }

  /**
   * WebDavActionLog operations that run with this client.
   */
  public WebDavActionLogOperations webDavActionLogs() {
    runtime.ensureOpen();
    return new WebDavActionLogOperations(this);
  }

  /**
   * WebhookTest operations that run with this client.
   */
  public WebhookTestOperations webhookTests() {
    runtime.ensureOpen();
    return new WebhookTestOperations(this);
  }

  /**
   * Workspace operations that run with this client.
   */
  public WorkspaceOperations workspaces() {
    runtime.ensureOpen();
    return new WorkspaceOperations(this);
  }

  /**
   * Sends one API request with {@link #defaultClient()} and returns its successful response, with
   * the body read as text. GET, DELETE and HEAD requests send {@code parameters} as the query
   * string; POST, PUT and PATCH requests send them as the request body. The request carries the
   * user agent and the language, workspace and authentication headers described for this class.
   *
   * @param url a path under the API root and {@link FilesConfig#getApiBase()}, such as
   *     {@code /users}; or an absolute URL, which is sent as it is and never relocated
   * @param requestType the HTTP method
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, such as {@code "api_key"}, {@code "session_id"},
   *     {@code "workspace_id"}, {@code "api_root"}, {@code "cancellation"}, {@code "timeout"} or
   *     {@code "max_retries"}; or {@code null} for none
   * @return the response's status code, headers and body
   * @throws RuntimeException an {@link com.files.exceptions.ApiErrorException} if the request cannot
   *     be authenticated, an option has the wrong type, Files.com answers with an error or the
   *     request fails; an {@link com.files.exceptions.OperationCancelledException} if the call is
   *     cancelled, passes its time limit or its thread is interrupted
   */
  public static FilesResponse apiRequest(String url, HttpMethods.RequestMethods requestType, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return defaultClient().runtime.request(requestType, url, parameters, options);
  }

  /**
   * Returns a {@link ListIterator} for a listing request of {@link #defaultClient()}, without
   * sending the request. Each page is requested, as {@link #apiRequest} sends it, only when the
   * iterator loads that page; see {@link ListIterator} for the ways to load pages. The iterator
   * keeps the default client as it is now, and copies of {@code parameters} and {@code options}.
   *
   * @param <T> the type of each listed item
   * @param url a path under the API root, as for {@link #apiRequest}
   * @param requestType the HTTP method
   * @param className the type each page's JSON array is read as
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, as for {@link #apiRequest}; each loaded model keeps them,
   *     without those that controlled only the call
   * @return an iterator that has not loaded any page
   * @throws RuntimeException not thrown by the current implementation, which only creates the
   *     iterator
   */
  public static <T> ListIterator<T> requestList(String url, RequestMethods requestType, TypeReference<List<T>> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return new ListIterator<T>(defaultClient(), url, requestType, className, parameters, options);
  }

  /**
   * Sends one API request with {@link #defaultClient()}, as {@link #apiRequest} does, and reads the
   * response body as a {@code T}. A model it returns keeps the default client as it is now and a
   * copy of {@code options}, without the options that controlled only this call
   * ({@code "cancellation"}, {@code "timeout"} and {@code "max_retries"}), so the model's own later
   * requests use them.
   *
   * @param <T> the type the response body is read as
   * @param url a path under the API root, as for {@link #apiRequest}
   * @param requestType the HTTP method
   * @param className the type the response body is read as
   * @param parameters the request parameters, or {@code null} for none
   * @param options request options, as for {@link #apiRequest}
   * @return the response body read as a {@code T}
   * @throws RuntimeException as for {@link #apiRequest}, and an
   *     {@link com.files.exceptions.ApiErrorException.ApiConnectionException} if the response body is
   *     not the expected JSON
   */
  public static <T> T requestItem(String url, RequestMethods requestType, TypeReference<T> className, HashMap<String, Object> parameters, HashMap<String, Object> options) throws RuntimeException {
    return defaultClient().runtime.requestItem(requestType, url, className, parameters, options);
  }

  /**
   * Sends a GET request to {@code url} with {@link #defaultClient()} and returns a stream of the
   * successful response's body. It sends no credentials, workspace or language header, so
   * {@code url} must be one that needs none, such as a download URL the API returned. A failure
   * that may be temporary is retried, as API requests are, until the response arrives.
   *
   * <p>{@code start} and {@code end} are not used: the request asks for the whole resource.
   *
   * @param url the full URL to download from
   * @param start not used
   * @param end not used
   * @return a stream of the response body, which the caller closes when done
   * @throws IOException declared for compatibility; a failed request throws an
   *     {@link com.files.exceptions.ApiErrorException} instead
   */
  public static FilesInputStream getFileInputStream(String url, long start, long end) throws IOException {
    return defaultClient().runtime.openDownload(url);
  }

  /**
   * Sends the first {@code length} bytes of {@code buffer} to {@code url} with
   * {@link #defaultClient()}, in one POST or PATCH request when {@code requestType} is one of those,
   * and otherwise in one PUT. It sends no credentials, workspace or language header, so {@code url}
   * must be one that needs none, such as an upload URL the API returned. A failure that may be
   * temporary is retried with the same bytes, as API requests are.
   *
   * @param url the full URL to upload to
   * @param requestType the HTTP method: POST, PATCH, or otherwise PUT
   * @param name not used
   * @param buffer the bytes to upload
   * @param length the number of bytes to send from the start of {@code buffer}
   * @return {@code length}
   * @throws IOException declared for compatibility; a failed request throws an
   *     {@link com.files.exceptions.ApiErrorException} instead
   */
  public static long putBuffer(String url, RequestMethods requestType, String name, byte[] buffer, long length) throws IOException {
    return defaultClient().runtime.putBuffer(url, requestType, buffer, length);
  }

  /**
   * Sends the next {@code length} bytes of {@code inputStream} to {@code url} with
   * {@link #defaultClient()}, once, as {@link #putBuffer} sends a buffer: the stream cannot be read
   * again, so a failed request is not retried. Apache HttpClient closes the stream once it has sent
   * the body.
   *
   * @param url the full URL to upload to
   * @param requestType the HTTP method: POST, PATCH, or otherwise PUT
   * @param name not used
   * @param inputStream the stream the request body is read from
   * @param length the number of bytes to send from {@code inputStream}
   * @return 0, as it always has, not the number of bytes sent
   * @throws IOException declared for compatibility; a failed request throws an
   *     {@link com.files.exceptions.ApiErrorException} instead
   */
  public static long putBufferedInputStream(String url, RequestMethods requestType, String name, BufferedInputStream inputStream, long length) throws IOException {
    defaultClient().runtime.putStream(url, requestType, inputStream, length);
    return 0;
  }

  /**
   * Sets a property of the process-wide configuration, as {@link FilesConfig#setProperty} does.
   * {@link #defaultClient()} uses the new value from its next call.
   *
   * <p>Calling this method first initializes this class if it is not yet initialized, and
   * initialization reads {@code upstreamMaxConnections} and {@code upstreamTimeout}. So setting
   * those two here has no effect on {@link #connectionManager}; set them with
   * {@link FilesConfig#setProperty} before this class is first used.
   *
   * @param property the property name, such as {@code "apiRoot"}; must not be {@code null}
   * @param value the property value; must not be {@code null}
   */
  public static void setProperty(String property, String value) {
    FilesConfig.getInstance().setProperty(property, value);
  }

  /**
   * Builds a {@link FilesClient}. Settings not given take the SDK's defaults, never the static
   * settings of {@link FilesClient}. A builder is not safe for use by several threads at once.
   */
  public static final class Builder {
    private final ClientRuntime.Settings.Builder settings = new ClientRuntime.Settings.Builder(FilesConfig.getInstance().getUserAgent());
    private HttpTransport transport;
    private HttpTransport.Builder transportBuilder;

    private Builder() {
    }

    /**
     * The API key requests authenticate with, unless a session is set.
     */
    public Builder apiKey(String apiKey) {
      settings.apiKey(apiKey);
      return this;
    }

    /**
     * The session ID requests authenticate with; it takes precedence over an API key.
     */
    public Builder sessionId(String sessionId) {
      settings.sessionId(sessionId);
      return this;
    }

    /**
     * The workspace requests act in, including workspace 0. Default: none, so no workspace is
     * sent. The {@code workspace_id} request option replaces it for one call, and a null option
     * sends none.
     */
    public Builder workspaceId(Long workspaceId) {
      settings.workspaceId(workspaceId);
      return this;
    }

    /**
     * The full URL of your Files.com site, such as {@code https://MY-SUBDOMAIN.files.com}. Default
     * {@code https://app.files.com}.
     */
    public Builder apiRoot(String apiRoot) {
      settings.apiRoot(apiRoot);
      return this;
    }

    /**
     * The language of the API's messages, sent as Accept-Language.
     */
    public Builder language(String language) {
      settings.language(language);
      return this;
    }

    /**
     * The most times a request is sent again after its first attempt. Default 3; 0 sends every
     * request once.
     */
    public Builder maxRetries(int maxRetries) {
      settings.maxRetries(maxRetries);
      return this;
    }

    /**
     * The wait before the first retry; each later retry waits twice as long. Default 100 ms.
     */
    public Builder retryDelay(Duration retryDelay) {
      settings.retryDelay(retryDelay);
      return this;
    }

    /**
     * The longest wait before a retry, including one the server asks for with Retry-After.
     * Default 10 seconds.
     */
    public Builder maxRetryDelay(Duration maxRetryDelay) {
      settings.maxRetryDelay(maxRetryDelay);
      return this;
    }

    /**
     * The longest wait to connect to a server. Default 30 seconds.
     */
    public Builder connectTimeout(Duration connectTimeout) {
      settings.connectTimeout(connectTimeout);
      return this;
    }

    /**
     * The longest wait for the next bytes of a response, while waiting for its headers and while
     * reading its body. Default 80 seconds.
     */
    public Builder readTimeout(Duration readTimeout) {
      settings.readTimeout(readTimeout);
      return this;
    }

    /**
     * The longest wait for a connection from the transport's pool, when every one allowed is in
     * use. It is separate from {@link #connectTimeout(Duration)}. Default 10 seconds.
     */
    public Builder connectionAcquisitionTimeout(Duration connectionAcquisitionTimeout) {
      settings.connectionAcquisitionTimeout(connectionAcquisitionTimeout);
      return this;
    }

    /**
     * The time limit of each call, across all its attempts and the waits between them. Default:
     * none. The {@code timeout} request option replaces it for one call.
     */
    public Builder callTimeout(Duration callTimeout) {
      settings.callTimeout(callTimeout);
      return this;
    }

    /**
     * Where the client may send a site's requests after Files.com names another server for it.
     * Default {@link RegionRelocationPolicy#FILES_COM_HOSTS}.
     */
    public Builder regionRelocation(RegionRelocationPolicy regionRelocation) {
      settings.regionRelocation(regionRelocation);
      return this;
    }

    /**
     * Sends requests over a transport you manage and share. Closing the client leaves it open. To
     * send them with an Apache HttpClient of your own, build a
     * {@link com.files.net.GuardedApacheClient} with {@link HttpTransport.Builder#buildClient()} and
     * give the client a transport using it, such as
     * {@link HttpTransport#borrowed(com.files.net.GuardedApacheClient)}.
     */
    public Builder transport(HttpTransport transport) {
      if (transport == null) {
        throw new IllegalArgumentException("The transport cannot be null");
      }
      this.transport = transport;
      this.transportBuilder = null;
      return this;
    }

    /**
     * Sends requests over a transport of the client's own, built with this builder when the
     * client is built, and closed when the client is closed. By default, the client has a
     * transport of its own with {@link HttpTransport#builder()}'s defaults.
     */
    public Builder transport(HttpTransport.Builder transportBuilder) {
      if (transportBuilder == null) {
        throw new IllegalArgumentException("The transport builder cannot be null");
      }
      this.transportBuilder = transportBuilder;
      this.transport = null;
      return this;
    }

    /**
     * Builds the client. A transport given with {@link #transport(HttpTransport)} must still be
     * open.
     *
     * @return the new client
     */
    public FilesClient build() {
      ClientRuntime.Settings built = settings.build();
      if (transport != null) {
        if (transport.isClosed()) {
          throw new IllegalStateException("The transport has been closed");
        }
        return new FilesClient(built, transport, false, false);
      }
      return new FilesClient(built, (transportBuilder != null ? transportBuilder : HttpTransport.builder()).build(), true, false);
    }
  }
}
