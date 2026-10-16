package com.files;

import com.files.exceptions.ApiErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SDK's process-wide configuration: named string properties, each read through a getter that
 * supplies its default.
 *
 * <p>{@link #getInstance()} creates the single instance on first use, loading the
 * {@code files-sdk.properties} resource bundled with the SDK, which sets {@code sdkVersion}. If
 * loading or closing that resource throws an {@code IOException}, a warning is logged and nothing
 * is cleared: properties already loaded keep their values, and getters return their defaults for
 * properties that were not loaded. {@link #setProperty} changes a property for the rest of the
 * process. No other source supplies values: system properties and environment variables are not
 * read.
 *
 * <p>Each getter reads its property when it is called. An integer property takes its default when
 * unset or empty, and otherwise must parse as an {@code int}, or the getter throws
 * {@code NumberFormatException}. A boolean property takes its default when unset or empty, and is
 * otherwise {@code true} only for {@code "true"}, ignoring case. A string property takes its
 * default only when unset. Code that reads a property once keeps the value it read: for example
 * {@link FilesClient} reads {@code upstreamMaxConnections} and {@code upstreamTimeout} when it is
 * initialized. {@link FilesClient#defaultClient()}, the client the static methods use, reads the
 * properties it is built with when it is called, so a change applies from the next call. Its API
 * requests, and the storage requests of {@link FilesClient}'s static methods, read
 * {@code maxNetworkRetries} (or {@code upstreamMaxAttempts}), {@code initialRetryDelayMillis} and
 * {@code maximumRetrySeconds} again before each attempt; a transfer keeps the values it started
 * with. A {@link FilesClient} built with {@link FilesClient#builder()} reads none of these
 * properties.
 */
public class FilesConfig {
  private static final Logger log = LoggerFactory.getLogger(FilesConfig.class);

  private static volatile FilesConfig instance;
  private final Properties properties;

  /**
   * Creates a configuration loaded from the {@code files-sdk.properties} resource. Applications use
   * {@link #getInstance()}; this constructor is available to subclasses.
   */
  protected FilesConfig() {
    properties = new Properties();
    try (InputStream file = FilesConfig.class.getResourceAsStream("/files-sdk.properties")) {
      properties.load(file);
    } catch (IOException e) {
      log.warn("could not load configurator properties");
    }
  }

  /**
   * Returns the process-wide configuration, creating and loading it on the first call.
   *
   * @return the single {@code FilesConfig} instance
   */
  public static FilesConfig getInstance() {
    if (instance == null) {
      synchronized (FilesConfig.class) {
        if (instance == null) {
          instance = new FilesConfig();
        }
      }
    }
    return instance;
  }

  private int intProperty(String key, int defaultValue) {
    String strValue = properties.getProperty(key);
    if (isNullOrEmpty(strValue)) {
      return defaultValue;
    } else {
      return Integer.valueOf(strValue);
    }
  }

  private boolean boolProperty(String key, boolean defaultValue) {
    String strValue = properties.getProperty(key);
    if (isNullOrEmpty(strValue)) {
      return defaultValue;
    } else {
      return Boolean.valueOf(strValue);
    }
  }

  /**
   * Returns the {@code allowInsecureBackend} boolean property.
   *
   * @param def not used; the default is always {@code false}
   * @return the property's value, {@code false} when unset or empty
   */
  public boolean getAllowInsecureBackend(String def) {
    return boolProperty("allowInsecureBackend", false);
  }

  /**
   * Returns the {@code apiBase} property: the path that model request URLs put between the API root
   * and the endpoint path.
   *
   * @return the property's value, {@code /api/rest/v1} when unset
   */
  public String getApiBase() {
    return properties.getProperty("apiBase", String.format("/api/rest/v1"));
  }

  /**
   * Returns the {@code apiRoot} property: the scheme and host that model request URLs start with,
   * unless a request's {@code "api_root"} option replaces it (see {@link #getApiRoot(Map)}).
   *
   * @return the property's value, {@code https://app.files.com} when unset
   */
  public String getApiRoot() {
    return properties.getProperty("apiRoot", "https://app.files.com");
  }

  /**
   * Returns the API root for one request: its {@code "api_root"} option when that is set, and
   * otherwise {@link #getApiRoot()}.
   *
   * @param options the request's options, or {@code null} for none
   * @return the {@code "api_root"} option, or {@link #getApiRoot()} when the option is absent or
   *     {@code null}
   * @throws ApiErrorException.InvalidParameterException if the {@code "api_root"} option is set to
   *     something other than a {@code String}
   */
  public String getApiRoot(Map<String, Object> options) {
    if (options == null || options.get("api_root") == null) {
      return getApiRoot();
    }
    if (!(options.get("api_root") instanceof String)) {
      throw new ApiErrorException.InvalidParameterException("Bad option: api_root must be of type String");
    }
    return (String) options.get("api_root");
  }

  /**
   * Returns the {@code apiKey} property. SDK requests do not read this property; they authenticate
   * with {@link FilesClient#apiKey}, {@link FilesClient#session} or a request's options, as
   * {@link FilesClient} describes.
   *
   * @return the property's value, {@code null} when unset
   */
  public String getApiKey() {
    return properties.getProperty("apiKey", null);
  }

  /**
   * Returns the {@code cachedBufferTinySize} integer property, a size in bytes.
   *
   * @return the property's value, 16384 (16 KiB) when unset or empty
   */
  public int getCachedBufferTinySize() {
    return intProperty("cachedBufferTinySize", 1024 * 16);
  }

  /**
   * Returns the {@code cachedBufferSmallSize} integer property, a size in bytes.
   *
   * @return the property's value, 1048576 (1 MiB) when unset or empty
   */
  public int getCachedBufferSmallSize() {
    return intProperty("cachedBufferSmallSize", 1024 * 1024 /* 1 MB */);
  }

  /**
   * Returns the {@code cachedBufferMediumSize} integer property, a size in bytes.
   *
   * @return the property's value, 16777216 (16 MiB) when unset or empty
   */
  public int getCachedBufferMediumSize() {
    return intProperty("cachedBufferMediumSize", 16777216 /* 16 MB */);
  }

  /**
   * Returns the {@code cachedBufferLargeSize} integer property, a size in bytes. It must be at
   * least the size of the largest upload part the SDK requests.
   *
   * @return the property's value, 50331648 (48 MiB) when unset or empty
   */
  public int getCachedBufferLargeSize() {
    // must be at least the size of the largest requested upload page
    return intProperty("cachedBufferLargeSize", 50331648 /* 48 MB */);
  }

  /**
   * Returns the {@code cachedBufferMaxBytes} integer property, a number of bytes.
   *
   * @return the property's value; when unset or empty, one fifth of the JVM's maximum memory
   *     ({@code Runtime.getRuntime().maxMemory()}) at the time of the call, at most 1610612736
   *     (1.5 GiB)
   */
  public int getCachedBufferMaxBytes() {
    return intProperty("cachedBufferMaxBytes", (int) Math.min(Runtime.getRuntime().maxMemory() / 5,
        1610612736 /* 1.5 GB */));
  }

  /**
   * Returns the {@code connectTimeoutMillis} integer property: how long, in milliseconds, a request
   * of {@link FilesClient#defaultClient()} waits to open a connection. 0 means no limit, and a
   * negative value leaves the limit to the system default. A change applies from the next call. An
   * Apache client passed to {@code FilesHttpExecutor} directly keeps its own timeouts. How long a
   * request of the default client waits for a connection from the SDK's pool is separate: 10
   * seconds, and not configurable. A built client sets it with
   * {@link FilesClient.Builder#connectionAcquisitionTimeout(java.time.Duration)}.
   *
   * @return the property's value, 30000 (30 seconds) when unset or empty
   */
  public int getConnectTimeoutMillis() {
    return intProperty("connectTimeoutMillis", 30000);
  }

  /**
   * Returns the {@code httpLoggingEnabled} boolean property.
   *
   * @return the property's value, {@code true} when unset or empty
   */
  public boolean getHttpLoggingEnabled() {
    return boolProperty("httpLoggingEnabled", true);
  }

  /**
   * Returns the {@code initialRetryDelayMillis} integer property: how long, in milliseconds, the
   * SDK waits before the first retry of a request. Each later retry of that request waits twice as
   * long as the one before, up to {@link #getMaximumRetrySeconds()}. The client the static methods
   * use, {@link FilesClient#defaultClient()}, reads this for every retry, so a change applies from
   * its next retry; a {@link FilesClient} built with {@link FilesClient#builder()} keeps the wait it
   * was built with.
   *
   * @return the property's value, 100 when unset or empty
   */
  public int getInitialRetryDelayMillis() {
    return intProperty("initialRetryDelayMillis", 100);
  }

  /**
   * Returns how many times, at most, the SDK sends a request again after its first attempt: the
   * {@code maxNetworkRetries} integer property, or, when only the older {@code upstreamMaxAttempts}
   * property is set, one less than that. {@link FilesClient#defaultClient()} reads this before each
   * attempt, so a change applies from its next attempt; a {@link FilesClient} built with
   * {@link FilesClient#builder()} keeps the limit it was built with.
   *
   * @return the property's value, 3 when neither property is set
   */
  public int getMaxNetworkRetries() {
    if (!isNullOrEmpty(properties.getProperty("maxNetworkRetries"))) {
      return intProperty("maxNetworkRetries", 3);
    }
    if (!isNullOrEmpty(properties.getProperty("upstreamMaxAttempts"))) {
      return Math.max(0, getUpstreamMaxAttempts() - 1);
    }
    return 3;
  }

  /**
   * Returns the {@code regionRelocation} boolean property: whether requests of
   * {@link FilesClient#defaultClient()} follow Files.com to the server it names for a site, when that
   * server is Files.com's own ({@link RegionRelocationPolicy#FILES_COM_HOSTS}).
   *
   * @return the property's value, {@code true} when unset or empty
   */
  public boolean getRegionRelocation() {
    return boolProperty("regionRelocation", true);
  }

  /**
   * Returns the {@code maximumRetrySeconds} integer property: the longest wait, in seconds, before a
   * retry, including one a server asks for with Retry-After. {@link FilesClient#defaultClient()}
   * reads this for every retry; a {@link FilesClient} built with {@link FilesClient#builder()} keeps
   * the maximum it was built with.
   *
   * @return the property's value, 10 when unset or empty
   */
  public int getMaximumRetrySeconds() {
    return intProperty("maximumRetrySeconds", 10);
  }

  /**
   * Returns the {@code readTimeoutMillis} integer property: how long, in milliseconds, a request of
   * {@link FilesClient#defaultClient()} waits for data from an open connection, including while it
   * reads the response body. It limits each wait between data, not a whole response. 0 means no
   * limit, and a negative value leaves the limit to the system default. A change applies from the
   * next call. An Apache client passed to {@code FilesHttpExecutor} directly keeps its own
   * timeouts.
   *
   * @return the property's value, 80000 (80 seconds) when unset or empty
   */
  public int getReadTimeoutMillis() {
    return intProperty("readTimeoutMillis", 80000);
  }

  /**
   * Returns the {@code sdkVersion} property, which the bundled {@code files-sdk.properties}
   * resource sets.
   *
   * @return the property's value, {@code 0.0.1} when unset
   */
  public String getSdkVersion() {
    return properties.getProperty("sdkVersion", "0.0.1");
  }

  /**
   * Returns the {@code userAgent} property, which API requests send as their {@code User-Agent}
   * header.
   *
   * @return the property's value; when unset, {@code Files.com Java SDK v} followed by
   *     {@link #getSdkVersion()}
   */
  public String getUserAgent() {
    String defaultUserAgent = String.format("Files.com Java SDK v%s", this.getSdkVersion());
    return properties.getProperty("userAgent", defaultUserAgent);
  }

  /**
   * Returns the {@code upstreamHttp2Enabled} boolean property.
   *
   * @return the property's value, {@code true} when unset or empty
   */
  public boolean getUpstreamHttp2Enabled() {
    return boolProperty("upstreamHttp2Enabled", true);
  }

  /**
   * Returns the {@code upstreamInsecureAllowed} boolean property.
   *
   * @return the property's value, {@code false} when unset or empty
   */
  public boolean getUpstreamInsecureAllowed() {
    return boolProperty("upstreamInsecureAllowed", false);
  }

  /**
   * Returns the {@code upstreamMaxAttempts} integer property: how many times, at most, the SDK
   * sends a request that keeps failing in a way it retries, counting the first request. The
   * default, 4, allows the first request and up to 3 retries, and 1 turns the SDK's retries off;
   * set it to at least 1. {@code maxNetworkRetries}, when set, replaces it (see
   * {@link #getMaxNetworkRetries()}, which the SDK reads before each attempt of
   * {@link FilesClient#defaultClient()}). Each resend is one of these attempts: the SDK turns off the
   * Apache HTTP client's own resends. A client passed to {@code FilesHttpExecutor} may retry by its
   * own rules; those resends are not counted here.
   *
   * @return the property's value, 4 when unset or empty
   */
  public int getUpstreamMaxAttempts() {
    return intProperty("upstreamMaxAttempts", 4);
  }

  /**
   * Returns the {@code upstreamMaxConnections} integer property. {@link FilesClient} reads it once,
   * when that class is initialized, as the total size of its connection pool.
   *
   * @return the property's value, 500 when unset or empty
   */
  public int getUpstreamMaxConnections() {
    return intProperty("upstreamMaxConnections", 500);
  }

  /**
   * Returns the {@code upstreamTimeout} integer property. {@link FilesClient} reads it once, when
   * that class is initialized, for the evictor of its connection pool.
   *
   * @return the property's value, 5000 when unset or empty
   */
  public int getUpstreamTimeout() {
    return intProperty("upstreamTimeout", 5000);
  }

  /**
   * Sets a property for the rest of the process, replacing any value loaded or set before. Getters
   * called afterwards return the new value; code that has already read the property keeps the
   * value it read.
   *
   * @param property the property name, such as {@code "apiRoot"}; must not be {@code null}
   * @param value the value, which the property's getter parses as this class describes; must not
   *     be {@code null}
   */
  public void setProperty(String property, String value) {
    properties.setProperty(property, value);
  }

  private boolean isNullOrEmpty(final String input) {
    return input == null || input.isEmpty();
  }
}
