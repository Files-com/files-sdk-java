package com.files;

import java.net.URI;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Decides where a client sends its requests after Files.com answers that the site must be reached
 * through another server: a {@code not-authenticated/lockout-region-mismatch} error, whose data
 * names the site's host.
 *
 * <p>When the policy returns a new API root, the client sends the request again there, as one of
 * its retries, and sends the rest of that call's requests there too, such as those of an upload.
 * Only a request with the client's own credentials moves the client's later calls as well; one
 * with the call's own {@code api_key} or {@code session_id}, or one that creates a session,
 * moves only its own call. Credentials go only to the root the policy returns. The SDK accepts
 * only an {@code http} or {@code https} root without user info, path, query or fragment, and
 * never moves from {@code https} to {@code http}; otherwise, and when the policy returns null,
 * the request fails with
 * {@link com.files.exceptions.ApiErrorException.LockoutRegionMismatchException}.
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
   * Never relocates: a region mismatch fails with
   * {@link com.files.exceptions.ApiErrorException.LockoutRegionMismatchException}.
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
   * @param host the host Files.com named for the site, exactly as it sent it; may be null
   * @return the API root to use instead, such as {@code https://mysite.files.com}, or null to fail
   *     with the region mismatch error
   */
  URI relocate(URI apiRoot, String host);
}
