# Files.com Java Client

The Files.com Java Client provides a direct, high performance integration to Files.com from applications written in Java.

Files.com is the cloud-native, next-gen MFT, SFTP, and secure file-sharing platform that replaces brittle legacy servers with one always-on, secure fabric. Automate mission-critical file flows—across any cloud, protocol, or partner—while supporting human collaboration and eliminating manual work.

With universal SFTP, AS2, HTTPS, and 50+ native connectors backed by military-grade encryption, Files.com unifies governance, visibility, and compliance in a single pane of glass.

The content included here should be enough to get started, but please visit our
[Developer Documentation Website](https://developers.files.com/java/) for the complete documentation.

## Introduction

The Files.com Java client library provides convenient access to the Files.com API from JVM based applications.

### Installation

Add the SDK to your Java application with Maven or Gradle. These build tools download the SDK
and the libraries it needs. Choose the instructions that match your project's build file.
If you are starting your first Java project, the [Maven getting-started guide](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
explains how to install Maven and create a project.

The SDK is published to [Maven Central](https://central.sonatype.com/artifact/com.files/files-sdk) as
`com.files:files-sdk`. Use the latest 2.x version shown there in place of `2.0.0` below.

#### Maven

In your application's `pom.xml`, add this dependency inside the `<dependencies>` element under
`<project>`. If `<dependencies>` already exists, copy only the `<dependency>` element into it.

```xml
<dependencies>
    <dependency>
        <groupId>com.files</groupId>
        <artifactId>files-sdk</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```

From the directory containing `pom.xml`, download the dependencies and compile your application:

```bash
mvn compile
```

#### Gradle

In your Java application's `build.gradle`, add Maven Central to `repositories` and the SDK to
`dependencies`. Add the entries to those blocks if they already exist.

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.files:files-sdk:2.0.0'
}
```

For `build.gradle.kts`, use the Kotlin syntax:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.files:files-sdk:2.0.0")
}
```

From your project directory, use its Gradle wrapper to download the dependencies and compile:

```bash
./gradlew classes
```

On Windows, run `gradlew.bat classes`. If your project has no wrapper, use your installed
Gradle with `gradle classes`.

#### Requirements

The SDK runs on Java 8 or later. Use a Java release that still receives security updates from its
vendor; for new applications we recommend Java 25.

The SDK logs through the SLF4J API and leaves the choice of logging provider to your
application; see Logging in the configuration documentation.

### Files.com is Committed to Java

Java is a core language used by the Files.com team for internal development.  This library is directly used by our official Boomi integration and our official MuleSoft integration.

As such, this library is actively developed and should be expected to be highly performant.

Explore the [files-sdk-java](https://github.com/Files-com/files-sdk-java) code on GitHub.

### Getting Support

The Files.com Support team provides official support for all of our official Files.com integration tools.

To initiate a support conversation, you can send an [Authenticated Support Request](https://www.files.com/docs/overview/requesting-support) or simply send an E-Mail to support@files.com.

## Authentication

There are two ways to authenticate: API Key authentication and Session-based authentication.

### Authenticate with an API Key

Authenticating with an API key is the recommended authentication method for most scenarios, and is
the method used in the examples on this site.

To use an API Key, first generate an API key from the [web
interface](https://www.files.com/docs/sdk-and-apis/api-keys) or [via the API or an
SDK](https://developers.files.com/java/resources/developers/api-keys).

Note that when using a user-specific API key, if the user is an administrator, you will have full
access to the entire API. If the user is not an administrator, you will only be able to access files
that user can access, and no access will be granted to site administration functions in the API.

```java title="Example Request"
import com.files.FilesClient;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

FilesClient.apiKey = "YOUR_API_KEY";

try {
  // Alternatively, you can specify the API key on a per-object basis in options HashMap to a model constructor.
  HashMap<String, Object> requestOptions = new HashMap<>();
  requestOptions.put("api_key", "my-key");
  User user = new User(params, requestOptions);

  // You may also specify the API key on a per-request basis in the final parameter to static methods.
  HashMap<String, Object> perRequestOptions = new HashMap<>();
  perRequestOptions.put("api_key", "my-key");
  User.find(id, params, perRequestOptions);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

Don't forget to replace the placeholder, `YOUR_API_KEY`, with your actual API key.

### Authenticate with a Session

You can also authenticate by creating a user session using the username and
password of an active user. If the user is an administrator, the session will have full access to
all capabilities of Files.com. Sessions created from regular user accounts will only be able to access files that
user can access, and no access will be granted to site administration functions.

Sessions use the exact same session timeout settings as web interface sessions. When a
session times out, simply create a new session and resume where you left off. This process is not
automatically handled by our SDKs because we do not want to store password information in memory without
your explicit consent.

#### Logging In

To create a session, the `create` method is called on the `Session` object with the user's username and
password.

This returns a session object that can be used to authenticate SDK method calls.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.Session;
import java.util.HashMap;

HashMap<String, Object> sessionParameters = new HashMap<>();
sessionParameters.put("username", "username");
sessionParameters.put("password", "password");

try {
  Session session = Session.create(sessionParameters);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

#### Using a Session

Once a session has been created, you can store the session globally, use the session per object, or use the session per request to authenticate SDK operations.

```java title="Example Request"
import com.files.FilesClient;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// You may set the returned session to be used by default for subsequent requests.
FilesClient.session = session;

try {
  // Alternatively, you can specify the session ID on a per-object basis in the second parameter to a model constructor.
  HashMap<String, Object> requestOptions = new HashMap<>();
  requestOptions.put("session_id", session.getId());
  user = new User(params, requestOptions);

  // You may also specify the session ID on a per-request basis in the final parameter to static methods.
  HashMap<String, Object> perRequestOptions = new HashMap<>();
  perRequestOptions.put("session_id", session.getId());
  User.find(id, params, perRequestOptions);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

#### Logging Out

User sessions can be ended calling the `destroy` method on the `session` object.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;

try {
  session.destroy();
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

## Configuration

A client built with `FilesClient.builder()` takes its configuration from its builder; see Clients
and Connections. The static methods, such as `User.list()`, are configured by calling
`FilesClient.setProperty()`.

### Configuration Options

#### Base URL

Set this to the full https:// URL of your Files.com subdomain (e.g. `https://MY-SUBDOMAIN.files.com`).
This is not required in most cases, but one benefit of setting it is that it ensures that authentication failures will be logged to your site's API logs.  Without setting this, we won't know which site to associate the authentication failure with, and it won't be logged to your site's API logs.
This is always required if your site is configured to disable global acceleration.
This can also be set to use a mock server in development or CI.

```java title="Example setting"
import com.files.FilesClient;

FilesClient.setProperty("apiRoot", "https://MY-SUBDOMAIN.files.com");

FilesClient client = FilesClient.builder()
    .apiKey("YOUR_API_KEY")
    .apiRoot("https://MY-SUBDOMAIN.files.com")
    .build();
```

#### Retries and Time Limits

These properties configure the static methods; a built client has builder methods of the same
meaning.

* `maxNetworkRetries`: the most times a request is sent again after its first attempt. Default 3.
* `initialRetryDelayMillis`: the wait before the first retry, doubling for each later one. Default 100.
* `maximumRetrySeconds`: the longest wait before a retry, including a server's Retry-After. Default 10.
* `connectTimeoutMillis` and `readTimeoutMillis`: the longest waits to connect and for the next
  bytes of a response. Defaults 30000 and 80000.
* `regionRelocation`: whether requests follow Files.com to its own server for a site after a
  region mismatch. Default true.

```java title="Example setting"
import com.files.FilesClient;

FilesClient.setProperty("maxNetworkRetries", "5");
FilesClient.setProperty("readTimeoutMillis", "120000");
```

#### Timeouts

`connectTimeoutMillis` is how long the SDK waits to open a connection, 30000 (30 seconds) by default.
`readTimeoutMillis` is how long it waits for data from an open connection, 80000 (80 seconds) by default; it limits each wait, not a whole response.
Both are in milliseconds; 0 means no limit, and a negative value leaves the limit to the system default.
The static methods read them when each call starts, so a change applies from the next call; a client you build keeps the timeouts it was built with.
How long a request of the static methods waits for a connection from the SDK's connection pool is separate: 10 seconds, and not configurable. A client you build sets it with `connectionAcquisitionTimeout`.

```java title="Example setting"
import com.files.FilesClient;

FilesClient.setProperty("connectTimeoutMillis", "30000");
FilesClient.setProperty("readTimeoutMillis", "80000");
```

#### Retry Attempts

`upstreamMaxAttempts` is how many times, at most, the SDK sends a request that keeps failing in a way it retries, counting the first request.
The default, 4, allows the first request and up to 3 retries. Set it to 1 to turn the SDK's retries off; it must be at least 1. `maxNetworkRetries`, when set, takes its place.
The SDK reads it before each attempt.
The SDK retries a request after a connection or read failure that may be temporary, HTTP 429, HTTP 502 or above, or HTTP 500 in answer to a GET.
It does not retry HTTP 501, a URL it cannot send, a certificate or host name that does not verify, or redirects that loop or go past the limit.
Each resend is one of these attempts: the SDK turns off the Apache HTTP client's own resends.

```java title="Example setting"
import com.files.FilesClient;

FilesClient.setProperty("upstreamMaxAttempts", "4");
```

#### Retry Delay

`initialRetryDelayMillis` is how long, in milliseconds, the SDK waits before the first retry of a request, 100 by default.
Each later retry of that request waits twice as long as the one before.
The SDK reads it for every retry, so a change applies from the next retry.

```java title="Example setting"
import com.files.FilesClient;

FilesClient.setProperty("initialRetryDelayMillis", "100");
```

### Logging

The SDK logs through the [SLF4J](https://www.slf4j.org/) 2 API, under logger names that begin
with `com.files`. Apache HttpClient, which sends the SDK's requests, logs under `org.apache.http`.
The SDK includes no logging provider or configuration, so your application's provider and
configuration decide what is written. Without a provider, SLF4J prints a one-time notice that it
found none, and the messages are discarded.

The SDK logs warnings, such as retried requests. At DEBUG level, `com.files` logs each request's
URL, parameters and options, and `org.apache.http` logs request headers. Both can include your API
key, so keep these loggers at INFO or above in production.

To send the SDK's messages to Log4j, add the Log4j SLF4J 2 provider and Log4j Core to your
application:

```xml title="pom.xml"
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.26.1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.26.1</version>
</dependency>
```

Then choose the `com.files` level in your Log4j configuration:

```xml title="src/main/resources/log4j2.xml"
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="com.files" level="WARN"/>
        <Root level="INFO">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

Other SLF4J providers, such as Logback, are set up the same way. You can read more about
[Log4j configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html).

#### SLF4J 1.7

The SDK only calls SLF4J methods that version 1.7 also provides. An application that manages
`org.slf4j:slf4j-api` at 1.7.x can keep it, together with a 1.7 binding such as
`org.slf4j:slf4j-simple:1.7.36`. Keep the API and the provider on the same SLF4J generation:
SLF4J 2 ignores 1.7 bindings, and SLF4J 1.7 cannot load SLF4J 2 providers, so either mix loses the
SDK's messages.

## Clients and Connections

A `FilesClient` holds everything its requests need: the API root of your site, credentials, a
workspace, time limits, retries and the connections its requests use. Build one with
`FilesClient.builder()`, reach each resource's operations from it, such as `client.users()`, and
close it when you are done.

Its settings never change once it is built, so several clients, for different users, sites or
workspaces, can work at the same time. What a client returns keeps using that client and the
request options it was requested with: saving a returned `User`, or loading the next page of a
listing, sends the same credentials to the same site, whatever you change afterward. A client is
safe to use from many threads.

```java title="Example Request"
import com.files.FilesClient;
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;

try (FilesClient client = FilesClient.builder()
    .apiKey("YOUR_API_KEY")
    .apiRoot("https://MY-SUBDOMAIN.files.com")
    .workspaceId(0L)
    .build()) {
  try {
    ListIterator<User> users = client.users().list();
    for (User user : users.listAutoPaging()) {
      user.setNotes("Reviewed");
      user.save();
    }
  } catch (NotAuthenticatedException e) {
    System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
  } catch (SdkException e) {
    System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
  }
}
```

### The Static Methods

The static methods, such as `User.list()`, use `FilesClient.defaultClient()`: a client with the
static settings `FilesClient.apiKey`, `FilesClient.session`, `FilesClient.workspaceId`,
`FilesClient.language` and the `FilesClient.setProperty` properties as they are when each call is
made. Objects and listings the static methods return keep the settings they were requested
with, so changing `FilesClient.apiKey` later affects only later calls. The default client reads
`maxNetworkRetries` (or `upstreamMaxAttempts`), `initialRetryDelayMillis` and
`maximumRetrySeconds` again before each attempt of a request, so a change also reaches a request
already retrying; uploads and downloads keep the values they started with.

An object you construct yourself, such as `new User(attributes)`, uses the default client until
you give it another with `setClient`.

```java title="Example Request"
import com.files.FilesClient;
import com.files.models.User;
import java.util.HashMap;

FilesClient.apiKey = "YOUR_API_KEY";
User user = User.find(userId, null);

HashMap<String, Object> attributes = new HashMap<>();
attributes.put("username", "new-user");
User created = new User(attributes);
created.setClient(client);
created.save();
```

### Request Options

Each operation also takes a map of request options for that one call:

* `api_key` or `session_id` authenticates with other credentials.
* `workspace_id` acts in another workspace; `null` sends none.
* `api_root` sends the call to another site, such as `https://OTHER-SUBDOMAIN.files.com`; the
  client's own API root does not change.
* `cancellation` is a `CancellationToken` that stops the call.
* `timeout` is a `java.time.Duration`: the time limit of the call, across all its attempts.
* `max_retries` is an `Integer` that replaces the client's retry limit.

The maps you pass are copied, so changing them afterward changes nothing. Objects a call
returns keep its `api_key`, `session_id`, `workspace_id` and `api_root`; a listing also keeps the
other options for the pages it requests.

```java title="Example Request"
import com.files.models.User;
import java.time.Duration;
import java.util.HashMap;

HashMap<String, Object> options = new HashMap<>();
options.put("workspace_id", 12L);
options.put("timeout", Duration.ofSeconds(20));

User user = client.users().find(userId, null, options);
```

### Retries

A request that fails in a way that may be temporary is sent again, up to `maxRetries` times
(3 by default, and 0 to send each request once): after a connection or read failure, HTTP 429,
HTTP 502 or above, or HTTP 500 in answer to a GET. HTTP 501 is never retried, and neither is
HTTP 500 in answer to another method. The first retry waits `retryDelay` (100 ms), and each later one
twice as long as the one before, up to `maxRetryDelay` (10 seconds). After HTTP 429 or 503, a
longer Retry-After within that maximum is waited instead.

A request is not retried when it cannot be sent as written, such as a URL without a host or with
a scheme other than HTTP or HTTPS, when the server's certificate or name does not verify, or when
its redirects loop or go past the limit. Other TLS and protocol failures are retried. A retried
request that changes data may take effect twice when an earlier attempt reached Files.com but its
answer was lost.

A retry is logged at WARN with its number, its wait and why it was needed, such as
`Retrying the GET request (retry 1 of 3) in 100 ms after HTTP status 503`. A failure without a
response is described by its kind, such as `the connection could not be made (ConnectException)`,
in logs and in the exception's message; the message of the underlying exception, which can repeat
URLs or other details, is logged only at DEBUG. The exception's message then names the request
by its method and path, without its query or host, such as `(response to GET /api/rest/v1/users/1)`,
as does the message of an error response without a structured error.

```java title="Example Request"
import com.files.FilesClient;
import java.time.Duration;

FilesClient client = FilesClient.builder()
    .apiKey("YOUR_API_KEY")
    .maxRetries(5)
    .retryDelay(Duration.ofMillis(250))
    .maxRetryDelay(Duration.ofSeconds(30))
    .build();
```

### Site Regions

Some sites must be reached through a server of their own. When Files.com answers a request with
a `not-authenticated/lockout-region-mismatch` error naming that server, the client sends the
request there instead, as one of its retries, and sends the rest of that call's requests there,
such as those of an upload. When the request used the client's own credentials, the client keeps
sending its later requests there too; a call with its own `api_key` or `session_id`, or one that
creates a session, moves only itself, and the next such call starts at the client's server
again, unless it continues an upload, with the same options, from the part that call returned,
as `FileUploadPart.putInputStream` does. By default it does so only for Files.com's own servers,
whose names end in `files.com`, over HTTPS; the credentials never go anywhere else.
`RegionRelocationPolicy.DISABLED` reports the error instead, as `LockoutRegionMismatchException`,
and a policy of your own can approve your custom domain. For the static methods, set the
`regionRelocation` property to `false` to disable it.

```java title="Example Request"
import com.files.FilesClient;
import com.files.RegionRelocationPolicy;
import java.net.URI;

FilesClient client = FilesClient.builder()
    .apiKey("YOUR_API_KEY")
    .regionRelocation((apiRoot, host) -> "files.example.com".equalsIgnoreCase(host)
        ? URI.create("https://files.example.com")
        : RegionRelocationPolicy.FILES_COM_HOSTS.relocate(apiRoot, host))
    .build();
```

### Time Limits and Cancellation

A client waits up to `connectTimeout` (30 seconds) to connect, `readTimeout` (80 seconds) for
the next bytes of a response, both while waiting for its headers and while reading its body,
and `connectionAcquisitionTimeout` (10 seconds) for a connection from its pool. `callTimeout`,
or the `timeout` request option, limits a whole call, its retries and waits included.

A call stops when its `CancellationToken` is cancelled, its time limit passes, the thread
making it is interrupted, or its client is closed. Its request is aborted, even while it waits
for the server or reads a response, and the call throws `OperationCancelledException`, whose
`getReason()` is `CANCELLED`, `TIMED_OUT` or `INTERRUPTED`. An interrupted thread keeps its
interrupt status, and a call made on a thread that is already interrupted stops at once.

One exception: a read of your own stream, such as an attachment's, cannot be interrupted in
Java 8. The call stops as soon as that read returns.

```java title="Example Request"
import com.files.CancellationToken;
import com.files.exceptions.OperationCancelledException;
import java.util.HashMap;

CancellationToken token = new CancellationToken();
HashMap<String, Object> options = new HashMap<>();
options.put("cancellation", token);

// On another thread, when the work is no longer needed:
token.cancel();

try {
  client.users().list(null, options).all();
} catch (OperationCancelledException e) {
  System.out.println("Stopped: " + e.getReason());
}
```

### Connections

Each client has an `HttpTransport` of its own, an Apache HttpClient with its connection pool,
unless you give it one to share. Give a client `HttpTransport.builder()` settings to configure
its own transport, which it closes when it is closed. Build a transport yourself and give it to
several clients to share its connections: closing a client leaves it open, and you close it once
no client uses it.

`customize` adjusts the Apache `HttpClientBuilder`, for a proxy, credentials for it,
interceptors for instrumentation or default headers. `sslContext` sets trusted certificates or a
client certificate; `connectionManager` uses a pool you manage. Whatever you customize, the SDK
turns off Apache's own retries, so each request stays within the client's retry limit, and it
removes Files.com credentials from every request, first or redirected, that is not sent to the
API root its call started at. It does so as each request is written to its connection, so replacing the HTTP
processor, request executor or redirect strategy does not change it.

Closing a client cancels its calls and transfers still running and waits for them to stop, even
if the closing thread is interrupted, which then keeps its interrupt status; a second `close`
waits the same way. A transfer blocked reading your own stream stops once that read returns. A
`close` from within a call or transfer, such as in a progress listener, cancels the same way but
returns at once, since the work it would wait for may be waiting for it. Afterward the client, and
what it returned, throw `IllegalStateException` for new requests.

```java title="Example Request"
import com.files.FilesClient;
import com.files.net.HttpTransport;
import org.apache.http.HttpHost;

try (HttpTransport shared = HttpTransport.builder()
    .maxConnections(100)
    .customize(http -> http.setProxy(new HttpHost("proxy.example.com", 8080)))
    .build()) {
  FilesClient first = FilesClient.builder().apiKey("FIRST_KEY").transport(shared).build();
  FilesClient second = FilesClient.builder().apiKey("SECOND_KEY").transport(shared).build();
  // ...
  first.close();
  second.close();
}
```

## Sort and Filter

Several of the Files.com API resources have list operations that return multiple instances of the
resource. The List operations can be sorted and filtered.

### Sorting

To sort the returned data, pass in the ```sort_by``` method argument.

Each resource supports a unique set of valid sort fields and can only be sorted by one field at a
time.

The argument value is a Java ```HashMap<String, Object>``` object that has a property of the
resource field name sort on and a value of either ```"asc"``` or ```"desc"``` to specify the sort
order.

#### Special note about the List Folder Endpoint

For historical reasons, and to maintain compatibility
with a variety of other cloud-based MFT and EFSS services, Folders will always be listed before Files
when listing a Folder.  This applies regardless of the sorting parameters you provide.  These *will* be
used, after the initial sort application of Folders before Files.

```java title="Sort Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users sorted by username
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> sortArgs = new HashMap<>();
sortArgs.put("username", "asc");
args.put("sort_by", sortArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Filtering

Filters apply selection criteria to the underlying query that returns the results. They can be
applied individually or combined with other filters, and the resulting data can be sorted by a
single field.

Each resource supports a unique set of valid filter fields, filter combinations, and combinations of
filters and sort fields.

The passed in argument value is a Java ```HashMap<String, Object>``` object that has a key of the
resource field name to filter on and a passed in value to use in the filter comparison.

#### Filter Types

| Filter | Type | Description |
| --------- | --------- | --------- |
| `filter` | Exact | Find resources that have an exact field value match to a passed in value. (i.e., FIELD_VALUE = PASS_IN_VALUE). |
| `filter_prefix` | Pattern | Find resources where the specified field is prefixed by the supplied value. This is applicable to values that are strings. |
| `filter_gt` | Range | Find resources that have a field value that is greater than the passed in value.  (i.e., FIELD_VALUE > PASS_IN_VALUE). |
| `filter_gteq` | Range | Find resources that have a field value that is greater than or equal to the passed in value.  (i.e., FIELD_VALUE >=  PASS_IN_VALUE). |
| `filter_lt` | Range | Find resources that have a field value that is less than the passed in value.  (i.e., FIELD_VALUE < PASS_IN_VALUE). |
| `filter_lteq` | Range | Find resources that have a field value that is less than or equal to the passed in value.  (i.e., FIELD_VALUE \<= PASS_IN_VALUE). |

```java title="Exact Filter Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// non admin users
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
filterArgs.put("not_site_admin", true);
args.put("filter", filterArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

```java title="Range Filter Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users who have logged in on or after 2024-01-01
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
filterArgs.put("last_login_at", "2024-01-01");
args.put("filter_gteq", filterArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

```java title="Pattern Filter Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users whose usernames start with 'test'
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
filterArgs.put("username", "test");
args.put("filter_prefix", filterArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

```java title="Combination Filter with Sort Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users whose usernames start with 'test' and are not admins
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterPrefixArgs = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
HashMap<String, Object> sortArgs = new HashMap<>();
filterPrefixArgs.put("username", "test");
filterArgs.put("not_site_admin", true);
sortArgs.put("last_login_at", "asc");
args.put("filter_prefix", filterPrefixArgs);
args.put("filter", filterArgs);
args.put("sort_by", sortArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

## Paths

Files.com preserves the spelling of file and folder paths while comparing them using shared case and Unicode rules. Use the SDK comparison helpers when matching paths locally.
<div></div>

### Capitalization

Files.com uses case-insensitive path matching based on its fixed Unicode comparison map.

For example, the following paths have the same comparison key:

| Path Variant                          | Comparison Key              |
|---------------------------------------|------------------------------|
| `Documents/Reports/Q1.pdf`            | `documents/reports/q1.pdf`  |
| `documents/reports/q1.PDF`            | `documents/reports/q1.pdf`  |
| `DOCUMENTS/REPORTS/Q1.PDF`            | `documents/reports/q1.pdf`  |

This behavior applies across:
- API requests
- Folder and file lookup operations
- Automations and workflows

See also: [Case Sensitivity Documentation](https://www.files.com/docs/files-and-folders/case-sensitivity/)

The `PathUtils.isSame` function in the Files.com SDK is designed to help you determine if two paths on
your native file system would be considered the same on Files.com. This is particularly important
when handling errors related to duplicate file names and when developing tools for folder
synchronization.

```java title="Compare Case-Insensitive Files and Paths"
import com.files.util.PathUtils;

if (PathUtils.isSame("Fïłèńämê.Txt", "filename.txt")) {
    System.out.println("Paths are the same");
}
```

### Slashes

Use `/` between folder and file names, without leading or trailing slashes. SDK normalization helpers convert backslashes to `/`, remove duplicate separators, and discard exact `.` and `..` components. Discarding `..` leaves the preceding folder name intact.

| Input | Normalized path |
|-------|-----------------|
| `folder/subfolder/file.txt` | `folder/subfolder/file.txt` |
| `/folder/subfolder/file.txt` | `folder/subfolder/file.txt` |
| `folder/subfolder/file.txt/` | `folder/subfolder/file.txt` |
| `//folder//file.txt` | `folder/file.txt` |
| `folder/../file.txt` | `folder/file.txt` |

<div></div>

### Unicode and Path Comparison

Files.com compares paths using a fixed mapping shared by the server and SDKs. It treats case and many accent differences as equivalent: `Résumé.txt` and `resume.txt` identify the same file, as do `q` followed by a combining acute accent and `q`. The mapping also handles other equivalences, such as Hiragana and Katakana. Lowercasing or applying a standard Unicode normalization form alone does not reproduce these rules.

SDK comparison helpers normalize path separators and dot segments, then apply the bundled [versioned comparison map](https://github.com/Files-com/files-sdk-javascript/blob/master/shared/path_comparison.json). The [shared examples](https://github.com/Files-com/files-sdk-javascript/blob/master/shared/comparison_examples.json) give exact comparison results for integrations that implement their own matching. The map uses hexadecimal Unicode scalar values as keys: a missing entry preserves the character, an empty replacement removes it, and other replacements may contain several characters. Apply each replacement once without normalizing or lowercasing the result again.

Use comparison results only for matching. Send the original path spelling in API requests and preserve it for display and local filenames; comparison results can have a different spelling or length.

Trailing whitespace is significant for comparison. `report.txt` and `report.txt ` are different file paths, and SDK helpers preserve spaces, tabs, and newlines. Folder names cannot end in whitespace. See [Unicode Normalization](https://www.files.com/docs/files-and-folders/file-system-semantics/unicode-normalization) for the complete path rules.

<div></div>

## Workspaces

A Workspace groups files, users, groups, Partners, integrations, and workflows within a Files.com Site. An integration can provision a Workspace for a department or project and delegate its operation to a team without making that team Site Administrators. Every Site has a Default Workspace, with ID `0`; additional Workspaces have their own IDs and root folders.

Account membership, request context, and permission grants serve different purposes. Creating an account in a Workspace determines where it belongs. Selecting a Workspace determines which resources a request operates on. A permission grant determines what the caller can do there. Selecting a Workspace never grants access to it.

### Accounts and Administrative Access

A user's or group's `workspace_id` identifies the Workspace the account belongs to. Accounts belonging to a Custom Workspace stay within it. Default Workspace users and groups can receive permissions in one or more Custom Workspaces while keeping their existing accounts in Workspace `0`.

| Account | Workspace Administrator assignment | Scope |
| --- | --- | --- |
| User belonging to a Custom Workspace | Set the user's `workspace_admin` to `true`. | That user's own Custom Workspace. |
| Default Workspace user | Create an `admin` Permission for the user on a Custom Workspace's root folder. | Each Custom Workspace with a root grant. |
| Default Workspace group | Create an `admin` Permission for the group on a Custom Workspace's root folder. | Every member inherits administration of each Workspace with a root grant. |

`workspace_admin` is not a summary of a user's effective administrative access. A Default Workspace user can administer a Custom Workspace through a direct or group root grant while their `workspace_admin` remains `false`. Groups have no `workspace_admin` field. See [Users](https://developers.files.com/java/resources/user-accounts/users) and [Groups](https://developers.files.com/java/resources/user-accounts/groups) for account fields.

An `admin` grant on the **Custom Workspace root** provides full Workspace Administrator authority over its files, users, groups, Partners, workflows, and integrations. An `admin` grant on a subfolder provides Folder Admin authority over that folder and its descendants; it does not provide Workspace administration. Other permission levels provide their corresponding folder access without Workspace administration. [Permissions](https://developers.files.com/java/resources/user-accounts/permissions) defines the levels.

Site Administrators manage cross-Workspace assignments to Default Workspace accounts. Workspace Administrators manage accounts and permissions within their own scope. Site Administrators retain access to every Workspace; adding a Workspace grant does not narrow Site Administrator authority. The [product documentation](https://www.files.com/docs/workspaces/workspace-administrators) explains the administrator's operational scope and site-wide controls.

### Request Context and API Keys

You can include the `X-Files-Workspace-Id` REST header to select a Workspace for a request. SDK request options and CLI configuration send that same selection. When a Workspace is selected, Workspace-scoped resources are listed, created, and changed within that context, and ordinary paths are relative to its root.

A resource's `workspace_id` request field describes the resource's Workspace membership. It is separate from the SDK's Workspace request option or REST header. Creating a Workspace-scoped resource in a Custom Workspace defaults its `workspace_id` to the selected Workspace; a mismatching membership value is rejected with `not-authorized/insufficient-permission-for-params`.

Selecting another Workspace with an API key requires a **Full Access key created in the Default Workspace**. A user key follows that user's current access, including group permissions. A site-wide Full Access key created in the Default Workspace has Site Administrator authority in every Workspace. A Files Only key stays in its creation Workspace, even if its user has cross-Workspace access. Any key created in a Custom Workspace stays within that Workspace. Selecting another context with these confined keys is rejected with `bad-request/invalid-workspace-id-header`.

An account belonging to a Custom Workspace is scoped there when it authenticates normally. For a Default Workspace user, explicitly select the intended Workspace for an integration rather than relying on an interactive login preference. [API Keys](https://developers.files.com/java/resources/developers/api-keys) and [Authentication](https://developers.files.com/java/overview/authentication) cover credentials.

The Files.com Java SDK supports workspace scoping by using the `FilesClient.workspaceId` configuration attribute. Scope a single request by passing `workspace_id` in the request options.

The adjacent scoping example uses a credential authorized for the selected Workspace. A group member uses their own Full Access user key from the Default Workspace; the Site Administrator credential used to assign the grant is not needed for their day-to-day work.

```java title="Example Request"
import com.files.FilesClient;
import com.files.models.Folder;
import java.util.HashMap;

FilesClient.workspaceId = 123L;

HashMap<String, Object> options = new HashMap<>();
options.put("workspace_id", 456L);

Folder.listFor("/", null, options);
```

### Delegating a Workspace to an Existing Group

An operations team already represented by a Default Workspace group can administer a Custom Workspace through one root Permission. The group and its members stay in the Default Workspace, so the same team can receive different access in other Workspaces.

First retrieve the target [Workspace](https://developers.files.com/java/resources/settings/workspaces) and [Group](https://developers.files.com/java/resources/user-accounts/groups) IDs as a Site Administrator in Workspace `0`. The examples use Workspace `123`, group `456`, and member user `789`; replace them with your own IDs. Confirm that the group belongs to Workspace `0` and that the intended user is a member.

Create the Permission using a Default Workspace Full Access site-wide key or a Full Access user key belonging to a Site Administrator. Keep the request context at `0` and use the qualified root path `_/Workspaces/123`. Set `group_id` to the group's ID, `permission` to `admin`, and `recursive` to `true`. Save the returned Permission `id` for later removal. For an individual Default Workspace user, use `user_id` instead of `group_id`.

For a Default Workspace group, a Site Administrator can also select Workspace `123` and use an empty `path` to grant access to its root. The qualified path in Workspace `0` works for both Default Workspace users and groups and keeps the account scope and target Workspace explicit. Appending a subfolder to the path would grant Folder Admin access instead of Workspace Administrator authority.

After the grant, make a request as the member, using their own credential with Workspace `123` selected, such as listing that Workspace's root folder. That member can work with the Workspace's files and perform Workspace Administrator operations, such as managing its users, Partners, and integrations. A Site Administrator's successful request does not establish that the member has the intended access.

```java title="Grant group administration"
import com.files.FilesClient;
import com.files.models.Permission;
import java.util.HashMap;

FilesClient.workspaceId = 0L;
HashMap<String, Object> params = new HashMap<>();
params.put("path", "_/Workspaces/123");
params.put("group_id", 456L);
params.put("permission", "admin");
params.put("recursive", true);
HashMap<String, Object> options = new HashMap<>();
options.put("workspace_id", 0L);
Permission grant = Permission.create(params, options);
System.out.println(grant.getId());
```

### Permission Inspection and Removal

List the member's Permissions with `user_id` and `include_groups=true` to include grants inherited through group membership. Listing only direct user grants can miss the Permission that provides Workspace administration. In Workspace `0`, the Custom Workspace root appears as `_/Workspaces/123`; in Workspace `123`, paths are relative to that root. Inspect the root path and `permission=admin`, rather than treating the user's `workspace_admin` field as their effective administrative access.

Permission lists show individual grants, rather than a single flag for effective administrative access. Membership in several groups combines their access. A Permission using `group_ids` instead of `group_id` requires membership in all the specified groups; it is not a shorthand for assigning the same grant to several independent groups.

Removing a member ends access received through that group. Deleting the root Permission ends the group's Workspace Administrator grant for every member. These changes leave independent direct and other group grants in place, so review all applicable grants when withdrawing access. Default Workspace user API keys follow those permission changes without being recreated.

Group membership maintained through SCIM follows the same rule. A Group Admin allowed to add members can give those users the group's existing Workspace Administrator access. Choose who manages the group with that authority in mind.

Delete the Permission by its returned `id` as the Site Administrator in Workspace `0`. The removal examples use Permission ID `9001`; replace it with the ID returned by your create request. Permissions are created and deleted, rather than updated in place. If narrower folder access is still needed, assign it explicitly; deleting a broad grant does not restore narrower grants it previously replaced.

```java title="Inspect member grants and remove the group grant"
HashMap<String, Object> query = new HashMap<>();
query.put("user_id", "789");
query.put("include_groups", true);
com.files.ListIterator<Permission> grants = Permission.list(query, options);
Permission.delete(9001L, null, options);
```

## Foreign Language Support

The Files.com Java SDK supports localized responses by using the `FilesClient.language` configuration attribute.
When configured, this guides the API in selecting a preferred language for applicable response content.

Language support currently applies to select human-facing fields only, such as notification messages
and error descriptions.

If the specified language is not supported or the value is omitted, the API defaults to English.

```shell title="Example Request"
import com.files.FilesClient;

FilesClient.language = "es";
```

## Errors

The Files.com Java SDK will return errors by raising exceptions. There are many exception classes defined in the Files SDK that correspond
to specific errors.

The raised exceptions come from two categories:

1.  SDK Exceptions - errors that originate within the SDK
2.  API Exceptions - errors that occur due to the response from the Files.com API.  These errors are grouped into common error types.

There are several types of exceptions within each category.  Exception classes indicate different types of errors and are named in a
fashion that describe the general premise of the originating error.  More details can be found in the exception object message using the
`getMessage()` method.

Use standard Java exception handling to detect and deal with errors.  It is generally recommended to catch specific errors first, then
catch the general `SdkException` exception as a catch-all.

```java title="Example Error Handling"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.*;
import java.util.HashMap;

HashMap<String, Object> sessionParameters = new HashMap<>();
sessionParameters.put("username", "USERNAME");
sessionParameters.put("password", "BADPASSWORD");

try {
  Session session = Session.create(sessionParameters);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Error Types

#### SDK Errors

SDK errors are general errors that occur within the SDK code.  These errors generate exceptions.  Each of these
exception classes inherit from a standard `SdkException` base class.

```shell title="Example SDK Exception Class Inheritance Structure"
com.files.exceptions.ApiErrorException.ApiConnectionException ->
com.files.exceptions.SdkException ->
RuntimeException
```
##### SDK Exception Classes

| Exception Class Name| Description |
| --------------- | ------------ |
| `ApiConnectionException`| No reliable response came back from the Files.com API: it could not be reached, or its response had no structured error (such as an HTML error page) or could not be decoded |
| `AuthenticationException`| Authentication information is missing |
| `InvalidParameterException`| A passed in parameter is invalid |
| `InvalidResponseException`| Deprecated and no longer thrown. Such responses raise `ApiConnectionException` |
| `ServerErrorException`| Deprecated and no longer thrown. A 5xx response raises the API exception for its error type, or `ApiConnectionException` |

#### API Errors

API errors are errors returned by the Files.com API.  Each exception class inherits from an error group base class.
The error group base class indicates a particular type of error.

The SDK chooses the exception class from the `type` in the error response, never from the HTTP status code. A
response whose `type` the SDK does not recognize raises `ApiErrorException` itself, which still provides the
response's details through `getError()`, `getHttpCode()`, and `getHeaders()`.

```shell title="Example API Exception Class Inheritance Structure"
com.files.exceptions.ApiErrorException.FolderAdminPermissionRequiredException ->
com.files.exceptions.ApiErrorException.NotAuthorizedException ->
com.files.exceptions.ApiErrorException ->
com.files.exceptions.SdkException ->
RuntimeException
```
##### API Exception Classes

| Exception Class Name | Error Group |
| --------- | --------- |
|`AgentUpgradeRequiredException`|  `BadRequestException` |
|`AttachmentTooLargeException`|  `BadRequestException` |
|`CannotDownloadDirectoryException`|  `BadRequestException` |
|`CantMoveWithMultipleLocationsException`|  `BadRequestException` |
|`DatetimeParseException`|  `BadRequestException` |
|`DestinationSameException`|  `BadRequestException` |
|`DestinationSiteMismatchException`|  `BadRequestException` |
|`DoesNotSupportSortingException`|  `BadRequestException` |
|`FolderMustNotBeAFileException`|  `BadRequestException` |
|`FoldersNotAllowedException`|  `BadRequestException` |
|`InternalGeneralErrorException`|  `BadRequestException` |
|`InvalidBodyException`|  `BadRequestException` |
|`InvalidCursorException`|  `BadRequestException` |
|`InvalidCursorTypeForSortException`|  `BadRequestException` |
|`InvalidEtagsException`|  `BadRequestException` |
|`InvalidFilterAliasCombinationException`|  `BadRequestException` |
|`InvalidFilterFieldException`|  `BadRequestException` |
|`InvalidFilterParamException`|  `BadRequestException` |
|`InvalidFilterParamFormatException`|  `BadRequestException` |
|`InvalidFilterParamValueException`|  `BadRequestException` |
|`InvalidInputEncodingException`|  `BadRequestException` |
|`InvalidInterfaceException`|  `BadRequestException` |
|`InvalidOauthProviderException`|  `BadRequestException` |
|`InvalidPathException`|  `BadRequestException` |
|`InvalidReturnToUrlException`|  `BadRequestException` |
|`InvalidSearchQueryException`|  `BadRequestException` |
|`InvalidSortFieldException`|  `BadRequestException` |
|`InvalidSortFilterCombinationException`|  `BadRequestException` |
|`InvalidUploadOffsetException`|  `BadRequestException` |
|`InvalidUploadPartGapException`|  `BadRequestException` |
|`InvalidUploadPartSizeException`|  `BadRequestException` |
|`InvalidWorkspaceIdHeaderException`|  `BadRequestException` |
|`MethodNotAllowedException`|  `BadRequestException` |
|`MultipleSortParamsNotAllowedException`|  `BadRequestException` |
|`NoValidInputParamsException`|  `BadRequestException` |
|`OffsetUploadNotAllowedWithMalwareScanningException`|  `BadRequestException` |
|`PartNumberTooLargeException`|  `BadRequestException` |
|`PathCannotHaveTrailingWhitespaceException`|  `BadRequestException` |
|`ReauthenticationNeededFieldsException`|  `BadRequestException` |
|`RequestBodyTooLargeException`|  `BadRequestException` |
|`RequestParamsContainInvalidCharacterException`|  `BadRequestException` |
|`RequestParamsInvalidException`|  `BadRequestException` |
|`RequestParamsRequiredException`|  `BadRequestException` |
|`SearchAllOnChildPathException`|  `BadRequestException` |
|`UnrecognizedSortIndexException`|  `BadRequestException` |
|`UnsupportedCurrencyException`|  `BadRequestException` |
|`UnsupportedHttpResponseFormatException`|  `BadRequestException` |
|`UnsupportedMediaTypeException`|  `BadRequestException` |
|`UserIdInvalidException`|  `BadRequestException` |
|`UserIdOnUserEndpointException`|  `BadRequestException` |
|`UserRequiredException`|  `BadRequestException` |
|`AdditionalAuthenticationRequiredException`|  `NotAuthenticatedException` |
|`ApiKeySessionsNotSupportedException`|  `NotAuthenticatedException` |
|`AuthenticationRequiredException`|  `NotAuthenticatedException` |
|`BundleRegistrationCodeFailedException`|  `NotAuthenticatedException` |
|`InboxRegistrationCodeFailedException`|  `NotAuthenticatedException` |
|`InvalidCredentialsException`|  `NotAuthenticatedException` |
|`InvalidOauthException`|  `NotAuthenticatedException` |
|`InvalidOrExpiredCodeException`|  `NotAuthenticatedException` |
|`InvalidSessionException`|  `NotAuthenticatedException` |
|`InvalidUsernameOrPasswordException`|  `NotAuthenticatedException` |
|`LockedOutException`|  `NotAuthenticatedException` |
|`LockoutRegionMismatchException`|  `NotAuthenticatedException` |
|`OneTimePasswordIncorrectException`|  `NotAuthenticatedException` |
|`TwoFactorAuthenticationErrorException`|  `NotAuthenticatedException` |
|`TwoFactorAuthenticationSetupExpiredException`|  `NotAuthenticatedException` |
|`ApiKeyIsDisabledException`|  `NotAuthorizedException` |
|`ApiKeyIsPathRestrictedException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForDesktopAppException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForFileOperationsException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForMobileAppException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForOfficeIntegrationException`|  `NotAuthorizedException` |
|`BillingInformationHiddenException`|  `NotAuthorizedException` |
|`BillingPermissionRequiredException`|  `NotAuthorizedException` |
|`BundleMaximumUsesReachedException`|  `NotAuthorizedException` |
|`BundlePermissionRequiredException`|  `NotAuthorizedException` |
|`CannotAdministerHigherLevelUserException`|  `NotAuthorizedException` |
|`CannotLoginWhileUsingKeyException`|  `NotAuthorizedException` |
|`CantActForOtherUserException`|  `NotAuthorizedException` |
|`ContactAdminForPasswordChangeHelpException`|  `NotAuthorizedException` |
|`FilesAgentFailedAuthorizationException`|  `NotAuthorizedException` |
|`FolderAdminOrBillingPermissionRequiredException`|  `NotAuthorizedException` |
|`FolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`FullPermissionRequiredException`|  `NotAuthorizedException` |
|`HistoryPermissionRequiredException`|  `NotAuthorizedException` |
|`InAppAiAssistantUnavailableException`|  `NotAuthorizedException` |
|`InsufficientPermissionForParamsException`|  `NotAuthorizedException` |
|`InsufficientPermissionForSiteException`|  `NotAuthorizedException` |
|`MoverAccessDeniedException`|  `NotAuthorizedException` |
|`MoverPackageRequiredException`|  `NotAuthorizedException` |
|`MustAuthenticateWithApiKeyException`|  `NotAuthorizedException` |
|`NeedAdminPermissionForInboxException`|  `NotAuthorizedException` |
|`NonAdminsMustQueryByFolderOrPathException`|  `NotAuthorizedException` |
|`NotAllowedToCreateBundleException`|  `NotAuthorizedException` |
|`NotEnqueuableSyncException`|  `NotAuthorizedException` |
|`PasswordChangeNotRequiredException`|  `NotAuthorizedException` |
|`PasswordChangeRequiredException`|  `NotAuthorizedException` |
|`PaymentMethodErrorException`|  `NotAuthorizedException` |
|`PreviewOnlyPermissionCannotDownloadException`|  `NotAuthorizedException` |
|`ReadOnlySessionException`|  `NotAuthorizedException` |
|`ReadPermissionRequiredException`|  `NotAuthorizedException` |
|`ReauthenticationFailedException`|  `NotAuthorizedException` |
|`ReauthenticationFailedFinalException`|  `NotAuthorizedException` |
|`ReauthenticationNeededActionException`|  `NotAuthorizedException` |
|`RecaptchaFailedException`|  `NotAuthorizedException` |
|`RemoteDesktopDebugLoggingDisabledException`|  `NotAuthorizedException` |
|`RootFolderBehaviorSiteAdminRequiredException`|  `NotAuthorizedException` |
|`RootFolderBehaviorSkipSiteAdminRequiredException`|  `NotAuthorizedException` |
|`SelfManagedRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrPartnerAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminOrFolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminOrPartnerAdminOrFolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminOrPartnerAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminRequiredException`|  `NotAuthorizedException` |
|`SiteFilesAreImmutableException`|  `NotAuthorizedException` |
|`TwoFactorAuthenticationRequiredException`|  `NotAuthorizedException` |
|`UserIdWithoutSiteAdminException`|  `NotAuthorizedException` |
|`WriteAndBundlePermissionRequiredException`|  `NotAuthorizedException` |
|`WritePermissionRequiredException`|  `NotAuthorizedException` |
|`ApiKeyNotFoundException`|  `NotFoundException` |
|`BundlePathNotFoundException`|  `NotFoundException` |
|`BundleRegistrationNotFoundException`|  `NotFoundException` |
|`CodeNotFoundException`|  `NotFoundException` |
|`FileNotFoundException`|  `NotFoundException` |
|`FileUploadNotFoundException`|  `NotFoundException` |
|`GroupNotFoundException`|  `NotFoundException` |
|`InboxNotFoundException`|  `NotFoundException` |
|`NestedNotFoundException`|  `NotFoundException` |
|`PlanNotFoundException`|  `NotFoundException` |
|`SiteNotFoundException`|  `NotFoundException` |
|`UserNotFoundException`|  `NotFoundException` |
|`AgentPushUpdateBlockedException`|  `ProcessingFailureException` |
|`AgentUnavailableException`|  `ProcessingFailureException` |
|`AiTaskCannotBeRunManuallyException`|  `ProcessingFailureException` |
|`AlreadyCompletedException`|  `ProcessingFailureException` |
|`AutomationCannotBeRunManuallyException`|  `ProcessingFailureException` |
|`BehaviorNotAllowedOnRemoteServerException`|  `ProcessingFailureException` |
|`BufferedUploadDisabledForThisDestinationException`|  `ProcessingFailureException` |
|`BundleOnlyAllowsPreviewsException`|  `ProcessingFailureException` |
|`BundleOperationRequiresSubfolderException`|  `ProcessingFailureException` |
|`ConfigurationLockedPathException`|  `ProcessingFailureException` |
|`CouldNotCreateParentException`|  `ProcessingFailureException` |
|`DestinationExistsException`|  `ProcessingFailureException` |
|`DestinationFolderLimitedException`|  `ProcessingFailureException` |
|`DestinationParentConflictException`|  `ProcessingFailureException` |
|`DestinationParentDoesNotExistException`|  `ProcessingFailureException` |
|`ExceededRuntimeLimitException`|  `ProcessingFailureException` |
|`ExpectationAlreadyHasOpenWindowException`|  `ProcessingFailureException` |
|`ExpectationNotManualTriggerException`|  `ProcessingFailureException` |
|`ExpiredPrivateKeyException`|  `ProcessingFailureException` |
|`ExpiredPublicKeyException`|  `ProcessingFailureException` |
|`ExportFailureException`|  `ProcessingFailureException` |
|`ExportNotReadyException`|  `ProcessingFailureException` |
|`FailedToChangePasswordException`|  `ProcessingFailureException` |
|`FileLockedException`|  `ProcessingFailureException` |
|`FileNotUploadedException`|  `ProcessingFailureException` |
|`FilePendingProcessingException`|  `ProcessingFailureException` |
|`FileProcessingErrorException`|  `ProcessingFailureException` |
|`FileTooBigToDecryptException`|  `ProcessingFailureException` |
|`FileTooBigToEncryptException`|  `ProcessingFailureException` |
|`FileUploadedToWrongRegionException`|  `ProcessingFailureException` |
|`FilenameTooLongException`|  `ProcessingFailureException` |
|`FolderLockedException`|  `ProcessingFailureException` |
|`FolderNotEmptyException`|  `ProcessingFailureException` |
|`HistoryUnavailableException`|  `ProcessingFailureException` |
|`InvalidBundleCodeException`|  `ProcessingFailureException` |
|`InvalidFileTypeException`|  `ProcessingFailureException` |
|`InvalidFilenameException`|  `ProcessingFailureException` |
|`InvalidPriorityColorException`|  `ProcessingFailureException` |
|`InvalidRangeException`|  `ProcessingFailureException` |
|`InvalidSiteException`|  `ProcessingFailureException` |
|`InvalidZipFileException`|  `ProcessingFailureException` |
|`MetadataNotSupportedOnRemotesException`|  `ProcessingFailureException` |
|`ModelSaveErrorException`|  `ProcessingFailureException` |
|`MultipleProcessingErrorsException`|  `ProcessingFailureException` |
|`PathTooLongException`|  `ProcessingFailureException` |
|`RecipientAlreadySharedException`|  `ProcessingFailureException` |
|`RemoteEntryReadOnlyException`|  `ProcessingFailureException` |
|`RemoteServerErrorException`|  `ProcessingFailureException` |
|`ResourceBelongsToParentSiteException`|  `ProcessingFailureException` |
|`ResourceLockedException`|  `ProcessingFailureException` |
|`SubfolderLockedException`|  `ProcessingFailureException` |
|`SyncInProgressException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationCodeAlreadySentException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationCountryBlacklistedException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationGeneralErrorException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationMethodUnsupportedErrorException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationUnsubscribedRecipientException`|  `ProcessingFailureException` |
|`UpdatesNotAllowedForRemotesException`|  `ProcessingFailureException` |
|`DuplicateShareRecipientException`|  `RateLimitedException` |
|`ReauthenticationRateLimitedException`|  `RateLimitedException` |
|`TooManyConcurrentLoginsException`|  `RateLimitedException` |
|`TooManyConcurrentRequestsException`|  `RateLimitedException` |
|`TooManyLoginAttemptsException`|  `RateLimitedException` |
|`TooManyRequestsException`|  `RateLimitedException` |
|`TooManySharesException`|  `RateLimitedException` |
|`AutomationsUnavailableException`|  `ServiceUnavailableException` |
|`LockOperationBusyException`|  `ServiceUnavailableException` |
|`MigrationInProgressException`|  `ServiceUnavailableException` |
|`SearchUnavailableException`|  `ServiceUnavailableException` |
|`SiteDisabledException`|  `ServiceUnavailableException` |
|`UploadsUnavailableException`|  `ServiceUnavailableException` |
|`AccountAlreadyExistsException`|  `SiteConfigurationException` |
|`AccountOverdueException`|  `SiteConfigurationException` |
|`NoAccountForSiteException`|  `SiteConfigurationException` |
|`SiteWasRemovedException`|  `SiteConfigurationException` |
|`TrialExpiredException`|  `SiteConfigurationException` |
|`TrialLockedException`|  `SiteConfigurationException` |
|`UserRequestsEnabledRequiredException`|  `SiteConfigurationException` |

## Pagination

Certain API operations return lists of objects. When the number of objects in the list is large,
the API will paginate the results.

The Files.com Java SDK provides multiple ways to paginate through lists of objects.

### Automatic Pagination

The `listAutoPaging` function iterates every item, requesting each page only when the loop reaches it, so only
one page is held in memory at a time. Breaking out of the loop stops further requests.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;

try {
  for (File item : Folder.listFor(path, null).listAutoPaging()) {
    System.out.println(item.path);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Manual Pagination

The `loadNextPage/hasNextPage` functions allow for manual pagination and loading of each page into memory.
Iterating the listing itself reads only the page loaded most recently.

```java title="Example Request"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;

try {
  ListIterator<File> listing = Folder.listFor(path, null);
  do {
    for (File item : listing.loadNextPage()) {
      System.out.println(item.path);
    }
  } while (listing.hasNextPage());
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Load All Items

The `all` function starts from the first page and loads all items into memory.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;
import java.util.List;

try {
  List<File> items = Folder.listFor(path, null).all();
  for (File item : items) {
    System.out.println(item.path);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Start Over

The `reset` function returns the same listing, ready to request its first page again with the original filters and
options. Without it, `listAutoPaging` continues after the page loaded most recently.

```java title="Example Request"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;

try {
  ListIterator<File> listing = Folder.listFor(path, null);
  for (File item : listing.loadNextPage()) {
    System.out.println(item.path);
  }

  for (File item : listing.reset().listAutoPaging()) {
    System.out.println(item.path);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

## File Attachments

Some API parameters take a file: a user's `avatar_file`, a site's `logo_file` and icons, a
behavior's `attachment_file`, a bundle's `watermark_attachment_file` and a style's `file`. These
parameters accept a `byte[]` or a `com.files.net.Attachment`, which also sends a local file or a
stream and sets the file's name and media type.

Attachments configure objects. To transfer files to and from your Files.com site, use `File`
and `Folder`.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import com.files.net.Attachment;
import java.nio.file.Paths;
import java.util.HashMap;

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("avatar_file", Attachment.fromPath(Paths.get("avatar.png")).withContentType("image/png"));

try {
  User user = User.update(userId, parameters);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Local Files

`Attachment.fromPath` reads the file when the request is sent, without loading it into memory.
The filename sent is the file's name, without its directories. Before sending, the SDK reads the
file once to record its size and SHA-256 digest. Each attempt, including a retry, is checked
against them; a changed file fails the request with an `UncheckedIOException` instead of sending
different content.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.Site;
import com.files.net.Attachment;
import java.nio.file.Paths;
import java.util.HashMap;

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("logo_file", Attachment.fromPath(Paths.get("brand", "logo.png")).withContentType("image/png"));
parameters.put("icon16_file", Attachment.fromPath(Paths.get("brand", "icon16.png")).withContentType("image/png"));

try {
  Site site = Site.update(parameters);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Bytes

A `byte[]` is sent as the file's content, as is `Attachment.fromBytes`, which also lets you
name the file. The SDK sends a copy of the array as it was when you made the call. Unless you
set them, the filename is the parameter's name, such as `avatar_file`, and the media type is
`application/octet-stream`. The API's image checks may need the right name and type.

Models keep their `byte[]` setters: `save()` sends the bytes as the file.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import com.files.net.Attachment;
import java.util.HashMap;

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("avatar_file", Attachment.fromBytes(pngBytes).withFilename("avatar.png").withContentType("image/png"));

try {
  User.update(userId, parameters);

  User user = User.find(userId, null);
  user.setAvatarFile(pngBytes);
  user.save();
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Streams

`Attachment.fromInputStream` sends a stream from its current position to its end. The SDK reads
the stream before it sends the request, so that a retry sends the same bytes. It keeps up to
1 MiB of the stream's content in memory and the rest in a temporary file that only your user
can read, and deletes that copy when the request ends. A stream of more than 64 MiB fails with
an `IllegalArgumentException` before anything is sent. `withSpoolLimit` and
`withMemoryThreshold` change these limits. Each attachment a request sends from a stream can
use up to its own limit.

The SDK does not close the stream; close it yourself, for example with try-with-resources.
The SDK cannot interrupt a stream that blocks while it is being read.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.Bundle;
import com.files.net.Attachment;
import java.io.InputStream;
import java.util.HashMap;

try {
  try (InputStream watermark = openWatermark()) {
    HashMap<String, Object> parameters = new HashMap<>();
    parameters.put("watermark_attachment_file", Attachment.fromInputStream(watermark)
        .withFilename("watermark.png")
        .withContentType("image/png")
        .withSpoolLimit(128L * 1024 * 1024));
    Bundle.update(bundleId, parameters);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### How the Request Is Sent

The SDK sends every parameter of a call in one request. It uses `multipart/form-data`, the
format of a browser upload, when form fields carry every other parameter exactly. Otherwise it
sends JSON, with each file as the API's file object `{"encoded_content": ..., "filename": ...,
"type": ...}`, whose content is Base64 and about a third larger. JSON is used for:

* a `null`, an empty list, an empty object or an empty key in an object;
* a number or boolean inside an object, such as a watermark's `transparency`;
* a list of objects;
* a filename containing `%`, `/` or `\`, which the API's form parser would change.

The request declares its exact length either way.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.Bundle;
import com.files.net.Attachment;
import java.nio.file.Paths;
import java.util.HashMap;

HashMap<String, Object> watermark = new HashMap<>();
watermark.put("transparency", 25);
watermark.put("max_height_or_width", 200);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("watermark_value", watermark);
parameters.put("watermark_attachment_file", Attachment.fromPath(Paths.get("watermark.png")).withContentType("image/png"));

try {
  Bundle.update(bundleId, parameters); // Sent as JSON, keeping 25 and 200 numbers
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Retries and Errors

The SDK retries a request as its client allows (see Clients and Connections), and each attempt
sends the same bytes. If the response to an update or create is lost, the API may apply it more
than once. Cancelling a call while its attachments are still being read or copied stops it before
anything is sent, and deletes the copies made so far.

* `IllegalArgumentException`: a parameter has the wrong type, a stream is longer than its spool
  limit, or a path is not a regular file.
* `UncheckedIOException`: a file or stream could not be read or copied, or a file changed
  between attempts. Its cause is the underlying `IOException`.
* API errors, such as an invalid image, raise the usual `ApiErrorException` types.

At DEBUG, the SDK's request log describes each attachment without its content. Apache
HttpClient's `org.apache.http.wire` log, at DEBUG, includes request bodies and so the content.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import com.files.net.Attachment;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.HashMap;

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("avatar_file", Attachment.fromPath(Paths.get("avatar.png")).withContentType("image/png"));

try {
  User.update(userId, parameters);
} catch (UncheckedIOException e) {
  System.out.println("Could not send the file: " + e.getMessage() + ": " + e.getCause().getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

## File Transfers

`client.transfers()` uploads local files and streams, and downloads files to local paths. Each
transfer runs with its client's settings as they were when it started, and returns when it is
complete.

An upload is sent in parts, sized by the SDK where Files.com's instructions for the upload let
it, and as Files.com plans them everywhere else (see Part Sizes). When Files.com allows it,
several parts are sent at the same time, as many as the upload measures to help (see
Concurrency); otherwise they are sent one at a time, in order. The parts go to the storage URLs
Files.com provides, with the method and headers it gives, and carry no Files.com credentials.
Once every part has been accepted, one final request completes the upload.

```java title="Example Request"
import com.files.FilesClient;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import java.nio.file.Paths;

try {
  UploadResult result = client.transfers().upload(Paths.get("report.pdf"), "reports/2026/report.pdf",
      UploadOptions.builder().mkdirParents(true).build());
  System.out.println("Uploaded " + result.getSize() + " bytes in " + result.getParts().size() + " parts");
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Files and Streams

A local file is opened once, and every part is read from it as it is sent, so its contents are
never held in memory. Each part is also read once before it is first sent, to record a digest of
its bytes: an attempt whose bytes no longer match, because the file changed in place, even with
the same size and modification time, is stopped before it is complete, and the upload fails. A part
is only ever accepted with the bytes it had when it was first read. The length uploaded is the
file's length when the upload starts, and its modification time is recorded unless you set
`modifiedTime`. If the file's length or modification time changes during the upload, the upload is
not completed either.

A stream is read forward, from its current position, one part at a time. Each part is kept in
memory until the storage server accepts it, so a retry sends exactly the same bytes. Give the
number of bytes to upload, and the upload fails with `EOFException` if the stream ends first. Or
leave the length out, and the stream is uploaded until it ends: it may hold at most
`maxStreamLength` bytes (5 TiB by default), and one that holds more fails before its upload is
completed, without sending a byte past that bound. The stream is never closed, reset or skipped.

```java title="Example Request"
import com.files.net.UploadOptions;
import java.io.InputStream;

try (InputStream stream = openExport()) {
  client.transfers().upload(stream, exportLength, "exports/latest.csv", UploadOptions.defaults());
}

try (InputStream stream = openPipe()) {
  client.transfers().upload(stream, "exports/piped.csv",
      UploadOptions.builder().maxStreamLength(64L * 1024 * 1024 * 1024).build());
}
```

### Part Sizes

Files.com plans every upload's parts. The SDK chooses their sizes only where Files.com's
instructions let it: an ordinary multipart upload to S3, to Files.com's own storage or a bucket of
yours, whose signed URLs fix neither a part's length nor its bytes. Anywhere else, such as a
Files Agent, or where the URLs require a length or checksum, the upload sends the parts
Files.com plans. Where the SDK chooses, every part but the last holds at least 5 MiB and at most
1 GiB, and the upload has at most 10,000 parts, or fewer if Files.com allows fewer. If Files.com
later gives a part's URL for another S3 upload, or one that fixes its length, the upload stops
before sending that part.

`PartSizing.automatic()`, the default, starts at 8 MiB for files up to 256 MiB, 16 MiB up to
4 GiB, 32 MiB up to 64 GiB and 64 MiB above that, smaller so that a file has at least 16 parts and
larger where 10,000 parts would not hold it. A stream of unknown length starts at 8 MiB, with parts
made larger as needed for 10,000 of them to hold `maxStreamLength`. Since a stream's parts are held
in memory, where its limiter's `getMaxBufferedBytes()` cannot hold parts that large, the stream
gets the parts Files.com plans instead: give a lower `maxStreamLength` for the SDK to size them.
`PartSizing.serverPlanned()` always sends the parts Files.com plans, and `PartSizing.fixed(size)`
sends parts of one size, failing before anything is sent where the SDK may not choose sizes or the
size breaks the limits above. `UploadResult.getPartSizeSource()` says which sizes an upload used.

```java title="Example Request"
import com.files.net.PartSizing;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import java.nio.file.Paths;

UploadResult result = client.transfers().upload(Paths.get("video.mov"), "videos/video.mov",
    UploadOptions.builder().partSizing(PartSizing.fixed(64L * 1024 * 1024)).build());
System.out.println(result.getParts().size() + " parts, sized " + result.getPartSizeSource());
```

### Default vs. Aggressive Startup

For automatic uploads, choose how quickly the SDK starts sending parts in parallel. Use default
startup when you have not measured your network's capacity. Choose aggressive startup for a
known fast connection, including uploads from a cloud VM.

Default startup begins an S3 upload with one part at a time and gradually increases concurrency
as measurements show an improvement. Use `UploadOptions.defaults()` or leave `startup` unset
in `UploadOptions.builder()`.

Aggressive startup begins with a target of 150 concurrent parts for S3 uploads. Set
`startup(UploadStartup.AGGRESSIVE)` to use it. The actual number of parts in flight is limited
by `maxConcurrency`, the transfer limiter, the connection pool and the number of available parts.

Both modes adjust concurrency using the same measurements after startup and can reach the
same throughput. Aggressive startup avoids the gradual increase at the beginning of the upload.
These startup options apply to uploads. Downloads use the behavior described in [Downloads](#downloads).

Choose one of the following examples with your configured `client`.

```java title="Default Startup"
import com.files.net.UploadOptions;
import java.nio.file.Paths;

client.transfers().upload(Paths.get("backup.tar"), "backups/backup.tar",
    UploadOptions.defaults());
```

```java title="Aggressive Startup"
import com.files.net.UploadOptions;
import com.files.net.UploadStartup;
import java.nio.file.Paths;

client.transfers().upload(Paths.get("backup.tar"), "backups/backup.tar",
    UploadOptions.builder().startup(UploadStartup.AGGRESSIVE).build());
```

### Concurrency

By default an upload adapts how many parts it sends at once to what its parts deliver, following
the policy for where they go, as Files.com's answers show it. Parts to S3, Files.com's own storage
or a bucket of yours, follow the scaled S3 policy: up to 150 parts at once, and up to 1024 once
64 GiB have been delivered at 96 MiB/s, growing by 4 parts when a window of at least 32 answers
shows the gain, and lowered by 35% after a temporary failure, by 10% when the storage server
throttles, and by 8% when requests queue while throughput falls. Parts to a Files Agent start at 2
at once and go up to 8. Files.com's answers do not yet identify a remote server integration, so
its uploads follow the S3 policy.

With default startup, an S3 upload increases concurrency by half when a measurement window
shows enough improvement without queueing, or by one after reducing concurrency. A window
that delivers 96 MiB/s increases concurrency to the policy's starting target.

Give the transfers of one job the same `TransferScope` (`scope(job)` in their options) to have
them share request limits and concurrency measurements. Compatible automatic uploads share one
concurrency target, and compatible automatic downloads share another. The SDK adjusts each target
using throughput, temporary failures and throttling responses from upload parts and download
attempts for whole files or bound ranges.
An upload to S3 warms up once for the job. The scope also shares one limiter for requests for file
contents and their buffered bytes, unless the options specify a different limiter. Upload
planning and finalization API calls do not use that limiter.

Run the job's blocking transfer calls on a bounded pool of threads that your application owns.
The number of calls you run at once limits how many files the SDK transfers at once, even when
the shared target allows more part requests. For downloads, submit each file for download as you
receive its listing page so transfers can begin while the rest of the folder is being listed.
Limit the number of queued transfer calls as well. Wait for every call to finish before closing
the scope. Concurrency targets and measurements are not retained between scopes.

For automatic uploads in a scope, default startup also controls how many files begin. The SDK
starts a few uploads and gradually starts more as the shared concurrency target increases or
existing uploads provide too few parts to use it. This avoids
starting every file's API requests at once. Aggressive startup admits the supplied files
immediately. Default and aggressive startup can use the same number of callers and reach the same throughput.
Default startup takes longer to increase concurrency. Requests for file contents still
follow the limiter, shared target and connection pool limits either way.

`maxConcurrency` caps how many parts go at once, as do the limiter's request slots and the
transport's connections to the storage host. `concurrencyMode(ConcurrencyMode.STATIC)` sends
`maxConcurrency` parts at once (4 if it is not set) instead of adapting. Each progress report's
`getConcurrency()` says what the upload does and why: its target, the parts in flight, its last
decision, and where its time went.

```java title="Example Request"
import com.files.net.TransferConcurrency;
import com.files.net.UploadOptions;
import com.files.net.UploadResult;
import java.nio.file.Paths;

UploadResult result = client.transfers().upload(Paths.get("backup.tar"), "backups/backup.tar",
    UploadOptions.defaults());
TransferConcurrency concurrency = result.getFinalProgress().getConcurrency();
System.out.println(concurrency.getPeakActiveAttempts() + " parts at once at most, last " + concurrency.getLastDecision());
```

### Retries and Limits

A part that fails with a connection failure, a request timeout (408), throttling (429) or a
server error (5xx other than 501) is sent again with the same bytes, when Files.com allows
retrying parts, up to the client's `maxRetries`. It waits the client's retry delay first; after
429 or 503 it waits the storage server's whole Retry-After instead, or not at all without one;
a part whose Retry-After asks for longer than the SDK can wait, some 24 days, is not sent again.
An automatic upload also sends no new part until the Retry-After has passed. While parts are sent
in parallel, a part waiting to be sent again lets other parts be sent meanwhile. A part whose URL is about to expire gets a new one for the same part of the
same upload. Parts already accepted are never sent again.

Transfers that share a client's connection pool send no more parts at once than the pool can
lease connections for: to one storage host, at most its limit for that host
(`HttpTransport.Builder.maxConnectionsPerRoute`, 1024 by default), and to all storage hosts
together, at most its total (`maxConnections`, 2048 by default). For a pool you supply, its limit
for the host's route, including one you set for that route, and its total are read whenever a part
waits, and never changed. A part waits its turn rather than failing for want of a connection.
Requests to the Files.com API, and anything else using the pool, are not counted: while they hold
connections, a part, or they, can still wait for one, up to the client's
`connectionAcquisitionTimeout`.

A `TransferLimiter` limits how many part requests transfers send at once and how many bytes of
file contents they hold in memory. Give several uploads the same limiter, even uploads of
different clients, and they take turns within one budget without sharing credentials. A part of
a stream larger than its limiter's `getMaxBufferedBytes()` fails the upload before it is read.

```java title="Example Request"
import com.files.net.TransferLimiter;
import com.files.net.UploadOptions;
import java.nio.file.Path;

TransferLimiter limiter = new TransferLimiter(8, 256L * 1024 * 1024);
UploadOptions options = UploadOptions.builder()
    .maxConcurrency(4)
    .limiter(limiter)
    .progressListener(progress -> System.out.println(progress.getTransferredBytes() + " bytes"))
    .build();

for (Path file : files) {
  client.transfers().upload(file, "incoming/" + file.getFileName(), options);
}
```

### Completion

The final request is never repeated once it may have reached Files.com: Files.com forgets an
upload once it completes it, so a repeat could not tell a completed upload from a missing one.
Only an attempt that could not be sent, such as one that could not connect, is retried. If the
request fails in a way that does not show whether Files.com completed the file, such as a lost
connection, the upload throws `UploadCompletionUnknownException`. Check the file at its
`getPath()` before uploading it again. A refusal from Files.com is thrown as its API error, and a
request that never reached Files.com as its connection error: then the upload was not
completed.

```java title="Example Request"
import com.files.exceptions.UploadCompletionUnknownException;
import com.files.models.File;
import java.nio.file.Paths;

try {
  client.transfers().upload(Paths.get("big.iso"), "images/big.iso");
} catch (UploadCompletionUnknownException e) {
  File file = client.files().find(e.getPath(), null);
  System.out.println("Uploaded " + file.getSize() + " of " + e.getSize() + " bytes");
}
```

### Checkpoints

Give an upload of a local file a `checkpointDirectory`, and it keeps a record there that a later
run of the same upload continues from. The record holds Files.com's reference for the upload and
each part's number, offset, length and, once accepted, ETag. It holds no credentials, URLs or
queries. A later run sends nothing from it except the reference, and sends that only where its
own call goes. Parts recorded as accepted are not sent again, and are reported by
`TransferProgress.getResumedBytes()`. A part recorded as planned may already have been sent, so
it is sent again with exactly the same bytes, and only where Files.com's answer to the later run
allows retrying parts (`retry_parts`). Files.com must still recognize the upload, and, where the
SDK chose the part sizes, give the same S3 upload, before any part is sent. Otherwise the run
throws `TransferCheckpointException` with reason `UPLOAD_CHANGED`, and you must discard the
checkpoint and upload the file again from the start. The record is deleted once Files.com
completes or refuses the upload.

The record is bound to the API root and workspace of the call, the remote path, the local file's
real path, and the file's identity, length and modification time. It keeps only a SHA-256 of the
API root, with its scheme, host and port, so no user information, query or fragment in a
configured API root is written. If the file has changed, the upload starts over. A run with
different part sizing is refused (`OTHER_TRANSFER`), and so is a second run of the same upload
while one holds the record (`IN_USE`). A record in a format this version of the SDK does not read
is refused (`UNREADABLE`); discard it to start over.

Before the final request is sent, the record notes that it is about to be sent, and that note is
made durable first. If it cannot be made durable, the request is not sent (`NOT_DURABLE`). A later
run that finds the note never sends the request again. Neither does a run that finds Files.com no
longer recognizes the upload. Both throw `UploadCompletionUnknownException` without sending
anything. Check the file, then call `discardUploadCheckpoint` before uploading it again.

The directory must be yours and private to you. On Linux and macOS its permissions must be
exactly `rwx------`, with no ACL on macOS. On Windows its protected ACL must grant only you full
control. A directory that does not exist yet is created that way. One that exists but is not
private, or that is a link, is refused (`UNTRUSTED`) before anything in it is read. Stream uploads
and `continueUpload` refuse options that set a checkpoint directory. The Windows store has not
been run natively in the SDK's own tests. Windows cannot make a new record's directory entry
durable, so after a crash there the upload may start over.

```java title="Example Request"
import com.files.exceptions.UploadCompletionUnknownException;
import com.files.net.UploadOptions;
import java.nio.file.Path;
import java.nio.file.Paths;

Path source = Paths.get("big.iso");
UploadOptions options = UploadOptions.builder()
    .checkpointDirectory(Paths.get(System.getProperty("user.home"), ".files-checkpoints"))
    .build();
try {
  // Run again after a failure or a crash to continue where it stopped.
  client.transfers().upload(source, "images/big.iso", options);
} catch (UploadCompletionUnknownException e) {
  // Check the file at e.getPath(), then start over if it is incomplete.
  client.transfers().discardUploadCheckpoint(source, "images/big.iso", options);
}
```

### Cancellation

Cancel an upload's `cancellation` token, set its `timeout`, interrupt the thread that called the
upload, or close its client, and the upload stops wherever it is: waiting for the limiter,
reading, sending, waiting for an answer or waiting to retry. Its requests are aborted, and once
every part in flight has stopped and the upload's own threads have ended, it throws
`OperationCancelledException`. Nothing more is sent, so the upload is not completed, unless its
final request had already been sent, which `UploadCompletionUnknownException` reports.

A read of your own stream that blocks cannot be interrupted in Java 8; the upload stops when it
returns. The progress listener runs on the thread that called the upload.

```java title="Example Request"
import com.files.CancellationToken;
import com.files.exceptions.OperationCancelledException;
import com.files.net.UploadOptions;
import java.nio.file.Paths;
import java.time.Duration;

CancellationToken token = new CancellationToken();
UploadOptions options = UploadOptions.builder()
    .cancellation(token)
    .timeout(Duration.ofHours(2))
    .build();
try {
  client.transfers().upload(Paths.get("archive.zip"), "archive.zip", options);
} catch (OperationCancelledException e) {
  System.out.println("Stopped: " + e.getReason());
}
```

### Downloads

`download` writes a remote file to a local path. The contents go into a new file in a directory of
the download's own, created beside the destination and readable only by you while it holds
unchecked bytes. The SDK replaces the destination only after every byte has been received, checked
and flushed to storage, and after confirming that the file is still the one the download created. A file put in
its place meanwhile is never published, written or deleted. This flush makes the file's contents
durable. It does not guarantee that the destination's directory entry survives a power loss.

A file larger than one part (`partSize`, 8 MiB by default) is first asked for its first part alone.
Where the storage server's answer binds the download to one version of the file, with the file's
size and a strong entity tag, the rest comes in parts, many at once, as for uploads (`concurrencyMode`
and `maxConcurrency`; an automatic download from S3 starts at 150 parts at once, within its caps),
each written at its own place in the file. Every part is asked for with that tag in `If-Match`, and
must come back as exactly its bytes of exactly that version; otherwise the file changed while it
was being downloaded, and the download fails. A part cut short is asked for again from its first
byte not yet written. Where the answer binds nothing, the file comes in one response.
`getFinalProgress()` and `isVersionBound()` on the result say how it went.

Every request for file contents uses the limiter, including a whole-file request, the first
request that binds a version, and a checkpoint's version check. Its copy buffer counts against
the limiter's memory budget. Whole-file requests count against the scope's shared automatic
target, and the SDK uses their results to adjust the target for all compatible downloads. The first version-binding request and a
checkpoint's version check run outside that adaptive target, within the limiter.

A response that is shorter than it said, that is not the file's size, or that comes from the
Files.com web application fails the download: what it created is deleted and the destination, and
every other file, is left as it was. A failed attempt that may be temporary is tried again, up to
the client's `maxRetries`, and the download's `timeout` covers all of it, describing the file
included.

A downloaded file keeps the permissions and group of the file it replaces; a new file gets the
permissions new files in that directory normally get. On file systems with ACLs rather than POSIX
permissions, such as Windows, a replaced file's ACL is kept and a new file keeps the owner-only
access it was staged with. A destination you could not write, or that belongs to another user, is
not replaced.

On macOS and Windows, the download creates and holds its directory and file through the operating
system, with JNA (`net.java.dev.jna:jna`, which the SDK depends on): Java's own file API can neither
create a macOS directory that inherits no ACL entries nor create a Windows file or directory whose
ACL inherits nothing. On macOS, a replaced file's ACL is kept too, and a new file gets the ACL
entries that new files in its directory inherit. Where JNA cannot be loaded, a download throws an
`IOException` before it creates anything; the SDK's other calls are unaffected. On macOS and
Windows, a download to a volume that does not enforce file ownership or keep ACLs, such as a FAT
drive or a macOS volume set to ignore ownership, also throws an `IOException`, since nothing on it
can be kept private. On Java 24 and later, JNA's native access prints a warning unless your
application allows it: with `--enable-native-access=ALL-UNNAMED` when JNA is on the class path,
`--enable-native-access=com.sun.jna` when it is on the module path, or the manifest attribute
`Enable-Native-Access: ALL-UNNAMED` in an executable JAR.

On Windows, JNA writes its native library to a temporary directory and loads it from there, so
anyone who could change that directory could run code as your application. If you set that
directory with `jna.tmpdir`, or turn the writing off with `jna.nounpack` or `jna.noclasspath`, JNA
follows your settings. Otherwise the SDK first checks the directory JNA would write to, and lets
JNA write its library only to one that only your application's account and administrators can change:
`java.io.tmpdir` if it is such a directory, or else the account's own `AppData\Local\Temp` if that
one is. The SDK sets `jna.tmpdir` to the real path of the directory it checked, for the rest of the
JVM's life. `jna.boot.library.path` and `jna.nosys=false` are kept as you set them, but the
directory is checked anyway, since JNA writes its library there when it does not find it where they
point. A library found through either setting is loaded as supplied, without being checked by the
SDK. Where neither directory qualifies, as can happen when `java.io.tmpdir` is a shared directory
such as `C:\Windows\Temp`, downloads throw an `IOException`: set `jna.tmpdir` to a directory only
that account and administrators can change, or, if `jna.boot.library.path` supplies JNA's library,
set `jna.nounpack=true`.

`File.saveAsLocalFile` downloads the same way, with the client that returned the `File`.

```java title="Example Request"
import com.files.net.DownloadOptions;
import java.nio.file.Paths;

client.transfers().download("reports/2026/report.pdf", Paths.get("report.pdf"));

client.transfers().download("reports/2026/report.pdf", Paths.get("copy.pdf"),
    DownloadOptions.builder().replaceExisting(false).build());
```

### Download Checkpoints

Give a download a `checkpointDirectory` (the same kind of private directory as an upload's), and a
download fetched in parts keeps its partial file when it stops before it is complete. A later
download of the same remote file to the same destination, with the same options, continues it. The
record holds the partial file's directory and the identities of that directory and its file. It
also holds the file's version (its strong entity tag, its size and a SHA-256 of its storage
resource) and the ranges the partial file holds. It holds no credentials, URLs or queries, and
binds the API root only as a SHA-256. Each later run describes the file again for a fresh download
URL.

A later run reopens the partial file only if it is still the one the download created, by its
recorded identities, and still private to you. Anything else at its name is refused
(`TransferCheckpointException` with reason `STAGE_CHANGED`) and left as it is. If it is gone, the
download starts over.

The version is confirmed before anything is written. The first missing range, or the last byte, is
asked for with `If-Match`, and must come back as exactly those bytes of exactly that version. If the
file changed, the checkpoint forgets the old version before the partial file is emptied, and the
download starts over, so bytes of two versions are never mixed. Only the missing ranges are then
fetched, and `TransferProgress.getResumedBytes()` reports the bytes the partial file already held.

While parts are written, what they hold is made durable and recorded at most once a second, and
again when the download stops, so a crash costs at most about a second of parts. The record stays
small however many parts there are: it holds coalesced ranges, and it is compacted as it grows. A
file that comes in one request keeps no progress.

Once the file is published, the checkpoint is deleted. If a run stopped after publishing but before
deleting it, the next run finds the destination is the partial file itself. If Files.com still
describes the same version, the run finishes without fetching anything; if not, the file is
downloaded again under your `replaceExisting` setting. `discardDownloadCheckpoint` deletes a
checkpoint and the partial file it records, if that is still the download's own.

Some partial files are not removed by the SDK, and you must delete them by hand: one left by a
crash before the checkpoint recorded it, one refused as `STAGE_CHANGED`, and one whose checkpoint
became unreadable, as a power loss can leave it. Each is in a hidden directory beside the
destination, named after it. The partial file's directory has no Windows directory flush, and the
Windows code has not been run natively in the SDK's tests.

```java title="Example Request"
import com.files.net.DownloadOptions;
import java.nio.file.Paths;

DownloadOptions options = DownloadOptions.builder()
    .checkpointDirectory(Paths.get(System.getProperty("user.home"), ".files-checkpoints"))
    .build();
// Run again after a failure or a crash to continue where it stopped.
client.transfers().download("images/big.iso", Paths.get("big.iso"), options);

// Or start over.
client.transfers().discardDownloadCheckpoint("images/big.iso", Paths.get("big.iso"), options);
```

## Mock Server

Files.com publishes a mock Files.com API server, which is useful for testing your use of the Files.com
SDKs and other direct integrations against the Files.com API in an integration test environment.
It never checks credentials: send any placeholder API key, such as `YOUR_API_KEY`, and never use
real Files.com credentials with it.

The server has two modes, chosen when it starts:

* **Legacy mode** (the default) checks required parameters and parameter types, then returns a fixed
  example response for each API endpoint. It does not maintain state and it does not deeply inspect
  your submissions for correctness, which makes it useful for testing basic network operations and
  JSON encoding for your SDK or API client.
* **Simulation mode** keeps records, files and folders in memory, so a test can create, list, update
  and delete resources, upload a file and download the same bytes, and make chosen requests fail,
  stall or lose their connection on purpose. Requests it does not simulate fail with a clear error
  instead of returning an example response.

### Quick Start with Docker

With [Docker](https://docs.docker.com/get-docker/) installed, start the published image from
[Docker Hub](https://hub.docker.com/r/filescom/files-mock-server) with the command shown. It runs
legacy mode at `http://127.0.0.1:4041`, reachable from your machine only: point your integration's
Files.com API base URL there and use a placeholder API key. Stop it with Ctrl+C; `--rm` removes its
container.

The published image is built for `linux/amd64` only, so the command asks for that platform;
without it, Docker on a machine of another architecture, such as an Apple silicon Mac, looks for
an image the tag does not have. The `latest` tag moves to whichever server was published last,
and `--pull=always` fetches it again each time, so it need not include simulation mode. To run
simulation mode, build the image from the source as below.

```shell title="Start the Mock Server with Docker"
docker run --platform linux/amd64 --rm --pull=always -p 127.0.0.1:4041:4041 filescom/files-mock-server:latest

## In another terminal, with a placeholder API key:
curl -H "X-FilesAPI-Key: YOUR_API_KEY" http://127.0.0.1:4041/api/rest/v1/users
```

### Simulation Mode in Docker

To run exactly the server the source's README describes, simulation mode included, build the image
from a checkout of the source on [GitHub](https://github.com/Files-com/files-mock-server) and run
it by the image ID you record with your test results. Only `linux/amd64` builds of the source have
been checked, so both commands ask for that platform.

Simulation mode listens only on loopback unless told otherwise, so the command binds it to the
container's interfaces with `-b tcp://0.0.0.0:4041` and publishes the port on your machine's
loopback address only. `FILES_MOCK_TRANSFER_ORIGIN` tells it the address your tests use, so the
upload and download URLs it returns point at the published port.

Simulation mode keeps its state only in the server process. Its control endpoints under
`/__files_mock/v1` report when the server is ready, reset it with your fixtures, add fault rules
and return the journal of the requests it received. It refuses work over its limits instead of
truncating it. The README in the source describes all of these, the operations it simulates and
how to configure its limits.

```shell title="Simulation Mode from the Source"
## In a checkout of the source:
docker build --platform linux/amd64 -t files-mock-server:local .
image=$(docker image inspect --format '{{.Id}}' files-mock-server:local)
echo "$image"   # record this ID with your test results
docker run --platform linux/amd64 --rm -e FILES_MOCK_MODE=simulation \
  -e FILES_MOCK_TRANSFER_ORIGIN=http://127.0.0.1:40410 \
  -p 127.0.0.1:40410:4041 "$image" -b tcp://0.0.0.0:4041

## In another terminal:
curl http://127.0.0.1:40410/__files_mock/v1/ready
```

### From the Source with Ruby

Start the server from its source with Ruby and Bundler. `FILES_MOCK_MODE` is read once at startup:
leaving it unset or setting it to `legacy` starts legacy mode, `simulation` starts simulation mode,
and any other value stops startup with an error. Legacy mode listens on port 4041 on all IPv4
interfaces, and simulation mode on `127.0.0.1:4041`.

```shell title="Start the Mock Server with Ruby"
bundle install

## Legacy mode
bundle exec puma

## Simulation mode
FILES_MOCK_MODE=simulation bundle exec puma
```

## Upgrading

Version 2.0 of `com.files:files-sdk` still runs on Java 8 or later. Code that calls the SDK's
static methods and models generally compiles against it unchanged, but code that extends SDK
classes may not; see Clients. Version 2.0 also changes how the SDK logs, raises the versions of
its dependencies, and changes how some errors, saves, listings, file parameters, retries and
transfers behave.

#### Logging

SDK 1.x brought Log4j Core, Log4j's SLF4J 1.x binding and a `log4j2.xml` file that turned
the `com.files` loggers off. SDK 2.0 depends only on the SLF4J 2 API, and your application
chooses the logging provider and its configuration. If your application relied on the Log4j
that SDK 1.x brought with it, add a provider as described in Logging, under Configuration.
The SDK's warnings now appear wherever your configuration sends them; set the `com.files`
level to change that.

Apache HttpClient's own log messages, under `org.apache.http`, now reach the same SLF4J
provider. Keep `com.files` and `org.apache.http` at INFO or above in production: at DEBUG,
both log credentials.

#### Dependencies

Later 1.x versions may already use some of these 2.0 versions, so compare with the version you
are upgrading from.

| Dependency | Earlier SDK 1.x | SDK 2.0 |
| --- | --- | --- |
| Jackson (`jackson-annotations`, `jackson-core`, `jackson-databind`) | 2.18.11 | 2.21.7 |
| Apache HttpClient and HttpMime | 4.5.14 | 4.5.14 |
| Apache HttpCore | 4.4.15 | 4.4.16 |
| Commons Codec | 1.11 | 1.23.0 |
| Commons Logging | 1.2 | 1.4.0 |
| SLF4J API | 1.7.x, through Log4j | 2.0.20 |
| Log4j | 2.19.0 | none |
| JNA (`jna`) | none | 5.19.1 |

If your build manages Jackson itself, keep `jackson-annotations`, `jackson-core` and
`jackson-databind` on one release, for example with Jackson's `jackson-bom`.

JNA, under the Apache License 2.0 or the LGPL 2.1, lets downloads on macOS and Windows keep their
unfinished file private; see Uploads and Downloads. It includes its own native library for each
platform. On Java 24 and later, its native access prints a warning unless your application allows
it: with `--enable-native-access=ALL-UNNAMED` when JNA is on the class path,
`--enable-native-access=com.sun.jna` when it is on the module path, or the manifest attribute
`Enable-Native-Access: ALL-UNNAMED` in an executable JAR.

#### Errors

Errors are classified by the error type in the API's response, never by the HTTP status alone:

* A response without a structured error body raises `ApiConnectionException`, whatever its
  status. It used to raise `ServerErrorException` for 5xx statuses, `AuthenticationException`
  for 403 and `InvalidResponseException` for other 4xx statuses. The SDK no longer throws
  `ServerErrorException` or `InvalidResponseException`, and both are deprecated.
* An error type that the SDK does not recognize, or a missing one, raises `ApiErrorException`
  with the response's details and headers, instead of a `NullPointerException` or another
  unchecked exception.

Review `catch` blocks for `ServerErrorException`, `InvalidResponseException` and
`AuthenticationException`; `AuthenticationException` is now raised only when the SDK has no
credentials to send.

The message of a request that failed without a response names the kind of failure and the
request's method and path, such as `The request failed: the connection could not be made
(ConnectException) (response to GET /api/rest/v1/users/1)`. It no longer repeats the message of the
underlying exception, which can include URLs or other details.

#### Saving and Listing

* For records the API identifies by id, `save()` on a record that already has one updates it
  with a single PATCH request, instead of creating another record. After a successful `save()` or `update()`, the object holds the
  attributes the server returned. A failed request leaves the object unchanged.
* `listAutoPaging()` loads pages as you iterate, continues past empty pages, and stops loading
  when you stop iterating. `all()` and the new `reset()` start over from the first page.

#### File Attachments

Parameters of type file, such as `avatar_file`, now send their `byte[]` as the file. SDK 1.x
sent it as Base64 text, which the API did not take as a file. They also accept an `Attachment`
for local files and streams; see File Attachments.

#### Remote Mount Backend Decimals

`min_free_cpu` and `min_free_mem` are exact decimals. `RemoteMountBackend.create` and `update`
take them as a `String` or a `BigDecimal`, which are sent as exact text, as well as the `Double`
that SDK 1.x took, which is still sent as a JSON number and keeps a double's precision limits.
Make a `BigDecimal` from text, such as `new BigDecimal("0.35")`, not from a `double`. The
attributes remain `BigDecimal`.

```java
HashMap<String, Object> parameters = new HashMap<>();
parameters.put("min_free_cpu", "0.35");
parameters.put("min_free_mem", new BigDecimal("12.5"));
RemoteMountBackend.update(backendId, parameters);
```

#### Clients

`FilesClient` is now also a client you can build, with its own credentials, workspace and
connections; see Clients and Connections. The static methods keep working, through
`FilesClient.defaultClient()`, with one difference: objects and listings they return keep the
static settings that were in effect when they were requested. Changing `FilesClient.apiKey`, for
example, no longer changes the credentials of a listing you already have.

Two changes stop some 1.x code from compiling:

* `FilesClient` is now `final`, so it can no longer be subclassed. In 1.x it was `abstract`, and a
  class could extend it.
* `FilesHttpClient` no longer has its `protected` `httpClient` field, so a subclass that uses that
  field no longer compiles.

Request option maps are copied: changing the map you passed no longer changes the object or
listing a call returned.

A client you build without a transport of your own, or a transport you build with
`HttpTransport.builder()`, keeps up to 1024 connections to one host and 2048 in all, so that an
automatic transfer can send as many parts at once as it targets. The limits apply to every request
the client sends, Files.com API requests included: requests that a pool of 20 connections a host,
such as the static methods' `FilesClient.connectionManager`, would have queued now each go out at
once on a connection of its own. Connections are opened only as they are needed and closed once idle
for 5 seconds. For lower limits, set `maxConnectionsPerRoute` and `maxConnections` on the transport
builder, or give the client a pool of your own; transfers stay within the limits either way. The
static methods keep their pool and its 20 connections a host.

Files.com credentials now go only to the API root a call starts at; in 1.x they went to whatever
host a request was first sent to. An API request to an absolute URL elsewhere, such as one passed
to `FilesClient.apiRequest`, carries none: to call another site, pass its root as the `api_root`
request option. Requests sent through `FilesHttpClient`'s executor carry credentials only to the
`apiRoot` property.

#### Retries and Time Limits

Later 1.x versions may already use some of these 2.0 values, such as the attempts, the wait and
the time limits, so compare with the version you are upgrading from.

| | Earlier SDK 1.x | SDK 2.0 |
| --- | --- | --- |
| Attempts of a request | 5, retrying 429 and 502 or above | 4 (3 retries), retrying 429, 502 or above, and 500 in answer to a GET |
| Wait before the first retry | 500 ms, doubling | 100 ms, doubling, up to 10 seconds |
| Retried failures | any I/O failure | failures that may be temporary; not a URL that cannot be sent, a certificate or host name that does not verify, or redirects that loop or go past the limit |
| Apache HttpClient's own resend | additional to the SDK's | off: each resend is one of the SDK's retries |
| Connect / read time limit | 10 / 30 seconds | 30 / 80 seconds; the read limit also covers a response body |
| Region mismatch | always an error | followed to Files.com's own servers, as a retry |

The `upstreamMaxAttempts` property still sets the number of attempts; `maxNetworkRetries` now
sets the number of retries. The static methods read them, and the wait, again before each attempt;
a client you build keeps the values it was built with. `connectTimeoutMillis` and
`readTimeoutMillis` keep their meaning: 0 means no limit, and a negative value leaves the limit to
the system default. The static methods wait up to 10 seconds for a connection from the pool. A
retry is logged when it happens, not after the last attempt.
A request made on a thread that is interrupted, or that is interrupted while it waits, now stops
and throws `OperationCancelledException`, and the thread keeps its interrupt status.

#### Uploads and Downloads

`FileUploadPart.putInputStream`, `putBufferedInputStream` and `putLocalFile` now run on the
same engine as `client.transfers()`: they send the parts Files.com plans with the method and
headers it gives, send parts in parallel only when Files.com allows it, retry a failed part with
the same bytes only when Files.com allows it, and complete the upload with every part's ETag and
the file's size. They no longer close your stream. The `Date` given to `putInputStream` and
`putBufferedInputStream` is now recorded as the file's modification time (`provided_mtime`), and
`putLocalFile` now records the local file's last modification time, to the second, the same way.

`File.saveAsLocalFile` now writes the download to a new, private file beside the destination and
replaces the destination only once every byte has arrived, instead of truncating the destination
first. The replaced file's permissions are kept, and a destination owned by another user is not
replaced. Because the download replaces the destination's directory entry instead of writing into
the existing file:

* A symbolic link at the destination is replaced by a regular file, so the file it pointed to is
  no longer updated.
* Other hard links to the destination keep the old contents.
* The destination's directory must allow creating files in it, even when the destination itself
  can be written.
* A device such as `/dev/stdout`, a FIFO or another special file is not written to: the download
  fails, or replaces it with a regular file. To send a download to a stream, read it from
  `File.getInputStream()`.

A download whose length differs from the size Files.com gives for the file (`File.size`) now
fails instead of being saved. Downloads to a `Path` with `client.transfers()` work the same way.

On macOS and Windows, downloads use JNA to keep the unfinished file private, and fail with an
`IOException`, before they create anything, where JNA cannot be loaded. On Windows, unless you set
`jna.tmpdir` or turn JNA's extraction off, the SDK lets JNA write its library only to a directory that only your
application's account and administrators can change, and sets `jna.tmpdir` to it; see Uploads and
Downloads. A library found through `jna.boot.library.path` or `jna.nosys=false` is loaded as supplied,
without being checked by the SDK. On macOS and Windows, a download to a
volume that does not enforce file ownership or keep ACLs, such as a FAT drive or a macOS volume set
to ignore ownership, now fails, since nothing on it can be kept private.

#### Files.com Connectors

The Files.com MuleSoft and Boomi connectors package the SDK version they were built with.
Updating the SDK in your own application does not change the SDK that a connector uses.

## Downloading to a stream

`client.transfers().download(remotePath, out, options)` writes a remote file to an `OutputStream`
of yours, in order, as its bytes arrive, without a local copy. The stream stays yours: it is never
flushed or closed. A version-bound part that fails is asked for again only from the first byte not
yet written; once some of a file that came unbound in one request has been written, a failure stops
the download rather than writing the file again from its start. The result's destination is null,
and a checkpoint cannot be kept.

```java
try (OutputStream out = Files.newOutputStream(Paths.get("report.csv"))) {
  DownloadResult result = client.transfers().download("reports/2024.csv", out, DownloadOptions.defaults());
  System.out.println(result.getSize() + " bytes");
}
```

## Uploading to Remote Servers, Snapshots and Child Sites

`client.transfers().uploadToRemoteServer(source, remoteServerId, destinationPath, options)`,
`uploadToSnapshot` and `uploadToChildSite` upload a local file into a Remote Server, Snapshot or Child
Site: the same upload, with the same requests, as `upload` to `_/RemoteServers/<id>/<path>`,
`_/Snapshots/<id>/<path>` or `_/Sites/<id>/<path>`. The destination path is normalized first: both
slash kinds separate folders, and empty, `.` and `..` components are dropped, so it stays inside the
destination. Without one, the file keeps its own name. `File.copyToRemoteServer`,
`File.moveToRemoteServer` and their Snapshot and Child Site siblings copy and move remote files the
same way: statically with the global settings, or on a `File` with the client it came from.

```java
UploadResult result = client.transfers().uploadToRemoteServer(Paths.get("q1.csv"), 42L, "reports/2026/q1.csv");
client.transfers().uploadToSnapshot(Paths.get("q1.csv"), 7L); // to _/Snapshots/7/q1.csv
FileAction copy = client.files().find("reports/2026/q1.csv", null, null).copyToChildSite(3L, "inbox/q1.csv");
```

The older `File.uploadToRemoteServer(remoteServerId, destinationPath)` and its siblings, with or without
parameters and options, only start an upload: they return the `FileUploadPart` for its first part and
send no file data.

## Using an Apache HttpClient of your own

`HttpTransport.builder().buildClient()` builds a `GuardedApacheClient`: an Apache HttpClient 4 with
your proxy, TLS, pool and interceptors, which you can send your own requests with before, while and
after Files.com clients use it. `HttpTransport.borrowed(http)` makes a transport that sends a
client's requests with that very client and leaves it yours to close; `HttpTransport.owned(http)`
makes one that closes it when the transport is closed.

```java
GuardedApacheClient http = HttpTransport.builder()
    .customize(builder -> builder.setProxy(new HttpHost("proxy.example.com", 8080)))
    .buildClient();
HttpTransport transport = HttpTransport.borrowed(http);
FilesClient client = FilesClient.builder().apiKey(apiKey).transport(transport).build();
// Use client, and http itself, from any thread.
client.close();
transport.close(); // aborts only this transport's requests; http stays open
http.close();
```

- Closing a transport that borrows the client aborts only its own requests, including a response
  whose body is still being read; the client, its pool and other transports using it go on. Closing
  the client, or a transport that owns it, closes it for every transport using it.
- Transports using one client share its pool's limits for transfers.
- Only a client built this way can be given to a transport: an Apache client built otherwise could
  bypass the SDK's protections. Your own requests never carry Files.com credentials, which are removed
  as each request is written unless a Files.com client sends it to its API root. The client does not
  retry automatically; each Files.com client retries its requests itself, within its own limit.

## Using `java.nio.file` with Files.com

`FilesComFileSystems` makes a `java.nio.file.FileSystem` bound to one `FilesClient`, so code
written for `java.nio.file.Files` works on remote files once its first path comes from it:

```java
try (FileSystem files = FilesComFileSystems.builder(client)
    .stageDirectory(Paths.get("/var/lib/app/files-stage"))   // an existing local directory
    .stageQuotaBytes(4L * 1024 * 1024 * 1024)                 // most bytes staged at once
    .build()) {
  Path report = files.getPath("/reports/2024.csv");
  Files.write(report, bytes);                                 // published when the stream closes
  byte[] read = Files.readAllBytes(report);
  try (DirectoryStream<Path> entries = Files.newDirectoryStream(report.getParent())) {
    entries.forEach(entry -> System.out.println(entry));      // one page at a time
  }
  Files.createDirectories(files.getPath("/archive/2024"));
  Files.move(report, files.getPath("/archive/2024/2024.csv"), StandardCopyOption.REPLACE_EXISTING);
}
```

- The file system borrows the client: closing it closes its own channels and directory streams,
  never the client. It is made only by `FilesComFileSystems`; no URI finds it, and its paths'
  URIs hold an opaque mount ID, never credentials.
- Reading downloads the file into a private copy in the stage directory, read and written only
  through the handle that created it. Its size is reserved in the stage quota first; a file whose
  size is unknown, or that does not fit, is refused, and a download is stopped before it writes
  past that size.
- Writing is staged. Opening with `CREATE` and `TRUNCATE_EXISTING` (the default of `Files.write`)
  prepares the empty copy, then publishes the file empty before the stream is returned; `WRITE`,
  `READ`+`WRITE` and `APPEND` change a copy of an existing file. Closing uploads the copy once.
  Flushing publishes nothing, and one channel of a file system writes a file at a time, however
  its path is spelled where Files.com compares paths alike. A failed close keeps the copy and is
  not retried; the exception's `getOtherFile()` is the name the copy's directory was created with,
  or null, a diagnostic that is not checked again and may no longer lead to the copy.
- `Files.delete` deletes a file or an empty folder and waits for it to be gone; copy and move wait
  for the API to finish, and one the API skips fails. Copying a folder creates an empty folder.
- API failures are native `IOException`s with the SDK exception as the cause; only the error type
  `not-found/file-not-found`, and the type `not-found` with the body code 404 from the request that
  starts a delete, are a `NoSuchFileException`. That delete's `not-found` means the API cannot
  resolve the path, which it reports alike for a stored path it cannot tell apart, so
  `Files.deleteIfExists` returning `false` reflects that unresolved logical path; it establishes
  neither physical absence nor the outcome of earlier request attempts. A not-found while the
  delete is waited for is a failure. `CREATE_NEW`, `CREATE` without
  `TRUNCATE_EXISTING`, `ATOMIC_MOVE`, links, watching and POSIX permissions throw
  `UnsupportedOperationException`, caused by a `NotImplementedException`, before changing
  anything.

## Development

The SDK is compiled for Java 8, but building and checking it needs JDK 25. The Maven Wrapper
(`./mvnw`) provides the pinned Maven version.

```sh
./mvnw verify       # compile, test, package, then check formatting and Checkstyle
./mvnw rewrite:run  # format the sources
./test.sh           # format, start the test servers, then verify, with a pinned JDK 25 it downloads on first use
```

`FilesApiTest` also runs against two WireMock servers in their own JDK 17 process, which `test.sh`
starts and stops; set `FILES_TEST_SERVER_JAVA_HOME` to that JDK. To run `./mvnw verify` yourself,
start them first, as `test-server/servers.sh` describes.

`FilesAuthTest` runs against the [Files.com mock server](https://github.com/Files-com/files-mock-server).
It connects to `localhost:4041` unless `FILES_MOCK_SERVER_HOST` and `FILES_MOCK_SERVER_PORT` are set.
