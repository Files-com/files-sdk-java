package com.files;

import java.net.URI;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Decides where a client sends its requests after Files.com answers that the site must be reached
 * through another server. Files.com answers so with HTTP 403 and either:
 *
 * <ul>
 *   <li>a {@code not-authenticated/lockout-region-mismatch} error, whose {@code data.host} names
 *       the site's host; when that is missing, empty or not text, the response's {@code X-Files-Host}
 *       header names it, and without either the policy is given null; or</li>
 *   <li>from older servers, plain text beginning {@code You have connected to a URL}, with an
 *       {@code X-Files-Host} header naming the site's host.</li>
 * </ul>
 *
 * <p>An {@code X-Files-Host} header names a host only when the response has exactly one, and its
 * value is neither empty nor a list of several. The client asks its policy only about such an
 * answer to an API request for a path under its API root, and only when the answer came from that
 * root's own server: every server the request reached, including each redirect it followed, has
 * the root's scheme, host and port, where an IPv6 address written another way, such as
 * {@code [0:0:0:0:0:0:0:1]} for {@code [::1]}, is the same host. An answer from another server,
 * such as one a redirect led to, or from a server the client did not record, fails with its own
 * error without asking the policy, and the plain text then raises a plain
 * {@link com.files.exceptions.ApiErrorException.ApiConnectionException}.
 *
 * <p>When the policy returns a new API root, the client sends the request again there, as one of
 * its retries, and sends the rest of that call's requests there too, such as those of an upload.
 * Only a request with the client's own credentials moves the client's later calls as well; one
 * with the call's own {@code api_key} or {@code session_id}, or one that creates a session,
 * moves only its own call. Credentials go only to the root the policy returns. The SDK accepts
 * only an {@code http} or {@code https} root without user info, path, query or fragment, and
 * never moves from {@code https} to {@code http}; otherwise, when the policy returns null, and when
 * no retry is left, the request fails with the answer's own error: for the typed error,
 * {@link com.files.exceptions.ApiErrorException.LockoutRegionMismatchException}, whose data and
 * headers hold the host; for the plain text,
 * {@link com.files.exceptions.ApiErrorException.LegacyRegionMismatchException}, an
 * {@link com.files.exceptions.ApiErrorException.ApiConnectionException} with the usual message
 * whose {@code getHost()} and {@code getHeaders()} hold the host and the response's headers, but
 * not the text. An integration can catch either error and save the site's server in its own
 * settings. A policy is also given the host, before the client decides; it can record the host and
 * return null to fail rather than follow.
 *
 * <p>The default is {@link #FILES_COM_HOSTS}. Use {@link #DISABLED} to always fail instead, or
 * write a policy that approves your own hosts, for example a custom domain, and otherwise defers
 * to the default:
 *
 * <pre>{@code
 * RegionRelocationPolicy policy = (apiRoot, host) -> "files.example.com".equalsIgnoreCase(host)
 *     ? URI.create("https://files.example.com")
 *     : RegionRelocationPolicy.FILES_COM_HOSTS.relocate(apiRoot, host);
 * }</pre>
 */
@FunctionalInterface
public interface RegionRelocationPolicy {
  /**
   * Relocates only to Files.com itself: {@code https://} followed by the host, when the host is
   * {@code files.com} or a name under it, such as {@code mysite.files.com}. A host with a port,
   * user info, path or any other character is refused.
   */
  RegionRelocationPolicy FILES_COM_HOSTS = new RegionRelocationPolicy() {
    private final Pattern filesComHost =
        Pattern.compile("([a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?\\.)*files\\.com", Pattern.CASE_INSENSITIVE);

    @Override
    public URI relocate(URI apiRoot, String host) {
      if (host == null || host.length() > 253 || !filesComHost.matcher(host).matches()) {
        return null;
      }
      return URI.create("https://" + host.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {
      return "RegionRelocationPolicy.FILES_COM_HOSTS";
    }
  };

  /**
   * Never relocates: a region mismatch fails with its own error, as described above.
   */
  RegionRelocationPolicy DISABLED = new RegionRelocationPolicy() {
    @Override
    public URI relocate(URI apiRoot, String host) {
      return null;
    }

    @Override
    public String toString() {
      return "RegionRelocationPolicy.DISABLED";
    }
  };

  /**
   * Chooses where to send the site's requests.
   *
   * @param apiRoot the API root the refused request went to, such as {@code https://app.files.com}
   * @param host the host Files.com named for the site, exactly as it sent it in the error's
   *     {@code data.host} or the {@code X-Files-Host} header; null when the typed error names none
   * @return the API root to use instead, such as {@code https://mysite.files.com}, or null to fail
   *     with the region mismatch error
   */
  URI relocate(URI apiRoot, String host);
}
